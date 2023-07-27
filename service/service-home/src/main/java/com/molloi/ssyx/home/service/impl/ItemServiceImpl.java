package com.molloi.ssyx.home.service.impl;

import com.molloi.ssyx.client.activity.ActivityFeignClient;
import com.molloi.ssyx.client.product.ProductFeignClient;
import com.molloi.ssyx.client.search.SkuFeignClient;
import com.molloi.ssyx.home.service.ItemService;
import com.molloi.ssyx.vo.product.SkuInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Molloi
 * @date 2023/7/20 21:11
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private ActivityFeignClient activityFeignClient;

    @Resource
    private SkuFeignClient skuFeignClient;

    @Override
    public Map<String, Object> item(Long skuId, Long userId) {
        Map<String,Object> result = new HashMap<>();

        // skuId查询
        CompletableFuture<Void> skuInfoVoCompletableFuture = CompletableFuture.runAsync(() -> {
            // 远程调用获取sku信息
            SkuInfoVo skuInfoVo = productFeignClient.getSkuInfoVo(skuId);
            result.put("skuInfoVo", skuInfoVo);
        }, threadPoolExecutor);

        // sku对应的优惠券信息
        CompletableFuture<Void> activityCompletableFuture = CompletableFuture.runAsync(() -> {
            Map<String,Object> activityMap = activityFeignClient.findActivityAndCoupon(skuId, userId);
            result.putAll(activityMap);
        }, threadPoolExecutor);

        // 更新商品热度
        CompletableFuture<Void> hotCompletableFuture = CompletableFuture.runAsync(() -> {
            // 远程调用更新热度
            skuFeignClient.incrHotScore(skuId);
        }, threadPoolExecutor);

        // 任务组合
        CompletableFuture.allOf(
                skuInfoVoCompletableFuture,
                activityCompletableFuture,
                hotCompletableFuture
        ).join();

        return result;
    }

}

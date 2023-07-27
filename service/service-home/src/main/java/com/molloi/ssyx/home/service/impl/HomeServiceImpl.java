package com.molloi.ssyx.home.service.impl;

import com.molloi.ssyx.client.product.ProductFeignClient;
import com.molloi.ssyx.client.search.SkuFeignClient;
import com.molloi.ssyx.client.user.UserFeignClient;
import com.molloi.ssyx.home.service.HomeService;
import com.molloi.ssyx.model.product.Category;
import com.molloi.ssyx.model.product.SkuInfo;
import com.molloi.ssyx.model.search.SkuEs;
import com.molloi.ssyx.vo.user.LeaderAddressVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Molloi
 * @date 2023/6/23 21:34
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private SkuFeignClient skuFeignClient;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Map<String, Object> homeData(Long userId) {
        Map<String,Object> result = new HashMap<>();
        // 1 根据userId获取当前登录用户提货信息 远程调用service-user模块
        CompletableFuture<Void> leaderAddressCompletableFuture = CompletableFuture.runAsync(() -> {
            LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
            result.put("leaderAddressVo", leaderAddressVo);
        }, threadPoolExecutor);


        // 2 获取所有分类 远程调用service-product模块
        CompletableFuture<Void> categoryCompletableFuture = CompletableFuture.runAsync(() -> {
            List<Category> categoryList = productFeignClient.findAllCategoryList();
            result.put("categoryList", categoryList);
        }, threadPoolExecutor);


        // 3 获取新人专享商品 远程调用service-product模块
        CompletableFuture<Void> newPersonSkuInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
            result.put("newPersonSkuInfoList", newPersonSkuInfoList);
        }, threadPoolExecutor);


        // 4 获取爆款产品 远程调用service-search模块
        CompletableFuture<Void> hotSkuCompletableFuture = CompletableFuture.runAsync(() -> {
            List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
            result.put("hotSkuList", hotSkuList);
        }, threadPoolExecutor);

        // 任务组合
        CompletableFuture.allOf(
                leaderAddressCompletableFuture,
                categoryCompletableFuture,
                newPersonSkuInfoCompletableFuture,
                hotSkuCompletableFuture
        ).join();

        return result;
    }
}

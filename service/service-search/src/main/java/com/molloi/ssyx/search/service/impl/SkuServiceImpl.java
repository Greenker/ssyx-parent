package com.molloi.ssyx.search.service.impl;

import com.molloi.ssyx.client.activity.ActivityFeignClient;
import com.molloi.ssyx.client.product.ProductFeignClient;
import com.molloi.ssyx.common.auth.AuthContextHolder;
import com.molloi.ssyx.enums.SkuType;
import com.molloi.ssyx.model.product.Category;
import com.molloi.ssyx.model.product.SkuInfo;
import com.molloi.ssyx.model.search.SkuEs;
import com.molloi.ssyx.search.repository.SkuRepository;
import com.molloi.ssyx.search.service.SkuService;
import com.molloi.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 同步数据到ES
 * @author Molloi
 * @date 2023/6/17 15:49
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuRepository skuRepository;

    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private ActivityFeignClient activityFeignClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void upperSku(Long skuId) {
        // 通过远程调用获取数据
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        SkuEs skuEs = new SkuEs();
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            // TODO 待完善-秒杀商品
        }
        skuRepository.save(skuEs);
    }

    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

    @Override
    public List<SkuEs> findHotSkuList() {
        Pageable pageable = PageRequest.of(0,10); // 0代表第一页
        Page<SkuEs> pageModel = skuRepository.findByOrderByHotScoreDesc(pageable);
        return pageModel.getContent();
    }

    @Override
    public Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo) {
        // 1 获取当前用户的仓库id，向skuEsQueryVo中设置wareId
        skuEsQueryVo.setWareId(AuthContextHolder.getWareId());
        // 2 调用SkuRepository方法，进行条件查询
        Page<SkuEs> pageModel = null;
        String keyword = skuEsQueryVo.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            pageModel = skuRepository.findByCategoryIdAndWareId(skuEsQueryVo.getCategoryId(),
                    skuEsQueryVo.getWareId(),pageable);
        } else {
            pageModel = skuRepository.findByKeywordAndWareId(skuEsQueryVo.getKeyword(),skuEsQueryVo.getWareId(),pageable);
        }
        // 3 查询商品是否存在优惠活动
        List<SkuEs>  skuEsList =  pageModel.getContent();
        //获取sku对应的促销活动标签
        if(!CollectionUtils.isEmpty(skuEsList)) {
            List<Long> skuIdList = skuEsList.stream().map(sku -> sku.getId()).collect(Collectors.toList());
            Map<Long, List<String>> skuIdToRuleListMap = activityFeignClient.findActivity(skuIdList);
            if(null != skuIdToRuleListMap) {
                skuEsList.forEach(skuEs -> skuEs.setRuleList(skuIdToRuleListMap.get(skuEs.getId())));
            }
        }
        return pageModel;
    }

    @Override
    public Boolean incrHotScore(Long skuId) {

        String key = "hotScore";
        // redis 保存每次+1
        Double hotScore = redisTemplate.opsForZSet().incrementScore(key, "skuId:" + skuId, 1);

        if (hotScore % 10 == 0) {
            Optional<SkuEs> optional = skuRepository.findById(skuId);
            SkuEs skuEs = optional.get();
            skuEs.setHotScore(Math.round(hotScore));
            skuRepository.save(skuEs); // 存在id值执行更新操作，无id值执行添加操作
        }

        return true;
    }
}

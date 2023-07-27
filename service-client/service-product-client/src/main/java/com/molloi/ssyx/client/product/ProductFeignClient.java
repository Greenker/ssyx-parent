package com.molloi.ssyx.client.product;

import com.molloi.ssyx.model.order.CartInfo;
import com.molloi.ssyx.model.product.Category;
import com.molloi.ssyx.model.product.SkuInfo;
import com.molloi.ssyx.vo.order.OrderConfirmVo;
import com.molloi.ssyx.vo.product.SkuInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Molloi
 * @date 2023/6/17 19:01
 */

@FeignClient(value = "service-product")
public interface ProductFeignClient {

    // 根据分类id获取分类信息
    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    Category getCategory(@PathVariable("categoryId") Long categoryId);

    // 根据skuId获取sku信息
    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);

    // 批量获取sku信息
    @PostMapping("/api/product/inner/findSkuInfoList")
    List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList);

    // 根据关键字匹配sku列表
    @GetMapping("/api/product/inner/findSkuInfoByKeyword/{keyword}")
    List<SkuInfo>  findSkuInfoByKeyword(@PathVariable String keyword);

    @PostMapping("/api/product/inner/findSkuInfoList")
    List<Category> findCategoryList(List<Long> idList);

    // 获取所有分类
    @GetMapping("/api/product/inner/findAllCategoryList")
    List<Category> findAllCategoryList();

    // 获取新人专享
    @GetMapping("/api/product/inner/findNewPersonSkuInfoList")
    List<SkuInfo> findNewPersonSkuInfoList();

    // 根据skuId获取sku信息
    @GetMapping("/api/product/inner/getSkuInfoVo/{skuId}")
    SkuInfoVo getSkuInfoVo(@PathVariable Long skuId);

}

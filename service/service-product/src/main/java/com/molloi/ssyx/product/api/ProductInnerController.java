package com.molloi.ssyx.product.api;

import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.product.Category;
import com.molloi.ssyx.model.product.SkuInfo;
import com.molloi.ssyx.product.service.CategoryService;
import com.molloi.ssyx.product.service.SkuInfoService;
import com.molloi.ssyx.vo.product.SkuInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Molloi
 * @date 2023/6/17 18:50
 */

@RestController
@RequestMapping("/api/product")
public class ProductInnerController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private SkuInfoService skuInfoService;

    // 根据分类id获取分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    // 根据skuId获取sku信息
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        return skuInfoService.getById(skuId);
    }

    // 根据skuId列表获取sku信息
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList){
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    // 根据关键字匹配sku列表
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo>  findSkuInfoByKeyword(@PathVariable String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }

    // 根据skuId列表获取sku信息
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> skuIdList){
        return categoryService.listByIds(skuIdList);
    }

    // 获取所有分类
    @GetMapping("inner/findAllCategoryList")
    public List<Category> findAllCategoryList(){
        return categoryService.list();
    }

    // 获取新人专享商品信息
    @GetMapping("inner/findNewPersonSkuInfoList")
    List<SkuInfo> findNewPersonSkuInfoList(){
        return skuInfoService.findNewPersonSkuInfoList();
    }

    // 根据skuId获取sku信息
    @GetMapping("inner/getSkuInfoVo/{skuId}")
    public SkuInfoVo getSkuInfoVo(@PathVariable Long skuId) {
        return skuInfoService.getSkuInfoVo(skuId);
    }

}

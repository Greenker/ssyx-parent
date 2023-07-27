package com.molloi.ssyx.search.controller;

import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.search.SkuEs;
import com.molloi.ssyx.search.service.SkuService;
import com.molloi.ssyx.vo.search.SkuEsQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品搜索列表接口
 * @author Molloi
 * @date 2023/6/17 15:48
 */
@RestController
@RequestMapping("api/search/sku")
public class SkuApiController {

    @Resource
    private SkuService skuService;

    // 上架
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperSku(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    // 下架
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取爆品商品")
    @GetMapping("inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }

    @ApiOperation("查询当前分类下的所有商品")
    @GetMapping("{page}/{limit}")
    public Result skuList(@PathVariable Integer page, @PathVariable Integer limit, SkuEsQueryVo skuEsQueryVo) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<SkuEs> pageModel = skuService.search(pageable,skuEsQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "更新商品incrHotScore")
    @GetMapping("inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId) {
        // 调用服务层
        return skuService.incrHotScore(skuId);
    }

}

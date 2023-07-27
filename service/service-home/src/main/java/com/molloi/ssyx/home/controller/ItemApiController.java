package com.molloi.ssyx.home.controller;

import com.molloi.ssyx.common.auth.AuthContextHolder;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.home.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Molloi
 * @date 2023/7/20 21:09
 */

@Api(tags = "商品详情")
@RestController
@RequestMapping("api/home")
public class ItemApiController {

    @Resource
    private ItemService itemService;

    @ApiOperation(value = "获取sku详细信息")
    @GetMapping("item/{skuId}")
    public Result index(@PathVariable Long skuId) {
        Long userId = AuthContextHolder.getUserId();
        Map<String,Object> map = itemService.item(skuId,userId);
        return Result.ok(map);
    }

}

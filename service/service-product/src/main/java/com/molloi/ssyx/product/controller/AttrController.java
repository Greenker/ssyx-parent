package com.molloi.ssyx.product.controller;


import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.product.Attr;
import com.molloi.ssyx.product.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
@Api(tags = "平台属性分组列表管理")
@RestController
@RequestMapping("/admin/product/attr")
public class AttrController {

    @Resource
    private AttrService attrService;

    @ApiOperation("获取列表")
    @GetMapping("{attrGroupId}")
    public Result list(@PathVariable Long attrGroupId) {
        List<Attr> list = attrService.getAttrListByGroupId(attrGroupId);
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Attr attr = attrService.getById(id);
        return Result.ok(attr);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Result.ok(null);
    }

}


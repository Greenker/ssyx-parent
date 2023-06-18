package com.molloi.ssyx.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.product.AttrGroup;
import com.molloi.ssyx.model.product.AttrGroup;
import com.molloi.ssyx.product.service.AttrGroupService;
import com.molloi.ssyx.vo.product.AttrGroupQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
@Api(tags = "平台属性分组管理")
@RestController
@RequestMapping("/admin/product/attrGroup")
@CrossOrigin
public class AttrGroupController {

    @Resource
    private AttrGroupService attrGroupService;

    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, AttrGroupQueryVo attrGroupQueryVo) {
        Page<AttrGroup> param = new Page<>(page, limit);
        IPage<AttrGroup> pageModel = attrGroupService.selectPage(param, attrGroupQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.ok(attrGroupService.getById(id));
    }

    @ApiOperation("添加")
    @PostMapping("save")
    public Result save(@RequestBody AttrGroup attrGroup) {
        return Result.ok(attrGroupService.save(attrGroup));
    }

    @ApiOperation("修改")
    @PutMapping("update")
    public Result updateById(@RequestBody AttrGroup attrGroup) {
        return Result.ok(attrGroupService.updateById(attrGroup));
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id) {
        return Result.ok(attrGroupService.removeById(id));
    }

    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result removeRows(@RequestBody List<Long> idList) {
        return Result.ok(attrGroupService.removeByIds(idList));
    }

    @ApiOperation(value = "获取全部属性分组")
    @GetMapping("findAllList")
    public Result findAllList() {
        return Result.ok(attrGroupService.list());
    }

}


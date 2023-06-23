package com.molloi.ssyx.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.product.Category;
import com.molloi.ssyx.product.service.CategoryService;
import com.molloi.ssyx.vo.product.CategoryQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
@Api(tags = "商品分类")
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation("商品分类列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, CategoryQueryVo categoryQueryVo) {

        Page<Category> param = new Page<>(page, limit);
        IPage<Category> pageModel = categoryService.selectPageCategory(param, categoryQueryVo);

        return Result.ok(pageModel);

    }

    @ApiOperation("根据id查询分类")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.ok(categoryService.getById(id));
    }

    @ApiOperation("添加分类")
    @PostMapping("save")
    public Result save(@RequestBody Category category) {
        return Result.ok(categoryService.save(category));
    }

    @ApiOperation("修改分类")
    @PutMapping("update")
    public Result updateById(@RequestBody Category category) {
        return Result.ok(categoryService.updateById(category));
    }

    @ApiOperation("根据id删除分类")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id) {
        return Result.ok(categoryService.removeById(id));
    }

    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result removeRows(@RequestBody List<Long> idList) {
        return Result.ok(categoryService.removeByIds(idList));
    }

    @ApiOperation("查询所有分类")
    @GetMapping("findAllList")
    public Result findAllList() {
        return Result.ok(categoryService.list());
    }

}


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
@CrossOrigin
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

}


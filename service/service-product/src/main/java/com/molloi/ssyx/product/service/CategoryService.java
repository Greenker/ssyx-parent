package com.molloi.ssyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.model.product.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.vo.product.CategoryQueryVo;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
public interface CategoryService extends IService<Category> {

    IPage<Category> selectPageCategory(Page<Category> param, CategoryQueryVo categoryQueryVo);
}

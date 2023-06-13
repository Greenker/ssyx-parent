package com.molloi.ssyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.model.product.Category;
import com.molloi.ssyx.product.mapper.CategoryMapper;
import com.molloi.ssyx.product.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.vo.product.CategoryQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public IPage<Category> selectPageCategory(Page<Category> param, CategoryQueryVo categoryQueryVo) {

        String name = categoryQueryVo.getName();
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name),Category::getName, name);

        return baseMapper.selectPage(param,queryWrapper);
    }

}

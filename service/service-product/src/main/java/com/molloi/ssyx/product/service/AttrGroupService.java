package com.molloi.ssyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.model.product.AttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.vo.product.AttrGroupQueryVo;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
public interface AttrGroupService extends IService<AttrGroup> {

    IPage<AttrGroup> selectPage(Page<AttrGroup> param, AttrGroupQueryVo attrGroupQueryVo);

}

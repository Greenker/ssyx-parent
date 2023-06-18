package com.molloi.ssyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.molloi.ssyx.model.product.Attr;
import com.molloi.ssyx.product.mapper.AttrMapper;
import com.molloi.ssyx.product.service.AttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Override
    public List<Attr> getAttrListByGroupId(Long attrGroupId) {
        return baseMapper.selectList(new LambdaQueryWrapper<Attr>().eq(Attr::getAttrGroupId,attrGroupId));
    }

}

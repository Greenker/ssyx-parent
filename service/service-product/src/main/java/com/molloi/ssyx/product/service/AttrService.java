package com.molloi.ssyx.product.service;

import com.molloi.ssyx.model.product.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
public interface AttrService extends IService<Attr> {

    List<Attr> getAttrListByGroupId(Long attrGroupId);

}

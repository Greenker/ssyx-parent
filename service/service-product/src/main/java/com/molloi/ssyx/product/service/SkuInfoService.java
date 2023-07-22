package com.molloi.ssyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.model.product.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.vo.product.SkuInfoQueryVo;
import com.molloi.ssyx.vo.product.SkuInfoVo;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
public interface SkuInfoService extends IService<SkuInfo> {

    IPage<SkuInfo> selectPage(Page<SkuInfo> param, SkuInfoQueryVo skuInfoQueryVo);

    void saveSkuInfo(SkuInfoVo skuInfoVo);

    SkuInfoVo getSkuInfo(Long id);

    void updateSkuInfo(SkuInfoVo skuInfoVo);

    void check(Long skuId, Integer status);

    void publish(Long skuId, Integer status);

    void isNewPerson(Long skuId, Integer status);

    List<SkuInfo> findSkuInfoList(List<Long> skuIdList);

    List<SkuInfo> findSkuInfoByKeyword(String keyword);

    List<SkuInfo> findNewPersonSkuInfoList();

    SkuInfoVo getSkuInfoVo(Long skuId);
}

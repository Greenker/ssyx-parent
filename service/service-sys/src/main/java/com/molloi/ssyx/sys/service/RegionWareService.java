package com.molloi.ssyx.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.sys.RegionWare;
import com.molloi.ssyx.vo.sys.RegionWareQueryVo;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author molloi
 * @since 2023-06-11
 */
public interface RegionWareService extends IService<RegionWare> {

    IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    void saveRegionWare(RegionWare regionWare);

    void updateStatus(Long id, Integer status);

}

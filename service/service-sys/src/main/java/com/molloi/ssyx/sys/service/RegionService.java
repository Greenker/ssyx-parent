package com.molloi.ssyx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.sys.Region;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author molloi
 * @since 2023-06-11
 */
public interface RegionService extends IService<Region> {

    List<Region> getRegionByKeyword(String keyword);
}

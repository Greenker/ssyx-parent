package com.molloi.ssyx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.molloi.ssyx.model.sys.Region;
import com.molloi.ssyx.sys.mapper.RegionMapper;
import com.molloi.ssyx.sys.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author molloi
 * @since 2023-06-11
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public List<Region> getRegionByKeyword(String keyword) {
        return baseMapper.selectList(new LambdaQueryWrapper<Region>().like(Region::getName, keyword));
    }
}

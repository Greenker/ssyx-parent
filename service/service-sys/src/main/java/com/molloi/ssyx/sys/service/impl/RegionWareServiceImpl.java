package com.molloi.ssyx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.common.exception.CustomException;
import com.molloi.ssyx.common.exception.GlobalExceptionHandler;
import com.molloi.ssyx.common.result.ResultCodeEnum;
import com.molloi.ssyx.model.sys.RegionWare;
import com.molloi.ssyx.sys.mapper.RegionWareMapper;
import com.molloi.ssyx.sys.service.RegionWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.vo.sys.RegionWareQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author molloi
 * @since 2023-06-11
 */
@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    @Override
    public IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {

        String keyword = regionWareQueryVo.getKeyword();

        LambdaQueryWrapper<RegionWare> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(RegionWare::getRegionName, keyword).or().like(RegionWare::getWareName, keyword);
        }

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public void saveRegionWare(RegionWare regionWare) {
        LambdaQueryWrapper<RegionWare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RegionWare::getRegionId, regionWare.getRegionId());
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new CustomException(ResultCodeEnum.REGION_OPEN);
        }
        baseMapper.insert(regionWare);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = baseMapper.selectById(id);
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }

}

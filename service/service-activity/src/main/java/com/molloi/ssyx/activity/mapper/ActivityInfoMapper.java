package com.molloi.ssyx.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.molloi.ssyx.model.activity.ActivityInfo;
import com.molloi.ssyx.model.activity.ActivityRule;
import com.molloi.ssyx.model.activity.ActivitySku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author molloi
 * @since 2023-06-17
 */
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {

    List<Long> selectSkuIdListExist(@Param("skuIdList") List<Long> skuIdList);

    List<ActivityRule> findActivityRule(@Param("skuId") Long skuId);

    List<ActivitySku> selectCartActivityList(@Param("skuIdList") List<Long> skuIdList);
}

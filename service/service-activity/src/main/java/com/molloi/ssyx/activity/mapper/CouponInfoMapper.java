package com.molloi.ssyx.activity.mapper;

import com.molloi.ssyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券信息 Mapper 接口
 * </p>
 *
 * @author molloi
 * @since 2023-06-17
 */
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

    List<CouponInfo> selectCouponInfoList(@Param("skuId") Long skuId, @Param("categoryId") Long categoryId,
                                          @Param("userId") Long userId);

    List<CouponInfo> selectCartCouponInfoList(@Param("userId") Long userId);
}

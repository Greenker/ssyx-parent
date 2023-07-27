package com.molloi.ssyx.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.activity.ActivityInfo;
import com.molloi.ssyx.model.activity.ActivityRule;
import com.molloi.ssyx.model.activity.CouponInfo;
import com.molloi.ssyx.model.order.CartInfo;
import com.molloi.ssyx.model.product.SkuInfo;
import com.molloi.ssyx.vo.activity.ActivityRuleVo;
import com.molloi.ssyx.vo.order.CartInfoVo;
import com.molloi.ssyx.vo.order.OrderConfirmVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author molloi
 * @since 2023-06-17
 */
public interface ActivityInfoService extends IService<ActivityInfo> {

    IPage<ActivityInfo> selectPage(Page<ActivityInfo> param);

    Map<String, Object> findActivityRuleList(Long activityId);

    void saveActivityRule(ActivityRuleVo activityRuleVo);

    List<SkuInfo> findSkuInfoByKeyword(String keyword);

    Map<Long, List<String>> findActivity(List<Long> skuIdList);

    Map<String, Object> findActivityAndCoupon(Long skuId, Long userId);

    List<ActivityRule> findActivityRule(Long skuId);

    OrderConfirmVo findCartActivityAndCoupon(List<CartInfo> cartInfoList, Long userId);

    List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList);

}

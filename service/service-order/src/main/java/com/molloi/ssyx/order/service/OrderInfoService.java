package com.molloi.ssyx.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.order.OrderInfo;
import com.molloi.ssyx.vo.order.OrderConfirmVo;
import com.molloi.ssyx.vo.order.OrderSubmitVo;

/**
 * @author Molloi
 * @date 2023/7/27 21:45
 * @title
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 确认订单
     */
    OrderConfirmVo confirmOrder();

    //生成订单
    Long submitOrder(OrderSubmitVo orderParamVo);

    //订单详情
    OrderInfo getOrderInfoById(Long orderId);

}

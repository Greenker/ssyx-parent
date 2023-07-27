package com.molloi.ssyx.order.service.impl;

import com.molloi.ssyx.model.order.OrderInfo;
import com.molloi.ssyx.order.mapper.OrderInfoMapper;
import com.molloi.ssyx.order.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.vo.order.OrderConfirmVo;
import com.molloi.ssyx.vo.order.OrderSubmitVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author molloi
 * @since 2023-07-27
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    public OrderConfirmVo confirmOrder() {
        return null;
    }

    @Override
    public Long submitOrder(OrderSubmitVo orderParamVo) {
        return null;
    }

    @Override
    public OrderInfo getOrderInfoById(Long orderId) {
        return null;
    }
}

package com.molloi.ssyx.order.service.impl;

import com.molloi.ssyx.model.order.OrderItem;
import com.molloi.ssyx.order.mapper.OrderItemMapper;
import com.molloi.ssyx.order.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单项信息 服务实现类
 * </p>
 *
 * @author molloi
 * @since 2023-07-27
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}

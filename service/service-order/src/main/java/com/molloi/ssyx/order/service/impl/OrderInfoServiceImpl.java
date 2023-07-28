package com.molloi.ssyx.order.service.impl;

import com.molloi.ssyx.client.activity.ActivityFeignClient;
import com.molloi.ssyx.client.order.CartFeignClient;
import com.molloi.ssyx.client.user.UserFeignClient;
import com.molloi.ssyx.common.auth.AuthContextHolder;
import com.molloi.ssyx.common.constant.RedisConst;
import com.molloi.ssyx.common.exception.CustomException;
import com.molloi.ssyx.common.result.ResultCodeEnum;
import com.molloi.ssyx.model.order.CartInfo;
import com.molloi.ssyx.model.order.OrderInfo;
import com.molloi.ssyx.order.mapper.OrderInfoMapper;
import com.molloi.ssyx.order.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.vo.order.OrderConfirmVo;
import com.molloi.ssyx.vo.order.OrderSubmitVo;
import com.molloi.ssyx.vo.user.LeaderAddressVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private CartFeignClient cartFeignClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ActivityFeignClient activityFeignClient;

    @Override
    public OrderConfirmVo confirmOrder() {

        // 获取用户id
        Long userId = AuthContextHolder.getUserId();

        // 获取团长等信息
        LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);

        // 获取购物车的商品信息
        List<CartInfo> cartInfoList = cartFeignClient.getCartCheckedList(userId);

        // 生成订单唯一标识
        String orderNo = System.currentTimeMillis() + "";
        redisTemplate.opsForValue().set(RedisConst.ORDER_REPEAT + orderNo, orderNo, 24, TimeUnit.HOURS);

        // 获取购物车满足条件的优惠活动以及优惠券信息
        OrderConfirmVo orderConfirmVo = activityFeignClient.findCartActivityAndCoupon(cartInfoList, userId);
        orderConfirmVo.setOrderNo(orderNo);
        orderConfirmVo.setLeaderAddressVo(leaderAddressVo);

        return orderConfirmVo;
    }

    @Override
    public Long submitOrder(OrderSubmitVo orderParamVo) {
        Long userId = AuthContextHolder.getUserId();
        orderParamVo.setUserId(userId);
        // 重复提交的判断 通过Redis + lua脚本
        // 若redis中存在 正常 -》删除  不存在-》重复提交
        // lua脚本保证原子性操作
        String orderNo = orderParamVo.getOrderNo();
        if (StringUtils.isEmpty(orderNo)) {
            throw new CustomException(ResultCodeEnum.ILLEGAL_REQUEST);
        }
        String script = "if(redis.call('get', KEY[1]) == ARGV[1] " +
                "then return redis.call('del', KEYS[1]) " +
                "else return 0 end";
        Boolean flag = (Boolean) redisTemplate.execute(new DefaultRedisScript(script, Boolean.class),
                Arrays.asList(RedisConst.ORDER_REPEAT + orderNo), orderNo);

        if (!flag) {
            throw new CustomException(ResultCodeEnum.REPEAT_SUBMIT);
        }



        return null;
    }

    @Override
    public OrderInfo getOrderInfoById(Long orderId) {
        return null;
    }
}

package com.molloi.ssyx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.model.user.Leader;
import com.molloi.ssyx.model.user.User;
import com.molloi.ssyx.model.user.UserDelivery;
import com.molloi.ssyx.user.mapper.LeaderMapper;
import com.molloi.ssyx.user.mapper.UserDeliveryMapper;
import com.molloi.ssyx.user.mapper.UserMapper;
import com.molloi.ssyx.user.service.UserService;
import com.molloi.ssyx.vo.user.LeaderAddressVo;
import com.molloi.ssyx.vo.user.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Molloi
 * @date 2023/6/23 15:16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserDeliveryMapper userDeliveryMapper;

    @Resource
    private LeaderMapper leaderMapper;

    @Override
    public User getUserByOpenId(String openId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId,openId));
    }

    @Override
    public LeaderAddressVo getLeaderAddressByUserId(Long userId) {
        UserDelivery userDelivery = userDeliveryMapper.selectOne(new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, userId).eq(UserDelivery::getIsDefault,1));
        if (userDelivery == null) {
            return null;
        }
        Leader leader = leaderMapper.selectById(userDelivery.getLeaderId());
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader,leaderAddressVo);
        leaderAddressVo.setUserId(userId);
        leaderAddressVo.setLeaderId(leader.getId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());
        return leaderAddressVo;
    }

    @Override
    public UserLoginVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setUserId(id);
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setOpenId(user.getOpenId());
        userLoginVo.setIsNew(user.getIsNew());

        UserDelivery userDelivery = userDeliveryMapper.selectOne(new LambdaQueryWrapper<UserDelivery>()
                .eq(UserDelivery::getUserId, id).eq(UserDelivery::getIsDefault,1));
        if (userDelivery != null) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setLeaderId(0L);
            userLoginVo.setWareId(0L);
        }

        return userLoginVo;
    }
}

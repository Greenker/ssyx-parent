package com.molloi.ssyx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.user.User;
import com.molloi.ssyx.vo.user.LeaderAddressVo;
import com.molloi.ssyx.vo.user.UserLoginVo;

/**
 * @author Molloi
 * @date 2023/6/23 15:15
 */
public interface UserService extends IService<User> {
    User getUserByOpenId(String openId);

    LeaderAddressVo getLeaderAddressByUserId(Long userId);

    UserLoginVo getUserLoginVo(Long id);
}

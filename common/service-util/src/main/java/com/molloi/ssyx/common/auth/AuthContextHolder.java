package com.molloi.ssyx.common.auth;

import com.molloi.ssyx.vo.user.UserLoginVo;

/**
 * ThreadLocal工具类
 * @author Molloi
 * @date 2023/6/23 17:31
 */
public class AuthContextHolder {
    //用户id
    private static ThreadLocal<Long> userId = new ThreadLocal<>();
    //仓库id
    private static ThreadLocal<Long> wareId = new ThreadLocal<>();
    //用户基本信息
    private static ThreadLocal<UserLoginVo> userLoginVo = new ThreadLocal<>();

    public static Long getUserId(){
        return userId.get();
    }

    public static void setUserId(Long _userId){
        userId.set(_userId);
    }

    public static Long getWareId(){
        return wareId.get();
    }

    public static void setWareId(Long _wareId){
        wareId.set(_wareId);
    }

    public static UserLoginVo getUserLoginVo() {
        return userLoginVo.get();
    }

    public static void setUserLoginVo(UserLoginVo _userLoginVo) {
        userLoginVo.set(_userLoginVo);
    }

}

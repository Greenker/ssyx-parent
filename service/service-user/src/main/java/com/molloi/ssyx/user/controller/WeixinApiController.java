package com.molloi.ssyx.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.molloi.ssyx.common.auth.AuthContextHolder;
import com.molloi.ssyx.common.constant.RedisConst;
import com.molloi.ssyx.common.exception.CustomException;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.common.result.ResultCodeEnum;
import com.molloi.ssyx.common.util.JwtHelper;
import com.molloi.ssyx.enums.UserType;
import com.molloi.ssyx.model.user.User;
import com.molloi.ssyx.user.service.UserService;
import com.molloi.ssyx.user.utils.ConstantPropertiesUtil;
import com.molloi.ssyx.user.utils.HttpClientUtils;
import com.molloi.ssyx.vo.user.LeaderAddressVo;
import com.molloi.ssyx.vo.user.UserLoginVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Molloi
 * @date 2023/6/23 14:33
 */
@RestController
@RequestMapping("/api/user/weixin")
public class WeixinApiController {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "微信登录获取openid(小程序)")
    @GetMapping("/wxLogin/{code}")
    public Result loginWx(@PathVariable String code) {
        // 1 得到微信返回的code临时值
        // 2 拿code + 小程序id + 小程序秘钥 请求微信服务接口  HttpClient工具
        if (StringUtils.isEmpty(code)) {
            throw new CustomException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        String result;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        // 3 得到返回的 session_key 和 openid
        JSONObject jsonObject = JSONObject.parseObject(result);
        String sessionKey = jsonObject.getString("session_key");
        String openId = jsonObject.getString("openid");

        // 4 根据open_id判断该用户是否已存在数据库中，如果没有则添加微信用户信息到数据库user表中
        User user = userService.getUserByOpenId(openId);
        if (user == null) {
            user = new User();
            user.setOpenId(openId);
            user.setNickName(openId);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }

        // 5 根据user_id获取团长id，在leader表中获取提货点信息和团长信息
        LeaderAddressVo leaderAddressVo = userService.getLeaderAddressByUserId(user.getId());

        // 6 使用jwt工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());

        // 7 获取当前登录用户信息，存放到redis中，设置有效时间
        UserLoginVo userLoginVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue().set(RedisConst.ADMIN_LOGIN_KEY_PREFIX+user.getId(), userLoginVo,
                RedisConst.USER_KEY_TIMEOUT, TimeUnit.MINUTES);

        // 8 封装数据到Map集合中返回
        Map<String, Object> map = new HashMap<>();
        map.put("user",user);
        map.put("token",token);
        map.put("leaderAddressVo",leaderAddressVo);
        return Result.ok(map);
    }

}

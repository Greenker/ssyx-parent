package com.molloi.ssyx.acl.controller;

import com.molloi.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Molloi
 * @date 2023/6/9 11:17
 */

@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
@CrossOrigin // 跨域
public class IndexController {

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login() {
        Map<String,String> map = new HashMap<>();
        map.put("token","token-admin");
        return Result.ok(map);
    }

    @ApiOperation("获取信息")
    @GetMapping("info")
    public Result info() {
        Map<String,String> map = new HashMap<>();
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    @ApiOperation("退出")
    @PostMapping("logout")
    public Result logout(){
        return Result.ok(null);
    }

}

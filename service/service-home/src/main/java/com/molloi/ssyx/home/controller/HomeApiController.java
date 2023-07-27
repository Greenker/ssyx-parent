package com.molloi.ssyx.home.controller;

import com.molloi.ssyx.common.auth.AuthContextHolder;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.home.service.HomeService;
import com.molloi.ssyx.vo.search.SkuEsQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Molloi
 * @date 2023/6/23 21:32
 */
@Api(tags = "首页接口")
@RestController
@RequestMapping("api/home")
public class HomeApiController {

    @Resource
    private HomeService homeService;

    @ApiOperation("首页数据显示")
    @GetMapping("index")
    public Result index() {
        Long userId = AuthContextHolder.getUserId();
        Map<String, Object> map = homeService.homeData(userId);
        return Result.ok(map);
    }

}

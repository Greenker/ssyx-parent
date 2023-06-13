package com.molloi.ssyx.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.sys.RegionWare;
import com.molloi.ssyx.sys.service.RegionWareService;
import com.molloi.ssyx.vo.sys.RegionWareQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author molloi
 * @since 2023-06-11
 */
@Api(tags = "开通区域接口")
@RestController
@RequestMapping("/admin/sys/regionWare")
@CrossOrigin
public class RegionWareController {

    @Resource
    private RegionWareService regionWareService;

    @ApiOperation("开通区域列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RegionWareQueryVo regionWareQueryVo) {
        Page<RegionWare> pageParam = new Page<>(page, limit);
        IPage<RegionWare> pageModel = regionWareService.selectPageRegionWare(pageParam, regionWareQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加开通区域")
    @PostMapping("save")
    public Result addRegionWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }

    @ApiOperation("删除开通区域")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("取消开通区域")
    @PutMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        regionWareService.updateStatus(id, status);
        return Result.ok(null);
    }

}


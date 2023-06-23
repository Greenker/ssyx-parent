package com.molloi.ssyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.acl.service.AdminService;
import com.molloi.ssyx.acl.service.RoleService;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.acl.Admin;
import com.molloi.ssyx.model.acl.Role;
import com.molloi.ssyx.vo.acl.AdminQueryVo;
import com.molloi.ssyx.vo.acl.RoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Molloi
 * @date 2023/6/9 13:34
 */

@Api(tags = "用户接口")
@RestController
@RequestMapping("/admin/acl/user")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    @ApiOperation("用户条件分页查询")
    @GetMapping("{current}/{limit}")
    public Result pageList(@PathVariable Long current, @PathVariable Long limit, AdminQueryVo adminQueryVo) {
        Page<Admin> pageParam = new Page<>(current, limit);
        IPage<Admin> pageModel = adminService.selectRolePage(pageParam, adminQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id查询用户")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.ok(adminService.getById(id));
    }

    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody Admin admin) {
        return adminService.save(admin) ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("根据id修改用户")
    @PutMapping("update")
    public Result update(@RequestBody Admin admin) {
        return adminService.updateById(admin) ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id) {
        return adminService.removeById(id) ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("根据id删除多个用户")
    @DeleteMapping("batchRemove")
    public Result removeRoles(@RequestBody List<Long> idList) {
        return adminService.removeByIds(idList) ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("获取用户已有角色列表和所有的角色列表")
    @GetMapping("toAssign/{adminId}")
    public Result getRoles(@PathVariable Long adminId) {
        Map<String, Object> map =  roleService.getRoleByAdminId(adminId);
        return Result.ok(map);
    }

    @ApiOperation("为用户分配角色")
    @PostMapping("doAssign")
    public Result assignRoles(@RequestParam Long adminId, @RequestParam Long[] roleId) {
        roleService.saveAdminRole(adminId, roleId);
        return Result.ok(null);
    }

}

package com.molloi.ssyx.acl.controller;

import com.molloi.ssyx.acl.service.PermissionService;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Molloi
 * @date 2023/6/10 19:00
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @ApiOperation("获取菜单列表")
    @GetMapping
    public Result getPermissionList() {
        return Result.ok(permissionService.queryAllPermission());
    }

    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result addPermission(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    @ApiOperation("根据id修改菜单")
    @PutMapping("update")
    public Result updatePermission(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    @ApiOperation("根据id删除菜单")
    @DeleteMapping("remove/{id}")
    public Result removePermission(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }

    @ApiOperation("获取角色已有菜单列表和所有的菜单列表")
    @GetMapping("toAssign/{roleId}")
    public Result getPermission(@PathVariable Long roleId) {
        List<Permission> permissionList =  permissionService.getPermissionByRoleId(roleId);
        return Result.ok(permissionList);
    }

    @ApiOperation("为角色分配菜单权限")
    @PostMapping("doAssign")
    public Result assignPermission(@RequestParam Long roleId, @RequestParam Long[] permissionId) {
        permissionService.saveRolePermissionId(roleId, permissionId);
        return Result.ok(null);
    }

}

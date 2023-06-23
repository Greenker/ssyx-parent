package com.molloi.ssyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.acl.service.PermissionService;
import com.molloi.ssyx.acl.service.RoleService;
import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.model.acl.Permission;
import com.molloi.ssyx.model.acl.Role;
import com.molloi.ssyx.vo.acl.RoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Molloi
 * @date 2023/6/9 12:36
 */

@Api(tags = "角色接口")
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation("角色条件分页查询")
    @GetMapping("{current}/{limit}")
    public Result pageList(@PathVariable Long current, @PathVariable Long limit, RoleQueryVo roleQueryVo) {
        Page<Role> pageParam = new Page<>(current, limit);
        IPage<Role> pageModel = roleService.selectRolePage(pageParam, roleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id获取角色")
    @GetMapping("get/{id}")
    public Result getRoleById(@PathVariable Long id) {
        return Result.ok(roleService.getById(id));
    }

    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody Role role) {
        return roleService.save(role) ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("根据id修改角色")
    @PutMapping("update")
    public Result updateById(@RequestBody Role role) {
        return roleService.updateById(role) ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("根据id删除角色")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id) {
        return roleService.removeById(id) ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("根据id删除多个角色")
    @DeleteMapping("batchRemove")
    public Result removeRoles(@RequestBody List<Long> idList) {
        return roleService.removeByIds(idList) ? Result.ok(null) : Result.fail(null);
    }

}

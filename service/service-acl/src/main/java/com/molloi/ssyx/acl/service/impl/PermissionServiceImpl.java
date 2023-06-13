package com.molloi.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.acl.mapper.PermissionMapper;
import com.molloi.ssyx.acl.mapper.RolePermissionMapper;
import com.molloi.ssyx.acl.service.PermissionService;
import com.molloi.ssyx.acl.utils.PermissionHelper;
import com.molloi.ssyx.model.acl.Permission;
import com.molloi.ssyx.model.acl.RolePermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Molloi
 * @date 2023/6/10 19:02
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> queryAllPermission() {

        List<Permission> allPermissionsList = baseMapper.selectList(
                new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        List<Permission> result = PermissionHelper.buildTree(allPermissionsList);

        return result;
    }

    @Override
    public void removeChildById(Long id) {

        List<Long> idList = new ArrayList<>();

        this.getAllPermissionId(id, idList);
        idList.add(id);

        baseMapper.deleteBatchIds(idList);

    }

    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {

        List<Permission> permissionList = baseMapper.selectList(null);

        List<RolePermission> roleList = rolePermissionMapper.selectList(
                new QueryWrapper<RolePermission>().eq("role_id", roleId));

        List<Long> idList = roleList.stream().map(item -> item.getPermissionId()).collect(Collectors.toList());

        for(Permission permission : permissionList) {
            permission.setSelect(idList.contains(permission.getId().longValue()));
        }

        return PermissionHelper.buildTree(permissionList);
    }

    @Override
    public void saveRolePermissionId(Long roleId, Long[] permissionId) {

        rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", roleId));

        for (Long id : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(id);
            rolePermissionMapper.insert(rolePermission);
        }

    }

    private void getAllPermissionId(Long id, List<Long> idList) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getPid, id);
        List<Permission> childList = baseMapper.selectList(queryWrapper);

        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.getAllPermissionId(item.getId(), idList);
        });

    }

}

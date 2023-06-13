package com.molloi.ssyx.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.acl.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author Molloi
 * @date 2023/6/10 19:02
 */
public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllPermission();

    void removeChildById(Long id);

    List<Permission> getPermissionByRoleId(Long roleId);

    void saveRolePermissionId(Long roleId, Long[] permissionId);
}

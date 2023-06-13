package com.molloi.ssyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.acl.Role;
import com.molloi.ssyx.vo.acl.RoleQueryVo;

import java.util.Map;

/**
 * @author Molloi
 * @date 2023/6/9 12:37
 */
public interface RoleService extends IService<Role> {
    IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

    Map<String, Object> getRoleByAdminId(Long adminId);

    void saveAdminRole(Long adminId, Long[] roleId);
}

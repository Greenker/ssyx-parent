package com.molloi.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.acl.mapper.AdminRoleMapper;
import com.molloi.ssyx.acl.mapper.RoleMapper;
import com.molloi.ssyx.acl.service.RoleService;
import com.molloi.ssyx.model.acl.AdminRole;
import com.molloi.ssyx.model.acl.Role;
import com.molloi.ssyx.vo.acl.RoleQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Molloi
 * @date 2023/6/9 12:38
 */

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private AdminRoleMapper adminRoleMapper;

    @Override
    public IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        String roleName = roleQueryVo.getRoleName();
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(roleName)) {
            queryWrapper.like(Role::getRoleName, roleName);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        Map<String, Object> map = new HashMap<>();

        List<Role> allRolesList = baseMapper.selectList(null);
        map.put("allRolesList", allRolesList);

        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        List<AdminRole> role = adminRoleMapper.selectList(wrapper);
        if (!role.isEmpty()) {
            // stream流
            List<Long> idList = role.stream().map(item -> item.getRoleId()).collect(Collectors.toList());

        /*
            原始方法
            List<Long> idList = new ArrayList<>();
            for (AdminRole role: roleId) {
                idList.add(role.getRoleId());
            }
        */

            List<Role> assignRoles = baseMapper.selectBatchIds(idList);
            map.put("assignRoles", assignRoles);
        } else {
            map.put("assignRoles", null);
        }

        return map;
    }

    @Override
    public void saveAdminRole(Long adminId, Long[] roleId) {
        LambdaQueryWrapper<AdminRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminRole::getAdminId, adminId);
        adminRoleMapper.delete(queryWrapper);

        for (Long id : roleId) {
            adminRoleMapper.insert(new AdminRole(id, adminId));
        }
    }

}

package com.molloi.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.acl.mapper.AdminMapper;
import com.molloi.ssyx.acl.service.AdminService;
import com.molloi.ssyx.common.util.MD5;
import com.molloi.ssyx.model.acl.Admin;
import com.molloi.ssyx.vo.acl.AdminQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Molloi
 * @date 2023/6/9 13:37
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public IPage<Admin> selectRolePage(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String username = adminQueryVo.getUsername();
        String name = adminQueryVo.getName();
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.like(Admin::getUsername, username);
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq(Admin::getName, name);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean save(Admin admin) {
        String password = MD5.encrypt(admin.getPassword());
        admin.setPassword(password);
        return baseMapper.insert(admin) == 0;
    }

}

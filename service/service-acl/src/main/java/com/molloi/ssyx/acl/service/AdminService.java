package com.molloi.ssyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.molloi.ssyx.model.acl.Admin;
import com.molloi.ssyx.vo.acl.AdminQueryVo;

/**
 * @author Molloi
 * @date 2023/6/9 13:36
 */
public interface AdminService extends IService<Admin> {
    IPage<Admin> selectRolePage(Page<Admin> pageParam, AdminQueryVo adminQueryVo);

    boolean save(Admin admin);
}

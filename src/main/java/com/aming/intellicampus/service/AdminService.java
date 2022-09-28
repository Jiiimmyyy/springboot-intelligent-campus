package com.aming.intellicampus.service;

import com.aming.intellicampus.pojo.Admin;
import com.aming.intellicampus.pojo.LoginForm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author AMing
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-09-26 21:10:22
*/
public interface AdminService extends IService<Admin> {

    Admin login(LoginForm loginForm);

    Page getAdminsByOpr(Page<Admin> adminPage, Admin admin);

    boolean updatePwd(Long userId, String oldPassword, String newPassword);
}

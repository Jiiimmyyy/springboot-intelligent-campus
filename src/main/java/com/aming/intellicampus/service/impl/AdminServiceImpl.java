package com.aming.intellicampus.service.impl;

import com.aming.intellicampus.mapper.AdminMapper;
import com.aming.intellicampus.pojo.LoginForm;
import com.aming.intellicampus.pojo.Student;
import com.aming.intellicampus.service.AdminService;
import com.aming.intellicampus.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Admin;
import com.aming.intellicampus.service.AdminService;
import com.aming.intellicampus.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author AMing
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-09-26 21:10:22
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(LoginForm loginForm) {
        //创建QueryWrapper对象
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        //拼接查询条件
        queryWrapper.eq("name",loginForm.getUsername());
        // 转换成密文进行查询
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public Page getAdminsByOpr(Page<Admin> adminPage, Admin admin) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        if (admin!=null){
            String name = admin.getName();
            if(!StringUtils.isEmpty(name)){
                adminQueryWrapper.eq("name",name);
            }

        }
        adminQueryWrapper.orderByDesc("id");
        return baseMapper.selectPage(adminPage,adminQueryWrapper);
    }

    @Override
    public boolean updatePwd(Long userId, String oldPassword, String newPassword) {
        String encryptedOldPassword = MD5.encrypt(oldPassword);
        String encryptedNewPassword = MD5.encrypt(newPassword);
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("id",userId).eq("password",encryptedOldPassword);
        Admin admin = baseMapper.selectOne(adminQueryWrapper);
        if(null==admin){
            return false;
        }else {
            admin.setPassword(encryptedNewPassword);
            this.saveOrUpdate(admin);
            return true;
        }
    }
}





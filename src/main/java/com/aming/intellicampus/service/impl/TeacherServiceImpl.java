package com.aming.intellicampus.service.impl;

import com.aming.intellicampus.pojo.LoginForm;
import com.aming.intellicampus.pojo.Student;
import com.aming.intellicampus.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Teacher;
import com.aming.intellicampus.service.TeacherService;
import com.aming.intellicampus.mapper.TeacherMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author AMing
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-09-26 21:19:54
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Page getTeacherByOpr(Page<Teacher> teacherPage, Teacher teacher) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        if (teacher!=null){
            String clazzName = teacher.getClazzName();
            if(!StringUtils.isEmpty(clazzName)){
                teacherQueryWrapper.eq("clazz_name",clazzName);
            }
            String name = teacher.getName();
            if (!StringUtils.isEmpty(name)){
                teacherQueryWrapper.like("name",name);
            }
        }
        teacherQueryWrapper.orderByDesc("id");
        return baseMapper.selectPage(teacherPage,teacherQueryWrapper);
    }

    @Override
    public boolean updatePwd(Long userId, String oldPassword, String newPassword) {
        String encryptedOldPassword = MD5.encrypt(oldPassword);
        String encryptedNewPassword = MD5.encrypt(newPassword);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("id",userId).eq("password",encryptedOldPassword);
        Teacher teacher = baseMapper.selectOne(teacherQueryWrapper);
        if(null==teacher){
            return false;
        }else {
            teacher.setPassword(encryptedNewPassword);
            this.saveOrUpdate(teacher);
            return true;
        }
    }
}





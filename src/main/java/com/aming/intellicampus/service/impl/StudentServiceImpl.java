package com.aming.intellicampus.service.impl;

import com.aming.intellicampus.pojo.Admin;
import com.aming.intellicampus.pojo.LoginForm;
import com.aming.intellicampus.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Student;
import com.aming.intellicampus.service.StudentService;
import com.aming.intellicampus.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author AMing
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-09-26 21:28:10
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Page getStudentsByOpr(Page<Student> studentPage, Student student) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        if (student!=null){
            String clazzName = student.getClazzName();
            if(!StringUtils.isEmpty(clazzName)){
                studentQueryWrapper.eq("clazz_name",clazzName);
            }
            String name = student.getName();
            if (!StringUtils.isEmpty(name)){
                studentQueryWrapper.like("name",name);
            }
        }
        studentQueryWrapper.orderByDesc("id");
        return baseMapper.selectPage(studentPage,studentQueryWrapper);
    }

    @Override
    public boolean updatePwd(Long userId, String oldPassword, String newPassword) {
        String encryptedOldPassword = MD5.encrypt(oldPassword);
        String encryptedNewPassword = MD5.encrypt(newPassword);
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("id",userId).eq("password",encryptedOldPassword);
        Student student = baseMapper.selectOne(studentQueryWrapper);
        if(null==student){
            return false;
        }else {
            student.setPassword(encryptedNewPassword);
            this.saveOrUpdate(student);
            return true;
        }
    }
}





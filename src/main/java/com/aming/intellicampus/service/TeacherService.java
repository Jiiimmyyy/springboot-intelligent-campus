package com.aming.intellicampus.service;

import com.aming.intellicampus.pojo.LoginForm;
import com.aming.intellicampus.pojo.Teacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author AMing
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-09-26 21:19:54
*/
public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);

    Page getTeacherByOpr(Page<Teacher> teacherPage, Teacher teacher);

    boolean updatePwd(Long userId, String oldPassword, String newPassword);
}

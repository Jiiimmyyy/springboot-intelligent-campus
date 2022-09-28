package com.aming.intellicampus.service;

import com.aming.intellicampus.pojo.LoginForm;
import com.aming.intellicampus.pojo.Student;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author AMing
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-09-26 21:28:10
*/
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    Page getStudentsByOpr(Page<Student> teacherPage, Student student);

    boolean updatePwd(Long userId, String oldPassword, String newPassword);
}

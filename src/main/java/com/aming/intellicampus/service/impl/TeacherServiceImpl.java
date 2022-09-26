package com.aming.intellicampus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Teacher;
import com.aming.intellicampus.service.TeacherService;
import com.aming.intellicampus.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

/**
* @author AMing
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-09-26 21:19:54
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

}





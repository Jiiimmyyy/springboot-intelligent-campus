package com.aming.intellicampus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Student;
import com.aming.intellicampus.service.StudentService;
import com.aming.intellicampus.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author AMing
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-09-26 21:28:10
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}





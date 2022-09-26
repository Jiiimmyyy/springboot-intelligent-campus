package com.aming.intellicampus.mapper;

import com.aming.intellicampus.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author AMing
* @description 针对表【tb_student】的数据库操作Mapper
* @createDate 2022-09-26 21:28:10
* @Entity com.aming.intellicampus.pojo.Student
*/
@Repository
public interface StudentMapper extends BaseMapper<Student> {

}





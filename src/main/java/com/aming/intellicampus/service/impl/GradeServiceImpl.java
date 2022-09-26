package com.aming.intellicampus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Grade;
import com.aming.intellicampus.service.GradeService;
import com.aming.intellicampus.mapper.GradeMapper;
import org.springframework.stereotype.Service;

/**
* @author AMing
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-09-26 21:27:58
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

}





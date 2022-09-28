package com.aming.intellicampus.service;

import com.aming.intellicampus.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author AMing
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-09-26 21:27:58
*/
public interface GradeService extends IService<Grade> {

    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);

    List<Grade> getGrades();
}

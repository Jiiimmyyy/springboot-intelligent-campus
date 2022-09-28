package com.aming.intellicampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Grade;
import com.aming.intellicampus.service.GradeService;
import com.aming.intellicampus.mapper.GradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
* @author AMing
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-09-26 21:27:58
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        //调用内置方法，自动生成SQL语句
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name",gradeName);
        }
        //设置排序规则
        queryWrapper.orderByDesc("id");
        Page<Grade> gradePage = baseMapper.selectPage(page, queryWrapper);
        return gradePage;
    }

    @Override
    public List<Grade> getGrades() {
        QueryWrapper<Grade> gradeQueryWrapper = new QueryWrapper<>();
        gradeQueryWrapper.select("name","introducation");
        return baseMapper.selectList(gradeQueryWrapper);
    }
}





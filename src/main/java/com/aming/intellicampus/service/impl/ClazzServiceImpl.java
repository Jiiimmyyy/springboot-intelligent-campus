package com.aming.intellicampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Clazz;
import com.aming.intellicampus.service.ClazzService;
import com.aming.intellicampus.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author AMing
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-09-26 21:23:28
*/
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{
    @Override
    public Page getPageByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> clazzQueryWrapper = new QueryWrapper<>();
        if (null!=clazz){

            //年级名称条件
            String gradeName = clazz.getGradeName();
            if(!StringUtils.isEmpty(gradeName)){
                clazzQueryWrapper.eq("grade_name",gradeName);
            }
            //班级名称条件
            String clazzName = clazz.getName();
            if(!StringUtils.isEmpty(clazzName)){
                clazzQueryWrapper.like("name",clazzName);
            }
            clazzQueryWrapper.orderByDesc("id");
            clazzQueryWrapper.orderByAsc("name");
        }
        return baseMapper.selectPage(page,clazzQueryWrapper);
    }

    @Override
    public List<Clazz> getClazzs() {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        return baseMapper.selectList(queryWrapper);
    }
}





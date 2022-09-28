package com.aming.intellicampus.service;

import com.aming.intellicampus.pojo.Clazz;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author AMing
* @description 针对表【tb_clazz】的数据库操作Service
* @createDate 2022-09-26 21:23:28
*/
public interface ClazzService extends IService<Clazz> {

    Page getPageByOpr(Page<Clazz> page, Clazz clazz);

    List<Clazz> getClazzs();
}

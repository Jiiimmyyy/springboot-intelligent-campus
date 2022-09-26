package com.aming.intellicampus.service.impl;

import com.aming.intellicampus.mapper.AdminMapper;
import com.aming.intellicampus.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aming.intellicampus.pojo.Admin;
import com.aming.intellicampus.service.AdminService;
import com.aming.intellicampus.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author AMing
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-09-26 21:10:22
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {

}





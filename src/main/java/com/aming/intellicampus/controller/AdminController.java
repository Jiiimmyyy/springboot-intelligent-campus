package com.aming.intellicampus.controller;

import com.aming.intellicampus.pojo.Admin;
import com.aming.intellicampus.pojo.Teacher;
import com.aming.intellicampus.service.AdminService;
import com.aming.intellicampus.util.MD5;
import com.aming.intellicampus.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员信息控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
//
@Autowired
AdminService adminService;

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getStudents(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              Admin admin){
        //新建空的分页Page对象，用来存储数据
        Page<Admin> adminPage = new Page<>(pageNo,pageSize);
        //通过service层，查询数据库，将数据封装到Page对象中
        Page pageOpr  = adminService.getAdminsByOpr(adminPage,admin);
        return Result.ok(pageOpr);
    }
    //saveOrUpdateAdmin
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        //新增用户时，才对密码字段进行操作
        Integer id =admin.getId();
        if (id == null || 0==id){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }

        adminService.saveOrUpdate(admin);
        return Result.ok();
    }


    @ApiOperation("通过管理员的id列表，批量删除管理员记录")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@ApiParam("要删除管理员记录的Id的JSON集合") @RequestBody List<Integer> list){
        boolean isOK = adminService.removeByIds(list);
        if (isOK){
            return Result.ok();
        }else {
            return Result.fail().message("删除管理员记录失败");
        }
    }
}

package com.aming.intellicampus.controller;

import com.aming.intellicampus.pojo.Student;
import com.aming.intellicampus.pojo.Teacher;
import com.aming.intellicampus.service.TeacherService;
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

@Api(tags = "老师信息控制器")
@RestController
@RequestMapping("/sms/teacherController")
// sms:Smart Campus System

public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              Teacher teacher){
        //新建空的分页Page对象，用来存储数据
        Page<Teacher> teacherPage = new Page<>();
        //通过service层，查询数据库，将数据封装到Page对象中
        Page pageOpr  = teacherService.getTeacherByOpr(teacherPage,teacher);
        return Result.ok(pageOpr);
    }
    //saveOrUpdateTeacher
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        //表单里不带密码时，不对原密码做任何操作，防止2次加密
        if (!Strings.isEmpty(teacher.getPassword())) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("通过老师的id列表，批量删除老师记录")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@ApiParam("要删除老师记录的Id的JSON集合") @RequestBody List<Integer> list){
        boolean isOK = teacherService.removeByIds(list);
        if (isOK){
            return Result.ok();
        }else {
            return Result.fail().message("删除老师记录失败");
        }
    }
}

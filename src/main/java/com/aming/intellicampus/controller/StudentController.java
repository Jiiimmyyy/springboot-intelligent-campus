package com.aming.intellicampus.controller;

import com.aming.intellicampus.pojo.Student;
import com.aming.intellicampus.pojo.Teacher;
import com.aming.intellicampus.service.StudentService;
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

@Api(tags = "学生信息控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudents(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              Student student){
        //新建空的分页Page对象，用来存储数据
        Page<Student> studentPage = new Page<>();
        //通过service层，查询数据库，将数据封装到Page对象中
        Page pageOpr  = studentService.getStudentsByOpr(studentPage,student);
        return Result.ok(pageOpr);
    }

    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        //表单里不带密码时，不对原密码做任何操作，防止2次加密
        Integer id = student.getId();
        //说明是个新增操作，新增操作才做明文转密文的操作
        if (null==id || 0==id){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }

        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @ApiOperation("通过学生的id列表，批量删除学生记录")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@ApiParam("要删除student记录的Id的JSON集合") @RequestBody List<Integer> list){
        boolean isOK = studentService.removeByIds(list);
        if (isOK){
            return Result.ok();
        }else {
            return Result.fail().message("删除学生记录失败");
        }
    }
}

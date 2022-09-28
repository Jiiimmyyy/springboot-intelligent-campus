package com.aming.intellicampus.controller;

import com.aming.intellicampus.pojo.Grade;
import com.aming.intellicampus.service.GradeService;
import com.aming.intellicampus.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "年级信息控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    //data包含ipage对象和查询db的结果,直接返回page就行
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradesByOpr(@ApiParam("分页查询页码数") @PathVariable("pageNo") Integer pageNo,
                                 @ApiParam("分页查询页大小") @PathVariable("pageSize")Integer pageSize,
                                 @ApiParam("分页查询模糊匹配年级名") String gradeName){
        //新建一个条件构造器
        QueryWrapper<Grade> gradeQueryWrapper = new QueryWrapper<Grade>();
        if (gradeName!=null){
            gradeQueryWrapper.like("name",gradeName);
        }
        //分页 带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //通过服务层查询数据
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page, gradeName);
        //封装Result对象并返回
        return Result.ok(pageRs);
    }

    /*
    *
  {
  "name": "test",
  "manager": "test",
  "email": "test@test.com",
  "telephone": "13666666666",
  "introducation": "test6666666666"
   }
    * */
    @ApiOperation("更新或修改年级信息到数据库，有id属性是修改，没有是添加")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade( @ApiParam("JSON的grade对象转换后台数据模型 ") @RequestBody Grade grade){
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("根据ID删除年级的记录")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除grade记录的Id的JSON集合") @RequestBody List<Integer> list){
        boolean isOK = gradeService.removeByIds(list);
        if (isOK){
            return Result.ok();
        }else {
            return Result.fail().message("删除失败");
        }

    }

    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }

}

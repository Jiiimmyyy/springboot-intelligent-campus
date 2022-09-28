package com.aming.intellicampus.controller;

import com.aming.intellicampus.pojo.Clazz;
import com.aming.intellicampus.pojo.Grade;
import com.aming.intellicampus.service.ClazzService;
import com.aming.intellicampus.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级信息控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@ApiParam("分页查询页码数") @PathVariable("pageNo") Integer pageNo,
                                 @ApiParam("分页查询页大小") @PathVariable("pageSize")Integer pageSize,
                                 @ApiParam("分页查询模糊匹配班级名") Clazz clazz){
        //新建一个空的分页对象，用来存储分页后的数据
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        //通过服务层查询数据
        //把空的分页传进去，把记录封装进去后，传出来
        Page pagesByOpr = clazzService.getPageByOpr(page, clazz);
        return Result.ok(pagesByOpr);
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok().message("班级更新成功");
    }

    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> idList){
        clazzService.removeByIds(idList);
        return Result.ok().message("班级删除成功");
    }

    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzs = clazzService.getClazzs();
        return Result.ok(clazzs);
    }
}

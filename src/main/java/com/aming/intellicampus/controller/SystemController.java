package com.aming.intellicampus.controller;

import com.aming.intellicampus.pojo.Admin;
import com.aming.intellicampus.pojo.LoginForm;
import com.aming.intellicampus.pojo.Student;
import com.aming.intellicampus.pojo.Teacher;
import com.aming.intellicampus.service.AdminService;
import com.aming.intellicampus.service.StudentService;
import com.aming.intellicampus.service.TeacherService;
import com.aming.intellicampus.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
使用注解@RestController，请求返回会自动转化为json格式的，
我们也可以返回字符串，自己转化为json格式。使用gson或者fastjson.
注解RestController => @controller + @ResponseBody
* */
@Api(tags = "系统信息控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;


    //从配置文件的指定属性获取值
    @Value("${myFileUpload.HeaderImgDirPath}")
    private String HeaderImgDirPath;//头像在服务器里的存储路径（暂时是本机的绝对路径）


    //从配置文件的指定属性获取值
    @Value("${myFileUpload.HeaderImgPortraitPath}")
    private String HeaderImgPortraitPath;//给前端返回的头像路径

    @ApiOperation("提交loginForm表单登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //1 验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String formVerifiCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || null==formVerifiCode){
            return Result.fail().message("验证码失效，请刷新后重试");
        }

        if (!sessionVerifiCode.equalsIgnoreCase(formVerifiCode)){
            return Result.fail().message("验证码有误，请刷新后重试");
        }
        // 从session域中移除验证码
        session.removeAttribute("verifiCode");

        //2 验证用户类型

        //准备1个map，存放响应的数据
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    Admin admin =  adminService.login(loginForm);
                    if (null!=admin){
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                         map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    //获取上面else抛出的异常的信息
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student =  studentService.login(loginForm);
                    if (null!=student){
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    //获取上面else抛出的异常的信息
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher =  teacherService.login(loginForm);
                    if (null!=teacher){
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    //获取上面else抛出的异常的信息
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("输入的用户类型有误");
    }

    @ApiOperation("通过token获取用户信息")
    @GetMapping("/getInfo")
    public Result getUserInfoByToken(HttpServletRequest request, @RequestHeader("token")String token){
        //1.1 获取用户中请求的token
        //1.2 检查token 是否过期 20H
        boolean isExpiration = JwtHelper.isExpiration(token);
        try {
            if (isExpiration==true){
                throw new RuntimeException("自动登录过期，请重新登录");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Result.fail().message(e.getMessage());
        }
        //2 解析token,获取用户id和用户类型
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        //3 准备一个Map集合用于存响应的数据
        Map<String, Object> map = new HashMap<>();
        switch (userType){
            case 1:
                Admin dbAdmin = adminService.getById(userId);
                map.put("user",dbAdmin);
                map.put("userType",1);
                break;
            case 2:
                Student dbStudent = studentService.getById(userId);
                map.put("user",dbStudent);
                map.put("userType",2);
                break;
            case 3:
                Teacher dbTeacher = teacherService.getById(userId);
                map.put("user",dbTeacher);
                map.put("userType",3);
                break;
        }

        return Result.ok(map);
    }

    @ApiOperation("获取验证码图片和4位验证码")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //1 获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //2 获取图片里的4位验证码
        String verifiCode =  new String(CreateVerifiCodeImage.getVerifiCode());
        //3 将验证码文本放入session域，为下一次验证做准备
        request.getSession().setAttribute("verifiCode",verifiCode);
        //4 将验证码图片响应给浏览器
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(verifiCodeImage,"JPEG",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("头像上传的统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
          @ApiParam("上传的图片") @RequestPart("multipartFile") MultipartFile headerImg){
        //1 保存图片
        String originalFilename = headerImg.getOriginalFilename();
//        String dirPath = "D:\\Environment\\JavaCode\\MyIntelligentCampusAdminSystem\\target\\classes\\public\\upload\\";
//        String portraitPath="/upload/";
        Map<String, Object> uploadResult = UploadFile.getUploadResult(headerImg, HeaderImgDirPath, HeaderImgPortraitPath);
        //2 响应图片的路径
        boolean isSuccess = (boolean)uploadResult.get("success");
        if (isSuccess){
            return Result.ok((String)uploadResult.get("portrait_path"));
        }else {
            return Result.fail().message((String) uploadResult.get("msg"));
        }

    }

    @PostMapping("/updatePwd/{oldPassword}/{newPassword}")
    public Result updatePassword(@RequestHeader("token")String token,
                                 @PathVariable String oldPassword,
                                 @PathVariable String newPassword){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.fail().message("自动登录到期，请重新登录");
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        boolean pwdRight = true;
        switch (userType){
            case 1:
                pwdRight = adminService.updatePwd(userId,oldPassword,newPassword);
                break;
            case 2:
                pwdRight = studentService.updatePwd(userId,oldPassword,newPassword);
                break;
            case 3:
                pwdRight = teacherService.updatePwd(userId,oldPassword,newPassword);
                break;
        }
        if (pwdRight){
            return Result.ok();
        }else {
            return Result.fail().message("你输入的旧密码有误");
        }

    }

}

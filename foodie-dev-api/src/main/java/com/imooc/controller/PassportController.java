package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录",tags = {"用户注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {


    @Autowired
    private UserService userService;
    @ApiOperation(value = "用户名是否存在",notes = "判断用户名是否存在",httpMethod = "GET")
    @RequestMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username){
        // 1 判断用户名不能为空
        if(StringUtils.isEmpty(username)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        // 2 判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户已存在");
        }
        return IMOOCJSONResult.ok();
    }
    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/register")
    public IMOOCJSONResult register(@RequestBody UserBo userBo){
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPwd = userBo.getConfirmPassword();

        // 判断用户名 和密码必须不为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        // 判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        // 密码长度不能少于位
        if(password.length() < 6){
            return IMOOCJSONResult.errorMsg("密码长度不能少于六位");
        }

        // 判断两次密码是否一致
        if( !password.equals(confirmPwd)){
            return IMOOCJSONResult.errorMsg("两次密码不一致");
        }
        // 实现注册
        userService.createUser(userBo);

        return IMOOCJSONResult.ok();

    }
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "post")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBo userBo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }

        // 实现登录

        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult == null){
            return IMOOCJSONResult.errorMsg("用户名或者密码不正确");
        }
        userResult = setPropertyNull(userResult);


        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);
        return IMOOCJSONResult.ok(userResult);
    }

    private Users setPropertyNull(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }


}

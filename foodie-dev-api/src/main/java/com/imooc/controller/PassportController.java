package com.imooc.controller;

import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassportController {


    @Autowired
    private UserService userService;
    @RequestMapping("/passport/usernameIsExist")
    public int usernameIsExist(@RequestParam String username){
        // 1 判断用户名不能为空
        if(StringUtils.isEmpty(username)){
            return 500;
        }
        // 2 判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return 500;
        }
        return 200;
    }
}

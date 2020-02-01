package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;

public interface UserService {

    public boolean queryUsernameIsExist(String username);

    public Users createUser(UserBo userBo);

    public Users queryUserForLogin(String username,String password);
}

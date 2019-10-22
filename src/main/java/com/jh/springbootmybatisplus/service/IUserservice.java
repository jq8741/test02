package com.jh.springbootmybatisplus.service;

import com.jh.springbootmybatisplus.entity.User;
import com.jh.springbootmybatisplus.entity.login.UserLogin;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserservice {

    public void insertUser(User user);

    public User getUser(String id);

    public void deleteUser(String id);

    public void update(User user);

    List<User>  selectUser();

    User userlogin(UserLogin logins);

    String getUserPassWord(String userName);

}

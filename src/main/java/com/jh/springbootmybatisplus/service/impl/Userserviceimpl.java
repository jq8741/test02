package com.jh.springbootmybatisplus.service.impl;

import com.jh.springbootmybatisplus.dao.UserDao;
import com.jh.springbootmybatisplus.entity.User;
import com.jh.springbootmybatisplus.entity.login.UserLogin;
import com.jh.springbootmybatisplus.mapper.UserMapper;
import com.jh.springbootmybatisplus.service.IUserservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Userserviceimpl implements IUserservice {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDao userDao;

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }
    @Override
    public User getUser(String id) {
        return userMapper.selectById(id);
    }
    @Override
    public void deleteUser(String id) {
        userMapper.deleteById(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public List<User> selectUser() {
        return userMapper.selectList(null);
    }

    @Override
    public User userlogin(UserLogin logins) {
        return userDao.userlogin(logins);
    }

    @Override
    public String getUserPassWord(String userName) {
        return userDao.getUserPassWord(userName);
    }

}

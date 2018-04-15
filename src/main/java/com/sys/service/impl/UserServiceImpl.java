package com.sys.service.impl;

import com.sys.mapper.UserMapper;
import com.sys.beans.User;
import com.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * service实现类
 *
 * @auther sunchao
 * @create 2018/4/2
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name){
        return userMapper.findByName(name);
    }

    @Override
    public int insertUser(Map<String,Object> param){
        return userMapper.insertUser(param);
    }

    @Override
    public  List<Map<String,Object>> findAllUser(){
        return userMapper.findAllUsers();
    }

}

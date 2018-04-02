package com.sys.service;

import com.sys.model.User;

import java.util.List;
import java.util.Map;

/**
 * service接口类
 *
 * @auther sunchao
 * @create 2018/4/2
 */
public interface UserService {

    User getUserByName(String name);

    int insertUser(Map<String,Object> param);

    List<Map<String,Object>> findAllUser();
}

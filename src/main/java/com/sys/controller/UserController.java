package com.sys.controller;

import com.sys.service.UserService;
import com.sys.utils.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户控制类相关
 *
 * @auther sunchao
 * @create 2018/3/30
 */


@Controller
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public int addUser(HttpServletRequest request){
        Map<String,Object> param = ParamUtil.paramMap(request);
        if(param.get("name")==null || "".equals(param.get("name"))){
            System.out.println("缺少用户名参数");
            return -1;
        }
        if(param.get("age")==null || "".equals(param.get("age"))){
            System.out.println("缺少年龄参数");
            return -1;
        }
        if(param.get("tel")==null || "".equals(param.get("tel"))){
            System.out.println("缺少手机号参数");
            return -1;
        }

        return userService.insertUser(param);
    }

    @ResponseBody
    @RequestMapping(value = "/findAllUser")
    public List<Map<String,Object>> findAllUser(){

        return userService.findAllUser();
    }
}

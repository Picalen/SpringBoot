package com.personal.mapper;

import com.personal.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 测试用户dao
 *
 * @auther sunchao
 * @create 2018/12/7
 */
@Repository
@Mapper
public interface TestUserMapper {

    int insertUser(User u);

    int updateUser(User u);

    int deleteUserById(User u);

    User findById(User u);

}

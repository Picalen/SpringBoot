package com.sys.mapper;

import com.sys.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户控制类相关
 *
 * @auther sunchao
 * @create 2018/3/30
 */
@Repository
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

    int insertUser(Map<String,Object> param);

    List<Map<String,Object>> findAllUsers();
}

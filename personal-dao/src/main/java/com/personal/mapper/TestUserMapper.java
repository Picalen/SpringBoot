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

    @Insert("insert test_user(id,name,age,detail) values(#{id},#{userName})")
    void insert(User u);

    @Update("update test_user set name = #{name},age = #{age},detail = #{detail} where id=#{id} ")
    void update(User u);

    @Delete("delete from test_user where id=#{id} ")
    void delete(@Param("id")String id);

    @Select("select id,name,age,detail from test_user where id=#{id} ")
    User find(@Param("id")String id);

    /**
     * 注：方法名和要UserMapper.xml中的id一致
     *
     */
    @Select("select id,name,age,detail from test_user  where name like '%name%' ")
    List<User> query(@Param("name") String userName);

    @Delete("delete from test_user")
    void deleteAll();

}

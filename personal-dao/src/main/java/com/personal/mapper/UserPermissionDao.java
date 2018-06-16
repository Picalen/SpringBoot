package com.personal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 获取用户显示模块
 * @author dmc
 */
@Repository
@Mapper
public interface UserPermissionDao {
    /**
     * 通过userid获取用户模块
     * @param userId
     * @return
     */
    List<Map<String,Object>> selectUserPermessionByUserId(String userId);
}

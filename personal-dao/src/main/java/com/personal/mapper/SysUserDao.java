package com.personal.mapper;

import com.personal.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface SysUserDao {
    /**
     * 自动生成
     * @param uid
     * @return
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * 自动生成
     * @param record
     * @return
     */
    int insert(SysUser record);

    /**
     * 自动生成
     * @param record
     * @return
     */
    int insertSelective(SysUser record);

    /**
     * 自动生成
     * @param uid
     * @return
     */
    SysUser selectByPrimaryKey(Long uid);

    /**
     * 自动生成
     * @param record
     * @return
     */
    SysUser selectSelective(SysUser record);

    /**
     * shiro登录
     *
     * @param username 用户名
     * @return
     */
    SysUser loginByUsername(String username);

    /**
     * 自动生成
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 自动生成
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysUser record);

    /**
     * 获取用户信息
     * @param map
     * @return
     */
    Map<String,Object> getUserInfo(Map<String,Object> map);

    /**
     * 修改密码
     * @param map
     * @return
     */
    void updatePwd(Map<String, Object> map);

    /**
     * 登录
     * @param map
     * @return
     */
    SysUser loginByUsernameAndPass(Map<String, String> map);


    /**
     * 修改用户信息
     * @param map
     * @return
     */
    void updateUserInfo(Map<String,Object> map);

    /**
     * 查询用户基本信息
     * @param map
     * @return
     */
    Map<String,Object> findUserInfoByUserName(Map<String,Object> map);
}
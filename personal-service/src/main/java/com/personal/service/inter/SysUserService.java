package com.personal.service.inter;

import com.personal.model.SysUser;
import net.sf.json.JSONObject;

import java.util.Map;

public interface SysUserService {
    /**
     * shiro登录
     *
     * @param username 用户名
     * @return
     */
    SysUser loginByUsername(String username);

    /**
     * 修改密码
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> updatePwd(Map<String,Object> map) throws Exception;


    SysUser loginByUserNameAndPass(Map<String,String> map);

    /**
     * 获取当前用户
     * @param sysUser 用户名
     * @return
     */
    Map<String ,Object> getCurrentUser(SysUser sysUser);

    /**
     * 更新用户信息
     * @param sysUser 用户信息
     * @return
     */
    Map<String ,Object> updateUserInfo(Map<String,Object> map);

    /**
     * 查询用户基本信息
     * @return
     * @throws Exception
     */
    JSONObject findUserInfoByUserName() throws Exception;
}

package com.personal.service.inter;


import net.sf.json.JSONObject;

import java.util.Map;
/**
 * @author ycv
 * @description 子账户管理
 */
public interface ChildrenAccountService {

    /**
     * 子账户信息查询
     * @return
     * @throws Exception
     */
    JSONObject findChildAccountList() throws Exception;

    /**
     * 角色信息分配
     * @param map
     * @return
     * @throws Exception
     */
    JSONObject setUserRole(Map<String,Object> map) throws Exception;

    /**
     * 添加
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> addChildAccount(Map<String,Object> map) throws Exception;

    /**
     * 编辑
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> updateChildAccount(Map<String,Object> map) throws Exception;

    /**
     * 删除
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> deleteChildAccount(Map<String,Object> map) throws Exception;

    /**
     * 配置用户角色
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> setUserRoleInfo(Map<String,Object> map) throws Exception;

    /**
     * 变更密码
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> updatePassword(Map<String,Object> map) throws Exception;
}

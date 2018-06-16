package com.personal.service.inter;


import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author ycv
 * @date 2017/12/19
 */
public interface RolePermissionService {
    /**
     * 查询角色与权限
     *
     * @return
     * @throws Exception
     */
    JSONObject getUserRolesDataList() throws Exception;

    /**
     * 根据当前用户查询角色与权限
     *
     * @return
     */
    JSONObject findRolePerByCurrentUser();

    /**
     * 添加
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> addRoleInfo(Map<String, Object> map) throws Exception;

    /**
     * 编辑
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> updateRoleInfo(Map<String, Object> map) throws Exception;

    /**
     * 删除
     *
     * @param map
     * @return
     * @throws Exception
     */
    Map<String, Object> deleteRoleInfo(Map<String, Object> map) throws Exception;

    /**
     * 获取指定角色的权限
     *
     * @param map
     * @return
     * @throws Exception
     */
    JSONObject findRolePermission(Map<String, Object> map) throws Exception;

    /**
     * 获取用户查看权限
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> getMenuList() throws Exception;

    /**
     * 查询全部权限信息
     *
     * @return
     * @throws Exception
     */
    Map<String, String> findAllPermission() throws Exception;

    List<String> findAllPermissionByUid(String uid) throws Exception;

    /**
     * 修改角色名
     *
     * @param map
     * @throws Exception
     */
    void updateRoleName(Map<String, Object> map) throws Exception;
}

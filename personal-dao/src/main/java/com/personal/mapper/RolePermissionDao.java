package com.personal.mapper;

import com.personal.model.SysUser;
import com.personal.model.Tree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ycv
 * @date 2017-12-19
 * 角色与权限管理
 */
@Repository
@Mapper
public interface RolePermissionDao {

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    SysUser findUserInfoByUsername(String username);
    /**
     * 查询当前用户角色信息
     * @param map
     * @return
     */
    List<String> findCurrentUserList(Map<String,Object> map);
    /**
     * 获取全部角色
     * @return
     */
    List<Map<String,Object>> findAllRoleList();
    /**
     * 查询部门负责人下角色
     * @param map
     * @return
     */
    List<Map<String,Object>> findDepartRoleList(Map<String,Object> map);
    /**
     * 显示用户创建的角色
     * @param map
     * @return
     */
    List<Map<String,Object>> findUserCreateRoleList(Map<String,Object> map);
    /**
     * 添加
     * @param map
     */
    void addRoleInfo(Map<String,Object> map);

    /**
     * 获取角色的id
     * @param map
     * @return
     */
    Map<String,Object> getRoleId(Map<String,Object> map);

    /**
     * 添加用户与角色的关联信息
     * @param map
     */
    void addUserRoleInfo(Map<String,Object> map);
    /**
     * 编辑
     * @param map
     */
    void updateRoleInfo(Map<String,Object> map);
    /**
     * 删除角色信息
     * @param map
     */
    void deleteRoleInfo(Map<String,Object> map);

    /**
     * 删除用户拥有的此角色信息
     * @param map
     */
    void deleteUserRoleInfo(Map<String,Object> map);

    /**
     * 删除角色权限
     * @param map
     */
    void deleteRolePermission(Map<String,Object> map);

    /**
     * 批量添加角色权限
     * @param rolePerList
     */
    void batchAddRolePerInfo(List rolePerList);

    /**
     * 查询指定角色的权限
     * @param map
     * @return
     */
    List<String> findRolePermission(Map<String,Object> map);
    /**
     * 查询角色对应权限信息
     * @param map
     * @return
     */
    List<Tree> findAllRolesList(Map<String,Object> map);

    /**
     * 根据用户角色查询对应权限url
     * @param map
     * @return
     */
    List<Map<String,Object>> findUrlByRid(Map<String,Object> map);

    /**
     * 根据权限id查询父级
     * @param id
     * @return
     */
    String findPidById(Object id);

    /**
     * 根据部门编号查询名称
     * @param map
     * @return
     */
    String findDepartNameById(Map<String,Object> map);

    /**
     * 查询所有权限信息
     * key:pid
     * value:req_url 请求地址
     * @return
     */
    List<Map<String,String>> findAllPermission();
    //SysUser findAccountInfoById

    /**
     * 修改角色名
     *
     * @param map
     */
    void updateRoleName(Map<String, Object> map);
}


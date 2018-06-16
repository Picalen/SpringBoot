package com.personal.mapper;

import com.personal.model.SysUser;
import com.personal.model.Tree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 子账户管理
 * @author ycv
 */
@Repository
@Mapper
public interface ChildrenAccountDao {

    /**
     * 获取账户信息
     * @param map
     * @return
     */
    List<Map<String,Object>> findChildListInfoList(Map<String,Object> map);

    /**
     * 查询部门信息 1 公司部门 2 行方部门
     * @param map
     * @return
     */
    List<Map<String,Object>> findDepartList(Map<String,Object> map);

    /**
     * 查询当前用户拥有的角色信息
     * @param map
     * @return
     */
    List<Map<String,Object>> findAllRoleList(Map<String,Object> map);

    /**
     * 查询当前用户创建的角色信息
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> findCreateRoleList(Map<String, Object> map);

    /**
     * 添加
     * @param map
     */
    void addChildAccount(Map<String,Object> map);

    /**
     * 编辑
     * @param map
     */
    void updateChildAccount(Map<String,Object> map);

    /**
     * 删除
     * @param map
     */
    void deleteChildAccount(Map<String,Object> map);

    /**
     * 删除用户角色
     * @param map
     */
    void deleteUserRoles(Map<String,Object> map);

    /**
     * 批量添加用户角色
     * @param roleList
     */
    void batchAddSysUserRole(List roleList);

    /**
     * 查询用户信息
     * @param map
     * @return
     */
    SysUser findUsernameExist(Map<String,Object> map);

    /**
     * 根据id查询账户信息
     * @param uid
     * @return
     */
    SysUser findUsernameById(String uid);

    /**
     * 查询用户拥有的角色
     * @param map
     * @return
     */
    List findRolesByUid(Map<String,Object> map);

    /**
     * 获取部门父级
     * @param departId
     * @return
     */
    Tree findParentIdById(String departId);

    /**
     * 根据用户获取符合条件的部门信息
     * @param map
     * @return
     */
    List<Tree> findDepartMenuList(Map<String,Object> map);

    /**
     * 获取所有部门 公司+银行
     *
     * @param
     * @return
     */
    List<Tree> findAllDepartMenuList();
}


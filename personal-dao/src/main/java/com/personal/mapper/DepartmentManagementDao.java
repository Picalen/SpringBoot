package com.personal.mapper;

import com.personal.model.Tree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * 部门管理
 * @author ycv
 */
@Repository
@Mapper
public interface DepartmentManagementDao {
    /**
     * 添加
     * @param map
     */
    void addDepartment(Map<String,Object> map);

    /**
     * 编辑
     * @param map
     */
    void updateDepartment(Map<String,Object> map);

    /**
     * 删除
     * @param map
     */
    void deleteDepartment(Map<String,Object> map);

    /**
     * 根据部门id,查询父级部门类型
     * @param map
     * @return
     */
    Map<String,Object> findDepartTypeById(Map<String,Object> map);

    /**
     * 获取部门信息
     * @param map
     * @return
     *
     */
    List<Tree> findCompanyDepartList(Map<String,Object> map);

    /**
     * 获取公司所有部门信息
     *
     * @param map
     * @return
     */
    List<Tree> findAllCompanyDepartList(Map<String, Object> map);

    /**
     * 根据id查询部门名称
     * @param departId
     * @return
     */
    String findNameById(String departId);


    /**
     * 根据部门id,查询是否有子级部门
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> findChildrenDepartment(Map<String, Object> map);

    /**
     * 根据部门id,查询是否有分配在该部门下的相关用户
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> findRelationUser(Map<String, Object> map);
}


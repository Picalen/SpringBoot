package com.personal.service.inter;



import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 部门管理
 * @author ycv
 */
public interface DepartmentManagementService {

    /**
     * 获取部门信息
     * @return
     * @throws Exception
     */
    JSONObject findDepartmentList() throws Exception;

    /**
     * 添加
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> addDepartment(Map<String,Object> map) throws Exception;

    /**
     * 编辑
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> updateDepartment(Map<String,Object> map) throws Exception;

    /**
     * 删除
     * @param map
     * @return
     * @throws Exception
     */
    Map<String,Object> deleteDepartment(Map<String,Object> map) throws Exception;
}

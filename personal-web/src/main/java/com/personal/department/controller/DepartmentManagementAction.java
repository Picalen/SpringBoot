package com.personal.department.controller;

import com.personal.service.inter.DepartmentManagementService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ycv
 * 部门管理
 */
@RestController
@RequestMapping(value = "/department")
public class DepartmentManagementAction {

    private Logger logger =Logger.getLogger(this.getClass());
    @Autowired
    private DepartmentManagementService departmentManagementServiceImpl;


    /**
     *  部门信息列表
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDepartmentList", method = RequestMethod.POST)
    public JSONObject getDepartmentList(HttpSession session) throws Exception{
        logger.info("===========获取部门信息==============");
        JSONObject json;
        json = departmentManagementServiceImpl.findDepartmentList();
        json = com.personal.utils.ReturnJson.toReturnJson(true,"部门信息查询成功",json);
        return json;
    }

    /**
     *  添加部门信息
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addDepartInfo", method = RequestMethod.POST)
    public JSONObject addDepartInfo(@RequestBody Map<String, Object> map, HttpSession session) throws Exception {
        logger.info("部门信息增加" + JSONObject.fromObject(map).toString());
        JSONObject json;
        Map<String, Object> result;
        //添加
        result = departmentManagementServiceImpl.addDepartment(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
        return json;
    }

    /**
     * 编辑部门信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateDepartInfo", method = RequestMethod.POST)
    public JSONObject updateDepartInfo(@RequestBody Map<String, Object> map, HttpSession session) throws Exception {
        logger.info("部门信息更新" + JSONObject.fromObject(map).toString());
        JSONObject json;
        Map<String, Object> result;
        //编辑
        result = departmentManagementServiceImpl.updateDepartment(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean) result.get("flag"), String.valueOf(result.get("info")), result.get("data"));
        return json;
    }

    /**
     * 删除部门
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delDepartInfo", method = RequestMethod.POST)
    public JSONObject delDepartInfo(@RequestBody Map<String,Object> map) throws Exception{
        logger.info("删除部门 "+JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        Map<String,Object>  result = new HashMap();
        result = departmentManagementServiceImpl.deleteDepartment(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
        return json;
    }
}

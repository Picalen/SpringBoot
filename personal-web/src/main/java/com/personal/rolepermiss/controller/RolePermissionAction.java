package com.personal.rolepermiss.controller;

import com.personal.service.inter.RolePermissionService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色与权限分配
 *
 * @author ycv
 * @date 2017/12/20
 */
@RestController
@RequestMapping(value = "/rolePer")
public class RolePermissionAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RolePermissionService rolePermissionServiceImpl;

    /**
     * 角色与权限查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findRolePerList", method = RequestMethod.POST)
    public JSONObject findRolePerList() throws Exception {
        logger.info("========== 角色与权限查询 ==========");
        JSONObject json = new JSONObject();
        json = rolePermissionServiceImpl.getUserRolesDataList();
        json = com.personal.utils.ReturnJson.toReturnJson(true, "角色与权限查询成功", json);
        return json;
    }

    /**
     * 修改角色名
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateRoleName", method = RequestMethod.POST)
    public JSONObject updateRoleName(@RequestBody Map<String, Object> map) throws Exception {
        logger.info("========== 修改角色名 ==========");
        JSONObject json;
        try {
            rolePermissionServiceImpl.updateRoleName(map);
            json = com.personal.utils.ReturnJson.toReturnJson(true, "角色修改成功", null);
        } catch (Exception e) {
            logger.info("error:" + e.getMessage());
            json = com.personal.utils.ReturnJson.toReturnJson(false, "角色修改失败：请查看后台报错", null);
        }

        return json;
    }

    @PostMapping("/findRolePerAfterHandle")
    public JSONObject findRolePerAfterHandle() {
        logger.info("========== 操作后查询角色与权限查询 ==========");
        JSONObject json = new JSONObject();
        try {
            json = rolePermissionServiceImpl.getUserRolesDataList();
            json.remove("treeData");
            json = com.personal.utils.ReturnJson.toReturnJson(true, "角色与权限查询成功", json);
            return json;
        } catch (Exception e) {
            logger.info("操作后查询角色与权限查询异常" + e.getMessage());
            return com.personal.utils.ReturnJson.toReturnJson(false, "角色与权限查询失败", json);
        }

    }

    /**
     * 添加/修改
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveRoleInfo", method = RequestMethod.POST)
    public JSONObject saveRoleInfo(@RequestBody Map<String, Object> map) throws Exception {
        logger.info("角色信息变更 " + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        Map<String, Object> result = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getSession().getAttribute("username");
        map.put("username", username);
        if (map.containsKey("id")) {
            //配置角色权限
            result = rolePermissionServiceImpl.updateRoleInfo(map);
        } else {
            //添加
            result = rolePermissionServiceImpl.addRoleInfo(map);
        }
        json = com.personal.utils.ReturnJson.toReturnJson((boolean) result.get("flag"), String.valueOf(result.get("info")), result.get("data"));
        String s = "";
        return json;
    }

    /**
     * 选中角色信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSelectRolePer", method = RequestMethod.POST)
    public JSONObject getCurrentRolePer(@RequestBody Map<String, Object> map) throws Exception {
        logger.info("获取选中角色的权限 " + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        json = rolePermissionServiceImpl.findRolePermission(map);
        json = com.personal.utils.ReturnJson.toReturnJson(true, "获取角色权限查询成功", json);
        return json;
    }

    /**
     * 删除角色信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteRoleInfo", method = RequestMethod.POST)
    public JSONObject deleteRoleInfo(@RequestBody Map<String, Object> map) throws Exception {
        logger.info("删除角色信息 " + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        Map<String, Object> result = new HashMap();
        result = rolePermissionServiceImpl.deleteRoleInfo(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean) result.get("flag"), String.valueOf(result.get("info")), result.get("data"));
        return json;
    }

}



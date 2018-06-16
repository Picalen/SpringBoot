package com.personal.children.controller;

import com.personal.service.inter.ChildrenAccountService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ycv
 * @description 子账户管理
 */
@RestController
@RequestMapping(value = "/children")
public class ChildrenAccountAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ChildrenAccountService childrenAccountServiceImpl;

    /**
     * 子账户管理查询
     * @params request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getChildAccountList", method = RequestMethod.POST)
    public JSONObject getChildAccountList() throws Exception{
        logger.info("======= 子账户管理查询 ========");
        JSONObject json;
        json = childrenAccountServiceImpl.findChildAccountList();
        json = com.personal.utils.ReturnJson.toReturnJson(true,"子账户查询成功",json);
        return json;
    }

    /**
     * 添加/编辑
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveChildAccount", method = RequestMethod.POST)
    public JSONObject saveChildAccount(@RequestBody Map<String,Object> map) throws Exception{
        logger.info("子账户信息变更 " + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        Map<String,Object> result = new HashMap();
        //添加
        if (!map.containsKey("id")){
            result = childrenAccountServiceImpl.addChildAccount(map);
        }else{//编辑
            result = childrenAccountServiceImpl.updateChildAccount(map);
        }
        json = com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
        return json;
    }

    /**
     * 删除
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/removeChildAccount", method = RequestMethod.POST)
    public JSONObject removeChildAccount(@RequestBody Map<String,Object> map) throws Exception{
        logger.info("删除子账户" + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        Map<String,Object> result = new HashMap();
        result = childrenAccountServiceImpl.deleteChildAccount(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
        return json;
    }

    /**
     * 查询角色信息
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    public JSONObject getRoleList(@RequestBody Map<String,Object> map) throws Exception{
        logger.info("查询角色信息 " + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        json = childrenAccountServiceImpl.setUserRole(map);
        json = com.personal.utils.ReturnJson.toReturnJson(true,"查询角色信息成功",json);
        return json;
    }

    /**
     * 分配角色
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setUserRoles", method = RequestMethod.POST)
    public JSONObject setUserRoles(@RequestBody Map<String,Object> map) throws Exception{
        logger.info("角色分配参数 " + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        Map<String,Object> result = new HashMap();
        result = childrenAccountServiceImpl.setUserRoleInfo(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
        return json;
    }

    /**
     * 变更密码
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public JSONObject changePassword(@RequestBody Map<String,Object> map) throws Exception{
        logger.info("===========================变更密码============================");
        logger.info("密码发送方式 " + JSONObject.fromObject(map).toString());
        JSONObject json = new JSONObject();
        Map<String,Object> result = new HashMap<>();
        result = childrenAccountServiceImpl.updatePassword(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
        return json;
    }
}



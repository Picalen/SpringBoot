package com.personal.service.impl;

import com.personal.enums.DEPARTTYPE;
import com.personal.enums.ROLES;
import com.personal.enums.SENDTYPE;
import com.personal.mapper.ChildrenAccountDao;
import com.personal.mapper.RolePermissionDao;
import com.personal.model.SysUser;
import com.personal.model.Tree;
import com.personal.service.inter.ChildrenAccountService;
import com.personal.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ycv
 * @description 子账户管理
 * @date
 */
@Service
public class ChildrenAccountServiceImpl implements ChildrenAccountService {

    private Logger logger =Logger.getLogger(this.getClass());
    /**
     * 申请账户时,默认密码
     */
    private static final String PASSWORD = "123456";

    @Autowired
    private ChildrenAccountDao mapper;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private RolePermissionServiceImpl rolePermissionServiceImpl;

    /**
     * 子账户查询
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject findChildAccountList() throws Exception {
        JSONObject json = new JSONObject();
        Map<String,Object> params = new HashMap<>();
        List list;
        Tree tree = new Tree();
        List departList;
        List<Map<String, Object>> childList;
        //获取当前用户信息
        SysUser user = rolePermissionServiceImpl.getUserInfo();
        //获取用户角色
        params.put("uid",user.getUid());
        List roles = rolePermissionDao.findCurrentUserList(params);
        try {
            //超级管理员
            if (roles.contains(ROLES.ADMIN.getCode())) {
//                params.put("create_user",user.getUid());
                params.put("depart_type", user.getDepartType());
                //获取符合用户权限的部门信息
                departList = mapper.findDepartList(params);
            } else if (roles.contains(ROLES.BANKMASTER.getCode())) {
                //普通用户
                params.put("depart_type","2");
                //判断旗下部门
                tree.setId(String.valueOf(user.getDepartId()));
                tree.setPid(String.valueOf(user.getParentId()));
                tree.setName(user.getDepartType());
                tree = getUserBankInfo(tree);
                //获取属于其下的部门(全部行)
                List<Tree> menuList = mapper.findDepartMenuList(params);
                //遍历获取当前用户所属行的部门id
                DepartUtil departUtil = new DepartUtil();
                list = departUtil.childList(menuList, Integer.valueOf(tree.getId()));
                if (list == null || list.size() == 0) {
                    list.add(" ");
                }
                params.put("list", list);
                departList = mapper.findDepartList(params);

            } else {
                //普通用户 获取本机构的depart_id

                // 判断旗下部门
                tree.setId(String.valueOf(user.getDepartId()));
                tree.setPid(String.valueOf(user.getParentId()));
                tree.setName(user.getDepartType());
                if (!DEPARTTYPE.Company.getCode().equals(user.getDepartType())) {
                    tree = getUserBankInfo(tree);
                }

                //获取属于其下的部门(全部行)
                List<Tree> menuList = mapper.findAllDepartMenuList();
                //遍历获取当前用户所属行的部门id
                DepartUtil departUtil = new DepartUtil();
                list = departUtil.childList(menuList,Integer.valueOf(tree.getId()));
                if (list == null || list.size() == 0){
                    list.add(" ");
                }
                params.put("list",list);
                departList = mapper.findDepartList(params);
            }
            //获取当前用户
//            if (DEPARTTYPE.Company.getCode().equals(user.getDepartType())){
//                //公司用户 ( 1 公司部门 2 行方部门)
//                params.put("depart_type","1");
//                //判断用户是否是管理员
//                if (!roles.contains(ROLES.ADMIN.getCode())){
//                    //普通用户视角
//
//                    params.put("create_user",user.getUid());
//                }
//                //获取符合用户权限的部门信息
//                departList = mapper.findDepartList(params);
//            }else {
//                //行方用户
//                params.put("depart_type","2");
//
//                //判断所属银行
//                tree.setId(String.valueOf(user.getDepartId()));
//                tree.setPid(String.valueOf(user.getParentId()));
//                tree.setName(user.getDepartType());
//                tree = getUserBankInfo(tree);
//
//                //获取属于银行的部门(全部行)
//                List<Tree> menuList = mapper.findDepartMenuList(params);
//                //遍历获取当前用户所属行的部门id
//                DepartUtil departUtil = new DepartUtil();
//                list = departUtil.childList(menuList,Integer.valueOf(tree.getId()));
//                if (list == null || list.size() == 0){
//                    list.add(" ");
//                }
//                params.put("list",list);
//                departList = mapper.findDepartList(params);
//                //判断是否是部门负责人
//                if (!roles.contains(ROLES.DepartHead.getCode())){
//                    //行方普通用户 只展示本人创建账户
//                    params.put("list",null);
//                    params.put("create_user",user.getUid());
//                }
//            }
            childList = mapper.findChildListInfoList(params);
            //数据整合
            childList = getChildInfoData(childList);
        } catch (Exception e) {
            logger.info("子账户查询获取异常 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"子账户查询获取异常",params);
        }
        json.put("data",JSONArray.fromObject(childList));
        json.put("departments",JSONArray.fromObject(departList));
        return json;
    }

    /**
     * 获取用户所属银行
     * @param tree
     * @return
     */
    public Tree getUserBankInfo(Tree tree){
        try{
            if (DEPARTTYPE.Bank.getCode().equals(tree.getName())){
                return tree;
            }else{
                if (StringUtils.isNotBlank(tree.getPid())){
                    tree = mapper.findParentIdById(tree.getPid());
                    tree = getUserBankInfo(tree);
                }
            }
        }catch (Exception e){
            logger.info("查询用户所属银行异常 \n" + e.getMessage());
        }
        return tree;
    }



    /**
     * 角色信息分配
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject setUserRole(Map<String, Object> map) throws Exception {
        JSONObject json = new JSONObject();
        Map<String,Object> params = new HashMap();
        List setRoleList = new ArrayList();
        List notGetRoleList = new ArrayList();
        //获取当前用户信息
        SysUser user = rolePermissionServiceImpl.getUserInfo();
        //当前用户角色信息
        params.put("uid",user.getUid());
        List roles = rolePermissionDao.findCurrentUserList(params);
        //判断用户类型
        if (DEPARTTYPE.Company.getCode().equals(user.getDepartType())){
            if (roles.contains(ROLES.ADMIN.getCode())){
                //管理员 可获取全部角色配置权限
                params.put("userId",null);
            }else{
                params.put("userId",user.getUid());
            }
        }else{
            params.put("userId",user.getUid());
        }
        //查询分配的角色
        params.put("uid",map.get("id"));
        //查询当前用户所拥有的角色信息
        List<Map<String,Object>> allRoleList = mapper.findAllRoleList(params);
        List<Map<String, Object>> createRoleList = mapper.findCreateRoleList(params);
        allRoleList.removeAll(createRoleList);
        allRoleList.addAll(createRoleList);
        try{
            if (allRoleList.size() > 0 ){
                for (Map<String,Object> role:allRoleList){
                    Map<String,Object> roleMap = new HashMap();
                    roleMap.put("key",role.get("rid"));
                    roleMap.put("title",role.get("name"));
                    roleMap.put("description",role.get("name"));
                    //未分配角色
                    notGetRoleList.add(roleMap);
                }
                //查询用户拥有的角色
                setRoleList = mapper.findRolesByUid(params);
            }
        }catch (Exception e){
            logger.info("返回角色配置失败 \n " + e.getMessage());
            return ReturnJson.toReturnJson(false,"返回角色配置失败",params);
        }
        json.put("targetKeys",JSONArray.fromObject(setRoleList));
        json.put("roleData",JSONArray.fromObject(notGetRoleList));
        return json;
    }


    /**
     * 添加子账户
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> addChildAccount(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap();
        Map<String,Object> result = new HashMap();
        boolean flag;

        //获取登录用户信息
        SysUser user = null;
        try{
            params.put("username",map.get("username"));
            //判断用户名是否存在
            user = mapper.findUsernameExist(params);
            if(user == null){
                //当前登录用户信息
                user = rolePermissionServiceImpl.getUserInfo();

                params.put("nickname",String.valueOf(map.get("name")));
                params.put("depart_id",String.valueOf(map.get("department")));
                //校验手机号是否符合要求
                flag = CheckedUtil.checkPhone(String.valueOf(map.get("phone")));
                if (flag){
                    params.put("telephone",String.valueOf(map.get("phone")));
                }else{
                    params.put("telephone",String.valueOf(map.get("phone")));
                    return ReturnJson.toReturnJson(false,"手机格式错误",params);
                }
                //校验邮箱是否符合要求
                flag = CheckedUtil.checkEmail(String.valueOf(map.get("email")));
                if (flag){
                    params.put("email",String.valueOf(map.get("email")));
                }else{
                    params.put("email",String.valueOf(map.get("email")));
                    return ReturnJson.toReturnJson(false,"邮箱格式错误",params);
                }
                //状态 0 锁定 1 正常
                params.put("status","1");
                params.put("create_user",user.getUid());

                user.setUsername(params.get("username").toString());
                user.setPassword(PASSWORD);
                //加密
                PasswordHelper pass = new PasswordHelper();
                pass.encryptPassword(user);
                params.put("password",user.getPassword());
                mapper.addChildAccount(params);
                result.put("flag",true);
                result.put("data",params);
                result.put("info","子账户添加成功");
                //账户添加成功,发送密码通知 判断密码发送方式 暂时只支持邮箱
                if (SENDTYPE.EMAIL.getCode().equals(String.valueOf(map.get("password")))){
                    try {
                        MailUtils mu = new MailUtils();
                        mu.setProperties("smtp.163.com", "15757174451@163.com", "15757174451@163.com", "Arthur1102");
                        //收件邮箱,默认为申请时填写邮箱
                        mu.setJsonParams("申请账户密码",PASSWORD,String.valueOf(params.get("email")));
                        mu.send();
                    } catch (Exception e) {
                        logger.info("信息通知异常 \n" + e.getMessage());
                        return ReturnJson.toReturnJson(false,"邮箱通知异常",params);
                    }
                }
            }else {
                result.put("flag",false);
                result.put("data",params);
                result.put("info","用户名已存在,添加失败");
            }
        }catch (Exception e){
            logger.info("子账户添加失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"子账户添加失败",params);
        }
        return result;
    }

    /**
     * 编辑
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> updateChildAccount(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap();
        Map<String,Object> result = new HashMap();
        boolean flag;
        SysUser user = null;
        try{
            params.put("uid",map.get("id"));
            user = mapper.findUsernameById(String.valueOf(map.get("id")));
            if (map.containsKey("name")){
                params.put("nickname",map.get("name"));
            }
            if (map.containsKey("username")){
                //判断用户名是否修改
                if (!user.getUsername().equals(map.get("username"))){
                    params.put("username",map.get("username"));
                    //判断用户名是否存在
                    user = mapper.findUsernameExist(params);
                    if (user != null){
                        return ReturnJson.toReturnJson(false,"用户名已存在,子账户修改失败",params);
                    }
                }else{
                    params.put("username",user.getUsername());
                }
            }
            if (map.containsKey("departmentId")){
                params.put("depart_id",map.get("departmentId"));
            }
            if (map.containsKey("phone")){
                //校验手机号是否符合要求
                flag = CheckedUtil.checkPhone(String.valueOf(map.get("phone")));
                if (flag){
                    params.put("telephone",String.valueOf(map.get("phone")));
                }else{
                    params.put("telephone",String.valueOf(map.get("phone")));
                    return ReturnJson.toReturnJson(false,"子账户手机格式错误",params);
                }
            }
            if (map.containsKey("email")){
                //校验邮箱是否符合要求
                flag = CheckedUtil.checkEmail(String.valueOf(map.get("email")));
                if (flag){
                    params.put("email",String.valueOf(map.get("email")));
                }else{
                    params.put("email",String.valueOf(map.get("email")));
                    return ReturnJson.toReturnJson(false,"子账户邮箱格式错误",params);
                }
            }
            if (map.containsKey("lock")){
                if ((boolean)map.get("lock")){
                    params.put("status","1");
                }else{
                    params.put("status","0");
                }
            }
            user = rolePermissionServiceImpl.getUserInfo();
            params.put("update_user",user.getUid());
            mapper.updateChildAccount(params);
            result.put("flag",true);
            result.put("data",params);
            result.put("info","子账户编辑成功");
        }catch (Exception e){
            logger.info("子账户编辑失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"子账户编辑失败",params);
        }
        return result;
    }

    /**
     * 删除
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> deleteChildAccount(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap();
        Map<String,Object> result = new HashMap();
        SysUser user = null;
        try{
            //获取当前账户信息
            user = rolePermissionServiceImpl.getUserInfo();
            if (String.valueOf(user.getUid()).equals(String.valueOf(map.get("id")))){
                return ReturnJson.toReturnJson(false,"不具备删除本身账户的权限",map);
            }
            params.put("uid",map.get("id"));
            mapper.deleteChildAccount(params);
            result.put("flag",true);
            result.put("data",params);
            result.put("info","删除子账户成功");
        }catch (Exception e){
            logger.info("子账户删除失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"子账户删除失败",params);
        }
        return result;
    }

    /**
     * 配置用户角色
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> setUserRoleInfo(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap();
        Map<String,Object> result = new HashMap();
        try{
            params.put("uid",map.get("id"));
            //删除用户已分配的角色
            mapper.deleteUserRoles(params);

            if (map.get("roles") instanceof ArrayList){
                ArrayList<Long> roles = (ArrayList<Long>)map.get("roles");
                List roleList = new ArrayList();
                if(roles.size() > 0){
                    for (Object ol:roles){
                        Map<String,Object> roleMap = new HashMap();
                        roleMap.put("uid",map.get("id"));
                        roleMap.put("rid",ol);
                        roleList.add(roleMap);
                    }
                    //批量添加角色
                    mapper.batchAddSysUserRole(roleList);
                }
            }
            result.put("flag",true);
            result.put("data",params);
            result.put("info","角色分配成功");
        }catch (Exception e){
            logger.info("子账户[角色分配失败] \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"子账户[角色分配失败]",params);
        }
        return result;
    }

    /**
     * 变更密码
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> updatePassword(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> result = new HashMap<>();
        SysUser user = null;
        String val = "";
        //随机密码生成长度限制
        Integer length = 6;
        try {
            //获取当前登录账户信息
            user = rolePermissionServiceImpl.getUserInfo();
            params.put("update_user",user.getUid());
            //生成随机数字和字母
            Random random = new Random();
            for (int i = 0;i<length;i++){
                String charOrNum = random.nextInt(2) % 2 == 0 ?"char" : "num";
                if ("char".equalsIgnoreCase(charOrNum)){
                    //输出是大写还是小写字母
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char)(random.nextInt(26) + temp);
                }else if("num".equalsIgnoreCase(charOrNum)){
                    val += String.valueOf(random.nextInt(10));
                }
            }
            //被修改人信息
            user = mapper.findUsernameById(String.valueOf(map.get("id")));
            user.setUsername(user.getUsername());
            user.setPassword(val);
            //密码加密
            PasswordHelper helper = new PasswordHelper();
            helper.encryptPassword(user);
            params.put("password",user.getPassword());
            params.put("uid",map.get("id"));
            mapper.updateChildAccount(params);
            result.put("flag",true);
            result.put("data",params);
            result.put("info","账户密码变更成功");

            //密码修改成功 判断信息通知类型
            if (SENDTYPE.EMAIL.getCode().equals(String.valueOf(map.get("password")))){
                //目前只支持邮箱通知方式
                try {
                    MailUtils m = new MailUtils();
                    m.setProperties("smtp.163.com", "15757174451@163.com", "15757174451@163.com", "Arthur1102");
                    m.setJsonParams("账户密码变更",val,user.getEmail());
                    m.send();
                } catch (Exception e) {
                    logger.info("账户密码变更发送通知失败 \n" + e.getMessage());
                    return ReturnJson.toReturnJson(false,"账户密码变更发送通知失败",params);
                }
            }
        } catch (Exception e) {
            logger.info("子账户[变更密码异常] \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"子账户[变更密码异常]",params);
        }
        return result;
    }

    /**
     * 查询数据处理
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> getChildInfoData(List<Map<String,Object>> childInfoList) throws Exception{
        List childList = new ArrayList();
        if(childInfoList.size() > 0){
            try{
                for (Map<String,Object> child:childInfoList){
                    Map<String,Object> map = new HashMap();
                    map.put("id",child.get("uid"));
                    if ((Object)child.get("username") != null){
                        map.put("username",child.get("username"));
                    }
                    if ((Object)child.get("nickname") != null){
                        map.put("name",child.get("nickname"));
                    }
                    if ((Object)child.get("depart_name") != null){
                        map.put("department",child.get("depart_name"));
                    }
                    if ((Object)child.get("telephone") != null){
                        map.put("phone",child.get("telephone"));
                    }
                    if ((Object)child.get("email") != null){
                        map.put("email",child.get("email"));
                    }
                    if("1".equals(child.get("status"))){
                        map.put("lock",true);
                    }else{
                        map.put("lock",false);
                    }
                    childList.add(map);
                }
            }catch (Exception e){
                logger.info("子账户查询数据处理失败 \n" + e.getMessage());
            }
        }
        return childList;
    }
}
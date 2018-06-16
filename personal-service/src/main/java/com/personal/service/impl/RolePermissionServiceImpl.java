package com.personal.service.impl;

import com.personal.enums.DEPARTTYPE;
import com.personal.enums.ROLES;
import com.personal.mapper.ChildrenAccountDao;
import com.personal.mapper.RolePermissionDao;
import com.personal.model.SysUser;
import com.personal.model.Tree;
import com.personal.service.inter.RolePermissionService;
import com.personal.utils.DepartUtil;
import com.personal.utils.MenuTreeUtil;
import com.personal.utils.ReturnJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 角色与权限分配
 *
 * @author ycv
 * @date 2017/12/20
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private ChildrenAccountServiceImpl childrenAccountServiceImpl;
    @Autowired
    private ChildrenAccountDao childrenAccountDao;

    /**
     * 查询角色与权限
     *
     * @return
     */
    @Override
    public JSONObject getUserRolesDataList() throws Exception {
        JSONObject json = new JSONObject();
        //获取当前用户信息
        Map<String, Object> params = findUIdByUsername();
        json = getRolesJson(params);
        return json;
    }

    /**
     * 根据当前用户查询角色与权限
     *
     * @return
     */
    @Override
    public JSONObject findRolePerByCurrentUser() {

        return null;
    }

    /**
     * 添加
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> addRoleInfo(Map<String, Object> map) throws Exception {
        Map<String, Object> params = new HashMap();
        Map<String, Object> result = new HashMap();
        SysUser user = null;
        try {
            //当前用户信息
            user = getUserInfo();
            params.put("create_user", user.getUid());
            if (DEPARTTYPE.Company.getCode().equals(user.getDepartType())) {
                //公司用户添加角色 0 行方 1 公司
                params.put("type", "1");
            } else {
                params.put("type", "0");
            }
            if (map.containsKey("name")) {
                params.put("name", map.get("name"));
            }
            //添加角色信息
            rolePermissionDao.addRoleInfo(params);
            //获取新创建的角色
            Map<String,Object> map2=rolePermissionDao.getRoleId(params);
            params.put("rid",(long)map2.get("rid"));
            //插入用户与角色的关联信息
            rolePermissionDao.addUserRoleInfo(params);
            result.put("flag", true);
            result.put("data", params);
            result.put("info", "角色添加成功");
        } catch (Exception e) {
            logger.info("角色添加失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "角色添加失败", params);
        }
        return result;
    }

    /**
     * 配置角色权限
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> updateRoleInfo(Map<String, Object> map) throws Exception {
        Map<String, Object> params = new HashMap<>();
        List list = new ArrayList();
        Set set = new HashSet();
        List pidList = new ArrayList();
        Map<String, Object> result = new HashMap<>();
        //角色rid
        params.put("rid", map.get("id"));
        try {
            //批量删除角色权限
            rolePermissionDao.deleteRolePermission(params);
            if (map.get("checkedKeys") instanceof ArrayList) {
                ArrayList<Long> checkedKeyList = (ArrayList<Long>) map.get("checkedKeys");
                logger.info("数组长度 " + checkedKeyList.size() + "数据" + checkedKeyList);
                //获取节点集合
                if (checkedKeyList.size() > 0) {
                    for (Object check : checkedKeyList) {
                        list = getPidList(list, check);
                    }
                    //节点去重
                    for (Object pid : list) {
                        if (set.add(pid)) {
                            pidList.add(pid);
                        }
                    }
                    logger.info("集合长度 " + pidList.size() + "父级去重结果 " + pidList);
                    List roleList = new ArrayList();
                    for (Object ol : pidList) {
                        Map<String, Object> permissMap = new HashMap<>();
                        permissMap.put("rid", map.get("id"));
                        permissMap.put("pid", ol);
                        roleList.add(permissMap);
                    }
                    //批量添加角色权限
                    rolePermissionDao.batchAddRolePerInfo(roleList);
                }
                result.put("flag", true);
                result.put("data", params);
                result.put("info", "角色权限配置成功");
            }
        } catch (Exception e) {
            logger.info("角色权限配置失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "角色权限配置失败", params);
        }
        return result;
    }

    /**
     * 根据id循环遍历父级
     *
     * @param id
     * @return
     */
    public List getPidList(List list, Object id) {
        if (id == null || id == "") {
            return list;
        } else {
            String pid = rolePermissionDao.findPidById(id);
            if (StringUtils.isNotBlank(pid)) {
                list.add(id);
                list.add(pid);
                getPidList(list, pid);
            } else {
                list.add(id);
            }
        }
        return list;
    }


    /**
     * 删除
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> deleteRoleInfo(Map<String, Object> map) throws Exception {
        Map<String, Object> result = new HashMap();
        try {
            map.put("rid", map.get("id"));
            //删除指定角色的权限
            rolePermissionDao.deleteRolePermission(map);
            //删除用户拥有的此角色信息

            rolePermissionDao.deleteUserRoleInfo(map);
            //删除角色
            rolePermissionDao.deleteRoleInfo(map);
            result.put("flag", true);
            result.put("data", map);
            result.put("info", "角色删除成功");
        } catch (Exception e) {
            logger.info("角色删除失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "角色删除失败", map);
        }
        return result;
    }

    /**
     * 获取指定角色的权限
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject findRolePermission(Map<String, Object> map) throws Exception {
        JSONObject json = new JSONObject();
        try {
            map.put("rid", map.get("id"));
            List<String> rolePermissionList = rolePermissionDao.findRolePermission(map);
            json.put("checkedKeys", JSONArray.fromObject(rolePermissionList));
        } catch (Exception e) {
            logger.info("指定角色权限查询异常 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "指定角色权限查询异常", map);
        }
        return json;
    }


    /**
     * 获取当前用户信息
     *
     * @return
     */
    public Map<String, Object> findUIdByUsername() throws Exception {
        Map<String, Object> map = new HashMap<>();
        SysUser user = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            String username = (String) subject.getSession().getAttribute("username");
            map.put("username", username);
            user = rolePermissionDao.findUserInfoByUsername(username);
            map.put("uid", user.getUid());
            map.put("depart_id", user.getDepartId());
            map.put("depart_type", user.getDepartType());
            map.put("parent_id", user.getParentId());
        } catch (Exception e) {
            logger.info("获取当前账户信息失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "获取当前账户信息失败", map);
        }
        return map;
    }

    /**
     * 查询对应角色用户查看权限
     *
     * @param map
     * @return
     * @throws Exception
     */
    public JSONObject getRolesJson(Map<String, Object> map) throws Exception {
        JSONObject json = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        List roleList = new ArrayList();
        List limitList = new ArrayList();
        List bankList = new ArrayList();
        Tree tree = new Tree();
        try {
            //查询当前用户角色
            List<String> rolesList = rolePermissionDao.findCurrentUserList(map);
            if (rolesList != null && rolesList.size() > 0) {
                if (!rolesList.contains(ROLES.ADMIN.getCode())) {
                    map.put("list", rolesList);
                    //部门负责人
                    if (rolesList.contains(ROLES.BANKMASTER.getCode())) {
                        //查看所属行
                        tree.setId(String.valueOf(map.get("depart_id")));
                        tree.setPid(String.valueOf(map.get("parent_id")));
                        tree.setName(String.valueOf(map.get("depart_type")));
                        tree = childrenAccountServiceImpl.getUserBankInfo(tree);
                        //获取银行下面所有部门
                        params.put("depart_type", "2");
                        List<Tree> departList = childrenAccountDao.findDepartMenuList(params);
                        DepartUtil departUtil = new DepartUtil();
                        bankList = departUtil.childList(departList, Integer.valueOf(tree.getId()));
                        bankList.add(tree.getId());
                        params.put("type", "0");
                        if (bankList == null && bankList.size() == 0) {
                            params.put("list", " ");
                        } else {
                            params.put("list", bankList);
                        }
                        //查询所属行全部角色
                        roleList = rolePermissionDao.findDepartRoleList(params);
                    } else {
                        //登录用户创建角色
                        roleList = rolePermissionDao.findUserCreateRoleList(map);
                    }
                } else {
                    //管理员 全部角色
                    roleList = rolePermissionDao.findAllRoleList();
                }
                //分配权限
                limitList = getRoleMenuList(map);
                json.put("data", JSONArray.fromObject(roleList));
                json.put("treeData", JSONArray.fromObject(limitList));
            }
        } catch (Exception e) {
            logger.info("查询对应用户角色查看权限异常 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "查询对应用户角色查看权限异常", map);
        }

        return json;
    }

    /**
     * 获取用户查看权限
     *
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getMenuList() throws Exception {
        List<String> menuList = new ArrayList();
        List<String> reqUrlList = new ArrayList();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        SysUser user = getUserInfo();
        params.put("uid", user.getUid());
        //查询当前用户角色
        try {
            List<String> roles = rolePermissionDao.findCurrentUserList(params);
            if (roles != null && roles.size() > 0) {
                if (!roles.contains(ROLES.ADMIN.getCode())) {
                    //普通账户视角
                    params.put("list", roles);
                }
                try {
                    List<Map<String, Object>> urlList = rolePermissionDao.findUrlByRid(params);
                    for (Map<String, Object> url : urlList) {
                        menuList.add((String.valueOf(url.get("url"))));
                        reqUrlList.add(String.valueOf(url.get("req_url")));
                    }
                    result.put("menuList", menuList);
                    result.put("reqUrlList", reqUrlList);

                } catch (Exception e) {
                    logger.info("权限地址获取异常 \n" + e.getMessage());
                    return ReturnJson.toReturnJson(false, "权限地址获取异常", params);
                }
            }
        } catch (Exception e) {
            logger.info("当前账户查看权限获取异常 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "当前账户查看权限获取异常", params);
        }
        return result;
    }

    /**
     * 查询登录用户信息
     *
     * @return
     */
    public SysUser getUserInfo() throws Exception {
        SysUser user = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            String username = (String) subject.getSession().getAttribute("username");
            user = rolePermissionDao.findUserInfoByUsername(username);
        } catch (InvalidSessionException e) {
            logger.info("获取用户信息异常 \n" + e.getMessage());
        }
        return user;
    }

    /**
     * 获取全部权限并分级
     *
     * @return
     * @throws Exception
     */
    public List getRoleMenuList(Map<String, Object> map) throws Exception {
        //所有可展示权限
        List<Object> menuList = null;
        try {
            List<Tree> allRoleList = rolePermissionDao.findAllRolesList(map);
            //级数划分
            MenuTreeUtil menu = new MenuTreeUtil();
            menuList = menu.menuList(allRoleList, "key", "title");
        } catch (Exception e) {
            logger.info("权限分级获取异常 \n" + e.getMessage());
        }
        return menuList;
    }

    /**
     * 查询所有权限信息
     *
     * @return
     */
    @Override
    public Map<String, String> findAllPermission() throws Exception {
        Map<String, String> result = new HashMap<>();
        List<Map<String, String>> permissionList = null;
        try {
            permissionList = rolePermissionDao.findAllPermission();
            for (Map<String, String> list : permissionList) {
                result.put(list.get("req_url"), list.get("pid"));
            }
        } catch (Exception e) {
            logger.info("获取全部权限信息异常 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false, "获取全部权限信息异常", result);
        }
        return result;
    }

    /**
     * 根据id查询用户所拥有权限
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<String> findAllPermissionByUid(String uid) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> rolesList = new ArrayList<>();
        List<String> permissList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        SysUser user = null;
        try {
            params.put("uid", uid);
            //查询用户拥有角色
            rolesList = rolePermissionDao.findCurrentUserList(params);
            if (!rolesList.contains(ROLES.ADMIN.getCode())) {
                //普通账户
                params.put("list", rolesList);
            }
            list = rolePermissionDao.findUrlByRid(params);
            for (Map<String, Object> ol : list) {
                permissList.add(String.valueOf(ol.get("pid")));
            }
        } catch (Exception e) {
            logger.info("根据id查询用户权限异常 \n" + e.getMessage());
        }
        return permissList;
    }

    /**
     * 修改角色名
     *
     * @return
     * @throws Exception
     */
    @Override
    public void updateRoleName(Map<String, Object> map) throws Exception {
        rolePermissionDao.updateRoleName(map);
    }
}
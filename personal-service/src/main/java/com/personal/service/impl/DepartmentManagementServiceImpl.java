package com.personal.service.impl;

import com.personal.enums.DEPARTTYPE;
import com.personal.enums.ROLES;
import com.personal.mapper.DepartmentManagementDao;
import com.personal.mapper.RolePermissionDao;
import com.personal.model.SysUser;
import com.personal.model.Tree;
import com.personal.service.inter.DepartmentManagementService;
import com.personal.utils.MenuTreeUtil;
import com.personal.utils.ReturnJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 部门管理
 * @author ycv
 */
@Service
public class DepartmentManagementServiceImpl implements DepartmentManagementService {

    private Logger logger =Logger.getLogger(this.getClass());

    @Autowired
    private DepartmentManagementDao mapper;
    @Autowired
    private ChildrenAccountServiceImpl childrenAccountServiceImpl;
    @Autowired
    private RolePermissionServiceImpl rolePermissionServiceImpl;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    /**
     * 查询部门列表
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject findDepartmentList() throws Exception {
        JSONObject json = new JSONObject();
        Map<String,Object> params = new HashMap<>();
        Tree tree = new Tree();
        List<Tree> list;
        List departmentList;
        List roleList;
        //查询用户信息
        SysUser user = rolePermissionServiceImpl.getUserInfo();
        //获取用户角色
        params.put("uid", user.getUid());
        List roles = rolePermissionDao.findCurrentUserList(params);
        try {
            if(DEPARTTYPE.Company.getCode().equals(user.getDepartType())){
                //公司账户 (0 行方 1 公司部门 2 行方部门)
                params.put("depart_type","1");
                if (roles.contains(ROLES.ADMIN.getCode())) {
                    list = mapper.findCompanyDepartList(params);
                } else {
                    list = mapper.findAllCompanyDepartList(params);
                    tree.setPid(null);
                    tree.setId(String.valueOf(user.getDepartId()));
                    tree.setName(user.getDepartName());
                    list.add(tree);
                }
                //departmentList = getDepartList(list);
            }else {
                params.put("depart_type","2");
                //获取隶属于行方部门`
                list = mapper.findCompanyDepartList(params);
                //判断当前用户是否直属行方
                if (DEPARTTYPE.Bank.getCode().equals(user.getDepartType())){
                    //银行作为第一层级
                    tree.setPid(null);
                    tree.setId(String.valueOf(user.getDepartId()));
                    tree.setName(user.getDepartName());
                    list.add(tree);
                }else{
                    //判断用户是否为行方负责人
                    params.put("uid",user.getUid());
                    roleList = rolePermissionDao.findCurrentUserList(params);
                    if (roleList.contains(ROLES.BANKMASTER.getCode())) {
                        //判断用户所属行
                        tree.setId(String.valueOf(user.getDepartId()));
                        tree.setPid(String.valueOf(user.getDepartId()));
                        tree.setName(user.getDepartType());
                        tree = childrenAccountServiceImpl.getUserBankInfo(tree);
                        //展示行方全部门信息
                        tree.setPid(null);
                        tree.setId(tree.getId());
                        //查询所属行名称
                        String departName = mapper.findNameById(tree.getId());
                        tree.setName(departName);
                        list.add(tree);
                    }else{
                        //(当前用户所在部门作为第一层级)
                        list = bankDepartList(list,user);
                    }
                }
            }
            departmentList = getDepartList(list);
        } catch (Exception e) {
            logger.info("部门信息查询失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"部门信息查询失败",params);
        }
        json.put("data", JSONArray.fromObject(departmentList));
        //添加判断  只有管理员用户可以添加一级部门
        if (roles.contains(ROLES.ADMIN.getCode())){
            json.put("security",true);
        }
        return json;
    }

    /**
     * 添加
     * @param map
     * @throws Exception
     */
    @Override
    public Map<String, Object> addDepartment(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap();
        Map<String,Object> result = new HashMap<>();
        SysUser user = rolePermissionServiceImpl.getUserInfo();
        Tree tree = new Tree();
        try{
            if (map.containsKey("id")) {
                params.put("parent_id",map.get("id"));
            }else{
                //第一层级部门 判断是否属于行方
                if (!DEPARTTYPE.Company.getCode().equals(user.getDepartType())){
                    tree.setId(String.valueOf(user.getDepartId()));
                    tree.setPid(String.valueOf(user.getParentId()));
                    tree.setName(user.getDepartType());
                    tree = childrenAccountServiceImpl.getUserBankInfo(tree);
                    params.put("parent_id",tree.getId());
                }
            }
            params.put("depart_name", map.get("name"));
            params.put("create_user",user.getUid());
            //判断用户类型 0 行方 1 公司部门 2 行方部门
            if (DEPARTTYPE.Company.getCode().equals(user.getDepartType())){
                params.put("depart_type","1");
            }else{
                //行方部门
                params.put("depart_type","2");
            }
            mapper.addDepartment(params);
            result.put("flag",true);
            result.put("data",params);
            result.put("info","部门添加成功");
        }catch (Exception e){
            logger.info("部门添加失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"部门添加失败",params);
        }
        return result;
    }

    /**
     * 编辑
     * @param map
     * @throws Exception
     */
    @Override
    public Map<String, Object> updateDepartment(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap();
        Map<String,Object> result = new HashMap<>();
        SysUser user = rolePermissionServiceImpl.getUserInfo();
        try{
            params.put("depart_id",map.get("id"));
            params.put("depart_name", map.get("name"));
            params.put("update_user",user.getUid());
            mapper.updateDepartment(params);
            result.put("flag",true);
            result.put("data",params);
            result.put("info","部门编辑成功");
        }catch (Exception e){
            logger.info("部门编辑失败 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"部门编辑失败",params);
        }
        return result;
    }

    /**
     * 删除
     * @param map
     * @throws Exception
     */
    @Override
    public Map<String, Object> deleteDepartment(Map<String, Object> map) throws Exception {
        Map<String,Object> params = new HashMap();
        Map<String,Object> result = new HashMap<>();
        try{
            params.put("depart_id",map.get("id"));
            List<Map<String, Object>> list1 = mapper.findChildrenDepartment(params);
            List<Map<String, Object>> list2 = mapper.findRelationUser(params);
            if (list1 != null && list1.size() > 0) {
                result.put("flag", false);
                result.put("data", params);
                result.put("info", "部门删除失败:请先删除子级部门后再尝试");
            } else if (list2 != null && list2.size() > 0) {
                result.put("flag", false);
                result.put("data", params);
                result.put("info", "部门删除失败:请先删除部门下用户后再尝试");
            } else {
                mapper.deleteDepartment(params);
                result.put("flag", true);
                result.put("data", params);
                result.put("info", "部门删除成功");
            }


        }catch (Exception e){
            logger.info("部门删除成功 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"部门删除失败",params);
        }
        return result;
    }

    /**
     * 判断用户类型
     * (是否属于银行人员)
     * @param map
     * @return
     */
    public boolean getUserType(Map<String,Object> map){
        boolean flag = false;
        if (DEPARTTYPE.Bank.getCode().equals(map.get("depart_type"))){
            //行方用户
            return true;
        }else{
            Map<String,Object> result = mapper.findDepartTypeById(map);
            if (result != null && result.size() > 0){
                flag = getUserType(result);
            }
        }
        return flag;
    }

    /**
     * 获取可展示部门层级
     * @param list
     * @return
     * @throws Exception
     */
    public List getDepartList(List<Tree> list) throws Exception{
        List<Object> menuList = null;
        try {
            MenuTreeUtil menu = new MenuTreeUtil();
            menuList = menu.menuList(list, "id", "name");
        } catch (Exception e) {
            logger.info("部门层级获取异常 \n" + e.getMessage());
        }
        return menuList;
    }

    /**
     * 当用户为行方时,部门展示信息处理
     * @param list 全部部门信息
     * @return
     * @throws Exception
     */
    public List<Tree> bankDepartList(List<Tree> list,SysUser user) throws Exception{
        String departNo = user.getDepartId().toString();
        if (StringUtils.isNotBlank(departNo)) {
            for (Tree bank:list){
                if (departNo.equals(bank.getId())){
                    bank.setPid(null);
                }
            }
        }
        return list;
    }
}
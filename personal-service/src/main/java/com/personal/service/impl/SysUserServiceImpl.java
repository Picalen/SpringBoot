package com.personal.service.impl;

import com.personal.enums.DEPARTTYPE;
import com.personal.mapper.RolePermissionDao;
import com.personal.mapper.SysUserDao;
import com.personal.model.SysUser;
import com.personal.model.Tree;
import com.personal.utils.CheckedUtil;
import com.personal.utils.PasswordHelper;
import com.personal.utils.ReturnJson;
import com.personal.service.inter.SysUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private ChildrenAccountServiceImpl childrenAccountServiceImpl;
    @Autowired
    private RolePermissionServiceImpl rolePermissionServiceImpl;
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * shiro登录
     *
     * @param username 用户名
     * @return
     */
    @Override
    public SysUser loginByUsername(String username) {
        return sysUserDao.loginByUsername(username);
    }
    ;

    /**
     * 修改密码
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> updatePwd(Map<String, Object> map) throws Exception {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getSession().getAttribute("username");
        params.put("username",username);

        String oldPassword = String.valueOf(map.get("oldPassword"));
        String newPassword = String.valueOf(map.get("newPassword"));

        PasswordHelper pass = new PasswordHelper();
        SysUser user = null;
        //获取当前账户信息
        try {
            user = rolePermissionDao.findUserInfoByUsername(username);
            //判断原密码是否正确
            String password = user.getPassword();
            user.setUsername(username);
            user.setPassword(oldPassword);
            pass.encryptPassword(user);
            if (password.equals(user.getPassword())){
                user.setPassword(newPassword);
                pass.encryptPassword(user);
                params.put("uid",user.getUid());
                params.put("password",user.getPassword());
                sysUserDao.updatePwd(params);
                result.put("flag",true);
                result.put("data",params);
                result.put("info", "密码修改成功");
            }else {
                return ReturnJson.toReturnJson(false,"原密码错误,修改失败",params);
            }
        } catch (Exception e) {
            logger.info("密码修改异常 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"密码修改异常",params);
        }
        return result;
    }

    @Override
    public SysUser loginByUserNameAndPass(Map<String, String> map) {
        return sysUserDao.loginByUsernameAndPass(map);
    }

    @Override
    public Map<String, Object> getCurrentUser(SysUser sysUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", sysUser.getNickname());
        map.put("userId", sysUser.getUid());
//        map.put("avatar", "../assets/myimg.png");
//        map.put("notifyCount", 1);
        return map;
    }

    /**
     *
     * 修改用户信息
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> updateUserInfo(Map<String,Object> map) {
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getSession().getAttribute("username");
        SysUser user = null;
        boolean flag;
        try {
            user = rolePermissionDao.findUserInfoByUsername(username);
            params.put("uid",user.getUid());
            params.put("update_user",user.getUid());
            params.put("nickname",String.valueOf(map.get("nickname")));

            flag = CheckedUtil.checkPhone(String.valueOf(map.get("phone")));
            if (flag){
                params.put("phone",String.valueOf(map.get("phone")));
            }else{
                return ReturnJson.toReturnJson(false,"手机格式异常",params);
            }
            flag = CheckedUtil.checkEmail(String.valueOf(map.get("email")));
            if (flag){
                params.put("email",String.valueOf(map.get("email")));
            }else {
                return ReturnJson.toReturnJson(false,"邮箱格式异常",params);
            }
            if (StringUtils.isNotBlank(String.valueOf(map.get("weChat")))){
                params.put("wechat",String.valueOf(map.get("weChat")));
            }
            sysUserDao.updateUserInfo(params);
            result.put("flag",true);
            result.put("data",params);
            result.put("info","更改个人中心信息成功");
        } catch (Exception e) {
            logger.info("更改个人信息异常 \n" + e.getMessage());
            return ReturnJson.toReturnJson(false,"更改个人信息异常",params);
        }
        return result;
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public JSONObject findUserInfoByUserName() throws Exception {
        JSONObject json = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Tree tree = new Tree();
        SysUser user = null;
        String bank = " ";
        try {
            user = rolePermissionServiceImpl.getUserInfo();
            params.put("uid", user.getUid());
            //查看帐号基本信息
            result = sysUserDao.findUserInfoByUserName(params);
            //判断用户类型
            if (!DEPARTTYPE.Company.getCode().equals(user.getDepartType())) {
                //获取 账户所在行信息
                tree.setId((String.valueOf(user.getDepartId())));
                tree.setPid(String.valueOf(user.getParentId()));
                tree.setName(user.getDepartType());
                tree = childrenAccountServiceImpl.getUserBankInfo(tree);
                params.put("depart_id", tree.getId());
                //所在行名称
                bank = rolePermissionDao.findDepartNameById(params);
            }
            result.put("bank", bank);
            //账号基本信息
            json.put("baseInfo", JSONObject.fromObject(result));
        } catch (Exception e) {
            logger.info("查看账户基本信息异常 \n " + e.getMessage());
            return ReturnJson.toReturnJson(false,"查看帐号信息异常",params);
        }
        return json;
    }

}

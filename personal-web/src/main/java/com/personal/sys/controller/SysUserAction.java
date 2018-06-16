package com.personal.sys.controller;

import com.personal.model.SysUser;
import com.personal.service.inter.RolePermissionService;
import com.personal.service.inter.SysUserService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class SysUserAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService userServiceImpl;

    @Autowired
    private RolePermissionService rolePermissionServiceImpl;

    /**
     * 用户账号密码登录
     *
     * @param sysUser  登录参数
     * @param session  session
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody SysUser sysUser, HttpSession session, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "account");
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword(), sysUser.isRemember());
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        String message;
        boolean flag;
        try {
            subject.login(token);
            message = "登录成功";
            flag = true;
            subject.getSession().setAttribute("username", sysUser.getUsername());
            session.setAttribute("username", sysUser.getUsername());
            session.setAttribute("email", "123test@qq.com");
            session.setAttribute("token", subject.getSession().getId());

            //获取此用户查看菜单权限
            StringBuilder builder = new StringBuilder();
            Map<String,Object> result = rolePermissionServiceImpl.getMenuList();
            List<String> menuList = (ArrayList)result.get("menuList");
            List<String> reqUrlList = (ArrayList)result.get("reqUrlList");

            if (null != menuList && menuList.size() > 0) {
                for (String ol : menuList) {
                    builder.append(ol);
                    builder.append("|");
                }
                builder.deleteCharAt(builder.length() - 1);
            }
            session.setAttribute("reqUrlList",reqUrlList);

            Cookie cookie = new Cookie("jdBjId", subject.getSession().getId().toString());
            cookie.setPath("/");
            cookie.setMaxAge(1800);
            response.addCookie(cookie);

            Cookie menus = new Cookie("aMjedNuS", builder.toString());
            menus.setPath("/");
            response.addCookie(menus);
        } catch (UnknownAccountException uae) {
            message = "未知账户";
            flag = false;
        } catch (IncorrectCredentialsException ice) {
            message = "错误的密码";
            flag = false;
        } catch (LockedAccountException lae) {
            message = "账户已锁定";
            flag = false;
        } catch (ExcessiveAttemptsException eae) {
            message = "错误次数过多";
            flag = false;
        } catch (AuthenticationException ae) {
            message = "用户名或密码错误";
            flag = false;
        } catch (Exception e) {
            logger.info("登录异常:\n" + e.getMessage());
            message = "未知错误";
            flag = false;
        }
        return com.personal.utils.ReturnJson.toReturnJson(flag, message, jsonObject);
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JSONObject logout(HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        try {
            Subject subject = SecurityUtils.getSubject();
            logger.info(session.getAttribute("username") + "用户登出");
            map.put("登出用户",session.getAttribute("username"));
            subject.logout();
            return com.personal.utils.ReturnJson.toReturnJson(true, "用户登出成功",map);
        } catch (Throwable e) {
            logger.info("登出异常:\n" + e.getMessage());
            return com.personal.utils.ReturnJson.toReturnJson(false, "用户登出异常",map);
        }
    }

    /**
     * 个人中心数据查询
     * @return
     */
    @RequestMapping(value = "/findUserBaseInfo", method = RequestMethod.POST)
    public JSONObject findUserBaseInfo() throws Exception {
        logger.info("========================= 个人中心数据查询 ==========================");
        JSONObject json = new JSONObject();
        json = userServiceImpl.findUserInfoByUserName();
        json = com.personal.utils.ReturnJson.toReturnJson(true,"个人中心数据查询成功",json);
        return json;
    }

    /**
     *
     * 个人中心信息变更
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public JSONObject updateUserInfo(@RequestBody Map<String,Object> map) {
        logger.info("个人中心信息变更" + JSONObject.fromObject(map));
        Map<String,Object> result = new HashMap<>();
        result = userServiceImpl.updateUserInfo(map);
        return com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
    }

    /**
     * 修改用户密码
     * @param map
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public JSONObject updatePassword(@RequestBody Map<String, Object> map) throws Exception {
        logger.info("修改用户密码" + JSONObject.fromObject(map));
        JSONObject json = new JSONObject();
        Map<String, Object> result = new HashMap();
        result = userServiceImpl.updatePwd(map);
        json = com.personal.utils.ReturnJson.toReturnJson((boolean)result.get("flag"),String.valueOf(result.get("info")),result.get("data"));
        return json;
    }

    @RequestMapping("/getCurrentUser")
    public JSONObject getCurrentUser() {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        Map<String, Object> map = userServiceImpl.getCurrentUser(sysUser);
        return JSONObject.fromObject(map);
    }


}


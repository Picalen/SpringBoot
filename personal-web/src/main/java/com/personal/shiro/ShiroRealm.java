package com.personal.shiro;

import com.personal.enums.ROLES;
import com.personal.mapper.RolePermissionDao;
import com.personal.model.SysUser;
import com.personal.service.inter.RolePermissionService;
import com.personal.service.inter.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

/**
 * @author  王嘉
 * @date  2017/12/20
 * shiro实现
 */
public class ShiroRealm extends AuthorizingRealm {
    public final static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    /**
    * 用户登录次数计数
    */
    private String shiroLoginCount = "shiro_login_count_";
    /**
     * 用户登录是否被锁定
    */
    private String shiroIsLock = "shiro_is_lock_";
    /**
     * 用户是否锁定
     * 0为锁定状态
     */
    private String accountEnable="0";


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        Session session = SecurityUtils.getSubject().getSession();
        Object alive=session.getAttribute("isAlive");
        if(alive==null){
            SecurityUtils.getSubject().logout();
        }else{
            List<String> list=(List<String>)session.getAttribute("permission");
            if (list!=null) {
                Set<String> reses = new HashSet<String>();
                try {
                    for(String res : list){
                        reses.add(res);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                info.addStringPermissions(reses);
                return info;
            }
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取用户的输入的账号
        String username = (String)authcToken.getPrincipal();
        SysUser user = sysUserService.loginByUsername(username);
        if(user==null) {
            throw new UnknownAccountException();
        }
        if (accountEnable.equals(user.getStatus())) {
            // 帐号锁定,抛异常
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                //用户对象，密码，用户名，realm name
                user,
                user.getPassword(),
                ByteSource.Util.bytes(username),
                getName()
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        //查询用户角色信息
        Map<String,Object> params = new HashMap<>();
        params.put("uid",user.getUid());
        List<String> roleList = null;
        try {
            roleList = rolePermissionDao.findCurrentUserList(params);
        } catch (Exception e) {
            logger.info("用户角色查询异常 \n " + e.getMessage());
        }

        if (roleList.contains(ROLES.ADMIN.getCode())){
            session.setAttribute("userRole","ADMIN");
        }else if (roleList.contains(ROLES.BANKMASTER.getCode())){
            session.setAttribute("userRole","BANKMASTER");
        }else {
            session.setAttribute("userRole",roleList);
        }
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getUid());
        List<String> list= null;
        try {
            list = rolePermissionService.findAllPermissionByUid(String.valueOf(user.getUid()));
        } catch (Exception e) {
            return authenticationInfo;
        }
        session.setAttribute("permission",list);
        session.setAttribute("isAlive","alive");
        return authenticationInfo;
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
    /**
     *
     * @Title: clearAuthz
     * @Description: TODO 清楚缓存的授权信息
     * @return void    返回类型
     */
    public void clearAuthz(){
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}

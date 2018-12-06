//package com.personal.shiro;
//
//import com.personal.service.inter.RolePermissionService;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * @author  王嘉
// * @date  2017/12/20
// * shiro配置
// */
//@Configuration
//public class ShiroConfig {
//    @Autowired
//    private RolePermissionService rolePermissionService;
//    /**
//     * ShiroFilterFactoryBean 处理拦截资源文件问题。
//     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
//     * 3、部分过滤器可指定参数，如perms，roles
//     *
//     */
//    @Bean
//    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        // 必须设置 SecurityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setLoginUrl("/ELogin");
//        // 拦截器.
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        // 配置不会被拦截的链接 顺序判断
//        filterChainDefinitionMap.put("/publicOpinionDynamic/publicOpinionReception","anon");
//        filterChainDefinitionMap.put("/monitorcompany/receptionMonitorCompanyPush","anon");
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/user/login", "anon");
//        try {
//            Map<String,String> map=rolePermissionService.findAllPermission();
//            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry<String, String> entry = it.next();
//                filterChainDefinitionMap.put(entry.getKey(),"perms["+String.valueOf(entry.getValue())+"]");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
////        filterChainDefinitionMap.put("/user/logout", "logout");
//        //filterChainDefinitionMap.put("/add", "perms[权限添加]");
//        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//
//
//        filterChainDefinitionMap.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//    @Bean
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        // 设置realm.
//        securityManager.setRealm(myShiroRealm());
//        //注入记住我管理器
//        //securityManager.setRememberMeManager(rememberMeManager());
//        return securityManager;
//    }
//    /**
//     * 身份认证realm
//     *
//     * @return
//     */
//    @Bean
//    public ShiroRealm myShiroRealm(){
//        ShiroRealm shiroRealm = new ShiroRealm();
//        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        return shiroRealm;
//    }
//
//    /**
//     * 凭证匹配器
//     * @return
//     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        //散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        //散列的次数，比如散列两次，相当于 md5(md5(""));
//        hashedCredentialsMatcher.setHashIterations(2);
//        return hashedCredentialsMatcher;
//    }
//
//
//    /*@Bean
//    public SimpleCookie rememberMeCookie(){
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
//        simpleCookie.setMaxAge(259200);
//        return simpleCookie;
//    }
//
//    *//**
//    * cookie管理对象;
//    * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
//    * @return
//    *//*
//    @Bean
//    public CookieRememberMeManager rememberMeManager(){
//       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//       cookieRememberMeManager.setCookie(rememberMeCookie());
//       //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//       cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
//       return cookieRememberMeManager;
//    }*/
//}

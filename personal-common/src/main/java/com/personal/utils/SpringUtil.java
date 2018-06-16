package com.personal.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author  daila
 * @date  2017/12/28.
 */
public class SpringUtil {
    public static WebApplicationContext getWebApplicationContext(){
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        return wac;
    }


    public static Object getBean(String name){
        return getWebApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> cls){
        return getWebApplicationContext().getBean(cls);
    }
}

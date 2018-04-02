package com.sys.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 帮助类
 *
 * @auther sunchao
 * @create 2018/4/2
 */


public class ParamUtil {
    /**
     * 把request参数转化为Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> paramMap(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<>();
        Enumeration<String> names = request.getParameterNames();
        for (Enumeration<String> e = names; e.hasMoreElements(); ) {
            String thisName = e.nextElement();
            String thisValue = request.getParameter(thisName);
            param.put(thisName, thisValue);
        }
        return param;
    }

}

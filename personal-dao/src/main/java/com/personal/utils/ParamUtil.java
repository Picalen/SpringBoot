package com.personal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ycv on 2017/11/21.
 * 获取请求参数
 */
public class ParamUtil {
    public final static Logger logger = LoggerFactory.getLogger(ParamUtil.class);

    /**
     * 把request参数转化为Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> paramMap(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        Enumeration<String> names = request.getParameterNames();
        for (Enumeration<String> e = names; e.hasMoreElements(); ) {
            String thisName = e.nextElement();
            String thisValue = request.getParameter(thisName);
            param.put(thisName, thisValue);
        }
        return param;
    }
}

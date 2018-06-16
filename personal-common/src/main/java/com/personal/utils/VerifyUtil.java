package com.personal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by daila on 2017/12/12.
 */
public class VerifyUtil {
    static Logger logger = LoggerFactory.getLogger(VerifyUtil.class);
    /**
     * 验证是否所有参数都不为空，有任意参数为空则返回true；
     * @param params
     * @return
     */
    public static boolean isNull(Map<String, Object> params,String ...names) {
        if(params ==null){
            return true;
        }
        for(String key:names){
            Object value = params.get(key);
            if(value==null ||"".equals(value.toString().trim())){
                logger.info("缺少参数："+key);
                return true;
            }
        }
        return false;
    }

    /**
     * 验证字符串是否可转数字，不能转返回false
     *
     * @param params
     * @return
     */
    public static boolean isNumber(String... params) {
        for (String param : params) {
            try {
                Double.parseDouble(param);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

}

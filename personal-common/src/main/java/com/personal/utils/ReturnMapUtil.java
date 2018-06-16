package com.personal.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 包装返回信息
 * @Author: dmc
 * @Date: Create in 下午6:47 18/1/15
 * @Version
 */
public class ReturnMapUtil {

    private static Map<String,Object> ERROR = new HashMap<>();

    public static Map<String , Object> returnErrorMap(){
        Map<String ,Object> map = new HashMap<>();
        map.put("flag",false);
        map.put("info","操作失败");
        map.put("data",ERROR);
        return map;
    }

    public static Map<String,Object> returnSuccessMap(Object object){
        Map<String,Object> map = new HashMap<>();
        map.put("flag",true);
        map.put("info","操作成功");
        map.put("data",object);
        return map;
    }
}

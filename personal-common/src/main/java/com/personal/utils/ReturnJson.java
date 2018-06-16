package com.personal.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author ycv
 * @description
 * @date 14:25 2018/1/12
 */
public class ReturnJson {

    //成功时返回
    /*public static JSONObject toReturnJson(Object object){
        return ReturnJson.toReturnJson(true,"操作成功",object);
    }*/

    public static JSONObject toReturnJson(boolean flag , String info, Object object){
        JSONObject json = new JSONObject();
        if (object instanceof JSONObject){
            json.put("data",object);
        }else if(object instanceof Map){
            JSONObject data = JSONObject.fromObject(object);
            json.put("data",data);
        }
        json.put("flag", flag);
        json.put("info", info);
        return json;
    }

}

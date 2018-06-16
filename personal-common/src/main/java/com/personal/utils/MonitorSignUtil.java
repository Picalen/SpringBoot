package com.personal.utils;

import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by daila on 2017/12/13.
 */
public class MonitorSignUtil {
    private static final Logger logger = Logger.getLogger(MonitorSignUtil.class);

    /**
     * 添加签名信息
     * @param sendParams
     * @return
     * @throws Exception
     */
    public static String addSigninfo(Map<String,Object> sendParams) throws Exception{
        //添加签名信息
        String sign = MonitorSignUtil.getSignByMap(sendParams);
        return sign;
    }
    public static String getSignByMap(Map<String, Object> map) {
        //排序
        LinkedHashMap<String, Object> signMap = new LinkedHashMap<String, Object>();
        signMap.putAll(map);

        //拼接属性值
        String str = "";
        for (Map.Entry<String, Object> entry : signMap.entrySet()) {
            //忽略null值
            Object v = entry.getValue();
            if(null==v || "".equals(v)){
                //统一置为""
                map.put(entry.getKey(), "");
                continue;
            }
            str +=v.toString().trim()+"|";
        }

        str = str.substring(0, str.length()-1);
        //进行加密
        logger.info("响应消息待加密数据:" + str);
        String certSign = MonitorSignUtil.getSignString(str);
        return certSign;
    }

    /**
     * 签名串加密，不可逆，先用MD5加密
     * @param pass 需要机密的字符串
     * @return 加密后的字符串sign
     */
    public static String getSignString(String pass){
        //先把字符串用MD5加密
        String sign = MD5.MD5Encode(pass,"UTF-8");
        return sign ;
    }
}

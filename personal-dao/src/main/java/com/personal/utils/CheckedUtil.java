package com.personal.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ycv
 * @description
 * @date 16:34 2018/1/16
 */
public class CheckedUtil {
    /**
     * 手机校验格式
     */
    private static final String checkPhone = "^[1][3,4,5,7,8][0-9]{9}$";
    /**
     * 邮箱校验格式
     */
    private static final String checkEmail = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 校验邮箱格式
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile(checkEmail);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 校验手机格式
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile(checkPhone);
            Matcher matcher = regex.matcher(phone);
            flag = matcher.matches();
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }
}

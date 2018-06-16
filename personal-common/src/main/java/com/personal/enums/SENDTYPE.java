package com.personal.enums;

/**
 * 发送密码方式
 * @author ycv
 * @description
 * @date 13:24 2017/01/04
 * 0 手机  1 邮箱
 */
public enum SENDTYPE {
    /**
     *  短信
     */
    SMS("0") ,
    /**
     *  邮件
     */
    EMAIL("1") ;

    private String code;

    private SENDTYPE(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }

}

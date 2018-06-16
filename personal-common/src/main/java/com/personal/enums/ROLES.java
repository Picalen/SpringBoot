package com.personal.enums;

/**
 * 主要角色信息
 * @author ycv
 * @description
 * @date 13:24 2017/12/29
 * 1 超级管理员 5 行方负责人
 */
public enum ROLES {
    ADMIN("1") , BANKMASTER("5");
    private String code;
    private ROLES(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }

}

package com.personal.enums;

/**
 * @author ycv
 * @description 0 行方 1 部门
 * @date 18:04 2017/12/26
 */
public enum DEPARTTYPE {
    Bank("0") , Company("1") ,BankDepart("2");
    private String code;

    private DEPARTTYPE(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}

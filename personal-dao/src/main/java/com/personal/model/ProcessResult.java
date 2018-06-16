package com.personal.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by ycv on 2017/11/24
 * 记录业务层处理结果
 */
public class ProcessResult implements Serializable{
    //返回码
    private String recode;
    //返回描述
    private String remsg;
    private String status;

    public ProcessResult(){

    }
    public ProcessResult(String recode,String remsg){
        this.recode = recode;
        this.remsg = remsg;
    }

    public String getRecode() {
        return recode;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public String getRemsg() {
        return remsg;
    }

    public void setRemsg(String remsg) {
        this.remsg = remsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

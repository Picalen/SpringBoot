package com.personal.model;

import java.io.Serializable;

/**
 * 数据层级model
 * @author ycv
 * @description
 * @date 10:22 2017/12/29
 */
public class Tree implements Serializable{
    /**
     * 唯一识别id
     */
    private String id;
    /**
     * 父节点id
     */
    private String pid;
    /**
     * 名称
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

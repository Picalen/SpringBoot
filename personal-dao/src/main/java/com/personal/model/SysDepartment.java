package com.personal.model;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Add bu ycv 2017-11-23
 部门类
 */
public class SysDepartment implements Serializable {
  private BigInteger departId;
  private String departName;
  private BigInteger parentId;
    //描述
  private String departDesc;
  private BigInteger createUser;
  private String createTime;
  private BigInteger updateUser;
  private String updateTime;

    public BigInteger getDepartId() {
        return departId;
    }

    public void setDepartId(BigInteger departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getDepartDesc() {
        return departDesc;
    }

    public void setDepartDesc(String departDesc) {
        this.departDesc = departDesc;
    }

    public BigInteger getCreateUser() {
        return createUser;
    }

    public void setCreateUser(BigInteger createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigInteger getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(BigInteger updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
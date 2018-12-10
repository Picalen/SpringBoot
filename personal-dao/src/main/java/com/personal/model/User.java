package com.personal.model;

import java.io.Serializable;

/**
 * @author sc
 * @date 用户测试类$
 * @description $
 */
public class User implements Serializable {
    private Integer id;

    private String name;

    private Integer age;

    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", detail='" + detail + '\'' +
                '}';
    }
}

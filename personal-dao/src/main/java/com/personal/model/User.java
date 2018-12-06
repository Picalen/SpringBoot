package com.personal.model;

import java.io.Serializable;

/**
 * @author sc
 * @date 用户测试类$
 * @description $
 */
public class User implements Serializable {

    private String name;

    private int age;

    private String detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

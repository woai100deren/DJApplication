package com.dj.mvvm.bean;

/**
 * Created by wangjing4 on 2017/8/28.
 */

public class StudentBean {
    private String name;
    private String nickName;

    public StudentBean(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }

    public String getNickName() {

        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

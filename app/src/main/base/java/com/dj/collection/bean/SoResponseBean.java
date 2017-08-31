package com.dj.collection.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by wangjing4 on 2017/8/24.
 */

public class SoResponseBean implements Serializable{
    @JSONField(name = "error")
    private String error;
    @JSONField(name = "data")
    private SoBean soBean;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public SoBean getSoBean() {
        return soBean;
    }

    public void setSoBean(SoBean soBean) {
        this.soBean = soBean;
    }

    @Override
    public String toString() {
        return "SoResponseBean : " +
                "error = "+error+","+
                "data = {"+
                "md5 = "+soBean.getMd5()+","+
                "url = "+soBean.getUrl()+"}";
    }
}

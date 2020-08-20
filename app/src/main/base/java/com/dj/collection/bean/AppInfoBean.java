package com.dj.collection.bean;

import java.io.Serializable;

public class AppInfoBean implements Serializable {
    //返回状态值
    private int result;
    //版本号
    private long code;
    //版本名称
    private String version;
    //下载地址
    private String url;
    //文件大小
    private long file_size;
    //是否强制升级：0，否，1：是
    private int compel;
    //升级内容描述
    private String context;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public int getCompel() {
        return compel;
    }

    public void setCompel(int compel) {
        this.compel = compel;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "result=" + result +
                ", code=" + code +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", file_size=" + file_size +
                ", compel=" + compel +
                ", context='" + context + '\'' +
                '}';
    }
}

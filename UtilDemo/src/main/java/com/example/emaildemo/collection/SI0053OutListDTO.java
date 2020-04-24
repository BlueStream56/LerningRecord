package com.example.emaildemo.collection;

import java.io.Serializable;

public class SI0053OutListDTO implements Serializable {

    private static final long serialVersionUID = 6727688817952076533L;

    private String aac999;//个人管理码
    private Long aae030;//开始年月
    private Long aae031;//终止年月
    private Double aae180;//月缴费基数
    private String aae078;//是否到账
    private String aab069;//缴费单位名称

    public String getAac999() {
        return aac999;
    }

    public void setAac999(String aac999) {
        this.aac999 = aac999;
    }

    public Long getAae030() {
        return aae030;
    }

    public void setAae030(Long aae030) {
        this.aae030 = aae030;
    }

    public Long getAae031() {
        return aae031;
    }

    public void setAae031(Long aae031) {
        this.aae031 = aae031;
    }

    public Double getAae180() {
        return aae180;
    }

    public void setAae180(Double aae180) {
        this.aae180 = aae180;
    }

    public String getAae078() {
        return aae078;
    }

    public void setAae078(String aae078) {
        this.aae078 = aae078;
    }

    public String getAab069() {
        return aab069;
    }

    public void setAab069(String aab069) {
        this.aab069 = aab069;
    }

    @Override
    public String toString() {
        return "SI0053OutListDTO{" +
                "aac999='" + aac999 + '\'' +
                ", aae030=" + aae030 +
                ", aae031=" + aae031 +
                ", aae180=" + aae180 +
                ", aae078='" + aae078 + '\'' +
                ", aab069='" + aab069 + '\'' +
                '}';
    }
}

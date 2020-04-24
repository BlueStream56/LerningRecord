package com.example.emaildemo.collection;

import java.io.Serializable;

public class SI0053InDTO implements Serializable {

    private static final long serialVersionUID = 6727688817952076533L;

    private Long aae003;//费款所属期
    private Long aae001;//年份
    private Double aae180;//缴费基数
    private String aab999;//单位编号
    private String aab069;//单位名称
    private String aab001;//单位编号
    private String aac999;
    private String aac001;
    private String aae140;

    public String getAae140() {
        return aae140;
    }

    public void setAae140(String aae140) {
        this.aae140 = aae140;
    }

    public String getAac001() {
        return aac001;
    }

    public void setAac001(String aac001) {
        this.aac001 = aac001;
    }

    public String getAac999() {
        return aac999;
    }

    public void setAac999(String aac999) {
        this.aac999 = aac999;
    }

    public String getAab001() {
        return aab001;
    }

    public void setAab001(String aab001) {
        this.aab001 = aab001;
    }

    public Long getAae003() {
        return aae003;
    }

    public void setAae003(Long aae003) {
        this.aae003 = aae003;
    }

    public Long getAae001() {
        return Long.valueOf(aae003.toString().substring(0,4));
    }

    public void setAae001(Long aae001) {
        this.aae001 = aae001;
    }

    public Double getAae180() {
        return aae180;
    }

    public void setAae180(Double aae180) {
        this.aae180 = aae180;
    }

    public String getAab999() {
        return aab999;
    }

    public void setAab999(String aab999) {
        this.aab999 = aab999;
    }

    public String getAab069() {
        return aab069;
    }

    public void setAab069(String aab069) {
        this.aab069 = aab069;
    }

    private String aac008;

    public String getAac008() {
        return aac008;
    }

    public void setAac008(String aac008) {
        this.aac008 = aac008;
    }
}

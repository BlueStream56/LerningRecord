package com.example.emaildemo.collection;

/**
 * <ul>
 * <li>文件名称：InDTO </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/20 </li>
 * </ul>
 *
 * <ul>
 * <li>修改记录：</li>
 * <li>版 本 号：</li>
 * <li>修改日期：</li>
 * <li>修 改 人：</li>
 * <li>修改内容：</li>
 * </ul>
 *
 * @author caoxx
 * @version 1.0.0
 */
public class InDTO {

    private String aac999;//个人编号
    private Long aae003;//费款所属期
    private Long aae001;//年份
    private Double aae180;//缴费基数
    private String aab999;//单位编号
    private String aab069;//单位名称

    public Long getAae001() {
        return Long.valueOf(aae003.toString().substring(0,4));
    }

    public void setAae001(Long aae001) {
        this.aae001 = aae001;
    }

    public String getAac999() {
        return aac999;
    }

    public void setAac999(String aac999) {
        this.aac999 = aac999;
    }

    public Long getAae003() {
        return aae003;
    }

    public void setAae003(Long aae003) {
        this.aae003 = aae003;
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

    @Override
    public String toString() {
        return "InDTO{" +
                "aac999='" + aac999 + '\'' +
                ", aae003=" + aae003 +
                ", aae001=" + aae001 +
                ", aae180=" + aae180 +
                ", aab999='" + aab999 + '\'' +
                ", aab069='" + aab069 + '\'' +
                '}';
    }
}

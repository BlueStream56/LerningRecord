package com.example.emaildemo.util.escard.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * <ul>
 * <li>文件名称: ReqInDTO </li>
 * <li>文件描述: </li>
 * <li>版权所有: 版权所有(C) 2019</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>创建日期:  2020/6/17</li>
 * </ul>
 * <ul>
 * <li>修改记录: </li>
 * <li>版 本 号: </li>
 * <li>修改日期: </li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author hongqn
 * @version 1.0.0
 */
public class ReqInDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JSONField(ordinal = 1, name = "reqTime")
    private String reqTime; //部平台请求时间 String 20  Y yyyy-MM-dd HH:mm:ss
    @JSONField(ordinal = 1, name = "reqSnum")
    private String reqSnum; //系统请求流水号 String 100  Y 系统请求流水号标注一次请求，第二次发起的请 求，其流水号不 一致
    @JSONField(ordinal = 1, name = "encrypt")
    private String encrypt; //加密数据 String 2048  Y 对请求业务报文加密，得到密文串
    @JSONField(ordinal = 1, name = "sign")
    private String sign; //签名数据 String 2048  Y 对加密密文串签名后的签名数据

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getReqSnum() {
        return reqSnum;
    }

    public void setReqSnum(String reqSnum) {
        this.reqSnum = reqSnum;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

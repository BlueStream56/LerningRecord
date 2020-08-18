package com.example.emaildemo.util.escard.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * <ul>
 * <li>文件名称: Mspp9010001InDTO.java</li>
 * <li>文件描述: </li>
 * <li>版权所有: 版权所有(C) 2018</li>
 * <li>公   司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>创建日期: 2020年06月17日 10时00分</li>
 * <li>编辑工具: IntelliJ IDEA</li>
 * </ul>
 * <ul>
 * <li>修改记录: </li>
 * <li>版 本 号: </li>
 * <li>修改日期: </li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author hujb
 * @version 1.0.0
 */
public class Mspp9010001InDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    @JSONField(name = "uniformOrderId")
    private String uniformOrderId;   // 部平台订单号 String 36 Y
    @JSONField(name = "akb020")
    private String akb020;   // 定点医疗机构编码 String 22 Y
    @JSONField(name = "esscNo")
    private String esscNo;   // 电子社保卡卡号 String 32 Y
    @JSONField(name = "aac002")
    private String aac002;   // 社会保障号码 String 18 Y
    @JSONField(name = "preBalanceId")
    private String preBalanceId;   // 预结算ID String 36 Y
    @JSONField(name = "akc264")
    private Double akc264;   // 交易总金额 double (12,2) Y
    @JSONField(name = "ake149")
    private Double ake149;   // 医保支付金额 double (12,2) Y
    @JSONField(name = "ake039")
    private Double ake039;   // 医保统筹支付金额 double (12,2) Y
    @JSONField(name = "ake173")
    private Double ake173;   // 其他基金支付金额 double (12,2) Y
    @JSONField(name = "akb066")
    private Double akb066;   // 医保个账支付金额 double (12,2) Y
    @JSONField(name = "ake201")
    private Double ake201;   // 现金支付金额 double (12,2) Y
    @JSONField(name = "merchantReserve")
    private String merchantReserve;   // 商户业务扩展域 String 1024 N 地方医保结算特殊数据项透传

    public String getUniformOrderId() {
        return uniformOrderId;
    }

    public void setUniformOrderId(String uniformOrderId) {
        this.uniformOrderId = uniformOrderId;
    }

    public String getAkb020() {
        return akb020;
    }

    public void setAkb020(String akb020) {
        this.akb020 = akb020;
    }

    public String getEsscNo() {
        return esscNo;
    }

    public void setEsscNo(String esscNo) {
        this.esscNo = esscNo;
    }

    public String getAac002() {
        return aac002;
    }

    public void setAac002(String aac002) {
        this.aac002 = aac002;
    }

    public String getPreBalanceId() {
        return preBalanceId;
    }

    public void setPreBalanceId(String preBalanceId) {
        this.preBalanceId = preBalanceId;
    }

    public Double getAkc264() {
        return akc264;
    }

    public void setAkc264(Double akc264) {
        this.akc264 = akc264;
    }

    public Double getAke149() {
        return ake149;
    }

    public void setAke149(Double ake149) {
        this.ake149 = ake149;
    }

    public Double getAke039() {
        return ake039;
    }

    public void setAke039(Double ake039) {
        this.ake039 = ake039;
    }

    public Double getAke173() {
        return ake173;
    }

    public void setAke173(Double ake173) {
        this.ake173 = ake173;
    }

    public Double getAkb066() {
        return akb066;
    }

    public void setAkb066(Double akb066) {
        this.akb066 = akb066;
    }

    public Double getAke201() {
        return ake201;
    }

    public void setAke201(Double ake201) {
        this.ake201 = ake201;
    }

    public String getMerchantReserve() {
        return merchantReserve;
    }

    public void setMerchantReserve(String merchantReserve) {
        this.merchantReserve = merchantReserve;
    }
}

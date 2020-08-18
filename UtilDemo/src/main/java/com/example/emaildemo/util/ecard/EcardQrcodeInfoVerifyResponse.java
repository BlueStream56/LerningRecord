/** 
 * @(#)EcardQrcodeInfoVerifyResponse.java 2018-5-4
 * 
 * Copyright (c) 1995-2100 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */
package com.example.emaildemo.util.ecard;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 二维码核验-应答参数
 * 
 * @author Fengsisi 2018-5-4
 */
public class EcardQrcodeInfoVerifyResponse {
	/**
	 * 姓名
	 */
	@JSONField(name = "name")
	private String name;
	
	/**
	 * 性别
	 */
	@JSONField(name = "gender")
	private String gender;
	
	/**
	 * 证件类型
	 */
	@JSONField(name = "id_type")
	private String idType;
	
	/**
	 * 证件号码
	 */
	@JSONField(name = "id_no")
	private String idNo;
	
	/**
	 * 社会保障号码
	 */
	@JSONField(name = "si_no")
	private String siNo;
	
	/**
	 * 社保卡号或人员识别号
	 */
	@JSONField(name = "si_card_no")
	private String siCardNo;
	
	/**
	 * 社保卡发卡地区行政区划代码
	 */
	@JSONField(name = "si_card_issue_area")
	private String siCardIssueArea;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the siNo
	 */
	public String getSiNo() {
		return siNo;
	}

	/**
	 * @param siNo the siNo to set
	 */
	public void setSiNo(String siNo) {
		this.siNo = siNo;
	}

	/**
	 * @return the siCardNo
	 */
	public String getSiCardNo() {
		return siCardNo;
	}

	/**
	 * @param siCardNo the siCardNo to set
	 */
	public void setSiCardNo(String siCardNo) {
		this.siCardNo = siCardNo;
	}

	/**
	 * @return the siCardIssueArea
	 */
	public String getSiCardIssueArea() {
		return siCardIssueArea;
	}

	/**
	 * @param siCardIssueArea the siCardIssueArea to set
	 */
	public void setSiCardIssueArea(String siCardIssueArea) {
		this.siCardIssueArea = siCardIssueArea;
	}
}

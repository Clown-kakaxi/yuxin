package com.yuchengtech.emp.ecif.customer.simplegroup.web.vo;

import java.io.Serializable;

public class CustProductVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 产品代码
	private String prodCode;
	// 产品名称
	private String prodName;
	// 产品类型
	private String prodType;
	// 产品简介
	private String prodSummary;
	// 品牌名称
	private String brandName;
	// 业务性质
	private String busiChar;
	// 产品分类
	private String prodClass;
	// 产品形态
	private String prodForm;
	// 产品状态
	private String prodStat;
	// 产品专利
	private String prodPatent;
	// 上线日期
	private String startDate;
	// 下线日期
	private String endDate;
	// 自主营销标识
	private String ownSaleFlag;
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getProdSummary() {
		return prodSummary;
	}
	public void setProdSummary(String prodSummary) {
		this.prodSummary = prodSummary;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBusiChar() {
		return busiChar;
	}
	public void setBusiChar(String busiChar) {
		this.busiChar = busiChar;
	}
	public String getProdClass() {
		return prodClass;
	}
	public void setProdClass(String prodClass) {
		this.prodClass = prodClass;
	}
	public String getProdForm() {
		return prodForm;
	}
	public void setProdForm(String prodForm) {
		this.prodForm = prodForm;
	}
	public String getProdStat() {
		return prodStat;
	}
	public void setProdStat(String prodStat) {
		this.prodStat = prodStat;
	}
	public String getProdPatent() {
		return prodPatent;
	}
	public void setProdPatent(String prodPatent) {
		this.prodPatent = prodPatent;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOwnSaleFlag() {
		return ownSaleFlag;
	}
	public void setOwnSaleFlag(String ownSaleFlag) {
		this.ownSaleFlag = ownSaleFlag;
	}

}

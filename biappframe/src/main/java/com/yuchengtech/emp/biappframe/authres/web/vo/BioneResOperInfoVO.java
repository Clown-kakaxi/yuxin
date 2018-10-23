package com.yuchengtech.emp.biappframe.authres.web.vo;

import java.util.ArrayList;
import java.util.List;

public class BioneResOperInfoVO {
	private String operId;
	private String visitUrl;
	private String methodName;
	private String operName;
	private String operNo;
	private String remark;
	private String resNo;
	private String resDefNo;
	private String upNo;
	private List<BioneResOperInfoVO> children;
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getVisitUrl() {
		return visitUrl;
	}
	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getResDefNo() {
		return resDefNo;
	}
	public void setResDefNo(String resDefNo) {
		this.resDefNo = resDefNo;
	}
	public String getUpNo() {
		return upNo;
	}
	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}
	public List<BioneResOperInfoVO> getChildren() {
		if(children == null){
			children = new ArrayList<BioneResOperInfoVO>();
		}
		return children;
	}
	public void setChildren(List<BioneResOperInfoVO> children) {
		this.children = children;
	}
	
}

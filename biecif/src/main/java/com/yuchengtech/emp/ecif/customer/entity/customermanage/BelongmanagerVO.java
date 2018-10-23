package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the BELONGMANAGER database table.
 * 
 */
public class BelongmanagerVO {

	private Long belongManagerId;

	private String adminManagerNo;
	
	private String adminManagerName;
	
	private String belongType;

	private String mainType;

	private Long custId;

	private String empcode1;

	private String empcode2;

	private String empname1;

	private String empname2;

    public BelongmanagerVO() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getBelongManagerId() {
		return this.belongManagerId;
	}

	public void setBelongManagerId(Long belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	public String getAdminManagerName() {
		return adminManagerName;
	}

	public void setAdminManagerName(String adminManagerName) {
		this.adminManagerName = adminManagerName;
	}

	public String getAdminManagerNo() {
		return this.adminManagerNo;
	}

	public void setAdminManagerNo(String adminManagerNo) {
		this.adminManagerNo = adminManagerNo;
	}

	public String getBelongType() {
		return this.belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEmpcode1() {
		return this.empcode1;
	}

	public void setEmpcode1(String empcode1) {
		this.empcode1 = empcode1;
	}

	public String getEmpcode2() {
		return this.empcode2;
	}

	public void setEmpcode2(String empcode2) {
		this.empcode2 = empcode2;
	}

	public String getEmpname1() {
		return empname1;
	}

	public void setEmpname1(String empname1) {
		this.empname1 = empname1;
	}

	public String getEmpname2() {
		return empname2;
	}

	public void setEmpname2(String empname2) {
		this.empname2 = empname2;
	}

}
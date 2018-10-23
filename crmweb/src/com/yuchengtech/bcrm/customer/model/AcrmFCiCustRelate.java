package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the ACRM_F_CI_CUST_RELATE database table.
 * 
 */
@Entity
@Table(name = "ACRM_F_CI_CUST_RELATE")
public class AcrmFCiCustRelate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ACRM_F_CI_CUST_RELATE_ID_GENERATOR", sequenceName = "ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACRM_F_CI_CUST_RELATE_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREAT_DATE")
	private Date creatDate;

	@Column(name = "CREAT_ORG")
	private String creatOrg;

	@Column(name = "CREAT_PERSON")
	private String creatPerson;

	@Column(name = "CUST_ID")
	private String custId;

	@Column(name = "CUST_NAME")
	private String custName;

	@Column(name = "CUST_NAME_R")
	private String custNameR;

	@Column(name = "CUST_NO_R")
	private String custNoR;

	@Column(name = "R_DESC")
	private String rDesc;

	@Column(name = "R_TYPE")
	private String rType;

	private String relationship;

	@Column(name = "UPDATE_PERSON")
	private String updatePerson;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	@Transient
	private String tel;// 关联客户对应的电话
	@Transient
	private String mobile;// 关联客户对应的手机
	@Transient
	private String membername;// 关联客户对应的名字
	@Transient
	private String coreNo;// 关联客户对应的核心号
	@Transient
	private Long fId;// 新增的ID作为主键

	public Long getFId() {
		return fId;
	}

	public void setFId(Long fId) {
		this.fId = fId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getCoreNo() {
		return coreNo;
	}

	public void setCoreNo(String coreNo) {
		this.coreNo = coreNo;
	}

	public AcrmFCiCustRelate() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getCreatOrg() {
		return this.creatOrg;
	}

	public void setCreatOrg(String creatOrg) {
		this.creatOrg = creatOrg;
	}

	public String getCreatPerson() {
		return this.creatPerson;
	}

	public void setCreatPerson(String creatPerson) {
		this.creatPerson = creatPerson;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustNameR() {
		return this.custNameR;
	}

	public void setCustNameR(String custNameR) {
		this.custNameR = custNameR;
	}

	public String getCustNoR() {
		return this.custNoR;
	}

	public void setCustNoR(String custNoR) {
		this.custNoR = custNoR;
	}

	public String getRDesc() {
		return this.rDesc;
	}

	public void setRDesc(String rDesc) {
		this.rDesc = rDesc;
	}

	public String getRType() {
		return this.rType;
	}

	public void setRType(String rType) {
		this.rType = rType;
	}

	public String getRelationship() {
		return this.relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getUpdatePerson() {
		return this.updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
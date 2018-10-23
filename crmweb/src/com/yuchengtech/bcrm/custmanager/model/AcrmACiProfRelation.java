package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import java.sql.Timestamp;
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


/**
 * The persistent class for the ACRM_A_CI_PROF_RELATION database table.
 * 
 */
@Entity
@Table(name="ACRM_A_CI_PROF_RELATION")
public class AcrmACiProfRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_A_CI_PROF_RELATION_ID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_A_CI_PROF_RELATION_ID_GENERATOR" )
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="CREAT_DATE")
	private Date creatDate;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_NAME_R")
	private String custNameR;

	@Column(name="CUST_NO_R")
	private String custNoR;

	@Column(name="R_DESC")
	private String rDesc;

	@Column(name="R_TYPE")
	private String rType;

	private String relationship;
	
	@Column(name="R_STATE")
	private String rState;
	
	@Column(name="CREATE_USER_ID")
	private String createUserId;
	
	@Column(name="CREATE_USER_NAME")
	private String createUserName;
	
	@Column(name="CREATE_TIMES")
	private String createTimes;
	
	@Column(name="OPERATE_TIME")
	private Timestamp operateTime;

	public AcrmACiProfRelation() {
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

	public String getRState() {
		return rState;
	}

	public void setRState(String rState) {
		this.rState = rState;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}

	public Timestamp getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}
	
	

}
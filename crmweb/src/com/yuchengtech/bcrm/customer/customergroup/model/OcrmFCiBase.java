package com.yuchengtech.bcrm.customer.customergroup.model;


import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OCRM_F_CI_BASE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_BASE")
public class OcrmFCiBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_BASE_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_BASE_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="CUST_BASE_CREATE_DATE")
	private Date custBaseCreateDate;

	@Column(name="CUST_BASE_CREATE_NAME")
	private String custBaseCreateName;

	@Column(name="CUST_BASE_CREATE_ORG")
	private String custBaseCreateOrg;

	@Column(name="CUST_BASE_DESC")
	private String custBaseDesc;

	@Column(name="CUST_BASE_MEMBER_NUM")
	private BigDecimal custBaseMemberNum;

	@Column(name="CUST_BASE_NAME")
	private String custBaseName;

	@Column(name="CUST_BASE_NUMBER")
	private String custBaseNumber;

	@Column(name="CUST_FROM")
	private String custFrom;

	@Column(name="CUST_FROM_NAME")
	private String custFromName;

	@Column(name="GROUP_MEMBER_TYPE")
	private String groupMemberType;

	@Column(name="GROUP_TYPE")
	private String groupType;

    @Temporal( TemporalType.DATE)
	@Column(name="RECENT_UPDATE_DATE")
	private Date recentUpdateDate;

	@Column(name="RECENT_UPDATE_ORG")
	private String recentUpdateOrg;

	@Column(name="RECENT_UPDATE_USER")
	private String recentUpdateUser;

	@Column(name="SHARE_FLAG")
	private String shareFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCustBaseCreateDate() {
		return custBaseCreateDate;
	}

	public void setCustBaseCreateDate(Date custBaseCreateDate) {
		this.custBaseCreateDate = custBaseCreateDate;
	}

	public String getCustBaseCreateName() {
		return custBaseCreateName;
	}

	public void setCustBaseCreateName(String custBaseCreateName) {
		this.custBaseCreateName = custBaseCreateName;
	}

	public String getCustBaseCreateOrg() {
		return custBaseCreateOrg;
	}

	public void setCustBaseCreateOrg(String custBaseCreateOrg) {
		this.custBaseCreateOrg = custBaseCreateOrg;
	}

	public String getCustBaseDesc() {
		return custBaseDesc;
	}

	public void setCustBaseDesc(String custBaseDesc) {
		this.custBaseDesc = custBaseDesc;
	}

	public BigDecimal getCustBaseMemberNum() {
		return custBaseMemberNum;
	}

	public void setCustBaseMemberNum(BigDecimal custBaseMemberNum) {
		this.custBaseMemberNum = custBaseMemberNum;
	}

	public String getCustBaseName() {
		return custBaseName;
	}

	public void setCustBaseName(String custBaseName) {
		this.custBaseName = custBaseName;
	}

	public String getCustBaseNumber() {
		return custBaseNumber;
	}

	public void setCustBaseNumber(String custBaseNumber) {
		this.custBaseNumber = custBaseNumber;
	}

	public String getCustFrom() {
		return custFrom;
	}

	public void setCustFrom(String custFrom) {
		this.custFrom = custFrom;
	}

	public String getCustFromName() {
		return custFromName;
	}

	public void setCustFromName(String custFromName) {
		this.custFromName = custFromName;
	}

	public String getGroupMemberType() {
		return groupMemberType;
	}

	public void setGroupMemberType(String groupMemberType) {
		this.groupMemberType = groupMemberType;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Date getRecentUpdateDate() {
		return recentUpdateDate;
	}

	public void setRecentUpdateDate(Date recentUpdateDate) {
		this.recentUpdateDate = recentUpdateDate;
	}

	public String getRecentUpdateOrg() {
		return recentUpdateOrg;
	}

	public void setRecentUpdateOrg(String recentUpdateOrg) {
		this.recentUpdateOrg = recentUpdateOrg;
	}

	public String getRecentUpdateUser() {
		return recentUpdateUser;
	}

	public void setRecentUpdateUser(String recentUpdateUser) {
		this.recentUpdateUser = recentUpdateUser;
	}

	public String getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(String shareFlag) {
		this.shareFlag = shareFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
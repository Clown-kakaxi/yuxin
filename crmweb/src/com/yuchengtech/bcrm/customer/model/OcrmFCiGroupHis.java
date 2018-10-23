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


/**
 * The persistent class for the OCRM_F_CI_GROUP_HIS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GROUP_HIS")
public class OcrmFCiGroupHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GROUP_HIS_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GROUP_HIS_ID_GENERATOR")
	private Long id;

	@Column(name="GAO_NEW")
	private String gaoNew;

	@Column(name="GAO_OLD")
	private String gaoOld;

	@Column(name="GROUP_ID")
	private String groupId;

	@Column(name="GROUP_NAME")
	private String groupName;

    @Temporal( TemporalType.DATE)
	@Column(name="TRANS_DATE")
	private Date transDate;

	@Column(name="TRANS_REASON")
	private String transReason;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="GAO_ORG_OLD")
	private String gaoOrgOld;
	
	@Column(name="GAO_ORG_NEW")
	private String gaoOrgNew;
    public OcrmFCiGroupHis() {
    }

	public String getGaoOrgOld() {
		return gaoOrgOld;
	}

	public void setGaoOrgOld(String gaoOrgOld) {
		this.gaoOrgOld = gaoOrgOld;
	}

	public String getGaoOrgNew() {
		return gaoOrgNew;
	}

	public void setGaoOrgNew(String gaoOrgNew) {
		this.gaoOrgNew = gaoOrgNew;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGaoNew() {
		return this.gaoNew;
	}

	public void setGaoNew(String gaoNew) {
		this.gaoNew = gaoNew;
	}

	public String getGaoOld() {
		return this.gaoOld;
	}

	public void setGaoOld(String gaoOld) {
		this.gaoOld = gaoOld;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getTransDate() {
		return this.transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getTransReason() {
		return this.transReason;
	}

	public void setTransReason(String transReason) {
		this.transReason = transReason;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
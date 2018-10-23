package com.yuchengtech.bcrm.custview.model;

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
 * The persistent class for the OCRM_F_CI_ORG_PRJ_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ORG_PRJ_INFO")
public class OcrmFCiOrgPrjInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_ORG_PRJ_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_ORG_PRJ_INFO_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="CREAT_DATE")
	private Date creatDate;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_NAME")
	private String orgName;

	@Column(name="PRJ_NAME")
	private String prjName;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

    public OcrmFCiOrgPrjInfo() {
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

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPrjName() {
		return this.prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
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
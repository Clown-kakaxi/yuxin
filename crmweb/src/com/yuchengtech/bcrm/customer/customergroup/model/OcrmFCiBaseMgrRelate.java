package com.yuchengtech.bcrm.customer.customergroup.model;


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
 * The persistent class for the OCRM_F_CI_BASE_MGR_RELATE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_BASE_MGR_RELATE")
public class OcrmFCiBaseMgrRelate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_BASE_MGR_RELATE_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_BASE_MGR_RELATE_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_BASE_ID")
	private Long custBaseId;

	@Column(name="CUST_MGR_ID")
	private String custMgrId;
	
	@Column(name="BELONG_ORG_ID")
	private String belongOrgId;

	@Column(name="MAIN_TYPE")
	private String mainType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getCustBaseId() {
		return custBaseId;
	}

	public void setCustBaseId(Long custBaseId) {
		this.custBaseId = custBaseId;
	}

	public String getCustMgrId() {
		return custMgrId;
	}

	public void setCustMgrId(String custMgrId) {
		this.custMgrId = custMgrId;
	}

	public String getBelongOrgId() {
		return belongOrgId;
	}

	public void setBelongOrgId(String belongOrgId) {
		this.belongOrgId = belongOrgId;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
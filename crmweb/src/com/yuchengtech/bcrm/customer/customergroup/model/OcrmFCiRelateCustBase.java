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
 * The persistent class for the OCRM_F_CI_RELATE_CUST_BASE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_RELATE_CUST_BASE")
public class OcrmFCiRelateCustBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_RELATE_CUST_BASE_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_RELATE_CUST_BASE_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="CRATE_DATE")
	private Date crateDate;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_BASE_ID")
	private Long custBaseId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_ZH_NAME")
	private String custZhName;

	@Column(name="BELONG_ORG_ID")
	private String belongOrgId;

	@Column(name="BELONG_ORG_NAME")
	private String belongOrgName;
	
	@Column(name="BELONG_CUST_MGR_ID")
	private String belongCustMgrId;
	
	@Column(name="BELONG_CUST_MGR_NAME")
	private String belongCustMgrName;
	
    public OcrmFCiRelateCustBase() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCrateDate() {
		return this.crateDate;
	}

	public void setCrateDate(Date crateDate) {
		this.crateDate = crateDate;
	}

	public String getCreateOrg() {
		return this.createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getCreateUser() {
		return this.createUser;
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

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustZhName() {
		return custZhName;
	}

	public void setCustZhName(String custZhName) {
		this.custZhName = custZhName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBelongOrgId() {
		return belongOrgId;
	}

	public void setBelongOrgId(String belongOrgId) {
		this.belongOrgId = belongOrgId;
	}

	public String getBelongOrgName() {
		return belongOrgName;
	}

	public void setBelongOrgName(String belongOrgName) {
		this.belongOrgName = belongOrgName;
	}

	public String getBelongCustMgrId() {
		return belongCustMgrId;
	}

	public void setBelongCustMgrId(String belongCustMgrId) {
		this.belongCustMgrId = belongCustMgrId;
	}

	public String getBelongCustMgrName() {
		return belongCustMgrName;
	}

	public void setBelongCustMgrName(String belongCustMgrName) {
		this.belongCustMgrName = belongCustMgrName;
	}
}
package com.yuchengtech.bcrm.model;

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
 * The persistent class for the OCRM_F_CI_ALIANCERVICE_RELA database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ALIANCERVICE_RELA")
public class OcrmFCiAliancerviceRela implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_CI_ALIANCERVICE_RELA_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_ALIANCERVICE_RELA_ID_GENERATOR")
	@Column(name = "ID",unique=true, nullable=false)
	private Long id;
	
	@Column(name="ADD_SERVICE_IDENTIFY")
	private String addServiceIdentify;

	@Column(name="ADD_SERVICE_NAME")
	private String addServiceName;

	@Column(name="ALIANCE_PROGRAM_ID")
	private String alianceProgramId;

	@Column(name="ALIANCE_PROGRAM_NAME")
	private String alianceProgramName;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_USER")
	private String createUser;

	

	@Column(name="RANGE_APPLY")
	private String rangeApply;

	private String remark;

	@Column(name="SERVICE_CONTENT")
	private String serviceContent;

	public OcrmFCiAliancerviceRela() {
	}

	public String getAddServiceIdentify() {
		return this.addServiceIdentify;
	}

	public void setAddServiceIdentify(String addServiceIdentify) {
		this.addServiceIdentify = addServiceIdentify;
	}

	public String getAddServiceName() {
		return this.addServiceName;
	}

	public void setAddServiceName(String addServiceName) {
		this.addServiceName = addServiceName;
	}

	public String getAlianceProgramId() {
		return this.alianceProgramId;
	}

	public void setAlianceProgramId(String alianceProgramId) {
		this.alianceProgramId = alianceProgramId;
	}

	public String getAlianceProgramName() {
		return this.alianceProgramName;
	}

	public void setAlianceProgramName(String alianceProgramName) {
		this.alianceProgramName = alianceProgramName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRangeApply() {
		return this.rangeApply;
	}

	public void setRangeApply(String rangeApply) {
		this.rangeApply = rangeApply;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServiceContent() {
		return this.serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

}
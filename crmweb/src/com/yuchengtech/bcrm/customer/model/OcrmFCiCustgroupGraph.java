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
 * The persistent class for the OCRM_F_CI_CUSTGROUP_GRAPH database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CUSTGROUP_GRAPH")
public class OcrmFCiCustgroupGraph implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUSTGROUP_GRAPH_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUSTGROUP_GRAPH_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ORG_ID")
	private String createOrgId;

	@Column(name="CREATE_ORG_NAME")
	private String createOrgName;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="CREATE_USER_NAME")
	private String createUserName;

	@Column(name="GRAPH_DESCRIBE")
	private String graphDescribe;

	@Column(name="GRAPH_NAME")
	private String graphName;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_ORG_ID")
	private String updateOrgId;

	@Column(name="UPDATE_ORG_NAME")
	private String updateOrgName;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="UPDATE_USER_NAME")
	private String updateUserName;

	public OcrmFCiCustgroupGraph() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateOrgId() {
		return this.createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getCreateOrgName() {
		return this.createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getGraphDescribe() {
		return this.graphDescribe;
	}

	public void setGraphDescribe(String graphDescribe) {
		this.graphDescribe = graphDescribe;
	}

	public String getGraphName() {
		return this.graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateOrgId() {
		return this.updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}

	public String getUpdateOrgName() {
		return this.updateOrgName;
	}

	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

}
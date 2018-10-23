package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the OCRM_F_CI_ACCOUNT_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ACCOUNT_INFO")
public class OcrmFCiAccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	//客户号+账号类型 --SEQ_ACCOUNT_INFO
	@Id
	@SequenceGenerator(name="OCRM_F_CI_ACCOUNT_INFO_ID_GENERATOR", sequenceName="SEQ_ACCOUNT_INFO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_ACCOUNT_INFO_ID_GENERATOR")
	@Column(name="ID")
	private String id;

	//客户号
	@Column(name="CUST_ID")
	private String cust_id;

	//账号类型
	@Column(name="ACT_TYPE")
	private String actType;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;
	
	@Column(name="UPDATE_ORG")
	private String updateOrg;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;
	
    public OcrmFCiAccountInfo() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public Timestamp getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Timestamp getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
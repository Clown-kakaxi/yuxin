package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the OCRM_F_SYS_ACCHK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SYS_ACCHK")
@NamedQuery(name="OcrmFSysAcchk.findAll", query="SELECT o FROM OcrmFSysAcchk o")
public class OcrmFSysAcchk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="CHK_TYPE")
	private String chkType;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Date createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="REL1")
	private String rel1;

	@Column(name="REL1_DESC")
	private String rel1Desc;
	
	@Column(name="REL2")
	private String rel2;

	@Column(name="REL2_DESC")
	private String rel2Desc;

	@Column(name="TYPE_DESC")
	private String typeDesc;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TM")
	private Date updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public OcrmFSysAcchk() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChkType() {
		return this.chkType;
	}

	public void setChkType(String chkType) {
		this.chkType = chkType;
	}

	public String getCreateOrg() {
		return this.createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public Date getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRel1() {
		return this.rel1;
	}

	public void setRel1(String rel1) {
		this.rel1 = rel1;
	}

	public String getRel1Desc() {
		return this.rel1Desc;
	}

	public void setRel1Desc(String rel1Desc) {
		this.rel1Desc = rel1Desc;
	}

	public String getRel2() {
		return this.rel2;
	}

	public void setRel2(String rel2) {
		this.rel2 = rel2;
	}

	public String getRel2Desc() {
		return this.rel2Desc;
	}

	public void setRel2Desc(String rel2Desc) {
		this.rel2Desc = rel2Desc;
	}

	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getUpdateOrg() {
		return this.updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Date getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
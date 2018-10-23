package com.yuchengtech.bcrm.system.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 账户信息表
 * @author Weilh
 * @since 2012-09-25
 */
@Entity
@Table(name="ADMIN_AUTH_ACCOUNT_ROLE")
public class AdminAuthAccountRole implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;//id

	@Column(name="ACCOUNT_ID")
	private Long accountId;//用户ID

	@Column(name="APP_ID")
	private String appId;//逻辑系统ID

	@Column(name="ROLE_ID")
	private Long roleId;//角色ID
	
	//add by liuming 20170217
	@Column(name="CREATE_USER")
	private String createUser;//创建人
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TM")
	private Date createTm;//创建时间
	
	@Column(name="UPDATE_USER")
	private String updateUser;//最近修改人
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TM")
	private Date updateTm;//最近修改时间

    public AdminAuthAccountRole() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}

	
}
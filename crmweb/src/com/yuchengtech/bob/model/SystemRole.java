package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色定义表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "OCRM_F_SM_SYS_ROLE")
public class SystemRole {
	
	//序列ID
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//角色ID
	@Column(name = "SYSTEM_ROLEID",length=100)
	private String roleid;
	
	//角色名称
	@Column(name = "SYSTEM_ROLENAME",length=100)
	private String rolename;
	
	//启用状态
	@Column(name = "IS_ENABLED")
	private Boolean isEnabled;
	
	//是否锁定角色(不允许删除)
	@Column(name = "IS_LOCKED")
	private Boolean isLocked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	
}

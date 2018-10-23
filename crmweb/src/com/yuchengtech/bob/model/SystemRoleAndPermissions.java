package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限与权限实例对应表
 * 
 * @author Administrator
 *
 */

@Entity
@Table(name = "OCRM_F_SM_SYS_ROLEANDPERS")
public class SystemRoleAndPermissions {
	
	//序列ID
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//角色ID
	@Column(name = "SYSTEM_ROLEID",length=100)
	private String roleid;
	
	//权限实例ID
	@Column(name = "SYSTEM_PERMISSIONSID",length=100)
	private String Permissionsid;

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

	public String getPermissionsid() {
		return Permissionsid;
	}

	public void setPermissionsid(String permissionsid) {
		Permissionsid = permissionsid;
	}

	
}

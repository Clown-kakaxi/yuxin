package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限明细表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "OCRM_F_SM_SYS_PERMISSIONS")
public class SystemPermissions {

	//序列ID
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)	
	private Long id;
	
	//权限ID
	@Column(name = "SYSTEM_PERMISSIONSID",length=100)
	private String permissionsid;

	//权限类别(跟ROLE对应)
	@Column(name = "SYSTEM_PERMISSIONSTYPE",length=100)
	private String permissionstype;

	//权限名称
	@Column(name = "SYSTEM_PERMISSIONSNAME",length=100)
	private String permissionsname;

	//权限动作
	@Column(name = "SYSTEM_PERMISSIONSACTION",length=100)	
	private String permissionsaction;

	//权限备注
	@Column(name = "SYSTEM_PERMISSIONSACMEMO",length=100)	
	private String permissionsmemo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionsid() {
		return permissionsid;
	}

	public void setPermissionsid(String permissionsid) {
		this.permissionsid = permissionsid;
	}

	public String getPermissionstype() {
		return permissionstype;
	}

	public void setPermissionstype(String permissionstype) {
		this.permissionstype = permissionstype;
	}

	public String getPermissionsname() {
		return permissionsname;
	}

	public void setPermissionsname(String permissionsname) {
		this.permissionsname = permissionsname;
	}

	public String getPermissionsaction() {
		return permissionsaction;
	}

	public void setPermissionsaction(String permissionsaction) {
		this.permissionsaction = permissionsaction;
	}

	public String getPermissionsmemo() {
		return permissionsmemo;
	}

	public void setPermissionsmemo(String permissionsmemo) {
		this.permissionsmemo = permissionsmemo;
	}
	
	
}

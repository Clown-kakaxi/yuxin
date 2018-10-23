package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户组与角色关联关系表
 * 
 * @author Administrator
 *
 */

@Entity
@Table(name = "OCRM_F_SM_SYS_ROLEANDGROUP")
public class SystemRoleAndGroup {

	//序列ID
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//角色ID
	@Column(name = "SYSTEM_ROLEID",length=100)
	private String roleid;
	//组ID
	@Column(name = "SYSTEM_GROUPID",length=100)
	private String gropuid;
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
	public String getGropuid() {
		return gropuid;
	}
	public void setGropuid(String gropuid) {
		this.gropuid = gropuid;
	}
	
	
	
}

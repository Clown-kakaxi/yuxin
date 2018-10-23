package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxInfoRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_INFO_ROLE")
public class TxInfoRole implements java.io.Serializable {

	// Fields

	private Long grantObjectId;
	private String roleCode;
	private String roleName;
	private String roleDesc;

	// Constructors

	/** default constructor */
	public TxInfoRole() {
	}

	/** full constructor */
	public TxInfoRole(String roleCode, String roleName, String roleDesc) {
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}

	// Property accessors
	@Id
		@Column(name = "GRANT_OBJECT_ID", unique = true, nullable = false)
	public Long getGrantObjectId() {
		return this.grantObjectId;
	}

	public void setGrantObjectId(Long grantObjectId) {
		this.grantObjectId = grantObjectId;
	}

	@Column(name = "ROLE_CODE", length = 20)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "ROLE_NAME", length = 40)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "ROLE_DESC")
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
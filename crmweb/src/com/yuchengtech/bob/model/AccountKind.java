package com.yuchengtech.bob.model;

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
 * AccountKind 账户类型表
 */
@Entity
@Table(name = "OCRM_SYS_ACCOUNT_KIND")
public class AccountKind implements Serializable {

    private static final long serialVersionUID = 9085552388675974157L;

    /** 账户类型ID */
    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(name = "KIND_ID",nullable = false)
	private Long kindId;
    
    /** 账户类型编码 */
    @Column(name = "KIND_CODE",nullable = false)
	private String kindCode;
    
    /** 账户类型名称 */
    @Column(name = "KIND_NAME", length = 100)
	private String kindName;
    
    /** 账户类型描述 */
    @Column(name = "KIND_DESC", length = 100)
	private String kindDesc;
	
	/** 创建日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/** 创建人ID */
	@Column(name = "EMPLOYEE_ID", length = 100)
	private String employeeId;

	/** 创建人名称 */
	@Column(name = "EMPLOYEE_NAME", length = 200)
	private String employeeName;
	
	/** 所属组织编码 */
	@Column(name = "ORG_NUM", length = 200)
	private String orgNum;
	
	/** 所属组织名称 */
	@Column(name = "USE_ORG", length = 200)
	private String useOrg;

	public Long getKindId() {
		return kindId;
	}

	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getKindDesc() {
		return kindDesc;
	}

	public void setKindDesc(String kindDesc) {
		this.kindDesc = kindDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

	public String getUseOrg() {
		return useOrg;
	}

	public void setUseOrg(String useOrg) {
		this.useOrg = useOrg;
	}
	
	
}

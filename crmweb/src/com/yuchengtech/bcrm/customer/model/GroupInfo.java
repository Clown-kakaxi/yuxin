package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the OCRM_F_CI_GROUP_INFO database table. 
 * 集团信息表
 */
@Entity
@Table(name = "OCRM_F_CI_GROUP_INFO") 
public class GroupInfo implements Serializable {
	
	private static final long serialVersionUID = 606233992148883364L;

	/** ID */
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;
	
	/** 集团编号 */
	@Column(name = "GROUP_NO", length = 500)
	private String groupNo;

	/** 集团名称 */
	@Column(name = "GROUP_NAME", length = 500)
	private String groupName;

	/** 集团类型: 0 - 全行性, 1 - 区域性 */
	@Column(name = "GROUP_TYPE", length = 500)
	private String groupType;
	
	/** 授信主办行 */
	@Column(name = "GROUP_HOST_ORG_NO", length = 500)
	private String groupHostOrgNo;
	
	/** 集团状态: 0 - 停用, 1 - 启用 */
	@Column(name = "GROUP_STATUS", length = 500)
	private String groupStatus;
	
	/** 集团简介 */
	@Column(name = "GROUP_MEMO", length = 500)
	private String groupMemo;

	/** 创建人ID */
	@Column(name = "CREATE_USER_ID", length = 500)
	private String createUserId;

	/** 创建人姓名 */
	@Column(name = "CREATE_USER_NAME", length = 500)
	private String createUserName;
	
	/** 创建人所属机构Id */
	@Column(name = "CREATE_USER_ORG_ID", length = 500)
	private String createUserOrgId;
	
	/** 创建日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATA_DATE")
	private Date createDate;
	
	/** 更新人ID */
	@Column(name = "UPDATE_USER_ID", length = 500)
	private String updateUserId;
	
	/** 更新日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	/** 集团母公司CUSTID */
	@Column(name = "GROUP_ROOT_CUST_ID", length = 500)
	private String groupRootCustId;
	
	/** 集团母公司注册地址 */
	@Column(name = "GROUP_ROOT_ADDRESS", length = 500)
	private String groupRootAdress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupHostOrgNo() {
		return groupHostOrgNo;
	}

	public void setGroupHostOrgNo(String groupHostOrgNo) {
		this.groupHostOrgNo = groupHostOrgNo;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getGroupMemo() {
		return groupMemo;
	}

	public void setGroupMemo(String groupMemo) {
		this.groupMemo = groupMemo;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getGroupRootCustId() {
		return groupRootCustId;
	}

	public void setGroupRootCustId(String groupRootCustId) {
		this.groupRootCustId = groupRootCustId;
	}

	public String getGroupRootAdress() {
		return groupRootAdress;
	}

	public void setGroupRootAdress(String groupRootAdress) {
		this.groupRootAdress = groupRootAdress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserOrgId() {
		return createUserOrgId;
	}

	public void setCreateUserOrgId(String createUserOrgId) {
		this.createUserOrgId = createUserOrgId;
	}
}
















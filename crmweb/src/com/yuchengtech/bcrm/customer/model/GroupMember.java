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
 * The persistent class for the OCRM_F_CI_GROUP_MEMBER database table. 
 * 集团成员表
 */
@Entity
@Table(name = "OCRM_F_CI_GROUP_MEMBER") 
public class GroupMember implements Serializable {
	
	private static final long serialVersionUID = -2547301738054803509L;

	/** ID */
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;
	
	/** 集团客户编号 */
	@Column(name = "GROUP_NO", length = 500)
	private String groupNo;

	/** 集团客户名称 */
	@Column(name = "GROUP_NAME", length = 500)
	private String groupName;
	
	/** 上级企业ID */
	@Column(name = "PARENT_ID")
	private String parentId;
	
	/** 企业CUSTID */
	@Column(name = "CUST_ID", length = 500)
	private String custId;

	/** 与上级企业关系ID */
	@Column(name = "RELATION_ID", length = 500)
	private String relationId;
	
	/** 成员类型: 1 - 标准集团成员, 2 - 对外担保 */
	@Column(name = "MEMBER_TYPE", length = 500)
	private String memberType;
	
	/** 持股比例 */
	@Column(name = "STOCK_RATE", length = 500)
	private String stockRate;

	/** 备注 */
	@Column(name = "REMARK", length = 500)
	private String remark;
	
	/** 更新人ID */
	@Column(name = "UPDATE_USER_ID", length = 500)
	private String updateUserId;
	
	/** 更新日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	/** 审批状态: 0 - 拒绝, 1 - 暂存, 2 - 主办行待审批, 3 - 总行待审批, 4 - 审批通过 */
	@Column(name = "APP_STATUS", length = 500)
	private String appStatus;
	
	/** 审批日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "APP_DATE")
	private Date appDate;
	
	/** 提交日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SUBMIT_DATE")
	private Date submitDate;
	
	/** 主办行意见 */
	@Column(name = "HOST_OPINION", length = 500)
	private String hostOpinion;
	
	/** 授信行意见 */
	@Column(name = "CREDIT_OPINION", length = 500)
	private String creditOpinion;
	
	/** 提交人员工号 */
	@Column(name = "APP_USER_ID", length = 500)
	private String appUserId;
	
	/** 提交人姓名 */
	@Column(name = "APP_USER_NAME", length = 500)
	private String appUserName;
	
	/** 提交人员所属机构Id */
	@Column(name = "APP_USER_ORG_ID", length = 500)
	private String appUserOrgId;
	
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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getStockRate() {
		return stockRate;
	}

	public void setStockRate(String stockRate) {
		this.stockRate = stockRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getHostOpinion() {
		return hostOpinion;
	}

	public void setHostOpinion(String hostOpinion) {
		this.hostOpinion = hostOpinion;
	}

	public String getCreditOpinion() {
		return creditOpinion;
	}

	public void setCreditOpinion(String creditOpinion) {
		this.creditOpinion = creditOpinion;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public String getAppUserOrgId() {
		return appUserOrgId;
	}

	public void setAppUserOrgId(String appUserOrgId) {
		this.appUserOrgId = appUserOrgId;
	}
}
















package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the OCRM_F_CI_GROUP_MEMBER database table.
 * 
 * FDM层(基础数据层),归属于客户集团的二级主题，数据为对集团的信息
 */
@Entity
@Table(name = "OCRM_F_CI_GROUP_MEMBER")
public class ClientGroupMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1486678405604390537L;

	/** 成员id */
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "MEMBER_ID", nullable = false)
	private Long memberId;

	/** 提交申请时间 */
	@Temporal(TemporalType.DATE)
	@Column(name = "APP_DATE", nullable = false)
	private Date applyDate;

	/** 审批状态 */
	@Column(name = "APP_STATUS", nullable = false, length = 20)
	private String applyStatus;

	/** 审批日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CHECK_DATE", nullable = false)
	private Date checkDate;

	/** 备注 */
	@Column(name = "memo", length = 500)
	private String memo;

	/** 主办机构 */
	@Column(name = "ORG_ID", nullable = false, length = 50)
	private String organizerId;

	/** 上级企业id */
	@Column(name = "PARENT_COM_ID", nullable = false)
	private Long parentCompanyId;

	/** 与上级企业关系id */
	@Column(name = "RELATION_ID", nullable = false, length = 20)
	private String relationId;

	/** 控股比例 */
	@Column(name = "SHARE_RATE", precision = 10, scale = 6)
	private BigDecimal shareRate;

	/** 更新日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE", nullable = false)
	private Date updateDate;

	/** 更新人id */
	@Column(name = "UPDATE_USER_ID", nullable = false, length = 50)
	private String updateUserId;

	// /** 集团客户编号 */
	// @Column(name = "GROUP_ID", nullable = false, length = 50)
	// private String groupId;
	//
	// /** 企业名称 */
	// @Column(name = "CUST_ID", nullable = false, length = 50)
	// private String custId;

	@OneToOne
	@JoinColumn(name = "CUST_ID", updatable = false, insertable = false)
	private ClientGroupDepositLoan ocrmFCiGroupDepLn;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID", updatable = false, insertable = false)
	private ClientGroupInfo ocrmFCiGroupInfo;

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRelationId() {
		return this.relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public BigDecimal getShareRate() {
		return this.shareRate;
	}

	public void setShareRate(BigDecimal shareRate) {
		this.shareRate = shareRate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setOrganizerId(String organizerId) {
		this.organizerId = organizerId;
	}

	public String getOrganizerId() {
		return organizerId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setParentCompanyId(Long parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}

	public Long getParentCompanyId() {
		return parentCompanyId;
	}

	public void setOcrmFCiGroupDepLn(ClientGroupDepositLoan ocrmFCiGroupDepLn) {
		this.ocrmFCiGroupDepLn = ocrmFCiGroupDepLn;
	}

	public ClientGroupDepositLoan getOcrmFCiGroupDepLn() {
		return ocrmFCiGroupDepLn;
	}

	public void setOcrmFCiGroupInfo(ClientGroupInfo ocrmFCiGroupInfo) {
		this.ocrmFCiGroupInfo = ocrmFCiGroupInfo;
	}

	public ClientGroupInfo getOcrmFCiGroupInfo() {
		return ocrmFCiGroupInfo;
	}

}
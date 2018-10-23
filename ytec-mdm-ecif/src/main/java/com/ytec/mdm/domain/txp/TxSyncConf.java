package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxSyncConf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_SYNC_CONF")
public class TxSyncConf implements java.io.Serializable {

	// Fields

	private Long syncConfId;
	private String txCode;
	private String srcSysNo;
	private String destSysNo;
	private String syncConfStat;
	private String syncConfDesc;
	private String syncMode;
	private String syncMethod;
	private String syncDealMethod;
	private String syncContentDef;
	private String syncContentDesc;
	private String syncDealClass;
	private String isRetry;
	private Long maxRetry;
	private String syncFailStrategy;
	private String createOper;
	private Timestamp createTime;
	private String updateOper;
	private Timestamp updateTime;
	private String approvalOper;
	private Timestamp approvalTime;
	private Timestamp effectiveTime;
	private Timestamp expiredTime;

	// Constructors

	/** default constructor */
	public TxSyncConf() {
	}

	/** full constructor */
	public TxSyncConf(String txCode, String srcSysNo, String destSysNo,
			String syncConfStat, String syncConfDesc, String syncMode,
			String syncMethod, String syncDealMethod, String syncContentDef,
			String syncContentDesc, String syncDealClass, String isRetry,
			Long maxRetry, String syncFailStrategy, String createOper,
			Timestamp createTime, String updateOper, Timestamp updateTime,
			String approvalOper, Timestamp approvalTime,
			Timestamp effectiveTime, Timestamp expiredTime) {
		this.txCode = txCode;
		this.srcSysNo = srcSysNo;
		this.destSysNo = destSysNo;
		this.syncConfStat = syncConfStat;
		this.syncConfDesc = syncConfDesc;
		this.syncMode = syncMode;
		this.syncMethod = syncMethod;
		this.syncDealMethod = syncDealMethod;
		this.syncContentDef = syncContentDef;
		this.syncContentDesc = syncContentDesc;
		this.syncDealClass = syncDealClass;
		this.isRetry = isRetry;
		this.maxRetry = maxRetry;
		this.syncFailStrategy = syncFailStrategy;
		this.createOper = createOper;
		this.createTime = createTime;
		this.updateOper = updateOper;
		this.updateTime = updateTime;
		this.approvalOper = approvalOper;
		this.approvalTime = approvalTime;
		this.effectiveTime = effectiveTime;
		this.expiredTime = expiredTime;
	}

	// Property accessors
	@Id
		@Column(name = "SYNC_CONF_ID", unique = true, nullable = false)
	public Long getSyncConfId() {
		return this.syncConfId;
	}

	public void setSyncConfId(Long syncConfId) {
		this.syncConfId = syncConfId;
	}

	@Column(name = "TX_CODE", length = 32)
	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	@Column(name = "SRC_SYS_NO", length = 20)
	public String getSrcSysNo() {
		return this.srcSysNo;
	}

	public void setSrcSysNo(String srcSysNo) {
		this.srcSysNo = srcSysNo;
	}

	@Column(name = "DEST_SYS_NO", length = 20)
	public String getDestSysNo() {
		return this.destSysNo;
	}

	public void setDestSysNo(String destSysNo) {
		this.destSysNo = destSysNo;
	}

	@Column(name = "SYNC_CONF_STAT", length = 20)
	public String getSyncConfStat() {
		return this.syncConfStat;
	}

	public void setSyncConfStat(String syncConfStat) {
		this.syncConfStat = syncConfStat;
	}

	@Column(name = "SYNC_CONF_DESC")
	public String getSyncConfDesc() {
		return this.syncConfDesc;
	}

	public void setSyncConfDesc(String syncConfDesc) {
		this.syncConfDesc = syncConfDesc;
	}

	@Column(name = "SYNC_MODE", length = 20)
	public String getSyncMode() {
		return this.syncMode;
	}

	public void setSyncMode(String syncMode) {
		this.syncMode = syncMode;
	}

	@Column(name = "SYNC_METHOD", length = 20)
	public String getSyncMethod() {
		return this.syncMethod;
	}

	public void setSyncMethod(String syncMethod) {
		this.syncMethod = syncMethod;
	}

	@Column(name = "SYNC_DEAL_METHOD", length = 20)
	public String getSyncDealMethod() {
		return this.syncDealMethod;
	}

	public void setSyncDealMethod(String syncDealMethod) {
		this.syncDealMethod = syncDealMethod;
	}

	@Column(name = "SYNC_CONTENT_DEF")
	public String getSyncContentDef() {
		return this.syncContentDef;
	}

	public void setSyncContentDef(String syncContentDef) {
		this.syncContentDef = syncContentDef;
	}

	@Column(name = "SYNC_CONTENT_DESC")
	public String getSyncContentDesc() {
		return this.syncContentDesc;
	}

	public void setSyncContentDesc(String syncContentDesc) {
		this.syncContentDesc = syncContentDesc;
	}

	@Column(name = "SYNC_DEAL_CLASS")
	public String getSyncDealClass() {
		return this.syncDealClass;
	}

	public void setSyncDealClass(String syncDealClass) {
		this.syncDealClass = syncDealClass;
	}

	@Column(name = "IS_RETRY", length = 20)
	public String getIsRetry() {
		return this.isRetry;
	}

	public void setIsRetry(String isRetry) {
		this.isRetry = isRetry;
	}

	@Column(name = "MAX_RETRY", precision = 22)
	public Long getMaxRetry() {
		return this.maxRetry;
	}

	public void setMaxRetry(Long maxRetry) {
		this.maxRetry = maxRetry;
	}

	@Column(name = "SYNC_FAIL_STRATEGY", length = 20)
	public String getSyncFailStrategy() {
		return this.syncFailStrategy;
	}

	public void setSyncFailStrategy(String syncFailStrategy) {
		this.syncFailStrategy = syncFailStrategy;
	}

	@Column(name = "CREATE_OPER", length = 20)
	public String getCreateOper() {
		return this.createOper;
	}

	public void setCreateOper(String createOper) {
		this.createOper = createOper;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_OPER", length = 20)
	public String getUpdateOper() {
		return this.updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	@Column(name = "UPDATE_TIME", length = 11)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "APPROVAL_OPER", length = 20)
	public String getApprovalOper() {
		return this.approvalOper;
	}

	public void setApprovalOper(String approvalOper) {
		this.approvalOper = approvalOper;
	}

	@Column(name = "APPROVAL_TIME", length = 11)
	public Timestamp getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}

	@Column(name = "EFFECTIVE_TIME", length = 11)
	public Timestamp getEffectiveTime() {
		return this.effectiveTime;
	}

	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	@Column(name = "EXPIRED_TIME", length = 11)
	public Timestamp getExpiredTime() {
		return this.expiredTime;
	}

	public void setExpiredTime(Timestamp expiredTime) {
		this.expiredTime = expiredTime;
	}

}
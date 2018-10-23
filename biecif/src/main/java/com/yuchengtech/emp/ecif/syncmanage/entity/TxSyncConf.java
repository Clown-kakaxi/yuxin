package com.yuchengtech.emp.ecif.syncmanage.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TX_SYNC_CONF database table.
 * 
 */
@Entity
@Table(name="TX_SYNC_CONF")
public class TxSyncConf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TX_SYNC_CONF_SYNCCONFID_GENERATOR", sequenceName="SEQ_TX_SYNC_CONF")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TX_SYNC_CONF_SYNCCONFID_GENERATOR")
	@Column(name="SYNC_CONF_ID")
	private Long syncConfId;

	@Column(name="APPROVAL_OPER")
	private String approvalOper;

	@Column(name="APPROVAL_TIME")
	private Timestamp approvalTime;

	@Column(name="CREATE_OPER")
	private String createOper;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="DEST_SYS_NO")
	private String destSysNo;

	@Column(name="EFFECTIVE_TIME")
	private Timestamp effectiveTime;

	@Column(name="EXPIRED_TIME")
	private Timestamp expiredTime;

	@Column(name="IS_RETRY")
	private String isRetry;

	@Column(name="MAX_RETRY")
	private BigDecimal maxRetry;

	@Column(name="SRC_SYS_NO")
	private String srcSysNo;

	@Column(name="SYNC_CONF_DESC")
	private String syncConfDesc;

	@Column(name="SYNC_CONF_STAT")
	private String syncConfStat;

	@Column(name="SYNC_CONTENT_DEF")
	private String syncContentDef;

	@Column(name="SYNC_CONTENT_DESC")
	private String syncContentDesc;

	@Column(name="SYNC_DEAL_CLASS")
	private String syncDealClass;

	@Column(name="SYNC_DEAL_METHOD")
	private String syncDealMethod;

	@Column(name="SYNC_FAIL_STRATEGY")
	private String syncFailStrategy;

	@Column(name="SYNC_METHOD")
	private String syncMethod;

	@Column(name="SYNC_MODE")
	private String syncMode;

	@Column(name="TX_CODE")
	private String txCode;

	@Column(name="UPDATE_OPER")
	private String updateOper;

	@Column(name="UPDATE_TIME")
	private Timestamp updateTime;

    public TxSyncConf() {
    }

	public Long getSyncConfId() {
		return this.syncConfId;
	}

	public void setSyncConfId(Long syncConfId) {
		this.syncConfId = syncConfId;
	}

	public String getApprovalOper() {
		return this.approvalOper;
	}

	public void setApprovalOper(String approvalOper) {
		this.approvalOper = approvalOper;
	}

	public Timestamp getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getCreateOper() {
		return this.createOper;
	}

	public void setCreateOper(String createOper) {
		this.createOper = createOper;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDestSysNo() {
		return this.destSysNo;
	}

	public void setDestSysNo(String destSysNo) {
		this.destSysNo = destSysNo;
	}

	public Timestamp getEffectiveTime() {
		return this.effectiveTime;
	}

	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Timestamp getExpiredTime() {
		return this.expiredTime;
	}

	public void setExpiredTime(Timestamp expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getIsRetry() {
		return this.isRetry;
	}

	public void setIsRetry(String isRetry) {
		this.isRetry = isRetry;
	}

	public BigDecimal getMaxRetry() {
		return this.maxRetry;
	}

	public void setMaxRetry(BigDecimal maxRetry) {
		this.maxRetry = maxRetry;
	}

	public String getSrcSysNo() {
		return this.srcSysNo;
	}

	public void setSrcSysNo(String srcSysNo) {
		this.srcSysNo = srcSysNo;
	}

	public String getSyncConfDesc() {
		return this.syncConfDesc;
	}

	public void setSyncConfDesc(String syncConfDesc) {
		this.syncConfDesc = syncConfDesc;
	}

	public String getSyncConfStat() {
		return this.syncConfStat;
	}

	public void setSyncConfStat(String syncConfStat) {
		this.syncConfStat = syncConfStat;
	}

	public String getSyncContentDef() {
		return this.syncContentDef;
	}

	public void setSyncContentDef(String syncContentDef) {
		this.syncContentDef = syncContentDef;
	}

	public String getSyncContentDesc() {
		return this.syncContentDesc;
	}

	public void setSyncContentDesc(String syncContentDesc) {
		this.syncContentDesc = syncContentDesc;
	}

	public String getSyncDealClass() {
		return this.syncDealClass;
	}

	public void setSyncDealClass(String syncDealClass) {
		this.syncDealClass = syncDealClass;
	}

	public String getSyncDealMethod() {
		return this.syncDealMethod;
	}

	public void setSyncDealMethod(String syncDealMethod) {
		this.syncDealMethod = syncDealMethod;
	}

	public String getSyncFailStrategy() {
		return this.syncFailStrategy;
	}

	public void setSyncFailStrategy(String syncFailStrategy) {
		this.syncFailStrategy = syncFailStrategy;
	}

	public String getSyncMethod() {
		return this.syncMethod;
	}

	public void setSyncMethod(String syncMethod) {
		this.syncMethod = syncMethod;
	}

	public String getSyncMode() {
		return this.syncMode;
	}

	public void setSyncMode(String syncMode) {
		this.syncMode = syncMode;
	}

	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getUpdateOper() {
		return this.updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
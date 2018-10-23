package com.yuchengtech.bcrm.callReport.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OCRM_F_SE_CALLREPORT_N database table.
 * 访谈结果（到期提醒）
 */
@Entity
@Table(name="OCRM_F_SE_CALLREPORT_N")
public class OcrmFSeCallreportN implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="ID",unique=true, nullable=false)
	private Long id;

	private BigDecimal amount;

	@Column(name="CALL_ID")
	private BigDecimal callId;

	@Column(name="CASE_STAGE")
	private String caseStage;

	@Column(name="CUST_ID")
	private String custId;
	
    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="FAIL_REASON")
	private String failReason;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="OUT_AMOUNT")
	private BigDecimal outAmount;

	@Column(name="PRODUCT_ID")
	private String productId;

	private String remark;

	@Column(name="SEQUEL_AMOUNT")
	private BigDecimal sequelAmount;

	@Column(name="SEQUEL_STAGE")
	private String sequelStage;

    public OcrmFSeCallreportN() {
    }

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCallId() {
		return this.callId;
	}

	public void setCallId(BigDecimal callId) {
		this.callId = callId;
	}

	public String getCaseStage() {
		return this.caseStage;
	}

	public void setCaseStage(String caseStage) {
		this.caseStage = caseStage;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFailReason() {
		return this.failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public BigDecimal getOutAmount() {
		return this.outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSequelAmount() {
		return this.sequelAmount;
	}

	public void setSequelAmount(BigDecimal sequelAmount) {
		this.sequelAmount = sequelAmount;
	}

	public String getSequelStage() {
		return this.sequelStage;
	}

	public void setSequelStage(String sequelStage) {
		this.sequelStage = sequelStage;
	}

}
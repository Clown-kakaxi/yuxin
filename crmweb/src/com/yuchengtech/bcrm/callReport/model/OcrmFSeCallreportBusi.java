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
 * The persistent class for the OCRM_F_SE_CALLREPORT_BUSI database table.
 * 访谈记录结果(商机)
 */
@Entity
@Table(name="OCRM_F_SE_CALLREPORT_BUSI")
public class OcrmFSeCallreportBusi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="ID",unique=true, nullable=false)
	private Long id;

	private BigDecimal amount;

	@Column(name="CALL_ID")
	private BigDecimal callId;

    @Temporal( TemporalType.DATE)
	@Column(name="ESTIMATED_TIME")
	private Date estimatedTime;

	@Column(name="FAIL_REASON")
	private String failReason;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="PRODUCT_ID")
	private String productId;

	@Column(name="SALES_STAGE")
	private String salesStage;
	
	@Column(name="BUSI_NAME")
	private String busiName;

    @Column(name="REMARK")
	private String remark;

    public OcrmFSeCallreportBusi() {
    }

	public String getBusiName() {
		return busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
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

	public Date getEstimatedTime() {
		return this.estimatedTime;
	}

	public void setEstimatedTime(Date estimatedTime) {
		this.estimatedTime = estimatedTime;
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

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSalesStage() {
		return this.salesStage;
	}

	public void setSalesStage(String salesStage) {
		this.salesStage = salesStage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
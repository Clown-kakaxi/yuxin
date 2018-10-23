package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_CHANNEL_ANALYSIS database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CHANNEL_ANALYSIS")
public class AcrmFCiChannelAnalysi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_CHANNEL_ANALYSIS_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_CHANNEL_ANALYSIS_ID_GENERATOR")
	private String id;

	@Column(name="BUSI_TYPE")
	private String busiType;

	private String channel;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_TYPE")
	private String custType;

	@Column(name="DRAW_AMT")
	private BigDecimal drawAmt;

	@Column(name="DRAW_COUNT")
	private BigDecimal drawCount;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DT")
	private Date endDt;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	private BigDecimal income;

	@Column(name="SAVE_AMT")
	private BigDecimal saveAmt;

	@Column(name="SAVE_COUNT")
	private BigDecimal saveCount;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DT")
	private Date startDt;

	@Column(name="TRAN_AMT")
	private BigDecimal tranAmt;

	@Column(name="TRANS_NUM")
	private BigDecimal transNum;

    public AcrmFCiChannelAnalysi() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusiType() {
		return this.busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public BigDecimal getDrawAmt() {
		return this.drawAmt;
	}

	public void setDrawAmt(BigDecimal drawAmt) {
		this.drawAmt = drawAmt;
	}

	public BigDecimal getDrawCount() {
		return this.drawCount;
	}

	public void setDrawCount(BigDecimal drawCount) {
		this.drawCount = drawCount;
	}

	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public BigDecimal getIncome() {
		return this.income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getSaveAmt() {
		return this.saveAmt;
	}

	public void setSaveAmt(BigDecimal saveAmt) {
		this.saveAmt = saveAmt;
	}

	public BigDecimal getSaveCount() {
		return this.saveCount;
	}

	public void setSaveCount(BigDecimal saveCount) {
		this.saveCount = saveCount;
	}

	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public BigDecimal getTranAmt() {
		return this.tranAmt;
	}

	public void setTranAmt(BigDecimal tranAmt) {
		this.tranAmt = tranAmt;
	}

	public BigDecimal getTransNum() {
		return this.transNum;
	}

	public void setTransNum(BigDecimal transNum) {
		this.transNum = transNum;
	}

}
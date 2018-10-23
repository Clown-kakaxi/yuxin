package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_CRE_CONTRACT_LMT_ST database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CRE_CONTRACT_LMT_ST")
public class AcrmFCiCreContractLmtSt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	@Column(name="DUE_BILL_NO")
	private String dueBillNo;

	@Column(name="LMT_STATUE")
	private String lmtStatue;

	@Column(name="OCCUR_AMT")
	private BigDecimal occurAmt;

	@Column(name="OCCUR_CURRENCY")
	private String occurCurrency;

	@Column(name="SUB_LIMIT_ID")
	private String subLimitId;

    public AcrmFCiCreContractLmtSt() {
    }

	public String getDueBillNo() {
		return this.dueBillNo;
	}

	public void setDueBillNo(String dueBillNo) {
		this.dueBillNo = dueBillNo;
	}

	public String getLmtStatue() {
		return this.lmtStatue;
	}

	public void setLmtStatue(String lmtStatue) {
		this.lmtStatue = lmtStatue;
	}

	public BigDecimal getOccurAmt() {
		return this.occurAmt;
	}

	public void setOccurAmt(BigDecimal occurAmt) {
		this.occurAmt = occurAmt;
	}

	public String getOccurCurrency() {
		return this.occurCurrency;
	}

	public void setOccurCurrency(String occurCurrency) {
		this.occurCurrency = occurCurrency;
	}

	public String getSubLimitId() {
		return this.subLimitId;
	}

	public void setSubLimitId(String subLimitId) {
		this.subLimitId = subLimitId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
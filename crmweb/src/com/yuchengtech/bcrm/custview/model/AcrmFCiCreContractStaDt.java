package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_CRE_CONTRACT_STA_DT database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CRE_CONTRACT_STA_DT")
public class AcrmFCiCreContractStaDt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	@Column(name="CONT_END_DT")
	private String contEndDt;

	@Column(name="CONT_START_DT")
	private String contStartDt;

	@Column(name="CREDIT_NO")
	private String creditNo;

	@Column(name="LIMIT_END_DT")
	private String limitEndDt;

	@Column(name="LIMIT_START_DT")
	private String limitStartDt;

	@Column(name="SUB_LIMIT")
	private BigDecimal subLimit;

	@Column(name="SUB_LIMIT_ID")
	private String subLimitId;

    public AcrmFCiCreContractStaDt() {
    }

	public String getContEndDt() {
		return this.contEndDt;
	}

	public void setContEndDt(String contEndDt) {
		this.contEndDt = contEndDt;
	}

	public String getContStartDt() {
		return this.contStartDt;
	}

	public void setContStartDt(String contStartDt) {
		this.contStartDt = contStartDt;
	}

	public String getCreditNo() {
		return this.creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getLimitEndDt() {
		return this.limitEndDt;
	}

	public void setLimitEndDt(String limitEndDt) {
		this.limitEndDt = limitEndDt;
	}

	public String getLimitStartDt() {
		return this.limitStartDt;
	}

	public void setLimitStartDt(String limitStartDt) {
		this.limitStartDt = limitStartDt;
	}

	public BigDecimal getSubLimit() {
		return this.subLimit;
	}

	public void setSubLimit(BigDecimal subLimit) {
		this.subLimit = subLimit;
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
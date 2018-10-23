package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_FIN_CUST_RISK_QA database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_CUST_RISK_QA")
public class OcrmFFinCustRiskQa implements Serializable {
	@Id
	@Column(name="CUST_Q_T_ID")
	private long custQTId;

	@Column(name="CUST_Q_ID")
	private BigDecimal custQId;

	@Column(name="CUST_SELECT_CONTENT")
	private String custSelectContent;

	@Column(name="QA_TITLE")
	private String qaTitle;

	private BigDecimal scoring;

	@Column(name="TITLE_REMARK")
	private String titleRemark;

    public OcrmFFinCustRiskQa() {
    }

	public long getCustQTId() {
		return this.custQTId;
	}

	public void setCustQTId(long custQTId) {
		this.custQTId = custQTId;
	}

	public BigDecimal getCustQId() {
		return this.custQId;
	}

	public void setCustQId(BigDecimal custQId) {
		this.custQId = custQId;
	}

	public String getCustSelectContent() {
		return this.custSelectContent;
	}

	public void setCustSelectContent(String custSelectContent) {
		this.custSelectContent = custSelectContent;
	}

	public String getQaTitle() {
		return this.qaTitle;
	}

	public void setQaTitle(String qaTitle) {
		this.qaTitle = qaTitle;
	}

	public BigDecimal getScoring() {
		return this.scoring;
	}

	public void setScoring(BigDecimal scoring) {
		this.scoring = scoring;
	}

	public String getTitleRemark() {
		return this.titleRemark;
	}

	public void setTitleRemark(String titleRemark) {
		this.titleRemark = titleRemark;
	}

}
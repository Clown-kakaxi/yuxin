package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiTaxinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_TAXINFO")
public class MCiTaxinfo implements java.io.Serializable {

	// Fields

	private String taxInfoId;
	private String custId;
	private String taxType;
	private Date taxDate;
	private Date startDate;
	private Date endDate;
	private String taxCurrency;
	private Double taxAmt;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiTaxinfo() {
	}

	/** minimal constructor */
	public MCiTaxinfo(String taxInfoId) {
		this.taxInfoId = taxInfoId;
	}

	/** full constructor */
	public MCiTaxinfo(String taxInfoId, String custId, String taxType,
			Date taxDate, Date startDate, Date endDate, String taxCurrency,
			Double taxAmt, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.taxInfoId = taxInfoId;
		this.custId = custId;
		this.taxType = taxType;
		this.taxDate = taxDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.taxCurrency = taxCurrency;
		this.taxAmt = taxAmt;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "TAX_INFO_ID", unique = true, nullable = false, length = 20)
	public String getTaxInfoId() {
		StackTraceElement stack_s[] = Thread.currentThread().getStackTrace();
		for (StackTraceElement stack : stack_s) {
			System.out.printf("File:%s, class:%s, method:%s,lineNumber:%s\n", stack.getFileName(), stack.getClassName(), stack.getMethodName(), stack.getLineNumber());
		}
		return this.taxInfoId;
	}

	public void setTaxInfoId(String taxInfoId) {
		this.taxInfoId = taxInfoId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "TAX_TYPE", length = 20)
	public String getTaxType() {
		return this.taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TAX_DATE", length = 7)
	public Date getTaxDate() {
		return this.taxDate;
	}

	public void setTaxDate(Date taxDate) {
		this.taxDate = taxDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "TAX_CURRENCY", length = 20)
	public String getTaxCurrency() {
		return this.taxCurrency;
	}

	public void setTaxCurrency(String taxCurrency) {
		this.taxCurrency = taxCurrency;
	}

	@Column(name = "TAX_AMT", precision = 17)
	public Double getTaxAmt() {
		return this.taxAmt;
	}

	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
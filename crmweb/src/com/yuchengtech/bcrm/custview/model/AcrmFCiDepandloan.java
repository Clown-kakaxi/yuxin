package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_DEPANDLOAN database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_DEPANDLOAN")
public class AcrmFCiDepandloan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_DEPANDLOAN_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_DEPANDLOAN_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="DEPO_LOAN")
	private BigDecimal depoLoan;

	@Column(name="DEPO_LOAN_LAST")
	private BigDecimal depoLoanLast;

	@Column(name="LAST_LOAN_AVE_YEAR")
	private BigDecimal lastLoanAveYear;

	@Column(name="LAST_SAVING_AVE_YEAR")
	private BigDecimal lastSavingAveYear;

	@Column(name="LOAN_AVE_YEAR")
	private BigDecimal loanAveYear;

	@Column(name="ODS_ST_DATE")
	private String odsStDate;

	@Column(name="SAVING_AVE_YEAR")
	private BigDecimal savingAveYear;
	
	@Column(name="SYCKNJS")
	private BigDecimal sycknjs;
	
	@Column(name="BNCKNJS")
	private BigDecimal bncknjs;
	
	@Column(name="SYDKNJS")
	private BigDecimal sydknjs;
	
	@Column(name="BNDKNJS")
	private BigDecimal bndknjs;
	
	@Column(name="SYNJCDB")
	private BigDecimal synjcdb;
	
	@Column(name="BNNJCDB")
	private BigDecimal bnnjcdb;
	
	@Column(name="SYMCDB")
	private BigDecimal symcdb;

	@Column(name="BNMCDB")
	private BigDecimal bnocdb;

    public AcrmFCiDepandloan() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getDepoLoan() {
		return this.depoLoan;
	}

	public void setDepoLoan(BigDecimal depoLoan) {
		this.depoLoan = depoLoan;
	}

	public BigDecimal getDepoLoanLast() {
		return this.depoLoanLast;
	}

	public void setDepoLoanLast(BigDecimal depoLoanLast) {
		this.depoLoanLast = depoLoanLast;
	}

	public BigDecimal getLastLoanAveYear() {
		return this.lastLoanAveYear;
	}

	public void setLastLoanAveYear(BigDecimal lastLoanAveYear) {
		this.lastLoanAveYear = lastLoanAveYear;
	}

	public BigDecimal getLastSavingAveYear() {
		return this.lastSavingAveYear;
	}

	public void setLastSavingAveYear(BigDecimal lastSavingAveYear) {
		this.lastSavingAveYear = lastSavingAveYear;
	}

	public BigDecimal getLoanAveYear() {
		return this.loanAveYear;
	}

	public void setLoanAveYear(BigDecimal loanAveYear) {
		this.loanAveYear = loanAveYear;
	}

	public String getOdsStDate() {
		return this.odsStDate;
	}

	public void setOdsStDate(String odsStDate) {
		this.odsStDate = odsStDate;
	}

	public BigDecimal getSavingAveYear() {
		return this.savingAveYear;
	}

	public void setSavingAveYear(BigDecimal savingAveYear) {
		this.savingAveYear = savingAveYear;
	}

	public BigDecimal getSycknjs() {
		return sycknjs;
	}

	public void setSycknjs(BigDecimal sycknjs) {
		this.sycknjs = sycknjs;
	}

	public BigDecimal getBncknjs() {
		return bncknjs;
	}

	public void setBncknjs(BigDecimal bncknjs) {
		this.bncknjs = bncknjs;
	}

	public BigDecimal getSydknjs() {
		return sydknjs;
	}

	public void setSydknjs(BigDecimal sydknjs) {
		this.sydknjs = sydknjs;
	}

	public BigDecimal getBndknjs() {
		return bndknjs;
	}

	public void setBndknjs(BigDecimal bndknjs) {
		this.bndknjs = bndknjs;
	}

	public BigDecimal getSynjcdb() {
		return synjcdb;
	}

	public void setSynjcdb(BigDecimal synjcdb) {
		this.synjcdb = synjcdb;
	}

	public BigDecimal getBnnjcdb() {
		return bnnjcdb;
	}

	public void setBnnjcdb(BigDecimal bnnjcdb) {
		this.bnnjcdb = bnnjcdb;
	}

	public BigDecimal getSymcdb() {
		return symcdb;
	}

	public void setSymcdb(BigDecimal symcdb) {
		this.symcdb = symcdb;
	}

	public BigDecimal getBnocdb() {
		return bnocdb;
	}

	public void setBnocdb(BigDecimal bnocdb) {
		this.bnocdb = bnocdb;
	}

}
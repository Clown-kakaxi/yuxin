package com.ytec.mdm.domain.biz;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Ocrm_F_Interview_Loanbank")
public class OcrmFInterviewLoanbank {
	@Id
	@Column(name = "Id")
	@SequenceGenerator(name = "Ocrm_F_Interview_Loanbank_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_Loanbank_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_number")
	private String taskNumber;

	@Column(name = "l_bankname")
	private String lBankname;

	@Column(name = "l_limittype")
	private String lLimittype;

	@Column(name = "l_limitmoney")
	private BigDecimal lLimitmoney;

	@Column(name = "l_balance")
	private BigDecimal lBalance;

	@Column(name = "l_rate")
	private BigDecimal lRate;

	@Column(name = "l_collateral")
	private String lCollateral;

	@Column(name = "l_memo")
	private String lMemo;
	
	@Column(name = "l_dbrate")
	private BigDecimal lDbrate;

	public OcrmFInterviewLoanbank() {
		super();
	}

	public OcrmFInterviewLoanbank(BigDecimal id, String taskNumber, String lBankname, String lLimittype, BigDecimal lLimitmoney, BigDecimal lBalance, BigDecimal lRate, String lCollateral, String lMemo,BigDecimal lDbrate) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.lBankname = lBankname;
		this.lLimittype = lLimittype;
		this.lLimitmoney = lLimitmoney;
		this.lBalance = lBalance;
		this.lRate = lRate;
		this.lCollateral = lCollateral;
		this.lMemo = lMemo;
		this.lDbrate = lDbrate;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getLBankname() {
		return lBankname;
	}

	public void setLBankname(String lBankname) {
		this.lBankname = lBankname;
	}

	public String getLLimittype() {
		return lLimittype;
	}

	public void setLLimittype(String lLimittype) {
		this.lLimittype = lLimittype;
	}

	public BigDecimal getLLimitmoney() {
		return lLimitmoney;
	}

	public void setLLimitmoney(BigDecimal lLimitmoney) {
		this.lLimitmoney = lLimitmoney;
	}

	public BigDecimal getLBalance() {
		return lBalance;
	}

	public void setLBalance(BigDecimal lBalance) {
		this.lBalance = lBalance;
	}

	public BigDecimal getLRate() {
		return lRate;
	}

	public void setLRate(BigDecimal lRate) {
		this.lRate = lRate;
	}

	public String getLCollateral() {
		return lCollateral;
	}

	public void setLCollateral(String lCollateral) {
		this.lCollateral = lCollateral;
	}

	public String getLMemo() {
		return lMemo;
	}

	public void setLMemo(String lMemo) {
		this.lMemo = lMemo;
	}

	public BigDecimal getLDbrate() {
		return lDbrate;
	}

	public void setLDbrate(BigDecimal lDbrate) {
		this.lDbrate = lDbrate;
	}
	
	
}

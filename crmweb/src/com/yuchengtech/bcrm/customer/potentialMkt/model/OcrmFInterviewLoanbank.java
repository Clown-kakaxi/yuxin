package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_LOANBANK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_LOANBANK")
public class OcrmFInterviewLoanbank implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_LOANBANK_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_LOANBANK_GENERATOR")
	private String id;

	@Column(name="L_BALANCE")
	private BigDecimal lBalance;

	@Column(name="L_BANKNAME")
	private String lBankname;

	@Column(name="L_COLLATERAL")
	private String lCollateral;

	@Column(name="L_LIMITMONEY")
	private BigDecimal lLimitmoney;

	@Column(name="L_LIMITTYPE")
	private String lLimittype;

	@Column(name="L_MEMO")
	private String lMemo;

	@Column(name="L_RATE")
	private BigDecimal lRate;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

	@Column(name="L_DBRATE")
	private BigDecimal lDbrate;
	
    public OcrmFInterviewLoanbank() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getLBalance() {
		return this.lBalance;
	}

	public void setLBalance(BigDecimal lBalance) {
		this.lBalance = lBalance;
	}

	public String getLBankname() {
		return this.lBankname;
	}

	public void setLBankname(String lBankname) {
		this.lBankname = lBankname;
	}

	public String getLCollateral() {
		return this.lCollateral;
	}

	public void setLCollateral(String lCollateral) {
		this.lCollateral = lCollateral;
	}

	public BigDecimal getLLimitmoney() {
		return this.lLimitmoney;
	}

	public void setLLimitmoney(BigDecimal lLimitmoney) {
		this.lLimitmoney = lLimitmoney;
	}

	public String getLLimittype() {
		return this.lLimittype;
	}

	public void setLLimittype(String lLimittype) {
		this.lLimittype = lLimittype;
	}

	public String getLMemo() {
		return this.lMemo;
	}

	public void setLMemo(String lMemo) {
		this.lMemo = lMemo;
	}

	public BigDecimal getLRate() {
		return this.lRate;
	}

	public void setLRate(BigDecimal lRate) {
		this.lRate = lRate;
	}
	
	public BigDecimal getLDbrate() {
		return this.lDbrate;
	}
	
	public void setLDbrate(BigDecimal lDbrate) {
		this.lDbrate = lDbrate;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}
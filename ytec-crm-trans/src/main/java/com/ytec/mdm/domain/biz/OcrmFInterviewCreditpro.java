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
@Table(name = "Ocrm_F_Interview_Creditpro")
public class OcrmFInterviewCreditpro {
	@Id
	@Column(name="Id")
	@SequenceGenerator(name = "Ocrm_F_Interview_Creditpro_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_Creditpro_ID_GENERATOR")
	private BigDecimal id;

	@Column(name="task_number")
	private String taskNumber;

	@Column(name="cp_use")
	private BigDecimal cpUse;

	@Column(name="cp_product")
	private BigDecimal cpProduct;

	@Column(name="cp_currency")
	private BigDecimal cpCurrency;

	@Column(name="cp_limitmoney")
	private BigDecimal cpLimitmoney;

	@Column(name="cp_memo")
	private String cpMemo;
	
	@Column(name="cp_collateral")
	private String cpCollateral;
	
	@Column(name="cp_dbrate")
	private BigDecimal cpDbrate;
	
	@Column(name="cp_product_p")
	private BigDecimal cpProductP;


	public OcrmFInterviewCreditpro() {
		super();
	}

	public OcrmFInterviewCreditpro(BigDecimal id, String taskNumber, BigDecimal cpUse, BigDecimal cpProduct, BigDecimal cpCurrency, BigDecimal cpLimitmoney, String cpMemo,String cpCollateral,BigDecimal cpDbrate,BigDecimal cpProductP) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.cpUse = cpUse;
		this.cpProduct = cpProduct;
		this.cpCurrency = cpCurrency;
		this.cpLimitmoney = cpLimitmoney;
		this.cpMemo = cpMemo;
		this.cpCollateral = cpCollateral;
		this.cpDbrate = cpDbrate;
		this.cpProductP = cpProductP;
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

	public BigDecimal getCpUse() {
		return cpUse;
	}

	public void setCpUse(BigDecimal cpUse) {
		this.cpUse = cpUse;
	}

	public BigDecimal getCpProduct() {
		return cpProduct;
	}

	public void setCpProduct(BigDecimal cpProduct) {
		this.cpProduct = cpProduct;
	}

	public BigDecimal getCpCurrency() {
		return cpCurrency;
	}

	public void setCpCurrency(BigDecimal cpCurrency) {
		this.cpCurrency = cpCurrency;
	}

	public BigDecimal getCpLimitmoney() {
		return cpLimitmoney;
	}

	public void setCpLimitmoney(BigDecimal cpLimitmoney) {
		this.cpLimitmoney = cpLimitmoney;
	}

	public String getCpMemo() {
		return cpMemo;
	}

	public void setCpMemo(String cpMemo) {
		this.cpMemo = cpMemo;
	}

	public String getCpCollateral() {
		return cpCollateral;
	}

	public void setCpCollateral(String cpCollateral) {
		this.cpCollateral = cpCollateral;
	}

	public BigDecimal getCpDbrate() {
		return cpDbrate;
	}

	public void setCpDbrate(BigDecimal cpDbrate) {
		this.cpDbrate = cpDbrate;
	}

	public BigDecimal getCpProductP() {
		return cpProductP;
	}

	public void setCpProductP(BigDecimal cpProductP) {
		this.cpProductP = cpProductP;
	}
	
	
}

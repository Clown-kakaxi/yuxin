package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_CREDITPRO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_CREDITPRO")
public class OcrmFInterviewCreditpro implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="OCRM_F_INTERVIEW_CREDITPRO_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_CREDITPRO_GENERATOR")
    private String id;
	
	@Column(name="CP_CURRENCY")
	private BigDecimal cpCurrency;

	@Column(name="CP_LIMITMONEY")
	private BigDecimal cpLimitmoney;

	@Column(name="CP_MEMO")
	private String cpMemo;

	@Column(name="CP_PRODUCT")
	private BigDecimal cpProduct;

	@Column(name="CP_USE")
	private BigDecimal cpUse;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

	@Column(name="CP_PRODUCT_P")
	private BigDecimal cpProductP;
	
	@Column(name="CP_COLLATERAL")
	private String cpCollateral;
	
	@Column(name="CP_DBRATE")
	private BigDecimal cpDbrate;
	
	@Column(name="CREDIT_PERIOD")
	private Long creditPeriod;
	
	@Column(name="REPAYMENT_METHOD")
	private String repaymentMethod;
	
    public OcrmFInterviewCreditpro() {
    }

	public BigDecimal getCpProductP() {
		return cpProductP;
	}

	public void setCpProductP(BigDecimal cpProductP) {
		this.cpProductP = cpProductP;
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

	public BigDecimal getCpCurrency() {
		return this.cpCurrency;
	}

	public void setCpCurrency(BigDecimal cpCurrency) {
		this.cpCurrency = cpCurrency;
	}

	public BigDecimal getCpLimitmoney() {
		return this.cpLimitmoney;
	}

	public void setCpLimitmoney(BigDecimal cpLimitmoney) {
		this.cpLimitmoney = cpLimitmoney;
	}

	public String getCpMemo() {
		return this.cpMemo;
	}

	public void setCpMemo(String cpMemo) {
		this.cpMemo = cpMemo;
	}

	public BigDecimal getCpProduct() {
		return this.cpProduct;
	}

	public void setCpProduct(BigDecimal cpProduct) {
		this.cpProduct = cpProduct;
	}

	public BigDecimal getCpUse() {
		return this.cpUse;
	}

	public void setCpUse(BigDecimal cpUse) {
		this.cpUse = cpUse;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Long getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(Long creditPeriod) {
		this.creditPeriod = creditPeriod;
	}

	public String getRepaymentMethod() {
		return repaymentMethod;
	}

	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}

}
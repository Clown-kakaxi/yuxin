package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_MATEPURCHASE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_MATEPURCHASE")
public class OcrmFInterviewMatepurchase implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_MATEPURCHASE_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_MATEPURCHASE_GENERATOR")
	private String id;

	@Column(name="MP_BALANCEDAYS")
	private BigDecimal mpBalancedays;

	@Column(name="MP_GOODS")
	private String mpGoods;

	@Column(name="MP_ISRELATE")
	private BigDecimal mpIsrelate;

	@Column(name="MP_MEMO")
	private String mpMemo;

	@Column(name="MP_MONTH2MONEY")
	private BigDecimal mpMonth2money;

	@Column(name="MP_PAYWAY")
	private BigDecimal mpPayway;

	@Column(name="MP_SUPPLIER")
	private String mpSupplier;

	@Column(name="MP_TRADEYEARS")
	private BigDecimal mpTradeyears;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

    public OcrmFInterviewMatepurchase() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getMpBalancedays() {
		return this.mpBalancedays;
	}

	public void setMpBalancedays(BigDecimal mpBalancedays) {
		this.mpBalancedays = mpBalancedays;
	}

	public String getMpGoods() {
		return this.mpGoods;
	}

	public void setMpGoods(String mpGoods) {
		this.mpGoods = mpGoods;
	}

	public BigDecimal getMpIsrelate() {
		return this.mpIsrelate;
	}

	public void setMpIsrelate(BigDecimal mpIsrelate) {
		this.mpIsrelate = mpIsrelate;
	}

	public String getMpMemo() {
		return this.mpMemo;
	}

	public void setMpMemo(String mpMemo) {
		this.mpMemo = mpMemo;
	}

	public BigDecimal getMpMonth2money() {
		return this.mpMonth2money;
	}

	public void setMpMonth2money(BigDecimal mpMonth2money) {
		this.mpMonth2money = mpMonth2money;
	}

	public BigDecimal getMpPayway() {
		return this.mpPayway;
	}

	public void setMpPayway(BigDecimal mpPayway) {
		this.mpPayway = mpPayway;
	}

	public String getMpSupplier() {
		return this.mpSupplier;
	}

	public void setMpSupplier(String mpSupplier) {
		this.mpSupplier = mpSupplier;
	}

	public BigDecimal getMpTradeyears() {
		return this.mpTradeyears;
	}

	public void setMpTradeyears(BigDecimal mpTradeyears) {
		this.mpTradeyears = mpTradeyears;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}
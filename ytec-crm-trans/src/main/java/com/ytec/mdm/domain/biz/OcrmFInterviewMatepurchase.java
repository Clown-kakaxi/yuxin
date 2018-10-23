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
@Table(name = "ocrm_f_interview_Matepurchase")
public class OcrmFInterviewMatepurchase {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "ocrm_f_interview_Matepurchase_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ocrm_f_interview_Matepurchase_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_number")
	private String taskNumber;

	@Column(name = "mp_goods")
	private String mpGoods;

	@Column(name = "mp_supplier")
	private String mpSupplier;

	@Column(name = "mp_isrelate")
	private BigDecimal mpIsrelate;

	@Column(name = "mp_month2money")
	private BigDecimal mpMonth2money;

	@Column(name = "mp_balancedays")
	private BigDecimal mpBalancedays;

	@Column(name = "mp_tradeyears")
	private BigDecimal mpTradeyears;

	@Column(name = "mp_payway")
	private BigDecimal mpPayway;

	@Column(name = "mp_memo")
	private String mpMemo;

	public OcrmFInterviewMatepurchase() {
		super();
	}

	public OcrmFInterviewMatepurchase(BigDecimal id, String taskNumber, String mpGoods, String mpSupplier, BigDecimal mpIsrelate, BigDecimal mpMonth2money, BigDecimal mpBalancedays,
			BigDecimal mpTradeyears, BigDecimal mpPayway, String mpMemo) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.mpGoods = mpGoods;
		this.mpSupplier = mpSupplier;
		this.mpIsrelate = mpIsrelate;
		this.mpMonth2money = mpMonth2money;
		this.mpBalancedays = mpBalancedays;
		this.mpTradeyears = mpTradeyears;
		this.mpPayway = mpPayway;
		this.mpMemo = mpMemo;
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

	public String getMpGoods() {
		return mpGoods;
	}

	public void setMpGoods(String mpGoods) {
		this.mpGoods = mpGoods;
	}

	public String getMpSupplier() {
		return mpSupplier;
	}

	public void setMpSupplier(String mpSupplier) {
		this.mpSupplier = mpSupplier;
	}

	public BigDecimal getMpIsrelate() {
		return mpIsrelate;
	}

	public void setMpIsrelate(BigDecimal mpIsrelate) {
		this.mpIsrelate = mpIsrelate;
	}

	public BigDecimal getMpMonth2money() {
		return mpMonth2money;
	}

	public void setMpMonth2money(BigDecimal mpMonth2money) {
		this.mpMonth2money = mpMonth2money;
	}

	public BigDecimal getMpBalancedays() {
		return mpBalancedays;
	}

	public void setMpBalancedays(BigDecimal mpBalancedays) {
		this.mpBalancedays = mpBalancedays;
	}

	public BigDecimal getMpTradeyears() {
		return mpTradeyears;
	}

	public void setMpTradeyears(BigDecimal mpTradeyears) {
		this.mpTradeyears = mpTradeyears;
	}

	public BigDecimal getMpPayway() {
		return mpPayway;
	}

	public void setMpPayway(BigDecimal mpPayway) {
		this.mpPayway = mpPayway;
	}

	public String getMpMemo() {
		return mpMemo;
	}

	public void setMpMemo(String mpMemo) {
		this.mpMemo = mpMemo;
	}
}

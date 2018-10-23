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
@Table(name = "Ocrm_F_Interview_Prosales")
public class OcrmFInterviewProsales {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "Ocrm_F_Interview_Prosales_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_Prosales_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_number")
	private String taskNumber;

	@Column(name = "ps_goods")
	private String psGoods;

	@Column(name = "ps_buyer")
	private String psBuyer;

	@Column(name = "ps_isrelate")
	private BigDecimal psIsrelate;

	@Column(name = "ps_month2money")
	private BigDecimal psMonth2money;

	@Column(name = "ps_balancedays")
	private BigDecimal psBalancedays;

	@Column(name = "ps_tradeyears")
	private BigDecimal psTradeyears;

	@Column(name = "ps_payway")
	private BigDecimal psPayway;

	@Column(name = "ps_memo")
	private String psMemo;

	public OcrmFInterviewProsales() {
		super();
	}

	public OcrmFInterviewProsales(BigDecimal id, String taskNumber, String psGoods, String psBuyer, BigDecimal psIsrelate, BigDecimal psMonth2money, BigDecimal psBalancedays, BigDecimal psTradeyears,
			BigDecimal psPayway, String psMemo) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.psGoods = psGoods;
		this.psBuyer = psBuyer;
		this.psIsrelate = psIsrelate;
		this.psMonth2money = psMonth2money;
		this.psBalancedays = psBalancedays;
		this.psTradeyears = psTradeyears;
		this.psPayway = psPayway;
		this.psMemo = psMemo;
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

	public String getPsGoods() {
		return psGoods;
	}

	public void setPsGoods(String psGoods) {
		this.psGoods = psGoods;
	}

	public String getPsBuyer() {
		return psBuyer;
	}

	public void setPsBuyer(String psBuyer) {
		this.psBuyer = psBuyer;
	}

	public BigDecimal getPsIsrelate() {
		return psIsrelate;
	}

	public void setPsIsrelate(BigDecimal psIsrelate) {
		this.psIsrelate = psIsrelate;
	}

	public BigDecimal getPsMonth2money() {
		return psMonth2money;
	}

	public void setPsMonth2money(BigDecimal psMonth2money) {
		this.psMonth2money = psMonth2money;
	}

	public BigDecimal getPsBalancedays() {
		return psBalancedays;
	}

	public void setPsBalancedays(BigDecimal psBalancedays) {
		this.psBalancedays = psBalancedays;
	}

	public BigDecimal getPsTradeyears() {
		return psTradeyears;
	}

	public void setPsTradeyears(BigDecimal psTradeyears) {
		this.psTradeyears = psTradeyears;
	}

	public BigDecimal getPsPayway() {
		return psPayway;
	}

	public void setPsPayway(BigDecimal psPayway) {
		this.psPayway = psPayway;
	}

	public String getPsMemo() {
		return psMemo;
	}

	public void setPsMemo(String psMemo) {
		this.psMemo = psMemo;
	}
}

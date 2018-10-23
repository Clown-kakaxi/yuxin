package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_PROSALES database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_PROSALES")
public class OcrmFInterviewProsale implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_PROSALES_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_PROSALES_GENERATOR")
	private String id;

	@Column(name="PS_BALANCEDAYS")
	private BigDecimal psBalancedays;

	@Column(name="PS_BUYER")
	private String psBuyer;

	@Column(name="PS_GOODS")
	private String psGoods;

	@Column(name="PS_ISRELATE")
	private BigDecimal psIsrelate;

	@Column(name="PS_MEMO")
	private String psMemo;

	@Column(name="PS_MONTH2MONEY")
	private BigDecimal psMonth2money;

	@Column(name="PS_PAYWAY")
	private BigDecimal psPayway;

	@Column(name="PS_TRADEYEARS")
	private BigDecimal psTradeyears;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

    public OcrmFInterviewProsale() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getPsBalancedays() {
		return this.psBalancedays;
	}

	public void setPsBalancedays(BigDecimal psBalancedays) {
		this.psBalancedays = psBalancedays;
	}

	public String getPsBuyer() {
		return this.psBuyer;
	}

	public void setPsBuyer(String psBuyer) {
		this.psBuyer = psBuyer;
	}

	public String getPsGoods() {
		return this.psGoods;
	}

	public void setPsGoods(String psGoods) {
		this.psGoods = psGoods;
	}

	public BigDecimal getPsIsrelate() {
		return this.psIsrelate;
	}

	public void setPsIsrelate(BigDecimal psIsrelate) {
		this.psIsrelate = psIsrelate;
	}

	public String getPsMemo() {
		return this.psMemo;
	}

	public void setPsMemo(String psMemo) {
		this.psMemo = psMemo;
	}

	public BigDecimal getPsMonth2money() {
		return this.psMonth2money;
	}

	public void setPsMonth2money(BigDecimal psMonth2money) {
		this.psMonth2money = psMonth2money;
	}

	public BigDecimal getPsPayway() {
		return this.psPayway;
	}

	public void setPsPayway(BigDecimal psPayway) {
		this.psPayway = psPayway;
	}

	public BigDecimal getPsTradeyears() {
		return this.psTradeyears;
	}

	public void setPsTradeyears(BigDecimal psTradeyears) {
		this.psTradeyears = psTradeyears;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}
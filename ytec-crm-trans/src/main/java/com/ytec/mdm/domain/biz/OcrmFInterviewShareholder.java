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
@Table(name = "Ocrm_F_Interview_Shareholder")
public class OcrmFInterviewShareholder {
	@Id
	@SequenceGenerator(name = "Ocrm_F_Interview_Shareholder_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_Shareholder_ID_GENERATOR")
	@Column(name = "ID")
	private BigDecimal id;

	@Column(name = "task_Number")
	private String taskNumber;

	@Column(name = "m_sponsor")
	private String mSponsor;

	@Column(name = "m_ratio")
	private BigDecimal mRatio;

	@Column(name = "m_money")
	private BigDecimal mMoney;

	public OcrmFInterviewShareholder() {
		super();
	}

	public OcrmFInterviewShareholder(BigDecimal id, String taskNumber, String mSponsor, BigDecimal mRatio, BigDecimal mMoney) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.mSponsor = mSponsor;
		this.mRatio = mRatio;
		this.mMoney = mMoney;
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

	public String getMSponsor() {
		return mSponsor;
	}

	public void setMSponsor(String mSponsor) {
		this.mSponsor = mSponsor;
	}

	public BigDecimal getMRatio() {
		return mRatio;
	}

	public void setMRatio(BigDecimal mRatio) {
		this.mRatio = mRatio;
	}

	public BigDecimal getMMoney() {
		return mMoney;
	}

	public void setMMoney(BigDecimal mMoney) {
		this.mMoney = mMoney;
	}

}

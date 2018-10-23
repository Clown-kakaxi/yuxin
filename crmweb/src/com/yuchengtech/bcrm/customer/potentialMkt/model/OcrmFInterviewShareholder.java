package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_SHAREHOLDER database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_SHAREHOLDER")
public class OcrmFInterviewShareholder implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_SHAREHOLDER_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_SHAREHOLDER_GENERATOR")
	private String id;

	@Column(name="M_MONEY")
	private BigDecimal mMoney;

	@Column(name="M_RATIO")
	private BigDecimal mRatio;

	@Column(name="M_SPONSOR")
	private String mSponsor;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

    public OcrmFInterviewShareholder() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getMMoney() {
		return this.mMoney;
	}

	public void setMMoney(BigDecimal mMoney) {
		this.mMoney = mMoney;
	}

	public BigDecimal getMRatio() {
		return this.mRatio;
	}

	public void setMRatio(BigDecimal mRatio) {
		this.mRatio = mRatio;
	}

	public String getMSponsor() {
		return this.mSponsor;
	}

	public void setMSponsor(String mSponsor) {
		this.mSponsor = mSponsor;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}
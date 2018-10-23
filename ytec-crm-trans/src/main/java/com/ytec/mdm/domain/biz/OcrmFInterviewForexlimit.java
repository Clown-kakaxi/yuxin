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
@Table(name = "ocrm_f_interview_Forexlimit")
public class OcrmFInterviewForexlimit {

	@Id
	@Column(name = "Id")
	@SequenceGenerator(name = "ocrm_f_interview_Forexlimit_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ocrm_f_interview_Forexlimit_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_number")
	private String taskNumber;

	@Column(name = "fl_name")
	private String flName;

	@Column(name = "fl_deal2month")
	private BigDecimal flDeal2month;

	@Column(name = "fl_limitmoney")
	private BigDecimal flLimitmoney;

	public OcrmFInterviewForexlimit() {
		super();
	}

	public OcrmFInterviewForexlimit(BigDecimal id, String taskNumber, String flName, BigDecimal flDeal2month, BigDecimal flLimitmoney) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.flName = flName;
		this.flDeal2month = flDeal2month;
		this.flLimitmoney = flLimitmoney;
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

	public String getFlName() {
		return flName;
	}

	public void setFlName(String flName) {
		this.flName = flName;
	}

	public BigDecimal getFlDeal2month() {
		return flDeal2month;
	}

	public void setFlDeal2month(BigDecimal flDeal2month) {
		this.flDeal2month = flDeal2month;
	}

	public BigDecimal getFlLimitmoney() {
		return flLimitmoney;
	}

	public void setFlLimitmoney(BigDecimal flLimitmoney) {
		this.flLimitmoney = flLimitmoney;
	}

}

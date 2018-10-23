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
@Table(name = "ocrm_f_interview_Depositpro")
public class OcrmFInterviewDepositpro {
	@Id
	@Column(name="Id")
	@SequenceGenerator(name = "ocrm_f_interview_Depositpro_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ocrm_f_interview_Depositpro_ID_GENERATOR")
	private BigDecimal id;

	@Column(name="task_number")
	private String taskNumber;

	@Column(name="dp_name")
	private String dpName;

	@Column(name="dp_avgdeposit")
	private BigDecimal dpAvgdeposit;

	public OcrmFInterviewDepositpro() {
		super();
	}

	public OcrmFInterviewDepositpro(BigDecimal id, String taskNumber, String dpName, BigDecimal dpAvgdeposit) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.dpName = dpName;
		this.dpAvgdeposit = dpAvgdeposit;
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

	public String getDpName() {
		return dpName;
	}

	public void setDpName(String dpName) {
		this.dpName = dpName;
	}

	public BigDecimal getDpAvgdeposit() {
		return dpAvgdeposit;
	}

	public void setDpAvgdeposit(BigDecimal dpAvgdeposit) {
		this.dpAvgdeposit = dpAvgdeposit;
	}
}

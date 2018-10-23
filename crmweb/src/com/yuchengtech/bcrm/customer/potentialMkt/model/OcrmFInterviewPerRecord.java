package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_PER_RECORD database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_PER_RECORD")
public class OcrmFInterviewPerRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_PER_RECORD_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_PER_RECORD_GENERATOR")
	private String id;
	
	@Column(name="TASK_NUMBER")
	private String taskNumber;

	@Column(name="AMOUNT_CAPITAL")
	private BigDecimal amountCapital;

	@Column(name="PER_EXPLAIN")
	private String perExplain;

	@Column(name="PER_NAME")
	private String perName;

	@Column(name="PER_ROLE")
	private String perRole;

	@Column(name="PER_TELPHONE")
	private String perTelphone;

	@Column(name="SHOLDER_CAPITAL")
	private String sholderCapital;

	public OcrmFInterviewPerRecord() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmountCapital() {
		return this.amountCapital;
	}

	public void setAmountCapital(BigDecimal amountCapital) {
		this.amountCapital = amountCapital;
	}

	public String getPerExplain() {
		return this.perExplain;
	}

	public void setPerExplain(String perExplain) {
		this.perExplain = perExplain;
	}

	public String getPerName() {
		return this.perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getPerRole() {
		return this.perRole;
	}

	public void setPerRole(String perRole) {
		this.perRole = perRole;
	}

	public String getPerTelphone() {
		return this.perTelphone;
	}

	public void setPerTelphone(String perTelphone) {
		this.perTelphone = perTelphone;
	}

	public String getSholderCapital() {
		return this.sholderCapital;
	}

	public void setSholderCapital(String sholderCapital) {
		this.sholderCapital = sholderCapital;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}
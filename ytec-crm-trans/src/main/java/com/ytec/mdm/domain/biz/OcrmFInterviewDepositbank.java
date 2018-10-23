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
@Table(name = "Ocrm_F_Interview_Depositbank")
public class OcrmFInterviewDepositbank {

	@Id
	@Column(name = "Id")
	@SequenceGenerator(name = "Ocrm_F_Interview_Depositbank_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_Depositbank_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_number")
	private String taskNumber;

	@Column(name = "d_bankname")
	private String dBankname;

	@Column(name = "d_avgdeposit")
	private BigDecimal dAvgdeposit;

	public OcrmFInterviewDepositbank() {
		super();
	}

	public OcrmFInterviewDepositbank(BigDecimal id, String taskNumber, String dBankname, BigDecimal dAvgdeposit) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.dBankname = dBankname;
		this.dAvgdeposit = dAvgdeposit;
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

	public String getDBankname() {
		return dBankname;
	}

	public void setDBankname(String dBankname) {
		this.dBankname = dBankname;
	}

	public BigDecimal getDAvgdeposit() {
		return dAvgdeposit;
	}

	public void setDAvgdeposit(BigDecimal dAvgdeposit) {
		this.dAvgdeposit = dAvgdeposit;
	}

}

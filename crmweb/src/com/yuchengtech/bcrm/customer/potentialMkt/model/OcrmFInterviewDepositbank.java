package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_DEPOSITBANK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_DEPOSITBANK")
public class OcrmFInterviewDepositbank implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_DEPOSITBANK_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_DEPOSITBANK_GENERATOR")
	private String id;

	@Column(name="D_AVGDEPOSIT")
	private BigDecimal dAvgdeposit;

	@Column(name="D_BANKNAME")
	private String dBankname;

	@Column(name="TASK_NUMBER")
	private String taskNumber;
	
	@Column(name="D_DEPOSIT_TYPE")
   	private String dDepositType;
   	
   	@Column(name="D_TERM")
   	private String dTerm;

    public OcrmFInterviewDepositbank() {
    }

	public BigDecimal getDAvgdeposit() {
		return this.dAvgdeposit;
	}

	public void setDAvgdeposit(BigDecimal dAvgdeposit) {
		this.dAvgdeposit = dAvgdeposit;
	}

	public String getDBankname() {
		return this.dBankname;
	}

	public void setDBankname(String dBankname) {
		this.dBankname = dBankname;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getDDepositType() {
		return dDepositType;
	}

	public void setDDepositType(String dDepositType) {
		this.dDepositType = dDepositType;
	}

	public String getDTerm() {
		return dTerm;
	}

	public void setDTerm(String dTerm) {
		this.dTerm = dTerm;
	}

}
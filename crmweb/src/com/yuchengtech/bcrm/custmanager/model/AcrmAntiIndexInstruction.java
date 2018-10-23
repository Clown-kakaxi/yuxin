package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_ANTI_INDEX_INSTRUCTION database table.
 * 
 */
@Entity
@Table(name="ACRM_ANTI_INDEX_INSTRUCTION")
public class AcrmAntiIndexInstruction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_ANTI_INDEX_INSTRUCTION_ID_INDEX_INSTRUCTION", sequenceName="ID_INDEX_INSTRUCTION" ,allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_ANTI_INDEX_INSTRUCTION_ID_INDEX_INSTRUCTION")
	@Column(name="INSTR_ID")
	private long instrId;

	@Column(name="CUST_ID")
	private String custId;
	
	@Column(name="INSTR_TIME")
	private String instrTime;

	@Column(name="INSTR_USER")
	private String instrUser;

	@Column(name="INSTRUCTION_CONTENT")
	private String instructionContent;

	@Temporal(TemporalType.DATE)
	@Column(name="KYJYBG_SBSJ")
	private Date kyjybgSbsj;

	public AcrmAntiIndexInstruction() {
	}

	public long getInstrId() {
		return this.instrId;
	}

	public void setInstrId(long instrId) {
		this.instrId = instrId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInstrUser() {
		return this.instrUser;
	}

	public void setInstrUser(String instrUser) {
		this.instrUser = instrUser;
	}

	public String getInstructionContent() {
		return this.instructionContent;
	}

	public void setInstructionContent(String instructionContent) {
		this.instructionContent = instructionContent;
	}

	public Date getKyjybgSbsj() {
		return this.kyjybgSbsj;
	}

	public void setKyjybgSbsj(Date kyjybgSbsj) {
		this.kyjybgSbsj = kyjybgSbsj;
	}

	public String getInstrTime() {
		return instrTime;
	}

	public void setInstrTime(String instrTime) {
		this.instrTime = instrTime;
	}

}






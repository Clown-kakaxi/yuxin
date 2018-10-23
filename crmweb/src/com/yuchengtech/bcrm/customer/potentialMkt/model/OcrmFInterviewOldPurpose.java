package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_INTERVIEW_OLD_PURPOSE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_OLD_PURPOSE")
public class OcrmFInterviewOldPurpose implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_OLD_PURPOSE_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_OLD_PURPOSE_GENERATOR")
	private String id;

	@Column(name="PUR_CUST2CALL")
	private String purCust2call;

	@Column(name="PUR_DEFEND2CALL")
	private String purDefend2call;

	@Column(name="PUR_MARK2PRO")
	private String purMark2pro;

	@Column(name="PUR_RISK2CALL")
	private String purRisk2call;

	@Column(name="PUR_SEEK2COLL")
	private String purSeek2coll;

	@Column(name="PUR_WARN2CALL")
	private String purWarn2call;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

    public OcrmFInterviewOldPurpose() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurCust2call() {
		return this.purCust2call;
	}

	public void setPurCust2call(String purCust2call) {
		this.purCust2call = purCust2call;
	}

	public String getPurDefend2call() {
		return this.purDefend2call;
	}

	public void setPurDefend2call(String purDefend2call) {
		this.purDefend2call = purDefend2call;
	}

	public String getPurMark2pro() {
		return this.purMark2pro;
	}

	public void setPurMark2pro(String purMark2pro) {
		this.purMark2pro = purMark2pro;
	}

	public String getPurRisk2call() {
		return this.purRisk2call;
	}

	public void setPurRisk2call(String purRisk2call) {
		this.purRisk2call = purRisk2call;
	}

	public String getPurSeek2coll() {
		return this.purSeek2coll;
	}

	public void setPurSeek2coll(String purSeek2coll) {
		this.purSeek2coll = purSeek2coll;
	}

	public String getPurWarn2call() {
		return this.purWarn2call;
	}

	public void setPurWarn2call(String purWarn2call) {
		this.purWarn2call = purWarn2call;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}
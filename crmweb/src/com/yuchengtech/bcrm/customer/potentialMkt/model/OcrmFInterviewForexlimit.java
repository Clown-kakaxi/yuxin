package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_FOREXLIMIT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_FOREXLIMIT")
public class OcrmFInterviewForexlimit implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_FOREXLIMIT_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_FOREXLIMIT_GENERATOR")
	private String id;
	
	@Column(name="FL_DEAL2MONTH")
	private BigDecimal flDeal2month;

	@Column(name="FL_LIMITMONEY")
	private BigDecimal flLimitmoney;

	@Column(name="FL_NAME")
	private String flName;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

    public OcrmFInterviewForexlimit() {
    }

	public BigDecimal getFlDeal2month() {
		return this.flDeal2month;
	}

	public void setFlDeal2month(BigDecimal flDeal2month) {
		this.flDeal2month = flDeal2month;
	}

	public BigDecimal getFlLimitmoney() {
		return this.flLimitmoney;
	}

	public void setFlLimitmoney(BigDecimal flLimitmoney) {
		this.flLimitmoney = flLimitmoney;
	}

	public String getFlName() {
		return this.flName;
	}

	public void setFlName(String flName) {
		this.flName = flName;
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

}
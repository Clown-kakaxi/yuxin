package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_PROFIT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_PROFIT")
public class OcrmFInterviewProfit implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_PROFIT_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_PROFIT_GENERATOR")
	private String id;

	@Column(name="P_GROSS")
	private BigDecimal pGross;

	@Column(name="P_MEMO")
	private String pMemo;

	@Column(name="P_PNET")
	private BigDecimal pPnet;

	@Column(name="P_REVENUE")
	private BigDecimal pRevenue;

	@Column(name="P_YEARS")
	private String pYears;
	
	@Column(name="P_YEARS_END")
	private String pYearsEnd;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

    public OcrmFInterviewProfit() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getPGross() {
		return this.pGross;
	}

	public void setPGross(BigDecimal pGross) {
		this.pGross = pGross;
	}

	public String getPMemo() {
		return this.pMemo;
	}

	public void setPMemo(String pMemo) {
		this.pMemo = pMemo;
	}

	public BigDecimal getPPnet() {
		return this.pPnet;
	}

	public void setPPnet(BigDecimal pPnet) {
		this.pPnet = pPnet;
	}

	public BigDecimal getPRevenue() {
		return this.pRevenue;
	}

	public void setPRevenue(BigDecimal pRevenue) {
		this.pRevenue = pRevenue;
	}

	public String getPYears() {
		return this.pYears;
	}

	public void setPYears(String pYears) {
		this.pYears = pYears;
	}
	
	public String getPYearsEnd() {
		return this.pYearsEnd;
	}
	
	public void setPYearsEnd(String pYearsEnd) {
		this.pYearsEnd = pYearsEnd;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}
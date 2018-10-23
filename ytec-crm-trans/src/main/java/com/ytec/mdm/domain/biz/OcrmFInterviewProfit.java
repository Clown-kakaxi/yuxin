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
@Table(name="Ocrm_F_Interview_Profit")
public class OcrmFInterviewProfit {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "Ocrm_F_Interview_Profit_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_Profit_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_Number")
	private String taskNumber;

	@Column(name = "p_Years")
	private String pYears;

	@Column(name = "p_Revenue")
	private BigDecimal pRevenue;

	@Column(name = "p_Gross")
	private BigDecimal pGross;

	@Column(name = "p_Pnet")
	private BigDecimal pPnet;

	@Column(name = "p_Memo")
	private String pMemo;
	
	@Column(name = "P_YEARS_END")
	private String pYearsEnd;

	public OcrmFInterviewProfit() {
		super();
	}

	public OcrmFInterviewProfit(BigDecimal id, String taskNumber, String pYears, BigDecimal p_Revenue, BigDecimal pGross, BigDecimal pPnet, String pMemo,String pYearsEnd) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.pYears = pYears;
		this.pRevenue = p_Revenue;
		this.pGross = pGross;
		this.pPnet = pPnet;
		this.pMemo = pMemo;
		this.pYearsEnd = pYearsEnd;
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

	public String getPYears() {
		return pYears;
	}

	public void setPYears(String pYears) {
		this.pYears = pYears;
	}

	public BigDecimal getPRevenue() {
		return pRevenue;
	}

	public void setPRevenue(BigDecimal p_Revenue) {
		this.pRevenue = p_Revenue;
	}

	public BigDecimal getPGross() {
		return pGross;
	}

	public void setPGross(BigDecimal pGross) {
		this.pGross = pGross;
	}

	public BigDecimal getPPnet() {
		return pPnet;
	}

	public void setPPnet(BigDecimal pPnet) {
		this.pPnet = pPnet;
	}

	public String getPMemo() {
		return pMemo;
	}

	public void setPMemo(String pMemo) {
		this.pMemo = pMemo;
	}

	public String getPYearsEnd() {
		return pYearsEnd;
	}
	
	public void setPYearsEnd(String pYearsEnd) {
		this.pYearsEnd = pYearsEnd;
	}
	
	
}

package com.yuchengtech.bcrm.serviceManage.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OCRM_F_SE_SCORE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SE_SCORE")
public class OcrmFSeScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_SE_SCORE_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_SE_SCORE_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ADD_DATE")
	private Date addDate;

	@Column(name="ADD_REASON")
	private String addReason;

	@Column(name="ADD_STATE")
	private String addState;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="ADD_ID")
	private String addId;

	@Column(name="SCORE_ADD")
	private BigDecimal scoreAdd;

	@Column(name="SCORE_TOTAL")
	private BigDecimal scoreTotal;

	@Column(name="SCORE_TODEL")
	private BigDecimal scoreTodel;

	@Column(name="SCORE_USED")
	private BigDecimal scoreUsed;
	
	
	@Column(name="COUNT_NUM")
	private BigDecimal countNum;
	
	@Column(name="CUST_CUM_COUNT")
	private BigDecimal custCumCount;
	
	@Column(name="CUST_CUM_COST")
	private BigDecimal custCumCost;
	
	@Column(name="CUST_COST_SUM")
	private BigDecimal custCostSum;
	
	

    public BigDecimal getCountNum() {
		return countNum;
	}

	public void setCountNum(BigDecimal countNum) {
		this.countNum = countNum;
	}

	public BigDecimal getCustCumCount() {
		return custCumCount;
	}

	public void setCustCumCount(BigDecimal custCumCount) {
		this.custCumCount = custCumCount;
	}

	public BigDecimal getCustCumCost() {
		return custCumCost;
	}

	public void setCustCumCost(BigDecimal custCumCost) {
		this.custCumCost = custCumCost;
	}

	public BigDecimal getCustCostSum() {
		return custCostSum;
	}

	public void setCustCostSum(BigDecimal custCostSum) {
		this.custCostSum = custCostSum;
	}

	public OcrmFSeScore() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddReason() {
		return this.addReason;
	}

	public void setAddReason(String addReason) {
		this.addReason = addReason;
	}

	public String getAddState() {
		return this.addState;
	}

	public void setAddState(String addState) {
		this.addState = addState;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}


	public BigDecimal getScoreAdd() {
		return this.scoreAdd;
	}

	public void setScoreAdd(BigDecimal scoreAdd) {
		this.scoreAdd = scoreAdd;
	}

	public BigDecimal getScoreTotal() {
		return this.scoreTotal;
	}

	public void setScoreTotal(BigDecimal scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public BigDecimal getScoreTodel() {
		return this.scoreTodel;
	}

	public void setScoreTodel(BigDecimal scoreTodel) {
		this.scoreTodel = scoreTodel;
	}

	public BigDecimal getScoreUsed() {
		return this.scoreUsed;
	}

	public void setScoreUsed(BigDecimal scoreUsed) {
		this.scoreUsed = scoreUsed;
	}

	public String getAddId() {
		return addId;
	}

	public void setAddId(String addId) {
		this.addId = addId;
	}

}
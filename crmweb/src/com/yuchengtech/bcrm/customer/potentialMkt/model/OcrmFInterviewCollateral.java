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
 * The persistent class for the OCRM_F_INTERVIEW_COLLATERAL database table.
 * @author denghj
 * @since 2015-11-11
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_COLLATERAL")
public class OcrmFInterviewCollateral implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_COLLATERAL_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_COLLATERAL_GENERATOR")
	private String id;
	
	@Column(name="TASK_NUMBER")
	private String taskNumber;
	
	@Column(name="COLLATERAL_TYPE")
	private String collateralType;
	
	@Column(name="ESTIMATE_VALUE")
	private Long estimateValue;
	
	@Column(name="COLLATERAL_ADDR")
	private String collateralAddr;
	
	@Column(name="NET_VALUE")
	private Long netValue;
	
	public OcrmFInterviewCollateral(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getCollateralType() {
		return collateralType;
	}

	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}

	public Long getEstimateValue() {
		return estimateValue;
	}

	public void setEstimateValue(Long estimateValue) {
		this.estimateValue = estimateValue;
	}

	public String getCollateralAddr() {
		return collateralAddr;
	}

	public void setCollateralAddr(String collateralAddr) {
		this.collateralAddr = collateralAddr;
	}

	public Long getNetValue() {
		return netValue;
	}

	public void setNetValue(Long netValue) {
		this.netValue = netValue;
	}
	
	
}

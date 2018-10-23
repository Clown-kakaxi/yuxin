package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiNationRisk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_NATION_RISK")
public class MCiNationRisk implements java.io.Serializable {

	// Fields

	private String riskNationCode;
	private String riskLevel;
	private Double rate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiNationRisk() {
	}

	/** minimal constructor */
	public MCiNationRisk(String riskNationCode) {
		this.riskNationCode = riskNationCode;
	}

	/** full constructor */
	public MCiNationRisk(String riskNationCode, String riskLevel, Double rate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.riskNationCode = riskNationCode;
		this.riskLevel = riskLevel;
		this.rate = rate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "RISK_NATION_CODE", unique = true, nullable = false, length = 20)
	public String getRiskNationCode() {
		return this.riskNationCode;
	}

	public void setRiskNationCode(String riskNationCode) {
		this.riskNationCode = riskNationCode;
	}

	@Column(name = "RISK_LEVEL", length = 20)
	public String getRiskLevel() {
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	@Column(name = "RATE", precision = 10, scale = 7)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
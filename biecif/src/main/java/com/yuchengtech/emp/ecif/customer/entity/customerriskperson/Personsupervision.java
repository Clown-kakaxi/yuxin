package com.yuchengtech.emp.ecif.customer.entity.customerriskperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONBUSIINSURANCE database table.
 * 
 */
@Entity
@Table(name="PERSONSUPERVISION")
public class Personsupervision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUPERVISION_ID", unique=true, nullable=false)
	private Long supervisionId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="AML_RISK_LEVEL",length=20)
	private String amlRiskLevel;
	
	@Column(name="CREDIT_RISK_LEVEL",length=20)
	private String creditRiskLevel;
	
	@Column(name="LAST_UPDATE_SYS",length=20)
	private Integer lastUpdateSys;
	
	@Column(name="LAST_UPDATE_USER",length=20)
	private Integer lastUpdateUser;
	
	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;
	
	@Column(name="TX_SEQ_NO",length=32)
	private String txSeqNo;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSupervisionId() {
		return supervisionId;
	}

	public void setSupervisionId(Long supervisionId) {
		this.supervisionId = supervisionId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getAmlRiskLevel() {
		return amlRiskLevel;
	}

	public void setAmlRiskLevel(String amlRiskLevel) {
		this.amlRiskLevel = amlRiskLevel;
	}

	public String getCreditRiskLevel() {
		return creditRiskLevel;
	}

	public void setCreditRiskLevel(String creditRiskLevel) {
		this.creditRiskLevel = creditRiskLevel;
	}

	public Integer getLastUpdateSys() {
		return lastUpdateSys;
	}

	public void setLastUpdateSys(Integer lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Integer getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(Integer lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}

package com.yuchengtech.emp.ecif.customer.entity.customerriskorg;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGSUPERVISION database table.
 * 
 */
@Entity
@Table(name="ORGSUPERVISION")
public class Orgsupervision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUPERVISION_ID", unique=true, nullable=false)
	private Long supervisionId;

	@Column(name="AML_RISK_LEVEL", length=20)
	private String amlRiskLevel;

	@Column(name="CBRC_RISK_SIGNAL", length=40)
	private String cbrcRiskSignal;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Orgsupervision() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSupervisionId() {
		return this.supervisionId;
	}

	public void setSupervisionId(Long supervisionId) {
		this.supervisionId = supervisionId;
	}

	public String getAmlRiskLevel() {
		return this.amlRiskLevel;
	}

	public void setAmlRiskLevel(String amlRiskLevel) {
		this.amlRiskLevel = amlRiskLevel;
	}

	public String getCbrcRiskSignal() {
		return this.cbrcRiskSignal;
	}

	public void setCbrcRiskSignal(String cbrcRiskSignal) {
		this.cbrcRiskSignal = cbrcRiskSignal;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
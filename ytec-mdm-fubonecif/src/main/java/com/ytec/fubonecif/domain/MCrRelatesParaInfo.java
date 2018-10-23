package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCrRelatesParaInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CR_RELATES_PARA_INFO")
public class MCrRelatesParaInfo implements java.io.Serializable {

	// Fields

	private BigDecimal relateId;
	private String relatesType;
	private String twRelatesType;
	private String isRelation;
	private String isCreditparty;
	private String isOutCreditparty;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCrRelatesParaInfo() {
	}

	/** minimal constructor */
	public MCrRelatesParaInfo(BigDecimal relateId) {
		this.relateId = relateId;
	}

	/** full constructor */
	public MCrRelatesParaInfo(BigDecimal relateId, String relatesType,
			String twRelatesType, String isRelation, String isCreditparty,
			String isOutCreditparty, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.relateId = relateId;
		this.relatesType = relatesType;
		this.twRelatesType = twRelatesType;
		this.isRelation = isRelation;
		this.isCreditparty = isCreditparty;
		this.isOutCreditparty = isOutCreditparty;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "RELATE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRelateId() {
		return this.relateId;
	}

	public void setRelateId(BigDecimal relateId) {
		this.relateId = relateId;
	}

	@Column(name = "RELATES_TYPE", length = 20)
	public String getRelatesType() {
		return this.relatesType;
	}

	public void setRelatesType(String relatesType) {
		this.relatesType = relatesType;
	}

	@Column(name = "TW_RELATES_TYPE", length = 20)
	public String getTwRelatesType() {
		return this.twRelatesType;
	}

	public void setTwRelatesType(String twRelatesType) {
		this.twRelatesType = twRelatesType;
	}

	@Column(name = "IS_RELATION", length = 1)
	public String getIsRelation() {
		return this.isRelation;
	}

	public void setIsRelation(String isRelation) {
		this.isRelation = isRelation;
	}

	@Column(name = "IS_CREDITPARTY", length = 1)
	public String getIsCreditparty() {
		return this.isCreditparty;
	}

	public void setIsCreditparty(String isCreditparty) {
		this.isCreditparty = isCreditparty;
	}

	@Column(name = "IS_OUT_CREDITPARTY", length = 1)
	public String getIsOutCreditparty() {
		return this.isOutCreditparty;
	}

	public void setIsOutCreditparty(String isOutCreditparty) {
		this.isOutCreditparty = isOutCreditparty;
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
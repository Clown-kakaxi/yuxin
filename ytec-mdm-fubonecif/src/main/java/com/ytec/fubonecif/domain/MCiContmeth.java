package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiContmeth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CONTMETH")
public class MCiContmeth implements java.io.Serializable {

	// Fields

	private String contmethId;
	private String custId;
	private String isPriori;
	private String contmethType;
	private String contmethInfo;
	private BigDecimal contmethSeq;
	private String remark;
	private String stat;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiContmeth() {
	}

	/** minimal constructor */
	public MCiContmeth(String contmethId) {
		this.contmethId = contmethId;
	}

	/** full constructor */
	public MCiContmeth(String contmethId, String custId, String isPriori,
			String contmethType, String contmethInfo, BigDecimal contmethSeq,
			String remark, String stat, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.contmethId = contmethId;
		this.custId = custId;
		this.isPriori = isPriori;
		this.contmethType = contmethType;
		this.contmethInfo = contmethInfo;
		this.contmethSeq = contmethSeq;
		this.remark = remark;
		this.stat = stat;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CONTMETH_ID", unique = true, nullable = false, length = 20)
	public String getContmethId() {
		return this.contmethId;
	}

	public void setContmethId(String contmethId) {
		this.contmethId = contmethId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "IS_PRIORI", length = 1)
	public String getIsPriori() {
		return this.isPriori;
	}

	public void setIsPriori(String isPriori) {
		this.isPriori = isPriori;
	}

	@Column(name = "CONTMETH_TYPE", length = 20)
	public String getContmethType() {
		return this.contmethType;
	}

	public void setContmethType(String contmethType) {
		this.contmethType = contmethType;
	}

	@Column(name = "CONTMETH_INFO", length = 100)
	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	@Column(name = "CONTMETH_SEQ", precision = 22, scale = 0)
	public BigDecimal getContmethSeq() {
		return this.contmethSeq;
	}

	public void setContmethSeq(BigDecimal contmethSeq) {
		this.contmethSeq = contmethSeq;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STAT", length = 20)
	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
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
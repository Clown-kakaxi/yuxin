package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the OWEINTEREST database table.
 * 
 */
@Entity
@Table(name="OWEINTEREST")
public class Oweinterest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="ACNT",length=18)
	private String acnt;

	@Column(name="CONTRACTNO",length=18)
	private String contractno;

	@Column(name="CSTCODE",length=18)
	private String cstcode;

	@Column(name="CURKIND",length=20)
	private String curkind;

	@Column(name="DUEBILLNO",length=18)
	private String duebillno;

	@Column(name="FLOW",length=18)
	private String flow;

	@Column(name="INSTCODE",length=18)
	private String instcode;

	@Column(name="INTETYPE",length=18)
	private String intetype;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="OPCODE",length=18)
	private String opcode;

	@Column(name="OPDATE",length=20)
	private String opdate;

	@Column(name="OWEDATE",length=20)
	private String owedate;

	@Column(name="OWESUM",length=18)
	private String owesum;

	@Column(name="TRFLAG",length=18)
	private String trflag;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Oweinterest() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getAcnt() {
		return this.acnt;
	}

	public void setAcnt(String acnt) {
		this.acnt = acnt;
	}

	public String getContractno() {
		return this.contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public String getCstcode() {
		return this.cstcode;
	}

	public void setCstcode(String cstcode) {
		this.cstcode = cstcode;
	}

	public String getCurkind() {
		return this.curkind;
	}

	public void setCurkind(String curkind) {
		this.curkind = curkind;
	}

	public String getDuebillno() {
		return this.duebillno;
	}

	public void setDuebillno(String duebillno) {
		this.duebillno = duebillno;
	}

	public String getFlow() {
		return this.flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getInstcode() {
		return this.instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}

	public String getIntetype() {
		return this.intetype;
	}

	public void setIntetype(String intetype) {
		this.intetype = intetype;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getOpcode() {
		return this.opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getOpdate() {
		return this.opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public String getOwedate() {
		return this.owedate;
	}

	public void setOwedate(String owedate) {
		this.owedate = owedate;
	}

	public String getOwesum() {
		return this.owesum;
	}

	public void setOwesum(String owesum) {
		this.owesum = owesum;
	}

	public String getTrflag() {
		return this.trflag;
	}

	public void setTrflag(String trflag) {
		this.trflag = trflag;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
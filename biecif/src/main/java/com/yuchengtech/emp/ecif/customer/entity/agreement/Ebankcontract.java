package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the EBANKCONTRACT database table.
 * 
 */
@Entity
@Table(name="EBANKCONTRACT")
public class Ebankcontract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="IAB_ACCNAME", length=18)
	private String iabAccname;

	@Column(name="IAB_ACCNO", length=18)
	private String iabAccno;

	@Column(name="IAB_AGNO", length=18)
	private String iabAgno;

	@Column(name="IAB_AGTYPE", length=18)
	private String iabAgtype;

	@Column(name="IAB_PC_MARK", length=18)
	private String iabPcMark;

	@Column(name="IAB_STT", length=18)
	private String iabStt;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Ebankcontract() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getIabAccname() {
		return this.iabAccname;
	}

	public void setIabAccname(String iabAccname) {
		this.iabAccname = iabAccname;
	}

	public String getIabAccno() {
		return this.iabAccno;
	}

	public void setIabAccno(String iabAccno) {
		this.iabAccno = iabAccno;
	}

	public String getIabAgno() {
		return this.iabAgno;
	}

	public void setIabAgno(String iabAgno) {
		this.iabAgno = iabAgno;
	}

	public String getIabAgtype() {
		return this.iabAgtype;
	}

	public void setIabAgtype(String iabAgtype) {
		this.iabAgtype = iabAgtype;
	}

	public String getIabPcMark() {
		return this.iabPcMark;
	}

	public void setIabPcMark(String iabPcMark) {
		this.iabPcMark = iabPcMark;
	}

	public String getIabStt() {
		return this.iabStt;
	}

	public void setIabStt(String iabStt) {
		this.iabStt = iabStt;
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

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
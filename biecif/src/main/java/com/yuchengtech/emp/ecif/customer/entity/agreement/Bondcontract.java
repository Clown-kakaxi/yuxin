package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the BONDCONTRACT database table.
 * 
 */
@Entity
@Table(name="BONDCONTRACT")
public class Bondcontract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="DJED",length=16)
	private String djed;

	@Column(name="FXRQ",length=16)
	private String fxrq;

	@Column(name="GZTGZH",length=18)
	private String gztgzh;

	@Column(name="KYED",length=16)
	private String kyed;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="YEBDRQ", length=20)
	private String yebdrq;

	@Column(name="ZMYE",length=16)
	private String zmye;

	@Column(name="ZQCYZT",length=8)
	private String zqcyzt;

	@Column(name="ZQDM",length=12)
	private String zqdm;

	@Column(name="ZQQC",length=8)
	private String zqqc;

	@Column(name="ZYED",length=16)
	private String zyed;

    public Bondcontract() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getDjed() {
		return this.djed;
	}

	public void setDjed(String djed) {
		this.djed = djed;
	}

	public String getFxrq() {
		return this.fxrq;
	}

	public void setFxrq(String fxrq) {
		this.fxrq = fxrq;
	}

	public String getGztgzh() {
		return this.gztgzh;
	}

	public void setGztgzh(String gztgzh) {
		this.gztgzh = gztgzh;
	}

	public String getKyed() {
		return this.kyed;
	}

	public void setKyed(String kyed) {
		this.kyed = kyed;
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

	public String getYebdrq() {
		return this.yebdrq;
	}

	public void setYebdrq(String yebdrq) {
		this.yebdrq = yebdrq;
	}

	public String getZmye() {
		return this.zmye;
	}

	public void setZmye(String zmye) {
		this.zmye = zmye;
	}

	public String getZqcyzt() {
		return this.zqcyzt;
	}

	public void setZqcyzt(String zqcyzt) {
		this.zqcyzt = zqcyzt;
	}

	public String getZqdm() {
		return this.zqdm;
	}

	public void setZqdm(String zqdm) {
		this.zqdm = zqdm;
	}

	public String getZqqc() {
		return this.zqqc;
	}

	public void setZqqc(String zqqc) {
		this.zqqc = zqqc;
	}

	public String getZyed() {
		return this.zyed;
	}

	public void setZyed(String zyed) {
		this.zyed = zyed;
	}

}
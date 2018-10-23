package com.yuchengtech.emp.ecif.customer.entity.customercontact;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CONTMETH database table.
 * 
 */
@Entity
@Table(name="M_CI_CONTMETH")
public class Contmeth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTMETH_ID", unique=true, nullable=false)
	private Long contmethId;

	@Column(name="CONTMETH_INFO", length=100)
	private String contmethInfo;

	@Column(name="CONTMETH_SEQ")
	private Long contmethSeq;

	@Column(name="CONTMETH_TYPE", length=20)
	private String contmethType;

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

    public Contmeth() {
    }

	public Long getContmethId() {
		return this.contmethId;
	}

	public void setContmethId(Long contmethId) {
		this.contmethId = contmethId;
	}

	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	public Long getContmethSeq() {
		return this.contmethSeq;
	}

	public void setContmethSeq(Long contmethSeq) {
		this.contmethSeq = contmethSeq;
	}

	public String getContmethType() {
		return this.contmethType;
	}

	public void setContmethType(String contmethType) {
		this.contmethType = contmethType;
	}

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
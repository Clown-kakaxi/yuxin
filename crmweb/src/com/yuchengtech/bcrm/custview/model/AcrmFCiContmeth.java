package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_CONTMETH database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CONTMETH")
public class AcrmFCiContmeth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_CONTMETH_CONTMETH_ID_GENERATOR", sequenceName="SEQUENCE_AFCI_CONTMETH" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_CONTMETH_CONTMETH_ID_GENERATOR")
	@Column(name="CONTMETH_ID")
	private String contmethId;

	@Column(name="CONTMETH_INFO")
	private String contmethInfo;

	@Column(name="CONTMETH_SEQ")
	private BigDecimal contmethSeq;

	@Column(name="CONTMETH_TYPE")
	private String contmethType;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="IS_PRIORI")
	private String isPriori;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	private String remark;

	private String stat;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
	
	@Column(name="CREATE_USER")
	private String createUser;
	
	@Column(name="CREATE_TM")
	private Timestamp createTm;

    public AcrmFCiContmeth() {
    }

	public String getContmethId() {
		return this.contmethId;
	}

	public void setContmethId(String contmethId) {
		this.contmethId = contmethId;
	}

	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	public BigDecimal getContmethSeq() {
		return this.contmethSeq;
	}

	public void setContmethSeq(BigDecimal contmethSeq) {
		this.contmethSeq = contmethSeq;
	}

	public String getContmethType() {
		return this.contmethType;
	}

	public void setContmethType(String contmethType) {
		this.contmethType = contmethType;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getIsPriori() {
		return this.isPriori;
	}

	public void setIsPriori(String isPriori) {
		this.isPriori = isPriori;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}


	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}
	
	

}
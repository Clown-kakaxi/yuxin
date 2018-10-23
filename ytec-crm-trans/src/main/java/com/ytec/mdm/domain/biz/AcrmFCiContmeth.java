package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the ACRM_F_CI_CONTMETH database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CONTMETH")
public class AcrmFCiContmeth implements Serializable {
	@Id
//	@SequenceGenerator(name="ACRM_F_CI_CONTMETH_CONTMETHID_GENERATOR", sequenceName="SEQ_CONTMETH_ID")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_CONTMETH_CONTMETHID_GENERATOR")
	@Column(name="CONTMETH_ID")
	private long contmethId;

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

    public AcrmFCiContmeth() {
    }

	public long getContmethId() {
		return this.contmethId;
	}

	public void setContmethId(long contmethId) {
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

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
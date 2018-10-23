package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the ACRM_F_CI_CROSSINDEX database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CROSSINDEX")
public class AcrmFCiCrossindex implements Serializable {
	@Id
	@Column(name="CROSSINDEX_ID")
	private String crossindexId;

	@Column(name="CUST_ID")
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="SRC_SYS_CUST_NO")
	private String srcSysCustNo;

	@Column(name="SRC_SYS_NO")
	private String srcSysNo;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiCrossindex() {
    }

	public String getCrossindexId() {
		return this.crossindexId;
	}

	public void setCrossindexId(String crossindexId) {
		this.crossindexId = crossindexId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
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

	public String getSrcSysCustNo() {
		return this.srcSysCustNo;
	}

	public void setSrcSysCustNo(String srcSysCustNo) {
		this.srcSysCustNo = srcSysCustNo;
	}

	public String getSrcSysNo() {
		return this.srcSysNo;
	}

	public void setSrcSysNo(String srcSysNo) {
		this.srcSysNo = srcSysNo;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
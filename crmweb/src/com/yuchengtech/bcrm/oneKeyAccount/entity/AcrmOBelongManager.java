package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * 所属客户经理
 * 
 */
@Entity
@Table(name="ACRM_O_BELONG_MANAGER")
public class AcrmOBelongManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BELONG_MANAGER_ID")
	private String belongManagerId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_MANAGER_NO")
	private String custManagerNo;

	@Column(name="CUST_MANAGER_TYPE")
	private String custManagerType;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MAIN_TYPE")
	private String mainType;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="VALID_FLAG")
	private String validFlag;

    public AcrmOBelongManager() {
    }

	public String getBelongManagerId() {
		return this.belongManagerId;
	}

	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustManagerNo() {
		return this.custManagerNo;
	}

	public void setCustManagerNo(String custManagerNo) {
		this.custManagerNo = custManagerNo;
	}

	public String getCustManagerType() {
		return this.custManagerType;
	}

	public void setCustManagerType(String custManagerType) {
		this.custManagerType = custManagerType;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getMainType() {
		return this.mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

}
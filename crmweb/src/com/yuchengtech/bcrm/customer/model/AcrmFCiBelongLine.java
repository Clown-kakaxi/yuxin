package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_BELONG_LINE database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_BELONG_LINE")
public class AcrmFCiBelongLine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="BELONG_LINE_ID")
	private String belongLineId;

	@Column(name="BELONG_LINE_NO")
	private String belongLineNo;

	@Column(name="BELONG_LINE_TYPE")
	private String belongLineType;

	@Column(name="CUST_ID")
	private String custId;

	@Temporal(TemporalType.DATE)
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

	private String recommender;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="VALID_FLAG")
	private String validFlag;

	public AcrmFCiBelongLine() {
	}

	public String getBelongLineId() {
		return this.belongLineId;
	}

	public void setBelongLineId(String belongLineId) {
		this.belongLineId = belongLineId;
	}

	public String getBelongLineNo() {
		return this.belongLineNo;
	}

	public void setBelongLineNo(String belongLineNo) {
		this.belongLineNo = belongLineNo;
	}

	public String getBelongLineType() {
		return this.belongLineType;
	}

	public void setBelongLineType(String belongLineType) {
		this.belongLineType = belongLineType;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getRecommender() {
		return this.recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
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
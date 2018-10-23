package com.yuchengtech.emp.ecif.customer.entity.customer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * The persistent class for the CUSTOMER database table.
 * 
 */
@Entity
@Table(name="M_CI_CUSTOMER")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="BLANK_FLAG", length=1)
	private String blankFlag;

	@Column(name="MERGE_FLAG", length=1)
	private String combineFlag;

	@Column(name="CREATE_BRANCH_NO", length=20)
	private String createBranchNo;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_TELLER_NO", length=20)
	private String createTellerNo;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="CUST_NO", length=20)
	private String custNo;

	@Column(name="CUST_TYPE", length=20)
	private String custType;

	@Column(name="EBANK_FLAG", length=1)
	private String ebankFlag;

	@Column(name="INOUT_FLAG", length=1)
	private String inoutFlag;

	@Column(name="REAL_FLAG", length=1)
	private String realFlag;

//	@Column(name="VALID_FLAG", length=1)
//	private String validFlag;

//	LAST_UPDATE_SYS，LAST_UPDATE_USER，LAST_UPDATE_TM，TX_SEQ_NO
//	最后修改系统，修改人，时间等
	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;
	
	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;
	
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	
	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getBlankFlag() {
		return this.blankFlag;
	}

	public void setBlankFlag(String blankFlag) {
		this.blankFlag = blankFlag;
	}

	public String getCombineFlag() {
		return this.combineFlag;
	}

	public void setCombineFlag(String combineFlag) {
		this.combineFlag = combineFlag;
	}

	public String getCreateBranchNo() {
		return this.createBranchNo;
	}

	public void setCreateBranchNo(String createBranchNo) {
		this.createBranchNo = createBranchNo;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateTellerNo() {
		return this.createTellerNo;
	}

	public void setCreateTellerNo(String createTellerNo) {
		this.createTellerNo = createTellerNo;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getEbankFlag() {
		return this.ebankFlag;
	}

	public void setEbankFlag(String ebankFlag) {
		this.ebankFlag = ebankFlag;
	}

	public String getInoutFlag() {
		return this.inoutFlag;
	}

	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	public String getRealFlag() {
		return this.realFlag;
	}

	public void setRealFlag(String realFlag) {
		this.realFlag = realFlag;
	}

//	public String getValidFlag() {
//		return this.validFlag;
//	}
//
//	public void setValidFlag(String validFlag) {
//		this.validFlag = validFlag;
//	}

	public String getLastUpdateSys() {
		return lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}
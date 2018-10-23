package com.yuchengtech.emp.ecif.customer.entity.customer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * The persistent class for the CUSTOMER database table.
 * 
 */
@Entity
@Table(name="H_M_CI_CUSTOMER")
public class HCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

//	CUST_ID	-5	BIGINT	19
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;
//	CUST_NO	12	VARCHAR	32
	@Column(name="CUST_NO", length=32)
	private String custNo;
//	CUST_TYPE	12	VARCHAR	20
	@Column(name="CUST_TYPE", length=20)
	private String custType;
//	EBANK_FLAG	1	CHAR	1
	@Column(name="EBANK_FLAG", length=1)
	private String ebankFlag;
//	REAL_FLAG	1	CHAR	1
	@Column(name="REAL_FLAG", length=1)
	private String realFlag;
//	INOUT_FLAG	1	CHAR	1
	@Column(name="INOUT_FLAG", length=1)
	private String inoutFlag;
//	BLANK_FLAG	1	CHAR	1
	@Column(name="BLANK_FLAG", length=1)
	private String blankFlag;
//	COMBINE_FLAG	1	CHAR	1
	@Column(name="COMBINE_FLAG", length=1)
	private String combineFlag;
//	CREATE_DATE	91	DATE	10
	@Column(name="CREATE_DATE")
	private Date createDate;
//	CREATE_TIME	93	TIMESTAMP	26
	@Column(name="CREATE_TIME")
	private Timestamp createTime;
//	CREATE_BRANCH_NO	12	VARCHAR	20
	@Column(name="CREATE_BRANCH_NO", length=20)
	private String createBranchNo;
//	CREATE_TELLER_NO	12	VARCHAR	20
	@Column(name="CREATE_TELLER_NO", length=20)
	private String createTellerNo;
//	LAST_UPDATE_SYS	12	VARCHAR	20
	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;
//	LAST_UPDATE_USER	12	VARCHAR	20
	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;
//	LAST_UPDATE_TM	93	TIMESTAMP	26
	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;
//	TX_SEQ_NO	12	VARCHAR	32
	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;
	//HIS_OPER_SYS	12	VARCHAR	20	20
	@Column(name = "HIS_OPER_SYS", length = 20 )
	private String hisOperSys;
	//HIS_OPER_TYPE	12	VARCHAR	2	2
	@Column(name = "HIS_OPER_TYPE", length = 2 )
	private String hisOperType;
	//HIS_OPER_TIME	93	TIMESTAMP	26	16
	@Column(name = "HIS_OPER_TIME" )
	private Timestamp hisOperTime;
	//HIS_DATA_DATE	12	VARCHAR	10	10
	@Column(name = "HIS_DATA_DATE", length = 10 )
	private String hisDataDate;

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

	public Timestamp getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getHisOperSys() {
		return hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	public String getHisOperType() {
		return hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	public Timestamp getHisOperTime() {
		return hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	public String getHisDataDate() {
		return hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

}
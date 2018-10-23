package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the BONDCONTRACT database table.
 * 
 */
@Entity
@Table(name="BDSACCTINFO")
public class Bdsacctinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
CONTR_ID	协议标识	VARCHAR	60
ACCTNO	理财帐户号	VARCHAR	32
SUBACCT	款项号	VARCHAR	32
ACCTNO1	存款帐号	VARCHAR	32
PRDCODE	产品代码	VARCHAR	32
ACCSTAT	帐户状态	VARCHAR	20
OPENDATE	开户日期	DATE	10
OPENBRC	开户机构	VARCHAR	9
TELLER1	开户员工编号	VARCHAR	20
LAST_UPDATE_SYS	最后更新系统	VARCHAR	20
LAST_UPDATE_USER	最后更新人	VARCHAR	20
LAST_UPDATE_TM	最后更新时间	TIMESTAMP	26
TX_SEQ_NO	交易流水号	VARCHAR	32
	*/
	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;
	@Column(name="ACCTNO")
	private String acctNo;
	@Column(name="SUBACCT")
	private String subAcct;
	@Column(name="ACCTNO1")
	private String acctNo1;
	@Column(name="PRDCODE")
	private String prdCode;	
	@Column(name="ACCSTAT")
	private String accStat;
	@Column(name="OPENDATE")
	private Date openDate;
	@Column(name="OPENBRC")
	private String openBrc;
	@Column(name="TELLER1")
	private String teller1;
	
//	@Column(name="LAST_UPDATE_SYS", length=20)
//	private String lastUpdateSys;
//	@Column(name="LAST_UPDATE_TM", length=20)
//	private Timestamp lastUpdateTm;
//	@Column(name="LAST_UPDATE_USER", length=20)
//	private String lastUpdateUser;
//	@Column(name="TX_SEQ_NO", length=32)
//	private String txSeqNo;

    public Bdsacctinfo() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}


	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getSubAcct() {
		return subAcct;
	}

	public void setSubAcct(String subAcct) {
		this.subAcct = subAcct;
	}

	public String getAcctNo1() {
		return acctNo1;
	}

	public void setAcctNo1(String acctNo1) {
		this.acctNo1 = acctNo1;
	}

	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	public String getAccStat() {
		return accStat;
	}

	public void setAccStat(String accStat) {
		this.accStat = accStat;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getOpenBrc() {
		return openBrc;
	}

	public void setOpenBrc(String openBrc) {
		this.openBrc = openBrc;
	}

	public String getTeller1() {
		return teller1;
	}

	public void setTeller1(String teller1) {
		this.teller1 = teller1;
	}

}
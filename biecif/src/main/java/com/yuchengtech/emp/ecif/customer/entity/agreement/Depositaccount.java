package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * The persistent class for the DEPOSITACCOUNT database table.
 * 
 */
@Entity
@Table(name="M_AG_DEPOSIT_ACCT")
public class Depositaccount implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
CONTR_ID	协议标识	VARCHAR	60
ACCTNO	存款账号	VARCHAR	32
SUBACCT	款项代码	VARCHAR	32
PRDCODE	产品代码	VARCHAR	32
OPNFLAG	开户标志	CHAR	1
CCY	币种	VARCHAR	20
BAL	余额	DECIMAL	17
ACCSTAT	帐户状态	VARCHAR	20
OPENDATE	开户日期	DATE	10
OPENBRC	开户机构	VARCHAR	9
TELLER	开户柜员	VARCHAR	20
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
	@Column(name="PRDCODE")
	private String prdCode;
	@Column(name="OPNFLAG")
	private String openFlag;
	@Column(name="CCY")
	private String ccy;
	@Column(name="BAL")
	private BigDecimal bal;
	@Column(name="ACCSTAT")
	private String accStat;
	@Column(name="OPENDATE")
	private Date openDate;
	@Column(name="OPENBRC")
	private String openBrc;
	@Column(name="TELLER")
	private String teller;
	@Column(name="ACCTPRY")
	private String acctpry;
	
//	@Column(name="LAST_UPDATE_SYS")
//	private String lastUpdateSys;
//	@Column(name="LAST_UPDATE_TM")
//	private Timestamp lastUpdateTm;
//	@Column(name="LAST_UPDATE_USER")
//	private String lastUpdateUser;
//	@Column(name="TX_SEQ_NO")
//	private String txSeqNo;

    public Depositaccount() {
    }

	public String getContrId() {
		return contrId;
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

	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
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

	public String getTeller() {
		return teller;
	}

	public void setTeller(String teller) {
		this.teller = teller;
	}

	public String getAcctpry() {
		return acctpry;
	}

	public void setAcctpry(String acctpry) {
		this.acctpry = acctpry;
	}

}
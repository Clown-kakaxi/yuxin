package com.yuchengtech.emp.ecif.report.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the RPT_CUST_ACCT_CHANGE_INFO database table.
 * 
 */
public class RptCustAcctChangeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String branchName;

	private String createBranchNo;

	private Timestamp createTime;

	private String custAcctSts;

	private String custName;

	private String custNo;

	private BigDecimal depositBal;

	private BigDecimal depositBalDayAvg;

	private Date rptDate;

    public RptCustAcctChangeInfo() {
    }

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCreateBranchNo() {
		return this.createBranchNo;
	}

	public void setCreateBranchNo(String createBranchNo) {
		this.createBranchNo = createBranchNo;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCustAcctSts() {
		return this.custAcctSts;
	}

	public void setCustAcctSts(String custAcctSts) {
		this.custAcctSts = custAcctSts;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public BigDecimal getDepositBal() {
		return this.depositBal;
	}

	public void setDepositBal(BigDecimal depositBal) {
		this.depositBal = depositBal;
	}

	public BigDecimal getDepositBalDayAvg() {
		return this.depositBalDayAvg;
	}

	public void setDepositBalDayAvg(BigDecimal depositBalDayAvg) {
		this.depositBalDayAvg = depositBalDayAvg;
	}

	public Date getRptDate() {
		return this.rptDate;
	}

	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}

}
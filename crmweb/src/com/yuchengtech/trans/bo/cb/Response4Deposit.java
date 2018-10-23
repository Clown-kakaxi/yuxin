package com.yuchengtech.trans.bo.cb;

import com.yuchengtech.trans.bo.RequestBody;

public class Response4Deposit extends RequestBody {
	String custCd;
	String accName;
	String accNo;
	String accCcy;
	String batp;
	String accAval;
	String sysDate;
	String sysTime;

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccCcy() {
		return accCcy;
	}

	public void setAccCcy(String accCcy) {
		this.accCcy = accCcy;
	}

	public String getBatp() {
		return batp;
	}

	public void setBatp(String batp) {
		this.batp = batp;
	}

	public String getAccAval() {
		return accAval;
	}

	public void setAccAval(String accAval) {
		this.accAval = accAval;
	}

	public String getSysDate() {
		return sysDate;
	}

	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	public String getSysTime() {
		return sysTime;
	}

	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}

	public String toString() {
		return "Response4Deposit: custCd=" + custCd + ">>accName=" + accName + ">>accNo=" + accNo + ">>accCcy=" + accCcy + ">>batp=" + batp + ">>accAval=" + accAval
				+ ">>sysDate=" + sysDate + ">>sysTime=" + sysTime;
	}
}

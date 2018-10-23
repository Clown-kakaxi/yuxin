package com.yuchengtech.trans.bo;

/**
 * @author Administrator
 *
 */
public  class CBRequestBody extends RequestBody{
	String txCode;// char(4) 交易代码
	String custCd;//char(12), 核心系统客户号
	
	String evaDate;
	String effDate;
	String evaScore;
	String opUser;
	
	

	
	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getEvaDate() {
		return evaDate;
	}

	public void setEvaDate(String evaDate) {
		this.evaDate = evaDate;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getEvaScore() {
		return evaScore;
	}

	public void setEvaScore(String evaScore) {
		this.evaScore = evaScore;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
   
	
}

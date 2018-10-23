package com.yuchengtech.trans.bo.cb;

/**
 * 
 * @author wangtb@yuchengtech.com
 * 个人客户	CMFR	理财产品客户评级	CRMUPDFR
 */
public class UpdateRequestBody2CB {
	String txCode;// char 4 Y 交易代码
	String custCd;// char 12 Y 核心系统客户号
	String evaDate;// integer yyyymmdd 8 Y 评估日期
	String effDate;// integer yyyymmdd 8 Y 评估有效期
	int evaScore;// integer 3 Y 评估分数
	String opUser;// char 10 Y 评估人

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
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

	public int getEvaScore() {
		return evaScore;
	}

	public void setEvaScore(int evaScore) {
		this.evaScore = evaScore;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
}

package com.yuchengtech.trans.bo.cb;

import com.yuchengtech.trans.bo.RequestBody;

public class QueryRequestBody2CB extends RequestBody {
	String txCode;//char(4) 交易代码
	String custCd;//char(12), 核心系统客户号
	
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
}

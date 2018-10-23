package com.yuchengtech.trans.bo.ecif;

import com.yuchengtech.bcrm.custmanager.model.AcrmFCiSpeciallist;
import com.yuchengtech.trans.bo.RequestBody;

public class RequestBody4UpdateSpecialList extends RequestBody {
	private String custNo = null;
	private String txCode = null;
	private String txName = null;
	private String authType = null;
	private String authCode = null;
	AcrmFCiSpeciallist speciallist;
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getTxName() {
		return txName;
	}
	public void setTxName(String txName) {
		this.txName = txName;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public AcrmFCiSpeciallist getSpeciallist() {
		return speciallist;
	}
	public void setSpeciallist(AcrmFCiSpeciallist speciallist) {
		this.speciallist = speciallist;
	}
}

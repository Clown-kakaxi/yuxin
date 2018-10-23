package com.yuchengtech.trans.bo.ecif;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.model.AcrmFCiAddress;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.customer.model.AcrmFCiOrg;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.trans.bo.RequestBody;

public class RequestBody4UpdateOrgCust extends RequestBody {
	private String custNo = null;
	private String txCode = null;
	private String txName = null;
	private String authType = null;
	private String authCode = null;
	private AcrmFCiCustomer customer;
	private AcrmFCiOrg org;
	private AcrmFCiCustIdentifier identifier;
	private AcrmFCiAddress address;
	private AcrmFCiContmeth contmeth;

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

	public AcrmFCiCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(AcrmFCiCustomer customer) {
		this.customer = customer;
	}

	public AcrmFCiOrg getOrg() {
		return org;
	}

	public void setOrg(AcrmFCiOrg org) {
		this.org = org;
	}


	public AcrmFCiCustIdentifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(AcrmFCiCustIdentifier identifier) {
		this.identifier = identifier;
	}

	public AcrmFCiAddress getAddress() {
		return address;
	}

	public void setAddress(AcrmFCiAddress address) {
		this.address = address;
	}

	public AcrmFCiContmeth getContmeth() {
		return contmeth;
	}

	public void setContmeth(AcrmFCiContmeth contmeth) {
		this.contmeth = contmeth;
	}
}

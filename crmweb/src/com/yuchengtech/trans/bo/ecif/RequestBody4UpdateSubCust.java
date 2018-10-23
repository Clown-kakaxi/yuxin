package com.yuchengtech.trans.bo.ecif;

import com.yuchengtech.trans.bo.RequestBody;

/**
 * @author wangtb@yuchengtech.com
 *         潜在客户转为正式客户，包含机构潜在客户和个人潜在客户
 */
public class RequestBody4UpdateSubCust extends RequestBody {
	String custNo;// char(12), 核心系统客户号

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

}

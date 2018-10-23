package com.yuchengtech.trans.bo.ecif;

import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.trans.bo.RequestBody;

/**
 * @author wangtb@yuchengtech.com
 *         修改客户归属信息：归属机构、归属客户经理
 */
public class RequestBody4UpdateBelong extends RequestBody {
	private String custNo;// char(12), 核心系统客户号
	private String txCode;
	private String txName = null;
	private String authType = null;
	private String authCode = null;
	private OcrmFCiBelongOrg belongBranch;
	private OcrmFCiBelongCustmgr belongManager;

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

	public OcrmFCiBelongOrg getBelongBranch() {
		return belongBranch;
	}

	public void setBelongBranch(OcrmFCiBelongOrg belongBranch) {
		this.belongBranch = belongBranch;
	}

	public OcrmFCiBelongCustmgr getBelongManager() {
		return belongManager;
	}

	public void setBelongManager(OcrmFCiBelongCustmgr belongManager) {
		this.belongManager = belongManager;
	}
}

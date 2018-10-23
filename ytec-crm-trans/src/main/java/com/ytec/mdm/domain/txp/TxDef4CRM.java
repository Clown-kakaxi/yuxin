package com.ytec.mdm.domain.txp;

/**
 * The Class TxDef4CRM.
 *
 * @项目名称：ytec-crm-trans
 * @类名称：TxModel
 * @类描述：交易对象模型
 * @功能描述:交易对象模型，用于在内存中缓存整个交易配置信息
 * @创建人：wangtb@yuchengtech.com
 * @创建时间：2014-12-03 上午11:30:18
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 */

public class TxDef4CRM {
	private String code;
	private String name;
	private String cnName;
	private String dealClass;
	private String dealEngine;
	private String cfgTp;
	private String txType;

	public TxDef4CRM() {
		super();
	}

	public TxDef4CRM(String code, String name, String cnName, String dealClass, String dealEngine, String cfgTp, String txType) {
		super();
		this.code = code;
		this.name = name;
		this.cnName = cnName;
		this.dealClass = dealClass;
		this.dealEngine = dealEngine;
		this.cfgTp = cfgTp;
		this.txType = txType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getDealClass() {
		return dealClass;
	}

	public void setDealClass(String dealClass) {
		this.dealClass = dealClass;
	}

	public String getDealEngine() {
		return dealEngine;
	}

	public void setDealEngine(String dealEngine) {
		this.dealEngine = dealEngine;
	}

	public String getCfgTp() {
		return cfgTp;
	}

	public void setCfgTp(String cfgTp) {
		this.cfgTp = cfgTp;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}
}
package com.ytec.mdm.domain.txp;

/**
 * The Class TxDef4CRM.
 *
 * @��Ŀ���ƣ�ytec-crm-trans
 * @�����ƣ�TxModel
 * @�����������׶���ģ��
 * @��������:���׶���ģ�ͣ��������ڴ��л�����������������Ϣ
 * @�����ˣ�wangtb@yuchengtech.com
 * @����ʱ�䣺2014-12-03 ����11:30:18
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
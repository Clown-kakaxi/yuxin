/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.model
 * @�ļ�����TxModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-6-10-10:56:55
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.model;

import java.util.List;
import java.util.Map;

import com.ytec.mdm.domain.txp.TxDef4CRM;
import com.ytec.mdm.domain.txp.TxNode4CRM;
import com.ytec.mdm.domain.txp.TxTabsRel4CRM;

/**
 * The Class TxModel4CRM.
 *
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�TxModel
 * @�����������׶���ģ��
 * @��������:���׶���ģ�ͣ��������ڴ��л�����������������Ϣ
 * @�����ˣ�wangtb@yuchengtech.com
 * @����ʱ�䣺2014-12-03 ����11:30:18
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class TxModel4CRM {
	private TxDef4CRM txDef;
	private List<TxNode4CRM> txNodeList;
	private Map<String,List<TxTabsRel4CRM>> txTabsRelMap;

	public TxDef4CRM getTxDef() {
		return txDef;
	}

	public void setTxDef(TxDef4CRM txDef) {
		this.txDef = txDef;
	}

	public List<TxNode4CRM> getTxNodeList() {
		return txNodeList;
	}

	public void setTxNodeList(List<TxNode4CRM> txNodeList) {
		this.txNodeList = txNodeList;
	}

	public Map<String, List<TxTabsRel4CRM>> getTxTabsRelMap() {
		return txTabsRelMap;
	}

	public void setTxTabsRelMap(Map<String, List<TxTabsRel4CRM>> txTabsRelMap) {
		this.txTabsRelMap = txTabsRelMap;
	}
	
}

/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.model
 * @文件名：TxModel.java
 * @版本信息：1.0.0
 * @日期：2014-6-10-10:56:55
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：TxModel
 * @类描述：交易对象模型
 * @功能描述:交易对象模型，用于在内存中缓存整个交易配置信息
 * @创建人：wangtb@yuchengtech.com
 * @创建时间：2014-12-03 上午11:30:18
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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

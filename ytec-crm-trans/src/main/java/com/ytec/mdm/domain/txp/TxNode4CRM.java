package com.ytec.mdm.domain.txp;

import java.util.List;

/**
 * The Class TxNode4CRM.
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

public class TxNode4CRM {
	private String name;
	private String table;
	private String filter;
	private String condition;
	private String conditionValue;
	private boolean list;
	private String xpath;
	private List<String> respNodes;

	public TxNode4CRM() {
		super();
	}

	public TxNode4CRM(String name, String table, String filter, String condition, String conditionValue, boolean list, String xpath, List<String> respNodes) {
		super();
		this.name = name;
		this.table = table;
		this.filter = filter;
		this.condition = condition;
		this.conditionValue = conditionValue;
		this.list = list;
		this.xpath = xpath;
		this.respNodes = respNodes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public boolean isList() {
		return list;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public List<String> getRespNodes() {
		return respNodes;
	}

	public void setRespNodes(List<String> respNodes) {
		this.respNodes = respNodes;
	}
}
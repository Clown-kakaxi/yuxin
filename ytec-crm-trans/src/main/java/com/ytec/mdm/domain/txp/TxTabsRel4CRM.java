package com.ytec.mdm.domain.txp;

public class TxTabsRel4CRM {
	private String nodeName;
	private String table;// 节点对应表所关联的表
	private String condition;// 关联表查询条件，从请求报文中获取参数
	private String[] key_s;// 表节点对应表与所关联表之间的关联主键

	public TxTabsRel4CRM() {
		super();
	}

	public TxTabsRel4CRM(String nodeName, String table, String condition, String[] keyMap) {
		super();
		this.nodeName = nodeName;
		this.table = table;
		this.condition = condition;
		this.key_s = keyMap;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String[] getKey_s() {
		return key_s;
	}

	public void setKey_s(String[] key_s) {
		this.key_s = key_s;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String[] getKeyMap() {
		return key_s;
	}

	public void setKeyMap(String[] keyMap) {
		this.key_s = keyMap;
	}

	@Override
	public String toString() {
		return "nodeName{" + nodeName + "}" + ", table{" + table + "}" + ", condition{" + condition + "}" + ", key_s{" + key_s + "}";
	}
}

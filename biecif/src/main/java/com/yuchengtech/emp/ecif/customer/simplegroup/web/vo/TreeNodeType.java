package com.yuchengtech.emp.ecif.customer.simplegroup.web.vo;

public class TreeNodeType {
	
	private String id;  //节点Id
	private String name;  //名称
	private String level;  //节点等级
	private String upId;
	private String isParent;  //是否父节点
	private String realId;  //数据库唯一标识
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
	public String getRealId() {
		return realId;
	}
	public void setRealId(String realId) {
		this.realId = realId;
	}
	public String getUpId() {
		return upId;
	}
	public void setUpId(String upId) {
		this.upId = upId;
	}
	
}

package com.yuchengtech.emp.bione.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Title : 通用的树形节点类
 * Description : 程序功能的描述
 * @author
 * @version 1.0
 */
public class CommonTreeNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 706621550112134159L;

	private String id ; // 节点id
	private String upId ; // 上级节点Id
	private String icon ; // 节点图标
	private String text ; // 节点名称
	private boolean isParent = false; // 当前节点是否是父节点
	private boolean ischecked = false; // 当前节点是否选中
	private boolean isexpand = false; //是否展开
	private Object data ; // 节点对应的数据对象
		
	/* 子节点集合 */
	private List<CommonTreeNode> children = null;
	
	/* 参数 */
	private Map<String, String> params = Maps.newHashMap();
	
	/* 默认父节点ID */
	public static String ROOT_ID = "0";

	/**
	 * 添加子节点
	 * 
	 * @param childNode
	 */
	public void addChildNode(CommonTreeNode childNode) {

		if (this.children == null)
			this.children = new ArrayList<CommonTreeNode>();

		this.children.add(childNode);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpId() {
		return upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<CommonTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<CommonTreeNode> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public boolean isIsexpand() {
		return isexpand;
	}

	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
}

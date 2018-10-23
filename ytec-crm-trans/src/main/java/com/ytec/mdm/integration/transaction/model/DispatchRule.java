/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.model
 * @文件名：DispatchRule.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:27:17
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import java.util.List;
import java.util.Map;

import com.ytec.mdm.integration.transaction.facade.ICaseDispatch;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：DispatchRule
 * @类描述：组合交易流程模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:27:29   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:27:29
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class DispatchRule {
	
	/**
	 * The rule type.
	 * 
	 * @属性描述:流程规则
	 */
	private String ruleType;
	
	/**
	 * The bean class.
	 * 
	 * @属性描述:判别函数
	 */
	private ICaseDispatch beanClass;
	
	/**
	 * The node list.
	 * 
	 * @属性描述:分发结点
	 */
	private List<DispatchNode> nodeList;
	
	/**
	 * The node map.
	 * 
	 * @属性描述:结点关系
	 */
	private Map<String,DispatchNode> nodeMap;
	
	/**
	 * Gets the rule type.
	 * 
	 * @return the rule type
	 */
	public String getRuleType() {
		return ruleType;
	}
	
	/**
	 * Sets the rule type.
	 * 
	 * @param ruleType
	 *            the new rule type
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	/**
	 * Gets the bean class.
	 * 
	 * @return the bean class
	 */
	public ICaseDispatch getBeanClass() {
		return beanClass;
	}
	
	/**
	 * Sets the bean class.
	 * 
	 * @param beanClass
	 *            the new bean class
	 */
	public void setBeanClass(ICaseDispatch beanClass) {
		this.beanClass = beanClass;
	}
	
	/**
	 * Gets the node list.
	 * 
	 * @return the node list
	 */
	public List<DispatchNode> getNodeList() {
		return nodeList;
	}
	
	/**
	 * Sets the node list.
	 * 
	 * @param nodeList
	 *            the new node list
	 */
	public void setNodeList(List<DispatchNode> nodeList) {
		this.nodeList = nodeList;
	}
	
	/**
	 * Gets the node map.
	 * 
	 * @return the node map
	 */
	public Map<String, DispatchNode> getNodeMap() {
		return nodeMap;
	}
	
	/**
	 * Sets the node map.
	 * 
	 * @param nodeMap
	 *            the node map
	 * @函数名称:void setNodeMap(Map<String,DispatchNode> nodeMap)
	 * @函数描述:
	 * @参数与返回说明: void setNodeMap(Map<String,DispatchNode> nodeMap)
	 * @算法描述:
	 */
	public void setNodeMap(Map<String, DispatchNode> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
	/**
	 * Gets the node map value.
	 * 
	 * @param key
	 *            the key
	 * @return the node map value
	 */
	public DispatchNode getNodeMapValue(String key){
		return nodeMap.get(key);
	}
}

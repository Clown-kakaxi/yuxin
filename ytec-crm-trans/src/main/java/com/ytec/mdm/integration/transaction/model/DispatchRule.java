/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.model
 * @�ļ�����DispatchRule.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:27:17
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import java.util.List;
import java.util.Map;

import com.ytec.mdm.integration.transaction.facade.ICaseDispatch;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DispatchRule
 * @����������Ͻ�������ģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:27:29   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:27:29
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class DispatchRule {
	
	/**
	 * The rule type.
	 * 
	 * @��������:���̹���
	 */
	private String ruleType;
	
	/**
	 * The bean class.
	 * 
	 * @��������:�б���
	 */
	private ICaseDispatch beanClass;
	
	/**
	 * The node list.
	 * 
	 * @��������:�ַ����
	 */
	private List<DispatchNode> nodeList;
	
	/**
	 * The node map.
	 * 
	 * @��������:����ϵ
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
	 * @��������:void setNodeMap(Map<String,DispatchNode> nodeMap)
	 * @��������:
	 * @�����뷵��˵��: void setNodeMap(Map<String,DispatchNode> nodeMap)
	 * @�㷨����:
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

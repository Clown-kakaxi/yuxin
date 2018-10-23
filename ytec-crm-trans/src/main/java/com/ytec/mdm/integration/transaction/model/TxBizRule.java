/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.model
 * @�ļ�����TxBizRule.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:28:45
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import com.ytec.mdm.integration.transaction.facade.IModelFilter;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxBizRule
 * @������������ģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:28:57   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:28:57
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxBizRule {
	
	/**
	 * The rule no.
	 * 
	 * @��������:������
	 */
	private String ruleNo;
	
	/**
	 * The rule name.
	 * 
	 * @��������:��������
	 */
	private String ruleName;
	
	/**
	 * The rule def type.
	 * 
	 * @��������:��������
	 */
	private String ruleDefType;
	
	/**
	 * The msg node filter.
	 * 
	 * @��������:���˺���
	 */
	private IMsgNodeFilter msgNodeFilter;
	
	/**
	 * @��������:modelFilter
	 * @��������:д����ģ�͹���
	 * @since 1.0.0
	 */
	private IModelFilter modelFilter;
	
	/**
	 * The rule expr.
	 * 
	 * @��������:������ʽ
	 */
	private Object ruleExpr;
	
	/**
	 * The rule desc.
	 * 
	 * @��������:��������
	 */
	private String  ruleDesc;
	
	/**
	 * @��������:ruleIntfType
	 * @��������:�ӿ�����
	 * @since 1.0.0
	 */
	private String ruleIntfType;
	
	/**
	 * Gets the rule no.
	 * 
	 * @return the rule no
	 */
	public String getRuleNo() {
		return ruleNo;
	}
	
	/**
	 * Sets the rule no.
	 * 
	 * @param ruleNo
	 *            the new rule no
	 */
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	
	/**
	 * Gets the rule name.
	 * 
	 * @return the rule name
	 */
	public String getRuleName() {
		return ruleName;
	}
	
	/**
	 * Sets the rule name.
	 * 
	 * @param ruleName
	 *            the new rule name
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	/**
	 * Gets the rule def type.
	 * 
	 * @return the rule def type
	 */
	public String getRuleDefType() {
		return ruleDefType;
	}
	
	/**
	 * Sets the rule def type.
	 * 
	 * @param ruleDefType
	 *            the new rule def type
	 */
	public void setRuleDefType(String ruleDefType) {
		this.ruleDefType = ruleDefType;
	}
	
	/**
	 * Gets the msg node filter.
	 * 
	 * @return the msg node filter
	 */
	public IMsgNodeFilter getMsgNodeFilter() {
		return msgNodeFilter;
	}
	
	/**
	 * Sets the msg node filter.
	 * 
	 * @param msgNodeFilter
	 *            the new msg node filter
	 */
	public void setMsgNodeFilter(IMsgNodeFilter msgNodeFilter) {
		this.msgNodeFilter = msgNodeFilter;
	}
	
	/**
	 * Gets the rule expr.
	 * 
	 * @return the rule expr
	 */
	public Object getRuleExpr() {
		return ruleExpr;
	}
	
	/**
	 * Sets the rule expr.
	 * 
	 * @param ruleExpr
	 *            the new rule expr
	 */
	public void setRuleExpr(Object ruleExpr) {
		this.ruleExpr = ruleExpr;
	}
	
	/**
	 * Gets the rule desc.
	 * 
	 * @return the rule desc
	 */
	public String getRuleDesc() {
		return ruleDesc;
	}
	
	/**
	 * Sets the rule desc.
	 * 
	 * @param ruleDesc
	 *            the new rule desc
	 */
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public IModelFilter getModelFilter() {
		return modelFilter;
	}

	public void setModelFilter(IModelFilter modelFilter) {
		this.modelFilter = modelFilter;
	}

	public String getRuleIntfType() {
		return ruleIntfType;
	}

	public void setRuleIntfType(String ruleIntfType) {
		this.ruleIntfType = ruleIntfType;
	}
	
	
	
}

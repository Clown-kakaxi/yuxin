/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.bo
 * @�ļ�����TxCustIdentifRule.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:55:17
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.bo;

import com.ytec.mdm.service.facade.IBizGetContId;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxCustIdentifRule
 * @���������ͻ�ʶ�����ģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:55:17   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:55:17
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxCustIdentifRule {
	/**
	 * @��������:ruleNo
	 * @��������:�����
	 * @since 1.0.0
	 */
	private String ruleNo;
	/**
	 * @��������:ruleName
	 * @��������:��������
	 * @since 1.0.0
	 */
	private String ruleName;
	/**
	 * @��������:ruleDefType
	 * @��������:��������
	 * @since 1.0.0
	 */
	private String ruleDefType;
	/**
	 * @��������:bizGetContId
	 * @��������:�ͻ�ʶ��ӿ�
	 * @since 1.0.0
	 */
	private IBizGetContId bizGetContId;
	/**
	 * @��������:ruleExpr
	 * @��������:������ʽ
	 * @since 1.0.0
	 */
	private String ruleExpr;
	/**
	 * @��������:ruleDesc
	 * @��������:��������
	 * @since 1.0.0
	 */
	private String  ruleDesc;
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDefType() {
		return ruleDefType;
	}
	public void setRuleDefType(String ruleDefType) {
		this.ruleDefType = ruleDefType;
	}
	public IBizGetContId getBizGetContId() {
		return bizGetContId;
	}
	public void setBizGetContId(IBizGetContId bizGetContId) {
		this.bizGetContId = bizGetContId;
	}
	public String getRuleExpr() {
		return ruleExpr;
	}
	public void setRuleExpr(String ruleExpr) {
		this.ruleExpr = ruleExpr;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	
	
}

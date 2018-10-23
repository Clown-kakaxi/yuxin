/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.model
 * @�ļ�����DispatchNode.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-6-10-10:56:05
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import com.ytec.mdm.integration.transaction.facade.IDispatchFun;

/**
 * The Class DispatchNode.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�DispatchNode
 * @����������Ͻ��׽��ģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:24:10
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:24:10
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class DispatchNode {
	
	/**
	 * The exe type.
	 * 
	 * @��������:exeType
	 * @��������:ִ������
	 * @since 1.0.0
	 */
	private String exeType; 
	
	/**
	 * The tx code.
	 * 
	 * @��������:txCode
	 * @��������:������
	 * @since 1.0.0
	 */
	private String txCode;
	
	/**
	 * The order step.
	 * 
	 * @��������:orderStep
	 * @��������:ִ��˳���
	 * @since 1.0.0
	 */
	private int orderStep;
	
	/**
	 * The rule.
	 * 
	 * @��������:rule
	 * @��������:�ַ�����
	 * @since 1.0.0
	 */
	private DispatchRule rule;
	
	/**
	 * The bean class.
	 * 
	 * @��������:beanClass
	 * @��������:ԭ�ӹ��ܺ���
	 * @since 1.0.0
	 */
	private Class beanClass;
	
	/**
	 * The result paramet path.
	 * 
	 * @��������:resultParametPath
	 * @��������:���ز���XPATH
	 * @since 1.0.0
	 */
	private String resultParametPath;
	
	/**
	 * The is return.
	 * 
	 * @��������:isReturn
	 * @��������:�Ƿ񽫴˴η�����Ϊ���շ���
	 * @since 1.0.0
	 */
	private boolean isReturn;
	
	/**
	 * Gets the exe type.
	 * 
	 * @return the exe type
	 */
	public String getExeType() {
		return exeType;
	}
	
	/**
	 * Sets the exe type.
	 * 
	 * @param exeType
	 *            the new exe type
	 */
	public void setExeType(String exeType) {
		this.exeType = exeType;
	}
	
	/**
	 * Gets the tx code.
	 * 
	 * @return the tx code
	 */
	public String getTxCode() {
		return txCode;
	}
	
	/**
	 * Sets the tx code.
	 * 
	 * @param txCode
	 *            the new tx code
	 */
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	
	/**
	 * Gets the rule.
	 * 
	 * @return the rule
	 */
	public DispatchRule getRule() {
		return rule;
	}
	
	/**
	 * Sets the rule.
	 * 
	 * @param rule
	 *            the new rule
	 */
	public void setRule(DispatchRule rule) {
		this.rule = rule;
	}
	
	/**
	 * Gets the bean class.
	 * 
	 * @return the bean class
	 */
	public IDispatchFun getBeanClass() {
		try {
			return (IDispatchFun)beanClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Sets the bean class.
	 * 
	 * @param beanClass
	 *            the new bean class
	 */
	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}
	
	/**
	 * Gets the order step.
	 * 
	 * @return the order step
	 */
	public int getOrderStep() {
		return orderStep;
	}
	
	/**
	 * Sets the order step.
	 * 
	 * @param orderStep
	 *            the new order step
	 */
	public void setOrderStep(int orderStep) {
		this.orderStep = orderStep;
	}
	
	/**
	 * Gets the result paramet path.
	 * 
	 * @return the result paramet path
	 */
	public String getResultParametPath() {
		return resultParametPath;
	}
	
	/**
	 * Sets the result paramet path.
	 * 
	 * @param resultParametPath
	 *            the new result paramet path
	 */
	public void setResultParametPath(String resultParametPath) {
		this.resultParametPath = resultParametPath;
	}
	
	/**
	 * Checks if is return.
	 * 
	 * @return true, if checks if is return
	 * @��������:boolean isReturn()
	 * @��������:
	 * @�����뷵��˵��: boolean isReturn()
	 * @�㷨����:
	 */
	public boolean isReturn() {
		return isReturn;
	}
	
	/**
	 * Sets the return.
	 * 
	 * @param isReturn
	 *            the new return
	 */
	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}

}

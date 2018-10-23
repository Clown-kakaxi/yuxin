/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.model
 * @文件名：DispatchNode.java
 * @版本信息：1.0.0
 * @日期：2014-6-10-10:56:05
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import com.ytec.mdm.integration.transaction.facade.IDispatchFun;

/**
 * The Class DispatchNode.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称：DispatchNode
 * @类描述：组合交易结点模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:24:10
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:24:10
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class DispatchNode {
	
	/**
	 * The exe type.
	 * 
	 * @属性名称:exeType
	 * @属性描述:执行类型
	 * @since 1.0.0
	 */
	private String exeType; 
	
	/**
	 * The tx code.
	 * 
	 * @属性名称:txCode
	 * @属性描述:交易码
	 * @since 1.0.0
	 */
	private String txCode;
	
	/**
	 * The order step.
	 * 
	 * @属性名称:orderStep
	 * @属性描述:执行顺序号
	 * @since 1.0.0
	 */
	private int orderStep;
	
	/**
	 * The rule.
	 * 
	 * @属性名称:rule
	 * @属性描述:分发规则
	 * @since 1.0.0
	 */
	private DispatchRule rule;
	
	/**
	 * The bean class.
	 * 
	 * @属性名称:beanClass
	 * @属性描述:原子功能函数
	 * @since 1.0.0
	 */
	private Class beanClass;
	
	/**
	 * The result paramet path.
	 * 
	 * @属性名称:resultParametPath
	 * @属性描述:返回参数XPATH
	 * @since 1.0.0
	 */
	private String resultParametPath;
	
	/**
	 * The is return.
	 * 
	 * @属性名称:isReturn
	 * @属性描述:是否将此次返回作为最终返回
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
	 * @函数名称:boolean isReturn()
	 * @函数描述:
	 * @参数与返回说明: boolean isReturn()
	 * @算法描述:
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

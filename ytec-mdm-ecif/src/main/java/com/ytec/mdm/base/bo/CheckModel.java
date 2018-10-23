/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����CheckModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-9:40:48
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.base.bo;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class CheckModel.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�CheckModel
 * @�����������У��ģ��
 * @��������:���У������ģ��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-16 ����8:21:13
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-16 ����8:21:13
 * @�޸ı�ע��
 * @�޸�����            �޸���Ա               �޸�ԭ��
 *  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class CheckModel {
	
	/**
	 * The class name.
	 * 
	 * @��������:������
	 */
	private String className;
	
	/**
	 * The check code colum.
	 * 
	 * @��������:У�����ֵ����
	 */
	private String checkCodeColum;
	
	/**
	 * The check col name.
	 * 
	 * @��������:��ҪУ�����������
	 */
	private String checkColName;
	
	/**
	 * The check rule.
	 * 
	 * @��������:У�����
	 */
	Map<String,String> checkRule=new HashMap<String,String>();
	
	
	
	
	/**
	 *@���캯�� 
	 */
	public CheckModel() {
	}
	
	
	/**
	 *@���캯�� 
	 * @param className
	 * @param checkCodeColum
	 * @param checkColName
	 */
	public CheckModel(String className, String checkCodeColum,
			String checkColName) {
		super();
		this.className = className;
		this.checkCodeColum = checkCodeColum;
		this.checkColName = checkColName;
	}

	
	/**
	 * @��������:addCheckRule
	 * @��������:����У�����
	 * @�����뷵��˵��:
	 * 		@param rulerCode У����
	 * 		@param ctRule    У�����
	 * @�㷨����:
	 */
	public void addCheckRule(String rulerCode,String ctRule){
		if(rulerCode!=null &&!rulerCode.isEmpty() &&ctRule!=null &&!ctRule.isEmpty()){
			this.checkRule.put(rulerCode, ctRule);
		}
	}
	
	/**
	 * Gets the check rule.
	 * 
	 * @param rulerCode
	 *            the ruler code
	 * @return the check rule
	 */
	public String getCheckRule(String rulerCode){
		if(rulerCode!=null &&!rulerCode.isEmpty()){
			return this.checkRule.get(rulerCode);
		}else{
			return null;
		}
	}
	
	/**
	 * Gets the class name.
	 * 
	 * @return the class name
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * Sets the class name.
	 * 
	 * @param className
	 *            the new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * Gets the check code colum.
	 * 
	 * @return the check code colum
	 */
	public String getCheckCodeColum() {
		return checkCodeColum;
	}
	
	/**
	 * Sets the check code colum.
	 * 
	 * @param checkCodeColum
	 *            the new check code colum
	 */
	public void setCheckCodeColum(String checkCodeColum) {
		this.checkCodeColum = checkCodeColum;
	}
	
	/**
	 * Gets the check col name.
	 * 
	 * @return the check col name
	 */
	public String getCheckColName() {
		return checkColName;
	}
	
	/**
	 * Sets the check col name.
	 * 
	 * @param checkColName
	 *            the new check col name
	 */
	public void setCheckColName(String checkColName) {
		this.checkColName = checkColName;
	}
	
	/**
	 * Gets the check rule.
	 * 
	 * @return the check rule
	 */
	public Map<String, String> getCheckRule() {
		return checkRule;
	}
	
	
	/**
	 * @��������:setCheckRule
	 * @��������:����У�����
	 * @�����뷵��˵��:
	 * 		@param checkRule
	 * @�㷨����:
	 */
	public void setCheckRule(Map<String, String> checkRule) {
		this.checkRule = checkRule;
	}
	

}

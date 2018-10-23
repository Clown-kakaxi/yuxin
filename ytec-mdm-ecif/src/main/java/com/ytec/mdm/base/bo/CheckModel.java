/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：CheckModel.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-9:40:48
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.bo;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class CheckModel.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称：CheckModel
 * @类描述：组合校验模型
 * @功能描述:组合校验配置模型
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-16 下午8:21:13
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-16 下午8:21:13
 * @修改备注：
 * @修改日期            修改人员               修改原因
 *  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class CheckModel {
	
	/**
	 * The class name.
	 * 
	 * @属性描述:类名称
	 */
	private String className;
	
	/**
	 * The check code colum.
	 * 
	 * @属性描述:校验的码值属性
	 */
	private String checkCodeColum;
	
	/**
	 * The check col name.
	 * 
	 * @属性描述:需要校验的数据属性
	 */
	private String checkColName;
	
	/**
	 * The check rule.
	 * 
	 * @属性描述:校验规则
	 */
	Map<String,String> checkRule=new HashMap<String,String>();
	
	
	
	
	/**
	 *@构造函数 
	 */
	public CheckModel() {
	}
	
	
	/**
	 *@构造函数 
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
	 * @函数名称:addCheckRule
	 * @函数描述:增加校验规则
	 * @参数与返回说明:
	 * 		@param rulerCode 校验码
	 * 		@param ctRule    校验规则
	 * @算法描述:
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
	 * @函数名称:setCheckRule
	 * @函数描述:设置校验规则
	 * @参数与返回说明:
	 * 		@param checkRule
	 * @算法描述:
	 */
	public void setCheckRule(Map<String, String> checkRule) {
		this.checkRule = checkRule;
	}
	

}

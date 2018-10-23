/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.bo
 * @文件名：TxCustIdentifRule.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:55:17
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.bo;

import com.ytec.mdm.service.facade.IBizGetContId;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxCustIdentifRule
 * @类描述：客户识别规则模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:55:17   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:55:17
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxCustIdentifRule {
	/**
	 * @属性名称:ruleNo
	 * @属性描述:规则号
	 * @since 1.0.0
	 */
	private String ruleNo;
	/**
	 * @属性名称:ruleName
	 * @属性描述:规则名称
	 * @since 1.0.0
	 */
	private String ruleName;
	/**
	 * @属性名称:ruleDefType
	 * @属性描述:规则类型
	 * @since 1.0.0
	 */
	private String ruleDefType;
	/**
	 * @属性名称:bizGetContId
	 * @属性描述:客户识别接口
	 * @since 1.0.0
	 */
	private IBizGetContId bizGetContId;
	/**
	 * @属性名称:ruleExpr
	 * @属性描述:规则表达式
	 * @since 1.0.0
	 */
	private String ruleExpr;
	/**
	 * @属性名称:ruleDesc
	 * @属性描述:规则描述
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

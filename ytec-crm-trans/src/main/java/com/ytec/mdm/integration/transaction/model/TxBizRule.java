/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.model
 * @文件名：TxBizRule.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:28:45
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import com.ytec.mdm.integration.transaction.facade.IModelFilter;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxBizRule
 * @类描述：规则模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:28:57   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:28:57
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxBizRule {
	
	/**
	 * The rule no.
	 * 
	 * @属性描述:规则码
	 */
	private String ruleNo;
	
	/**
	 * The rule name.
	 * 
	 * @属性描述:规则名称
	 */
	private String ruleName;
	
	/**
	 * The rule def type.
	 * 
	 * @属性描述:规则类型
	 */
	private String ruleDefType;
	
	/**
	 * The msg node filter.
	 * 
	 * @属性描述:过滤函数
	 */
	private IMsgNodeFilter msgNodeFilter;
	
	/**
	 * @属性名称:modelFilter
	 * @属性描述:写交易模型过滤
	 * @since 1.0.0
	 */
	private IModelFilter modelFilter;
	
	/**
	 * The rule expr.
	 * 
	 * @属性描述:规则表达式
	 */
	private Object ruleExpr;
	
	/**
	 * The rule desc.
	 * 
	 * @属性描述:规则描述
	 */
	private String  ruleDesc;
	
	/**
	 * @属性名称:ruleIntfType
	 * @属性描述:接口类型
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

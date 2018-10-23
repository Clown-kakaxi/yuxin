/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：CheckByExpression.java
 * @版本信息：1.0.0
 * @日期：2014-3-28-上午9:57:09
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.bs;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.integration.transaction.facade.IModelFilter;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CheckByExpression
 * @类描述：通过规则表达式验证数据,返回boolean
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-3-28 上午9:57:09   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-3-28 上午9:57:09
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CheckByExpression implements IModelFilter {
	private Logger log = LoggerFactory
			.getLogger(CheckByExpression.class);
	/**
	 * @属性名称:variableNames
	 * @属性描述:参数名称
	 * @since 1.0.0
	 */
	private String[] variableNames;
	/**
	 * @属性名称:expression
	 * @属性描述:规则表达式
	 * @since 1.0.0
	 */
	private String expression;

	/**
	 *@构造函数 
	 */
	public CheckByExpression() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IModelFilter#execute(com.ytec.mdm.base.bo.EcifData, java.lang.Object)
	 */
	@Override
	public boolean execute(EcifData ecifData, Object entity) {
		// TODO Auto-generated method stub
		try{
			List<Variable> variables = new LinkedList<Variable>();
			Object ob;
			if(variableNames!=null){
				for(String variableName:variableNames){
					try{
						ob=ReflectionUtils.getFieldValue(entity,variableName);
						variables.add(Variable.createVariable(variableName,ob));
					}catch(Exception e){
						log.error("错误信息",e);
						continue;
					}
				}
			}
			Object result=ExpressionEvaluator.evaluate(expression, variables);
			if(result instanceof Boolean){
		    	return (Boolean)result;
			}else{
				log.error("验证返回类型不是boolean{}",result.getClass());
		    }
		}catch(Exception e){
			log.error("表达式验证异常",e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IModelFilter#init(java.lang.String)
	 */
	@Override
	public void init(String ruleExpr) {
		// TODO Auto-generated method stub
		this.expression=ruleExpr;
		/***获取表达式中的参数名称**/
		this.variableNames=ExpressionEvaluator.getVariables(ruleExpr);
	}
}

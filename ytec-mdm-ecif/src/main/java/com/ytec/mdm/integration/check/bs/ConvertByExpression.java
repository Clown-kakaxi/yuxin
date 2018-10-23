/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：ConvertByExpression.java
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
 * @类名称：ConvertByExpression
 * @类描述：通过规则表达式验证数据,并且转换数据,将转换后的数据赋值回去，返回boolean
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
public class ConvertByExpression implements IModelFilter {
	private Logger log = LoggerFactory
			.getLogger(ConvertByExpression.class);
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
	 * @属性名称:setVariableName
	 * @属性描述:需要赋值的字段名称
	 * @since 1.0.0
	 */
	private String setVariableName;

	/**
	 *@构造函数 
	 */
	public ConvertByExpression() {
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
						if("ecifData".equals(variableName)){
							variables.add(Variable.createVariable(variableName,ecifData));
						}else{
							ob=ReflectionUtils.getFieldValue(entity,variableName);
							variables.add(Variable.createVariable(variableName,ob));
						}
					}catch(Exception e){
						log.error("错误信息",e);
						continue;
					}
				}
			}
			Object result=ExpressionEvaluator.evaluate(expression, variables);
			if (!ecifData.isSuccess()) {
				log.warn("数据组合验证失败:{}",ecifData.getDetailDes());
				return false;
			}
			if (!"null".equals(result)||result!=null) {
				ReflectionUtils.setFieldValue(entity,setVariableName, result);
			}
			return true;
		}catch(Exception e){
			log.error("表达式验证转换异常",e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IModelFilter#init(java.lang.String)
	 */
	@Override
	public void init(String ruleExpr) {
		// TODO Auto-generated method stub
		//"setVariableName = X==\"oqwq\"? $DOFILTER(ecifDate,\"T1\",\"we\",Y):null";
		int index=ruleExpr.indexOf('=');
	
		setVariableName=ruleExpr.substring(0, index).trim();
		this.expression=ruleExpr.substring(index+1).trim();
		/***获取表达式中的参数名称**/
		this.variableNames=ExpressionEvaluator.getVariables(this.expression);
	}
//	public static void main(String args[]){
//		String ruleExpr="identNo = identType==\"13\"? $DOFILTER(ecifData,\"T03\",\"identNo\",identNo) : null";
//		ConvertByExpression dd=new ConvertByExpression();
//		dd.init(ruleExpr);
//		EcifData ecifData= new EcifData();
//		MCiPerIdentifier entity=new MCiPerIdentifier();
//		
//		entity.setIdentNo("420581198704050031");
//		entity.setIdentType("13");
//		MdmConstants.INFORCHECKCONVERSION="cstinfcheckrule";
//		TxBizRuleFactory.init();
//		dd.execute(ecifData, entity); 
//	}
	
}

/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����CheckByExpression.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-28-����9:57:09
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CheckByExpression
 * @��������ͨ��������ʽ��֤����,����boolean
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-28 ����9:57:09   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-28 ����9:57:09
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CheckByExpression implements IModelFilter {
	private Logger log = LoggerFactory
			.getLogger(CheckByExpression.class);
	/**
	 * @��������:variableNames
	 * @��������:��������
	 * @since 1.0.0
	 */
	private String[] variableNames;
	/**
	 * @��������:expression
	 * @��������:������ʽ
	 * @since 1.0.0
	 */
	private String expression;

	/**
	 *@���캯�� 
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
						log.error("������Ϣ",e);
						continue;
					}
				}
			}
			Object result=ExpressionEvaluator.evaluate(expression, variables);
			if(result instanceof Boolean){
		    	return (Boolean)result;
			}else{
				log.error("��֤�������Ͳ���boolean{}",result.getClass());
		    }
		}catch(Exception e){
			log.error("���ʽ��֤�쳣",e);
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
		/***��ȡ���ʽ�еĲ�������**/
		this.variableNames=ExpressionEvaluator.getVariables(ruleExpr);
	}
}

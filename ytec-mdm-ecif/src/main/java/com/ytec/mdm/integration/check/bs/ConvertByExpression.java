/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����ConvertByExpression.java
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
 * @�����ƣ�ConvertByExpression
 * @��������ͨ��������ʽ��֤����,����ת������,��ת��������ݸ�ֵ��ȥ������boolean
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
public class ConvertByExpression implements IModelFilter {
	private Logger log = LoggerFactory
			.getLogger(ConvertByExpression.class);
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
	 * @��������:setVariableName
	 * @��������:��Ҫ��ֵ���ֶ�����
	 * @since 1.0.0
	 */
	private String setVariableName;

	/**
	 *@���캯�� 
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
						log.error("������Ϣ",e);
						continue;
					}
				}
			}
			Object result=ExpressionEvaluator.evaluate(expression, variables);
			if (!ecifData.isSuccess()) {
				log.warn("���������֤ʧ��:{}",ecifData.getDetailDes());
				return false;
			}
			if (!"null".equals(result)||result!=null) {
				ReflectionUtils.setFieldValue(entity,setVariableName, result);
			}
			return true;
		}catch(Exception e){
			log.error("���ʽ��֤ת���쳣",e);
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
		/***��ȡ���ʽ�еĲ�������**/
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

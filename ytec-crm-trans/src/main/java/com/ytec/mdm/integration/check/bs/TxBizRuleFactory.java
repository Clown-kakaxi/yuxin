/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����TxBizRuleFactory.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:46:24
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.check.bs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IModelFilter;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;
import com.ytec.mdm.integration.transaction.model.TxBizRule;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�TxBizRuleFactory
 * @����������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:46:25
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:46:25
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public class TxBizRuleFactory {
	private static Logger log = LoggerFactory.getLogger(TxBizRuleFactory.class);
	private static Map<String,TxBizRule> txBizRuleConfMap=new HashMap<String,TxBizRule>();
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public static void init(){
		txBizRuleConfMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Object[]> txBizRuleList=baseDAO.findByNativeSQLWithIndexParam("SELECT T.RULE_NO,T.RULE_NAME,T.RULE_DEF_TYPE,T.RULE_DEAL_TYPE" +
				",T.RULE_PKG_PATH,T.RULE_DEAL_CLASS,T.RULE_EXPR,T.RULE_EXPR_DESC FROM TX_BIZ_RULE_CONF T,TX_BIZ_RULE_GROUP G WHERE G.RULE_GROUP_NO=? and T.RULE_GROUP_ID=G.RULE_GROUP_ID AND RULE_STAT='1'",MdmConstants.INFORCHECKCONVERSION);
		if(txBizRuleList!=null &&!txBizRuleList.isEmpty()){
			Class clazz=null;
			IMsgNodeFilter cc0=null;
			IModelFilter cc1=null;
			String szInterface0=IMsgNodeFilter.class.getName();
			String szInterface1=IModelFilter.class.getName();
			for(Object[] objs:txBizRuleList){
				TxBizRule txBizRule=new TxBizRule();
				if(StringUtil.isEmpty(objs[0])){
					log.error("�������Ϊ��");
					continue;
				}
				txBizRule.setRuleNo((String)objs[0]);
				txBizRule.setRuleName((String)objs[1]);
				txBizRule.setRuleDefType((String)objs[2]);
				if(objs[4]==null||objs[5]==null){
					log.error("������������Ϊ��");
					continue;
				}

				if("1".equals(objs[3])){
					if(!StringUtil.isEmpty(objs[6])){
						txBizRule.setRuleExpr(Pattern.compile((String)objs[6]));
					}else{
						log.error("����{}�ı��ʽΪ��",(String)objs[0]);
						continue;
					}
				}else{
					if(!StringUtil.isEmpty(objs[6])){
						txBizRule.setRuleExpr((String)objs[6]);
					}
				}

				String className=objs[4].toString()+"."+objs[5].toString();
				try{
					clazz = Class.forName(className);
					if(ReflectionUtils.isInterface(clazz, szInterface0) ){
						cc0=(IMsgNodeFilter)clazz.newInstance();
						txBizRule.setMsgNodeFilter(cc0);
						txBizRule.setRuleIntfType("IMsgNodeFilter");
					}else if(ReflectionUtils.isInterface(clazz, szInterface1) ){
						cc1=(IModelFilter)clazz.newInstance();
						cc1.init((String)objs[6]);
						txBizRule.setModelFilter(cc1);
						txBizRule.setRuleIntfType("IModelFilter");
					}else{
						log.error("��������({}),û��ʵ��IMsgNodeFilter��IModelFilter�ӿ�",className);
						continue;
					}
				}catch(Exception e){
					log.error("��ʼ�����ù����������",e);
					continue;
				}

				txBizRule.setRuleDesc((String)objs[7]);
				txBizRuleConfMap.put(txBizRule.getRuleNo(), txBizRule);
			}
		}

	}
	/**
	 * @��������:doFilter
	 * @��������:��Ϣ����
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param ruler    ����
	 * 		@param attrName ��������
	 * 		@param value   ֵ
	 * 		@param params ����
	 * 		@return
	 * @�㷨����:
	 */
	public static String doFilter(EcifData ecifData, String ruler,
			String attrName, String value, String... params) {
		TxBizRule txBizRule = null;
		if (ruler != null) {
			String[] rulers = ruler.split("\\,");
			for (String ruler_i : rulers) {
				if ((txBizRule = txBizRuleConfMap.get(ruler_i)) != null) {
					if (txBizRule.getMsgNodeFilter() != null) {
						String tempv = value;
						value = txBizRule.getMsgNodeFilter().execute(
								txBizRule.getRuleExpr(), value, params);
						if (value == null) {
							log.warn("����{}:����:{},ͨ��У��ת������{}({})ʧ��", attrName,
									tempv, ruler_i, txBizRule.getRuleName());
							if (StringUtil.isEmpty(txBizRule.getRuleDesc())) {
								ecifData.setStatus(
										ErrorCode.ERR_RULE_CHECK_TRANSFORM_ERROR
												.getCode(), String.format(
												"%s:%sʧ��", attrName,
												txBizRule.getRuleName()));
							} else {
								ecifData.setStatus(
										ErrorCode.ERR_RULE_CHECK_TRANSFORM_ERROR
												.getCode(), String.format(
												"%s:%sʧ��:%s", attrName,
												txBizRule.getRuleName(),
												txBizRule.getRuleDesc()));
							}
							return null;
						}
					}
				} else {
					ecifData.setStatus(
							ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(),
							"У��������ô���");
					log.error("У��ת������{}��Ч", ruler_i);
					return null;
				}
			}
		}
		return value;
	}

	public static boolean doFilter(EcifData ecifData,String ruler,Object entity){
		TxBizRule txBizRule=null;
		if(ruler!=null){
			String[] rulers=ruler.split("\\,");
			for(String ruler_i:rulers){
				if((txBizRule=txBizRuleConfMap.get(ruler_i))!=null){
					if(txBizRule.getModelFilter()!=null){
						boolean rul=txBizRule.getModelFilter().execute(ecifData, entity);
						if(!rul){
							log.warn("����ͨ��({})������,ͨ����",txBizRule.getRuleName());
							if(ecifData.isSuccess()){
								if (StringUtil.isEmpty(txBizRule.getRuleDesc())) {
									ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(), "����ͨ����(%s)������",txBizRule.getRuleName());
								}else{
									ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(), txBizRule.getRuleDesc());
								}
							}
							return false;
						}
					}
				}else{
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(), "ģ�͹������ô���");
					log.error("ģ�͹��˹���{}��Ч",ruler_i);
					return false;
				}
			}
		}
		return true;
	}

}

/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.comidentification
 * @�ļ�����GetContIdByType.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:56:36
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.biz.comidentification;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.core.EcifDealEngineFactory;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;
import com.ytec.mdm.service.bo.TxCustIdentifRule;
import com.ytec.mdm.service.facade.IBizGetContId;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�GetContIdByType
 * @��������:������ʶ�������������Ե�ʶ��
 * @��������:����ʶ�����ͣ�����Դϵͳ�ͻ��Ż�ECIF�ͻ���ʶ��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:56:37   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:56:37
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class GetContIdByType implements IBizGetContId {
	private static Logger log = LoggerFactory.getLogger(GetContIdByType.class);
	/**
	 * @��������:txCustIdentifRuleMap
	 * @��������:�ͻ�ʶ���ӦMAP
	 * @since 1.0.0
	 */
	private static Map<String,TxCustIdentifRule> txCustIdentifRuleMap=new HashMap<String,TxCustIdentifRule>();
	
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void init(){
		txCustIdentifRuleMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Object[]> txCustIdentifRuleList=baseDAO.findByNativeSQLWithIndexParam("SELECT T.RULE_NO,T.RULE_NAME,T.RULE_DEF_TYPE,T.RULE_DEAL_TYPE" +
				",T.RULE_PKG_PATH,T.RULE_DEAL_CLASS,T.RULE_EXPR,T.RULE_EXPR_DESC FROM TX_BIZ_RULE_CONF T ,TX_BIZ_RULE_GROUP G WHERE G.RULE_GROUP_NO=? AND T.RULE_GROUP_ID=G.RULE_GROUP_ID AND RULE_STAT='1'",MdmConstants.CUSTOMERIDENTIFICATION);
		if(txCustIdentifRuleList!=null &&!txCustIdentifRuleList.isEmpty()){
			Class clazz=null;
			IBizGetContId cc=null;
			String szInterface=IBizGetContId.class.getName();
			for(Object[] objs:txCustIdentifRuleList){
				TxCustIdentifRule txCustIdentifRule=new TxCustIdentifRule();
				if(StringUtil.isEmpty(objs[0])){
					log.error("�ͻ�ʶ��������Ϊ��");
					continue;
				}
				txCustIdentifRule.setRuleNo((String)objs[0]);
				txCustIdentifRule.setRuleName((String)objs[1]);
				txCustIdentifRule.setRuleDefType((String)objs[2]);
				if(objs[4]==null||objs[5]==null){
					if(objs[6]==null){
						log.error("�ͻ�ʶ�������������Ϊ��");
						continue;
					}
				}else{
					String className=objs[4].toString()+"."+objs[5].toString();
					try{
						clazz = Class.forName(className);
						if(ReflectionUtils.isInterface(clazz, szInterface) ){
							cc=(IBizGetContId)clazz.newInstance();
							txCustIdentifRule.setBizGetContId(cc);
						}else{
							log.error("��������({}),û��ʵ��IBizGetContId�ӿ�",className);
							continue;
						}
					}catch(Exception e){
						log.error("��ʼ�����ÿͻ�ʶ������������",e);
						continue;
					}
				}
				txCustIdentifRule.setRuleExpr((String)objs[6]);
				txCustIdentifRule.setRuleDesc((String)objs[7]);
				txCustIdentifRuleMap.put(txCustIdentifRule.getRuleNo(), txCustIdentifRule);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.service.facade.IBizGetContId#bizGetContId(com.ytec.mdm.base.bo.EcifData)
	 */
	public void bizGetContId(EcifData ecifData) {
		String ruler = (String) ecifData.getCustDiscRul();
		if(StringUtil.isEmpty(ruler)){
			log.error("��ȡ�ͻ�ʶ�����Ϊ��");
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE);
			return;
		}
		TxCustIdentifRule txCustIdentifRule=null;
		if((txCustIdentifRule=txCustIdentifRuleMap.get(ruler))!=null){
			if(txCustIdentifRule.getBizGetContId()!=null){
				txCustIdentifRule.getBizGetContId().bizGetContId(ecifData);
			}else{
				log.info("ʹ���Զ���ͻ�ʶ����[{}]ʶ��ͻ�",txCustIdentifRule.getRuleExpr());
				IEcifDealEngine txDealEngine=EcifDealEngineFactory.getTxDealEngine(txCustIdentifRule.getRuleExpr());
				if(txDealEngine!=null){
					//����ԭ������
					String txCode=ecifData.getTxCode();
					//�л�ʶ������
					ecifData.setTxCode(txCustIdentifRule.getRuleExpr());
					txDealEngine.execute(ecifData);
					log.info("�Զ���ͻ�ʶ����:{}",ecifData.getDetailDes());
					//��ԭ������
					ecifData.setTxCode(txCode);
				}else{
					log.error("��ȡ�ͻ�ʶ����{}ʧ��",txCustIdentifRule.getRuleExpr());
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE);
				}
			}
		}else{
			log.error("��ȡ�ͻ�ʶ�����{}ʧ��",ruler);
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE);
		}
		return;
	}

}

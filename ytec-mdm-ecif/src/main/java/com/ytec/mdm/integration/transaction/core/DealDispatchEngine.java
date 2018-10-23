/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����DealDispatchEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:06:47
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.core.EcifDealEngineFactory;
import com.ytec.mdm.integration.transaction.facade.IDispatchFun;
import com.ytec.mdm.integration.transaction.model.DispatchNode;
import com.ytec.mdm.integration.transaction.model.DispatchRule;
import com.ytec.mdm.server.common.DealDispatchCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DealDispatchEngine
 * @�������������������
 * @��������:�������̿���
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:06:48   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:06:48
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class DealDispatchEngine {
	private static Logger log = LoggerFactory.getLogger(DealDispatchEngine.class);
	/**
	 * @��������:DealDispatcher
	 * @��������:���׷ַ�����
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	public static void DealDispatcher(EcifData ecifData){
		/**
		 * ����ԭ������
		 */
		String originalTxCode=ecifData.getTxCode();
		/**
		 * �ռ�����
		*/
		Map<String, String> paramMap =parameterCollect(ecifData.getBodyNode(),MdmConstants.QUERY_TX_REQ_PRARM_NODE);
		ecifData.setParameterMap(paramMap);
		DispatchRule dispRule=null;
		if((dispRule=DealDispatchCfg.getDispatchRule(originalTxCode))==null){
			/**
			 * ����Ҫ����
			 */
			log.info("ֱ�ӵ��ý���[txCode:{}]",originalTxCode);
			EcifDealEngineFactory.getTxDealEngine(originalTxCode).execute(ecifData);
		}else{
			/**
			 * ��Ҫ����
			 */
			log.info("����[{}]���ȿ�ʼ",originalTxCode);
			Element returnNode=DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
			doDispatcher(dispRule,ecifData,returnNode);
			/**
			 * ����ʧ��ֱ�ӷ��ش���
			 */
			if(!ecifData.isSuccess()){
				log.info("����[{}]�����ж�",originalTxCode);
			}else{
				/**
				 *���÷��ر���
				 */
				List<Element> t=returnNode.elements();
				if(t!=null && t.size()>0){
					log.info("����[{}]���÷��ؽ��",originalTxCode);
					ecifData.setRepNode(returnNode);
					/**
					 * ����з��أ�����״̬˵��
					 */
					ecifData.resetStatus();
				}
			}
			log.info("����[{}]���Ƚ���",originalTxCode);
			ecifData.setTxCode(originalTxCode);
		}
	}
	/**
	 * @��������:orderDispatcher
	 * @��������:˳��ִ�п���
	 * @�����뷵��˵��:
	 * 		@param dispRule
	 * 		@param ecifData
	 * 		@param returnNode
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	private static void orderDispatcher(DispatchRule dispRule,EcifData ecifData,Element returnNode){
		List<DispatchNode> nodeList= dispRule.getNodeList();
		for(DispatchNode node:nodeList){
			/**
			 * ��ȡִ������
			 */
			String exeType=node.getExeType();
			if("0".equals(exeType)){
				/**
				 * ʹ��ԭ�ӽ���
				 */
				log.info("����ԭ�ӽ���[{}]",node.getTxCode());
				/**
				 * ��У��ԭ�ӽ����Ƿ����
				 */
				if(!TxModelHolder.txDefCheck(node.getTxCode())){
					log.warn("����ԭ�ӽ���[{}]�����ڻ���ͣ��",node.getTxCode());
					ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_FOUND);
					return;
				}
				/**
				 * ���ý������õ�������
				 */
				ecifData.setTxCode(node.getTxCode());
				EcifDealEngineFactory.getTxDealEngine(node.getTxCode()).execute(ecifData);
			}else if("1".equals(exeType)){
				/**
				 * ʹ��ԭ�Ӻ���
				 */
				IDispatchFun dispatchFun=node.getBeanClass();
				if(dispatchFun!=null){
					log.info("����ԭ�ӽ��׺���[{}]",dispatchFun.getClass().getName());
					dispatchFun.execute(ecifData);
				}else{
					log.error("����ԭ�ӽ��׺���ʧ��");
					ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL.getCode(),"����ԭ�ӽ��׺���ʧ��");
					return;
				}
			}else if("2".equals(exeType)){
				/**
				 * ��������
				 */
				if(node.getRule()!=null){
					doDispatcher(node.getRule(),ecifData,returnNode);
				}
			}else{
				log.error("%s��֧��ִ�й���%s",ecifData.getTxCode(),exeType);
				ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"�������ô���");
				return;
			}
			if(!ecifData.isSuccess()){
				/**���Դ���?������ȷ��Ĭ��****/
				return ;
			}
			/***
			 * ��ȡ�����������Ϊ�´ε��ȵĲ���
			 */
			if(!StringUtil.isEmpty(node.getResultParametPath())){
				log.info("��ȡ{}�µĲ���",node.getResultParametPath());
				ecifData.getParameterMap().putAll(parameterCollect(ecifData.getRepNode(),node.getResultParametPath()));
			}
			/**
			 * ��������
			 */
			if(node.isReturn()){
				//returnNode=ecifData.getRepNode();
				collectReturnNode(ecifData,returnNode);
			}
		}
	}
	/**
	 * @��������:caseDispatcher
	 * @��������:��ִ֧�п���
	 * @�����뷵��˵��:
	 * 		@param dispRule
	 * 		@param ecifData
	 * 		@param returnNode
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	private static void caseDispatcher(DispatchRule dispRule,EcifData ecifData,Element returnNode){
		/**
		 * ͨ���жϺ������ж����ĸ���֧
		 */
		String determination=dispRule.getBeanClass().decide(ecifData);
		if(!ecifData.isSuccess()){
			log.warn("�ж��������ش���:{}",ecifData.getDetailDes());
			return;
		}
		log.info("�ж���������[{}]",determination);
		DispatchNode dispatchNode=null;
		if((dispatchNode=dispRule.getNodeMapValue(determination))!=null){
			/**
			 * ��ȡִ������
			 */
			String exeType=dispatchNode.getExeType();
			if("0".equals(exeType)){
				/**
				 * ʹ��ԭ�ӽ���
				 */
				log.info("����ԭ�ӽ���[{}]",dispatchNode.getTxCode());
				/**
				 * ��У��ԭ�ӽ����Ƿ����
				 */
				if(!TxModelHolder.txDefCheck(dispatchNode.getTxCode())){
					log.warn("����ԭ�ӽ���{}�����ڻ���ͣ��",dispatchNode.getTxCode());
					ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_FOUND);
					return;
				}
				ecifData.setTxCode(dispatchNode.getTxCode());
				EcifDealEngineFactory.getTxDealEngine(dispatchNode.getTxCode()).execute(ecifData);
			}else if("1".equals(exeType)){
				/**
				 * ʹ��ԭ�Ӻ���
				 */
				IDispatchFun dispatchFun=dispatchNode.getBeanClass();
				if(dispatchFun!=null){
					log.info("����ԭ�ӽ��׺���[{}]",dispatchFun.getClass().getName());
					dispatchFun.execute(ecifData);
				}else{
					log.error("����ԭ�ӽ��׺���ʧ��");
					ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL.getCode(),"����ԭ�ӽ��׺���ʧ��");
					return;
				}
			}else if("2".equals(exeType)){
				/**
				 * ��������
				 */
				if(dispatchNode.getRule()!=null){
					doDispatcher(dispatchNode.getRule(),ecifData,returnNode);
				}
			}else{
				log.error("%s��֧��ִ�й���%s",ecifData.getTxCode(),exeType);
				ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"�������ô���");
				return;
			}
			
			/***
			 * ��ȡ�����������Ϊ�´ε��ȵĲ���
			 */
			if(ecifData.isSuccess()){
				if(!StringUtil.isEmpty(dispatchNode.getResultParametPath())){
					log.info("��ȡ{}�µĲ���",dispatchNode.getResultParametPath());
					ecifData.getParameterMap().putAll(parameterCollect(ecifData.getRepNode(),dispatchNode.getResultParametPath()));
				}
				/**
				 * ��������
				 */
				if(dispatchNode.isReturn()){
					//returnNode=ecifData.getRepNode();
					collectReturnNode(ecifData,returnNode);
				}
			}
		}else{
			log.error("��֧���ж���ִ�й���[{}]",determination);
			ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"�������ô���");
			return;
		}
	}
	
	/**
	 * @��������:doDispatcher
	 * @��������:��Ͽ���
	 * @�����뷵��˵��:
	 * 		@param dispRule
	 * 		@param ecifData
	 * 		@param returnNode
	 * @�㷨����:
	 */
	private static void doDispatcher(DispatchRule dispRule,EcifData ecifData,Element returnNode){
		if("case".equals(dispRule.getRuleType())){
			/**
			 * �߷�֧�����߼�
			 */
			log.info("��֧����");
			caseDispatcher(dispRule,ecifData,returnNode);
		}else{
			/**
			 * ��˳������߼�
			 */
			log.info("˳�����");
			orderDispatcher(dispRule,ecifData,returnNode);
		}
	}
	
	
	/**
	 * @��������:parameterCollect
	 * @��������:��ȡ����
	 * @�����뷵��˵��:
	 * 		@param bodyNode
	 * 		@param xpath
	 * 		@return
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parameterCollect(Element bodyNode,String xpath){
		Map<String, String> opm = new HashMap<String, String>();
		if(bodyNode==null){
			return opm;
		}
		/**
		 * �ռ�xpath�µ�������Ϊ����
		 */
		List<Element> filterChildren =bodyNode.selectNodes(xpath+"/*");
		if(filterChildren!=null){
			for (Element element : filterChildren) {
				if(!StringUtil.isEmpty(element.getText())){
					opm.put(element.getName(), element.getText());
				}
			}
		}
		return opm;
	}
	
	/**
	 * @��������:collectReturnNode
	 * @��������:������Ϣ��ȡ
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param returnNode
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	private static void collectReturnNode(EcifData ecifData,Element returnNode){
		if(ecifData.getRepNode()!=null){
			if(returnNode.elements()==null ||returnNode.elements().size()==0){
				/**
				 * ��һ�����ر�������
				 * 
				 */
				returnNode.setName(ecifData.getRepNode().getName());
			}
			List<Element> tempList= ecifData.getRepNode().elements();
			for(Element point:tempList){
				/**
				 * �����ص����ݸ��Ƶ����ض�����
				 */
				returnNode.add(point.detach());
			}
		}
		return;
	}

}

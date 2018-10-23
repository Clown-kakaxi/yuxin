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

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;

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
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class DealDispatchEngine {
	private static Logger log = LoggerFactory.getLogger(DealDispatchEngine.class);

	/**
	 * @��������:DealDispatcher
	 * @��������:���׷ַ�����
	 * @�����뷵��˵��:
	 * @param ecifData
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	public static void DealDispatcher(EcifData data) {
		/**
		 * ����ԭ������
		 */
		String txCode = data.getTxCode();
		/**
		 * �ռ�����
		 */
		Map<String, String> paramMap = parameterCollect(data.getBodyNode(), MdmConstants.QUERY_TX_REQ_PRARM_NODE);
		data.setParameterMap(paramMap);
		try {
			if (txCode == null || txCode.trim().equals("")) {
				log.error("���ױ��벻��Ϊ��");
				data.setStatus(ErrorCode.ERR_RULE_VALUE_IS_NULL);
				data.setSuccess(false);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("���׽ڵ�ȱ��");
			log.error("{}", e);
			data.setStatus(ErrorCode.ERR_RULE_VALUE_IS_NULL);
			data.setSuccess(false);
			return;
		}
		IEcifDealEngine engine = DealDispatchEngine.getTxDealEngine(txCode);

		if (engine == null) {
			String err = String.format("�޷���ȡ���״��������࣬����(%s)����ʧ��", txCode);
			log.error(err);
			data.setStatus(ErrorCode.ERR_SERVER_PROG_ERROR.getCode(),err);
			data.setSuccess(false);
		} else {
			engine.execute(data);
		}
	}

	/**
	 * @��������:getTxDealEngine
	 * @��������:��ȡ��������
	 * @�����뷵��˵��:
	 * @param txCode
	 * @return
	 * @�㷨����:
	 */
	public static IEcifDealEngine getTxDealEngine(String txCode) {

		IEcifDealEngine txDealEngine = null;
		try {
			TxModel4CRM txModel = TxModelHolder.getTxModel(txCode);
			// ���ݽ�������������Ӧ�Ľ��״�������
			String txType = txModel.getTxDef().getTxType();
			String cfgTy = txModel.getTxDef().getCfgTp();
			if (MdmConstants.TX_CFG_TP_CUS.equals(cfgTy)) {
				log.info("�ͻ�������[{}:{}]", txModel.getTxDef().getName(), txModel.getTxDef().getCnName());
				txDealEngine = (IEcifDealEngine) SpringContextUtils.getBean(txModel.getTxDef().getDealEngine());
			} else {
				if (MdmConstants.TX_TYPE_R.equals(txType)) {// ��ѯ����
					log.info("��ѯ����[{}:{}]", txModel.getTxDef().getName(), txModel.getTxDef().getCnName());
					txDealEngine = new QueryEcifDealEngine();
				} else if (MdmConstants.TX_TYPE_W.equals(txType)) {
					log.info("д����[{}:{}]", txModel.getTxDef().getName(), txModel.getTxDef().getCnName());
					txDealEngine = new WriteEcifDealEngine();
				} else {
					txDealEngine = new ErrorEcifDealEngine("��������:" + txType + " ,ϵͳ��֧��!");
					log.error("��������:{},ϵͳ��֧��!", txType);
				}
			}

		} catch (Exception e) {
			log.error("���콻�״�������ʧ��", e);
			String errorMsg = "����" + txCode + "�����ڻ�������Ϣ�쳣";
			txDealEngine = new ErrorEcifDealEngine(errorMsg);
		}

		return txDealEngine;
	}

	/**
	 * @��������:parameterCollect
	 * @��������:��ȡ����
	 * @�����뷵��˵��:
	 * @param bodyNode
	 * @param xpath
	 * @return
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parameterCollect(Element bodyNode, String xpath) {
		Map<String, String> opm = new HashMap<String, String>();
		if (bodyNode == null) { return opm; }
		/**
		 * �ռ�xpath�µ�������Ϊ����
		 */
		List<Element> filterChildren = bodyNode.selectNodes(xpath + "/*");
		if (filterChildren != null) {
			for (Element element : filterChildren) {
				if (!StringUtil.isEmpty(element.getText())) {
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
	 * @param ecifData
	 * @param returnNode
	 * @�㷨����:
	 */
	@SuppressWarnings("unchecked")
	private static void collectReturnNode(EcifData data, Element returnNode) {
		if (data.getRepNode() != null) {
			if (returnNode.elements() == null || returnNode.elements().size() == 0) {
				/**
				 * ��һ�����ر�������
				 */
				returnNode.setName(data.getRepNode().getName());
			}
			List<Element> tempList = data.getRepNode().elements();
			for (Element point : tempList) {
				/**
				 * �����ص����ݸ��Ƶ����ض�����
				 */
				returnNode.add(point.detach());
			}
		}
		return;
	}
}

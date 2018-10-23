/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����WriteEcifDealEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:18:05
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxMsgNodeTabMap;
import com.ytec.mdm.integration.check.bs.TxBizRuleFactory;
import com.ytec.mdm.integration.transaction.bs.ServiceEntityMgr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�WriteEcifDealEngine
 * @��������д���״�������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:18:05
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:18:05
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriteEcifDealEngine extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory.getLogger(WriteEcifDealEngine.class);
	private Map<Long, List<TxMsgNode>> txMsgNodeMap = new TreeMap<Long, List<TxMsgNode>>();
	/**
	 * @��������:authType
	 * @��������:����Ȩ������
	 * @since 1.0.0
	 */
	private String authType = null;
	/**
	 * @��������:authCode
	 * @��������:����Ȩ����
	 * @since 1.0.0
	 */
	private String authCode = null;

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void execute(EcifData data) {
		try {
			this.ecifData = data;
			this.ecifData.setWriteModelObj(new WriteModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			WriteModel generalInfoList = this.ecifData.getWriteModelObj();
			// ���ڵ�
			TxMsgNode rootTxMsgNode = this.getTxDealInfos();
			if (rootTxMsgNode == null) {
				data.setStatus(ErrorCode.ERR_XML_CFG_NO_ROOT_NODE.getCode(), "�������ô���");
				log.error("����:{},���������ô���,û�����ø��ڵ�!", data.getTxCode());
				return;
			}
			if (StringUtil.isEmpty(data.getAuthCode())) {
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
				authCode = (String) data.getParameterMap().get(MdmConstants.AUTH_CODE);
			} else {
				authCode = data.getAuthCode();
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
			}
			// ��ù��������ڵ�
			this.ecifData.getWriteModelObj().getOperMap().putAll(data.getParameterMap());

			/** ��ȡ�ͻ�ʶ������ ***/
			this.ecifData.setCustDiscRul(txModel.getTxDef().getTxDiscRul());

			/*** ��ȡ���׵Ŀͻ����� ***/
			if (MdmConstants.TX_CODE_PER.equals(txModel.getTxDef().getTxCustType())) {
				this.ecifData.getWriteModelObj().getOperMap().put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_PER_TYPE);
			} else if (MdmConstants.TX_CODE_ORG.equals(txModel.getTxDef().getTxCustType())) {
				this.ecifData.getWriteModelObj().getOperMap().put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_ORG_TYPE);
			} else if (MdmConstants.TX_CODE_INTER.equals(txModel.getTxDef().getTxCustType())) {
				this.ecifData.getWriteModelObj().getOperMap().put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_BANK_TYPE);
			}

			/** �ϸ������������޸� **/
			if (MdmConstants.TX_DIV_INS_UPD.equals(txModel.getTxDef().getTxDivInsUpd())) {
				this.ecifData.getWriteModelObj().setDivInsUpd(true);
			}

			boolean ref = getTableNodeObect(ecifData.getBodyNode(), rootTxMsgNode, generalInfoList);
			if (!ref) { return; }

			this.ecifData.setTxType(this.txModel.getTxDef().getTxLvl2Tp());
			/**
			 * ���״�����
			 */
			String txDealClass = txModel.getTxDef().getTxDealClass();
			IEcifBizLogic createBizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);
			if (createBizLogic == null) {
				log.error("���״�����Ϊ��");
				this.ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL);
				return;
			}
			createBizLogic.process(this.ecifData);
			if (this.ecifData.isSuccess()) {
				/***
				 * ��ӡ���ݱ����¼
				 */
				if (this.ecifData.getDataSynchro() != null) {
					if (BusinessCfg.getBoolean("printChangeLog")) {
						log.info("�ͻ�[{}]��Ϣ�������:", this.ecifData.getCustId());
						for (Object o : this.ecifData.getDataSynchro()) {
							log.info(o.toString());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("������Ϣ", e);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			return;
		}
		if (this.ecifData.getRepNode() != null) { return; }
		Map map = null;
		Element responseEle = DocumentHelper.createElement(txModel.getResTxMsg().getMainMsgRoot());
		/**
		 * �����ر���
		 */
		if ((map = this.ecifData.getWriteModelObj().getResultMap()) != null) {
			Element hand = null;
			Element pointer = responseEle;
			for (TxMsgNode txMsgNode : this.txModel.getResTxMsgNodeList()) {
				if (txMsgNode.getNodeCode() != null && txMsgNode.getNodeCode().equals(txModel.getResTxMsg().getMainMsgRoot())) {
					/* ���÷���ֵ */
					if (this.txModel.getTxMsgNodeAttrMap() != null && txMsgNode.getNodeId() != null) {
						List<TxMsgNodeAttr> txMsgNodeAttr = this.txModel.getTxMsgNodeAttrMap().get(txMsgNode.getNodeId());
						getResNodeXml(txMsgNodeAttr, map, responseEle);
					}
				} else {
					List<TxMsgNodeAttr> txMsgNodeAttr = this.txModel.getTxMsgNodeAttrMap().get(txMsgNode.getNodeId());
					if (MdmConstants.NODE_GROUP_M.equals(txMsgNode.getNodeGroup())) {
						if (!MdmConstants.NODE_GROUP_NO_LABEL.equals(txMsgNode.getNodeLabel())) {
							hand = pointer.addElement(txMsgNode.getNodeCode() + MdmConstants.NODE_GROUP_SUFFIX);
							pointer = hand;
						}
						List<Map> resList = (List<Map>) map.get(txMsgNode.getNodeCode());
						if (resList != null && !resList.isEmpty()) {
							for (Map listmap : resList) {
								hand = pointer.addElement(txMsgNode.getNodeCode());
								getResNodeXml(txMsgNodeAttr, listmap, hand);
							}
						}
					} else {
						Map resList = (Map) map.get(txMsgNode.getNodeCode());
						if (!"0".equals(txMsgNode.getNodeDisplay())) {
							hand = pointer.addElement(txMsgNode.getNodeCode());
						} else {
							hand = pointer;
						}
						getResNodeXml(txMsgNodeAttr, resList, hand);
					}
				}
			}
		}
		this.ecifData.setRepNode(responseEle);
		return;
	}

	/**
	 * @��������:getResNodeXml
	 * @��������:��ȡ��Ӧ����
	 * @�����뷵��˵��:
	 * @param txMsgNodeAttr
	 * @param map
	 * @param pointer
	 * @�㷨����:
	 */
	private void getResNodeXml(List<TxMsgNodeAttr> txMsgNodeAttr, Map map, Element pointer) {
		String repValue = null;
		Element hand = null;
		if (txMsgNodeAttr != null) {
			for (TxMsgNodeAttr NodeAttr : txMsgNodeAttr) {
				if (NodeAttr.getAttrCode() != null && NodeAttr.getDataLen() != null && NodeAttr.getDataLen() != 0) {
					hand = pointer.addElement(NodeAttr.getAttrCode());
					if (map != null && map.get(NodeAttr.getAttrCode()) != null) {
						repValue = map.get(NodeAttr.getAttrCode()).toString();
						if (!repValue.isEmpty()) {
							/** ת��,ת�� **/
							repValue = processCTDR(NodeAttr, repValue);
							/**
							 * DOM4j��������ʱ���ܴ���NULL����
							 */
							if (repValue == null) {
								repValue = "";
							}
							hand.setText(repValue);
						}
					} else {
						/**
						 * ����Ĭ������
						 */
						hand.setText(StringUtil.toString(NodeAttr.getDefaultVal()));
					}
				}
			}
		}
	}

	/**
	 * @��������:getTxDealInfos
	 * @��������:��ȡ���ڵ�������ϵ
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public TxMsgNode getTxDealInfos() {
		List<TxMsgNode> tsMsgNodeList = this.txModel.getReqTxMsgNodeList();
		if (tsMsgNodeList == null || tsMsgNodeList.isEmpty()) { return null; }
		TxMsgNode rootTxMsgNode = null;
		List<TxMsgNode> txMsgNodeList = null;
		/**
		 * ���б��д������ϵ������㴦����߼���
		 */
		for (TxMsgNode txMsgNode : tsMsgNodeList) {
			if (txMsgNode.getUpNodeId() == -1) {
				rootTxMsgNode = txMsgNode;
			}
			txMsgNodeList = txMsgNodeMap.get(txMsgNode.getUpNodeId());

			if (txMsgNodeList == null) {
				txMsgNodeList = new ArrayList<TxMsgNode>();
				txMsgNodeMap.put(txMsgNode.getUpNodeId(), txMsgNodeList);
			}
			txMsgNodeList.add(txMsgNode);
		}
		return rootTxMsgNode;
	}

	/**
	 * @��������:getTableNodeObect
	 * @��������:����ת���ɶ���
	 * @�����뷵��˵��:
	 * @param context
	 * @param currentxMsgNode
	 * @param generalInfoList
	 * @�㷨����:
	 */
	private boolean getTableNodeObect(Element context, TxMsgNode currentxMsgNode, WriteModel generalInfoList) {
		Long nodeId = currentxMsgNode.getNodeId();
		List<TxMsgNodeTabMap> txMsgNodeTabMaps = txModel.getTxMsgNodeTabMapMap().get(nodeId);
		if (txMsgNodeTabMaps != null && !txMsgNodeTabMaps.isEmpty()) {
			List<TxMsgNodeAttr> txMsgNodeAttrs = txModel.getTxMsgNodeAttrMap().get(nodeId);
			if (txMsgNodeAttrs != null && !txMsgNodeAttrs.isEmpty()) {
				for (TxMsgNodeTabMap txMsgNodeTabMap : txMsgNodeTabMaps) {
					String className = txMsgNodeTabMap.getObjName();
					/**
					 * ���ɶ���
					 */
					Object obj = getClassByParams(className, context, txMsgNodeAttrs, txMsgNodeTabMap.getTabId(), nodeId);
					/**
					 * ������صĶ���Ϊ�գ������Ǵ���Ҳ������������Ч
					 */
					if (obj != null) {
						/**
						 * ������Ч
						 */
						generalInfoList.setOpModelList(obj);
					} else {
						if (!ecifData.isSuccess()) {
							/**
							 * �������
							 */
							return false;
						}
					}
				}
			}
		}
		/**
		 * ��ǰ�ڵ��µ������ӽڵ�
		 */
		List<TxMsgNode> txMsgNodeList = txMsgNodeMap.get(nodeId);
		if (txMsgNodeList != null) {
			List<Element> nodeElement = null;
			for (TxMsgNode txMsgNode : txMsgNodeList) {
				String nodeCode = txMsgNode.getNodeCode();
				/**
				 * ����ڵ���ı�ǩ����
				 */
				if (MdmConstants.NODE_GROUP_M.equals(txMsgNode.getNodeGroup())) {
					if (MdmConstants.NODE_GROUP_NO_LABEL.equals(txMsgNode.getNodeLabel())) {
						nodeElement = context.selectNodes(nodeCode);
					} else {
						nodeElement = context.selectNodes(nodeCode + MdmConstants.NODE_GROUP_SUFFIX + "/" + nodeCode);
					}
				} else {
					/**
					 * ��������߼���Ӱ����
					 */
					if (!"0".equals(txMsgNode.getNodeDisplay())) {
						nodeElement = context.selectNodes(nodeCode);
					} else {
						nodeElement = new LinkedList();
						nodeElement.add(context);
					}
				}
				if (nodeElement != null && !nodeElement.isEmpty()) {
					for (Element oneNode : nodeElement) {
						getTableNodeObect(oneNode, txMsgNode, generalInfoList);
						if (!ecifData.isSuccess()) { return false; }
					}
				}
			}
		}
		return true;
	}

	/**
	 * @��������:getClassByParams
	 * @��������:����ֵ
	 * @�����뷵��˵��:
	 * @param className
	 * @param context
	 * @param txMsgNodeAttrs
	 * @param tabId
	 * @return
	 * @�㷨����:
	 */
	public <T> T getClassByParams(String className, Element context, List<TxMsgNodeAttr> txMsgNodeAttrs, Long tabId, Long nodeId) {
		T entity = null;
		boolean isAllEmpty = true;
		String val = null;
		StringBuffer txMsgNodeAttrCtList = null;
		if (!StringUtil.isEmpty(className)) {
			try {
				/**
				 * ������
				 */
				Class clazz = ServiceEntityMgr.getEntityByName(className);
				if (clazz == null) {
					log.error("û���ҵ�{}��ʵ����", className);
					this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
					return null;
				}
				/**
				 * ʵ��������
				 */
				entity = (T) clazz.newInstance();
				Object value = null;
				Class typeClass = null;
				Field field = null;
				for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrs) {
					if (tabId.equals(txMsgNodeAttr.getTabId())) {
						/**
						 * �޸�Ȩ�޿���
						 */
						/***
						 * ��ϢƯ��
						 ****/
						if ("Y".equals(txMsgNodeAttr.getNulls())
								&& !EcifPubDataUtils.infoLevelRead(authType, authCode, MdmConstants.CTRLTYPE_WRITE, txMsgNodeAttr.getTabId(), txMsgNodeAttr.getColId())) {
							continue;
						}
						Element attrElement = context.element(txMsgNodeAttr.getAttrCode());
						if (!StringUtil.isEmpty(txMsgNodeAttr.getCheckRule())) {
							if (txMsgNodeAttrCtList == null) {
								txMsgNodeAttrCtList = new StringBuffer(txMsgNodeAttr.getCheckRule());
							} else {
								txMsgNodeAttrCtList.append(',').append(txMsgNodeAttr.getCheckRule());
							}
						}
						//ͨ��getTextTrim���ܻ��ȡ��""���͵�ֵ��ʵ�ʻ� Ϊ��
						if (attrElement != null&&!StringUtil.isEmpty(attrElement.getTextTrim())) {
							val = attrElement.getTextTrim();
						} else {
							val = txMsgNodeAttr.getDefaultVal();
							/*** Ĭ��ֵ���� ***/
							if (!StringUtil.isEmpty(val)) {
								/**
								 * ����Ĭ��ֵ
								 */
								val = parseDefaultVal(val, context);
								if (!this.ecifData.isSuccess()) { return null; }
							}
						}
						if (!StringUtil.isEmpty(val)) {
							/**
							 * ��ȡ���������������ݿ���ֶ����ƣ���Ҫת����java�������淶����
							 */
							field = clazz.getDeclaredField(NameUtil.getColumToJava(txMsgNodeAttr.getColName()));
							typeClass = field.getType();
							/**
							 * ���ַ��͵�����ת����ʵ�����Ե�����
							 */
							value = convertStringToObject(typeClass, val, txMsgNodeAttr.getDataFmt());
							if (value != null) {
								/**
								 * ���ö�˽�ķ���Ȩ��
								 */
								field.setAccessible(true);
								/**
								 * ��������
								 */
								field.set(entity, value);
								isAllEmpty = false;
							} else {
								return null;
							}
						} else {
							/** �ж��Ƿ���ҵ���������ǿ� **/
							if ("P".equals(txMsgNodeAttr.getNulls())) { return null; }
						}
					}
				}

			} catch (Exception e) {
				log.error("������Ϣ��", e);
				this.ecifData.setStatus(ErrorCode.ERR_POJO_UNKNOWN_ERROR);
				return null;
			}
		} else {
			log.warn("���ݿ���������");
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), "���ݿ���������");
			return null;
		}
		/**
		 * ���е�����Ϊ�գ����ؿ�
		 */
		if (isAllEmpty) { return null; }
		/******
		 * д���׹�����
		 */
		if (txMsgNodeAttrCtList != null && txMsgNodeAttrCtList.length() > 0) {
			boolean rel = TxBizRuleFactory.doFilter(this.ecifData, txMsgNodeAttrCtList.toString(), entity);
			if (!rel) { return null; }
		}
		return entity;
	}
}

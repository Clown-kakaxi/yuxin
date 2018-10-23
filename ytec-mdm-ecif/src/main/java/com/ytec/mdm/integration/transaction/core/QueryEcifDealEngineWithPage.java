/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����QueryEcifDealEngineWithPage.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:10:57
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.QueryModel;
import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�QueryEcifDealEngineWithPage
 * @����������ѯ���״�������(����ҳ����)
 * @��������:��ѯ�������� ��������ѯ���������ò�ѯ�߼�
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:10:58   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:10:58
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
@Scope("prototype")
public class QueryEcifDealEngineWithPage extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory
			.getLogger(QueryEcifDealEngineWithPage.class);
	private String authType = null;
	private String authCode = null;
	// �Ƿ����в�ѯΪ��
	private boolean isAllQueryNull;

	@SuppressWarnings("unchecked")
	@Override
	public void execute(EcifData data) {
		try {
			isAllQueryNull = true;
			this.ecifData = data;
			this.ecifData.setQueryModelObj(new QueryModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			// ��ȡ��ѯ����,���Ķ������ԡ����������������Ϊ��ѯ����
			Map<String, String> reqParamMap = new HashMap<String, String>(data.getParameterMap());
			if (StringUtil.isEmpty(data.getAuthCode())) {
				authType = reqParamMap.get(MdmConstants.AUTH_TYPE);
				authCode = reqParamMap.get(MdmConstants.AUTH_CODE);
			}else{
				authCode=data.getAuthCode();
				authType = reqParamMap.get(MdmConstants.AUTH_TYPE);
			}
			// ������Ӧ����
			List<TxMsgNode> tsMsgNodeList = this.txModel.getResTxMsgNodeList();
			// �洢���ڵ�������Ϣ
			Map<Long, Object> parentNodeValueMap = new HashMap<Long, Object>();

			// ���ڵ�
			TxMsgNode rootTxMsgNode = null;
			Map<Long, List<TxMsgNode>> txMsgNodeMap = new HashMap<Long, List<TxMsgNode>>();
			List<TxMsgNode> txMsgNodeList = null;

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
			// ��ʼ������Ӧ����
			if (rootTxMsgNode == null) {
				data.setStatus(ErrorCode.ERR_XML_CFG_NO_ROOT_NODE.getCode(),"�������ô���");
				log.warn("����:{},��Ӧ�������ô���,û�����ø��ڵ�!", data.getTxCode());
				return;
			}
			// ���״�����
			String txDealClass = txModel.getTxDef().getTxDealClass();
			this.bizLogic = (IEcifBizLogic) SpringContextUtils
					.getBean(txDealClass);

			Element responseEle = DocumentHelper.createElement(txModel.getResTxMsg().getMainMsgRoot());

			buildNodeXML(responseEle, rootTxMsgNode, txMsgNodeMap, reqParamMap,
					parentNodeValueMap);
			if (isAllQueryNull && data.isSuccess()) {
				data.setStatus(ErrorCode.WRN_NONE_FOUND);
				data.setSuccess(true);
			}
			if (data.isSuccess()) {
				data.setRepNode((Element)responseEle.element(txModel.getResTxMsg().getMainMsgRoot()).detach());
			}
		} catch (Exception e) {
			log.error("������Ϣ", e);
			data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
		}
		return;

	}

	
	/**
	 * @��������:buildNodeXML
	 * @��������:���ڵ�ת��ΪXML
	 * @�����뷵��˵��:
	 * 		@param pointer
	 * 		@param currentxMsgNode
	 * 		@param txMsgNodeMap
	 * 		@param reqParamMap
	 * 		@param parentNodeValueMap
	 * @�㷨����:
	 */
	private void buildNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap,
			Map<String, String> reqParamMap,
			Map<Long, Object> parentNodeValueMap) {
		if (MdmConstants.NODE_TP_T.equals(currentxMsgNode.getNodeTp())) {
			buildTableNodeXML(pointer, currentxMsgNode, txMsgNodeMap,
					reqParamMap, parentNodeValueMap);
			if (!this.ecifData.isSuccess()) {
				return;
			}
		} else {// ��ͨ�Ľڵ�
			if (MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())) {
				if(!MdmConstants.NODE_GROUP_NO_LABEL.equals(currentxMsgNode.getNodeLabel())){
					pointer = pointer.addElement(currentxMsgNode.getNodeCode()+ MdmConstants.NODE_GROUP_SUFFIX);
				}
			} else {
				if (!MdmConstants.NODE_TP_T.equals(currentxMsgNode.getNodeTp())) {
					pointer = pointer.addElement(currentxMsgNode.getNodeCode());
				}

			}
			buildCommonNodeXML(pointer, currentxMsgNode, reqParamMap);

			buildChildNodeXML(pointer, currentxMsgNode, txMsgNodeMap,
					reqParamMap, parentNodeValueMap);
		}
	}

	/**
	 * @param resXMlBuf
	 * @param currentxMsgNode
	 * @param txMsgNodeMap
	 * @param reqParamMap
	 * @param tabStr
	 * @param parentNodeValueMap
	 *            ���ڵ������ֵ ,���������ѯ
	 */
	private void buildChildNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap,
			Map<String, String> reqParamMap,
			Map<Long, Object> parentNodeValueMap) {
		// ��ǰ�ڵ��µ������ӽڵ�
		List<TxMsgNode> txMsgNodeList = txMsgNodeMap.get(currentxMsgNode
				.getNodeId());
		if (txMsgNodeList != null) {
			for (TxMsgNode txMsgNode : txMsgNodeList) {
				buildNodeXML(pointer, txMsgNode, txMsgNodeMap, reqParamMap,
						parentNodeValueMap);
				if (!this.ecifData.isSuccess()) {
					return;
				}
			}
		}
	}

	
	/**
	 * @��������:buildCommonNodeXML
	 * @��������:������ͨ�ڵ��XML����
	 * @�����뷵��˵��:
	 * 		@param pointer
	 * 		@param currentxMsgNode
	 * 		@param reqParamMap
	 * @�㷨����:
	 */
	private void buildCommonNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<String, String> reqParamMap) {
		Long nodeId = currentxMsgNode.getNodeId();
		// �����ֶ�
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel
				.getTxMsgNodeAttrMap().get(nodeId);
		for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
			if(txMsgNodeAttr.getDataLen()==null||txMsgNodeAttr.getDataLen()==0){
				continue;
			}
			String attrCode = txMsgNodeAttr.getAttrCode();
			String value = "";
			if (txMsgNodeAttr.getDefaultVal() != null) {
				value = txMsgNodeAttr.getDefaultVal();
			} else {
				value = reqParamMap.get(attrCode);
			}
			pointer.addElement(attrCode).setText(StringUtil.toString(value));
		}

	}

	
	/**
	 * @��������:buildTableNodeXML
	 * @��������:����������ݿ��Ľڵ��XML����
	 * @�����뷵��˵��:
	 * 		@param pointer
	 * 		@param currentxMsgNode
	 * 		@param txMsgNodeMap
	 * 		@param reqParamMap
	 * 		@param parentNodeValueMap
	 * @�㷨����:
	 */
	private void buildTableNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap,
			Map<String, String> reqParamMap,
			Map<Long, Object> parentNodeValueMap) {
		// �������ݿ��Ľڵ�
		Long nodeId = currentxMsgNode.getNodeId();
		String nodeCode = currentxMsgNode.getNodeCode();
		boolean fkAttrIdValueNull=false;
		// ��ѯ�ֶ�
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel
				.getTxMsgNodeAttrMap().get(nodeId);
		if (txMsgNodeAttrList == null || txMsgNodeAttrList.size() == 0) {
			String msg = "���Ľڵ�:" + currentxMsgNode.getNodeCode() + "û�в�ѯ����";
			log.warn(msg);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"û�����÷�����");
			return;
		}
		Map<String, Object> requestMap = new HashMap<String, Object>();
		List<String> queryFieldList = new ArrayList<String>();
		/**�����ѯ����*/
		for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
			queryFieldList.add(txMsgNodeAttr.getAttrCode());
			// �ж��Ƿ�ʹ���������ѯ����
			if (txMsgNodeAttr.getFkAttrId() != null
					&& txMsgNodeAttr.getFkAttrId() != 0) {
				Object value = parentNodeValueMap.get(txMsgNodeAttr.getFkAttrId());
				if (StringUtil.isEmpty(value)) {
					fkAttrIdValueNull=true;
					log.warn("���Ľڵ�:{},��ѯ����:{}�Ҳ��������ѯ����",nodeCode,txMsgNodeAttr.getAttrCode());
				} else {
					String dataType = txMsgNodeAttr.getDataType();
					if (MdmConstants.NODE_TYPE_FIX_STR.equals(dataType)
							|| MdmConstants.NODE_TYPE_VARI_STR.equals(dataType)) {// �ַ���
						value = value.toString();
					} else if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// ����
						value = new Long(value.toString());
					} else {
						String msg = "��������������ô���";
						this.ecifData.setStatus(ErrorCode.ERR_XML_CFG_NO_FK.getCode(),
								msg);
						log.error("���{}����������ʹ���,�����������Ϊ:{}",nodeCode,dataType);
						return;
					}
					requestMap.put(MdmConstants.QUERY_SQL_FILTER_FK_FLAG
							+ txMsgNodeAttr.getAttrCode(), value);
				}
			}

		}
		this.ecifData.getQueryModelObj().setQueryFieldList(queryFieldList);
		/**
		 * ��ȡ������������
		 */
		getFilterParam(currentxMsgNode,requestMap,reqParamMap);
		if (!this.ecifData.isSuccess()) {
			return;
		}
		this.ecifData.getQueryModelObj().setParentNodeValueMap(requestMap);
		// ======================�����ѯ����===========end
		//�����ѯ���
		this.ecifData.getQueryModelObj().setQuerySql(TxMsgModeQuerySQLHolder.getQuerySQL(this.txModel.getTxDef()
						.getTxCode(), nodeId));
		boolean nodeGroupFlag = false;
		if (!MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())) {
			nodeGroupFlag = false;
		} else {
			nodeGroupFlag = true;
		}
		if(nodeGroupFlag){
			/**********���÷�ҳ��ѯ��ʼ***/
			Element p=(Element)this.ecifData.getBodyNode().selectSingleNode("//"+nodeCode+"PageInfo/currentPage");
			if(p!=null&&!StringUtil.isEmpty(p.getTextTrim())){
				this.ecifData.getQueryModelObj().setPageStart(Integer.valueOf(p.getTextTrim()));
			}else{
				this.ecifData.getQueryModelObj().setPageStart(1);
			}
			p=(Element)this.ecifData.getBodyNode().selectSingleNode("//"+nodeCode+"PageInfo/pageSize");
			if(p!=null&&!StringUtil.isEmpty(p.getTextTrim())){
				this.ecifData.getQueryModelObj().setPageSize(Integer.valueOf(p.getTextTrim()));
			}else{
				this.ecifData.getQueryModelObj().setPageSize(MdmConstants.queryMaxsize);
			}
			/***********************/
		}
		this.ecifData.getQueryModelObj().setPageSelect(nodeGroupFlag);
		
		try {
			// ���ò�ѯ����
			if(!fkAttrIdValueNull){
				bizLogic.process(this.ecifData);
			}else{
				this.ecifData.getQueryModelObj().setResultSize(0);
				this.ecifData.getQueryModelObj().setResulList(null);
			}
			if (!this.ecifData.isSuccess()) {
				return;
			}
		} catch (Exception e) {
			log.error("������Ϣ��", e);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
			return;
		}
		if (this.ecifData.getQueryModelObj().getResultSize() > 1) {
			if (!MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup()) && !MdmConstants.NODE_NOGROUP_LIST_SUFFIX) {
				this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_TOO_MANY.getCode(), "���ؽ������");
				log.warn("��ѯ���صĽ����¼�������õĲ�һ�£������Ľ��Ϊ1��");
				return;
			}
			nodeGroupFlag = true;
		}
		if (nodeGroupFlag) {
			if("0".equals(currentxMsgNode.getNodeDisplay())){
				this.ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), "��Ӧ�������ô���");
				log.warn("�ڵ������ò������Ӱʽ��ǩ");
				return;
			}
			if (!MdmConstants.NODE_GROUP_NO_LABEL.equals(currentxMsgNode.getNodeLabel())) {//------------------------------
				pointer = pointer.addElement(nodeCode+ MdmConstants.NODE_GROUP_SUFFIX);
			}
		} else {//------------------------------
			if (!MdmConstants.NODE_TP_T.equals(currentxMsgNode.getNodeTp())) {
				if(!"0".equals(currentxMsgNode.getNodeDisplay())){
					pointer = pointer.addElement(nodeCode);
				}
			}

		}
		List<Map<String, Object>> queryResultList = (List<Map<String, Object>>) this.ecifData
				.getQueryModelObj().getResulList();
		boolean noResult = false;
		if (queryResultList == null || queryResultList.size() == 0) {

			String msg = "����:���Ľڵ�" + nodeCode + "û�в�ѯ������!";
			log.warn(msg);
			// ����һ���ռ�¼
			queryResultList = new ArrayList<Map<String, Object>>();
			queryResultList.add(new HashMap<String, Object>());
			noResult = true;
		} else {
			isAllQueryNull = false;
		}
		
		if(this.ecifData.getQueryModelObj().isPageSelect()){
			/********���÷�ҳ��Ϣ**************/
			Element pageInfo=pointer.addElement(currentxMsgNode.getNodeCode()+"PageInfo");
			pageInfo.addElement("currentPage").setText(String.valueOf(this.ecifData
				.getQueryModelObj().getPageStart()));
			pageInfo.addElement("pageSize").setText(String.valueOf(this.ecifData
				.getQueryModelObj().getResultSize()));
			pageInfo.addElement("totalSize").setText(String.valueOf(this.ecifData
				.getQueryModelObj().getTotalCount()));
			/**********************/
		}
		
		
		String newValue;
		for (Map<String, Object> rowMap : queryResultList) {
			Element hand = null;
			if (!noResult || !nodeGroupFlag) {
				if(!"0".equals(currentxMsgNode.getNodeDisplay())){
					hand = pointer.addElement(currentxMsgNode.getNodeCode());//------------------------------
				}else{
					hand = pointer;
				}
				for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
					Object value = rowMap.get(txMsgNodeAttr.getAttrCode());
					newValue = null;
					parentNodeValueMap.put(txMsgNodeAttr.getAttrId(), value);
					if (!StringUtil.isEmpty(value)) {
						/*** ��ϢƯ�� ****/
						if (!EcifPubDataUtils.infoLevelRead(authType, authCode,
								MdmConstants.CTRLTYPE_QUERY,
								txMsgNodeAttr.getTabId(),
								txMsgNodeAttr.getColId())) {
							if(StringUtil.isEmpty(MdmConstants.txInfoCtrlformat)){//�������ռλ��Ϊ�գ���ǩ��Ҫ
								continue;
							}
							if(txMsgNodeAttr.getDataLen()!=null&&txMsgNodeAttr.getDataLen()<MdmConstants.txInfoCtrlformat.length()){
								newValue = MdmConstants.txInfoCtrlformat.substring(
										MdmConstants.txInfoCtrlformat.length()-txMsgNodeAttr.getDataLen().intValue());
							}else{
								newValue = MdmConstants.txInfoCtrlformat;
							}
						} else {
							// ת�����ַ���
							newValue = this.convertObjectToString(nodeId,
									txMsgNodeAttr, value);
							if (!this.ecifData.isSuccess()) {
								return;
							}
							// ת��
							newValue = this.processCTDR(txMsgNodeAttr, newValue);
						}
					}
					if (!this.ecifData.isSuccess()) {
						return;
					}
					if(txMsgNodeAttr.getDataLen()!=null && txMsgNodeAttr.getDataLen()!=0){
						hand.addElement(txMsgNodeAttr.getAttrCode()).setText(StringUtil.toString(newValue));
					}
				}
			} else {
				hand = pointer;
			}
//			for (String dataKey : rowMap.keySet()) {
//				for (String reqKey : reqParamMap.keySet()) {
//					if (reqKey.equals(dataKey)) {
//						reqParamMap.put(reqKey,
//								String.valueOf(rowMap.get(reqKey)));
//					}
//				}
//			}
			if (!noResult) {
				buildChildNodeXML(hand, currentxMsgNode, txMsgNodeMap,
						reqParamMap, parentNodeValueMap);
			}
		}

	}

}

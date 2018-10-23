/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����CustIdentiEngine.java
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.QueryModel;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�CustIdentiEngine
 * @���������Զ���ͻ�ʶ��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:10:58
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:10:58
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Component
@Scope("prototype")
public class CustIdentiEngine extends AbstractEcifDealEngine {
	private static Logger log = LoggerFactory.getLogger(CustIdentiEngine.class);

	/*
	 * (non-Javadoc)
	 * @see
	 * com.ytec.mdm.integration.transaction.core.AbstractEcifDealEngine#execute
	 * (com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void execute(EcifData data) {
		try {
			this.ecifData = data;
			this.ecifData.setQueryModelObj(new QueryModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			// ��ȡ��ѯ����,���Ķ������ԡ����������������Ϊ��ѯ����
			Map<String, String> reqParamMap = new HashMap<String, String>(data.getParameterMap());
			// ��ȡ�ͻ�ʶ�𷵻����
			List<TxMsgNode> tsMsgNodeList = this.txModel.getResTxMsgNodeList();
			// ���ڵ�(�ͻ�ʶ��������ڸ��ڵ���)
			TxMsgNode rootTxMsgNode = null;
			for (TxMsgNode txMsgNode : tsMsgNodeList) {
				if (txMsgNode.getUpNodeId() == -1) {
					rootTxMsgNode = txMsgNode;
					break;
				}
			}
			// �ж������
			if (rootTxMsgNode == null) {
				data.setStatus(ErrorCode.ERR_XML_CFG_NO_ROOT_NODE.getCode(), "�������ô���");
				log.warn("����:{},�ͻ�ʶ����������ô���,û�����ø��ڵ�!", data.getTxCode());
				return;
			}
			// ���״�����
			String txDealClass = txModel.getTxDef().getTxDealClass();
			this.bizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);
			// �ͻ�����
			searchCustomer(rootTxMsgNode, reqParamMap);
		} catch (Exception e) {
			log.error("������Ϣ", e);
			data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
		}
		return;
	}

	/**
	 * @��������:searchCustomer
	 * @��������:�ͻ�ʶ���ж�
	 * @�����뷵��˵��:
	 * @param currentxMsgNode
	 *        �ͻ�ʶ���������ڵ�
	 * @param reqParamMap
	 *        �ͻ�ʶ�����
	 * @throws Exception
	 * @�㷨����:
	 */
	private void searchCustomer(TxMsgNode currentxMsgNode, Map<String, String> reqParamMap) throws Exception {
		// �������ݿ��Ľڵ�
		Long nodeId = currentxMsgNode.getNodeId();
		// ��ѯ�ֶ�
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel.getTxMsgNodeAttrMap().get(nodeId);
		if (txMsgNodeAttrList == null || txMsgNodeAttrList.size() == 0) {
			String msg = "���Ľڵ�:" + currentxMsgNode.getNodeCode() + "û�в�ѯ����";
			log.warn(msg);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(), "û�����÷�����");
			return;
		}
		Map<String, Object> requestMap = new HashMap<String, Object>();
		List<String> queryFieldList = new ArrayList<String>();
		/** �����ѯ���� */
		for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
			queryFieldList.add(txMsgNodeAttr.getAttrCode());
		}
		this.ecifData.getQueryModelObj().setQueryFieldList(queryFieldList);
		/**
		 * ��ȡ������������
		 */
		getFilterParam(currentxMsgNode, requestMap, reqParamMap);
		if (!this.ecifData.isSuccess()) { return; }
		this.ecifData.getQueryModelObj().setParentNodeValueMap(requestMap);
		// ======================�����ѯ����===========end
		// �����ѯ���
		this.ecifData.getQueryModelObj().setQuerySql(
				TxMsgModeQuerySQLHolder.getQuerySQL(this.txModel.getTxDef().getTxCode(), nodeId));
		// ���ò�ѯ����
		bizLogic.process(this.ecifData);
		List<Map<String, Object>> queryResultList = (List<Map<String, Object>>) this.ecifData.getQueryModelObj()
				.getResulList();
		// ��װ��ѯ���
		if (queryResultList == null || queryResultList.size() == 0) {
			// ʶ�𲻵��ͻ�
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
			return;
		} else if (queryResultList.size() == 1) {
			ecifData.resetStatus();
			Map ob = queryResultList.get(0);
			CustStatus custStatCtrl = null;
			// �ж��ͻ��Ƿ�ʧЧ
			if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus((String) ob.get(MdmConstants.TX_CUST_STATE))) != null
					&& custStatCtrl.isReOpen()) {// ʧЧ�ͻ�
				log.warn("��ʶ�𵽿ͻ�[{}],���ͻ�״̬[{}({})],��ʧЧ", (String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO),
						custStatCtrl.getCustStatus(), custStatCtrl.getCustStatusDesc());
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
			} else {
				ecifData.setCustId(StringUtil.toString(ob.get(MdmConstants.CUSTID)));
				// ecifData.setEcifCustNo((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
				ecifData.getCustId((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
				ecifData.setCustType((String) ob.get(MdmConstants.TX_CUST_TYPE));
				ecifData.setCustStatus((String) ob.get(MdmConstants.TX_CUST_STATE));
			}
		} else {
			// �жϲ�ѯ������ͻ����ų�ʧЧ�ͻ�(ע��,�ϲ���)��
			Map ob = null;
			int availableNum = 0;
			CustStatus custStatCtrl = null;
			for (int i = 0; i < queryResultList.size(); i++) {
				ob = queryResultList.get(i);
				// �ж��ͻ��Ƿ�ʧЧ
				if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(
						(String) ob.get(MdmConstants.TX_CUST_STATE))) != null
						&& custStatCtrl.isReOpen()) {// ʧЧ�ͻ�
					continue;
				}
				availableNum++;
				// �ͻ�Ψһ
				if (availableNum == 1) {
					// ���ÿͻ�ID
					ecifData.setCustId(StringUtil.toString(ob.get(MdmConstants.CUSTID)));
					// ����ECIF�ͻ���
					// ecifData.setEcifCustNo((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
					ecifData.getCustId((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
					// ���ÿͻ�����
					ecifData.setCustType((String) ob.get(MdmConstants.TX_CUST_TYPE));
					// ���ÿͻ�״̬
					ecifData.setCustStatus((String) ob.get(MdmConstants.TX_CUST_STATE));
				} else {
					// �ͻ�ʶ��Ψһ
					ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "���ؿͻ���Ψһ");
					return;
				}
			}
			if (availableNum == 0) {
				// ʶ�𲻵��ͻ�
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else {
				// ����״̬����
				ecifData.resetStatus();
			}
		}
		return;

	}

}

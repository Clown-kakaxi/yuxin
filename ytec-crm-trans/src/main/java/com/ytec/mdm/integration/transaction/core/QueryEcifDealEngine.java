/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����QueryEcifDealEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:10:57
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.QueryModel;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxNode4CRM;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;

/**
 * @��Ŀ���ƣ�ytec-mdm-trans(CRM)
 * @�����ƣ�QueryEcifDealEngine
 * @����������ѯ���״�������
 * @��������:��ѯ�������� ��������ѯ���������ò�ѯ�߼�
 * @�����ˣ�wangtb@yuchengtech.com
 * @����ʱ�䣺2014-12-06 ����11:10:58
 *                  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class QueryEcifDealEngine extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory.getLogger(QueryEcifDealEngine.class);

	/**
	 * @��������:nodeGroupFlag
	 * @��������:�ڵ�����Ʊ�ʶ
	 * @since 1.0.0
	 */
	private boolean nodeGroupFlag = false;
	/**
	 * @��������:authType
	 * @��������:���ݷ���Ȩ�����
	 * @since 1.0.0
	 */
	private String authType = null;
	/**
	 * @��������:authCode
	 * @��������:����Ȩ�޷�����
	 * @since 1.0.0
	 */
	private String authCode = null;
	/**
	 * @��������:isAllQueryNull
	 * @��������:�Ƿ����в�ѯΪ��
	 * @since 1.0.0
	 */
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
			} else {
				authCode = data.getAuthCode();
				authType = reqParamMap.get(MdmConstants.AUTH_TYPE);
			}

			// ��ȡ���״����ಢ����ѯ����
			String txDealClass = txModel.getTxDef().getDealClass();
			this.bizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);

			bizLogic.process(data);

			// ����Ĭ����Ӧ��Ϣ���ڵ�
			Element responseEle = DocumentHelper.createElement("ResponseBody");

			if (data.getQueryModelObj().getResulList() != null && data.getQueryModelObj().getResulList().size() != 0) {
				isAllQueryNull = false;
			}

			// ��ѯ���������������������ѯ������������ݣ�������Ӧ��ϢΪErrorCode.WRN_NONE_FOUND
			if (isAllQueryNull && data.isSuccess()) {
				data.setStatus(ErrorCode.WRN_NONE_FOUND);
				data.setSuccess(true);
				return;
			}
			if (data.isSuccess()) {
				// ����ɹ������ݲ�ѯ�����װ��Ӧ���ģ���queryBizLogic�л�ȡ����ѯ�����װ�� queryModelObj�е�resultList��
				// ��ŷ�ʽΪList,List��Ϊһ��Map��key=�����нڵ�����value=JQL��ѯ�����
				buildRespNodeXML(responseEle, data.getQueryModelObj().getResulList(), this.txModel);
				data.setRepNode(responseEle);
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
	 * @param pointer
	 * @param currentxMsgNode
	 * @param txMsgNodeMap
	 * @param reqParamMap
	 * @param parentNodeValueMap
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @�㷨����:
	 */
	private void buildRespNodeXML(Element respBody, List<Map<String, Object>> respData, TxModel4CRM model) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException {

		// ��ȡ���Ķ��壬���������װ����ʱ����Ӧ����
		// �����Ķ���洢��Map�У�key:nodeName(�����нڵ���),value:TxNode4CRM���󣬶�ӦtransConf.xml��//trans/trans/node�ڵ�����
		List<TxNode4CRM> nodeDef_s = model.getTxNodeList();
		Map<String, TxNode4CRM> nodeMap = new HashMap<String, TxNode4CRM>();

		// ������ѯ������ض�����װXML����
		// 1����ȡXML��node������Ϣ����ѯ��������ݶ���
		// 2��ͨ��node���ã��������ݶ���ͨ��Java�����ȡʵ����(ʵ����ʵ����Ҳ�ɿ����Ǳ���������һ����)����ͨ��ʵ���������������ñ�����Ҷ�ӽڵ�
		// 3��Ϊ��ʡ���ĳ��ȣ�����ֵ��Ҷ�ӽڵ㲻��setText()����������Ϊ����<node/>�Ŀսڵ�
		Map<String, Object> data = respData.get(0);
		Set<String> key_s = data.keySet();
		Iterator<String> itr = key_s.iterator();
		for (TxNode4CRM nodeDef : nodeDef_s) {
			String nodeName = nodeDef.getName();
			nodeMap.put(nodeName, nodeDef);
			Object entity = data.get(nodeName);

			// ����ֵ������ӿ�XML�ڵ�
			if (entity == null || ((List) entity).size() == 0) {
				if (nodeDef.isList() == true) {
					respBody.addElement(nodeName + "List").addElement(nodeName);
				} else {
					respBody.addElement(nodeName);
				}
				continue;
			}

			// TODO:��Ҫ�����������ϣ�����Ƿ�List(node.isList)�����Ż�����

			// ��ѯ���(��������)��װ��List�У���Ҫ����ȡֵ
			// �ýڵ��ӦList���ݶ��󳤶�>1�����ж�����¼
			if (nodeDef.isList() == true) {// entity instanceof List && ((List) entity).size() > 1) {
				// �����ƶ��Ŵ�ϵͳ�������List�ڵ㣬��ڵ��Ϸ����һ${nodeName}List
				Element nodeListEle = respBody.addElement(nodeName + "List");
				List<Object> obj_s = (List<Object>) entity;
				for (Object obj : obj_s) {
					Field[] field_s = obj.getClass().getDeclaredFields();
					Element nodeEle = nodeListEle.addElement(nodeName);
					for (Field field : field_s) {
						String fieldName = field.getName();
						if (nodeDef.getRespNodes().contains(fieldName)) {
							String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
							Object fieldValue = MethodUtils.invokeMethod(obj, methodName, null);
							if (fieldValue == null || "null".equals(fieldValue)) {
								nodeEle.addElement(fieldName);
							} else {
								nodeEle.addElement(fieldName).setText("" + fieldValue);
							}
						} else {
							continue;
						}
					}
				}
			}
			// �ڵ��Ӧ��¼(ʵ����)��ѯ���ֻ��һ��
			// ʵ����entity instanceof List�ж϶��࣬��ΪqueryBizLogic�������б�����������JQL��ѯ�����List��ʽ�洢
			// ��Ϊ��ֹ (List) entity ǿת�������ϴ��ж�
			else {
				if (entity instanceof List && ((List) entity).size() > 1) {
					String err = String.format("%s(%s)", ErrorCode.ERR_ECIF_DATA_TOO_MANY.getChDesc(), nodeName);
					this.ecifData.setSuccess(false);
					this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_TOO_MANY.getCode(), err);
					return;
				}
				Element nodeEle = respBody.addElement(nodeName);

				// ��ѯ���ͳһ��װ��List�У������ֻ��һ����¼����ֱ��ǿת
				Object obj = ((List) entity).get(0);
				Field[] field_s = obj.getClass().getDeclaredFields();
				for (Field field : field_s) {
					String fieldName = field.getName();
					if (nodeDef.getRespNodes().contains(fieldName)) {
						String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Object fieldValue = MethodUtils.invokeMethod(obj, methodName, null);
						if (fieldValue == null || "null".equals(fieldValue)) {
							nodeEle.addElement(fieldName);
						} else {
							nodeEle.addElement(fieldName).setText("" + fieldValue);
						}
					} else {
						continue;
					}
				}
			}
		}
	}
}

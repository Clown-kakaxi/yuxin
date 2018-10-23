/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @�ļ�����FixedRequestCoderHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-15-����5:26:56
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxDef;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.model.TxModel;
import com.ytec.mdm.interfaces.socket.http.tools.DynamicBytes;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�FixedRequestCoderHandler
 * @���������������Ľ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-15 ����5:26:56
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-15 ����5:26:56
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class FixedRequestCoderHandler implements IFixedRequestCoder{
	protected static Logger log = LoggerFactory
			.getLogger(FixedRequestCoderHandler.class);
	/**
	 * @��������:bufferSize
	 * @��������:��������С
	 * @since 1.0.0
	 */
	private static int bufferSize = 1024;
	/**
	 * @��������:txFixedAttrsModelMap
	 * @��������:������������������ģ��
	 * @since 1.0.0
	 */
	private static ConcurrentHashMap<String, FixedAttrsModel> txFixedAttrsModelMap = new ConcurrentHashMap<String, FixedAttrsModel>();

	/**
	 * @��������:requestFixedStringToXml
	 * @��������:��������ת����ƽ��XML
	 * @�����뷵��˵��:
	 * @param data
	 * @throws Exception
	 * @�㷨����:
	 */
	public void requestFixedStringToXml(EcifData data) throws Exception {
		FixedAttrsModel fixedAttrsModel = null;
		/**��������������ģ��Map�в���**/
		if ((fixedAttrsModel = getFixedAttrsModel(data.getTxCode(), true)) != null) {//��Ӧģ�ʹ���
			List<TxMsgNodeAttr> requestAttrList = fixedAttrsModel.getRequestAttrList();
			int length = requestAttrList.size();
			TxMsgNodeAttr point = null;
			//�½�������
			Document requestDoc = DocumentHelper.createDocument();
			if (StringUtil.isEmpty(fixedAttrsModel.getRequestRoot())) {
				String msg = "����:" + data.getTxCode() + ",������û�����ø����";
				data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),msg);
				log.error(msg);
				return;
			}
			//������������
			Element rootElement = DocumentHelper.createElement(fixedAttrsModel.getRequestRoot());
			requestDoc.setRootElement(rootElement);
			//��ȡ�����ֽ���
			byte[] fixedBytes = data.getPrimalMsg().getBytes(data.getCharsetName());
			String text = null;
			int offset = 0;
			int fixedStringLength = fixedBytes.length;
			for (int i = 0; i < length; i++) {//��������������
				point = requestAttrList.get(i);
				if (point.getAttrSeq() - 1 == i) {
					if (fixedStringLength < offset + point.getDataLen()) {//���ݳ���
						String msg = "����:" + data.getTxCode() + ",�����ĳ��Ȳ�ƥ��";
						data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),msg);
						log.error(msg);
						return;
					}
					//ͨ�����Ƚ�ȡ����
					text = new String(fixedBytes, offset, point.getDataLen().intValue(),data.getCharsetName());
					offset += point.getDataLen();
					log.debug("{}:{}|{}|{}", i, point.getAttrCode(),point.getDataLen(), text);
					//�½�xml���Բ���������
					rootElement.addElement(point.getAttrCode()).setText(text);
				} else {
					String msg = "����:" + data.getTxCode() + ",�����Ķ����������ô���";
					data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
					log.error(msg);
					return;
				}
			}
			//���ñ���
			data.setPrimalDoc(requestDoc);
			//������������
			data.setBodyNode(rootElement);
			//log.debug(XMLUtils.elementToString(rootElement));
		} else {
			String msg = "����:" + data.getTxCode() + ",���������ô���";
			data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
			log.error(msg);
			return;
		}
		return;
	}

	/**
	 * @��������:responseXmlToFixedString
	 * @��������:ƽ��XMLת���ɶ�������
	 * @�����뷵��˵��:
	 * @param data
	 * @return
	 * @throws Exception
	 * @�㷨����:
	 */
	public String responseXmlToFixedString(EcifData data) throws Exception {
		byte[] b = responseXmlToFixedByte(data);
		if(data.isSuccess()){
			return new String(b,data.getCharsetName());
		}else{
			return "";
		}
	}

	/**
	 * @��������:responseXmlToFixedByte
	 * @��������:ƽ��XMLת���ɶ����ֽ���
	 * @�����뷵��˵��:
	 * @param data
	 * @return
	 * @throws Exception
	 * @�㷨����:
	 */
	public byte[] responseXmlToFixedByte(EcifData data) throws Exception {
		FixedAttrsModel fixedAttrsModel = null;
		/**��������������ģ��Map�в���**/
		if ((fixedAttrsModel = getFixedAttrsModel(data.getTxCode(), false)) != null) {//��Ӧģ�ʹ���
			//��ʼ����Ӧ�ֽڻ�����
			DynamicBytes bos = new DynamicBytes(bufferSize);
			List<TxMsgNodeAttr> responseAttrList = fixedAttrsModel.getResponseAttrList();
			int length = responseAttrList.size();
			TxMsgNodeAttr point = null;
			//��ȡ��ӦXML����
			Element rootElement = data.getRepNode();
			//log.info(XMLUtils.elementToString(rootElement));
			String text = null;
			byte[] b = null;
			for (int i = 0; i < length; i++) {//��������������
				point = responseAttrList.get(i);
				if (point.getAttrSeq() - 1 == i) {
					text = rootElement.elementText(point.getAttrCode());
					log.debug("{}:{}|{}|{}", i, point.getAttrCode(),point.getDataLen(), text);
					if(text==null){
						b=null;
					}else{
						b=text.getBytes(data.getCharsetName());
					}
					b = fixedAttrFormat(b, point.getDataLen().intValue(),point.getDataType());//��ʽ������
					bos.append(b, b.length);
				} else {
					String msg = "����:" + data.getTxCode() + ",��Ӧ���Ķ����������ô���";
					data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
					log.error(msg);
					return null;
				}
			}
			//������Ӧ�ֽ���
			return bos.bytes();
		} else {
			String msg = "����:" + data.getTxCode() + ",���������ô���";
			data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
			log.error(msg);
			return null;
		}
	}

	/**
	 * @��������:getFixedAttrsModel
	 * @��������:��ȡ��������
	 * @�����뷵��˵��:
	 * @param txCode
	 * @param isRequest
	 * @return
	 * @throws Exception
	 * @�㷨����:
	 */
	private FixedAttrsModel getFixedAttrsModel(String txCode, boolean isRequest)
			throws Exception {
		FixedAttrsModel fixedAttrsModel = null;
		TxModel txModel = null;
		if ((txModel = TxModelHolder.getTxModel(txCode)) != null) {
			if ((fixedAttrsModel = txFixedAttrsModelMap.get(txCode)) != null) {//��ȡ��������
				if (isRequest) {
					// ����޸������Ƿ����仯
					TxDef txDef = txModel.getTxDef();
					Timestamp newTimestamp = txDef.getUpdateTm();
					Timestamp oldTimestamp = fixedAttrsModel.getUpdateTm();
					if (newTimestamp != null
							&& (oldTimestamp == null || newTimestamp
									.after(oldTimestamp))) {//���ñ��޸�
						fixedAttrsModel = buildFixedAttrsModel(txModel);
						txFixedAttrsModelMap.replace(txCode, fixedAttrsModel);
					}
				}
			} else {
				fixedAttrsModel = buildFixedAttrsModel(txModel);
				FixedAttrsModel txFixedAttrsModelTemp = null;
				if ((txFixedAttrsModelTemp = txFixedAttrsModelMap.putIfAbsent(
						txCode, fixedAttrsModel)) != null) {
					fixedAttrsModel = txFixedAttrsModelTemp;
				}
			}
		}
		return fixedAttrsModel;
	}

	/**
	 * @��������:buildFixedAttrsModel
	 * @��������:�����������Զ���
	 * @�����뷵��˵��:
	 * @param txModel
	 * @return
	 * @throws Exception
	 * @�㷨����:
	 */
	private FixedAttrsModel buildFixedAttrsModel(TxModel txModel)
			throws Exception {
		FixedAttrsModel fixedAttrsModel = new FixedAttrsModel();
		TxDef txDef = txModel.getTxDef();
		//���ø���ʱ��
		fixedAttrsModel.setUpdateTm(txDef.getUpdateTm());
		//���ý�����
		fixedAttrsModel.setTxCode(txDef.getTxCode());
		//����������ڵ�
		fixedAttrsModel.setRequestRoot(txModel.getReqTxMsg().getMainMsgRoot());
		//������Ӧ���ڵ�
		fixedAttrsModel.setResponseRoot(txModel.getResTxMsg().getMainMsgRoot());
		//���������������б�
		fixedAttrsModel.setRequestAttrList(getFixedAttrList(
				txModel.getReqTxMsgNodeList(), txModel.getTxMsgNodeAttrMap()));
		//������Ӧ�������б�
		fixedAttrsModel.setResponseAttrList(getFixedAttrList(
				txModel.getResTxMsgNodeList(), txModel.getTxMsgNodeAttrMap()));
		return fixedAttrsModel;
	}

	/**
	 * @��������:getFixedAttrList
	 * @��������:�ӽ��������н���������԰�˳������
	 * @�����뷵��˵��:
	 * @param txMsgNodeList
	 * @param txMsgNodeAttrMap
	 * @return
	 * @throws Exception
	 * @�㷨����:
	 */
	private List<TxMsgNodeAttr> getFixedAttrList(List<TxMsgNode> txMsgNodeList,
			Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap) throws Exception {
		List<TxMsgNodeAttr> temp = new ArrayList<TxMsgNodeAttr>();
		if (txMsgNodeList != null && txMsgNodeList.size() > 0) {
			List<TxMsgNodeAttr> t = null;
			for (TxMsgNode TxMsgNode : txMsgNodeList) {//��ȡ����µ���������
				t = txMsgNodeAttrMap.get(TxMsgNode.getNodeId());
				if (t != null) {
					for (TxMsgNodeAttr t_i : t) {
						if (t_i.getAttrSeq() != null&&t_i.getAttrSeq()!=0) {// ˳��Ų���Ϊnull
							temp.add(t_i);
						}
					}
				}
			}
			// ����TxMsgNodeAttr attrSeq��������
			if (!temp.isEmpty()) {
				Comparator<TxMsgNodeAttr> comparator = new ComparatorAttr();
				Collections.sort(temp, comparator);
				return temp;
			} else {
				return temp;
			}

		} else {
			return temp;
		}
	}

	/**
	 * @��������:fixedAttrFormat
	 * @��������:���������ֶθ�ʽ��
	 * @�����뷵��˵��:
	 * @param text
	 *            ����
	 * @param leng
	 *            ����
	 * @param DataType
	 *            ����
	 * @return
	 * @�㷨����:
	 */
	public abstract byte[] fixedAttrFormat(byte[] src, int leng,String DataType);

}

class ComparatorAttr implements Comparator<TxMsgNodeAttr> {
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(TxMsgNodeAttr arg0, TxMsgNodeAttr arg1) {
		//��������
		return arg0.getAttrSeq().intValue() - arg1.getAttrSeq().intValue();
	}

}

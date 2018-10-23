/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.integration.sync
 * @�ļ�����SynchroToSystemHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:08:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.integration.sync;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.domain.txp.TxSyncErr;
import com.ytec.mdm.domain.txp.TxSyncLog;
import com.ytec.mdm.integration.sync.ptsync.SynchroExecuteHandler;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
 * @�����ƣ�FubonSynchroHandler
 * @�����������һ��������ͬ��������
 * @��������:
 * @�����ˣ�wangtb@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:08:44
 * @�޸��ˣ�wangtb@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:08:44
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Component
@Scope("prototype")
public class FubonSynchroHandler4CRM extends SynchroExecuteHandler {
	private static Logger log = LoggerFactory.getLogger(FubonSynchroHandler4CRM.class);
	private static String ECIFSYSCD = BusinessCfg.getString("appCd"); // ECIF��ESB�еı��

	// TODO �ı��ĸ�ʽ
	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Element databody) {
		if (databody == null) {
			log.error("ͬ������{},���ɵı�����Ϊ��", txSyncConf.getSyncConfId());
			return false;
		}
		try {
			SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
			Document requestDoc = DocumentHelper.createDocument();
			requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);

			Element transBody = requestDoc.addElement("TransBody");

			Element requestHeader = transBody.addElement("RequestHeader");
			requestHeader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt ��������
			requestHeader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm ����ʱ��
			requestHeader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo ������ˮ��
			requestHeader.addElement("ReqSysCd").setText(ECIFSYSCD);// ReqSysCd ��Χϵͳ����
			requestHeader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// ReqSeqNo ��Χϵͳ������ˮ��
			requestHeader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// ReqDt ��������
			requestHeader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// ReqTm ����ʱ��
			requestHeader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// ChnlNo ������
			requestHeader.addElement("BrchNo").setText(requestHeader.elementTextTrim("BrchNo"));// BrchNo ������
			requestHeader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// BizLine ҵ������
			requestHeader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// TrmNo �ն˺�
			requestHeader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// TrmIP �ն�IP
			requestHeader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// TlrNo ������Ա��

			databody.setName("RequestBody");
			transBody.add(databody);
			synchroRequestMsg = XMLUtils.xmlToString(requestDoc);

			// ����Ŵ�����ӿ��г��ȱ�ʶλΪ4λ����ʹ�����·�ʽ��װ���ģ����޸�SocketClient��packing����
			// synchroRequestMsg = String.format("%08d", synchroRequestMsg.length())+synchroRequestMsg;

			return true;
		} catch (Exception e) {
			log.error("ͬ������{},ͬ�������쳣", txSyncConf.getSyncConfId());
			log.error("��װͬ�������쳣", e);
		}
		return false;
	}

	@Override
	public boolean executeResult() {
		if (this.synchroResponseMsg != null) {
			try {
				Document root = XMLUtils.stringToXml(synchroResponseMsg.substring(8));

				String txStatCodeXpath = "//TransBody/ResponseTail/TxStatCode";
				String txStatDescXpath = "//TransBody/ResponseTail/TxStatString";
				String txStatDetailXpath = "//TransBody/ResponseTail/TxStatDesc";

				Node txStatCodeNode = root.selectSingleNode(txStatCodeXpath);
				Node txStatDescNode = root.selectSingleNode(txStatDescXpath);
				Node txStatDetailNode = root.selectSingleNode(txStatDetailXpath);
				if (txStatCodeNode == null || txStatDescNode == null || txStatDetailNode == null) {
					String msg = String.format("{},�յ���Χϵͳ[%s]��Ӧ���ı�Ҫ�ڵ�Ϊ��[��Ҫ�ڵ㣺%s,%s,%s]",
							ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("crmCd"),
							txStatCodeXpath, txStatDescXpath, txStatDetailXpath);
					syncLog = new TxSyncLog();
					syncLog.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
					syncLog.setSyncDealInfo(msg);

					syncErr = new TxSyncErr();
					syncErr.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
					syncErr.setSyncDealInfo(msg);
					return false;
				}

				String txStatCode = txStatCodeNode.getText().trim();
				String txStatDesc = txStatDescNode.getText().trim();
				String txStatDetail = "��Χϵͳ[" + BusinessCfg.getString("crmCd") + "]��Ӧ:txStatDesc:{" + txStatDesc
						+ "},txStatDetail:{" + txStatDetailNode.getText() + "}";

				syncLog = new TxSyncLog();
				syncLog.setSyncDealResult(txStatCode);
				syncLog.setSyncDealInfo(txStatDetail);
				if (ErrorCode.SUCCESS.getCode().equals(txStatCode)) {
					return true;
				} else {
					syncErr = new TxSyncErr();
					syncErr.setSyncDealResult(txStatCode);
					syncErr.setSyncDealInfo(txStatDetail);
					return false;
				}
			} catch (DocumentException e) {
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("crmCd"), e);
				return false;
			} catch (Exception e) {
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("crmCd"), e);
				return false;
			}
		} else {
			log.info("ͬ����Ӧ����Ϊ��");
		}
		return true;
	}
}

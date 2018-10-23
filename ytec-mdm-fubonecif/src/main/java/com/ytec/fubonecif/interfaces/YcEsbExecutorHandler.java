/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����YcEsbExecutorHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:10:10
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.Validator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.interfaces.esb.ycesb.server.YcEsbExecutor;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�YcEsbExecutorHandler
 * @��������YC ESB �ӿ� ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:10:10   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:10:10
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class YcEsbExecutorHandler extends YcEsbExecutor {

	private static Logger log = LoggerFactory
			.getLogger(YcEsbExecutorHandler.class);

	public YcEsbExecutorHandler() {
		super();
	}

	@Override
	protected void beforeExecutor() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void afterExecutor() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getEcifData() throws Exception {
		// TODO Auto-generated method stub
		Element requestHeader = (Element) this.doc
				.selectSingleNode("//transaction/header");
		if (requestHeader == null) {
			log.error("������ͷ������");
			throw new RequestIOException("������ͷ������");
		}
		data.setRequestHeader(requestHeader);
		Element bodyNode = (Element) this.doc
				.selectSingleNode("//transaction/body/request");
		if (bodyNode == null) {
			log.error("�������岻����");
			throw new RequestIOException("�������岻����");
		}
		data.setBodyNode(bodyNode);
		String txCode = bodyNode.elementTextTrim(MdmConstants.TX_DEF_TX_CODE);
		//String reqSysCd = bodyNode.elementTextTrim("sndAppCd");	
		String reqSysCd = requestHeader.selectSingleNode("//header/msg/sndAppCd").getText();		//jlnx ECIF����������
		String reqSeqNo = requestHeader.selectSingleNode("//header/msg/seqNb").getText();
		data.setTxCode(txCode);
		data.setOpChnlNo(reqSysCd);
		data.setReqSeqNo(reqSeqNo);
		/** ������ ***/
		String brchNo = bodyNode.elementTextTrim("brchNo");
		data.setBrchNo(brchNo);
		/** ��Ա�� ***/
		String tlrNo = bodyNode.elementTextTrim("tlrNo");
		data.setTlrNo(tlrNo);
		/** ��Ա�� ***/
		String authPwd = bodyNode.elementTextTrim("authPwd");
//		data.setAuthPwd(authPwd);
	}

	@Override
	protected String createOutputDocument() throws Exception {
		// TODO Auto-generated method stub
		Document responseDoc = DocumentHelper.createDocument();
		Element root = responseDoc.addElement("transaction");
		Element header = root.addElement("header");
		Element body = root.addElement("body");
//jlnx		
		if(data.getRequestHeader().elementText("ver")!=null){
			body.addElement("ver").setText(data.getRequestHeader().elementText("ver"));
		}
		if(data.getRequestHeader().elementText("ext")!=null){
			body.addElement("ext").setText(data.getRequestHeader().elementText("ext"));   //����ESB�������
		}

//		header.addElement("ver").setText("1.0");
		Element msg = header.addElement("msg");
		Element reqMsg = data.getRequestHeader().element("msg");
		// �����һλ��0��Ϊ1
		String msgCd = reqMsg.elementText("msgCd");
		if (msgCd != null && msgCd != "") {
			msgCd = msgCd.trim();
			msg.addElement("msgCd").setText(
					msgCd.substring(0, msgCd.length() - 1).concat("1"));
		} else {
			msg.addElement("msgCd");
		}
		Date now = new Date();
		msg.addElement("callTyp").setText(reqMsg.elementText("callTyp"));
		msg.addElement("seqNb").setText(reqMsg.elementText("seqNb"));
		msg.addElement("sndAppCd").setText(BusinessCfg.getString("appCd"));
		msg.addElement("sndDt").setText(
				new SimpleDateFormat("yyyyMMdd").format(now));
		msg.addElement("sndTm").setText(
				new SimpleDateFormat("HHmmss").format(now));
		msg.addElement("rcvAppCd").setText(reqMsg.elementText("sndAppCd"));
		msg.addElement("refMsgCd").setText(reqMsg.elementText("msgCd"));
		msg.addElement("refCallTyp").setText(reqMsg.elementText("callTyp"));
		msg.addElement("refSndAppCd").setText(reqMsg.elementText("sndAppCd"));
		msg.addElement("refSndDt").setText(reqMsg.elementText("sndDt"));
		msg.addElement("refSeqNb").setText(reqMsg.elementText("seqNb"));
		if(reqMsg.elementText("replyToQ")!=null){
			msg.addElement("replyToQ").setText(reqMsg.elementText("replyToQ"));   //����ESB�������
		}
		body.add(data.getRepNode());
		Element status = header.addElement("status");
		status.addElement("retCd").setText(data.getRepStateCd());
		status.addElement("desc").setText(data.getDetailDes());
		status.addElement("location");
		status.addElement("appCd").setText(BusinessCfg.getString("appCd"));
		status.addElement("ip").setText(StringUtil.getLocalIp());
		
		//resXml = XMLUtils.xmlToString(responseDoc);
		responseDoc.setXMLEncoding(esbCharsetName);
		resXml = responseDoc.getRootElement().asXML();

//		resXml = new String(resXml.getBytes("UTF-8"));
		System.out.println("========================================1");
		System.out.println(resXml);
		
		return resXml;
	}

	@Override
	protected String createDefauteMsg(String errorCode, String msg) {
		// TODO Auto-generated method stub
		try {
			Document responseDoc = DocumentHelper.createDocument();
			responseDoc.setXMLEncoding(esbCharsetName);
			Element root = responseDoc.addElement("transaction");
			Element header = root.addElement("header");
			Element body = root.addElement("body");
			if(data.getRepHeader().elementText("ver")!=null){
				body.addElement("ver").setText(data.getRepHeader().elementText("ver"));
			}
			Element msgE = header.addElement("msg");
			Element reqMsg = data.getRequestHeader().element("msg");
			// �����һλ��0��Ϊ1
			String msgCd = reqMsg.elementText("msgCd");
			if (msgCd != null && msgCd != "") {
				msgCd = msgCd.trim();
				msgE.addElement("msgCd").setText(
						msgCd.substring(0, msgCd.length() - 1).concat("1"));
			} else {
				msgE.addElement("msgCd");
			}
			Date now = new Date();
			msgE.addElement("callTyp").setText(reqMsg.elementText("callTyp"));
			msgE.addElement("seqNb").setText(reqMsg.elementText("seqNb"));
			msgE.addElement("sndAppCd").setText(BusinessCfg.getString("appCd"));
			msgE.addElement("sndDt").setText(
					new SimpleDateFormat("yyyyMMdd").format(now));
			msgE.addElement("sndTm").setText(
					new SimpleDateFormat("HHmmss").format(now));
			msgE.addElement("rcvAppCd").setText(reqMsg.elementText("sndAppCd"));
			msgE.addElement("refMsgCd").setText(reqMsg.elementText("msgCd"));
			msgE.addElement("refCallTyp")
					.setText(reqMsg.elementText("callTyp"));
			msgE.addElement("refSndAppCd").setText(
					reqMsg.elementText("sndAppCd"));
			msgE.addElement("refSndDt").setText(reqMsg.elementText("sndDt"));
			msgE.addElement("refSeqNb").setText(reqMsg.elementText("seqNb"));
			body.add(data.getRepNode());
			Element status = header.addElement("status");
			status.addElement("retCd").setText(errorCode);
			status.addElement("desc").setText(msg);
			status.addElement("location");
			status.addElement("appCd").setText(BusinessCfg.getString("appCd"));
			status.addElement("ip").setText(StringUtil.getLocalIp());
			resXml = XMLUtils.xmlToString(responseDoc);
			return resXml;
		} catch (Exception e) {
			log.error("��װĬ�ϱ����쳣",e);
			resXml=e.toString();
			return resXml;
		}
	}

}

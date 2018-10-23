/**
 * @��Ŀ����ytec-mdm-sampleecif
 * @������com.ytec.sampleecif.interfaces
 * @�ļ�����SocketExecutorHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-����1:44:23
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.interfaces.socket.http.server;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.integration.transaction.core.WriteEcifDealEngine;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.interfaces.SocketRequestCodeHelper;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-sampleecif
 * @�����ƣ�SocketExecutorHandler
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-16 ����1:44:23
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-16 ����1:44:23
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class SocketExecutorHandler extends SocketExecutor {
	private static Logger log = LoggerFactory.getLogger(SocketExecutorHandler.class);

	private static int maxBody = ServerConfiger.getIntArg("requestMaxBody"); // ��������ֽ���
	private static int headLength = ServerConfiger.getIntArg("headLength"); // ����ͷ����
	private static String CRMSYSCD = BusinessCfg.getString("appCd"); // CRM��ESB/EAI�еı��
	private static final Pattern ENCODING = Pattern.compile("encoding=('|\")([\\w|-]+)('|\")", Pattern.CASE_INSENSITIVE);
	private String rootNodeName = "TransBody";
	private String reqHeaderNodeName = "RequestHeader";
	private String reqBodyNodeName = "RequestBody";
	private String resHeaderNodeName = "ResponseHeader";
	private String resBodyNodeName = "ResponseBody";
	private String resTailNodeName = "ResponseTail";

	/**
	 * @��������:RESPSECCD
	 * @��������:TODO
	 * @since 1.0.0
	 */

	/**
	 * @���캯��
	 * @param decoder
	 */
	public SocketExecutorHandler() {
		super(new SocketRequestCodeHelper(headLength, maxBody));
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * createOutputDocument()
	 */
	@Override
	protected String createOutputDocument() throws Exception {
		log.info("������Ӧ����");
		StringBuffer buf = new StringBuffer();
		String repXml = null;
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
		Document responseDoc = DocumentHelper.createDocument();
		responseDoc.setXMLEncoding(charSet);
		Element body = responseDoc.addElement(rootNodeName);
		Element requestHeader = data.getRequestHeader();
		Element respheader = body.addElement(resHeaderNodeName);
		respheader.addElement("RespSysCd").setText(CRMSYSCD);// ��Ӧϵͳ��
		respheader.addElement("SvrDt").setText(df8.format(new Date()));// ��������
		respheader.addElement("SvrTm").setText(df20.format(new Date()));// ����ʱ��
		respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// ������ˮ��
		respheader.addElement("ReqSysCd").setText(requestHeader.elementTextTrim("ReqSysCd"));// ��Χϵͳ����
		respheader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// ��Χϵͳ������ˮ��
		respheader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// ��������
		respheader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// ����ʱ��
		respheader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// ������
		respheader.addElement("BrchNo").setText(respheader.elementTextTrim("BrchNo"));// ������
		respheader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// ҵ������
		respheader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// �ն˺�
		respheader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// �ն�IP
		respheader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// ������Ա��
		if (data.getRepNode() != null) {
			body.add(data.getRepNode());
		} else {
			body.addElement(resBodyNodeName);
		}
		Element resptail = body.addElement(resTailNodeName);
		resptail.addElement("TxStatCode").setText(data.getRepStateCd());
		resptail.addElement("TxStatDesc").setText(data.getDetailDes());
		if (data.isSuccess()) {
			resptail.addElement("TxStatString").setText("SUCCESS");
		} else {
			resptail.addElement("TxStatString").setText("FAIL");
		}
		repXml = XMLUtils.xmlToString(responseDoc);
		int length = repXml.getBytes(charSet).length;
		buf.append(String.format("%08d", length));
		buf.append(repXml);
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * createDefauteMsg(java.lang.String, java.lang.String)
	 */
	@Override
	protected String createDefauteMsg(String errorCode, String msg) throws IOException {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
			Document responseDoc = DocumentHelper.createDocument();
			responseDoc.setXMLEncoding(charSet);

			Element transBody = responseDoc.addElement(rootNodeName);
			Element respheader = transBody.addElement(resHeaderNodeName);
			Element requestHeader = null;
			if (data != null) {
				requestHeader = data.getRequestHeader();
			}
			if (requestHeader == null) {
				respheader.addElement("RespSysCd").setText(CRMSYSCD);// ��Ӧϵͳ��
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// ��������
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// ����ʱ��
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// ������ˮ��
				respheader.addElement("ReqSysCd").setText("");// ��Χϵͳ����
				respheader.addElement("ReqSeqNo").setText("");// ��Χϵͳ������ˮ��
				respheader.addElement("ReqDt").setText("");// ��������
				respheader.addElement("ReqTm").setText("");// ����ʱ��
				respheader.addElement("ChnlNo").setText("");// ������
				respheader.addElement("BrchNo").setText("");// ������
				respheader.addElement("BizLine").setText("");// ҵ������
				respheader.addElement("TrmNo").setText("");// �ն˺�
				respheader.addElement("TrmIP").setText("");// �ն�IP
				respheader.addElement("TlrNo").setText("");// ������Ա��
			} else {
				respheader.addElement("RespSysCd").setText(CRMSYSCD);// ��Ӧϵͳ��
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// ��������
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// ����ʱ��
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// ������ˮ��
				respheader.addElement("ReqSysCd").setText(requestHeader.elementTextTrim("ReqSysCd"));// ��Χϵͳ����
				respheader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// ��Χϵͳ������ˮ��
				respheader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// ��������
				respheader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// ����ʱ��
				respheader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// ������
				respheader.addElement("BrchNo").setText(requestHeader.elementTextTrim("BrchNo"));// ������
				respheader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// ҵ������
				respheader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// �ն˺�
				respheader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// �ն�IP
				respheader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// ������Ա��
			}
			transBody.addElement(resBodyNodeName);
			Element resptail = transBody.addElement(resTailNodeName);
			resptail.addElement("TxStatCode").setText(data.getRepStateCd());
			resptail.addElement("TxStatDesc").setText(data.getDetailDes());
			if (data.isSuccess()) {
				resptail.addElement("TxStatString").setText("SUCCESS");
			} else {
				resptail.addElement("TxStatString").setText("FAIL");
			}
			resXml = XMLUtils.xmlToString(responseDoc);

		} catch (Exception e) {
			log.error("��װĬ�ϱ����쳣", e);
			resXml = e.toString();
		}
		StringBuffer buf = new StringBuffer();
		int length = 0;
		length = resXml.getBytes(charSet).length;
		buf.append(String.format("%08d", length));
		if (length != 0) {
			buf.append(resXml);
		}
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#getEcifData
	 * ()
	 */
	@Override
	protected void getEcifData() throws Exception {
		// TODO Auto-generated method stub
		/****
		 * �����������������ϵͳ
		 * �����������ʽ���ģ�����涨�ĸ�λ���ǽ����������ϵͳ
		 */
		Element ecifReqNode = (Element) doc.selectSingleNode("//" + rootNodeName);
		if (ecifReqNode == null) {
			String msg = String.format("�����ĸ����(%s)������", rootNodeName);
			log.info(msg);
			throw new RequestIOException(msg);
		}
		Element requestBody = ecifReqNode.element(reqBodyNodeName);
		if (requestBody == null) {
			String msg = String.format("�������������ݽ��(%s)������", reqBodyNodeName);
			log.info(msg);
			throw new RequestIOException(msg);
		}
		String txCode = requestBody.elementTextTrim(MdmConstants.TX_DEF_TX_CODE);
		Element requestHeader = ecifReqNode.element(reqHeaderNodeName);
		if (requestHeader == null) {
			String msg = String.format("����������ͷ���(%s)������", reqHeaderNodeName);
			log.info(msg);
			throw new RequestIOException(msg);
		}
		String reqSysCd = requestHeader.elementTextTrim("ReqSysCd");
		String reqSeqNo = requestHeader.elementTextTrim("ReqSeqNo");
		String brchNo = requestHeader.elementTextTrim("BrchNo");// ������
		String tlrNo = requestHeader.elementTextTrim("TlrNo");// ��Ա��

		data.setRequestHeader(requestHeader);
		data.setOpChnlNo(reqSysCd);
		data.setReqSeqNo(reqSeqNo);
		data.setBrchNo(brchNo);
		data.setTlrNo(tlrNo);

		data.setBodyNode(requestBody);
		data.setTxCode(txCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * beforeExecutor()
	 */
	@Override
	protected void beforeExecutor() throws Exception {
		// TODO Auto-generated method stub
		/****
		 * ����������XML���� ֱ�ӵ���resolvingXml
		 * ���������������������綨��������Ҫת��
		 ***/
		this.recvmsg = decoder.getBodyString();
		/* ��ȡ�ַ���* */
		Matcher matcher = ENCODING.matcher(this.recvmsg);
		if (matcher.find()) {
			try {
				Charset result = Charset.forName(matcher.group(2));
				if (!result.name().equalsIgnoreCase(decoder.getCharSet())) {
					decoder.setCharSet(result.name());
					this.recvmsg = decoder.getBodyString();
				}
			} catch (Exception ignore) {
			}
		}
		data.setPrimalMsg(recvmsg);
		log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
		/**** ����XML���� ****/
		resolvingXml();
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor#
	 * afterExecutor()
	 */
	@Override
	protected void afterExecutor() {

	}

}

/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����SocketExecutorHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-����1:44:23
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ytec.mdm.base.bo.Error;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.normalsocket.server.SocketExecutor;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
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

	private static int maxBody = ServerConfiger.getIntArg("requestMaxBody"); // ��������ֽ���
	private static int headLength = ServerConfiger.getIntArg("headLength"); // ����ͷ����
	private static String ECIFSYSCD = BusinessCfg.getString("appCd"); // ECIF��ESB�еı��
	private static final Pattern ENCODING = Pattern
			.compile("encoding=('|\")([\\w|-]+)('|\")", Pattern.CASE_INSENSITIVE);
	/**
	 * @��������:RESPSECCD
	 * @��������:TODO
	 * @since 1.0.0
	 */
	private String rootNodeName = "TransBody";
	private String reqHeaderNodeName = "RequestHeader";
	private String reqBodyNodeName = "RequestBody";
	private String resHeaderNodeName = "ResponseHeader";
	private String resBodyNodeName = "ResponseBody";
	private String resTailNodeName = "ResponseTail";

	/**
	 * @���캯��
	 * @param decoder
	 */
	public SocketExecutorHandler() {
		super(new SocketRequestCodeHelper(headLength, maxBody));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String createOutputDocument() throws Exception {
		StringBuffer buf = new StringBuffer();
		String repXml = null;

		// TODO read conf File
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");

		Document responseDoc = DocumentHelper.createDocument();
		responseDoc.setXMLEncoding(charSet);

		Element body = responseDoc.addElement(rootNodeName);
		Element requestHeader = data.getRequestHeader();
		Element respheader = body.addElement(resHeaderNodeName);
		respheader.addElement("RespSysCd").setText(ECIFSYSCD);// RespSysCd ��Ӧϵͳ��
		respheader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt ��������
		respheader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm ����ʱ��
		respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo ������ˮ��
		respheader.addElement("ReqSysCd").setText(requestHeader.elementTextTrim("ReqSysCd"));// ReqSysCd ��Χϵͳ����
		respheader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// ReqSeqNo ��Χϵͳ������ˮ��
		respheader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// ReqDt ��������
		respheader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// ReqTm ����ʱ��
		respheader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// ChnlNo ������
		respheader.addElement("BrchNo").setText(requestHeader.elementTextTrim("BrchNo"));// BrchNo ������
		respheader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// BizLine ҵ������
		respheader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// TrmNo �ն˺�
		respheader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// TrmIP �ն�IP
		respheader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// TlrNo ������Ա��

		if (data.getRepNode() != null) {
			Element respBodyNode = data.getRepNode();
			if (data.getWriteModelObj() != null) {
				Map respResultMap = data.getWriteModelObj().getResultMap();
				Set keys = respResultMap.keySet();
				Iterator itr = keys.iterator();
				while (itr.hasNext()) {
					String keyName = (String) itr.next();
					// TODO
					respBodyNode.addElement(keyName).setText(respResultMap.get(keyName).toString());
				}
			}
			body.add(respBodyNode);
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

		// // �Ŵ�ϵͳ���⻯����-->> ��ǰ4λ��ʾ���ĳ���
		// if ("LN".equals(data.getOpChnlNo())) {
		// buf.append(String.format("%08d", length).substring(4, 8));
		// } else {
		buf.append(String.format("%08d", length));
		// }
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
			// QName qname = null;
			// String repXml = null;
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

			if (requestHeader != null) {
				respheader.addElement("RespSysCd").setText(ECIFSYSCD);// RespSysCd ��Ӧϵͳ��
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt ��������
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm ����ʱ��
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo ������ˮ��
				respheader.addElement("ReqSysCd").setText("");// ReqSysCd ��Χϵͳ����
				respheader.addElement("ReqSeqNo").setText("");// ReqSeqNo ��Χϵͳ������ˮ��
				respheader.addElement("ReqDt").setText("");// ReqDt ��������
				respheader.addElement("ReqTm").setText("");// ReqTm ����ʱ��
				respheader.addElement("ChnlNo").setText("");// ChnlNo ������
				respheader.addElement("BrchNo").setText("");// BrchNo ������
				respheader.addElement("BizLine").setText("");// BizLine ҵ������
				respheader.addElement("TrmNo").setText("");// TrmNo �ն˺�
				respheader.addElement("TrmIP").setText("");// TrmIP �ն�IP
				respheader.addElement("TlrNo").setText("");// TlrNo ������Ա��
			} else {
				respheader.addElement("RespSysCd").setText(ECIFSYSCD);// RespSysCd ��Ӧϵͳ��
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt ��������
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm ����ʱ��
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo ������ˮ��
				respheader.addElement("ReqSysCd").setText("");// ReqSysCd ��Χϵͳ����
				respheader.addElement("ReqSeqNo").setText("");// ReqSeqNo ��Χϵͳ������ˮ��
				respheader.addElement("ReqDt").setText("");// ReqDt ��������
				respheader.addElement("ReqTm").setText("");// ReqTm ����ʱ��
				respheader.addElement("ChnlNo").setText("");// ChnlNo ������
				respheader.addElement("BrchNo").setText("");// BrchNo ������
				respheader.addElement("BizLine").setText("");// BizLine ҵ������
				respheader.addElement("TrmNo").setText("");// TrmNo �ն˺�
				respheader.addElement("TrmIP").setText("");// TrmIP �ն�IP
				respheader.addElement("TlrNo").setText("");// TlrNo ������Ա��
			}

			// cqr_seq.addElement("ResponseBody");
			transBody.addElement(resBodyNodeName);

			// Element resptail = cqr_seq.addElement("ResponseTail");
			Element resptail = transBody.addElement(resTailNodeName);

			resptail.addElement("TxStatCode").setText(errorCode);
			resptail.addElement("TxStatDesc").setText(msg);
			if (ErrorCode.SUCCESS.getCode().equals(errorCode)) {
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

		Element ecifReqNode = (Element) doc.selectSingleNode("//TransBody");
		if (ecifReqNode == null) {
			Error err = ErrorCode.ERR_XML_FORMAT_ERROR;
			err.setChDesc(err.getChDesc() + "�����ĸ��ڵ㲻����,��Ҫ���ڵ㣺" + rootNodeName);
			log.info(err.getChDesc());
			data.setStatus(err);
			throw new RequestIOException(err.getChDesc());
		}
		// NS_NODE_NEME = ecifReqNode.getName();
		Element bodyNode = ecifReqNode.element(reqBodyNodeName);// "RequestBody");
		if (bodyNode == null) {
			Error err = ErrorCode.ERR_XML_FORMAT_ERROR;
			err.setChDesc(err.getChDesc() + ",�������岻���ڣ���Ҫ���ڵ㣺" + reqBodyNodeName);
			log.info(err.getChDesc());
			data.setStatus(err);
			throw new RequestIOException(err.getChDesc());
		}
		String txCode = bodyNode.elementTextTrim(MdmConstants.TX_DEF_TX_CODE);
		Element requestHeader = ecifReqNode.element(reqHeaderNodeName);// "RequestHeader");
		if (requestHeader == null) {
			Error err = ErrorCode.ERR_XML_FORMAT_ERROR;
			err.setChDesc(err.getChDesc() + ",������ͷ�����ڣ���Ҫ���ڵ㣺" + reqHeaderNodeName);
			log.info(err.getChDesc());
			data.setStatus(err);
			throw new RequestIOException(err.getChDesc());
		}
		String reqSysCd = requestHeader.elementTextTrim("ReqSysCd");
		String reqSeqNo = requestHeader.elementTextTrim("ReqSeqNo");

		String brchNo = requestHeader.elementTextTrim("BrchNo");// ������
		String tlrNo = requestHeader.elementTextTrim("TlrNo");// ������Ա��

		data.setBodyNode(bodyNode);
		data.setTxCode(txCode);
		data.setOpChnlNo(reqSysCd);
		data.setReqSeqNo(reqSeqNo);
		data.setRequestHeader(requestHeader);
		data.setBrchNo(brchNo);
		data.setTlrNo(tlrNo);
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
		log.info(String.format("%08d", this.recvmsg.getBytes().length)
				+ SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
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
		// TODO Auto-generated method stub

	}

}

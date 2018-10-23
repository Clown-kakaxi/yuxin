/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����WsExecutorHandler2.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:10:44
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.interfaces.ws.server.WsExecutor;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.MdmConstants;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�WsExecutorHandler2
 * @��������web service �ӿ� ����2
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:10:52   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:10:52
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class WsExecutorHandler2 extends WsExecutor {
	private static Logger log = LoggerFactory
			.getLogger(WsExecutorHandler2.class);
	private static String ECIFSYSCD = BusinessCfg.getString("appCd"); // ECIF��ESB�еı��
	/**
	 * @��������:RESPSECCD
	 * @��������:TODO
	 * @since 1.0.0
	 */
	private static String RESPSECCD = BusinessCfg
			.getString("sopXmlRespSecCd"); //

	private String NS_PREFIX; // XML �����ռ�
	private String NS_URI; // XML �����ռ�URL
	private String NS_NODE_NEME; // ECIF ��ESB�еĽ������
	
	/**
	 *@���캯�� 
	 */
	public WsExecutorHandler2() {
		super();
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected void getEcifData() throws Exception {
		List<Namespace> nameSpaceList = doc.getRootElement()
				.additionalNamespaces();
		if (nameSpaceList != null && nameSpaceList.size() == 1) {
			Namespace ecifNamespace = nameSpaceList.get(0);
			NS_PREFIX = ecifNamespace.getPrefix();
			NS_URI = ecifNamespace.getURI();
		} else {
			log.info("�����������ռ����");
			throw new RequestIOException("�����������ռ����");
		}
		/*** ����SOAP���� ***/
		Element ecifReqNode = (Element) doc.selectSingleNode("//" + NS_PREFIX
				+ ":*");
		if (ecifReqNode == null) {
			log.info("������SOAP��㲻����");
			throw new RequestIOException("������SOAP��㲻����");
		}
		NS_NODE_NEME = ecifReqNode.getName();
		Element bodyNode = ecifReqNode.element("RequestBody");
		if (bodyNode == null) {
			log.info("�������岻����");
			throw new RequestIOException("�������岻����");
		}
		String txCode = bodyNode.elementTextTrim(MdmConstants.TX_DEF_TX_CODE);
		Element requestHeader = ecifReqNode.element("RequestHeader");
		if(requestHeader==null){
			log.info("������ͷ������");
			throw new RequestIOException("������ͷ������");
		}
		String reqSysCd = requestHeader.elementTextTrim("ReqSysCd");
		String reqSeqNo = requestHeader.elementTextTrim("ReqSeqNo");

		String brchNo = requestHeader.elementTextTrim("BrchNo");// ������
		String tlrNo = requestHeader.elementTextTrim("TlrNo");// ��Ա��
		String AuthCd = requestHeader.elementTextTrim("AuthPw");// ��Ȩ��

		data.setBodyNode(bodyNode);
		data.setTxCode(txCode);
		data.setOpChnlNo(reqSysCd);
		data.setReqSeqNo(reqSeqNo);
		data.setRequestHeader(requestHeader);
		data.setBrchNo(brchNo);
		data.setTlrNo(tlrNo);
//		data.setAuthPwd(AuthCd);
	}

	/**
	 * SOAP������װ
	 * 
	 * @param data
	 *            �������ݶ���
	 */
	@Override
	protected String createOutputDocument() throws Exception {
		QName qname = null;
		String repXml = null;
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
		Document responseDoc = DocumentHelper.createDocument();
		responseDoc.setXMLEncoding(charSet);
		Namespace soapenv = DocumentHelper.createNamespace("soapenv",
				"http://schemas.xmlsoap.org/soap/envelope/");
		Namespace cqr = DocumentHelper.createNamespace(NS_PREFIX, NS_URI);
		qname = new QName("Envelope", soapenv);
		Element root = responseDoc.addElement(qname);
		root.add(cqr);
		qname = new QName("Header", soapenv);
		root.addElement(qname);
		qname = new QName("Body", soapenv);
		Element body = root.addElement(qname);
		qname = new QName(NS_NODE_NEME, cqr);
		Element cqr_seq = body.addElement(qname);
		Element requestHeader = data.getRequestHeader();
		Element respheader = cqr_seq.addElement("ResponseHeader");

		respheader.addElement("VerNo").setText(
				requestHeader.elementTextTrim("VerNo"));
		respheader.addElement("RespSysCd").setText(ECIFSYSCD);
		respheader.addElement("RespSecCd").setText(RESPSECCD);
		respheader.addElement("TxnCd").setText(
				requestHeader.elementTextTrim("TxnCd"));
		respheader.addElement("ReqDt").setText(
				requestHeader.elementTextTrim("ReqDt"));
		respheader.addElement("ReqTm").setText(
				requestHeader.elementTextTrim("ReqTm"));
		respheader.addElement("ReqSeqNo").setText(
				requestHeader.elementTextTrim("ReqSeqNo"));
		respheader.addElement("SvrDt").setText(df8.format(new Date()));
		respheader.addElement("SvrTm").setText(df20.format(new Date()));
		respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));
		respheader.addElement("AuthFlg").setText("1");
		respheader.addElement("BrchNo").setText(
				requestHeader.elementTextTrim("BrchNo"));
		respheader.addElement("BrchNme").setText(
				requestHeader.elementTextTrim("BrchNme"));
		respheader.addElement("DevCd");
		respheader.addElement("TlrNo").setText(
				requestHeader.elementTextTrim("TlrNo"));
		respheader.addElement("TlrLvl").setText(
				requestHeader.elementTextTrim("TlrLvl"));
		respheader.addElement("TlrNme").setText(
				requestHeader.elementTextTrim("TlrNme"));
		respheader.addElement("TlrTyp").setText(
				requestHeader.elementTextTrim("TlrTyp"));
		respheader.addElement("TlrPwd").setText(
				requestHeader.elementTextTrim("TlrPwd"));
		respheader.addElement("TrmNo").setText(
				requestHeader.elementTextTrim("TrmNo"));
		respheader.addElement("TrmIP").setText(
				requestHeader.elementTextTrim("TrmIP"));
		respheader.addElement("ChnlNo").setText(
				requestHeader.elementTextTrim("ChnlNo"));
		respheader.addElement("RcvFileNme").setText("");
		respheader.addElement("TotNum").setText("");
		respheader.addElement("CurrRecNum").setText("");
		respheader.addElement("FileHMac").setText("");
		respheader.addElement("HMac").setText("");
		/*******/
		if (data.getRepNode() != null) {
			cqr_seq.add(data.getRepNode());
		} else {
			cqr_seq.addElement("ResponseBody");
		}
		Element fault = cqr_seq.addElement("Fault");
		fault.addElement("FaultCode").setText(data.getRepStateCd());
		fault.addElement("FaultString").setText(data.getDetailDes());
		Element detail = fault.addElement("Detail");
		if (data.isSuccess()) {
			detail.addElement("TxnStat").setText("SUCCESS");
		} else {
			detail.addElement("TxnStat").setText("ERROR");
		}
		repXml = XMLUtils.xmlToString(responseDoc);
		return repXml;
	}

	/**
	 * SOAPĬ�ϱ�����װ
	 * 
	 * @param data
	 *            �������ݶ���
	 */
	@Override
	protected String createDefauteMsg(String errorCode, String msg){
		try {
			QName qname = null;
			String repXml = null;
			SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
			Document responseDoc = DocumentHelper.createDocument();
			responseDoc.setXMLEncoding(charSet);
			Namespace soapenv =DocumentHelper.createNamespace("soapenv",
					"http://schemas.xmlsoap.org/soap/envelope/");
			if(NS_PREFIX==null){
				NS_PREFIX = BusinessCfg.getString("sopXmlNsPrefix");     //XML �����ռ�
				NS_URI = BusinessCfg.getString("sopXmlNsUri");		      //XML �����ռ�URL
			}
			if(NS_NODE_NEME==null){
				NS_NODE_NEME=BusinessCfg.getString("sopXmlNsNodeNeme");	
			}
			Namespace cqr = DocumentHelper.createNamespace(NS_PREFIX, NS_URI);
			qname = new QName("Envelope", soapenv);
			Element root = responseDoc.addElement(qname);
			root.add(cqr);
			qname = new QName("Header", soapenv);
			root.addElement(qname);
			qname = new QName("Body", soapenv);
			Element body = root.addElement(qname);
			qname = new QName(NS_NODE_NEME, cqr);
			Element cqr_seq = body.addElement(qname);
			Element requestHeader=null;
			if(data!=null){
				requestHeader = data.getRequestHeader();
			}
			Element respheader = cqr_seq.addElement("ResponseHeader");

			if (requestHeader == null) {
				respheader.addElement("VerNo").setText("v1.0");
				respheader.addElement("RespSysCd").setText(ECIFSYSCD);
				respheader.addElement("RespSecCd").setText(RESPSECCD);
				respheader.addElement("TxnCd").setText("");
				respheader.addElement("ReqDt").setText("");
				respheader.addElement("ReqTm").setText("");
				respheader.addElement("ReqSeqNo").setText("");
				respheader.addElement("SvrDt").setText(df8.format(new Date()));
				respheader.addElement("SvrTm").setText(df20.format(new Date()));
				respheader.addElement("SvrSeqNo").setText(
						df20.format(new Date()));
				respheader.addElement("AuthFlg").setText("1");
				respheader.addElement("BrchNo").setText("");
				respheader.addElement("BrchNme").setText("");
				respheader.addElement("DevCd");
				respheader.addElement("TlrNo").setText("");
				respheader.addElement("TlrLvl").setText("");
				respheader.addElement("TlrNme").setText("");
				respheader.addElement("TlrTyp").setText("");
				respheader.addElement("TlrPwd").setText("");
				respheader.addElement("TrmNo").setText("");
				respheader.addElement("TrmIP").setText("");
				respheader.addElement("ChnlNo").setText("");
				respheader.addElement("RcvFileNme").setText("");
				respheader.addElement("TotNum").setText("");
				respheader.addElement("CurrRecNum").setText("");
				respheader.addElement("FileHMac").setText("");
				respheader.addElement("HMac").setText("");
			} else {
				respheader.addElement("VerNo").setText(
						requestHeader.elementTextTrim("VerNo"));
				respheader.addElement("RespSysCd").setText(ECIFSYSCD);
				respheader.addElement("RespSecCd").setText(RESPSECCD);
				respheader.addElement("TxnCd").setText(
						requestHeader.elementTextTrim("TxnCd"));
				respheader.addElement("ReqDt").setText(
						requestHeader.elementTextTrim("ReqDt"));
				respheader.addElement("ReqTm").setText(
						requestHeader.elementTextTrim("ReqTm"));
				respheader.addElement("ReqSeqNo").setText(
						requestHeader.elementTextTrim("ReqSeqNo"));
				respheader.addElement("SvrDt").setText(df8.format(new Date()));
				respheader.addElement("SvrTm").setText(df20.format(new Date()));
				respheader.addElement("SvrSeqNo").setText(
						df20.format(new Date()));
				respheader.addElement("AuthFlg").setText("1");
				respheader.addElement("BrchNo").setText(
						requestHeader.elementTextTrim("BrchNo"));
				respheader.addElement("BrchNme").setText(
						requestHeader.elementTextTrim("BrchNme"));
				respheader.addElement("DevCd");
				respheader.addElement("TlrNo").setText(
						requestHeader.elementTextTrim("TlrNo"));
				respheader.addElement("TlrLvl").setText(
						requestHeader.elementTextTrim("TlrLvl"));
				respheader.addElement("TlrNme").setText(
						requestHeader.elementTextTrim("TlrNme"));
				respheader.addElement("TlrTyp").setText(
						requestHeader.elementTextTrim("TlrTyp"));
				respheader.addElement("TlrPwd").setText(
						requestHeader.elementTextTrim("TlrPwd"));
				respheader.addElement("TrmNo").setText(
						requestHeader.elementTextTrim("TrmNo"));
				respheader.addElement("TrmIP").setText(
						requestHeader.elementTextTrim("TrmIP"));
				respheader.addElement("ChnlNo").setText(
						requestHeader.elementTextTrim("ChnlNo"));
				respheader.addElement("RcvFileNme").setText("");
				respheader.addElement("TotNum").setText("");
				respheader.addElement("CurrRecNum").setText("");
				respheader.addElement("FileHMac").setText("");
				respheader.addElement("HMac").setText("");
			}
			cqr_seq.addElement("ResponseBody");
			Element fault = cqr_seq.addElement("Fault");
			fault.addElement("FaultCode").setText(errorCode);
			fault.addElement("FaultString").setText(msg);
			Element detail = fault.addElement("Detail");
			detail.addElement("TxnStat").setText("ERROR");
			repXml = XMLUtils.xmlToString(responseDoc);
			return repXml;
		} catch (Exception e) {
			log.error("��װĬ�ϱ����쳣",e);
			resXml=e.toString();
			return resXml;
		}
	}

	@Override
	protected void beforeExecutor() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterExecutor() {
		// TODO Auto-generated method stub
		
	}
}

/**
 * @项目名：ytec-mdm-sampleecif
 * @包名：com.ytec.sampleecif.interfaces
 * @文件名：SocketExecutorHandler.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-下午1:44:23
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-sampleecif
 * @类名称：SocketExecutorHandler
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-16 下午1:44:23
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-16 下午1:44:23
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 */
public class SocketExecutorHandler extends SocketExecutor {
	private static Logger log = LoggerFactory.getLogger(SocketExecutorHandler.class);

	private static int maxBody = ServerConfiger.getIntArg("requestMaxBody"); // 报文最大字节数
	private static int headLength = ServerConfiger.getIntArg("headLength"); // 报文头长度
	private static String CRMSYSCD = BusinessCfg.getString("appCd"); // CRM在ESB/EAI中的编号
	private static final Pattern ENCODING = Pattern.compile("encoding=('|\")([\\w|-]+)('|\")", Pattern.CASE_INSENSITIVE);
	private String rootNodeName = "TransBody";
	private String reqHeaderNodeName = "RequestHeader";
	private String reqBodyNodeName = "RequestBody";
	private String resHeaderNodeName = "ResponseHeader";
	private String resBodyNodeName = "ResponseBody";
	private String resTailNodeName = "ResponseTail";

	/**
	 * @属性名称:RESPSECCD
	 * @属性描述:TODO
	 * @since 1.0.0
	 */

	/**
	 * @构造函数
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
		log.info("构造响应报文");
		StringBuffer buf = new StringBuffer();
		String repXml = null;
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
		Document responseDoc = DocumentHelper.createDocument();
		responseDoc.setXMLEncoding(charSet);
		Element body = responseDoc.addElement(rootNodeName);
		Element requestHeader = data.getRequestHeader();
		Element respheader = body.addElement(resHeaderNodeName);
		respheader.addElement("RespSysCd").setText(CRMSYSCD);// 响应系统号
		respheader.addElement("SvrDt").setText(df8.format(new Date()));// 服务日期
		respheader.addElement("SvrTm").setText(df20.format(new Date()));// 服务时间
		respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// 服务流水号
		respheader.addElement("ReqSysCd").setText(requestHeader.elementTextTrim("ReqSysCd"));// 外围系统代号
		respheader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// 外围系统交易流水号
		respheader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// 请求日期
		respheader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// 请求时间
		respheader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// 渠道号
		respheader.addElement("BrchNo").setText(respheader.elementTextTrim("BrchNo"));// 机构号
		respheader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// 业务条线
		respheader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// 终端号
		respheader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// 终端IP
		respheader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// 操作柜员号
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
				respheader.addElement("RespSysCd").setText(CRMSYSCD);// 响应系统号
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// 服务日期
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// 服务时间
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// 服务流水号
				respheader.addElement("ReqSysCd").setText("");// 外围系统代号
				respheader.addElement("ReqSeqNo").setText("");// 外围系统交易流水号
				respheader.addElement("ReqDt").setText("");// 请求日期
				respheader.addElement("ReqTm").setText("");// 请求时间
				respheader.addElement("ChnlNo").setText("");// 渠道号
				respheader.addElement("BrchNo").setText("");// 机构号
				respheader.addElement("BizLine").setText("");// 业务条线
				respheader.addElement("TrmNo").setText("");// 终端号
				respheader.addElement("TrmIP").setText("");// 终端IP
				respheader.addElement("TlrNo").setText("");// 操作柜员号
			} else {
				respheader.addElement("RespSysCd").setText(CRMSYSCD);// 响应系统号
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// 服务日期
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// 服务时间
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// 服务流水号
				respheader.addElement("ReqSysCd").setText(requestHeader.elementTextTrim("ReqSysCd"));// 外围系统代号
				respheader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// 外围系统交易流水号
				respheader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// 请求日期
				respheader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// 请求时间
				respheader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// 渠道号
				respheader.addElement("BrchNo").setText(requestHeader.elementTextTrim("BrchNo"));// 机构号
				respheader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// 业务条线
				respheader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// 终端号
				respheader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// 终端IP
				respheader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// 操作柜员号
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
			log.error("组装默认报文异常", e);
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
		 * 解析出交易码和请求系统
		 * 如果是替他格式报文，必须规定哪个位置是交易码和请求系统
		 */
		Element ecifReqNode = (Element) doc.selectSingleNode("//" + rootNodeName);
		if (ecifReqNode == null) {
			String msg = String.format("请求报文根结点(%s)不存在", rootNodeName);
			log.info(msg);
			throw new RequestIOException(msg);
		}
		Element requestBody = ecifReqNode.element(reqBodyNodeName);
		if (requestBody == null) {
			String msg = String.format("请求报文请求内容结点(%s)不存在", reqBodyNodeName);
			log.info(msg);
			throw new RequestIOException(msg);
		}
		String txCode = requestBody.elementTextTrim(MdmConstants.TX_DEF_TX_CODE);
		Element requestHeader = ecifReqNode.element(reqHeaderNodeName);
		if (requestHeader == null) {
			String msg = String.format("请求报文请求头结点(%s)不存在", reqHeaderNodeName);
			log.info(msg);
			throw new RequestIOException(msg);
		}
		String reqSysCd = requestHeader.elementTextTrim("ReqSysCd");
		String reqSeqNo = requestHeader.elementTextTrim("ReqSeqNo");
		String brchNo = requestHeader.elementTextTrim("BrchNo");// 机构号
		String tlrNo = requestHeader.elementTextTrim("TlrNo");// 柜员号

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
		 * 如果传输的是XML报文 直接调用resolvingXml
		 * 如果传输的是替他报文例如定长报文需要转换
		 ***/
		this.recvmsg = decoder.getBodyString();
		/* 获取字符集* */
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
		/**** 解析XML报文 ****/
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

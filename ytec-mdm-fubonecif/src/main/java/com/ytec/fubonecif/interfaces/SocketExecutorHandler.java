/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.interfaces
 * @文件名：SocketExecutorHandler.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-下午1:44:23
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-fubonecif
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

	private static int maxBody = ServerConfiger.getIntArg("requestMaxBody"); // 报文最大字节数
	private static int headLength = ServerConfiger.getIntArg("headLength"); // 报文头长度
	private static String ECIFSYSCD = BusinessCfg.getString("appCd"); // ECIF在ESB中的编号
	private static final Pattern ENCODING = Pattern
			.compile("encoding=('|\")([\\w|-]+)('|\")", Pattern.CASE_INSENSITIVE);
	/**
	 * @属性名称:RESPSECCD
	 * @属性描述:TODO
	 * @since 1.0.0
	 */
	private String rootNodeName = "TransBody";
	private String reqHeaderNodeName = "RequestHeader";
	private String reqBodyNodeName = "RequestBody";
	private String resHeaderNodeName = "ResponseHeader";
	private String resBodyNodeName = "ResponseBody";
	private String resTailNodeName = "ResponseTail";

	/**
	 * @构造函数
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
		respheader.addElement("RespSysCd").setText(ECIFSYSCD);// RespSysCd 响应系统号
		respheader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt 服务日期
		respheader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm 服务时间
		respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo 服务流水号
		respheader.addElement("ReqSysCd").setText(requestHeader.elementTextTrim("ReqSysCd"));// ReqSysCd 外围系统代号
		respheader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// ReqSeqNo 外围系统交易流水号
		respheader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// ReqDt 请求日期
		respheader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// ReqTm 请求时间
		respheader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// ChnlNo 渠道号
		respheader.addElement("BrchNo").setText(requestHeader.elementTextTrim("BrchNo"));// BrchNo 机构号
		respheader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// BizLine 业务条线
		respheader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// TrmNo 终端号
		respheader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// TrmIP 终端IP
		respheader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// TlrNo 操作柜员号

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

		// // 信贷系统特殊化处理-->> 以前4位表示报文长度
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
				respheader.addElement("RespSysCd").setText(ECIFSYSCD);// RespSysCd 响应系统号
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt 服务日期
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm 服务时间
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo 服务流水号
				respheader.addElement("ReqSysCd").setText("");// ReqSysCd 外围系统代号
				respheader.addElement("ReqSeqNo").setText("");// ReqSeqNo 外围系统交易流水号
				respheader.addElement("ReqDt").setText("");// ReqDt 请求日期
				respheader.addElement("ReqTm").setText("");// ReqTm 请求时间
				respheader.addElement("ChnlNo").setText("");// ChnlNo 渠道号
				respheader.addElement("BrchNo").setText("");// BrchNo 机构号
				respheader.addElement("BizLine").setText("");// BizLine 业务条线
				respheader.addElement("TrmNo").setText("");// TrmNo 终端号
				respheader.addElement("TrmIP").setText("");// TrmIP 终端IP
				respheader.addElement("TlrNo").setText("");// TlrNo 操作柜员号
			} else {
				respheader.addElement("RespSysCd").setText(ECIFSYSCD);// RespSysCd 响应系统号
				respheader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt 服务日期
				respheader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm 服务时间
				respheader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo 服务流水号
				respheader.addElement("ReqSysCd").setText("");// ReqSysCd 外围系统代号
				respheader.addElement("ReqSeqNo").setText("");// ReqSeqNo 外围系统交易流水号
				respheader.addElement("ReqDt").setText("");// ReqDt 请求日期
				respheader.addElement("ReqTm").setText("");// ReqTm 请求时间
				respheader.addElement("ChnlNo").setText("");// ChnlNo 渠道号
				respheader.addElement("BrchNo").setText("");// BrchNo 机构号
				respheader.addElement("BizLine").setText("");// BizLine 业务条线
				respheader.addElement("TrmNo").setText("");// TrmNo 终端号
				respheader.addElement("TrmIP").setText("");// TrmIP 终端IP
				respheader.addElement("TlrNo").setText("");// TlrNo 操作柜员号
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

		Element ecifReqNode = (Element) doc.selectSingleNode("//TransBody");
		if (ecifReqNode == null) {
			Error err = ErrorCode.ERR_XML_FORMAT_ERROR;
			err.setChDesc(err.getChDesc() + "请求报文根节点不存在,需要根节点：" + rootNodeName);
			log.info(err.getChDesc());
			data.setStatus(err);
			throw new RequestIOException(err.getChDesc());
		}
		// NS_NODE_NEME = ecifReqNode.getName();
		Element bodyNode = ecifReqNode.element(reqBodyNodeName);// "RequestBody");
		if (bodyNode == null) {
			Error err = ErrorCode.ERR_XML_FORMAT_ERROR;
			err.setChDesc(err.getChDesc() + ",请求报文体不存在，需要根节点：" + reqBodyNodeName);
			log.info(err.getChDesc());
			data.setStatus(err);
			throw new RequestIOException(err.getChDesc());
		}
		String txCode = bodyNode.elementTextTrim(MdmConstants.TX_DEF_TX_CODE);
		Element requestHeader = ecifReqNode.element(reqHeaderNodeName);// "RequestHeader");
		if (requestHeader == null) {
			Error err = ErrorCode.ERR_XML_FORMAT_ERROR;
			err.setChDesc(err.getChDesc() + ",请求报文头不存在，需要根节点：" + reqHeaderNodeName);
			log.info(err.getChDesc());
			data.setStatus(err);
			throw new RequestIOException(err.getChDesc());
		}
		String reqSysCd = requestHeader.elementTextTrim("ReqSysCd");
		String reqSeqNo = requestHeader.elementTextTrim("ReqSeqNo");

		String brchNo = requestHeader.elementTextTrim("BrchNo");// 机构号
		String tlrNo = requestHeader.elementTextTrim("TlrNo");// 操作柜员号

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
		log.info(String.format("%08d", this.recvmsg.getBytes().length)
				+ SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
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
		// TODO Auto-generated method stub

	}

}

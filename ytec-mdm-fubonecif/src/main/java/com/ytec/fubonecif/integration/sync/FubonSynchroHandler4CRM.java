/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.integration.sync
 * @文件名：SynchroToSystemHandler.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:08:43
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-fubonecif
 * @类名称：FubonSynchroHandler
 * @类描述：富邦华一银行数据同步处理类
 * @功能描述:
 * @创建人：wangtb@yuchengtech.com
 * @创建时间：2013-12-17 下午12:08:44
 * @修改人：wangtb@yuchengtech.com
 * @修改时间：2013-12-17 下午12:08:44
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Component
@Scope("prototype")
public class FubonSynchroHandler4CRM extends SynchroExecuteHandler {
	private static Logger log = LoggerFactory.getLogger(FubonSynchroHandler4CRM.class);
	private static String ECIFSYSCD = BusinessCfg.getString("appCd"); // ECIF在ESB中的编号

	// TODO 改报文格式
	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Element databody) {
		if (databody == null) {
			log.error("同步配置{},生成的报文体为空", txSyncConf.getSyncConfId());
			return false;
		}
		try {
			SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
			Document requestDoc = DocumentHelper.createDocument();
			requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);

			Element transBody = requestDoc.addElement("TransBody");

			Element requestHeader = transBody.addElement("RequestHeader");
			requestHeader.addElement("SvrDt").setText(df8.format(new Date()));// SvrDt 服务日期
			requestHeader.addElement("SvrTm").setText(df20.format(new Date()));// SvrTm 服务时间
			requestHeader.addElement("SvrSeqNo").setText(df20.format(new Date()));// SvrSeqNo 服务流水号
			requestHeader.addElement("ReqSysCd").setText(ECIFSYSCD);// ReqSysCd 外围系统代号
			requestHeader.addElement("ReqSeqNo").setText(requestHeader.elementTextTrim("ReqSeqNo"));// ReqSeqNo 外围系统交易流水号
			requestHeader.addElement("ReqDt").setText(requestHeader.elementTextTrim("ReqDt"));// ReqDt 请求日期
			requestHeader.addElement("ReqTm").setText(requestHeader.elementTextTrim("ReqTm"));// ReqTm 请求时间
			requestHeader.addElement("ChnlNo").setText(requestHeader.elementTextTrim("ChnlNo"));// ChnlNo 渠道号
			requestHeader.addElement("BrchNo").setText(requestHeader.elementTextTrim("BrchNo"));// BrchNo 机构号
			requestHeader.addElement("BizLine").setText(requestHeader.elementTextTrim("BizLine"));// BizLine 业务条线
			requestHeader.addElement("TrmNo").setText(requestHeader.elementTextTrim("TrmNo"));// TrmNo 终端号
			requestHeader.addElement("TrmIP").setText(requestHeader.elementTextTrim("TrmIP"));// TrmIP 终端IP
			requestHeader.addElement("TlrNo").setText(requestHeader.elementTextTrim("TlrNo"));// TlrNo 操作柜员号

			databody.setName("RequestBody");
			transBody.add(databody);
			synchroRequestMsg = XMLUtils.xmlToString(requestDoc);

			// 如果信贷服务接口中长度标识位为4位，则使用如下方式组装报文，并修改SocketClient中packing方法
			// synchroRequestMsg = String.format("%08d", synchroRequestMsg.length())+synchroRequestMsg;

			return true;
		} catch (Exception e) {
			log.error("同步配置{},同步报文异常", txSyncConf.getSyncConfId());
			log.error("组装同步报文异常", e);
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
					String msg = String.format("{},收到外围系统[%s]响应报文必要节点为空[需要节点：%s,%s,%s]",
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
				String txStatDetail = "外围系统[" + BusinessCfg.getString("crmCd") + "]响应:txStatDesc:{" + txStatDesc
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
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("crmCd"), e);
				return false;
			} catch (Exception e) {
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("crmCd"), e);
				return false;
			}
		} else {
			log.info("同步响应报文为空");
		}
		return true;
	}
}

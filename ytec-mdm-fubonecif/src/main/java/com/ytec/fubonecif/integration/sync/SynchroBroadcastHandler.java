/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.integration.sync
 * @�ļ�����SynchroBroadcastHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:08:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.integration.sync;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.sync.ptsync.SynchroExecuteHandler;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�SynchroBroadcastHandler
 * @������������ͬ������(�㲥)
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:08:44   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:08:44
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
@Scope("prototype")
public class SynchroBroadcastHandler extends SynchroExecuteHandler {
	private static Logger log = LoggerFactory.getLogger(SynchroBroadcastHandler.class);
	private static String NS_PREFIX="ns";
	private static String NS_URI="www.yuchengtech.com.cn";
	private static String NS_NODE_NEME="S007001990ECIF01";
			

	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Element databody) {
		// TODO Auto-generated method stub
		if(databody==null){
			log.error("ͬ������{},���ɵı�����Ϊ��",txSyncConf.getSyncConfId());
			return false;
		}
		try{
			QName qname = null;
			SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
			Document requestDoc = DocumentHelper.createDocument();
			requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);
			Namespace soapenv = DocumentHelper.createNamespace("soapenv","http://schemas.xmlsoap.org/soap/envelope/");
			Namespace cqr = DocumentHelper.createNamespace(NS_PREFIX, NS_URI);
			qname = new QName("Envelope", soapenv);
			Element root = requestDoc.addElement(qname);
			root.add(cqr);
			qname = new QName("Header", soapenv);
			root.addElement(qname);
			qname = new QName("Body", soapenv);
			Element body = root.addElement(qname);
			qname = new QName(NS_NODE_NEME, cqr);
			Element cqr_seq = body.addElement(qname);
			Element requestHeader = cqr_seq.addElement("RequestHeader");
			requestHeader.addElement("VerNo").setText("1.0");
			requestHeader.addElement("ReqSysCd").setText("ECIF");
			requestHeader.addElement("ReqSecCd").setText("10001");
			requestHeader.addElement("TxnTyp").setText("RQ");
			requestHeader.addElement("TxnMod").setText("0");
			requestHeader.addElement("TxnCd").setText(NS_NODE_NEME);
			requestHeader.addElement("TxnNme").setText(StringUtil.toString(txSyncConf.getSyncConfDesc()));
			requestHeader.addElement("ReqDt").setText(df8.format(new Date()));
			requestHeader.addElement("ReqTm").setText(df20.format(new Date()));
			requestHeader.addElement("ReqSeqNo").setText(df20.format(new Date()));
			requestHeader.addElement("ChnlNo").setText("");
			requestHeader.addElement("BrchNo").setText("");
			requestHeader.addElement("BrchNme").setText("");
			requestHeader.addElement("TrmNo").setText("");
			requestHeader.addElement("TrmIP").setText("");
			requestHeader.addElement("TlrNo").setText("");
			requestHeader.addElement("TlrNme").setText("");
			requestHeader.addElement("TlrLvl").setText("");
			requestHeader.addElement("TlrTyp").setText("");
			requestHeader.addElement("TlrPwd").setText("");
			requestHeader.addElement("AuthTlr").setText("");
			requestHeader.addElement("AuthPwd").setText("");
			requestHeader.addElement("AuthCd").setText("");
			requestHeader.addElement("AuthFlg").setText("");
			requestHeader.addElement("AuthDisc").setText("");
			requestHeader.addElement("AuthWk").setText("");
			requestHeader.addElement("SndFileNme").setText("");
			requestHeader.addElement("BgnRec").setText("");
			requestHeader.addElement("MaxRec").setText("");
			requestHeader.addElement("FileHMac").setText("");
			requestHeader.addElement("HMac").setText("");
			/*******/
			databody.setName("RequestBody");
			cqr_seq.add(databody);
			synchroRequestMsg = XMLUtils.xmlToString(requestDoc);
			return true;
		}catch(Exception e){
			log.error("ͬ������{},ͬ�������쳣",txSyncConf.getSyncConfId());
			log.error("��װͬ�������쳣",e);
		}
		return false;
	}

	@Override
	public boolean executeResult() {
		// TODO Auto-generated method stub
		if(this.synchroResponseMsg!=null){
			log.info(this.synchroResponseMsg);
		}else{
			log.info("ͬ����Ӧ����Ϊ��");
		}
		return true;
	}

}

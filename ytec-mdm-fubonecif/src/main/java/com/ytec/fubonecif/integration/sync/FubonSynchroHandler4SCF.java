package com.ytec.fubonecif.integration.sync;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.fubonecif.domain.MCiAddress;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCrossindex;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiOrg;
import com.ytec.fubonecif.domain.MCiPerKeyflag;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.domain.txp.TxSyncErr;
import com.ytec.mdm.domain.txp.TxSyncLog;
import com.ytec.mdm.integration.sync.ptsync.SynchroExecuteHandler;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.server.common.BusinessCfg;

@Component
@Scope("prototype")
public class FubonSynchroHandler4SCF extends SynchroExecuteHandler{
	private static Logger log = LoggerFactory.getLogger(FubonSynchroHandler4SCF.class);
	private static String ECIFSYSCD = BusinessCfg.getString("appCd");
	
	private JPABaseDAO baseDAO;
	
	@Override
	public boolean execute(TxSyncConf txSyncConf, TxEvtNotice txEvtNotice) {
		String custId = txEvtNotice.getCustNo();
		if (StringUtil.isEmpty(custId)) {
			log.error("ͬ���ͻ��Ŀͻ���Ϊ��");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ���ͻ��Ŀͻ���Ϊ��");
			return false;
		}

		try {
			synchroRequestMsg = syncPackageReqXml(custId);

			System.out.printf("synchroRequestMsg.getBytes().length=%d, synchroRequestMsg.length()=%d\n", synchroRequestMsg.getBytes().length, synchroRequestMsg.length());
		} catch (Exception e) {
			String msg = String.format("��װͬ��������ʧ��(%s), �޷�����Χϵͳ(%s)ͬ���ͻ���Ϣ", e.getLocalizedMessage(), txSyncConf.getDestSysNo());
			log.error(msg);
			log.error("������Ϣ:{}", e);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
			txEvtNotice.setEventDealInfo(msg);
			return false;
		}
		   return true;
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String syncPackageReqXml(String custId) throws Exception{
		List list = null;
		try {
			list = getDataFromDb(custId);

			if (list == null) {
				String msg = String.format("��ѯ�ͻ�(%s)��Ϣʧ�ܣ��޷�ͬ����Ϣ����Ӧ��ϵͳ", custId);
				log.error(msg);
				throw new Exception(msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}	
		   
		    SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat df20 = new SimpleDateFormat("HHmmss");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		    Document requestDoc = DocumentHelper.createDocument();
		    requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);
		    Element transBody = requestDoc.addElement("TransBody");

			Element requestHeader = transBody.addElement("RequestHeader");

			requestHeader.addElement("ReqSysCd").setText(ECIFSYSCD);// ReqSysCd ��Χϵͳ����
			requestHeader.addElement("ReqSeqNo").setText(df.format(new Date()).toString()+"000");// ReqSeqNo ��Χϵͳ������ˮ��
			requestHeader.addElement("ReqDt").setText(df8.format(new Date()));// ReqDt ��������
			requestHeader.addElement("ReqTm").setText(df20.format(new Date()));// ReqTm ����ʱ��
			requestHeader.addElement("DestSysCd").setText("SCF");// Ŀ��ϵͳ����
			requestHeader.addElement("ChnlNo").setText("");// ChnlNo ������
			requestHeader.addElement("BrchNo").setText("");// BrchNo ������
			requestHeader.addElement("BizLine").setText("");// BizLine ҵ������
			requestHeader.addElement("TrmNo").setText("");// TrmNo �ն˺�
			requestHeader.addElement("TrmIP").setText("");// TrmIP �ն�IP
			requestHeader.addElement("TlrNo").setText("");// TlrNo ������Ա��
		    
			Element databody = transBody.addElement("RequestBody");
			for(int i = 0; i < list.size(); i++){
				
				Map<String, String> map = (Map<String, String>) list.get(i);
				databody.addElement("txCode").setText("SCFUpdateCustInfo");
				databody.addElement("custId").setText(map.get("custId") == null ? "" : map.get("custId"));
				databody.addElement("identType").setText(map.get("identType") == null ? "" : map.get("identType"));
				databody.addElement("identNo").setText(map.get("identNo") == null ? "" : map.get("identNo"));
				databody.addElement("inoutFlag").setText(map.get("inoutFlag") == null ? "" : map.get("inoutFlag"));
				databody.addElement("lncustp").setText(map.get("lncustp") == null ? "" : map.get("lncustp"));
				databody.addElement("transDate").setText(df8.format(new Date()));
			}
			
			String reqxml = XMLUtils.xmlToString(requestDoc);
			StringBuffer buf = new StringBuffer();
			buf.append(reqxml);
			return buf.toString();
	}


    
	@SuppressWarnings("rawtypes")
	public List getDataFromDb(String custId) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Object obj = null;
		MCiCustomer customer = new MCiCustomer();
	    MCiOrg org=new MCiOrg();
		MCiIdentifier ident=new MCiIdentifier();
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			obj = returnEntiry(custId, "MCiCustomer");
			if (obj != null) {
				customer = (MCiCustomer) obj;
			}else if(customer.getCoreNo() != null){
				String coreNo = customer.getCoreNo();
				Map<String, String> values = new HashMap<String, String>();
				values.put("custId", custId);
				values.put("srcSysNo", BusinessCfg.getString("cbCd"));
				MCiCrossindex crossIndex;
				try {
					crossIndex = (MCiCrossindex) baseDAO.findUniqueWithNameParam("from MCiCrossindex where custId=:custId and srcSysNo=:srcSysNo ", values);
					if (!coreNo.equals(crossIndex.getSrcSysCustNo())) {
						log.error("ECIF���ݴ���");
						String msg = String.format("ECIF���ݴ��󣬿ͻ���ͻ����Ŀͻ���(%s)�뽻����������Ŀͻ���(%s)��һ��", coreNo, crossIndex.getSrcSysCustNo());
						log.error(msg);
						throw new Exception(msg);
					}
				} catch (Exception e) {
					String msg = String.format("��ѯ�ͻ���Ϣ����ͨ��ECIF�ͻ���()��ѯ������������Ŀͻ��Ŵ���", custId);
					log.error(msg);
					log.error("{}", e);
					throw new Exception(msg + e.getLocalizedMessage());
				}	
			}else{
				String msg = String.format("��ѯ�ͻ���Ϣ����ͨ��ECIF�ͻ���()��ѯ�ͻ�����ϢΪ��", custId);
				log.warn(msg);
				log.warn("�ͻ�ͬ����Ϣ�ѹ��ڻ�ϵͳ���ݴ���");
				return null;
			}
			map.put("custId", customer.getCustId());
			map.put("inoutFlag", customer.getInoutFlag());
			
			Object identObj = queryAddress(custId, "identType", "20", "MCiIdentifier");
			if(identObj != null){
				ident=(MCiIdentifier)identObj;	
				map.put("identType", ident.getIdentType());
				map.put("identNo", ident.getIdentNo());
			}
			
			Object objOrg = returnEntiry(custId, "MCiOrg");
			if(objOrg != null){
				org=(MCiOrg) objOrg;
				map.put("lncustp", org.getLncustp());
			}			
			
			list.add(map);
			
		} catch (Exception e) {
				log.error("���ݿ����ʧ�ܣ�" + e.getMessage());
				log.error("������Ϣ��{}", e);
			}
		
			return list;
	}

	
	
	public Object queryContmeth(String custId, String str, String contmethType,String simpleNames) throws Exception {
	       
		try{
			   //����
			   String simpleName=simpleNames;
			   //��ñ���
			   String tableName=simpleName;
			   // ƴװJQL��ѯ���
			   StringBuffer jql=new StringBuffer();
			   Map<String,String> paramMap=new HashMap<String, String>();
			     
			   //��ѯ��
			   jql.append("FROM "+tableName+" a");
			   //��ѯ����
			   jql.append(" WHERE 1=1");
			   jql.append(" AND a.custId =:custId");
			   jql.append(" AND a." + str + " =:" + str + "");
			   // ����ѯ���������뵽map��������
			   paramMap.put("custId", custId);
			   paramMap.put(str, contmethType);
			   List result=null;
			   result=baseDAO.findWithNameParm(jql.toString(), paramMap);
			   if(result != null && result.size() > 0){
				   
				   return result.get(0);
			   }
		   
		   }catch(Exception e){
			   
			   log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
		   }
		 
		   return null;
		   
	}

	public Object queryAddress(String custId, String str, String addrType,String simpleNames) throws Exception{
		
		   try{
			   //����
			   String simpleName=simpleNames;
			   //��ñ���
			   String tableName=simpleName;
			   // ƴװJQL��ѯ���
			   StringBuffer jql=new StringBuffer();
			   Map<String,String> paramMap=new HashMap<String, String>();
			     
			   //��ѯ��
			   jql.append("FROM "+tableName+" a");
			   //��ѯ����
			   jql.append(" WHERE 1=1");
			   jql.append(" AND a.custId =:custId");
			   jql.append(" AND a." + str + " =:" + str + "");
			   // ����ѯ���������뵽map��������
			   paramMap.put("custId", custId);
			   paramMap.put(str, addrType);
			   List result=null;
			   result=baseDAO.findWithNameParm(jql.toString(), paramMap);
			   if(result != null && result.size() > 0){
				   
				   return result.get(0);
			   }
		   
		   }catch(Exception e){
			   
			   log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
		   }
		  return null;
	}

	public  Object returnEntiry(String custId, String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();
		// ��ѯ��
		jql.append("FROM " + tableName + " a");
		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		if("MCiIdentifier".equals(tableName)){
			jql.append(" and  a.isOpenAccIdent='Y' ");
		}
		// ����ѯ���������뵽map��������
		paramMap.put("custId", custId);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

	    return null;
	    
	}
	
	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Element databody) {
		
		   return true;
	}

	@Override
	public boolean executeResult() {
		if (this.synchroResponseMsg != null) {
			// log.info("����ͬ����Ӧ���ģ�\n{}", this.synchroResponseMsg);
			// TODO
			try {
				Document root = XMLUtils.stringToXml(synchroResponseMsg.substring(8));
				
				String txStatCodeXpath = "//TransBody/ResponseTail/TxStatCode";
				String txStatDescXpath = "//TransBody/ResponseTail/TxStatString";
				String txStatDetailXpath = "//TransBody/ResponseTail/TxStatDesc";
				
				Node txStatCodeNode = root.selectSingleNode(txStatCodeXpath);
				Node txStatDescNode = root.selectSingleNode(txStatDescXpath);
				Node txStatDetailNode = root.selectSingleNode(txStatDetailXpath);
				if (txStatCodeNode == null || txStatDescNode == null || txStatDetailNode == null) {
					String msg = String.format("{},�յ���Χϵͳ[%s]��Ӧ���ı�Ҫ�ڵ�Ϊ��[��Ҫ�ڵ㣺%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("scfCd"), txStatCodeXpath,
							txStatDescXpath, txStatDetailXpath);
					log.error(msg);
					
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
				String txStatDetail = "��Χϵͳ[" + BusinessCfg.getString("wmsCd") + "]��Ӧ:txStatDesc:{" + txStatDesc + "},txStatDetail:{" + txStatDetailNode.getText() + "}";

				syncLog = new TxSyncLog();
				syncLog.setSyncDealResult(txStatCode);
				syncLog.setSyncDealInfo(txStatDetail);

				// TODO 0000 to Constant
				if ("000000".equals(txStatCode)) {
					return true;
				} else {
					syncErr = new TxSyncErr();
					syncErr.setSyncDealResult(txStatCode);
					syncErr.setSyncDealInfo(txStatDetail);
					return false;
				}
			} catch (DocumentException e) {
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("scfCd"), e);
				return false;
			} catch (Exception e) {
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("scfCd"), e);
				return false;
			}
		} else {
			// TODO
			log.info("ͬ����Ӧ����Ϊ��");
			return false;
		}
	}

}

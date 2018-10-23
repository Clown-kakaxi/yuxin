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
			log.error("同步客户的客户号为空");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步客户的客户号为空");
			return false;
		}

		try {
			synchroRequestMsg = syncPackageReqXml(custId);

			System.out.printf("synchroRequestMsg.getBytes().length=%d, synchroRequestMsg.length()=%d\n", synchroRequestMsg.getBytes().length, synchroRequestMsg.length());
		} catch (Exception e) {
			String msg = String.format("组装同步请求报文失败(%s), 无法向外围系统(%s)同步客户信息", e.getLocalizedMessage(), txSyncConf.getDestSysNo());
			log.error(msg);
			log.error("错误信息:{}", e);
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
				String msg = String.format("查询客户(%s)信息失败，无法同步信息至供应链系统", custId);
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

			requestHeader.addElement("ReqSysCd").setText(ECIFSYSCD);// ReqSysCd 外围系统代号
			requestHeader.addElement("ReqSeqNo").setText(df.format(new Date()).toString()+"000");// ReqSeqNo 外围系统交易流水号
			requestHeader.addElement("ReqDt").setText(df8.format(new Date()));// ReqDt 请求日期
			requestHeader.addElement("ReqTm").setText(df20.format(new Date()));// ReqTm 请求时间
			requestHeader.addElement("DestSysCd").setText("SCF");// 目标系统代号
			requestHeader.addElement("ChnlNo").setText("");// ChnlNo 渠道号
			requestHeader.addElement("BrchNo").setText("");// BrchNo 机构号
			requestHeader.addElement("BizLine").setText("");// BizLine 业务条线
			requestHeader.addElement("TrmNo").setText("");// TrmNo 终端号
			requestHeader.addElement("TrmIP").setText("");// TrmIP 终端IP
			requestHeader.addElement("TlrNo").setText("");// TlrNo 操作柜员号
		    
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
						log.error("ECIF数据错误，");
						String msg = String.format("ECIF数据错误，客户表客户核心客户号(%s)与交叉索引表核心客户号(%s)不一致", coreNo, crossIndex.getSrcSysCustNo());
						log.error(msg);
						throw new Exception(msg);
					}
				} catch (Exception e) {
					String msg = String.format("查询客户信息错误，通过ECIF客户号()查询交叉索引表核心客户号错误", custId);
					log.error(msg);
					log.error("{}", e);
					throw new Exception(msg + e.getLocalizedMessage());
				}	
			}else{
				String msg = String.format("查询客户信息错误，通过ECIF客户号()查询客户表信息为空", custId);
				log.warn(msg);
				log.warn("客户同步信息已过期或系统数据错误");
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
				log.error("数据库操作失败：" + e.getMessage());
				log.error("错误信息：{}", e);
			}
		
			return list;
	}

	
	
	public Object queryContmeth(String custId, String str, String contmethType,String simpleNames) throws Exception {
	       
		try{
			   //类名
			   String simpleName=simpleNames;
			   //获得表名
			   String tableName=simpleName;
			   // 拼装JQL查询语句
			   StringBuffer jql=new StringBuffer();
			   Map<String,String> paramMap=new HashMap<String, String>();
			     
			   //查询表
			   jql.append("FROM "+tableName+" a");
			   //查询条件
			   jql.append(" WHERE 1=1");
			   jql.append(" AND a.custId =:custId");
			   jql.append(" AND a." + str + " =:" + str + "");
			   // 将查询的条件放入到map集合里面
			   paramMap.put("custId", custId);
			   paramMap.put(str, contmethType);
			   List result=null;
			   result=baseDAO.findWithNameParm(jql.toString(), paramMap);
			   if(result != null && result.size() > 0){
				   
				   return result.get(0);
			   }
		   
		   }catch(Exception e){
			   
			   log.error("查询客户编号失败：" + e.getMessage());
		   }
		 
		   return null;
		   
	}

	public Object queryAddress(String custId, String str, String addrType,String simpleNames) throws Exception{
		
		   try{
			   //类名
			   String simpleName=simpleNames;
			   //获得表名
			   String tableName=simpleName;
			   // 拼装JQL查询语句
			   StringBuffer jql=new StringBuffer();
			   Map<String,String> paramMap=new HashMap<String, String>();
			     
			   //查询表
			   jql.append("FROM "+tableName+" a");
			   //查询条件
			   jql.append(" WHERE 1=1");
			   jql.append(" AND a.custId =:custId");
			   jql.append(" AND a." + str + " =:" + str + "");
			   // 将查询的条件放入到map集合里面
			   paramMap.put("custId", custId);
			   paramMap.put(str, addrType);
			   List result=null;
			   result=baseDAO.findWithNameParm(jql.toString(), paramMap);
			   if(result != null && result.size() > 0){
				   
				   return result.get(0);
			   }
		   
		   }catch(Exception e){
			   
			   log.error("查询客户编号失败：" + e.getMessage());
		   }
		  return null;
	}

	public  Object returnEntiry(String custId, String tableName) {
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();
		// 查询表
		jql.append("FROM " + tableName + " a");
		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		if("MCiIdentifier".equals(tableName)){
			jql.append(" and  a.isOpenAccIdent='Y' ");
		}
		// 将查询的条件放入到map集合里面
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
			// log.info("数据同步响应报文：\n{}", this.synchroResponseMsg);
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
					String msg = String.format("{},收到外围系统[%s]响应报文必要节点为空[需要节点：%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("scfCd"), txStatCodeXpath,
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
				String txStatDetail = "外围系统[" + BusinessCfg.getString("wmsCd") + "]响应:txStatDesc:{" + txStatDesc + "},txStatDetail:{" + txStatDetailNode.getText() + "}";

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
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("scfCd"), e);
				return false;
			} catch (Exception e) {
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("scfCd"), e);
				return false;
			}
		} else {
			// TODO
			log.info("同步响应报文为空");
			return false;
		}
	}

}

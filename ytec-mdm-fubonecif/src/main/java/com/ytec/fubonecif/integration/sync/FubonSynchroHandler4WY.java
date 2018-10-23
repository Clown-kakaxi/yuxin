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

import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCrossindex;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
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
public class FubonSynchroHandler4WY extends SynchroExecuteHandler{
	private static Logger log = LoggerFactory.getLogger(FubonSynchroHandler4WY.class);
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
				String msg = String.format("查询客户(%s)信息失败，无法同步信息至网银系统", custId);
				log.error(msg);
				throw new Exception(msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}	
		   
		    SimpleDateFormat df8 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");//确定日期格式
		    SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmss");
		    Document requestDoc = DocumentHelper.createDocument();
		    requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);
		    Element transBody=requestDoc.addElement("Message");
		    
		    Element requestHeader=transBody.addElement("Head");
		    requestHeader.addElement("_MChannelId").setText("CRM");// 渠道代码
		    requestHeader.addElement("_MCHTimestamp").setText(df8.format(new Date()));//传输日期时间
		    requestHeader.addElement("_TransactionId").setText("crm.MCChannelUpdateCifInfo");//交易代码
			requestHeader.addElement("_MCHJnlNo").setText(df20.format(new Date()).toString()+"000");//交易序号
			requestHeader.addElement("_LoginType").setText("");//登陆类型
			requestHeader.addElement("_AuthMode").setText("");//认证方式
		    
			Element databody = transBody.addElement("Body");
			//databody.addElement("txCode").setText("TBWY");
			
			//List<MCiAddress> addrList = baseDAO.findWithIndexParam("FROM MCiAddress where custId=?", custId);
			List<MCiContmeth> contList= baseDAO.findWithIndexParam("FROM MCiContmeth where custId=?", custId);
			
			for(int i = 0; i < list.size(); i++)
			{
				
				Map<String, String> map = (Map<String, String>) list.get(i);
				databody.addElement("CifNo").setText(map.get("coreNo") == null ? "" : map.get("coreNo"));
				databody.addElement("CifName").setText(map.get("custName") == null ? "" : map.get("custName"));
				databody.addElement("CifType").setText("C");
				databody.addElement("CifEngName").setText(map.get("enName") == null ? "" : map.get("enName"));
				
			    
			    if(contList != null && contList.size() > 0){
			    	
			    	for(MCiContmeth contmeth : (List<MCiContmeth>)contList){

			    		if("102".equals(contmeth.getContmethType())){
			    			
			    			databody.addElement("PhoneNum").setText(contmeth.getContmethInfo() == null ? "" : contmeth.getContmethInfo());
			    		}
			    		if("501".equals(contmeth.getContmethType())){

			    			databody.addElement("Email").setText(contmeth.getContmethInfo() == null ? "" : contmeth.getContmethInfo());
			    		}
			    	}
			    }
			    //databody.addElement("PhoneNum").setText(map.get("PhoneNum") == null ? "" : map.get("PhoneNum"));	
			    //databody.addElement("Email").setText(map.get("Email") == null ? "" : map.get("Email"));
			    databody.addElement("IdNo").setText(map.get("IdNo") == null ? "" : map.get("IdNo"));
			    databody.addElement("IdType").setText(map.get("IdType") == null ? "" : map.get("IdType"));
			    
			    //add by liuming 20170715
			    databody.addElement("Effdate").setText(map.get("Effdate") == null ? "" : map.get("Effdate"));//开户证件生效日期
			    databody.addElement("Expdate").setText(map.get("Expdate") == null ? "" : map.get("Expdate"));//开户证件失效日期
			    databody.addElement("Birthdate").setText(map.get("Birthdate") == null ? "" : map.get("Birthdate"));//生日
			    databody.addElement("Gender").setText(map.get("Gender") == null ? "" : map.get("Gender"));//性别
			}
			
			String reqxml = XMLUtils.xmlToString(requestDoc);
			StringBuffer buf = new StringBuffer();
			buf.append(reqxml);
			buf.append("\r");
			return buf.toString();
	}


    
	@SuppressWarnings("rawtypes")
	public List getDataFromDb(String custId) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Object obj = null;
		MCiCustomer customer = new MCiCustomer();
		//modify by liuming 20170715
	    MCiPerson person = new MCiPerson();
		MCiIdentifier identity=new MCiIdentifier();
		//MCiPerKeyflag perKeyflag=new MCiPerKeyflag();
		//MCiAddress address = new MCiAddress();
		MCiContmeth contmeth = new MCiContmeth();
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			obj = returnEntiry(custId, "MCiCustomer");
			if (obj != null) {
				customer = (MCiCustomer) obj;
			}else if(customer.getCoreNo() != null){
				String coreNo = customer.getCoreNo();
				Map<String, String> values = new HashMap<String, String>();
				values.put("custId", custId);
				values.put("srcSysNo", BusinessCfg.getString("wyCd"));
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
			map.put("coreNo", customer.getCoreNo());
			map.put("custName",customer.getCustName());
			String enName=customer.getEnName();
			if(enName==null || "".equals(enName)){
				map.put("enName", "");
			}else{
			    map.put("enName", customer.getEnName());
			}
			
		     Object contObject = queryContmeth(custId, "contmethType", "2031", "MCiContmeth");
		      if (contObject != null) {
		        contmeth = (MCiContmeth)contObject;
		      } else {
		        Object contObject1 = queryContmeth(custId, "contmethType", "2041", "MCiContmeth");
		        if (contObject1 != null) {
		          contmeth = (MCiContmeth)contObject1;
		        } else {
		          Object contObject2 = queryContmeth(custId, "contmethType", "209", "MCiContmeth");
		          if (contObject2 != null) {
		            contmeth = (MCiContmeth)contObject2;
		          } else {
		            Object contObject3 = queryContmeth(custId, "contmethType", "209", "MCiContmeth");
		            if (contObject3 != null) {
		              contmeth = (MCiContmeth)contObject3;
		            } else {
		              Object contObject4 = queryContmeth(custId, "contmethType", "500", "MCiContmeth");
		              if (contObject4 != null) {
		                contmeth = (MCiContmeth)contObject4;
		              } else {
		                Object contObject5 = queryContmeth(custId, "contmethType", "501", "MCiContmeth");
		                if (contObject5 != null) {
		                  contmeth = (MCiContmeth)contObject5;
		                }
		              }
		            }
		          }
		        }
		      }

			
			Object objIdentity = returnEntiry(custId, "MCiIdentifier");
			if(objIdentity != null){
				identity=(MCiIdentifier)objIdentity;	
			}
			map.put("IdNo", identity.getIdentNo());
			map.put("IdType", identity.getIdentType());
			//add by liuming 20170715增加证件有效期，证件失效日期(个人和企业)
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//证件有效期
			map.put("Effdate", (identity.getIdentEffectiveDate() == null || identity.getIdentEffectiveDate().equals("")) ? "" : sdf.format(identity.getIdentEffectiveDate()));
			//证件失效日期
			map.put("Expdate", (identity.getIdentExpiredDate() == null || identity.getIdentExpiredDate().equals("")) ? "" : sdf.format(identity.getIdentExpiredDate()));
			//生日
			map.put("Birthdate", "");
			//性别
			map.put("Gender", "");
			if(customer.getCustType().equals("2")){//个人
				Object objPsrson = returnEntiry(custId, "MCiPerson");
				if(objPsrson != null){
					person = (MCiPerson)objPsrson;	
					//生日
					map.put("Birthdate", (person.getBirthday() == null || person.getBirthday().equals("")) ? "" : sdf.format(person.getBirthday()));
					//性别
					if(person.getGender() != null && person.getGender().equals("1")){
						map.put("Gender", "M");//男
					}else if (person.getGender() != null && person.getGender().equals("2")){
						map.put("Gender", "F");//女
					}else{
						map.put("Gender", "U");//未知
					}
				}
			}
			
			obj=queryContmeth(custId,"contmethType","102","MCiContmeth");
			if(obj!=null){
				contmeth=(MCiContmeth)obj;
				String bzContmeth=contmeth.getContmethInfo();
				if(bzContmeth ==null && "".equals(bzContmeth)){
					
					map.put("PhoneNum", "");
				}else{
					map.put("PhoneNum", bzContmeth);
				}
			}else{//没有这个类型的联系方式信息
				
				map.put("PhoneNum", "");
			}
			
			obj=queryContmeth(custId,"contmethType","501","MCiContmeth");
			if(obj!=null){
				contmeth=(MCiContmeth)obj;
				String bzEmail=contmeth.getContmethInfo();
				if(bzEmail ==null && "".equals(bzEmail)){
					
					map.put("Email", "");
				}else{
					map.put("Email", bzEmail);
				}
			}else{//没有这个类型的联系方式信息
				
				map.put("Email", "");
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

				String txStatCodeXpath = "//Message/Head/_RejCode";
				String txStatDescXpath = "//Message/Head/_ProcessState";
				String txStatDetailXpath = "//Message/Head/_RejMsg";

				Node txStatCodeNode = root.selectSingleNode(txStatCodeXpath);
				Node txStatDescNode = root.selectSingleNode(txStatDescXpath);
				Node txStatDetailNode = root.selectSingleNode(txStatDetailXpath);
				if (txStatCodeNode == null || txStatDescNode == null || txStatDetailNode == null) {
					String msg = String.format("{},收到外围系统[%s]响应报文必要节点为空[需要节点：%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("wyCd"), txStatCodeXpath,
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
				String txStatDetail = "外围系统[" + BusinessCfg.getString("wyCd") + "]响应:txStatDesc:{" + txStatDesc + "},txStatDetail:{" + txStatDetailNode.getText() + "}";
				
				syncLog = new TxSyncLog();
				syncLog.setSyncDealResult(txStatCode);
				syncLog.setSyncDealInfo(txStatDetail);

				// TODO 0000 to Constant
				if ("000000".equals(txStatCode)) {
					return true;
				} else {
//					syncErr = new TxSyncErr();
//					syncErr.setSyncDealResult(txStatCode);
//					syncErr.setSyncDealInfo(txStatDetail);
//					return false;
					if("EIPCRM00100".equals(txStatCode)){
						syncErr = new TxSyncErr();
						syncErr.setSyncDealResult(txStatCode);
						syncErr.setSyncDealInfo("未找到网银客户，请检查你的业务系统类型及号码");
					}
					if("EIPCRM00101".equals(txStatCode)){
						syncErr = new TxSyncErr();
						syncErr.setSyncDealResult(txStatCode);
						syncErr.setSyncDealInfo("未找到网银客户证件类型，请检查你的证件类型");
					}
					return false;
				}
			} catch (DocumentException e) {
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("wyCd"), e);
				return false;
			} catch (Exception e) {
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("wyCd"), e);
				return false;
			}
		} else {
			// TODO
			log.info("同步响应报文为空");
			return false;
		}
	}

}

package com.yuchengtech.bcrm.oneKeyAccount.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.customer.model.AcrmFCiAddress;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiAccountInfo;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiOpenInfo;
import com.yuchengtech.bcrm.oneKeyAccount.model.OnekeyAcccout2CBVO;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.oneKeyOpen.CBOpenAccountTransaction;
import com.yuchengtech.trans.inf.Transaction;

/**
 * CRM请求核心一键开户
 * @author wx
 *
 */
@Service
public class OnekeyAccount2CBService extends CommonService{
	private static Logger log = LoggerFactory.getLogger(OnekeyAccount2CBService.class);
	public OnekeyAccount2CBService() {
		JPABaseDAO<AcrmFCiCustomer, String> baseDAO = new JPABaseDAO<AcrmFCiCustomer, String>(
				AcrmFCiCustomer.class);
		super.setBaseDAO(baseDAO);
	}
	
	
	/**
	 * 核心开户
	 * @param logId
	 * @param custId
	 * @return
	 */
	public Map<String, Object> account2CB(String logId, String custId){
		boolean isOnlyPushInfo = true;//是否只推送信息不开户
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(logId) || StringUtils.isEmpty(custId)){
			String msg = "请求参数logId或ecifId缺失，无法查询信息";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		OnekeyAcccout2CBVO vo = new OnekeyAcccout2CBVO();
		vo.setTranType("1");
		vo.setCUSTID(custId);
		String userCode = auth.getUserId();
		userCode = userCode.substring(0, 3) + userCode.substring(4, userCode.length());
		vo.setCOCODE(userCode);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("custId", custId);
		//查询客户信息
		String jql_custInfo = "select t from AcrmFCiCustomer t where t.custId=:custId";//
		List<AcrmFCiCustomer> l_custInfo = this.baseDAO.findWithNameParm(jql_custInfo, params);
		String coreNo = "";
		if(l_custInfo == null || l_custInfo.size() != 1){
			String msg = "根据客户号查询客户信息失败";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}else{
			AcrmFCiCustomer custInfo = l_custInfo.get(0);
			coreNo = custInfo.getCoreNo();//核心客户号，如果是老户则不为空
			String identType = custInfo.getIdentType();//开户证件类型
			String identNo = custInfo.getIdentNo();//证件号码
			if(StringUtils.isEmpty(identType) || StringUtils.isEmpty(identNo)){
				String msg = "查询客户信息失败，证件类型或证件号码为空";//
				log.error(msg);
				retMap.put("status", "error");
				retMap.put("msg", msg);
				return retMap;
			}
			//匹配当前证件类型对应的核心证件类型
			if(StringUtils.isEmpty(identType)){
				String msg = "获取证件类型失败，请重试或联系管理员";//
				log.error(msg);
				retMap.put("status", "error");
				retMap.put("msg", msg);
				return retMap;
			}
			String idTypeSql = "select T.REL2 from OCRM_F_SYS_ACCHK T where  T.CHK_TYPE='CC2' and T.REL1='"+identType+"'";
			List<Object> idTypeList = this.baseDAO.findByNativeSQLWithNameParam(idTypeSql, null);
			String cdIdType = "";
			if(idTypeList != null && idTypeList.size() == 1){
				Object os = idTypeList.get(0);
				if(os != null && !os.toString().equals("")){
					cdIdType = os.toString();
				}
			}
			if(StringUtils.isEmpty(cdIdType)){
				String msg = "无法为当前证件类型匹配到对应的核心证件类型，无法开户";//
				log.error(msg);
				retMap.put("status", "error");
				retMap.put("msg", msg);
				return retMap;
			}
			vo.setIDCODE(cdIdType);
			vo.setBRAIDID(identNo);
			String custNm = custInfo.getCustName();
			vo.setCUFULNM(custNm);
			String riskCode = custInfo.getRiskNationCode();//风险国别代码
			if(StringUtils.isEmpty(custNm) || StringUtils.isEmpty(riskCode)){
				String msg = "查询客户信息失败，风险国别代码为空";//
				log.error(msg);
				retMap.put("status", "error");
				retMap.put("msg", msg);
				return retMap;
			}
			vo.setRISCOUN(riskCode);
		}
		//查询证件信息
		String jql_identInfo = "select t from AcrmFCiCustIdentifier t where t.custId=:custId and t.isOpenAccIdent != 'Y'";
		List<AcrmFCiCustIdentifier> l_identInfo = this.baseDAO.findWithNameParm(jql_identInfo, params);
		if(l_identInfo != null && l_identInfo.size() == 1){
			AcrmFCiCustIdentifier identInfo = l_identInfo.get(0);
			vo.setREFID(identInfo.getIdentNo() ==  null ? "" : identInfo.getIdentNo().toString());
		}
		//查询个人信息
		String jql_personInfo = "select t from AcrmFCiPerson t where t.custId=:custId";//
		List<AcrmFCiPerson> l_personInfo = this.baseDAO.findWithNameParm(jql_personInfo, params);
		if(l_personInfo == null || l_personInfo.size() != 1){
			String msg = "查询个人信息失败";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}else{
			AcrmFCiPerson personInfo = l_personInfo.get(0);
			String citizenShip = personInfo.getCitizenship();
			if(StringUtils.isEmpty(citizenShip)){
				String msg = "个人信息中国别为空";//
				log.error(msg);
				retMap.put("status", "error");
				retMap.put("msg", msg);
				return retMap;
			}
			if(citizenShip.equals("CTN")){
				citizenShip = "TWN";
			}
			vo.setCORCOUN(citizenShip);	
			String getder = personInfo.getGender();
			if(getder != null && getder.equals("1")){//男
				vo.setSEXIND("M");
			}else if(getder.equals("2")){
				vo.setSEXIND("F");
			}
			Date birthday = personInfo.getBirthday();
			if(birthday != null){
				String str_birthday = new SimpleDateFormat("yyyyMMdd").format(birthday);
				vo.setESTDATE(str_birthday);
			}
			String perEnname = personInfo.getPinyinName();
			if(StringUtils.isEmpty(perEnname) || StringUtils.isEmpty(perEnname)){
				String msg = "查询客户信息失败，客户姓名或英文名称为空";//
				log.error(msg);
				retMap.put("status", "error");
				retMap.put("msg", msg);
				return retMap;
			}
			vo.setCUSNM(perEnname);
		}
		//查询地址信息--AcrmFCiAddress
		String jql_addrInfo = "select t from AcrmFCiAddress t where t.custId=:custId";
		List<AcrmFCiAddress> l_addrInfo = this.baseDAO.findWithNameParm(jql_addrInfo, params);
		if(l_addrInfo != null && l_addrInfo.size() >= 1){
			AcrmFCiAddress addrInfo = l_addrInfo.get(0);
			String addr = addrInfo.getAddr() == null ? "" : addrInfo.getAddr();
			//地址只有一个
			vo.setMAILAD1(addr);
//				for (int i = 0; i < l_addrInfo.size(); i++) {
//					AcrmFCiAddress addrInfo = l_addrInfo.get(i);
//					String addr = addrInfo.getAddr() == null ? "" : addrInfo.getAddr();
//					//地址只有一个
//					vo.setMAILAD1(addr);
//					if(i == 0){
//						vo.setMAILAD1(addr);
//					}else if(i == 1){
//						vo.setMAILAD2(addr);
//					}else if(i == 2){
//						vo.setMAILAD3(addr);
//					}
//				}
		}
		//查询电话信息
		String jql_contMethInfo = "select t from AcrmFCiContmeth t where t.custId=:custId";
		List<AcrmFCiContmeth> l_contMethInfo = this.baseDAO.findWithNameParm(jql_contMethInfo, params);
		if(l_contMethInfo != null && l_contMethInfo.size() >= 1){
			int resCount = 0;
			for (int i = 0; i < l_contMethInfo.size(); i++) {
				AcrmFCiContmeth acrmFCiContmeth = l_contMethInfo.get(i);
				String contmethType = acrmFCiContmeth.getContmethType();
				String contmethInfo = acrmFCiContmeth.getContmethInfo() == null ? "" : acrmFCiContmeth.getContmethInfo();
				if(contmethType.equals("500") || contmethType.equals("501") || contmethType.equals("502") || contmethType.equals("106")){//电子邮箱、电子对账单
					vo.setEMAILAD(contmethInfo);
				}else{//联系电话
					/*if(contmethInfo.getBytes().length > 14){
						String msg = "联系电话【"+contmethInfo+"】长度超过14，无法请求核心开户";//
						log.error(msg);
						retMap.put("status", "error");
						retMap.put("msg", msg);
						return retMap;
					}*/
					if("100".equals(contmethType)){//手机
						contmethInfo = contmethInfo.substring(contmethInfo.lastIndexOf("-") + 1, contmethInfo.length());
					}else if("204".equals(contmethType) || "203".equals(contmethType) || "999".equals(contmethType)){//住宅电话 3个“-”
						contmethInfo = contmethInfo.substring(contmethInfo.indexOf("-") + 1, contmethInfo.length());
						contmethInfo = contmethInfo.substring(contmethInfo.indexOf("-") + 1, contmethInfo.length());
					}
					if(contmethInfo.getBytes().length > 14){
						String msg = "联系电话【"+contmethInfo+"】长度超过14，无法请求核心开户";//
						log.error(msg);
						retMap.put("status", "error");
						retMap.put("msg", msg);
						return retMap;
					}
					if(resCount == 0){
						vo.setCUSTEL1(contmethInfo);
					}else if(resCount == 1){
						vo.setCUSTEL2(contmethInfo);
					}else if(resCount == 2){
						vo.setCUSTEL3(contmethInfo);
					}
					resCount++;
				}
				
			}
		}
		//添加机构号
		vo.setBRANCH(auth.getUnitId());
		//添加归属客户经理信息
		String jql_belongMgrInfo = "select t from OcrmFCiBelongCustmgr t where t.custId=:custId";
		List<OcrmFCiBelongCustmgr> l_belongMgrInfo = this.baseDAO.findWithNameParm(jql_belongMgrInfo, params);
		if(l_belongMgrInfo == null || l_belongMgrInfo.size() != 1){
			String msg = "归属客户经理信息为空";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		OcrmFCiBelongCustmgr belongMgrInfo = l_belongMgrInfo.get(0); 
		String custManagerNo = belongMgrInfo.getMgrId();
		if(StringUtils.isEmpty(custManagerNo)){
			String msg = "归属客户经理的工号为空";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		vo.setACCODE(custManagerNo);
		//查询境内外信息
		String jql_openInfo = "select t from OcrmFCiOpenInfo t where t.custId=:custId";
		List<OcrmFCiOpenInfo> l_openInfo = this.baseDAO.findWithNameParm(jql_openInfo, params);
		String outFlag = "";
		if(l_openInfo != null && l_openInfo.size() == 1){
			outFlag = l_openInfo.get(0).getInoutFlag() == null ? "" : l_openInfo.get(0).getInoutFlag();
		}else{
			String msg = "没有查询到账户境内外信息，无法开户";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}

		//查询账户信息
		String jql_accountInfo = "select t from OcrmFCiAccountInfo t where t.cust_id=:custId order by t.id desc";
		List<OcrmFCiAccountInfo> l_accountInfo = this.baseDAO.findWithNameParm(jql_accountInfo, params);
		ArrayList<String> accates = new ArrayList<String>();
		if(l_accountInfo != null && l_accountInfo.size() >= 1){
			for (OcrmFCiAccountInfo accountInfo : l_accountInfo) {
				String ACCATE = accountInfo.getActType();
				if(!StringUtils.isEmpty(ACCATE)){
					accates.add(ACCATE);
					if(outFlag.equals("D")){
						if(ACCATE.equals("K")){
							isOnlyPushInfo = false;
							vo.setACCATE("K");
						}
					}else if(outFlag.equals("F")){
						if(ACCATE.equals("H")){
							isOnlyPushInfo = false;
							vo.setACCATE("H");
						}
					}
				}
			}

			if(isOnlyPushInfo){
				vo.setACCATE(accates.get(0));
			}
			vo.setOnlyPushInfo(isOnlyPushInfo);
		}else{
			String msg = "没有查询到该客户的账户类型，请联系管理员";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		//校验报文字段
		String[] fileds = new String[]{vo.getIDCODE(), vo.getBRAIDID(), vo.getREFID(),
										vo.getCUFULNM(), vo.getCUSNM(), vo.getCORCOUN(),
										vo.getRISCOUN(), vo.getSEXIND(), vo.getESTDATE(),
										vo.getMAILAD1(), vo.getCUSTEL1(), vo.getCUSTEL2(),
										vo.getCUSTEL3(), vo.getEMAILAD(), vo.getBRANCH(),
										vo.getACCODE(), vo.getCOCODE(), vo.getACCATE()};
		boolean[] requireds = new boolean[]{true, true, false,
											true, true, true,
											true, false, false,
											false, false, false,
											false, false, true,
											true, true, true};
		int[] filedLens = new int[]{1, 20, 20,
									50, 35, 3,
									3, 1, 8,
									100, 14, 14,
									14, 100, 3,
									10, 10, 3};
		boolean validMsgFiled = validMsgFiled(fileds, requireds, filedLens);
		if(!validMsgFiled){
			String logMsg = "请求内容有字段错误，请联系管理员";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
			return retMap;
		}
		
		//请求核心开户
		TxData txData = new TxData();
		String reqMsg = this.packageMsg(vo);//请求报文
		try {
			reqMsg = String.format("%08d", reqMsg.getBytes("GBK").length) + reqMsg;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			String logMsg = "系统错误，联系管理员";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
			return retMap;
		}//添加报文长度
		txData.setReqMsg(reqMsg);//添加请求报文
		Transaction trans = new CBOpenAccountTransaction(txData);//核心开户交易处理类
		trans.process();
		TxLog txLog = trans.getTxLog();
		this.baseDAO.save(txLog);
		retMap = txData.getTxMap();
		if(retMap.isEmpty()){
			String msg = "核心开户失败，响应报文不存在";//
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		if(retMap.containsKey("status") && retMap.get("status").equals("success")){
			if(retMap.containsKey("isNewCust")){
				boolean isNewCust = (Boolean) retMap.get("isNewCust");
				if(!isNewCust){//如果是老户，查询核心客户号
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("custId", custId);
					Object o_EcifCoreNo = retMap.get("coreNo");
					if(o_EcifCoreNo == null || StringUtils.isEmpty(o_EcifCoreNo.toString())){
						String jql_custInfo2 = "select t from AcrmFCiCustomer t where t.custId=:custId";//
						List<AcrmFCiCustomer> l_custInfo2 = this.baseDAO.findWithNameParm(jql_custInfo2, params2);
						if(l_custInfo2 != null && l_custInfo2.size() >= 1){
							AcrmFCiCustomer custInfo2 = l_custInfo2.get(0);
							coreNo = custInfo2.getCoreNo();
						}
						if(StringUtils.isEmpty(coreNo)){
							String msg = "数据有误--该客户在核心已开户，但在ECIF和CRM中都未获取到核心客户号，请联系管理员";//
							log.error(msg);
							retMap.put("status", "error");
							retMap.put("msg", msg);
							return retMap;
						}
					}
					coreNo = o_EcifCoreNo.toString();
					//查询核心客户账号信息
					String coreAccNo = "";
					String jql_custOpenInfo = "select t from OcrmFCiOpenInfo t where t.custId=:custId";
					List<OcrmFCiOpenInfo> l_custOpenInfo = this.baseDAO.findWithNameParm(jql_custOpenInfo, params2);
					if(l_custOpenInfo != null && l_custOpenInfo.size() >= 1){
						OcrmFCiOpenInfo custOpenInfo = l_custOpenInfo.get(0);
						coreAccNo = custOpenInfo.getAccNo();
					}
					String logMsg = "该客户已存在";//
					log.info(logMsg);
					retMap.put("status", "success");
					retMap.put("msg", logMsg);
					retMap.put("isNewCust", true);
					retMap.put("CUSTCOD", coreNo);
					retMap.put("ACCNO", coreAccNo);
//						retMap.put("ACCCY", ACCCY);
				}else{
					if(isOnlyPushInfo){//开户成功，但是当前账户类型只送信息不开户
						String logMsg = "核心开户（只送信息不开户）成功";//
						log.info(logMsg);
						retMap.put("status", "success");
						retMap.put("msg", logMsg);
						retMap.put("onlyAddInfo", true);
						return retMap;
					}
					//ReqSeqNo
					String ReqSeqNo = retMap.get("ReqSeqNo") == null ? "" : retMap.get("ReqSeqNo").toString();//交易流水号
					String CUSTCOD = retMap.get("CUSTCOD") == null ? "" : retMap.get("CUSTCOD").toString();//核心客户号
					String ACCNO = retMap.get("ACCNO") == null ? "" : retMap.get("ACCNO").toString();//核心客户账号
					String ACCCY1 = retMap.get("ACCCY1") == null ? "" : retMap.get("ACCCY1").toString();//币种1
					String ACCCY2 = retMap.get("ACCCY2") == null ? "" : retMap.get("ACCCY2").toString();//币种2
					String custAcccy = ACCCY1;//核心返回两个币种，CRM数据库字段只够存一个
					if(StringUtils.isEmpty(ACCCY1) && !StringUtils.isEmpty(ACCCY2)){
						custAcccy = ACCCY2;
					}
					//更新客户信息
					String jql_up = "update AcrmFCiCustomer t set t.coreNo=:CUSTCOD where t.custId=:custId";
					params.clear();
					params.put("CUSTCOD", CUSTCOD);
					params.put("custId", custId);
					this.baseDAO.batchExecuteWithNameParam(jql_up, params);
					//更新核心客户帐号及币种
					String jql_countInfo = "update OcrmFCiOpenInfo t set t.accNo=:ACCNO, t.curType=:ACCCY where t.custId=:custId";
					params.clear();
					params.put("custId", custId);
					params.put("ACCNO", ACCNO);
					params.put("ACCCY", custAcccy);
					this.baseDAO.batchExecuteWithNameParam(jql_countInfo, params);
					//更新交叉索引表
					String seqSql = "select max(T.crossindex_id)+1 from ACRM_O_CROSSINDEX T";
					List<Object> seqL = this.baseDAO.findByNativeSQLWithNameParam(seqSql, null);
					String crossId = "100000000";
					if(seqL != null && seqL.size() == 1){
						crossId = seqL.get(0) == null ? "100000000" : seqL.get(0).toString();
					}
					String insertSql = "INSERT INTO ACRM_O_CROSSINDEX";
					insertSql += "(CROSSINDEX_ID, SRC_SYS_NO, SRC_SYS_CUST_NO, CUST_ID, LAST_UPDATE_SYS, LAST_UPDATE_USER, LAST_UPDATE_TM, TX_SEQ_NO)";
					insertSql += " VALUES(?0,?1,?2,?3,?4,?5,?6,?7)";
					String userId = auth.getUserId();
					if(userId.length() >= 8){
						userId = userId.substring(0, 3) + userId.substring(4, 8);
					}
					this.baseDAO.createNativeQueryWithIndexParam(insertSql,crossId,"ECIF",CUSTCOD,custId,"CRM",userId,new Timestamp(System.currentTimeMillis()),ReqSeqNo).executeUpdate();
				}
			}
		}
		return retMap;
	}
	
	
	/**
	 * 拼接报文
	 * @param vo
	 * @return
	 */
	private String packageMsg(OnekeyAcccout2CBVO vo){
		StringBuffer sb_msg = new StringBuffer();
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("HHmmssSS");
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Date currDate = new Date();
		String _userId = auth.getUserId();
		if(_userId.length() >= 8){
			_userId = _userId.substring(0, 3) + _userId.substring(4, 8);
		}
		String ReqSeqNo = df20.format(currDate);//交易流水号(当前时间ms)
		String ReqDt = df8.format(currDate);//日期
		String ReqTm = df10.format(currDate);//时间
		try {
			sb_msg.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
			sb_msg.append("<TransBody>\n");
			StringBuffer sb_msgHeader = new StringBuffer();
			// 获取将要发送到核心的内容字段
			sb_msgHeader.append("    <RequestHeader>\n");
			sb_msgHeader.append("        <ReqSysCd>" + vo.getReqSysCd() + "</ReqSysCd>\n");
			sb_msgHeader.append("        <ReqSeqNo>" + ReqSeqNo + "</ReqSeqNo>\n");
			sb_msgHeader.append("        <ReqDt>" + ReqDt + "</ReqDt>\n");
			sb_msgHeader.append("        <ReqTm>" + ReqTm + "</ReqTm>\n");
			sb_msgHeader.append("        <DestSysCd>" + vo.getDestSysCd() + "</DestSysCd>\n");
			sb_msgHeader.append("        <ChnlNo>82</ChnlNo>\n");
			sb_msgHeader.append("        <BrchNo>" + auth.getUnitId() + "</BrchNo>\n");
			sb_msgHeader.append("        <BizLine>6491</BizLine>\n");
			sb_msgHeader.append("        <TrmNo>TRM10010</TrmNo>\n");
			sb_msgHeader.append("        <TrmIP>" + auth.getCurrentIP() + "</TrmIP>\n");
			sb_msgHeader.append("        <TlrNo>" + _userId + "</TlrNo>\n");
			sb_msgHeader.append("    </RequestHeader>\n");
			sb_msg.append(sb_msgHeader);
			StringBuffer sb_body = new StringBuffer();
			sb_body.append("    <RequestBody>\n");
			sb_body.append("        <txCode>"+vo.getTxCode()+"</txCode>\n");
			sb_body.append("        <txName>ECIF请求核心开户</txName>\n");
			sb_body.append("        <authType></authType>\n");
			sb_body.append("        <authCode>001</authCode>\n");
			sb_body.append("        <CUSTID>" + vo.getCUSTID() + "</CUSTID>\n");// ECIF客户号
			sb_body.append("        <TranType>" + vo.getTranType() + "</TranType>\n");// 数据类型
			sb_body.append("        <IDCODE>" + vo.getIDCODE() + "</IDCODE>\n");// 证件类型
			sb_body.append("        <BRAIDID>" + vo.getBRAIDID() + "</BRAIDID>\n");// 证件号码
			sb_body.append("        <REFID>" + vo.getREFID() + "</REFID>\n");// 台胞证/港澳证号码
			sb_body.append("        <CUFULNM>" + vo.getCUFULNM() + "</CUFULNM>\n");// 中文名
			sb_body.append("        <CUSNM>" + vo.getCUSNM() + "</CUSNM>\n");// 英文名
			sb_body.append("        <CORCOUN>" + vo.getCORCOUN() + "</CORCOUN>\n");// 国别
			sb_body.append("        <RISCOUN>" + vo.getRISCOUN() + "</RISCOUN>\n");// 风险国别
			sb_body.append("        <SEXIND>" + vo.getSEXIND() + "</SEXIND>\n");// 性别
			sb_body.append("        <ESTDATE>" + vo.getESTDATE() + "</ESTDATE>\n");// 出生日期
			sb_body.append("        <MAILAD1>" + vo.getMAILAD1() + "</MAILAD1>\n");// 地址1
			sb_body.append("        <MAILAD2>" + vo.getMAILAD2() + "</MAILAD2>\n");// 地址1
			sb_body.append("        <MAILAD3>" + vo.getMAILAD3() + "</MAILAD3>\n");// 地址1
			sb_body.append("        <CUSTEL1>" + vo.getCUSTEL1() + "</CUSTEL1>\n");// 电话1
			sb_body.append("        <CUSTEL2>" + vo.getCUSTEL2() + "</CUSTEL2>\n");// 电话1
			sb_body.append("        <CUSTEL3>" + vo.getCUSTEL3() + "</CUSTEL3>\n");// 电话1
			sb_body.append("        <EMAILAD>" + vo.getEMAILAD() + "</EMAILAD>\n");// 邮件地址
			sb_body.append("        <BRANCH>" + vo.getBRANCH() + "</BRANCH>\n");// 行号
			sb_body.append("        <ACCODE>" + vo.getCOCODE() + "</ACCODE>\n");// 客户经理代码
			sb_body.append("        <NEWOLD>" + vo.getNEWOLD() + "</NEWOLD>\n");// 新旧户标识
			sb_body.append("        <COCODE>" + vo.getACCODE() + "</COCODE>\n");// 新旧户标识
			sb_body.append("        <ACCATE>" + vo.getACCATE() + "</ACCATE>\n");// 账号属性
			sb_body.append("        <ONLYPUSHINFO>" + vo.isOnlyPushInfo() + "</ONLYPUSHINFO>\n");//是否只推送信息不开户
			sb_body.append("    </RequestBody>\n");
			sb_msg.append(sb_body);
			sb_msg.append("</TransBody>\n");

		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误："+e.getLocalizedMessage();
			log.error(logMsg);
			return null;
		}
		return sb_msg.toString();
	}
	
	
	/**
	 * 校验报文内容的字段()
	 * @param fileds			需要校验的字段内容
	 * @param requireds			是否是必输项
	 * @param filedLens			最大长度
	 * @return
	 */
	public boolean validMsgFiled(String[] fileds, boolean[] requireds, int[] filedLens){
		try {
			if(fileds == null || requireds == null || filedLens == null){
				System.err.println("参数不允许为空");
				return false;
			}
			if(fileds.length != requireds.length && fileds.length != filedLens.length
					&& requireds.length != filedLens.length){
				System.err.println("数组长度必须一致");
				return false;
			}
			for (int i = 0; i < fileds.length; i++) {
				String filed = fileds[i];
				if(requireds[i] && StringUtils.isEmpty(filed)){//如果必输但是为空
					System.err.println("fileds["+i+"]必输但是为空");
					return false;
				}else{
					if(filed != null){
						int requiredLen = filedLens[i];
						int contentLen = filed.getBytes("GBK").length;
						if(contentLen > requiredLen){//内容超长
							System.err.println("内容超长");
							return false;
						}
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.err.println("转换字节数组错误");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("系统错误");
			return false;
		}
		
		return true;
	}
}

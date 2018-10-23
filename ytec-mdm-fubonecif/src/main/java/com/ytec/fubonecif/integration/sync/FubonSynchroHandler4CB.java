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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.ytec.fubonecif.domain.MCiAgentinfo;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiBusiType;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCrossindex;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiGrade;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiInterbank;
import com.ytec.fubonecif.domain.MCiOrg;
import com.ytec.fubonecif.domain.MCiOrgExecutiveinfo;
import com.ytec.fubonecif.domain.MCiPerLinkman;
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

/**
 * @项目名称：ytec-mdm-fubonecif
 * @类名称：FubonSynchroHandler4CB
 * @类描述：富邦华一银行数据同步处理类，核心系统客户化
 * @功能描述:
 * @创建人：wangtb@yuchengtech.com
 * @创建时间：
 * @修改人：wangtb@yuchengtech.com
 * @修改时间：
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Component
@Scope("prototype")
public class FubonSynchroHandler4CB extends SynchroExecuteHandler {
	private static Logger log = LoggerFactory.getLogger(SynchroBroadcastHandler.class);
	private static String ECIFSYSCD = BusinessCfg.getString("appCd"); // ECIF在ESB中的编号
	// 操作数据库
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
			synchroRequestMsg = packageReqXml(custId);

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

	/**
	 * 组装响应报文
	 * 
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String packageReqXml(String custId) throws Exception {
		List list = null;
		try {
			list = getDataFromDb(custId);

			if (list == null) {
				String msg = String.format("查询客户(%s)信息失败，无法同步信息至核心系统", custId);
				log.error(msg);
				throw new Exception(msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}

		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("hhmmss");
		Document requestDoc = DocumentHelper.createDocument();
		requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);
		Element transBody = requestDoc.addElement("TransBody");

		Element requestHeader = transBody.addElement("RequestHeader");

		requestHeader.addElement("ReqSysCd").setText(ECIFSYSCD);// ReqSysCd 外围系统代号
		requestHeader.addElement("ReqSeqNo").setText("");// ReqSeqNo 外围系统交易流水号
		requestHeader.addElement("ReqDt").setText(df8.format(new Date()));// ReqDt 请求日期
		requestHeader.addElement("ReqTm").setText(df20.format(new Date()));// ReqTm 请求时间
		requestHeader.addElement("DestSysCd").setText("CB");// 目标系统代号
		requestHeader.addElement("ChnlNo").setText("");// ChnlNo 渠道号
		requestHeader.addElement("BrchNo").setText("");// BrchNo 机构号
		requestHeader.addElement("BizLine").setText("");// BizLine 业务条线
		requestHeader.addElement("TrmNo").setText("");// TrmNo 终端号
		requestHeader.addElement("TrmIP").setText("");// TrmIP 终端IP
		requestHeader.addElement("TlrNo").setText("");// TlrNo 操作柜员号

		Element databody = transBody.addElement("RequestBody");
		databody.addElement("txCode").setText("CMUC");

		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = (Map<String, String>) list.get(i);
			String coreNo = map.get("coreNo");
			databody.addElement("coreNo").setText(coreNo == null ? "" : coreNo);

			databody.addElement("identType").setText(map.get("identType") == null ? "" : map.get("identType"));
			databody.addElement("identNo").setText(map.get("identNo") == null ? "" : map.get("identNo"));
			databody.addElement("identValidPeriod").setText(map.get("identValidPeriod") == null ? "" : map.get("identValidPeriod")); // 核心系统证件有效期对应ECIF证件表中IdentCheckingDate

			String custName = map.get("custName");

			if (StringUtils.isEmpty(custName)) {
				String msg = String.format("ECIF数据错误，客户名称(%s)称无效", custName);
				log.error(msg);
				throw new Exception(msg);
			}

			Map<String, String> mapName = splitString(custName, "custName");
			if (mapName != null) {
				databody.addElement("custName").setText(mapName.get("0"));// 中文名1
				if (mapName.containsKey("1")) {
					databody.addElement("custName2").setText(mapName.get("1"));// 中文名2
				} else {
					databody.addElement("custName2");
				}
			}

			String enName = map.get("enName");
//			List booleanList = new ArrayList();
//			if(enName!=null && !"".equals(enName)){
//				char[] chr = enName.toCharArray();
//				for(int j=0;j<chr.length;j++){
//					boolean flag =returnSpecial(chr[j]);
//					booleanList.add(flag);
//				}
//			}
//			boolean enFlag = booleanList.contains(true);//判断是否存在双字节的
			//if(enFlag){//存在双字节
				Map<String,String> mapEnName = splitString(enName, "enName");;
				if (mapEnName != null) {
					   databody.addElement("enName").setText(mapEnName.get("0"));//英文名字
					if (mapEnName.containsKey("1")) {
						databody.addElement("enName2").setText(mapEnName.get("1"));// 英文名字2
					} else {
						databody.addElement("enName2");
					}
				} else {
					databody.addElement("enName");// 英文名字
					databody.addElement("enName2");// 英文名字
				}
//			}else{//不存在双字节
//				String enName1 = "";
//				String enName2 ="";
//				if(enName.getBytes().length>35){
//					enName1 = enName.substring(0, 35);
//					enName2 = enName.substring(35, enName.getBytes().length);
//				}else{
//					enName1 = enName.substring(0, enName.getBytes().length);
//				}
//				 databody.addElement("enName").setText(enName1);//英文名字
//				 databody.addElement("enName2").setText(enName2);// 英文名字
//			}
			
		
			databody.addElement("custType").setText(map.get("custType") == null ? "" : map.get("custType"));// 企业/个人标志
			databody.addElement("custGrade").setText(map.get("custGrade") == null ? "" : map.get("custGrade"));// 贵宾客户等级
			databody.addElement("nationCode").setText(map.get("nationCode") == null ? "" : map.get("nationCode"));// 所在国别
			String adminZone = map.get("adminZone");
			if (adminZone != null && !"".equals(adminZone)) {
				if (adminZone.equals("810000") || adminZone.equals("710000") || adminZone.equals("820000")) {
					databody.addElement("adminZone").setText(adminZone);// 只有港澳台才放入地区代码
				} else {
					databody.addElement("adminZone").setText("");// 地区代码
				}
			} else {
				databody.addElement("adminZone").setText("");// 地区代码
			}
			databody.addElement("inoutFlag").setText(map.get("inoutFlag") == null ? "" : map.get("inoutFlag"));// 境内/外标志
			databody.addElement("orgCustType").setText(map.get("orgCustType") == null ? "" : map.get("orgCustType"));// 企业类型
			databody.addElement("busiLicNo").setText(map.get("busiLicNo") == null ? "" :map.get("busiLicNo"));//营业执照
			databody.addElement("orgType").setText(map.get("orgType") == null ? "" : map.get("orgType"));// 合资类型
//			if("1".equals(map.get("custType"))){
//				databody.addElement("orgSubType").setText(map.get("orgSubType") == null ? "" : map.get("orgSubType"));// 自贸区类型
//				databody.addElement("ifOrgSubType").setText(map.get("ifOrgSubType") == null ? "" : map.get("ifOrgSubType"));// 是否自贸区标志-机构
//			}else{
//				databody.addElement("OrgSubType").setText(map.get("perOrgSubType") == null ? "" : map.get("perOrgSubType"));// 自贸区类型
//				databody.addElement("ifOrgSubType").setText(map.get("ifPerOrgSubType") == null ? "" : map.get("ifPerOrgSubType"));// 是否自贸区类型-个人
//			}
			databody.addElement("staffin").setText(map.get("staffin") == null ? "" : map.get("staffin"));//关联人属性
			//databody.addElement("inCllType").setText(map.get("inCllType") == null ? "" : map.get("inCllType"));//行业类别
			databody.addElement("orgSubType").setText(map.get("orgSubType") == null ? "" : map.get("orgSubType"));// 自贸区类型
			databody.addElement("ifOrgSubType").setText(map.get("ifOrgSubType") == null ? "" : map.get("ifOrgSubType"));// 是否自贸区类型
			databody.addElement("jointCustType").setText(map.get("jointCustType") == null ? "" : map.get("jointCustType"));// 联名户标志
			databody.addElement("custManagerNo").setText(map.get("custManagerNo") == null ? "" : map.get("custManagerNo"));// 客户经理代码
			databody.addElement("arCustFlag").setText(map.get("arCustFlag") == null ? "" : map.get("arCustFlag"));// 是否AR客户标志
			databody.addElement("swift").setText(map.get("swift") == null ? "" : map.get("swift"));// swift代码
			databody.addElement("riskNationCode").setText(map.get("riskNationCode") == null ? "" : map.get("riskNationCode"));// 国别风险国别代码
			String addr = map.get("addr");
			String zipCode = map.get("zipCode");
			Map<String, String> addrMap = new HashMap<String, String>();
			if (addr != null && !addr.toString().trim().equals("")) {
				addrMap = splitString(addr, "addr");
			}
			if (addrMap.containsKey("2")) {// 邮寄地址3 如果有值那么就将原先的地址与邮编合并在一起
				addr = addr+zipCode;
				addrMap = splitString(addr, "addr");
				databody.addElement("mailad1").setText(addrMap.get("0") == null ? "" : addrMap.get("0"));// 邮寄地址1
				if (addrMap.containsKey("1")) {// 邮寄地址2
					databody.addElement("mailad2").setText(addrMap.get("1"));
				} else {
					databody.addElement("mailad2");
				}
				databody.addElement("mailad3").setText(addrMap.get("2"));
			} else {//如果第三个字段不存值那么就发该地址对应的邮编
				databody.addElement("mailad1").setText(addrMap.get("0") == null ? "" : addrMap.get("0"));// 邮寄地址1
				if (addrMap.containsKey("1")) {// 邮寄地址2
					databody.addElement("mailad2").setText(addrMap.get("1"));
				} else {
					databody.addElement("mailad2").setText("");
				}
				if(zipCode!=null && !zipCode.toString().trim().equals("")){
					Map<String, String> ZipCodeMap = new HashMap<String, String>();
					ZipCodeMap = splitString(zipCode, "addr");
					databody.addElement("mailad3").setText(ZipCodeMap.get("0"));
				}else{
					databody.addElement("mailad3").setText("");
				}
			}
			databody.addElement("linkmanName").setText(map.get("linkmanName") == null ? "" : map.get("linkmanName"));// 联系人1
			databody.addElement("contmethInfo").setText(map.get("contmethInfo") == null ? "" : map.get("contmethInfo"));// 电话号码1

			String agentName = map.get("agentName");
			Map<String,String> agentMap = new HashMap<String, String>();
			if(agentName!=null && !agentName.toString().trim().equals("")){
				agentMap=splitString(agentName, "custName");
			}
			
			databody.addElement("agentName").setText(agentMap.get("0") == null ? "" : agentMap.get("0"));// 代理人户名
			databody.addElement("agentIdentNo").setText(map.get("agentIdentNo") == null ? "" : map.get("agentIdentNo"));// 代理人证件号码
			databody.addElement("agentIdentType").setText(map.get("agentIdentType") == null ? "" : map.get("agentIdentType"));// 代理人证件号码
			databody.addElement("agentNationCode").setText(map.get("agentNationCode") == null ? "" : map.get("agentNationCode"));// 代理人国别
		}

		String reqxml = XMLUtils.xmlToString(requestDoc);
		StringBuffer buf = new StringBuffer();
		buf.append(reqxml);
		return buf.toString();
	}

	@SuppressWarnings("rawtypes")
	public List getDataFromDb(String custId) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Object obj = null;
		MCiCustomer customer = new MCiCustomer();
		MCiPerson person = new MCiPerson();
		MCiBusiType busiType=new MCiBusiType();//行业类别
		MCiOrg org = new MCiOrg();
		MCiIdentifier ident = new MCiIdentifier();
		MCiAddress address = new MCiAddress();
		MCiGrade grade = new MCiGrade();
		MCiBelongManager manager = new MCiBelongManager();
		MCiAgentinfo agent = new MCiAgentinfo();
		MCiOrgExecutiveinfo execuInfo = new MCiOrgExecutiveinfo();
		MCiPerLinkman PerLinkMan = new MCiPerLinkman();
		MCiInterbank bank = new MCiInterbank();
		Map<String, String> map = new HashMap<String, String>();
		try {
			obj = returnEntiry(custId, "MCiCustomer");
			if (obj != null) {
				customer = (MCiCustomer) obj;
			} else if (customer.getCoreNo() != null) {
				String coreNo = customer.getCoreNo();
				Map<String, String> values = new HashMap<String, String>();
				values.put("custId", custId);
				values.put("srcSysNo", BusinessCfg.getString("cbCd"));
				MCiCrossindex crossIndex;
				try {
					crossIndex = (MCiCrossindex) baseDAO.findUniqueWithNameParam("from MCiCrossindex where custId=:custId and srcSysNo=:srcSysNo ", values);
					if (!coreNo.equals(crossIndex.getSrcSysCustNo())) {
						log.error("ECIF数据错误，");
						String msg = String.format("ECIF数据错误，客户表客户核心客户号(%s)与交叉索引表中核心客户号(%s)不一致", coreNo, crossIndex.getSrcSysCustNo());
						log.error(msg);
						throw new Exception(msg);
					}
				} catch (Exception e) {
					String msg = String.format("查询客户信息错误，通过ECIF客户号()查询交叉索引表核心客户号错误", custId);
					log.error(msg);
					log.error("{}", e);
					throw new Exception(msg + e.getLocalizedMessage());
				}
			} else {
				String msg = String.format("查询客户信息错误，通过ECIF客户号()查询客户表信息为空", custId);
				log.warn(msg);
				log.warn("客户同步信息已过期或系统数据错误");
				return null;
				// throw new Exception(msg);
			}
			map.put("coreNo", customer.getCoreNo());
			map.put("custName", customer.getCustName());
			map.put("enName", customer.getEnName());
			map.put("custType", customer.getCustType());
			map.put("inoutFlag", customer.getInoutFlag());
			map.put("arCustFlag", customer.getArCustFlag());
			map.put("custGrade", customer.getVipFlag());//VIPFLAG
			map.put("staffin", customer.getStaffin());
			map.put("riskNationCode", customer.getRiskNationCode());
			String custType = customer.getCustType();

			List<Object> identList = returnEntiryAsList(custId, "MCiIdentifier");

			for (Object objIdent : identList) {
				MCiIdentifier identTmp = (MCiIdentifier) objIdent;
				if (identTmp.getIsOpenAccIdent() != null && MdmConstants.IS_CB_OPEN_IDENT.equals(identTmp.getIsOpenAccIdent())) {
					ident = identTmp;
					break;// 如果有多个开户证件，则是因为数据问题，默认开户证件只有一个 TODO
				}
			}
			String identType = ident.getIdentType();//开户证件类型
			if(identType==null || "".equals(identType) || "99".equals(identType)){
				map.put("identType", "");
			}else{
				map.put("identType", ident.getIdentType());
			}
			map.put("identNo", ident.getIdentNo());//开户证件号码
			
			//String identType = ident.getIdentType();
			if(identType!=null && !"".equals(identType)){
				if("X1".equals(identType)){//判断开户证件类型对应的其他证件
					for (Object objIdent : identList) {
						MCiIdentifier identTmp = (MCiIdentifier) objIdent;
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("5")) {
							MCiIdentifier identity = identTmp;//局部对象，取证件号
							map.put("busiLicNo",identity.getIdentNo());
							//break;// 如果有多个开户证件，则是因为数据问题，默认开户证件只有一个 TODO
						}
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("5") && identTmp.getIdentExpiredDate()!=null) {
							ident = identTmp;
							break;// 如果有多个开户证件，则是因为数据问题，默认开户证件只有一个 TODO
						}
					}
				}else if("X2".equals(identType)){
					for (Object objIdent : identList) {
						MCiIdentifier identTmp = (MCiIdentifier) objIdent;
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("6")) {
							MCiIdentifier identity = identTmp;//局部对象，取证件号
							map.put("busiLicNo",identity.getIdentNo());
							//break;// 如果有多个开户证件，则是因为数据问题，默认开户证件只有一个 TODO
						}
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("6") && identTmp.getIdentExpiredDate()!=null) {
							ident = identTmp;
							break;// 如果有多个开户证件，则是因为数据问题，默认开户证件只有一个 TODO
						}
					}
				}else{
					 if (custType != null && !"".equals(custType) && "2".equals(custType)) {// 个人
						
						for(Object objIdent : identList){
							MCiIdentifier identTmp = (MCiIdentifier) objIdent;
							if (identTmp.getIdentType()!=null && (identTmp.getIdentType().equals("5") || identTmp.getIdentType().equals("6"))) {
								MCiIdentifier identity = identTmp;//局部对象，取证件号
								map.put("busiLicNo",identity.getIdentNo());//开户证件不是X1和X2,但有台胞证或港澳通行证
							}
						}
					}
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if (ident.getIdentExpiredDate() != null) {
				String identExpiredDate = sdf.format(ident.getIdentExpiredDate());
				map.put("identValidPeriod", identExpiredDate);
			} else {
				map.put("identValidPeriod", "");
			}
			// map.put("identValidPeriod", ident.getIdentExpiredDate() == null ? "" : ident.getIdentExpiredDate().toString()); // TODO: 核心系统证件有效期对应ECIF证件表中IdentExpiredDate
			
			Object objPerson = returnEntiry(custId, "MCiPerson");
			if (objPerson != null) {
				person = (MCiPerson) objPerson;
			}

			String perJoinCustType = (String) person.getJointCustType();
			if (perJoinCustType == null || "".equals(perJoinCustType.trim()) || "null".equals(perJoinCustType.toLowerCase().trim())) {
				perJoinCustType = "";
			}

			Object objOrg = returnEntiry(custId, "MCiOrg");
			if (objOrg != null) {
				org = (MCiOrg) objOrg;
			}

			String OrgJoinCustType = (String) org.getJointCustType();
			if (OrgJoinCustType == null || "".equals(OrgJoinCustType.trim()) || "null".equals(OrgJoinCustType.toLowerCase().trim())) {
				OrgJoinCustType = "";
			}

			log.info("联名户标志：" + "机构的是：" + OrgJoinCustType + "==个人的是：" + perJoinCustType);

			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
				map.put("jointCustType", OrgJoinCustType);
			} else if ("2".equals(custType)) {
				map.put("jointCustType", perJoinCustType);
			}

			// map.put("jointCustType", perJoinCustType+OrgJoinCustType);

			String orgNationCode = (String) org.getNationCode();
			if (orgNationCode == null || "".equals(orgNationCode.trim()) || "null".equals(orgNationCode.toLowerCase().trim())) {
				orgNationCode = "";
			}
			String perNationCode = (String) person.getCitizenship();
			if (perNationCode == null || "".equals(perNationCode.trim()) || "null".equals(perNationCode.toLowerCase().trim())) {
				perNationCode = "";
			}

			log.info("所在国别：" + "机构的是：" + orgNationCode + "==个人的是：" + perNationCode);
			// map.put("citizenship", person.getCitizenship() + "");
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
				map.put("nationCode", orgNationCode);
			} else if ("2".equals(custType)) {// 个人
				map.put("nationCode", perNationCode);
			}
			// map.put("nationCode", orgNationCode+perNationCode);
			map.put("orgCustType", org.getLncustp());
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
				
				map.put("busiLicNo", org.getBusiLicNo());
			}
			String orgType=org.getOrgType();
			if(orgType==null || "".equals(orgType)|| "99".equals(orgType)){
			    map.put("orgType", "");
			}else{
				map.put("orgType", org.getOrgType());
			}
			String orgSubType =(String) org.getOrgSubType();
			if(orgSubType==null || "".equals(orgSubType)|| "99".equals(orgSubType)){
			    //map.put("orgSubType", "");
				orgSubType="";
			}
			String perOrgSubType=(String)person.getOrgSubType();
			if(perOrgSubType==null || "".equals(perOrgSubType) || "99".equals(perOrgSubType)){
				//map.put("perOrgSubType", "");
				perOrgSubType="";
			}
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
				map.put("orgSubType", orgSubType);
			} else if ("2".equals(custType)) {// 个人
				map.put("orgSubType", perOrgSubType);
			}
			
			String ifOrgSubType=(String)org.getIfOrgSubType();
			if(ifOrgSubType==null || "".equals(ifOrgSubType)){
				//map.put(ifOrgSubType, "");
				ifOrgSubType="";
			}
			
//			String perOrgSubType=person.getOrgSubType();
//			if(perOrgSubType==null || "".equals(perOrgSubType) || "99".equals(perOrgSubType)){
//				map.put("perOrgSubType", "");
//			}else{
//				map.put("perOrgSubType", person.getOrgSubType());
//			}
			String ifPerOrgSubType=(String)person.getIfOrgSubType();
			if(ifPerOrgSubType==null || "".equals(ifPerOrgSubType)){
				//map.put(ifPerOrgSubType, "");
				ifPerOrgSubType="";
			}
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
				map.put("ifOrgSubType", ifOrgSubType);
			} else if ("2".equals(custType)) {// 个人
				map.put("ifOrgSubType", ifPerOrgSubType);
			}
			String inCllType=org.getInCllType();
			if(inCllType==null || "".equals(inCllType)){
				inCllType="";
			}
			boolean flag=true;
			if(custType != null && !"".equals(custType) && "1".equals(custType)){//机构
				
				Object objBusiType=returnBusiTypeEntiry(inCllType, "MCiBusiType");
				
				if(objBusiType != null){
					
					busiType=(MCiBusiType)objBusiType;
					while(flag){
					String fcode=busiType.getParentCode();
					if(fcode.equals("C")){
							String code=busiType.getFCode();
							if(code.equals("34") || code.equals("35") || code.equals("36") || code.equals("37") || code.equals("38")){
								map.put("inCllType", "5201");
								break;
							}
							if(code.equals("39") || code.equals("40")){
								map.put("inCllType", "5202");
								break;
							}
							if(code.equals("25") || code.equals("30") || code.equals("26") || code.equals("42") || code.equals("27")
									|| code.equals("28") || code.equals("15") || code.equals("32") || code.equals("31") || code.equals("33")){
								map.put("inCllType", "5203");
								break;
							}
							if(code.equals("20") || code.equals("29") || code.equals("13") || code.equals("17") || code.equals("16")
									|| code.equals("18") || code.equals("14") || code.equals("22") || code.equals("19")){
								map.put("inCllType", "5204");
								break;
							}
							if(code.equals("43") || code.equals("41") || code.equals("24") || code.equals("23") || code.equals("21")){
								map.put("inCllType", "5205");
								break;
							}
					}
					if(fcode.equals("U") || fcode.equals("V") || fcode.equals("Y") || fcode.equals("Z")){
							map.put("inCllType", "");
							break;
					}
					//上面五种行业之外的
					busiType=(MCiBusiType)returnBusiTypeEntiry(fcode, "MCiBusiType");
					if(busiType != null && busiType.getParentCode().equals("-1")){
						map.put("inCllType", fcode);
						flag=false;								
					}
					}
					//map.put("inCllType", busiType.getParentCode());	
				}else{
					map.put("inCllType", inCllType);
				}	
			}
			if(custType != null && !"".equals(custType) && "1".equals(custType)){//机构
			
			Object addrObject = queryAddress(custId, "addrType", "01", "MCiAddress");
			if (addrObject != null) {
				address = (MCiAddress) addrObject;
			}else {
				Object addrObject1 = queryAddress(custId, "addrType", "02", "MCiAddress");
				if(addrObject1!=null){
					address = (MCiAddress) addrObject1;
				}else{
					Object addrObj = queryAddress(custId, "addrType", "07", "MCiAddress");
					if (addrObj != null) {
						address = (MCiAddress) addrObj;
					}
				}
			}
				map.put("addr", address.getAddr());
				map.put("zipCode", address.getZipcode());
			}else if("2".equals(custType)){//个人
				Object addrObject = queryAddress(custId, "addrType", "01", "MCiAddress");
				if(addrObject != null){
					address = (MCiAddress) addrObject;
				}else {
					Object addrObject1 = queryAddress(custId, "addrType", "04", "MCiAddress");
					if(addrObject1 != null){
						address = (MCiAddress) addrObject1;
					}
				}
				map.put("addr", address.getAddr());
				map.put("zipCode", address.getZipcode());
			}
			String adminZone = person.getAreaCode();
			if (adminZone == null || "".equals(adminZone.trim()) || "null".equals(adminZone.toLowerCase().trim())) {
				adminZone = "";
			}
			String adminZone1 = org.getAreaCode();
			if (adminZone1 == null || "".equals(adminZone1.trim()) || "null".equals(adminZone1.toLowerCase().trim())) {
				adminZone1 = "";
			}
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
				map.put("adminZone", adminZone1);
			} else if ("2".equals(custType)) {// 个人
				map.put("adminZone", adminZone);
			}
			log.info("地区代码：" + "机构的是：" + adminZone1 + "==个人的是：" + adminZone);
			// map.put("adminZone", adminZone1+adminZone);

			Object agentObject = returnEntiry(custId, "MCiAgentinfo");
			if (agentObject != null) {
				agent = (MCiAgentinfo) agentObject;
			}

			map.put("agentName", agent.getAgentName());// 代理人户名
			String agentIdentType=agent.getIdentType();
			if(agentIdentType==null || "".equals(agentIdentType) || "99".equals(agentIdentType)){
			   map.put("agentIdentType", "");// 代理人证件类型
			}else{
			   map.put("agentIdentType", agent.getIdentType());// 代理人证件类型
			}
			map.put("agentIdentNo", agent.getIdentNo());// 代理人证件号码
			map.put("agentNationCode", agent.getAgentNationCode());// 代理人国别

			Object LinkObject = queryAddress1(custId, "linkmanType", "21", "MCiOrgExecutiveinfo");
			if (LinkObject != null) {
				execuInfo = (MCiOrgExecutiveinfo) LinkObject;
			}

			Object PerObject = queryAddress(custId, "linkmanType", "21", "MCiPerLinkman");
			if (PerObject != null) {
				PerLinkMan = (MCiPerLinkman) PerObject;
			}
			String perlinkmanName = execuInfo.getLinkmanName();
			if (perlinkmanName == null || "".equals(perlinkmanName.trim()) || "null".equals(perlinkmanName.toLowerCase().trim())) {
				perlinkmanName = "";
			}

			String orgLinkmanName = PerLinkMan.getLinkmanName();
			if (orgLinkmanName == null || "".equals(orgLinkmanName.trim()) || "null".equals(orgLinkmanName.toLowerCase().trim())) {
				orgLinkmanName = "";
			}

			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
//				map.put("linkmanName", orgLinkmanName);
				map.put("linkmanName", "");
			} else if ("2".equals(custType)) {// 个人
//				map.put("linkmanName", perlinkmanName);
				map.put("linkmanName", "");
			}

			log.info("联系人1：" + "机构的是：" + orgLinkmanName + "==个人的是：" + perlinkmanName);

			// map.put("linkmanName", orgLinkmanName+perlinkmanName);

			// String contmethInfo = (String)execuInfo.getHomeTel();
			
			/**
			 * 话说这是一个很蛋疼的需求导致的恶心的代码变更，后出生产事故，本人概不负责
			 */
			/*
			List<MCiContmeth> contmeth = baseDAO
					.findWithIndexParam("FROM MCiContmeth where custId=? and contmethType in (" + MdmConstants.SYNC_CONTTYPE_CB_IN + ") order by lastUpdateTm desc", custId);
			String contmethInfo = contmeth == null || contmeth.size() == 0 ? "" : contmeth.get(0).getContmethInfo();
			contmethInfo = contmethInfo == null || "".equals(contmethInfo) || "null".equals(contmethInfo.toLowerCase().trim()) ? "" : contmethInfo;
			log.info(String.format("custId: %s--->> contmethInfo:%s, contmeth.size:%d\n", custId, contmethInfo, contmeth.size()));
			*/
			List<MCiContmeth> contmethList=baseDAO.findWithIndexParam("FROM MCiContmeth where custId=? and contmethType in (" + MdmConstants.SYNC_CONTTYPE_CB_IN + ") order by contmethType,lastUpdateTm desc", custId);
			if(contmethList != null && contmethList.size()>0){	
				for(MCiContmeth contmeth:(List<MCiContmeth>)contmethList){
						if("102".equals(contmeth.getContmethType())){
							map.put("contmethInfo", contmeth.getContmethInfo());
							break;
						}else{
							map.put("contmethInfo", contmeth.getContmethInfo());
						}
					}
			}
			/*
			 * String contmenthInfo1 = (String) PerLinkMan.getMobile();
			 * if (contmenthInfo1 == null && "".equals(contmenthInfo1)) {
			 * contmenthInfo1 = (String) PerLinkMan.getMobile2();
			 * if (contmenthInfo1 == null && "".equals(contmenthInfo1)) {
			 * contmenthInfo1 = (String) PerLinkMan.getTel();
			 * if (contmenthInfo1 == null && "".equals(contmenthInfo1)) {
			 * contmenthInfo1 = (String) PerLinkMan.getTel2();
			 * }
			 * }
			 * }
			 * if (contmethInfo == null || "".equals(contmethInfo.trim()) || "null".equals(contmethInfo.toLowerCase().trim())) {
			 * contmethInfo = "";
			 * }
			 * if (contmenthInfo1 == null || "".equals(contmenthInfo1.trim()) || "null".equals(contmenthInfo1.toLowerCase().trim())) {
			 * contmenthInfo1 = "";
			 * }
			 */
		//	if (custType != null && !"".equals(custType) && "1".equals(custType)) {// 机构
				//map.put("contmethInfo", contmethInfo);
		//	//} else if ("2".equals(custType)) {// 个人
		//		map.put("contmethInfo", contmenthInfo1);
		//	}
			//log.info("电话号码1：" + "机构的是：" + contmethInfo + "==个人的是：" + contmethInfo);
			// map.put("contmethInfo", contmethInfo+contmenthInfo1);
			/**
			 * 恶心的代码结束
			 */

			Object GradeObject = queryAddress(custId, "custGradeType", "08", "MCiGrade");
			if (GradeObject != null) {
				grade = (MCiGrade) GradeObject;
			}
			//map.put("custGrade", grade.getCustGrade());
			

			Object bankObject = returnEntiry(custId, "MCiInterbank");
			if (bankObject != null) {
				bank = (MCiInterbank) bankObject;
			}
			map.put("swift", bank.getSwift());
			Object ManagerOrg = returnEntiry(custId, "MCiBelongManager");
			if (ManagerOrg != null) {
				manager = (MCiBelongManager) ManagerOrg;
			}
			map.put("custManagerNo", manager.getCustManagerNo());

			list.add(map);
		} catch (Exception e) {
			log.error("数据库操作失败：" + e.getMessage());
			log.error("错误信息：{}", e);
		}

		return list;

	}

	public Object queryAddress1(String custId, String str, String addrType, String simpleNames) throws Exception {

		try {
			// 类名
			String simpleName = simpleNames;
			// 获得表名
			String tableName = simpleName;
			// 拼装JQL查询语句
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// 查询表
			jql.append("FROM " + tableName + " a");
			// 查询条件
			jql.append(" WHERE 1=1");
			jql.append(" AND a.orgCustId =:orgCustId");
			jql.append(" AND a." + str + " =:" + str + "");
			// 将查询的条件放入到map集合里面
			paramMap.put("orgCustId", custId);
			paramMap.put(str, addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
		}
		return null;
	}

	public Object queryAddress(String custId, String str, String addrType, String simpleNames) throws Exception {

		try {
			// 类名
			String simpleName = simpleNames;
			// 获得表名
			String tableName = simpleName;
			// 拼装JQL查询语句
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// 查询表
			jql.append("FROM " + tableName + " a");
			// 查询条件
			jql.append(" WHERE 1=1");
			jql.append(" AND a.custId =:custId");
			jql.append(" AND a." + str + " =:" + str + "");
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			paramMap.put(str, addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
		}
		return null;
	}

	public Object returnBusiTypeEntiry(String fCode, String tableName) {
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.fCode =:fCode");
		// 将查询的条件放入到map集合里面
		paramMap.put("fCode", fCode);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

	}
	public Object returnEntiry(String custId, String tableName) {
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// 将查询的条件放入到map集合里面
		paramMap.put("custId", custId);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

	}

	public List<Object> returnEntiryAsList(String custId, String tableName) {
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// 将查询的条件放入到map集合里面
		paramMap.put("custId", custId);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null) { return result; }

		return null;
	}

	public  Map<String, String> splitString(String str, String type) throws Exception {
		if(str!=null && !"".equals(str.trim())){
			str = newString(str);
			int custNamecount = 50;
			int addrcount = 35;
			if (type.toString().equals("custName")) {
				return splitStr(str, custNamecount);
			} else if (type.toString().equals("addr")) { 
				return splitStr(str, addrcount); 
			}else if (type.toString().equals("enName")){
				return splitStr(str, addrcount);
			}
		}
		return null;
	}
	
	
	
	public  String newString(String str) throws Exception{
		if(str!=null && !"".equals(str.trim())){
			char[] charArray = str.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i <charArray.length; i++) {
				if (i == 0) {
					if(returnSpecial(charArray[i])) {
						sb.append(" " + charArray[i]); // 不是单字节的增加空格
					}else{
						sb.append(charArray[i]); // 是单字节的直接添加
					}
				}else if(i == (charArray.length-1)){//如果是最后一个字符了
					char bs = charArray[i - 1];
					if (returnSpecial(bs)) {// 前面那个不是单字节
						if(returnSpecial(charArray[i])) {//最后一个不是单字节
							sb.append(charArray[i]+" ");
						}else{//当前是单字节
							sb.append(" "+charArray[i]);
						}
					}else{//前面那个是单字节
						if(returnSpecial(charArray[i])) {//最后一个不是单字节
							sb.append(" "+charArray[i]+" ");
						}else{//当前是单字节
							sb.append(charArray[i]);
						}
					}
				}else {
					char bs = charArray[i - 1];
					char chr = charArray[i];
					if (returnSpecial(chr)) {// 当前不是单字节
						if(returnSpecial(bs)){//前面那个不是单字节
							sb.append(chr);
						}else{//是单字节
						    sb.append(" "+chr);
						}
					} else {// 当前是单字节
						if (returnSpecial(bs)) {// 前面那个不是单字节
							 sb.append(" "+chr);
						} else {//前面那个是单字节
							 sb.append(chr);
						}
					}
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	
	public  Map<String,String> splitStr(String str,int countType){
		char[] charArray = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int count = 0;
		Map<String, String> map = new HashMap<String, String>();
		String nowStr = "";
		for (int i = 0; i <charArray.length; i++) {
			if(sb.toString().getBytes().length==(countType-1)){
				char bs = charArray[i-1];
				char chr = charArray[i];
				if(returnSpecial(chr)){//如果不是单字节
					map.put("" + count, sb.toString());
					count++;
					nowStr=" "+chr;
					sb = new StringBuffer();
				}else{//当前是单字节
					if(returnSpecial(bs)){//前面不是单字节
						map.put("" + count, sb.toString());
						count++;
						nowStr=""+chr;
						sb = new StringBuffer();
					}else{//前面是单字节
						sb.append(chr);
						map.put("" + count, sb.toString());
						count++;
						sb = new StringBuffer();
					}
				}
			}else if(sb.toString().getBytes().length==countType){//当前长度已经是35了
				char bs = charArray[i-1];
				char chr = charArray[i];
				if(returnSpecial(bs)){//前面哪一个不是单字节
					int bsCount= sb.toString().lastIndexOf((bs+""));
					String strs = sb.substring(0, bsCount);
					map.put("" + count, strs);
					strs="";
					count++;
					nowStr=" "+bs+chr;
					sb = new StringBuffer();
				}else{//前面那个是单字节
					map.put("" + count, sb.toString());
					count++;
					if(returnSpecial(chr)){//当前的不是单字节
						nowStr=" "+chr;
					}else{//当前的是单字节
						nowStr=""+chr;
					}
					
					sb = new StringBuffer();
				}
			}else if (sb.toString().getBytes().length == 0) {
				char chr = charArray[i];
				String newChr = "";
				if("".equals(nowStr)){
					if(returnSpecial(charArray[i])){//当前不是单字节
						newChr=" "+chr;
					}else{//当前是单字节
						newChr=""+chr;
					}
				}else{
					newChr=""+chr;
				}
				sb.append(nowStr);
				nowStr="";
				sb.append(newChr);
				if(i==(charArray.length-1)){
					map.put("" + count, sb.toString());
					count++;
					sb = new StringBuffer();
				}
			}else if(i==(charArray.length-1)){
				sb.append(charArray[i]);
				map.put("" + count, sb.toString());
				count++;
				sb = new StringBuffer();
			}else{
				sb.append(charArray[i]);
			}
		}
		return map;
	}
	
	
	
//	/**
//	 * 将字符串分割
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public  Map<String, String> splitraddress(String str, int countType) throws Exception {
//		char[] charArray = str.toCharArray();
//		StringBuffer sb = new StringBuffer();
//		int count = 0;
//		Map<String, String> map = new HashMap<String, String>();
//		String nowStr = "";
//		for (int i = 0; i <charArray.length; i++) {
//			if (i == 0) {
//				if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// 检测第一个是否是汉子
//					sb.append(" " + charArray[i]); // 是汉字接添加一个空格
//				} else if(returnSpecial(charArray[i])) {
//					sb.append(" " + charArray[i]); // 是汉字接添加一个空格
//				}else{
//					sb.append(charArray[i]); // 不是汉子直接拼接
//				}
//			} else {
//				if ((sb.toString().getBytes().length) >= countType || i == (charArray.length-1)) {//判断长度是否超过规定长度或者是否是最后一个字符
//					
//					if (sb.toString().getBytes().length == 0 || i==(charArray.length-1)) {
//						sb.append(nowStr);
//						char bs = charArray[i-1];//前面一个数
//						if((bs >= 0x4e00) && (bs <= 0x9fbb)){//如果前面那个数是汉子
//							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子直接拼接
//								sb.append(charArray[i]+" ");
//							}else{
//								if(returnSpecial(charArray[i])){
//									sb.append(charArray[i]);
//								}else{
//								   sb.append(" "+charArray[i]);
//								}
//							}
//						}else{//前面那个不是汉子
//							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子
//								if(returnSpecial(bs)){
//									sb.append(charArray[i]);
//								}else{
//								    sb.append(" "+charArray[i]);
//								}
//							}else{//当前不是汉子
//								if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
//								    sb.append(" "+charArray[i]);
//								}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
//									 sb.append(" "+charArray[i]);
//								}else{
//								     sb.append(charArray[i]);
//								}
//							}
//						}
//						map.put("" + count, sb.toString());
//					}else{
//						char bs = charArray[i - 1];//前面一个数
//						if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// 检测最后一个是否是汉子
//							if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// 如果最后一个是汉子，判断前面一个是否是汉字是汉子增加空格
//								if ((sb.toString().getBytes().length) >= countType) {
//									sb.append(" "); // 是汉字接添加一个空格
//								} else {
//									sb.append(charArray[i] + " ");
//								}
//							} else {//最后一个不是汉子
//								if ((sb.toString().getBytes().length) <= countType && i == charArray.length) {
//									if(returnSpecial(bs)){
//										sb.append(charArray[i]);
//									}else{
//										sb.append(" "+charArray[i]);
//									}
//									
//								}
//								
//							}
//							nowStr = " " + charArray[i];
//							
//						} else {// 当前不是汉字，判断前面一个是否是汉字，如果前面是汉子不增加，如果前面不是汉字就增加
//							if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {
//								if(returnSpecial(charArray[i])){
//									sb.append(charArray[i]);
//								}else{
//								    sb.append(" "+charArray[i]);
//								}
//							} else {
//								if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
//								    sb.append(" "+charArray[i]);
//								}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
//									 sb.append(" "+charArray[i]);
//								}else{
//								     sb.append(charArray[i]);
//								}
//							}
//							nowStr = "";
//						}
//						map.put("" + count, sb.toString());
//						count++;
//						sb = new StringBuffer();
//						continue;
//					}
//					
//					
//				}
//				if (sb.toString().getBytes().length == 0) {
//					sb.append(nowStr);
//					nowStr="";
//					char bs = charArray[i-1];//前面一个数
//					if((bs >= 0x4e00) && (bs <= 0x9fbb)){//如果前面那个数是汉子
//						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子直接拼接
//							sb.append(charArray[i]);
//						}else{
//							if(returnSpecial(charArray[i])){
//							   sb.append(charArray[i]);
//							}else{
//							   sb.append(" "+charArray[i]);
//							}
//						}
//					}else{
//						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//如果当前是汉子
//							if(returnSpecial(bs) && sb.toString().getBytes().length >0){
//								sb.append(charArray[i]);
//							}else{
//							    sb.append(" "+charArray[i]);
//							}
//						}else{
//							if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
//							    sb.append(" "+charArray[i]);
//							}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
//								 sb.append(" "+charArray[i]);
//							}else if(returnSpecial(charArray[i]) && sb.toString().getBytes().length == 0){
//								 sb.append(" "+charArray[i]);
//							}else{
//								 sb.append(charArray[i]);
//							}
//							
//						}
//					}
//					
//				} else {
//					char bs = charArray[i - 1];
//					char chr = charArray[i];
//					if ((chr >= 0x4e00) && (chr <= 0x9fbb)) {// 当前这个字是汉字
//						if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// 前面那个是否是汉字
//							sb.append(chr);
//						} else {
//							if(returnSpecial(bs)){
//								sb.append(chr);
//							}else{
//							    sb.append(" "+chr);
//							}
//						}
//
//					} else {// 当前拿到的不是汉子
//						if (((bs >= 0x4e00) && (bs <= 0x9fbb))) {// 如果他前面的是汉子
//							if(returnSpecial(chr)){
//								sb.append(chr);
//							}else{
//							    sb.append(" "+chr);
//							}
//						} else {//前面不是汉子
//							if(returnSpecial(bs) && !returnSpecial(chr)){
//								sb.append(" "+chr);
//							}else if(!returnSpecial(bs) && returnSpecial(chr)){
//								sb.append(" "+chr);
//							}else{
//							    sb.append(chr);
//							}
//						}
//					}
//				}
//			}
//		}	
//		return map;
//	}

	
	public  boolean returnSpecial(char chr){
		
		boolean flag = false;
		String s =chr+"";
		if(s.getBytes().length==1){
			flag=false;
		}else{
			flag=true;
		}
//		char[] chrs = {'（','）','【','】','，','。','？','；','、','《','》','：','￥','　','［','］','．','＞','＜','！','＾','＃','＄','％','＆','＊','＿','＝','＋','｝','｛','｜','＼','＇','，','．','｀','～','‘','’'};
//		for(int i =0;i<chrs.length;i++){
//			if(chr==chrs[i]){
//				flag=true;
//			}
//		}
		return flag;
	}
	// TODO 改报文格式
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
					String msg = String.format("{},收到外围系统[%s]响应报文必要节点为空[需要节点：%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("cbCd"), txStatCodeXpath,
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
				String txStatDetail = "外围系统[" + BusinessCfg.getString("cbCd") + "]响应:txStatDesc:{" + txStatDesc + "},txStatDetail:{" + txStatDetailNode.getText() + "}";

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
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("cbCd"), e);
				return false;
			} catch (Exception e) {
				log.error("收到外围系统[{}]报文做下一步处理时出错,错误信息:\n{}", BusinessCfg.getString("cbCd"), e);
				return false;
			}
		} else {
			// TODO
			log.info("同步响应报文为空");
			return false;
		}
	}
}

package com.yuchengtech.trans.client;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;

import com.yuchengtech.bcrm.customer.service.CustomerTransService;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bcrm.util.SpringContextUtils;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.ecif.EcifTransaction;
import com.yuchengtech.trans.impl.queryCustInfo.CBAccountQueryTransaction;
import com.yuchengtech.trans.inf.Transaction;
import com.yuchengtech.trans.socket.NioClient;

public class TransClient {
	private static Logger log = Logger.getLogger(TransClient.class);
	
	static CommonService commonService =  (CommonService)SpringContextUtils.getBean(CustomerTransService.class);
	private static boolean isInited = false;
	// private static Map<String, String> txXmlMap = new HashMap<String,
	// String>();
	private static Map<String, String> txSysMap = new HashMap<String, String>();
	private static Map<String, String> sysAddrIpMap = new HashMap<String, String>();
	private static Map<String, String> sysAddrPortMap = new HashMap<String, String>();
	private static Map<String, String> nodeEntityMap = new HashMap<String, String>();

	private static String[] requestBodyClasses = new String[] {
			"com.yuchengtech.trans.bo.ecif.RequestBody4UpdateBelong",
			"com.yuchengtech.trans.bo.ecif.RequestBody4UpdateOrgCust",
			"com.yuchengtech.trans.bo.ecif.RequestBody4UpdatePerCust",
			"com.yuchengtech.trans.bo.ecif.RequestBody4UpdateSpecialList",
			"com.yuchengtech.trans.bo.ecif.RequestBody4UpdateSubCust",
			"com.yuchengtech.trans.bo.cb.QueryRequestBody2CB" };
	private static String[] entityClasses = new String[] { "customer",
			"address", "identifier", "contmeth", "person", "org", "belongOrg",
			"belongManager" };
	private static String[] entityClassesName = new String[] {
			"com.yuchengtech.bcrm.customer.model.AcrmFCiAddress",
			"com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier",
			"com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth",
			"com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer",
			"com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson",
			"com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiOrg",
			"com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr",
			"com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg",
			"com.yuchengtech.trans.bo.test.Bo",
			"com.yuchengtech.trans.bo.test.Arr",
			"com.yuchengtech.trans.bo.test.ReqBody" };

	private static void init() {
		if (isInited) {
			return;
		}

		nodeEntityMap
				.put("customer",
						"com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer");
		nodeEntityMap.put("address",
				"com.yuchengtech.bcrm.customer.model.AcrmFCiAddress");
		nodeEntityMap.put("identifier",
				"com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier");
		nodeEntityMap.put("contmeth",
				"com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth");
		nodeEntityMap
				.put("person",
						"com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson");
		nodeEntityMap.put("org",
				"com.yuchengtech.bcrm.customer.model.AcrmFCiOrg");

		txSysMap.put("CMSD", "CB");
		txSysMap.put("CMSN", "CB");
		txSysMap.put("CMST", "CB");
		txSysMap.put("CMCD", "CB");
		txSysMap.put("CMCN", "CB");
		txSysMap.put("CMCT", "CB");
		txSysMap.put("CMFR", "CB");

		String ecifport = FileTypeConstance.getBipProperty("ECIF.PORT");
		String ecifip = FileTypeConstance.getBipProperty("ECIF.IP");
		String cbsport = FileTypeConstance.getBipProperty("CBS.PORT");
		String cbsip = FileTypeConstance.getBipProperty("CBS.IP");
		String lnsip = FileTypeConstance.getBipProperty("LN.IP");
		String lnport = FileTypeConstance.getBipProperty("LN.PORT");
		//add by liuming 20170522
		String loanip = FileTypeConstance.getBipProperty("LOAN.IP");
		String loanport = FileTypeConstance.getBipProperty("LOAN.PORT");
		
		sysAddrIpMap.put("ECIF", ecifip);
		sysAddrPortMap.put("ECIF", ecifport);
		sysAddrIpMap.put("CB", cbsip);
		sysAddrPortMap.put("CB", cbsport);
		sysAddrIpMap.put("LNIP", lnsip);
		sysAddrPortMap.put("LNPORT", lnport);
		//add by liuming 20170522
		sysAddrIpMap.put("LOANIP", loanip);
		sysAddrPortMap.put("LOANPORT", loanport);
		isInited = true;
	}

	// public static String reqBodyXml(List<Map<Object,Object>> list) throws
	// Exception{
	public static String reqBodyXml(List list) throws Exception {
		StringBuffer sb = new StringBuffer();
		// 首先将拿出来的封装成一个Map
		Map<Object, List<Map<Object, Object>>> parmMap = new HashMap<Object, List<Map<Object, Object>>>();
		List<Map<Object, Object>> lists = new ArrayList<Map<Object, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<Object, Object> columnMap = new HashMap<Object, Object>();
			Map<Object, Object> map = (Map<Object, Object>) list.get(i);
			Object tableName = map.get("tableName");
			if (checkTable(tableName.toString())) {
				if (parmMap.containsKey(tableName)) {
					columnMap.put(map.get("colunmn"), map.get("content"));
					lists.add(columnMap);
					parmMap.put(tableName, lists);
				} else {
					lists = new ArrayList<Map<Object, Object>>();
					columnMap.put(map.get("colunmn"), map.get("content"));
					lists.add(columnMap);
					parmMap.put(tableName, lists);
				}
			} else {
				continue;
			}
		}
		// 在对里面的Map做具体的处理
		Set<Object> set = parmMap.keySet();
		Iterator<Object> it = set.iterator();
		while (it.hasNext()) {
			Object tableName = it.next();
			List<Map<Object, Object>> bodyList = parmMap.get(tableName);
			sb.append("<" + tableName + ">");
			for (int i = 0; i < bodyList.size(); i++) {
				Map<Object, Object> bodyMap = bodyList.get(i);
				Set<Object> bodyset = bodyMap.keySet();
				Iterator<Object> bodyit = bodyset.iterator();
				while (bodyit.hasNext()) {
					Object column = bodyit.next();
					Object content = bodyMap.get(column);
					sb.append("<" + column + ">" + content + "</" + column
							+ ">");
				}
			}
			sb.append("</" + tableName + ">");
		}
		return new String(sb.toString().getBytes(), "GBK");
	}

	public static boolean checkTable(String tableName) {
		String[] str = { "ACRM_F_CI_ADDRESS", "ACRM_F_CI_CUSTIDENTIFIER",
				"ACRM_F_CI_CONTMETH", "ACRM_F_CI_CUSTOMER", "ACRM_F_CI_PERSON",
				"ACRM_F_CI_ORG", "OCRM_F_CI_BELONGCUSTMGR",
				"OCRM_F_CI_BELONGORG", "ACRM_F_CI_SPECIALLIST",
				"ACRM_F_CI_PER_KEYFLAG" };
		for (String strs : str) {
			if (tableName != null && strs.equals(tableName.trim())) {
				return true;
			}
		}
		return false;

	}

	public static String parseObject2Xml(Object... objs) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?><TransBody>");
		for (Object outerObj : objs) {
			if (outerObj instanceof RequestHeader) {
				sb.append("<RequestHeader>");
				Field[] fileds = outerObj.getClass().getDeclaredFields();
				for (Field field : fileds) {
					String fieldName = field.getName();
					sb.append("<" + fieldName + ">");
					String methodName = "get"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					sb.append(MethodUtils.invokeMethod(outerObj, methodName,
							null));
					sb.append("</" + fieldName + ">"); // BeanUtils.getMappedProperty(customer,
														// "custId", "111");
				}
				sb.append("</RequestHeader>");
				// } else if
				// (Arrays.asList(entityClassesName).contains(obj.getClass().getName()))
				// {// (obj instanceof RequestBody4UpdatePerCust ||
				// ReqBody.class.getName().equals(obj.getClass().getName())) {
			} else if (Arrays.asList(requestBodyClasses).contains(
					outerObj.getClass().getName())) {// (obj instanceof
														// RequestBody4UpdatePerCust
														// ||
														// ReqBody.class.getName().equals(obj.getClass().getName()))
														// {
				sb.append("<RequestBody>");
				Field[] fileds = outerObj.getClass().getDeclaredFields();
				for (Field field : fileds) {
					String fieldName = field.getName();
					if (Arrays.asList(entityClasses).contains(fieldName)) {
						sb.append("<" + fieldName + ">");
						String methodName = "get"
								+ fieldName.substring(0, 1).toUpperCase()
								+ fieldName.substring(1);
						Object innerObj = MethodUtils.invokeMethod(outerObj,
								methodName, null);
						if (innerObj == null) {
							sb.append("</" + fieldName + ">");
							continue;
						}
						Field[] innerFileds = innerObj.getClass()
								.getDeclaredFields();
						for (Field innerField : innerFileds) {
							if (innerField.getName().equals("serialVersionUID")) {
								continue;
							}
							String innerFieldName = innerField.getName();
							sb.append("<" + innerFieldName + ">");
							String innerMethodName = "get"
									+ innerFieldName.substring(0, 1)
											.toUpperCase()
									+ innerFieldName.substring(1);
							Object value = MethodUtils.invokeMethod(innerObj,
									innerMethodName, null);
							sb.append(value == null ? "" : value);
							sb.append("</" + innerFieldName + ">");
						}
						sb.append("</" + fieldName + ">");
					} else {
						sb.append("<" + fieldName + ">");
						String methodName = "get"
								+ fieldName.substring(0, 1).toUpperCase()
								+ fieldName.substring(1);
						Object other = MethodUtils.invokeMethod(outerObj,
								methodName, null);
						if (other != null)
							sb.append(other);
						sb.append("</" + fieldName + ">");
					}
				}
				sb.append("</RequestBody>");
			} else if (outerObj instanceof List) {
				sb.append("<RequestBody>");
				sb.append(TransClient.reqBodyXml((List) outerObj));
				sb.append("</RequestBody>");
			} else if (outerObj instanceof String) {
				sb.append(outerObj);
			}

		}
		sb.append("</TransBody>");
		return sb.toString();
	}

	public static String process(RequestHeader header, Object body)throws Exception {
//		TxData txData = new TxData();
		if (header == null) {
			throw new Exception("请求报文头RequestHeader不可为空");
		}
		TransClient.init();
		String msg = TransClient.parseObject2Xml(header, body);
		msg = String.format("%08d", msg.getBytes("GBK").length) + msg;
		
//		txData.setReqMsg(msg);//添加请求报文
		log.info("SEND_MSG: \n" + msg);
		String ip = sysAddrIpMap.get(header.getDestSysCd());
		int port = Integer.parseInt(sysAddrPortMap.get(header.getDestSysCd()));

		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(msg);
//			txData.setResMsg(resp);//添加返回报文
//			txData = tran.analysisResMsg(txData);
//			TxLog txLog = tran.getTxLog(txData);
//			this.baseDAO.save(txLog);
		} catch (IOException e) {
			log.info(String.format("destSysNo:%s, ip:%s, port:%d\n",
					header.getDestSysCd(), ip, port));
			e.printStackTrace();
		}
		log.info("RESP_MSG: \n" + resp);

		return resp;
	}
	
	/**
	 * 买售商组装请求报文
	 * 
	 * @param mobile
	 * @param message
	 * @param sendTime
	 * @return
	 * @throws Exception
	 */
	public static String GroupMsgXml(String grpno) throws Exception {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		sb.append("<Packet>\n");
		sb.append("   <Data>\n");
		sb.append("    <Req>\n");
		sb.append("      <grp_no>" + grpno + "</grp_no>\n");
		sb.append("    </Req>\n");
		sb.append("      <Pub>\n");
		sb.append("       <prcscd>queryTradeBusyInfo</prcscd>\n");
		sb.append("      </Pub>\n");
		sb.append("   </Data>\n");
		sb.append("</Packet>\n");

		StringBuffer sbReq = new StringBuffer();
		sbReq.append(String
				.format("%04d", sb.toString().getBytes("GBK").length));
		sbReq.append(sb.toString());
		return sbReq.toString();
	}

	public static String process1(String grpno) throws Exception {
		TransClient.init();
		String msg = GroupMsgXml(grpno);
		log.info("SEND_MSG: \n" + msg);

		String ip = (String)sysAddrIpMap.get("LNIP");
		int port = Integer.parseInt((String)sysAddrPortMap.get("LNPORT"));
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("RESP_MSG: \n" + resp);

		return resp;
	}

	/**
	 * crm同步客户至信贷请求报文测试
	 * 
	 * @param mobile
	 * @param message
	 * @param sendTime
	 * @return
	 * @throws Exception
	 */
	public static String syncCustInfoToLNMsgXml() throws Exception {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
		sb.append("<TransBody>\n");
		sb.append("  <RequestHeader>\n");
		sb.append("      <DestSysCd>LN</DestSysCd>\n");
		sb.append("  </RequestHeader>\n");
		sb.append("  <RequestBody>\n");
		sb.append("      <Packet>\n");
		sb.append("         <Data>\n");
		sb.append("           <Req>\n");
		sb.append("              <Org>\n");
		sb.append("                 <annualIncome/>\n");
		sb.append("                 <areaCode>900000</areaCode>\n");
		sb.append("                 <buildDate>2006-09-11</buildDate>\n");
		sb.append("                 <comHoldType>201</comHoldType>\n");
		sb.append("                 <comSpBusiness>2</comSpBusiness>\n");
		sb.append("                 <comSpDetail></comSpDetail>\n");
		sb.append("                 <comSpEndDate></comSpEndDate>\n");
		sb.append("                 <comSpLicNo></comSpLicNo>\n");
		sb.append("                 <comSpLicOrg></comSpLicOrg>\n");
		sb.append("                 <comSpStrDate></comSpStrDate>\n");
		sb.append("                 <employeeScale></employeeScale>\n");
		sb.append("                 <entBelong></entBelong>\n");
		sb.append("                 <entProperty>800</entProperty>\n");
		sb.append("                 <entScale>CS04</entScale>\n");
		sb.append("                 <entScaleRh>CS04</entScaleRh>\n");
		sb.append("                 <finRepType>PB0001</finRepType>\n");
		sb.append("                 <fundSource></fundSource>\n");
		sb.append("                 <holdStockAmt></holdStockAmt>\n");
		sb.append("                 <inCllType></inCllType>\n");
		sb.append("                 <industryCategory>S005</industryCategory>\n");
		sb.append("                 <investType>2</investType>\n");
		sb.append("                 <legalReprGender></legalReprGender>\n");
		sb.append("                 <legalReprIdentNo>M221201206</legalReprIdentNo>\n");
		sb.append("                 <legalReprIdentType>X2</legalReprIdentType>\n");
		sb.append("                 <legalReprName>彭琦娟</legalReprName>\n");
		sb.append("                 <legalReprTel></legalReprTel>\n");
		sb.append("                 <loadCardAuditDt></loadCardAuditDt>\n");
		sb.append("                 <loadCardPwd></loadCardPwd>\n");
		sb.append("                 <loanCardFlag>2</loanCardFlag>\n");
		sb.append("                 <loanCardNo></loanCardNo>\n");
		sb.append("                 <loanCardStat></loanCardStat>\n");
		sb.append("                 <mainBusiness>批发零售家具</mainBusiness>\n");
		sb.append("                 <mainIndustry>5283</mainIndustry>\n");
		sb.append("                 <minorBusiness></minorBusiness>\n");
		sb.append("                 <nationCode>WSM</nationCode>\n");
		sb.append("                 <orgCustType>003</orgCustType>\n");
		sb.append("                 <orgFex></orgFex>\n");
		sb.append("                 <orgHomepage></orgHomepage>\n");
		sb.append("                 <orgTel>1391601601</orgTel>\n");
		sb.append("                 <orgZipcode>201105</orgZipcode>\n");
		sb.append("                 <superDept>502</superDept>\n");
		sb.append("                 <topCorpLevel></topCorpLevel>\n");
		sb.append("                 <totalAssets></totalAssets>\n");
		// sb.append("                 <creditCode></creditCode>\n");
		// sb.append("                 <loanOrgType></loanOrgType>\n");
		// sb.append("                 <flagCapDtl></flagCapDtl>\n");
		// sb.append("                 <yearRate></yearRate>\n");
		// sb.append("                 <orgState></orgState>\n");
		// sb.append("                 <basCusState></basCusState>\n");
		// sb.append("                 <accOpenLicense></accOpenLicense>\n");
		sb.append("              </Org>\n");
		sb.append("              <Address>\n");
		sb.append("                 <legalReprAddr></legalReprAddr>\n");
		sb.append("                 <actBusiAddr></actBusiAddr>\n");
		sb.append("                 <postAddr>闵行区沪星</postAddr>\n");
		sb.append("                 <registerAddr>闵行区</registerAddr>\n");
		sb.append("                 <registerEnAddr></registerEnAddr>\n");
		sb.append("              </Address>\n");
		sb.append("              <Customer>\n");
		sb.append("                 <custId>110000103736</custId>\n");
		sb.append("                 <arCustFlag>2</arCustFlag>\n");
		sb.append("                 <arCustType></arCustType>\n");
		sb.append("                 <createBranchNo>502</createBranchNo>\n");
		sb.append("                 <createDate>2006-10-17</createDate>\n");
		sb.append("                 <createTellerNo></createTellerNo>\n");
		sb.append("                 <cusBankRel>B1</cusBankRel>\n");
		sb.append("                 <cusCorpRel></cusCorpRel>\n");
		sb.append("                 <custName>SENDERSON INDUSTRIAL CO., LTD.</custName>\n");
		sb.append("                 <enName>SENDERSON INDUSTRIAL CO., LTD.</enName>\n");
		sb.append("                 <inoutFlag>F</inoutFlag>\n");
		sb.append("                 <loanCustMgr></loanCustMgr>\n");
		sb.append("                 <loanCustStat>20</loanCustStat>\n");
		sb.append("                 <loanMainBrId>502</loanMainBrId>\n");
		sb.append("                 <shortName>SENDERSON INDUSTRIAL CO., LTD.</shortName>\n");
		// sb.append("                 <coreNo>501000000052</coreNo>\n");
		sb.append("              </Customer>\n");
		sb.append("              <Identifier>\n");
		sb.append("                 <fexcIdentNo></fexcIdentNo>\n");
		sb.append("                 <certType>2X</certType>\n");
		sb.append("                 <certCode>635193395</certCode>\n");
		sb.append("                 <nocIdenRegDate></nocIdenRegDate>\n");
		sb.append("                 <nocIdentCheckingDate></nocIdentCheckingDate>\n");
		sb.append("                 <nocIdentEffectiveDate></nocIdentEffectiveDate>\n");
		sb.append("                 <nocIdentNo>44556646-4</nocIdentNo>\n");
		sb.append("                 <nocIdentOrg></nocIdentOrg>\n");
		sb.append("                 <natTaxIdenRegDate></natTaxIdenRegDate>\n");
		sb.append("                 <natTaxIdentCheckingDate></natTaxIdentCheckingDate>\n");
		sb.append("                 <natTaxIdentExpiredDate></natTaxIdentExpiredDate>\n");
		sb.append("                 <natTaxIdentNo>4455664654</natTaxIdentNo>\n");
		sb.append("                 <natTaxIdentOrg></natTaxIdentOrg>\n");
		sb.append("                 <locTaxIdenRegDate></locTaxIdenRegDate>\n");
		sb.append("                 <locTaxIdentCheckingDate></locTaxIdentCheckingDate>\n");
		sb.append("                 <locTaxIdentExpiredDate></locTaxIdentExpiredDate>\n");
		sb.append("                 <locTaxIdentNo>4455664654</locTaxIdentNo>\n");
		sb.append("                 <locTaxIdentOrg></locTaxIdentOrg>\n");
		sb.append("              </Identifier>\n");
		sb.append("              <Grade>\n");
		sb.append("                 <creditCustGrade></creditCustGrade>\n");
		sb.append("                 <creditEvaluateDate></creditEvaluateDate>\n");
		sb.append("                 <creditExpiredDate></creditExpiredDate>\n");
		sb.append("                 <orgName></orgName>\n");
		sb.append("                 <outCreditCustGrade></outCreditCustGrade>\n");
		sb.append("                 <outCreditEvaluateDate></outCreditEvaluateDate>\n");
		sb.append("              </Grade>\n");
		sb.append("              <GroupInfo>\n");
		sb.append("                 <groupNo></groupNo>\n");
		sb.append("                 <groupType></groupType>\n");
		sb.append("              </GroupInfo>\n");
		sb.append("              <OrgBusiinfo>\n");
		sb.append("                 <mainProduct></mainProduct>\n");
		sb.append("                 <manageStat>100</manageStat>\n");
		sb.append("                 <workFieldArea></workFieldArea>\n");
		sb.append("                 <workFieldOwnership>1</workFieldOwnership>\n");
		sb.append("              </OrgBusiinfo>\n");
		sb.append("              <OrgExecutiveinfo>\n");
		sb.append("                 <actCtrlIdentNo></actCtrlIdentNo>\n");
		sb.append("                 <actCtrlIdentType></actCtrlIdentType>\n");
		sb.append("                 <actCtrlIndivCusId></actCtrlIndivCusId>\n");
		sb.append("                 <actCtrlLinkmanName></actCtrlLinkmanName>\n");
		sb.append("                 <actCtrlMateIdentNo></actCtrlMateIdentNo>\n");
		sb.append("                 <actCtrlMateIdentType></actCtrlMateIdentType>\n");
		sb.append("                 <actCtrlMateIndivCusId></actCtrlMateIndivCusId>\n");
		sb.append("                 <actCtrlMateLinkmanName></actCtrlMateLinkmanName>\n");
		sb.append("                 <linkMateIdentNo></linkMateIdentNo>\n");
		sb.append("                 <linkMateIdentType></linkMateIdentType>\n");
		sb.append("                 <linkMateIndivCusId></linkMateIndivCusId>\n");
		sb.append("                 <linkMateLinkmanName></linkMateLinkmanName>\n");
		sb.append("                 <finaLinkmanName>彭琦娟</finaLinkmanName>\n");
		sb.append("                 <opLinkmanName></opLinkmanName>\n");
		sb.append("              </OrgExecutiveinfo>\n");
		sb.append("              <OrgIssuestock>\n");
		sb.append("                 <marketPlace></marketPlace>\n");
		sb.append("                 <stockCode></stockCode>\n");
		sb.append("              </OrgIssuestock>\n");
		sb.append("              <OrgKeyflag>\n");
		sb.append("                 <hasIeRight>1</hasIeRight>\n");
		sb.append("                 <isAreaImpEnt></isAreaImpEnt>\n");
		sb.append("                 <isListedCorp>2</isListedCorp>\n");
		sb.append("                 <isNewCorp>2</isNewCorp>\n");
		sb.append("                 <isNotLocalEnt>1</isNotLocalEnt>\n");
		sb.append("                 <isNtnalMacroCtrl>2</isNtnalMacroCtrl>\n");
		sb.append("                 <isPrepEnt></isPrepEnt>\n");
		sb.append("                 <isRuralCorp></isRuralCorp>\n");
		sb.append("                 <isSteelEnt>2</isSteelEnt>\n");
		sb.append("                 <isTwoHighEnt>2</isTwoHighEnt>\n");
		// sb.append("                 <shippingInd></shippingInd>\n");
		// sb.append("                 <comCityFlg></comCityFlg>\n");
		sb.append("              </OrgKeyflag>\n");
		sb.append("              <OrgRegisterinfo>\n");
		sb.append("                 <apprDocNo></apprDocNo>\n");
		sb.append("                 <apprOrg></apprOrg>\n");
		sb.append("                 <auditCon></auditCon>\n");
		sb.append("                 <auditDate></auditDate>\n");
		sb.append("                 <auditEndDate></auditEndDate>\n");
		sb.append("                 <endDate>9999-09-09</endDate>\n");
		sb.append("                 <factCapital></factCapital>\n");
		sb.append("                 <factCapitalCurr></factCapitalCurr>\n");
		sb.append("                 <registerCapital>0.0</registerCapital>\n");
		sb.append("                 <registerCapitalCurr>840</registerCapitalCurr>\n");
		sb.append("                 <registerDate>2006-09-11</registerDate>\n");
		sb.append("                 <registerNo>635193395</registerNo>\n");
		sb.append("                 <registerType>580</registerType>\n");
		sb.append("                 <registerZipcode>201101</registerZipcode>\n");
		sb.append("                 <regOrg></regOrg>\n");
		// sb.append("                 <regCodeType></regCodeType>\n");
		sb.append("              </OrgRegisterinfo>\n");
		sb.append("           </Req>\n");
		sb.append("           <Pub>\n");
		sb.append("               <prcscd>receiveCusComInfo</prcscd>\n");
		sb.append("           </Pub>\n");
		sb.append("         </Data>\n");
		sb.append("     </Packet>\n");
		sb.append("   </RequestBody>\n");
		sb.append("</TransBody>\n");

		StringBuffer sbReq = new StringBuffer();
		sbReq.append(String
				.format("%08d", sb.toString().getBytes("GBK").length));
		sbReq.append(sb.toString());
		return sbReq.toString();
	}

	/**
	 * add by liuming
	 * 20170522
	 * @throws
	 */
	public static String process2() throws Exception {
		TransClient.init();
		String msg = syncCustInfoToLNMsgXml();
		log.info("SEND_MSG_REQUEST: \n" + msg);
		System.out.println(msg);
		String ip = sysAddrIpMap.get("LOANIP");
		int port = Integer.parseInt(sysAddrPortMap.get("LOANPORT"));
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(msg);
			// System.out.println(resp);
		} catch (IOException e) {
			// log.info(String.format("destSysNo:%s, ip:%s, port:%d\n",
			// header.getDestSysCd(), ip, port));
			e.printStackTrace();
		}
		log.info("RESP_MSG_RESPONSE: \n" + resp);

		return resp;
	}

	/**
	 * add by liuming 20170522
	 * @throws
	 */
	public static String processLN(String xml) throws Exception {
		TransClient.init();
		String msg = xml;
		log.info("SEND_MSG_REQUEST: \n" + msg);
//		System.out.println(msg);
		String ip = sysAddrIpMap.get("LOANIP");
		int port = Integer.parseInt(sysAddrPortMap.get("LOANPORT"));
//		String ip = "10.18.249.210";
//		int port = 6000;
		log.info("ip: \n" + ip);
		log.info("port: \n" + port);
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(msg);
			// System.out.println(resp);
		} catch (IOException e) {
			// log.info(String.format("destSysNo:%s, ip:%s, port:%d\n",
			// header.getDestSysCd(), ip, port));
			e.printStackTrace();
		}
		log.info("RESP_MSG_RESPONSE: \n" + resp);

		return resp;
	}
	/**
	 * 向WMS系统请求
	 * @param xml
	 * @return
	 */
	public static String processWMS(String xml){
		TransClient.init();
		String msg = xml;
		log.info("SEND_MSG_REQUEST: \n" + msg);
		String ip = sysAddrIpMap.get("WMSIP");
		int port = Integer.parseInt(sysAddrPortMap.get("WMSPORT"));
		log.info("ip: \n" + ip);
		log.info("port: \n" + port);
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("RESP_MSG_RESPONSE: \n" + resp);
		return resp;
	}
	
	public static void main(String[] args) throws Exception {
		// String reqxml=process1("CUSGRP0000000710");
//		String reqxml = process2();
//		System.out.println(reqxml);
	}
}

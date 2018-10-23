package com.yuchengtech.bcrm.customer.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.commons.lang3.ObjectUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.socket.NioClient;

/**
 * @author discription：潜在客户管理功能 date 2014-11-01
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class VisitInfoService extends CommonService {
	// add by liuming 20170722
	private static Logger log = LoggerFactory.getLogger(VisitInfoService.class);
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	public VisitInfoService() {
		JPABaseDAO<AcrmFCiPotCusCom, String> baseDAO = new JPABaseDAO<AcrmFCiPotCusCom, String>(
				AcrmFCiPotCusCom.class);
		super.setBaseDAO(baseDAO);
	}
	/**
	 * 根据客户名称校验该客户是否已经存在，如果存在，返回归属客户经理的工号和名称
	 * @param custName 新建潜在客户的客户名称
	 * @return {String} null/(mgrId,mgrName)
	 */
	public String isCustExists(String custName){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT OC.MGR_ID,OC.MGR_NAME ")
		.append("FROM ACRM_F_CI_CUSTOMER  AC ")
		.append("LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR OC ON AC.CUST_ID=OC.CUST_ID ")
		.append("WHERE AC.CUST_NAME='").append(custName).append("' ");
		System.out.println(custName);
		List list = baseDAO.createNativeQueryWithSQL(sb.toString()).getResultList();
		if (list.size() == 0) {
			return null;
		} else {
			Object[] obj = (Object[]) list.get(0);
			String mgrId = ObjectUtils.defaultIfNull(obj[0], "").toString();
			String mgrName = ObjectUtils.defaultIfNull(obj[1], "").toString();
			return mgrId + "," + mgrName;
		}
	}
	/**
	 * 保存新建的潜在客户信息
	 * @param custZhName 客户名称
	 * @param linkUser 联系人
	 * @param hy 行业
	 * @param ifhy 是否目标行业
	 * @param linkPhone 联系人电话
	 * @return
	 */
	public String save(String custZhName, String linkUser, String hy,
			String ifhy, String linkPhone) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 生成CUST_ID
		Random random1 = new Random();
		int sp = Math.abs(random1.nextInt());
		String result = "crm" + sp;

		AcrmFCiPotCusCom com = new AcrmFCiPotCusCom();
		com.setCusId(result);
		com.setCusName(custZhName);
		com.setCustType("1");
		com.setDel("0");
		com.setAttenName(linkUser);
		com.setCallNo(linkPhone);
		com.setCustMgr(auth.getUserId());
		com.setMainBrId(auth.getUnitId());
		com.setIndustType(hy);
		com.setIfTargetbusi(ifhy);
		com.setState("0");
		com.setConclusion(new BigDecimal(1));
		// add by liuming 20170722
		saveToCustomer(result, custZhName, auth);
		super.save(com);
		return null;
	}

	/**
	 * 保存客户信息到客户相关表
	 */
	@SuppressWarnings("unused")
	public void saveToCustomer(String custid, String name, AuthUser auth) {
		// 拼接请求报文
		String custId = custid;// 客户号
		String custMgr = auth.getUserId();// 客户经理
		String custName = name;// 客户名称
		String ecifId = null;// ecif客户号
		String identId = null;// 证件id号
		String belongManagerId = null;
		String belongBranchId = null;
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");
		StringBuffer reQxmlorg = new StringBuffer();
		String reqXml = "";
		String Hxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n"
				+ "<TransBody>\n" + " <RequestHeader>\n"
				+ "    <ReqSysCd>CRM</ReqSysCd>\n" + "    <ReqSeqNo>"
				+ df20.format(new Date())
				+ "</ReqSeqNo>\n"
				+ "    <ReqDt>"
				+ df8.format(new Date())
				+ "</ReqDt>\n"
				+ "    <ReqTm>"
				+ df10.format(new Date())
				+ "</ReqTm>\n"
				+ "    <DestSysCd>ECIF</DestSysCd>\n"
				+ "    <ChnlNo>82</ChnlNo>\n"
//				+ "    <BrchNo>6801</BrchNo>\n"
                + "    <BrchNo>"+auth.getUnitId()+"</BrchNo>\n"
				+ "    <BizLine>6491</BizLine>\n"
				+ "    <TrmNo>TRM10010</TrmNo>\n"
				+ "    <TrmIP>127.0.0.1</TrmIP>\n"
				+ "    <TlrNo>"
				+ custMgr
				+ "</TlrNo>\n"
				+ " </RequestHeader>\n"
				+ " <RequestBody>\n"
				+ "    <txCode>openOrgAccount4CrmAndDms</txCode>\n"
				+ "    <txName>潜在机构客户开户(crm拜访笔记)</txName>\n"
				+ "    <authType>1</authType>\n"
				+ "    <authCode>1010</authCode>\n";

		String orgIdentifier = " <orgIdentifier>\n"
				+ "    <identType>20</identType>\n"
				+ "    <identNo></identNo>\n" + "    <identCustName>"
				+ custName
				+ "</identCustName>\n"
				+ "	  <identDesc></identDesc>\n"
				+ "	  <countryOrRegion></countryOrRegion>\n"
				+ "	  <identOrg></identOrg>\n"
				+ "	  <identApproveUnit></identApproveUnit>\n"
				+ "	  <identCheckFlag></identCheckFlag>\n"
				+ "	  <idenRegDate></idenRegDate>\n"
				+ "	  <identCheckingDate></identCheckingDate>\n"
				+ "	  <identCheckedDate></identCheckedDate>\n"
				+ "	  <identValidPeriod></identValidPeriod>\n"
				+ "	  <identEffectiveDate></identEffectiveDate>\n"
				+ "	  <identExpiredDate></identExpiredDate>\n"
				+ "	  <identValidFlag></identValidFlag>\n"
				+ "	  <txSeqNo></txSeqNo>\n"
				+ "	  <identPeriod></identPeriod>\n"
				+ "	  <isOpenAccIdent></isOpenAccIdent>\n"
				+ "	  <isOpenAccIdentLn></isOpenAccIdentLn>\n"
				+ "	  <openAccIdentModifiedFlag></openAccIdentModifiedFlag>\n"
				+ "	  <identModifiedTime></identModifiedTime>\n"
				+ "	  <verifyDate></verifyDate>\n"
				+ "	  <verifyEmployee></verifyEmployee>\n"
				+ "	  <verifyResult></verifyResult>\n"
				+ "	  <lastUpdateSys></lastUpdateSys>\n"
				+ "	  <lastUpdateUser></lastUpdateUser>\n"
				+ "	  <lastUpdateTm></lastUpdateTm>\n" + "  </orgIdentifier>\n";

		String customer = "<customer>\n" + "	    <identType>20</identType>\n"
				+ "		<identNo></identNo>\n" + "		<custName>"
				+ custName
				+ "</custName>\n"
				+ "		<custType>1</custType>\n" // 客户类型：默认1:企业
				+ "		<shortName></shortName>\n" // 客户简称
				+ "		<enName></enName>\n" // 英文名称
				+ "		<industType></industType>" // 所属行业
				+ "		<riskNationCode></riskNationCode>\n" // 国别风险国别代码
				+ "		<potentialFlag>1</potentialFlag>\n" // 潜在客户标志:默认1:潜在客户
				+ "		<inoutFlag></inoutFlag>\n" // 境内境外标志
				+ "		<arCustFlag></arCustFlag>\n" // AR客户标志
				+ "		<arCustType></arCustType>\n" // AR客户类型
				+ "        <createDate>"
				+ df8.format(new Date())
				+ "</createDate>\n                          "
				+ "        <createTime>"
				+ df8.format(new Date())
				+ "</createTime>\n"
				+ "        <createBranchNo></createBranchNo>\n"
				+ "        <createTellerNo></createTellerNo>\n"
				+ "        <createDateLn></createDateLn>\n"
				+ "        <createTimeLn></createTimeLn>\n"
				+ "        <createBranchNoLn></createBranchNoLn>\n"
				+ "        <createTellerNoLn></createTellerNoLn>\n"
				+ "        <custLevel></custLevel>\n"
				+ "        <riskLevel></riskLevel>\n"
				+ "        <riskValidDate></riskValidDate>\n"
				+ "        <creditLevel></creditLevel>\n"
				+ "        <currentAum></currentAum>\n"
				+ "        <totalDebt></totalDebt>\n"
				+ "        <infoPer></infoPer>\n"
				+ "        <faxtradeNorecNum></faxtradeNorecNum>\n"
				+ "        <coreNo></coreNo>\n"
				+ "        <postName></postName>\n"
				+ "        <enShortName></enShortName>\n"
				+ "        <custStat></custStat>\n"
				+ "        <jobType></jobType>\n"
				+ "        <industType></industType>\n"
				+ "        <riskNationCode></riskNationCode>\n"
				+ "        <ebankFlag></ebankFlag>\n"
				+ "        <realFlag></realFlag>\n"
				+ "        <blankFlag></blankFlag>\n"
				+ "        <vipFlag></vipFlag>\n"
				+ "        <mergeFlag></mergeFlag>\n"
				+ "        <custnmIdentModifiedFlag></custnmIdentModifiedFlag>\n"
				+ "        <linkmanName></linkmanName>\n"
				+ "        <linkmanTel></linkmanTel>\n"
				+ "        <firstLoanDate></firstLoanDate>\n"
				+ "        <loanCustMgr></loanCustMgr>\n"
				+ "        <loanMainBrId></loanMainBrId>\n"
				+ "        <arCustFlag></arCustFlag>\n"
				+ "        <arCustType></arCustType>\n"
				+ "        <sourceChannel></sourceChannel>\n"
				+ "        <recommender></recommender>\n"
				+ "        <loanCustRank></loanCustRank>\n"
				+ "        <loanCustStat></loanCustStat>\n"
				+ "        <staffin></staffin>\n"
				+ "        <swift></swift>\n"
				+ "        <cusBankRel></cusBankRel>\n"
				+ "        <cusCorpRel></cusCorpRel>\n"
				+ "        <profctr></profctr>\n"
				+ "        <loanCustId></loanCustId>\n" + "     </customer>\n";
		String contmeth = " <contmeth>\n"
				+ "    <contmethType></contmethType>\n"
				+ "    <contmethInfo></contmethInfo>\n"
				+ "    <contmethSeq></contmethSeq>\n" + "	<remark></remark>\n"
				+ "	<stat></stat>\n" + " </contmeth>\n";
		String crossindex = " <crossindex>\n" + "     <srcSysNo>DMS</srcSysNo>\n"
				+ "     <srcSysCustNo>"+custId+"</srcSysCustNo>\n" + " </crossindex>";
		String org = "	<org>\n" + "		<comSpLicOrg></comSpLicOrg>\n"
				+ "		<comSpDetail></comSpDetail>\n"
				+ "		<comSpLicNo></comSpLicNo>\n"
				+ "		<comSpBusiness></comSpBusiness>\n"
				+ "		<topCorpLevel></topCorpLevel>\n"
				+ "		<fexcPrmCode></fexcPrmCode>\n"
				+ "		<fundSource></fundSource>\n"
				+ "		<induDeveProspect></induDeveProspect>\n"
				+ "		<busiStartDate></busiStartDate>\n"
				+ "		<businessMode></businessMode>\n"
				+ "		<minorBusiness></minorBusiness>\n"
				+ "		<mainBusiness></mainBusiness>\n"
				+ "		<superDept></superDept>\n" + "		<buildDate></buildDate>\n"
				+ "		<entBelong></entBelong>\n"
				+ "		<investType></investType>\n"
				+ "		<industryCategory></industryCategory>\n"
				+ "		<inCllType></inCllType>\n"
				+ "		<governStructure></governStructure>\n"
				+ "		<orgForm></orgForm>\n" + "		<comHoldType></comHoldType>\n"
				+ "		<economicType></economicType>\n"
				+ "		<employeeScale></employeeScale>\n"
				+ "		<assetsScale></assetsScale>\n"
				+ "		<entScaleCk></entScaleCk>\n"
				+ "		<entScaleRh></entScaleRh>\n" + "		<entScale></entScale>\n"
				+ "		<entProperty></entProperty>\n"
				+ "		<industryChar></industryChar>\n"
				+ "		<industryDivision></industryDivision>\n"
				+ "		<minorIndustry></minorIndustry>\n"
				+ "		<mainIndustry></mainIndustry>\n"
				+ "		<busiLicNo></busiLicNo>\n"
				+ "		<orgCodeAnnDate></orgCodeAnnDate>\n"
				+ "		<orgCodeUnit></orgCodeUnit>\n"
				+ "		<orgExpDate></orgExpDate>\n"
				+ "		<orgRegDate></orgRegDate>\n" + "		<orgCode></orgCode>\n"
				+ "		<orgSubType></orgSubType>\n" + "		<orgType></orgType>\n"
				+ "		<areaCode></areaCode>\n" + "		<remark></remark>\n"
				+ "		<lastDealingsDesc></lastDealingsDesc>\n"
				+ "		<orgWeixin></orgWeixin>\n" + "		<orgWeibo></orgWeibo>\n"
				+ "		<orgHomepage></orgHomepage>\n"
				+ "		<orgEmail></orgEmail>\n" + "		<orgFex></orgFex>\n"
				+ "		<orgTel></orgTel>\n" + "		<orgCus></orgCus>\n"
				+ "		<orgZipcode></orgZipcode>\n" + "		<orgAddr></orgAddr>\n"
				+ "		<holdStockAmt></holdStockAmt>\n"
				+ "		<isStockHolder></isStockHolder>\n"
				+ "		<industryPosition></industryPosition>\n"
				+ "		<annualProfit></annualProfit>\n"
				+ "		<annualIncome></annualIncome>\n"
				+ "		<totalDebt></totalDebt>\n"
				+ "		<totalAssets></totalAssets>\n"
				+ "		<finRepType></finRepType>\n"
				+ "		<legalReprNationCode></legalReprNationCode>\n"
				+ "		<legalReprPhoto></legalReprPhoto>\n"
				+ "		<legalReprAddr></legalReprAddr>\n"
				+ "		<legalReprTel></legalReprTel>\n"
				+ "		<legalReprIdentNo></legalReprIdentNo>\n"
				+ "		<legalReprIdentType></legalReprIdentType>\n"
				+ "		<legalReprGender></legalReprGender>\n"
				+ "		<legalReprName></legalReprName>\n"
				+ "		<partnerType></partnerType>\n"
				+ "		<loadCardAuditDt></loadCardAuditDt>\n"
				+ "		<loadCardPwd></loadCardPwd>\n"
				+ "		<loanCardStat></loanCardStat>\n"
				+ "		<loanCardNo></loanCardNo>\n"
				+ "		<loanCardFlag></loanCardFlag>\n"
				+ "		<comSpEndDate></comSpEndDate>\n"
				+ "		<comSpStrDate></comSpStrDate>\n"
				+ "		<zoneCode></zoneCode>\n" + "		<nationCode></nationCode>\n"
				+ "		<hqNationCode></hqNationCode>\n"
				+ "		<orgBizCustType></orgBizCustType>\n"
				+ "		<churcustype></churcustype>\n"
				+ "		<jointCustType></jointCustType>\n"
				+ "		<creditCode></creditCode>\n" + "		<lncustp></lncustp>\n"
				+ "		<orgCustType></orgCustType>\n" + "		<custName>" + custName
				+ "</custName>\n" + "		<ifOrgSubType></ifOrgSubType>\n"
				+ "	</org>\n";
		// 归属经理
		String belongManager = " <belongManager>\n " + "	<custManagerNo>"
				+ custMgr + "</custManagerNo>\n" + "	<mainType>1</mainType>\n"
				+ "	<validFlag></validFlag>\n" + "	<startDate></startDate>\n"
				+ "	<endDate></endDate>\n"
				+ "	<custManagerType></custManagerType>\n"
				+ " </belongManager>\n";
		// 归属机构
		String belongBranch = " <belongBranch>\n " + "	<belongBranchNo>"
				+ auth.getUnitId() + "</belongBranchNo>\n" + " </belongBranch>\n";
		reQxmlorg.append(Hxml);
		reQxmlorg.append(orgIdentifier);
		reQxmlorg.append(customer);
		reQxmlorg.append(contmeth);
		reQxmlorg.append(crossindex);
		reQxmlorg.append(org);
		reQxmlorg.append(belongManager);
		reQxmlorg.append(belongBranch);
		reQxmlorg.append("</RequestBody></TransBody>\n");
		reqXml = reQxmlorg.toString();
		try {
			// 调用ECIF开户
			Map map = process(reqXml);
			log.info("获取ECIF客户号:" + map);
			ecifId = map.get("custNo") != null ? map.get("custNo").toString()
					: "";
			identId = map.get("identId") != null ? map.get("identId")
					.toString() : "";
			belongManagerId = map.get("belongManagerId") != null ? map.get(
					"belongManagerId").toString() : "";
			belongBranchId = map.get("belongBranchId") != null ? map.get(
					"belongBranchId").toString() : "";
					
			String[] SQL = new String[6];
					
			if (ecifId != null && !ecifId.equals("")) {
				// 保存到customer,默认开户证件：20-境内组织机构代码
				String customerInsert = " INSERT INTO ACRM_F_CI_CUSTOMER(CUST_ID,CUST_NAME,CUST_TYPE,POTENTIAL_FLAG,CREATE_TIME_LN,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER,IDENT_TYPE)"
						+ " VALUES('"
						+ ecifId
						+ "','"
						+ custName
						+ "','1','1',sysDate,'DMS',sysDate,'"
						+ custMgr
						+ "','20')";
				// 保存到机构表
				String orgInsert = " INSERT INTO ACRM_F_CI_ORG(CUST_ID,CUST_NAME)"
						+ " VALUES('" + ecifId + "','" + custName + "')";
				String identInsert = "";
				if (identId != null && !identId.equals("")) {
					// 保存到证件表,默认开户证件：20-境内组织机构代码
				   identInsert = " INSERT INTO ACRM_F_CI_CUST_IDENTIFIER(IDENT_ID,CUST_ID,IDENT_TYPE)"
							+ " VALUES('"
							+ identId
							+ "','"
							+ ecifId
							+ "','20')";
				}
				String belongMgrInsert = "";
				if (belongManagerId != null && !belongManagerId.equals("")) {
					belongMgrInsert = " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR(ID,CUST_ID,MGR_ID,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
							+ " VALUES('"
							+ belongManagerId
							+ "','"
							+ ecifId
							+ "','"
							+ custMgr
							+ "','1',"
							+ " (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr
							+ "'),"
							+ " (select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr
							+ "')),"
							+ " (SELECT t.user_name FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr + "'))";
				} else {
					belongMgrInsert = " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR(ID,CUST_ID,MGR_ID,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
							+ " VALUES(ID_SEQUENCE.NEXTVAL,'"
							+ ecifId
							+ "','"
							+ custMgr
							+ "','1',"
							+ " (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr
							+ "'),"
							+ " (select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr
							+ "')),"
							+ " (SELECT t.user_name FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr + "'))";
				}
				// 保存到归属经理表
				// 保存到归属机构表
				String belongOrgInsert = "";
				if (belongBranchId != null && !belongBranchId.equals("")) {
					belongOrgInsert = " INSERT INTO OCRM_F_CI_BELONG_ORG(ID,CUST_ID,MAIN_TYPE,INSTITUTION_CODE,INSTITUTION_NAME)"
							+ " VALUES('"
							+ belongBranchId
							+ "','"
							+ ecifId
							+ "','1',(SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr
							+ "'),(select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr + "')))";
				} else {
					belongOrgInsert = " INSERT INTO OCRM_F_CI_BELONG_ORG(ID,CUST_ID,MAIN_TYPE,INSTITUTION_CODE,INSTITUTION_NAME)"
							+ " VALUES(ID_SEQUENCE.NEXTVAL,'"
							+ ecifId
							+ "','1',(SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr
							+ "'),(select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"
							+ custMgr + "')))";
				}
				// 保存到交叉索引表
				String crossIndexInsert = " INSERT INTO ACRM_F_CI_CROSSINDEX(CROSSINDEX_ID,SRC_SYS_NO,SRC_SYS_CUST_NO,CUST_ID)"
						+ " VALUES(sq_crossindex.nextval,'DMS','"
						+ custId
						+ "','" + ecifId + "')";
				SQL[0] = customerInsert;
				SQL[1] = orgInsert;
				SQL[2] = identInsert;
				SQL[3] = belongMgrInsert;
				SQL[4] = belongOrgInsert;
				SQL[5] = crossIndexInsert;
				JdbcTemplate jdbcTemplate2 = new JdbcTemplate(ds);
				jdbcTemplate2.batchUpdate(SQL);
			}else{
				log.info("调ecif开户接口失败，未取得客户编号!");
				throw new BizException(1, 0, "10001","调ecif开户接口失败，未取得客户编号!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("保存客户信息发生异常!");
			e.printStackTrace();
			throw new BizException(1, 0, "10001","保存客户信息发生异常!");
		}
	}

	/**
	 * 调用ECIF开户
	 */
	@SuppressWarnings("unchecked")
	public static Map process(String mxlmsg) throws Exception {
		Map idsMap = new HashMap();
		log.info("ECIF开户请求报文:" + mxlmsg);
		String msg = mxlmsg;
		String port = FileTypeConstance.getBipProperty("ECIF.PORT");
		String ip = FileTypeConstance.getBipProperty("ECIF.IP");
//		String port="9500";
//		String ip="127.0.0.1";

		NioClient cl = new NioClient(ip, Integer.parseInt(port));
		String resp = null;
		try {
			resp = cl.SocketCommunication(String.format("%08d",
					msg.getBytes("GBK").length)
					+ msg);
		} catch (IOException e) {
			log.info("调用ECIF系统超时!");
			throw new BizException(1, 0, "10001","调用ECIF系统超时!");
		}
		log.info("ECIF开户返回报文:" + resp);
		String custNo = "";
		String custId = "";
		String identId = "";
		String belongManagerId = "";
		String belongBranchId = "";
		/**
		 * 处理返回报文
		 * 
		 * @param xml
		 * @return
		 */
		try {
			resp = resp.substring(8);
			Document doc = DocumentHelper.parseText(resp);
			Element root = doc.getRootElement();
			String TxStatDesc = root.element("ResponseTail")
					.element("TxStatDesc").getTextTrim();
			String TxStatCode = root.element("ResponseTail")
					.element("TxStatCode").getTextTrim();
			if ("000000".equals(TxStatCode)) {
				// 返回技术主键：
				custNo = root.element("ResponseBody").element("custNo")
						.getTextTrim();
				if (root.element("ResponseBody").element("custId") != null) {
					custId = root.element("ResponseBody").element("custId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("identId") != null) {
					identId = root.element("ResponseBody").element("identId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("belongManagerId") != null) {
					belongManagerId = root.element("ResponseBody")
							.element("belongManagerId").getTextTrim();
				}
				if (root.element("ResponseBody").element("belongBranchId") != null) {
					belongBranchId = root.element("ResponseBody")
							.element("belongBranchId").getTextTrim();
				}
				idsMap.put("custNo", custNo);
				idsMap.put("custId", custId);
				idsMap.put("identId", identId);
				idsMap.put("belongManagerId", belongManagerId);
				idsMap.put("belongBranchId", belongBranchId);

			} else {
				log.info("调用ECIF开户接口异常:" + TxStatDesc);
			}
		} catch (BizException e) {
			log.info("调用ECIF开户接口异常1:" + e.getMessage());
		} catch (Exception e) {
			log.info("调用ECIF开户接口异常2:" + e.getMessage());
		}
		return idsMap;
	}
}

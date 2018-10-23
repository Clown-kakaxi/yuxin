package com.yuchengtech.bcrm.customer.newCustomer.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.model.AcrmFCiOrg;
import com.yuchengtech.bcrm.customer.model.ReviewMapping;
import com.yuchengtech.bcrm.customer.newCustomer.NewSynchroData;
import com.yuchengtech.bcrm.customer.newCustomer.NewXmlTableUtil;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;
import com.yuchengtech.trans.socket.NioClient;

@Service
public class CustomerManagerNewService extends CommonService {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	private Map<String, Object> json;

	private static Logger log = LoggerFactory.getLogger(CustomerManagerNewService.class);

	public CustomerManagerNewService() {
		JPABaseDAO<AcrmFCiCustomer, Long> baseDAO = new JPABaseDAO<AcrmFCiCustomer, Long>(
				AcrmFCiCustomer.class);
		super.setBaseDAO(baseDAO);
	}

	/**
	 * 判断该客户是否在CRM中存在。
	 * @param identCustName
	 * @param identNo
	 * @param identType
	 * @return
	 */
	public List queryExsitTempInCrm(String custId, String custName,
			String identNo, String identType) {
		String custName1 = custName.trim();
		String tempSql = "select * from ACRM_F_CI_CUSTOMER t  where t.cust_id='"
				+ custId
				+ "'  and  t.cust_name='"
				+ custName1
				+ "' "
				+ " and t.IDENT_TYPE ='"
				+ identType
				+ "'"
				+ " and t.IDENT_no = '" + identNo + "' ";
		List<Object[]> custList = this.em.createNativeQuery(tempSql).getResultList();
		return custList;
	}

	/**
	 * 1、通过客户名称、证件号码、证件类型调ecif接口，开户生成custId 2、将值保存到客户表，同时将custId存入各表
	 * 
	 * @param identCustName
	 * @param identType
	 * @param identNo
	 * @return
	 */
	public String save(String custId, String identCustName, String identType,
			String identNo, String custType, Object ComFirst, Object ComSecond) {
		Map idsMap = new HashMap();
		String ecifCustId = "";//调用ECIF开户后返回的客户号
		// OcrmFCiBelongOrg ocrmFCiBelongOrg = new OcrmFCiBelongOrg();
		 OcrmFCiBelongCustmgr ocrmFCiBelongCustmgr = new OcrmFCiBelongCustmgr();

		String sql = "";
		String sql1 = "";
		String sql2 = "";
		String sql6 = "";
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date date1 = new Date();
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format1.format(date1);
		date = "to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
		String currenUserId = auth.getUserId();
		String tempUnitId = auth.getUnitId();
		// 生成CUST_ID
		Random random1 = new Random();
		int sp = Math.abs(random1.nextInt());
		String result = "crm" + sp;
		log.info(String.format("根据客户号%s,客户名称%s,证件号%s,证件类型%s查询客户信息以判断该客户是否在CRM中存在", custId, identCustName, identNo, identType));
		List custlist = queryExsitTempInCrm(custId, identCustName, identNo, identType);
		// 新增潜在客户：
		if (custId == null || custId.equals("")) {
			// 1.该客户在CRM中存在
			if (custlist.size() > 0) {
				log.info("该客户在CRM中存在，客户信息保存失败");
			}
			// 2.该客户在CRM中不存在
			if (custlist.size() == 0) {
				log.info("该客户在CRM中不存在");
				// A三证齐全【页面传递过来判定】的客户立即调用ECIF接口，由ECIF生成客户号，返回给CRM，CRM将数据保存到客户表和扩展表中，但如果是导入创建，多次调用ECIF接口
				if (identCustName != "" && identNo != "" && identType != "") {
					log.info("客户名称、证件类型、证件号都不为空，调用ECIF接口进行开户");
					Map repPhone = null;
					try {
						repPhone = CallIntrface(custId, identCustName, identNo, identType, ComFirst, ComSecond);
						log.info("repPhone:" + repPhone);
						ecifCustId = repPhone.get("custNo").toString();
						log.info(String.format("获取到ECIF开户后返回的客户号:%s", ecifCustId));
					} catch (Exception e) {
						throw new BizException(1, 0, "10001", "调ecif开户接口失败，未取得客户编号");
					} finally {
					}
					log.info("将客户信息保存到客户信息表ACRM_F_CI_CUSTOMER");
					// 新增
					sql = " INSERT INTO ACRM_F_CI_CUSTOMER(CUST_ID,CUST_NAME,CUST_TYPE,POTENTIAL_FLAG,IDENT_TYPE,IDENT_NO,CREATE_TIME_LN,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER)"
							+ " VALUES('"+ ecifCustId + "','"+ identCustName+ "','"+custType+"','1','"+ identType+ "','"+ identNo+ "',sysDate,'CRM',sysDate,'"+ auth.getUserId() + "')";
					this.em.createNativeQuery(sql).executeUpdate();

					// ocrmFCiBelongOrg.setCustId(repPhone.get("custId").toString());
					// ocrmFCiBelongOrg.setInstitutionCode(tempUnitId);
					// ocrmFCiBelongOrg.setInstitutionName(auth.getUnitName());
					// ocrmFCiBelongOrg.setMainType("1");
					List li = auth.getRolesInfo();
					for (Object m : li) {
						Map map = (Map) m;
						// 根据角色判断
						// if("R304".equals(map.get("ROLE_CODE"))){
						// ocrmFCiBelongCustmgr.setMgrId(auth.getUserId());
						// ocrmFCiBelongCustmgr.setAssignUsername(auth.getUsername());
						// break;
						// }else{
						// String user="VM"+auth.getUnitId();
						// String name=auth.getUnitName();
						// ocrmFCiBelongCustmgr.setMgrId(user);
						// ocrmFCiBelongCustmgr.setAssignUsername(name);
						// }
						// 不根据角色判断
					}
					log.info("更新客户归属信息到表OCRM_F_CI_BELONG_CUSTMGR");
					ocrmFCiBelongCustmgr.setMgrId(auth.getUserId());
					ocrmFCiBelongCustmgr.setAssignUsername(auth.getUsername());
					ocrmFCiBelongCustmgr.setCustId(ecifCustId);
					ocrmFCiBelongCustmgr.setInstitution(auth.getUnitId());
					ocrmFCiBelongCustmgr.setInstitutionName(auth.getUnitName());
					ocrmFCiBelongCustmgr.setAssignUser(auth.getUserId());
					ocrmFCiBelongCustmgr.setMgrName(auth.getCname());
					ocrmFCiBelongCustmgr.setMainType("1");
					//
					// em.persist(ocrmFCiBelongOrg);
					em.persist(ocrmFCiBelongCustmgr);
				} else {
					log.info("潜在客户信息保存失败：客户名称、证件类型、证件号不齐全");
					throw new BizException(1,2,"0000","客户信息保存失败：客户名称、证件类型、证件号不齐全");
				}
			}
		} else {
			log.info(String.format("潜在客户信息保存失败：页面传递了客户号[%s]", custId));
		}
		idsMap.put("custId", ecifCustId);
		return ecifCustId;
	}

	/**
	 * 调用开户接口
	 * 
	 * @param custId
	 * @param identCustName
	 * @param identNo
	 * @param identType
	 * @return
	 * @throws Exception
	 */
	public Map CallIntrface(String custId, String identCustName,
			String identNo, String identType, Object ComFirst, Object ComSecond)
			throws Exception {
		// 处理第一页json:customer表的字段
		JSONArray json1 = JSONArray.fromObject(ComFirst);
		net.sf.json.JSONObject jsonObject = json1.getJSONObject(0);
		String potentialFlag = jsonObject.getString("POTENTIAL_FLAG");// 潜在客户标识
		String shortName = jsonObject.getString("SHORT_NAME");// 客户简称
		String enName = jsonObject.getString("EN_NAME");// 英文名称
		String industType = jsonObject.getString("IN_CLL_TYPE_ID");// 行业类别
		String riskNationCode = jsonObject.getString("RISK_NATION_CODE");// 国别风险国别代码
		String identEndDate = jsonObject.getString("IDENT_END_DATE");// 证件到期日
		// String orgCustType=jsonObject.getString("ORG_CUST_TYPE");//客户类型
		// String loanOrgType=jsonObject.getString("LOAN_ORG_TYPE");//组织机构类别
		String busiLicNo=jsonObject.getString("BUSI_LIC_NO");//统一社会信用代码
		// String swRegisCode=jsonObject.getString("SW_REGIS_CODE");//税务登记证编号
		// String areaRegCode=jsonObject.getString("AREA_REG_CODE");//地税税务登记代码

		// String flagCapDtl=jsonObject.getString("FLAG_CAP_DTL");//组织机构类别细分
		String creditCode=jsonObject.getString("CREDIT_CODE");//机构信用代码
		// String
		// accOpenLicense=jsonObject.getString("ACC_OPEN_LICENSE");//开户许可证核准号
		// String
		// nationRegCode=jsonObject.getString("NATION_REG_CODE");//国税税务登记代码
		String nationCode=jsonObject.getString("NATION_CODE");//企业所在国别
	    String hqNationCode=jsonObject.getString("HQ_NATION_CODE");//总部所在国别
		// 处理第二页json:customer表的字段
		JSONArray json2 = JSONArray.fromObject(ComSecond);
		net.sf.json.JSONObject jsonObject2 = json2.getJSONObject(0);
		String inoutFlag = jsonObject2.getString("INOUT_FLAG");// 境内境外标志
		String arCustFlag = jsonObject2.getString("AR_CUST_FLAG");// AR客户状态
		String arCustType = jsonObject2.getString("AR_CUST_TYPE");// AR客户类型

		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String msg = "";
		// 调用对公接口
		StringBuffer reQxmlorg = new StringBuffer();
		String Hxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n"
				+ "<TransBody>\n" 
				+ " <RequestHeader>\n"
				+ "    <ReqSysCd>CRM</ReqSysCd>\n" 
				+ "    <ReqSeqNo>"+ df20.format(new Date())+ "</ReqSeqNo>\n"
				+ "    <ReqDt>"+ df8.format(new Date())+ "</ReqDt>\n"
				+ "    <ReqTm>"+ df10.format(new Date())+ "</ReqTm>\n"
				+ "    <DestSysCd>ECIF</DestSysCd>\n"
				+ "    <ChnlNo>82</ChnlNo>\n"
				+ "    <BrchNo>6801</BrchNo>\n"
				+ "    <BizLine>6491</BizLine>\n"
				+ "    <TrmNo>TRM10010</TrmNo>\n"
				+ "    <TrmIP>127.0.0.1</TrmIP>\n"
				+ "    <TlrNo>"+ auth.getUserId()+ "</TlrNo>\n"
				+
				// "    <TrmIP>10.18.249.157</TrmIP>\n" +
				// "    <TlrNo>700N0624</TlrNo>\n" +
				" </RequestHeader>\n"
				+ " <RequestBody>\n"
				+ "    <txCode>openOrgAccount4Crm</txCode>\n"
				+ "    <txName>潜在机构客户开户</txName>\n"
				+ "    <authType>1</authType>\n"
				+ "    <authCode>1010</authCode>\n";

		String orgIdentifier = "";
		if ("".equals(identType) || "".equals(identNo)) {
			orgIdentifier =
				  " <orgIdentifier>\n"
				+ "    <identType></identType>\n"
				+ "    <identNo></identNo>\n"
				+ "    <identCustName></identCustName>\n"
				+ " </orgIdentifier>\n";

		} else {
			orgIdentifier = 
				" <orgIdentifier>\n" 
				+"    <identType>"+ identType+ "</identType>\n"
				+"    <identNo>"+ identNo+ "</identNo>\n"
				+"    <identCustName>"+ identCustName+"</identCustName>\n"
				+"	  <identDesc></identDesc>\n"
				+"	  <countryOrRegion></countryOrRegion>\n"
				+"	  <identOrg></identOrg>\n"
				+"	  <identApproveUnit></identApproveUnit>\n"
				+"	  <identCheckFlag></identCheckFlag>\n"
				+"	  <idenRegDate></idenRegDate>\n"
				+"	  <identCheckingDate></identCheckingDate>\n"
				+"	  <identCheckedDate></identCheckedDate>\n"
				+"	  <identValidPeriod></identValidPeriod>\n"
				+"	  <identEffectiveDate></identEffectiveDate>\n"
				+"	  <identExpiredDate></identExpiredDate>\n"
				+"	  <identValidFlag></identValidFlag>\n"
				+"	  <txSeqNo></txSeqNo>\n"
				+"	  <identPeriod></identPeriod>\n"
				+"	  <isOpenAccIdent></isOpenAccIdent>\n"
				+"	  <isOpenAccIdentLn></isOpenAccIdentLn>\n"
				+"	  <openAccIdentModifiedFlag></openAccIdentModifiedFlag>\n"
				+"	  <identModifiedTime></identModifiedTime>\n"
				+"	  <verifyDate></verifyDate>\n"
				+"	  <verifyEmployee></verifyEmployee>\n"
				+"	  <verifyResult></verifyResult>\n"
				+"	  <lastUpdateSys></lastUpdateSys>\n"
				+"	  <lastUpdateUser></lastUpdateUser>\n"
				+"	  <lastUpdateTm></lastUpdateTm>\n"
				+"</orgIdentifier>\n";
		}
		String customer = "";
		if ("".equals(identType) || "".equals(identNo)) {
			customer = 
				  " <customer>\n" 
				+ "    <custType>1</custType>\n"
				+ "    <identType></identType>\n"
				+ "    <identNo></identNo>\n"
				+ "    <custName></custName>\n" 
				+ " </customer>\n";
		} else {
			customer = 
				" <customer>\n" 
				+ "		<custType>1</custType>\n"
				+ "		<identType>"+ identType+ "</identType>\n"
				+ "		<identNo>"+ identNo+ "</identNo>\n"
				+ "		<custName>"+ identCustName+ "</custName>\n"
				+ "		<shortName>"+ shortName+ "</shortName>\n"
				+ "		<custType>1</custType>\n"+ // 客户类型：企业
				  "		<shortName>"+ shortName+ "</shortName>\n"+ // 客户简称
				  "		<enName>"+ enName+ "</enName>\n"+ // 英文名称
				  "		<industType>"+industType+"</industType>"+ //所属行业
				  "		<riskNationCode>"+ riskNationCode+ "</riskNationCode>\n"+ // 国别风险国别代码
				  "		<potentialFlag>"+ potentialFlag+ "</potentialFlag>\n"+ // 潜在客户标志
				  "		<inoutFlag>"+ inoutFlag+ "</inoutFlag>\n"+ // 境内境外标志
				  "		<arCustFlag>"+ arCustFlag+ "</arCustFlag>\n"+ // AR客户标志
				  "		<arCustType>"+ arCustType+ "</arCustType>\n"+ // AR客户类型
				  "     <createDate></createDate>\n                          "
				  +"     <createTime></createTime>\n"
				  +"     <createBranchNo></createBranchNo>\n"
				  +"     <createTellerNo></createTellerNo>\n"
				  +"     <createDateLn></createDateLn>\n"
				  +"     <createTimeLn></createTimeLn>\n"
				  +"     <createBranchNoLn></createBranchNoLn>\n"
				  +"     <createTellerNoLn></createTellerNoLn>\n"
				  +"     <custLevel></custLevel>\n"
				  +"     <riskLevel></riskLevel>\n"
				  +"     <riskValidDate></riskValidDate>\n"
				  +"     <creditLevel></creditLevel>\n"
				  +"     <currentAum></currentAum>\n"
				  +"     <totalDebt></totalDebt>\n"
				  +"     <infoPer></infoPer>\n"
				  +"     <faxtradeNorecNum></faxtradeNorecNum>\n"
				  +"     <coreNo></coreNo>\n"
				  +"     <postName></postName>\n"
				  +"     <enShortName></enShortName>\n"
				  +"     <custStat></custStat>\n"
				  +"     <jobType></jobType>\n"
				  +"     <industType></industType>\n"
				  +"     <riskNationCode></riskNationCode>\n"
				  +"     <ebankFlag></ebankFlag>\n"
				  +"     <realFlag></realFlag>\n"
				  +"     <blankFlag></blankFlag>\n"
				  +"     <vipFlag></vipFlag>\n"
				  +"     <mergeFlag></mergeFlag>\n"
				  +"     <custnmIdentModifiedFlag></custnmIdentModifiedFlag>\n"
				  +"     <linkmanName></linkmanName>\n"
				  +"     <linkmanTel></linkmanTel>\n"
				  +"     <firstLoanDate></firstLoanDate>\n"
				  +"     <loanCustMgr></loanCustMgr>\n"
				  +"     <loanMainBrId></loanMainBrId>\n"
				  +"     <arCustFlag></arCustFlag>\n"
				  +"     <arCustType></arCustType>\n"
				  +"     <sourceChannel></sourceChannel>\n"
				  +"     <recommender></recommender>\n"
				  +"     <loanCustRank></loanCustRank>\n"
				  +"     <loanCustStat></loanCustStat>\n"
				  +"     <staffin></staffin>\n"
				  +"     <swift></swift>\n"
				  +"     <cusBankRel></cusBankRel>\n"
				  +"     <cusCorpRel></cusCorpRel>\n"
				  +"     <profctr></profctr>\n"
				  +"     <loanCustId></loanCustId>\n"
				  +"     </customer>\n";
		}
		String contmeth = "";
		// if("".equals(contmethType)||"".equals(contmethInfo)){
		// contmeth=
		// "   <contmeth>\n" +
		// "    <contmethType></contmethType>\n" +
		// "    <contmethInfo></contmethInfo>\n" +
		// "    <contmethSeq></contmethSeq>\n" +
		// "		<remark></remark>\n"+
		// "		<stat></stat>\n"+
		// "   </contmeth>\n";
		//
		// }else{
		contmeth = " <contmeth>\n" 
				+ "    <contmethType></contmethType>\n"
				+ "    <contmethInfo></contmethInfo>\n"
				+ "    <contmethSeq></contmethSeq>\n"
				+ "	   <remark></remark>\n" 
				+ "	   <stat></stat>\n"
				+ " </contmeth>\n";
		// }
		String crossindex = 
				" <crossindex>\n" 
				+ "     <srcSysNo></srcSysNo>\n"
				+ "     <srcSysCustNo></srcSysCustNo>\n" 
				+ " </crossindex>";
		String org=
				"	<org>\n"
				+"		<comSpLicOrg></comSpLicOrg>\n"
				+"		<comSpDetail></comSpDetail>\n"
				+"		<comSpLicNo></comSpLicNo>\n"
				+"		<comSpBusiness></comSpBusiness>\n"
				+"		<topCorpLevel></topCorpLevel>\n"
				+"		<fexcPrmCode></fexcPrmCode>\n"
				+"		<fundSource></fundSource>\n"
				+"		<induDeveProspect></induDeveProspect>\n"
				+"		<busiStartDate></busiStartDate>\n"
				+"		<businessMode></businessMode>\n"
				+"		<minorBusiness></minorBusiness>\n"
				+"		<mainBusiness></mainBusiness>\n"
				+"		<superDept></superDept>\n"
				+"		<buildDate></buildDate>\n"
				+"		<entBelong></entBelong>\n"
				+"		<investType></investType>\n"
				+"		<industryCategory></industryCategory>\n"
				+"		<inCllType></inCllType>\n"
				+"		<governStructure></governStructure>\n"
				+"		<orgForm></orgForm>\n"
				+"		<comHoldType></comHoldType>\n"
				+"		<economicType></economicType>\n"
				+"		<employeeScale></employeeScale>\n"
				+"		<assetsScale></assetsScale>\n"
				+"		<entScaleCk></entScaleCk>\n"
				+"		<entScaleRh></entScaleRh>\n"
				+"		<entScale></entScale>\n"
				+"		<entProperty></entProperty>\n"
				+"		<industryChar></industryChar>\n"
				+"		<industryDivision></industryDivision>\n"
				+"		<minorIndustry></minorIndustry>\n"
				+"		<mainIndustry></mainIndustry>\n"
				+"		<busiLicNo>"+busiLicNo+"</busiLicNo>\n"
				+"		<orgCodeAnnDate></orgCodeAnnDate>\n"
				+"		<orgCodeUnit></orgCodeUnit>\n"
				+"		<orgExpDate></orgExpDate>\n"
				+"		<orgRegDate></orgRegDate>\n"
				+"		<orgCode></orgCode>\n"
				+"		<orgSubType></orgSubType>\n"
				+"		<orgType></orgType>\n"
				+"		<areaCode></areaCode>\n"
				+"		<remark></remark>\n"
				+"		<lastDealingsDesc></lastDealingsDesc>\n"
				+"		<orgWeixin></orgWeixin>\n"
				+"		<orgWeibo></orgWeibo>\n"
				+"		<orgHomepage></orgHomepage>\n"
				+"		<orgEmail></orgEmail>\n"
				+"		<orgFex></orgFex>\n"
				+"		<orgTel></orgTel>\n"
				+"		<orgCus></orgCus>\n"
				+"		<orgZipcode></orgZipcode>\n"
				+"		<orgAddr></orgAddr>\n"
				+"		<holdStockAmt></holdStockAmt>\n"
				+"		<isStockHolder></isStockHolder>\n"
				+"		<industryPosition></industryPosition>\n"
				+"		<annualProfit></annualProfit>\n"
				+"		<annualIncome></annualIncome>\n"
				+"		<totalDebt></totalDebt>\n"
				+"		<totalAssets></totalAssets>\n"
				+"		<finRepType></finRepType>\n"
				+"		<legalReprNationCode></legalReprNationCode>\n"
				+"		<legalReprPhoto></legalReprPhoto>\n"
				+"		<legalReprAddr></legalReprAddr>\n"
				+"		<legalReprTel></legalReprTel>\n"
				+"		<legalReprIdentNo></legalReprIdentNo>\n"
				+"		<legalReprIdentType></legalReprIdentType>\n"
				+"		<legalReprGender></legalReprGender>\n"
				+"		<legalReprName></legalReprName>\n"
				+"		<partnerType></partnerType>\n"
				+"		<loadCardAuditDt></loadCardAuditDt>\n"
				+"		<loadCardPwd></loadCardPwd>\n"
				+"		<loanCardStat></loanCardStat>\n"
				+"		<loanCardNo></loanCardNo>\n"
				+"		<loanCardFlag></loanCardFlag>\n"
				+"		<comSpEndDate></comSpEndDate>\n"
				+"		<comSpStrDate></comSpStrDate>\n"
				+"		<zoneCode></zoneCode>\n"
				+"		<nationCode>"+nationCode+"</nationCode>\n"
				+"		<hqNationCode>"+hqNationCode+"</hqNationCode>\n"
				+"		<orgBizCustType></orgBizCustType>\n"
				+"		<churcustype></churcustype>\n"
				+"		<jointCustType></jointCustType>\n"
				+"		<creditCode>"+creditCode+"</creditCode>\n"
				+"		<lncustp></lncustp>\n"
				+"		<orgCustType></orgCustType>\n"
				+"		<custName></custName>\n"
				+"		<ifOrgSubType></ifOrgSubType>\n"
				+"	</org>\n";
		reQxmlorg.append(Hxml);
		reQxmlorg.append(orgIdentifier);
		reQxmlorg.append(customer);
		reQxmlorg.append(contmeth);
		// reQxmlorg.append(crossindex);
		reQxmlorg.append(org);
		reQxmlorg.append("<belongManager>\n");
		reQxmlorg.append("		<custManagerNo>" + auth.getUserId()
				+ "</custManagerNo>\n");
		reQxmlorg.append("		<mainType>1</mainType>\n");
		reQxmlorg.append("		<validFlag></validFlag>\n"
				+ "		<startDate></startDate>\n" + "		<endDate></endDate>\n"
				+ "		<custManagerType></custManagerType>\n");
		reQxmlorg.append("</belongManager>\n");

		reQxmlorg.append("<belongBranch>\n");
		reQxmlorg.append("		<belongBranchNo>" + auth.getUnitId()
				+ "</belongBranchNo>\n");
		reQxmlorg.append("</belongBranch>\n" + "</RequestBody>\n"
				+ "</TransBody>\n");
		msg = reQxmlorg.toString();
		log.info("msg:" + msg);
		Map rep = process(msg);
		return rep;
	}

	/**
	 * 处理返回报文
	 * 
	 * @param mxlmsg
	 * @return
	 * @throws Exception
	 */
	public static Map process(String mxlmsg) throws Exception {
		Map idsMap = new HashMap();
		System.out.println("访问报文:" + mxlmsg);
		log.info("开户请求报文:" + mxlmsg);
		String msg = mxlmsg;
		// String ip = "10.20.34.108";
		// int port = 9500;
		String port = FileTypeConstance.getBipProperty("ECIF.PORT");
		String ip = FileTypeConstance.getBipProperty("ECIF.IP");
		// String port="9500";
		// String ip="127.0.0.1";
		NioClient cl = new NioClient(ip, Integer.parseInt(port));
		String resp = null;

		try {
			resp = cl.SocketCommunication(String.format("%08d",
					msg.getBytes("GBK").length)
					+ msg);
		} catch (IOException e) {
			// System.out.printf("destSysNo:%s, ip:%s, port:%d\n", ip, port);
			throw e;
		}
		System.out.println("resp:\n" + resp);
		log.info("开户返回报文:" + resp);
		String custNo = "";
		String identId = "";// 证件ID acrm_f_ci_cust_identifier
		String contmethId = "";// 联系ID
		String addrId = "";// 地址ID
		String custId = "";
		String ErrorCode = "";
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
			ErrorCode = root.element("ResponseTail").element("TxStatCode")
					.getTextTrim();
			if (!"000000".equals(TxStatCode)) {
				throw new BizException(1, 0, "10001", TxStatDesc
						+ "调用ECIF接口失败或超时,请稍后重试！");
			}
			if ("000000".equals(TxStatCode)) {
				// 返回技术主键：
				custNo = root.element("ResponseBody").element("custNo")
						.getTextTrim();
				if (root.element("ResponseBody").element("identId") != null) {
					identId = root.element("ResponseBody").element("identId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("contmethId") != null) {
					contmethId = root.element("ResponseBody")
							.element("contmethId").getTextTrim();
				}
				if (root.element("ResponseBody").element("addrId") != null) {
					addrId = root.element("ResponseBody").element("addrId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("custId") != null) {
					custId = root.element("ResponseBody").element("custId")
							.getTextTrim();
				}
				idsMap.put("custNo", custNo);
				idsMap.put("identId", identId);
				idsMap.put("contmethId", contmethId);
				idsMap.put("addrId", addrId);
				idsMap.put("custId", custId);
			}
		} catch (BizException e) {
			// e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}
		return idsMap;
	}

	/**
	 * 判断该客户信息是否正在走流程中
	 * 
	 * @param jobName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int judgeExist(String instancePre) {
		List list = this.em
				.createNativeQuery(
						"SELECT DISTINCT A.USER_NAME||'['||T.AUTHOR||']' AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
								+ instancePre
								+ "%'"
								+ " union  "
								+ " select DISTINCT ac.USER_NAME || '[' || his.update_user || ']' AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
								+ " left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
								+ " where his.cust_id=substr('"
								+ instancePre
								+ "',instr ('"
								+ instancePre
								+ "','_')+1) and his.appr_flag='0' ")
				.getResultList();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			throw new BizException(1, 0, "1002", "客户记录被锁定，操作员" + list.get(0));
		}
		return 1;
	}

	/**
	 * 增加修改历史信息
	 * @param jarray 具体修改项
	 * @param date 修改日期
	 * @param flag 修改标识 毫秒级日期long
	 * @param type 修改类型
	 */
	public void bathsave(JSONArray jarray, Date date, String flag, String type) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		@SuppressWarnings("unchecked")
		List<ReviewMapping> list = this.em.createQuery(
				"select c from ReviewMapping c where c.moduleItem='" + type
						+ "' order by c.tableName,c.pageColumn")
				.getResultList();
		if (jarray.size() > 0) {
			for (int i = 0; i < jarray.size(); ++i) {
				JSONObject wa = (JSONObject) jarray.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem") == null ? "" : String
						.valueOf(wa.get("updateItem")));
				ws.setUpdateBeCont(wa.get("updateBeCont") == null ? "" : String
						.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont") == null ? "" : String
						.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView") == null ? ""
						: String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId") == null ? ""
						: String.valueOf(wa.get("updateTableId")));
				// 1、文本，2、日期
				ws.setFieldType(wa.get("fieldType") == null ? "1" : String
						.valueOf(wa.get("fieldType")));
				ws.setApprFlag("");// 0审核中,1审核通过,2审核拒绝
				for (int k = 0; k < list.size(); k++) {
					if ((String.valueOf(wa.get("updateItemEn"))).equals(list
							.get(k).getPageColumn())) {
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
				ws.setUpdateUser(currenUserId);
				ws.setUpdateDate(date);
				ws.setUpdateFlag(flag);
				if (ws.getUpdateTable() == null
						|| "".equals(ws.getUpdateTable())
						|| ws.getUpdateItemEn() == null
						|| "".equals(ws.getUpdateItemEn())) {
					log.warn(type + "-----字段映射未找到(忽略该字段): "
							+ String.valueOf(wa.get("updateItemEn")));
					continue;
				}
				super.save(ws);
			}
		}
	}

	/**
	 * 校验证件1号码是否唯一
	 * 
	 * @param jFirst
	 * @param custId
	 */
	public void identNoExist(JSONArray jFirst, String custId) {
		if (jFirst == null) {
			return;
		}
		// 校验证件1号码是否唯一
		String identNo = "";
		for (int i = 0; i < jFirst.size(); ++i) {
			JSONObject tempObj = (JSONObject) jFirst.get(i);
			if ("IDENT_NO".equals(tempObj.get("updateItemEn"))) {
				identNo = tempObj.get("updateAfCont") == null ? "" : String
						.valueOf(tempObj.get("updateAfCont"));
				break;
			}
		}
		if (!"".equals(identNo)) {
			List<?> list = this.em.createNativeQuery(
					"SELECT C.CUST_NAME FROM ACRM_F_CI_CUSTOMER C WHERE C.IDENT_NO = '"
							+ identNo + "'").getResultList();
			if (list != null && list.size() > 0 && list.get(0) != null) {
				throw new BizException(1, 0, "1002", "证件号码不能与客户（" + list.get(0)
						+ "）重复！");
			}
		}
	}
	/**
	 * 处理企业规模
	 * @param custId
	 * @param custState
	 * @param Industry
	 * @param Employee
	 * @param AnnualIncome
	 * @param TotalAssets
	 * @param tempFlag
	 * @param date
	 */
	public void processEntScale(
			String custId, String custState, 
			String Industry, String Employee, 
			String AnnualIncome, String TotalAssets,
			String tempFlag, Date date){
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String querySql = " SELECT FN_EXCHANGE_ENT_SCALE( "
				+ "	 (SELECT    CASE WHEN  INSTR(CODE,'A')>0  THEN   '01'     "+ // --农、林、牧、渔业
				"		        WHEN  INSTR(CODE,'B')>0 OR INSTR(CODE,'C')>0 "
				+ "						OR INSTR(CODE,'D')>0    THEN '02'   "+ // --工业
				"		        WHEN  INSTR(CODE,'E')>0  THEN '03'     "+ // --建筑业
				"		        WHEN  INSTR(CODE,'51')>0 THEN '04'     "+ // --批发业
				"		        WHEN  INSTR(CODE,'52')>0 THEN '05'     "+ // --零售业
				"		        WHEN  INSTR(CODE,'54')>0 OR INSTR(CODE,'55')>0 "
				+ "						OR INSTR(CODE,'56')>0 OR INSTR(CODE,'57')>0"
				+ "						 OR  INSTR(CODE,'58')>0THEN '06' "+ // --交通运输业
				"		        WHEN  INSTR(CODE,'59')>0 THEN '07'       "+ // --仓储业
				"		        WHEN  INSTR(CODE,'60')>0 THEN '08'       "+ // --邮政业
				"		        WHEN  INSTR(CODE,'61')>0 THEN '09'       "+ // --住宿业
				"		        WHEN  INSTR(CODE,'62')>0 THEN '10'       "+ // --餐饮业
				"		        WHEN  INSTR(CODE,'631')>0 OR INSTR(CODE,'64')>0 THEN '11' "+ // --信息传输业
				"		        WHEN  INSTR(CODE,'65')>0 THEN '12'        "+ // --软件和信息技术服务业
				"		        WHEN  INSTR(CODE,'701')>0 THEN '13'       "+ // --房地产开发经营
				"		        WHEN  INSTR(CODE,'702')>0 THEN '14'       "+ // --物业管理
				"		        WHEN  INSTR(CODE,'L')>0 THEN '15'         "+ // --租赁和商务服务业
				"		        ELSE '16'     "+ // --其他
				"		        END AS HY     "
				+ "		     FROM(             "
				+ "		     SELECT to_char(WM_CONCAT(T.F_CODE)) AS CODE      "
				+ "		     FROM ACRM_F_CI_BUSI_TYPE T               "
				+ "		     START WITH T.F_CODE ='"+ Industry+ "'"
				+ "		     CONNECT BY T.F_CODE=PRIOR T.PARENT_CODE) ) "
				+ " ,'"+ Employee+ "','"+ AnnualIncome+ "','"+ TotalAssets+ "')AS ENT_SCALE"
				+ " FROM ACRM_F_CI_CUSTOMER O WHERE O.CUST_ID ='"
				+ custId + "'  and rownum=1";
		//企业规模根据客户状态决定如果是A则用存款规模覆盖银监规模，如果是B则重新生成，并设为存款规模和银监规模
		String entScale = null;
		String entScaleCk = null;
		if("A".equals(custState)){
			log.info(String.format("客户状态：%s,存款企业规模覆盖银监企业规模", custState));
			AcrmFCiOrg acrmFCiOrg = baseDAO.getEntityManager().find(AcrmFCiOrg.class, custId);
			entScale = acrmFCiOrg.getEntScale();
			entScaleCk = acrmFCiOrg.getEntScaleCk();
			if((entScaleCk==null&&entScale!=null)||(entScaleCk!=null&&!entScaleCk.equals(entScale))){
				log.info("修改企业规模，更新企业规模为："+entScale);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setUpdateDate(date);
				ws.setUpdateBeCont(entScale);
				ws.setUpdateAfCont(entScaleCk);
				ws.setCustId(custId);
				ws.setUpdateFlag(tempFlag);
				ws.setUpdateItemEn("ENT_SCALE");
				ws.setUpdateItem("企业规模(银监)");
				ws.setUpdateUser(auth.getUserId());
				ws.setUpdateTable("ACRM_F_CI_ORG");
				ws.setFieldType("1");
				super.save(ws);
			}
		}else{
			if (!("".equals(Industry) || null == Industry)) {
				log.info(String.format("客户状态：%s,生成企业规模(存款和银监一致)", custState));
				List<Object> list = baseDAO.findByNativeSQLWithNameParam(querySql, new HashMap());
				entScaleCk = list.get(0).toString();
				entScale = entScaleCk;
				log.info("新增一个企业规模，更新企业规模为："+entScaleCk);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setUpdateDate(date);
				ws.setUpdateAfCont(entScale);
				ws.setUpdateAfContView(entScale);
				ws.setCustId(custId);
				ws.setUpdateFlag(tempFlag);
				ws.setUpdateItemEn("ENT_SCALE");
				ws.setUpdateItem("企业规模(银监)");
				ws.setUpdateUser(auth.getUserId());
				ws.setUpdateTable("ACRM_F_CI_ORG");
				ws.setFieldType("1");
				super.save(ws);
				ws = new OcrmFCiCustinfoUphi();
				ws.setUpdateDate(date);
				ws.setUpdateAfCont(entScaleCk);
				ws.setUpdateAfContView(entScaleCk);
				ws.setCustId(custId);
				ws.setUpdateFlag(tempFlag);
				ws.setUpdateItemEn("ENT_SCALE_CK");
				ws.setUpdateItem("企业规模(存款)");
				ws.setUpdateUser(auth.getUserId());
				ws.setUpdateTable("ACRM_F_CI_ORG");
				ws.setFieldType("1");
				super.save(ws);
			}
		}
	}
	/**
	 * 对公客户信息 注：一次提交,一次复核,流程实例号拼接规则如下： instanceid = "CI_"+custId+"_"+flag;
	 * 对应的修改历史记录为：flag+"|0000" 加竖线及4位数字,第一位数字：0表示第一页,1表示第二页,7表示第二页发生日期
	 * 2表示第三页地址,3表示第三页联系人信息,4表示第三页联系信息，5表示第三页证件信息 6表示第四页
	 * 第二位数字,若是第三页的,1表示新增、0表示修改 第三、四位数字,若是第三页的，表示对应的修改记录条数
	 * 
	 * @param jComFirst
	 * @param Industry
	 * @param Employee
	 * @param AnnualIncome
	 * @param TotalAssets
	 * @param jComSecond
	 * @param jComSecond2
	 * @param jComAddress
	 * @param jComLinkman
	 * @param jComThreeContact
	 * @param jComThreeIdent
	 * @param custId
	 * @param custName
	 * @param custState 客户状态
	 * @return
	 * @throws Exception
	 */
	public List<?> commitFsxComAll(JSONArray jComFirst, String Industry,
			String Employee, String AnnualIncome, String TotalAssets,
			JSONArray jComSecond, JSONArray jComSecond2, JSONArray jComAddress,
			JSONArray jComLinkman, JSONArray jComThreeContact,
			JSONArray jComThreeIdent, JSONArray jComFourth, String custId,
			String custName, String submitFlag,String custState) throws Exception {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Map> returnList = new ArrayList();
		Map<String, String> returnMap = null;
		DecimalFormat df = new DecimalFormat("00");
		String flag = DateUtils.currentTimeMillis();// 修改标识更改为毫秒级
		Date date = new Date();
		String tempFlag = "";
		
		if (submitFlag.equals("true")) {// 走工作流时
			log.info("该客户为既有客户，需要走流程，初始化流程信息");
			String instanceid = "CF_" + custId + "_" + flag;
			String jobName = "对公客户复核信息_" + custName;// 自定义流程名称
			String wfid_1 = "1000000"; // 流程号
			String currNode_1 = "1000000_a3";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String nextNode_1 = "1000000_a4";
			log.info("初始化下一节点编号为：" + nextNode_1);
			List<?> list = auth.getRolesInfo();
			for (Object m : list) {
				Map<?, ?> map = (Map<?, ?>) m;
				paramMap.put("role", map.get("ROLE_CODE"));
				// if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
				// nextNode_1 = "79_a4";
				// // continue;
				// } else if ("R302".equals(map.get("ROLE_CODE"))
				// || "R303".equals(map.get("ROLE_CODE"))) {// 个法金ARM,RM
				// nextNode_1 = "79_a5";
				// // continue;
				// } else
				if ("R104".equals(map.get("ROLE_CODE"))// 总行法金ARM
				        || "R105".equals(map.get("ROLE_CODE"))// 总行法金ARM
				        || "R304".equals(map.get("ROLE_CODE"))// 法金ARM
				        || "R305".equals(map.get("ROLE_CODE"))) {// 法金RM
					log.info("当前用户的角色信息是否在(R104:总行法金ARM,R105:总行法金ARM,R304:法金ARM,R305:法金RM)之内，修改下一节点编号为："+nextNode_1);
					nextNode_1 = "1000000_a4";
					// continue;
				}
				if ("admin".equals(map.get("ROLE_CODE"))) {
					log.info("提示:该客户为系统管理员，不允许提交");
				}
			}
			returnMap = new HashMap<String, String>();
			returnMap.put("wfid", wfid_1);
			returnMap.put("currNode", currNode_1);
			returnMap.put("nextNode", nextNode_1);
			returnMap.put("instanceid", instanceid);
			returnMap.put("jobName", jobName);
			returnList.add(returnMap);
			/**
			 * 统一初始化所有流程
			 */
			for (int i = 0; i < returnList.size(); i++) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = returnList.get(i);
				super.initWorkflowByWfidAndInstanceid(map.get("wfid"), map.get("jobName"), paramMap, map.get("instanceid"));
				map.remove("jobName");// 避免太长传值到前台会造成问题
			}
		}

		// 客户信息管理第一页
		if (jComFirst.size() > 0) {
			tempFlag = flag + "|0000";
			log.info("客户信息管理第一页有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			this.bathsave(jComFirst, date, tempFlag, "客户信息管理第一页");
			// 处理企业规模
			processEntScale(
					custId, custState, 
					Industry, Employee, 
					AnnualIncome, TotalAssets,
					tempFlag, date);
			if (submitFlag.equals("false")) {// 一般的保存
				log.info("当前客户没有核心客户号，不是既有客户,执行一般的保存");
				// 调用交易接口后保存
				/*if (!("".equals(Industry) || null == Industry)) {
					log.info("行业类别信息不为空，进行企业规模校验");
					Connection conn = this.em.unwrap(java.sql.Connection.class);
					Statement stmt = null;
					ResultSet rs = null;
					Result result = null;
					String entScale = "";
					try {
						log.info("企业规模只能在临时户时做校验>>>>>>>>>");
						String querySql = " SELECT FN_EXCHANGE_ENT_SCALE( "
								+ "	 (SELECT    CASE WHEN  INSTR(CODE,'A')>0  THEN   '01'     "+ // --农、林、牧、渔业
								"		        WHEN  INSTR(CODE,'B')>0 OR INSTR(CODE,'C')>0 "
								+ "						OR INSTR(CODE,'D')>0    THEN '02'   "+ // --工业
								"		        WHEN  INSTR(CODE,'E')>0  THEN '03'     "+ // --建筑业
								"		        WHEN  INSTR(CODE,'51')>0 THEN '04'     "+ // --批发业
								"		        WHEN  INSTR(CODE,'52')>0 THEN '05'     "+ // --零售业
								"		        WHEN  INSTR(CODE,'54')>0 OR INSTR(CODE,'55')>0 "
								+ "						OR INSTR(CODE,'56')>0 OR INSTR(CODE,'57')>0"
								+ "						 OR  INSTR(CODE,'58')>0THEN '06' "+ // --交通运输业
								"		        WHEN  INSTR(CODE,'59')>0 THEN '07'       "+ // --仓储业
								"		        WHEN  INSTR(CODE,'60')>0 THEN '08'       "+ // --邮政业
								"		        WHEN  INSTR(CODE,'61')>0 THEN '09'       "+ // --住宿业
								"		        WHEN  INSTR(CODE,'62')>0 THEN '10'       "+ // --餐饮业
								"		        WHEN  INSTR(CODE,'631')>0 OR INSTR(CODE,'64')>0 THEN '11' "+ // --信息传输业
								"		        WHEN  INSTR(CODE,'65')>0 THEN '12'        "+ // --软件和信息技术服务业
								"		        WHEN  INSTR(CODE,'701')>0 THEN '13'       "+ // --房地产开发经营
								"		        WHEN  INSTR(CODE,'702')>0 THEN '14'       "+ // --物业管理
								"		        WHEN  INSTR(CODE,'L')>0 THEN '15'         "+ // --租赁和商务服务业
								"		        ELSE '16'     "+ // --其他
								"		        END AS HY     "
								+ "		     FROM(             "
								+ "		     SELECT to_char(WM_CONCAT(T.F_CODE)) AS CODE      "
								+ "		     FROM ACRM_F_CI_BUSI_TYPE T               "
								+ "		     START WITH T.F_CODE ='"+ Industry+ "'"
								+ "		     CONNECT BY T.F_CODE=PRIOR T.PARENT_CODE) ) "
								+ " ,'"+ Employee+ "','"+ AnnualIncome+ "','"+ TotalAssets+ "')AS ENT_SCALE"
								+ " FROM ACRM_F_CI_CUSTOMER O WHERE O.CUST_ID ='"
								+ custId + "'  and rownum=1";
						stmt = conn.createStatement();
						log.info("--执行查询SQL:" + querySql);
						rs = stmt.executeQuery(querySql);
						result = ResultSupport.toResult(rs);
						entScale = result.getRows()[0].get("ENT_SCALE").toString();
						log.info(String.format("生成企业银监规模成功：%s", entScale));
						// entScale=rs.getString(1);
					} catch (SQLException e) {
						e.printStackTrace();
						throw e;
					}
					@SuppressWarnings("unchecked")
					List<OcrmFCiCustinfoUphi> list2 = this.em.createQuery(
							"select v from OcrmFCiCustinfoUphi v where v.custId='"+ custId + "' and  v.updateFlag='"+ tempFlag
									+ "' and v.updateItemEn='ENT_SCALE' ")
							.getResultList();
					if (list2.size() > 0) {// 修改了企业规模
						log.info("修改了企业规模，更新企业规模为："+entScale);
						OcrmFCiCustinfoUphi ws = null;
						ws = list2.get(0);
						ws.setUpdateDate(date);
						ws.setUpdateAfCont(entScale);
						super.save(ws);
					} else {// 新增一个企业规模
						log.info("新增一个企业规模，更新企业规模为："+entScale);
						OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
						ws.setUpdateDate(date);
						ws.setUpdateAfCont(entScale);
						ws.setCustId(custId);
						ws.setUpdateFlag(tempFlag);
						ws.setUpdateItemEn("ENT_SCALE");
						ws.setUpdateItem("企业规模(银监)");
						ws.setUpdateUser(auth.getUserId());
						ws.setUpdateTable("ACRM_F_CI_ORG");
						ws.setFieldType("1");
						super.save(ws);
					}
					// ws.setUpdateAfCont("CS02");
				}*/
				log.info("开始调用ECIF接口更新第一页修改过的数据，并在更新成功后更新本地数据");
				dwFormEcif(custId, custName,tempFlag, auth);
			}
		}
		// 客户信息管理第二页
		if (jComSecond.size() > 0) {
			log.info("客户信息管理第二页有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			tempFlag = flag + "|1000";
			this.bathsave(jComSecond, date, tempFlag, "客户信息管理第二页");
			if (submitFlag.equals("false")) {// 一般的保存
				// 调用交易接口后保存
				dwFormEcif(custId,custName, tempFlag, auth);
			}
		}
		// 客户信息管理第四页
		if (jComFourth.size() > 0) {
			log.info("客户信息管理第四页有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			tempFlag = flag + "|6000";
			this.bathsave(jComFourth, date, tempFlag, "客户信息管理第四页");

			if (submitFlag.equals("false")) {// 一般的保存
				// 调用交易接口后保存
				dwFormEcif(custId, custName,tempFlag, auth);
			}
		}
		// 客户信息管理第三页：地址
		if (jComAddress.size() > 0) {
			log.info("客户信息管理第三页地址有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			for (int i = 0; i < jComAddress.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jComAddress.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1": "0";// 1表示新增,0表示修改
				tempFlag = flag + "|2" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "客户信息管理第三页");
				if (submitFlag.equals("false")) {// 一般的保存
					// 调用交易接口
					dwGridEcif(custId, tempFlag, auth, 2, isChange);
				}
			}
		}
		// 客户信息管理第三页：联系人
		if (jComLinkman.size() > 0) {
			log.info("客户信息管理第三页联系人有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			for (int i = 0; i < jComLinkman.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jComLinkman
						.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("PER_IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				tempFlag = flag + "|3" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "客户信息管理第三页");
				if (submitFlag.equals("false")) {// 一般的保存
					// 调用交易接口
					dwGridEcif(custId, tempFlag, auth, 3, isChange);
				}
			}
		}

		// 客户信息管理第三页：联系信息
		if (jComThreeContact.size() > 0) {
			log.info("客户信息管理第三页联系信息有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			for (int i = 0; i < jComThreeContact.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jComThreeContact
						.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				tempFlag = flag + "|4" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "客户信息管理第三页");
				if (submitFlag.equals("false")) {// 一般的保存
					// 调用交易接口
					dwGridEcif(custId, tempFlag, auth, 4, isChange);
				}
			}
		}
		// 客户信息管理第三页：证件信息
		if (jComThreeIdent.size() > 0 && !jComThreeIdent.get(0).toString().equals("null")) {
			log.info("客户信息管理第三页证件信息有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			for (int i = 0; i < jComThreeIdent.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jComThreeIdent
						.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				tempFlag = flag + "|5" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "客户信息管理第三页");
				if (submitFlag.equals("false")) {// 一般的保存
					// 调用交易接口
					dwGridEcif(custId, tempFlag, auth, 5, isChange);
				}
			}
		}
		// 客户信息管理第二页：发生日期信息
		if (jComSecond2.size() > 0 && !jComSecond2.get(0).toString().equals("null")) {
			log.info("客户信息管理第二页发生日期有数据修改，将修改过的数据保存到表OCRM_F_CI_CUSTINFO_UPHIS中");
			for (int i = 0; i < jComSecond2.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jComSecond2
						.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				tempFlag = flag + "|7" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "客户信息管理第二页");
				if (submitFlag.equals("false")) {// 一般的保存
					// 调用交易接口
					dwGridEcif(custId, tempFlag, auth, 5, isChange);
				}
			}
		}
		return returnList;
	}

	/**
	 * 审批同意 先拼接报文、发送报文、然后根据返回报文处理CRM更新
	 * 注：此处的异常必须抛出为BizException,否则在前台不能正常提示出相应的异常信息
	 * 
	 * @param vo
	 */
	public void dwGridEcif(String custId, String updateFlag, AuthUser auth,
			int pageItem, String modifyFlag) {
		try {
			// 调用交易接口
			String sql = "select v from OcrmFCiCustinfoUphi v where v.custId='"
					+ custId + "' and  v.updateFlag='" + updateFlag
					+ "' order by v.updateTable,v.updateTableId";
			String responseXml = TranCrmToEcif(custId, updateFlag, "", sql);
			boolean responseFlag = doResXms(responseXml);
			// 判断如果是新增调用操、则获取返回的主键信息,如果返回为null则调用,id_sequence.nextval生成,待现场人员实现
			String pk_xx = getResXms(responseXml);

			// 增加处理返回报文、判断交易返回结果,用于CRM业务更新时使用
			if (responseFlag) {
				// 交易成功,拼装CRM 业务SQL并执行
				if ("0".equals(modifyFlag)) {
					joinUpdateSql(custId, updateFlag, auth);
				} else {
					joinInsertSql(pk_xx, custId, updateFlag, auth);
				}

				// 客户信息变更同步
				if (pageItem == 2) { // 地址
					NewSynchroData.addressSync(
							this.em.unwrap(java.sql.Connection.class),
							updateFlag, custId);
				} else if (pageItem == 4) { // 联系信息
					NewSynchroData.contmethSync(
							this.em.unwrap(java.sql.Connection.class),
							updateFlag, custId);
				}

			} else {
				throw new BizException(1, 0, "0000",
						"Warning-168：数据信息同步失败，请及时联系IT部门！");
			}

		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			log.error("处理过程中发现异常(错误):");
			throw new BizException(1, 0, "0000",
					"Warning-169：数据信息同步失败，请及时联系IT部门！");
		}
	}

	/**
	 * 封装请求报文 SQL 变量必须先赋值才能调用此方法
	 * 
	 * @param cust_id
	 * @param update_flag
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String TranCrmToEcif(String custId, String updateFlag,
			String updateTable, String sql) throws Exception {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		@SuppressWarnings("unchecked")
		List<OcrmFCiCustinfoUphi> list1 = this.em.createQuery(sql)
				.getResultList();
		RequestHeader header = new RequestHeader();
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");
		header.setReqSysCd("CRM");
		header.setReqSeqNo(df20.format(new Date()));
		header.setReqDt(df8.format(new Date()));
		header.setReqTm(df10.format(new Date()));
		header.setDestSysCd("ECIF");
		header.setChnlNo("82");
		header.setBrchNo("503");
		header.setBizLine("209");
		header.setTrmNo("TRM10010");
		header.setTrmIP("127.0.0.1");

		// 前提是先拼装好SQL
		// Result result = querySQL(vo);
		StringBuffer sb = new StringBuffer();
		sb.append("<RequestBody>");
		sb.append("<txCode>updateOrgCustInfoNew</txCode>");
		sb.append("<txName>修改机构客户基本信息</txName>");
		sb.append("<authType>1</authType>");
		sb.append("<authCode>1010</authCode>");
		sb.append("<custNo>" + custId + "</custNo>");
		// List<Map<Object, Object>> list = new ArrayList<Map<Object,
		// Object>>();
		String updateUser = null;
		updateUser = list1.get(0).getUpdateUser();
		header.setTlrNo(updateUser);
		NewXmlTableUtil util = new NewXmlTableUtil();
		String reqXml = util.reqBodyXml(list1);// 将从表里面拿到的字段和字段内容，表名进行分装
		sb.append(reqXml);

		sb.append("</RequestBody>");
		String Xml = new String(sb.toString().getBytes());
		String req = TransClient.process(header, Xml);
		return req;
	}

	/**
	 * 处理返回报文
	 * 
	 * @param xml
	 * @return
	 */
	public boolean doResXms(String xml) throws Exception {
		try {
			xml = xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String TxStatCode = root.element("ResponseTail")
					.element("TxStatCode").getTextTrim();
			if (TxStatCode != null && !TxStatCode.trim().equals("")
					&& (TxStatCode.trim().equals("000000"))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 处理返回id
	 * 
	 * @param xml
	 * @return
	 */
	public String getResXms(String xml) throws Exception {
		String id = "";
		try {
			xml = xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			if (root.element("ResponseBody").element("addrId") != null) {
				id = root.element("ResponseBody").element("addrId")
						.getTextTrim();
			}
			if (root.element("ResponseBody").element("contmethId") != null) {
				id = root.element("ResponseBody").element("contmethId")
						.getTextTrim();
			}
			if (root.element("ResponseBody").element("identId") != null) {
				id = root.element("ResponseBody").element("identId")
						.getTextTrim();
			}
			if (root.element("ResponseBody").element("linkmanId") != null) {
				id = root.element("ResponseBody").element("linkmanId")
						.getTextTrim();
			}
			if (root.element("ResponseBody").element("issueStockId") != null) {
				id = root.element("ResponseBody").element("issueStockId")
						.getTextTrim();
			}
			if (root.element("ResponseBody").element("belongManagerId") != null) {
				id = root.element("ResponseBody").element("belongManagerId")
						.getTextTrim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	// /**
	// * 处理返回ident_id,addrId
	// * @param xml
	// * @return
	// */
	// public String getResXms(String xml,String ids) throws Exception{
	// String id = "";
	// try{
	// xml=xml.substring(8);
	// Document doc = DocumentHelper.parseText(xml);
	// Element root = doc.getRootElement();
	// if(root.element("ResponseBody").element(ids)!=null){
	// id = root.element("ResponseBody").element(ids).getTextTrim();
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// return id;
	// }
	public List<Object> searchident(String sql, int clom) {
		log.info("" + sql);
		List<Object> List = new ArrayList<Object>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet result = null;
		try {// UPDATE_TABLE
			connection = ds.getConnection();
			stmt = connection.createStatement();
			result = stmt.executeQuery(sql);
			String updateTable = "";
			while (result.next()) {
				updateTable = result.getString(1);
				List.add(updateTable);
			}
			log.info("searchident: " + List.toString());
			return List;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(result, stmt, connection);
		}
		return null;
	}

	/**
	 * 单独处理证件新增
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void addTrans(String custId, String updateFlag, AuthUser auth,
			String addSql) throws Exception {
		@SuppressWarnings("unchecked")
		List<OcrmFCiCustinfoUphi> list1 = this.em
				.createQuery(
						"select v.updateTable from OcrmFCiCustinfoUphi v  where v.custId='"
								+ custId
								+ "' and v.updateFlag='"
								+ updateFlag
								+ "'"
								+ "  and (v.updateBeCont is null or v.updateBeCont='')   and (v.updateAfCont is null or v.updateAfCont='')     "
								+ "  and (" + addSql + ")     "
								+ "  and v.updateTableId = '1'")
				.getResultList();

		for (int i = 0; i < list1.size(); ++i) {
			String updateTable = (String) (list1.get(i) != null ? list1.get(i)
					: "");
			// 调用交易接口,此SQL必须按UPDATE_TABLE项排序，否则会存在逻辑错误---TranCrmToEcif
			String sql = "select v from OcrmFCiCustinfoUphi v where v.custId='"
					+ custId + "' and  v.updateFlag='" + updateFlag
					+ "' and v.updateTable = '" + updateTable
					+ "' order by v.updateTable,v.updateTableId";

			String responseXml = TranCrmToEcif(custId, updateFlag, updateTable,
					sql);
			boolean responseFlag = doResXms(responseXml);
			// 判断如果是新增调用操、则获取返回的主键信息,
			String pk_xx = getResXms(responseXml);

			// 增加处理返回报文、判断交易返回结果,用于CRM业务更新时使用
			if (responseFlag) {
				@SuppressWarnings("unchecked")
				List<OcrmFCiCustinfoUphi> list2 = this.em
						.createQuery(
								"select v from OcrmFCiCustinfoUphi v where v.custId='"
										+ custId
										+ "' and  v.updateFlag='"
										+ updateFlag
										+ "' and v.updateTable = '"
										+ updateTable
										+ "'  and v.updateTableId = '1' order by v.updateTable,v.updateTableId")
						.getResultList();
				OcrmFCiCustinfoUphi ws = null;
				for (int j = 0; j < list2.size(); ++j) {
					ws = list2.get(0);
					ws.setUpdateAfCont(pk_xx);
					super.save(ws);
				}
			} else {
				throw new BizException(1, 0, "0000",
						"Warning-168：数据信息同步失败，请及时联系IT部门！");
			}
		}
	}

	public void joinSql(String custId, String custName,String flag, AuthUser auth)
			throws Exception {
		ArrayList<String> SQLS = null;
		@SuppressWarnings("unchecked")
		List<OcrmFCiCustinfoUphi> list1 = this.em.createQuery(
				"select v from OcrmFCiCustinfoUphi v where v.custId='" + custId
						+ "' and  v.updateFlag='" + flag
						+ "' order by v.updateTable,v.updateTableId")
				.getResultList();
		SQLS = new ArrayList<String>();
		boolean tableFlag = false; // 是否同一表操作标识
		boolean insertFlag = false; // 插入与否标识，false表示update
		String lastTable = null; // 上一次的tableName

		StringBuffer tempUpdateSql = null; // 修改的单个表语句
		String tempWhereSql = null;
		StringBuffer tempInsertSql = null; // 插入语句
		StringBuffer tempValuesSql = null;
		int totalCount = list1.size();
		for (int i = 0; i < list1.size(); ++i) {
			totalCount = totalCount - 1;
			String dataOld = list1.get(i).getUpdateBeCont() != null ? (String) list1
					.get(i).getUpdateBeCont() : "";// 修改前内容
			dataOld = StringEscapeUtils.escapeSql(dataOld);// sql特殊字符转义
			String dataNew = list1.get(i).getUpdateAfCont() != null ? (String) list1
					.get(i).getUpdateAfCont() : "";// 修改后内容
			dataNew = StringEscapeUtils.escapeSql(dataNew);// sql特殊字符转义
			String colName = list1.get(i).getUpdateItemEn() != null ? (String) list1
					.get(i).getUpdateItemEn() : "";// 修改项目字段名
			String tabName = list1.get(i).getUpdateTable() != null ? (String) list1
					.get(i).getUpdateTable() : "";// 修改表名
			String pkFlag = list1.get(i).getUpdateTableId() != null ? (String) list1
					.get(i).getUpdateTableId() : "";// 是否主键字段标识
			String fieldType = list1.get(i).getFieldType() != null ? (String) list1
					.get(i).getFieldType() : "";// 字段类型

			// 排除表、字段数据错误
			if (tabName == null || "".equals(tabName) || colName == null
					|| "".equals(colName)) {
				continue;
			}
			if (lastTable != null && lastTable.equals(tabName)) {
				tableFlag = true;
			} else {
				tableFlag = false;
			}
			// 添加更新字段sql
			if (!tableFlag) {// 不同的表时，首先判断是否新增，是新增则insert
				if (tempUpdateSql != null
						&& !"".equals(tempUpdateSql.toString())) {
					if (tempWhereSql == null) {
						if (lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO")
								|| lastTable
										.equals("ACRM_F_CI_ORG_EXECUTIVEINFO1")) {
							tempWhereSql = " WHERE ORG_CUST_ID='" + custId
									+ "'";
						} else if (lastTable
								.equals("OCRM_F_CI_GROUP_MEMBER_NEW")) {
							tempWhereSql = " WHERE CUS_ID='" + custId + "'";
						} else {
							tempWhereSql = " WHERE CUST_ID='" + custId + "'";
						}
					}
					SQLS.add(tempUpdateSql.toString() + tempWhereSql);
					log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString()
							+ tempWhereSql + "]");
				}
				if (tempInsertSql != null && tempValuesSql != null
						&& !"".equals(tempInsertSql.toString())
						&& !"".equals(tempValuesSql.toString())) {
					SQLS.add(tempInsertSql.toString() + ") "
							+ tempValuesSql.toString() + ")");
					log.info("添加批量INSERT SQL:[" + tempInsertSql.toString()
							+ ") " + tempValuesSql.toString() + ") ]");
				}

				lastTable = tabName;
				tempUpdateSql = new StringBuffer();
				tempWhereSql = null;
				tempInsertSql = new StringBuffer();
				tempValuesSql = new StringBuffer();

				// 判断是否新增操作
				if ("1".equals(pkFlag)
						&& ((dataNew == null || "".equals(dataNew)) || ((dataNew != null && !""
								.equals(dataNew)) && (dataOld == null || ""
								.equals(dataOld))))) {
					insertFlag = true;
				} else {
					insertFlag = false;
				}
				if (insertFlag) {
					if (dataNew == null || "".equals(dataNew)) {
						// 当前台未指定主键时,则使用默认主键
						if (dataOld == null || "".equals(dataOld)) {
							tempInsertSql.append(" INSERT INTO " + tabName
									+ "(" + colName);
							tempValuesSql
									.append(" VALUES (ID_SEQUENCE.NEXTVAL");
						} else if (dataOld.indexOf("NEXTVAL") > -1) {// 前台指定了主键序列
							tempInsertSql.append(" INSERT INTO " + tabName
									+ "(" + colName);
							tempValuesSql.append(" VALUES (" + dataOld);
						} else {// 前台指定了具体的主键值
							tempInsertSql.append(" INSERT INTO " + tabName
									+ "(" + colName);
							tempValuesSql.append(" VALUES ('" + dataOld + "'");
						}
					} else {// ECIF交易返回了主键值
						tempInsertSql.append(" INSERT INTO " + tabName + "("
								+ colName);
						tempValuesSql.append(" VALUES ('" + dataNew + "'");
					}
				} else {
					if ("2".equals(fieldType)) {
						tempUpdateSql.append(" UPDATE " + tabName + " SET "
								+ colName + "=TO_DATE('" + dataNew
								+ "','yyyy-MM-dd')");
					} else {
						tempUpdateSql.append(" UPDATE " + tabName + " SET "
								+ colName + "='" + dataNew + "'");
					}
				}
			} else {
				// 同一张表时,直接拼接sql,判断是新增还是修改
				if (insertFlag) {// 新增
					if ("2".equals(fieldType)) {// 若是日期类型
						tempInsertSql.append(" ," + colName);
						tempValuesSql.append(" ,TO_DATE('" + dataNew
								+ "','yyyy-MM-dd')");
					} else {
						tempInsertSql.append(" ," + colName);
						tempValuesSql.append(" ,'" + dataNew + "'");
					}
				} else {// 修改
					if ("2".equals(fieldType)) {
						tempUpdateSql.append(" ," + colName + "=TO_DATE('"
								+ dataNew + "','yyyy-MM-dd')");
					} else {
						tempUpdateSql.append(" ," + colName + "='" + dataNew
								+ "'");
					}
				}
			}
			// 添加update where条件sql
			if (!insertFlag && "1".equals(pkFlag)) {
				tempWhereSql = " WHERE " + colName + "='" + dataNew + "'";
			}
			// 判断是否最后一行操作右是增加相应的sql
			if (totalCount == 0) {
				if (tempUpdateSql != null
						&& !"".equals(tempUpdateSql.toString())) {
					if (tempWhereSql == null) {
						if (lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO")
								|| lastTable
										.equals("ACRM_F_CI_ORG_EXECUTIVEINFO1")) {
							tempWhereSql = " WHERE ORG_CUST_ID='" + custId
									+ "'";
						} else if (lastTable
								.equals("OCRM_F_CI_GROUP_MEMBER_NEW")) {
							tempWhereSql = " WHERE CUS_ID='" + custId + "'";
						} else {
							tempWhereSql = " WHERE CUST_ID='" + custId + "'";
						}

					}
					SQLS.add(tempUpdateSql.toString() + tempWhereSql);
					log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString()
							+ tempWhereSql + "]");
				}
				if (tempInsertSql != null && tempValuesSql != null
						&& !"".equals(tempInsertSql.toString())
						&& !"".equals(tempValuesSql.toString())) {
					SQLS.add(tempInsertSql.toString() + ") "
							+ tempValuesSql.toString() + ")");
					log.info("添加批量INSERT SQL:[" + tempInsertSql.toString()
							+ ") " + tempValuesSql.toString() + ") ]");
				}
			}
		}
		// 批量处理存在表别名的SQL语句
		for (int i = 0, len = SQLS.size(); i < len; i++) {
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_ADDRESS0",
					"ACRM_F_CI_ADDRESS"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_ADDRESS1",
					"ACRM_F_CI_ADDRESS"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER1", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER2", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER3", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER4", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER5", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_ORG_EXECUTIVEINFO1",
					"ACRM_F_CI_ORG_EXECUTIVEINFO"));
		}
		// 去除null值
		String SQL[] = new String[SQLS.size()];

		SQL = SQLS.toArray(new String[0]);
		if (SQL.length > 0) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			jdbcTemplate.batchUpdate(SQL);
		}
		String[] SQL2 = new String[2];
		String sql1 = " UPDATE ACRM_F_CI_CUSTOMER SET LAST_UPDATE_SYS='CRM',LAST_UPDATE_TM=SYSDATE,LAST_UPDATE_USER='"
				+ auth.getUserId() + "' where cust_id='" + custId + "'";// 修改最后更新时间
		String sql2="UPDATE   ACRM_F_CI_POT_CUS_COM P SET P.CUS_NAME='"+custName+"' WHERE P.CUS_ID=( SELECT SRC_SYS_CUST_NO"+
                    "  FROM ACRM_F_CI_CROSSINDEX  WHERE  CUST_ID='"+custId+"' AND SRC_SYS_NO = 'DMS')";
		// String
		// sql2=" UPDATE OCRM_F_CI_BELONG_CUSTMGR SET  MGR_NAME='"+auth.getUsername()+"',INSTITUTION_NAME='"+auth.getUnitName()+"' where cust_id='"+custId
		// +"'";//修改客户经理名称
		// String
		// sql3=" UPDATE OCRM_F_CI_BELONG_ORG SET INSTITUTION_CODE='"+auth.getUnitId()+"' ,INSTITUTION_NAME='"+auth.getUnitName()+"' where cust_id='"+custId
		// +"'";//修改机构名称
		// 修改企业规模的值，根据所属行业，从业人数，营业收入，资产总额来判断
		// String
		// sql4=" UPDATE ACRM_F_CI_ORG SET ACRM_F_CI_ORG.ENT_SCALE=(   SELECT FN_EXCHANGE_ENT_SCALE( (   "+
		// " SELECT   CASE WHEN  INSTR(CODE,'A')>0  THEN   '01'   "+//
		// --农、林、牧、渔业
		// "      WHEN  INSTR(CODE,'B')>0 OR INSTR(CODE,'C')>0 OR INSTR(CODE,'D')>0    THEN '02'        "+//--工业
		// "      WHEN  INSTR(CODE,'E')>0  THEN '03'    "+//--建筑业
		// "      WHEN  INSTR(CODE,'51')>0 THEN '04'    "+//--批发业
		// "      WHEN  INSTR(CODE,'52')>0 THEN '05'    "+//--零售业
		// "      WHEN  INSTR(CODE,'54')>0 OR INSTR(CODE,'55')>0 OR INSTR(CODE,'56')>0 OR INSTR(CODE,'57')>0 OR  INSTR(CODE,'58')>0THEN '06'       "+//--交通运输业
		// "      WHEN  INSTR(CODE,'59')>0 THEN '07'    "+//--仓储业
		// "      WHEN  INSTR(CODE,'60')>0 THEN '08'    "+//--邮政业
		// "      WHEN  INSTR(CODE,'61')>0 THEN '09'    "+//--住宿业
		// "      WHEN  INSTR(CODE,'62')>0 THEN '10'    "+//--餐饮业
		// "      WHEN  INSTR(CODE,'631')>0 OR INSTR(CODE,'64')>0 THEN '11'    "+//--信息传输业
		// "      WHEN  INSTR(CODE,'65')>0 THEN '12'     "+//--软件和信息技术服务业
		// "      WHEN  INSTR(CODE,'701')>0 THEN '13'    "+//--房地产开发经营
		// "      WHEN  INSTR(CODE,'702')>0 THEN '14'    "+//--物业管理
		// "      WHEN  INSTR(CODE,'L')>0 THEN '15'      "+//--租赁和商务服务业
		// "      ELSE '16'    "+//--其他
		// "      END AS HY    "+
		// " FROM(             "+
		// " SELECT to_char(WM_CONCAT(T.F_CODE)) AS CODE         "+
		// "   FROM ACRM_F_CI_BUSI_TYPE T                "+
		// "   START WITH T.F_CODE =(SELECT O.EMPLOYEE_SCALE FROM ACRM_F_CI_ORG O WHERE O.CUST_ID ='"+custId+"' )    "+
		// "   CONNECT BY T.F_CODE=PRIOR T.PARENT_CODE) A)   "+
		// "  ,O.EMPLOYEE_SCALE,O.ANNUAL_INCOME,O.TOTAL_ASSETS）     "+
		// " FROM ACRM_F_CI_ORG O             "+
		// " WHERE O.CUST_ID ='"+custId+"' ) WHERE CUST_ID='"+custId+"' ";
		SQL2[0] = sql1;
		SQL2[1]=sql2;
		// SQL2[2]=sql3;
		// SQL2[1]=sql4;
		JdbcTemplate jdbcTemplate2 = new JdbcTemplate(ds);
		jdbcTemplate2.batchUpdate(SQL2);
	};

	/**
	 * 拼接sql,将已有值进行更新
	 * 
	 * @param custId
	 * @param flag
	 * @param auth
	 * @throws Exception
	 */
	public void dwFormEcif(String custId, String custName,String flag, AuthUser auth)
			throws Exception {
		String newFlag = flag.split("|")[15];// 判断是否第一页
		// 如果第15位是0，第一页的数据需要先判断是否新增证件、地址、联系人、集团
		// 若是，同步ecif，获取客户编号；若不是直接同步ecif
		if (newFlag.equals("0")) {
			// 判断是否存在新增证件信息,若存在则优先处理证件信息
			String identifierSql = " v.updateTable = 'ACRM_F_CI_CUST_IDENTIFIER' or    v.updateTable = 'ACRM_F_CI_CUST_IDENTIFIER1' or   "
					+ "      v.updateTable = 'ACRM_F_CI_CUST_IDENTIFIER2' or    v.updateTable = 'ACRM_F_CI_CUST_IDENTIFIER3' or   "
					+ "      v.updateTable = 'ACRM_F_CI_CUST_IDENTIFIER4' or    v.updateTable = 'ACRM_F_CI_CUST_IDENTIFIER5' ";
			addTrans(custId, flag, auth, identifierSql);

			// 判断是否存在新增地址信息,若存在则优先处理地址信息
			String addressSql = " v.updateTable = 'ACRM_F_CI_ADDRESS0' or    v.updateTable = 'ACRM_F_CI_ADDRESS1'";
			addTrans(custId, flag, auth, addressSql);

			// 判断是否存在新增法人信息,若存在则优先处理法人信息
			String linkmanSql = " v.updateTable = 'ACRM_F_CI_ORG_EXECUTIVEINFO1'";
			addTrans(custId, flag, auth, linkmanSql);

		} else if (newFlag.equals("1")) {// 判断是否第二页
			// 如果第15位是1，第二页的数据需要先判断是否上市地和股票代码
			// 判断是否存在新增法人信息,若存在则优先处理法人信息
			String stockSql = " v.updateTable = 'ACRM_F_CI_ORG_ISSUESTOCK'";
			addTrans(custId, flag, auth, stockSql);
		}

		// 调用交易接口
		String sql = "select v from OcrmFCiCustinfoUphi v where v.custId='"
				+ custId + "' and  v.updateFlag='" + flag
				+ "' order by v.updateTable";
		String responseXml = TranCrmToEcif(custId, flag, "", sql);
		boolean responseFlag = doResXms(responseXml);
		if (responseFlag) {
			// 交易成功,拼装CRM 业务SQL并执行
			joinSql(custId,custName, flag, auth);
			// this.em.flush();
			if (newFlag.equals("0")) {
				// 处理等级表等由于审批时间差-产生的冗余记录
				NewSynchroData
						.deleteRedundancy(
								this.em.unwrap(java.sql.Connection.class),
								flag, custId);
			}
			// 客户信息变更同步同步
			NewSynchroData.customerSync(
					this.em.unwrap(java.sql.Connection.class), flag, custId);
			NewSynchroData.orgSync(this.em.unwrap(java.sql.Connection.class),
					flag, custId);
			NewSynchroData.sendRemind(
					this.em.unwrap(java.sql.Connection.class), flag, custId);

		} else {
			throw new BizException(1, 0, "0000",
					"Warning-168：数据信息同步失败，请及时联系IT部门！");
		}

	}

	/**
	 * 拼接业务更新SQL并add到SQLS 注：单表单条记录修改操作 ACRM_F_CI_ORG_EXECUTIVEINFO
	 * ACRM_F_CI_ADDRESS ACRM_F_CI_CONTMETH ACRM_F_CI_CUST_IDENTIFIER
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void joinUpdateSql(String custId, String updateFlag, AuthUser auth)
			throws SQLException {
		// 注：必须按order by UPDATE_TABLE,UPDATE_TABLE_ID
		// 排序处理,根据第一个字段是否主键及其值是否为空决定是insert还是update
		ArrayList<String> SQLS = null;

		@SuppressWarnings("unchecked")
		List<OcrmFCiCustinfoUphi> list1 = this.em.createQuery(
				"select v from OcrmFCiCustinfoUphi v where v.custId='" + custId
						+ "' and  v.updateFlag='" + updateFlag
						+ "' order by v.updateTable,v.updateTableId")
				.getResultList();
		SQLS = new ArrayList<String>();
		boolean tableFlag = false; // 是否同一表操作标识
		StringBuffer tempUpdateSql = null; // 修改的单个表语句
		String tempWhereSql = null;
		String lastTable = null; // 上一次的tableName
		int totalCount = list1.size();
		for (int i = 0; i < list1.size(); ++i) {
			totalCount = totalCount - 1;
			String dataNew = list1.get(i).getUpdateAfCont() != null ? (String) list1
					.get(i).getUpdateAfCont() : "";// 修改后内容
			dataNew = StringEscapeUtils.escapeSql(dataNew);// sql特殊字符转义
			String colName = list1.get(i).getUpdateItemEn() != null ? (String) list1
					.get(i).getUpdateItemEn() : "";// 修改项目字段名
			String tabName = list1.get(i).getUpdateTable() != null ? (String) list1
					.get(i).getUpdateTable() : "";// 修改表名
			String pkFlag = list1.get(i).getUpdateTableId() != null ? (String) list1
					.get(i).getUpdateTableId() : "";// 是否主键字段标识
			String fieldType = list1.get(i).getFieldType() != null ? (String) list1
					.get(i).getFieldType() : "";// 字段类型

			// 排除表、字段数据错误
			if (tabName == null || "".equals(tabName) || colName == null
					|| "".equals(colName)) {
				continue;
			}
			if (lastTable != null && lastTable.equals(tabName)) {
				tableFlag = true;
			} else {
				tableFlag = false;
			}
			// 添加更新字段sql
			if (!tableFlag) {// 不同的表时
				if (tempUpdateSql != null
						&& !"".equals(tempUpdateSql.toString())) {
					if (tempWhereSql == null) {
						if (lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO")
								|| lastTable
										.equals("ACRM_F_CI_ORG_EXECUTIVEINFO1")) {
							tempWhereSql = " WHERE ORG_CUST_ID='" + custId
									+ "'";
						} else if (lastTable
								.equals("OCRM_F_CI_GROUP_MEMBER_NEW")) {
							tempWhereSql = " WHERE CUS_ID='" + custId + "'";
						} else {
							tempWhereSql = " WHERE CUST_ID='" + custId + "'";
						}
					}
					SQLS.add(tempUpdateSql.toString() + tempWhereSql);
					log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString()
							+ tempWhereSql + "]");
				}
				lastTable = tabName;
				tempUpdateSql = new StringBuffer();
				tempWhereSql = null;

				if ("2".equals(fieldType)) {
					tempUpdateSql.append(" UPDATE " + tabName + " SET "
							+ colName + "=TO_DATE('" + dataNew
							+ "','yyyy-MM-dd')");
				} else {
					tempUpdateSql.append(" UPDATE " + tabName + " SET "
							+ colName + "='" + dataNew + "'");
				}
			} else {// 相同的表时
				if ("2".equals(fieldType)) {
					tempUpdateSql.append(" ," + colName + "=TO_DATE('"
							+ dataNew + "','yyyy-MM-dd')");
				} else {
					tempUpdateSql.append(" ," + colName + "='" + dataNew + "'");
				}
			}
			// 添加update where条件sql
			if ("1".equals(pkFlag)) {
				tempWhereSql = " WHERE " + colName + "='" + dataNew + "'";
			}
		}
		// 判断是否最后一行操作右是增加相应的sql
		if (totalCount == 0) {
			if (tempUpdateSql != null && !"".equals(tempUpdateSql.toString())) {
				if (tempWhereSql == null) {
					if (lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO")
							|| lastTable.equals("ACRM_F_CI_ORG_EXECUTIVEINFO1")) {
						tempWhereSql = " WHERE ORG_CUST_ID='" + custId + "'";
					} else if (lastTable.equals("OCRM_F_CI_GROUP_MEMBER_NEW")) {
						tempWhereSql = " WHERE CUS_ID='" + custId + "'";
					} else {
						tempWhereSql = " WHERE CUST_ID='" + custId + "'";
					}
				}
				SQLS.add(tempUpdateSql.toString() + tempWhereSql);
				log.info("添加批量UPDATE SQL:[" + tempUpdateSql.toString()
						+ tempWhereSql + "]");
			}
		}

		// 批量处理存在表别名的SQL语句
		for (int i = 0, len = SQLS.size(); i < len; i++) {
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_ADDRESS0",
					"ACRM_F_CI_ADDRESS"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_ADDRESS1",
					"ACRM_F_CI_ADDRESS"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER1", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER2", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER3", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER4", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER5", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_ORG_EXECUTIVEINFO1",
					"ACRM_F_CI_ORG_EXECUTIVEINFO"));
		}
		// 去除null值
		String SQL[] = new String[SQLS.size()];

		SQL = SQLS.toArray(new String[0]);
		if (SQL.length > 0) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			jdbcTemplate.batchUpdate(SQL);
		}
	}

	/**
	 * 拼接业务更新SQL并add到SQLS 注：单表单条记录新增操作 ACRM_F_CI_ORG_EXECUTIVEINFO
	 * ACRM_F_CI_ADDRESS ACRM_F_CI_CONTMETH
	 * 
	 * @param vo
	 * @param pk_xx
	 *            新增主键/如若为空,则使用默认的id_sequence.nextval,
	 *            或者可通过设置修改历史字段的修改前值存放sequence名称
	 * @throws SQLException
	 */
	public void joinInsertSql(String pk_xx, String custId, String updateFlag,
			AuthUser auth) throws SQLException {
		// 注：必须按order by UPDATE_TABLE,UPDATE_TABLE_ID
		// 排序处理,根据第一个字段是否主键及其值是否为空决定是insert还是update
		ArrayList<String> SQLS = null;
		@SuppressWarnings("unchecked")
		List<OcrmFCiCustinfoUphi> list1 = this.em.createQuery(
				"select v from OcrmFCiCustinfoUphi v where v.custId='" + custId
						+ "' and  v.updateFlag='" + updateFlag
						+ "' order by v.updateTable,v.updateTableId")
				.getResultList();
		SQLS = new ArrayList<String>();
		StringBuffer tempInsertSql = null; // 修改的单个表语句
		StringBuffer tempValuesSql = null;
		String lastTable = null; // 上一次的tableName
		for (int i = 0; i < list1.size(); ++i) {
			String dataOld = list1.get(i).getUpdateBeCont() != null ? (String) list1
					.get(i).getUpdateBeCont() : "";// 修改前内容
			dataOld = StringEscapeUtils.escapeSql(dataOld);// sql特殊字符转义
			String dataNew = list1.get(i).getUpdateAfCont() != null ? (String) list1
					.get(i).getUpdateAfCont() : "";// 修改后内容
			dataNew = StringEscapeUtils.escapeSql(dataNew);// sql特殊字符转义
			String colName = list1.get(i).getUpdateItemEn() != null ? (String) list1
					.get(i).getUpdateItemEn() : "";// 修改项目字段名
			String tabName = list1.get(i).getUpdateTable() != null ? (String) list1
					.get(i).getUpdateTable() : "";// 修改表名
			String pkFlag = list1.get(i).getUpdateTableId() != null ? (String) list1
					.get(i).getUpdateTableId() : "";// 是否主键字段标识
			String fieldType = list1.get(i).getFieldType() != null ? (String) list1
					.get(i).getFieldType() : "";// 字段类型

			// 排除表、字段数据错误
			if (tabName == null || "".equals(tabName) || colName == null
					|| "".equals(colName)) {
				continue;
			}
			// 添加更新字段sql
			if (tempInsertSql == null) {
				tempInsertSql = new StringBuffer();
				tempValuesSql = new StringBuffer();

				if ("1".equals(pkFlag) && (pk_xx == null || "".equals(pk_xx))) {
					if (dataNew == null || "".equals(dataNew)) {
						// 当前台未指定主键时,则使用默认主键
						if (dataOld == null || "".equals(dataOld)) {
							tempInsertSql.append(" INSERT INTO " + tabName
									+ "(" + colName);
							tempValuesSql
									.append(" VALUES (ID_SEQUENCE.NEXTVAL");
						} else if (dataOld.indexOf("NEXTVAL") > -1) {// 前台指定了主键序列
							tempInsertSql.append(" INSERT INTO " + tabName
									+ "(" + colName);
							tempValuesSql.append(" VALUES (" + dataOld);
						} else {// 前台指定了具体的主键值
							tempInsertSql.append(" INSERT INTO " + tabName
									+ "(" + colName);
							tempValuesSql.append(" VALUES ('" + dataOld + "'");
						}
					} else {// ECIF交易返回了主键值
						tempInsertSql.append(" INSERT INTO " + tabName + "("
								+ colName);
						tempValuesSql.append(" VALUES ('" + dataNew + "'");
					}
				} else {// ECIF交易返回了主键值
					tempInsertSql.append(" INSERT INTO " + tabName + "("
							+ colName);
					tempValuesSql.append(" VALUES ('" + pk_xx + "'");
				}
			} else {
				if ("2".equals(fieldType)) {
					tempInsertSql.append(" ," + colName);
					tempValuesSql.append(" ,TO_DATE('" + dataNew
							+ "','yyyy-MM-dd')");
				} else {
					tempInsertSql.append(" ," + colName);
					tempValuesSql.append(" ,'" + dataNew + "'");
				}
			}
		}
		if (tempInsertSql != null && tempValuesSql != null
				&& !"".equals(tempInsertSql.toString())
				&& !"".equals(tempValuesSql.toString())) {
			SQLS.add(tempInsertSql.toString() + ") " + tempValuesSql.toString()
					+ ")");
			log.info("添加批量INSERT SQL:[" + tempInsertSql.toString() + ") "
					+ tempValuesSql.toString() + ") ]");
		}
		// 批量处理存在表别名的SQL语句
		for (int i = 0, len = SQLS.size(); i < len; i++) {
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_ADDRESS0",
					"ACRM_F_CI_ADDRESS"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i), "ACRM_F_CI_ADDRESS1",
					"ACRM_F_CI_ADDRESS"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER1", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER2", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER3", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER4", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_CUST_IDENTIFIER5", "ACRM_F_CI_CUST_IDENTIFIER"));
			SQLS.set(i, StringUtils.replace(SQLS.get(i),
					"ACRM_F_CI_ORG_EXECUTIVEINFO1",
					"ACRM_F_CI_ORG_EXECUTIVEINFO"));
		}
		// 去除null值
		String SQL[] = new String[SQLS.size()];

		SQL = SQLS.toArray(new String[0]);
		if (SQL.length > 0) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			jdbcTemplate.batchUpdate(SQL);
		}
	}

	/**
	 * 删除：批量删除
	 * @param ids
	 */
		public void batchDestroy(String custids){
			String custid[] = custids.split(",");
			for(int i=0;i<custid.length;i++){
				String[] SQL2 = new String[16];
				String sql1=" DELETE FROM ACRM_F_CI_CUSTOMER A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql2=" DELETE FROM ACRM_F_CI_ORG A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql3=" DELETE FROM ACRM_F_CI_ORG_BUSIINFO A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql4=" DELETE FROM ACRM_F_CI_ORG_REGISTERINFO A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql5=" DELETE FROM ACRM_F_CI_ADDRESS A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql6=" DELETE FROM ACRM_F_CI_ORG_EXECUTIVEINFO A WHERE A.ORG_CUST_ID = '"+custid[i]+"'";
				String sql7=" DELETE FROM ACRM_F_CI_CUST_IDENTIFIER A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql8=" DELETE FROM OCRM_F_CI_GROUP_MEMBER_NEW A WHERE A.CUS_ID = '"+custid[i]+"'";
				String sql9=" DELETE FROM OCRM_F_CI_BELONG_CUSTMGR A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql10=" DELETE FROM OCRM_F_CI_BELONG_ORG A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql11=" DELETE FROM ACRM_F_CI_ORG_KEYFLAG A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql12=" DELETE FROM ACRM_F_CI_ORG_ISSUESTOCK A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql13=" DELETE FROM OCRM_F_CI_CUST_SCIENCE A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql14=" DELETE FROM OCRM_F_CI_CUST_PENALIZED A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql15=" DELETE FROM ACRM_F_CI_CONTMETH A WHERE A.CUST_ID = '"+custid[i]+"'";
				String sql16=" DELETE FROM ACRM_F_CI_AGENTINFO A WHERE A.CUST_ID = '"+custid[i]+"'";
				
				SQL2[0]=sql1;
			    SQL2[1]=sql2;
				SQL2[2]=sql3;
				SQL2[3]=sql4;
				SQL2[4]=sql5;
				SQL2[5]=sql6;
				SQL2[6]=sql7;
				SQL2[7]=sql8;
				SQL2[8]=sql9;
				SQL2[9]=sql10;
				SQL2[10]=sql11;
				SQL2[11]=sql12;
				SQL2[12]=sql13;
				SQL2[13]=sql14;
				SQL2[14]=sql15;
				SQL2[15]=sql16;
				
				JdbcTemplate jdbcTemplate2 = new JdbcTemplate(ds);
				jdbcTemplate2.batchUpdate(SQL2);
				
			}
		}
}

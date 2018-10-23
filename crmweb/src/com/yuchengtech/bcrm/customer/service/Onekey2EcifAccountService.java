package com.yuchengtech.bcrm.customer.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerKeyflag;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bcrm.customer.model.AcrmFCiAddress;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamily;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bcrm.oneKeyAccount.entity.EcifAccountMsg;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiAccountInfo;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiCustjoinInfo;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiOpenInfo;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmMCiRelPerson;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmMCiTaxMain;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmMCiTaxSub;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.oneKeyOpen.EcifOpenAccountTransaction;
import com.yuchengtech.trans.inf.Transaction;

/**
 * CRM到ECIF个人一键开户
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class Onekey2EcifAccountService extends CommonService {
	private static Logger log = LoggerFactory.getLogger(Onekey2EcifAccountService.class);
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性
	@SuppressWarnings("unused")
	private SimpleDateFormat sdf_16 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	

	public Onekey2EcifAccountService() {
		JPABaseDAO<AcrmFCiPotCusCom, String> baseDAO = new JPABaseDAO<AcrmFCiPotCusCom, String>(AcrmFCiPotCusCom.class);
		super.setBaseDAO(baseDAO);
	}

	/**
	 * ECIF开户，处理请求数据
	 * @param request HttpServletRequest
	 * @return Map 开户结果信息，包含开户是否成功，以及结果提示信息
	 */
	@SuppressWarnings({ "unused" })
	public Map<String, Object> openEcifAccount(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		EcifAccountMsg msgVO = new EcifAccountMsg();
		try {
			List<AcrmFCiCustIdentifier> identyList = new ArrayList<AcrmFCiCustIdentifier>();//证件信息
			List<AcrmFCiAddress> addressInfoList = new ArrayList<AcrmFCiAddress>();// 地址信息
			List<AcrmFCiContmeth> contmethInfoList = new ArrayList<AcrmFCiContmeth>();// 联系方式信息
			// String cust_id = request.getParameter("cust_id");//客户编号
			// 测试数据
			// String cust_id = "崔恒薇测试4";
			String str_logId = request.getParameter("serializeId");
			log.info(String.format("开户交易流水号：%s", str_logId));
			if (StringUtils.isEmpty(str_logId)) {
				modelMap.put("status", "error");
				modelMap.put("msg", "开户失败, 获取交易流水号失败...");
				log.warn("开户失败, 获取交易流水号失败...");
				return modelMap;
			}
			msgVO.setSeqNO(str_logId);
			JSONObject objectjson1 = JSONObject.fromObject(request.getParameter("objectjson1"));//第一屏的基本信息
			JSONObject accountInfoJson = JSONObject.fromObject(request.getParameter("accountInfoJson"));//账户信息
			JSONObject serviceInfoJson = JSONObject.fromObject(request.getParameter("serviceInfoJson"));//银行服务信息
			String custId1 = objectjson1.optString("custId1");//主户客户号
			String custId2 = objectjson1.optString("custId2");//从户客户号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// 当前登录用户
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String branchNo = auth.getUnitId();//当前用户机构编号
			String userId = auth.getUserId();//当前操作员工号
			// 获取当前时间
			Timestamp ts = new Timestamp(System.currentTimeMillis());

			// 1.基本信息
			AcrmFCiCustomer acrmFCiCustomer = new AcrmFCiCustomer();
			//AcrmFCiCustomer创建机构、创建时间、创建日期
			acrmFCiCustomer.setCreateBranchNo(branchNo);
			acrmFCiCustomer.setCreateDate(new Date());
			acrmFCiCustomer.setCreateTime(ts);
			acrmFCiCustomer.setCustStat("A");
			AcrmFCiPerson acrmFCiPerson = new AcrmFCiPerson();
			// acrmFCiPerson.setCustId(cust_id);//**
			//ecif是否已经开户
			String ecifIsOpen = objectjson1.optString("ecifIsOpen");
			// 姓名
			String currCustName = objectjson1.optString("custname");
			acrmFCiPerson.setPersonalName(objectjson1.optString("custname"));
			//客户类型--默认为对私(2)
			acrmFCiCustomer.setCustType("2");
			// 客户信息表保存客户姓名
			acrmFCiCustomer.setCustName(objectjson1.optString("custname"));
			// 姓名拼音
			acrmFCiPerson.setPinyinName(objectjson1.optString("customPinYin"));
			// 性别
			acrmFCiPerson.setGender(objectjson1.optString("gender"));
			// 个人生日
			String birthday = objectjson1.optString("birthday");// 2017-09-06
			if (birthday != null && (!"".equals(birthday))) {
				try {
					acrmFCiPerson.setBirthday(sdf.parse(birthday));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			// 国籍
			acrmFCiPerson.setCitizenship(objectjson1.optString("citizenShip"));
			// 出生地
			acrmFCiPerson.setBirthlocale(objectjson1.optString("birthLocale"));

			// 地址表
			// 居住地址（国籍）
			if (!objectjson1.optString("HOME_ADDR").equals("")) {
				AcrmFCiAddress acrmFCiAddress = new AcrmFCiAddress();
				// acrmFCiAddress.setAddrId(maxAddrId++);
				// acrmFCiAddress.setCustId(cust_id);//客户编号
				acrmFCiAddress.setAddrType("04");// 地址类型，码表XD000025
				acrmFCiAddress.setCountryOrRegion(objectjson1.optString("HOME_ADDR"));// 居住地址（国籍）
				// 居住地址（详细内容）
				acrmFCiAddress.setAddr(objectjson1.optString("HOME_ADDR_INFO"));
				acrmFCiPerson.setHomeAddr(objectjson1.optString("HOME_ADDR_INFO"));
				// 居住地邮编
				acrmFCiAddress.setZipcode(objectjson1.optString("POST_ZIPCODE"));
				acrmFCiPerson.setHomeZipcode(objectjson1.optString("POST_ZIPCODE"));
				// 最后更新人
				acrmFCiAddress.setLastUpdateUser(auth.getUserId());
				// 最后更新时间
				acrmFCiAddress.setLastUpdateTm(ts);
				// 创建人
				acrmFCiAddress.setCreateUser(auth.getUserId());
				// 创建时间
				acrmFCiAddress.setCreateTm(ts);
				addressInfoList.add(acrmFCiAddress);
				// this.baseDAO.save(acrmFCiAddress);
			}
			
			// 邮寄地址国籍
			if (!objectjson1.optString("MAIL_ADDR").equals("")) {
				AcrmFCiAddress acrmFCiAddressMail = new AcrmFCiAddress();
				// acrmFCiAddressMail.setAddrId(maxAddrId++);
				// acrmFCiAddressMail.setCustId(cust_id);
				acrmFCiAddressMail.setAddrType("01");//地址类型：邮寄地址
				acrmFCiAddressMail.setCountryOrRegion(objectjson1.optString("MAIL_ADDR"));//邮寄地址国籍
				// 邮寄详细地址
				acrmFCiAddressMail.setAddr(objectjson1.optString("MAIL_ADDR_INFO"));
				// 邮寄地邮编
				acrmFCiAddressMail.setZipcode(objectjson1.optString("MAIL_ZIPCODE"));
				// 最后更新人
				acrmFCiAddressMail.setLastUpdateUser(auth.getUserId());
				// 最后更新时间
				acrmFCiAddressMail.setLastUpdateTm(ts);
				// 创建人
				acrmFCiAddressMail.setCreateUser(auth.getUserId());
				// 创建时间
				acrmFCiAddressMail.setCreateTm(ts);
				addressInfoList.add(acrmFCiAddressMail);
			}

			// 租赁还是自有
			String isRent = objectjson1.optString("isRent");
			if (!isRent.equals("")) {
				AcrmFCiPerFamily acrmFCiPerFamily = new AcrmFCiPerFamily();
				// acrmFCiPerFamily.setCustId(cust_id);
				acrmFCiPerFamily.setHouseStat(isRent);// 创建时间租赁还是自有标志
				
				// 最后更新人
				acrmFCiPerFamily.setLastUpdateUser(auth.getUserId());
				// 最后更新时间
				acrmFCiPerFamily.setLastUpdateTm(ts);
				msgVO.setPersonFamilyInfo(acrmFCiPerFamily);
			}

			//业务手机
			AcrmFCiContmeth mbPhone = new AcrmFCiContmeth();
			//联系手机
			AcrmFCiContmeth mbPhone1 = new AcrmFCiContmeth();
			
			mbPhone.setContmethType("102");
			mbPhone1.setContmethType("209");
			// 联系方式内容：移动电话归属国家-移动电话国际区号-移动电话电话号86-86-13390700349 CHN-86-13390700349
			mbPhone.setContmethInfo(objectjson1.optString("QUYUMA") + "-" + objectjson1.optString("mbPHONENUM"));
			mbPhone1.setContmethInfo(objectjson1.optString("QUYUMA") + "-" + objectjson1.optString("mbPHONENUM"));
			//记录状态
			mbPhone.setStat("1");
			mbPhone1.setStat("1");
			// 最后更新人
			mbPhone.setLastUpdateUser(auth.getUserId());
			mbPhone1.setLastUpdateUser(auth.getUserId());
			// 最后更新时间
			mbPhone.setLastUpdateTm(ts);
			mbPhone1.setLastUpdateTm(ts);
			// 创建人
			mbPhone.setCreateUser(auth.getUserId());
			mbPhone1.setCreateUser(auth.getUserId());
			// 创建时间
			mbPhone.setCreateTm(ts);
			mbPhone1.setCreateTm(ts);
			contmethInfoList.add(mbPhone);
			contmethInfoList.add(mbPhone1);
			// 其他电话1
			String otherPhoneType1 = objectjson1.optString("ORTHERPHONE");
			if (!otherPhoneType1.equals("")) {
				AcrmFCiContmeth otherPhone1 = new AcrmFCiContmeth();
				// otherPhone1.setContmethId((maxContemethId++)+"");
				// otherPhone1.setCustId(cust_id);//客户编号
				// 其他电话1类型选择
				otherPhone1.setContmethType(otherPhoneType1);
				// 联系方式内容：固定电话：国家+下拉默认国家区号+区域码+电话号
				otherPhone1.setContmethInfo(objectjson1.optString("PHONE_CITIZENSHIP") + "-" + objectjson1.optString("QUYUMA1") + "-"
						+ objectjson1.optString("QUYUMA2") + "-" + objectjson1.optString("PHONENUM1"));
				//记录状态
				otherPhone1.setStat("1");
				// 最后更新人
				otherPhone1.setLastUpdateUser(auth.getUserId());
				// 最后更新时间
				otherPhone1.setLastUpdateTm(ts);
				// 创建人
				otherPhone1.setCreateUser(auth.getUserId());
				// 创建时间
				otherPhone1.setCreateTm(ts);
				contmethInfoList.add(otherPhone1);
			}

			// 其他电话2
			String otherPhoneType2 = objectjson1.optString("ORTHERPHONE1");
			if (!otherPhoneType2.equals("")) {
				AcrmFCiContmeth otherPhone2 = new AcrmFCiContmeth();
				// otherPhone2.setContmethId((maxContemethId++)+"");
				// otherPhone2.setCustId(cust_id);//客户编号
				// 其他电话2类型选择
				otherPhone2.setContmethType(otherPhoneType2);
				// 联系方式内容：固定电话：国家+下拉默认国家区号+区域码+电话号
				otherPhone2.setContmethInfo(objectjson1.optString("PHONE_CITIZENSHIP1") + "-" + objectjson1.optString("QUYUMA3") + "-"
						+ objectjson1.optString("QUYUMA4") + "-" + objectjson1.optString("PHONENUM2"));
				//记录状态
				otherPhone2.setStat("1");
				// 最后更新人
				otherPhone2.setLastUpdateUser(auth.getUserId());
				// 最后更新时间
				otherPhone2.setLastUpdateTm(ts);
				// 创建人
				otherPhone2.setCreateUser(auth.getUserId());
				// 创建时间
				otherPhone2.setCreateTm(ts);
				contmethInfoList.add(otherPhone2);
			}

			// 职业资料
			if(objectjson1.optString("JOBINFO")!= null &&
					!"".equals(objectjson1.optString("JOBINFO").toString())){
				String careerStat = objectjson1.optString("JOBINFO").toString();
				acrmFCiPerson.setCareerStat(careerStat);
				if(careerStat.equals("04")) {// 全日制雇员
					// 全日制的单位名称
					if(objectjson1.optString("JOBNAME")!= null &&
							!"".equals(objectjson1.optString("JOBNAME").toString())){
						acrmFCiPerson.setUnitName(objectjson1.optString("JOBNAME"));
					}
					// 全日制的职位:
					if(objectjson1.optString("JOB")!= null &&
							!"".equals(objectjson1.optString("JOB").toString())){
						acrmFCiPerson.setDuty(objectjson1.optString("JOB"));
					}
				}else if(careerStat.equals("99")) {// 其他
					if(objectjson1.optString("JOBREMARK")!= null && !"".equals(objectjson1.optString("JOBREMARK").toString())){
						//其他备注
						acrmFCiPerson.setOtherCareer(objectjson1.optString("JOBREMARK").toString());
					}
				}
			}

			// 电子邮箱E-mail
			if (!objectjson1.optString("EMAIL").equals("")) {
				AcrmFCiContmeth email = new AcrmFCiContmeth();
				// E-mail类型
				email.setContmethType("500");
				// 联系方式内容：
				email.setContmethInfo(objectjson1.optString("EMAIL"));
				acrmFCiPerson.setEmail(objectjson1.optString("EMAIL"));
				//记录状态
				email.setStat("1");
				// 最后更新人
				email.setLastUpdateUser(auth.getUserId());
				// 最后更新时间
				email.setLastUpdateTm(ts);
				// 创建人
				email.setCreateUser(auth.getUserId());
				// 创建时间
				email.setCreateTm(ts);
				contmethInfoList.add(email);
				if(serviceInfoJson.optString("dianziBank") != null){
					if(serviceInfoJson.optString("dianziBank").toString().equals("1")){
						//开网银的时候添加业务邮箱
						AcrmFCiContmeth email1 = new AcrmFCiContmeth();
						// E-mail类型
						email1.setContmethType("501");
						// 联系方式内容：
						email1.setContmethInfo(objectjson1.optString("EMAIL"));
						acrmFCiPerson.setEmail(objectjson1.optString("EMAIL"));
						//记录状态
						email1.setStat("1");
						// 最后更新人
						email1.setLastUpdateUser(auth.getUserId());
						// 最后更新时间
						email1.setLastUpdateTm(ts);
						// 创建人
						email1.setCreateUser(auth.getUserId());
						// 创建时间
						email1.setCreateTm(ts);
						contmethInfoList.add(email1);
					}
				}
			}

			AcrmFCiPerKeyflag acrmFCiPerKeyflag = new AcrmFCiPerKeyflag();	
			// 在我行有无关联人
			if(objectjson1.optString("HASRELATED")!=null &&
					!"".equals(objectjson1.optString("HASRELATED").toString())){
				String HASRELATED = objectjson1.optString("HASRELATED").toString();
				acrmFCiPerKeyflag.setIsRealtedBank(HASRELATED);
				//acrmFCiPerson.setIfRelPerson(HASRELATED);
				if(HASRELATED.equals("1")){
					OcrmMCiRelPerson ocrmMCiRelPerson = new OcrmMCiRelPerson();
					// ocrmMCiRelPerson.setCustId(cust_id);
					// 有关联人关联人姓名
					ocrmMCiRelPerson.setPersonalName(objectjson1.optString("RELATEDNAME"));
					// 有关联人与关联人关系
					ocrmMCiRelPerson.setRelation(objectjson1.optString("RELATION"));

					// 创建人
					ocrmMCiRelPerson.setCreateUser(auth.getUserId());
					// 创建时间
					ocrmMCiRelPerson.setCreateTm(ts);
					// 创建机构
					ocrmMCiRelPerson.setCreateOrg(auth.getUnitId());
					// 最后更新人
					ocrmMCiRelPerson.setUpdateUser(auth.getUserId());
					// 最后更新时间
					ocrmMCiRelPerson.setUpdateTm(ts);
					// 修改机构
					ocrmMCiRelPerson.setUpdateOrg(auth.getUnitId());
					msgVO.setCustRelPerson(ocrmMCiRelPerson);
				}
			}

			// 与我行关联关系(客户其实要的是：关联人属性（核心）)
			if(objectjson1.optString("RELATION1") != null){
				String RELATION1 = objectjson1.optString("RELATION1");
				acrmFCiCustomer.setStaffin(RELATION1);
				if(RELATION1.equals("S")){//内部员工及其近亲属
					//是否本行职工 1
					acrmFCiPerKeyflag.setIsEmployee("1");
				}else{
					acrmFCiPerKeyflag.setIsEmployee("0");
				}
			}
			// acrmFCiCustomer.setCusBankRel(objectjson1.get("RELATION1").toString());
			// 来源渠道
			acrmFCiCustomer.setSourceChannel(objectjson1.optString("SOURCECHANNEL"));
			//风险国别代码
			acrmFCiCustomer.setRiskNationCode(objectjson1.optString("RISK_NATION_CODE"));
			// 最后更新人
			acrmFCiCustomer.setLastUpdateUser(auth.getUserId());
			// 最后更新时间
			acrmFCiCustomer.setLastUpdateTm(ts);

			//所属客户经理 objectjson1.customManager
			String s_custMgrId = objectjson1.optString("customManager");
			if (s_custMgrId != null && !s_custMgrId.equals("")) {
				OcrmFCiBelongCustmgr  belongManager = new OcrmFCiBelongCustmgr();
				OcrmFCiBelongOrg belongOrg = new OcrmFCiBelongOrg();
				//AcrmOBelongManager belongManager = new AcrmOBelongManager();// customManager
				//先从客户经理信息表查询客户经理信息
				//String manageInfoSql = "select t.CUST_MANAGER_ID, t.CUST_MANAGER_NAME from OCRM_F_CM_CUST_MGR_INFO t where t.user_id='"+s_custMgrId+"'";
				String manageInfoSql = "select  t.ACCOUNT_NAME,t.USER_NAME,t.ORG_ID,t.ORG_NAME from V_OPENACC_MGR t";
				manageInfoSql += "  where t.ID ='"+s_custMgrId+"'";
				Map<String, Object> paramsMgr = new HashMap<String, Object>();
				paramsMgr.put("managerUerId", s_custMgrId);
				List<Object[]> mgrList = this.baseDAO.findByNativeSQLWithNameParam(manageInfoSql, null);
				if(mgrList != null && mgrList.size() >= 1){
					Object[] objs = mgrList.get(0);
					if(objs != null && objs.length >= 1){
						Date nowDate = new Date();
						String managerNo = objs[0] == null ? "" : objs[0].toString();
						belongManager.setMgrId(managerNo);
						belongManager.setMgrName(objs[1].toString());
						belongManager.setInstitution(objs[2].toString());
						belongManager.setInstitutionName(objs[3].toString());
						belongManager.setAssignUser(auth.getUserId());
						belongManager.setAssignUsername(auth.getUsername());
						belongManager.setAssignDate(nowDate);
						belongManager.setEffectDate(nowDate);
						belongOrg.setInstitutionCode(objs[2].toString());
						belongOrg.setInstitutionName(objs[3].toString());
						belongOrg.setAssignUser(auth.getUserId());
						belongOrg.setAssignUsername(auth.getUsername());
						belongOrg.setAssignDate(nowDate);
						belongOrg.setEtlDate(nowDate);
						
						//belongManager.setCustManagerNo(managerNo);
					}
				}
				/*belongManager.setBelongManagerId(s_custMgrId);
				//belongManager.setCustManagerNo(s_custMgrNo);
				belongManager.setLastUpdateSys("CRM");
				belongManager.setLastUpdateUser(auth.getUserId());
				belongManager.setLastUpdateTm(new Timestamp(System.currentTimeMillis()));*/
				msgVO.setBelongManager(belongManager);
				msgVO.setBelongOrg(belongOrg);
			}

			// 证件表
			String  identityType3 = objectjson1.optString("identityType3");
			if(identityType3 != null && !"".equals(identityType3)){
				AcrmFCiCustIdentifier acrmFCiCustIdentifier = new AcrmFCiCustIdentifier();//主证件
				AcrmFCiCustIdentifier acrmFCiCustIdentifier1 = new AcrmFCiCustIdentifier();//辅助证件
				acrmFCiCustIdentifier.setIdentCustName(currCustName);// 证件户名
				acrmFCiCustIdentifier1.setIdentType(identityType3);// 证件类型
				if(identityType3.equals("6") || identityType3.equals("X24") || identityType3.equals("X3")){
					acrmFCiCustomer.setIdentType("X2");//台湾身份证
					acrmFCiCustomer.setIdentNo(objectjson1.optString("twIdentNum3"));
					acrmFCiCustIdentifier1.setIdentCustName(currCustName);// 证件户名
					acrmFCiCustIdentifier.setIdentType("X2");// 证件类型
					acrmFCiCustIdentifier.setIsOpenAccIdent("Y");
					acrmFCiCustIdentifier1.setIsOpenAccIdent("N");
					acrmFCiCustIdentifier.setIdentNo(objectjson1.optString("twIdentNum3"));// 证件号码
					acrmFCiCustIdentifier1.setIdentNo(objectjson1.optString("identityNo3"));
					String youxiaoqixian = objectjson1.optString("youxiaoqixian");// 2017-09-12// 有效日期
					if (youxiaoqixian != null && (!"".equals(youxiaoqixian)) && youxiaoqixian.length() > 4) {
						try {
							acrmFCiCustIdentifier.setIdentExpiredDate(sdf.parse(youxiaoqixian));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					String LEGAL_EXPIRED_DATE = objectjson1.optString("LEGAL_EXPIRED_DATE");// 2017-09-12// 有效日期
					if (LEGAL_EXPIRED_DATE != null && (!"".equals(LEGAL_EXPIRED_DATE)) && LEGAL_EXPIRED_DATE.length() > 4) {
						try {
							acrmFCiCustIdentifier1.setIdentExpiredDate(sdf.parse(LEGAL_EXPIRED_DATE));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					//发证机关所在地
					if(objectjson1.optString("qianfajiguan") != null &&
							!"".equals(objectjson1.optString("qianfajiguan"))){
						acrmFCiCustIdentifier.setCountryOrRegion(objectjson1.optString("qianfajiguan"));
					}
					
					//持证次数
					if(objectjson1.optString("chizhengcishu") != null &&
							!"".equals(objectjson1.optString("chizhengcishu"))){
						acrmFCiCustIdentifier1.setIdentCount(Integer.parseInt(objectjson1.optString("chizhengcishu")));
					}
					acrmFCiCustIdentifier.setOpenAccIdentModifiedFlag("1");
					acrmFCiCustIdentifier1.setOpenAccIdentModifiedFlag("1");
					
					acrmFCiCustIdentifier.setLastUpdateUser(auth.getUserId());// 最后更新人
					acrmFCiCustIdentifier.setLastUpdateTm(ts);// 最后更新时间
					acrmFCiCustIdentifier1.setLastUpdateUser(auth.getUserId());// 最后更新人
					acrmFCiCustIdentifier1.setLastUpdateTm(ts);// 最后更新时间
					identyList.add(acrmFCiCustIdentifier);
					identyList.add(acrmFCiCustIdentifier1);
					
					
				}else if(identityType3.equals("5")){
					acrmFCiCustomer.setIdentType("X1");
					acrmFCiCustomer.setIdentNo(objectjson1.optString("gaIdentNum3"));
					acrmFCiCustIdentifier1.setIdentCustName(currCustName);// 证件户名
					acrmFCiCustIdentifier.setIdentType("X1");// 证件类型
					acrmFCiCustIdentifier.setIsOpenAccIdent("Y");
					acrmFCiCustIdentifier1.setIsOpenAccIdent("N");
					acrmFCiCustIdentifier.setIdentNo(objectjson1.optString("gaIdentNum3"));// 证件号码
					acrmFCiCustIdentifier1.setIdentNo(objectjson1.optString("identityNo3"));
					String youxiaoqixian = objectjson1.optString("youxiaoqixian");// 2017-09-12// 有效日期
					if (youxiaoqixian != null && (!"".equals(youxiaoqixian)) && youxiaoqixian.length() > 4) {
						try {
							acrmFCiCustIdentifier.setIdentExpiredDate(sdf.parse(youxiaoqixian));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					String LEGAL_EXPIRED_DATE = objectjson1.optString("LEGAL_EXPIRED_DATE");// 2017-09-12// 有效日期
					if (LEGAL_EXPIRED_DATE != null && (!"".equals(LEGAL_EXPIRED_DATE)) && LEGAL_EXPIRED_DATE.length() > 4) {
						try {
							acrmFCiCustIdentifier1.setIdentExpiredDate(sdf.parse(LEGAL_EXPIRED_DATE));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					//发证机关所在地
					if(objectjson1.optString("qianfajiguan") != null &&
							!"".equals(objectjson1.optString("qianfajiguan"))){
						acrmFCiCustIdentifier.setCountryOrRegion(objectjson1.optString("qianfajiguan"));
					}
					
					/*//持证次数
					if(objectjson1.optString("chizhengcishu") != null &&
							!"".equals(objectjson1.optString("chizhengcishu"))){
						acrmFCiCustIdentifier.setIdentCount(Integer.parseInt(objectjson1.optString("chizhengcishu")));
					}*/
					
					acrmFCiCustIdentifier.setOpenAccIdentModifiedFlag("1");
					acrmFCiCustIdentifier1.setOpenAccIdentModifiedFlag("1");
					
					acrmFCiCustIdentifier.setLastUpdateUser(auth.getUserId());// 最后更新人
					acrmFCiCustIdentifier.setLastUpdateTm(ts);// 最后更新时间
					acrmFCiCustIdentifier1.setLastUpdateUser(auth.getUserId());// 最后更新人
					acrmFCiCustIdentifier1.setLastUpdateTm(ts);// 最后更新时间
					identyList.add(acrmFCiCustIdentifier);
					identyList.add(acrmFCiCustIdentifier1);
				}else{
					acrmFCiCustomer.setIdentType(identityType3);
					acrmFCiCustomer.setIdentNo(objectjson1.optString("identityNo3"));
					
					acrmFCiCustIdentifier.setIdentType(identityType3);// 证件类型
					acrmFCiCustIdentifier.setIsOpenAccIdent("Y");
					acrmFCiCustIdentifier.setIdentNo(objectjson1.optString("identityNo3"));// 证件号码
					String legal_expired_date = objectjson1.optString("LEGAL_EXPIRED_DATE");// 2017-09-12// 有效日期
					if (legal_expired_date != null && (!"".equals(legal_expired_date)) && legal_expired_date.length() > 4) {
						try {
							acrmFCiCustIdentifier.setIdentExpiredDate(sdf.parse(legal_expired_date));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					//发证机关所在地
					if(objectjson1.optString("qianfajiguan") != null &&
							!"".equals(objectjson1.optString("qianfajiguan"))){
						acrmFCiCustIdentifier.setCountryOrRegion(objectjson1.optString("qianfajiguan"));
					}
					
					/*//持证次数
					if(objectjson1.optString("chizhengcishu") != null &&
							!"".equals(objectjson1.optString("chizhengcishu"))){
						acrmFCiCustIdentifier.setIdentCount(Integer.parseInt(objectjson1.optString("chizhengcishu")));
					}*/
					acrmFCiCustIdentifier.setOpenAccIdentModifiedFlag("1");
					acrmFCiCustIdentifier.setLastUpdateUser(auth.getUserId());// 最后更新人
					acrmFCiCustIdentifier.setLastUpdateTm(ts);// 最后更新时间
					identyList.add(acrmFCiCustIdentifier);
				}
			}
			
			// 是否是联名户
			String isLianMingHu = request.getParameter("isLianMingHu");
			// 联名户
			OcrmFCiCustjoinInfo ocrmFCiCustjoinInfo = new OcrmFCiCustjoinInfo();
			// 姓名
			acrmFCiPerson.setPersonalName(objectjson1.optString("custname"));
			if (isLianMingHu.equals("0")) {// 不是联名户
				acrmFCiPerson.setJointCustType("N");
				
				OcrmMCiTaxMain ocrmMCiTaxMain = new OcrmMCiTaxMain();
				List<OcrmMCiTaxSub>  ocrmMCiTaxSubList = new ArrayList<OcrmMCiTaxSub>();
				String radio1 = objectjson1.optString("radio1");// 税收居民类型
							// :01-仅为中国税收居民;02-仅为非居民;03-既是中国税收居民又是其他国家（地区）税收居民
				if (!StringUtils.isEmpty(radio1)) {
					ocrmMCiTaxMain.setPersonStatement(radio1);
					
					String str_allPayTaxes = objectjson1.optString("taxData");
					if(radio1.equals("02") || radio1.equals("03")){
						
						// 是否为美国纳税人
						String isUNtaxpayer = objectjson1.optString("isUNtaxpayer");
						// 美国纳税人识别号--USTIN
						String USTIN = objectjson1.optString("USTIN");
						if(isUNtaxpayer.equals("1")){
							ocrmMCiTaxMain.setUsaflag(isUNtaxpayer);
							ocrmMCiTaxMain.setUstin(USTIN);
						}else if(isUNtaxpayer.equals("2")){
							ocrmMCiTaxMain.setUsaflag("0");
						}
						
						//是否居民国（地区）不发放纳税人识别号 
						if(objectjson1.optString("REASON")!= null && 
								!"".equals(objectjson1.optString("REASON").toString())){
							if(objectjson1.optString("REASON").toString().equals("on")){
								ocrmMCiTaxMain.setIfNoTinCountry("1");
							}else{
								ocrmMCiTaxMain.setIfNoTinCountry("0"); 
							}
						}else{
							ocrmMCiTaxMain.setIfNoTinCountry("0"); 
						}
						
						// 是否账户持有人未能取得纳税人识别号
						if(objectjson1.optString("REASON2")!= null && 
								!"".equals(objectjson1.optString("REASON2").toString())){
							if(objectjson1.optString("REASON2").toString().equals("on")){
								ocrmMCiTaxMain.setIfNoTinPerson("1");
							}else{
								ocrmMCiTaxMain.setIfNoTinPerson("0"); 
							}
						}else{
							ocrmMCiTaxMain.setIfNoTinPerson("0"); 
						}
						
						// 账户持有人未能取得纳税人识别号原因
						ocrmMCiTaxMain.setReason(objectjson1.optString("detailReason"));

						if (!StringUtils.isEmpty(str_allPayTaxes)) {
							JSONArray jsa_payTaxes = JSONArray.fromObject(str_allPayTaxes);
							if (jsa_payTaxes != null && jsa_payTaxes.size() >= 1) {
								for (int i = 0; i < jsa_payTaxes.size(); i++) {
									JSONObject jso = jsa_payTaxes.optJSONObject(i);
									if(jso.optString("code")!=null && 
											!"".equals(jso.optString("code").toString())){
										OcrmMCiTaxSub ocrmMCiTaxSub = new OcrmMCiTaxSub();
										ocrmMCiTaxSub.setTin(jso.optString("code"));// 纳税人识别号
										ocrmMCiTaxSub.setTaxCountry(jso.optString("name"));// 税收居民国
										
										// 创建人
										ocrmMCiTaxSub.setCreateUser(auth.getUserId());
										// 创建时间
										ocrmMCiTaxSub.setCreateTm(ts);
										// 创建机构
										ocrmMCiTaxSub.setCreateOrg(auth.getUnitId());
										// 最后更新人
										ocrmMCiTaxSub.setUpdateUser(auth.getUserId());
										// 最后更新时间
										ocrmMCiTaxSub.setUpdateTm(ts);
										// 修改机构
										ocrmMCiTaxSub.setUpdateOrg(auth.getUnitId());
										ocrmMCiTaxSubList.add(ocrmMCiTaxSub);
									}
								 }
								msgVO.setOcrmMCiTaxSubList(ocrmMCiTaxSubList);
							 }
						 }
					  
					}
					
					// 创建人
					ocrmMCiTaxMain.setCreateUser(auth.getUserId());
					// 创建时间
					ocrmMCiTaxMain.setCreateTm(ts);
					// 创建机构
					ocrmMCiTaxMain.setCreateOrg(auth.getUnitId());
					// 最后更新人
					ocrmMCiTaxMain.setUpdateUser(auth.getUserId());
					// 最后更新时间
					ocrmMCiTaxMain.setUpdateTm(ts);
					// 修改机构
					ocrmMCiTaxMain.setUpdateOrg(auth.getUnitId());
					msgVO.setOcrmMCiTaxMain(ocrmMCiTaxMain);
				}

			} else if (isLianMingHu.equals("1")) {// 是联名户
				
				acrmFCiPerson.setJointCustType("Y");
				String custnames = objectjson1.optString("custname");
				String[] custnameArray = custnames.split("/");
				// 联名户
				String lianMingCustname = custnameArray[1];
				// 联名户姓名拼音
				ocrmFCiCustjoinInfo.setPinyinName(objectjson1.optString("lianMinPinYin"));
				// 联名户性别
				ocrmFCiCustjoinInfo.setGender(objectjson1.optString("sex"));
				// 联名户国籍
				ocrmFCiCustjoinInfo.setCitizenship(objectjson1.optString("CITIZENSHIP1"));
				// 证件类型1
				ocrmFCiCustjoinInfo.setIdentType1(objectjson1.optString("lianMingIdenType1"));
				// 证件号码1
				ocrmFCiCustjoinInfo.setIdentNo1(objectjson1.optString("lianMingIdenNo1"));
				// sbSql.append(" AND IDENT_TYPE = '"+objectjson1.optString("lianMingIdenType1")
				// +"' AND IDENT_NO = '"+objectjson1.optString("lianMingGaIdentNum1")+"' ");
				// 证件类型2--特殊证件类型
				if (objectjson1.optString("lianMingIdenType1").equals("6")// 台湾同胞来往内地通行证1
						|| objectjson1.optString("lianMingIdenType1").equals("X24")// 临时台胞证1
						|| objectjson1.optString("lianMingIdenType1").equals("X3")// 旅行证件1
				) {
					ocrmFCiCustjoinInfo.setIdentType2(objectjson1.optString("lianMingIdenType1"));// 台湾同胞来往内地通行证2
					// 证件号码2
					ocrmFCiCustjoinInfo.setIdentNo2(objectjson1.optString("lianMingIdenNo1"));

					ocrmFCiCustjoinInfo.setIdentType1("X2");// 台湾身份证1
					// 证件号码1
					ocrmFCiCustjoinInfo.setIdentNo1(objectjson1.optString("lianMingTwIdentNum1"));
					
					// 有效日期1  主证件
					String legal_expired_date1 = objectjson1.optString("LEGAL_EXPIRED_DATE1");
					// 证件1是否长期有效
					String s_IsActPerm1 = objectjson1.optString("LONGTERM1");
					if (legal_expired_date1 != null && (!"".equals(legal_expired_date1))) {
						try {
							ocrmFCiCustjoinInfo.setIdtExpireDt1(sdf.parse(legal_expired_date1));
							if (s_IsActPerm1 != null && (s_IsActPerm1.equals("on") || s_IsActPerm1.equals("1"))) {
								ocrmFCiCustjoinInfo.setIsActPerm1("1");
							} else {
								ocrmFCiCustjoinInfo.setIsActPerm1("0");
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					// 有效日期2
					String legal_expired_date2 = objectjson1.optString("LEGAL_EXPIRED_DATE2");
					// 证件2是否长期有效
					String s_IsActPerm2 = objectjson1.optString("LONGTERM2");
					if (legal_expired_date2 != null && (!"".equals(legal_expired_date2))) {
						try {
							ocrmFCiCustjoinInfo.setIdtExpireDt2(sdf.parse(legal_expired_date2));
							if (s_IsActPerm2 != null && (s_IsActPerm2.equals("on") || s_IsActPerm2.equals("1"))) {
								ocrmFCiCustjoinInfo.setIsActPerm2("1");
							} else {
								ocrmFCiCustjoinInfo.setIsActPerm2("0");
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
				} else if (objectjson1.optString("lianMingIdenType1").equals("5")) {// 港澳居民来往内地通行证1
					ocrmFCiCustjoinInfo.setIdentType2(objectjson1.optString("lianMingIdenType1"));// 台湾同胞来往内地通行证2
					// 证件号码2
					ocrmFCiCustjoinInfo.setIdentNo2(objectjson1.optString("lianMingIdenNo1"));
					ocrmFCiCustjoinInfo.setIdentType1("X1");// 港澳身份证1
					// 证件号码1
					ocrmFCiCustjoinInfo.setIdentNo1(objectjson1.optString("lianMingGaIdentNum1"));
					
					// 有效日期1  主证件
					String legal_expired_date1 = objectjson1.optString("LEGAL_EXPIRED_DATE1");
					// 证件1是否长期有效
					String s_IsActPerm1 = objectjson1.optString("LONGTERM1");
					if (legal_expired_date1 != null && (!"".equals(legal_expired_date1))) {
						try {
							ocrmFCiCustjoinInfo.setIdtExpireDt1(sdf.parse(legal_expired_date1));
							if (s_IsActPerm1 != null && (s_IsActPerm1.equals("on") || s_IsActPerm1.equals("1"))) {
								ocrmFCiCustjoinInfo.setIsActPerm1("1");
							} else {
								ocrmFCiCustjoinInfo.setIsActPerm1("0");
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					// 有效日期2
					String legal_expired_date2 = objectjson1.optString("LEGAL_EXPIRED_DATE2");
					// 证件2是否长期有效
					String s_IsActPerm2 = objectjson1.optString("LONGTERM2");
					if (legal_expired_date2 != null && (!"".equals(legal_expired_date2))) {
						try {
							ocrmFCiCustjoinInfo.setIdtExpireDt2(sdf.parse(legal_expired_date2));
							if (s_IsActPerm2 != null && (s_IsActPerm2.equals("on") || s_IsActPerm2.equals("1"))) {
								ocrmFCiCustjoinInfo.setIsActPerm2("1");
							} else {
								ocrmFCiCustjoinInfo.setIsActPerm2("0");
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					
					// sbSql.append(" AND IDENT_TYPE = 'X1' AND IDENT_NO = '"+objectjson1.optString("lianMingGaIdentNum1")+"' ");
				}else{
					// 有效日期1
					String legal_expired_date1 = objectjson1.optString("LEGAL_EXPIRED_DATE2");
					// 证件1是否长期有效
					String s_IsActPerm1 = objectjson1.optString("LONGTERM2");
					if (legal_expired_date1 != null && (!"".equals(legal_expired_date1))) {
						try {
							ocrmFCiCustjoinInfo.setIdtExpireDt1(sdf.parse(legal_expired_date1));
							if (s_IsActPerm1 != null && (s_IsActPerm1.equals("on") || s_IsActPerm1.equals("1"))) {
								ocrmFCiCustjoinInfo.setIsActPerm1("1");
							} else {
								ocrmFCiCustjoinInfo.setIsActPerm1("0");
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				
				
				//获取当前开户证件号码
				String openAccountIdentNo = acrmFCiCustomer.getIdentNo();
				if(!StringUtils.isEmpty(openAccountIdentNo)){
					if(ecifIsOpen.equals("true")){//ecif已经开户
						StringBuilder sb1 = new StringBuilder();
						sb1.append("SELECT M.CUST_ID CUST_ID,M.IDENT_TYPE IDENT_TYPE,M.IDENT_NO IDENT_NO ")
						.append(" FROM OCRM_F_CI_CUSTJOIN_INFO T ,ACRM_F_CI_CUSTOMER M ")
						.append(" WHERE  M.CUST_ID = T.CUST_ID ")
						.append(" AND  T.IDENT_TYPE1 = '"+ ocrmFCiCustjoinInfo.getIdentType1())
						.append("' AND T.IDENT_NO1 = '"+ocrmFCiCustjoinInfo.getIdentNo1()+"' ")
						.append(" AND M.IDENT_TYPE ='"+ acrmFCiCustomer.getIdentType() )
						.append("' AND REPLACE(M.IDENT_NO,'*','') = '"+ acrmFCiCustomer.getIdentNo() +"'");
						List<Object[]> tempList = this.baseDAO.findByNativeSQLWithNameParam(sb1.toString(), null);
						if(tempList != null && tempList.size() >0){
							Object[] object1 = tempList.get(0);
							if(object1 != null && object1.length >0){
								String certType3 = object1[1].toString();
								String certNo3 = object1[2].toString();
								acrmFCiCustomer.setIdentNo(certNo3);//重新设置客户信息的证件号码
								
								//找出证件信息中的开户证件并修改证件号码
								if(identyList != null && identyList.size() >= 1){
									for (int i = 0; i < identyList.size(); i++) {
										AcrmFCiCustIdentifier identify = identyList.get(i);
										if(identify != null && identify.getIsOpenAccIdent().equals("Y")){//开户证件
											identify.setIdentNo(certNo3);
										}
									}
								}
							}
						}
					}else{
						//查询当前证件是否已经有开过联名户
						String lianmingIdentinfo = "select t.IDENT_NO from ACRM_F_CI_CUSTOMER  t where  replace(t.IDENT_NO,'*','')='"+openAccountIdentNo+"'";
						List<String> identList = this.baseDAO.findByNativeSQLWithNameParam(lianmingIdentinfo, null);
						if(identList != null && identList.size() >= 1){
							String currMaxIdentNo = openAccountIdentNo;
							for (int i = 0; i < identList.size(); i++) {
								String identNo = identList.get(i);
								if(!StringUtils.isEmpty(identNo)){
									if(identNo.length() > currMaxIdentNo.length()){
										currMaxIdentNo = identNo;
									}
								}
							}
							currMaxIdentNo = currMaxIdentNo + "*";//在当前最长的开户证件加上一个*
							acrmFCiCustomer.setIdentNo(currMaxIdentNo);//重新设置客户信息的证件号码
							//找出证件信息中的开户证件并修改证件号码
							if(identyList != null && identyList.size() >= 1){
								for (int i = 0; i < identyList.size(); i++) {
									AcrmFCiCustIdentifier identify = identyList.get(i);
									if(identify != null && identify.getIsOpenAccIdent().equals("Y")){//开户证件
										identify.setIdentNo(currMaxIdentNo);
									}
								}
							}
						}else{
							//该证件号没有开过户
							modelMap.put("status", "error");
							modelMap.put("msg", "开户失败, 该客户没有开过户");
							return modelMap;
						}
					}
				}

				// 联名户证件发证机关所在地
				ocrmFCiCustjoinInfo.setCountryOrRegion(objectjson1.optString("CITIZENSHIP2"));
				// 创建人
				ocrmFCiCustjoinInfo.setCreateUser(auth.getUserId());
				// 创建时间
				ocrmFCiCustjoinInfo.setCreateTm(ts);
				// 创建机构
				ocrmFCiCustjoinInfo.setCreateOrg(auth.getUnitId());
				// 最后更新人
				ocrmFCiCustjoinInfo.setUpdateUser(auth.getUserId());
				// 最后更新时间
				ocrmFCiCustjoinInfo.setUpdateTm(ts);
				// 修改机构
				ocrmFCiCustjoinInfo.setUpdateOrg(auth.getUnitId());
				msgVO.setCustJoinInfo(ocrmFCiCustjoinInfo);
				
				//主户本人声明
				OcrmMCiTaxMain ocrmMCiTaxMain = new OcrmMCiTaxMain();
				ocrmMCiTaxMain.setCustId(custId1);
				List<OcrmMCiTaxSub>  ocrmMCiTaxSubList = new ArrayList<OcrmMCiTaxSub>();
				String radio1 = objectjson1.optString("radio1");// 税收居民类型
							// :01-仅为中国税收居民;02-仅为非居民;03-既是中国税收居民又是其他国家（地区）税收居民
				if (!StringUtils.isEmpty(radio1)) {
					ocrmMCiTaxMain.setPersonStatement(radio1);
					
					String str_allPayTaxes = objectjson1.optString("taxData");
					if(radio1.equals("02") || radio1.equals("03")){
						
						// 是否为美国纳税人
						String isUNtaxpayer = objectjson1.optString("isUNtaxpayer");
						// 美国纳税人识别号--USTIN
						String USTIN = objectjson1.optString("USTIN");
						if(isUNtaxpayer.equals("1")){
							ocrmMCiTaxMain.setUsaflag(isUNtaxpayer);
							ocrmMCiTaxMain.setUstin(USTIN);
						}else if(isUNtaxpayer.equals("2")){
							ocrmMCiTaxMain.setUsaflag("0");
						}
						
						//是否居民国（地区）不发放纳税人识别号 
						if(objectjson1.optString("REASON")!= null && 
								!"".equals(objectjson1.optString("REASON").toString())){
							if(objectjson1.optString("REASON").toString().equals("on")){
								ocrmMCiTaxMain.setIfNoTinCountry("1");
							}else{
								ocrmMCiTaxMain.setIfNoTinCountry("0"); 
							}
						}else{
							ocrmMCiTaxMain.setIfNoTinCountry("0"); 
						}
						
						// 是否账户持有人未能取得纳税人识别号
						if(objectjson1.optString("REASON2")!= null && 
								!"".equals(objectjson1.optString("REASON2").toString())){
							if(objectjson1.optString("REASON2").toString().equals("on")){
								ocrmMCiTaxMain.setIfNoTinPerson("1");
							}else{
								ocrmMCiTaxMain.setIfNoTinPerson("0"); 
							}
						}else{
							ocrmMCiTaxMain.setIfNoTinPerson("0"); 
						}
						
						// 账户持有人未能取得纳税人识别号原因
						ocrmMCiTaxMain.setReason(objectjson1.optString("detailReason"));
						if (!StringUtils.isEmpty(str_allPayTaxes)) {
							JSONArray jsa_payTaxes = JSONArray.fromObject(str_allPayTaxes);
							if (jsa_payTaxes != null && jsa_payTaxes.size() >= 1) {
								for (int i = 0; i < jsa_payTaxes.size(); i++) {
									JSONObject jso = jsa_payTaxes.optJSONObject(i);
									//OcrmMCiAccoTax ocrmMCiAccoTax = new OcrmMCiAccoTax();
									if(jso.optString("code")!=null && 
											!"".equals(jso.optString("code").toString())){
										OcrmMCiTaxSub ocrmMCiTaxSub = new OcrmMCiTaxSub();
										ocrmMCiTaxSub.setCustId(custId1);
										ocrmMCiTaxSub.setTin(jso.optString("code"));// 纳税人识别号
										ocrmMCiTaxSub.setTaxCountry(jso.optString("name"));// 税收居民国
										
										// 创建人
										ocrmMCiTaxSub.setCreateUser(auth.getUserId());
										// 创建时间
										ocrmMCiTaxSub.setCreateTm(ts);
										// 创建机构
										ocrmMCiTaxSub.setCreateOrg(auth.getUnitId());
										// 最后更新人
										ocrmMCiTaxSub.setUpdateUser(auth.getUserId());
										// 最后更新时间
										ocrmMCiTaxSub.setUpdateTm(ts);
										// 修改机构
										ocrmMCiTaxSub.setUpdateOrg(auth.getUnitId());
										ocrmMCiTaxSubList.add(ocrmMCiTaxSub);
									}
								 }
								msgVO.setOcrmMCiTaxSubList(ocrmMCiTaxSubList);
							 }
						 }
					  
					}
					
					// 创建人
					ocrmMCiTaxMain.setCreateUser(auth.getUserId());
					// 创建时间
					ocrmMCiTaxMain.setCreateTm(ts);
					// 创建机构
					ocrmMCiTaxMain.setCreateOrg(auth.getUnitId());
					// 最后更新人
					ocrmMCiTaxMain.setUpdateUser(auth.getUserId());
					// 最后更新时间
					ocrmMCiTaxMain.setUpdateTm(ts);
					// 修改机构
					ocrmMCiTaxMain.setUpdateOrg(auth.getUnitId());
					msgVO.setOcrmMCiTaxMain(ocrmMCiTaxMain);
				}
				
				
				// 3.从户本人声明
				OcrmMCiTaxMain ocrmMCiTaxMain2 = new OcrmMCiTaxMain();
				ocrmMCiTaxMain2.setCustId(custId2);
				List<OcrmMCiTaxSub>  ocrmMCiTaxSubList2 = new ArrayList<OcrmMCiTaxSub>();
				String radio2 = objectjson1.optString("radio2");// 税收居民类型
							// :01-仅为中国税收居民;02-仅为非居民;03-既是中国税收居民又是其他国家（地区）税收居民
				if (!StringUtils.isEmpty(radio2)) {
					ocrmMCiTaxMain2.setPersonStatement(radio2);
					
					String str_allPayTaxes2 = objectjson1.optString("taxData2");
					if(radio2.equals("02") || radio2.equals("03")){
						
						// 是否为美国纳税人
						String isUNtaxpayer2 = objectjson1.optString("isUNtaxpayer2");
						// 美国纳税人识别号--USTIN
						String USTIN2 = objectjson1.optString("USTIN2");
						
						if(isUNtaxpayer2.equals("1")){
							ocrmMCiTaxMain2.setUstin(USTIN2);
							ocrmMCiTaxMain2.setUsaflag(isUNtaxpayer2);
						}else if(isUNtaxpayer2.equals("2")){
							ocrmMCiTaxMain2.setUsaflag("0");
						}
						
						//是否居民国（地区）不发放纳税人识别号 
						if(objectjson1.optString("REASON3")!= null && 
								!"".equals(objectjson1.optString("REASON3").toString())){
							if(objectjson1.optString("REASON3").toString().equals("on")){
								ocrmMCiTaxMain2.setIfNoTinCountry("1");
							}else{
								ocrmMCiTaxMain2.setIfNoTinCountry("0"); 
							}
						}else{
							ocrmMCiTaxMain2.setIfNoTinCountry("0"); 
						}
						
						// 是否账户持有人未能取得纳税人识别号
						if(objectjson1.optString("REASON4")!= null && 
								!"".equals(objectjson1.optString("REASON4").toString())){
							if(objectjson1.optString("REASON4").toString().equals("on")){
								ocrmMCiTaxMain2.setIfNoTinPerson("1");
							}else{
								ocrmMCiTaxMain2.setIfNoTinPerson("0"); 
							}
						}else{
							ocrmMCiTaxMain2.setIfNoTinPerson("0"); 
						}
						
						// 账户持有人未能取得纳税人识别号原因
						ocrmMCiTaxMain2.setReason(objectjson1.optString("detailReason2"));

						if (!StringUtils.isEmpty(str_allPayTaxes2)) {
							JSONArray jsa_payTaxes2 = JSONArray.fromObject(str_allPayTaxes2);
							if (jsa_payTaxes2 != null && jsa_payTaxes2.size() >= 1) {
								for (int i = 0; i < jsa_payTaxes2.size(); i++) {
									JSONObject jso2 = jsa_payTaxes2.optJSONObject(i);
									//OcrmMCiAccoTax ocrmMCiAccoTax = new OcrmMCiAccoTax();
									if(jso2.optString("code")!=null && 
											!"".equals(jso2.optString("code").toString())){
										OcrmMCiTaxSub ocrmMCiTaxSub2 = new OcrmMCiTaxSub();
										ocrmMCiTaxSub2.setCustId(custId2);
										ocrmMCiTaxSub2.setTin(jso2.optString("code"));// 纳税人识别号
										ocrmMCiTaxSub2.setTaxCountry(jso2.optString("name"));// 税收居民国
										
										// 创建人
										ocrmMCiTaxSub2.setCreateUser(auth.getUserId());
										// 创建时间
										ocrmMCiTaxSub2.setCreateTm(ts);
										// 创建机构
										ocrmMCiTaxSub2.setCreateOrg(auth.getUnitId());
										// 最后更新人
										ocrmMCiTaxSub2.setUpdateUser(auth.getUserId());
										// 最后更新时间
										ocrmMCiTaxSub2.setUpdateTm(ts);
										// 修改机构
										ocrmMCiTaxSub2.setUpdateOrg(auth.getUnitId());
										ocrmMCiTaxSubList2.add(ocrmMCiTaxSub2);
									}
								 }
								msgVO.setOcrmMCiTaxSubList2(ocrmMCiTaxSubList2);
							 }
						 }
					  
					}
					
					// 最后更新人
					ocrmMCiTaxMain2.setUpdateUser(auth.getUserId());
					// 最后更新时间
					ocrmMCiTaxMain2.setUpdateTm(ts);
					// 修改机构
					ocrmMCiTaxMain2.setUpdateOrg(auth.getUnitId());
					msgVO.setOcrmMCiTaxMain2(ocrmMCiTaxMain2);
				}
			}

			// 银行信息
			OcrmFCiOpenInfo ocrmFCiOpenInfo = new OcrmFCiOpenInfo();
			
			// 邮寄地址是否同居住地址
			if(objectjson1.optString("same")!=null &&
					!"".equals(objectjson1.optString("same").toString())){
				String isEquAdd = objectjson1.optString("same").toString();
				if(isEquAdd.equals("1") || isEquAdd.equals("on")){
					ocrmFCiOpenInfo.setIsEquAdd("1");
				}else{
					ocrmFCiOpenInfo.setIsEquAdd("0");
				}
			}
			
			// ocrmFCiOpenInfo.setCustId(cust_id);
			// 境内/外
			String cusCategory = accountInfoJson.optString("cusCategory");
			ocrmFCiOpenInfo.setInoutFlag(cusCategory);
			acrmFCiCustomer.setInoutFlag(cusCategory);//客户表中落地境内外
			
			List<OcrmFCiAccountInfo> custAccountInfoList = new ArrayList<OcrmFCiAccountInfo>();
			// 境内外客户类型
			if (cusCategory.equals("D")) {// 境内客户
				
				if (accountInfoJson.optJSONArray("DCheckbox") != null) {
					JSONArray jingneiCheckbox = accountInfoJson.optJSONArray("DCheckbox");
					for (int i = 0; i < jingneiCheckbox.size(); i++) {
						OcrmFCiAccountInfo ocrmFCiAccountInfo = new OcrmFCiAccountInfo();
						// ocrmFCiAccountInfo.setCust_id(cust_id);
						if(jingneiCheckbox.get(i)!=null &&
								!"".equals(jingneiCheckbox.get(i).toString())){
							ocrmFCiAccountInfo.setActType(jingneiCheckbox.get(i).toString());
						}
						
						// 创建人
						ocrmFCiAccountInfo.setCreateUser(auth.getUserId());
						// 创建时间
						ocrmFCiAccountInfo.setCreateTm(ts);
						// 创建机构
						ocrmFCiAccountInfo.setCreateOrg(auth.getUnitId());
						// 最后更新人
						ocrmFCiAccountInfo.setUpdateUser(auth.getUserId());
						// 最后更新时间
						ocrmFCiAccountInfo.setUpdateTm(ts);
						// 修改机构
						ocrmFCiAccountInfo.setUpdateOrg(auth.getUnitId());
						custAccountInfoList.add(ocrmFCiAccountInfo);
					}
				}else{
					String radioD = accountInfoJson.optString("DCheckbox");
					OcrmFCiAccountInfo ocrmFCiAccountInfo = new OcrmFCiAccountInfo();
					// ocrmFCiAccountInfo.setCust_id(cust_id);
					if(radioD!=null &&!"".equals(radioD.toString())){
						ocrmFCiAccountInfo.setActType(radioD.toString());
					}
					
					// 创建人
					ocrmFCiAccountInfo.setCreateUser(auth.getUserId());
					// 创建时间
					ocrmFCiAccountInfo.setCreateTm(ts);
					// 创建机构
					ocrmFCiAccountInfo.setCreateOrg(auth.getUnitId());
					// 最后更新人
					ocrmFCiAccountInfo.setUpdateUser(auth.getUserId());
					// 最后更新时间
					ocrmFCiAccountInfo.setUpdateTm(ts);
					// 修改机构
					ocrmFCiAccountInfo.setUpdateOrg(auth.getUnitId());
					custAccountInfoList.add(ocrmFCiAccountInfo);
					
				}
				
			} else if (cusCategory.equals("F")) {// 境外客户
				
				if (accountInfoJson.optJSONArray("FCheckbox") != null) {
					JSONArray jingwaiCheckbox = accountInfoJson.optJSONArray("FCheckbox");
					for (int i = 0; i < jingwaiCheckbox.size(); i++) {
						OcrmFCiAccountInfo ocrmFCiAccountInfo = new OcrmFCiAccountInfo();
						if(jingwaiCheckbox.get(i)!=null &&
								!"".equals(jingwaiCheckbox.get(i).toString())){
							ocrmFCiAccountInfo.setActType(jingwaiCheckbox.get(i).toString());
						}
						
						// ocrmFCiAccountInfo.setCust_id(cust_id);
						
						// 创建人
						ocrmFCiAccountInfo.setCreateUser(auth.getUserId());
						// 创建时间
						ocrmFCiAccountInfo.setCreateTm(ts);
						// 创建机构
						ocrmFCiAccountInfo.setCreateOrg(auth.getUnitId());
						// 最后更新人
						ocrmFCiAccountInfo.setUpdateUser(auth.getUserId());
						// 最后更新时间
						ocrmFCiAccountInfo.setUpdateTm(ts);
						// 修改机构
						ocrmFCiAccountInfo.setUpdateOrg(auth.getUnitId());
						custAccountInfoList.add(ocrmFCiAccountInfo);
					}
				}else{
					String radioF = accountInfoJson.optString("FCheckbox");
					OcrmFCiAccountInfo ocrmFCiAccountInfo = new OcrmFCiAccountInfo();
					// ocrmFCiAccountInfo.setCust_id(cust_id);
					if(radioF!=null &&!"".equals(radioF.toString())){
						ocrmFCiAccountInfo.setActType(radioF.toString());
					}
					
					// 创建人
					ocrmFCiAccountInfo.setCreateUser(auth.getUserId());
					// 创建时间
					ocrmFCiAccountInfo.setCreateTm(ts);
					// 创建机构
					ocrmFCiAccountInfo.setCreateOrg(auth.getUnitId());
					// 最后更新人
					ocrmFCiAccountInfo.setUpdateUser(auth.getUserId());
					// 最后更新时间
					ocrmFCiAccountInfo.setUpdateTm(ts);
					// 修改机构
					ocrmFCiAccountInfo.setUpdateOrg(auth.getUnitId());
					custAccountInfoList.add(ocrmFCiAccountInfo);
				}
			}

			// 借记卡申请
			if(serviceInfoJson.optString("jiejika")!=null ){
				String isOpenCard = serviceInfoJson.optString("jiejika").toString();
				
				
				if (isOpenCard.equals("1")) {
					ocrmFCiOpenInfo.setIsOpenCard(isOpenCard);
					acrmFCiPerKeyflag.setIsDebitCard(isOpenCard);
					// 卡种类
					if(serviceInfoJson.optString("cardType")!=null &&
							!"".equals(serviceInfoJson.optString("cardType").toString())){
						ocrmFCiOpenInfo.setCardCatlg(serviceInfoJson.optString("cardType").toString());
					}
					if(serviceInfoJson.optString("cardType1_0")!=null &&
							!"".equals(serviceInfoJson.optString("cardType1_0").toString())){
						ocrmFCiOpenInfo.setCardType(serviceInfoJson.optString("cardType1_0").toString());
					}
					if(serviceInfoJson.optString("cardType2_0")!=null &&
							!"".equals(serviceInfoJson.optString("cardType2_0").toString())){
						ocrmFCiOpenInfo.setCardFc(serviceInfoJson.optString("cardType2_0").toString());
					}
				}else{
					ocrmFCiOpenInfo.setIsOpenCard("0");
					acrmFCiPerKeyflag.setIsDebitCard("0");
				}
				
				// ATM转账限额设置
				// 每日累计限额
				if(serviceInfoJson.optString("ATMDayLimitDefault") != null &&
						!"".equals(serviceInfoJson.optString("ATMDayLimitDefault").toString())){
					String ATMDayLimitDefault = serviceInfoJson.optString("ATMDayLimitDefault").toString();
					ocrmFCiOpenInfo.setIsDftlmtdAtm(ATMDayLimitDefault);
					if(ATMDayLimitDefault.equals("1")){// 默认每日累计限额（RMB50,000元）
						ocrmFCiOpenInfo.setLmtamtDAtm(new BigDecimal("50000"));
					}else if(ATMDayLimitDefault.equals("0")){// 每日累计转账最高限额RMB
						if(serviceInfoJson.optString("ATMDayLimit")!=null &&
								!"".equals(serviceInfoJson.optString("ATMDayLimit").toString())){}
						ocrmFCiOpenInfo.setLmtamtDAtm(new BigDecimal(serviceInfoJson.optString("ATMDayLimit").toString()));
					}
				}
				
				// 每日累计笔数
				if(serviceInfoJson.optString("ATMDayCountDefault") != null &&
						!"".equals(serviceInfoJson.optString("ATMDayCountDefault").toString())){
					String ATMDayCountDefault = serviceInfoJson.optString("ATMDayCountDefault").toString();
					ocrmFCiOpenInfo.setIsDftcntAtm(ATMDayCountDefault);
					if(ATMDayCountDefault.equals("1")){// 默认每日累计笔数（10笔）
						ocrmFCiOpenInfo.setLmtcntDAtm(new BigDecimal("10"));
					}else if(ATMDayCountDefault.equals("0")){// 每日累计转账笔数
						if(serviceInfoJson.optString("ATMDayLimitCount")!=null &&
								!"".equals(serviceInfoJson.optString("ATMDayLimitCount").toString())){}
						ocrmFCiOpenInfo.setLmtcntDAtm(new BigDecimal(serviceInfoJson.optString("ATMDayLimitCount").toString()));
					}
				}
				
				// 每年累计限额
				if(serviceInfoJson.optString("ATMYearLimitDefault") != null &&
						!"".equals(serviceInfoJson.optString("ATMYearLimitDefault").toString())){
					String ATMYearLimitDefault = serviceInfoJson.optString("ATMYearLimitDefault").toString();
					ocrmFCiOpenInfo.setIsDftlmtyAtm(ATMYearLimitDefault);
					if(ATMYearLimitDefault.equals("1")){// 默认每年累计限额（RMB10,000,000元）
						ocrmFCiOpenInfo.setLmtamtYAtm(new BigDecimal("10000000"));
					}else if(ATMYearLimitDefault.equals("0")){//每年累计转账最高限额RMB
						if(serviceInfoJson.optString("ATMYearLimit")!=null &&
								!"".equals(serviceInfoJson.optString("ATMYearLimit").toString())){}
						ocrmFCiOpenInfo.setLmtamtYAtm(new BigDecimal(serviceInfoJson.optString("ATMYearLimit").toString()));
					}
				}
				
				// POS消费限额设置
				if(serviceInfoJson.optString("POSDefault") != null &&
						!"".equals(serviceInfoJson.optString("POSDefault").toString())){
					String POSDefault = serviceInfoJson.optString("POSDefault").toString();
					ocrmFCiOpenInfo.setIsDftlmtPos(POSDefault);
					if(POSDefault.equals("1")){// 默认单笔限额（RMB500,000元）
						ocrmFCiOpenInfo.setLmtamtPos(new BigDecimal("500000"));
					}else if(POSDefault.equals("0")){// 单笔消费限额RMB
						if(serviceInfoJson.optString("eachCustemLimit")!=null &&
								!"".equals(serviceInfoJson.optString("eachCustemLimit").toString())){}
						ocrmFCiOpenInfo.setLmtamtPos(new BigDecimal(serviceInfoJson.optString("eachCustemLimit").toString()));
					}
				}
			}
			
			
			// 电子银行服务
			if(serviceInfoJson.optString("dianziBank")!=null){
				String dianziBank = serviceInfoJson.optString("dianziBank").toString();
				if(dianziBank.equals("1")){
					acrmFCiPerKeyflag.setIsEbankSignCust(dianziBank);
					ocrmFCiOpenInfo.setIsOpenEbk(dianziBank);
					// 网络银行
					if(serviceInfoJson.optString("netBank")!=null ){
						String netBank =serviceInfoJson.optString("netBank").toString();
						if(netBank.equals("1")){
							ocrmFCiOpenInfo.setIsNetbk(netBank);
							// ukey
							if(serviceInfoJson.optString("ukey")!=null ){
								String ukey =serviceInfoJson.optString("ukey").toString();
								if(ukey.equals("1")){
									ocrmFCiOpenInfo.setIsUkey(ukey);
								}else{
									ocrmFCiOpenInfo.setIsUkey("0");
								}
							}
							
							// 短信认证
							if(serviceInfoJson.optString("shortMessage")!=null ){
								String shortMessage =serviceInfoJson.optString("shortMessage").toString();
								if(shortMessage.equals("1")){
									ocrmFCiOpenInfo.setIsMsgNetbk(shortMessage);
								}else{
									ocrmFCiOpenInfo.setIsMsgNetbk("0");
								}
							}
						}else{
							ocrmFCiOpenInfo.setIsNetbk("0");
						}
					}
					
					
					/*// 电话银行
					if(serviceInfoJson.optString("phoneBank")!=null ){
						String phoneBank =serviceInfoJson.optString("phoneBank").toString();
						if(phoneBank.equals("1")){
							ocrmFCiOpenInfo.setIsTelbk(phoneBank);
						}else{
							ocrmFCiOpenInfo.setIsTelbk("0");
						}
					}*/
					// 手机银行
					if(serviceInfoJson.optString("mobileBank")!=null ){
						String mobileBank =serviceInfoJson.optString("mobileBank").toString();
						if(mobileBank.equals("1")){
							ocrmFCiOpenInfo.setIsPhone(mobileBank);
							// 短信验证
							if(serviceInfoJson.optString("shortMessage2")!=null ){
								String shortMessage2 =serviceInfoJson.optString("shortMessage2").toString();
								if(shortMessage2.equals("1")){
									ocrmFCiOpenInfo.setIsMsgPhone(shortMessage2);
								}else{
									ocrmFCiOpenInfo.setIsMsgPhone("0");
								}
							}
						}else{
							ocrmFCiOpenInfo.setIsPhone("0");
						}
					}
					
					
					
					// 日累计转账限额
					if(serviceInfoJson.optString("dayAccLimitDefault") != null &&
							!"".equals(serviceInfoJson.optString("dayAccLimitDefault").toString())){
						String dayAccLimitDefault = serviceInfoJson.optString("dayAccLimitDefault").toString();
						ocrmFCiOpenInfo.setIsDftlmtdEb(dayAccLimitDefault);
						if(dayAccLimitDefault.equals("1")){// 默认无限制
							
						}else if(dayAccLimitDefault.equals("0")){// 自定义
							if(serviceInfoJson.optString("dayAccSelfDefine")!=null &&
									!"".equals(serviceInfoJson.optString("dayAccSelfDefine").toString())){}
							ocrmFCiOpenInfo.setLmtamtDEb(new BigDecimal(serviceInfoJson.optString("dayAccSelfDefine").toString()));
						}
					}
					// 日累计转账笔数
					if(serviceInfoJson.optString("dayAccCountDefault") != null &&
							!"".equals(serviceInfoJson.optString("dayAccCountDefault").toString())){
						String dayAccCountDefault = serviceInfoJson.optString("dayAccCountDefault").toString();
						ocrmFCiOpenInfo.setIsDftcntEb(dayAccCountDefault);
						if(dayAccCountDefault.equals("1")){// 默认无限制
							
						}else if(dayAccCountDefault.equals("0")){// 自定义
							if(serviceInfoJson.optString("dayCountSelfDefine")!=null &&
									!"".equals(serviceInfoJson.optString("dayCountSelfDefine").toString())){}
							ocrmFCiOpenInfo.setLmtcntDEb(new BigDecimal(serviceInfoJson.optString("dayCountSelfDefine").toString()));
						}
					}
					
					// 年累计转账限额
					if(serviceInfoJson.optString("yearAccLimitDefault") != null &&
							!"".equals(serviceInfoJson.optString("yearAccLimitDefault").toString())){
						String yearAccLimitDefault = serviceInfoJson.optString("yearAccLimitDefault").toString();
						ocrmFCiOpenInfo.setIsDftlmtyEb(yearAccLimitDefault);
						if(yearAccLimitDefault.equals("1")){// 默认无限制
							
						}else if(yearAccLimitDefault.equals("0")){// 自定义
							if(serviceInfoJson.optString("yearAccSelfDefine")!=null &&
									!"".equals(serviceInfoJson.optString("yearAccSelfDefine").toString())){}
							ocrmFCiOpenInfo.setLmtamtYEb(new BigDecimal(serviceInfoJson.optString("yearAccSelfDefine").toString()));
						}
					}
				}else{
					ocrmFCiOpenInfo.setIsOpenEbk("0");
					acrmFCiPerKeyflag.setIsEbankSignCust("0");
				}
			}
			
			acrmFCiPerKeyflag.setLastUpdateSys("CRM");
			acrmFCiPerKeyflag.setLastUpdateTm(ts);
			acrmFCiPerKeyflag.setLastUpdateUser(auth.getUserId());
			msgVO.setAcrmFCiPerKeyflag(acrmFCiPerKeyflag);
			
			// 电子对账单
			if(serviceInfoJson.optString("elecState")!=null ){
				String elecState =serviceInfoJson.optString("elecState").toString();
				if(elecState.equals("1")){
					ocrmFCiOpenInfo.setIsChk(elecState);
					// 对账单是否同email
					if(serviceInfoJson.optString("isEquEmail")!=null &&
							!"".equals(serviceInfoJson.optString("isEquEmail").toString())){
						String isEquEmail =serviceInfoJson.optString("isEquEmail").toString();
						if(isEquEmail.equals("1")){
							ocrmFCiOpenInfo.setIsEquEmail(isEquEmail);
						}else{
							ocrmFCiOpenInfo.setIsEquEmail("0");
						}
					}
					// E-mail:
					if (serviceInfoJson.optString("elecState").equals("1")) {
						// 电子邮箱E-mail
						if (!serviceInfoJson.optString("email").equals("")) {
							AcrmFCiContmeth email2 = new AcrmFCiContmeth();
							// email2.setContmethId((maxContemethId++)+"");
							// email2.setCustId(cust_id);//客户编号
							// 电子对账单E-mail类型
							email2.setContmethType("106");
							// 联系方式内容：固定电话：国家+下拉默认国家区号+区域码+电话号
							email2.setContmethInfo(serviceInfoJson.optString("email"));
							//记录状态
							email2.setStat("1");
							// 最后更新人
							email2.setLastUpdateUser(auth.getUserId());
							// 最后更新时间
							email2.setLastUpdateTm(ts);
							// 创建人
							email2.setCreateUser(auth.getUserId());
							// 创建时间
							email2.setCreateTm(ts);
							contmethInfoList.add(email2);
						}
					}
					
				}else{
					ocrmFCiOpenInfo.setIsChk("0");
				}
			}
			
			

			// 财务变动通知
			if(serviceInfoJson.optString("chgNotice")!=null){
				String chgNotice =serviceInfoJson.optString("chgNotice").toString();
				if(chgNotice.equals("1")){
					ocrmFCiOpenInfo.setIsChgNotice(chgNotice);
				}else{
					ocrmFCiOpenInfo.setIsChgNotice("0");
				}
			}
			
			
			// 创建人
			ocrmFCiOpenInfo.setCreateUser(auth.getUserId());
			
			// 创建时间
			ocrmFCiOpenInfo.setCreateTm(ts);
			// 创建机构
			ocrmFCiOpenInfo.setCreateOrg(auth.getUnitId());

			// 修改人
			ocrmFCiOpenInfo.setUpdateUser(auth.getUserId());
			// 修改时间
			ocrmFCiOpenInfo.setUpdateTm(ts);
			// 修改机构
			ocrmFCiOpenInfo.setUpdateOrg(auth.getUnitId());

			/*
			 * //复核人工号 objectjson2.reviewNo, //密码 objectjson2.reviewPsw
			 */

			// 最后更新人
			acrmFCiPerson.setLastUpdateUser(auth.getUserId());
			// 最后更新时间
			acrmFCiPerson.setLastUpdateTm(ts);

			// modelMap.put("personInfo", acrmFCiPerson);
			// modelMap.put("customerInfo", acrmFCiCustomer);
			// modelMap.put("addressInfo", addressInfoList);
			// modelMap.put("contmethInfo", contmethInfoList);
			// modelMap.put("custJoinInfo", ocrmFCiCustjoinInfo);
			// modelMap.put("custOpenInfo", ocrmFCiOpenInfo);
			// modelMap.put("custAccountInfo", custAccountInfoList);//
			msgVO.setCustomerInfo(acrmFCiCustomer);
			// msgVO.setCustIdentyInfo(acrmFCiCustIdentifier);
			msgVO.setCustIdentyList(identyList);
			msgVO.setPersonInfo(acrmFCiPerson);
			msgVO.setAddressInfoList(addressInfoList);
			msgVO.setContmethInfoList(contmethInfoList);
			msgVO.setCustBankInfo(ocrmFCiOpenInfo);
			msgVO.setCustAccountInfoList(custAccountInfoList);
			
			//添加归属客户经理
			//--
			String requestMsg = this.packgeMsg(msgVO);
			modelMap.clear();
			modelMap = process(requestMsg, msgVO);
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误，详细信息：" + e.getMessage();
			log.error(logMsg);
			modelMap.put("status", "error");
			modelMap.put("msg", logMsg);
			return modelMap;
		}
		return modelMap;
	}


	
	/**
	 * 拼接请求报文
	 * @param vo	客户信息数据
	 * @return String 返回的请求报文
	 */
	private String packgeMsg(EcifAccountMsg vo){
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiCustomer customerInfo = null;// 客户信息
		List<AcrmFCiCustIdentifier> identyList = null;// 证件信息
		AcrmFCiPerson personInfo = null;// 个人信息
		// List<OcrmMCiAccoTax> accoTaxList = null;// 账户信息
		List<AcrmFCiAddress> addressInfoList = null;// 地址信息
		List<AcrmFCiContmeth> contmethInfoList = null;// 联系方式信息
		// OcrmFCiCustjoinInfo custJoinInfo = null;// 联名户信息
		// OcrmFCiOpenInfo custCardAccountInfo = null;// 银行信息
		// List<OcrmFCiAccountInfo> custAccountInfoList = null;// 账户信息
		// OcrmMCiRelPerson ocrmMCiRelPerson = null;
		OcrmFCiBelongCustmgr belongManagerInfo = null;
		OcrmFCiBelongOrg belongOrgInfo = null;
		if (vo != null) {
			customerInfo = vo.getCustomerInfo();
			identyList = vo.getCustIdentyList();//
			personInfo = vo.getPersonInfo();
			addressInfoList = vo.getAddressInfoList();
			contmethInfoList = vo.getContmethInfoList();
			// custJoinInfo = vo.getCustJoinInfo();
			// custCardAccountInfo = vo.getCustBankInfo();
			// custAccountInfoList = vo.getCustAccountInfoList();
			// accoTaxList = vo.getAccoTaxList();
			// ocrmMCiRelPerson = vo.getCustRelPerson();
			belongManagerInfo = vo.getBelongManager();
			belongOrgInfo = vo.getBelongOrg();
			
		}
		// 拼接请求报文
		// String custId = custId;// 客户号
		String custMgr = auth.getUserId();// 客户经理
		// String custName = vo.getCustomerInfo().getCustName();// 客户名称
		String ecifId = null;// ecif客户号
		// String identId = null;// 证件id号
		// String belongManagerId =
		// null;//vo.getBelongManagerInfo().getCUST_MANAGER_NO();
		// String belongBranchId = null;
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSS");
		SimpleDateFormat df10 = new SimpleDateFormat("HHmmssSS");
		Date currDate = new Date();
		String _userId = auth.getUserId();
		if(_userId.length() >= 8){
			_userId = _userId.substring(0, 3) + _userId.substring(4, 8);
		}
		String ReqSeqNo = df20.format(currDate);//交易流水号(当前时间ms)
		String ReqDt = df8.format(currDate);//日期
		String ReqTm = df10.format(currDate);//时间
		StringBuffer reQxmlorg = new StringBuffer();
		String reqXml = "";
		String Hxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" 
				+ "<TransBody>\n" 
				+ " <RequestHeader>\n" 
				+ "    <ReqSysCd>CRM</ReqSysCd>\n"
				+ "    <ReqSeqNo>" + ReqSeqNo + "</ReqSeqNo>\n"
				+ "    <ReqDt>" + ReqDt + "</ReqDt>\n"
				+ "    <ReqTm>" + ReqTm + "</ReqTm>\n"
				+ "    <DestSysCd>ECIF</DestSysCd>\n"
				+ "    <ChnlNo>82</ChnlNo>\n"
				+ "    <BrchNo>" + auth.getUnitId() + "</BrchNo>\n"
				+ "    <BizLine>6491</BizLine>\n"
				+ "    <TrmNo>TRM10010</TrmNo>\n"
				+ "    <TrmIP>" + auth.getCurrentIP() + "</TrmIP>\n"
				+ "    <TlrNo>" + custMgr + "</TlrNo>\n"
				+ " </RequestHeader>\n"
				+ " <RequestBody>\n"
				+ "    <txCode>openPerAccount4Crm</txCode>\n"
				+ "    <txName>CRM个人一键开户</txName>\n"
				+ "    <authType>1</authType>\n"
				+ "    <authCode>" + auth.getUserCode() + "</authCode>\n";
		// customer：客户信息
		StringBuffer sb_customer = getCustomerInfo(customerInfo);

		// perIdentifier：证件信息
		StringBuffer sb_IdentyList = getIdentyListInfo(identyList);

		// contmeth:联系信息
		StringBuffer sb_contmeth = getContmethInfo(contmethInfoList);

		// address:地址信息
		// List<AcrmFCiAddress> addressInfoList = new
		// ArrayList<AcrmFCiAddress>();//地址信息
		StringBuffer sb_addr = getAddrInfo(addressInfoList);

		// person:个人客户信息
		// AcrmFCiPerson personInfo = new AcrmFCiPerson();//个人信息
		StringBuffer sb_person = getPersonInfo(personInfo);

		// belongBranch：归属机构
		
		 StringBuffer sb_belongBranch = getBelongBranchInfo(belongOrgInfo, auth);
		 
		// belongManager:归属客户经理
		StringBuffer sb_belongManager = getCustManagerInfo(belongManagerInfo);

		// crossindex:交叉索引
//		StringBuffer sb_crossindex = new StringBuffer();
//		sb_crossindex.append("<crossindex>\n");// crossindex:交叉索引--start
//		sb_crossindex.append("    <srcSysNo></srcSysNo>\n");// 源系统编号**
//		sb_crossindex.append("    <srcSysCustNo></srcSysCustNo>\n");// 源系统客户编号**
//		sb_crossindex.append("</crossindex>\n");// crossindex:交叉索引--end

		reQxmlorg.append(Hxml);
		reQxmlorg.append(sb_customer.toString());
		reQxmlorg.append(sb_IdentyList.toString());
		reQxmlorg.append(sb_contmeth.toString());
		reQxmlorg.append(sb_addr.toString());
		reQxmlorg.append(sb_person.toString());
		reQxmlorg.append(sb_belongBranch.toString());
		reQxmlorg.append(sb_belongManager.toString());
//		reQxmlorg.append(sb_crossindex.toString());

		reQxmlorg.append("</RequestBody></TransBody>\n");
		return reqXml = reQxmlorg.toString();
	}

	/**
	 * 发送请求并解析响应报文
	 * @param reqXml	请求报文
	 * @param vo		客户信息数据
	 * @return	Map		返回信息
	 */
	private Map<String, Object> process(String reqXml, EcifAccountMsg vo){
		String resultContent = "";
		String ecifId = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 调用ECIF开户
			TxData txData = new TxData();
			reqXml = String.format("%08d", reqXml.getBytes("GBK").length) + reqXml;
			txData.setReqMsg(reqXml);
			Transaction trans = new EcifOpenAccountTransaction(txData);
			trans.process();
			TxLog txLog = trans.getTxLog();
			this.baseDAO.save(txLog);
			map = txData.getTxMap();
//			map = process(reqXml, vo.getSeqNO());
			log.info("获取ECIF客户号:" + map);
			Object openResult = map.get("status");
			if (openResult == null || openResult.toString().equals("error")) {
				resultContent = "调用ECIF开户失败";
			} else if (openResult != null && openResult.toString().equals("success")) {
				resultContent = "调用ECIF开户成功";
				// ECIF客户号
				ecifId = map.get("custNo") != null ? map.get("custNo").toString() : "";
				// 证件ID
				// identId = map.get("identId") != null ? map.get("identId")
				// .toString() : "";
				// //归属客户经理ID
				// belongManagerId = map.get("belongManagerId") != null ?
				// map.get(
				// "belongManagerId").toString() : "";
				// //归属机构ID
				// belongBranchId = map.get("belongBranchId") != null ? map.get(
				// "belongBranchId").toString() : "";

				if (ecifId != null && !ecifId.equals("")) {
					resultContent = "调用ECIF开户成功";
					map.put("status", "success");
					map.put("msg", resultContent);
					map.put("custId", ecifId);
					log.error(resultContent);
					// crm数据落地
					Map<String, Object> saveCRMData = saveCRMData(map, vo);
					map.putAll(saveCRMData);
				} else {
					resultContent = "调ecif开户接口失败，未取得客户编号!";
					map.put("status", "error");
					map.put("msg", resultContent);
					log.error("调ecif开户接口失败，未取得客户编号!");
					return map;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultContent = "保存客户信息发生异常!";
			map.put("status", "error");
			map.put("msg", resultContent);
			log.info("保存客户信息发生异常!");
			return map;
		 }
		return map;
	}

	/**
	 * 拼接custormerInfo的xml报文部分
	 * 
	 * @param customerInfo
	 * @return
	 */
	private StringBuffer getCustomerInfo(AcrmFCiCustomer customerInfo) {
		StringBuffer sb_customer = new StringBuffer();//
		if (customerInfo != null) {
			sb_customer.append("<customer>\n");// customer：客户信息--start
			sb_customer.append("    <totalDebt></totalDebt>\n");// 总负债 --
			sb_customer.append("    <riskLevel></riskLevel>\n");// 风险等级（个金）--
			sb_customer.append("    <infoPer></infoPer>\n");// 客户信息完整度--
			sb_customer.append("    <custStat></custStat>\n");// 客户状态--
			sb_customer.append("    <enName></enName>\n");// 英文名称--
			sb_customer.append("    <realFlag></realFlag>\n");// 真实性认证--
			sb_customer.append("    <ebankFlag></ebankFlag>\n");// E客户标志--
			sb_customer.append("    <potentialFlag></potentialFlag>\n");// 潜在客户标志--
			sb_customer.append("    <riskNationCode>" + customerInfo.getRiskNationCode() + "</riskNationCode>\n");// 风险国家代号**
			sb_customer.append("    <industType></industType>\n");// 所属行业--
			sb_customer.append("    <jobType></jobType>\n");// 职业类别--
			sb_customer.append("    <enShortName></enShortName>\n");// 英文简称--
			sb_customer.append("    <shortName></shortName>\n");// 客户简称--
			sb_customer.append("    <postName></postName>\n");// 邮递户名--
			sb_customer.append("    <custName>" + customerInfo.getCustName() + "</custName>\n");// 客户名称**
			sb_customer.append("    <identNo>" + customerInfo.getIdentNo() + "</identNo>\n");// 证件号码**
			sb_customer.append("    <identType>" + customerInfo.getIdentType() + "</identType>\n");// 证件类型**
			sb_customer.append("    <custType></custType>\n");// 客户类型--
			sb_customer.append("    <coreNo></coreNo>\n");// 核心客户号--
			sb_customer.append("    <faxtradeNorecNum></faxtradeNorecNum>\n");// 传真交易正本未回收数量--
			sb_customer.append("    <currentAum></currentAum>\n");// 管理总资产时点值--
			sb_customer.append("    <creditLevel></creditLevel>\n");// 信用评级（法金）--
			sb_customer.append("    <riskValidDate></riskValidDate>\n");// 风险等级有效期--
			sb_customer.append("    <custLevel></custLevel>\n");// 客户级别--
			sb_customer.append("    <createTellerNoLn></createTellerNoLn>\n");// 客户创建柜员编号(LN)--
			sb_customer.append("    <createBranchNoLn></createBranchNoLn>\n");// 客户创建机构编号(LN)--
			sb_customer.append("    <createTimeLn></createTimeLn>\n");// 客户创建时间(LN)--
			sb_customer.append("    <createDateLn></createDateLn>\n");// 客户创建日期(LN)--
			sb_customer.append("    <createTellerNo></createTellerNo>\n");// 客户创建柜员编号(CB)--
			sb_customer.append("    <createBranchNo></createBranchNo>\n");// 客户创建机构编号(CB)--
			sb_customer.append("    <createTime></createTime>\n");// 客户创建时间(CB)--
			sb_customer.append("    <createDate></createDate>\n");// 客户创建日期(CB)--
			sb_customer.append("    <cusCorpRel></cusCorpRel>\n");// 与我行合作关系--
			sb_customer.append("    <cusBankRel>"+customerInfo.getCusBankRel()+"</cusBankRel>\n");// 与我行关联关系--
			sb_customer.append("    <profctr></profctr>\n");// --
			sb_customer.append("    <swift></swift>\n");//
			sb_customer.append("    <staffin>" + customerInfo.getStaffin() + "</staffin>\n");// 关联人属性(核心）**
			sb_customer.append("    <loanCustStat></loanCustStat>\n");// 信贷客户状态--
			sb_customer.append("    <loanCustRank></loanCustRank>\n");// 信贷客户分类--
			sb_customer.append("    <recommender></recommender>\n");// 推荐人--
			sb_customer.append("    <sourceChannel>" + customerInfo.getSourceChannel() + "</sourceChannel>\n");// 客户来源渠道**
			sb_customer.append("    <arCustType></arCustType>\n");// AR客户类型--
			sb_customer.append("    <arCustFlag></arCustFlag>\n");// AR客户标志--
			sb_customer.append("    <loanMainBrId></loanMainBrId>\n");// 信贷主管机构--
			sb_customer.append("    <loanCustMgr></loanCustMgr>\n");// 信贷主管客户经理--
			sb_customer.append("    <firstLoanDate></firstLoanDate>\n");// 信贷最早开户日期--
			sb_customer.append("    <linkmanTel></linkmanTel>\n");// 联系人电话--
			sb_customer.append("    <linkmanName></linkmanName>\n");// 联系人姓名--
			sb_customer.append("    <custnmIdentModifiedFlag></custnmIdentModifiedFlag>\n");// custname
																							// or
																							// id/refno
																							// change
																							// by--
			sb_customer.append("    <mergeFlag></mergeFlag>\n");// 客户合并标志--
			sb_customer.append("    <vipFlag></vipFlag>\n");// VIP标志--
			sb_customer.append("    <blankFlag></blankFlag>\n");// 空白客户标志--
			sb_customer.append("    <inoutFlag>" + customerInfo.getInoutFlag() + "</inoutFlag>\n");// 境内外标志**
			sb_customer.append("</customer>\n");// customer：客户信息--end
		}
		return sb_customer;//
	}

	/**
	 * 拼接证件信息
	 * 
	 * @param identyList
	 * @return
	 */
	private StringBuffer getIdentyListInfo(List<AcrmFCiCustIdentifier> identyList) {
		StringBuffer sb_identyList = new StringBuffer();
		if (identyList != null && identyList.size() >= 1) {
			// sb_identyList.append("<identyList>\n");
			for (int i = 0; i < identyList.size(); i++) {
				AcrmFCiCustIdentifier identyInfo = identyList.get(i);//
				StringBuffer sb_perIdentifier = new StringBuffer();
				sb_perIdentifier.append("<perIdentifier>\n");// perIdentifier：证件信息--start
				sb_perIdentifier.append("    <openAccIdentModifiedFlag></openAccIdentModifiedFlag>\n");// 开户证件修改标志--
				sb_perIdentifier.append("    <identModifiedTime></identModifiedTime>\n");// 证件修改时间--
				sb_perIdentifier.append("    <verifyDate></verifyDate>\n");// 校验日期--
				sb_perIdentifier.append("    <verifyEmployee></verifyEmployee>\n");// 校验员工--
				sb_perIdentifier.append("    <verifyResult></verifyResult>\n");// 校验结果--
				sb_perIdentifier.append("    <lastUpdateSys>CRM</lastUpdateSys>\n");// 最后更新系统--
				sb_perIdentifier.append("    <identNo>" + identyInfo.getIdentNo() + "</identNo>\n");// 证件号码**
				sb_perIdentifier.append("    <identCustName>" + identyInfo.getIdentCustName() + "</identCustName>\n");// 证件户名**
				sb_perIdentifier.append("    <identDesc>"+identyInfo.getIdentDesc()+"</identDesc>\n");// 证件描述--
				sb_perIdentifier.append("    <countryOrRegion>"+identyInfo.getCountryOrRegion()+"</countryOrRegion>\n");// 发证国家或地区**
				sb_perIdentifier.append("    <identOrg>"+identyInfo.getIdentOrg()+"</identOrg>\n");// 发证机构**
				sb_perIdentifier.append("    <identCheckFlag></identCheckFlag>\n");// 证件年检标志--
				sb_perIdentifier.append("    <idenRegDate></idenRegDate>\n");// 证件登记日期--
				sb_perIdentifier.append("    <identCheckingDate></identCheckingDate>\n");// 证件年检到期日--
				sb_perIdentifier.append("    <lastUpdateUser>" + identyInfo.getLastUpdateUser() + "</lastUpdateUser>\n");// 最后更新人
				sb_perIdentifier.append("    <lastUpdateTm>" + identyInfo.getLastUpdateTm() + "</lastUpdateTm>\n");// 最后更新时间
				sb_perIdentifier.append("    <txSeqNo></txSeqNo>\n");// 交易流水号--
				sb_perIdentifier.append("    <identCheckedDate></identCheckedDate>\n");// 证件年检日期--
				sb_perIdentifier.append("    <identValidPeriod></identValidPeriod>\n");// 证件有效期
				sb_perIdentifier.append("    <identEffectiveDate></identEffectiveDate>\n");// 证件生效日期--
				sb_perIdentifier.append("    <identExpiredDate></identExpiredDate>\n");// 证件失效日期--
				sb_perIdentifier.append("    <identValidFlag></identValidFlag>\n");// 证件有效标志--
				sb_perIdentifier.append("    <identPeriod></identPeriod>\n");// 证件期限--
				sb_perIdentifier.append("    <isOpenAccIdentLn></isOpenAccIdentLn>\n");// 是否开户证件(信贷)--
				sb_perIdentifier.append("    <identApproveUnit></identApproveUnit>\n");// 证件批准单位--
				sb_perIdentifier.append("    <isOpenAccIdent>" + identyInfo.getIsOpenAccIdent() + "</isOpenAccIdent>\n");// 是否开户证件(核心)--
			  //sb_perIdentifier.append("    <identId></identId>\n");//证件编号**
				sb_perIdentifier.append("    <custId></custId>\n");// 客户编号**
				sb_perIdentifier.append("    <identType>" + identyInfo.getIdentType() + "</identType>\n");// 证件类型**
				sb_perIdentifier.append("</perIdentifier>\n");// perIdentifier：证件信息--end
				sb_identyList.append(sb_perIdentifier.toString());
			}
			// sb_identyList.append("</identyList>\n");//
		}
		return sb_identyList;//
	}

	/**
	 * 拼接联系方式信息
	 * 
	 * @param contmethInfoList
	 * @return
	 */
	private StringBuffer getContmethInfo(List<AcrmFCiContmeth> contmethInfoList) {
		StringBuffer sb_contmeth = new StringBuffer();
		if (contmethInfoList != null && contmethInfoList.size() >= 1) {
//			sb_contmeth.append("<contmethList>\n");
			// List<EcifCustContmethVO> contmethList = vo.getCustContmethList();
			if (contmethInfoList != null && contmethInfoList.size() >= 1) {
				for (int i = 0; i < contmethInfoList.size(); i++) {
					AcrmFCiContmeth contVo = contmethInfoList.get(i);
					sb_contmeth.append("<contmeth>\n");// contmeth:联系信息--start
//					sb_contmeth.append("    <contmethId></contmethId>\n");//联系方式ID
					sb_contmeth.append("    <isPriori></isPriori>\n");// 是否首选
					sb_contmeth.append("    <contmethType>" + contVo.getContmethType() + "</contmethType>\n");// 联系方式类型##
					sb_contmeth.append("    <contmethInfo>" + contVo.getContmethInfo() + "</contmethInfo>\n");// 联系方式内容##
					sb_contmeth.append("    <contmethSeq></contmethSeq>\n");// 联系顺序号
					sb_contmeth.append("    <remark></remark>\n");// 备注
					sb_contmeth.append("    <stat></stat>\n");// 记录状态
					sb_contmeth.append("</contmeth>\n");// contmeth:联系信息--end
				}
			}
//			sb_contmeth.append("</contmethList>\n");
		}
		return sb_contmeth;
	}

	/**
	 * 拼接地址信息
	 * 
	 * @param addressInfoList
	 * @return
	 */
	private StringBuffer getAddrInfo(List<AcrmFCiAddress> addressInfoList) {
		StringBuffer sb_address = new StringBuffer();
		if (addressInfoList != null && addressInfoList.size() >= 1) {
//			sb_address.append("<addrList>\n");//
			for (int i = 0; i < addressInfoList.size(); i++) {
				AcrmFCiAddress address = addressInfoList.get(i);
				sb_address.append("<address>\n");// address:地址信息--start
//				sb_address.append("		<addrId>00010001001</addrId>\n");//地址标识--
				sb_address.append("		<addrType>" + address.getAddrType() + "</addrType>\n");// 地址类型##
				sb_address.append("    	<addr>" + address.getAddr() + "</addr>\n");// 详细地址##
				sb_address.append("    	<enAddr></enAddr>\n");// 英文地址--
				sb_address.append("    	<contmethInfo>" + address.getContmethInfo() + "</contmethInfo>\n");// 地址联系电话**
				sb_address.append("    	<zipcode>"+address.getZipcode()+"</zipcode>\n");// 邮政编码--
				sb_address.append("    	<countryOrRegion>" + address.getCountryOrRegion() + "</countryOrRegion>\n");// 国家或地区代码**
				sb_address.append("    	<adminZone></adminZone>\n");// 行政区划代码
				sb_address.append("   	<areaCode></areaCode>\n");// 大区代码
				sb_address.append("    	<provinceCode></provinceCode>\n");// 省直辖市自治区代码
				sb_address.append("    	<cityCode></cityCode>\n");// 市地区州盟代码--
				sb_address.append("    	<countyCode>" + address.getCountyCode() + "</countyCode>\n");// 县区代码**
				sb_address.append("    	<townName></townName>\n");// 乡镇名称--
				sb_address.append("    	<townCode></townCode>\n");// 乡镇代码--
				sb_address.append("    	<streetName></streetName>\n");// 街道名称--
				sb_address.append("    	<villageNo></villageNo>\n");// 行政村编号--
				sb_address.append("    	<villageName></villageName>\n");// 行政村名称--
				sb_address.append("</address>\n");// address:地址信息--end
			}
//			sb_address.append("</addrList>\n");//
		}
		return sb_address;
	}

	/**
	 * 拼接个人信息
	 * 
	 * @param personInfo
	 * @return
	 */
	private StringBuffer getPersonInfo(AcrmFCiPerson personInfo) {
		StringBuffer sb_person = new StringBuffer();

		if (personInfo != null) {
			sb_person.append("<person>\n");// person:个人客户信息--start
			sb_person.append("    <salaryAcctNo></salaryAcctNo>\n");// 工资账号--
			sb_person.append("    <salaryAcctBank></salaryAcctBank>\n");// 工资账户开户行--
			sb_person.append("    <bankDuty></bankDuty>\n");// 在我行职务--
			sb_person.append("    <holdStockAmt></holdStockAmt>\n");// 拥有我行股份金额--
			sb_person.append("    <careerTitle>"+personInfo.getCareerTitle()+"</careerTitle>\n");// 职称--
			sb_person.append("    <qualification></qualification>\n");// 资格证书名称--
			sb_person.append("    <hasQualification></hasQualification>\n");// 是否有执业资格--
			sb_person.append("    <currCareerStartDate></currCareerStartDate>\n");// 参加本单位日期--
			sb_person.append("    <annualIncome></annualIncome>\n");// 年收入--
			sb_person.append("    <annualIncomeScope></annualIncomeScope>\n");// 年收入范围--
			sb_person.append("    <careerStartDate></careerStartDate>\n");// 参加工作时间--
			sb_person.append("    <workResult></workResult>\n");// 业绩评价--
			sb_person.append("    <duty>" + personInfo.getDuty() + "</duty>\n");// 职务##
			sb_person.append("    <cntName></cntName>\n");// 单位联系人--
			sb_person.append("    <adminLevel></adminLevel>\n");// 行政级别--
			sb_person.append("    <postPhone>" + personInfo.getPostPhone() + "</postPhone>\n");// 联系电话**
			sb_person.append("    <postZipcode>"+personInfo.getPostZipcode()+"</postZipcode>\n");// 通讯编码--
			sb_person.append("    <postAddr>" + personInfo.getPostAddr() + "</postAddr>\n");// 通讯地址--
			sb_person.append("    <unitFex></unitFex>\n");// 传真号码--
			sb_person.append("    <unitTel></unitTel>\n");// 单位电话--
			sb_person.append("    <unitZipcode></unitZipcode>\n");// 单位邮编
			sb_person.append("    <unitAddr></unitAddr>\n");// 单位地址--
			sb_person.append("    <unitChar></unitChar>\n");// 单位性质--
			sb_person.append("    <unitName>" + personInfo.getUnitName() + "</unitName>\n");// 单位名称
			sb_person.append("    <careerType>" + personInfo.getCareerType() + "</careerType>\n");// 职业**
			sb_person.append("    <careerStat>" + personInfo.getCareerStat() + "</careerStat>\n");// 职业状况**
			sb_person.append("    <major></major>\n");// 所学专业--
			sb_person.append("    <graduateSchool></graduateSchool>\n");// 毕业学校--
			sb_person.append("    <highestSchooling></highestSchooling>\n");// 最高学历--
			sb_person.append("    <homeTel>" + personInfo.getHomeTel() + "</homeTel>\n");// 住宅电话**
			sb_person.append("    <homeZipcode>" + personInfo.getHomeZipcode() + "</homeZipcode>\n");// 住宅邮编**
			sb_person.append("    <homeAddr>" + personInfo.getHomeAddr() + "</homeAddr>\n");// 住宅地址**
			sb_person.append("    <starSign></starSign>\n");// 星座--
			sb_person.append("    <qq></qq>\n");// QQ---
			sb_person.append("    <weixin></weixin>\n");// 微信--
			sb_person.append("    <weibo></weibo>\n");// 微博--
			sb_person.append("    <homepage></homepage>\n");// 主页--
			sb_person.append("    <email></email>\n");// 邮件地址**
			sb_person.append("    <mobilePhone>" + personInfo.getMobilePhone() + "</mobilePhone>\n");// 手机号码**
			sb_person.append("    <profession></profession>\n");// 从事行业--
			sb_person.append("    <graduationDate></graduationDate>\n");// 毕业时间--
			sb_person.append("    <loanCardNo></loanCardNo>\n");// 贷款卡号码--
			sb_person.append("    <highestDegree></highestDegree>\n");// 最高学位--
			sb_person.append("    <remark></remark>\n");// 备注--
			sb_person.append("    <lastDealingsDesc></lastDealingsDesc>\n");// 前次来行状况--
			sb_person.append("    <usaTaxIdenNo>" + personInfo.getUsaTaxIdenNo() + "</usaTaxIdenNo>\n");// 美国纳税人识别号**
			sb_person.append("    <resume></resume>\n");// 个人简历--
			sb_person.append("    <holdCard></holdCard>\n");// 持卡情况--
			sb_person.append("    <holdAcct></holdAcct>\n");// 在我行开立账户情况--
			sb_person.append("    <politicalFace></politicalFace>\n");// 政治面貌--
			sb_person.append("    <religiousBelief></religiousBelief>\n");// 宗教信仰--
			sb_person.append("    <health></health>\n");// 健康状况--
			sb_person.append("    <residence></residence>\n");// 居住状况--
			sb_person.append("    <marriage></marriage>\n");// 婚姻状况--
			sb_person.append("    <hukouPlace></hukouPlace>\n");// 户口所在地--
			sb_person.append("    <household></household>\n");// 户籍性质--
			sb_person.append("    <nativeplace></nativeplace>\n");// 籍贯--
			sb_person.append("    <nationality>" + personInfo.getNationality() + "</nationality>\n");// 民族--
			sb_person.append("    <areaCode></areaCode>\n");// 行政区划代码--
			sb_person.append("    <citizenship>" + personInfo.getCitizenship() + "</citizenship>\n");// 国籍##
			sb_person.append("    <birthlocale>" + personInfo.getBirthlocale() + "</birthlocale>\n");// 出生地点**
			sb_person.append("    <birthday>" + sdf.format(personInfo.getBirthday()) + "</birthday>\n");// 出生日期**
			sb_person.append("    <gender>" + personInfo.getGender() + "</gender>\n");// 性别**
			sb_person.append("    <usedName></usedName>\n");// 曾用名--
			sb_person.append("    <nickName></nickName>\n");// 客户昵称--
			sb_person.append("    <personTitle></personTitle>\n");// 客户称谓--
			sb_person.append("    <pinyinAbbr></pinyinAbbr>\n");// 拼音缩写--
			sb_person.append("    <pinyinName>" + personInfo.getPinyinName() + "</pinyinName>\n");// 拼音名称**
			sb_person.append("    <personalName>" + personInfo.getPersonalName() + "</personalName>\n");// 客户名字**
			sb_person.append("    <surName></surName>\n");// 客户姓氏--
			sb_person.append("    <orgSubType></orgSubType>\n");// 自贸区类型--
			sb_person.append("    <jointCustType>" + personInfo.getJointCustType() + "</jointCustType>\n");// 联名户类型**
			sb_person.append("    <perCustType></perCustType>\n");// 个人客户类型--
			sb_person.append("    <ifOrgSubType></ifOrgSubType>\n");// 是否自贸区--
			sb_person.append("</person>\n");// person:个人客户信息--end
		}
		return sb_person;
	}
	

	
	/**
	 * 拼接客户归属机构信息
	 * @param belongOrgInfo	客户归属机构信息数据
	 * @param auth			登录用户信息
	 * @return
	 */
	private StringBuffer getBelongBranchInfo(OcrmFCiBelongOrg belongOrgInfo,
			AuthUser auth) {
		StringBuffer sb_belongBranch = new StringBuffer();
		 sb_belongBranch.append("<belongBranch>\n");//belongBranch：归属机构--start
		 sb_belongBranch.append("    <lastUpdateTm>"+ new Timestamp(System.currentTimeMillis()) +"</lastUpdateTm>\n");//最后更新时间
		 sb_belongBranch.append("    <lastUpdateUser>" + auth.getUserId() + "</lastUpdateUser>\n");//最后更新人
		 sb_belongBranch.append("    <lastUpdateSys>CRM</lastUpdateSys>\n");//最后更新系统
		 sb_belongBranch.append("    <custId></custId>\n");//客户编号
		 sb_belongBranch.append("    <belongBranchId></belongBranchId>\n");//归属机构ID
		 sb_belongBranch.append("    <belongBranchNo>" +belongOrgInfo.getInstitutionCode() + "</belongBranchNo>\n");//归属机构编号
		 sb_belongBranch.append("    <mainType>1</mainType>\n");//主协办类型
		 sb_belongBranch.append("    <validFlag>1</validFlag>\n");//有效标志
		 //sb_belongBranch.append("    <belongBranchType></belongBranchType>\n");//归属机构类型
		 //sb_belongBranch.append("    <startDate></startDate>\n");//开始日期
		 //sb_belongBranch.append("    <endDate></endDate>\n");//结束日期
		 sb_belongBranch.append("</belongBranch>\n");//belongBranch：归属机构--end
		return sb_belongBranch;
	}

	/**
	 * 拼接客户归属经理信息
	 * 
	 * @param belongManagerInfo
	 * @return
	 */
	private StringBuffer getCustManagerInfo(OcrmFCiBelongCustmgr belongManagerInfo) {
		StringBuffer sb_belongManager = new StringBuffer();
		if (belongManagerInfo != null && belongManagerInfo.getMgrId() != null && !belongManagerInfo.getMgrId().equals("")) {
			sb_belongManager.append("<belongManager>\n");// belongManager:归属客户经理--start
			sb_belongManager.append("    <custManagerType></custManagerType>\n");// 客户经理类型--
			sb_belongManager.append("    <custManagerNo>" + belongManagerInfo.getMgrId() + "</custManagerNo>\n");// 客户经理编号**
			sb_belongManager.append("    <mainType>1</mainType>\n");// 主协办类型---
			sb_belongManager.append("    <validFlag>1</validFlag>\n");// 有效标志--
			sb_belongManager.append("    <startDate></startDate>\n");// 开始日期--
			sb_belongManager.append("    <endDate></endDate>\n");// 结束日期--
			sb_belongManager.append("</belongManager>\n");// belongManager:归属客户经理--end
		}
		return sb_belongManager;
	}

	
	/**
	 * 保存数据到CRM
	 * @param resInfoMap
	 * @param vo
	 * @return
	 */
	private Map<String, Object> saveCRMData(Map<String, Object> resInfoMap, EcifAccountMsg vo) {
		String ecifId = resInfoMap.get("custId").toString();//
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (vo == null) {
			String logMsg = "ECIF开户数据落地失败：待落地数据为空...";
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
			log.error(logMsg);
			return retMap;
		}
		/* crm数据落地 */
		// 客户信息
		try {
			// 客户信息

			AcrmFCiCustomer customerInfo = vo.getCustomerInfo();//
//			String jqlCustomer = "select t from AcrmFCiCustomer t where t.custId";
			if(customerInfo != null){
				customerInfo.setCustId(ecifId);
				String jqlCustCoreInfo = "select t from AcrmFCiCustomer t where t.custId=:custId";
				Map<String, Object> paramCustCoreNo = new HashMap<String, Object>();
				paramCustCoreNo.put("custId", ecifId);
				List<AcrmFCiCustomer> customerInfoList = this.baseDAO.findWithNameParm(jqlCustCoreInfo, paramCustCoreNo);
				if(customerInfoList != null && customerInfoList.size() >= 1){
					AcrmFCiCustomer customer = customerInfoList.get(0);
					String custCoreNo = customer.getCoreNo();
					customerInfo.setCoreNo(custCoreNo);
					//部分字段不更新
					customerInfo.setCreateBranchNo(customer.getCreateBranchNo());
					customerInfo.setCreateDate(customer.getCreateDate());
					customerInfo.setCreateTellerNo(customer.getCreateTellerNo());
					customerInfo.setCreateTime(customer.getCreateTime());
				}
				this.baseDAO.merge(customerInfo);
			}
			// 证件信息 --custIdentyInfo
			//获取证件信息表的主键--ECIF返回的主键
			List<AcrmFCiCustIdentifier> custIdentyList = vo.getCustIdentyList();
			if (custIdentyList != null && custIdentyList.size() >= 1) {
				for (int i = 0; i < custIdentyList.size(); i++) {
					String identId = null;
					AcrmFCiCustIdentifier acrmFCiCustIdentifier = custIdentyList.get(i);
					acrmFCiCustIdentifier.setCustId(ecifId);
					if(resInfoMap.containsKey("identifyInfo")){//客户已存在时不会返回证件信息表的主键
						Map<String, Object> identInfoMap = (Map<String, Object>) resInfoMap.get("identifyInfo");
						if(identInfoMap.containsKey(acrmFCiCustIdentifier.getIdentType())){
							identId = (String) identInfoMap.get(acrmFCiCustIdentifier.getIdentType());
							acrmFCiCustIdentifier.setIdentId(identId);
						}
					}
					if(StringUtils.isEmpty(identId)){
						String checkJql = "select t from AcrmFCiCustIdentifier t where t.custId=:custId and t.identType=:identType";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("custId", ecifId);
						params.put("identType", acrmFCiCustIdentifier.getIdentType());
						List<AcrmFCiCustIdentifier> resL = this.baseDAO.findWithNameParm(checkJql, params);
						if(resL != null && resL.size() == 1){//存在已有数据时更新数据
							AcrmFCiCustIdentifier resIdent = resL.get(0);
							identId = resIdent.getIdentId();
							acrmFCiCustIdentifier.setIdentId(identId);
						}else{
							String sql_nextIdentifierId = "select ID_SEQUENCE.nextval from dual";//
							List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextIdentifierId);
							String str_nextIdentifierId = "100000";
							if (l_o != null && l_o.size() >= 1) {
								Object o = l_o.get(0);
								if (o != null && !o.toString().equals("")) {
									str_nextIdentifierId = o.toString();
								}
							}
							identId = str_nextIdentifierId;
							acrmFCiCustIdentifier.setIdentId(identId);
						}
					}
					this.baseDAO.merge(acrmFCiCustIdentifier);
				}
			}

			// 个人信息--personInfo
			AcrmFCiPerson personInfo = vo.getPersonInfo();
			if(personInfo != null){
				personInfo.setCustId(ecifId);
				this.baseDAO.merge(personInfo);
			}
			// 个人家庭信息
			AcrmFCiPerFamily personFamilyInfo = vo.getPersonFamilyInfo();
			if(personFamilyInfo != null){
				personFamilyInfo.setCustId(ecifId);
				String checkJql = "select t from AcrmFCiPerFamily t where t.custId=:custId";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("custId", ecifId);
				List<AcrmFCiPerFamily> resL = this.baseDAO.findWithNameParm(checkJql, params);
				if(resL != null && resL.size() == 1){
					AcrmFCiPerFamily resIdent = resL.get(0);
					personFamilyInfo.setId(resIdent.getId());
				}else{
					String sql_personFamilyId = "select ID_SEQUENCE.nextval from dual";//
					List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_personFamilyId);
					String str_personFamilyId = "100000";
					if (l_o != null && l_o.size() >= 1) {
						Object o = l_o.get(0);
						if (o != null && !o.toString().equals("")) {
							str_personFamilyId = o.toString();
						}
					}
					personFamilyInfo.setId(Long.parseLong(str_personFamilyId));
				}
				this.baseDAO.merge(personFamilyInfo);
			}

			// 关联人信息------------------------
			OcrmMCiRelPerson ocrmMCiRelPerson = vo.getCustRelPerson();
			if(ocrmMCiRelPerson != null){
				ocrmMCiRelPerson.setCustId(ecifId);
				String checkJql = "select t from OcrmMCiRelPerson t where t.custId=:custId";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("custId", ecifId);
				List<OcrmMCiRelPerson> resL = this.baseDAO.findWithNameParm(checkJql, params);
				if(resL != null && resL.size() == 1){
					OcrmMCiRelPerson resIdent = resL.get(0);
					ocrmMCiRelPerson.setId(resIdent.getId());
					//部分字段不更新
					ocrmMCiRelPerson.setCreateOrg(resIdent.getCreateOrg());
					ocrmMCiRelPerson.setCreateTm(resIdent.getCreateTm());
					ocrmMCiRelPerson.setCreateUser(resIdent.getCreateUser());
				}else{
					String sql_RelPersonId = "select SEQ_REL_PERSON.nextval from dual";//
					List<Object> l_o2 = this.baseDAO.findByNativeSQLWithIndexParam(sql_RelPersonId);
					String str_RelPersonId = "100000";
					if (l_o2 != null && l_o2.size() >= 1) {
						Object o = l_o2.get(0);
						if (o != null && !o.toString().equals("")) {
							str_RelPersonId = o.toString();
						}
					}
					ocrmMCiRelPerson.setId(Long.parseLong(str_RelPersonId));
				}
				this.baseDAO.merge(ocrmMCiRelPerson);
			}
						
			// 主户本人声明信息--ocrmMCiTaxMain-------------------------------------
			OcrmMCiTaxMain ocrmMCiTaxMain = vo.getOcrmMCiTaxMain();
			if(ocrmMCiTaxMain != null){
				String custId1 = ocrmMCiTaxMain.getCustId();
				if(custId1 == null || StringUtils.isEmpty(custId1)){
					custId1 = ecifId;
				}
				ocrmMCiTaxMain.setCustId(custId1);
				String qJql = "select t from OcrmMCiTaxMain t where t.custId='"+custId1+"'";
				List<OcrmMCiTaxMain> rsList = this.baseDAO.findWithNameParm(qJql, null);
				if(rsList != null && rsList.size() >= 1){
					for (OcrmMCiTaxMain rs : rsList) {
						ocrmMCiTaxMain.setCreateOrg(rs.getCreateOrg());
						ocrmMCiTaxMain.setCreateTm(rs.getCreateTm());
						ocrmMCiTaxMain.setCreateUser(rs.getCreateUser());
					}
				} 
				this.baseDAO.merge(ocrmMCiTaxMain);
				
				// 纳税信息
				List<OcrmMCiTaxSub> ocrmMCiTaxSubList = vo.getOcrmMCiTaxSubList();
				//删除历史数据
				String delJql = "delete from OcrmMCiTaxSub t where t.custId=:custId";
				Map<String, Object> delPars = new HashMap<String, Object>();
				delPars.put("custId", custId1);
				this.baseDAO.batchExecuteWithNameParam(delJql, delPars);
				if(ocrmMCiTaxSubList != null && ocrmMCiTaxSubList.size() >= 1){
					for (int i = 0; i < ocrmMCiTaxSubList.size(); i++) {
						String sql_ocrmMciTaxSubId = "select SEQ_TAX_SUB.nextval from dual";
						List<Object> l_o3 = this.baseDAO.findByNativeSQLWithIndexParam(sql_ocrmMciTaxSubId);
						String str_ocrmMciTaxSubId = "100000";
						if(l_o3 != null && l_o3.size() >= 1){
							Object o = l_o3.get(0);
							if(o != null && !o.toString().equals("")){
								str_ocrmMciTaxSubId = o.toString();
							}
						}
						OcrmMCiTaxSub ocrmMCiTaxSub = ocrmMCiTaxSubList.get(i);
						ocrmMCiTaxSub.setId(Long.parseLong(str_ocrmMciTaxSubId));
						ocrmMCiTaxSub.setCustId(custId1);
						this.baseDAO.merge(ocrmMCiTaxSub);
					}
				}
			}
			
			// 从户本人声明信息--ocrmMCiTaxMain-------------------------------------
			OcrmMCiTaxMain ocrmMCiTaxMain2 = vo.getOcrmMCiTaxMain2();
			if(ocrmMCiTaxMain2 != null){
				String custId2 = ocrmMCiTaxMain2.getCustId();
				String qJql = "select t from OcrmMCiTaxMain t where t.custId='"+custId2+"'";
				List<OcrmMCiTaxMain> rsList = this.baseDAO.findWithNameParm(qJql, null);
				if(rsList != null && rsList.size() >= 1){
					for (OcrmMCiTaxMain rs : rsList) {
						ocrmMCiTaxMain2.setCreateOrg(rs.getCreateOrg());
						ocrmMCiTaxMain2.setCreateTm(rs.getCreateTm());
						ocrmMCiTaxMain2.setCreateUser(rs.getCreateUser());
					}
				} 
				this.baseDAO.merge(ocrmMCiTaxMain2);
				
				// 纳税信息
				List<OcrmMCiTaxSub> ocrmMCiTaxSubList2 = vo.getOcrmMCiTaxSubList2();
				//删除历史数据
				String delJql = "delete from OcrmMCiTaxSub t where t.custId=:custId";
				Map<String, Object> delPars = new HashMap<String, Object>();
				delPars.put("custId", custId2);
				this.baseDAO.batchExecuteWithNameParam(delJql, delPars);
				if(ocrmMCiTaxSubList2 != null && ocrmMCiTaxSubList2.size() >= 1){
					for (int i = 0; i < ocrmMCiTaxSubList2.size(); i++) {
						String sql_ocrmMciTaxSubId = "select SEQ_TAX_SUB.nextval from dual";
						List<Object> l_o3 = this.baseDAO.findByNativeSQLWithIndexParam(sql_ocrmMciTaxSubId);
						String str_ocrmMciTaxSubId2 = "100000";
						if(l_o3 != null && l_o3.size() >= 1){
							Object o = l_o3.get(0);
							if(o != null && !o.toString().equals("")){
								str_ocrmMciTaxSubId2 = o.toString();
							}
						}
						OcrmMCiTaxSub ocrmMCiTaxSub2 = ocrmMCiTaxSubList2.get(i);
						ocrmMCiTaxSub2.setId(Long.parseLong(str_ocrmMciTaxSubId2));
						ocrmMCiTaxSub2.setCustId(custId2);
						this.baseDAO.merge(ocrmMCiTaxSub2);
					}
				}
			}
			
			// 地址信息
			//获取地址信息表的主键--ECIF返回
			List<AcrmFCiAddress> addressInfoList = vo.getAddressInfoList();
			if (addressInfoList != null && addressInfoList.size() >= 1) {
				for (int i = 0; i < addressInfoList.size(); i++) {
					String addrId = null;
					AcrmFCiAddress acrmFCiAddress = addressInfoList.get(i);
					acrmFCiAddress.setCustId(ecifId);
					String addrType = acrmFCiAddress.getAddrType();
					if(resInfoMap.containsKey("addressInfo")){
						Map<String, Object> addrInfoMap = (Map<String, Object>) resInfoMap.get("addressInfo");
						if(addrInfoMap.containsKey(addrType)){
							addrId = (String) addrInfoMap.get(addrType);
							acrmFCiAddress.setAddrId(Long.parseLong(addrId));
						}
					}
					if(StringUtils.isEmpty(addrId)){
						String checkJql = "select t from AcrmFCiAddress t where t.custId=:custId and t.addrType=:addrType";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("custId", ecifId);
						params.put("addrType", addrType);
						List<AcrmFCiAddress> resL = this.baseDAO.findWithNameParm(checkJql, params);
						if(resL != null && resL.size() == 1){
							AcrmFCiAddress resAddr = resL.get(0);
							acrmFCiAddress.setAddrId(resAddr.getAddrId());
							acrmFCiAddress.setCreateTm(resAddr.getCreateTm());
							acrmFCiAddress.setCreateUser(resAddr.getCreateUser());
						}else{
							long addrid = 10000;
							String sql_maxAddrId = "select SEQ_ADDR_ID.nextval from dual";//
							List<Object> addridList = this.baseDAO.findByNativeSQLWithIndexParam(sql_maxAddrId);
							if (addridList != null && addridList.size() >= 0) {
								Object o = addridList.get(0);
								if (o != null && !o.toString().equals("")) {
									addrid = Long.parseLong(o.toString());
								}
							}
							acrmFCiAddress.setAddrId(addrid);
						}
					}
					this.baseDAO.merge(acrmFCiAddress);
				}
			}

			// 联系方式信息
			//获取联系方式信息表的主键--ECIF返回
			List<AcrmFCiContmeth> contmethInfoList = vo.getContmethInfoList();
			if (contmethInfoList != null && contmethInfoList.size() >= 1) {
				for (int i = 0; i < contmethInfoList.size(); i++) {
					String contmethId = null;
					AcrmFCiContmeth acrmFCiContmeth = contmethInfoList.get(i);
					acrmFCiContmeth.setCustId(ecifId);
					String contmethType = acrmFCiContmeth.getContmethType();
					if(resInfoMap.containsKey("contmethInfo")){
						Map<String, Object> contmethInfoMap = (Map<String, Object>) resInfoMap.get("contmethInfo");
						if(contmethInfoMap.containsKey(contmethType)){
							contmethId = (String) contmethInfoMap.get(contmethType);
							acrmFCiContmeth.setContmethId(contmethId);
						}
					}
					if(StringUtils.isEmpty(contmethId)){
						String checkJql = "select t from AcrmFCiContmeth t where t.custId=:custId and t.contmethType=:contmethType";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("custId", ecifId);
						params.put("contmethType", contmethType);
						List<AcrmFCiContmeth> resL = this.baseDAO.findWithNameParm(checkJql, params);
						if(resL != null && resL.size() == 1){
							AcrmFCiContmeth resCont = resL.get(0);
							acrmFCiContmeth.setContmethId(resCont.getContmethId());
							acrmFCiContmeth.setCreateTm(resCont.getCreateTm());
							acrmFCiContmeth.setCreateUser(resCont.getCreateUser());
						}else{
							String sql_contmethId = "select SEQUENCE_AFCI_CONTMETH.nextval from dual";//
							List<Object> l_o4 = this.baseDAO.findByNativeSQLWithIndexParam(sql_contmethId);
							String str_contmethId = "100000";
							if (l_o4 != null && l_o4.size() >= 1) {
								Object o = l_o4.get(0);
								if (o != null && !o.toString().equals("")) {
									str_contmethId = o.toString();
								}
							}
							acrmFCiContmeth.setContmethId(str_contmethId);
						}
					}
					this.baseDAO.merge(acrmFCiContmeth);
				}
			}

			// 联名户信息--custJoinInfo
			OcrmFCiCustjoinInfo custJoinInfo = vo.getCustJoinInfo();
			if(custJoinInfo != null){
				custJoinInfo.setCustId(ecifId);
				String checkJql = "select t from OcrmFCiCustjoinInfo t where t.custId=:custId";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("custId", ecifId);
				List<OcrmFCiCustjoinInfo> resL = this.baseDAO.findWithNameParm(checkJql, params);
				if(resL != null && resL.size() == 1){
					OcrmFCiCustjoinInfo resCont = resL.get(0);
					custJoinInfo.setId(resCont.getId());
					//部分字段不更新
					custJoinInfo.setCreateOrg(resCont.getCreateOrg());
					custJoinInfo.setCreateTm(resCont.getCreateTm());
					custJoinInfo.setCreateUser(resCont.getCreateUser());
				}else{
					String sql_custJoinId = "select SEQ_CUSTJOIN_INFO.nextval from dual";//
					List<Object> l_o5 = this.baseDAO.findByNativeSQLWithIndexParam(sql_custJoinId);
					String str_custJoinId = "100000";
					if (l_o5 != null && l_o5.size() >= 1) {
						Object o = l_o5.get(0);
						if (o != null && !o.toString().equals("")) {
							str_custJoinId = o.toString();
						}
					}
					custJoinInfo.setId(Long.parseLong(str_custJoinId));
				}
				this.baseDAO.merge(custJoinInfo);
			}
			
			//在我行有无关联人
			AcrmFCiPerKeyflag acrmFCiPerKeyflag = vo.getAcrmFCiPerKeyflag();
			if(acrmFCiPerKeyflag != null){
				acrmFCiPerKeyflag.setCustId(ecifId);
				/*String jqlPerKeyFlag = "select t from AcrmFCiPerKeyflag t where t.custId=:custId";
				Map<String,Object> paramPerKeyFlag = new HashMap<String,Object>();
				paramPerKeyFlag.put("custId",ecifId);
				List<AcrmFCiPerKeyflag> perKeyFlagList = this.baseDAO.findWithNameParm(jqlPerKeyFlag, paramPerKeyFlag);
				if(perKeyFlagList != null && perKeyFlagList.size() >=1){
					
				}*/
				this.baseDAO.merge(acrmFCiPerKeyflag);
			}

			// 账户信息--custBankInfo------------------------
			OcrmFCiOpenInfo custOpenInfo = vo.getCustBankInfo();
			if(custOpenInfo != null){
				custOpenInfo.setCustId(ecifId);
				String jqlAccNo = "select t from OcrmFCiOpenInfo t where t.custId=:custId";
				Map<String, Object> paramAccNo = new HashMap<String, Object>();
				paramAccNo.put("custId", ecifId);
				List<OcrmFCiOpenInfo> custOpenInfoList = this.baseDAO.findWithNameParm(jqlAccNo, paramAccNo);
				if(custOpenInfoList != null && custOpenInfoList.size() >= 1){
					OcrmFCiOpenInfo rs = custOpenInfoList.get(0);
					custOpenInfo.setAccNo(rs.getAccNo());
					//部分字段不更新
					custOpenInfo.setCreateOrg(rs.getCreateOrg());
					custOpenInfo.setCreateTm(rs.getCreateTm());
					custOpenInfo.setCreateUser(rs.getCreateUser());
				}
				this.baseDAO.merge(custOpenInfo);
			}

			// 开卡信息表
			List<OcrmFCiAccountInfo> custAccountInfoList = vo.getCustAccountInfoList();
			String delJql2 = "delete from OcrmFCiAccountInfo t where t.cust_id=:custId";
			Map<String, Object> delPars2 = new HashMap<String, Object>();
			delPars2.put("custId", ecifId);
			this.baseDAO.batchExecuteWithNameParam(delJql2, delPars2);
			if (custAccountInfoList != null && custAccountInfoList.size() >= 1) {
				for (int i = 0; i < custAccountInfoList.size(); i++) {
					String sql_custAccountId = "select SEQ_ACCOUNT_INFO.nextval from dual";//
					List<Object> l_o6 = this.baseDAO.findByNativeSQLWithIndexParam(sql_custAccountId);
					String str_custAccountId = "100000";
					if (l_o6 != null && l_o6.size() >= 1) {
						Object o = l_o6.get(0);
						if (o != null && !o.toString().equals("")) {
							str_custAccountId = o.toString();
						}
					}
					OcrmFCiAccountInfo ocrmFCiAccountInfo = custAccountInfoList.get(i);
					ocrmFCiAccountInfo.setId(str_custAccountId);
					ocrmFCiAccountInfo.setCust_id(ecifId);
					this.baseDAO.merge(ocrmFCiAccountInfo);
				}
			}

			// 保存归属客户经理信息
			//AcrmOBelongManager belongManager = vo.getBelongManager();
			String delBelongMgr = "delete from OcrmFCiBelongCustmgr t where t.custId=:custId";
			Map<String, Object> paramBelongMgr = new HashMap<String, Object>();
			paramBelongMgr.put("custId", ecifId);
			this.baseDAO.batchExecuteWithNameParam(delBelongMgr, paramBelongMgr);
			OcrmFCiBelongCustmgr belongManager = vo.getBelongManager();
			String sql_belongManagerId = "select CommonSequnce.nextval from dual";//
			List<Object> l_belongM = this.baseDAO.findByNativeSQLWithIndexParam(sql_belongManagerId);
			long str_belongManagerId = 100000;
			if (l_belongM != null && l_belongM.size() >= 1) {
				Object o = l_belongM.get(0);
				if (o != null && !o.toString().equals("")) {
					str_belongManagerId = Long.parseLong(o.toString());
				}
			}
			belongManager.setId(str_belongManagerId);
			belongManager.setCustId(ecifId);
			belongManager.setMainType("1");
			this.baseDAO.save(belongManager);
			
			
			//保存客户归属机构信息
			String delBelongBranch = "delete from OcrmFCiBelongOrg t where t.custId=:custId";
			Map<String, Object> pBranch = new HashMap<String, Object>();
			pBranch.put("custId", ecifId);
			this.baseDAO.batchExecuteWithNameParam(delBelongBranch, pBranch);
			OcrmFCiBelongOrg belongOrg = vo.getBelongOrg();
			String sql_belongOrgId = "select CommonSequnce.nextval from dual";
			List<Object> l_belongOrg = this.baseDAO.findByNativeSQLWithIndexParam(sql_belongOrgId);
			long str_belongOrgId = 100000;
			if (l_belongOrg != null && l_belongOrg.size() >= 1) {
				Object o = l_belongOrg.get(0);
				if (o != null && !o.toString().equals("")) {
					str_belongOrgId = Long.parseLong(o.toString());
				}
			}
			belongOrg.setId(str_belongOrgId);
			belongOrg.setCustId(ecifId);
			belongOrg.setMainType("1");
			this.baseDAO.merge(belongOrg);
			//保存交叉索引表
			if(!vo.isUpdate()){
				
			}
			
			String logMsg = "ECIF开户成功，数据落地成功";
			log.info(logMsg);
			retMap.put("status", "success");
			retMap.put("msg", logMsg);
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误，请联系管理员";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
			return retMap;
		}
		return retMap;
	}
	
	@Deprecated
	private String getSeqNextVal(String seqNm){
		if(seqNm == null || seqNm.equals("")){
			seqNm = "ID_SEQUENCE";
		}
		String str_nextLogId = "100000";
		try {
			String str_seq = "select " + seqNm + ".nextval from dual";
			List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(str_seq);
			
			if (l_o != null && l_o.size() >= 1) {
				Object o = l_o.get(0);
				if (o != null && !o.toString().equals("")) {
					str_nextLogId = o.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str_nextLogId;
	}
}

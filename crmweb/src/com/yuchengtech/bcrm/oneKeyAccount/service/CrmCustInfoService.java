package com.yuchengtech.bcrm.oneKeyAccount.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerKeyflag;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.customer.model.AcrmFCiAddress;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamily;
import com.yuchengtech.bcrm.oneKeyAccount.entity.AcrmOBelongManager;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiAccountInfo;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiCustjoinInfo;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiOpenInfo;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmMCiRelPerson;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmMCiTaxMain;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmMCiTaxSub;
import com.yuchengtech.bcrm.oneKeyAccount.utils.ConvertBeanToMap;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * CRM客户信息相关Service
 * @author wx
 *
 */
@Service
public class CrmCustInfoService extends CommonService{

	private static Logger log = LoggerFactory
			.getLogger(CrmCustInfoService.class);

	private ConvertBeanToMap beanToMap = new ConvertBeanToMap();
	public CrmCustInfoService() {
		JPABaseDAO<AcrmFCiCustomer, String> baseDAO = new JPABaseDAO<AcrmFCiCustomer, String>(
				AcrmFCiCustomer.class);
		super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 查询客户信息
	 * @param custNm 客户名称
	 * @param identType 证件类型1
	 * @param identNum 证件号码1
	 * @param identType2 证件类型1
	 * @param identNum2 证件号码2
	 * @param jointaccount 联名户标志
	 * @param ecifIsOpen 是否ECIF开户
	 * @return
	 */
	public Map<String, Object> validateCustomerInfo(String custNm, String identType, String identNum,
			String identType2, String identNum2,String jointaccount,String ecifIsOpen){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(custNm) || StringUtils.isEmpty(identType) || StringUtils.isEmpty(identNum)){
				String msg = "信息不全{客户姓名、证件类型、证件号码不全}，无法校验客户信息";
				log.error(msg);
				retMap.put("status", "error");
				retMap.put("msg", msg);
				return retMap;
			}

			//查询IC控件端口,读卡器端口,如果没有查询到，默认为端口2
			String IcPortSql = "select * from Ocrm_sys_lookup_item t where t.f_lookup_id='XD000370'";
			List<Object[]> portList = this.baseDAO.findByNativeSQLWithIndexParam(IcPortSql, null);
			if(portList != null && portList.size() >= 1){
				Object[] os = portList.get(0);
				if(os != null && os.length >= 3){
					String IcPort = os[2] == null ? "2" : os[2].toString();
					retMap.put("IcPort", IcPort);
				}
			}
			//根据证件 类型和证件号去客户信息表查询
			String jql = "select t from AcrmFCiCustomer t where t.identType=:identType and t.identNo=:identNum";//
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("identType", identType);
			params.put("identNum", identNum);//带*号的主户证件号
			List<AcrmFCiCustomer> l_customerInfo = this.baseDAO.findWithNameParm(jql, params);
			if(l_customerInfo == null || l_customerInfo.size() < 1){
				String msg = "CRM系统中没有对应的客户信息，该客户为新户";
				log.info(msg);
				retMap.put("status", "success");
				retMap.put("msg", msg);
				return retMap;
			}else if(l_customerInfo.size() > 1){
				String msg = "CRM系统中对应的客户信息有多条，疑为脏数据存在，请联系管理员";
				log.error(msg);
				retMap.put("status", "success");
				retMap.put("msg", msg);
				return retMap;
			}else if(l_customerInfo.size() == 1){
				if(!StringUtils.isEmpty(jointaccount)){
					Map<String, Object> infoMap = new HashMap<String, Object>();
					if(jointaccount.equals("1")){//是联名户
						//主户的信息
						String jql3 = "select t from AcrmFCiCustomer t where t.identType=:identType and t.identNo=:identNo";
						Map<String,Object> params3 = new HashMap<String, Object>();
						params3.put("identType", identType);
						params3.put("identNo", identNum.replaceAll("[*]", ""));
						List<AcrmFCiCustomer>  l_customerInfo3 = this.baseDAO.findWithNameParm(jql3, params3);
						if(l_customerInfo3 == null || l_customerInfo3.size() < 1){
							String msg = "CRM系统中没有对应的客户信息，该主户为新户";
							log.info(msg);
							retMap.put("status", "success");
							retMap.put("msg", msg);
							return retMap;
						}else if(l_customerInfo3.size() > 1){
							String msg = "该主户在CRM系统中对应的客户信息有多条，疑为脏数据存在，请联系管理员";
							log.error(msg);
							retMap.put("status", "success");
							retMap.put("msg", msg);
							return retMap;
						}else if(l_customerInfo3.size() == 1){
							AcrmFCiCustomer customer3 = l_customerInfo3.get(0);
							String custId1 = customer3.getCustId();
							infoMap.put("custId1", custId1);
							if(!StringUtils.isEmpty(custId1)){
								Map<String,Object> paramsMap3 = new HashMap<String, Object>();
								paramsMap3.put("custId", custId1);
								//08--查询个人声明信息
								String jql_taxMainInfo = "select t from OcrmMCiTaxMain t where t.custId=:custId";//
								List<OcrmMCiTaxMain> l_taxMainInfo = this.baseDAO.findWithNameParm(jql_taxMainInfo, paramsMap3);
								if(l_taxMainInfo != null && l_taxMainInfo.size() == 1){
									OcrmMCiTaxMain taxMainInfo = l_taxMainInfo.get(0);
									infoMap.put("taxMainInfo", beanToMap.convert(taxMainInfo, taxMainInfo.getClass()));
								}
								//09--查询纳税信息
								String jql_taxSubInfo = "select t from OcrmMCiTaxSub t where t.custId=:custId";//
								List<OcrmMCiTaxSub> l_taxSubInfo = this.baseDAO.findWithNameParm(jql_taxSubInfo, paramsMap3);
								if(l_taxSubInfo != null && l_taxSubInfo.size() >= 1){
									List<Map<String, Object>> l_jso_taxSubInfo = new ArrayList<Map<String, Object>>();
									for (int i = 0; i < l_taxSubInfo.size(); i++) {
										OcrmMCiTaxSub taxSubInfo = l_taxSubInfo.get(i);
										l_jso_taxSubInfo.add(beanToMap.convert(taxSubInfo, taxSubInfo.getClass()));
									}
									infoMap.put("taxSubInfoList", l_jso_taxSubInfo);
								}
							}
						}
						
						//从户的信息
						String jql2 = "select t from AcrmFCiCustomer t where t.identType=:identType and t.identNo=:identNo";
						Map<String,Object> params2 = new HashMap<String, Object>();
						params2.put("identType", identType2);
						params2.put("identNo", identNum2);
						List<AcrmFCiCustomer>  l_customerInfo2 = this.baseDAO.findWithNameParm(jql2, params2);
						if(l_customerInfo2 == null || l_customerInfo2.size() < 1){
							String msg = "CRM系统中没有对应的客户信息，该从户为新户";
							log.info(msg);
							retMap.put("status", "success");
							retMap.put("msg", msg);
							return retMap;
						}else if(l_customerInfo2.size() > 1){
							String msg = "该从户在CRM系统中对应的客户信息有多条，疑为脏数据存在，请联系管理员";
							log.error(msg);
							retMap.put("status", "success");
							retMap.put("msg", msg);
							return retMap;
						}else if(l_customerInfo2.size() == 1){
							AcrmFCiCustomer customer2 = l_customerInfo2.get(0);
							String custId2 = customer2.getCustId();//ECIF客户号
							if(!StringUtils.isEmpty(custId2)){
								infoMap.put("customer2", beanToMap.convert(customer2, customer2.getClass()));
								Map<String,Object> paramsMap2 = new HashMap<String, Object>();
								paramsMap2.put("custId", custId2);
								//根据custId2查询所有客户详细信息
								//01--查询个人信息
								String jql_personInfo2 = "select t from AcrmFCiPerson t where t.custId=:custId";
								List<AcrmFCiPerson> l_personInfo2 = this.baseDAO.findWithNameParm(jql_personInfo2, paramsMap2);
								if(l_personInfo2 != null && l_personInfo2.size() == 1){
									AcrmFCiPerson personInfo2 = l_personInfo2.get(0);
									infoMap.put("personInfo2", beanToMap.convert(personInfo2, personInfo2.getClass()));
								}
								//02--查询证件信息
								String jql_identInfo2 = "select t from AcrmFCiCustIdentifier t where t.custId=:custId";
								List<AcrmFCiCustIdentifier> l_identInfo2 = this.baseDAO.findWithNameParm(jql_identInfo2, paramsMap2);
								if(l_identInfo2 != null && l_identInfo2.size()>= 1){
									List<Map<String,Object>> l_jso_identInfo2 = new ArrayList<Map<String,Object>>();
									for (int i = 0; i < l_identInfo2.size(); i++) {
										AcrmFCiCustIdentifier identInfo2 = l_identInfo2.get(i);
										l_jso_identInfo2.add(beanToMap.convert(identInfo2, identInfo2.getClass()));
									}
									infoMap.put("identInfoList2", l_jso_identInfo2);
								}
								
								//03--查询个人声明信息
								String jql_taxMainInfo2 = "select t from OcrmMCiTaxMain t where t.custId=:custId";//
								List<OcrmMCiTaxMain> l_taxMainInfo2 = this.baseDAO.findWithNameParm(jql_taxMainInfo2, paramsMap2);
								if(l_taxMainInfo2 != null && l_taxMainInfo2.size() == 1){
									OcrmMCiTaxMain taxMainInfo2 = l_taxMainInfo2.get(0);
									infoMap.put("taxMainInfo2", beanToMap.convert(taxMainInfo2, taxMainInfo2.getClass()));
								}
								//04--查询纳税信息
								String jql_taxSubInfo2 = "select t from OcrmMCiTaxSub t where t.custId=:custId";//
								List<OcrmMCiTaxSub> l_taxSubInfo2 = this.baseDAO.findWithNameParm(jql_taxSubInfo2, paramsMap2);
								if(l_taxSubInfo2 != null && l_taxSubInfo2.size() >= 1){
									List<Map<String, Object>> l_jso_taxSubInfo2 = new ArrayList<Map<String, Object>>();
									for (int i = 0; i < l_taxSubInfo2.size(); i++) {
										OcrmMCiTaxSub taxSubInfo2 = l_taxSubInfo2.get(i);
										l_jso_taxSubInfo2.add(beanToMap.convert(taxSubInfo2, taxSubInfo2.getClass()));
									}
									infoMap.put("taxSubInfoList2", l_jso_taxSubInfo2);
								}
							}
						}
					}
					AcrmFCiCustomer customer = l_customerInfo.get(0);
					String custId = customer.getCustId();//ECIF客户号
					String coreNo = customer.getCoreNo();//核心客户号
					if(!StringUtils.isEmpty(custId)){
						infoMap.put("customer", beanToMap.convert(customer, customer.getClass()));
						Map<String, Object> paramsMap = new HashMap<String, Object>();
						paramsMap.put("custId", custId);
						//根据custId查询所有客户详细信息
						//01--查询个人信息
						String jql_personInfo = "select t from AcrmFCiPerson t where t.custId=:custId";//
						List<AcrmFCiPerson> l_personInfo = this.baseDAO.findWithNameParm(jql_personInfo, paramsMap);
						if(l_personInfo != null && l_personInfo.size() == 1){
							AcrmFCiPerson personInfo = l_personInfo.get(0);
							infoMap.put("personInfo", beanToMap.convert(personInfo, personInfo.getClass()));
						}
						//02--查询证件信息
						String jql_identInfo = "select t from AcrmFCiCustIdentifier t where t.custId=:custId";
						List<AcrmFCiCustIdentifier> l_identInfo = this.baseDAO.findWithNameParm(jql_identInfo, paramsMap);
						if(l_identInfo != null && l_identInfo.size() >= 1){
							List<Map<String, Object>> l_jso_identInfo = new ArrayList<Map<String, Object>>();
							for (int i = 0; i < l_identInfo.size(); i++) {
								AcrmFCiCustIdentifier identInfo = l_identInfo.get(i);
								l_jso_identInfo.add(beanToMap.convert(identInfo, identInfo.getClass()));
							}
							infoMap.put("identInfoList", l_jso_identInfo);
						}
						//03--查询地址信息
						String jql_addrInfo = "select t from AcrmFCiAddress t where t.custId=:custId";
						List<AcrmFCiAddress> l_addrInfo = this.baseDAO.findWithNameParm(jql_addrInfo, paramsMap);
						if(l_addrInfo != null && l_addrInfo.size() >= 1){
							List<Map<String, Object>> l_jso_addrInfo = new ArrayList<Map<String, Object>>();
							for (int i = 0; i < l_addrInfo.size(); i++) {
								AcrmFCiAddress addrInfo = l_addrInfo.get(i);
								l_jso_addrInfo.add(beanToMap.convert(addrInfo, addrInfo.getClass()));
							}
							infoMap.put("addrInfoList", l_jso_addrInfo);
						}
						//04--查询联系方式信息
						String jql_contMethInfo = "select t from AcrmFCiContmeth t where t.custId=:custId";
						List<AcrmFCiContmeth> l_contMethInfo = this.baseDAO.findWithNameParm(jql_contMethInfo, paramsMap);
						if(l_contMethInfo != null && l_contMethInfo.size() >= 1){
							List<Map<String, Object>> l_jso_contMethInfo = new ArrayList<Map<String, Object>>();
							for (int i = 0; i < l_contMethInfo.size(); i++) {
								AcrmFCiContmeth contMethInfo = l_contMethInfo.get(i);
								l_jso_contMethInfo.add(beanToMap.convert(contMethInfo, contMethInfo.getClass()));
							}
							infoMap.put("contMethInfoList", l_jso_contMethInfo);
						}
						//05--查询个人家庭信息
						String jql_familyInfo = "select t from AcrmFCiPerFamily t where t.custId=:custId";
						List<AcrmFCiPerFamily> l_familyInfo = this.baseDAO.findWithNameParm(jql_familyInfo, paramsMap);
						if(l_familyInfo != null && l_familyInfo.size() == 1){
							AcrmFCiPerFamily familyInfo = l_familyInfo.get(0);
							infoMap.put("familyInfo", beanToMap.convert(familyInfo, familyInfo.getClass()));
						}
						//06--查询联名户信息
						String jql_lianmingInfo = "select t from OcrmFCiCustjoinInfo t where t.custId=:custId";
						List<OcrmFCiCustjoinInfo> l_lianmingInfo = this.baseDAO.findWithNameParm(jql_lianmingInfo, paramsMap);
						if(l_lianmingInfo != null && l_lianmingInfo.size() == 1){
							OcrmFCiCustjoinInfo lianmingInfo = l_lianmingInfo.get(0);
							infoMap.put("lianmingInfo", beanToMap.convert(lianmingInfo, lianmingInfo.getClass()));
						}
						
						//13--查询在我行有无关联人信息
						String jql_perkeyflagInfo = "select t from AcrmFCiPerKeyflag t where t.custId=:custId";//
						List<AcrmFCiPerKeyflag> l_perkeyflagInfo = this.baseDAO.findWithNameParm(jql_perkeyflagInfo, paramsMap);
						if(l_perkeyflagInfo != null && l_perkeyflagInfo.size() == 1){
							AcrmFCiPerKeyflag perkeyflagInfo = l_perkeyflagInfo.get(0);
							infoMap.put("perkeyflagInfo", beanToMap.convert(perkeyflagInfo, perkeyflagInfo.getClass()));
						}
						
						//07--查询关联人信息
						String jql_contactorInfo = "select t from OcrmMCiRelPerson t where t.custId=:custId";//
						List<OcrmMCiRelPerson> l_contactorInfo = this.baseDAO.findWithNameParm(jql_contactorInfo, paramsMap);
						if(l_contactorInfo != null && l_contactorInfo.size() == 1){
							OcrmMCiRelPerson contactorInfo = l_contactorInfo.get(0);
							infoMap.put("contactorInfo", beanToMap.convert(contactorInfo, contactorInfo.getClass()));
						}
						if(!jointaccount.equals("1")){//不是联名户
							//08--查询个人声明信息
							String jql_taxMainInfo = "select t from OcrmMCiTaxMain t where t.custId=:custId";//
							List<OcrmMCiTaxMain> l_taxMainInfo = this.baseDAO.findWithNameParm(jql_taxMainInfo, paramsMap);
							if(l_taxMainInfo != null && l_taxMainInfo.size() == 1){
								OcrmMCiTaxMain taxMainInfo = l_taxMainInfo.get(0);
								infoMap.put("taxMainInfo", beanToMap.convert(taxMainInfo, taxMainInfo.getClass()));
							}
							//09--查询纳税信息
							String jql_taxSubInfo = "select t from OcrmMCiTaxSub t where t.custId=:custId";//
							List<OcrmMCiTaxSub> l_taxSubInfo = this.baseDAO.findWithNameParm(jql_taxSubInfo, paramsMap);
							if(l_taxSubInfo != null && l_taxSubInfo.size() >= 1){
								List<Map<String, Object>> l_jso_taxSubInfo = new ArrayList<Map<String, Object>>();
								for (int i = 0; i < l_taxSubInfo.size(); i++) {
									OcrmMCiTaxSub taxSubInfo = l_taxSubInfo.get(i);
									l_jso_taxSubInfo.add(beanToMap.convert(taxSubInfo, taxSubInfo.getClass()));
								}
								infoMap.put("taxSubInfoList", l_jso_taxSubInfo);
							}
						}
						
						//10--查询卡账信息--OcrmFCiOpenInfo
						String jql_openInfo = "select t from OcrmFCiOpenInfo t where t.custId=:custId";//
						List<OcrmFCiOpenInfo> l_openInfo = this.baseDAO.findWithNameParm(jql_openInfo, paramsMap);
						if(l_openInfo != null && l_openInfo.size() == 1){
							OcrmFCiOpenInfo openInfo = l_openInfo.get(0);
							infoMap.put("openInfo", beanToMap.convert(openInfo, openInfo.getClass()));
						}
						//11--查询银行服务信息--OcrmFCiAccountInfo
						String jql_accountInfo = "select t from OcrmFCiAccountInfo t where t.cust_id=:custId";//
						List<OcrmFCiAccountInfo> l_accountInfo = this.baseDAO.findWithNameParm(jql_accountInfo, paramsMap);
						if(l_accountInfo != null && l_accountInfo.size() >= 1){
							List<Map<String, Object>> l_jso_accountInfo = new ArrayList<Map<String, Object>>();
							for (int i = 0; i < l_accountInfo.size(); i++) {
								OcrmFCiAccountInfo accountInfo = l_accountInfo.get(i);
								l_jso_accountInfo.add(beanToMap.convert(accountInfo, accountInfo.getClass()));
							}
							infoMap.put("accountInfoList", l_jso_accountInfo);
						}
						//12--查询归属客户经理信息--AcrmOBelongManager
						
						String jql_belongManagerInfo = "select t2.id,t2.account_name,t2.user_name from OCRM_F_CI_BELONG_CUSTMGR t1 left join admin_auth_account t2 on t1.mgr_id=t2.account_name where t1.cust_id='"+custId+"'";//
						List<Object[]> l_belongManagerInfo = this.baseDAO.findByNativeSQLWithNameParam(jql_belongManagerInfo, null);
						if(l_belongManagerInfo != null && l_belongManagerInfo.size() == 1){
							Object[] info = l_belongManagerInfo.get(0);
							Map<String, Object> map_belongManagerInfo = new HashMap<String, Object>();
							if(info.length >= 3){
								map_belongManagerInfo.put("userId", info[0]);
								map_belongManagerInfo.put("custManagerId", info[1]);
								map_belongManagerInfo.put("custManagerName", info[2]);
							}
							infoMap.put("belongManagerInfo", map_belongManagerInfo);
						}
						
						retMap.put("ECIFData", infoMap);
						retMap.put("status", "success");
						return retMap;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		return retMap;
	}
	
	
	
	/**
	 * 根据证件类型，证件号码查询客户信息
	 * @param identType
	 * @param identNo
	 * @return
	 */
	public Map<String, Object> getCustInfoByIdent(String identType, String identNo){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(identType) || StringUtils.isEmpty(identNo)){
				String logMsg  = "缺少证件类型或者证件号码，无法查询客户信息";
				log.error(logMsg);
				return null;
			}
			StringBuffer sb_sql = new StringBuffer();
			sb_sql.append("select");
			sb_sql.append(" a.CUST_ID, a.CUST_NAME, a.IDENT_TYPE, b.F_VALUE, a.IDENT_NO");
			sb_sql.append(" from");
			sb_sql.append(" ACRM_F_CI_CUSTOMER a, OCRM_SYS_LOOKUP_ITEM b");
			sb_sql.append(" where 1=1");
			sb_sql.append(" and a.IDENT_TYPE ='"+identType+"'");
			sb_sql.append(" and a.ident_no ='"+identNo+"'");
			sb_sql.append(" and a.IDENT_TYPE = b.F_CODE");
			sb_sql.append(" and b.f_lookup_id = 'XD000040'");
			sb_sql.append(" and b.F_CODE in ('1', '2', '3', '7', '8', 'X3', '0', '5', '6', 'X5', 'X14', 'X24')");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("identType", identType);
			params.put("identNo", identNo);
			List<Object[]> custInfoList = this.baseDAO.findByNativeSQLWithNameParam(sb_sql.toString(), null);
			List<Map<String, Object>> retCustList = new ArrayList<Map<String,Object>>();
			if(custInfoList != null && custInfoList.size() >= 1){
				for (Object[] objs : custInfoList) {
					if(objs != null){
						String o_custId = objs[0] == null ? "" : objs[0].toString();
						String o_custName = objs[1] == null ? "" : objs[1].toString();
						String o_identType = objs[2] == null ? "" : objs[2].toString();
						String o_identTypeNm = objs[3] == null ? "" : objs[3].toString();
						String o_identNo = objs[4] == null ? "" : objs[4].toString();
						Map<String, Object> sglCustMap = new HashMap<String, Object>();
						sglCustMap.put("custId", o_custId);
						sglCustMap.put("custName", o_custName);
						sglCustMap.put("identType", o_identType);
						sglCustMap.put("identTypeNm", o_identTypeNm);
						sglCustMap.put("identNo", o_identNo);
						retCustList.add(sglCustMap);
					}
				}
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("count", retCustList.size());
				jsonMap.put("data", retCustList);
				retMap.put("json", jsonMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	
	public Map<String, Object> updateCutomerCoreNo(String custId, String coreNo){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(custId) || StringUtils.isEmpty(coreNo)){
				String logMsg = "ECIF客户号或者核心客户号为空，无法更新信息";
				log.error(logMsg);
				retMap.put("status", "error");
				retMap.put("msg", logMsg);
			}
			String jql = "update AcrmFCiCustomer t set t.coreNo='"+coreNo+"' where t.custId='"+custId+"'";
			int res = this.baseDAO.batchExecuteWithIndexParam(jql, null);
			if(res >= 0){
				String logMsg = "成功更新客户信息";
				log.info(logMsg);
				retMap.put("status", "success");
				retMap.put("msg", logMsg);
			}else{
				String logMsg = "没有可更新数据";
				log.warn(logMsg);
				retMap.put("status", "warn");
				retMap.put("msg", logMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误，请联系管理员";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
		}
		return retMap;
	}
}

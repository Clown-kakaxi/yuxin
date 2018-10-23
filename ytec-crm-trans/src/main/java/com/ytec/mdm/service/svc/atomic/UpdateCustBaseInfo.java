package com.ytec.mdm.service.svc.atomic;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.dao.ProcedureHelper;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.biz.AcrmFCiAddress;
import com.ytec.mdm.domain.biz.AcrmFCiContmeth;
import com.ytec.mdm.domain.biz.AcrmFCiCrossindex;
import com.ytec.mdm.domain.biz.AcrmFCiCustomer;
import com.ytec.mdm.domain.biz.AcrmFCiPerMateinfo;
import com.ytec.mdm.domain.biz.AcrmFCiPerson;
import com.ytec.mdm.domain.biz.OcrmFCiBelongCustmgr;
import com.ytec.mdm.domain.biz.OcrmFCiCustinfoUphi;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

@Service
@SuppressWarnings({ "unchecked", "rawtypes", "unused", "deprecation" })
public class UpdateCustBaseInfo implements IEcifBizLogic {

	private static Logger log = LoggerFactory
			.getLogger(UpdateCustBaseInfo.class);

	private static Object FAILED = "Failed";
	private JPABaseDAO baseDAO;

	private static String customerName = "AcrmFCiCustomer";
	private static String personName = "AcrmFCiPerson";
	// private static String custIndentifierName = "AcrmFCiCustIdentifier";
	private static String addressName = "AcrmFCiAddress";
	private static String comtmethName = "AcrmFCiContmeth";
	
	//修改网银接口 新增配偶信息对应的表acrm_f_ci_per_mateinfo
	private static String mateinfoName = "AcrmFCiPerMateinfo";
	

	SimpleDateFormat df10 = new SimpleDateFormat(MdmConstants.DATE_FORMAMT);
	SimpleDateFormat df19 = new SimpleDateFormat(MdmConstants.TIME_FORMAMT);
	List<OcrmFCiCustinfoUphi> ocrmFCiCustinfoUphi = new ArrayList<OcrmFCiCustinfoUphi>();

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData crmData) throws Exception {
		System.out.println("=============================CRM服务=================================");
		/**
		 * 严格限定日期/时间格式
		 */
		df10.setLenient(false);
		df19.setLenient(false);

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		Element body = crmData.getBodyNode();// 获取节点
		String txCode = body.element("txCode").getTextTrim();// 获取交易编码
		String txName = body.element("txName").getTextTrim();// 获取交易名称
		String authType = body.element("authType").getTextTrim();// 获取权限控制类型
		String authCode = body.element("authCode").getTextTrim();// 获取权限控制代码

		OcrmFCiBelongCustmgr custMgr = new OcrmFCiBelongCustmgr();// //归属客户经理

		String custId = null; // 获取客户号
		try {
			custId = body.element("customer").element("custId").getTextTrim();

			Object obj = queryCustMgr(custId);

			if (obj != null) {
				custMgr = (OcrmFCiBelongCustmgr) obj;
				String custBelongMgr = custMgr.getMgrId();
				ProcedureHelper pc = new ProcedureHelper();
				NameUtil getName = new NameUtil();
				String procedureName = getName.GetProcedureName();
				// 调用存储过程
				pc.callProcedureNoReturn(procedureName, new Object[] { custId,
						custBelongMgr });
			}

		} catch (Exception e) {
			log.error("没有获取客户号" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_ECIF_NULL_ECIFCUSTNO);
			crmData.setSuccess(false);
			return;
		}
		// 客户表
		AcrmFCiCustomer customer = new AcrmFCiCustomer();
		customer.setCustId(custId);

		AcrmFCiPerson person = new AcrmFCiPerson(); // 个人客户表
		AcrmFCiAddress address = new AcrmFCiAddress(); // 地址信息表
		AcrmFCiContmeth comtmeth = new AcrmFCiContmeth(); // 联系信息表
		OcrmFCiBelongCustmgr belongManager = new OcrmFCiBelongCustmgr();// 归属客户经理
		AcrmFCiCrossindex crossIndex = new AcrmFCiCrossindex();// 交叉索引表
		AcrmFCiPerMateinfo mateinfo = new AcrmFCiPerMateinfo(); //配偶信息表

		Object obj = queryCustomer(custId, crmData, customerName);//查询客户，也就是没有更改之前的值 

		/**
		 * 判断查询客户表是否失败
		 */
		if (obj != null && obj.equals(FAILED)) {
			return;
		} else {
			/**
			 * CRM已存在该客户，根据新信息覆盖更新客户信息(有值覆盖无值),并新增到数据库中
			 */
			try {
				if (obj != null) {
					AcrmFCiCustomer oldcustomer = addCustomer(customer, body);//新值  将报文的值设置到customer中

					AcrmFCiCustomer old_customer = (AcrmFCiCustomer) obj;//旧值

					recordUpdateCustomerHis(oldcustomer, old_customer);//记录修改历史表

					customer = updateCustomer(oldcustomer, old_customer);//更新旧值

					Object personObj = (AcrmFCiPerson) queryCustomer(//旧值
							custId, crmData, personName);
					AcrmFCiPerson person_old=(AcrmFCiPerson)personObj;
					AcrmFCiPerson old_person =(AcrmFCiPerson)personObj;
					person = addPerson(customer, person, body);//新值

					recordUpdatePersonHis(person, old_person);

					AcrmFCiPerson oldperson = updatePerson(person, person_old);
					
					
					//修改配偶信息
					AcrmFCiPerMateinfo oldmateinfo = addMateinfo(customer, mateinfo, body);//新值
					
					Object mateinfoObj = (AcrmFCiPerMateinfo) queryMateinfo(//旧值
							custId, crmData, mateinfoName);
					
					AcrmFCiPerMateinfo old_mateinfo =(AcrmFCiPerMateinfo)mateinfoObj;
					
					recordUpdateMateinfoHis(oldmateinfo, old_mateinfo);// 比较

					mateinfo = updateMateinfo(oldmateinfo, old_mateinfo);
					
					
					

					List<AcrmFCiAddress> update_address = new ArrayList<AcrmFCiAddress>();
					if (body.selectSingleNode("address") != null) {
						List<Element> list_address = body.elements("address");
						for (int i = 0; i < list_address.size(); i++) {
							Element add_element = list_address.get(i);
							AcrmFCiAddress acrm_address = new AcrmFCiAddress();
							acrm_address = addAddress(customer, acrm_address,
									add_element);
							String addrType = acrm_address.getAddrType();
							AcrmFCiAddress oldaddress = (AcrmFCiAddress) queryAddress(
									custId, addrType, crmData, addressName);

							recordUpdateAddressHis(acrm_address, oldaddress,
									addrType);// 比较
							oldaddress = updateAddress(acrm_address, oldaddress);

							update_address.add(oldaddress);
						}
					}

					List<AcrmFCiContmeth> update_contmeth = new ArrayList<AcrmFCiContmeth>();
					if (body.selectSingleNode("contmeth") != null) {
						List<Element> list_contmeth = body.elements("contmeth");
						for (int i = 0; i < list_contmeth.size(); i++) {
							Element cont_element = list_contmeth.get(i);
							AcrmFCiContmeth acrm_contmeth = new AcrmFCiContmeth();
							acrm_contmeth = addComtmeth(customer,
									acrm_contmeth, cont_element);
							String contmethType = acrm_contmeth
									.getContmethType();
							AcrmFCiContmeth oldcomtmeth = (AcrmFCiContmeth) queryContmeth(
									custId, contmethType, crmData, comtmethName);

							recordUpdateContmethHis(acrm_contmeth, oldcomtmeth,
									contmethType);// 比较
							oldcomtmeth = updateComtmeth(acrm_contmeth,
									oldcomtmeth);
							update_contmeth.add(oldcomtmeth);
							if ("501".equals(acrm_contmeth.getContmethType())) {
								oldperson.setEmail(acrm_contmeth
										.getContmethInfo());
							}
						}
					}

					List<AcrmFCiCrossindex> crossIndex_list = new ArrayList<AcrmFCiCrossindex>();
					if (body.selectSingleNode("crossIndex") != null) {
						List list_crossIndex = body.elements("crossIndex");
						for (int i = 0; i < list_crossIndex.size(); i++) {
							Element crossIndex_Element = (Element) list_crossIndex
									.get(i);
							AcrmFCiCrossindex acrm_crossIndex = new AcrmFCiCrossindex();
							acrm_crossIndex = addCrossIndex(customer,
									acrm_crossIndex, crossIndex_Element);
							AcrmFCiCrossindex oldcrossIndex = (AcrmFCiCrossindex) queryCrossIndex(
									custId, acrm_crossIndex.getSrcSysNo(),
									crmData, "AcrmFCiCrossindex");
							oldcrossIndex = updateCrossIndex(acrm_crossIndex,
									oldcrossIndex);
							crossIndex_list.add(oldcrossIndex);
						}
					}
			/*		
					//新增配偶信息
					List<AcrmFCiPerMateinfo> update_mateinfo = new ArrayList<AcrmFCiPerMateinfo>();
					if (body.selectSingleNode("mateinfo") != null) {
						List<Element> list_mateinfo = body.elements("mateinfo");
						for (int i = 0; i < list_mateinfo.size(); i++) {
							Element add_element = list_mateinfo.get(i);
							AcrmFCiPerMateinfo acrm_mateinfo = new AcrmFCiPerMateinfo();
							acrm_mateinfo = addMateinfo(customer, acrm_mateinfo,
									add_element);
							//String addrType = acrm_mateinfo.getAddrType();
							AcrmFCiPerMateinfo oldmateinfo = (AcrmFCiPerMateinfo) queryMateinfo(
									custId, crmData, mateinfoName);
							recordUpdateMateinfoHis(acrm_mateinfo, oldmateinfo);// 比较
							oldmateinfo = updateMateinfo(acrm_mateinfo, oldmateinfo);

							update_mateinfo.add(oldmateinfo);
						}
					}*/
					

					baseDAO.merge(customer);

					if (oldperson.getCustId() != null) {
						baseDAO.merge(oldperson);
					}
					for (int i = 0; i < update_address.size(); i++) {
						address = update_address.get(i);
						baseDAO.merge(address);
					}
					for (int i = 0; i < update_contmeth.size(); i++) {
						comtmeth = update_contmeth.get(i);
						baseDAO.merge(comtmeth);
					}
					for (int i = 0; i < crossIndex_list.size(); i++) {
						AcrmFCiCrossindex newCrossIndex = new AcrmFCiCrossindex();
						newCrossIndex = crossIndex_list.get(i);
						if (newCrossIndex.getCrossindexId() != null
								&& !"".equals(newCrossIndex.getCrossindexId()
										.trim())) {
							baseDAO.merge(newCrossIndex);
						}
					}

					//					recordUpdateCustomerHis(oldcustomer, old_customer);
					//					recordUpdatePersonHis(person, old_person);
					baseDAO.flush();
				}
				/**
				 * CRM无该客户，新增客户信息
				 */
				else {
					customer = addCustomer(customer, body);
					person = addPerson(customer, person, body);

					List<AcrmFCiContmeth> contmeth_list = new ArrayList<AcrmFCiContmeth>();
					if (body.selectSingleNode("contmeth") != null) {
						List list_contmeth = body.elements("contmeth");
						for (int i = 0; i < list_contmeth.size(); i++) {
							Element address_Element = (Element) list_contmeth
									.get(i);
							AcrmFCiContmeth acrm_contmeth = new AcrmFCiContmeth();
							acrm_contmeth = addComtmeth(customer,
									acrm_contmeth, address_Element);
							contmeth_list.add(acrm_contmeth);
						}
					}

					List<AcrmFCiAddress> address_list = new ArrayList<AcrmFCiAddress>();
					if (body.selectSingleNode("address") != null) {
						List list_address = body.elements("address");
						for (int i = 0; i < list_address.size(); i++) {
							Element address_Element = (Element) list_address
									.get(i);
							AcrmFCiAddress acrm_address = new AcrmFCiAddress();
							acrm_address = addAddress(customer, acrm_address,
									address_Element);
							address_list.add(acrm_address);
						}
					}
					// belongManager=addBelongMgr(customer,belongManager,body);
					List<AcrmFCiCrossindex> crossIndex_list = new ArrayList<AcrmFCiCrossindex>();
					if (body.selectSingleNode("crossIndex") != null) {
						List list_crossIndex = body.elements("crossIndex");
						for (int i = 0; i < list_crossIndex.size(); i++) {
							Element crossIndex_Element = (Element) list_crossIndex
									.get(i);
							AcrmFCiCrossindex acrm_crossIndex = new AcrmFCiCrossindex();
							acrm_crossIndex = addCrossIndex(customer,
									acrm_crossIndex, crossIndex_Element);
							crossIndex_list.add(acrm_crossIndex);
						}
					}

					baseDAO.save(customer);
					if (person.getCustId() != null) {
						baseDAO.save(person);
					}
					for (int i = 0; i < contmeth_list.size(); i++) {
						comtmeth = contmeth_list.get(i);
						baseDAO.save(comtmeth);
					}
					for (int i = 0; i < address_list.size(); i++) {
						address = address_list.get(i);
						baseDAO.save(address);
					}
					for (int i = 0; i < crossIndex_list.size(); i++) {
						AcrmFCiCrossindex newCrossIndex = new AcrmFCiCrossindex();
						newCrossIndex = crossIndex_list.get(i);
						if (newCrossIndex.getCrossindexId() != null
								&& !"".equals(newCrossIndex.getCrossindexId()
										.trim())) {
							baseDAO.save(newCrossIndex);
						}
					}
					baseDAO.flush();
				}
			} catch (Exception e) {
				String msg;
				if (e instanceof ParseException) {
					msg = String.format(
							"日期/时间(%s)格式不符合规范,转换错误",
							e.getLocalizedMessage()
							.substring(
									e.getLocalizedMessage()
									.indexOf('"'))
									.replace("\"", ""));
					crmData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(),
							msg);
					log.error("{},{}", msg, e);
				} else if (e instanceof NumberFormatException) {
					msg = String.format(
							"数值(%s)格式不符合规范,转换错误",
							e.getLocalizedMessage()
							.substring(
									e.getLocalizedMessage()
									.indexOf('"'))
									.replace("\"", ""));
					crmData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(),
							msg);
					log.error("{},{}", msg, e);
				} else {
					msg = "数据保存失败";
					log.error("{},{}", msg, e);
					crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(),
							msg);
				}
				crmData.setSuccess(false);
				return;
			}
		}
	}

	public Object queryCustMgr(String custId) throws Exception {
		// 获得表名
		String tableName = "OcrmFCiBelongCustmgr";
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
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据客户编号查询所有的数据
	 * 
	 * @param custId
	 * @param crmData
	 * @return
	 * @throws Exception
	 */
	public Object queryCustomer(String custId, EcifData crmData,
			String simpleNames) throws Exception {

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
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}

	public Object queryCrossIndex(String custId, String srcSysNo,
			EcifData crmData, String simpleNames) throws Exception {

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
			jql.append(" AND a.srcSysNo =:srcSysNo");
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			paramMap.put("srcSysNo", srcSysNo);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}

	/**
	 * 查询地址信息表
	 * 
	 * @param custId
	 * @param addrType
	 * @param crmData
	 * @param simpleNames
	 * @return
	 * @throws Exception
	 */
	public Object queryAddress(String custId, String addrType,
			EcifData crmData, String simpleNames) throws Exception {

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
			jql.append(" AND a.addrType =:addrType");
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			paramMap.put("addrType", addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}
	
	
	/**
	 * 查询配偶信息表
	 * 
	 * @param custId
	 * @param addrType
	 * @param crmData
	 * @param simpleNames
	 * @return
	 * @throws Exception
	 */
	public Object queryMateinfo(String custId,
			EcifData crmData, String simpleNames) throws Exception {

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
			//jql.append(" AND a.addrType =:addrType");
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			//paramMap.put("addrType", addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}
	
	
	

	/**
	 * 查询联系信息
	 * 
	 * @param custId
	 * @param addrType
	 * @param crmData
	 * @param simpleNames
	 * @return
	 * @throws Exception
	 */
	public Object queryContmeth(String custId, String contmethType,
			EcifData crmData, String simpleNames) throws Exception {

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
			jql.append(" AND a.contmethType =:contmethType");
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			paramMap.put("contmethType", contmethType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}

	/**
	 * 增加客户信息表
	 * 
	 * @param customer
	 */
	public AcrmFCiCustomer addCustomer(AcrmFCiCustomer customer, Element body)
			throws Exception {

		String custId = body.element("customer").element("custId")
				.getTextTrim();
		String coreNo = body.element("customer").element("coreNo")
				.getTextTrim();
		String custType = body.element("customer").element("custType")
				.getTextTrim();
		String identType = body.element("customer").element("identType")
				.getTextTrim();
		String identNo = body.element("customer").element("identNo")
				.getTextTrim();
		String custName = body.element("customer").element("custName")
				.getTextTrim();
		String postName = body.element("customer").element("postName")
				.getTextTrim();
		String shortName = body.element("customer").element("shortName")
				.getTextTrim();
		String enName = body.element("customer").element("enName")
				.getTextTrim();
		String enShortName = body.element("customer").element("enShortName")
				.getTextTrim();
		String custStat = body.element("customer").element("custStat")
				.getTextTrim();
		String riskNationCode = body.element("customer")
				.element("riskNationCode").getTextTrim();
		String potentialFlag = body.element("customer")
				.element("potentialFlag").getTextTrim();
		String ebankFlag = body.element("customer").element("ebankFlag")
				.getTextTrim();
		String realFlag = body.element("customer").element("realFlag")
				.getTextTrim();
		String inoutFlag = body.element("customer").element("inoutFlag")
				.getTextTrim();
		String blankFlag = body.element("customer").element("blankFlag")
				.getTextTrim();
		String vipFlag = body.element("customer").element("vipFlag")
				.getTextTrim();
		String mergeFlag = body.element("customer").element("mergeFlag")
				.getTextTrim();
		String linkmanName = body.element("customer").element("linkmanName")
				.getTextTrim();
		String linkmanTel = body.element("customer").element("linkmanTel")
				.getTextTrim();
		String firstLoanDate = body.element("customer")
				.element("firstLoanDate").getTextTrim();
		String loanCustMgr = body.element("customer").element("loanCustMgr")
				.getTextTrim();
		String cusBankRel = body.element("customer").element("cusBankRel")
				.getTextTrim();
		String loanMainBrId = body.element("customer").element("loanMainBrId")
				.getTextTrim();
		String arCustFlag = body.element("customer").element("arCustFlag")
				.getTextTrim();
		String arCustType = body.element("customer").element("arCustType")
				.getTextTrim();
		String sourceChannel = body.element("customer")
				.element("sourceChannel").getTextTrim();
		String recommender = body.element("customer").element("recommender")
				.getTextTrim();
		String infoPer = body.element("customer").element("infoPer")
				.getTextTrim();
		String createDate = body.element("customer").element("createDate")
				.getTextTrim();
		String createTime = body.element("customer").element("createTime")
				.getTextTrim();
		String createBranchNo = body.element("customer")
				.element("createBranchNo").getTextTrim();
		String createTellerNo = body.element("customer")
				.element("createTellerNo").getTextTrim();

		customer.setCustId(custId);
		customer.setCoreNo(coreNo);
		customer.setCustType(custType);
		customer.setIdentType(identType);
		customer.setIdentNo(identNo);
		customer.setCustName(custName);
		customer.setPostName(postName);
		customer.setShortName(shortName);
		customer.setEnName(enName);
		customer.setEnShortName(enShortName);
		customer.setCustStat(custStat);
		customer.setRiskNationCode(riskNationCode);
		customer.setPotentialFlag(potentialFlag);
		customer.setEbankFlag(ebankFlag);
		customer.setRealFlag(realFlag);
		customer.setInoutFlag(inoutFlag);
		customer.setBlankFlag(blankFlag);
		customer.setVipFlag(vipFlag);
		customer.setMergeFlag(mergeFlag);
		customer.setLinkmanName(linkmanName);
		customer.setLinkmanTel(linkmanTel);
		if (firstLoanDate != null && !firstLoanDate.trim().equals("")) {
			customer.setFirstLoanDate(df10.parse(firstLoanDate));
		}
		customer.setLoanCustMgr(loanCustMgr);
		// customer.setCusBankRel(cusBankRel);
		customer.setLoanMainBrId(loanMainBrId);
		customer.setArCustFlag(arCustFlag);
		customer.setArCustType(arCustType);
		customer.setSourceChannel(sourceChannel);
		customer.setRecommender(recommender);
		customer.setInfoPer(infoPer);
		if (createDate != null && !createDate.trim().equals("")) {
			customer.setCreateDate(df10.parse(createDate));
		}
		if (createTime != null && !createTime.trim().equals("")) {
			customer.setCreateTime(new Timestamp(df19.parse(createTime)
					.getTime()));
		}
		customer.setCreateBranchNo(createBranchNo);
		customer.setCreateTellerNo(createTellerNo);
		customer.setLastUpdateSys("WY");
		customer.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return customer;
	}

	/**
	 * 修改客户信息表
	 * 
	 * @param customer
	 */
	public AcrmFCiCustomer updateCustomer(AcrmFCiCustomer customer,
			AcrmFCiCustomer oldcustomer) throws Exception {

		if (oldcustomer == null) {
			return customer;
		}

		oldcustomer.setCustId(customer.getCustId() == null ? oldcustomer
				.getCustId() : customer.getCustId());
		oldcustomer.setCoreNo(customer.getCoreNo() == null ? oldcustomer
				.getCoreNo() : customer.getCoreNo());
		oldcustomer.setCustType(customer.getCustType() == null ? oldcustomer
				.getCustType() : customer.getCustType());
		oldcustomer.setIdentType(customer.getIdentType() == null ? oldcustomer
				.getIdentType() : customer.getIdentType());
		oldcustomer.setIdentNo(customer.getIdentNo() == null ? oldcustomer
				.getIdentNo() : customer.getIdentNo());
		oldcustomer.setCustName(customer.getCustName() == null ? oldcustomer
				.getCustName() : customer.getCustName());
		oldcustomer.setPostName(customer.getPostName() == null ? oldcustomer
				.getPostName() : customer.getPostName());
		oldcustomer.setShortName(customer.getShortName() == null ? oldcustomer
				.getShortName() : customer.getShortName());
		oldcustomer.setEnName(customer.getEnName() == null ? oldcustomer
				.getEnName() : customer.getEnName());
		oldcustomer
		.setEnShortName(customer.getEnShortName() == null ? oldcustomer
				.getEnShortName() : customer.getEnShortName());
		oldcustomer.setCustStat(customer.getCustStat() == null ? oldcustomer
				.getCustStat() : customer.getCustStat());
		oldcustomer
		.setRiskNationCode(customer.getRiskNationCode() == null ? oldcustomer
				.getRiskNationCode() : customer.getRiskNationCode());
		oldcustomer
		.setPotentialFlag(customer.getPotentialFlag() == null ? oldcustomer
				.getPotentialFlag() : customer.getPotentialFlag());
		oldcustomer.setEbankFlag(customer.getEbankFlag() == null ? oldcustomer
				.getEbankFlag() : customer.getEbankFlag());
		oldcustomer.setRealFlag(customer.getRealFlag() == null ? oldcustomer
				.getRealFlag() : customer.getRealFlag());
		oldcustomer.setInoutFlag(customer.getInoutFlag() == null ? oldcustomer
				.getInoutFlag() : customer.getInoutFlag());
		oldcustomer.setBlankFlag(customer.getBlankFlag() == null ? oldcustomer
				.getBlankFlag() : customer.getBlankFlag());
		oldcustomer.setVipFlag(customer.getVipFlag() == null ? oldcustomer
				.getVipFlag() : customer.getVipFlag());
		oldcustomer.setMergeFlag(customer.getMergeFlag() == null ? oldcustomer
				.getMergeFlag() : customer.getMergeFlag());
		oldcustomer
		.setLinkmanName(customer.getLinkmanName() == null ? oldcustomer
				.getLinkmanName() : customer.getLinkmanName());
		oldcustomer
		.setLinkmanTel(customer.getLinkmanTel() == null ? oldcustomer
				.getLinkmanTel() : customer.getLinkmanTel());
		oldcustomer
		.setFirstLoanDate(customer.getFirstLoanDate() == null ? oldcustomer
				.getFirstLoanDate() : customer.getFirstLoanDate());
		oldcustomer
		.setLoanCustMgr(customer.getLoanCustMgr() == null ? oldcustomer
				.getLoanCustMgr() : customer.getLoanCustMgr());
		// oldcustomer.setCusBankRel(customer.getCusBankRel()==null?oldcustomer.getCusBankRel():customer.getCusBankRel());
		oldcustomer
		.setLoanMainBrId(customer.getLoanMainBrId() == null ? oldcustomer
				.getLoanMainBrId() : customer.getLoanMainBrId());
		oldcustomer
		.setArCustFlag(customer.getArCustFlag() == null ? oldcustomer
				.getArCustFlag() : customer.getArCustFlag());
		oldcustomer
		.setArCustType(customer.getArCustType() == null ? oldcustomer
				.getArCustType() : customer.getArCustType());
		oldcustomer
		.setSourceChannel(customer.getSourceChannel() == null ? oldcustomer
				.getSourceChannel() : customer.getSourceChannel());
		oldcustomer
		.setRecommender(customer.getRecommender() == null ? oldcustomer
				.getRecommender() : customer.getRecommender());
		oldcustomer.setInfoPer(customer.getInfoPer() == null ? oldcustomer
				.getInfoPer() : customer.getInfoPer());
		oldcustomer
		.setCreateDate(customer.getCreateDate() == null ? oldcustomer
				.getCreateDate() : customer.getCreateDate());
		oldcustomer
		.setCreateTime(customer.getCreateTime() == null ? oldcustomer
				.getCreateTime() : customer.getCreateTime());
		oldcustomer
		.setCreateBranchNo(customer.getCreateBranchNo() == null ? oldcustomer
				.getCreateBranchNo() : customer.getCreateBranchNo());
		oldcustomer
		.setCreateTellerNo(customer.getCreateTellerNo() == null ? oldcustomer
				.getCreateTellerNo() : customer.getCreateTellerNo());
		oldcustomer.setLastUpdateSys("WY");
		oldcustomer.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return oldcustomer;
	}

	public void recordUpdateCustomerHis(AcrmFCiCustomer customer,//旧值默认不等于null
			AcrmFCiCustomer oldcustomer) {
		// OcrmFCiCustinfoUphi his=new OcrmFCiCustinfoUphi();
		if((customer.getCustName() ==null || "".equals(customer.getCustName())) && (oldcustomer.getCustName() ==null || "".equals(oldcustomer.getCustName()))){
		}
		//else if (customer.getCustName() !=null  && !customer.getCustName().equals(oldcustomer.getCustName())) {
		else if(customer.getCustName() ==null ){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '客户名称', '"+oldcustomer.getCustName()+"', '"+customer.getCustName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CUST_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!customer.getCustName().equals(oldcustomer.getCustName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldcustomer.getCustId());
			//			his.setUpdateItem("客户名称");
			//			his.setUpdateItemEn("CUST_NAME");
			//			his.setUpdateTable("ACRM_F_CI_CUSTOMER");
			//			his.setUpdateBeCont(oldcustomer.getCustName());
			//			his.setUpdateAfCont(customer.getCustName());
			//			his.setUpdateUser(oldcustomer.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '客户名称', '"+oldcustomer.getCustName()+"', '"+customer.getCustName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CUST_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((customer.getShortName() ==null || "".equals(customer.getShortName())) && (oldcustomer.getShortName() ==null || "".equals(oldcustomer.getShortName()))){
		}
		//else if ( customer.getShortName() !=null && !customer.getShortName().equals(oldcustomer.getShortName())) {
		else if(customer.getShortName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '客户简称', '"+oldcustomer.getShortName()+"', '"+customer.getShortName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'SHORT_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);

		}else if(!customer.getShortName().equals(oldcustomer.getShortName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldcustomer.getCustId());
			//			his.setUpdateItem("客户简称");
			//			his.setUpdateItemEn("SHORT_NAME");
			//			his.setUpdateTable("ACRM_F_CI_CUSTOMER");
			//			his.setUpdateBeCont(oldcustomer.getShortName());
			//			his.setUpdateAfCont(customer.getShortName());
			//			his.setUpdateUser(oldcustomer.getCustId());
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '客户简称', '"+oldcustomer.getShortName()+"', '"+customer.getShortName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'SHORT_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((customer.getEnName() ==null || "".equals(customer.getEnName())) && (oldcustomer.getEnName() ==null || "".equals(oldcustomer.getEnName()))){
		}
		//else if (customer.getEnName() !=null && !customer.getEnName().equals(oldcustomer.getEnName())) {
		else if(customer.getEnName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '英文名称', '"+oldcustomer.getEnName()+"', '"+customer.getEnName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'EN_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!customer.getEnName().equals(oldcustomer.getEnName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldcustomer.getCustId());
			//			his.setUpdateItem("英文名称");
			//			his.setUpdateItemEn("EN_NAME");
			//			his.setUpdateTable("ACRM_F_CI_CUSTOMER");
			//			his.setUpdateBeCont(oldcustomer.getEnName());
			//			his.setUpdateAfCont(customer.getEnName());
			//			his.setUpdateUser(oldcustomer.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '英文名称', '"+oldcustomer.getEnName()+"', '"+customer.getEnName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'EN_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
	}

	public void recordUpdatePersonHis(AcrmFCiPerson person,
			AcrmFCiPerson oldperson) {
		// OcrmFCiCustinfoUphi his=new OcrmFCiCustinfoUphi();
		if(oldperson == null){
			if(person.getPinyinName() != null && !"".equals(person.getPinyinName())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '拼音名称', null, '"+person.getPinyinName()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);	
			}
			if(person.getNickName() != null && !"".equals(person.getNickName())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '客户昵称', null, '"+person.getNickName()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getCitizenship() != null && !"".equals(person.getCitizenship())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '国籍', null, '"+person.getCitizenship()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getGender() != null && !"".equals(person.getGender())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '性别', null, '"+person.getGender()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getBirthday() != null && !"".equals(person.getBirthday())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '出生日期', null, '"+person.getBirthday()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'BIRTHDAY', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getUnitFex() != null && !"".equals(person.getUnitFex())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '传真号码', null, '"+person.getUnitFex()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'UNIT_FEX', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
		}
		if((person.getPinyinName() ==null || "".equals(person.getPinyinName())) && (oldperson.getPinyinName() ==null || "".equals(oldperson.getPinyinName()))){
		}
		//else if ( person.getPinyinName() !=null && !person.getPinyinName().equals(oldperson.getPinyinName())) {
		else if(person.getPinyinName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '拼音名称', '"+oldperson.getPinyinName()+"', '"+person.getPinyinName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getPinyinName().equals(oldperson.getPinyinName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("拼音名称");
			//			his.setUpdateItemEn("PINYIN_NAME");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getPinyinName());
			//			his.setUpdateAfCont(person.getPinyinName());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '拼音名称', '"+oldperson.getPinyinName()+"', '"+person.getPinyinName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getNickName() ==null || "".equals(person.getNickName())) && (oldperson.getNickName() ==null || "".equals(oldperson.getNickName()))){
		}
		//else if (person.getNickName() !=null && !person.getNickName().equals(oldperson.getNickName())) {
		else if(person.getNickName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '客户昵称', '"+oldperson.getNickName()+"', '"+person.getNickName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getNickName().equals(oldperson.getNickName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("客户昵称");
			//			his.setUpdateItemEn("NICK_NAME");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getNickName());
			//			his.setUpdateAfCont(person.getNickName());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '客户昵称', '"+oldperson.getNickName()+"', '"+person.getNickName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getCitizenship() ==null || "".equals(person.getCitizenship())) && (oldperson.getCitizenship() ==null || "".equals(oldperson.getCitizenship()))){
		}
		//else if ( person.getCitizenship() !=null && !person.getCitizenship().equals(oldperson.getCitizenship())) {
		else if( person.getCitizenship() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '国籍', '"+oldperson.getCitizenship()+"', '"+person.getCitizenship()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getCitizenship().equals(oldperson.getCitizenship())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("国籍");
			//			his.setUpdateItemEn("CITIZENSHIP");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getCitizenship());
			//			his.setUpdateAfCont(person.getCitizenship());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '国籍', '"+oldperson.getCitizenship()+"', '"+person.getCitizenship()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getGender() ==null || "".equals(person.getGender())) && (oldperson.getGender() ==null || "".equals(oldperson.getGender()))){
		}
		//else if ( person.getGender() !=null && !person.getGender().equals(oldperson.getGender())) {
		else if(person.getGender() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '性别', '"+oldperson.getGender()+"', '"+person.getGender()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getGender().equals(oldperson.getGender())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("性别");
			//			his.setUpdateItemEn("GENDER");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getGender());
			//			his.setUpdateAfCont(person.getGender());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '性别', '"+oldperson.getGender()+"', '"+person.getGender()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getBirthday() ==null || "".equals(person.getBirthday())) && (oldperson.getBirthday() ==null || "".equals(oldperson.getBirthday()))){
		}
		//else if ( person.getBirthday() !=null && !person.getBirthday().equals(oldperson.getBirthday())) {
		else if( person.getBirthday() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '出生日期', '"+oldperson.getBirthday()+"', '"+person.getBirthday()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'BIRTHDAY', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getBirthday().equals(oldperson.getBirthday())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("出生日期");
			//			his.setUpdateItemEn("BIRTHDAY");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(df10.format(oldperson.getBirthday()));
			//			his.setUpdateAfCont(df10.format(person.getBirthday()));
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '出生日期', '"+oldperson.getBirthday()+"', '"+person.getBirthday()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'BIRTHDAY', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getUnitFex() ==null || "".equals(person.getUnitFex())) && (oldperson.getUnitFex() ==null || "".equals(oldperson.getUnitFex()))){
		}
		//else if ( person.getUnitFex() !=null && !person.getUnitFex().equals(oldperson.getUnitFex())) {
		else if(person.getUnitFex() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '传真号码', '"+oldperson.getUnitFex()+"', '"+person.getUnitFex()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'UNIT_FEX', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getUnitFex().equals(oldperson.getUnitFex())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("传真号码");
			//			his.setUpdateItemEn("UNIT_FEX");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getUnitFex());
			//			his.setUpdateAfCont(person.getUnitFex());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '传真号码', '"+oldperson.getUnitFex()+"', '"+person.getUnitFex()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'UNIT_FEX', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}

	}

	public void recordUpdateAddressHis(AcrmFCiAddress address,
			AcrmFCiAddress oldaddress, String addrType) {
		if(oldaddress ==null ){
			if("01".equals(addrType) && address.getAddr() != null && !"".equals(address.getAddr())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '邮寄地址', null, '"+address.getAddr()+"', '"+address.getCustId()+"', '"+address.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("04".equals(addrType) && address.getAddr() != null && !"".equals(address.getAddr())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '居住地址', null, '"+address.getAddr()+"', '"+address.getCustId()+"', '"+address.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
		}
		if (//oldaddress !=null && 
				address.getAddrType() != null && oldaddress.getAddrType()!=null 
				&& address.getAddrType().equals(oldaddress.getAddrType())
				&& oldaddress.getAddrType().equals(addrType)) {
			if ("01".equals(addrType)){
				if((address.getAddr() == null || "".equals(address.getAddr())) && (oldaddress.getAddr() == null || "".equals(oldaddress.getAddr()))){	
				}
				//else if( address.getAddr() !=null &&!address.getAddr().equals(oldaddress.getAddr())) {
				else if(address.getAddr() ==null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '邮寄地址', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getAddr().equals(oldaddress.getAddr())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldaddress.getCustId());
					//				his.setUpdateItem("邮寄地址");//联系地址
					//				his.setUpdateItemEn("ADDR");
					//				his.setUpdateTable("ACRM_F_CI_ADDRESS");
					//				his.setUpdateBeCont(oldaddress.getAddr());
					//				his.setUpdateAfCont(address.getAddr());
					//				his.setUpdateUser(oldaddress.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '邮寄地址', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				if((address.getZipcode() == null || "".equals(address.getZipcode())) && (oldaddress.getZipcode() == null || "".equals(oldaddress.getZipcode()))){
				}
				//else if (address.getZipcode() != null && !address.getZipcode().equals(oldaddress.getZipcode())) {
				else if(address.getZipcode() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '邮寄地址邮政编码', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getZipcode().equals(oldaddress.getZipcode())){
					//					OcrmFCiCustinfoUphi hi = new OcrmFCiCustinfoUphi();
					//					hi.setCustId(oldaddress.getCustId());
					//					hi.setUpdateItem("邮政编码");
					//					hi.setUpdateItemEn("ZIPCODE");
					//					hi.setUpdateTable("ACRM_F_CI_ADDRESS");
					//					hi.setUpdateBeCont(oldaddress.getZipcode());
					//					hi.setUpdateAfCont(address.getZipcode());
					//					hi.setUpdateUser(oldaddress.getCustId());
					//					hi.setUpdateDate(new Date());
					//					hi.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '邮寄地址邮政编码', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("04".equals(addrType)){
				if((address.getAddr() == null || "".equals(address.getAddr())) && (oldaddress.getAddr() == null || "".equals(oldaddress.getAddr()))){	
				}
				//else if( address.getAddr() != null && !address.getAddr().equals(oldaddress.getAddr())) {
				else if(address.getAddr() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '居住地址', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getAddr().equals(oldaddress.getAddr())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldaddress.getCustId());
					//				his.setUpdateItem("居住地址");//家庭地址
					//				his.setUpdateItemEn("ADDR");
					//				his.setUpdateTable("ACRM_F_CI_ADDRESS");
					//				his.setUpdateBeCont(oldaddress.getAddr());
					//				his.setUpdateAfCont(address.getAddr());
					//				his.setUpdateUser(oldaddress.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '居住地址', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				if((address.getZipcode() == null || "".equals(address.getZipcode())) && (oldaddress.getZipcode() == null || "".equals(oldaddress.getZipcode()))){
				}
				//else if( address.getZipcode()!= null && !address.getZipcode().equals(oldaddress.getZipcode())) {
				else if(address.getZipcode() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '居住地址邮政编码', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getZipcode().equals(oldaddress.getZipcode())){
					//					OcrmFCiCustinfoUphi hi = new OcrmFCiCustinfoUphi();
					//					hi.setCustId(oldaddress.getCustId());
					//					hi.setUpdateItem("邮政编码");
					//					hi.setUpdateItemEn("ZIPCODE");
					//					hi.setUpdateTable("ACRM_F_CI_ADDRESS");
					//					hi.setUpdateBeCont(oldaddress.getZipcode());
					//					hi.setUpdateAfCont(address.getZipcode());
					//					hi.setUpdateUser(oldaddress.getCustId());
					//					hi.setUpdateDate(new Date());
					//					hi.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '居住地址邮政编码', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
		}
	}
	//		if (address.getZipcode() != null
	//				&& !address.getZipcode().equals(oldaddress.getZipcode())
	//				&& oldaddress.getAddrType().equals(addrType)) {
	//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
	//			his.setCustId(oldaddress.getCustId());
	//			his.setUpdateItem("邮政编码");
	//			his.setUpdateItemEn("ZIPCODE");
	//			his.setUpdateTable("ACRM_F_CI_ADDRESS");
	//			his.setUpdateBeCont(oldaddress.getZipcode());
	//			his.setUpdateAfCont(address.getZipcode());
	//			his.setUpdateUser(oldaddress.getCustId());
	//			his.setUpdateDate(new Date());
	//			his.setApprFlag("X");
	//			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
	//					+ "(sysdate, '邮政编码', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
	//			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
	//
	//		}
	
	
	//记录配偶更新历史信息
	public void recordUpdateMateinfoHis(AcrmFCiPerMateinfo mateinfo,
			AcrmFCiPerMateinfo oldmateinfo) {
		if(oldmateinfo == null){
			//配偶客户编号 CUST_ID_MATE
			//配偶姓名 MATE_NAME
			//家庭电话 HOME_TEL
			//手机号码  MOBILE
			if(mateinfo.getCustIdMate() != null && !"".equals(mateinfo.getCustIdMate())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '配偶客户编号', null, '"+mateinfo.getCustIdMate()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);	
			}
			//配偶姓名 MATE_NAME
			if(mateinfo.getMateName() != null && !"".equals(mateinfo.getMateName())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '配偶姓名', null, '"+mateinfo.getMateName()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			//家庭电话 HOME_TEL
			if(mateinfo.getHomeTel() != null && !"".equals(mateinfo.getHomeTel())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '家庭电话', null, '"+mateinfo.getHomeTel()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			//手机号码  MOBILE
			if(mateinfo.getMobile() != null && !"".equals(mateinfo.getMobile())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '手机号码', null, '"+mateinfo.getMobile()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
		}
		if((mateinfo.getCustIdMate() ==null || "".equals(mateinfo.getCustIdMate())) && (oldmateinfo.getCustIdMate() ==null || "".equals(oldmateinfo.getCustIdMate()))){
		}
		else if(mateinfo.getCustIdMate() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '配偶客户编号', '"+oldmateinfo.getCustIdMate()+"', '"+mateinfo.getCustIdMate()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getCustIdMate().equals(oldmateinfo.getCustIdMate())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '配偶客户编号', '"+oldmateinfo.getCustIdMate()+"', '"+mateinfo.getCustIdMate()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((mateinfo.getMateName() ==null || "".equals(mateinfo.getMateName())) && (oldmateinfo.getMateName() ==null || "".equals(oldmateinfo.getMateName()))){
		}
		else if(mateinfo.getMateName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '配偶姓名 ', '"+oldmateinfo.getMateName()+"', '"+mateinfo.getMateName()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getMateName().equals(oldmateinfo.getMateName())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '配偶姓名', '"+oldmateinfo.getMateName()+"', '"+mateinfo.getMateName()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((mateinfo.getHomeTel() ==null || "".equals(mateinfo.getHomeTel())) && (oldmateinfo.getHomeTel() ==null || "".equals(oldmateinfo.getHomeTel()))){
		}
		else if( mateinfo.getHomeTel() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '家庭电话', '"+oldmateinfo.getHomeTel()+"', '"+mateinfo.getHomeTel()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getHomeTel().equals(oldmateinfo.getHomeTel())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '家庭电话', '"+oldmateinfo.getHomeTel()+"', '"+mateinfo.getHomeTel()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((mateinfo.getMobile() ==null || "".equals(mateinfo.getMobile())) && (oldmateinfo.getMobile() ==null || "".equals(oldmateinfo.getMobile()))){
		}
		else if(mateinfo.getMobile() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '手机号码', '"+oldmateinfo.getMobile()+"', '"+mateinfo.getMobile()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getMobile().equals(oldmateinfo.getMobile())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '手机号码', '"+oldmateinfo.getMobile()+"', '"+mateinfo.getMobile()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
	}
	
	

	public void recordUpdateContmethHis(AcrmFCiContmeth contmeth,
			AcrmFCiContmeth oldcontmeth, String contmethType) {
		if(oldcontmeth == null){
			if("2031".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '办公电话', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("2041".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '家庭电话', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);

			}
			if("209".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '联络手机号码', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("102".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '业务手机号码', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("500".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '联系邮箱', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("501".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '业务邮箱', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}

		}
		if (//oldcontmeth !=null && 
				contmeth.getContmethType() != null && oldcontmeth.getContmethType()!=null
				&& contmeth.getContmethType().equals(
						oldcontmeth.getContmethType())
						&& oldcontmeth.getContmethType().equals(contmethType)) {
			if ("2031".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if (contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if(contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '办公电话', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("办公电话");
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '办公电话', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("2041".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if( contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if(contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '家庭电话', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("家庭电话");
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '家庭电话', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("209".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if( contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if( contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '联络手机号码', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("联络手机号码");//移动电话1
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '联络手机号码', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("102".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if(contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if(contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '业务手机号码', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("业务手机号码");//手机电话1
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '业务手机号码', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("500".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if(contmeth.getContmethInfo()!= null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if(contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '联系邮箱', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("电子邮件地址");//联系邮箱
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '联系邮箱', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("501".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if(contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if (contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '业务邮箱', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("电子对账单邮箱");//业务邮箱
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '业务邮箱', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
		}
	}

	/**
	 * 增加个人客户表
	 * 
	 * @param customer
	 */
	public AcrmFCiPerson addPerson(AcrmFCiCustomer customer,
			AcrmFCiPerson person, Element body) throws Exception {
		String custId = customer.getCustId();

		String perCustType = body.element("person").element("perCustType")
				.getTextTrim();// 个人客户类型
		String jointCustType = body.element("person").element("jointCustType")
				.getTextTrim();// 联名户类型
		String orgSubType = body.element("person").element("orgSubType")
				.getTextTrim();// 自贸区类型
		//		String ifOrgSubType = body.element("person").element("ifOrgSubType")
		//				.getTextTrim();// 是否自贸区标志
		String surName = body.element("person").element("surName")
				.getTextTrim();// 客户姓氏
		String personalName = body.element("person").element("personalName")
				.getTextTrim();// 客户名字
		String pinyinName = body.element("person").element("pinyinName")
				.getTextTrim();// 拼音名称
		String pinyinAbbr = body.element("person").element("pinyinAbbr")
				.getTextTrim();// 拼音缩写
		String personTitle = body.element("person").element("personTitle")
				.getTextTrim();// 客户称谓
		String areaCode = body.element("person").element("areaCode")
				.getTextTrim();// 行政区划代码
		String nickName = body.element("person").element("nickName")
				.getTextTrim();// 客户昵称
		String usedName = body.element("person").element("usedName")
				.getTextTrim();// 曾用名
		String gender = body.element("person").element("gender").getTextTrim();// 性别
		String birthday = body.element("person").element("birthday")
				.getTextTrim();// 出生日期
		String birthlocale = body.element("person").element("birthlocale")
				.getTextTrim();// 出生地点
		String citizenship = body.element("person").element("citizenship")
				.getTextTrim();// 国籍
		String nationality = body.element("person").element("nationality")
				.getTextTrim();// 民族
		String nativeplace = body.element("person").element("nativeplace")
				.getTextTrim();// 籍贯
		String household = body.element("person").element("household")
				.getTextTrim();// 户籍性质
		String hukouPlace = body.element("person").element("hukouPlace")
				.getTextTrim();// 户口所在地
		String marriage = body.element("person").element("marriage")
				.getTextTrim();// 婚姻状况
		String residence = body.element("person").element("residence")
				.getTextTrim();// 居住状况
		String health = body.element("person").element("health").getTextTrim();// 健康状况
		String religiousBelief = body.element("person")
				.element("religiousBelief").getTextTrim();// 宗教信仰
		String politicalFace = body.element("person").element("politicalFace")
				.getTextTrim();// 政治面貌
		String mobilePhone = body.element("person").element("mobilePhone")
				.getTextTrim();// 手机号码
		String email = body.element("person").element("email").getTextTrim();// 邮件地址
		String homepage = body.element("person").element("homepage")
				.getTextTrim();// 主页
		String weibo = body.element("person").element("weibo").getTextTrim();// 微博
		String weixin = body.element("person").element("weixin").getTextTrim();// 微信
		String qq = body.element("person").element("qq").getTextTrim();// QQ
		String starSign = body.element("person").element("starSign")
				.getTextTrim();// 星座
		String homeAddr = body.element("person").element("homeAddr")
				.getTextTrim();// 住宅地址
		String homeZipcode = body.element("person").element("homeZipcode")
				.getTextTrim();// 住宅邮编
		String homeTel = body.element("person").element("homeTel")
				.getTextTrim();// 住宅电话
		String highestSchooling = body.element("person")
				.element("highestSchooling").getTextTrim();// 最高学历
		String highestDegree = body.element("person").element("highestDegree")
				.getTextTrim();// 最高学位
		String graduateSchool = body.element("person")
				.element("graduateSchool").getTextTrim();// 毕业学校
		String major = body.element("person").element("major").getTextTrim();// 所学专业
		String graduationDate = body.element("person")
				.element("graduationDate").getTextTrim();// 毕业时间
		String careerStat = body.element("person").element("careerStat")
				.getTextTrim();// 职业状况
		String careerType = body.element("person").element("careerType")
				.getTextTrim();// 职业
		String profession = body.element("person").element("profession")
				.getTextTrim();// 从事行业
		String unitName = body.element("person").element("unitName")
				.getTextTrim();// 单位名称
		String unitChar = body.element("person").element("unitChar")
				.getTextTrim();// 单位性质
		String unitAddr = body.element("person").element("unitAddr")
				.getTextTrim();// 单位地址
		String unitZipcode = body.element("person").element("unitZipcode")
				.getTextTrim();// 单位邮编
		String unitTel = body.element("person").element("unitTel")
				.getTextTrim();// 单位电话
		String unitFex = body.element("person").element("unitFex")
				.getTextTrim();// 传真号码
		String postAddr = body.element("person").element("postAddr")
				.getTextTrim();// 通讯地址
		String postZipcode = body.element("person").element("postZipcode")
				.getTextTrim();// 通讯编码
		String postPhone = body.element("person").element("postPhone")
				.getTextTrim();// 联系电话
		String adminLevel = body.element("person").element("adminLevel")
				.getTextTrim();// 行政级别
		String cntName = body.element("person").element("cntName")
				.getTextTrim();// 单位联系人
		String duty = body.element("person").element("duty").getTextTrim();// 职务
		String workResult = body.element("person").element("workResult")
				.getTextTrim();// 业绩评价
		String careerStartDate = body.element("person")
				.element("careerStartDate").getTextTrim();// 参加工作时间
		String annualIncomeScope = body.element("person")
				.element("annualIncomeScope").getTextTrim();// 年收入范围
		String annualIncome = body.element("person").element("annualIncome")
				.getTextTrim();// 年收入
		String currCareerStartDate = body.element("person")
				.element("currCareerStartDate").getTextTrim();// 参加本单位日期
		String hasQualification = body.element("person")
				.element("hasQualification").getTextTrim();// 是否有执业资格
		String qualification = body.element("person").element("qualification")
				.getTextTrim();// 资格证书名称
		String careerTitle = body.element("person").element("careerTitle")
				.getTextTrim();// 职称
		String holdStockAmt = body.element("person").element("holdStockAmt")
				.getTextTrim();// 拥有我行股份金额
		String bankDuty = body.element("person").element("bankDuty")
				.getTextTrim();// 在我行职务
		String salaryAcctBank = body.element("person")
				.element("salaryAcctBank").getTextTrim();// 工资账户开户行
		String salaryAcctNo = body.element("person").element("salaryAcctNo")
				.getTextTrim();// 工资账号
		String loanCardNo = body.element("person").element("loanCardNo")
				.getTextTrim();// 贷款卡号码
		String holdAcct = body.element("person").element("holdAcct")
				.getTextTrim();// 在我行开立账户情况
		String holdCard = body.element("person").element("holdCard")
				.getTextTrim();// 持卡情况
		String resume = body.element("person").element("resume").getTextTrim();// 个人简历
		String usaTaxIdenNo = body.element("person").element("usaTaxIdenNo")
				.getTextTrim();// 美国纳税人识别号
		String lastDealingsDesc = body.element("person")
				.element("lastDealingsDesc").getTextTrim();// 前次来行状况
		String remark = body.element("person").element("remark").getTextTrim();// 备注
		
		//客户个人信息，新修改部分
		String spouseName = body.element("person").element("spouseName").getTextTrim();//  配偶姓名
		String spousePhone = body.element("person").element("spousePhone").getTextTrim();// 配偶联系电话
		String spouseMobile = body.element("person").element("spouseMobile").getTextTrim();// 配偶移动电话
		String spouseId = body.element("person").element("spouseId").getTextTrim();// 配偶对应客户号
		String spouseCoreId = body.element("person").element("spouseCoreId").getTextTrim();// 配偶对应核心客户号
		
		
		person.setSpouseCoreId(spouseCoreId);
		person.setSpouseId(spouseId);
		person.setSpouseMobile(spouseMobile);
		person.setSpouseName(spouseName);
		person.setSpousePhone(spousePhone);
		
		
		
		
		
		

		person.setCustId(custId);
		person.setPerCustType(perCustType);
		person.setAreaCode(areaCode);
		person.setJointCustType(jointCustType);
		person.setOrgSubType(orgSubType);
		//person.setIfOrgSubType(ifOrgSubType);
		person.setSurName(surName);
		person.setPersonalName(personalName);
		person.setPinyinName(pinyinName);
		person.setPinyinAbbr(pinyinAbbr);
		person.setPersonTitle(personTitle);
		person.setNickName(nickName);
		person.setUsedName(usedName);
		person.setGender(gender);
		if (birthday != null && !birthday.trim().equals("")) {
			person.setBirthday(df10.parse(birthday));
		}
		person.setBirthlocale(birthlocale);
		person.setCitizenship(citizenship);
		person.setNationality(nationality);
		person.setNativeplace(nativeplace);
		person.setHousehold(household);
		person.setHukouPlace(hukouPlace);
		person.setMarriage(marriage);
		person.setResidence(residence);
		person.setHealth(health);
		person.setReligiousBelief(religiousBelief);
		person.setPoliticalFace(politicalFace);
		person.setMobilePhone(mobilePhone);
		person.setEmail(email);
		person.setHomepage(homepage);
		person.setWeibo(weibo);
		person.setWeixin(weixin);
		person.setQq(qq);
		person.setStarSign(starSign);
		person.setHomeAddr(homeAddr);
		person.setHomeZipcode(homeZipcode);
		person.setHomeTel(homeTel);
		person.setHighestSchooling(highestSchooling);
		person.setHighestDegree(highestDegree);
		person.setGraduateSchool(graduateSchool);
		person.setMajor(major);
		if (graduationDate != null && !graduationDate.trim().equals("")) {
			person.setGraduationDate(df10.parse(graduationDate));
		}
		person.setCareerStat(careerStat);
		person.setCareerType(careerType);
		person.setProfession(profession);
		person.setUnitName(unitName);
		person.setUnitChar(unitChar);
		person.setUnitAddr(unitAddr);
		person.setUnitZipcode(unitZipcode);
		person.setUnitTel(unitTel);
		person.setUnitFex(unitFex);
		person.setPostAddr(postAddr);
		person.setPostZipcode(postZipcode);
		person.setPostPhone(postPhone);
		person.setAdminLevel(adminLevel);
		person.setCntName(cntName);
		person.setDuty(duty);
		person.setWorkResult(workResult);
		if (careerStartDate != null && !careerStartDate.trim().equals("")) {
			person.setCareerStartDate(df10.parse(careerStartDate));
		}
		person.setAnnualIncomeScope(annualIncomeScope);
		if (annualIncome != null && !annualIncome.trim().equals("")) {
			person.setAnnualIncome(new BigDecimal(annualIncome));
		}
		if (currCareerStartDate != null
				&& !currCareerStartDate.trim().equals("")) {
			person.setCurrCareerStartDate(df10.parse(currCareerStartDate));
		}
		person.setHasQualification(hasQualification);
		person.setQualification(qualification);
		person.setCareerTitle(careerTitle);
		if (holdStockAmt != null && !holdStockAmt.trim().equals("")) {
			person.setHoldStockAmt(new BigDecimal(holdStockAmt));
		}
		person.setBankDuty(bankDuty);
		person.setSalaryAcctBank(salaryAcctBank);
		person.setSalaryAcctNo(salaryAcctNo);
		person.setLoanCardNo(loanCardNo);
		person.setHoldAcct(holdAcct);
		person.setHoldCard(holdCard);
		person.setResume(resume);
		person.setUsaTaxIdenNo(usaTaxIdenNo);
		person.setLastDealingsDesc(lastDealingsDesc);
		person.setRemark(remark);
		person.setLastUpdateSys("WY");
		person.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return person;
	}

	/**
	 * 更新个人客户表
	 * 
	 * @param customer
	 */
	public AcrmFCiPerson updatePerson(AcrmFCiPerson person,
			AcrmFCiPerson oldperson) throws Exception {

		if (oldperson == null) {
			return person;
		}

		oldperson.setCustId(person.getCustId() == null ? oldperson.getCustId()
				: person.getCustId());
		oldperson.setPerCustType(person.getPerCustType() == null ? oldperson
				.getPerCustType() : person.getPerCustType());
		oldperson
		.setJointCustType(person.getJointCustType() == null ? oldperson
				.getJointCustType() : person.getJointCustType());
		oldperson.setOrgSubType(person.getOrgSubType() == null ? oldperson
				.getOrgSubType() : person.getOrgSubType());
		//		oldperson.setIfOrgSubType(person.getIfOrgSubType() == null ? oldperson
		//				.getIfOrgSubType() : person.getIfOrgSubType());
		oldperson.setSurName(person.getSurName() == null ? oldperson
				.getSurName() : person.getSurName());
		oldperson.setPersonalName(person.getPersonalName() == null ? oldperson
				.getPersonalName() : person.getPersonalName());
		oldperson.setPinyinName(person.getPinyinName() == null ? oldperson
				.getPinyinName() : person.getPinyinName());
		oldperson.setPinyinAbbr(person.getPinyinAbbr() == null ? oldperson
				.getPinyinAbbr() : person.getPinyinAbbr());
		oldperson.setPersonTitle(person.getPersonTitle() == null ? oldperson
				.getPersonTitle() : person.getPersonTitle());
		oldperson.setNickName(person.getNickName() == null ? oldperson
				.getNickName() : person.getNickName());
		oldperson.setUsedName(person.getUsedName() == null ? oldperson
				.getUsedName() : person.getUsedName());
		oldperson.setGender(person.getGender() == null ? oldperson.getGender()
				: person.getGender());
		oldperson.setBirthday(person.getBirthday() == null ? oldperson
				.getBirthday() : person.getBirthday());
		oldperson.setBirthlocale(person.getBirthlocale() == null ? oldperson
				.getBirthlocale() : person.getBirthlocale());
		oldperson.setCitizenship(person.getCitizenship() == null ? oldperson
				.getCitizenship() : person.getCitizenship());
		oldperson.setNationality(person.getNationality() == null ? oldperson
				.getNationality() : person.getNationality());
		oldperson.setNativeplace(person.getNativeplace() == null ? oldperson
				.getNativeplace() : person.getNativeplace());
		oldperson.setHousehold(person.getHousehold() == null ? oldperson
				.getHousehold() : person.getHousehold());
		oldperson.setHukouPlace(person.getHukouPlace() == null ? oldperson
				.getHukouPlace() : person.getHukouPlace());
		oldperson.setMarriage(person.getMarriage() == null ? oldperson
				.getMarriage() : person.getMarriage());
		oldperson.setResidence(person.getResidence() == null ? oldperson
				.getResidence() : person.getResidence());
		oldperson.setHealth(person.getHealth() == null ? oldperson.getHealth()
				: person.getHealth());
		oldperson
		.setReligiousBelief(person.getReligiousBelief() == null ? oldperson
				.getReligiousBelief() : person.getReligiousBelief());
		oldperson
		.setPoliticalFace(person.getPoliticalFace() == null ? oldperson
				.getPoliticalFace() : person.getPoliticalFace());
		oldperson.setMobilePhone(person.getMobilePhone() == null ? oldperson
				.getMobilePhone() : person.getMobilePhone());
		oldperson.setEmail(person.getEmail() == null ? oldperson.getEmail()
				: person.getEmail());
		oldperson.setHomepage(person.getHomepage() == null ? oldperson
				.getHomepage() : person.getHomepage());
		oldperson.setWeibo(person.getWeibo() == null ? oldperson.getWeibo()
				: person.getWeibo());
		oldperson.setWeixin(person.getWeixin() == null ? oldperson.getWeixin()
				: person.getWeixin());
		oldperson.setQq(person.getQq() == null ? oldperson.getQq() : person
				.getQq());
		oldperson.setStarSign(person.getStarSign() == null ? oldperson
				.getStarSign() : person.getStarSign());
		oldperson.setHomeAddr(person.getHomeAddr() == null ? oldperson
				.getHomeAddr() : person.getHomeAddr());
		oldperson.setHomeZipcode(person.getHomeZipcode() == null ? oldperson
				.getHomeZipcode() : person.getHomeZipcode());
		oldperson.setHomeTel(person.getHomeTel() == null ? oldperson
				.getHomeTel() : person.getHomeTel());
		oldperson
		.setHighestSchooling(person.getHighestSchooling() == null ? oldperson
				.getHighestSchooling() : person.getHighestSchooling());
		oldperson
		.setHighestDegree(person.getHighestDegree() == null ? oldperson
				.getHighestDegree() : person.getHighestDegree());
		oldperson
		.setGraduateSchool(person.getGraduateSchool() == null ? oldperson
				.getGraduateSchool() : person.getGraduateSchool());
		oldperson.setMajor(person.getMajor() == null ? oldperson.getMajor()
				: person.getMajor());
		oldperson
		.setGraduationDate(person.getGraduationDate() == null ? oldperson
				.getGraduationDate() : person.getGraduationDate());
		oldperson.setCareerStat(person.getCareerStat() == null ? oldperson
				.getCareerStat() : person.getCareerStat());
		oldperson.setCareerType(person.getCareerType() == null ? oldperson
				.getCareerType() : person.getCareerType());
		oldperson.setProfession(person.getProfession() == null ? oldperson
				.getProfession() : person.getProfession());
		oldperson.setUnitName(person.getUnitName() == null ? oldperson
				.getUnitName() : person.getUnitName());
		oldperson.setUnitChar(person.getUnitChar() == null ? oldperson
				.getUnitChar() : person.getUnitChar());
		oldperson.setUnitAddr(person.getUnitAddr() == null ? oldperson
				.getUnitAddr() : person.getUnitAddr());
		oldperson.setUnitZipcode(person.getUnitZipcode() == null ? oldperson
				.getUnitZipcode() : person.getUnitZipcode());
		oldperson.setUnitTel(person.getUnitTel() == null ? oldperson
				.getUnitTel() : person.getUnitTel());
		oldperson.setUnitFex(person.getUnitFex() == null ? oldperson
				.getUnitFex() : person.getUnitFex());
		oldperson.setPostAddr(person.getPostAddr() == null ? oldperson
				.getPostAddr() : person.getPostAddr());
		oldperson.setPostZipcode(person.getPostZipcode() == null ? oldperson
				.getPostZipcode() : person.getPostZipcode());
		oldperson.setPostPhone(person.getPostPhone() == null ? oldperson
				.getPostPhone() : person.getPostPhone());
		oldperson.setAdminLevel(person.getAdminLevel() == null ? oldperson
				.getAdminLevel() : person.getAdminLevel());
		oldperson.setCntName(person.getCntName() == null ? oldperson
				.getCntName() : person.getCntName());
		oldperson.setDuty(person.getDuty() == null ? oldperson.getDuty()
				: person.getDuty());
		oldperson.setWorkResult(person.getWorkResult() == null ? oldperson
				.getWorkResult() : person.getWorkResult());
		oldperson
		.setCareerStartDate(person.getCareerStartDate() == null ? oldperson
				.getCareerStartDate() : person.getCareerStartDate());
		oldperson
		.setAnnualIncomeScope(person.getAnnualIncomeScope() == null ? oldperson
				.getAnnualIncomeScope() : person.getAnnualIncomeScope());
		oldperson.setAnnualIncome(person.getAnnualIncome() == null ? oldperson
				.getAnnualIncome() : person.getAnnualIncome());
		oldperson
		.setCurrCareerStartDate(person.getCurrCareerStartDate() == null ? oldperson
				.getCurrCareerStartDate() : person
				.getCurrCareerStartDate());
		oldperson
		.setHasQualification(person.getHasQualification() == null ? oldperson
				.getHasQualification() : person.getHasQualification());
		oldperson
		.setQualification(person.getQualification() == null ? oldperson
				.getQualification() : person.getQualification());
		oldperson.setCareerTitle(person.getCareerTitle() == null ? oldperson
				.getCareerTitle() : person.getCareerTitle());
		oldperson.setHoldStockAmt(person.getHoldStockAmt() == null ? oldperson
				.getHoldStockAmt() : person.getHoldStockAmt());
		oldperson.setBankDuty(person.getBankDuty() == null ? oldperson
				.getBankDuty() : person.getBankDuty());
		oldperson
		.setSalaryAcctBank(person.getSalaryAcctBank() == null ? oldperson
				.getSalaryAcctBank() : person.getSalaryAcctBank());
		oldperson.setSalaryAcctNo(person.getSalaryAcctNo() == null ? oldperson
				.getSalaryAcctNo() : person.getSalaryAcctNo());
		oldperson.setLoanCardNo(person.getLoanCardNo() == null ? oldperson
				.getLoanCardNo() : person.getLoanCardNo());
		oldperson.setHoldAcct(person.getHoldAcct() == null ? oldperson
				.getHoldAcct() : person.getHoldAcct());
		oldperson.setHoldCard(person.getHoldCard() == null ? oldperson
				.getHoldCard() : person.getHoldCard());
		oldperson.setResume(person.getResume() == null ? oldperson.getResume()
				: person.getResume());
		oldperson.setUsaTaxIdenNo(person.getUsaTaxIdenNo() == null ? oldperson
				.getUsaTaxIdenNo() : person.getUsaTaxIdenNo());
		oldperson
		.setLastDealingsDesc(person.getLastDealingsDesc() == null ? oldperson
				.getLastDealingsDesc() : person.getLastDealingsDesc());
		oldperson.setRemark(person.getRemark() == null ? oldperson.getRemark()
				: person.getRemark());
		oldperson.setAreaCode(person.getAreaCode() == null ? oldperson
				.getAreaCode() : person.getAreaCode());
		
		//新字段添加
		oldperson.setSpouseCoreId(person.getSpouseCoreId() == null ? oldperson
				.getSpouseCoreId() : person.getSpouseCoreId());
		oldperson.setSpouseId(person.getSpouseId() == null ? oldperson
				.getSpouseId() : person.getSpouseId());
		oldperson.setSpouseMobile(person.getSpouseMobile() == null ? oldperson
				.getSpouseMobile() : person.getSpouseMobile());
		oldperson.setSpouseName(person.getSpouseName() == null ? oldperson
				.getSpouseName() : person.getSpouseName());
		oldperson.setSpousePhone(person.getSpousePhone()== null ? oldperson
				.getSpousePhone() : person.getSpousePhone());
		
		
		
		
		
		
		
		
		oldperson.setLastUpdateSys("WY");
		oldperson.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return oldperson;
	}

	/**
	 * 增加联系信息表
	 * 
	 * @param customer
	 */
	public AcrmFCiContmeth addComtmeth(AcrmFCiCustomer customer,
			AcrmFCiContmeth contmeth, Element body) throws Exception {

		String custId = customer.getCustId();

		String contmehtId = body.element("contmethId").getTextTrim();
		String isPriori = body.element("isPriori").getTextTrim(); // 是否首选
		String contmethType = body.element("contmethType").getTextTrim(); // 联系方式类型
		String contmethInfo = body.element("contmethInfo").getTextTrim(); // 联系方式内容
		String contmethSeq = body.element("contmethSeq").getTextTrim(); // 联系顺序号
		String remark = body.element("remark").getTextTrim(); // 备注
		String stat = body.element("stat").getTextTrim(); // 记录状态

		contmeth.setContmethId(Long.parseLong(contmehtId));
		contmeth.setCustId(custId);
		contmeth.setIsPriori(isPriori);
		contmeth.setContmethType(contmethType);
		contmeth.setContmethInfo(contmethInfo);
		if (contmethSeq != null && !contmethSeq.trim().equals("")) {
			contmeth.setContmethSeq(new BigDecimal(contmethSeq));
		}
		contmeth.setRemark(remark);
		contmeth.setStat(stat);
		contmeth.setLastUpdateSys("WY");
		contmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return contmeth;
	}

	/**
	 * 增加地址信息表
	 * 
	 * @param customer
	 */
	public AcrmFCiAddress addAddress(AcrmFCiCustomer customer,
			AcrmFCiAddress address, Element body) throws Exception {

		String custId = customer.getCustId();
		String addrId = body.element("addrId").getTextTrim();
		String addrType = body.element("addrType").getTextTrim();// 地址类型
		String addr = body.element("addr").getTextTrim();// 详细地址
		String enAddr = body.element("enAddr").getTextTrim();// 英文地址
		String contmethInfo = body.element("contmethInfo").getTextTrim();// 地址联系电话
		String zipcode = body.element("zipcode").getTextTrim();// 邮政编码
		String countryOrRegion = body.element("countryOrRegion").getTextTrim();// 国家或地区代码
		String adminZone = body.element("adminZone").getTextTrim();// 行政区划代码
		String areaCode = body.element("areaCode").getTextTrim();// 大区代码
		String provinceCode = body.element("provinceCode").getTextTrim();// 省直辖市自治区代码
		String cityCode = body.element("cityCode").getTextTrim();// 市地区州盟代码
		String countyCode = body.element("countyCode").getTextTrim();// 县区代码
		String townCode = body.element("townCode").getTextTrim();// 乡镇代码
		String townName = body.element("townName").getTextTrim();// 乡镇名称
		String streetName = body.element("streetName").getTextTrim();// 街道名称
		String villageNo = body.element("villageNo").getTextTrim();// 行政村编号
		String villageName = body.element("villageName").getTextTrim();// 行政村名称

		address.setCustId(custId);
		address.setAddrId(Long.parseLong(addrId));
		address.setAddrType(addrType);
		address.setAddr(addr);
		address.setEnAddr(enAddr);
		address.setContmethInfo(contmethInfo);
		address.setZipcode(zipcode);
		address.setCountryOrRegion(countryOrRegion);
		address.setAdminZone(adminZone);
		address.setAreaCode(areaCode);
		address.setProvinceCode(provinceCode);
		address.setCityCode(cityCode);
		address.setCountyCode(countyCode);
		address.setTownCode(townCode);
		address.setTownName(townName);
		address.setStreetName(streetName);
		address.setVillageNo(villageNo);
		address.setVillageName(villageName);
		address.setLastUpdateSys("WY");
		address.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return address;
	}

	/**
	 * 更新地址信息表
	 * 
	 * @param customer
	 */
	public AcrmFCiAddress updateAddress(AcrmFCiAddress address,
			AcrmFCiAddress oldaddress) throws Exception {

		if (oldaddress == null) {
			return address;
		}

		oldaddress.setAddrId(address.getAddrId() == 0 ? address.getAddrId()
				: oldaddress.getAddrId());
		oldaddress.setCustId(address.getCustId() == null ? oldaddress
				.getCustId() : address.getCustId());
		oldaddress.setAddrType(address.getAddrType() == null ? oldaddress
				.getAddrType() : address.getAddrType());
		oldaddress.setAddr(address.getAddr() == null ? oldaddress.getAddr()
				: address.getAddr());
		oldaddress.setEnAddr(address.getEnAddr() == null ? oldaddress
				.getEnAddr() : address.getEnAddr());
		oldaddress
		.setContmethInfo(address.getContmethInfo() == null ? oldaddress
				.getContmethInfo() : address.getContmethInfo());
		oldaddress.setZipcode(address.getZipcode() == null ? oldaddress
				.getZipcode() : address.getZipcode());
		oldaddress
		.setCountryOrRegion(address.getCountryOrRegion() == null ? oldaddress
				.getCountryOrRegion() : address.getCountryOrRegion());
		oldaddress.setAdminZone(address.getAdminZone() == null ? oldaddress
				.getAdminZone() : address.getAdminZone());
		oldaddress.setAreaCode(address.getAreaCode() == null ? oldaddress
				.getAreaCode() : address.getAreaCode());
		oldaddress
		.setProvinceCode(address.getProvinceCode() == null ? oldaddress
				.getProvinceCode() : address.getProvinceCode());
		oldaddress.setCityCode(address.getCityCode() == null ? oldaddress
				.getCityCode() : address.getCityCode());
		oldaddress.setCountyCode(address.getCountyCode() == null ? oldaddress
				.getCountyCode() : address.getCountyCode());
		oldaddress.setTownCode(address.getTownCode() == null ? oldaddress
				.getTownCode() : address.getTownCode());
		oldaddress.setTownName(address.getTownName() == null ? oldaddress
				.getTownName() : address.getTownName());
		oldaddress.setStreetName(address.getStreetName() == null ? oldaddress
				.getStreetName() : address.getStreetName());
		oldaddress.setVillageNo(address.getVillageNo() == null ? oldaddress
				.getVillageNo() : address.getVillageNo());
		oldaddress.setVillageName(address.getVillageName() == null ? oldaddress
				.getVillageName() : address.getVillageName());
		oldaddress.setLastUpdateSys("WY");
		oldaddress.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return oldaddress;
	}
	
	
	/**
	 * 增加配偶信息表
	 * 
	 * @param customer
	 */
	public AcrmFCiPerMateinfo addMateinfo(AcrmFCiCustomer customer,
			AcrmFCiPerMateinfo mateinfo, Element body) throws Exception {

		String custId = customer.getCustId();
		String custIdMate = body.element("mateinfo").element("custIdMate").getTextTrim();//配偶客户编号
		String mateName = body.element("mateinfo").element("mateName").getTextTrim();// 配偶姓名
		String homeTel = body.element("mateinfo").element("homeTel").getTextTrim();// 家庭电话
		String mobile = body.element("mateinfo").element("mobile").getTextTrim();// 手机号码
		mateinfo.setCustId(custId);
		mateinfo.setCustIdMate(custIdMate);
		mateinfo.setMateName(mateName);
		mateinfo.setHomeTel(homeTel);
		mateinfo.setMobile(mobile);
		mateinfo.setLastUpdateSys("WY");
		mateinfo.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return mateinfo;
	}
	
	/**
	 * 更新配偶信息客户表
	 * 
	 * @param customer
	 */
	public AcrmFCiPerMateinfo updateMateinfo(AcrmFCiPerMateinfo mateinfo,
			AcrmFCiPerMateinfo oldmateinfo) throws Exception {

		if (oldmateinfo == null) {
			return mateinfo;
		}
		//配偶客户编号 CUST_ID_MATE
		//配偶姓名 MATE_NAME
		//家庭电话 HOME_TEL
		//手机号码  MOBILE
		oldmateinfo.setCustId(mateinfo.getCustId() == null ? oldmateinfo.getCustId()
				: mateinfo.getCustId());
		oldmateinfo.setCustIdMate(mateinfo.getCustIdMate() == null ? oldmateinfo
				.getCustIdMate() : mateinfo.getCustIdMate());
		oldmateinfo.setHomeTel(mateinfo.getHomeTel() == null ? oldmateinfo
				.getHomeTel() : mateinfo.getHomeTel());
		oldmateinfo.setMobile(mateinfo.getMobile() == null ? oldmateinfo
				.getMobile() : mateinfo.getMobile());
		
		oldmateinfo.setLastUpdateSys("WY");
		oldmateinfo.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return oldmateinfo;
	}

	/**
	 * 更新联系信息表
	 * 
	 * @param customer
	 */
	public AcrmFCiContmeth updateComtmeth(AcrmFCiContmeth comtmeth,
			AcrmFCiContmeth oldcomtmeth) throws Exception {

		if (oldcomtmeth == null) {
			return comtmeth;
		}

		oldcomtmeth.setContmethId(comtmeth.getContmethId() == 0 ? comtmeth
				.getContmethId() : oldcomtmeth.getContmethId());
		oldcomtmeth.setCustId(comtmeth.getCustId() == null ? oldcomtmeth
				.getCustId() : comtmeth.getCustId());
		oldcomtmeth.setIsPriori(comtmeth.getIsPriori() == null ? oldcomtmeth
				.getIsPriori() : comtmeth.getIsPriori());
		comtmeth.setContmethType(comtmeth.getContmethType() == null ? oldcomtmeth
				.getContmethType() : comtmeth.getContmethType());
		oldcomtmeth
		.setContmethInfo(comtmeth.getContmethInfo() == null ? oldcomtmeth
				.getContmethInfo() : comtmeth.getContmethInfo());
		oldcomtmeth
		.setContmethSeq(comtmeth.getContmethSeq() == null ? oldcomtmeth
				.getContmethSeq() : comtmeth.getContmethSeq());
		oldcomtmeth.setRemark(comtmeth.getRemark() == null ? oldcomtmeth
				.getRemark() : comtmeth.getRemark());
		oldcomtmeth.setStat(comtmeth.getStat() == null ? oldcomtmeth.getStat()
				: comtmeth.getStat());
		oldcomtmeth.setLastUpdateSys("WY");
		oldcomtmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return oldcomtmeth;
	}

	/**
	 * 新增交叉索引表
	 * 
	 * @param customer
	 * @param crossIndex
	 * @param body
	 * @return
	 */
	public AcrmFCiCrossindex addCrossIndex(AcrmFCiCustomer customer,
			AcrmFCiCrossindex crossIndex, Element body) throws Exception {

		String custId = customer.getCustId();
		String crossindexId = body.element("crossindexId").getTextTrim();// 技术主键
		String srcSysNo = body.element("srcSysNo").getTextTrim();// 源系统编号
		String srcSysCustNo = body.element("srcSysCustNo").getTextTrim();// 源系统客户编号
		crossIndex.setCrossindexId(crossindexId);
		crossIndex.setCustId(custId);
		crossIndex.setSrcSysCustNo(srcSysCustNo);
		crossIndex.setSrcSysNo(srcSysNo);
		crossIndex.setLastUpdateSys("WY");
		crossIndex.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return crossIndex;
	}

	public AcrmFCiCrossindex updateCrossIndex(AcrmFCiCrossindex crossIndex,
			AcrmFCiCrossindex oldcrossIndex) throws Exception {
		if (oldcrossIndex == null) {
			return crossIndex;
		}
		// oldcrossIndex.setCrossindexId(crossIndex.getCrossindexId()==null?oldcrossIndex.getCrossindexId():crossIndex.getCrossindexId());
		oldcrossIndex.setCustId(crossIndex.getCustId() == null ? oldcrossIndex
				.getCustId() : crossIndex.getCustId());
		oldcrossIndex
		.setSrcSysCustNo(crossIndex.getSrcSysCustNo() == null ? oldcrossIndex
				.getSrcSysCustNo() : crossIndex.getSrcSysCustNo());
		oldcrossIndex
		.setSrcSysNo(crossIndex.getSrcSysNo() == null ? oldcrossIndex
				.getSrcSysNo() : crossIndex.getSrcSysNo());
		oldcrossIndex.setLastUpdateSys("WY");
		oldcrossIndex.setLastUpdateTm(new Timestamp(new Date().getTime()));
		return oldcrossIndex;
	}
}

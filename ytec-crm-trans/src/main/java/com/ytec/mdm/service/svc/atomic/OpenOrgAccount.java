package com.ytec.mdm.service.svc.atomic;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ytec.mdm.domain.biz.AcrmFCiCustIdentifier;
import com.ytec.mdm.domain.biz.AcrmFCiCustomer;
import com.ytec.mdm.domain.biz.AcrmFCiOrg;
import com.ytec.mdm.domain.biz.AdminAuthAccount;
import com.ytec.mdm.domain.biz.AdminAuthOrg;
import com.ytec.mdm.domain.biz.OcrmFCiBelongCustmgr;
import com.ytec.mdm.domain.biz.OcrmFCiBelongOrg;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;


/**
 * @author xuhoufei xuhf@yuchengtech.com
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes", "unused", "deprecation" })
public class OpenOrgAccount implements IEcifBizLogic {

	// 输出日志
	private static Logger log = LoggerFactory.getLogger(OpenOrgAccount.class);
	// 操作数据库
	private JPABaseDAO baseDAO;

	private static Object FAILED = "Failed";

	private static String customerName = "AcrmFCiCustomer";
	private static String orgName = "AcrmFCiOrg";
	private static String custIdentifierName = "AcrmFCiCustIdentifier";
	private static String addressName = "AcrmFCiAddress";
	private static String comtmethName = "AcrmFCiContmeth";

	SimpleDateFormat df10 = new SimpleDateFormat(MdmConstants.DATE_FORMAMT);
	SimpleDateFormat df19 = new SimpleDateFormat(MdmConstants.TIME_FORMAMT);

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData crmData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		Element body = crmData.getBodyNode(); // 获取节点

		String txCode = body.element("txCode").getTextTrim(); // 获取交易编码
		String txName = body.element("txName").getTextTrim(); // 获取交易名称
		String authType = body.element("authType").getTextTrim(); // 获取权限控制类型
		String authCode = body.element("authCode").getTextTrim(); // 获取权限控制代码
		OcrmFCiBelongCustmgr custMgr = new OcrmFCiBelongCustmgr();////归属客户经理
		// 获取客户号
		String custId = null;
		try {
			custId = body.element("customer").element("custId").getTextTrim();
			Object obj = queryCustMgr(custId);
			if(obj!=null){
				custMgr=(OcrmFCiBelongCustmgr)obj;
				String custBelongMgr=custMgr.getMgrId();
				ProcedureHelper pc=new ProcedureHelper();
				NameUtil getName=new NameUtil();
				String procedureName=getName.GetProcedureName();
				//调用存储过程
				pc.callProcedureNoReturn(procedureName, new Object[]{custId,custBelongMgr});
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

		AcrmFCiOrg org = new AcrmFCiOrg(); // 机构客户表
		AcrmFCiCustIdentifier custIdentifier = new AcrmFCiCustIdentifier(); // 客户证件信息表
		AcrmFCiAddress address = new AcrmFCiAddress(); // 地址信息表
		AcrmFCiContmeth comtmeth = new AcrmFCiContmeth(); // 联系信息表
		OcrmFCiBelongCustmgr belongManager = new OcrmFCiBelongCustmgr();//归属客户经理
		OcrmFCiBelongOrg    belongOrg = new OcrmFCiBelongOrg();//归属机构
		AcrmFCiCrossindex crossIndex = new AcrmFCiCrossindex();//交叉索引表
		Object obj = queryCustomer(custId, crmData, customerName);
		if (obj != null && obj.equals(FAILED)) {// 判断查询客户表是否失败
			return;
		} else {
			try {
				if (obj != null) {
					AcrmFCiCustomer oldcustomer = addCustomer(customer, body);
					customer = (AcrmFCiCustomer) obj;
					customer = updateCustomer(oldcustomer, customer);

					AcrmFCiOrg oldorg = (AcrmFCiOrg) queryCustomer(custId, crmData, orgName);
					org = addOrg(customer, org, body);
					oldorg = updateOrg(org, oldorg);
                    //机构证件id ECIF传过来
					List<Element> list_Identifier = body.elements("orgIdentifier");
					List<AcrmFCiCustIdentifier> update_custIdentifier = new ArrayList<AcrmFCiCustIdentifier>();
					for (int i = 0; i < list_Identifier.size(); i++) {
						Element per_element = list_Identifier.get(i);
						AcrmFCiCustIdentifier acrm_custIdentifier = new AcrmFCiCustIdentifier();
						acrm_custIdentifier = addcustIdentifier(customer, acrm_custIdentifier, per_element);
						String identType = acrm_custIdentifier.getIdentType();
						AcrmFCiCustIdentifier oldcustIdentifier = (AcrmFCiCustIdentifier) queryIdentifier(custId, identType, crmData,
								custIdentifierName);
						oldcustIdentifier = updatecustIdentifier(acrm_custIdentifier, oldcustIdentifier);
						update_custIdentifier.add(oldcustIdentifier);
					}
					//地址id ECIF传过来
					List<AcrmFCiAddress> update_address = new ArrayList<AcrmFCiAddress>();
					if(body.selectSingleNode("address")!=null){
						List<Element> list_address = body.elements("address");
						for (int i = 0; i < list_address.size(); i++) {
							Element add_element = list_address.get(i);
							AcrmFCiAddress acrm_address = new AcrmFCiAddress();
							acrm_address = addAddress(customer, acrm_address, add_element);
							String addrType = acrm_address.getAddrType();
							AcrmFCiAddress oldaddress = (AcrmFCiAddress) queryAddress(custId, addrType, crmData, addressName);
							oldaddress = updateAddress(acrm_address, oldaddress);
							update_address.add(oldaddress);
						}
					}

					//CONTMETH_ID ECIF传过来
					List<AcrmFCiContmeth> update_contmeth = new ArrayList<AcrmFCiContmeth>();
					if(body.selectSingleNode("contmeth")!=null){
						List<Element> list_contmeth = body.elements("contmeth");
						for (int i = 0; i < list_contmeth.size(); i++) {
							Element cont_element = list_contmeth.get(i);
							AcrmFCiContmeth acrm_contmeth = new AcrmFCiContmeth();
							acrm_contmeth = addComtmeth(customer, acrm_contmeth, cont_element);
							String contmethType = acrm_contmeth.getContmethType();
							AcrmFCiContmeth oldcomtmeth = (AcrmFCiContmeth) queryContmeth(custId, contmethType, crmData, comtmethName);
							oldcomtmeth = updateComtmeth(acrm_contmeth, oldcomtmeth);
							update_contmeth.add(oldcomtmeth);
						}
					}
					OcrmFCiBelongCustmgr oldCustMrg = (OcrmFCiBelongCustmgr) queryCustMgr(custId);
					belongManager=addBelongMgr(customer,belongManager,body);
					oldCustMrg=updateBelongCustMrg(belongManager,oldCustMrg);//归属客户经理

					OcrmFCiBelongOrg oldBelongOrg =(OcrmFCiBelongOrg)queryCustomer(custId,crmData,"OcrmFCiBelongOrg");
					belongOrg = addBelongOrg(customer,belongOrg,body);
					oldBelongOrg=updateBelongOrg(belongOrg,oldBelongOrg);
//					if(oldcrossIndex!=null){
//						deleteCrossIndex(custId);//如果存在那么就删除原先的
//					}

					List<AcrmFCiCrossindex> crossIndex_list = new ArrayList<AcrmFCiCrossindex>();
					if(body.selectSingleNode("crossIndex")!=null){
						List list_crossIndex = body.elements("crossIndex");
						for(int i=0;i<list_crossIndex.size();i++){
							Element crossIndex_Element = (Element) list_crossIndex.get(i);
							AcrmFCiCrossindex acrm_crossIndex = new AcrmFCiCrossindex();
							acrm_crossIndex = addCrossIndex(customer, acrm_crossIndex, crossIndex_Element);
							AcrmFCiCrossindex oldcrossIndex=(AcrmFCiCrossindex)queryCrossIndex(custId,acrm_crossIndex.getSrcSysNo(),crmData,"AcrmFCiCrossindex");
							oldcrossIndex=updateCrossIndex(acrm_crossIndex, oldcrossIndex);
							crossIndex_list.add(oldcrossIndex);
						}
					}

					//crossIndex=addCrossIndex(customer, crossIndex, body);


					baseDAO.merge(customer);
					if(oldorg.getCustId()!=null){
					  baseDAO.merge(oldorg);
					}
					for (int i = 0; i < update_custIdentifier.size(); i++) {
						custIdentifier = update_custIdentifier.get(i);
						baseDAO.merge(custIdentifier);
					}
					for (int i = 0; i < update_address.size(); i++) {
						address = update_address.get(i);
						baseDAO.merge(address);
					}
					for (int i = 0; i < update_contmeth.size(); i++) {
						comtmeth = update_contmeth.get(i);
						baseDAO.merge(comtmeth);
					}
					if(oldCustMrg.getCustId()!=null){
						 baseDAO.merge(oldCustMrg);
					}
					if(oldBelongOrg.getCustId()!=null){
						 baseDAO.merge(oldBelongOrg);
					}
//					if(oldcrossIndex.getCrossindexId()!=null){
//						 baseDAO.merge(oldcrossIndex);
//					}
					for(int i=0;i<crossIndex_list.size();i++){
						AcrmFCiCrossindex newCrossIndex = new AcrmFCiCrossindex();
						newCrossIndex = crossIndex_list.get(i);
						if(newCrossIndex.getCrossindexId()!=null && !"".equals(newCrossIndex.getCrossindexId().trim())){
							baseDAO.merge(newCrossIndex);
						}
					}
					baseDAO.flush();
				} else {
					customer = addCustomer(customer, body);
					org = addOrg(customer, org, body);
					List list_Identifier = body.elements("orgIdentifier");
					List<AcrmFCiCustIdentifier> Identtifier_list = new ArrayList<AcrmFCiCustIdentifier>();
					for (int i = 0; i < list_Identifier.size(); i++) {
						Element address_Element = (Element) list_Identifier.get(i);
						AcrmFCiCustIdentifier acrm_custIdentifier = new AcrmFCiCustIdentifier();
						acrm_custIdentifier = addcustIdentifier(customer, acrm_custIdentifier, address_Element);
						Identtifier_list.add(acrm_custIdentifier);
					}

				   List<AcrmFCiContmeth> contmeth_list = new ArrayList<AcrmFCiContmeth>();
			       if(body.selectSingleNode("contmeth")!=null){
						List list_contmeth = body.elements("contmeth");
						for (int i = 0; i < list_contmeth.size(); i++) {
							Element address_Element = (Element) list_contmeth.get(i);
							AcrmFCiContmeth acrm_contmeth = new AcrmFCiContmeth();
							acrm_contmeth = addComtmeth(customer, acrm_contmeth, address_Element);
							contmeth_list.add(acrm_contmeth);
						}
			        }

			       List<AcrmFCiAddress> address_list = new ArrayList<AcrmFCiAddress>();
			       if(body.selectSingleNode("address")!=null){
				        List list_address = body.elements("address");
						for (int i = 0; i < list_address.size(); i++) {
							Element address_Element = (Element) list_address.get(i);
							AcrmFCiAddress acrm_address = new AcrmFCiAddress();
							acrm_address = addAddress(customer, acrm_address, address_Element);
							address_list.add(acrm_address);
						}
			       }

					belongManager=addBelongMgr(customer,belongManager,body);
					belongOrg = addBelongOrg(customer,belongOrg,body);
					List<AcrmFCiCrossindex> crossIndex_list = new ArrayList<AcrmFCiCrossindex>();
					if(body.selectSingleNode("crossIndex")!=null){
					   List list_crossIndex = body.elements("crossIndex");
					   for(int i=0;i<list_crossIndex.size();i++){
						   Element crossIndex_Element = (Element) list_crossIndex.get(i);
						   AcrmFCiCrossindex acrm_crossIndex = new AcrmFCiCrossindex();
						   acrm_crossIndex = addCrossIndex(customer, acrm_crossIndex, crossIndex_Element);
						   crossIndex_list.add(acrm_crossIndex);
					   }
					}
					baseDAO.save(customer);
					if(org.getCustId()!=null){
					  baseDAO.save(org);
					}
					for (int i = 0; i < Identtifier_list.size(); i++) {
						custIdentifier = Identtifier_list.get(i);
						baseDAO.save(custIdentifier);
					}
					for (int i = 0; i < contmeth_list.size(); i++) {
						comtmeth = contmeth_list.get(i);
						baseDAO.save(comtmeth);
					}
					for (int i = 0; i < address_list.size(); i++) {
						address = address_list.get(i);
						baseDAO.save(address);
					}
					if(belongManager.getCustId()!=null){
					   baseDAO.save(belongManager);
					}
					if(belongOrg.getCustId()!=null){
					   baseDAO.save(belongOrg);
					}
					for(int i=0;i<crossIndex_list.size();i++){
						AcrmFCiCrossindex newCrossIndex = new AcrmFCiCrossindex();
						newCrossIndex = crossIndex_list.get(i);
						if(newCrossIndex.getCrossindexId()!=null && !"".equals(newCrossIndex.getCrossindexId().trim())){
							baseDAO.save(newCrossIndex);
						}
					}
					baseDAO.flush();
				}
			} catch (Exception e) {
				String msg;
				if (e instanceof ParseException) {
					msg = String.format("日期/时间(%s)格式不符合规范,转换错误",
							e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
					crmData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
					log.error("{},{}", msg, e);
				} else if (e instanceof NumberFormatException) {
					msg = String.format("数值(%s)格式不符合规范,转换错误",
							e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
					crmData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
					log.error("{},{}", msg, e);
				} else {
					msg = "数据保存失败";
					log.error("{},{}", msg, e);
					crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
				}
				crmData.setSuccess(false);
				return;
			}
		}

		/**
		 * 处理返回报文
		 */
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		Element customerEle = responseEle.addElement("custNo");
		customerEle.setText(custId);

		crmData.setRepNode(responseEle);

	}

	public void deleteCrossIndex(String custId) throws Exception{
//		String tableName ="AcrmFCiCrossindex";
//		// 拼装JQL查询语句
//		StringBuffer jql = new StringBuffer();
		String sql="delete from ACRM_F_CI_CROSSINDEX where cust_Id='"+custId+"'";
//		Map<String, String> paramMap = new HashMap<String, String>();
//		jql.append("DELETE FROM "+tableName +" ");
//		jql.append("WHERE custId=:custId");
//		paramMap.put("custId", custId);
		baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		baseDAO.flush();
	}
	public Object queryCustMgr(String custId) throws Exception{
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
		}else{
			return null;
		}


}


	/**
	 * 根据客户编号查询出所有的数据
	 *
	 * @param custId
	 * @param crmData
	 * @return
	 * @throws Exception
	 */

	public Object queryCustomer(String custId, EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) { return result.get(0); }

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
	public Object queryAddress(String custId, String addrType, EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("查询客户编号失败：" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}

	/**
	 * 查询证件信息
	 *
	 * @param custId
	 * @param addrType
	 * @param crmData
	 * @param simpleNames
	 * @return
	 * @throws Exception
	 */
	public Object queryIdentifier(String custId, String identType, EcifData crmData, String simpleNames) throws Exception {

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
			jql.append(" AND a.identType =:identType");
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			paramMap.put("identType", identType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

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
	public Object queryContmeth(String custId, String contmethType, EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) { return result.get(0); }

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
	public Object queryCrossIndex(String custId, String srcSysNo, EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) { return result.get(0); }

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
	public AcrmFCiCustomer addCustomer(AcrmFCiCustomer customer, Element body) throws Exception {

		String custId = body.element("customer").element("custId").getTextTrim();
		String coreNo = body.element("customer").element("coreNo").getTextTrim();
		String custType = body.element("customer").element("custType").getTextTrim();
		String identType = body.element("customer").element("identType").getTextTrim();
		String identNo = body.element("customer").element("identNo").getTextTrim();
		String custName = body.element("customer").element("custName").getTextTrim();
		String postName = body.element("customer").element("postName").getTextTrim();
		String shortName = body.element("customer").element("shortName").getTextTrim();
		String enName = body.element("customer").element("enName").getTextTrim();
		String enShortName = body.element("customer").element("enShortName").getTextTrim();
		String custStat = body.element("customer").element("custStat").getTextTrim();
		String riskNationCode = body.element("customer").element("riskNationCode").getTextTrim();
		String potentialFlag = body.element("customer").element("potentialFlag").getTextTrim();
		String ebankFlag = body.element("customer").element("ebankFlag").getTextTrim();
		String realFlag = body.element("customer").element("realFlag").getTextTrim();
		String inoutFlag = body.element("customer").element("inoutFlag").getTextTrim();
		String blankFlag = body.element("customer").element("blankFlag").getTextTrim();
		String vipFlag = body.element("customer").element("vipFlag").getTextTrim();
		String mergeFlag = body.element("customer").element("mergeFlag").getTextTrim();
		String linkmanName = body.element("customer").element("linkmanName").getTextTrim();
		String linkmanTel = body.element("customer").element("linkmanTel").getTextTrim();
		String firstLoanDate = body.element("customer").element("firstLoanDate").getTextTrim();
		String loanCustMgr = body.element("customer").element("loanCustMgr").getTextTrim();
		String cusBankRel = body.element("customer").element("cusBankRel").getTextTrim();
		// String loanMainBrId = body.element("customer").element("loanMainBrId").getTextTrim();
		String arCustFlag = body.element("customer").element("arCustFlag").getTextTrim();
		String arCustType = body.element("customer").element("arCustType").getTextTrim();
		String sourceChannel = body.element("customer").element("sourceChannel").getTextTrim();
		String recommender = body.element("customer").element("recommender").getTextTrim();
		String infoPer = body.element("customer").element("infoPer").getTextTrim();
		String createDate = body.element("customer").element("createDate").getTextTrim();
		String createTime = body.element("customer").element("createTime").getTextTrim();
		String createBranchNo = body.element("customer").element("createBranchNo").getTextTrim();
		String createTellerNo = body.element("customer").element("createTellerNo").getTextTrim();
		//add by liuming20170713
		String loanCustStat = body.element("customer").element("loanCustStat").getTextTrim();//信贷客户状态
		String loanCustId = body.element("customer").element("loanCustId") != null ? body.element("customer").element("loanCustId").getTextTrim():"";//信贷客户号
		customer.setLoanCustStat(loanCustStat);
		customer.setLoanCustId(loanCustId);
		
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
		// customer.setLoanMainBrId(loanMainBrId);
		customer.setArCustFlag(arCustFlag);
		customer.setArCustType(arCustType);
		customer.setSourceChannel(sourceChannel);
		customer.setRecommender(recommender);
		customer.setInfoPer(infoPer);
		if (createDate != null && !createDate.trim().equals("")) {
			// customer.setCreateDate(df10.parse(createDate));
			// SimpleDateFormat df10 = new SimpleDateFormat("yyyy-MM-dd");
			customer.setCreateDate(df10.parse(createDate));
		}
		if (createTime != null && !createTime.trim().equals("")) {
			// customer.setCreateTime(new Timestamp(new Long(createTime)));
			// SimpleDateFormat df19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			customer.setCreateTime(new Timestamp(new Long(df19.parse(createTime).getTime())));
		}
		customer.setCreateBranchNo(createBranchNo);
		customer.setCreateTellerNo(createTellerNo);
		return customer;
	}

	/**
	 * 修改客户信息表
	 *
	 * @param customer
	 */
	public AcrmFCiCustomer updateCustomer(AcrmFCiCustomer customer, AcrmFCiCustomer oldcustomer) throws Exception {

		if (oldcustomer == null) { return customer; }

		oldcustomer.setCustId(customer.getCustId() == null ? oldcustomer.getCustId() : customer.getCustId());
		oldcustomer.setCoreNo(customer.getCoreNo() == null ? oldcustomer.getCoreNo() : customer.getCoreNo());
		oldcustomer.setCustType(customer.getCustType() == null ? oldcustomer.getCustType() : customer.getCustType());
		oldcustomer.setIdentType(customer.getIdentType() == null ? oldcustomer.getIdentType() : customer.getIdentType());
		oldcustomer.setIdentNo(customer.getIdentNo() == null ? oldcustomer.getIdentNo() : customer.getIdentNo());
		oldcustomer.setCustName(customer.getCustName() == null ? oldcustomer.getCustName() : customer.getCustName());
		oldcustomer.setPostName(customer.getPostName() == null ? oldcustomer.getPostName() : customer.getPostName());
		oldcustomer.setShortName(customer.getShortName() == null ? oldcustomer.getShortName() : customer.getShortName());
		oldcustomer.setEnName(customer.getEnName() == null ? oldcustomer.getEnName() : customer.getEnName());
		oldcustomer.setEnShortName(customer.getEnShortName() == null ? oldcustomer.getEnShortName() : customer.getEnShortName());
		oldcustomer.setCustStat(customer.getCustStat() == null ? oldcustomer.getCustStat() : customer.getCustStat());
		oldcustomer.setRiskNationCode(customer.getRiskNationCode() == null ? oldcustomer.getRiskNationCode() : customer.getRiskNationCode());
		oldcustomer.setPotentialFlag(customer.getPotentialFlag() == null ? oldcustomer.getPotentialFlag() : customer.getPotentialFlag());
		oldcustomer.setEbankFlag(customer.getEbankFlag() == null ? oldcustomer.getEbankFlag() : customer.getEbankFlag());
		oldcustomer.setRealFlag(customer.getRealFlag() == null ? oldcustomer.getRealFlag() : customer.getRealFlag());
		oldcustomer.setInoutFlag(customer.getInoutFlag() == null ? oldcustomer.getInoutFlag() : customer.getInoutFlag());
		oldcustomer.setBlankFlag(customer.getBlankFlag() == null ? oldcustomer.getBlankFlag() : customer.getBlankFlag());
		oldcustomer.setVipFlag(customer.getVipFlag() == null ? oldcustomer.getVipFlag() : customer.getVipFlag());
		oldcustomer.setMergeFlag(customer.getMergeFlag() == null ? oldcustomer.getMergeFlag() : customer.getMergeFlag());
		oldcustomer.setLinkmanName(customer.getLinkmanName() == null ? oldcustomer.getLinkmanName() : customer.getLinkmanName());
		oldcustomer.setLinkmanTel(customer.getLinkmanTel() == null ? oldcustomer.getLinkmanTel() : customer.getLinkmanTel());
		oldcustomer.setFirstLoanDate(customer.getFirstLoanDate() == null ? oldcustomer.getFirstLoanDate() : customer.getFirstLoanDate());
		oldcustomer.setLoanCustMgr(customer.getLoanCustMgr() == null ? oldcustomer.getLoanCustMgr() : customer.getLoanCustMgr());
		// oldcustomer.setCusBankRel(customer.getCusBankRel()==null?oldcustomer.getCusBankRel():customer.getCusBankRel());
		oldcustomer.setLoanMainBrId(customer.getLoanMainBrId() == null ? oldcustomer.getLoanMainBrId() : customer.getLoanMainBrId());
		oldcustomer.setArCustFlag(customer.getArCustFlag() == null ? oldcustomer.getArCustFlag() : customer.getArCustFlag());
		oldcustomer.setArCustType(customer.getArCustType() == null ? oldcustomer.getArCustType() : customer.getArCustType());
		oldcustomer.setSourceChannel(customer.getSourceChannel() == null ? oldcustomer.getSourceChannel() : customer.getSourceChannel());
		oldcustomer.setRecommender(customer.getRecommender() == null ? oldcustomer.getRecommender() : customer.getRecommender());
		oldcustomer.setInfoPer(customer.getInfoPer() == null ? oldcustomer.getInfoPer() : customer.getInfoPer());
		oldcustomer.setCreateDate(customer.getCreateDate() == null ? oldcustomer.getCreateDate() : customer.getCreateDate());
		oldcustomer.setCreateTime(customer.getCreateTime() == null ? oldcustomer.getCreateTime() : customer.getCreateTime());
		oldcustomer.setCreateBranchNo(customer.getCreateBranchNo() == null ? oldcustomer.getCreateBranchNo() : customer.getCreateBranchNo());
		oldcustomer.setCreateTellerNo(customer.getCreateTellerNo() == null ? oldcustomer.getCreateTellerNo() : customer.getCreateTellerNo());
        //add by liuming 20170713
		oldcustomer.setLoanCustStat(customer.getLoanCustStat() == null ? oldcustomer.getLoanCustStat() : customer.getLoanCustStat());
		oldcustomer.setLoanCustId(customer.getLoanCustId() == null ? oldcustomer.getLoanCustId() : customer.getLoanCustId());
		return oldcustomer;
	}

	/**
	 * 增加机构客户表
	 *
	 * @param customer
	 */
	public AcrmFCiOrg addOrg(AcrmFCiCustomer customer, AcrmFCiOrg org, Element body) throws Exception {
        if(body.selectSingleNode("org")==null){
        	return org;
        }
		String custId = customer.getCustId();
		String custName = body.element("org").element("custName").getTextTrim();//机构用户的名称
		String orgCustType = body.element("org").element("orgCustType").getTextTrim(); // 机构客户类型
		String churcustype = body.element("org").element("churcustype").getTextTrim(); // 境内客户类别
		String nationCode = body.element("org").element("nationCode").getTextTrim(); // 国家或地区代码
		String areaCode = body.element("org").element("areaCode").getTextTrim(); // 行政区划代码
		String orgType = body.element("org").element("orgType").getTextTrim(); // 组织机构类型
		String orgSubType = body.element("org").element("orgSubType").getTextTrim(); // 组织机构子类型（核心）
		String ifOrgSubType= body.element("org").element("ifOrgSubType").getTextTrim();//是否自贸区
		String lncustp = body.element("org").element("lncustp").getTextTrim();//企业类型（核心）
		String orgCode = body.element("org").element("orgCode").getTextTrim(); // 组织机构代码
		String orgRegDate = body.element("org").element("orgRegDate").getTextTrim(); // 组织机构登记日期
		String orgExpDate = body.element("org").element("orgExpDate").getTextTrim(); // 组织机构有效日期
		String orgCodeUnit = body.element("org").element("orgCodeUnit").getTextTrim(); // 代码证颁发机关
		String orgCodeAnnDate = body.element("org").element("orgCodeAnnDate").getTextTrim(); // 代码证年检到期日
		String busiLicNo = body.element("org").element("busiLicNo").getTextTrim(); // 营业执照号码
		String mainIndustry = body.element("org").element("mainIndustry").getTextTrim(); // 行业分类（主营）
		String minorIndustry = body.element("org").element("minorIndustry").getTextTrim(); // 行业分类（副营）
		String industryDivision = body.element("org").element("industryDivision").getTextTrim(); // 产业划分
		String industryChar = body.element("org").element("industryChar").getTextTrim(); // 行业特征
		String entProperty = body.element("org").element("entProperty").getTextTrim(); // 企业性质
		String entScale = body.element("org").element("entScale").getTextTrim(); // 企业规模（银监）
		String entScaleRh = body.element("org").element("entScaleRh").getTextTrim(); // 企业规模（人行）
		String entScaleCk = body.element("org").element("entScaleCk").getTextTrim(); // 企业规模（存款）
		String assetsScale = body.element("org").element("assetsScale").getTextTrim(); // 资产规模
		String employeeScale = body.element("org").element("employeeScale").getTextTrim(); // 员工规模
		String economicType = body.element("org").element("economicType").getTextTrim(); // 经济类型
		String comHoldType = body.element("org").element("comHoldType").getTextTrim(); // 控股类型
		String orgForm = body.element("org").element("orgForm").getTextTrim(); // 组织形式
		String governStructure = body.element("org").element("governStructure").getTextTrim(); // 治理结构
		String inCllType = body.element("org").element("inCllType").getTextTrim(); // 行内行业类别
		String industryCategory = body.element("org").element("industryCategory").getTextTrim(); // 行业分类（企业规模）
		String investType = body.element("org").element("investType").getTextTrim(); // 投资主体
		String entBelong = body.element("org").element("entBelong").getTextTrim(); // 企业隶属
		String buildDate = body.element("org").element("buildDate").getTextTrim(); // 成立日期
		String superDept = body.element("org").element("superDept").getTextTrim(); // 主管部门
		String mainBusiness = body.element("org").element("mainBusiness").getTextTrim(); // 主营业务
		String minorBusiness = body.element("org").element("minorBusiness").getTextTrim(); // 兼营业务
		String businessMode = body.element("org").element("businessMode").getTextTrim(); // 经营方式
		String busiStartDate = body.element("org").element("busiStartDate").getTextTrim(); // 开始营业时间
		String induDeveProspect = body.element("org").element("induDeveProspect").getTextTrim(); // 行业发展前景
		String fundSource = body.element("org").element("fundSource").getTextTrim(); // 经费来源
		String zoneCode = body.element("org").element("zoneCode").getTextTrim(); // 经济区编码
		String fexcPrmCode = body.element("org").element("fexcPrmCode").getTextTrim(); // 外汇许可证号码
		String topCorpLevel = body.element("org").element("topCorpLevel").getTextTrim(); // 产业化龙头企业级别
		String comSpBusiness = body.element("org").element("comSpBusiness").getTextTrim(); // 特种经营标志
		String comSpLicNo = body.element("org").element("comSpLicNo").getTextTrim(); // 特种经营许可证编号
		String comSpDetail = body.element("org").element("comSpDetail").getTextTrim(); // 特种经营情况
		String comSpLicOrg = body.element("org").element("comSpLicOrg").getTextTrim(); // 特种许可证颁发机关
		String comSpStrDate = body.element("org").element("comSpStrDate").getTextTrim(); // 特种经营起始日期
		String comSpEndDate = body.element("org").element("comSpEndDate").getTextTrim(); // 特种经营到期日期
		String loanCardFlag = body.element("org").element("loanCardFlag").getTextTrim(); // 有无贷款卡
		String loanCardNo = body.element("org").element("loanCardNo").getTextTrim(); // 贷款卡号码
		String loanCardStat = body.element("org").element("loanCardStat").getTextTrim(); // 贷款卡状态
		String loadCardPwd = body.element("org").element("loadCardPwd").getTextTrim(); // 贷款卡密码
		String loadCardAuditDt = body.element("org").element("loadCardAuditDt").getTextTrim(); // 贷款卡年审日期
		String legalReprName = body.element("org").element("legalReprName").getTextTrim(); // 法定代表人名称
		String legalReprGender = body.element("org").element("legalReprGender").getTextTrim(); // 法定代表人性别
		String legalReprIdentType = body.element("org").element("legalReprIdentType").getTextTrim(); // 法定代表人证件类型
		String legalReprIdentNo = body.element("org").element("legalReprIdentNo").getTextTrim(); // 法定代表人证件号码
		String legalReprTel = body.element("org").element("legalReprTel").getTextTrim(); // 法定代表人联系电话
		String legalReprAddr = body.element("org").element("legalReprAddr").getTextTrim(); // 法定代表人户籍地址
		String legalReprPhoto = body.element("org").element("legalReprPhoto").getTextTrim(); // 法定代表人照片
		String legalReprNationCode = body.element("org").element("legalReprNationCode").getTextTrim(); // 法定代表人所在国家（地区）
		String finRepType = body.element("org").element("finRepType").getTextTrim(); // 财务报表类型
		String totalAssets = body.element("org").element("totalAssets").getTextTrim(); // 总资产
		String totalDebt = body.element("org").element("totalDebt").getTextTrim(); // 总负债
		String annualIncome = body.element("org").element("annualIncome").getTextTrim(); // 年收入
		String annualProfit = body.element("org").element("annualProfit").getTextTrim(); // 年利润
		String industryPosition = body.element("org").element("industryPosition").getTextTrim(); // 行业地位
		String isStockHolder = body.element("org").element("isStockHolder").getTextTrim(); // 是否我行股东
		String holdStockAmt = body.element("org").element("holdStockAmt").getTextTrim(); // 拥有我行股份金额
		String orgAddr = body.element("org").element("orgAddr").getTextTrim(); // 通讯地址
		String orgZipcode = body.element("org").element("orgZipcode").getTextTrim(); // 邮政编码
		String orgCus = body.element("org").element("orgCus").getTextTrim(); // 联系人
		String orgTel = body.element("org").element("orgTel").getTextTrim(); // 联系电话
		String orgFex = body.element("org").element("orgFex").getTextTrim(); // 传真号码
		String orgEmail = body.element("org").element("orgEmail").getTextTrim(); // 邮件地址
		String orgHomepage = body.element("org").element("orgHomepage").getTextTrim(); // 主页
		String orgWeibo = body.element("org").element("orgWeibo").getTextTrim(); // 微博
		String orgWeixin = body.element("org").element("orgWeixin").getTextTrim(); // 微信
		String lastDealingsDesc = body.element("org").element("lastDealingsDesc").getTextTrim(); // 前次来行状况
		String remark = body.element("org").element("remark").getTextTrim(); // 备注
        String orgBizCustType = body.element("org").element("orgBizCustType").getTextTrim();//法金客户类型

		org.setCustId(custId);
		org.setCustName(custName);
		org.setOrgBizCustType(orgBizCustType);
		org.setOrgCustType(orgCustType);
		org.setLncustp(lncustp);
		org.setChurcustype(churcustype);
		org.setNationCode(nationCode);
		org.setAreaCode(areaCode);
		org.setOrgType(orgType);
		org.setOrgSubType(orgSubType);
		org.setIfOrgSubType(ifOrgSubType);
		org.setOrgCode(orgCode);
		if (orgRegDate != null && !orgRegDate.trim().equals("")) {
			// SimpleDateFormat df10 = new SimpleDateFormat("yyyy-MM-dd");
			org.setOrgRegDate(df10.parse(orgRegDate));
		}
		if (orgExpDate != null && !orgExpDate.trim().equals("")) {
			org.setOrgExpDate(df10.parse(orgExpDate));
		}
		org.setOrgCodeUnit(orgCodeUnit);
		if (orgCodeAnnDate != null && !orgCodeAnnDate.trim().equals("")) {
			org.setOrgCodeAnnDate(df10.parse(orgCodeAnnDate));
		}
		org.setBusiLicNo(busiLicNo);
		org.setMainIndustry(mainIndustry);
		org.setMinorIndustry(minorIndustry);
		org.setIndustryDivision(industryDivision);
		org.setIndustryChar(industryChar);
		org.setEntProperty(entProperty);
		org.setEntScale(entScale);
		org.setEntScaleRh(entScaleRh);
		org.setEntScaleCk(entScaleCk);
		org.setAssetsScale(assetsScale);
		org.setEmployeeScale(employeeScale);
		org.setEconomicType(economicType);
		org.setComHoldType(comHoldType);
		org.setOrgForm(orgForm);
		org.setGovernStructure(governStructure);
		org.setInCllType(inCllType);
		org.setIndustryCategory(industryCategory);
		org.setInvestType(investType);
		org.setEntBelong(entBelong);
		if (buildDate != null && !buildDate.trim().equals("")) {
			org.setBuildDate(df10.parse(buildDate));
		}
		org.setSuperDept(superDept);
		org.setMainBusiness(mainBusiness);
		org.setMinorBusiness(minorBusiness);
		org.setBusinessMode(businessMode);
		if (busiStartDate != null && !busiStartDate.trim().equals("")) {
			org.setBusiStartDate(df10.parse(busiStartDate));
		}
		org.setInduDeveProspect(induDeveProspect);
		org.setFundSource(fundSource);
		org.setZoneCode(zoneCode);
		org.setFexcPrmCode(fexcPrmCode);
		org.setTopCorpLevel(topCorpLevel);
		org.setComSpBusiness(comSpBusiness);
		org.setComSpLicNo(comSpLicNo);
		org.setComSpDetail(comSpDetail);
		org.setComSpLicOrg(comSpLicOrg);
		if (comSpStrDate != null && !comSpStrDate.trim().equals("")) {
			org.setComSpStrDate(df10.parse(comSpStrDate));
		}
		if (comSpEndDate != null && !comSpEndDate.trim().equals("")) {
			org.setComSpEndDate(df10.parse(comSpEndDate));
		}
		org.setLoanCardFlag(loanCardFlag);
		org.setLoanCardNo(loanCardNo);
		org.setLoanCardStat(loanCardStat);
		org.setLoadCardPwd(loadCardPwd);
		if (loadCardAuditDt != null && !loadCardAuditDt.trim().equals("")) {
			org.setLoadCardAuditDt(df10.parse(loadCardAuditDt));
		}
		org.setLegalReprName(legalReprName);
		org.setLegalReprGender(legalReprGender);
		org.setLegalReprIdentType(legalReprIdentType);
		org.setLegalReprIdentNo(legalReprIdentNo);
		org.setLegalReprTel(legalReprTel);
		org.setLegalReprAddr(legalReprAddr);
		org.setLegalReprPhoto(legalReprPhoto);
		org.setLegalReprNationCode(legalReprNationCode);
		org.setFinRepType(finRepType);
		if (totalAssets != null && !totalAssets.trim().equals("")) {
			org.setTotalAssets(new BigDecimal(totalAssets));
		}
		if (totalDebt != null && !totalDebt.trim().equals("")) {
			org.setTotalDebt(new BigDecimal(totalDebt));
		}
		if (annualIncome != null && !annualIncome.trim().equals("")) {
			org.setAnnualIncome(new BigDecimal(annualIncome));
		}
		if (annualProfit != null && !annualProfit.trim().equals("")) {
			org.setAnnualProfit(new BigDecimal(annualProfit));
		}
		org.setIndustryPosition(industryPosition);
		org.setIsStockHolder(isStockHolder);
		if (holdStockAmt != null && !holdStockAmt.trim().equals("")) {
			org.setHoldStockAmt(new BigDecimal(holdStockAmt));
		}
		org.setOrgAddr(orgAddr);
		org.setOrgZipcode(orgZipcode);
		org.setOrgCus(orgCus);
		org.setOrgTel(orgTel);
		org.setOrgFex(orgFex);
		org.setOrgEmail(orgEmail);
		org.setOrgHomepage(orgHomepage);
		org.setOrgWeibo(orgWeibo);
		org.setOrgWeixin(orgWeixin);
		org.setLastDealingsDesc(lastDealingsDesc);
		org.setRemark(remark);

		return org;

	}

	/**
	 * 更新机构客户表
	 *
	 * @param customer
	 */
	public AcrmFCiOrg updateOrg(AcrmFCiOrg org, AcrmFCiOrg oldorg) throws Exception {

		if (oldorg == null) { return org; }

		oldorg.setCustId(org.getCustId() == null ? oldorg.getCustId() : org.getCustId());
		oldorg.setCustName(org.getCustName()==null? oldorg.getCustName() : org.getCustName());
		oldorg.setOrgCustType(org.getOrgCustType() == null ? oldorg.getOrgCustType() : org.getOrgCustType());
		oldorg.setLncustp(org.getLncustp() ==null?oldorg.getLncustp():org.getLncustp());
		oldorg.setChurcustype(org.getChurcustype() == null ? oldorg.getChurcustype() : org.getChurcustype());
		oldorg.setNationCode(org.getNationCode() == null ? oldorg.getNationCode() : org.getNationCode());
		oldorg.setAreaCode(org.getAreaCode() == null ? oldorg.getAreaCode() : org.getAreaCode());
		oldorg.setOrgBizCustType(org.getOrgBizCustType()==null? oldorg.getOrgBizCustType():org.getOrgBizCustType());
		oldorg.setOrgType(org.getOrgType() == null ? oldorg.getOrgType() : org.getOrgType());
		oldorg.setOrgSubType(org.getOrgSubType() == null ? oldorg.getOrgSubType() : org.getOrgSubType());
		oldorg.setIfOrgSubType(org.getIfOrgSubType() == null ? oldorg.getIfOrgSubType() : org.getIfOrgSubType());
		oldorg.setOrgCode(org.getOrgCode() == null ? oldorg.getOrgCode() : org.getOrgCode());
		oldorg.setOrgRegDate(org.getOrgRegDate() == null ? oldorg.getOrgRegDate() : org.getOrgRegDate());
		oldorg.setOrgExpDate(org.getOrgExpDate() == null ? oldorg.getOrgExpDate() : org.getOrgExpDate());
		oldorg.setOrgCodeUnit(org.getOrgCodeUnit() == null ? oldorg.getOrgCodeUnit() : org.getOrgCodeUnit());
		oldorg.setOrgCodeAnnDate(org.getOrgCodeAnnDate() == null ? oldorg.getOrgCodeAnnDate() : org.getOrgCodeAnnDate());
		oldorg.setBusiLicNo(org.getBusiLicNo() == null ? oldorg.getBusiLicNo() : org.getBusiLicNo());
		oldorg.setMainIndustry(org.getMainIndustry() == null ? oldorg.getMainIndustry() : org.getMainIndustry());
		oldorg.setMinorIndustry(org.getMinorIndustry() == null ? oldorg.getMinorIndustry() : org.getMinorIndustry());
		oldorg.setIndustryDivision(org.getIndustryDivision() == null ? oldorg.getIndustryDivision() : org.getIndustryDivision());
		oldorg.setIndustryChar(org.getIndustryChar() == null ? oldorg.getIndustryChar() : org.getIndustryChar());
		oldorg.setEntProperty(org.getEntProperty() == null ? oldorg.getEntProperty() : org.getEntProperty());
		oldorg.setEntScale(org.getEntScale() == null ? oldorg.getEntScale() : org.getEntScale());
		oldorg.setEntScaleRh(org.getEntScaleRh() == null ? oldorg.getEntScaleRh() : org.getEntScaleRh());
		oldorg.setEntScaleCk(org.getEntScaleCk() == null ? oldorg.getEntScaleCk() : org.getEntScaleCk());
		oldorg.setAssetsScale(org.getAssetsScale() == null ? oldorg.getAssetsScale() : org.getAssetsScale());
		oldorg.setEmployeeScale(org.getEmployeeScale() == null ? oldorg.getEmployeeScale() : org.getEmployeeScale());
		oldorg.setEconomicType(org.getEconomicType() == null ? oldorg.getEconomicType() : org.getEconomicType());
		oldorg.setComHoldType(org.getComHoldType() == null ? oldorg.getComHoldType() : org.getComHoldType());
		oldorg.setOrgForm(org.getOrgForm() == null ? oldorg.getOrgForm() : org.getOrgForm());
		oldorg.setGovernStructure(org.getGovernStructure() == null ? oldorg.getGovernStructure() : org.getGovernStructure());
		oldorg.setInCllType(org.getInCllType() == null ? oldorg.getInCllType() : org.getInCllType());
		oldorg.setIndustryCategory(org.getIndustryCategory() == null ? oldorg.getIndustryCategory() : org.getIndustryCategory());
		oldorg.setInvestType(org.getInvestType() == null ? oldorg.getInvestType() : org.getInvestType());
		oldorg.setEntBelong(org.getEntBelong() == null ? oldorg.getEntBelong() : org.getEntBelong());
		oldorg.setBuildDate(org.getBuildDate() == null ? oldorg.getBuildDate() : org.getBuildDate());
		oldorg.setSuperDept(org.getSuperDept() == null ? oldorg.getSuperDept() : org.getSuperDept());
		oldorg.setMainBusiness(org.getMainBusiness() == null ? oldorg.getMainBusiness() : org.getMainBusiness());
		oldorg.setMinorBusiness(org.getMinorBusiness() == null ? oldorg.getMinorBusiness() : org.getMinorBusiness());
		oldorg.setBusinessMode(org.getBusinessMode() == null ? oldorg.getBusinessMode() : org.getBusinessMode());
		oldorg.setBusiStartDate(org.getBusiStartDate() == null ? oldorg.getBusiStartDate() : org.getBusiStartDate());
		oldorg.setInduDeveProspect(org.getInduDeveProspect() == null ? oldorg.getInduDeveProspect() : org.getInduDeveProspect());
		oldorg.setFundSource(org.getFundSource() == null ? oldorg.getFundSource() : org.getFundSource());
		oldorg.setZoneCode(org.getZoneCode() == null ? oldorg.getZoneCode() : org.getZoneCode());
		oldorg.setFexcPrmCode(org.getFexcPrmCode() == null ? oldorg.getFexcPrmCode() : org.getFexcPrmCode());
		oldorg.setTopCorpLevel(org.getTopCorpLevel() == null ? oldorg.getTopCorpLevel() : org.getTopCorpLevel());
		oldorg.setComSpBusiness(org.getComSpBusiness() == null ? oldorg.getComSpBusiness() : org.getComSpBusiness());
		oldorg.setComSpLicNo(org.getComSpLicNo() == null ? oldorg.getComSpLicNo() : org.getComSpLicNo());
		oldorg.setComSpDetail(org.getComSpDetail() == null ? oldorg.getComSpDetail() : org.getComSpDetail());
		oldorg.setComSpLicOrg(org.getComSpLicOrg() == null ? oldorg.getComSpLicOrg() : org.getComSpLicOrg());
		oldorg.setComSpStrDate(org.getComSpStrDate() == null ? oldorg.getComSpStrDate() : org.getComSpStrDate());
		oldorg.setComSpEndDate(org.getComSpEndDate() == null ? oldorg.getComSpEndDate() : org.getComSpEndDate());
		oldorg.setLoanCardFlag(org.getLoanCardFlag() == null ? oldorg.getLoanCardFlag() : org.getLoanCardFlag());
		oldorg.setLoanCardNo(org.getLoanCardNo() == null ? oldorg.getLoanCardNo() : org.getLoanCardNo());
		oldorg.setLoanCardStat(org.getLoanCardStat() == null ? oldorg.getLoanCardStat() : org.getLoanCardStat());
		oldorg.setLoadCardPwd(org.getLoadCardPwd() == null ? oldorg.getLoadCardPwd() : org.getLoadCardPwd());
		oldorg.setLoadCardAuditDt(org.getLoadCardAuditDt() == null ? oldorg.getLoadCardAuditDt() : org.getLoadCardAuditDt());
		oldorg.setLegalReprName(org.getLegalReprName() == null ? oldorg.getLegalReprName() : org.getLegalReprName());
		oldorg.setLegalReprGender(org.getLegalReprGender() == null ? oldorg.getLegalReprGender() : org.getLegalReprGender());
		oldorg.setLegalReprIdentType(org.getLegalReprIdentType() == null ? oldorg.getLegalReprIdentType() : org.getLegalReprIdentType());
		oldorg.setLegalReprIdentNo(org.getLegalReprIdentNo() == null ? oldorg.getLegalReprIdentNo() : org.getLegalReprIdentNo());
		oldorg.setLegalReprTel(org.getLegalReprTel() == null ? oldorg.getLegalReprTel() : org.getLegalReprTel());
		oldorg.setLegalReprAddr(org.getLegalReprAddr() == null ? oldorg.getLegalReprAddr() : org.getLegalReprAddr());
		oldorg.setLegalReprPhoto(org.getLegalReprPhoto() == null ? oldorg.getLegalReprPhoto() : org.getLegalReprPhoto());
		oldorg.setLegalReprNationCode(org.getLegalReprNationCode() == null ? oldorg.getLegalReprNationCode() : org.getLegalReprNationCode());
		oldorg.setFinRepType(org.getFinRepType() == null ? oldorg.getFinRepType() : org.getFinRepType());
		oldorg.setTotalAssets(org.getTotalAssets() == null ? oldorg.getTotalAssets() : org.getTotalAssets());
		oldorg.setTotalDebt(org.getTotalDebt() == null ? oldorg.getTotalDebt() : org.getTotalDebt());
		oldorg.setAnnualIncome(org.getAnnualIncome() == null ? oldorg.getAnnualIncome() : org.getAnnualIncome());
		oldorg.setAnnualProfit(org.getAnnualProfit() == null ? oldorg.getAnnualProfit() : org.getAnnualProfit());
		oldorg.setIndustryPosition(org.getIndustryPosition() == null ? oldorg.getIndustryPosition() : org.getIndustryPosition());
		oldorg.setIsStockHolder(org.getIsStockHolder() == null ? oldorg.getIsStockHolder() : org.getIsStockHolder());
		oldorg.setHoldStockAmt(org.getHoldStockAmt() == null ? oldorg.getHoldStockAmt() : org.getHoldStockAmt());
		oldorg.setOrgAddr(org.getOrgAddr() == null ? oldorg.getOrgAddr() : org.getOrgAddr());
		oldorg.setOrgZipcode(org.getOrgZipcode() == null ? oldorg.getOrgZipcode() : org.getOrgZipcode());
		oldorg.setOrgCus(org.getOrgCus() == null ? oldorg.getOrgCus() : org.getOrgCus());
		oldorg.setOrgTel(org.getOrgTel() == null ? oldorg.getOrgTel() : org.getOrgTel());
		oldorg.setOrgFex(org.getOrgFex() == null ? oldorg.getOrgFex() : org.getOrgFex());
		oldorg.setOrgEmail(org.getOrgEmail() == null ? oldorg.getOrgEmail() : org.getOrgEmail());
		oldorg.setOrgHomepage(org.getOrgHomepage() == null ? oldorg.getOrgHomepage() : org.getOrgHomepage());
		oldorg.setOrgWeibo(org.getOrgWeibo() == null ? oldorg.getOrgWeibo() : org.getOrgWeibo());
		oldorg.setOrgWeixin(org.getOrgWeixin() == null ? oldorg.getOrgWeixin() : org.getOrgWeixin());
		oldorg.setLastDealingsDesc(org.getLastDealingsDesc() == null ? oldorg.getLastDealingsDesc() : org.getLastDealingsDesc());
		oldorg.setRemark(org.getRemark() == null ? oldorg.getRemark() : org.getRemark());

		return oldorg;

	}

	/**
	 * 增加客户证件信息表
	 *
	 * @param customer
	 */
	public AcrmFCiCustIdentifier addcustIdentifier(AcrmFCiCustomer customer, AcrmFCiCustIdentifier custIdentifier, Element body) throws Exception {

		String custId = customer.getCustId();
        //ECIF 传过来的作为技术主键
		String identId = body.element("identId").getTextTrim();// 证件id
		String identType = body.element("identType").getTextTrim();// 证件类型
		String identNo = body.element("identNo").getTextTrim();// 证件号码
		String identCustName = body.element("identCustName").getTextTrim();// 证件户名
		String identDesc = body.element("identDesc").getTextTrim();// 证件描述
		String countryOrRegion = body.element("countryOrRegion").getTextTrim();// 发证国家或地区
		String identOrg = body.element("identOrg").getTextTrim();// 发证机构
		String identApproveUnit = body.element("identApproveUnit").getTextTrim();// 证件批准单位
		String identCheckFlag = body.element("identCheckFlag").getTextTrim();// 证件年检标志
		String identCheckingDate = body.element("identCheckingDate").getTextTrim();// 证件年检到期日
		String identCheckedDate = body.element("identCheckedDate").getTextTrim();// 证件年检日期
		String identValidPeriod = body.element("identValidPeriod").getTextTrim();// 证件有效期
		String identEffectiveDate = body.element("identEffectiveDate").getTextTrim();// 证件生效日期
		String identExpiredDate = body.element("identExpiredDate").getTextTrim();// 证件失效日期
		String identValidFlag = body.element("identValidFlag").getTextTrim();// 证件有效标志
		String identPeriod = body.element("identPeriod").getTextTrim();// 证件期限
		String isOpenAccIdent = body.element("isOpenAccIdent").getTextTrim();// 是否开户证件
		String openAccIdentModifiedFlag = body.element("openAccIdentModifiedFlag").getTextTrim();// 开户证件修改标志
		String identModifiedTime = body.element("identModifiedTime").getTextTrim();// 证件修改时间
		String verifyDate = body.element("verifyDate").getTextTrim();// 校验日期
		String verifyEmployee = body.element("verifyEmployee").getTextTrim();// 校验员工
		String verifyResult = body.element("verifyResult").getTextTrim();// 校验结果
		//报文传过来
		custIdentifier.setIdentId(Long.parseLong(identId));
		custIdentifier.setCustId(custId);
		custIdentifier.setIdentType(identType);
		custIdentifier.setIdentNo(identNo);
		custIdentifier.setIdentCustName(identCustName);
		custIdentifier.setIdentDesc(identDesc);
		custIdentifier.setCountryOrRegion(countryOrRegion);
		custIdentifier.setIdentOrg(identOrg);
		custIdentifier.setIdentApproveUnit(identApproveUnit);
		custIdentifier.setIdentCheckFlag(identCheckFlag);
		if (identCheckedDate != null && !identCheckedDate.trim().equals("")) {
			custIdentifier.setIdentCheckingDate(df10.parse(identCheckingDate));
		}
		if (identCheckedDate != null && !identCheckedDate.trim().equals("")) {
			custIdentifier.setIdentCheckedDate(df10.parse(identCheckedDate));
		}
		if (identValidPeriod != null && !identValidPeriod.trim().equals("")) {
			custIdentifier.setIdentValidPeriod(new BigDecimal(identValidPeriod));
		}
		if (identEffectiveDate != null && !identEffectiveDate.trim().equals("")) {
			custIdentifier.setIdentEffectiveDate(df10.parse(identEffectiveDate));
		}
		if (identExpiredDate != null && !identExpiredDate.trim().equals("")) {
			custIdentifier.setIdentExpiredDate(df10.parse(identExpiredDate));
		}
		custIdentifier.setIdentValidFlag(identValidFlag);
		if (identPeriod != null && !identPeriod.trim().equals("")) {
			custIdentifier.setIdentPeriod(new BigDecimal(identPeriod));
		}
		custIdentifier.setIsOpenAccIdent(isOpenAccIdent);
		custIdentifier.setOpenAccIdentModifiedFlag(openAccIdentModifiedFlag);
		if (identModifiedTime != null && !identModifiedTime.trim().equals("")) {
			custIdentifier.setIdentModifiedTime(new Timestamp(new Long(df19.parse(identModifiedTime).getTime())));
		}
		if (verifyDate != null && !verifyDate.trim().equals("")) {
			custIdentifier.setVerifyDate(df10.parse(verifyDate));
		}
		custIdentifier.setVerifyEmployee(verifyEmployee);
		custIdentifier.setVerifyResult(verifyResult);

		return custIdentifier;
	}

	/**
	 * 更新客户证件信息表
	 *
	 * @param customer
	 */
	public AcrmFCiCustIdentifier updatecustIdentifier(AcrmFCiCustIdentifier custIdentifier, AcrmFCiCustIdentifier oldcustIdentifier) throws Exception {

		if (oldcustIdentifier == null) { return custIdentifier; }

	    //oldcustIdentifier.setIdentId(custIdentifier.getIdentId()==0?oldcustIdentifier.getIdentId():custIdentifier.getIdentId());
		oldcustIdentifier.setCustId(custIdentifier.getCustId() == null ? oldcustIdentifier.getCustId() : custIdentifier.getCustId());
		oldcustIdentifier.setIdentType(custIdentifier.getIdentType() == null ? oldcustIdentifier.getIdentType() : custIdentifier.getIdentType());
		oldcustIdentifier.setIdentNo(custIdentifier.getIdentNo() == null ? oldcustIdentifier.getIdentNo() : custIdentifier.getIdentNo());
		oldcustIdentifier.setIdentCustName(custIdentifier.getIdentCustName() == null ? oldcustIdentifier.getIdentCustName() : custIdentifier
				.getIdentCustName());
		oldcustIdentifier.setIdentDesc(custIdentifier.getIdentDesc() == null ? oldcustIdentifier.getIdentDesc() : custIdentifier.getIdentDesc());
		oldcustIdentifier.setCountryOrRegion(custIdentifier.getCountryOrRegion() == null ? oldcustIdentifier.getCountryOrRegion() : custIdentifier
				.getCountryOrRegion());
		oldcustIdentifier.setIdentOrg(custIdentifier.getIdentOrg() == null ? oldcustIdentifier.getIdentOrg() : custIdentifier.getIdentOrg());
		oldcustIdentifier.setIdentApproveUnit(custIdentifier.getIdentApproveUnit() == null ? oldcustIdentifier.getIdentApproveUnit() : custIdentifier
				.getIdentApproveUnit());
		oldcustIdentifier.setIdentCheckFlag(custIdentifier.getIdentCheckFlag() == null ? oldcustIdentifier.getIdentCheckFlag() : custIdentifier
				.getIdentCheckFlag());
		oldcustIdentifier.setIdentCheckingDate(custIdentifier.getIdentCheckingDate() == null ? oldcustIdentifier.getIdentCheckingDate()
				: custIdentifier.getIdentCheckingDate());
		oldcustIdentifier.setIdentCheckedDate(custIdentifier.getIdentCheckedDate() == null ? oldcustIdentifier.getIdentCheckedDate() : custIdentifier
				.getIdentCheckedDate());
		oldcustIdentifier.setIdentValidPeriod(custIdentifier.getIdentPeriod() == null ? oldcustIdentifier.getIdentPeriod() : custIdentifier
				.getIdentPeriod());
		oldcustIdentifier.setIdentEffectiveDate(custIdentifier.getIdentEffectiveDate() == null ? oldcustIdentifier.getIdentEffectiveDate()
				: custIdentifier.getIdentEffectiveDate());
		oldcustIdentifier.setIdentExpiredDate(custIdentifier.getIdentExpiredDate() == null ? oldcustIdentifier.getIdentExpiredDate() : custIdentifier
				.getIdentExpiredDate());
		oldcustIdentifier.setIdentValidFlag(custIdentifier.getIdentValidFlag() == null ? oldcustIdentifier.getIdentValidFlag() : custIdentifier
				.getIdentValidFlag());
		oldcustIdentifier.setIdentPeriod(custIdentifier.getIdentPeriod() == null ? oldcustIdentifier.getIdentPeriod() : custIdentifier
				.getIdentPeriod());
		oldcustIdentifier.setIsOpenAccIdent(custIdentifier.getIsOpenAccIdent() == null ? oldcustIdentifier.getIsOpenAccIdent() : custIdentifier
				.getIsOpenAccIdent());
		oldcustIdentifier.setOpenAccIdentModifiedFlag(custIdentifier.getOpenAccIdentModifiedFlag() == null ? oldcustIdentifier
				.getOpenAccIdentModifiedFlag() : custIdentifier.getOpenAccIdentModifiedFlag());
		oldcustIdentifier.setIdentModifiedTime(custIdentifier.getIdentModifiedTime() == null ? oldcustIdentifier.getIdentModifiedTime()
				: custIdentifier.getIdentModifiedTime());
		oldcustIdentifier.setVerifyDate(custIdentifier.getVerifyDate() == null ? oldcustIdentifier.getVerifyDate() : custIdentifier.getVerifyDate());
		oldcustIdentifier.setVerifyEmployee(custIdentifier.getVerifyEmployee() == null ? oldcustIdentifier.getVerifyEmployee() : custIdentifier
				.getVerifyEmployee());
		oldcustIdentifier.setVerifyResult(custIdentifier.getVerifyResult() == null ? oldcustIdentifier.getVerifyResult() : custIdentifier
				.getVerifyResult());

		return oldcustIdentifier;
	}

	/**
	 * 增加地址信息表
	 *
	 * @param customer
	 */
	public AcrmFCiAddress addAddress(AcrmFCiCustomer customer, AcrmFCiAddress address, Element body) throws Exception {

		String custId = customer.getCustId();
		String addrId = body.element("addrId").getTextTrim();// 地址id
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
        address.setAddrId(Long.parseLong(addrId));
		address.setCustId(custId);
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

		return address;
	}

	/**
	 * 更新地址信息表
	 *
	 * @param customer
	 */
	public AcrmFCiAddress updateAddress(AcrmFCiAddress address, AcrmFCiAddress oldaddress) throws Exception {

		if (oldaddress == null) { return address; }

		// oldaddress.setAddrId(address.getAddrId()==null?address.getAddrId():oldaddress.getAddrId());
		oldaddress.setCustId(address.getCustId() == null ? oldaddress.getCustId() : address.getCustId());
		oldaddress.setAddrType(address.getAddrType() == null ? oldaddress.getAddrType() : address.getAddrType());
		oldaddress.setAddr(address.getAddr() == null ? oldaddress.getAddr() : address.getAddr());
		oldaddress.setEnAddr(address.getEnAddr() == null ? oldaddress.getEnAddr() : address.getEnAddr());
		oldaddress.setContmethInfo(address.getContmethInfo() == null ? oldaddress.getContmethInfo() : address.getContmethInfo());
		oldaddress.setZipcode(address.getZipcode() == null ? oldaddress.getZipcode() : address.getZipcode());
		oldaddress.setCountryOrRegion(address.getCountryOrRegion() == null ? oldaddress.getCountryOrRegion() : address.getCountryOrRegion());
		oldaddress.setAdminZone(address.getAdminZone() == null ? oldaddress.getAdminZone() : address.getAdminZone());
		oldaddress.setAreaCode(address.getAreaCode() == null ? oldaddress.getAreaCode() : address.getAreaCode());
		oldaddress.setProvinceCode(address.getProvinceCode() == null ? oldaddress.getProvinceCode() : address.getProvinceCode());
		oldaddress.setCityCode(address.getCityCode() == null ? oldaddress.getCityCode() : address.getCityCode());
		oldaddress.setCountyCode(address.getCountyCode() == null ? oldaddress.getCountyCode() : address.getCountyCode());
		oldaddress.setTownCode(address.getTownCode() == null ? oldaddress.getTownCode() : address.getTownCode());
		oldaddress.setTownName(address.getTownName() == null ? oldaddress.getTownName() : address.getTownName());
		oldaddress.setStreetName(address.getStreetName() == null ? oldaddress.getStreetName() : address.getStreetName());
		oldaddress.setVillageNo(address.getVillageNo() == null ? oldaddress.getVillageNo() : address.getVillageNo());
		oldaddress.setVillageName(address.getVillageName() == null ? oldaddress.getVillageName() : address.getVillageName());

		return oldaddress;
	}

	/**
	 * 增加联系信息表
	 *
	 * @param customer
	 */
	public AcrmFCiContmeth addComtmeth(AcrmFCiCustomer customer, AcrmFCiContmeth comtmeth, Element body) throws Exception {

		String custId = customer.getCustId();
        String contmethId= body.element("contmethId").getTextTrim();// 技术主键
		String isPriori = body.element("isPriori").getTextTrim();// 是否首选
		String contmethType = body.element("contmethType").getTextTrim();// 联系方式类型
		String contmethInfo = body.element("contmethInfo").getTextTrim();// 联系方式内容
		String contmethSeq = body.element("contmethSeq").getTextTrim();// 联系顺序号
		String remark = body.element("remark").getTextTrim();// 备注
		String stat = body.element("stat").getTextTrim();// 记录状态

		comtmeth.setCustId(custId);
		comtmeth.setContmethId(Long.parseLong(contmethId));
		comtmeth.setIsPriori(isPriori);
		comtmeth.setContmethType(contmethType);
		comtmeth.setContmethInfo(contmethInfo);
		if (contmethSeq != null && !contmethSeq.trim().equals("")) {
			comtmeth.setContmethSeq(new BigDecimal(contmethSeq));
		}
		comtmeth.setRemark(remark);
		comtmeth.setStat(stat);

		return comtmeth;
	}

	/**
	 * 更新信息表
	 *
	 * @param customer
	 */
	public AcrmFCiContmeth updateComtmeth(AcrmFCiContmeth comtmeth, AcrmFCiContmeth oldcomtmeth) throws Exception {

		if (oldcomtmeth == null) { return comtmeth; }

		// oldcomtmeth.setContmethId(comtmeth.getContmethId()==null?comtmeth.getContmethId():oldcomtmeth.getContmethId());
		oldcomtmeth.setCustId(comtmeth.getCustId() == null ? oldcomtmeth.getCustId() : comtmeth.getCustId());
		oldcomtmeth.setIsPriori(comtmeth.getIsPriori() == null ? oldcomtmeth.getIsPriori() : comtmeth.getIsPriori());
		comtmeth.setContmethType(comtmeth.getContmethType() == null ? oldcomtmeth.getContmethType() : comtmeth.getContmethType());
		oldcomtmeth.setContmethInfo(comtmeth.getContmethInfo() == null ? oldcomtmeth.getContmethInfo() : comtmeth.getContmethInfo());
		oldcomtmeth.setContmethSeq(comtmeth.getContmethSeq() == null ? oldcomtmeth.getContmethSeq() : comtmeth.getContmethSeq());
		oldcomtmeth.setRemark(comtmeth.getRemark() == null ? oldcomtmeth.getRemark() : comtmeth.getRemark());
		oldcomtmeth.setStat(comtmeth.getStat() == null ? oldcomtmeth.getStat() : comtmeth.getStat());

		return oldcomtmeth;
	}
	/**
	 * 新增归属客户经理
	 * @param customer
	 * @param belongMgr
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public OcrmFCiBelongCustmgr addBelongMgr(AcrmFCiCustomer customer, OcrmFCiBelongCustmgr belongMgr, Element body) throws Exception{
		if(body.selectSingleNode("belongManager")==null){
			return belongMgr;
		}
		String custId = customer.getCustId();
		//String belongManagerId = body.element("belongManager").element("belongManagerId").getTextTrim();
		String custManagerNo = body.element("belongManager").element("custManagerNo").getTextTrim();
		if(custManagerNo!=null && !"".equals(custManagerNo)){
			Object obj = queryAdminAuth(custManagerNo);
			AdminAuthAccount adminAuth = new AdminAuthAccount();
			AdminAuthOrg adminOrg = new AdminAuthOrg();
			if(obj!=null){
				adminAuth=(AdminAuthAccount)obj;
			    belongMgr.setMgrName(adminAuth.getUserName()==null?"":adminAuth.getUserName());
			    String orgId=adminAuth.getOrgId();
			    if(orgId!=null && !"".equals(orgId)){
			    	  belongMgr.setInstitution(orgId);
			    	  Object object=queryAdminOrg(orgId);
					  if(object!=null){
						  adminOrg=(AdminAuthOrg)object;
						  belongMgr.setInstitutionName(adminOrg.getOrgName()==null?"":adminOrg.getOrgName());
					  }

			    }

			}
		}
		String MainType = body.element("belongManager").element("mainType").getTextTrim();
		//belongMgr.setId(Long.parseLong(belongManagerId));//技术主键
		belongMgr.setCustId(custId);//客户编号
		belongMgr.setMgrId(custManagerNo);//客户经理编号
		belongMgr.setMainType(MainType);//主协办类型
		return belongMgr;
	}

	/**
	 * 查询系统用户表
	 * @param custManagerNo
	 * @return
	 * @throws Exception
	 */
	public Object queryAdminAuth(String custManagerNo) throws Exception{

			// 获得表名
			String tableName = "AdminAuthAccount";
			// 拼装JQL查询语句
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// 查询表
			jql.append("FROM " + tableName + " a");

			// 查询条件
			jql.append(" WHERE 1=1");
			jql.append(" AND a.accountName =:accountName");
			// 将查询的条件放入到map集合里面
			paramMap.put("accountName", custManagerNo);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}else{
				return null;
			}


	}
	/**
	 * 查询系统机构表
	 * @param custManagerNo
	 * @return
	 * @throws Exception
	 */
	public Object queryAdminOrg(String orgId) throws Exception{

		// 获得表名
		String tableName = "AdminAuthOrg";
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.orgId =:orgId");
		// 将查询的条件放入到map集合里面
		paramMap.put("orgId", orgId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}else{
			return null;
		}


}

	/**
	 * 更新归属客户经理
	 *
	 * @param customer
	 */
	public OcrmFCiBelongCustmgr updateBelongCustMrg(OcrmFCiBelongCustmgr belongCustMgr, OcrmFCiBelongCustmgr oldBelongCustMgr) throws Exception {

		if (oldBelongCustMgr == null) { return belongCustMgr; }
		//oldBelongCustMgr.setId(belongCustMgr.getId()==0?oldBelongCustMgr.getId():belongCustMgr.getId());
		oldBelongCustMgr.setEffectDate(belongCustMgr.getEffectDate()==null?oldBelongCustMgr.getEffectDate():belongCustMgr.getEffectDate());
		oldBelongCustMgr.setAssignDate(belongCustMgr.getAssignDate()==null?oldBelongCustMgr.getAssignDate():belongCustMgr.getAssignDate());
		oldBelongCustMgr.setAssignUser(belongCustMgr.getAssignUser()==null?oldBelongCustMgr.getAssignUser():belongCustMgr.getAssignUser());
		oldBelongCustMgr.setAssignUsername(belongCustMgr.getAssignUsername()==null?oldBelongCustMgr.getAssignUsername():belongCustMgr.getAssignUsername());
		oldBelongCustMgr.setCheckRight(belongCustMgr.getCheckRight()==null?oldBelongCustMgr.getCheckRight():belongCustMgr.getCheckRight());
		oldBelongCustMgr.setCustId(belongCustMgr.getCustId()==null?oldBelongCustMgr.getCustId():belongCustMgr.getCustId());
		oldBelongCustMgr.setInstitution(belongCustMgr.getInstitution()==null?oldBelongCustMgr.getInstitution():belongCustMgr.getInstitution());
		oldBelongCustMgr.setInstitutionName(belongCustMgr.getInstitutionName()==null?oldBelongCustMgr.getInstitutionName():belongCustMgr.getInstitutionName());
		oldBelongCustMgr.setMainType(belongCustMgr.getMainType()==null?oldBelongCustMgr.getMainType():belongCustMgr.getMainType());
		oldBelongCustMgr.setMaintainRight(belongCustMgr.getMaintainRight()==null?oldBelongCustMgr.getMaintainRight():belongCustMgr.getMaintainRight());
		oldBelongCustMgr.setMgrId(belongCustMgr.getMgrId()==null?oldBelongCustMgr.getMgrId():belongCustMgr.getMgrId());

		return oldBelongCustMgr;
	}


	/**
	 * 新增归属机构
	 * @param customer
	 * @param belongMgr
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public OcrmFCiBelongOrg addBelongOrg(AcrmFCiCustomer customer, OcrmFCiBelongOrg belongOrg, Element body) throws Exception{
		if(body.selectSingleNode("belongBranch")==null){
			return belongOrg;
		}
		String custId = customer.getCustId();
		String  belongBranchNo= body.element("belongBranch").element("belongBranchNo").getTextTrim();
		String MainType = body.element("belongBranch").element("mainType").getTextTrim();
		//String belongBranchId = body.element("belongBranch").element("belongBranchId").getTextTrim();
	//	belongOrg.setId(Long.parseLong(belongBranchId));//技术主键
		belongOrg.setCustId(custId);//客户编号
		belongOrg.setInstitutionCode(belongBranchNo);//归属机构代码
		belongOrg.setMainType(MainType);//主协办类型
		return belongOrg;
	}

	/**
	 * 更新归属机构
	 *
	 * @param customer
	 */
	public OcrmFCiBelongOrg updateBelongOrg(OcrmFCiBelongOrg belongOrg, OcrmFCiBelongOrg oldBelongOrg) throws Exception {

		if (oldBelongOrg == null) { return belongOrg; }
		//oldBelongOrg.setId(belongOrg.getId()==0?oldBelongOrg.getId():belongOrg.getId());
		oldBelongOrg.setAssignDate(belongOrg.getAssignDate()==null?oldBelongOrg.getAssignDate():belongOrg.getAssignDate());
	    oldBelongOrg.setAssignUser(belongOrg.getAssignUser()==null?oldBelongOrg.getAssignUser():belongOrg.getAssignUser());
		oldBelongOrg.setAssignUsername(belongOrg.getAssignUsername()==null?oldBelongOrg.getAssignUsername():belongOrg.getAssignUsername());
		oldBelongOrg.setCustId(belongOrg.getCustId()==null?oldBelongOrg.getCustId():belongOrg.getCustId());
		oldBelongOrg.setEtlDate(belongOrg.getEtlDate()==null?oldBelongOrg.getEtlDate():belongOrg.getEtlDate());
		oldBelongOrg.setInstitutionCode(belongOrg.getInstitutionCode()==null?oldBelongOrg.getInstitutionCode():belongOrg.getInstitutionCode());
		oldBelongOrg.setInstitutionName(belongOrg.getInstitutionName()==null?oldBelongOrg.getInstitutionName():belongOrg.getInstitutionName());
		oldBelongOrg.setMainType(belongOrg.getMainType()==null?oldBelongOrg.getMainType():belongOrg.getMainType());

		return oldBelongOrg;
	}
	/**
	 * 新增交叉索引表
	 * @param customer
	 * @param crossIndex
	 * @param body
	 * @return
	 */
	 public AcrmFCiCrossindex addCrossIndex(AcrmFCiCustomer customer,AcrmFCiCrossindex crossIndex,Element body) throws Exception{

		   String custId = customer.getCustId();
		   String crossindexId = body.element("crossindexId").getTextTrim();//技术主键
		   String srcSysNo = body.element("srcSysNo").getTextTrim();//源系统编号
		   String srcSysCustNo = body.element("srcSysCustNo").getTextTrim();//源系统客户编号
		   crossIndex.setCrossindexId(crossindexId);
		   crossIndex.setCustId(custId);
		   crossIndex.setSrcSysCustNo(srcSysCustNo);
		   crossIndex.setSrcSysNo(srcSysNo);
		   return crossIndex;
	   }

   /**
	 * 新增交叉索引表
	 * @param customer
	 * @param crossIndex
	 * @param body
	 * @return
	 */
  public AcrmFCiCrossindex updateCrossIndex(AcrmFCiCrossindex crossIndex,AcrmFCiCrossindex oldcrossIndex) throws Exception{
	  if (oldcrossIndex == null) { return crossIndex; }
	 // oldcrossIndex.setCrossindexId(crossIndex.getCrossindexId()==null?oldcrossIndex.getCrossindexId():crossIndex.getCrossindexId());
	  oldcrossIndex.setCustId(crossIndex.getCustId()==null?oldcrossIndex.getCustId():crossIndex.getCustId());
	  oldcrossIndex.setSrcSysCustNo(crossIndex.getSrcSysCustNo()==null?oldcrossIndex.getSrcSysCustNo():crossIndex.getSrcSysCustNo());
	  oldcrossIndex.setSrcSysNo(crossIndex.getSrcSysNo()==null?oldcrossIndex.getSrcSysNo():crossIndex.getSrcSysNo());
	  return oldcrossIndex;
  }

}

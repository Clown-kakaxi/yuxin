package com.ytec.mdm.service.svc.atomic;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.Error;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.exception.BizException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.biz.AcrmFCiAddress;
import com.ytec.mdm.domain.biz.AcrmFCiCustomer;
import com.ytec.mdm.domain.biz.AcrmFCiOrg;
import com.ytec.mdm.domain.biz.AcrmFCiOrgExecutiveinfo;
import com.ytec.mdm.domain.biz.AcrmFCiPotCusCom;
import com.ytec.mdm.domain.biz.OcrmFCiBelongCustmgr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
public class QueryAllCustVisitInfo implements IEcifBizLogic {

	private static Logger log = LoggerFactory.getLogger(QueryAllCustVisitInfo.class);

	private static String[] ILLEGAL_STRS = { "*", "-", "\\", "\"" };

	// 操作数据库
	private static JPABaseDAO baseDAO;

	public void process(EcifData data) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		Element tranElement = responseEle.addElement("postcuscomList");
		/**
		 * 获取请求报文数据
		 */
		Element body = data.getBodyNode();

		String bodyStr = body.asXML();
		for (String ILLEGAL_STR : ILLEGAL_STRS) {
			if (bodyStr.contains(ILLEGAL_STR)) {
				String msg = String.format("%s(%s)", ErrorCode.ERR_ECIF_INVALID_REQ_PARA.getChDesc(), ILLEGAL_STR);
				log.error(msg);
				data.setStatus(ErrorCode.ERR_ECIF_INVALID_REQ_PARA.getCode(), msg);
			}
		}
		String txCode = body.element("txCode").getTextTrim(); // 交易编码
		String txName = body.element("txName").getTextTrim(); // 交易名称

		String authType = body.element("authType").getTextTrim(); // 权限控制类型
		String authCode = body.element("authCode").getTextTrim(); // 权限控制代码
		String custMgr = body.element("mgrId").getTextTrim(); // 客户经理编号
		String taskType1 = body.element("taskType").getTextTrim();//拜访类型
		String custName = body.element("custName").getTextTrim();//客户名称
		String currentPage = body.element("currentPage").getTextTrim();//当前页
		int taskType=Integer.parseInt(taskType1); // 拜访类型((1、新户拜访 2、旧户拜访))
        int number = Integer.parseInt(currentPage);
		List<AcrmFCiPotCusCom> list=null;//查询结果

		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(custMgr) || StringUtils.isEmpty(taskType1)) {
			String msg = "信息不完整，报文请求节点中txCode,custMgr,taskType1不允许为空";
			log.error(msg);
			data.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
	
			try{
				if(taskType==1){
					list=selectNewCustInfo(custMgr,custName,number);//新户拜访的客户信息
					if(list!=null){
						for(AcrmFCiPotCusCom ac:list){
							Element customerEle = tranElement.addElement("postcuscom");
		
							Element hand = customerEle.addElement("cusId");
							hand.setText(ac.getCusId()==null?"":ac.getCusId());
		
							hand = customerEle.addElement("cusName");
							hand.setText(ac.getCusName()==null?"":ac.getCusName());
		
							hand = customerEle.addElement("cusPhone");
							hand.setText(ac.getCusPhone()==null?"":ac.getCusPhone());
		
							hand = customerEle.addElement("cusAddr");
							hand.setText(ac.getCusAddr()==null?"":ac.getCusAddr());
							
							hand = customerEle.addElement("attenName");
							hand.setText(ac.getAttenName()==null?"":ac.getAttenName());
							
							hand = customerEle.addElement("attenBusi");
							hand.setText(ac.getAttenBusi()==null?"":ac.getAttenBusi());
							
							hand = customerEle.addElement("attenPhone");
							hand.setText(ac.getAttenPhone()==null?"":ac.getAttenPhone());
						}
					}else{
						log.info("客户数据{}不存在","");
						data.setStatus(ErrorCode.WRN_NONE_FOUND);
						data.setSuccess(true);
					}
			    }else if(taskType==0){//旧户
						List<OcrmFCiBelongCustmgr> BelongcustMrgs=selectOldCustInfo(custMgr,custName,number);
						if(BelongcustMrgs!=null){
							for(OcrmFCiBelongCustmgr custMrg:BelongcustMrgs){
								String cusId=custMrg.getCustId();
								AcrmFCiCustomer customer=getCustomer(cusId,"1");
								if(customer!=null){
									Element bodyElement = tranElement.addElement("postcuscom");
									String custId = customer.getCustId();
									bodyElement.addElement("cusId").setText(customer.getCustId()==null?"":customer.getCustId());
									bodyElement.addElement("cusName").setText(customer.getCustName()==null?"":customer.getCustName());;
									AcrmFCiOrg org=getAcrmFCiOrg(custId);
									if(org!=null){
									   bodyElement.addElement("cusPhone").setText(org.getOrgTel()==null?"":org.getOrgTel());
									}else{
									  bodyElement.addElement("cusPhone").setText("");
									}
									AcrmFCiAddress address=getAddress(custId,"09");
									if(address!=null){
										bodyElement.addElement("cusAddr").setText(address.getAddr()==null?"":address.getAddr());
									}else{
										bodyElement.addElement("cusAddr").setText("");
									}
									AcrmFCiOrgExecutiveinfo executiveinfo=getExecutiveinfo(custId,"2");
									if(executiveinfo!=null){
										bodyElement.addElement("attenName").setText(executiveinfo.getLinkmanName()==null?"":executiveinfo.getLinkmanName());;
										bodyElement.addElement("attenBusi").setText(executiveinfo.getWorkPosition()==null?"":executiveinfo.getWorkPosition());;
										bodyElement.addElement("attenPhone").setText(executiveinfo.getOfficeTel()==null?"":executiveinfo.getOfficeTel());;
									}else{
										bodyElement.addElement("attenName").setText("");;
										bodyElement.addElement("attenBusi").setText("");;
										bodyElement.addElement("attenPhone").setText("");;
									}
									
								}
							}
						}else{
							String msg = "未查到任何记录警告";
							log.error("{},{}", msg+"交易编码是："+txCode);
							data.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
							data.setSuccess(true);
						}
					}
			}catch(Exception e){
				String msg = "查询数据失败";
				log.error("{},{}", msg+"交易编码是："+txCode, e);
				data.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
				data.setSuccess(false);
				return;
			}
		   data.setRepNode(responseEle);	
	}

		
		
	
	//根据客户经理查询客户拜访类型为新户拜访的客户信息
	public List selectNewCustInfo(String custMgr,String custName,int currentPage) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "AcrmFCiPotCusCom";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custMgr =:custMgr" );
		jql.append(" AND a.cusName like '%"+custName+"%'");
		paramMap.put("custMgr", custMgr);    //根据实际情况决定custId类型
//		jql.append(" AND rownum>=:indexrow");
//		jql.append(" and rownum<=:lastrow");
        Query query =baseDAO.createQueryWithNameParam(jql.toString(), paramMap);
        query.setFirstResult(((currentPage-1)*50));
        query.setMaxResults(50);
        
		//paramMap.put("?", "%"+custName+"%");
//		paramMap.put("indexrow", ((currentPage-1)*50+1));
//		paramMap.put("lastrow", (currentPage*50));

		List result =null;
		result = query.getResultList();
		//result= baseDAO.findWithNameParm(jql.toString(), paramMap).subList(((currentPage-1)*50+1), (currentPage*50));
		if (result != null && result.size() > 0) {
//			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
//				log.error("错误:{}---->>>>查询记录数:{},最大限制:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
//				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
//			}
			log.debug("SQL:[{}]查询记录数:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return result;
		}
		return null;
	}
	//由客户经理查询所对应的客户id的集合
	public List selectOldCustInfo (String custMgr,String custName,int currentPage )throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "OcrmFCiBelongCustmgr";
		String simpleName1 = "AcrmFCiCustomer";
		// 获得表名
		String tableName = simpleName;
		String tableName1 = simpleName1;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("select a FROM " + tableName + " a ,"+tableName1+" c " );
		//jql.append(" LEFT JOIN "+tableName1+" C ON C.custId = a.custId"); 

		// 查询条件
		jql.append(" WHERE 1=1 and c.custId = a.custId");
		jql.append(" And a.mgrId=:custMgr" );
		jql.append(" AND c.custName like '%"+custName+"%'");
		
		paramMap.put("custMgr", custMgr);    //根据实际情况决定custId类型
		Query query=baseDAO.createQueryWithNameParam(jql.toString(), paramMap);
		query.setFirstResult(((currentPage-1)*50));
	    query.setMaxResults(50);
		List result =null;
		result = query.getResultList();
		//result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
//			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
//				log.error("错误:{}---->>>>查询记录数:{},最大限制:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
//				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
//			}
			log.debug("SQL:[{}]查询记录数:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return result;
		}
		return null;
	}
	//由客户编号查询客户信息
	public AcrmFCiCustomer getCustomer(String custId,String custType) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "AcrmFCiCustomer";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a" );

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" And a.custId=:custId");
		jql.append(" AND a.custType=:custType");
		jql.append(" and a.potentialFlag='0' ");
		paramMap.put("custId", custId);    //根据实际情况决定custId类型
		paramMap.put("custType", custType);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("错误:{}---->>>>查询记录数:{},最大限制:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]查询记录数:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiCustomer) result.get(0);
		}
		return null;
	}
	//由客户编号查询客户电话
	public AcrmFCiOrg getAcrmFCiOrg(String custId) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "AcrmFCiOrg";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a" );

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" And a.custId=:custId" );
		
		paramMap.put("custId", custId);    //根据实际情况决定custId类型
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("错误:{}---->>>>查询记录数:{},最大限制:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]查询记录数:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiOrg) result.get(0);
		}
		return null;
		
	}
	
	//由客户编号查询客户地址信息
	public AcrmFCiAddress getAddress(String custId,String addrType) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "AcrmFCiAddress";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a" );

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" And a.custId=:custId" );
		jql.append(" AND a.addrType=:addrType");
		paramMap.put("custId", custId);    //根据实际情况决定custId类型
		paramMap.put("addrType", addrType);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("错误:{}---->>>>查询记录数:{},最大限制:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]查询记录数:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiAddress) result.get(0);
		}
		return null;
		
	}
	//由客户编号查询客户地址信息
	public AcrmFCiOrgExecutiveinfo getExecutiveinfo(String custId,String linkmanType ) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "AcrmFCiOrgExecutiveinfo";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a" );

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" And a.orgCustId=:custId" );
		jql.append(" AND a.linkmanType=:linkmanType");
		paramMap.put("orgCustId", custId);    //根据实际情况决定custId类型
		paramMap.put("linkmanType", linkmanType);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("错误:{}---->>>>查询记录数:{},最大限制:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]查询记录数:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiOrgExecutiveinfo) result.get(0);
		}
		return null;	
	}

}

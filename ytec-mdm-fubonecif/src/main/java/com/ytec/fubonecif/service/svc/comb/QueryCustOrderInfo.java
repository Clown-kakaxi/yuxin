package com.ytec.fubonecif.service.svc.comb;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Parameter;
import javax.persistence.Query;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.fubonecif.domain.OWzCustomerInfo;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * 查询预约客户信息
 * 
 * @author wx
 * 
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryCustOrderInfo implements IEcifBizLogic {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(QueryCustOrderInfo.class);

	public void process(EcifData ecifData) throws Exception {
		try {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			Element body = ecifData.getBodyNode();// 获取节点
			System.err.println("请求报文内容：【"+ecifData.getPrimalMsg()+"】");
			String txCode = body.element("txCode").getTextTrim();// 获取交易编号
			if (StringUtils.isEmpty(txCode)) {
				String msg = "信息不完整，报文请求节点中txCode不允许为空";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			String flag = body.element("flag").getTextTrim();// 查询方式(1：根据姓名、证件类型、证件号查询，2：直接根据预约号和证件号查询)
			if (StringUtils.isEmpty(flag)) {
				String msg = "信息不完整，报文请求节点中flag不允许为空";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			String identiNo = body.element("identiNo").getTextTrim();// 证件号码
			if (StringUtils.isEmpty(identiNo)) {
				String msg = "信息不完整，报文请求节点中identiNo不允许为空";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			//判断查询方式
			if(flag.equals("1")){//根据姓名、证件类型、证件号查询
				//姓名、证件类型、证件号不能为空
				String custName = body.element("custName").getTextTrim();// 客户姓名
				String identiType = body.element("identiType").getTextTrim();// 证件类型
				if(StringUtils.isEmpty(custName) || StringUtils.isEmpty(identiType)){
					String msg = "信息不完整，根据姓名、证件类型、证件号查询时报文请求节点中custName或identiType都不允许为空";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
					return;
				}
				//根据三证查询结果--CUSTOMERID INTO v_customer_id
				String sql = "SELECT * FROM ("      
						+ " SELECT T.CUSTOMERID,ROW_NUMBER()OVER (ORDER BY REGEXP_SUBSTR(CUSTOMERID,'[[:digit:]]+$') DESC) MM  FROM O_WZ_CUSTOMER_INFO T"
						+ " WHERE trim(CERTTYPE)=trim('"+identiType+"') AND trim(CERTID)=trim('"+identiNo+"') and trim(CUSTOMERNAME)=trim('"+custName+"'))"
						+ " WHERE MM=1";//
				List<Object[]> custInfoList  = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);//
				if(custInfoList == null || custInfoList.size() < 1){
					String msg = "该客户没有进行过预约，没有预约信息";
					log.error(msg);
					ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
					return;
				}
				for(int i = 0; i < custInfoList.size(); i++){
					Object[] os = custInfoList.get(i);
					if(os != null && os.length >= 1){
						String customerId = os[0].toString();
						String jlq_2 = "select t from OWzCustomerInfo t where t.customerid=:customerid";//
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("customerid", customerId);
						List<OWzCustomerInfo> l_custInfo = this.baseDAO.findWithNameParm(jlq_2, params);
						if(l_custInfo == null || l_custInfo.size() != 1){
							String msg = "没有查询到相关的客户信息";
							log.error(msg);
							ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
							return;
						}
						OWzCustomerInfo custInfo = l_custInfo.get(0);
						String msg = "成功获取到客户预约信息";
						log.info(msg);
						ecifData.setStatus(ErrorCode.SUCCESS.getCode(), msg);
						ecifData.setSuccess(true);
						Map map = (Map)JSONObject.toBean(JSONObject.fromObject(JSONSerializer.toJSON(custInfo)), Map.class);
						Set keySet = map.keySet();
						Iterator iterator = keySet.iterator();
						while (iterator.hasNext()) {
							String key = (String)iterator.next();
							String val = (map.get(key)==null?"":map.get(key)).toString();
							ecifData.getWriteModelObj().setResult(key, val);
						}
						return;
					}
				}
			}else if(flag.equals("2")){//根据证件号和预约客户号查询
				String orderNo = body.element("orderNo").getTextTrim();// 预约客户号
				if(StringUtils.isEmpty(orderNo)){
					String msg = "根据证件号和预约客户号查询时orderNo不能为空";
					log.error(msg);
					ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
					return;
				}
				String sql = "SELECT * FROM ("      
						+ " SELECT T.CUSTOMERID,ROW_NUMBER()OVER (ORDER BY REGEXP_SUBSTR(CUSTOMERID,'[[:digit:]]+$') DESC) MM  FROM O_WZ_CUSTOMER_INFO T"
						+ " WHERE trim(SORDERSERIALNO)=trim('"+orderNo+"') AND trim(CERTID)=trim('"+identiNo+"'))"
						+ " WHERE MM=1";//;
				List<Object[]> l_res1 = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);
				if(l_res1 == null || l_res1.size() != 1){
					String msg = "根据证件号和预约客户号查询客户信息失败";
					log.error(msg);
					ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
					return;
				}
				Object[] res1 = l_res1.get(0);
				if(res1 != null && res1.length >= 1){
					String customerId = res1[0].toString();
					String jql = "select t from OWzCustomerInfo t where t.customerid=:customerid";//
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("customerid", customerId);
					List<OWzCustomerInfo> l_custInfo = this.baseDAO.findWithNameParm(jql, params);
					if(l_custInfo == null || l_custInfo.size() != 1){
						String msg = "没有查询到相关的客户信息";
						log.error(msg);
						ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
						return;
					}
					OWzCustomerInfo custInfo  = l_custInfo.get(0);
					String msg = "成功获取到客户预约信息";
					log.info(msg);
					ecifData.setStatus(ErrorCode.SUCCESS.getCode(), msg);
					ecifData.setSuccess(true);
					Map map = (Map)JSONObject.toBean(JSONObject.fromObject(JSONSerializer.toJSON(custInfo)), Map.class);
					Set keySet = map.keySet();
					Iterator iterator = keySet.iterator();
					while (iterator.hasNext()) {
						String key = (String)iterator.next();
						String val = (map.get(key)==null?"":map.get(key)).toString();
						ecifData.getWriteModelObj().setResult(key, val);
					}
					return;
				}else{
					String msg = "没有查询到客户号";
					log.error(msg);
					ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), msg);
			return;
		}
	}
	
}

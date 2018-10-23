package com.ytec.mdm.service.svc.atomic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.biz.AcrmFCiAddress;
import com.ytec.mdm.domain.biz.AcrmFCiOrgExecutiveinfo;
import com.ytec.mdm.domain.biz.AcrmFCiPotCusCom;
import com.ytec.mdm.domain.biz.OcrmFInterviewRecord;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

@Service
public class QuerySpecifiedCust implements IEcifBizLogic {
	// 输出日志
		private static Logger log = LoggerFactory.getLogger(OpenOrgAccount.class);
		// 操作数据库
		private JPABaseDAO baseDAO;	
		private static String acrmFCiPotCusCom="AcrmFCiPotCusCom";
		@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
		public void process(EcifData data) throws Exception {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			Element body = data.getBodyNode(); // 获取节点
			String txCode = body.element("txCode").getTextTrim(); // 获取交易编码
			String custNames = body.element("custName").getTextTrim();//客户名称
			String currentPage = body.element("currentPage").getTextTrim();//当前页
			String custMgr=body.element("custMgr").getTextTrim();//客户经理编号
			int number = Integer.parseInt(currentPage);
			Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
			
			if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(custMgr) || StringUtils.isEmpty(currentPage) ) {
				String msg = "信息不完整，报文请求节点中txCode,custMgr,currentPage不允许为空";
				log.error(msg);
				data.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			
			AcrmFCiAddress address = new AcrmFCiAddress(); // 地址信息表
			AcrmFCiOrgExecutiveinfo OrgExecutiveinfo = new AcrmFCiOrgExecutiveinfo(); 
			try{
				Element bodyElement = responseEle.addElement("postcuscomList");
				List<AcrmFCiPotCusCom> record_list = queryRecord(custMgr, data, acrmFCiPotCusCom,custNames,number);
				if(record_list!=null){
					for(int i=0;i<record_list.size();i++){
						Element recordElement = bodyElement.addElement("postcuscom");
						AcrmFCiPotCusCom potCusCom = record_list.get(i);
						String	custmgrId =potCusCom.getCustMgr();//客户经理编号
						
						String  custId = potCusCom.getCusId();//客户编号
						String	custName =potCusCom.getCusName();//客户名称
						String cusAddr=potCusCom.getCusAddr();
						String attenName=potCusCom.getAttenName();
						String attenBusi=potCusCom.getAttenBusi();
						String attenPhone=potCusCom.getAttenPhone();
						String legalName=potCusCom.getLegalName();
						BigDecimal regCapAmt=potCusCom.getCapAmount();
						String cusResource=potCusCom.getCusResource();
						String reqCurrency=potCusCom.getReqCurrency();
						
						
						recordElement.addElement("cusId").setText(custId==null?"":custId);     
						recordElement.addElement("cusName").setText(custName==null?"":custName); 
						recordElement.addElement("cusAddr").setText(cusAddr==null?"":cusAddr); 
						recordElement.addElement("attenName").setText(attenName==null?"":attenName); 
						recordElement.addElement("attenBusi").setText(attenBusi==null?"":attenBusi); 
						recordElement.addElement("attenPhone").setText(attenPhone==null?"":attenPhone); 
						recordElement.addElement("legalName").setText(legalName==null?"":legalName); 
						recordElement.addElement("regCapAmt").setText(regCapAmt==null?"":regCapAmt.toString()); 
						recordElement.addElement("cusResource").setText(cusResource==null?"":cusResource); 
						recordElement.addElement("reqCurrency").setText(reqCurrency==null?"":reqCurrency); 
					}
				}else{
					String msg = "未查到任何记录警告";
					log.error("{},{}", msg+"交易编码是："+txCode);
					data.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
					data.setSuccess(true);
				}
				
			}catch(Exception e){
				String msg;
				if (e instanceof ParseException) {
					msg = String.format("日期/时间(%s)格式不符合规范,转换错误",
							e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
					data.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
					log.error("{},{}", msg+"交易编码是："+txCode, e);
				} else if (e instanceof NumberFormatException) {
					msg = String.format("数值(%s)格式不符合规范,转换错误",
							e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
					data.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
					log.error("{},{}", msg+"交易编码是："+txCode, e);
				} else {
					msg = "查询数据失败";
					log.error("{},{}", msg+"交易编码是："+txCode, e);
					data.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
				}
				data.setSuccess(false);
				return;
			}
			
			data.setRepNode(responseEle);
		}

		/**
		 * 查询拜访任务信息
		 * @param mgrId
		 * @param data
		 * @param simpleNames
		 * @return
		 * @throws Exception
		 */
		public List queryRecord(String custMgr,EcifData data, String simpleNames,String custName,int currentPage) throws Exception {

			try {
				// 类名
				String simpleName = simpleNames;
				// 获得表名
				String tableName = simpleName;
				// 拼装JQL查询语句
				StringBuffer jql = new StringBuffer();
				Map paramMap = new HashMap();

				// 查询表
				jql.append("FROM " + tableName + " a");

				// 查询条件
				jql.append(" WHERE 1=1");
				jql.append(" AND a.custMgr =:custMgr");
				jql.append(" AND (conclusion='1' or conclusion='2') and custType='1' and state='0'  ");
				jql.append(" AND a.cusName like '%"+custName+"%'");
				paramMap.put("custMgr", custMgr);
				Query query =baseDAO.createQueryWithNameParam(jql.toString(), paramMap);
			    query.setFirstResult((currentPage-1)*50);
			    query.setMaxResults(50);
				List result = null;
				result = query.getResultList();
				if (result != null && result.size() > 0) { 
					 return result; 
				}

			} catch (Exception e) {
				log.error("查询拜访任务信息失败：" + e.getMessage());
				data.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
				data.setSuccess(false);
			}
			return null;
		}
		
		
}

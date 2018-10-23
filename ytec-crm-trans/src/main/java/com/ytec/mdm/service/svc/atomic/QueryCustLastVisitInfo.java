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
//import com.ytec.mdm.domain.biz.OcrmFInterviewRecord;

import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class QueryCustLastVisitInfo implements IEcifBizLogic{

	// 输出日志
	private static Logger log = LoggerFactory.getLogger(OpenOrgAccount.class);
	// 操作数据库
	private JPABaseDAO baseDAO;	
	private static String interviewRecord="OcrmFInterviewRecord";
	private static String addressName = "AcrmFCiAddress";
	SimpleDateFormat df12 = new SimpleDateFormat(MdmConstants.DATE_FORMAMT);
	SimpleDateFormat df10 = new SimpleDateFormat(MdmConstants.TIME_FORMAMT);
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData data) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element body = data.getBodyNode(); // 获取节点
		String txCode = body.element("txCode").getTextTrim(); // 获取交易编码
		String mgrId = body.element("mgrId").getTextTrim();//获取客户经理编号
		String qrystartdate = body.element("qrystartdate").getTextTrim();//获取开始时间
		String qryenddate = body.element("qryenddate").getTextTrim();//获取结束时间
		String custNames = body.element("custName").getTextTrim();//客户名称
		String currentPage = body.element("currentPage").getTextTrim();//当前页
		int number = Integer.parseInt(currentPage);
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		
		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(mgrId) || StringUtils.isEmpty(qrystartdate) ||  StringUtils.isEmpty(qryenddate) ) {
			String msg = "信息不完整，报文请求节点中txCode,mgrId,qrystartdate,qryenddate不允许为空";
			log.error(msg);
			data.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		
		AcrmFCiAddress address = new AcrmFCiAddress(); // 地址信息表
		AcrmFCiOrgExecutiveinfo OrgExecutiveinfo = new AcrmFCiOrgExecutiveinfo(); 
		try{
			Element bodyElement = responseEle.addElement("interviewRecordList");
			Date startDate = df10.parse(qrystartdate);
			Date endDate = df10.parse(qryenddate);
//			qrystartdate=df10.format(startDate);
//			qryenddate=df10.format(endDate);
			List<OcrmFInterviewRecord> record_list = queryRecord(mgrId,startDate,endDate,data, interviewRecord,custNames,number);
			if(record_list!=null){
				for(int i=0;i<record_list.size();i++){
					Element recordElement = bodyElement.addElement("interviewRecord");
					OcrmFInterviewRecord interviewRecord = record_list.get(i);
					String  taskNumber = interviewRecord.getTaskNumber();//拜访任务编号                                      
					String  custId = interviewRecord.getCustId();//客户编号
					String	custName =interviewRecord.getCustName();//客户名称
					String	custmgrId =interviewRecord.getMgrId();//客户经理编号
					String	mgrName = interviewRecord.getMgrName();//客户经理名称
					String	taskType ="";
					BigDecimal bigtaskType= interviewRecord.getTaskType();
					if(bigtaskType!=null){
					   taskType = bigtaskType.toString();//拜访类型((1、新户拜访 0、旧户拜访))
					}
					Date visitDate = interviewRecord.getVisitTime();
					String	visitTime="";
					if(visitDate!=null){
						visitTime = df10.format(visitDate);//拜访日期
					}
					Date createDate = interviewRecord.getCreateTime();
					String	createTime = "";//创建日期
					if(createDate!=null){
						createTime=df12.format(createDate);
					}
					String	remark = interviewRecord.getRemark();//备注
					String bigReviewState = interviewRecord.getReviewState();//拜访任务审批状态
					String	reviewState =""; 
					if(bigReviewState!=null){
						reviewState=bigReviewState.toString();
					}
					String visitType = interviewRecord.getVisitType();
					
					String modelType="";
					BigDecimal bigmodelType= interviewRecord.getModelType();
					if(bigmodelType!=null){
						modelType = bigmodelType.toString();//拜访类型((1、新户拜访 0、旧户拜访))
					}
					
					String	isNew = "";//是否临时新增（Y/N）
					String	custAddr = "";//详细地址
					String	attenName = "";//联系人
					String	attenPhone = "";//联系人电话
					String	isConclusion = "Y";//是否已筛选（Y/N）
					recordElement.addElement("taskNumber").setText(taskNumber==null?"":taskNumber);     
					recordElement.addElement("custId").setText(custId==null?"":custId); 
					recordElement.addElement("custName").setText(custName==null?"":custName); 
					recordElement.addElement("mgrId").setText(custmgrId==null?"":custmgrId); 
					recordElement.addElement("mgrName").setText(mgrName==null?"":mgrName); 
					recordElement.addElement("taskType").setText(taskType==null?"":taskType); 
					recordElement.addElement("visitTime").setText(visitTime==null?"":visitTime); 
					recordElement.addElement("createTime").setText(createTime==null?"":createTime); 
					recordElement.addElement("remark").setText(remark==null?"":remark); 
					recordElement.addElement("reviewState").setText(reviewState==null?"":reviewState); 
					recordElement.addElement("visitType").setText(visitType==null?"":visitType);
					recordElement.addElement("isNew").setText(isNew==null?"":isNew); 	
					recordElement.addElement("modelType").setText(modelType==null?"":modelType); 
					Object addrObj =queryAddress(custId, "09", data, addressName);
					Object newCustomerObj = queryNewCustomer(custId,data);
					if(addrObj!=null){
						address=(AcrmFCiAddress) addrObj;
						custAddr = address.getAddr();
						recordElement.addElement("custAddr").setText(custAddr==null?"":custAddr); 
					}else if(newCustomerObj!=null){
						AcrmFCiPotCusCom postCustCom = (AcrmFCiPotCusCom)newCustomerObj;
						custAddr = postCustCom.getCusAddr();
						recordElement.addElement("custAddr").setText(custAddr==null?"":custAddr); 
					}else{
						recordElement.addElement("custAddr").setText(custAddr);
					}
					Object linkObj = queryEeecutiveinfo(custId, "linkmanType", "2",data,"AcrmFCiOrgExecutiveinfo");
					if(linkObj!=null){
						OrgExecutiveinfo = (AcrmFCiOrgExecutiveinfo) linkObj;
						attenName=OrgExecutiveinfo.getLinkmanName();
						attenPhone=OrgExecutiveinfo.getOfficeTel();
						recordElement.addElement("attenName").setText(attenName==null?"":attenName); 
						recordElement.addElement("attenPhone").setText(attenPhone==null?"":attenPhone); 
					}else if(newCustomerObj!=null){
						AcrmFCiPotCusCom postCustCom = (AcrmFCiPotCusCom)newCustomerObj;
						attenName=postCustCom.getAttenName();
						attenPhone=postCustCom.getAttenPhone();
						
						recordElement.addElement("attenName").setText(attenName==null?"":attenName); 
						recordElement.addElement("attenPhone").setText(attenPhone==null?"":attenPhone); 
					}else{
						recordElement.addElement("attenName").setText(attenName); 
						recordElement.addElement("attenPhone").setText(attenPhone); 
					}
					
					//判断是否是新户,如果是新户,校验是否已筛选
					//add by liuming 20170214
					if(newCustomerObj != null){
						AcrmFCiPotCusCom postCustCom = (AcrmFCiPotCusCom)newCustomerObj;
						String conclusion = postCustCom.getConclusion() ==  null ? "" : postCustCom.getConclusion().toString();
						if(!conclusion.equals("4")){
							isConclusion = "N";
						}
						recordElement.addElement("isConclu").setText(isConclusion);
					}else{
						recordElement.addElement("isConclu").setText(isConclusion);
					}
					
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
	public List queryRecord(String mgrId,Date startDate,Date endDate,EcifData data, String simpleNames,String custName,int currentPage) throws Exception {

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
			jql.append(" AND a.mgrId =:mgrId");
			jql.append(" AND a.visitTime >=:startDate");
			jql.append(" AND a.visitTime <=:endDate");
			jql.append(" AND a.reviewState in(1,4) ");
			jql.append(" AND a.custName like '%"+custName+"%'");
			//BigDecimal big =new BigDecimal(1);
			// 将查询的条件放入到map集合里面
			paramMap.put("mgrId", mgrId);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			//paramMap.put("reviewState", big);
			Query query =baseDAO.createQueryWithNameParam(jql.toString(), paramMap);
		    query.setFirstResult((currentPage-1)*50);
		    query.setMaxResults(50);
			List result = null;
			result = query.getResultList();
			//result = baseDAO.findWithNameParm(jql.toString(), paramMap);
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
			log.error("查询客户地址失败：" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
		}
		return null;
	}
	
	public Object queryEeecutiveinfo(String custId, String str, String addrType,EcifData data, String simpleNames) throws Exception {

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
			jql.append(" AND a.orgCustId =:orgCustId");
			jql.append(" AND a." + str + " =:" + str + "");
			// 将查询的条件放入到map集合里面
			paramMap.put("orgCustId", custId);
			paramMap.put(str, addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("查询联系信息失败：" + e.getMessage());
			data.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			data.setSuccess(false);
		}
		return null;
	}

	
	public Object queryNewCustomer(String custId,EcifData data)  throws Exception {
		
		try {
			// 获得表名
			String tableName = "AcrmFCiPotCusCom";
			// 拼装JQL查询语句
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// 查询表
			jql.append("FROM " + tableName + " a");
			// 查询条件
			jql.append(" WHERE 1=1");
			jql.append(" AND a.cusId =:cusId");
			// 将查询的条件放入到map集合里面
			paramMap.put("cusId", custId);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("查询联系信息失败：" + e.getMessage());
			data.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			data.setSuccess(false);
		}
		return null;
		
	}
}

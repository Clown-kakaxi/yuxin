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

	// �����־
	private static Logger log = LoggerFactory.getLogger(OpenOrgAccount.class);
	// �������ݿ�
	private JPABaseDAO baseDAO;	
	private static String interviewRecord="OcrmFInterviewRecord";
	private static String addressName = "AcrmFCiAddress";
	SimpleDateFormat df12 = new SimpleDateFormat(MdmConstants.DATE_FORMAMT);
	SimpleDateFormat df10 = new SimpleDateFormat(MdmConstants.TIME_FORMAMT);
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData data) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element body = data.getBodyNode(); // ��ȡ�ڵ�
		String txCode = body.element("txCode").getTextTrim(); // ��ȡ���ױ���
		String mgrId = body.element("mgrId").getTextTrim();//��ȡ�ͻ�������
		String qrystartdate = body.element("qrystartdate").getTextTrim();//��ȡ��ʼʱ��
		String qryenddate = body.element("qryenddate").getTextTrim();//��ȡ����ʱ��
		String custNames = body.element("custName").getTextTrim();//�ͻ�����
		String currentPage = body.element("currentPage").getTextTrim();//��ǰҳ
		int number = Integer.parseInt(currentPage);
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		
		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(mgrId) || StringUtils.isEmpty(qrystartdate) ||  StringUtils.isEmpty(qryenddate) ) {
			String msg = "��Ϣ����������������ڵ���txCode,mgrId,qrystartdate,qryenddate������Ϊ��";
			log.error(msg);
			data.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		
		AcrmFCiAddress address = new AcrmFCiAddress(); // ��ַ��Ϣ��
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
					String  taskNumber = interviewRecord.getTaskNumber();//�ݷ�������                                      
					String  custId = interviewRecord.getCustId();//�ͻ����
					String	custName =interviewRecord.getCustName();//�ͻ�����
					String	custmgrId =interviewRecord.getMgrId();//�ͻ�������
					String	mgrName = interviewRecord.getMgrName();//�ͻ���������
					String	taskType ="";
					BigDecimal bigtaskType= interviewRecord.getTaskType();
					if(bigtaskType!=null){
					   taskType = bigtaskType.toString();//�ݷ�����((1���»��ݷ� 0���ɻ��ݷ�))
					}
					Date visitDate = interviewRecord.getVisitTime();
					String	visitTime="";
					if(visitDate!=null){
						visitTime = df10.format(visitDate);//�ݷ�����
					}
					Date createDate = interviewRecord.getCreateTime();
					String	createTime = "";//��������
					if(createDate!=null){
						createTime=df12.format(createDate);
					}
					String	remark = interviewRecord.getRemark();//��ע
					String bigReviewState = interviewRecord.getReviewState();//�ݷ���������״̬
					String	reviewState =""; 
					if(bigReviewState!=null){
						reviewState=bigReviewState.toString();
					}
					String visitType = interviewRecord.getVisitType();
					
					String modelType="";
					BigDecimal bigmodelType= interviewRecord.getModelType();
					if(bigmodelType!=null){
						modelType = bigmodelType.toString();//�ݷ�����((1���»��ݷ� 0���ɻ��ݷ�))
					}
					
					String	isNew = "";//�Ƿ���ʱ������Y/N��
					String	custAddr = "";//��ϸ��ַ
					String	attenName = "";//��ϵ��
					String	attenPhone = "";//��ϵ�˵绰
					String	isConclusion = "Y";//�Ƿ���ɸѡ��Y/N��
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
					
					//�ж��Ƿ����»�,������»�,У���Ƿ���ɸѡ
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
				String msg = "δ�鵽�κμ�¼����";
				log.error("{},{}", msg+"���ױ����ǣ�"+txCode);
				data.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
				data.setSuccess(true);
			}
			
		}catch(Exception e){
			String msg;
			if (e instanceof ParseException) {
				msg = String.format("����/ʱ��(%s)��ʽ�����Ϲ淶,ת������",
						e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
				data.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
				log.error("{},{}", msg+"���ױ����ǣ�"+txCode, e);
			} else if (e instanceof NumberFormatException) {
				msg = String.format("��ֵ(%s)��ʽ�����Ϲ淶,ת������",
						e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
				data.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
				log.error("{},{}", msg+"���ױ����ǣ�"+txCode, e);
			} else {
				msg = "��ѯ����ʧ��";
				log.error("{},{}", msg+"���ױ����ǣ�"+txCode, e);
				data.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
			}
			data.setSuccess(false);
			return;
		}
		
		data.setRepNode(responseEle);
	}

	/**
	 * ��ѯ�ݷ�������Ϣ
	 * @param mgrId
	 * @param data
	 * @param simpleNames
	 * @return
	 * @throws Exception
	 */
	public List queryRecord(String mgrId,Date startDate,Date endDate,EcifData data, String simpleNames,String custName,int currentPage) throws Exception {

		try {
			// ����
			String simpleName = simpleNames;
			// ��ñ���
			String tableName = simpleName;
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map paramMap = new HashMap();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");

			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.mgrId =:mgrId");
			jql.append(" AND a.visitTime >=:startDate");
			jql.append(" AND a.visitTime <=:endDate");
			jql.append(" AND a.reviewState in(1,4) ");
			jql.append(" AND a.custName like '%"+custName+"%'");
			//BigDecimal big =new BigDecimal(1);
			// ����ѯ���������뵽map��������
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
			log.error("��ѯ�ݷ�������Ϣʧ�ܣ�" + e.getMessage());
			data.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			data.setSuccess(false);
		}
		return null;
	}
	
	
	/**
	 * ��ѯ��ַ��Ϣ��
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
			// ����
			String simpleName = simpleNames;
			// ��ñ���
			String tableName = simpleName;
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");

			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.custId =:custId");
			jql.append(" AND a.addrType =:addrType");
			// ����ѯ���������뵽map��������
			paramMap.put("custId", custId);
			paramMap.put("addrType", addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("��ѯ�ͻ���ַʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
		}
		return null;
	}
	
	public Object queryEeecutiveinfo(String custId, String str, String addrType,EcifData data, String simpleNames) throws Exception {

		try {
			// ����
			String simpleName = simpleNames;
			// ��ñ���
			String tableName = simpleName;
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");
			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.orgCustId =:orgCustId");
			jql.append(" AND a." + str + " =:" + str + "");
			// ����ѯ���������뵽map��������
			paramMap.put("orgCustId", custId);
			paramMap.put(str, addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("��ѯ��ϵ��Ϣʧ�ܣ�" + e.getMessage());
			data.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			data.setSuccess(false);
		}
		return null;
	}

	
	public Object queryNewCustomer(String custId,EcifData data)  throws Exception {
		
		try {
			// ��ñ���
			String tableName = "AcrmFCiPotCusCom";
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");
			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.cusId =:cusId");
			// ����ѯ���������뵽map��������
			paramMap.put("cusId", custId);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("��ѯ��ϵ��Ϣʧ�ܣ�" + e.getMessage());
			data.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			data.setSuccess(false);
		}
		return null;
		
	}
}

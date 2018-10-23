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
	// �����־
		private static Logger log = LoggerFactory.getLogger(OpenOrgAccount.class);
		// �������ݿ�
		private JPABaseDAO baseDAO;	
		private static String acrmFCiPotCusCom="AcrmFCiPotCusCom";
		@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
		public void process(EcifData data) throws Exception {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			Element body = data.getBodyNode(); // ��ȡ�ڵ�
			String txCode = body.element("txCode").getTextTrim(); // ��ȡ���ױ���
			String custNames = body.element("custName").getTextTrim();//�ͻ�����
			String currentPage = body.element("currentPage").getTextTrim();//��ǰҳ
			String custMgr=body.element("custMgr").getTextTrim();//�ͻ�������
			int number = Integer.parseInt(currentPage);
			Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
			
			if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(custMgr) || StringUtils.isEmpty(currentPage) ) {
				String msg = "��Ϣ����������������ڵ���txCode,custMgr,currentPage������Ϊ��";
				log.error(msg);
				data.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			
			AcrmFCiAddress address = new AcrmFCiAddress(); // ��ַ��Ϣ��
			AcrmFCiOrgExecutiveinfo OrgExecutiveinfo = new AcrmFCiOrgExecutiveinfo(); 
			try{
				Element bodyElement = responseEle.addElement("postcuscomList");
				List<AcrmFCiPotCusCom> record_list = queryRecord(custMgr, data, acrmFCiPotCusCom,custNames,number);
				if(record_list!=null){
					for(int i=0;i<record_list.size();i++){
						Element recordElement = bodyElement.addElement("postcuscom");
						AcrmFCiPotCusCom potCusCom = record_list.get(i);
						String	custmgrId =potCusCom.getCustMgr();//�ͻ�������
						
						String  custId = potCusCom.getCusId();//�ͻ����
						String	custName =potCusCom.getCusName();//�ͻ�����
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
		public List queryRecord(String custMgr,EcifData data, String simpleNames,String custName,int currentPage) throws Exception {

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
				log.error("��ѯ�ݷ�������Ϣʧ�ܣ�" + e.getMessage());
				data.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
				data.setSuccess(false);
			}
			return null;
		}
		
		
}

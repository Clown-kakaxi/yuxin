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
 * ��ѯԤԼ�ͻ���Ϣ
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
			Element body = ecifData.getBodyNode();// ��ȡ�ڵ�
			System.err.println("���������ݣ���"+ecifData.getPrimalMsg()+"��");
			String txCode = body.element("txCode").getTextTrim();// ��ȡ���ױ��
			if (StringUtils.isEmpty(txCode)) {
				String msg = "��Ϣ����������������ڵ���txCode������Ϊ��";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			String flag = body.element("flag").getTextTrim();// ��ѯ��ʽ(1������������֤�����͡�֤���Ų�ѯ��2��ֱ�Ӹ���ԤԼ�ź�֤���Ų�ѯ)
			if (StringUtils.isEmpty(flag)) {
				String msg = "��Ϣ����������������ڵ���flag������Ϊ��";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			String identiNo = body.element("identiNo").getTextTrim();// ֤������
			if (StringUtils.isEmpty(identiNo)) {
				String msg = "��Ϣ����������������ڵ���identiNo������Ϊ��";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			//�жϲ�ѯ��ʽ
			if(flag.equals("1")){//����������֤�����͡�֤���Ų�ѯ
				//������֤�����͡�֤���Ų���Ϊ��
				String custName = body.element("custName").getTextTrim();// �ͻ�����
				String identiType = body.element("identiType").getTextTrim();// ֤������
				if(StringUtils.isEmpty(custName) || StringUtils.isEmpty(identiType)){
					String msg = "��Ϣ������������������֤�����͡�֤���Ų�ѯʱ��������ڵ���custName��identiType��������Ϊ��";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
					return;
				}
				//������֤��ѯ���--CUSTOMERID INTO v_customer_id
				String sql = "SELECT * FROM ("      
						+ " SELECT T.CUSTOMERID,ROW_NUMBER()OVER (ORDER BY REGEXP_SUBSTR(CUSTOMERID,'[[:digit:]]+$') DESC) MM  FROM O_WZ_CUSTOMER_INFO T"
						+ " WHERE trim(CERTTYPE)=trim('"+identiType+"') AND trim(CERTID)=trim('"+identiNo+"') and trim(CUSTOMERNAME)=trim('"+custName+"'))"
						+ " WHERE MM=1";//
				List<Object[]> custInfoList  = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);//
				if(custInfoList == null || custInfoList.size() < 1){
					String msg = "�ÿͻ�û�н��й�ԤԼ��û��ԤԼ��Ϣ";
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
							String msg = "û�в�ѯ����صĿͻ���Ϣ";
							log.error(msg);
							ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
							return;
						}
						OWzCustomerInfo custInfo = l_custInfo.get(0);
						String msg = "�ɹ���ȡ���ͻ�ԤԼ��Ϣ";
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
			}else if(flag.equals("2")){//����֤���ź�ԤԼ�ͻ��Ų�ѯ
				String orderNo = body.element("orderNo").getTextTrim();// ԤԼ�ͻ���
				if(StringUtils.isEmpty(orderNo)){
					String msg = "����֤���ź�ԤԼ�ͻ��Ų�ѯʱorderNo����Ϊ��";
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
					String msg = "����֤���ź�ԤԼ�ͻ��Ų�ѯ�ͻ���Ϣʧ��";
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
						String msg = "û�в�ѯ����صĿͻ���Ϣ";
						log.error(msg);
						ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
						return;
					}
					OWzCustomerInfo custInfo  = l_custInfo.get(0);
					String msg = "�ɹ���ȡ���ͻ�ԤԼ��Ϣ";
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
					String msg = "û�в�ѯ���ͻ���";
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

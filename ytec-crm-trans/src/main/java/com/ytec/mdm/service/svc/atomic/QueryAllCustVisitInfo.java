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

	// �������ݿ�
	private static JPABaseDAO baseDAO;

	public void process(EcifData data) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		Element tranElement = responseEle.addElement("postcuscomList");
		/**
		 * ��ȡ����������
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
		String txCode = body.element("txCode").getTextTrim(); // ���ױ���
		String txName = body.element("txName").getTextTrim(); // ��������

		String authType = body.element("authType").getTextTrim(); // Ȩ�޿�������
		String authCode = body.element("authCode").getTextTrim(); // Ȩ�޿��ƴ���
		String custMgr = body.element("mgrId").getTextTrim(); // �ͻ�������
		String taskType1 = body.element("taskType").getTextTrim();//�ݷ�����
		String custName = body.element("custName").getTextTrim();//�ͻ�����
		String currentPage = body.element("currentPage").getTextTrim();//��ǰҳ
		int taskType=Integer.parseInt(taskType1); // �ݷ�����((1���»��ݷ� 2���ɻ��ݷ�))
        int number = Integer.parseInt(currentPage);
		List<AcrmFCiPotCusCom> list=null;//��ѯ���

		if (StringUtils.isEmpty(txCode) || StringUtils.isEmpty(custMgr) || StringUtils.isEmpty(taskType1)) {
			String msg = "��Ϣ����������������ڵ���txCode,custMgr,taskType1������Ϊ��";
			log.error(msg);
			data.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
	
			try{
				if(taskType==1){
					list=selectNewCustInfo(custMgr,custName,number);//�»��ݷõĿͻ���Ϣ
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
						log.info("�ͻ�����{}������","");
						data.setStatus(ErrorCode.WRN_NONE_FOUND);
						data.setSuccess(true);
					}
			    }else if(taskType==0){//�ɻ�
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
							String msg = "δ�鵽�κμ�¼����";
							log.error("{},{}", msg+"���ױ����ǣ�"+txCode);
							data.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
							data.setSuccess(true);
						}
					}
			}catch(Exception e){
				String msg = "��ѯ����ʧ��";
				log.error("{},{}", msg+"���ױ����ǣ�"+txCode, e);
				data.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
				data.setSuccess(false);
				return;
			}
		   data.setRepNode(responseEle);	
	}

		
		
	
	//���ݿͻ������ѯ�ͻ��ݷ�����Ϊ�»��ݷõĿͻ���Ϣ
	public List selectNewCustInfo(String custMgr,String custName,int currentPage) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "AcrmFCiPotCusCom";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custMgr =:custMgr" );
		jql.append(" AND a.cusName like '%"+custName+"%'");
		paramMap.put("custMgr", custMgr);    //����ʵ���������custId����
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
//				log.error("����:{}---->>>>��ѯ��¼��:{},�������:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
//				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
//			}
			log.debug("SQL:[{}]��ѯ��¼��:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return result;
		}
		return null;
	}
	//�ɿͻ������ѯ����Ӧ�Ŀͻ�id�ļ���
	public List selectOldCustInfo (String custMgr,String custName,int currentPage )throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "OcrmFCiBelongCustmgr";
		String simpleName1 = "AcrmFCiCustomer";
		// ��ñ���
		String tableName = simpleName;
		String tableName1 = simpleName1;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("select a FROM " + tableName + " a ,"+tableName1+" c " );
		//jql.append(" LEFT JOIN "+tableName1+" C ON C.custId = a.custId"); 

		// ��ѯ����
		jql.append(" WHERE 1=1 and c.custId = a.custId");
		jql.append(" And a.mgrId=:custMgr" );
		jql.append(" AND c.custName like '%"+custName+"%'");
		
		paramMap.put("custMgr", custMgr);    //����ʵ���������custId����
		Query query=baseDAO.createQueryWithNameParam(jql.toString(), paramMap);
		query.setFirstResult(((currentPage-1)*50));
	    query.setMaxResults(50);
		List result =null;
		result = query.getResultList();
		//result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
//			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
//				log.error("����:{}---->>>>��ѯ��¼��:{},�������:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
//				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
//			}
			log.debug("SQL:[{}]��ѯ��¼��:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return result;
		}
		return null;
	}
	//�ɿͻ���Ų�ѯ�ͻ���Ϣ
	public AcrmFCiCustomer getCustomer(String custId,String custType) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "AcrmFCiCustomer";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a" );

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" And a.custId=:custId");
		jql.append(" AND a.custType=:custType");
		jql.append(" and a.potentialFlag='0' ");
		paramMap.put("custId", custId);    //����ʵ���������custId����
		paramMap.put("custType", custType);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("����:{}---->>>>��ѯ��¼��:{},�������:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]��ѯ��¼��:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiCustomer) result.get(0);
		}
		return null;
	}
	//�ɿͻ���Ų�ѯ�ͻ��绰
	public AcrmFCiOrg getAcrmFCiOrg(String custId) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "AcrmFCiOrg";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a" );

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" And a.custId=:custId" );
		
		paramMap.put("custId", custId);    //����ʵ���������custId����
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("����:{}---->>>>��ѯ��¼��:{},�������:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]��ѯ��¼��:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiOrg) result.get(0);
		}
		return null;
		
	}
	
	//�ɿͻ���Ų�ѯ�ͻ���ַ��Ϣ
	public AcrmFCiAddress getAddress(String custId,String addrType) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "AcrmFCiAddress";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a" );

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" And a.custId=:custId" );
		jql.append(" AND a.addrType=:addrType");
		paramMap.put("custId", custId);    //����ʵ���������custId����
		paramMap.put("addrType", addrType);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("����:{}---->>>>��ѯ��¼��:{},�������:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]��ѯ��¼��:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiAddress) result.get(0);
		}
		return null;
		
	}
	//�ɿͻ���Ų�ѯ�ͻ���ַ��Ϣ
	public AcrmFCiOrgExecutiveinfo getExecutiveinfo(String custId,String linkmanType ) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "AcrmFCiOrgExecutiveinfo";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a" );

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" And a.orgCustId=:custId" );
		jql.append(" AND a.linkmanType=:linkmanType");
		paramMap.put("orgCustId", custId);    //����ʵ���������custId����
		paramMap.put("linkmanType", linkmanType);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {
			if (result.size() > MdmConstants.MAX_SELECT_COUNT) {
				log.error("����:{}---->>>>��ѯ��¼��:{},�������:{}", ErrorCode.ERR_ECIF_DATA_MAX.getChDesc(), result.size(), MdmConstants.MAX_SELECT_COUNT);
				throw new Exception(ErrorCode.ERR_ECIF_DATA_MAX.getCode());
			}
			log.debug("SQL:[{}]��ѯ��¼��:{}", jql, result.size());
			log.debug("selectNewCudInfo return: {}", result);
			return (AcrmFCiOrgExecutiveinfo) result.get(0);
		}
		return null;	
	}

}

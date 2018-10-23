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
	
	//�޸������ӿ� ������ż��Ϣ��Ӧ�ı�acrm_f_ci_per_mateinfo
	private static String mateinfoName = "AcrmFCiPerMateinfo";
	

	SimpleDateFormat df10 = new SimpleDateFormat(MdmConstants.DATE_FORMAMT);
	SimpleDateFormat df19 = new SimpleDateFormat(MdmConstants.TIME_FORMAMT);
	List<OcrmFCiCustinfoUphi> ocrmFCiCustinfoUphi = new ArrayList<OcrmFCiCustinfoUphi>();

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData crmData) throws Exception {
		System.out.println("=============================CRM����=================================");
		/**
		 * �ϸ��޶�����/ʱ���ʽ
		 */
		df10.setLenient(false);
		df19.setLenient(false);

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		Element body = crmData.getBodyNode();// ��ȡ�ڵ�
		String txCode = body.element("txCode").getTextTrim();// ��ȡ���ױ���
		String txName = body.element("txName").getTextTrim();// ��ȡ��������
		String authType = body.element("authType").getTextTrim();// ��ȡȨ�޿�������
		String authCode = body.element("authCode").getTextTrim();// ��ȡȨ�޿��ƴ���

		OcrmFCiBelongCustmgr custMgr = new OcrmFCiBelongCustmgr();// //�����ͻ�����

		String custId = null; // ��ȡ�ͻ���
		try {
			custId = body.element("customer").element("custId").getTextTrim();

			Object obj = queryCustMgr(custId);

			if (obj != null) {
				custMgr = (OcrmFCiBelongCustmgr) obj;
				String custBelongMgr = custMgr.getMgrId();
				ProcedureHelper pc = new ProcedureHelper();
				NameUtil getName = new NameUtil();
				String procedureName = getName.GetProcedureName();
				// ���ô洢����
				pc.callProcedureNoReturn(procedureName, new Object[] { custId,
						custBelongMgr });
			}

		} catch (Exception e) {
			log.error("û�л�ȡ�ͻ���" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_ECIF_NULL_ECIFCUSTNO);
			crmData.setSuccess(false);
			return;
		}
		// �ͻ���
		AcrmFCiCustomer customer = new AcrmFCiCustomer();
		customer.setCustId(custId);

		AcrmFCiPerson person = new AcrmFCiPerson(); // ���˿ͻ���
		AcrmFCiAddress address = new AcrmFCiAddress(); // ��ַ��Ϣ��
		AcrmFCiContmeth comtmeth = new AcrmFCiContmeth(); // ��ϵ��Ϣ��
		OcrmFCiBelongCustmgr belongManager = new OcrmFCiBelongCustmgr();// �����ͻ�����
		AcrmFCiCrossindex crossIndex = new AcrmFCiCrossindex();// ����������
		AcrmFCiPerMateinfo mateinfo = new AcrmFCiPerMateinfo(); //��ż��Ϣ��

		Object obj = queryCustomer(custId, crmData, customerName);//��ѯ�ͻ���Ҳ����û�и���֮ǰ��ֵ 

		/**
		 * �жϲ�ѯ�ͻ����Ƿ�ʧ��
		 */
		if (obj != null && obj.equals(FAILED)) {
			return;
		} else {
			/**
			 * CRM�Ѵ��ڸÿͻ�����������Ϣ���Ǹ��¿ͻ���Ϣ(��ֵ������ֵ),�����������ݿ���
			 */
			try {
				if (obj != null) {
					AcrmFCiCustomer oldcustomer = addCustomer(customer, body);//��ֵ  �����ĵ�ֵ���õ�customer��

					AcrmFCiCustomer old_customer = (AcrmFCiCustomer) obj;//��ֵ

					recordUpdateCustomerHis(oldcustomer, old_customer);//��¼�޸���ʷ��

					customer = updateCustomer(oldcustomer, old_customer);//���¾�ֵ

					Object personObj = (AcrmFCiPerson) queryCustomer(//��ֵ
							custId, crmData, personName);
					AcrmFCiPerson person_old=(AcrmFCiPerson)personObj;
					AcrmFCiPerson old_person =(AcrmFCiPerson)personObj;
					person = addPerson(customer, person, body);//��ֵ

					recordUpdatePersonHis(person, old_person);

					AcrmFCiPerson oldperson = updatePerson(person, person_old);
					
					
					//�޸���ż��Ϣ
					AcrmFCiPerMateinfo oldmateinfo = addMateinfo(customer, mateinfo, body);//��ֵ
					
					Object mateinfoObj = (AcrmFCiPerMateinfo) queryMateinfo(//��ֵ
							custId, crmData, mateinfoName);
					
					AcrmFCiPerMateinfo old_mateinfo =(AcrmFCiPerMateinfo)mateinfoObj;
					
					recordUpdateMateinfoHis(oldmateinfo, old_mateinfo);// �Ƚ�

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
									addrType);// �Ƚ�
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
									contmethType);// �Ƚ�
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
					//������ż��Ϣ
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
							recordUpdateMateinfoHis(acrm_mateinfo, oldmateinfo);// �Ƚ�
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
				 * CRM�޸ÿͻ��������ͻ���Ϣ
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
							"����/ʱ��(%s)��ʽ�����Ϲ淶,ת������",
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
							"��ֵ(%s)��ʽ�����Ϲ淶,ת������",
							e.getLocalizedMessage()
							.substring(
									e.getLocalizedMessage()
									.indexOf('"'))
									.replace("\"", ""));
					crmData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(),
							msg);
					log.error("{},{}", msg, e);
				} else {
					msg = "���ݱ���ʧ��";
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
		// ��ñ���
		String tableName = "OcrmFCiBelongCustmgr";
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// ����ѯ���������뵽map��������
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
	 * ���ݿͻ���Ų�ѯ���е�����
	 * 
	 * @param custId
	 * @param crmData
	 * @return
	 * @throws Exception
	 */
	public Object queryCustomer(String custId, EcifData crmData,
			String simpleNames) throws Exception {

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
			// ����ѯ���������뵽map��������
			paramMap.put("custId", custId);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}

	public Object queryCrossIndex(String custId, String srcSysNo,
			EcifData crmData, String simpleNames) throws Exception {

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
			jql.append(" AND a.srcSysNo =:srcSysNo");
			// ����ѯ���������뵽map��������
			paramMap.put("custId", custId);
			paramMap.put("srcSysNo", srcSysNo);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
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
	public Object queryAddress(String custId, String addrType,
			EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}
	
	
	/**
	 * ��ѯ��ż��Ϣ��
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
			//jql.append(" AND a.addrType =:addrType");
			// ����ѯ���������뵽map��������
			paramMap.put("custId", custId);
			//paramMap.put("addrType", addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}
	
	
	

	/**
	 * ��ѯ��ϵ��Ϣ
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
			jql.append(" AND a.contmethType =:contmethType");
			// ����ѯ���������뵽map��������
			paramMap.put("custId", custId);
			paramMap.put("contmethType", contmethType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) {
				return result.get(0);
			}

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}

	/**
	 * ���ӿͻ���Ϣ��
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
	 * �޸Ŀͻ���Ϣ��
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

	public void recordUpdateCustomerHis(AcrmFCiCustomer customer,//��ֵĬ�ϲ�����null
			AcrmFCiCustomer oldcustomer) {
		// OcrmFCiCustinfoUphi his=new OcrmFCiCustinfoUphi();
		if((customer.getCustName() ==null || "".equals(customer.getCustName())) && (oldcustomer.getCustName() ==null || "".equals(oldcustomer.getCustName()))){
		}
		//else if (customer.getCustName() !=null  && !customer.getCustName().equals(oldcustomer.getCustName())) {
		else if(customer.getCustName() ==null ){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ͻ�����', '"+oldcustomer.getCustName()+"', '"+customer.getCustName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CUST_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!customer.getCustName().equals(oldcustomer.getCustName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldcustomer.getCustId());
			//			his.setUpdateItem("�ͻ�����");
			//			his.setUpdateItemEn("CUST_NAME");
			//			his.setUpdateTable("ACRM_F_CI_CUSTOMER");
			//			his.setUpdateBeCont(oldcustomer.getCustName());
			//			his.setUpdateAfCont(customer.getCustName());
			//			his.setUpdateUser(oldcustomer.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ͻ�����', '"+oldcustomer.getCustName()+"', '"+customer.getCustName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CUST_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((customer.getShortName() ==null || "".equals(customer.getShortName())) && (oldcustomer.getShortName() ==null || "".equals(oldcustomer.getShortName()))){
		}
		//else if ( customer.getShortName() !=null && !customer.getShortName().equals(oldcustomer.getShortName())) {
		else if(customer.getShortName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ͻ����', '"+oldcustomer.getShortName()+"', '"+customer.getShortName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'SHORT_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);

		}else if(!customer.getShortName().equals(oldcustomer.getShortName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldcustomer.getCustId());
			//			his.setUpdateItem("�ͻ����");
			//			his.setUpdateItemEn("SHORT_NAME");
			//			his.setUpdateTable("ACRM_F_CI_CUSTOMER");
			//			his.setUpdateBeCont(oldcustomer.getShortName());
			//			his.setUpdateAfCont(customer.getShortName());
			//			his.setUpdateUser(oldcustomer.getCustId());
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ͻ����', '"+oldcustomer.getShortName()+"', '"+customer.getShortName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'SHORT_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((customer.getEnName() ==null || "".equals(customer.getEnName())) && (oldcustomer.getEnName() ==null || "".equals(oldcustomer.getEnName()))){
		}
		//else if (customer.getEnName() !=null && !customer.getEnName().equals(oldcustomer.getEnName())) {
		else if(customer.getEnName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, 'Ӣ������', '"+oldcustomer.getEnName()+"', '"+customer.getEnName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'EN_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!customer.getEnName().equals(oldcustomer.getEnName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldcustomer.getCustId());
			//			his.setUpdateItem("Ӣ������");
			//			his.setUpdateItemEn("EN_NAME");
			//			his.setUpdateTable("ACRM_F_CI_CUSTOMER");
			//			his.setUpdateBeCont(oldcustomer.getEnName());
			//			his.setUpdateAfCont(customer.getEnName());
			//			his.setUpdateUser(oldcustomer.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, 'Ӣ������', '"+oldcustomer.getEnName()+"', '"+customer.getEnName()+"', '"+oldcustomer.getCustId()+"', '"+oldcustomer.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'EN_NAME', 'ACRM_F_CI_CUSTOMER', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
	}

	public void recordUpdatePersonHis(AcrmFCiPerson person,
			AcrmFCiPerson oldperson) {
		// OcrmFCiCustinfoUphi his=new OcrmFCiCustinfoUphi();
		if(oldperson == null){
			if(person.getPinyinName() != null && !"".equals(person.getPinyinName())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, 'ƴ������', null, '"+person.getPinyinName()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);	
			}
			if(person.getNickName() != null && !"".equals(person.getNickName())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '�ͻ��ǳ�', null, '"+person.getNickName()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getCitizenship() != null && !"".equals(person.getCitizenship())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '����', null, '"+person.getCitizenship()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getGender() != null && !"".equals(person.getGender())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '�Ա�', null, '"+person.getGender()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getBirthday() != null && !"".equals(person.getBirthday())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '��������', null, '"+person.getBirthday()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'BIRTHDAY', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if(person.getUnitFex() != null && !"".equals(person.getUnitFex())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '�������', null, '"+person.getUnitFex()+"', '"+person.getCustId()+"', '"+person.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'UNIT_FEX', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
		}
		if((person.getPinyinName() ==null || "".equals(person.getPinyinName())) && (oldperson.getPinyinName() ==null || "".equals(oldperson.getPinyinName()))){
		}
		//else if ( person.getPinyinName() !=null && !person.getPinyinName().equals(oldperson.getPinyinName())) {
		else if(person.getPinyinName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, 'ƴ������', '"+oldperson.getPinyinName()+"', '"+person.getPinyinName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getPinyinName().equals(oldperson.getPinyinName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("ƴ������");
			//			his.setUpdateItemEn("PINYIN_NAME");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getPinyinName());
			//			his.setUpdateAfCont(person.getPinyinName());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, 'ƴ������', '"+oldperson.getPinyinName()+"', '"+person.getPinyinName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getNickName() ==null || "".equals(person.getNickName())) && (oldperson.getNickName() ==null || "".equals(oldperson.getNickName()))){
		}
		//else if (person.getNickName() !=null && !person.getNickName().equals(oldperson.getNickName())) {
		else if(person.getNickName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ͻ��ǳ�', '"+oldperson.getNickName()+"', '"+person.getNickName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getNickName().equals(oldperson.getNickName())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("�ͻ��ǳ�");
			//			his.setUpdateItemEn("NICK_NAME");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getNickName());
			//			his.setUpdateAfCont(person.getNickName());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ͻ��ǳ�', '"+oldperson.getNickName()+"', '"+person.getNickName()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getCitizenship() ==null || "".equals(person.getCitizenship())) && (oldperson.getCitizenship() ==null || "".equals(oldperson.getCitizenship()))){
		}
		//else if ( person.getCitizenship() !=null && !person.getCitizenship().equals(oldperson.getCitizenship())) {
		else if( person.getCitizenship() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '����', '"+oldperson.getCitizenship()+"', '"+person.getCitizenship()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getCitizenship().equals(oldperson.getCitizenship())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("����");
			//			his.setUpdateItemEn("CITIZENSHIP");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getCitizenship());
			//			his.setUpdateAfCont(person.getCitizenship());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '����', '"+oldperson.getCitizenship()+"', '"+person.getCitizenship()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getGender() ==null || "".equals(person.getGender())) && (oldperson.getGender() ==null || "".equals(oldperson.getGender()))){
		}
		//else if ( person.getGender() !=null && !person.getGender().equals(oldperson.getGender())) {
		else if(person.getGender() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�Ա�', '"+oldperson.getGender()+"', '"+person.getGender()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getGender().equals(oldperson.getGender())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("�Ա�");
			//			his.setUpdateItemEn("GENDER");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getGender());
			//			his.setUpdateAfCont(person.getGender());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�Ա�', '"+oldperson.getGender()+"', '"+person.getGender()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getBirthday() ==null || "".equals(person.getBirthday())) && (oldperson.getBirthday() ==null || "".equals(oldperson.getBirthday()))){
		}
		//else if ( person.getBirthday() !=null && !person.getBirthday().equals(oldperson.getBirthday())) {
		else if( person.getBirthday() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��������', '"+oldperson.getBirthday()+"', '"+person.getBirthday()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'BIRTHDAY', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getBirthday().equals(oldperson.getBirthday())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("��������");
			//			his.setUpdateItemEn("BIRTHDAY");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(df10.format(oldperson.getBirthday()));
			//			his.setUpdateAfCont(df10.format(person.getBirthday()));
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��������', '"+oldperson.getBirthday()+"', '"+person.getBirthday()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'BIRTHDAY', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((person.getUnitFex() ==null || "".equals(person.getUnitFex())) && (oldperson.getUnitFex() ==null || "".equals(oldperson.getUnitFex()))){
		}
		//else if ( person.getUnitFex() !=null && !person.getUnitFex().equals(oldperson.getUnitFex())) {
		else if(person.getUnitFex() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�������', '"+oldperson.getUnitFex()+"', '"+person.getUnitFex()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'UNIT_FEX', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!person.getUnitFex().equals(oldperson.getUnitFex())){
			//			OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
			//			his.setCustId(oldperson.getCustId());
			//			his.setUpdateItem("�������");
			//			his.setUpdateItemEn("UNIT_FEX");
			//			his.setUpdateTable("ACRM_F_CI_PERSON");
			//			his.setUpdateBeCont(oldperson.getUnitFex());
			//			his.setUpdateAfCont(person.getUnitFex());
			//			his.setUpdateUser(oldperson.getCustId());
			//			his.setUpdateDate(new Date());
			//			his.setApprFlag("X");
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�������', '"+oldperson.getUnitFex()+"', '"+person.getUnitFex()+"', '"+oldperson.getCustId()+"', '"+oldperson.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'UNIT_FEX', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}

	}

	public void recordUpdateAddressHis(AcrmFCiAddress address,
			AcrmFCiAddress oldaddress, String addrType) {
		if(oldaddress ==null ){
			if("01".equals(addrType) && address.getAddr() != null && !"".equals(address.getAddr())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '�ʼĵ�ַ', null, '"+address.getAddr()+"', '"+address.getCustId()+"', '"+address.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("04".equals(addrType) && address.getAddr() != null && !"".equals(address.getAddr())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '��ס��ַ', null, '"+address.getAddr()+"', '"+address.getCustId()+"', '"+address.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
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
							+ "(sysdate, '�ʼĵ�ַ', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getAddr().equals(oldaddress.getAddr())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldaddress.getCustId());
					//				his.setUpdateItem("�ʼĵ�ַ");//��ϵ��ַ
					//				his.setUpdateItemEn("ADDR");
					//				his.setUpdateTable("ACRM_F_CI_ADDRESS");
					//				his.setUpdateBeCont(oldaddress.getAddr());
					//				his.setUpdateAfCont(address.getAddr());
					//				his.setUpdateUser(oldaddress.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '�ʼĵ�ַ', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				if((address.getZipcode() == null || "".equals(address.getZipcode())) && (oldaddress.getZipcode() == null || "".equals(oldaddress.getZipcode()))){
				}
				//else if (address.getZipcode() != null && !address.getZipcode().equals(oldaddress.getZipcode())) {
				else if(address.getZipcode() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '�ʼĵ�ַ��������', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getZipcode().equals(oldaddress.getZipcode())){
					//					OcrmFCiCustinfoUphi hi = new OcrmFCiCustinfoUphi();
					//					hi.setCustId(oldaddress.getCustId());
					//					hi.setUpdateItem("��������");
					//					hi.setUpdateItemEn("ZIPCODE");
					//					hi.setUpdateTable("ACRM_F_CI_ADDRESS");
					//					hi.setUpdateBeCont(oldaddress.getZipcode());
					//					hi.setUpdateAfCont(address.getZipcode());
					//					hi.setUpdateUser(oldaddress.getCustId());
					//					hi.setUpdateDate(new Date());
					//					hi.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '�ʼĵ�ַ��������', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("04".equals(addrType)){
				if((address.getAddr() == null || "".equals(address.getAddr())) && (oldaddress.getAddr() == null || "".equals(oldaddress.getAddr()))){	
				}
				//else if( address.getAddr() != null && !address.getAddr().equals(oldaddress.getAddr())) {
				else if(address.getAddr() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ס��ַ', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getAddr().equals(oldaddress.getAddr())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldaddress.getCustId());
					//				his.setUpdateItem("��ס��ַ");//��ͥ��ַ
					//				his.setUpdateItemEn("ADDR");
					//				his.setUpdateTable("ACRM_F_CI_ADDRESS");
					//				his.setUpdateBeCont(oldaddress.getAddr());
					//				his.setUpdateAfCont(address.getAddr());
					//				his.setUpdateUser(oldaddress.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ס��ַ', '"+oldaddress.getAddr()+"', '"+address.getAddr()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ADDR', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				if((address.getZipcode() == null || "".equals(address.getZipcode())) && (oldaddress.getZipcode() == null || "".equals(oldaddress.getZipcode()))){
				}
				//else if( address.getZipcode()!= null && !address.getZipcode().equals(oldaddress.getZipcode())) {
				else if(address.getZipcode() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ס��ַ��������', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!address.getZipcode().equals(oldaddress.getZipcode())){
					//					OcrmFCiCustinfoUphi hi = new OcrmFCiCustinfoUphi();
					//					hi.setCustId(oldaddress.getCustId());
					//					hi.setUpdateItem("��������");
					//					hi.setUpdateItemEn("ZIPCODE");
					//					hi.setUpdateTable("ACRM_F_CI_ADDRESS");
					//					hi.setUpdateBeCont(oldaddress.getZipcode());
					//					hi.setUpdateAfCont(address.getZipcode());
					//					hi.setUpdateUser(oldaddress.getCustId());
					//					hi.setUpdateDate(new Date());
					//					hi.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ס��ַ��������', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
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
	//			his.setUpdateItem("��������");
	//			his.setUpdateItemEn("ZIPCODE");
	//			his.setUpdateTable("ACRM_F_CI_ADDRESS");
	//			his.setUpdateBeCont(oldaddress.getZipcode());
	//			his.setUpdateAfCont(address.getZipcode());
	//			his.setUpdateUser(oldaddress.getCustId());
	//			his.setUpdateDate(new Date());
	//			his.setApprFlag("X");
	//			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
	//					+ "(sysdate, '��������', '"+oldaddress.getZipcode()+"', '"+address.getZipcode()+"', '"+oldaddress.getCustId()+"', '"+oldaddress.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'ZIPCODE', 'ACRM_F_CI_ADDRESS', null, null, null, 'X', null, null, null)";
	//			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
	//
	//		}
	
	
	//��¼��ż������ʷ��Ϣ
	public void recordUpdateMateinfoHis(AcrmFCiPerMateinfo mateinfo,
			AcrmFCiPerMateinfo oldmateinfo) {
		if(oldmateinfo == null){
			//��ż�ͻ���� CUST_ID_MATE
			//��ż���� MATE_NAME
			//��ͥ�绰 HOME_TEL
			//�ֻ�����  MOBILE
			if(mateinfo.getCustIdMate() != null && !"".equals(mateinfo.getCustIdMate())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '��ż�ͻ����', null, '"+mateinfo.getCustIdMate()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);	
			}
			//��ż���� MATE_NAME
			if(mateinfo.getMateName() != null && !"".equals(mateinfo.getMateName())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '��ż����', null, '"+mateinfo.getMateName()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			//��ͥ�绰 HOME_TEL
			if(mateinfo.getHomeTel() != null && !"".equals(mateinfo.getHomeTel())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '��ͥ�绰', null, '"+mateinfo.getHomeTel()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			//�ֻ�����  MOBILE
			if(mateinfo.getMobile() != null && !"".equals(mateinfo.getMobile())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '�ֻ�����', null, '"+mateinfo.getMobile()+"', '"+mateinfo.getCustId()+"', '"+mateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
		}
		if((mateinfo.getCustIdMate() ==null || "".equals(mateinfo.getCustIdMate())) && (oldmateinfo.getCustIdMate() ==null || "".equals(oldmateinfo.getCustIdMate()))){
		}
		else if(mateinfo.getCustIdMate() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��ż�ͻ����', '"+oldmateinfo.getCustIdMate()+"', '"+mateinfo.getCustIdMate()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getCustIdMate().equals(oldmateinfo.getCustIdMate())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��ż�ͻ����', '"+oldmateinfo.getCustIdMate()+"', '"+mateinfo.getCustIdMate()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'PINYIN_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((mateinfo.getMateName() ==null || "".equals(mateinfo.getMateName())) && (oldmateinfo.getMateName() ==null || "".equals(oldmateinfo.getMateName()))){
		}
		else if(mateinfo.getMateName() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��ż���� ', '"+oldmateinfo.getMateName()+"', '"+mateinfo.getMateName()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getMateName().equals(oldmateinfo.getMateName())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��ż����', '"+oldmateinfo.getMateName()+"', '"+mateinfo.getMateName()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'NICK_NAME', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((mateinfo.getHomeTel() ==null || "".equals(mateinfo.getHomeTel())) && (oldmateinfo.getHomeTel() ==null || "".equals(oldmateinfo.getHomeTel()))){
		}
		else if( mateinfo.getHomeTel() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��ͥ�绰', '"+oldmateinfo.getHomeTel()+"', '"+mateinfo.getHomeTel()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getHomeTel().equals(oldmateinfo.getHomeTel())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '��ͥ�绰', '"+oldmateinfo.getHomeTel()+"', '"+mateinfo.getHomeTel()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CITIZENSHIP', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if((mateinfo.getMobile() ==null || "".equals(mateinfo.getMobile())) && (oldmateinfo.getMobile() ==null || "".equals(oldmateinfo.getMobile()))){
		}
		else if(mateinfo.getMobile() ==null){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ֻ�����', '"+oldmateinfo.getMobile()+"', '"+mateinfo.getMobile()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}else if(!mateinfo.getMobile().equals(oldmateinfo.getMobile())){
			String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
					+ "(sysdate, '�ֻ�����', '"+oldmateinfo.getMobile()+"', '"+mateinfo.getMobile()+"', '"+oldmateinfo.getCustId()+"', '"+oldmateinfo.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'GENDER', 'ACRM_F_CI_PERSON', null, null, null, 'X', null, null, null)";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
	}
	
	

	public void recordUpdateContmethHis(AcrmFCiContmeth contmeth,
			AcrmFCiContmeth oldcontmeth, String contmethType) {
		if(oldcontmeth == null){
			if("2031".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '�칫�绰', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("2041".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '��ͥ�绰', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);

			}
			if("209".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '�����ֻ�����', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("102".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, 'ҵ���ֻ�����', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("500".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, '��ϵ����', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if("501".equals(contmethType) && contmeth.getContmethInfo() != null && !"".equals(contmeth.getContmethInfo())){
				String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
						+ "(sysdate, 'ҵ������', null, '"+contmeth.getContmethInfo()+"', '"+contmeth.getCustId()+"', '"+contmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
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
							+ "(sysdate, '�칫�绰', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("�칫�绰");
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '�칫�绰', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("2041".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if( contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if(contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ͥ�绰', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("��ͥ�绰");
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ͥ�绰', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("209".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if( contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if( contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '�����ֻ�����', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("�����ֻ�����");//�ƶ��绰1
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '�����ֻ�����', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("102".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if(contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if(contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, 'ҵ���ֻ�����', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("ҵ���ֻ�����");//�ֻ��绰1
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, 'ҵ���ֻ�����', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("500".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if(contmeth.getContmethInfo()!= null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if(contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ϵ����', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("�����ʼ���ַ");//��ϵ����
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, '��ϵ����', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if ("501".equals(contmethType)){
				if((contmeth.getContmethInfo() == null || "".equals(contmeth.getContmethInfo())) && (oldcontmeth.getContmethInfo() == null || "".equals(oldcontmeth.getContmethInfo()))){	
				}
				//else if(contmeth.getContmethInfo() != null && !contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())) {
				else if (contmeth.getContmethInfo() == null){
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, 'ҵ������', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}else if(!contmeth.getContmethInfo().equals(oldcontmeth.getContmethInfo())){
					//				OcrmFCiCustinfoUphi his = new OcrmFCiCustinfoUphi();
					//				his.setCustId(oldcontmeth.getCustId());
					//				his.setUpdateItem("���Ӷ��˵�����");//ҵ������
					//				his.setUpdateItemEn("CONTMETH_INFO");
					//				his.setUpdateTable("ACRM_F_CI_CONTMETH");
					//				his.setUpdateBeCont(oldcontmeth.getContmethInfo());
					//				his.setUpdateAfCont(contmeth.getContmethInfo());
					//				his.setUpdateUser(oldcontmeth.getCustId());
					//				his.setUpdateDate(new Date());
					//				his.setApprFlag("X");
					String sql = "insert into OCRM_F_CI_CUSTINFO_UPHIS (UPDATE_DATE, UPDATE_ITEM, UPDATE_BE_CONT, UPDATE_AF_CONT, UPDATE_USER, CUST_ID, CUST_NAME, UP_ID, UPDATE_FLAG, UPDATE_ITEM_EN, UPDATE_TABLE, UPDATE_TABLE_ID, UPDATE_AF_CONT_VIEW, FIELD_TYPE, APPR_FLAG, APPR_USER, APPR_DATE, UPDATE_BE_CONT_VIEW) values"
							+ "(sysdate, 'ҵ������', '"+oldcontmeth.getContmethInfo()+"', '"+contmeth.getContmethInfo()+"', '"+oldcontmeth.getCustId()+"', '"+oldcontmeth.getCustId()+"', null, ID_SEQUENCE.NEXTVAL, null, 'CONTMETH_INFO', 'ACRM_F_CI_CONTMETH', null, null, null, 'X', null, null, null)";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
		}
	}

	/**
	 * ���Ӹ��˿ͻ���
	 * 
	 * @param customer
	 */
	public AcrmFCiPerson addPerson(AcrmFCiCustomer customer,
			AcrmFCiPerson person, Element body) throws Exception {
		String custId = customer.getCustId();

		String perCustType = body.element("person").element("perCustType")
				.getTextTrim();// ���˿ͻ�����
		String jointCustType = body.element("person").element("jointCustType")
				.getTextTrim();// ����������
		String orgSubType = body.element("person").element("orgSubType")
				.getTextTrim();// ��ó������
		//		String ifOrgSubType = body.element("person").element("ifOrgSubType")
		//				.getTextTrim();// �Ƿ���ó����־
		String surName = body.element("person").element("surName")
				.getTextTrim();// �ͻ�����
		String personalName = body.element("person").element("personalName")
				.getTextTrim();// �ͻ�����
		String pinyinName = body.element("person").element("pinyinName")
				.getTextTrim();// ƴ������
		String pinyinAbbr = body.element("person").element("pinyinAbbr")
				.getTextTrim();// ƴ����д
		String personTitle = body.element("person").element("personTitle")
				.getTextTrim();// �ͻ���ν
		String areaCode = body.element("person").element("areaCode")
				.getTextTrim();// ������������
		String nickName = body.element("person").element("nickName")
				.getTextTrim();// �ͻ��ǳ�
		String usedName = body.element("person").element("usedName")
				.getTextTrim();// ������
		String gender = body.element("person").element("gender").getTextTrim();// �Ա�
		String birthday = body.element("person").element("birthday")
				.getTextTrim();// ��������
		String birthlocale = body.element("person").element("birthlocale")
				.getTextTrim();// �����ص�
		String citizenship = body.element("person").element("citizenship")
				.getTextTrim();// ����
		String nationality = body.element("person").element("nationality")
				.getTextTrim();// ����
		String nativeplace = body.element("person").element("nativeplace")
				.getTextTrim();// ����
		String household = body.element("person").element("household")
				.getTextTrim();// ��������
		String hukouPlace = body.element("person").element("hukouPlace")
				.getTextTrim();// �������ڵ�
		String marriage = body.element("person").element("marriage")
				.getTextTrim();// ����״��
		String residence = body.element("person").element("residence")
				.getTextTrim();// ��ס״��
		String health = body.element("person").element("health").getTextTrim();// ����״��
		String religiousBelief = body.element("person")
				.element("religiousBelief").getTextTrim();// �ڽ�����
		String politicalFace = body.element("person").element("politicalFace")
				.getTextTrim();// ������ò
		String mobilePhone = body.element("person").element("mobilePhone")
				.getTextTrim();// �ֻ�����
		String email = body.element("person").element("email").getTextTrim();// �ʼ���ַ
		String homepage = body.element("person").element("homepage")
				.getTextTrim();// ��ҳ
		String weibo = body.element("person").element("weibo").getTextTrim();// ΢��
		String weixin = body.element("person").element("weixin").getTextTrim();// ΢��
		String qq = body.element("person").element("qq").getTextTrim();// QQ
		String starSign = body.element("person").element("starSign")
				.getTextTrim();// ����
		String homeAddr = body.element("person").element("homeAddr")
				.getTextTrim();// סլ��ַ
		String homeZipcode = body.element("person").element("homeZipcode")
				.getTextTrim();// סլ�ʱ�
		String homeTel = body.element("person").element("homeTel")
				.getTextTrim();// סլ�绰
		String highestSchooling = body.element("person")
				.element("highestSchooling").getTextTrim();// ���ѧ��
		String highestDegree = body.element("person").element("highestDegree")
				.getTextTrim();// ���ѧλ
		String graduateSchool = body.element("person")
				.element("graduateSchool").getTextTrim();// ��ҵѧУ
		String major = body.element("person").element("major").getTextTrim();// ��ѧרҵ
		String graduationDate = body.element("person")
				.element("graduationDate").getTextTrim();// ��ҵʱ��
		String careerStat = body.element("person").element("careerStat")
				.getTextTrim();// ְҵ״��
		String careerType = body.element("person").element("careerType")
				.getTextTrim();// ְҵ
		String profession = body.element("person").element("profession")
				.getTextTrim();// ������ҵ
		String unitName = body.element("person").element("unitName")
				.getTextTrim();// ��λ����
		String unitChar = body.element("person").element("unitChar")
				.getTextTrim();// ��λ����
		String unitAddr = body.element("person").element("unitAddr")
				.getTextTrim();// ��λ��ַ
		String unitZipcode = body.element("person").element("unitZipcode")
				.getTextTrim();// ��λ�ʱ�
		String unitTel = body.element("person").element("unitTel")
				.getTextTrim();// ��λ�绰
		String unitFex = body.element("person").element("unitFex")
				.getTextTrim();// �������
		String postAddr = body.element("person").element("postAddr")
				.getTextTrim();// ͨѶ��ַ
		String postZipcode = body.element("person").element("postZipcode")
				.getTextTrim();// ͨѶ����
		String postPhone = body.element("person").element("postPhone")
				.getTextTrim();// ��ϵ�绰
		String adminLevel = body.element("person").element("adminLevel")
				.getTextTrim();// ��������
		String cntName = body.element("person").element("cntName")
				.getTextTrim();// ��λ��ϵ��
		String duty = body.element("person").element("duty").getTextTrim();// ְ��
		String workResult = body.element("person").element("workResult")
				.getTextTrim();// ҵ������
		String careerStartDate = body.element("person")
				.element("careerStartDate").getTextTrim();// �μӹ���ʱ��
		String annualIncomeScope = body.element("person")
				.element("annualIncomeScope").getTextTrim();// �����뷶Χ
		String annualIncome = body.element("person").element("annualIncome")
				.getTextTrim();// ������
		String currCareerStartDate = body.element("person")
				.element("currCareerStartDate").getTextTrim();// �μӱ���λ����
		String hasQualification = body.element("person")
				.element("hasQualification").getTextTrim();// �Ƿ���ִҵ�ʸ�
		String qualification = body.element("person").element("qualification")
				.getTextTrim();// �ʸ�֤������
		String careerTitle = body.element("person").element("careerTitle")
				.getTextTrim();// ְ��
		String holdStockAmt = body.element("person").element("holdStockAmt")
				.getTextTrim();// ӵ�����йɷݽ��
		String bankDuty = body.element("person").element("bankDuty")
				.getTextTrim();// ������ְ��
		String salaryAcctBank = body.element("person")
				.element("salaryAcctBank").getTextTrim();// �����˻�������
		String salaryAcctNo = body.element("person").element("salaryAcctNo")
				.getTextTrim();// �����˺�
		String loanCardNo = body.element("person").element("loanCardNo")
				.getTextTrim();// �������
		String holdAcct = body.element("person").element("holdAcct")
				.getTextTrim();// �����п����˻����
		String holdCard = body.element("person").element("holdCard")
				.getTextTrim();// �ֿ����
		String resume = body.element("person").element("resume").getTextTrim();// ���˼���
		String usaTaxIdenNo = body.element("person").element("usaTaxIdenNo")
				.getTextTrim();// ������˰��ʶ���
		String lastDealingsDesc = body.element("person")
				.element("lastDealingsDesc").getTextTrim();// ǰ������״��
		String remark = body.element("person").element("remark").getTextTrim();// ��ע
		
		//�ͻ�������Ϣ�����޸Ĳ���
		String spouseName = body.element("person").element("spouseName").getTextTrim();//  ��ż����
		String spousePhone = body.element("person").element("spousePhone").getTextTrim();// ��ż��ϵ�绰
		String spouseMobile = body.element("person").element("spouseMobile").getTextTrim();// ��ż�ƶ��绰
		String spouseId = body.element("person").element("spouseId").getTextTrim();// ��ż��Ӧ�ͻ���
		String spouseCoreId = body.element("person").element("spouseCoreId").getTextTrim();// ��ż��Ӧ���Ŀͻ���
		
		
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
	 * ���¸��˿ͻ���
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
		
		//���ֶ����
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
	 * ������ϵ��Ϣ��
	 * 
	 * @param customer
	 */
	public AcrmFCiContmeth addComtmeth(AcrmFCiCustomer customer,
			AcrmFCiContmeth contmeth, Element body) throws Exception {

		String custId = customer.getCustId();

		String contmehtId = body.element("contmethId").getTextTrim();
		String isPriori = body.element("isPriori").getTextTrim(); // �Ƿ���ѡ
		String contmethType = body.element("contmethType").getTextTrim(); // ��ϵ��ʽ����
		String contmethInfo = body.element("contmethInfo").getTextTrim(); // ��ϵ��ʽ����
		String contmethSeq = body.element("contmethSeq").getTextTrim(); // ��ϵ˳���
		String remark = body.element("remark").getTextTrim(); // ��ע
		String stat = body.element("stat").getTextTrim(); // ��¼״̬

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
	 * ���ӵ�ַ��Ϣ��
	 * 
	 * @param customer
	 */
	public AcrmFCiAddress addAddress(AcrmFCiCustomer customer,
			AcrmFCiAddress address, Element body) throws Exception {

		String custId = customer.getCustId();
		String addrId = body.element("addrId").getTextTrim();
		String addrType = body.element("addrType").getTextTrim();// ��ַ����
		String addr = body.element("addr").getTextTrim();// ��ϸ��ַ
		String enAddr = body.element("enAddr").getTextTrim();// Ӣ�ĵ�ַ
		String contmethInfo = body.element("contmethInfo").getTextTrim();// ��ַ��ϵ�绰
		String zipcode = body.element("zipcode").getTextTrim();// ��������
		String countryOrRegion = body.element("countryOrRegion").getTextTrim();// ���һ��������
		String adminZone = body.element("adminZone").getTextTrim();// ������������
		String areaCode = body.element("areaCode").getTextTrim();// ��������
		String provinceCode = body.element("provinceCode").getTextTrim();// ʡֱϽ������������
		String cityCode = body.element("cityCode").getTextTrim();// �е������˴���
		String countyCode = body.element("countyCode").getTextTrim();// ��������
		String townCode = body.element("townCode").getTextTrim();// �������
		String townName = body.element("townName").getTextTrim();// ��������
		String streetName = body.element("streetName").getTextTrim();// �ֵ�����
		String villageNo = body.element("villageNo").getTextTrim();// ��������
		String villageName = body.element("villageName").getTextTrim();// ����������

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
	 * ���µ�ַ��Ϣ��
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
	 * ������ż��Ϣ��
	 * 
	 * @param customer
	 */
	public AcrmFCiPerMateinfo addMateinfo(AcrmFCiCustomer customer,
			AcrmFCiPerMateinfo mateinfo, Element body) throws Exception {

		String custId = customer.getCustId();
		String custIdMate = body.element("mateinfo").element("custIdMate").getTextTrim();//��ż�ͻ����
		String mateName = body.element("mateinfo").element("mateName").getTextTrim();// ��ż����
		String homeTel = body.element("mateinfo").element("homeTel").getTextTrim();// ��ͥ�绰
		String mobile = body.element("mateinfo").element("mobile").getTextTrim();// �ֻ�����
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
	 * ������ż��Ϣ�ͻ���
	 * 
	 * @param customer
	 */
	public AcrmFCiPerMateinfo updateMateinfo(AcrmFCiPerMateinfo mateinfo,
			AcrmFCiPerMateinfo oldmateinfo) throws Exception {

		if (oldmateinfo == null) {
			return mateinfo;
		}
		//��ż�ͻ���� CUST_ID_MATE
		//��ż���� MATE_NAME
		//��ͥ�绰 HOME_TEL
		//�ֻ�����  MOBILE
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
	 * ������ϵ��Ϣ��
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
	 * ��������������
	 * 
	 * @param customer
	 * @param crossIndex
	 * @param body
	 * @return
	 */
	public AcrmFCiCrossindex addCrossIndex(AcrmFCiCustomer customer,
			AcrmFCiCrossindex crossIndex, Element body) throws Exception {

		String custId = customer.getCustId();
		String crossindexId = body.element("crossindexId").getTextTrim();// ��������
		String srcSysNo = body.element("srcSysNo").getTextTrim();// Դϵͳ���
		String srcSysCustNo = body.element("srcSysCustNo").getTextTrim();// Դϵͳ�ͻ����
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

package com.ytec.mdm.service.svc.atomic;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.ytec.mdm.domain.biz.AcrmFCiAccountInfo;
import com.ytec.mdm.domain.biz.AcrmFCiAddress;
import com.ytec.mdm.domain.biz.AcrmFCiBankService;
import com.ytec.mdm.domain.biz.AcrmFCiContmeth;
import com.ytec.mdm.domain.biz.AcrmFCiCrossindex;
import com.ytec.mdm.domain.biz.AcrmFCiCustIdentifier;
import com.ytec.mdm.domain.biz.AcrmFCiCustIdentifierLmh;
import com.ytec.mdm.domain.biz.AcrmFCiCustomer;
import com.ytec.mdm.domain.biz.AcrmFCiPerKeyflag;
import com.ytec.mdm.domain.biz.AcrmFCiPerson;
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
public class OpenPerAccount implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(OpenPerAccount.class);

	private static Object FAILED = "Failed";
	private JPABaseDAO baseDAO;

	private static String customerName = "AcrmFCiCustomer";
	private static String personName = "AcrmFCiPerson";
	private static String custIndentifierName = "AcrmFCiCustIdentifier";
	private static String addressName = "AcrmFCiAddress";
	private static String comtmethName = "AcrmFCiContmeth";

	SimpleDateFormat df10 = new SimpleDateFormat(MdmConstants.DATE_FORMAMT);
	SimpleDateFormat df19 = new SimpleDateFormat(MdmConstants.TIME_FORMAMT);

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData crmData) throws Exception {
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

		OcrmFCiBelongCustmgr custMgr = new OcrmFCiBelongCustmgr();////�����ͻ�����

		String custId = null; // ��ȡ�ͻ���
		try {
			custId = body.element("customer").element("custId").getTextTrim();

			Object obj = queryCustMgr(custId);
			if(obj!=null){
				custMgr=(OcrmFCiBelongCustmgr)obj;
				String custBelongMgr=custMgr.getMgrId();
				ProcedureHelper pc=new ProcedureHelper();
				NameUtil getName=new NameUtil();
				String procedureName=getName.GetProcedureName();
				//���ô洢����
				pc.callProcedureNoReturn(procedureName, new Object[]{custId,custBelongMgr});
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
		AcrmFCiCustIdentifier custIndentifier = new AcrmFCiCustIdentifier(); // �ͻ�֤����Ϣ��
		AcrmFCiAddress address = new AcrmFCiAddress(); // ��ַ��Ϣ��
		AcrmFCiContmeth comtmeth = new AcrmFCiContmeth(); // ��ϵ��Ϣ��
		OcrmFCiBelongCustmgr belongManager = new OcrmFCiBelongCustmgr();//�����ͻ�����
		OcrmFCiBelongOrg    belongOrg = new OcrmFCiBelongOrg();//��������
		AcrmFCiCrossindex crossIndex = new AcrmFCiCrossindex();//����������
		Object obj = queryCustomer(custId, crmData, customerName);
		
		AcrmFCiAccountInfo accountInfo=new AcrmFCiAccountInfo();
		AcrmFCiBankService bankService=new AcrmFCiBankService();
		AcrmFCiPerKeyflag  perKeyflag=new AcrmFCiPerKeyflag();
		AcrmFCiCustIdentifierLmh identifierLmh=new AcrmFCiCustIdentifierLmh();

		/**
		 * �жϲ�ѯ�ͻ����Ƿ�ʧ��
		 */
		if (obj != null && obj.equals(FAILED)) {
			return;
		}
		/**
		 *
		 */
		else {
			/**
			 * CRM�Ѵ��ڸÿͻ�����������Ϣ���Ǹ��¿ͻ���Ϣ(��ֵ������ֵ),�����������ݿ���
			 */
			try {
				if (obj != null) {
					AcrmFCiCustomer oldcustomer = addCustomer(customer, body);
					customer = (AcrmFCiCustomer) obj;
					customer = updateCustomer(oldcustomer, customer);

					AcrmFCiPerson oldperson = (AcrmFCiPerson) queryCustomer(custId, crmData, personName);
					person = addPerson(customer, person, body);
					oldperson = updatePerson(person, oldperson);

					List<Element> list_Identifier = body.elements("perIdentifier");
					List<AcrmFCiCustIdentifier> update_custIdentifier = new ArrayList<AcrmFCiCustIdentifier>();
					for (int i = 0; i < list_Identifier.size(); i++) {
						Element per_element = list_Identifier.get(i);
						AcrmFCiCustIdentifier acrm_custIdentifier = new AcrmFCiCustIdentifier();
						acrm_custIdentifier = addCustIndentifier(customer, acrm_custIdentifier, per_element);
						String identType = acrm_custIdentifier.getIdentType();
						AcrmFCiCustIdentifier oldcustIndentifier = (AcrmFCiCustIdentifier) queryIdentifier(custId, identType, crmData, custIndentifierName);
						oldcustIndentifier = updateCustIndentifier(acrm_custIdentifier, oldcustIndentifier);
						update_custIdentifier.add(oldcustIndentifier);
					}

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
					oldCustMrg=updateBelongCustMrg(belongManager,oldCustMrg);//�����ͻ�����

					OcrmFCiBelongOrg oldBelongOrg =(OcrmFCiBelongOrg)queryCustomer(custId,crmData,"OcrmFCiBelongOrg");
					belongOrg = addBelongOrg(customer,belongOrg,body);
					oldBelongOrg=updateBelongOrg(belongOrg,oldBelongOrg);   //��������

//					AcrmFCiCrossindex oldcrossIndex=(AcrmFCiCrossindex)queryCustomer(custId,crmData,"AcrmFCiCrossindex");
//					if(oldcrossIndex!=null){
//						deleteCrossIndex(custId);//���������ô��ɾ��ԭ�ȵ�
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
					//oldcrossIndex=updateCrossIndex(crossIndex, oldcrossIndex);
					//���������ű�
					List<AcrmFCiAccountInfo> accountInfo_list = new ArrayList<AcrmFCiAccountInfo>();
					if(body.selectSingleNode("accountInfo")!=null){
					   List list_accountInfo = body.elements("accountInfo");
					   for(int i=0;i<list_accountInfo.size();i++){
						   Element accountInfo_Element = (Element) list_accountInfo.get(i);
						   AcrmFCiAccountInfo acrm_accountInfo = new AcrmFCiAccountInfo();
						   acrm_accountInfo = addAccountInfo(customer, acrm_accountInfo, accountInfo_Element);
						   accountInfo_list.add(acrm_accountInfo);
					   }
					}
					
					List<AcrmFCiBankService> bankService_list = new ArrayList<AcrmFCiBankService>();
					if(body.selectSingleNode("serviceInfo")!=null){
					   List list_bankService = body.elements("serviceInfo");
					   for(int i=0;i<list_bankService.size();i++){
						   Element bankService_Element = (Element) list_bankService.get(i);
						   AcrmFCiBankService acrm_bankService= new AcrmFCiBankService();
						   acrm_bankService = addBankService(customer, acrm_bankService, bankService_Element);
						   bankService_list.add(acrm_bankService);
					   }
					}
					
					List<AcrmFCiPerKeyflag> perKeyflag_list = new ArrayList<AcrmFCiPerKeyflag>();
					if(body.selectSingleNode("perKeyFlag")!=null){
					   List list_perKeyflag = body.elements("perKeyFlag");
					   for(int i=0;i<list_perKeyflag.size();i++){
						   Element perKeyflag_Element = (Element) list_perKeyflag.get(i);
						   AcrmFCiPerKeyflag acrm_perKeyflag= new AcrmFCiPerKeyflag();
						   acrm_perKeyflag = addperKeyflag(customer, acrm_perKeyflag, perKeyflag_Element);
						   perKeyflag_list.add(acrm_perKeyflag);
					   }
					}
					
					List<AcrmFCiCustIdentifierLmh> identifierLmh_list = new ArrayList<AcrmFCiCustIdentifierLmh>();
					if(body.selectSingleNode("identifierLmh")!=null){
					   List list_identifierLmh = body.elements("identifierLmh");
					   for(int i=0;i<list_identifierLmh.size();i++){
						   Element identifierLmh_Element = (Element) list_identifierLmh.get(i);
						   AcrmFCiCustIdentifierLmh acrm_identifierLmh= new AcrmFCiCustIdentifierLmh();
						   acrm_identifierLmh = addIdentifierLmh(customer, acrm_identifierLmh, identifierLmh_Element);
						   identifierLmh_list.add(acrm_identifierLmh);
					   }
					}



					baseDAO.merge(customer);
					if(oldperson.getCustId()!=null){
					    baseDAO.merge(oldperson);
					}
					for (int i = 0; i < update_custIdentifier.size(); i++) {
						custIndentifier = update_custIdentifier.get(i);
						baseDAO.merge(custIndentifier);
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
					
					for (int i = 0; i < accountInfo_list.size(); i++) {
						accountInfo = accountInfo_list.get(i);
						baseDAO.save(accountInfo);
					}
					
					for (int i = 0; i < bankService_list.size(); i++) {
						bankService = bankService_list.get(i);
						baseDAO.save(bankService);
					}
					
					for (int i = 0; i < perKeyflag_list.size(); i++) {
						perKeyflag = perKeyflag_list.get(i);
						baseDAO.save(perKeyflag);
					}
					for (int i = 0; i < identifierLmh_list.size(); i++) {
						identifierLmh = identifierLmh_list.get(i);
						baseDAO.save(identifierLmh);
					}
					
					
					baseDAO.flush();
				}
				/**
				 * CRM�޸ÿͻ��������ͻ���Ϣ
				 */
				else {

					customer = addCustomer(customer, body);
					person = addPerson(customer, person, body);
					List list_Identifier = body.elements("perIdentifier");
					List<AcrmFCiCustIdentifier> Identtifier_list = new ArrayList<AcrmFCiCustIdentifier>();
					for (int i = 0; i < list_Identifier.size(); i++) {
						Element address_Element = (Element) list_Identifier.get(i);
						AcrmFCiCustIdentifier acrm_custIndentifier = new AcrmFCiCustIdentifier();
						acrm_custIndentifier = addCustIndentifier(customer, acrm_custIndentifier, address_Element);
						Identtifier_list.add(acrm_custIndentifier);
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
					
					List<AcrmFCiAccountInfo> accountInfo_list = new ArrayList<AcrmFCiAccountInfo>();
					if(body.selectSingleNode("accountInfo")!=null){
					   List list_accountInfo = body.elements("accountInfo");
					   for(int i=0;i<list_accountInfo.size();i++){
						   Element accountInfo_Element = (Element) list_accountInfo.get(i);
						   AcrmFCiAccountInfo acrm_accountInfo = new AcrmFCiAccountInfo();
						   acrm_accountInfo = addAccountInfo(customer, acrm_accountInfo, accountInfo_Element);
						   accountInfo_list.add(acrm_accountInfo);
					   }
					}
					
					List<AcrmFCiBankService> bankService_list = new ArrayList<AcrmFCiBankService>();
					if(body.selectSingleNode("serviceInfo")!=null){
					   List list_bankService = body.elements("serviceInfo");
					   for(int i=0;i<list_bankService.size();i++){
						   Element bankService_Element = (Element) list_bankService.get(i);
						   AcrmFCiBankService acrm_bankService= new AcrmFCiBankService();
						   acrm_bankService = addBankService(customer, acrm_bankService, bankService_Element);
						   bankService_list.add(acrm_bankService);
					   }
					}
					
					List<AcrmFCiPerKeyflag> perKeyflag_list = new ArrayList<AcrmFCiPerKeyflag>();
					if(body.selectSingleNode("perKeyFlag")!=null){
					   List list_perKeyflag = body.elements("perKeyFlag");
					   for(int i=0;i<list_perKeyflag.size();i++){
						   Element perKeyflag_Element = (Element) list_perKeyflag.get(i);
						   AcrmFCiPerKeyflag acrm_perKeyflag= new AcrmFCiPerKeyflag();
						   acrm_perKeyflag = addperKeyflag(customer, acrm_perKeyflag, perKeyflag_Element);
						   perKeyflag_list.add(acrm_perKeyflag);
					   }
					}
					
					List<AcrmFCiCustIdentifierLmh> identifierLmh_list = new ArrayList<AcrmFCiCustIdentifierLmh>();
					if(body.selectSingleNode("identifierLmh")!=null){
					   List list_identifierLmh = body.elements("identifierLmh");
					   for(int i=0;i<list_identifierLmh.size();i++){
						   Element identifierLmh_Element = (Element) list_identifierLmh.get(i);
						   AcrmFCiCustIdentifierLmh acrm_identifierLmh= new AcrmFCiCustIdentifierLmh();
						   acrm_identifierLmh = addIdentifierLmh(customer, acrm_identifierLmh, identifierLmh_Element);
						   identifierLmh_list.add(acrm_identifierLmh);
					   }
					}

					baseDAO.save(customer);
					if(person.getCustId()!=null){
					  baseDAO.save(person);
					}
					for (int i = 0; i < Identtifier_list.size(); i++) {
						custIndentifier = Identtifier_list.get(i);
						baseDAO.save(custIndentifier);
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
					
					for (int i = 0; i < accountInfo_list.size(); i++) {
						accountInfo = accountInfo_list.get(i);
						baseDAO.save(accountInfo);
					}
					
					for (int i = 0; i < bankService_list.size(); i++) {
						bankService = bankService_list.get(i);
						baseDAO.save(bankService);
					}
					
					for (int i = 0; i < perKeyflag_list.size(); i++) {
						perKeyflag = perKeyflag_list.get(i);
						baseDAO.save(perKeyflag);
					}
					for (int i = 0; i < identifierLmh_list.size(); i++) {
						identifierLmh = identifierLmh_list.get(i);
						baseDAO.save(identifierLmh);
					}
					baseDAO.flush();
				}
			} catch (Exception e) {
				String msg;
				if (e instanceof ParseException) {
					msg = String.format("����/ʱ��(%s)��ʽ�����Ϲ淶,ת������", e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
					crmData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
					log.error("{},{}", msg, e);
				} else if (e instanceof NumberFormatException) {
					msg = String.format("��ֵ(%s)��ʽ�����Ϲ淶,ת������", e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf('"')).replace("\"", ""));
					crmData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
					log.error("{},{}", msg, e);
				} else {
					msg = "���ݱ���ʧ��";
					log.error("{},{}", msg, e);
					crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
				}
				crmData.setSuccess(false);
				return;
			}
		}

		/**
		 * �����ر���
		 */
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		Element customerEle = responseEle.addElement("custNo");
		customerEle.setText(custId);

		// Element hand = customerEle.addElement("custId");
		// hand.setText(custId);
		crmData.setRepNode(responseEle);

	}

	public void deleteCrossIndex(String custId) throws Exception{
//		String tableName ="AcrmFCiCrossindex";
//		// ƴװJQL��ѯ���
//		StringBuffer jql = new StringBuffer();
		String sql="delete from ACRM_F_CI_CROSSINDEX where cust_Id='"+custId+"'";
//		Map<String, String> paramMap = new HashMap<String, String>();
//		jql.append("DELETE FROM "+tableName +" ");
//		jql.append("WHERE custId=:custId");
//		paramMap.put("custId", custId);
		baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		baseDAO.flush();
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
	public Object queryCrossIndex(String custId, String srcSysNo, EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}
	public Object queryCustMgr(String custId) throws Exception{
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
			}else{
				return null;
			}


	}

	/**
	 * ���ݿͻ���Ų�ѯ�����е�����
	 *
	 * @param custId
	 * @param crmData
	 * @return
	 * @throws Exception
	 */

	public Object queryCustomer(String custId, EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) { return result.get(0); }

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
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(false);
			return FAILED;
		}
		return null;
	}

	/**
	 * ��ѯ֤����Ϣ
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
			jql.append(" AND a.identType =:identType");
			// ����ѯ���������뵽map��������
			paramMap.put("custId", custId);
			paramMap.put("identType", identType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

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
	public Object queryContmeth(String custId, String contmethType, EcifData crmData, String simpleNames) throws Exception {

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
			if (result != null && result.size() > 0) { return result.get(0); }

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
		String loanMainBrId = body.element("customer").element("loanMainBrId").getTextTrim();
		String arCustFlag = body.element("customer").element("arCustFlag").getTextTrim();
		String arCustType = body.element("customer").element("arCustType").getTextTrim();
		String sourceChannel = body.element("customer").element("sourceChannel").getTextTrim();
		String recommender = body.element("customer").element("recommender").getTextTrim();
		String infoPer = body.element("customer").element("infoPer").getTextTrim();
		String createDate = body.element("customer").element("createDate").getTextTrim();
		String createTime = body.element("customer").element("createTime").getTextTrim();
		String createBranchNo = body.element("customer").element("createBranchNo").getTextTrim();
		String createTellerNo = body.element("customer").element("createTellerNo").getTextTrim();

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
			customer.setCreateTime(new Timestamp(df19.parse(createTime).getTime()));
		}
		customer.setCreateBranchNo(createBranchNo);
		customer.setCreateTellerNo(createTellerNo);
		return customer;
	}

	/**
	 * �޸Ŀͻ���Ϣ��
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

		return oldcustomer;
	}

	/**
	 * ���Ӹ��˿ͻ���
	 *
	 * @param customer
	 */
	public AcrmFCiPerson addPerson(AcrmFCiCustomer customer, AcrmFCiPerson person, Element body) throws Exception {
		String custId = customer.getCustId();

		String perCustType = body.element("person").element("perCustType").getTextTrim();// ���˿ͻ�����
		String jointCustType = body.element("person").element("jointCustType").getTextTrim();// ����������
		String orgSubType=body.element("person").element("orgSubType").getTextTrim();//��ó������
		String ifOrgSubType=body.element("person").element("ifOrgSubType").getTextTrim();//�Ƿ���ó����־
		String surName = body.element("person").element("surName").getTextTrim();// �ͻ�����
		String personalName = body.element("person").element("personalName").getTextTrim();// �ͻ�����
		String pinyinName = body.element("person").element("pinyinName").getTextTrim();// ƴ������
		String pinyinAbbr = body.element("person").element("pinyinAbbr").getTextTrim();// ƴ����д
		String personTitle = body.element("person").element("personTitle").getTextTrim();// �ͻ���ν
		String areaCode = body.element("person").element("areaCode").getTextTrim();//������������
		String nickName = body.element("person").element("nickName").getTextTrim();// �ͻ��ǳ�
		String usedName = body.element("person").element("usedName").getTextTrim();// ������
		String gender = body.element("person").element("gender").getTextTrim();// �Ա�
		String birthday = body.element("person").element("birthday").getTextTrim();// ��������
		String birthlocale = body.element("person").element("birthlocale").getTextTrim();// �����ص�
		String citizenship = body.element("person").element("citizenship").getTextTrim();// ����
		String nationality = body.element("person").element("nationality").getTextTrim();// ����
		String nativeplace = body.element("person").element("nativeplace").getTextTrim();// ����
		String household = body.element("person").element("household").getTextTrim();// ��������
		String hukouPlace = body.element("person").element("hukouPlace").getTextTrim();// �������ڵ�
		String marriage = body.element("person").element("marriage").getTextTrim();// ����״��
		String residence = body.element("person").element("residence").getTextTrim();// ��ס״��
		String health = body.element("person").element("health").getTextTrim();// ����״��
		String religiousBelief = body.element("person").element("religiousBelief").getTextTrim();// �ڽ�����
		String politicalFace = body.element("person").element("politicalFace").getTextTrim();// ������ò
		String mobilePhone = body.element("person").element("mobilePhone").getTextTrim();// �ֻ�����
		String email = body.element("person").element("email").getTextTrim();// �ʼ���ַ
		String homepage = body.element("person").element("homepage").getTextTrim();// ��ҳ
		String weibo = body.element("person").element("weibo").getTextTrim();// ΢��
		String weixin = body.element("person").element("weixin").getTextTrim();// ΢��
		String qq = body.element("person").element("qq").getTextTrim();// QQ
		String starSign = body.element("person").element("starSign").getTextTrim();// ����
		String homeAddr = body.element("person").element("homeAddr").getTextTrim();// סլ��ַ
		String homeZipcode = body.element("person").element("homeZipcode").getTextTrim();// סլ�ʱ�
		String homeTel = body.element("person").element("homeTel").getTextTrim();// סլ�绰
		String highestSchooling = body.element("person").element("highestSchooling").getTextTrim();// ���ѧ��
		String highestDegree = body.element("person").element("highestDegree").getTextTrim();// ���ѧλ
		String graduateSchool = body.element("person").element("graduateSchool").getTextTrim();// ��ҵѧУ
		String major = body.element("person").element("major").getTextTrim();// ��ѧרҵ
		String graduationDate = body.element("person").element("graduationDate").getTextTrim();// ��ҵʱ��
		String careerStat = body.element("person").element("careerStat").getTextTrim();// ְҵ״��
		String careerType = body.element("person").element("careerType").getTextTrim();// ְҵ
		String profession = body.element("person").element("profession").getTextTrim();// ������ҵ
		String unitName = body.element("person").element("unitName").getTextTrim();// ��λ����
		String unitChar = body.element("person").element("unitChar").getTextTrim();// ��λ����
		String unitAddr = body.element("person").element("unitAddr").getTextTrim();// ��λ��ַ
		String unitZipcode = body.element("person").element("unitZipcode").getTextTrim();// ��λ�ʱ�
		String unitTel = body.element("person").element("unitTel").getTextTrim();// ��λ�绰
		String unitFex = body.element("person").element("unitFex").getTextTrim();// �������
		String postAddr = body.element("person").element("postAddr").getTextTrim();// ͨѶ��ַ
		String postZipcode = body.element("person").element("postZipcode").getTextTrim();// ͨѶ����
		String postPhone = body.element("person").element("postPhone").getTextTrim();// ��ϵ�绰
		String adminLevel = body.element("person").element("adminLevel").getTextTrim();// ��������
		String cntName = body.element("person").element("cntName").getTextTrim();// ��λ��ϵ��
		String duty = body.element("person").element("duty").getTextTrim();// ְ��
		String workResult = body.element("person").element("workResult").getTextTrim();// ҵ������
		String careerStartDate = body.element("person").element("careerStartDate").getTextTrim();// �μӹ���ʱ��
		String annualIncomeScope = body.element("person").element("annualIncomeScope").getTextTrim();// �����뷶Χ
		String annualIncome = body.element("person").element("annualIncome").getTextTrim();// ������
		String currCareerStartDate = body.element("person").element("currCareerStartDate").getTextTrim();// �μӱ���λ����
		String hasQualification = body.element("person").element("hasQualification").getTextTrim();// �Ƿ���ִҵ�ʸ�
		String qualification = body.element("person").element("qualification").getTextTrim();// �ʸ�֤������
		String careerTitle = body.element("person").element("careerTitle").getTextTrim();// ְ��
		String holdStockAmt = body.element("person").element("holdStockAmt").getTextTrim();// ӵ�����йɷݽ��
		String bankDuty = body.element("person").element("bankDuty").getTextTrim();// ������ְ��
		String salaryAcctBank = body.element("person").element("salaryAcctBank").getTextTrim();// �����˻�������
		String salaryAcctNo = body.element("person").element("salaryAcctNo").getTextTrim();// �����˺�
		String loanCardNo = body.element("person").element("loanCardNo").getTextTrim();// �������
		String holdAcct = body.element("person").element("holdAcct").getTextTrim();// �����п����˻����
		String holdCard = body.element("person").element("holdCard").getTextTrim();// �ֿ����
		String resume = body.element("person").element("resume").getTextTrim();// ���˼���
		String usaTaxIdenNo = body.element("person").element("usaTaxIdenNo").getTextTrim();// ������˰��ʶ���
		String lastDealingsDesc = body.element("person").element("lastDealingsDesc").getTextTrim();// ǰ������״��
		String remark = body.element("person").element("remark").getTextTrim();// ��ע

		person.setCustId(custId);
		person.setPerCustType(perCustType);
        person.setAreaCode(areaCode);
		person.setJointCustType(jointCustType);
		person.setOrgSubType(orgSubType);
		person.setIfOrgSubType(ifOrgSubType);
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
		if (currCareerStartDate != null && !currCareerStartDate.trim().equals("")) {
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

		return person;
	}

	/**
	 * ���¸��˿ͻ���
	 *
	 * @param customer
	 */
	public AcrmFCiPerson updatePerson(AcrmFCiPerson person, AcrmFCiPerson oldperson) throws Exception {

		if (oldperson == null) { return person; }

		oldperson.setCustId(person.getCustId() == null ? oldperson.getCustId() : person.getCustId());
		oldperson.setPerCustType(person.getPerCustType() == null ? oldperson.getPerCustType() : person.getPerCustType());
		oldperson.setJointCustType(person.getJointCustType() == null ? oldperson.getJointCustType() : person.getJointCustType());
		oldperson.setOrgSubType(person.getOrgSubType() == null ? oldperson.getOrgSubType() : person.getOrgSubType());
		oldperson.setIfOrgSubType(person.getIfOrgSubType() == null ? oldperson.getIfOrgSubType() : person.getIfOrgSubType());
		oldperson.setSurName(person.getSurName() == null ? oldperson.getSurName() : person.getSurName());
		oldperson.setPersonalName(person.getPersonalName() == null ? oldperson.getPersonalName() : person.getPersonalName());
		oldperson.setPinyinName(person.getPinyinName() == null ? oldperson.getPinyinName() : person.getPinyinName());
		oldperson.setPinyinAbbr(person.getPinyinAbbr() == null ? oldperson.getPinyinAbbr() : person.getPinyinAbbr());
		oldperson.setPersonTitle(person.getPersonTitle() == null ? oldperson.getPersonTitle() : person.getPersonTitle());
		oldperson.setNickName(person.getNickName() == null ? oldperson.getNickName() : person.getNickName());
		oldperson.setUsedName(person.getUsedName() == null ? oldperson.getUsedName() : person.getUsedName());
		oldperson.setGender(person.getGender() == null ? oldperson.getGender() : person.getGender());
		oldperson.setBirthday(person.getBirthday() == null ? oldperson.getBirthday() : person.getBirthday());
		oldperson.setBirthlocale(person.getBirthlocale() == null ? oldperson.getBirthlocale() : person.getBirthlocale());
		oldperson.setCitizenship(person.getCitizenship() == null ? oldperson.getCitizenship() : person.getCitizenship());
		oldperson.setNationality(person.getNationality() == null ? oldperson.getNationality() : person.getNationality());
		oldperson.setNativeplace(person.getNativeplace() == null ? oldperson.getNativeplace() : person.getNativeplace());
		oldperson.setHousehold(person.getHousehold() == null ? oldperson.getHousehold() : person.getHousehold());
		oldperson.setHukouPlace(person.getHukouPlace() == null ? oldperson.getHukouPlace() : person.getHukouPlace());
		oldperson.setMarriage(person.getMarriage() == null ? oldperson.getMarriage() : person.getMarriage());
		oldperson.setResidence(person.getResidence() == null ? oldperson.getResidence() : person.getResidence());
		oldperson.setHealth(person.getHealth() == null ? oldperson.getHealth() : person.getHealth());
		oldperson.setReligiousBelief(person.getReligiousBelief() == null ? oldperson.getReligiousBelief() : person.getReligiousBelief());
		oldperson.setPoliticalFace(person.getPoliticalFace() == null ? oldperson.getPoliticalFace() : person.getPoliticalFace());
		oldperson.setMobilePhone(person.getMobilePhone() == null ? oldperson.getMobilePhone() : person.getMobilePhone());
		oldperson.setEmail(person.getEmail() == null ? oldperson.getEmail() : person.getEmail());
		oldperson.setHomepage(person.getHomepage() == null ? oldperson.getHomepage() : person.getHomepage());
		oldperson.setWeibo(person.getWeibo() == null ? oldperson.getWeibo() : person.getWeibo());
		oldperson.setWeixin(person.getWeixin() == null ? oldperson.getWeixin() : person.getWeixin());
		oldperson.setQq(person.getQq() == null ? oldperson.getQq() : person.getQq());
		oldperson.setStarSign(person.getStarSign() == null ? oldperson.getStarSign() : person.getStarSign());
		oldperson.setHomeAddr(person.getHomeAddr() == null ? oldperson.getHomeAddr() : person.getHomeAddr());
		oldperson.setHomeZipcode(person.getHomeZipcode() == null ? oldperson.getHomeZipcode() : person.getHomeZipcode());
		oldperson.setHomeTel(person.getHomeTel() == null ? oldperson.getHomeTel() : person.getHomeTel());
		oldperson.setHighestSchooling(person.getHighestSchooling() == null ? oldperson.getHighestSchooling() : person.getHighestSchooling());
		oldperson.setHighestDegree(person.getHighestDegree() == null ? oldperson.getHighestDegree() : person.getHighestDegree());
		oldperson.setGraduateSchool(person.getGraduateSchool() == null ? oldperson.getGraduateSchool() : person.getGraduateSchool());
		oldperson.setMajor(person.getMajor() == null ? oldperson.getMajor() : person.getMajor());
		oldperson.setGraduationDate(person.getGraduationDate() == null ? oldperson.getGraduationDate() : person.getGraduationDate());
		oldperson.setCareerStat(person.getCareerStat() == null ? oldperson.getCareerStat() : person.getCareerStat());
		oldperson.setCareerType(person.getCareerType() == null ? oldperson.getCareerType() : person.getCareerType());
		oldperson.setProfession(person.getProfession() == null ? oldperson.getProfession() : person.getProfession());
		oldperson.setUnitName(person.getUnitName() == null ? oldperson.getUnitName() : person.getUnitName());
		oldperson.setUnitChar(person.getUnitChar() == null ? oldperson.getUnitChar() : person.getUnitChar());
		oldperson.setUnitAddr(person.getUnitAddr() == null ? oldperson.getUnitAddr() : person.getUnitAddr());
		oldperson.setUnitZipcode(person.getUnitZipcode() == null ? oldperson.getUnitZipcode() : person.getUnitZipcode());
		oldperson.setUnitTel(person.getUnitTel() == null ? oldperson.getUnitTel() : person.getUnitTel());
		oldperson.setUnitFex(person.getUnitFex() == null ? oldperson.getUnitFex() : person.getUnitFex());
		oldperson.setPostAddr(person.getPostAddr() == null ? oldperson.getPostAddr() : person.getPostAddr());
		oldperson.setPostZipcode(person.getPostZipcode() == null ? oldperson.getPostZipcode() : person.getPostZipcode());
		oldperson.setPostPhone(person.getPostPhone() == null ? oldperson.getPostPhone() : person.getPostPhone());
		oldperson.setAdminLevel(person.getAdminLevel() == null ? oldperson.getAdminLevel() : person.getAdminLevel());
		oldperson.setCntName(person.getCntName() == null ? oldperson.getCntName() : person.getCntName());
		oldperson.setDuty(person.getDuty() == null ? oldperson.getDuty() : person.getDuty());
		oldperson.setWorkResult(person.getWorkResult() == null ? oldperson.getWorkResult() : person.getWorkResult());
		oldperson.setCareerStartDate(person.getCareerStartDate() == null ? oldperson.getCareerStartDate() : person.getCareerStartDate());
		oldperson.setAnnualIncomeScope(person.getAnnualIncomeScope() == null ? oldperson.getAnnualIncomeScope() : person.getAnnualIncomeScope());
		oldperson.setAnnualIncome(person.getAnnualIncome() == null ? oldperson.getAnnualIncome() : person.getAnnualIncome());
		oldperson.setCurrCareerStartDate(person.getCurrCareerStartDate() == null ? oldperson.getCurrCareerStartDate() : person.getCurrCareerStartDate());
		oldperson.setHasQualification(person.getHasQualification() == null ? oldperson.getHasQualification() : person.getHasQualification());
		oldperson.setQualification(person.getQualification() == null ? oldperson.getQualification() : person.getQualification());
		oldperson.setCareerTitle(person.getCareerTitle() == null ? oldperson.getCareerTitle() : person.getCareerTitle());
		oldperson.setHoldStockAmt(person.getHoldStockAmt() == null ? oldperson.getHoldStockAmt() : person.getHoldStockAmt());
		oldperson.setBankDuty(person.getBankDuty() == null ? oldperson.getBankDuty() : person.getBankDuty());
		oldperson.setSalaryAcctBank(person.getSalaryAcctBank() == null ? oldperson.getSalaryAcctBank() : person.getSalaryAcctBank());
		oldperson.setSalaryAcctNo(person.getSalaryAcctNo() == null ? oldperson.getSalaryAcctNo() : person.getSalaryAcctNo());
		oldperson.setLoanCardNo(person.getLoanCardNo() == null ? oldperson.getLoanCardNo() : person.getLoanCardNo());
		oldperson.setHoldAcct(person.getHoldAcct() == null ? oldperson.getHoldAcct() : person.getHoldAcct());
		oldperson.setHoldCard(person.getHoldCard() == null ? oldperson.getHoldCard() : person.getHoldCard());
		oldperson.setResume(person.getResume() == null ? oldperson.getResume() : person.getResume());
		oldperson.setUsaTaxIdenNo(person.getUsaTaxIdenNo() == null ? oldperson.getUsaTaxIdenNo() : person.getUsaTaxIdenNo());
		oldperson.setLastDealingsDesc(person.getLastDealingsDesc() == null ? oldperson.getLastDealingsDesc() : person.getLastDealingsDesc());
		oldperson.setRemark(person.getRemark() == null ? oldperson.getRemark() : person.getRemark());
        oldperson.setAreaCode(person.getAreaCode()==null? oldperson.getAreaCode():person.getAreaCode());
		return oldperson;
	}

	/**
	 * ���ӿͻ�֤����Ϣ��
	 *
	 * @param customer
	 */
	public AcrmFCiCustIdentifier addCustIndentifier(AcrmFCiCustomer customer, AcrmFCiCustIdentifier custIndentifier, Element body) throws Exception {
		String custId = customer.getCustId();

		String identId = body.element("identId").getTextTrim();
		String identType = body.element("identType").getTextTrim();// ֤������
		String identNo = body.element("identNo").getTextTrim();// ֤������
		String identCustName = body.element("identCustName").getTextTrim();// ֤������
		String identDesc = body.element("identDesc").getTextTrim();// ֤������
		String countryOrRegion = body.element("countryOrRegion").getTextTrim();// ��֤���һ����
		String identOrg = body.element("identOrg").getTextTrim();// ��֤����
		String identApproveUnit = body.element("identApproveUnit").getTextTrim();// ֤����׼��λ
		String identCheckFlag = body.element("identCheckFlag").getTextTrim();// ֤������־
		String identCheckingDate = body.element("identCheckingDate").getTextTrim();// ֤����쵽����
		String identCheckedDate = body.element("identCheckedDate").getTextTrim();// ֤���������
		String identValidPeriod = body.element("identValidPeriod").getTextTrim();// ֤����Ч��
		String identEffectiveDate = body.element("identEffectiveDate").getTextTrim();// ֤����Ч����
		String identExpiredDate = body.element("identExpiredDate").getTextTrim();// ֤��ʧЧ����
		String identValidFlag = body.element("identValidFlag").getTextTrim();// ֤����Ч��־
		String identPeriod = body.element("identPeriod").getTextTrim();// ֤������
		String isOpenAccIdent = body.element("isOpenAccIdent").getTextTrim();// �Ƿ񿪻�֤��
		String openAccIdentModifiedFlag = body.element("openAccIdentModifiedFlag").getTextTrim();// ����֤���޸ı�־
		String identModifiedTime = body.element("identModifiedTime").getTextTrim();// ֤���޸�ʱ��
		String verifyDate = body.element("verifyDate").getTextTrim();// У������
		String verifyEmployee = body.element("verifyEmployee").getTextTrim();// У��Ա��
		String verifyResult = body.element("verifyResult").getTextTrim();// У����

		custIndentifier.setIdentId(Long.parseLong(identId));
		custIndentifier.setCustId(custId);
		custIndentifier.setIdentType(identType);
		custIndentifier.setIdentNo(identNo);
		custIndentifier.setIdentCustName(identCustName);
		custIndentifier.setIdentDesc(identDesc);
		custIndentifier.setCountryOrRegion(countryOrRegion);
		custIndentifier.setIdentOrg(identOrg);
		custIndentifier.setIdentApproveUnit(identApproveUnit);
		custIndentifier.setIdentCheckFlag(identCheckFlag);
		if (identCheckedDate != null && !identCheckedDate.trim().equals("")) {
			custIndentifier.setIdentCheckingDate(df10.parse(identCheckingDate));
		}
		if (identCheckedDate != null && !identCheckedDate.trim().equals("")) {
			custIndentifier.setIdentCheckedDate(df10.parse(identCheckedDate));
		}
		if (identValidPeriod != null && !identValidPeriod.trim().equals("")) {
			custIndentifier.setIdentValidPeriod(new BigDecimal(identValidPeriod));
		}
		if (identEffectiveDate != null && !identEffectiveDate.trim().equals("")) {
			custIndentifier.setIdentEffectiveDate(df10.parse(identEffectiveDate));
		}
		if (identExpiredDate != null && !identExpiredDate.trim().equals("")) {
			custIndentifier.setIdentExpiredDate(df10.parse(identExpiredDate));
		}
		custIndentifier.setIdentValidFlag(identValidFlag);
		if (identPeriod != null && !identPeriod.trim().equals("")) {
			custIndentifier.setIdentPeriod(new BigDecimal(identPeriod));
		}
		custIndentifier.setIsOpenAccIdent(isOpenAccIdent);
		custIndentifier.setOpenAccIdentModifiedFlag(openAccIdentModifiedFlag);
		if (identModifiedTime != null && !identModifiedTime.trim().equals("")) {
			custIndentifier.setIdentModifiedTime(new Timestamp(df19.parse(identModifiedTime).getTime()));
		}
		if (verifyDate != null && !verifyDate.trim().equals("")) {
			custIndentifier.setVerifyDate(df10.parse(verifyDate));
		}
		custIndentifier.setVerifyEmployee(verifyEmployee);
		custIndentifier.setVerifyResult(verifyResult);

		return custIndentifier;
	}

	/**
	 * ���¿ͻ�֤����Ϣ��
	 *
	 * @param customer
	 */
	public AcrmFCiCustIdentifier updateCustIndentifier(AcrmFCiCustIdentifier custIndentifier, AcrmFCiCustIdentifier oldcustIndentifier) throws Exception {

		if (oldcustIndentifier == null) { return custIndentifier; }

		oldcustIndentifier.setIdentId(custIndentifier.getIdentId() == 0 ? custIndentifier.getIdentId() : oldcustIndentifier.getIdentId());
		oldcustIndentifier.setCustId(custIndentifier.getCustId() == null ? oldcustIndentifier.getCustId() : custIndentifier.getCustId());
		oldcustIndentifier.setIdentType(custIndentifier.getIdentType() == null ? oldcustIndentifier.getIdentType() : custIndentifier.getIdentType());
		oldcustIndentifier.setIdentNo(custIndentifier.getIdentNo() == null ? oldcustIndentifier.getIdentNo() : custIndentifier.getIdentNo());
		oldcustIndentifier.setIdentCustName(custIndentifier.getIdentCustName() == null ? oldcustIndentifier.getIdentCustName() : custIndentifier.getIdentCustName());
		oldcustIndentifier.setIdentDesc(custIndentifier.getIdentDesc() == null ? oldcustIndentifier.getIdentDesc() : custIndentifier.getIdentDesc());
		oldcustIndentifier.setCountryOrRegion(custIndentifier.getCountryOrRegion() == null ? oldcustIndentifier.getCountryOrRegion() : custIndentifier.getCountryOrRegion());
		oldcustIndentifier.setIdentOrg(custIndentifier.getIdentOrg() == null ? oldcustIndentifier.getIdentOrg() : custIndentifier.getIdentOrg());
		oldcustIndentifier.setIdentApproveUnit(custIndentifier.getIdentApproveUnit() == null ? oldcustIndentifier.getIdentApproveUnit() : custIndentifier.getIdentApproveUnit());
		oldcustIndentifier.setIdentCheckFlag(custIndentifier.getIdentCheckFlag() == null ? oldcustIndentifier.getIdentCheckFlag() : custIndentifier.getIdentCheckFlag());
		oldcustIndentifier.setIdentCheckingDate(custIndentifier.getIdentCheckingDate() == null ? oldcustIndentifier.getIdentCheckingDate() : custIndentifier.getIdentCheckingDate());
		oldcustIndentifier.setIdentCheckedDate(custIndentifier.getIdentCheckedDate() == null ? oldcustIndentifier.getIdentCheckedDate() : custIndentifier.getIdentCheckedDate());
		oldcustIndentifier.setIdentValidPeriod(custIndentifier.getIdentPeriod() == null ? oldcustIndentifier.getIdentPeriod() : custIndentifier.getIdentPeriod());
		oldcustIndentifier.setIdentEffectiveDate(custIndentifier.getIdentEffectiveDate() == null ? oldcustIndentifier.getIdentEffectiveDate() : custIndentifier.getIdentEffectiveDate());
		oldcustIndentifier.setIdentExpiredDate(custIndentifier.getIdentExpiredDate() == null ? oldcustIndentifier.getIdentExpiredDate() : custIndentifier.getIdentExpiredDate());
		oldcustIndentifier.setIdentValidFlag(custIndentifier.getIdentValidFlag() == null ? oldcustIndentifier.getIdentValidFlag() : custIndentifier.getIdentValidFlag());
		oldcustIndentifier.setIdentPeriod(custIndentifier.getIdentPeriod() == null ? oldcustIndentifier.getIdentPeriod() : custIndentifier.getIdentPeriod());
		oldcustIndentifier.setIsOpenAccIdent(custIndentifier.getIsOpenAccIdent() == null ? oldcustIndentifier.getIsOpenAccIdent() : custIndentifier.getIsOpenAccIdent());
		oldcustIndentifier.setOpenAccIdentModifiedFlag(custIndentifier.getOpenAccIdentModifiedFlag() == null ? oldcustIndentifier.getOpenAccIdentModifiedFlag() : custIndentifier
				.getOpenAccIdentModifiedFlag());
		oldcustIndentifier.setIdentModifiedTime(custIndentifier.getIdentModifiedTime() == null ? oldcustIndentifier.getIdentModifiedTime() : custIndentifier.getIdentModifiedTime());
		oldcustIndentifier.setVerifyDate(custIndentifier.getVerifyDate() == null ? oldcustIndentifier.getVerifyDate() : custIndentifier.getVerifyDate());
		oldcustIndentifier.setVerifyEmployee(custIndentifier.getVerifyEmployee() == null ? oldcustIndentifier.getVerifyEmployee() : custIndentifier.getVerifyEmployee());
		oldcustIndentifier.setVerifyResult(custIndentifier.getVerifyResult() == null ? oldcustIndentifier.getVerifyResult() : custIndentifier.getVerifyResult());

		return oldcustIndentifier;
	}

	/**
	 * ���ӵ�ַ��Ϣ��
	 *
	 * @param customer
	 */
	public AcrmFCiAddress addAddress(AcrmFCiCustomer customer, AcrmFCiAddress address, Element body) throws Exception {

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

		return address;
	}

	/**
	 * ���µ�ַ��Ϣ��
	 *
	 * @param customer
	 */
	public AcrmFCiAddress updateAddress(AcrmFCiAddress address, AcrmFCiAddress oldaddress) throws Exception {

		if (oldaddress == null) { return address; }

		oldaddress.setAddrId(address.getAddrId() == 0 ? address.getAddrId() : oldaddress.getAddrId());
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
	 * ������ϵ��Ϣ��
	 *
	 * @param customer
	 */
	public AcrmFCiContmeth addComtmeth(AcrmFCiCustomer customer, AcrmFCiContmeth contmeth, Element body) throws Exception {

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

		return contmeth;
	}

	/**
	 * ������Ϣ��
	 *
	 * @param customer
	 */
	public AcrmFCiContmeth updateComtmeth(AcrmFCiContmeth comtmeth, AcrmFCiContmeth oldcomtmeth) throws Exception {

		if (oldcomtmeth == null) { return comtmeth; }

		oldcomtmeth.setContmethId(comtmeth.getContmethId() == 0 ? comtmeth.getContmethId() : oldcomtmeth.getContmethId());
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
	 * ���������ͻ�����
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
		//belongMgr.setId(Long.parseLong(belongManagerId));//��������
		belongMgr.setCustId(custId);//�ͻ����
		belongMgr.setMgrId(custManagerNo);//�ͻ�������
		belongMgr.setMainType(MainType);//��Э������
		return belongMgr;
	}

	/**
	 * ��ѯϵͳ�û���
	 * @param custManagerNo
	 * @return
	 * @throws Exception
	 */
	public Object queryAdminAuth(String custManagerNo) throws Exception{

			// ��ñ���
			String tableName = "AdminAuthAccount";
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");

			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.accountName =:accountName");
			// ����ѯ���������뵽map��������
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
	 * ��ѯϵͳ������
	 * @param custManagerNo
	 * @return
	 * @throws Exception
	 */
	public Object queryAdminOrg(String orgId) throws Exception{

		// ��ñ���
		String tableName = "AdminAuthOrg";
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.orgId =:orgId");
		// ����ѯ���������뵽map��������
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
	 * ���¹����ͻ�����
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
	 * ������������
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
		//belongOrg.setId(Long.parseLong(belongBranchId));//��������
		belongOrg.setCustId(custId);//�ͻ����
		belongOrg.setInstitutionCode(belongBranchNo);//������������
		belongOrg.setMainType(MainType);//��Э������
		return belongOrg;
	}

	/**
	 * ���¹�������
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
	 * ��������������
	 * @param customer
	 * @param crossIndex
	 * @param body
	 * @return
	 */
   public AcrmFCiCrossindex addCrossIndex(AcrmFCiCustomer customer,AcrmFCiCrossindex crossIndex,Element body) throws Exception{

	   String custId = customer.getCustId();
	   String crossindexId = body.element("crossindexId").getTextTrim();//��������
	   String srcSysNo = body.element("srcSysNo").getTextTrim();//Դϵͳ���
	   String srcSysCustNo = body.element("srcSysCustNo").getTextTrim();//Դϵͳ�ͻ����
	   crossIndex.setCrossindexId(crossindexId);
	   crossIndex.setCustId(custId);
	   crossIndex.setSrcSysCustNo(srcSysCustNo);
	   crossIndex.setSrcSysNo(srcSysNo);
	   return crossIndex;
   }

   /**
	 * ��������������
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
  
  /**
   * �����˻���Ϣ
   */
  public AcrmFCiAccountInfo addAccountInfo(AcrmFCiCustomer customer,AcrmFCiAccountInfo acrm_accountInfo,Element body) throws Exception{

	   String custId = customer.getCustId();
	   String isDomesticCust = body.element("isDomesticCust").getTextTrim();
	   String accountContents = body.element("accountContents").getTextTrim();
	   String state=body.element("state").getTextTrim();
	   String serialno = body.element("serialno").getTextTrim();
	   acrm_accountInfo.setCustId(custId);
	   acrm_accountInfo.setIsDomesticCust(isDomesticCust);
	   acrm_accountInfo.setAccountContents(accountContents);
	   acrm_accountInfo.setState(state);
	   acrm_accountInfo.setLastUpdateUser("WZ");
	   acrm_accountInfo.setLastUpdateTm(new Date());
	   acrm_accountInfo.setSerialno(serialno);
	   return acrm_accountInfo;
  }
  
  
  /**
   * ������Ϣ
   */
  
  public AcrmFCiBankService addBankService(AcrmFCiCustomer customer,AcrmFCiBankService acrm_bankService,Element body) throws Exception{

	   String custId = customer.getCustId();
	   String accept = body.element("accept").getTextTrim();
	   String atmHigh = body.element("atmHigh").getTextTrim();
	   String atmLimit = body.element("atmLimit").getTextTrim();
	   String changeNotice = body.element("changeNotice").getTextTrim();
	   String faxservicemail = body.element("faxservicemail").getTextTrim();
	   String isAtmHigh = body.element("isAtmHigh").getTextTrim();
	   String isCardApply = body.element("isCardApply").getTextTrim();
	   String isElebankSer = body.element("isElebankSer").getTextTrim();
	   String isNtBank = body.element("isNtBank").getTextTrim();
	   String isPosHigh = body.element("isPosHigh").getTextTrim();
	   String mail = body.element("mail").getTextTrim();
	   String mailAddress = body.element("mailAddress").getTextTrim();
	   String messageCode = body.element("messageCode").getTextTrim();
	   String microBanking = body.element("microBanking").getTextTrim();
	   String phoneBanking = body.element("phoneBanking").getTextTrim();
	   String posHigh = body.element("posHigh").getTextTrim();
	   String mobileBanking = body.element("mobileBanking").getTextTrim();
	   String posLimit = body.element("posLimit").getTextTrim();
	   String state = body.element("state").getTextTrim();
	   String statements = body.element("statements").getTextTrim();
	   String transactionService = body.element("transactionService").getTextTrim();
	   String ukey = body.element("ukey").getTextTrim();
	   String likeelecbill = body.element("likeelecbill").getTextTrim();
	   String serialno = body.element("serialno").getTextTrim();
	   acrm_bankService.setCustId(custId);
	   acrm_bankService.setAccept(accept);
	   acrm_bankService.setAtmHigh(atmHigh);
	   acrm_bankService.setAtmLimit(atmLimit);
	   acrm_bankService.setChangeNotice(changeNotice);
	   acrm_bankService.setFaxservicemail(faxservicemail);
	   acrm_bankService.setIsAtmHigh(isAtmHigh);
	   acrm_bankService.setIsCardApply(isCardApply);
	   acrm_bankService.setIsElebankSer(isElebankSer);
	   acrm_bankService.setIsNtBank(isNtBank);
	   acrm_bankService.setIsPosHigh(isPosHigh);
	   acrm_bankService.setLastUpdateTm(new Date());
	   acrm_bankService.setLastUpdateUser("WZ");
	   acrm_bankService.setLikeelecbill(likeelecbill);
	   acrm_bankService.setMail(mail);
	   acrm_bankService.setMailAddress(mailAddress);
	   acrm_bankService.setMessageCode(messageCode);
	   acrm_bankService.setMicroBanking(microBanking);
	   acrm_bankService.setPhoneBanking(phoneBanking);
	   acrm_bankService.setPosHigh(posHigh);
	   acrm_bankService.setMobileBanking(mobileBanking);
	   acrm_bankService.setPosLimit(posLimit);
	   acrm_bankService.setState(state);
	   acrm_bankService.setStatements(statements);
	   acrm_bankService.setTransactionService(transactionService);
	   acrm_bankService.setUkey(ukey);
	   acrm_bankService.setSerialno(serialno);
	   
	   return acrm_bankService;
 }
  
  /**
   * ���˱�ʶ��Ϣ
   * @param customer
   * @param acrm_perKeyflag
   * @param body
   * @return
   * @throws Exception
   */
  public AcrmFCiPerKeyflag addperKeyflag(AcrmFCiCustomer customer,AcrmFCiPerKeyflag acrm_perKeyflag,Element body) throws Exception{
	  String custId = customer.getCustId();
	  String creditAmount = body.element("creditAmount").getTextTrim();
	  String foreignHabitatioFlag = body.element("foreignHabitatioFlag").getTextTrim();
	  String hasBadLoan = body.element("hasBadLoan").getTextTrim();
	  String hasBirthInsure = body.element("hasBirthInsure").getTextTrim();
	  String hasCar = body.element("hasCar").getTextTrim();
	  String hasCreditInfo = body.element("hasCreditInfo").getTextTrim();
	  String hasEndoInsure = body.element("hasEndoInsure").getTextTrim();
	  String hasHouseFund = body.element("hasHouseFund").getTextTrim();
	  String hasIdleInsure = body.element("hasIdleInsure").getTextTrim();
	  String hasInjuryInsure = body.element("hasInjuryInsure").getTextTrim();
	  String hasMediInsure = body.element("hasMediInsure").getTextTrim();
	  String hasOtherBankLoan = body.element("hasOtherBankLoan").getTextTrim();
	  String hasPhoto = body.element("hasPhoto").getTextTrim();
	  String hasThisBankLoan = body.element("hasThisBankLoan").getTextTrim();
	  String isCrediblePeasant = body.element("isCrediblePeasant").getTextTrim();
	  String isCreditCust = body.element("isCreditCust").getTextTrim();
	  String isDebitCard = body.element("isDebitCard").getTextTrim();
	  String isDividendCust = body.element("isDividendCust").getTextTrim();
	  String isEbankSignCust = body.element("isEbankSignCust").getTextTrim();
	  String isEmployee = body.element("isEmployee").getTextTrim();
	  String isFaxTransCust = body.element("isFaxTransCust").getTextTrim();
	  String isGuarantee = body.element("isGuarantee").getTextTrim();
	  String isImportantCust = body.element("isImportantCust").getTextTrim();
	  String isMerchant = body.element("isMerchant").getTextTrim();
	  String isNative = body.element("isNative").getTextTrim();
	  String isOnJobWorker = body.element("isOnJobWorker").getTextTrim();
	  String isPayrollCust = body.element("isPayrollCust").getTextTrim();
	  String isPeasant = body.element("isPeasant").getTextTrim();
	  String isPrivBankCust = body.element("isPrivBankCust").getTextTrim();
	  String isSecretCust = body.element("isSecretCust").getTextTrim();
	  String isSendEcomstatFlag = body.element("isSendEcomstatFlag").getTextTrim();
	  String isShareholder = body.element("isShareholder").getTextTrim();
	  String isUptoViplevel = body.element("isUptoViplevel").getTextTrim();
	  String usaTaxFlag = body.element("usaTaxFlag").getTextTrim();
	  acrm_perKeyflag.setCreditAmount((body.element("creditAmount").getTextTrim()).equals("")?null:new BigDecimal(body.element("creditAmount").getTextTrim()));
	  acrm_perKeyflag.setCustId(custId);
	  acrm_perKeyflag.setEtlDate(null);
	  acrm_perKeyflag.setForeignHabitatioFlag(foreignHabitatioFlag);
	  acrm_perKeyflag.setHasBadLoan(hasBadLoan);
	  acrm_perKeyflag.setHasBirthInsure(hasBirthInsure);
	  acrm_perKeyflag.setHasCar(hasCar);
	  acrm_perKeyflag.setHasCreditInfo(hasCreditInfo);
	  acrm_perKeyflag.setHasEndoInsure(hasEndoInsure);
	  acrm_perKeyflag.setHasHouseFund(hasHouseFund);
	  acrm_perKeyflag.setHasIdleInsure(hasIdleInsure);
	  acrm_perKeyflag.setHasInjuryInsure(hasInjuryInsure);
	  acrm_perKeyflag.setHasMediInsure(hasMediInsure);
	  acrm_perKeyflag.setHasOtherBankLoan(hasOtherBankLoan);
	  acrm_perKeyflag.setHasPhoto(hasPhoto);
	  acrm_perKeyflag.setHasThisBankLoan(hasThisBankLoan);
	  acrm_perKeyflag.setIsCrediblePeasant(isCrediblePeasant);
	  acrm_perKeyflag.setIsCreditCust(isCreditCust);
	  acrm_perKeyflag.setIsDebitCard(isDebitCard);
	  acrm_perKeyflag.setIsDividendCust(isDividendCust);
	  acrm_perKeyflag.setIsEbankSignCust(isEbankSignCust);
	  acrm_perKeyflag.setIsEmployee(isEmployee);
	  acrm_perKeyflag.setIsFaxTransCust(isFaxTransCust);
	  acrm_perKeyflag.setIsGuarantee(isGuarantee);
	  acrm_perKeyflag.setIsImportantCust(isImportantCust);
	  acrm_perKeyflag.setIsMerchant(isMerchant);
	  acrm_perKeyflag.setIsNative(isNative);
	  acrm_perKeyflag.setIsOnJobWorker(isOnJobWorker);
	  acrm_perKeyflag.setIsPayrollCust(isPayrollCust);
	  acrm_perKeyflag.setIsPeasant(isPeasant);
	  acrm_perKeyflag.setIsPrivBankCust(isPrivBankCust);
	  acrm_perKeyflag.setIsSecretCust(isSecretCust);
	  acrm_perKeyflag.setIsSendEcomstatFlag(isSendEcomstatFlag);
	  acrm_perKeyflag.setIsShareholder(isShareholder);
	  acrm_perKeyflag.setIsUptoViplevel(isUptoViplevel);
	  acrm_perKeyflag.setLastUpdateSys("WZ");
	  acrm_perKeyflag.setLastUpdateTm(new Timestamp(System.currentTimeMillis()));
	  acrm_perKeyflag.setLastUpdateUser("WZ");
	  acrm_perKeyflag.setTxSeqNo("");
	  acrm_perKeyflag.setUsaTaxFlag(usaTaxFlag);
	  return acrm_perKeyflag;
	  
  }
  
  public AcrmFCiCustIdentifierLmh addIdentifierLmh(AcrmFCiCustomer customer,AcrmFCiCustIdentifierLmh acrm_identifierLmh,Element body) throws Exception{
	  String custId = customer.getCustId();
	  String countryOrRegion = body.element("countryOrRegion").getTextTrim();
	  String idenRegDate = body.element("idenRegDate").getTextTrim();
	  String identApproveUnit = body.element("identApproveUnit").getTextTrim();
	  String identCheckedDate = body.element("identCheckedDate").getTextTrim();
	  String identCheckFlag = body.element("identCheckFlag").getTextTrim();
	  String identCheckingDate = body.element("identCheckingDate").getTextTrim();
	  String identCustName = body.element("identCustName").getTextTrim();
	  String identDesc = body.element("identDesc").getTextTrim();
	  String identEffectiveDate = body.element("identEffectiveDate").getTextTrim();
	  String identExpiredDate = body.element("identExpiredDate").getTextTrim();
	  String identId = body.element("identId").getTextTrim();
	  String identNo = body.element("identNo").getTextTrim();
	  String verifyResult = body.element("verifyResult").getTextTrim();
	  String verifyEmployee = body.element("verifyEmployee").getTextTrim();
	  String verifyDate = body.element("verifyDate").getTextTrim();
	  String txSeqNo = body.element("txSeqNo").getTextTrim();
	  String openAccIdentModifiedFlag = body.element("openAccIdentModifiedFlag").getTextTrim();
	  String isOpenAccIdentLn = body.element("isOpenAccIdentLn").getTextTrim();
	  String identValidPeriod = body.element("identValidPeriod").getTextTrim();
	  String identValidFlag = body.element("identValidFlag").getTextTrim();
	  String identType = body.element("identType").getTextTrim();
	  String identPeriod = body.element("identPeriod").getTextTrim();
	  String identOrg = body.element("identOrg").getTextTrim();
	  acrm_identifierLmh.setCountryOrRegion(countryOrRegion);
	  acrm_identifierLmh.setCustId(custId);
	  acrm_identifierLmh.setEtlDate(null);
	  acrm_identifierLmh.setIdenRegDate(body.element("idenRegDate").getTextTrim().equals("")?null:new Date(idenRegDate));
	  acrm_identifierLmh.setIdentApproveUnit(identApproveUnit);
	  acrm_identifierLmh.setIdentCheckedDate(body.element("identCheckedDate").getTextTrim().equals("")?null:new Date(identCheckedDate));
	  acrm_identifierLmh.setIdentCheckFlag(identCheckFlag);
	  acrm_identifierLmh.setIdentCheckingDate(identCheckingDate.equals("")?null:new Date( identCheckingDate));
	  acrm_identifierLmh.setIdentCustName(identCustName);
	  acrm_identifierLmh.setIdentDesc(identDesc);
	  acrm_identifierLmh.setIdentEffectiveDate(identEffectiveDate.equals("")?null:new Date(identEffectiveDate));
	  acrm_identifierLmh.setIdentExpiredDate(identExpiredDate.equals("")?null:new Date(identExpiredDate));
	  acrm_identifierLmh.setIdentId(identId);
	  acrm_identifierLmh.setIdentNo(identNo);
	  acrm_identifierLmh.setVerifyResult(verifyResult);
	  acrm_identifierLmh.setVerifyEmployee(verifyEmployee);
	  acrm_identifierLmh.setVerifyDate(verifyDate.equals("")?null:new Date(verifyDate));
	  acrm_identifierLmh.setTxSeqNo(txSeqNo);
	  acrm_identifierLmh.setOpenAccIdentModifiedFlag(openAccIdentModifiedFlag);
	  acrm_identifierLmh.setLastUpdateUser("WZ");
	  acrm_identifierLmh.setLastUpdateTm(new Timestamp(System.currentTimeMillis()));
	  acrm_identifierLmh.setLastUpdateSys("WZ");
	  acrm_identifierLmh.setIsOpenAccIdentLn(isOpenAccIdentLn);
	  acrm_identifierLmh.setIsOpenAccIdent(isOpenAccIdentLn);
	  acrm_identifierLmh.setIdentValidPeriod(identValidPeriod.equals("")?null:new BigDecimal(identValidPeriod));
	  acrm_identifierLmh.setIdentValidFlag(identValidFlag);
	  acrm_identifierLmh.setIdentType(identType);
	  acrm_identifierLmh.setIdentPeriod(identPeriod.equals("")?null:new BigDecimal(identPeriod));
	  acrm_identifierLmh.setIdentOrg(identOrg);
	  acrm_identifierLmh.setIdentModifiedTime(null);
	  return acrm_identifierLmh;
  }


}

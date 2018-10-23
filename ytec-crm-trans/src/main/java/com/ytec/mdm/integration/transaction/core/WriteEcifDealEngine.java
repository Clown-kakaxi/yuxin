/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����WriteEcifDealEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:18:05
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.exception.BizException;
import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.biz.AcrmFCiPotCusCom;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxNode4CRM;
import com.ytec.mdm.integration.check.bs.TxBizRuleFactory;
import com.ytec.mdm.integration.transaction.bs.ServiceEntityMgr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�WriteEcifDealEngine
 * @��������д���״�������
 * @��������:
 * @�����ˣ�wangtb@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:18:05
 *                  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriteEcifDealEngine extends AbstractEcifDealEngine {
	//add by liuming 20170719
	private JPABaseDAO baseDAO;

	private static Logger log = LoggerFactory.getLogger(WriteEcifDealEngine.class);
	/**
	 * @��������:authType
	 * @��������:����Ȩ������
	 * @since 1.0.0
	 */
	private String authType = null;
	/**
	 * @��������:authCode
	 * @��������:����Ȩ����
	 * @since 1.0.0
	 */
	private String authCode = null;

	@Override
	public void execute(EcifData data) {
		try {
			//add by liuming 20170719
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			
			this.ecifData = data;
			this.ecifData.setWriteModelObj(new WriteModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			WriteModel generalInfoList = this.ecifData.getWriteModelObj();
			if (StringUtil.isEmpty(data.getAuthCode())) {
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
				authCode = (String) data.getParameterMap().get(MdmConstants.AUTH_CODE);
			} else {
				authCode = data.getAuthCode();
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
			}

			// ��ù��������ڵ�
			this.ecifData.getWriteModelObj().getOperMap().putAll(data.getParameterMap());

			// �ӱ����л�ȡ���ݲ���װ�����ݲ��������У������ʧ���򷵻�
			boolean ref = this.getTableNodeObect(ecifData.getPrimalDoc(), generalInfoList);
			if (!ref) {
				log.error("�ӱ����л�ȡ���ݲ���װ�����ݲ��������У�����ʧ��");
				return;
			}
			/**
			 * ���״�����
			 */
			String txDealClass = txModel.getTxDef().getDealClass();
			IEcifBizLogic createBizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);
			if (createBizLogic == null) {
				log.error("���״�����Ϊ��");
				this.ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL);
				return;
			}
			//add by liuming �ͻ����Ѵ��ڣ�����ʧ��
			if (ecifData.getTxCode() != null 
					&& ecifData.getTxCode().equals("addPotenCust")) {//�ƶ��Ŵ�����Ǳ�ڿͻ�
				boolean isNew = true;//�Ƿ�����Ǳ�ڿͻ�
				List list = ecifData.getWriteModelObj().getOpModelList();
				if (list != null && list.size() > 0) {
					if (list.get(0).getClass().equals(AcrmFCiPotCusCom.class)) {
						AcrmFCiPotCusCom cuscom = (AcrmFCiPotCusCom) list.get(0);
						// �¿ͻ�����
						String custName = (cuscom != null ? cuscom.getCusName().trim(): "");
						// �ɿͻ�����
						String oldCustName = "";
						String custId = (cuscom != null ? cuscom.getCusId(): "");
						// ����custId�ж������������޸�
						String sql1 = "select c.cus_name from ACRM_F_CI_POT_CUS_COM c where c.cus_id ='"+ custId + "'";
						List list1 = baseDAO.findByNativeSQLWithIndexParam(sql1, null);
						if (list1 != null && list1.size() > 0) {
							// �޸�
							isNew = false;
							oldCustName = (list1.get(0) == null ? "" : list1.get(0).toString());
							if (!custName.equals(oldCustName)) {
								// �޸��˿ͻ����ƣ��ݲ�����
							}
						} else {
							// ����
							String sql2 = "select 1 from acrm_f_ci_customer c where c.cust_name ='"
									+ custName + "'";
							List list2 = baseDAO.findByNativeSQLWithIndexParam(
									sql2, null);
							if (list2 != null && list2.size() > 0) {
								log.error("�ͻ��Ѵ���");
								isNew = false;
								this.ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST);
								//this.ecifData.setStatus(ErrorCode.SUCCESS);
								return;
							}else{
								isNew = true;
							}
						}
					}
				}
				createBizLogic.process(this.ecifData);
				//add by liuming 20170720
				if(isNew){
				    saveToCustomer(this.ecifData);
				}
			}else{
				createBizLogic.process(this.ecifData);
			}
			if (this.ecifData.isSuccess()) {
				/***
				 * ��ӡ���ݱ����¼
				 */
				if (this.ecifData.getDataSynchro() != null && BusinessCfg.getBoolean("printChangeLog")) {
					log.info("�ͻ�[{}]��Ϣ�������:", this.ecifData.getEcifCustNo());
					for (Object o : this.ecifData.getDataSynchro()) {
						log.info(o.toString());
					}
				}
			}
		} catch (Exception e) {
			log.error("������Ϣ", e);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			return;
		}
		if (this.ecifData.getRepNode() != null) { return; }
		Element responseEle = DocumentHelper.createElement("ResponseBody");
		this.ecifData.setRepNode(responseEle);
		return;
	}

	/**
	 * @param doc ���������ĵ��ڵ�
	 * @param info д��������ģ�ͣ����ݽṹ�������ڻ�ȡ�����������ݶ��󣬽�������Ķ���洢��OpModelList��
	 * @return
	 */
	private boolean getTableNodeObect(Document doc, WriteModel info) {

		Element root = doc.getRootElement();
		List<TxNode4CRM> nodeList = this.getTxModel().getTxNodeList();
		for (TxNode4CRM nodeDef : nodeList) {
			String className = nodeDef.getTable();
			String xpath = nodeDef.getXpath();
			try {
				// ���ݽ�������(transConf.xml)�ж��ڽڵ��������Ϣ����ȡXML���Ľڵ㣬ȡ�����ݣ��籨�Ľڵ�������Ϊ�գ������
				List<Element> table_s = (List<Element>) root.selectNodes(xpath);
				if (table_s == null || table_s.size() == 0) {
					continue;
				}
				for (Element table : table_s) {
					Object clazz = Class.forName(className).newInstance();
					List<Element> column_s = table.elements();
					for (Element column : column_s) {
						String fieldValue = column.getTextTrim();
						if (!StringUtil.isEmpty(fieldValue)) {

							// ����������������ȡ��ƴ��
							String fieldName = column.getName();
							Field field = clazz.getClass().getDeclaredField(fieldName);

							// ԭ������������setter�������Զ����ɣ���������������������������Զ�Ӧsetter�����������ϴ˹淶�������쳣��
							String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

							field.setAccessible(true);
							try {
								// ��Ҫ����JPAʵ���������Ծ����������������䴦���������쳣
								Class fieldType = field.getType();
								if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
									// LONG ����
									MethodUtils.invokeMethod(clazz, methodName, Long.parseLong(fieldValue));
								} else if (fieldType.equals(String.class)) {
									// �ַ�����
									MethodUtils.invokeMethod(clazz, methodName, fieldValue);
								} else if (fieldType.equals(BigInteger.class)) {
									// BigInteger ����
									MethodUtils.invokeMethod(clazz, methodName, BigInteger.valueOf(Long.parseLong(fieldValue)));
								} else if (fieldType.equals(BigDecimal.class)) {
									// BigDecimal ����
									// ��new BigDecimal������ֵ������ת���������������������
									MethodUtils.invokeMethod(clazz, methodName, new BigDecimal(fieldValue));
								} else if (fieldType.equals(java.util.Date.class) || fieldType.equals(java.sql.Date.class)) {
									// ��������
									MethodUtils.invokeMethod(clazz, methodName, MdmConstants.DATE_FORMAT_10.parse(fieldValue));
								} else if (fieldType.equals(Timestamp.class)) {
									// ʱ������(ʱ���)
									// MethodUtils.invokeMethod(clazz, methodName, MdmConstants.DATE_FORMAT_19.parse(fieldValue).getTime());
									field.set(clazz, Timestamp.valueOf(fieldValue));
								} else {
									// ��������
									MethodUtils.invokeMethod(clazz, methodName, fieldValue);
								}
							} catch (Exception e) {
								String err = String.format("���״�������޷����ݱ�������(λ���� %s �� %s=%s)�����ݿ����", xpath, fieldName, fieldValue);
								log.error("{}", err);
								log.error("{}", e);
								this.ecifData.setSuccess(false);
								this.ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), err);
								return false;
							}
						}
					}
					info.setOpModelList(clazz);
				}
			} catch (Exception e) {
				String err = String.format("���״�������޷����ݱ��Ĺ������ݴ���ʵ����(%s)", className);
				log.error("{}", err);
				log.error("{}", e);
				this.ecifData.setSuccess(false);
				this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_ERROR.getCode(), err);
				return false;
			}
		}

		return true;
	}

	/**
	 * @��������:getClassByParams
	 * @��������:����ֵ
	 * @�����뷵��˵��:
	 * @param className
	 * @param context
	 * @param txMsgNodeAttrs
	 * @param tabId
	 * @return
	 * @�㷨����:
	 * @WARN �÷����ѷ�������
	 * @see getTableNodeObect
	 */
	public <T> T getClassByParams(String className, Element context, List<TxMsgNodeAttr> txMsgNodeAttrs, Long tabId, Long nodeId) {
		T entity = null;
		boolean isAllEmpty = true;
		String val = null;
		StringBuffer txMsgNodeAttrCtList = null;
		if (!StringUtil.isEmpty(className)) {
			try {
				/**
				 * ������
				 */
				Class clazz = ServiceEntityMgr.getEntityByName(className);
				if (clazz == null) {
					log.error("û���ҵ�{}��ʵ����", className);
					this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
					return null;
				}
				/**
				 * ʵ��������
				 */
				entity = (T) clazz.newInstance();
				Object value = null;
				Class typeClass = null;
				Field field = null;
				for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrs) {
					if (tabId.equals(txMsgNodeAttr.getTabId())) {
						/**
						 * �޸�Ȩ�޿���
						 */
						/***
						 * ��ϢƯ��
						 ****/
						if ("Y".equals(txMsgNodeAttr.getNulls())
								&& !EcifPubDataUtils.infoLevelRead(authType, authCode, MdmConstants.CTRLTYPE_WRITE, txMsgNodeAttr.getTabId(), txMsgNodeAttr.getColId())) {
							continue;
						}
						Element attrElement = context.element(txMsgNodeAttr.getAttrCode());
						if (!StringUtil.isEmpty(txMsgNodeAttr.getCheckRule())) {
							if (txMsgNodeAttrCtList == null) {
								txMsgNodeAttrCtList = new StringBuffer(txMsgNodeAttr.getCheckRule());
							} else {
								txMsgNodeAttrCtList.append(',').append(txMsgNodeAttr.getCheckRule());
							}
						}
						if (attrElement != null) {
							val = attrElement.getTextTrim();
						} else {
							val = txMsgNodeAttr.getDefaultVal();
							/*** Ĭ��ֵ���� ***/
							if (!StringUtil.isEmpty(val)) {
								/**
								 * ����Ĭ��ֵ
								 */
								val = parseDefaultVal(val, context);
								if (!this.ecifData.isSuccess()) { return null; }
							}
						}
						if (!StringUtil.isEmpty(val)) {
							/**
							 * ��ȡ���������������ݿ���ֶ����ƣ���Ҫת����java�������淶����
							 */
							field = clazz.getDeclaredField(NameUtil.getColumToJava(txMsgNodeAttr.getColName()));
							typeClass = field.getType();
							/**
							 * ���ַ��͵�����ת����ʵ�����Ե�����
							 */
							value = convertStringToObject(typeClass, val, txMsgNodeAttr.getDataFmt());
							if (value != null) {
								/**
								 * ���ö�˽�ķ���Ȩ��
								 */
								field.setAccessible(true);
								/**
								 * ��������
								 */
								field.set(entity, value);
								isAllEmpty = false;
							} else {
								return null;
							}
						} else {
							/** �ж��Ƿ���ҵ���������ǿ� **/
							if ("P".equals(txMsgNodeAttr.getNulls())) { return null; }
						}
					}
				}

			} catch (Exception e) {
				log.error("������Ϣ��", e);
				this.ecifData.setStatus(ErrorCode.ERR_POJO_UNKNOWN_ERROR);
				return null;
			}
		} else {
			log.warn("���ݿ���������");
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), "���ݿ���������");
			return null;
		}
		/**
		 * ���е�����Ϊ�գ����ؿ�
		 */
		if (isAllEmpty) { return null; }
		/******
		 * д���׹�����
		 */
		if (txMsgNodeAttrCtList != null && txMsgNodeAttrCtList.length() > 0) {
			boolean rel = TxBizRuleFactory.doFilter(this.ecifData, txMsgNodeAttrCtList.toString(), entity);
			if (!rel) { return null; }
		}
		return entity;
	}

	public void createRespDocument(EcifData data, Element node) {

	}
	
	/**
	 * ����ͻ���Ϣ���ͻ���ر�
	 */
	@SuppressWarnings("unused")
	public void saveToCustomer(EcifData data){
		//ƴ��������
		List list = data.getWriteModelObj().getOpModelList();
		String custId = null;//�ƶ��Ŵ��ͻ���
		String custMgr = null;//�ͻ�����
		String custName = null;//�ͻ�����
		String ecifId = null;//ecif�ͻ���
		String identId = null;//֤��id��
		String belongManagerId = null;
		String belongBranchId = null;
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmssSS");
		StringBuffer reQxmlorg = new StringBuffer();
		String reqXml = "";
		String orgId = "";
		
		if(list != null && list.size()>0){
			if(list.get(0).getClass().equals(AcrmFCiPotCusCom.class)){
				AcrmFCiPotCusCom cuscom = (AcrmFCiPotCusCom)list.get(0);
				 custId = cuscom.getCusId();
				 custMgr = cuscom.getCustMgr();
				 custName = cuscom.getCusName();
				 String c = " SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+custMgr+"'";
				 List list1 = baseDAO.findByNativeSQLWithIndexParam(c, null);
				 if(list1 != null && list1.size() >0){
					 orgId =  list1.get(0) != null ? list1.get(0).toString() : custMgr.substring(0,3);
				 }else{
					 orgId = custMgr.substring(0,3);
				 }
				 String Hxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n"
							+ "<TransBody>\n" 
							+ " <RequestHeader>\n"
							+ "    <ReqSysCd>CRM</ReqSysCd>\n" 
							+ "    <ReqSeqNo>"+ df20.format(new Date())+ "</ReqSeqNo>\n"
							+ "    <ReqDt>"+ df8.format(new Date())+ "</ReqDt>\n"
							+ "    <ReqTm>"+ df10.format(new Date())+ "</ReqTm>\n"
							+ "    <DestSysCd>ECIF</DestSysCd>\n"
							+ "    <ChnlNo>82</ChnlNo>\n"
//							+ "    <BrchNo>6801</BrchNo>\n"
							+ "    <BrchNo>"+orgId+"</BrchNo>\n"
							+ "    <BizLine>6491</BizLine>\n"
							+ "    <TrmNo>TRM10010</TrmNo>\n"
							+ "    <TrmIP>127.0.0.1</TrmIP>\n"
							+ "    <TlrNo>"+ custMgr+ "</TlrNo>\n"
							+ " </RequestHeader>\n"
							+ " <RequestBody>\n"
							+ "    <txCode>openOrgAccount4CrmAndDms</txCode>\n"
							+ "    <txName>Ǳ�ڻ����ͻ�����(�ƶ��Ŵ�)</txName>\n"
							+ "    <authType>1</authType>\n"
							+ "    <authCode>1010</authCode>\n";

					String orgIdentifier = " <orgIdentifier>\n" 
							+"    <identType>20</identType>\n"
							+"    <identNo></identNo>\n"
							+"    <identCustName>"+custName+"</identCustName>\n"
							+"	  <identDesc></identDesc>\n"
							+"	  <countryOrRegion></countryOrRegion>\n"
							+"	  <identOrg></identOrg>\n"
							+"	  <identApproveUnit></identApproveUnit>\n"
							+"	  <identCheckFlag></identCheckFlag>\n"
							+"	  <idenRegDate></idenRegDate>\n"
							+"	  <identCheckingDate></identCheckingDate>\n"
							+"	  <identCheckedDate></identCheckedDate>\n"
							+"	  <identValidPeriod></identValidPeriod>\n"
							+"	  <identEffectiveDate></identEffectiveDate>\n"
							+"	  <identExpiredDate></identExpiredDate>\n"
							+"	  <identValidFlag></identValidFlag>\n"
							+"	  <txSeqNo></txSeqNo>\n"
							+"	  <identPeriod></identPeriod>\n"
							+"	  <isOpenAccIdent></isOpenAccIdent>\n"
							+"	  <isOpenAccIdentLn></isOpenAccIdentLn>\n"
							+"	  <openAccIdentModifiedFlag></openAccIdentModifiedFlag>\n"
							+"	  <identModifiedTime></identModifiedTime>\n"
							+"	  <verifyDate></verifyDate>\n"
							+"	  <verifyEmployee></verifyEmployee>\n"
							+"	  <verifyResult></verifyResult>\n"
							+"	  <lastUpdateSys></lastUpdateSys>\n"
							+"	  <lastUpdateUser></lastUpdateUser>\n"
							+"	  <lastUpdateTm></lastUpdateTm>\n"
							+"  </orgIdentifier>\n";
					
					String customer ="<customer>\n" 
							  +"	    <identType>20</identType>\n"
							  +"		<identNo></identNo>\n"
							  +"		<custName>"+custName+"</custName>\n"
							  +"		<custType>1</custType>\n" // �ͻ����ͣ�Ĭ��1:��ҵ
							  +"		<shortName></shortName>\n" // �ͻ����
							  +"		<enName></enName>\n" // Ӣ������
							  +"		<industType></industType>" //������ҵ
							  +"		<riskNationCode></riskNationCode>\n" // ������չ������
						  	  +"		<potentialFlag>1</potentialFlag>\n" // Ǳ�ڿͻ���־:Ĭ��1:Ǳ�ڿͻ�
							  +"		<inoutFlag></inoutFlag>\n" // ���ھ����־
							  +"		<arCustFlag></arCustFlag>\n" // AR�ͻ���־
							  +"		<arCustType></arCustType>\n" // AR�ͻ�����
							  +"        <createDate>"+df8.format(new Date())+"</createDate>\n                          "
							  +"        <createTime>"+df8.format(new Date())+"</createTime>\n"
							  +"        <createBranchNo></createBranchNo>\n"
							  +"        <createTellerNo></createTellerNo>\n"
							  +"        <createDateLn></createDateLn>\n"
							  +"        <createTimeLn></createTimeLn>\n"
							  +"        <createBranchNoLn></createBranchNoLn>\n"
							  +"        <createTellerNoLn></createTellerNoLn>\n"
							  +"        <custLevel></custLevel>\n"
							  +"        <riskLevel></riskLevel>\n"
							  +"        <riskValidDate></riskValidDate>\n"
							  +"        <creditLevel></creditLevel>\n"
							  +"        <currentAum></currentAum>\n"
							  +"        <totalDebt></totalDebt>\n"
							  +"        <infoPer></infoPer>\n"
							  +"        <faxtradeNorecNum></faxtradeNorecNum>\n"
							  +"        <coreNo></coreNo>\n"
							  +"        <postName></postName>\n"
							  +"        <enShortName></enShortName>\n"
							  +"        <custStat></custStat>\n"
							  +"        <jobType></jobType>\n"
							  +"        <industType></industType>\n"
							  +"        <riskNationCode></riskNationCode>\n"
							  +"        <ebankFlag></ebankFlag>\n"
							  +"        <realFlag></realFlag>\n"
							  +"        <blankFlag></blankFlag>\n"
							  +"        <vipFlag></vipFlag>\n"
							  +"        <mergeFlag></mergeFlag>\n"
							  +"        <custnmIdentModifiedFlag></custnmIdentModifiedFlag>\n"
							  +"        <linkmanName></linkmanName>\n"
							  +"        <linkmanTel></linkmanTel>\n"
							  +"        <firstLoanDate></firstLoanDate>\n"
							  +"        <loanCustMgr></loanCustMgr>\n"
							  +"        <loanMainBrId></loanMainBrId>\n"
							  +"        <arCustFlag></arCustFlag>\n"
							  +"        <arCustType></arCustType>\n"
							  +"        <sourceChannel></sourceChannel>\n"
							  +"        <recommender></recommender>\n"
							  +"        <loanCustRank></loanCustRank>\n"
							  +"        <loanCustStat></loanCustStat>\n"
							  +"        <staffin></staffin>\n"
							  +"        <swift></swift>\n"
							  +"        <cusBankRel></cusBankRel>\n"
							  +"        <cusCorpRel></cusCorpRel>\n"
							  +"        <profctr></profctr>\n"
							  +"        <loanCustId></loanCustId>\n"
							  +"     </customer>\n";			
					String contmeth  = " <contmeth>\n" 
							         + "    <contmethType></contmethType>\n"
						             + "    <contmethInfo></contmethInfo>\n"
							         + "    <contmethSeq></contmethSeq>\n"
							         + "	<remark></remark>\n" 
							         + "	<stat></stat>\n"
							         + " </contmeth>\n";
					String crossindex = 
							  " <crossindex>\n" 
							+ "     <srcSysNo>DMS</srcSysNo>\n"
							+ "     <srcSysCustNo>"+custId+"</srcSysCustNo>\n" 
							+ " </crossindex>";
					String org=
							 "	<org>\n"
							+"		<comSpLicOrg></comSpLicOrg>\n"
							+"		<comSpDetail></comSpDetail>\n"
							+"		<comSpLicNo></comSpLicNo>\n"
							+"		<comSpBusiness></comSpBusiness>\n"
							+"		<topCorpLevel></topCorpLevel>\n"
							+"		<fexcPrmCode></fexcPrmCode>\n"
							+"		<fundSource></fundSource>\n"
							+"		<induDeveProspect></induDeveProspect>\n"
							+"		<busiStartDate></busiStartDate>\n"
							+"		<businessMode></businessMode>\n"
							+"		<minorBusiness></minorBusiness>\n"
							+"		<mainBusiness></mainBusiness>\n"
							+"		<superDept></superDept>\n"
							+"		<buildDate></buildDate>\n"
							+"		<entBelong></entBelong>\n"
							+"		<investType></investType>\n"
							+"		<industryCategory></industryCategory>\n"
							+"		<inCllType></inCllType>\n"
							+"		<governStructure></governStructure>\n"
							+"		<orgForm></orgForm>\n"
							+"		<comHoldType></comHoldType>\n"
							+"		<economicType></economicType>\n"
							+"		<employeeScale></employeeScale>\n"
							+"		<assetsScale></assetsScale>\n"
							+"		<entScaleCk></entScaleCk>\n"
							+"		<entScaleRh></entScaleRh>\n"
							+"		<entScale></entScale>\n"
							+"		<entProperty></entProperty>\n"
							+"		<industryChar></industryChar>\n"
							+"		<industryDivision></industryDivision>\n"
							+"		<minorIndustry></minorIndustry>\n"
							+"		<mainIndustry></mainIndustry>\n"
							+"		<busiLicNo></busiLicNo>\n"
							+"		<orgCodeAnnDate></orgCodeAnnDate>\n"
							+"		<orgCodeUnit></orgCodeUnit>\n"
							+"		<orgExpDate></orgExpDate>\n"
							+"		<orgRegDate></orgRegDate>\n"
							+"		<orgCode></orgCode>\n"
							+"		<orgSubType></orgSubType>\n"
							+"		<orgType></orgType>\n"
							+"		<areaCode></areaCode>\n"
							+"		<remark></remark>\n"
							+"		<lastDealingsDesc></lastDealingsDesc>\n"
							+"		<orgWeixin></orgWeixin>\n"
							+"		<orgWeibo></orgWeibo>\n"
							+"		<orgHomepage></orgHomepage>\n"
							+"		<orgEmail></orgEmail>\n"
							+"		<orgFex></orgFex>\n"
							+"		<orgTel></orgTel>\n"
							+"		<orgCus></orgCus>\n"
							+"		<orgZipcode></orgZipcode>\n"
							+"		<orgAddr></orgAddr>\n"
							+"		<holdStockAmt></holdStockAmt>\n"
							+"		<isStockHolder></isStockHolder>\n"
							+"		<industryPosition></industryPosition>\n"
							+"		<annualProfit></annualProfit>\n"
							+"		<annualIncome></annualIncome>\n"
							+"		<totalDebt></totalDebt>\n"
							+"		<totalAssets></totalAssets>\n"
							+"		<finRepType></finRepType>\n"
							+"		<legalReprNationCode></legalReprNationCode>\n"
							+"		<legalReprPhoto></legalReprPhoto>\n"
							+"		<legalReprAddr></legalReprAddr>\n"
							+"		<legalReprTel></legalReprTel>\n"
							+"		<legalReprIdentNo></legalReprIdentNo>\n"
							+"		<legalReprIdentType></legalReprIdentType>\n"
							+"		<legalReprGender></legalReprGender>\n"
							+"		<legalReprName></legalReprName>\n"
							+"		<partnerType></partnerType>\n"
							+"		<loadCardAuditDt></loadCardAuditDt>\n"
							+"		<loadCardPwd></loadCardPwd>\n"
							+"		<loanCardStat></loanCardStat>\n"
							+"		<loanCardNo></loanCardNo>\n"
							+"		<loanCardFlag></loanCardFlag>\n"
							+"		<comSpEndDate></comSpEndDate>\n"
							+"		<comSpStrDate></comSpStrDate>\n"
							+"		<zoneCode></zoneCode>\n"
							+"		<nationCode></nationCode>\n"
							+"		<hqNationCode></hqNationCode>\n"
							+"		<orgBizCustType></orgBizCustType>\n"
							+"		<churcustype></churcustype>\n"
							+"		<jointCustType></jointCustType>\n"
							+"		<creditCode></creditCode>\n"
							+"		<lncustp></lncustp>\n"
							+"		<orgCustType></orgCustType>\n"
							+"		<custName>"+custName+"</custName>\n"
							+"		<ifOrgSubType></ifOrgSubType>\n"
							+"	</org>\n";
					//��������
					String belongManager = " <belongManager>\n "
					                      +"	<custManagerNo>"+custMgr+"</custManagerNo>\n"
					                      +"	<mainType>1</mainType>\n"
					                      +"	<validFlag></validFlag>\n"
					                      +"	<startDate></startDate>\n"
					                      +"	<endDate></endDate>\n"
					                      +"	<custManagerType></custManagerType>\n"
					                      +" </belongManager>\n";
					//��������
					String belongBranch = " <belongBranch>\n "
			                              +"	<belongBranchNo>"+orgId+"</belongBranchNo>\n"
			                              +" </belongBranch>\n";
					reQxmlorg.append(Hxml);
					reQxmlorg.append(orgIdentifier); 
					reQxmlorg.append(customer);
					reQxmlorg.append(contmeth);
					reQxmlorg.append(crossindex);
					reQxmlorg.append(org);
					reQxmlorg.append(belongManager);
					reQxmlorg.append(belongBranch);
					reQxmlorg.append("</RequestBody></TransBody>\n");
					reqXml = reQxmlorg.toString();
					try {
						//����ECIF����
						Map map = process(reqXml);
						log.info("��ȡECIF�ͻ���:" + map);
						ecifId = map.get("custNo") != null ? map.get("custNo").toString() : "";
						identId =  map.get("identId") != null ? map.get("identId").toString() : "";
						belongManagerId =  map.get("belongManagerId") != null ? map.get("belongManagerId").toString() : "";
						belongBranchId =  map.get("belongBranchId") != null ? map.get("belongBranchId").toString() : "";
						if(ecifId != null && !ecifId.equals("")){
							//���浽customer,Ĭ�Ͽ���֤����20-������֯��������
							String customerInsert = " INSERT INTO ACRM_F_CI_CUSTOMER(CUST_ID,CUST_NAME,CUST_TYPE,POTENTIAL_FLAG,CREATE_TIME_LN,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER,IDENT_TYPE)"
									+ " VALUES('"+ ecifId + "','"+ custName + "','1','1',sysDate,'DMS',sysDate,'"+ custMgr + "','20')";
							baseDAO.batchExecuteNativeWithIndexParam(customerInsert);
							//���浽������
							String orgInsert = " INSERT INTO ACRM_F_CI_ORG(CUST_ID,CUST_NAME)"
									+ " VALUES('"+ ecifId + "','"+ custName + "')";
							baseDAO.batchExecuteNativeWithIndexParam(orgInsert);
							if(identId != null && !identId.equals("")){
								//���浽֤����,Ĭ�Ͽ���֤����20-������֯��������
								String identInsert = " INSERT INTO ACRM_F_CI_CUST_IDENTIFIER(IDENT_ID,CUST_ID,IDENT_TYPE)"
										+ " VALUES('"+identId+"','"+ ecifId + "','20')";
								baseDAO.batchExecuteNativeWithIndexParam(identInsert);
							}
							String belongMgrInsert = "";
							if(belongManagerId != null && !belongManagerId.equals("")){
								belongMgrInsert= " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR(ID,CUST_ID,MGR_ID,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
										+ " VALUES('"+belongManagerId+"','"+ ecifId + "','"+ custMgr + "','1'," +
										  " (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')," +
										  " (select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))," +
										  " (SELECT t.user_name FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))";
							}else{
							    belongMgrInsert = " INSERT INTO OCRM_F_CI_BELONG_CUSTMGR(ID,CUST_ID,MGR_ID,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME,MGR_NAME)"
										+ " VALUES(ID_SEQUENCE.NEXTVAL,'"+ ecifId + "','"+ custMgr + "','1'," +
										  " (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')," +
										  " (select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))," +
										  " (SELECT t.user_name FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'))";
							}
							//���浽���������
							baseDAO.batchExecuteNativeWithIndexParam(belongMgrInsert);
							//���浽����������
							String belongOrgInsert = "";
							if(belongBranchId != null && !belongBranchId.equals("")){
								 belongOrgInsert = " INSERT INTO OCRM_F_CI_BELONG_ORG(ID,CUST_ID,MAIN_TYPE,INSTITUTION_CODE,INSTITUTION_NAME)"
										+ " VALUES('"+belongBranchId+"','"+ ecifId + "','1',(SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'),(select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')))";
							}else{
								 belongOrgInsert = " INSERT INTO OCRM_F_CI_BELONG_ORG(ID,CUST_ID,MAIN_TYPE,INSTITUTION_CODE,INSTITUTION_NAME)"
										+ " VALUES(ID_SEQUENCE.NEXTVAL,'"+ ecifId + "','1',(SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "'),(select t.org_name from admin_auth_org t where t.org_id in (SELECT t.org_id FROM ADMIN_AUTH_ACCOUNT T WHERE T.ACCOUNT_NAME='"+ custMgr + "')))";
							}
							baseDAO.batchExecuteNativeWithIndexParam(belongOrgInsert);
							//���浽����������
							String crossIndexInsert = " INSERT INTO ACRM_F_CI_CROSSINDEX(CROSSINDEX_ID,SRC_SYS_NO,SRC_SYS_CUST_NO,CUST_ID)"
									+ " VALUES(sq_crossindex.nextval,'DMS','"+ custId + "','"+ ecifId + "')";
							baseDAO.batchExecuteNativeWithIndexParam(crossIndexInsert);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 	   }
		 }
	}
	
	/**
	 * ����ECIF����
	 * �ƶ��Ŵ�
	 */
	public static Map process(String mxlmsg) throws Exception {
		Map idsMap = new HashMap();
		log.info("ECIF����������:" + mxlmsg);
		String msg = mxlmsg;
//		String ip="127.0.0.1";
//		uat��ecif��ַ
		String ip = "10.20.34.108";
//		������ecif��ַ
//		String ip = "10.7.34.10";
		int port = 9500;
	
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(String.format("%08d",
					msg.getBytes("GBK").length)
					+ msg);
		} catch (IOException e) {
			log.info("����ECIFϵͳ��ʱ!");
		}
		log.info("ECIF�������ر���:" + resp);
		String custNo = "";
		String custId = "";
		String identId = "";
		String belongManagerId = "";
		String belongBranchId = "";
		/**
		 * �����ر���
		 * 
		 * @param xml
		 * @return
		 */
		try {
			resp = resp.substring(8);
			Document doc = DocumentHelper.parseText(resp);
			Element root = doc.getRootElement();
			String TxStatDesc = root.element("ResponseTail")
					.element("TxStatDesc").getTextTrim();
			String TxStatCode = root.element("ResponseTail")
					.element("TxStatCode").getTextTrim();
			if ("000000".equals(TxStatCode)) {
				// ���ؼ���������
				custNo = root.element("ResponseBody").element("custNo")
						.getTextTrim();
				if (root.element("ResponseBody").element("custId") != null) {
					custId = root.element("ResponseBody").element("custId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("identId") != null) {
					identId = root.element("ResponseBody").element("identId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("belongManagerId") != null) {
					belongManagerId = root.element("ResponseBody").element("belongManagerId")
							.getTextTrim();
				}
				if (root.element("ResponseBody").element("belongBranchId") != null) {
					belongBranchId = root.element("ResponseBody").element("belongBranchId")
							.getTextTrim();
				}
				idsMap.put("custNo", custNo);
				idsMap.put("custId", custId);
				idsMap.put("identId", identId);
				idsMap.put("belongManagerId", belongManagerId);
				idsMap.put("belongBranchId", belongBranchId);
				
			} else {
				log.info("����ECIF�����ӿ��쳣:"+TxStatDesc);
			}
		} catch (BizException e) {
			log.info("����ECIF�����ӿ��쳣1:"+e.getMessage());
		} catch (Exception e) {
			log.info("����ECIF�����ӿ��쳣2:"+e.getMessage());
		}
		return idsMap;
	}
}

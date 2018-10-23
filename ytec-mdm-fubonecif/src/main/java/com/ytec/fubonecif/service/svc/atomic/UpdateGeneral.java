/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.atomic
 * @�ļ�����UpdateGeneral.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:03:03
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.atomic;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

//import javax.swing.text.StyledEditorKit.BoldAction;
//import javax.xml.namespace.QName;

//import org.apache.axiom.om.OMElement;
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.addressing.EndpointReference;
//import org.apache.axis2.client.Options;
//import org.apache.axis2.rpc.client.RPCServiceClient;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiKeyinfoChange;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.mdm.base.bo.DataChangeModel;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.FieldChangeModle;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.GetKeyNameUtil;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.ObjectReturnMessage;
import com.ytec.mdm.service.component.biz.identification.GetCustomerName;
import com.ytec.mdm.service.component.biz.identification.GetObjectByBusinessKey;
import com.ytec.mdm.service.component.biz.redundance.UpdateRedundance;
import com.ytec.mdm.service.component.general.IdUtils;
import com.ytec.mdm.service.facade.IColumnUtils;
import com.ytec.mdm.service.facade.ICoveringRule;
import com.ytec.mdm.service.facade.IMCIdentifying;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�UpdateGeneral
 * @��������ͨ���޸�
 * @��������:Ϊ��������contId������,Ȼ�󱣴�����
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:03:04
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:03:04
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UpdateGeneral {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(UpdateGeneral.class);

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
		ICoveringRule cover = (ICoveringRule) SpringContextUtils.getBean(MdmConstants.COVERINGRULE);
		IColumnUtils columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
		UpdateRedundance updateRedundance = (UpdateRedundance) SpringContextUtils.getBean("updateRedundance");
		ObjectReturnMessage objMessage = new ObjectReturnMessage();
		boolean custNameChanged = false;// �����Ƿ��б仯
		boolean idenCustNameChanged = false;// ����֤�������Ƿ��б仯
		boolean isOpen = false;// �Ƿ񿪻�
		String custName = null;// ����
		String oldCustName = null;
		List infoList = ecifData.getWriteModelObj().getOpModelList();
		List<List> allUpdateRedandanceSqlList = new ArrayList();

		Map<String, Object> priavetResultMap = new HashMap<String, Object>();

		for (Object newObj : infoList) {
			// Ϊ��������CustId
			if (ecifData.getCustId() != null) {
				OIdUtils.setCustIdValue(newObj, ecifData.getCustId());
			}
			if (MdmConstants.TX_CODE_U.equals(ecifData.getTxType())) {
				objMessage = findObj.bizGetObject(newObj, false, true, ecifData.getOpChnlNo());
			} else {
				objMessage = findObj.bizGetObject(newObj, false, false, ecifData.getOpChnlNo());
			}

			if (objMessage.isSuccessFlag()) {
				// ������ڣ����ݸ���ԭ������һ�����µĶ���Ȼ����¸�����
				Object oldObj = objMessage.getObject();
				if (newObj.getClass().equals(MCiIdentifier.class)) {
					MCiIdentifier identifier = (MCiIdentifier) newObj;
					MCiIdentifier oldIdentifier = (MCiIdentifier) oldObj;

					custName = identifier.getIdentCustName();
					if (custName != null && !custName.isEmpty() && !custName.equals(oldIdentifier.getIdentCustName())) {
						custNameChanged = true;
						idenCustNameChanged = true;
					}

				}

				/**
				 * ��ȡ�޸������ֶ���Ϣsql������ȫ�����������һ��ִ��update
				 */
				// Object oldObjBak = new Object(); //�����޸�ǰ����
				// BeanUtils.copyProperties(oldObj, oldObjBak);
				List<Map> sqlList = updateRedundance.getRedundanceSql(ecifData, newObj, oldObj);
				allUpdateRedandanceSqlList.add(sqlList);

				newObj = cover.cover(ecifData, oldObj, newObj);
				if (newObj != null) {
					newObj = columnUtils.setUpdateGeneralColumns(ecifData, newObj);
					if (MdmConstants.isSaveHistory) {
						Object hisObj = null;
						if (!MdmConstants.chooseSaveHistory || BusinessCfg.isSaveHisObj(oldObj.getClass().getSimpleName())) {
							hisObj = columnUtils.toHistoryObj(oldObj, ecifData.getOpChnlNo(), MdmConstants.HIS_OPER_TYPE_U);
						}
						if (hisObj != null) {
							baseDAO.persist(hisObj);
						}
					}
					baseDAO.merge(newObj);
					
					/**
					 * ����Ӧ���Ķ�������Ӽ�������ֵ
					 */
					String keyName = GetKeyNameUtil.getInstance().getKeyName(newObj);
					String keyValue = (String) ReflectionUtils.getFieldValue(newObj, keyName);
					log.info("����Ӧ��������ӷ�����Ϣ[{}-->>{}={}]", newObj.getClass().getSimpleName(), keyName, keyValue);
					priavetResultMap.put(keyName, keyValue);
				}
			} else {
				if (MdmConstants.TX_CODE_U.equals(ecifData.getTxType()) && ecifData.getWriteModelObj().isDivInsUpd()) {
					String msg = newObj.getClass().getSimpleName() + "�пͻ���Ϣ������";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_OTHER.getCode(), msg);
					throw new Exception(msg);
				}
				if (ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode().equals(objMessage.getError().getCode())) {
					if (MdmConstants.existBusinesskeyError) {
						String nodeName = newObj.getClass().getSimpleName().substring(3, 4).toLowerCase() + newObj.getClass().getSimpleName().substring(4);
						String msg = String.format("�ڵ�(%s)������Ϣ����(ҵ����Ϣ�����)", nodeName);
						log.error(msg);
						ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode(), msg);
						throw new Exception(msg);
					} else {
						continue;
					}
				}
				if (ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode().equals(objMessage.getError().getCode())) {
					String nodeName = newObj.getClass().getSimpleName().substring(3, 4).toLowerCase() + newObj.getClass().getSimpleName().substring(4);
					String msg = String.format("ECIF�����ڽڵ�(%s)�м�������Ӧ������", nodeName);
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode(), msg);
					throw new Exception(msg);
				}
				if (ErrorCode.ERR_ALL.getCode().equals(objMessage.getError().getCode())) {
					ecifData.setStatus(objMessage.getError().getCode(), objMessage.getError().getChDesc());
					throw new Exception(objMessage.getError().getChDesc());
				}

				/***
				 * ��������
				 */
				String custNameTmp = null;
				if (newObj.getClass().equals(MCiIdentifier.class)) {
					MCiIdentifier identifier = (MCiIdentifier) newObj;
					custNameTmp = identifier.getIdentCustName();
				}
				if (custNameTmp != null) {
					if (!isOpen && oldCustName == null) {
						GetCustomerName getCustomerName = (GetCustomerName) SpringContextUtils.getBean("getCustomerName");
						oldCustName = getCustomerName.bizGetCustName(ecifData.getCustId(MdmConstants.CUSTID_TYPE));
						if (oldCustName == null) {
							isOpen = true;
						}
					}
					if (!isOpen && !custNameTmp.equals(oldCustName)) {
						custName = custNameTmp;
						custNameChanged = true;
						idenCustNameChanged = true;
					}
				}
				newObj = OIdUtils.createId(newObj);
				newObj = columnUtils.setCreateGeneralColumns(ecifData, newObj);
				baseDAO.persist(newObj);

				/**
				 * ����Ӧ���Ķ�������Ӽ�������ֵ
				 */
				String keyName = GetKeyNameUtil.getInstance().getKeyName(newObj);
				String keyValue = (String) ReflectionUtils.getFieldValue(newObj, keyName);
				log.info("����Ӧ��������ӷ�����Ϣ[{}-->>{}={}]", newObj.getClass().getSimpleName(), keyName, keyValue);
				priavetResultMap.put(keyName, keyValue);
			}
		}

		/**
		 * ȫ���޸Ķ�����ɺ������޸ĵ�sql����UpdateByGeneral���������ֶ��޸�
		 */
		Map needUpdateMap = new HashMap();
		for (List<Map> sqlList : allUpdateRedandanceSqlList) {
			for (Map map : sqlList) { // ��һ�����������ֶν��и���
				String jql = (String) map.get("jql");
				Object newValue = map.get("newValue");
				if (!needUpdateMap.containsKey(jql)) { // ȥ���ظ����µ����
					needUpdateMap.put(jql, newValue);
				}
			}
		}
		ecifData.getParameterMap().put("redundances", needUpdateMap);

		if (custNameChanged) {
			String jql = null;
			Map paramMap = new HashMap();
			paramMap.put("custName", custName);
			paramMap.put("contId", ecifData.getCustId(MdmConstants.CUSTID_TYPE));
			paramMap.put("updateTm", new Timestamp(System.currentTimeMillis()));
			paramMap.put("updateSrcSysCd", ecifData.getOpChnlNo());
			if (idenCustNameChanged) {
				// jql = "update MCiNametitle i set i.custName=:custName,i.lastUpdateTm=:updateTm,i.lastUpdateSys=:updateSrcSysCd where i.custId=:contId";
				// if (custName != null && !custName.isEmpty()) {
				// baseDAO.batchExecuteWithNameParam(jql, paramMap);
				// log.info("{}�����б仯",ecifData.getCustId());
				// }
			}
			String identifierTab = null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())) {
				identifierTab = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierTab = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			jql = "update " + identifierTab + " i set i.identCustName=:custName,i.lastUpdateTm=:updateTm,i.lastUpdateSys=:updateSrcSysCd where i.custId=:contId";
			if (custName != null && !custName.isEmpty()) {
				baseDAO.batchExecuteWithNameParam(jql, paramMap);
				log.info("{}�����б仯", ecifData.getCustId());
			}

		}
		baseDAO.flush();
		ecifData.resetStatus();

		/**
		 * ���ô�����������ɵ���Ӧ��Ϣ���Թ���Ӧ����ƴװ
		 */
		Set<String> keySet = priavetResultMap.keySet();
		Iterator itr = keySet.iterator();
		while (itr.hasNext()) {
			String key = itr.next().toString();
			Object value = priavetResultMap.get(key);
			ecifData.getWriteModelObj().setResult(key, value);
		}
		return;
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void updateRedundance(EcifData ecifData) throws Exception {

		Map needUpdateMap = (Map) ecifData.getParameterMap().get("redundances");

		log.info("@@@��ʼͬ�������ֶΡ�����");
		Iterator i = needUpdateMap.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry e = (Map.Entry) i.next();
			log.info(e.getKey().toString());
			baseDAO.batchExecuteWithIndexParam(e.getKey().toString(), e.getValue(), ecifData.getCustId());// �޸������ֶ�
		}
		if("updatePerCustInfo".equals(ecifData.getTxCode())){//CRM�޸ĸ��˿ͻ���Ϣ
			List generalInfoList=ecifData.getWriteModelObj().getOpModelList();
			String custId=ecifData.getCustId();
			//Map<String, String> paraValues = new HashMap<String, String>();
			Element body=ecifData.getBodyNode();
			String sql="select t.CONTMETH_INFO from M_CI_CONTMETH t where t.CONTMETH_TYPE='501' and t.CUST_ID='"+custId+"'";
			//List<MCiContmeth> list=baseDAO.findByNativeSQLWithIndexParam(sql, null);
			for (Object newObj : generalInfoList){
					String email=null;
					if(newObj.getClass().equals(MCiPerson.class)){//�޸���person��email��������ϵ��501
						MCiPerson person=(MCiPerson)newObj;
						email=person.getEmail();
						
					List<Object> contmethList=baseDAO.findByNativeSQLWithIndexParam(sql, null);//��ѯ��ϵ����û��'501'
					if(contmethList != null && contmethList.size() > 0){//��ϵ�����'501'�������ֶ�ֵ
						if(email != null && !"".equals(email)){
						//String custId=ecifData.getCustId();
		 				sql="update M_CI_CONTMETH t set t.CONTMETH_INFO='"+email+"',t.LAST_UPDATE_SYS='CRM',t.LAST_UPDATE_TM=sysdate where t.CONTMETH_TYPE='501' and t.CUST_ID='"+custId+"'";
						baseDAO.batchExecuteNativeWithIndexParam(sql, null);	
						}
					}else{//��ϵ��û��'501'��������¼
						IMCIdentifying util = (IMCIdentifying) SpringContextUtils.getBean(MdmConstants.MCIDENTIFYING);
						String contmethId=util.getPriIdByAttrName("CONTMETH_ID");
						sql=" insert into M_CI_CONTMETH (CONTMETH_ID,CUST_ID,CONTMETH_INFO,CONTMETH_TYPE,IS_PRIORI,REMARK,STAT,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TM,TX_SEQ_NO) values" 
						+ "(SEQ_CONTMETH_ID.NEXTVAL,'"+custId+"','"+email+"','501','9',null,'1','CRM','ETL',sysdate,null)";
						baseDAO.batchExecuteNativeWithIndexParam(sql,null);
						}
					}
					String contmethInfo=null;
					if(newObj.getClass().equals(MCiContmeth.class)){//�޸���'��ϵ��501'������person���email
						MCiContmeth contmeth=(MCiContmeth)newObj;
						
						if("501".equals(contmeth.getContmethType())){
							contmethInfo=contmeth.getContmethInfo();
						}
						if(contmethInfo !=null && !"".equals(contmethInfo)){
						    sql="update M_CI_PERSON t set t.EMAIL='"+contmethInfo+"',t.LAST_UPDATE_SYS='CRM',t.LAST_UPDATE_TM=sysdate where t.CUST_ID='"+custId+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql, null);
						}
					}
			}
			//crmɾ���ֶ���Ϣ
			if(body.selectSingleNode("contmeth") != null){
				List<Element> contInfos=body.elements("contmeth");
				for (Element contInfo : contInfos) {
					if(contInfo.element("contmethId") != null && !"".equals(contInfo.element("contmethId"))){//����contmethId�ڵ㣬���������ϵ��ʽ����������ڵ㣬����û��ֵ��
						String contmethId=contInfo.element("contmethId").getTextTrim();                      //ͬʱ��ϵ��Ϣ�϶�����ֵ�������߼���ʱû��
						String contmethInfo=(contInfo.element("contmethInfo") == null || "".equals(contInfo.element("contmethInfo"))) ? "" : contInfo.element("contmethInfo").getTextTrim();
					
						if(contmethInfo==null || "".equals(contmethInfo)){
							
							updateContAndAddrRecord(custId,contmethId,"M_CI_CONTMETH");
						}	
					}
				}	 
			}//��ȡ��ϵ��Ϣ����
			if(body.selectSingleNode("address") != null){
				List<Element> addrInfos=body.elements("address");
				for (Element addrInfo : addrInfos) {
					if(addrInfo.element("addrId")!=null && !"".equals(addrInfo.element("addrId"))){//����addrId�ڵ�
						String addrId=addrInfo.element("addrId").getTextTrim();
						String addr=(addrInfo.element("addr") == null || "".equals(addrInfo.element("addr"))) ? "" : addrInfo.element("addr").getTextTrim();
						
						if(addr==null || "".equals(addr)){
							
							updateContAndAddrRecord(custId,addrId,"M_CI_ADDRESS");
						}
				   }
				}
			}//��ȡ��ַ��Ϣ����
		}//crm�޸ĸ�����Ϣ�������
		if("updateCustBaseInfo".equals(ecifData.getTxCode())){//�����޸Ŀͻ�������Ϣ
			List generalInfoList=ecifData.getWriteModelObj().getOpModelList();
			String custId=ecifData.getCustId();
			//String contmethInfo=null;
			Map<String, String> paraValues = new HashMap<String, String>();
			Element body=ecifData.getBodyNode();
			for (Object newObj : generalInfoList){
				
				if(newObj.getClass().equals(MCiContmeth.class)){//��ȡ�޸���غ����ϵ��Ϣ
					MCiContmeth contmeth=(MCiContmeth)newObj;
					if("501".equals(contmeth.getContmethType())){
						String contmethInfo=contmeth.getContmethInfo();
						if(contmethInfo !=null && !"".equals(contmethInfo)){
							String sql="update M_CI_PERSON t set t.EMAIL='"+contmethInfo+"',t.LAST_UPDATE_SYS='WY',t.LAST_UPDATE_TM=sysdate where t.CUST_ID='"+custId+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql, null);
							}
						}
				}
			}
			if(body.selectSingleNode("contmeth") != null){
				List<Element> contInfos=body.elements("contmeth");
				for (Element contInfo : contInfos) {
					String contmethType=contInfo.element("contmethType").getTextTrim();
					String contmethInfo=(contInfo.element("contmethInfo") == null || "".equals(contInfo.element("contmethInfo"))) ? "" : contInfo.element("contmethInfo").getTextTrim();
					
					if(contmethInfo==null || "".equals(contmethInfo)){
						
						deleteContmethRecord(custId,contmethType,paraValues);
					}
					
				}	 
			}//��ȡ��ϵ��Ϣ����
			if(body.element("customer")!=null && !"".equals(body.element("customer"))){//����customer�ڵ�
				if((body.element("customer").element("shortName")!=null && !"".equals(body.element("customer").element("shortName"))) 
						&& (body.element("customer").element("shortName").getTextTrim()==null ||
						"".equals(body.element("customer").element("shortName").getTextTrim()))){//�ͻ����
					String sql="update M_CI_CUSTOMER t set t.SHORT_NAME=null where t.CUST_ID='"+custId+"'";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				if((body.element("customer").element("enName")!=null && !"".equals(body.element("customer").element("enName"))) 
						&& (body.element("customer").element("enName").getTextTrim()==null || 
						"".equals(body.element("customer").element("enName").getTextTrim()))){//Ӣ������
					String sql="update M_CI_CUSTOMER t set t.EN_NAME=null where t.CUST_ID='"+custId+"'";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
			if(body.element("person")!=null && !"".equals(body.element("person"))){//����person�ڵ�
				if((body.element("person").element("pinyinName")!=null && !"".equals(body.element("person").element("pinyinName")))
						&& (body.element("person").element("pinyinName").getTextTrim()==null ||
						"".equals(body.element("person").element("pinyinName").getTextTrim()))){
					String sql="update M_CI_PERSON t set t.PINYIN_NAME=null where t.CUST_ID='"+custId+"'";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				if((body.element("person").element("nickName")!=null && !"".equals(body.element("person").element("nickName")))
						&& (body.element("person").element("nickName").getTextTrim()==null ||
						"".equals(body.element("person").element("nickName").getTextTrim()))){
					String sql="update M_CI_PERSON t set t.NICK_NAME=null where t.CUST_ID='"+custId+"'";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				if((body.element("person").element("unitFex")!=null && !"".equals(body.element("person").element("unitFex")))
						&& (body.element("person").element("unitFex").getTextTrim()==null ||
						"".equals(body.element("person").element("unitFex").getTextTrim()))){
					String sql="update M_CI_PERSON t set t.UNIT_FEX=null where t.CUST_ID='"+custId+"'";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
				
			}
			
		}//�����޸Ĵ������
		
		//ͨ���޸�ͬ������ƽ̨ҵ���ֻ���
		String custId=ecifData.getCustId();
		MCiCustomer customer=(MCiCustomer) baseDAO.findUniqueWithIndexParam1("FROM MCiCustomer where custId=?", custId);
		MCiContmeth contmeth=(MCiContmeth) baseDAO.findUniqueWithIndexParam1("FROM MCiContmeth where custId=? and contmethType='102'", custId);
		String contmethInfo=null;
		if(contmeth != null && !"".equals(contmeth)){
			contmethInfo=contmeth.getContmethInfo();
		}
		if(contmethInfo != null && !"".equals(contmethInfo)){
			//�ں������ʱ�����ͬ����������ע�͵���
			if(customer != null && !"".equals(customer)){
				String coreNo=customer.getCoreNo();
				String sql="update ebt_user_mobile@Sms t set t.mobilephone='"+contmethInfo+"' where t.custid='"+coreNo+"'";
//				baseDAO.batchExecuteNativeWithIndexParam(sql,null);
			}
		}
		
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void saveHist(EcifData ecifData) throws Exception {
		if (BusinessCfg.getBoolean("doChangeLog")) {
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			if(ecifData.getDataSynchro() == null){
				log.debug("�ͻ��ؼ���Ϣ�ޱ���������¼������");
				return;
			}
			log.info("�ͻ�[{}]��Ϣ���������ʷ��¼", ecifData.getCustId());
			for (Object o : ecifData.getDataSynchro()) {
				log.info(o.toString());
				DataChangeModel ch = (DataChangeModel) o;
				List<FieldChangeModle> fields = (List<FieldChangeModle>) ch.getFieldChangeList();
				for (FieldChangeModle field : fields) {
					MCiKeyinfoChange change = new MCiKeyinfoChange();
					change = (MCiKeyinfoChange) OIdUtils.createId(change);
					change.setCustId(ecifData.getCustId());
					change.setChangeType(ch.getEntityName());
					change.setInfoName(field.getFieldName());
					change.setBeforeInfo(field.getOldVal() == null ? null : field.getOldVal().toString());
					change.setAfterInfo(field.getNewval() == null ? null : field.getNewval().toString());
					change.setChangeDate(new Date());
					change.setChangeTime(new Timestamp((new Date()).getTime()));
					change.setLastUpdateSys(ecifData.getOpChnlNo());
					change.setLastUpdateUser(ecifData.getTlrNo());
					change.setTxSeqNo(ecifData.getReqSeqNo());
					// TODO �����죬Ŀǰ��Ч�����ݿ�����������
					baseDAO.save(change);
				}
				baseDAO.flush();
			}
		}
	}
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void updateContAndAddrRecord(String custId,String fieldType,String tableName){
		
		if("M_CI_CONTMETH".equals(tableName)){
			String sql=null;
//			if("501".equals(fieldType)){
//				sql="update M_CI_CONTMETH t set t.CONTMETH_INFO=null,t.LAST_UPDATE_SYS='CRM',t.LAST_UPDATE_TM=sysdate where t.CONTMETH_TYPE='"+fieldType+"' and t.CUST_ID='"+custId+"'";
//				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
//				sql="update M_CI_PERSON t set t.EMAIL=null,t.LAST_UPDATE_SYS='CRM',t.LAST_UPDATE_TM=sysdate where t.CUST_ID='"+custId+"'";
//				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
//			}else{
//				sql="update M_CI_CONTMETH t set t.CONTMETH_INFO=null,t.LAST_UPDATE_SYS='CRM',t.LAST_UPDATE_TM=sysdate where t.CONTMETH_TYPE='"+fieldType+"' and t.CUST_ID='"+custId+"'";
//				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
//			}
			sql="update M_CI_CONTMETH t set t.CONTMETH_INFO=null,t.LAST_UPDATE_SYS='CRM',t.LAST_UPDATE_TM=sysdate where t.CONTMETH_ID='"+fieldType+"' and t.CUST_ID='"+custId+"'";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
		if("M_CI_ADDRESS".equals(tableName)){
			
			String sql="update M_CI_ADDRESS t set t.ADDR=null,t.LAST_UPDATE_SYS='CRM',t.LAST_UPDATE_TM=sysdate where t.ADDR_ID='"+fieldType+"' and t.CUST_ID='"+custId+"'";
			baseDAO.batchExecuteNativeWithIndexParam(sql, null);
		}
	}
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void deleteContmethRecord(String custId,String contmethType,Map<String, String> paraValues){
		
		paraValues.put("custId", custId);
		paraValues.put("contmethType", contmethType);
		MCiContmeth ecifContmeth= (MCiContmeth) baseDAO.findUniqueWithNameParam("from MCiContmeth where custId=:custId and contmethType=:contmethType ", paraValues);
		//��ϵ��ʽ����
		String deleteItem=null;
		if("209".equals(contmethType)){
			deleteItem="�����ֻ�����";
		}else if("2031".equals(contmethType)){
			deleteItem="��ͥ�绰";
		}else if("2041".equals(contmethType)){
			deleteItem="�칫�绰";
		}else if("500".equals(contmethType)){
			deleteItem="��ϵ����";
		}
		String ecifContInfo=ecifContmeth.getContmethInfo();
		String lastUpdateSys=ecifContmeth.getLastUpdateSys();//���W�y����������ӛ䛣�CRM����null���ֶ�ֵ
			if((ecifContInfo!=null && !"".equals(ecifContInfo))){
				String sql="insert into M_CI_CUSTINFO_DELETEHIS(DELETE_DATE,DELETE_TTEM_CODE,DELETE_ITEM,DELETE_CONT,DELETE_ITEM_EN,DELETE_TABLE,DELETE_USER,CUST_ID,DELETE_ID)" 
						+"values(sysdate,'"+contmethType+"','"+deleteItem+"','"+ecifContInfo+"','CONTMETH_INFO','M_CI_CONTMETH','"+custId+"','"+custId+"',SEQ_DELETE_ID.NEXTVAL)";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				sql="delete from M_CI_CONTMETH t where t.CONTMETH_TYPE='"+contmethType+"' and t.CUST_ID='"+custId+"'";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
			if((ecifContInfo==null || "".equals(ecifContInfo)) && ("WY".equals(lastUpdateSys) || "CRM".equals(lastUpdateSys))){
				String sql="delete from M_CI_CONTMETH t where t.CONTMETH_TYPE='"+contmethType+"' and t.CUST_ID='"+custId+"'";
				baseDAO.batchExecuteNativeWithIndexParam(sql, null);
			}
	}
//	@SuppressWarnings("unused")
//	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
//	public void callWebService(EcifData ecifData) throws AxisFault{
//		
//		String coreNo=null;
//		String custName=null;
//		String identType=null;
//		String identNo=null;
//		String custType=null;
//		String phoneNo=null;
//		String custId=ecifData.getCustId();
//		//List custInfoList = ecifData.getWriteModelObj().getOpModelList();
//		Properties properties=new Properties();
//		try{
//			InputStream inputStream=UpdateGeneral.class.getResourceAsStream("/webService.properties");
//			properties.load(inputStream);
//			inputStream.close(); // �ر���
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		String url=properties.getProperty("url");
//		log.info("url---------->"+url);

//		String sql="select t.CORE_NO,t.CUST_NAME,t.IDENT_NO,t.CUST_TYPE,t.IDENT_TYPE from M_CI_CUSTOMER t where t.CUST_ID='"+custId+"'";
//		List<Object[]> custInfoList=baseDAO.findByNativeSQLWithIndexParam(sql,null);
//		if(custInfoList != null && custInfoList.size()>0){
//			for(int i=0;i<custInfoList.size();i++){
//				
//				coreNo=(String) custInfoList.get(0)[0];
//				custName=(String) custInfoList.get(0)[1];
//				identNo=(String) custInfoList.get(0)[2];
//				custType=(String) custInfoList.get(0)[3];
//				identType=(String) custInfoList.get(0)[4];
//			}
//		}
//		List<MCiContmeth> contList=baseDAO.findWithIndexParam("from MCiContmeth where custId=?" , custId);
//		if(contList != null && contList.size()>0){
//			for(MCiContmeth cont:(List<MCiContmeth>)contList){
//				if("102".equals(cont.getContmethType())){
//					phoneNo=cont.getContmethInfo();
//					break;
//				}
//			}
//		}
//		// axis2 �����
//		//String url = "http://10.20.35.131/WebConvertMD5/ws_ConvertMD5.asmx";
//		// ʹ��RPC��ʽ����WebService
//		RPCServiceClient serviceClient = new RPCServiceClient();
//		// ָ������WebService��URL
//		EndpointReference targetEPR = new EndpointReference(url);
//		Options options = serviceClient.getOptions();
//		// ȷ��Ŀ������ַ
//		options.setTo(targetEPR);
//		// ȷ�����÷���
//		options.setAction("http://tempuri.org/Fun_SMSBankSetting");
//		QName qname = new QName("http://tempuri.org/", "Fun_SMSBankSetting");
//		
////		String[] para = { "700000009131", "֣����", "T122629962", null, null,
////				null, "1", "X2", "18610928269", null, null, null, null, null };
//		String[] para = { coreNo, custName, identNo, null, null,
//				null, custType, identType, phoneNo, null, null, null, null, null };
//		Object[] parameters = new Object[] { "1", para };
//		//Class[] result = new Class[] { String.class };
//		OMElement element = serviceClient.invokeBlocking(qname, parameters);
//		//Object[] response = serviceClient.invokeBlocking(qname, parameters,result);
//		String result = element.getFirstElement().getText();
//	
//		System.out.println(result);
//		//log.info("--------->"+response[0]);
//		
//	}
}

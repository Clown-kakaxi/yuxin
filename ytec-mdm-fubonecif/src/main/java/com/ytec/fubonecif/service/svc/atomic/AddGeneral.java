/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.atomic
 * @�ļ�����AddGeneral.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:01:52
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.atomic;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.fubonecif.domain.MCiBelongBranch;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.GetKeyNameUtil;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.ObjectReturnMessage;
import com.ytec.mdm.service.component.biz.identification.GetCustomerName;
import com.ytec.mdm.service.component.biz.identification.GetObjectByBusinessKey;
import com.ytec.mdm.service.facade.IColumnUtils;
import com.ytec.mdm.service.facade.ICoveringRule;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AddGeneral
 * @��������ͨ�ñ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:01:53
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:01:53
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AddGeneral {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(AddGeneral.class);

	private Map<String, Object> resultMap = new HashMap<String, Object>();

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
		ICoveringRule cover = (ICoveringRule) SpringContextUtils.getBean(MdmConstants.COVERINGRULE);
		IColumnUtils columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
		ObjectReturnMessage objMessage = new ObjectReturnMessage();
		boolean custNameChanged = false;// �����Ƿ��б仯
		boolean idenCustNameChanged = false;// ����֤�������Ƿ��б仯
		String custName = null;// ����
		String oldCustName = null;
		boolean isOpen = false;// �Ƿ񿪻�
		List infoList = ecifData.getWriteModelObj().getOpModelList();
		
		//add begin by liuming 20170717
		boolean isPotential = false;//�Ƿ�ΪǱ�ڿͻ�
		String selectSql="select 1 from M_CI_CUSTOMER t where t.potential_flag='1' and t.cust_id='"+ecifData.getCustId()+"'";
		List list = baseDAO.findByNativeSQLWithIndexParam(selectSql, null);
		if(list != null && list.size() >0){
			isPotential = true;
		}
		//add end 
		
		for (Object newObj : infoList) {

			log.info("�������[{}]����", newObj.getClass().getSimpleName());

			// Ϊ��������CustId
			if (ecifData.getCustId() != null) {
				OIdUtils.setCustIdValue(newObj, ecifData.getCustId());
			}

			/**
			 * ���ݽ������ͣ�����ͬ����Ϣʶ��
			 */
			if (MdmConstants.TX_CODE_U.equals(ecifData.getTxType())) {
				objMessage = findObj.bizGetObject(newObj, false, true, ecifData.getOpChnlNo());
			} else {
				objMessage = findObj.bizGetObject(newObj, false, false, ecifData.getOpChnlNo());
			}

			/**
			 * ������ڣ����ݸ���ԭ������һ�����µĶ���Ȼ����¸�����
			 */
			if (objMessage.isSuccessFlag()) {
				Object oldObj = objMessage.getObject();

				/**
				 * ��Ժ��Ŀͻ���У��
				 * wangtb@yuchengtech.com
				 * 20141110
				 */
				if (newObj.getClass().equals(MCiCustomer.class)) {
					MCiCustomer newCust = (MCiCustomer) newObj;
					MCiCustomer oldCust = (MCiCustomer) oldObj;

					if (MdmConstants.SRC_SYS_CD_CB.equals(ecifData.getOpChnlNo())) {
						if (newCust.getCoreNo() == null) {
							String msg = String.format("����ϵͳ(%s)������������Ŀͻ���(coreNo)Ϊ��", ecifData.getOpChnlNo());
							log.error(msg);
							ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_SRCSYSCUSTNO.getCode(), msg);
							throw new Exception(msg);
						}
						if (!StringUtils.isEmpty(oldCust.getCoreNo()) && !newCust.getCoreNo().equals(oldCust.getCoreNo())) {
							String msg = String.format("�´�����Ŀͻ���(%s)����к��Ŀͻ���(%s)��һ��", newCust.getCoreNo(),oldCust.getCoreNo());
							log.error(msg);
							ecifData.setStatus(ErrorCode.ERR_ECIF_INVALID_SRCCUSTNO.getCode(), msg);
							throw new Exception(msg);
						}
					}
				}//���Ŀͻ���У�����
				
				if (newObj.getClass().equals(MCiIdentifier.class)) {
					MCiIdentifier identifier = (MCiIdentifier) newObj;
					MCiIdentifier odlIdentifier = (MCiIdentifier) oldObj;
					custName = identifier.getIdentCustName();
					if (custName != null && !custName.isEmpty() && !custName.equals(odlIdentifier.getIdentCustName())) {
						custNameChanged = true;
						idenCustNameChanged = true;
					}
				}
				
				//add by liuming 20170717
				//Ǳ�ڿͻ�����ʱ�������ͻ�����͹���������crm����Ϊ׼
				if(isPotential && 
						(newObj.getClass().equals(MCiBelongManager.class) 
								|| newObj.getClass().equals(MCiBelongBranch.class))){
					continue;
				}
				//add end 
				
				newObj = cover.cover(ecifData, oldObj, newObj);
				if (newObj != null) {
					newObj = columnUtils.setUpdateGeneralColumns(ecifData, newObj);
					if (MdmConstants.isSaveHistory) {

						log.info("��¼����ǰ��ʷ���ݣ����ݶ���:{}", newObj.getClass().getSimpleName());

						Object hisObj = null;
						if (!MdmConstants.chooseSaveHistory || BusinessCfg.isSaveHisObj(oldObj.getClass().getSimpleName())) {
							hisObj = columnUtils.toHistoryObj(oldObj, ecifData.getOpChnlNo(), MdmConstants.HIS_OPER_TYPE_U);
						}
						if (hisObj != null) {
							baseDAO.persist(hisObj);
							// TODO
						}
					}
					baseDAO.merge(newObj);

					/**
					 * ����Ӧ���Ķ�������Ӽ�������ֵ
					 */
					String keyName = GetKeyNameUtil.getInstance().getKeyName(newObj);
					String keyValue = (String) ReflectionUtils.getFieldValue(newObj, keyName);
					log.info("����Ӧ��������ӷ�����Ϣ[{}-->>{}={}]", newObj.getClass().getSimpleName(), keyName, keyValue);
					ecifData.getWriteModelObj().setResult(keyName, keyValue);
				}
			}
			/**
			 * ��������ڼ�¼������
			 */
			else {
				if (ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode().equals(objMessage.getError().getCode())) {
					if (MdmConstants.existBusinesskeyError) {
						String msg = objMessage.getError().getChDesc();// newObj.getClass().getSimpleName() + "����������������:ҵ����Ϣ��Ϊ��";
						log.error(msg);
						ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode(), msg);
						throw new Exception(msg);
					} else {
						continue;
					}
				}
				if (ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode().equals(objMessage.getError().getCode())) {
					String msg = objMessage.getError().getChDesc();// newObj.getClass().getSimpleName() + "������������:ECIF�����ڸ�����";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode(), msg);
					throw new Exception(msg);
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
				ecifData.getWriteModelObj().setResult(keyName, keyValue);
			}
		}
		if (custNameChanged) {
			String jql = null;
			Map paramMap = new HashMap();
			paramMap.put("custName", custName);
			paramMap.put("contId", ecifData.getCustId(MdmConstants.CUSTID_TYPE));
			paramMap.put("updateTm", new Timestamp(System.currentTimeMillis()));
			paramMap.put("updateSrcSysCd", ecifData.getOpChnlNo());
			if (idenCustNameChanged) {
				jql = "update MCiCustomer i set i.custName=:custName,i.lastUpdateTm=:updateTm,i.lastUpdateSys=:updateSrcSysCd where i." + MdmConstants.CUSTID + "=:contId";
				if (custName != null && !custName.isEmpty()) {
					baseDAO.batchExecuteWithNameParam(jql, paramMap);
					log.info("{}�����б仯", ecifData.getCustId());
				}
			}
			String identifierTab = null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())) {
				identifierTab = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierTab = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			jql = "update " + identifierTab + " i set i.identCustName=:custName,i.lastUpdateTm=:updateTm,i.lastUpdateSys=:updateSrcSysCd where i." + MdmConstants.CUSTID + "=:contId";
			if (custName != null && !custName.isEmpty()) {
				baseDAO.batchExecuteWithNameParam(jql, paramMap);
				log.info("{}�����б仯", ecifData.getCustId());
			}
		}
		baseDAO.flush();
		ecifData.resetStatus();
		return;
	}
}

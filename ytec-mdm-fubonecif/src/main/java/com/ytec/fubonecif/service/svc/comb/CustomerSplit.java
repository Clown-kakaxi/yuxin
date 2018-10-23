/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����CustomerSplit.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:03:49
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.mdm.service.component.general.CustStatusMgr;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.mdm.service.svc.atomic.CustSuspectRecord;
import com.ytec.fubonecif.domain.MCiCustomer;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CustomerSplit
 * @���������ͻ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:03:50   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:03:50
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomerSplit implements IEcifBizLogic
{
	private static Logger log = LoggerFactory
			.getLogger(CustomerSplit.class);
	public void process(EcifData ecifData){
		String custType;
		String reserveCustNo;
		Object reserveCustId;
		/**�����ͻ�**/
		String custNo=ecifData.getWriteModelObj().getOperMapValue("custNo");
		/**��ֿͻ�**/
		String suspectCustNo=ecifData.getWriteModelObj().getOperMapValue("suspectCustNo");
		// �����ͻ�ʶ��
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO, custNo);
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, custNo);
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()){
			if(MdmConstants.checkCustomerStatus){
				/*****
				 * �ͻ�״̬�ж�
				 */
				CustStatus custStatCtrl=null;
				if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus(ecifData.getCustStatus()))!=null){
					if(!custStatCtrl.isNormal()){
						if(!custStatCtrl.isUpdate()){
							log.warn("�ͻ�({})״̬{}",ecifData.getCustId(),custStatCtrl.getCustStatusDesc());
							ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"�����ͻ�%s״̬:%s", custNo,custStatCtrl.getCustStatusDesc());
							return;
						}
					}
				}
			}
			custType=ecifData.getCustType();
//			reserveCustNo=ecifData.getEcifCustNo();
			reserveCustNo=ecifData.getCustId();
			reserveCustId=ecifData.getCustId(MdmConstants.CUSTID_TYPE);
		}else{
			log.warn("�����ͻ�({}){}",custNo,ecifData.getDetailDes());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_MERGE_RULE.getCode(),"�����ͻ�%s%s", custNo,ecifData.getDetailDes());
			return;
		}
		// ��ֿͻ�ʶ��
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO, suspectCustNo);
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, suspectCustNo);
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()){
			if(!MdmConstants.COMBINE_FLAG.equals(ecifData.getCustStatus())){
				log.warn("��ֿͻ�({})״̬��Ϊ�Ѻϲ�",suspectCustNo);
				ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"��ֿͻ�%s״̬�����Ѻϲ�",suspectCustNo);
				return;
			}
			if(!custType.equals(ecifData.getCustType())){
				log.warn("��ֿͻ�({})�ͻ������뱣���ͻ���һ��",ecifData.getCustId());
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_MERGE_RULE.getCode(),"��ֿͻ�%s�ͻ������뱣���ͻ���һ��", suspectCustNo);
				return;
			}
		}else{
			log.warn("��ֿͻ�({}){}",suspectCustNo,ecifData.getDetailDes());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_MERGE_RULE.getCode(),"��ֿͻ�%s%s", suspectCustNo,ecifData.getDetailDes());
			return;
		}
		
		AddGeneral add = (AddGeneral) SpringContextUtils.getBean("addGeneral");
		try{
			MCiCustomer customer =  new MCiCustomer();
			customer.setCustStat(MdmConstants.STATE);
			ecifData.getWriteModelObj().setOpModelList(customer);
			add.process(ecifData);
//			ecifData.getWriteModelObj().setResult("suspectCustNo", ecifData.getEcifCustNo());
			ecifData.getWriteModelObj().setResult("suspectCustNo", ecifData.getCustId());
		}catch (Exception e){
			log.error("���ݲ����쳣",e);
			if(ecifData.isSuccess()){
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return ;
		}
		/***
		 * ��¼�ϲ�������
		 */
		try{
			CustSuspectRecord record=(CustSuspectRecord)SpringContextUtils.getBean("custSuspectRecord");
			record.process(ecifData, reserveCustId, reserveCustNo, false);
		}catch (Exception e){
			log.error("AddByGeneral:", e);
		}
		return;
	}
}

/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.svc
 * @�ļ�����UpdateCustName.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-12-10:24:48
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.fubonecif.service.svc.comb;


import java.util.Map;

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
import com.ytec.fubonecif.service.svc.atomic.UpdateCustNameDao;


/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�UpdateCustName
 * @���������ͻ������޸�
 * @��������:�޸Ŀͻ�����������Ƿ��������ͻ�����ͬ��֤������У������޸�
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-12 ����10:24:57   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-12 ����10:24:57
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings(
		{ "rawtypes", "unchecked" })
public class UpdateCustName implements IEcifBizLogic
{
	private static Logger log = LoggerFactory
			.getLogger(UpdateCustName.class);
	public void process(EcifData ecifData){
		String custName=ecifData.getWriteModelObj().getOperMapValue("custName");
		// �ͻ�ʶ��
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		Map opMp = ecifData.getWriteModelObj().getOperMap();
		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
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
//							ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"�ÿͻ�%s״̬:%s", ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
							ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"�ÿͻ�%s״̬:%s", ecifData.getCustId(),custStatCtrl.getCustStatusDesc());
							return;
						}
					}
				}
				
			}
			//����޸ĺ��Ƿ��������ͻ���֤��ͬ, ���ڸÿͻ����������
			UpdateCustNameDao updateCustNameDao = (UpdateCustNameDao) SpringContextUtils.getBean("updateCustNameDao");
			try{
				updateCustNameDao.process(ecifData,custName);
//				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getEcifCustNo());
				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getCustId());
			}catch (Exception e){
				log.error("���ݲ����쳣",e);
				if(ecifData.isSuccess()){
					ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
				}
				return ;
			}
		}
		return;
	}
}

/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����SuspectCust.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-8-����4:11:08
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.ytec.fubonecif.service.svc.atomic.SuspectCustDao;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�SuspectCust
 * @�����������������޸Ŀͻ�ʱ��ʵ�����ƿͻ���ʶ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-8 ����4:11:08   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-8 ����4:11:08
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class SuspectCust implements IEcifBizLogic{
	private static Logger log = LoggerFactory
			.getLogger(SuspectCust.class);
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IEcifBizLogic#process(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void process(EcifData ecifData) throws Exception {
		// TODO Auto-generated method stub
		if(ecifData.getCustId()==null){//�ͻ�δʶ��
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
						if(!custStatCtrl.isNormal() &&custStatCtrl.isReOpen()){
							//*****�ͻ��Ѿ�ʧЧ������Ҫ���ƿͻ�ʶ��
							return;
						}
					}
				}
			}else{
				if(!ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE.getCode().equals(ecifData.getRepStateCd())){
					ecifData.resetStatus();
				}
				return;
			}
		}
		
		//*****���ƿͻ������ж�
		try{
			SuspectCustDao suspectCustDao=(SuspectCustDao)SpringContextUtils.getBean("suspectCustDao");
			Map<String,Map> map=suspectCustDao.process(ecifData);
			List<Map> list=new ArrayList();
			if(!map.isEmpty()){
				Map point=null;
				CustStatus custStatCtrl=null;
				for(Entry<String, Map> entry:map.entrySet()){
					point=entry.getValue();
					if(point.get("custStat")!=null&&(custStatCtrl=CustStatusMgr.getInstance().getCustStatus(point.get("custStat").toString()))!=null){
						if(!custStatCtrl.isNormal() &&custStatCtrl.isReOpen()){
							//*****�ͻ��Ѿ�ʧЧ
							continue;
						}
					}
					list.add(point);
				}
			}
			if(list.size()>0){
				ecifData.getWriteModelObj().setResult("suspectCust", list);
			}
//			ecifData.getWriteModelObj().setResult("custNo", ecifData.getEcifCustNo());
			ecifData.getWriteModelObj().setResult("custNo", ecifData.getCustId());
		}catch(Exception e){
			log.error("���ݲ����쳣",e);
			if(ecifData.isSuccess()){
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return ;
		}
		return;
	}

}

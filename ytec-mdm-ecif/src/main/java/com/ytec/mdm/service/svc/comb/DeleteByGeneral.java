/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.svc.comb
 * @�ļ�����DeleteByGeneral.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:04:53
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.svc.comb;

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
import com.ytec.mdm.service.svc.atomic.DeleteGeneral;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�DeleteByGeneral
 * @��������ͨ�ÿͻ���Ϣɾ�� ��Ӧ����Ϣģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:04:56
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:04:56
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings("rawtypes")
public class DeleteByGeneral implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(DeleteByGeneral.class);

	public void process(EcifData ecifData) {
		DeleteGeneral del = (DeleteGeneral) SpringContextUtils.getBean("deleteGeneral");
		// �ͻ�ʶ��
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		Map opMp = ecifData.getWriteModelObj().getOperMap();
		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()) {
			try {
				if (MdmConstants.checkCustomerStatus) {
					/*****
					 * �ͻ�״̬�ж�
					 */
					CustStatus custStatCtrl = null;
					if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(ecifData.getCustStatus())) != null) {
						if (!custStatCtrl.isNormal()) {
							if (!custStatCtrl.isUpdate()) {
								log.warn("�ͻ�({})״̬{}", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
								// ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"�ÿͻ�%s״̬:%s", ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
								ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(), "�ÿͻ�%s״̬:%s",
										ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
								return;
							}
						}
					}
				}
				del.process(ecifData);
			} catch (Exception e) {
				log.error("DeleteByGeneral:", e);
				ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
				return;
			}
		}
		return;
	}

}

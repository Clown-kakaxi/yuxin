/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����AddByGeneral.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:03:24
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.util.List;
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
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
//import com.ytec.fubonecif.domain.MCiNametitle;
//import com.ytec.fubonecif.domain.MCiOrgIdentifier;
//import com.ytec.fubonecif.domain.MCiPerIdentifier;
import com.ytec.fubonecif.domain.MCiIdentifier;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AddByGeneral
 * @��������ͨ�ÿͻ���Ϣ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:03:24
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:03:24
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AddByGeneral implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(AddByGeneral.class);

	public void process(EcifData ecifData) {
		String custName = null;
		String custNameTmp = null;
		/****
		 * ��֤����
		 */
		List generalInfoList = ecifData.getWriteModelObj().getOpModelList();
		for (Object newObj : generalInfoList) {
			if (newObj.getClass().equals(MCiIdentifier.class)) {
				MCiIdentifier identifier = (MCiIdentifier) newObj;
				custNameTmp = identifier.getIdentCustName();
			}
			if (custNameTmp != null) {
				if (custName != null) {
					if (!custName.equals(custNameTmp)) {
						ecifData.setStatus(ErrorCode.ERR_ECIF_CUSTNAME.getCode(), "֤����Ϣ��������Ϣ�еĻ�����ͳһ");
						return;
					}
				} else {
					custName = custNameTmp;
				}
			}
		}

		/**
		 * �ͻ�ʶ��
		 */
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		Map opMp = ecifData.getWriteModelObj().getOperMap();
		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()) {
			if (MdmConstants.checkCustomerStatus) {
				/*****
				 * �ͻ�״̬�ж�
				 */
				CustStatus custStatCtrl = null;
				if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(ecifData.getCustStatus())) != null) {
					if (!custStatCtrl.isNormal()) {
						if (!custStatCtrl.isUpdate()) {
							log.warn("�ͻ�({})״̬{}", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
							// ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(), "�ÿͻ�%s״̬:%s", ecifData.getEcifCustNo(), custStatCtrl.getCustStatusDesc());
							ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(), "�ÿͻ�%s״̬:%s",
									ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
							return;
						}
					}
				}

			}
			// ���ڸÿͻ����������
			AddGeneral add = (AddGeneral) SpringContextUtils.getBean("addGeneral");
			try {
				add.process(ecifData);
				// ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,ecifData.getEcifCustNo());
				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getCustId());
			} catch (Exception e) {
				log.error("���ݲ����쳣", e);
				if (ecifData.isSuccess()) {
					ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
				}
				return;
			}
		}
		return;
	}
}

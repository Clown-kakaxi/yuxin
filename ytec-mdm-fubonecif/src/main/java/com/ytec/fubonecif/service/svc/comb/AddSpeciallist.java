/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����AddSpeciallist.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-26-����2:42:05
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
 * @�����ƣ�AddSpeciallist
 * @��������������������ά��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-26 ����2:42:05
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-26 ����2:42:05
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class AddSpeciallist implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(AddSpeciallist.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ytec.mdm.integration.transaction.facade.IEcifBizLogic#process(com
	 * .ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void process(EcifData ecifData) throws Exception {
		// TODO Auto-generated method stub
		// �ͻ�ʶ��
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils
				.getBean("getContIdByType");
		findContId.bizGetContId(ecifData);
		// ��Ӻ�����
		AddGeneral add = (AddGeneral) SpringContextUtils.getBean("addGeneral");
		try {
			/**���ݲ���**/
			add.process(ecifData);
//			ecifData.getWriteModelObj().setResult("custNo",
//					ecifData.getEcifCustNo());
			ecifData.getWriteModelObj().setResult("custNo",
					ecifData.getCustId());
		} catch (Exception e) {
			log.error("���ݲ����쳣", e);
			if (ecifData.isSuccess()) {
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return;
		}
		return;
	}

}

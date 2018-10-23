/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.component.biz.comidentification
 * @�ļ�����GetContIdFlow.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:56:15
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.component.biz.comidentification;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.bo.Identifier;
import com.ytec.mdm.service.component.biz.identification.AbsBizGetContId;
import com.ytec.mdm.service.facade.IBizGetContId;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�GetContIdFlow
 * @�����������˿ͻ������ʶ�����ڸ��˿ͻ�����
 * @��������:����Դϵͳ�ͻ�����ʶ��Ȼ������֤ʶ��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:56:15   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:56:15
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetContIdFlow implements IBizGetContId {

	public void bizGetContId(EcifData ecifData) {

		// ��Դϵͳ�ͻ���ʶ��
		IBizGetContId bizGetContId = (AbsBizGetContId) SpringContextUtils
				.getBean(MdmConstants.GETCONTIDBYSRCCUSTNO);
		bizGetContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()) {
			return;
		} else {
			// ����֤ʶ��
			bizGetContId = (AbsBizGetContId) SpringContextUtils
					.getBean(MdmConstants.GETCONTIDBYIDENT);
			List<Identifier> identList = (List) ecifData
					.getWriteModelObj().getOperMap().get("identList");
			if (identList != null) {
				for (Identifier ident : identList) {
					ecifData.getWriteModelObj()
							.getOperMap()
							.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD,
									ident.getIdentType());
					ecifData.getWriteModelObj()
							.getOperMap()
							.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO,
									ident.getIdentNo());
					ecifData.getWriteModelObj()
							.getOperMap()
							.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME,
									ident.getIdentCustName());
					bizGetContId.bizGetContId(ecifData);
					if (ecifData.isSuccess()) {
						return;
					}
				}
			}
		}
		return;
	}

}

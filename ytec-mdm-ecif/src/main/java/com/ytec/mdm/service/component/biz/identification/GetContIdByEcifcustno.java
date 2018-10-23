/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.identification
 * @�ļ�����GetContIdByEcifcustno.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:57:35
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.biz.identification;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.component.general.CustStatusMgr;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�GetContIdByEcifcustno
 * @����������ECIF�ͻ�ʶ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:57:36   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:57:36
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings("rawtypes")
public class GetContIdByEcifcustno extends AbsBizGetContId {
	private JPABaseDAO baseDAO;
	/**
	 * 
	 * @param Map
	 *            <String,String> ecifCustNo Map�б�Ҫ�ļ�ecifCustNo
	 * @return ContMessage
	 */
	public void bizGetContId(EcifData ecifData) {
		// ��ȡecif�ͻ���
		String ecifCustNo = (String)ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO);

		// ��ò������ݴ���
		if (ecifCustNo == null || ecifCustNo.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_ECIFCUSTNO.getCode(),ErrorCode.ERR_ECIF_NULL_ECIFCUSTNO.getChDesc());
			return;
		} else {
			// ����ecif�ͻ��ţ����ҿͻ���ʶ
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			List result = baseDAO.findByNativeSQLWithIndexParam("SELECT CUST_ID,CUST_TYPE,CUST_STAT FROM M_CI_CUSTOMER WHERE CUST_ID=?"
					,ecifCustNo);

			// �ɹ����ؿͻ���ʶ��ʧ�ܷ������ݲ�����
			if (result == null || result.size() == 0) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if (result.size() == 1){
				ecifData.resetStatus();
				Object[] ob=(Object[])result.get(0);
				ecifData.setCustId(ob[0].toString());
//				ecifData.setEcifCustNo(ecifCustNo);
				ecifData.getCustId(ecifCustNo);
				ecifData.setCustType((String)ob[1]);
				ecifData.setCustStatus((String)ob[2]);
			}else{
				//�жϲ�ѯ������ͻ����ų�ʧЧ�ͻ�(ע��,�ϲ���)��
				Object[] ob=null;
				int availableNum=0;
				CustStatus custStatCtrl=null;
				for(int i=0;i<result.size();i++){
					ob=(Object[]) result.get(i);
					if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus((String)ob[2]))!=null&&custStatCtrl.isReOpen()){//ʧЧ�ͻ�
							continue;
					}
					availableNum++;
					if(availableNum==1){
						ecifData.setCustId(ob[0].toString());
//						ecifData.setEcifCustNo(ecifCustNo);
						ecifData.getCustId(ecifCustNo);
						ecifData.setCustType((String)ob[1]);
						ecifData.setCustStatus((String)ob[2]);
					}else{
						ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(),"���ؿͻ���Ψһ");
						return;
					}
				}
				if(availableNum==0){
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
					return;
				}else{
					ecifData.resetStatus();
				}
			}
		}
		return;
	}
}

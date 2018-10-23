/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.identification
 * @�ļ�����GetContIdBySrccustno.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:58:05
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
import com.ytec.mdm.service.facade.IBizGetContId;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�GetContIdBySrccustno
 * @�������������Ŀͻ���ʶ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:58:05   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:58:05
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@SuppressWarnings("rawtypes")
public class GetContIdBySrccustno extends AbsBizGetContId {
	private JPABaseDAO baseDAO;

	/**
	 * 
	 * @param Map
	 *            <String,String> srccustno Map��������Ҫ�ļ�srcSysCd,srcSysCustNo
	 * @return ContMessage
	 */
	public void bizGetContId(EcifData ecifData) {
		// ��Map�л�ȡԴϵͳ�š� Դϵͳ�ͻ���
		String srcSysCd = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD);
		String srcSysCustNo = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO);
		String conTpCd=(String)ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_CUST_TYPE);

		// Դϵͳ�Ż�Դ�ͻ���������һ��Ϊ�գ������ݲ���ȷ
		if (srcSysCd == null || srcSysCd.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_SRCSYSCD.getCode(),ErrorCode.ERR_ECIF_NULL_SRCSYSCD.getChDesc());
			return;
		} else if (srcSysCustNo == null || srcSysCustNo.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_SRCSYSCUSTNO.getCode(),ErrorCode.ERR_ECIF_NULL_SRCSYSCUSTNO.getChDesc());
			return;
		} else {
			// ��Դϵͳ�ź�Դ�ͻ��������ݿ��в��ҿͻ���ʶ
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			StringBuffer jql = new StringBuffer("");
			List result=null;
			jql.append(" SELECT distinct X.CUST_ID,C.CUST_NO,C.CUST_TYPE,C.CUST_STAT");
			jql.append(" FROM M_CI_CROSSINDEX X,M_CI_CUSTOMER C");
			jql.append(" WHERE X.SRC_SYS_NO=?");
			jql.append(" AND X.SRC_SYS_CUST_NO=?");
			jql.append(" AND C.CUST_ID=X.CUST_ID");
			if(conTpCd!=null && !conTpCd.isEmpty()){
				jql.append(" AND C.CUST_TYPE=?");
				result = baseDAO.findByNativeSQLWithIndexParam(jql.toString(),srcSysCd,srcSysCustNo,conTpCd);
			}else{
				result = baseDAO.findByNativeSQLWithIndexParam(jql.toString(),srcSysCd,srcSysCustNo);
			}

			// ��װ��ѯ���
			if (result == null || result.size() == 0) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if (result.size() == 1)  {
				ecifData.resetStatus();
				Object[] ob=(Object[])result.get(0);
				ecifData.setCustId(ob[0].toString());
//				ecifData.setEcifCustNo((String)ob[1]);
				ecifData.getCustId((String)ob[1]);
				ecifData.setCustType((String)ob[2]);
				ecifData.setCustStatus((String)ob[3]);
			}else{
				//�жϲ�ѯ������ͻ����ų�ʧЧ�ͻ�(ע��,�ϲ���)��
				Object[] ob=null;
				int availableNum=0;
				CustStatus custStatCtrl=null;
				for(int i=0;i<result.size();i++){
					ob=(Object[]) result.get(i);
					if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus((String)ob[3]))!=null&&custStatCtrl.isReOpen()){//ʧЧ�ͻ�
						continue;
					}
					availableNum++;
					if(availableNum==1){
						ecifData.setCustId(ob[0].toString());
//						ecifData.setEcifCustNo((String)ob[1]);
						ecifData.getCustId((String)ob[1]);
						ecifData.setCustType((String)ob[2]);
						ecifData.setCustStatus((String)ob[3]);
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

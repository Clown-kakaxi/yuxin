package com.ytec.mdm.service.component.biz.identification;

import java.util.List;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�GetAccountOnlyByIdent
 * @������������֤�����ͺ�֤������ʶ��
 * @��������:����֤�����ͺ�֤������ʶ�� -------- --------
 *                     ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class GetAccountOnlyByIdent extends AbsBizGetContId {

	private JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

	/**
	 * ��֤�����ͺ�֤��������Ϊ�ͻ�ʶ�����ͬһ֤�����ͺ�֤������Ķ���Ϊһ���ͻ�
	 * 
	 */
	@Override
	public void bizGetContId(EcifData ecifData) {
		System.err.println("��֤�����ͺ�֤��������Ϊ�ͻ�ʶ�����ͬһ֤�����ͺ�֤������Ķ���Ϊһ���ͻ�");
		// ��ȡ��Ϣ--֤�����͡�֤�����롢֤���ͻ���
		String identTpCd = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD);//֤������
		String identNo = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTNO);//֤������
		String identName = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTNAME);//�ͻ�����
		String custType = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_CUST_TYPE);//�ͻ�����
		
		// ��֤����һ������Ϊ�գ�����Ϊ���ݲ���ȷ
		
		if (identTpCd == null || identTpCd.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTTP.getCode(), ErrorCode.ERR_ECIF_NULL_IDENTTP.getChDesc());
			return;
		} else if (identNo == null || identNo.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTNO.getCode(), ErrorCode.ERR_ECIF_NULL_IDENTNO.getChDesc());
			return;
		} else if (identName == null || identName.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTNAME.getCode(),ErrorCode.ERR_ECIF_NULL_IDENTNAME.getChDesc());
			return;
		} else {
			String identifierName = null;//���ݿ��е�֤������
			if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
				identifierName = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierName = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			// �������������ݿ��в��ҿͻ���ʶ
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			StringBuffer jql = new StringBuffer("");
			jql.append("SELECT distinct I.custId,C.custType,C.custStat FROM ");
			jql.append(identifierName);
			//ֻ����֤�����ͺ�֤�������ѯ������ѯ�ͻ�����
			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=? AND C.custId=I.custId ");//AND C.custType=?");
			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo);
			
			// ��װ��ѯ���
			if (result == null || result.size() == 0) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if (result.size() == 1) {
				ecifData.resetStatus();
				Object[] ob = (Object[]) result.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			} else {
				// �жϲ�ѯ������ͻ����ų�ʧЧ�ͻ�(ע��,�ϲ���)��
				Object[] ob = null;
				int availableNum = 0;
				CustStatus custStatCtrl = null;
				for (int i = 0; i < result.size(); i++) {
					ob = (Object[]) result.get(i);
					System.out.println("ob:" + ob + ", size:" + ob.length);
					// if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus((String)ob[3]))!=null&&custStatCtrl.isReOpen()){//ʧЧ�ͻ�
					if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus((String) ob[2])) != null
							&& custStatCtrl.isReOpen()) {// ʧЧ�ͻ�
						// if(!MdmConstants.TX_CODE_K.equals((ecifData.getTxType()))){
						continue;
						// }
					}
					availableNum++;
					if (availableNum == 1) {
						ecifData.setCustId(ob[0].toString());
						// ecifData.setEcifCustNo((String)ob[1]);
						ecifData.setCustType((String) ob[1]);
						ecifData.setCustStatus((String) ob[2]);
					} else {
						ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "���ؿͻ���Ψһ");
						return;
					}
				}
				if (availableNum == 0) {
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
					return;
				} else {
					ecifData.resetStatus();
				}
			}
		}
		// ����ʶ����Ϣ
		return;
	}

}

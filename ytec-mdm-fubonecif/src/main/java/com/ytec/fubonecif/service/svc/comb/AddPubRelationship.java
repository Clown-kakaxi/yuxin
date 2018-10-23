package com.ytec.fubonecif.service.svc.comb;

/**
 * <pre>
 * Title:ά���ͻ���ϵ
 * Description:��Control���OperMap�У�
 *             �������srcSysCd��srcCustNo��destCustNo����������ֵ��Ϊ��,
 *            ��ͨ��ԭ�ͻ���ʶ�������ͻ���custId,Ȼ��ͨ����ϵ���Ͳ��ҹ�ϵ�����ά��
 * </pre>
 * 
 * @author wangzy1@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
 * </pre>
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.mdm.service.facade.IBizGetContId;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;

@Service
public class AddPubRelationship implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(AddPubRelationship.class);

	@Override
	public void process(EcifData ecifData) {
		// TODO Auto-generated method stub

		// ����ͨ����Ϣ
//		ContReturnMessage message = new ContReturnMessage();
//		CUDResponseData result = new CUDResponseData();
//		GeneralBO general = new GeneralBO();
//		IBizGetContId bizGetContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
//		Map srccustnoMap = new HashMap();
//		general.setSrcSysCd(control.getSrcSysCd());
//		String srcCustNo = (String) control.getOperMap().get("srcCustNo");
//		String destCustNo = (String) control.getOperMap().get("destCustNo");
//		srccustnoMap.put("filterRule", control.getOperMap().get("filterRule"));
//		srccustnoMap.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, control.getSrcSysCd());
//		srccustnoMap.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO, srcCustNo);
//		srccustnoMap.put(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, srcCustNo);
//		message = (ContReturnMessage) bizGetContId.bizGetContId(srccustnoMap);
//		Long srcCustId = null;
//		Long destCustId = null;
//		if (message.isSuccessFlag()) {
//			srcCustId = Long.parseLong(message.getContId());
//			/*****
//			 * �ͻ�״̬�ж�
//			 */
//			if (MdmConstants.checkCustomerStatus) {
//				GetCustomerStatus bizCustStatus = (GetCustomerStatus) SpringContextUtils.getBean("getCustomerStatus");
//				CustStatCtrl custStatCtrl = bizCustStatus.getCustStat(srcCustId, general.getContTpCd());
//				if (custStatCtrl.getCustStat() != null) {
//					if (!custStatCtrl.getCustStat().isNormal()) {
//						if (!custStatCtrl.getCustStat().isUpdate()) {
//							log.warn("�ͻ�(" + message.getContId() + ")״̬" + custStatCtrl.getCustStat().getCustStatusDesc());
//							result.setSucessFlag(false);
//							result.setError(ErrorCode.ERR_ECIF_CUST_STATUS);
//							result.setDesc(custStatCtrl.getCustStat().getCustStatusDesc());
//							return result;
//						}
//					}
//				}
//			}
//		} else {
//			result.setSucessFlag(false);
//			result.setError(message.getError());
//			return result;
//		}
//		srccustnoMap.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO, destCustNo);
//		srccustnoMap.put(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, destCustNo);
//		message = (ContReturnMessage) bizGetContId.bizGetContId(srccustnoMap);
//		if (message.isSuccessFlag()) {
//			destCustId = Long.parseLong(message.getContId());
//		} else {
//			result.setSucessFlag(false);
//			result.setError(message.getError());
//			return result;
//		}
//		Custrel custrel = new Custrel();
//		custrel.setCustRelType((String) control.getOperMap().get("custRelType"));
//		custrel.setDestCustId(destCustId);
//		custrel.setSrcCustId(srcCustId);
//		custrel.setCustRelStat((String) control.getOperMap().get("custRelStat"));
//		custrel.setRelStartDate(Validator.parseDateString((String) control.getOperMap().get("relStartDate")));
//		custrel.setRelEndDate(Validator.parseDateString((String) control.getOperMap().get("relEndDate")));
//		custrel.setCustRelDesc((String) control.getOperMap().get("custRelDesc"));
//		custrel.setApprovalFlag((String) control.getOperMap().get("approvalFlag"));
//		generalInfoList.clear();
//		generalInfoList.add(custrel);
//
//		general.setTxId(control.getTxId());
//		general.setUser(control.getUser());
//		try {
//			AddGeneral add = (AddGeneral) SpringContextUtils.getBean("addGeneral");
//			general.setContId(null);
//			result = add.process(general, generalInfoList);
//		} catch (Exception e) {
//			log.error("AddByGeneral:", e);
//			result.setSucessFlag(false);
//			result.setError(ErrorCode.ERR_UNKNOWN_ERROR);
//			result.setDesc(e.getMessage());
//			return result;
//		}

//		return result;
		return;
	}

}

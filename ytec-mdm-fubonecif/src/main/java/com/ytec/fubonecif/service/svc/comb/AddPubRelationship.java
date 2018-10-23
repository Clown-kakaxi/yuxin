package com.ytec.fubonecif.service.svc.comb;

/**
 * <pre>
 * Title:维护客户关系
 * Description:在Control类的OperMap中，
 *             必须存在srcSysCd，srcCustNo，destCustNo三个键，切值不为空,
 *            先通过原客户号识别两个客户的custId,然后通过关系类型查找关系，最后维护
 * </pre>
 * 
 * @author wangzy1@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
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

		// 设置通用信息
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
//			 * 客户状态判断
//			 */
//			if (MdmConstants.checkCustomerStatus) {
//				GetCustomerStatus bizCustStatus = (GetCustomerStatus) SpringContextUtils.getBean("getCustomerStatus");
//				CustStatCtrl custStatCtrl = bizCustStatus.getCustStat(srcCustId, general.getContTpCd());
//				if (custStatCtrl.getCustStat() != null) {
//					if (!custStatCtrl.getCustStat().isNormal()) {
//						if (!custStatCtrl.getCustStat().isUpdate()) {
//							log.warn("客户(" + message.getContId() + ")状态" + custStatCtrl.getCustStat().getCustStatusDesc());
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

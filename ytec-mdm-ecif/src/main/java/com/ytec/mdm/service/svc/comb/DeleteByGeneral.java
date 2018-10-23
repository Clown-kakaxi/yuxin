/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.svc.comb
 * @文件名：DeleteByGeneral.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:04:53
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：DeleteByGeneral
 * @类描述：通用客户信息删除 对应简单信息模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:04:56
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:04:56
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings("rawtypes")
public class DeleteByGeneral implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(DeleteByGeneral.class);

	public void process(EcifData ecifData) {
		DeleteGeneral del = (DeleteGeneral) SpringContextUtils.getBean("deleteGeneral");
		// 客户识别
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		Map opMp = ecifData.getWriteModelObj().getOperMap();
		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()) {
			try {
				if (MdmConstants.checkCustomerStatus) {
					/*****
					 * 客户状态判断
					 */
					CustStatus custStatCtrl = null;
					if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(ecifData.getCustStatus())) != null) {
						if (!custStatCtrl.isNormal()) {
							if (!custStatCtrl.isUpdate()) {
								log.warn("客户({})状态{}", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
								// ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"该客户%s状态:%s", ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
								ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(), "该客户%s状态:%s",
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

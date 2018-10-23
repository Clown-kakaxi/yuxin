/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.svc
 * @文件名：UpdateCustName.java
 * @版本信息：1.0.0
 * @日期：2014-2-12-10:24:48
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.fubonecif.service.svc.comb;


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
import com.ytec.fubonecif.service.svc.atomic.UpdateCustNameDao;


/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：UpdateCustName
 * @类描述：客户户名修改
 * @功能描述:修改客户户名，检查是否与其他客户有相同三证，如果有，不能修改
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-12 上午10:24:57   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-12 上午10:24:57
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings(
		{ "rawtypes", "unchecked" })
public class UpdateCustName implements IEcifBizLogic
{
	private static Logger log = LoggerFactory
			.getLogger(UpdateCustName.class);
	public void process(EcifData ecifData){
		String custName=ecifData.getWriteModelObj().getOperMapValue("custName");
		// 客户识别
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		Map opMp = ecifData.getWriteModelObj().getOperMap();
		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()){
			if(MdmConstants.checkCustomerStatus){
				/*****
				 * 客户状态判断
				 */
				CustStatus custStatCtrl=null;
				if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus(ecifData.getCustStatus()))!=null){
					if(!custStatCtrl.isNormal()){
						if(!custStatCtrl.isUpdate()){
							log.warn("客户({})状态{}",ecifData.getCustId(),custStatCtrl.getCustStatusDesc());
//							ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"该客户%s状态:%s", ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
							ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"该客户%s状态:%s", ecifData.getCustId(),custStatCtrl.getCustStatusDesc());
							return;
						}
					}
				}
				
			}
			//检查修改后是否与其他客户三证相同, 存在该客户，添加属性
			UpdateCustNameDao updateCustNameDao = (UpdateCustNameDao) SpringContextUtils.getBean("updateCustNameDao");
			try{
				updateCustNameDao.process(ecifData,custName);
//				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getEcifCustNo());
				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getCustId());
			}catch (Exception e){
				log.error("数据操作异常",e);
				if(ecifData.isSuccess()){
					ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
				}
				return ;
			}
		}
		return;
	}
}

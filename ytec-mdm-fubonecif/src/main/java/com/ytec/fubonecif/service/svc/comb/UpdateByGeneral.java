/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.svc.comb
 * @文件名：UpdateByGeneral.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:06:19
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
import com.ytec.fubonecif.service.svc.atomic.UpdateGeneral;
//import com.ytec.fubonecif.domain.MCiNametitle;
//import com.ytec.fubonecif.domain.MCiOrgIdentifier;
//import com.ytec.fubonecif.domain.MCiPerIdentifier;
import com.ytec.fubonecif.domain.MCiIdentifier;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：UpdateByGeneral
 * @类描述：通用客户信息修改
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:06:19   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:06:19
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings(
		{ "rawtypes", "unchecked" })
public class UpdateByGeneral implements IEcifBizLogic
{
	private static Logger log = LoggerFactory
			.getLogger(UpdateByGeneral.class);
	public void process(EcifData ecifData){
		String custName=null;
		String custNameTmp=null;
		/****
		 * 验证户名
		 */
		List generalInfoList=ecifData.getWriteModelObj().getOpModelList();
		for (Object newObj : generalInfoList) {
//			if(newObj.getClass().equals(MCiNametitle.class)){
//				MCiNametitle nametitle=(MCiNametitle)newObj;
//				custNameTmp=nametitle.getCustName();
//			} else
				if(newObj.getClass().equals(MCiIdentifier.class)){
					MCiIdentifier identifier=(MCiIdentifier)newObj;
				custNameTmp=identifier.getIdentCustName();
			}
//				else if(newObj.getClass().equals(MCiOrgIdentifier.class)){
//				MCiOrgIdentifier identifier=(MCiOrgIdentifier)newObj;
//				custNameTmp=identifier.getIdentCustName();
//			}

			if(custNameTmp!=null){
				if(custName!=null){
					if(!custName.equals(custNameTmp)){
						ecifData.setStatus(ErrorCode.ERR_ECIF_CUSTNAME.getCode(),"证件信息或名称信息中的户名不统一");
						return;
					}
				}else{
					custName=custNameTmp;
				}
			}
		}
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
			// 存在该客户，添加属性
			UpdateGeneral update = (UpdateGeneral) SpringContextUtils.getBean("updateGeneral");
			try{
				update.process(ecifData);
				update.updateRedundance(ecifData);			//修改冗余字段
				
				//TODO added by wangtb@yuchengtech.com 2014-11-16
				update.saveHist(ecifData);
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

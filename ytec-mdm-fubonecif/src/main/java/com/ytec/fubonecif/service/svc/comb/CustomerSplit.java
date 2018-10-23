/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.svc.comb
 * @文件名：CustomerSplit.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:03:49
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

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
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.mdm.service.svc.atomic.CustSuspectRecord;
import com.ytec.fubonecif.domain.MCiCustomer;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CustomerSplit
 * @类描述：客户拆分
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:03:50   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:03:50
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomerSplit implements IEcifBizLogic
{
	private static Logger log = LoggerFactory
			.getLogger(CustomerSplit.class);
	public void process(EcifData ecifData){
		String custType;
		String reserveCustNo;
		Object reserveCustId;
		/**保留客户**/
		String custNo=ecifData.getWriteModelObj().getOperMapValue("custNo");
		/**拆分客户**/
		String suspectCustNo=ecifData.getWriteModelObj().getOperMapValue("suspectCustNo");
		// 保留客户识别
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO, custNo);
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, custNo);
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
							ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"保留客户%s状态:%s", custNo,custStatCtrl.getCustStatusDesc());
							return;
						}
					}
				}
			}
			custType=ecifData.getCustType();
//			reserveCustNo=ecifData.getEcifCustNo();
			reserveCustNo=ecifData.getCustId();
			reserveCustId=ecifData.getCustId(MdmConstants.CUSTID_TYPE);
		}else{
			log.warn("保留客户({}){}",custNo,ecifData.getDetailDes());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_MERGE_RULE.getCode(),"保留客户%s%s", custNo,ecifData.getDetailDes());
			return;
		}
		// 拆分客户识别
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO, suspectCustNo);
		ecifData.getWriteModelObj().putOperMapValue(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, suspectCustNo);
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()){
			if(!MdmConstants.COMBINE_FLAG.equals(ecifData.getCustStatus())){
				log.warn("拆分客户({})状态不为已合并",suspectCustNo);
				ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"拆分客户%s状态不是已合并",suspectCustNo);
				return;
			}
			if(!custType.equals(ecifData.getCustType())){
				log.warn("拆分客户({})客户类型与保留客户不一致",ecifData.getCustId());
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_MERGE_RULE.getCode(),"拆分客户%s客户类型与保留客户不一致", suspectCustNo);
				return;
			}
		}else{
			log.warn("拆分客户({}){}",suspectCustNo,ecifData.getDetailDes());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_MERGE_RULE.getCode(),"拆分客户%s%s", suspectCustNo,ecifData.getDetailDes());
			return;
		}
		
		AddGeneral add = (AddGeneral) SpringContextUtils.getBean("addGeneral");
		try{
			MCiCustomer customer =  new MCiCustomer();
			customer.setCustStat(MdmConstants.STATE);
			ecifData.getWriteModelObj().setOpModelList(customer);
			add.process(ecifData);
//			ecifData.getWriteModelObj().setResult("suspectCustNo", ecifData.getEcifCustNo());
			ecifData.getWriteModelObj().setResult("suspectCustNo", ecifData.getCustId());
		}catch (Exception e){
			log.error("数据操作异常",e);
			if(ecifData.isSuccess()){
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return ;
		}
		/***
		 * 记录合并操作表
		 */
		try{
			CustSuspectRecord record=(CustSuspectRecord)SpringContextUtils.getBean("custSuspectRecord");
			record.process(ecifData, reserveCustId, reserveCustNo, false);
		}catch (Exception e){
			log.error("AddByGeneral:", e);
		}
		return;
	}
}

/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.svc.comb
 * @文件名：SuspectCust.java
 * @版本信息：1.0.0
 * @日期：2014-5-8-下午4:11:08
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.ytec.fubonecif.service.svc.atomic.SuspectCustDao;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：SuspectCust
 * @类描述：在新增或修改客户时，实现疑似客户的识别功能
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-8 下午4:11:08   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-8 下午4:11:08
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
public class SuspectCust implements IEcifBizLogic{
	private static Logger log = LoggerFactory
			.getLogger(SuspectCust.class);
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IEcifBizLogic#process(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void process(EcifData ecifData) throws Exception {
		// TODO Auto-generated method stub
		if(ecifData.getCustId()==null){//客户未识别
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
						if(!custStatCtrl.isNormal() &&custStatCtrl.isReOpen()){
							//*****客户已经失效，不需要疑似客户识别
							return;
						}
					}
				}
			}else{
				if(!ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE.getCode().equals(ecifData.getRepStateCd())){
					ecifData.resetStatus();
				}
				return;
			}
		}
		
		//*****疑似客户规则判定
		try{
			SuspectCustDao suspectCustDao=(SuspectCustDao)SpringContextUtils.getBean("suspectCustDao");
			Map<String,Map> map=suspectCustDao.process(ecifData);
			List<Map> list=new ArrayList();
			if(!map.isEmpty()){
				Map point=null;
				CustStatus custStatCtrl=null;
				for(Entry<String, Map> entry:map.entrySet()){
					point=entry.getValue();
					if(point.get("custStat")!=null&&(custStatCtrl=CustStatusMgr.getInstance().getCustStatus(point.get("custStat").toString()))!=null){
						if(!custStatCtrl.isNormal() &&custStatCtrl.isReOpen()){
							//*****客户已经失效
							continue;
						}
					}
					list.add(point);
				}
			}
			if(list.size()>0){
				ecifData.getWriteModelObj().setResult("suspectCust", list);
			}
//			ecifData.getWriteModelObj().setResult("custNo", ecifData.getEcifCustNo());
			ecifData.getWriteModelObj().setResult("custNo", ecifData.getCustId());
		}catch(Exception e){
			log.error("数据操作异常",e);
			if(ecifData.isSuccess()){
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return ;
		}
		return;
	}

}

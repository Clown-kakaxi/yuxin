/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.svc.comb
 * @文件名：CustomerStateMgr.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:04:06
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CustomerStateMgr
 * @类描述：客户状态管理
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:04:06   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:04:06
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
public class CustomerStateMgr implements IEcifBizLogic
{
	private static Logger log = LoggerFactory
			.getLogger(CustomerStateMgr.class);
	public void process(EcifData ecifData){
		// 客户识别
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		findContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()){
			// 存在该客户，添加属性
			AddGeneral add = (AddGeneral) SpringContextUtils.getBean("addGeneral");
			try{
				add.process(ecifData);
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

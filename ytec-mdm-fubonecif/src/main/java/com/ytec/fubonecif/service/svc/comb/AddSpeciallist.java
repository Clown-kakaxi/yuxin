/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.svc.comb
 * @文件名：AddSpeciallist.java
 * @版本信息：1.0.0
 * @日期：2014-5-26-下午2:42:05
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;

/**
 * @项目名称：ytec-mdm-fubonecif
 * @类名称：AddSpeciallist
 * @类描述：特殊名单新增维护
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-26 下午2:42:05
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-26 下午2:42:05
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
public class AddSpeciallist implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(AddSpeciallist.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ytec.mdm.integration.transaction.facade.IEcifBizLogic#process(com
	 * .ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void process(EcifData ecifData) throws Exception {
		// TODO Auto-generated method stub
		// 客户识别
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils
				.getBean("getContIdByType");
		findContId.bizGetContId(ecifData);
		// 添加黑名单
		AddGeneral add = (AddGeneral) SpringContextUtils.getBean("addGeneral");
		try {
			/**数据操作**/
			add.process(ecifData);
//			ecifData.getWriteModelObj().setResult("custNo",
//					ecifData.getEcifCustNo());
			ecifData.getWriteModelObj().setResult("custNo",
					ecifData.getCustId());
		} catch (Exception e) {
			log.error("数据操作异常", e);
			if (ecifData.isSuccess()) {
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return;
		}
		return;
	}

}

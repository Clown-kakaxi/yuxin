/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：WriteEcifDealEngine.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:18:05
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：WriteEcifDealEngine
 * @类描述：写交易处理引擎
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:18:05
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:18:05
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustrizEcifDealEngine extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory.getLogger(WriteEcifDealEngine.class);

	@Override
	public void execute(EcifData data) {
		String txCode = data.getTxCode();
		bizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txCode);

		if (bizLogic == null) {
			String msg = String.format("交易出错，未有交易代码(%s)对应的处理类", txCode);
			log.error(msg);
			data.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
			data.setSuccess(false);
		}
		try {
			String msg = String.format("交易[交易编码:%s]开始处理", txCode);
			log.info(msg);
			bizLogic.process(data);
		} catch (Exception e) {
			String msg = String.format("交易出错");
			log.info(msg);
			log.error("{}", e);
			data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), msg);
			data.setSuccess(false);
			return;
		}
	}
}

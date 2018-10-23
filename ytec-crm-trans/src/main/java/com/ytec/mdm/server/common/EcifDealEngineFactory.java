/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：EcifDealEngineFactory.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:09:29
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.core.ErrorEcifDealEngine;
import com.ytec.mdm.integration.transaction.core.QueryEcifDealEngine;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.core.WriteEcifDealEngine;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;


/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：EcifDealEngineFactory
 * @类描述：交易交易处理引擎工厂类
 * @功能描述:构造交易处理引擎对象
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:09:30
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:09:30
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public class EcifDealEngineFactory {

	private static Logger log = LoggerFactory
			.getLogger(EcifDealEngineFactory.class);

	/**
	 * @函数名称:getTxDealEngine
	 * @函数描述:获取交易引擎
	 * @参数与返回说明:
	 * 		@param txCode
	 * 		@return
	 * @算法描述:
	 */
	public static IEcifDealEngine getTxDealEngine(String txCode) {

		IEcifDealEngine txDealEngine = null;
		try {
			TxModel4CRM txModel = TxModelHolder.getTxModel(txCode);
			System.out.println("txCode:"+ txCode);
			// 根据交易类型生成相应的交易处理引擎
			String txType = txModel.getTxDef().getTxType();
			String cfgTy=txModel.getTxDef().getCfgTp();
			if(MdmConstants.TX_CFG_TP_CUS.equals(cfgTy)){
				log.info("客户化交易[{}:{}]",txModel.getTxDef().getName(),txModel.getTxDef().getCnName());
				txDealEngine = (IEcifDealEngine)SpringContextUtils.getBean(txModel.getTxDef().getDealEngine());
			}else{
				if (MdmConstants.TX_TYPE_R.equals(txType)){// 查询交易
					log.info("查询交易[{}:{}]",txModel.getTxDef().getName(),txModel.getTxDef().getCnName());
					txDealEngine = new QueryEcifDealEngine();
				}else if(MdmConstants.TX_TYPE_W.equals(txType)){
					log.info("写交易[{}:{}]",txModel.getTxDef().getName(),txModel.getTxDef().getCnName());
					txDealEngine = new WriteEcifDealEngine();
				}else {
					txDealEngine = new ErrorEcifDealEngine("交易类型:"+txType+" ,系统不支持!");
					log.error("交易类型:{},系统不支持!", txType);
				}
			}

		} catch (Exception e) {
			log.error("构造交易处理引擎失败",e);
			String errorMsg = "交易"+txCode+"不存在或配置信息异常";
			txDealEngine = new ErrorEcifDealEngine(errorMsg);
		}

		return txDealEngine;
	}
}

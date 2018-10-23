/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.sync.ptsync
 * @文件名：SynchroExecuteHandler.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:59:55
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.sync.ptsync;

import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.transaction.core.QueryEcifDealEngine;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SynchroExecuteHandler
 * @类描述：基于查询交易同步报文处理
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:59:56   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:59:56
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class SynchroExecuteHandler extends AbsSynchroExecutor {
	private static Logger log = LoggerFactory
			.getLogger(SynchroExecuteHandler.class);
	@Override
	public boolean execute(TxSyncConf txSyncConf,TxEvtNotice txEvtNotice) {
		// TODO Auto-generated method stub
		String txCode=txSyncConf.getSyncContentDef();
		if(StringUtil.isEmpty(txEvtNotice.getCustNo())){
			log.error("同步客户的客户号为空");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步客户的客户号为空");
			return false;
		}
		if(StringUtil.isEmpty(txCode)){
			log.error("同步配置{},同步类容为空,应填写同步查询交易码",txSyncConf.getSyncConfId());
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TXCODE_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步类容为空,应填写同步查询交易码");
			return false;
		}else{
			if(!TxModelHolder.txDefCheck(txCode)){
				log.error("同步配置{},同步查询交易{}不存在或已停用",txSyncConf.getSyncConfId(),txCode);
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TXCODE_ERROR.getCode());
				txEvtNotice.setEventDealInfo(String.format("同步查询交易%s不存在或已停用",txCode));
				return false;
			}else{
				EcifData ecifData=new EcifData();
				ecifData.setTxCode(txCode);
				ecifData.setOpChnlNo(txSyncConf.getDestSysNo());
				Map<String, String> parameterMap=new TreeMap<String, String>();
//				parameterMap.put("custId", txEvtNotice.getCustNo());
				parameterMap.put("custNo", txEvtNotice.getCustNo());
				ecifData.setParameterMap(parameterMap);
				IEcifDealEngine txDealEngine = new QueryEcifDealEngine();
				try{
					txDealEngine.execute(ecifData);
				}catch(Exception e){
					log.error("执行同步查询交易{}错误",txCode);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TX_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("执行同步查询交易%s错误",txCode));
					log.error("错误信息",e);
					return false;
				}
				if(!ecifData.isSuccess()||ErrorCode.WRN_NONE_FOUND.getCode().equals(ecifData.getRepStateCd())){
					log.error("执行同步查询交易{}错误[{}]",txCode,ecifData.getDetailDes());
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TX_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("执行同步查询交易%s错误[%s]",txCode,ecifData.getDetailDes()));
					return false;
				}
				if(ErrorCode.WRN_NONE_FOUND.getCode().equals(ecifData.getRepStateCd())){
					log.error("执行同步查询交易{}信息为空[{}]",txCode,ecifData.getDetailDes());
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_INFO_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("执行同步查询交易%s信息为空[%s]",txCode,ecifData.getDetailDes()));
					return false;
				}
				if(!asseReqMsg( txSyncConf,ecifData.getRepNode())){
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
					txEvtNotice.setEventDealInfo("组装报文体和报文头失败");
					return false;
				}
			}
		}
		return true;
	}
	
	public abstract boolean asseReqMsg(TxSyncConf txSyncConf,Element databody);

}

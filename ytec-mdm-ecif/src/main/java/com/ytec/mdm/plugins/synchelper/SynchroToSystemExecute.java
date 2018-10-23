/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper
 * @文件名：SynchroToSystemExecute.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:59:55
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.sync.ptsync.AbsSynchroExecutor;
import com.ytec.mdm.integration.transaction.core.QueryEcifDealEngine;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：SynchroToSystemExecute
 * @类描述：基于查询交易,转换成其他系统接口的报文处理
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:59:56
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:59:56
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public abstract class SynchroToSystemExecute extends AbsSynchroExecutor {
	private static Logger log = LoggerFactory.getLogger(SynchroToSystemExecute.class);

	@Override
	public boolean execute(TxSyncConf txSyncConf, TxEvtNotice txEvtNotice) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(txSyncConf.getSyncContentDef())) {
			log.error("同步内容定义为空");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步内容定义为空");
			return false;
		}
		/** 同步内容定义格式为:同步查询交易码|外系统接口模板名称 ***/
		String[] contentDefs = StringUtil.split(txSyncConf.getSyncContentDef(), '|');
		if (contentDefs == null || contentDefs.length != 2) {
			log.error("同步内容定义错误，应该为:同步查询交易码|外系统接口模板名称");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步内容定义错误，应该为:同步查询交易码|外系统接口模板名称");
			return false;
		}
		String txCode = contentDefs[0];
		if (StringUtil.isEmpty(txEvtNotice.getCustNo())) {
			log.error("同步客户的客户号为空");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步客户的客户号为空");
			return false;
		}
		if (StringUtil.isEmpty(txCode)) {
			log.error("同步配置{},同步类容为空,应填写同步查询交易码", txSyncConf.getSyncConfId());
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TXCODE_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步类容为空,应填写同步查询交易码");
			return false;
		} else {
			if (!TxModelHolder.txDefCheck(txCode)) {
				log.error("同步配置{},同步查询交易{}不存在或已停用", txSyncConf.getSyncConfId(), txCode);
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TXCODE_ERROR.getCode());
				txEvtNotice.setEventDealInfo(String.format("同步查询交易%s不存在或已停用", txCode));
				return false;
			} else {
				EcifData ecifData = new EcifData();
				ecifData.setTxCode(txCode);
				ecifData.setOpChnlNo(txSyncConf.getDestSysNo());
				Map<String, String> parameterMap = new TreeMap<String, String>();
				parameterMap.put("custNo", txEvtNotice.getCustNo());
				ecifData.setParameterMap(parameterMap);
				IEcifDealEngine txDealEngine = new QueryEcifDealEngine();
				try {
					txDealEngine.execute(ecifData);
				} catch (Exception e) {
					log.error("执行同步查询交易{}错误", txCode);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TX_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("执行同步查询交易%s错误", txCode));
					log.error("错误信息", e);
					return false;
				}
				if (!ecifData.isSuccess() || ErrorCode.WRN_NONE_FOUND.getCode().equals(ecifData.getRepStateCd())) {
					log.error("执行同步查询交易{}错误[{}]", txCode, ecifData.getDetailDes());
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TX_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("执行同步查询交易%s错误[%s]", txCode, ecifData.getDetailDes()));
					return false;
				}
				if (ErrorCode.WRN_NONE_FOUND.getCode().equals(ecifData.getRepStateCd())) {
					log.error("执行同步查询交易{}信息为空[{}]", txCode, ecifData.getDetailDes());
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_INFO_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("执行同步查询交易%s信息为空[%s]", txCode, ecifData.getDetailDes()));
					return false;
				}
				SyncXmlHelper syncXmlHelper = SyncXmlHelper.getInstance();
				Document doc = null;
				try {
					DocumentHelper.createDocument(ecifData.getRepNode());
					doc = syncXmlHelper.parseSyncXml(ecifData, contentDefs[1]);
				} catch (Exception e) {
					log.error("转换接口{}失败", contentDefs[1]);
					log.error("转换接口失败", e);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
					txEvtNotice.setEventDealInfo("组装报文体和报文头失败");
					return false;
				}
				if (doc == null) {
					log.error("转换接口{}报文为空", contentDefs[1]);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
					txEvtNotice.setEventDealInfo("组装报文体和报文头失败");
					return false;
				}
				if (!asseReqMsg(txSyncConf, doc)) {
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
					txEvtNotice.setEventDealInfo("组装报文体和报文头失败");
					return false;
				}
			}
		}
		return true;
	}

	public abstract boolean asseReqMsg(TxSyncConf txSyncConf, Document doc);

}

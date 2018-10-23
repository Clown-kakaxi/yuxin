/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：SynchroEvent.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:33:43
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */

package com.ytec.mdm.interfaces.common.even;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.interfaces.common.even.SynchroEventDAO;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：SynchroEvent
 * @类描述： 数据同步事件通知
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:33:43
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:33:43
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class SynchroEvent implements Observer {
	private Logger log = LoggerFactory.getLogger(SynchroEvent.class);

	private Map<String, TxSyncConf> txSyncConfMap = new ConcurrentHashMap<String, TxSyncConf>();

	/**
	 * Gets the tx sync conf.
	 * 
	 * @param txCode
	 *            the tx code
	 * @param opSyscd
	 *            the op syscd
	 * @return the tx sync conf
	 */
	private TxSyncConf getTxSyncConf(String txCode, String opSyscd) {
		log.info(String.format("读取交易配置信息[%s_%s]", txCode,opSyscd));
		return txSyncConfMap.get(txCode + "_" + opSyscd);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.even.Observer#executeObserver(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void executeObserver(EcifData ecifData) {
		// TODO Auto-generated method stub
		if (ecifData.isSuccess()) {
			TxSyncConf txSyncConf = null;
			if ((txSyncConf = getTxSyncConf(ecifData.getTxCode(), ecifData.getOpChnlNo())) != null) {
				try {
					log.info(String.format("同步交易配置里存在对应的配置，该交易[%s]需要同步数据", ecifData.getTxCode()));
					SynchroEventDAO eventDAO = (SynchroEventDAO) SpringContextUtils.getBean("synchroEventDAO");
					eventDAO.synchroEvent(ecifData, txSyncConf);
				} catch (Exception e) {
					log.error("数据同步事件录入异常", e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.even.Observer#init()
	 */
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		/** 数据同步配置 ***/
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		txSyncConfMap.clear();
		List<TxSyncConf> txSyncConfList = baseDAO.findWithIndexParam("FROM TxSyncConf C WHERE C.syncConfStat='1'");
		if (txSyncConfList != null) {
			for (TxSyncConf txSyncConf : txSyncConfList) {
				txSyncConfMap.put(txSyncConf.getTxCode() + "_" + txSyncConf.getSrcSysNo(), txSyncConf);
			}
		}
	}
}

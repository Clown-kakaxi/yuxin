/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：SynchroEventDAO.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:33:39
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.sync.listen.DataSynchroData;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：SynchroEventDAO
 * @类描述：事件通知表记录
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:33:43
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:33:43
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
public class SynchroEventDAO {
	/**
	 * @函数名称:synchroEvent
	 * @函数描述:事件通知表记录
	 * @参数与返回说明:
	 * @param ecifData
	 * @param txSyncConf
	 * @throws Exception
	 * @算法描述:
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void synchroEvent(EcifData ecifData, TxSyncConf txSyncConf) throws Exception {
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/** 同一个交易，同一个客户的操作，如果没有被处理，不需要提交同步事件 **/
		// List<TxEvtNotice> notices = baseDAO
		// .findWithIndexParam(
		// "FROM TxEvtNotice T where T.txCode=? and T.custNo=? and T.eventSysNo=? and T.eventType=? and eventDealStat=?",
		// ecifData.getTxCode(), ecifData.getEcifCustNo(),
		// ecifData.getOpChnlNo(), "0",
		// DataSynchroData.EVENTDEALSTAT_WAIT);

		List<TxEvtNotice> notices = baseDAO
				.findWithIndexParam(
						"FROM TxEvtNotice T where T.txCode=? and T.custNo=? and T.eventSysNo=? and T.eventType=? and eventDealStat=?",
						ecifData.getTxCode(), ecifData.getCustId(), ecifData.getOpChnlNo(), "0",
						DataSynchroData.EVENTDEALSTAT_WAIT);
		if (notices == null || notices.isEmpty()) {
			TxEvtNotice txEvtNotice = new TxEvtNotice();
			txEvtNotice.setEventId(OIdUtils.getIdOfLong());
			txEvtNotice.setEventName("数据同步");
			txEvtNotice.setEventType("0");// 数据同步
			txEvtNotice.setEventDesc(txSyncConf.getSyncConfDesc());
			txEvtNotice.setEventTime(new Timestamp(ecifData.getStopWatch().getStopTime()));
			txEvtNotice.setTxCode(ecifData.getTxCode());
			// OIdUtils.setCustIdValue(txEvtNotice,ecifData.getCustId());
			txEvtNotice.setCustNo(ecifData.getCustId());
			txEvtNotice.setCustId(Long.parseLong(ecifData.getCustId()));
			txEvtNotice.setTxFwId(ecifData.getReqSeqNo());
			txEvtNotice.setEventSysNo(ecifData.getOpChnlNo());
			txEvtNotice.setEventDealStat(DataSynchroData.EVENTDEALSTAT_WAIT);
			// 添加了手动处理标识
			txEvtNotice.setManualFlag("0");// 系统处理
			txEvtNotice.setManualStat("0");// 未手动处理
			baseDAO.persist(txEvtNotice);
			baseDAO.flush();
		}
	}
}

/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����SynchroEvent.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:33:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SynchroEvent
 * @�������� ����ͬ���¼�֪ͨ
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:33:43
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:33:43
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
		log.info(String.format("��ȡ����������Ϣ[%s_%s]", txCode,opSyscd));
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
					log.info(String.format("ͬ��������������ڶ�Ӧ�����ã��ý���[%s]��Ҫͬ������", ecifData.getTxCode()));
					SynchroEventDAO eventDAO = (SynchroEventDAO) SpringContextUtils.getBean("synchroEventDAO");
					eventDAO.synchroEvent(ecifData, txSyncConf);
				} catch (Exception e) {
					log.error("����ͬ���¼�¼���쳣", e);
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
		/** ����ͬ������ ***/
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

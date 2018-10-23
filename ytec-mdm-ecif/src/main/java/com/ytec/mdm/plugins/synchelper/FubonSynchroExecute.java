/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper
 * @�ļ�����SynchroToSystemExecute.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:59:55
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.sync.listen.DataSynchroData;
import com.ytec.mdm.integration.sync.ptsync.AbsSynchroExecutor;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;
import com.ytec.mdm.server.common.DataSynchroConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SynchroToSystemExecute
 * @�����������ڲ�ѯ����,ת��������ϵͳ�ӿڵı��Ĵ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:59:56
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:59:56
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public abstract class FubonSynchroExecute extends AbsSynchroExecutor {
	private static Logger log = LoggerFactory.getLogger(FubonSynchroExecute.class);

	@Override
	public boolean execute(TxSyncConf txSyncConf, TxEvtNotice txEvtNotice) {
		// TODO Auto-generated method stub

		/*
		 * StackTraceElement stack_s[] = Thread.currentThread().getStackTrace();
		 * for (StackTraceElement stack : stack_s) {
		 * System.out.printf("File:%s, class:%s, method:%s,lineNumber:%s\n", stack.getFileName(), stack.getClassName(), stack.getMethodName(), stack.getLineNumber());
		 * }//
		 */

		String destSys = txSyncConf.getDestSysNo();
		String syncDealMethod = txSyncConf.getSyncDealMethod();
		String destSysNo = txSyncConf.getDestSysNo();
		String clientName = DataSynchroData.getClientByType(syncDealMethod);
		Map<String, Object> clientMap = DataSynchroConfiger.getClientArg(clientName, destSys);

		Set keys = clientMap.keySet();
		Iterator itr = keys.iterator();
		while (itr.hasNext()) {
			Object key = itr.next();
			System.out.printf("%s.%s---->>>key:%s=value:%s\n", this.getClass().getSimpleName(), "execute", key, clientMap.get(key));
		}
		IClient client = (IClient) SpringContextUtils.getBean(clientName);
		try {
			client.init(clientMap);
			ClientResponse resp = client.sendMsg("<xml><body></body></xml>");
			if (resp.isSuccess()) {
				String respMsg = resp.getResponseMsg();
				System.out.println("FubonSynchroExecute.execute---->>>"+respMsg);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	public abstract boolean asseReqMsg(TxSyncConf txSyncConf, Document doc);

}

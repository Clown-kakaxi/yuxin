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
public abstract class SynchroToSystemExecute extends AbsSynchroExecutor {
	private static Logger log = LoggerFactory.getLogger(SynchroToSystemExecute.class);

	@Override
	public boolean execute(TxSyncConf txSyncConf, TxEvtNotice txEvtNotice) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(txSyncConf.getSyncContentDef())) {
			log.error("ͬ�����ݶ���Ϊ��");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ�����ݶ���Ϊ��");
			return false;
		}
		/** ͬ�����ݶ����ʽΪ:ͬ����ѯ������|��ϵͳ�ӿ�ģ������ ***/
		String[] contentDefs = StringUtil.split(txSyncConf.getSyncContentDef(), '|');
		if (contentDefs == null || contentDefs.length != 2) {
			log.error("ͬ�����ݶ������Ӧ��Ϊ:ͬ����ѯ������|��ϵͳ�ӿ�ģ������");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ�����ݶ������Ӧ��Ϊ:ͬ����ѯ������|��ϵͳ�ӿ�ģ������");
			return false;
		}
		String txCode = contentDefs[0];
		if (StringUtil.isEmpty(txEvtNotice.getCustNo())) {
			log.error("ͬ���ͻ��Ŀͻ���Ϊ��");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ���ͻ��Ŀͻ���Ϊ��");
			return false;
		}
		if (StringUtil.isEmpty(txCode)) {
			log.error("ͬ������{},ͬ������Ϊ��,Ӧ��дͬ����ѯ������", txSyncConf.getSyncConfId());
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TXCODE_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ������Ϊ��,Ӧ��дͬ����ѯ������");
			return false;
		} else {
			if (!TxModelHolder.txDefCheck(txCode)) {
				log.error("ͬ������{},ͬ����ѯ����{}�����ڻ���ͣ��", txSyncConf.getSyncConfId(), txCode);
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TXCODE_ERROR.getCode());
				txEvtNotice.setEventDealInfo(String.format("ͬ����ѯ����%s�����ڻ���ͣ��", txCode));
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
					log.error("ִ��ͬ����ѯ����{}����", txCode);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TX_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("ִ��ͬ����ѯ����%s����", txCode));
					log.error("������Ϣ", e);
					return false;
				}
				if (!ecifData.isSuccess() || ErrorCode.WRN_NONE_FOUND.getCode().equals(ecifData.getRepStateCd())) {
					log.error("ִ��ͬ����ѯ����{}����[{}]", txCode, ecifData.getDetailDes());
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_TX_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("ִ��ͬ����ѯ����%s����[%s]", txCode, ecifData.getDetailDes()));
					return false;
				}
				if (ErrorCode.WRN_NONE_FOUND.getCode().equals(ecifData.getRepStateCd())) {
					log.error("ִ��ͬ����ѯ����{}��ϢΪ��[{}]", txCode, ecifData.getDetailDes());
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_INFO_ERROR.getCode());
					txEvtNotice.setEventDealInfo(String.format("ִ��ͬ����ѯ����%s��ϢΪ��[%s]", txCode, ecifData.getDetailDes()));
					return false;
				}
				SyncXmlHelper syncXmlHelper = SyncXmlHelper.getInstance();
				Document doc = null;
				try {
					DocumentHelper.createDocument(ecifData.getRepNode());
					doc = syncXmlHelper.parseSyncXml(ecifData, contentDefs[1]);
				} catch (Exception e) {
					log.error("ת���ӿ�{}ʧ��", contentDefs[1]);
					log.error("ת���ӿ�ʧ��", e);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
					txEvtNotice.setEventDealInfo("��װ������ͱ���ͷʧ��");
					return false;
				}
				if (doc == null) {
					log.error("ת���ӿ�{}����Ϊ��", contentDefs[1]);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
					txEvtNotice.setEventDealInfo("��װ������ͱ���ͷʧ��");
					return false;
				}
				if (!asseReqMsg(txSyncConf, doc)) {
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
					txEvtNotice.setEventDealInfo("��װ������ͱ���ͷʧ��");
					return false;
				}
			}
		}
		return true;
	}

	public abstract boolean asseReqMsg(TxSyncConf txSyncConf, Document doc);

}

/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.svc
 * @�ļ�����SynchrTransmiHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:07:38
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.integration.sync.pubsub.SynchrTransmi;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�SynchrTransmiHandler
 * @������������ͬ������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:07:38   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:07:38
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SynchrTransmiHandler extends SynchrTransmi {
	private static Logger log = LoggerFactory
			.getLogger(SynchrTransmiHandler.class);

	@Override
	public void createSynchrMsg() {
		// TODO Auto-generated method stub
		asyn = false; 		  // ���ô���ͬ�����첽 ��true:�첽 ,����Ҫ���; false:ͬ��,��Ҫ���
		clientName = "httpClient";  // ͬ���ͻ���
		destSysNo = "01";     // ����ϵͳ
		
		/*********��ԭ����ת��**********/
		requestMsg=ecifData.getPrimalMsg();
	}

	@Override
	public void reducedSynchrResult() {
		// TODO Auto-generated method stub
		/*********������Ӧ����**********/
		log.info("������Ӧ����");
		log.info(responseMsg);

	}

}

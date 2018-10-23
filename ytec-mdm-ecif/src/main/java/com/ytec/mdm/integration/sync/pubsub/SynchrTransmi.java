/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.sync.pubsub
 * @�ļ�����SynchrTransmi.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:01:16
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.sync.pubsub;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IDispatchFun;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SynchrTransmi
 * @������������ͬ����ת��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:01:16   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:01:16
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public  abstract class SynchrTransmi implements IDispatchFun {
	private static Logger log = LoggerFactory.getLogger(SynchrTransmi.class);
	protected EcifData ecifData;
	/**
	 * @��������:asyn
	 * @��������:true:�첽 ,����Ҫ���; false:ͬ��,��Ҫ���
	 * @since 1.0.0
	 */
	protected boolean asyn=false;
	/**
	 * @��������:clientName
	 * @��������:ͬ���ͻ���
	 * @since 1.0.0
	 */
	protected String clientName;
	/**
	 * @��������:destSysNo
	 * @��������:����ϵͳ
	 * @since 1.0.0
	 */
	protected String destSysNo;
	/**
	 * @��������:requestMsg
	 * @��������:ͬ������
	 * @since 1.0.0
	 */
	protected String requestMsg;
	/**
	 * @��������:responseMsg
	 * @��������:ͬ������
	 * @since 1.0.0
	 */
	protected String responseMsg;
	
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IDispatchFun#execute(com.ytec.mdm.base.bo.EcifData)
	 */
	public void execute(EcifData ecifData){
		this.ecifData=ecifData;
		log.info("����ͬ������");
		createSynchrMsg();
		if(!ecifData.isSuccess()){
			return ;
		}
		log.info("��ȡͬ�����Ϳͻ���");
		IClient client =  getSyncClient();
		if(client!=null){
			ClientResponse clientResponse = client.sendMsg(requestMsg);
			if(clientResponse.isSuccess()){
				responseMsg=clientResponse.getResponseMsg();
				if(responseMsg==null){
					ecifData.setStatus(ErrorCode.ERR_COMM_UNKNOWN_ERROR.getCode(),"ͬ�����ͽ���ʧ��");
					return;
				}
				if(!asyn){
					log.info("����ͬ�����Ľ��");
					reducedSynchrResult();
				}
			}else{
				log.error("�ͻ��˷���ʧ��");
				ecifData.setStatus(ErrorCode.ERR_SYNCHRO_SEND_ERROR.getCode(),"�ͻ��˷���ʧ��");
				return;
			}
		}else{
			ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),"��ȡͬ�����Ϳͻ���ʧ��");
		}
	}
	/**
	 * @��������:createSynchrMsg
	 * @��������:����ͬ������ ���� ͬ���ͻ��ˣ�����ϵͳ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public abstract void createSynchrMsg();
	/**
	 * @��������:reducedSynchrResult
	 * @��������:����ͬ�����Ľ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public abstract void reducedSynchrResult();
	
	
	/**
	 * @��������:getSyncClient
	 * @��������:��ȡͬ�����Ϳͻ���
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	private IClient getSyncClient(){
		IClient client = null;
		try {
			client = (IClient) SpringContextUtils.getBean(clientName);
		} catch (Exception e) {
			log.error("��ȡͬ�����Ϳͻ���ʧ��", e);
		}
		if (client == null) {
			log.error("��ȡͬ�����Ϳͻ���{}ʧ��", clientName);
			return null;
		} else {
			Map clientArg = ServerConfiger.getClientArg(
					clientName, destSysNo);
			if (clientArg == null) {
				log.error("��ȡͬ�����Ϳͻ���{}����ʧ��", clientName);
				return null;
			} else {
				try {
					client.init(clientArg);
				} catch (Exception e) {
					log.error("��ʼ��ͬ�����Ϳͻ���{}����ʧ��", clientName);
					return null;
				}
				return client;
			}
		}
	}

}

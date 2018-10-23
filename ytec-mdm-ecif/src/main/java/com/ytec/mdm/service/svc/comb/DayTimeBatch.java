/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.svc.comb
 * @�ļ�����DayTimeBatch.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:04:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.svc.comb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.csv.CsvBean;
import com.ytec.mdm.domain.txp.TxBatchDetail;
import com.ytec.mdm.domain.txp.TxBatchLog;
import com.ytec.mdm.integration.adapter.dao.BatchLogHelper;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.interfaces.common.even.EvenSubject;
import com.ytec.mdm.interfaces.common.even.Subject;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DayTimeBatch
 * @����������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:04:28   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:04:28
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class DayTimeBatch implements IEcifBizLogic,Runnable{
	private static Logger log = LoggerFactory.getLogger(DayTimeBatch.class);
	protected EcifData data;
	protected boolean asyn;						//true:�첽  false:ͬ��
	protected boolean needAnother=false;        //�ý������һ�룬��Ҫ��һ�����׼�����
	protected long readNum=0;					//��ȡ��¼��(�ܲ�����¼)
	protected long skipNum=0;					//������¼��(��֤���ϸ�����)
	protected long dealNum=0;					//�����¼��(ͨ����֤������)
	protected long succNum=0;					//�ɹ���¼��
	protected long failNum=0;					//ʧ�ܼ�¼��
	protected BatchLogHelper batchLogHelper; 	//������־������
	protected TxBatchLog txBatchLog;
	private List<TxBatchDetail> txBatchDetailList;
	
	public void process(EcifData ecifData) throws Exception {
		// TODO Auto-generated method stub
		data=ecifData;
		/**��ȡ��Ϣ***/
		getControlInfo();
		if(!data.isSuccess()){
			return;
		}
		batchLogHelper=(BatchLogHelper)SpringContextUtils.getBean("batchLogHelper");
		if((txBatchLog=batchLogHelper.getTxBatchByTxFwId(ecifData))!=null){
			if(!batchLogHelper.isAsynBatchLog(txBatchLog)){
				ecifData.setStatus(ErrorCode.WRN_BATCH_HAS_SKIP.getCode(),"��ˮ%s�����������ύ",ecifData.getReqSeqNo());
				log.warn("��ˮ{}���������ظ��ύ",ecifData.getReqSeqNo());
				return;
			}
		}else{
			/**��¼������־*/
			try{
				txBatchLog=batchLogHelper.txBatchBefore(ecifData);
			}catch(Exception e){
				log.error("������������{}������ʼ��־ʧ��,��ˮ��{}",ecifData.getTxCode(),ecifData.getReqSeqNo());
				log.error("����:",e);
			}
			txBatchDetailList=new ArrayList<TxBatchDetail>();
		}
		if(asyn){
			log.info("�첽������������");
			try{
				Thread svcTread=new Thread(this);
				svcTread.start();
			}catch(Exception e){
				log.error("����������ʧ��",e);
				data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), "����������ʧ��");
				return;
			}
		}else{
			log.info("ͬ��������������");
			run();
		}
		return;
	}
	public void run() {
		try{
			try{
				batchLogHelper.txBatchBegin(data,txBatchLog);
			}catch(Exception e){
				log.error("������������{}����ִ����־ʧ��,��ˮ��{}",data.getTxCode(),data.getReqSeqNo());
				log.error("����:",e);
			}
			getBatchFile();
			if(!data.isSuccess()){
				log.error("��ȡ�����ļ�ʧ��");
				return;
			}
			executeBatch();
			if(!data.isSuccess()){
				log.error("���������ļ�ʧ��");
				return;
			}
			putBatchFile();
			if(!data.isSuccess()){
				log.error("�ϴ������ļ�ʧ��");
				return;
			}
			synchroBatch();
			if(!data.isSuccess()){
				log.error("������ɺ�����ͬ��ʧ��");
				return;
			}
		}catch(Exception e){
			log.error("������ʧ��",e);
			data.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
		}finally{
			if(txBatchLog!=null){
				setLogStatistic();
				try{
					batchLogHelper.txBatchEnd(txBatchLog,data.isSuccess(),needAnother);
				}catch(Exception e){
					log.error("��������������ˮ��{}���������־ʧ��",txBatchLog.getTxFwId());
					log.error("����:",e);
				}
			}
			try{
				batchLogHelper.noteTxBatchDetail(txBatchDetailList, data);
				if(asyn){//������첽�����������¼�֪ͨ
					Subject evenSubject=EvenSubject.getInstance();
					/**�¼�֪ͨ*/
					evenSubject.eventNotify(data);
				}
			}catch(Exception e){
				log.error("����:",e);
			}
		}
		log.info("���������������׽���");
	}

	/***
	 * ͳ�Ʋ�����¼
	 * @param flag  true:�ɹ�  false:ʧ��
	 */
	protected void statisticalRecord(boolean flag){
		dealNum++;				//�����¼��
		if(flag){
			succNum++;			//�ɹ���¼��
		}else{
			failNum++;			//ʧ�ܼ�¼��
		}
	}
	
	/**
	 * @��������:setLogStatistic
	 * @��������:����־������ͳ����Ϣ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	protected void setLogStatistic(){
		txBatchLog.setDealNum(dealNum);
		txBatchLog.setFailNum(failNum);
		txBatchLog.setReadNum(readNum);
		txBatchLog.setSkipNum(skipNum);
		txBatchLog.setSuccNum(succNum);
	}
	
	protected void addTxBatchDetail(String recNo,String recText,String dealResult,String dealDesc){
		TxBatchDetail txBatchDetail=new TxBatchDetail();
		txBatchDetail.setRecNo(recNo);
		txBatchDetail.setRecText(recText);
		txBatchDetail.setDealResult(dealResult);
		txBatchDetail.setDealDesc(dealDesc);
		txBatchDetailList.add(txBatchDetail);
	}
	
	protected void addTxBatchDetail(CsvBean batchLine){
		TxBatchDetail txBatchDetail=new TxBatchDetail();
		txBatchDetail.setRecNo(batchLine.getRecNo());
		txBatchDetail.setRecText(batchLine.getPrimalLineMsg());
		txBatchDetail.setDealResult(batchLine.getErrorCode());
		txBatchDetail.setDealDesc(batchLine.getErrorDesc());
		txBatchDetailList.add(txBatchDetail);
	}
	
	/***��ȡ��Ϣ***/
	public abstract void getControlInfo();
	
	/***��ȡ�����ļ�****/
	public abstract void getBatchFile();
	
	/***���������ļ�****/
	public abstract void executeBatch();
	
	/**�ϴ������ļ�****/
	public abstract void putBatchFile();
	
	/***������ɺ�����ͬ��****/
	public abstract void synchroBatch();
}

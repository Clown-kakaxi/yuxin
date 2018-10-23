/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����TxFlowControlValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-15-����10:56:41
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.integration.check;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.integration.check.validation.AbstractValidationChain;
import com.ytec.mdm.interfaces.common.even.CensusObject;
import com.ytec.mdm.interfaces.common.even.StatisticsEvent;
import com.ytec.mdm.server.common.BusinessCfg;
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxFlowControlValidation
 * @����������������
 * @��������:�Խ�������,ÿֻ����,ÿ����Χϵͳ�ķ��� ��������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-15 ����10:56:41   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-15 ����10:56:41
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxFlowControlValidation extends AbstractValidationChain {

	/**
	 * @��������:visitTotalCount
	 * @��������:��������
	 * @since 1.0.0
	 */
	private int visitTotalCount=0;
	/**
	 * @��������:txCodeVisitCount
	 * @��������:ÿֻ���� �����ʴ���
	 * @since 1.0.0
	 */
	private Map<String,Integer> txCodeVisitCount=new HashMap<String,Integer>();
	/**
	 * @��������:reqSysVisitCount
	 * @��������:ÿ����Χϵͳ�����ʴ���
	 * @since 1.0.0
	 */
	private Map<String,Integer> reqSysVisitCount=new HashMap<String,Integer>();
	
	/**
	 * @��������:txMaxTPS
	 * @��������:��������TPS
	 * @since 1.0.0
	 */
	private double txMaxTPS;
	
	/**
	 * @��������:queueMaxSize
	 * @��������:��������С
	 * @since 1.0.0
	 */
	private int queueMaxSize;
	
	/**
	 * @��������:txTimeQueue
	 * @��������:����ʱ���¼����
	 * @since 1.0.0
	 */
	private  final ConcurrentLinkedQueue<Long> txTimeQueue=new ConcurrentLinkedQueue<Long>();
	
	/**
	 * @��������:queueSize
	 * @��������:���д�С
	 * @since 1.0.0
	 */
	private final AtomicInteger queueSize=new AtomicInteger(0); 
	
	
	/**
	 *@���캯�� 
	 */
	public TxFlowControlValidation() {
		super();
		/***�ͻ������÷��ʿ���***/
		//visitTotalCount=5;
		//txCodeVisitCount.put("QECIF2_003", 2);
		reqSysVisitCount.put("CBS", 100);
		txMaxTPS=BusinessCfg.getInt("txMaxTPS");
		queueMaxSize=100;
		int index=(int)Math.round(txMaxTPS);
		if(index==0){
			queueMaxSize=1;
		}if(index<queueMaxSize){
			queueMaxSize=index;
		}
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.check.validation.AbstractValidationChain#reqMsgValidation(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		if (txMaxTPS > 0) {
			if (queueSize.compareAndSet(queueMaxSize, queueMaxSize)) {
				/** �������� **/
				long headTime = ecifData.getStopWatch().getStartTime();
				txTimeQueue.offer(headTime);
				double time_i;
				if ((time_i = headTime - txTimeQueue.poll()) != 0) {
					double tps = Double.valueOf((queueMaxSize * 1000)) / time_i;
					if (tps > txMaxTPS) {
						ecifData.setStatus(
								ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),
								"������æ�����Ժ��ٷ���");
						log.warn("������æ���Ժ��ٷ���[tps={}]", (queueMaxSize * 1000)
								/ time_i);
						return false;
					}
				}
			} else {
				/** ����û���� **/
				long headTime = ecifData.getStopWatch().getStartTime();
				txTimeQueue.offer(headTime);
				double time_i;
				if (txTimeQueue.peek() != null
						&& (time_i = headTime - txTimeQueue.peek()) != 0) {
					double tps = Double
							.valueOf((queueSize.incrementAndGet() * 1000))
							/ time_i;
					if (tps > txMaxTPS) {
						ecifData.setStatus(
								ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),
								"������æ�����Ժ��ٷ���");
						log.warn("������æ���Ժ��ٷ���[tps={}]", (queueMaxSize * 1000)
								/ time_i);
						return false;
					}
				}
			}
		}
		/**�Ƿ񳬹�����**/
		if(visitTotalCount>0&&StatisticsEvent.txCensus.getTotal()>=visitTotalCount){
			ecifData.setStatus(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"�˷������ѳ���������,������ͣ����");
			log.warn("�˷��������������ѳ��������ʴ���[{}]",visitTotalCount);
			return false;
		}
		/**�Ƿ�˴ν��׳�������**/
		int visitCount=0;
		CensusObject visitCountNow=null;
		visitCount=txCodeVisitCount.get(ecifData.getTxCode())==null?0:txCodeVisitCount.get(ecifData.getTxCode());
		visitCountNow =StatisticsEvent.txCodeCensus.get(ecifData.getTxCode());
		if(visitCount>0&&visitCountNow!=null &&visitCountNow.getTotal()>=visitCount){
			ecifData.setStatus(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"��֧�����ѳ���������,������ͣ����");
			log.warn("��֧���״��������ѳ��������ʴ���[{}]",visitTotalCount);
			return false;
		}
		/**�Ƿ��ϵͳ/�����������ʴ���**/
		visitCount=reqSysVisitCount.get(ecifData.getOpChnlNo())==null?0:reqSysVisitCount.get(ecifData.getOpChnlNo());
		visitCountNow =StatisticsEvent.reqSysCensus.get(ecifData.getOpChnlNo());
		if(visitCount>0&&visitCountNow!=null &&visitCountNow.getTotal()>=visitCount){
			ecifData.setStatus(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"��ϵͳ�������ѳ���������,������ͣ����");
			log.warn("��ϵͳ/���������ѳ��������ʴ���[{}]",visitTotalCount);
			return false;
		}
		return true;
	}

}

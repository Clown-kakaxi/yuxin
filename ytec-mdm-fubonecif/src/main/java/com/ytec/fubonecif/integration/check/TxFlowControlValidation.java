/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：TxFlowControlValidation.java
 * @版本信息：1.0.0
 * @日期：2014-4-15-上午10:56:41
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxFlowControlValidation
 * @类描述：流量控制
 * @功能描述:对交易总量,每只交易,每个外围系统的访问 流量控制
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-15 上午10:56:41   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-15 上午10:56:41
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxFlowControlValidation extends AbstractValidationChain {

	/**
	 * @属性名称:visitTotalCount
	 * @属性描述:访问总量
	 * @since 1.0.0
	 */
	private int visitTotalCount=0;
	/**
	 * @属性名称:txCodeVisitCount
	 * @属性描述:每只交易 最大访问次数
	 * @since 1.0.0
	 */
	private Map<String,Integer> txCodeVisitCount=new HashMap<String,Integer>();
	/**
	 * @属性名称:reqSysVisitCount
	 * @属性描述:每个外围系统最大访问次数
	 * @since 1.0.0
	 */
	private Map<String,Integer> reqSysVisitCount=new HashMap<String,Integer>();
	
	/**
	 * @属性名称:txMaxTPS
	 * @属性描述:交易最大的TPS
	 * @since 1.0.0
	 */
	private double txMaxTPS;
	
	/**
	 * @属性名称:queueMaxSize
	 * @属性描述:队列最大大小
	 * @since 1.0.0
	 */
	private int queueMaxSize;
	
	/**
	 * @属性名称:txTimeQueue
	 * @属性描述:交易时间记录队列
	 * @since 1.0.0
	 */
	private  final ConcurrentLinkedQueue<Long> txTimeQueue=new ConcurrentLinkedQueue<Long>();
	
	/**
	 * @属性名称:queueSize
	 * @属性描述:队列大小
	 * @since 1.0.0
	 */
	private final AtomicInteger queueSize=new AtomicInteger(0); 
	
	
	/**
	 *@构造函数 
	 */
	public TxFlowControlValidation() {
		super();
		/***客户化配置访问控制***/
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
				/** 队列满了 **/
				long headTime = ecifData.getStopWatch().getStartTime();
				txTimeQueue.offer(headTime);
				double time_i;
				if ((time_i = headTime - txTimeQueue.poll()) != 0) {
					double tps = Double.valueOf((queueMaxSize * 1000)) / time_i;
					if (tps > txMaxTPS) {
						ecifData.setStatus(
								ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),
								"服务器忙，请稍后再访问");
						log.warn("服务器忙，稍后再访问[tps={}]", (queueMaxSize * 1000)
								/ time_i);
						return false;
					}
				}
			} else {
				/** 队列没有满 **/
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
								"服务器忙，请稍后再访问");
						log.warn("服务器忙，稍后再访问[tps={}]", (queueMaxSize * 1000)
								/ time_i);
						return false;
					}
				}
			}
		}
		/**是否超过总量**/
		if(visitTotalCount>0&&StatisticsEvent.txCensus.getTotal()>=visitTotalCount){
			ecifData.setStatus(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"此服务器已超过最大访问,对其暂停访问");
			log.warn("此服务器处理请求已超过最大访问次数[{}]",visitTotalCount);
			return false;
		}
		/**是否此次交易超过总量**/
		int visitCount=0;
		CensusObject visitCountNow=null;
		visitCount=txCodeVisitCount.get(ecifData.getTxCode())==null?0:txCodeVisitCount.get(ecifData.getTxCode());
		visitCountNow =StatisticsEvent.txCodeCensus.get(ecifData.getTxCode());
		if(visitCount>0&&visitCountNow!=null &&visitCountNow.getTotal()>=visitCount){
			ecifData.setStatus(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"该支交易已超过最大访问,对其暂停访问");
			log.warn("该支交易处理请求已超过最大访问次数[{}]",visitTotalCount);
			return false;
		}
		/**是否该系统/渠道超过访问次数**/
		visitCount=reqSysVisitCount.get(ecifData.getOpChnlNo())==null?0:reqSysVisitCount.get(ecifData.getOpChnlNo());
		visitCountNow =StatisticsEvent.reqSysCensus.get(ecifData.getOpChnlNo());
		if(visitCount>0&&visitCountNow!=null &&visitCountNow.getTotal()>=visitCount){
			ecifData.setStatus(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"该系统或渠道已超过最大访问,对其暂停访问");
			log.warn("该系统/渠道请求已超过最大访问次数[{}]",visitTotalCount);
			return false;
		}
		return true;
	}

}

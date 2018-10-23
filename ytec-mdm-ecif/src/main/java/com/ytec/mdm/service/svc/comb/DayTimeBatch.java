/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.svc.comb
 * @文件名：DayTimeBatch.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:04:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：DayTimeBatch
 * @类描述：联机批量
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:04:28   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:04:28
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class DayTimeBatch implements IEcifBizLogic,Runnable{
	private static Logger log = LoggerFactory.getLogger(DayTimeBatch.class);
	protected EcifData data;
	protected boolean asyn;						//true:异步  false:同步
	protected boolean needAnother=false;        //该交易完成一半，需要另一个交易继续。
	protected long readNum=0;					//读取记录数(总操作记录)
	protected long skipNum=0;					//跳过记录数(验证不合格数量)
	protected long dealNum=0;					//处理记录数(通过验证的条数)
	protected long succNum=0;					//成功记录数
	protected long failNum=0;					//失败记录数
	protected BatchLogHelper batchLogHelper; 	//批量日志帮助类
	protected TxBatchLog txBatchLog;
	private List<TxBatchDetail> txBatchDetailList;
	
	public void process(EcifData ecifData) throws Exception {
		// TODO Auto-generated method stub
		data=ecifData;
		/**提取信息***/
		getControlInfo();
		if(!data.isSuccess()){
			return;
		}
		batchLogHelper=(BatchLogHelper)SpringContextUtils.getBean("batchLogHelper");
		if((txBatchLog=batchLogHelper.getTxBatchByTxFwId(ecifData))!=null){
			if(!batchLogHelper.isAsynBatchLog(txBatchLog)){
				ecifData.setStatus(ErrorCode.WRN_BATCH_HAS_SKIP.getCode(),"流水%s联机批量已提交",ecifData.getReqSeqNo());
				log.warn("流水{}联机批量重复提交",ecifData.getReqSeqNo());
				return;
			}
		}else{
			/**记录批量日志*/
			try{
				txBatchLog=batchLogHelper.txBatchBefore(ecifData);
			}catch(Exception e){
				log.error("联机批量交易{}处理起始日志失败,流水号{}",ecifData.getTxCode(),ecifData.getReqSeqNo());
				log.error("错误:",e);
			}
			txBatchDetailList=new ArrayList<TxBatchDetail>();
		}
		if(asyn){
			log.info("异步处理批量交易");
			try{
				Thread svcTread=new Thread(this);
				svcTread.start();
			}catch(Exception e){
				log.error("启动批处理失败",e);
				data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), "启动批处理失败");
				return;
			}
		}else{
			log.info("同步处理批量交易");
			run();
		}
		return;
	}
	public void run() {
		try{
			try{
				batchLogHelper.txBatchBegin(data,txBatchLog);
			}catch(Exception e){
				log.error("联机批量交易{}处理执行日志失败,流水号{}",data.getTxCode(),data.getReqSeqNo());
				log.error("错误:",e);
			}
			getBatchFile();
			if(!data.isSuccess()){
				log.error("获取批量文件失败");
				return;
			}
			executeBatch();
			if(!data.isSuccess()){
				log.error("处理批量文件失败");
				return;
			}
			putBatchFile();
			if(!data.isSuccess()){
				log.error("上传批量文件失败");
				return;
			}
			synchroBatch();
			if(!data.isSuccess()){
				log.error("处理完成后数据同步失败");
				return;
			}
		}catch(Exception e){
			log.error("批处理失败",e);
			data.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
		}finally{
			if(txBatchLog!=null){
				setLogStatistic();
				try{
					batchLogHelper.txBatchEnd(txBatchLog,data.isSuccess(),needAnother);
				}catch(Exception e){
					log.error("联机批量交易流水号{}处理完成日志失败",txBatchLog.getTxFwId());
					log.error("错误:",e);
				}
			}
			try{
				batchLogHelper.noteTxBatchDetail(txBatchDetailList, data);
				if(asyn){//如果是异步操作，调用事件通知
					Subject evenSubject=EvenSubject.getInstance();
					/**事件通知*/
					evenSubject.eventNotify(data);
				}
			}catch(Exception e){
				log.error("错误:",e);
			}
		}
		log.info("联机处理批量交易结束");
	}

	/***
	 * 统计操作记录
	 * @param flag  true:成功  false:失败
	 */
	protected void statisticalRecord(boolean flag){
		dealNum++;				//处理记录数
		if(flag){
			succNum++;			//成功记录数
		}else{
			failNum++;			//失败记录数
		}
	}
	
	/**
	 * @函数名称:setLogStatistic
	 * @函数描述:在日志中设置统计信息
	 * @参数与返回说明:
	 * @算法描述:
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
	
	/***提取信息***/
	public abstract void getControlInfo();
	
	/***获取批量文件****/
	public abstract void getBatchFile();
	
	/***处理批量文件****/
	public abstract void executeBatch();
	
	/**上传批量文件****/
	public abstract void putBatchFile();
	
	/***处理完成后数据同步****/
	public abstract void synchroBatch();
}

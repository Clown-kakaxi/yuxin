/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.svc.atomic
 * @文件名：DayTimeBatchAccompany.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:02:14
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.svc.atomic;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.base.util.csv.CSVReader;
import com.ytec.mdm.base.util.csv.CSVWriter;
import com.ytec.mdm.base.util.csv.CsvBean;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：DayTimeBatchAccompany
 * @类描述：联机批量帮助类,带并行计算
 * @功能描述:初始化一定数量的线程，同时并发处理。
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:02:15   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:02:15
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class DayTimeBatchAccompany extends DayTimeBatchHelper {
	private static Logger log = LoggerFactory.getLogger(DayTimeBatchAccompany.class);
	/**
	 * @属性名称:readNum
	 * @属性描述:读取记录数(总操作记录)
	 * @since 1.0.0
	 */
	protected final AtomicInteger readNum=new AtomicInteger(0);
	/**
	 * @属性名称:skipNum
	 * @属性描述:跳过记录数(验证不合格数量)
	 * @since 1.0.0
	 */
	protected final AtomicInteger skipNum=new AtomicInteger(0);
	/**
	 * @属性名称:dealNum
	 * @属性描述:处理记录数(通过验证的条数)
	 * @since 1.0.0
	 */
	protected final AtomicInteger dealNum=new AtomicInteger(0);
	/**
	 * @属性名称:succNum
	 * @属性描述:成功记录数
	 * @since 1.0.0
	 */
	protected final AtomicInteger succNum=new AtomicInteger(0);
	/**
	 * @属性名称:failNum
	 * @属性描述:失败记录数
	 * @since 1.0.0
	 */
	protected final AtomicInteger failNum=new AtomicInteger(0);
	
	/**
	 * @属性名称:reader
	 * @属性描述:数据读取对象
	 * @since 1.0.0
	 */
	protected CSVReader reader = null;
	/**
	 * @属性名称:noNext
	 * @属性描述:是否有下一条数据
	 * @since 1.0.0
	 */
	protected final AtomicBoolean noNext = new AtomicBoolean(false); 
	/**
	 * @属性名称:batchReqFile
	 * @属性描述:文件名称
	 * @since 1.0.0
	 */
	protected String batchReqFile = null;
	/**
	 * @属性名称:lno
	 * @属性描述:数据行号
	 * @since 1.0.0
	 */
	protected AtomicInteger lno=new AtomicInteger(0);
	/**
	 * @属性名称:queue
	 * @属性描述:数据结果存放队列
	 * @since 1.0.0
	 */
	protected List<CsvBean> queue=new CopyOnWriteArrayList<CsvBean>();
	
	protected int threadNum=10;
	@Override
	public void executeBatch() {
		if (batchReqFiles == null) {
			log.error("请求批量文件设置为空");
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
					"请求批量文件设置为空");
			return;
		}
		if(fileCharSet==null){
			fileCharSet=this.data.getCharsetName();
		}
		CSVWriter writer = null;
		for (int index = 0; index < batchReqFiles.length; index++) {
			batchReqFile = batchReqFiles[index];
			lno.set(0);
			if (StringUtil.isEmpty(batchReqFile)) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"批量文件名为空");
				log.error("批量文件名为空");
				return;
			}
			if (StringUtil.isEmpty(batchRspFiles[index])) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"响应批量文件名为空");
				log.error("响应批量文件名为空");
				return;
			}
			try {
				reader = new CSVReader(new InputStreamReader(new FileInputStream(localPath + "/"+ batchReqFile),fileCharSet), 
						separator, true);
				writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(localPath + "/"
						+ batchRspFiles[index]),fileCharSet), separator, true);
				int i;
				Thread[] threads=new Thread[threadNum];
				for(i=0;i<threadNum;i++){
					threads[i]=new Thread(new BatchWorker());
					threads[i].start();
				}
				for(i=0;i<threadNum;i++){
					threads[i].join();
				}
				List<CsvBean> tempList=new ArrayList<CsvBean>(queue);
				Comparator<CsvBean> comparator=new ComparatorCsvBean();
				Collections.sort(tempList, comparator);
				
				for(CsvBean nextLine:tempList){
					// ///////////////
					// 写返回结果
					// //////////////
					writer.writeNext(nextLine.getRspLineMsgs());
					
					/***
					 * 写明细日志
					 */
					addTxBatchDetail(nextLine);
				}
				queue.clear();
					
			} catch (Exception e) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"批量文件读取错误");
				log.error("批量文件{}读取错误", batchReqFile);
				log.error("读取错误信息:", e);
				return;
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (Exception e) {

					}
				}
				if (writer != null) {
					try {
						writer.close();
					} catch (Exception e) {

					}
				}
			}

		}

	}
	
	protected synchronized CsvBean readNext(){
		if(noNext.get()){
			return null;
		}
		CsvBean csvBean=null;
		try {
			csvBean = reader.readNext();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("读取数据异常",e);
			csvBean=null;
		}
		if(csvBean==null){
			noNext.set(true);
		}
		return csvBean;
	}
	
	/***
	 * 统计操作记录
	 * @param flag  true:成功  false:失败
	 */
	protected void statisticalRecord(boolean flag){
		dealNum.incrementAndGet();				//处理记录数
		if(flag){
			succNum.incrementAndGet();			//成功记录数
		}else{
			failNum.incrementAndGet();			//失败记录数
		}
	}
	
	/**
	 * @函数名称:setLogStatistic
	 * @函数描述:在日志中设置统计信息
	 * @参数与返回说明:
	 * @算法描述:
	 */
	protected void setLogStatistic(){
		txBatchLog.setDealNum(dealNum.longValue());
		txBatchLog.setFailNum(failNum.longValue());
		txBatchLog.setReadNum(readNum.longValue());
		txBatchLog.setSkipNum(skipNum.longValue());
		txBatchLog.setSuccNum(succNum.longValue());
	}
	
	/**
	 * @项目名称：ytec-mdm-ecif 
	 * @类名称：BatchWorker
	 * @类描述：多线程处理工作
	 * @功能描述:
	 * @创建人：wangzy1@yuchengtech.com
	 * @创建时间：2014-6-17 上午9:57:00   
	 * @修改人：wangzy1@yuchengtech.com
	 * @修改时间：2014-6-17 上午9:57:00
	 * @修改备注：
	 * @修改日期		修改人员		修改原因
	 * --------    	--------	----------------------------------------
	 * @version 1.0.0
	 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
	 * 
	 */
	private class BatchWorker implements Runnable{
		/**
		 *@构造函数 
		 * @param helper
		 */
		public BatchWorker() {
			// TODO Auto-generated constructor stub
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			CsvBean nextLine = null;
			int lo;
			while ((nextLine =readNext()) != null) {
				lo=lno.incrementAndGet();
				readNum.incrementAndGet();//读取记录数(总操作记录)
				nextLine.setRecNo(String.format("%s:%d", batchReqFile,lo));
				nextLine.setId(lo);
				// 加入数据验证
				if (!checkBatchOne(nextLine)) {
					skipNum.incrementAndGet();//跳过记录数(验证不合格数量)
					responseWarnOne(nextLine);
				} else {
					try {
						// 处理单条数据
						executeBatchOne(nextLine);
						if(nextLine.isSuccess()){
							statisticalRecord(true);
						}else{
							log.warn("处理单条数据[{}]错误", nextLine.getPrimalLineMsg());
							statisticalRecord(false);
							responseWarnOne(nextLine);
						}
					} catch (Exception e) {
						log.error("处理单条数据[{}]错误", nextLine.getPrimalLineMsg());
						log.error("处理单条数据错误:", e);
						statisticalRecord(false);
						nextLine.setResultState(ErrorCode.WRN_BATCH_HAS_FAILURE.getCode(), "处理错误");
						responseWarnOne(nextLine);
					}
				}
				// /////////////////
				// 响应数据转换
				// //////////////////
				transRspBatchOne(nextLine);
				// ///////////////
				// 写返回结果
				// //////////////
				queue.add(nextLine);
			}
		}
	}
	
	/**
	 * @项目名称：ytec-mdm-ecif 
	 * @类名称：ComparatorCsvBean
	 * @类描述：结果列表对比
	 * @功能描述:用于结果按行号排序
	 * @创建人：wangzy1@yuchengtech.com
	 * @创建时间：2014-6-17 上午9:56:20   
	 * @修改人：wangzy1@yuchengtech.com
	 * @修改时间：2014-6-17 上午9:56:20
	 * @修改备注：
	 * @修改日期		修改人员		修改原因
	 * --------    	--------	----------------------------------------
	 * @version 1.0.0
	 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
	 * 
	 */
	private class ComparatorCsvBean implements Comparator<CsvBean> {
		public int compare(CsvBean arg0, CsvBean arg1) {
			return arg0.getId()-arg1.getId();
		}
	}
	
}

/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.svc.atomic
 * @�ļ�����DayTimeBatchAccompany.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:02:14
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DayTimeBatchAccompany
 * @����������������������,�����м���
 * @��������:��ʼ��һ���������̣߳�ͬʱ��������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:02:15   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:02:15
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class DayTimeBatchAccompany extends DayTimeBatchHelper {
	private static Logger log = LoggerFactory.getLogger(DayTimeBatchAccompany.class);
	/**
	 * @��������:readNum
	 * @��������:��ȡ��¼��(�ܲ�����¼)
	 * @since 1.0.0
	 */
	protected final AtomicInteger readNum=new AtomicInteger(0);
	/**
	 * @��������:skipNum
	 * @��������:������¼��(��֤���ϸ�����)
	 * @since 1.0.0
	 */
	protected final AtomicInteger skipNum=new AtomicInteger(0);
	/**
	 * @��������:dealNum
	 * @��������:�����¼��(ͨ����֤������)
	 * @since 1.0.0
	 */
	protected final AtomicInteger dealNum=new AtomicInteger(0);
	/**
	 * @��������:succNum
	 * @��������:�ɹ���¼��
	 * @since 1.0.0
	 */
	protected final AtomicInteger succNum=new AtomicInteger(0);
	/**
	 * @��������:failNum
	 * @��������:ʧ�ܼ�¼��
	 * @since 1.0.0
	 */
	protected final AtomicInteger failNum=new AtomicInteger(0);
	
	/**
	 * @��������:reader
	 * @��������:���ݶ�ȡ����
	 * @since 1.0.0
	 */
	protected CSVReader reader = null;
	/**
	 * @��������:noNext
	 * @��������:�Ƿ�����һ������
	 * @since 1.0.0
	 */
	protected final AtomicBoolean noNext = new AtomicBoolean(false); 
	/**
	 * @��������:batchReqFile
	 * @��������:�ļ�����
	 * @since 1.0.0
	 */
	protected String batchReqFile = null;
	/**
	 * @��������:lno
	 * @��������:�����к�
	 * @since 1.0.0
	 */
	protected AtomicInteger lno=new AtomicInteger(0);
	/**
	 * @��������:queue
	 * @��������:���ݽ����Ŷ���
	 * @since 1.0.0
	 */
	protected List<CsvBean> queue=new CopyOnWriteArrayList<CsvBean>();
	
	protected int threadNum=10;
	@Override
	public void executeBatch() {
		if (batchReqFiles == null) {
			log.error("���������ļ�����Ϊ��");
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
					"���������ļ�����Ϊ��");
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
						"�����ļ���Ϊ��");
				log.error("�����ļ���Ϊ��");
				return;
			}
			if (StringUtil.isEmpty(batchRspFiles[index])) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"��Ӧ�����ļ���Ϊ��");
				log.error("��Ӧ�����ļ���Ϊ��");
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
					// д���ؽ��
					// //////////////
					writer.writeNext(nextLine.getRspLineMsgs());
					
					/***
					 * д��ϸ��־
					 */
					addTxBatchDetail(nextLine);
				}
				queue.clear();
					
			} catch (Exception e) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"�����ļ���ȡ����");
				log.error("�����ļ�{}��ȡ����", batchReqFile);
				log.error("��ȡ������Ϣ:", e);
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
			log.error("��ȡ�����쳣",e);
			csvBean=null;
		}
		if(csvBean==null){
			noNext.set(true);
		}
		return csvBean;
	}
	
	/***
	 * ͳ�Ʋ�����¼
	 * @param flag  true:�ɹ�  false:ʧ��
	 */
	protected void statisticalRecord(boolean flag){
		dealNum.incrementAndGet();				//�����¼��
		if(flag){
			succNum.incrementAndGet();			//�ɹ���¼��
		}else{
			failNum.incrementAndGet();			//ʧ�ܼ�¼��
		}
	}
	
	/**
	 * @��������:setLogStatistic
	 * @��������:����־������ͳ����Ϣ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	protected void setLogStatistic(){
		txBatchLog.setDealNum(dealNum.longValue());
		txBatchLog.setFailNum(failNum.longValue());
		txBatchLog.setReadNum(readNum.longValue());
		txBatchLog.setSkipNum(skipNum.longValue());
		txBatchLog.setSuccNum(succNum.longValue());
	}
	
	/**
	 * @��Ŀ���ƣ�ytec-mdm-ecif 
	 * @�����ƣ�BatchWorker
	 * @�����������̴߳�����
	 * @��������:
	 * @�����ˣ�wangzy1@yuchengtech.com
	 * @����ʱ�䣺2014-6-17 ����9:57:00   
	 * @�޸��ˣ�wangzy1@yuchengtech.com
	 * @�޸�ʱ�䣺2014-6-17 ����9:57:00
	 * @�޸ı�ע��
	 * @�޸�����		�޸���Ա		�޸�ԭ��
	 * --------    	--------	----------------------------------------
	 * @version 1.0.0
	 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
	 * 
	 */
	private class BatchWorker implements Runnable{
		/**
		 *@���캯�� 
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
				readNum.incrementAndGet();//��ȡ��¼��(�ܲ�����¼)
				nextLine.setRecNo(String.format("%s:%d", batchReqFile,lo));
				nextLine.setId(lo);
				// ����������֤
				if (!checkBatchOne(nextLine)) {
					skipNum.incrementAndGet();//������¼��(��֤���ϸ�����)
					responseWarnOne(nextLine);
				} else {
					try {
						// ����������
						executeBatchOne(nextLine);
						if(nextLine.isSuccess()){
							statisticalRecord(true);
						}else{
							log.warn("����������[{}]����", nextLine.getPrimalLineMsg());
							statisticalRecord(false);
							responseWarnOne(nextLine);
						}
					} catch (Exception e) {
						log.error("����������[{}]����", nextLine.getPrimalLineMsg());
						log.error("���������ݴ���:", e);
						statisticalRecord(false);
						nextLine.setResultState(ErrorCode.WRN_BATCH_HAS_FAILURE.getCode(), "�������");
						responseWarnOne(nextLine);
					}
				}
				// /////////////////
				// ��Ӧ����ת��
				// //////////////////
				transRspBatchOne(nextLine);
				// ///////////////
				// д���ؽ��
				// //////////////
				queue.add(nextLine);
			}
		}
	}
	
	/**
	 * @��Ŀ���ƣ�ytec-mdm-ecif 
	 * @�����ƣ�ComparatorCsvBean
	 * @������������б�Ա�
	 * @��������:���ڽ�����к�����
	 * @�����ˣ�wangzy1@yuchengtech.com
	 * @����ʱ�䣺2014-6-17 ����9:56:20   
	 * @�޸��ˣ�wangzy1@yuchengtech.com
	 * @�޸�ʱ�䣺2014-6-17 ����9:56:20
	 * @�޸ı�ע��
	 * @�޸�����		�޸���Ա		�޸�ԭ��
	 * --------    	--------	----------------------------------------
	 * @version 1.0.0
	 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
	 * 
	 */
	private class ComparatorCsvBean implements Comparator<CsvBean> {
		public int compare(CsvBean arg0, CsvBean arg1) {
			return arg0.getId()-arg1.getId();
		}
	}
	
}

/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.svc.atomic
 * @�ļ�����DayTimeBatchHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:02:14
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.svc.atomic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.base.util.csv.CSVReader;
import com.ytec.mdm.base.util.csv.CSVWriter;
import com.ytec.mdm.base.util.csv.CsvBean;
import com.ytec.mdm.service.svc.comb.DayTimeBatch;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DayTimeBatchHelper
 * @����������������������
 * @��������:
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
public abstract class DayTimeBatchHelper extends DayTimeBatch {
	private static Logger log = LoggerFactory
			.getLogger(DayTimeBatchHelper.class);
	protected String localPath;
	protected String[] batchReqFiles; // ���������ļ�
	protected String[] batchRspFiles; // ��Ӧ�����ļ�
	protected char separator; // �ָ���
	protected boolean noquotechar; // �Ƿ����ַ������÷� ��asd ���ǡ�asd��
	protected char quotechar; // �ַ������÷�
	protected int infoLength; // ÿ��������Ϣ��
	protected String fileCharSet;   //�ļ����ַ���

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
		CSVReader reader = null;
		CSVWriter writer = null;
		CsvBean nextLine = null;
		int lno;
		String batchReqFile = null;
		for (int index = 0; index < batchReqFiles.length; index++) {
			batchReqFile = batchReqFiles[index];
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
				lno = 0;
				while ((nextLine = reader.readNext()) != null) {
					lno++;
					readNum++;//��ȡ��¼��(�ܲ�����¼)
					nextLine.setRecNo(String.format("%s:%d", batchReqFile, lno));
					// ����������֤
					if (!checkBatchOne(nextLine)) {
						skipNum++;//������¼��(��֤���ϸ�����)
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
					writer.writeNext(nextLine.getRspLineMsgs());
					
					/***
					 * д��ϸ��־
					 */
					addTxBatchDetail(nextLine);
					
				}
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

	/***
	 * ����һ������
	 * 
	 * @param batchLine
	 *            һ�����ݵĶ���
	 */
	public abstract void executeBatchOne(CsvBean batchLine) throws Exception;

	/***
	 * ��֤һ������
	 * 
	 * @param batchLine
	 *            һ�����ݵĶ���
	 * @return
	 */
	public abstract boolean checkBatchOne(CsvBean batchLine);

	/***
	 * ת��һ����Ӧ��Ϣ
	 * 
	 * @param batchLine
	 *            һ�����ݵĶ���
	 * @return
	 */
	public abstract boolean transRspBatchOne(CsvBean batchLine);

	/***
	 * 
	 * @param batchLine
	 *            һ�����ݵĶ���
	 * @return
	 */
	public abstract void responseWarnOne(CsvBean batchLine);
}

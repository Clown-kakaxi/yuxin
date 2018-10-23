/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.svc.atomic
 * @文件名：DayTimeBatchHelper.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:02:14
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：DayTimeBatchHelper
 * @类描述：联机批量帮助类
 * @功能描述:
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
public abstract class DayTimeBatchHelper extends DayTimeBatch {
	private static Logger log = LoggerFactory
			.getLogger(DayTimeBatchHelper.class);
	protected String localPath;
	protected String[] batchReqFiles; // 请求批量文件
	protected String[] batchRspFiles; // 响应批量文件
	protected char separator; // 分隔符
	protected boolean noquotechar; // 是否有字符串引用符 如asd 还是“asd”
	protected char quotechar; // 字符串引用符
	protected int infoLength; // 每行数据信息数
	protected String fileCharSet;   //文件的字符集

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
		CSVReader reader = null;
		CSVWriter writer = null;
		CsvBean nextLine = null;
		int lno;
		String batchReqFile = null;
		for (int index = 0; index < batchReqFiles.length; index++) {
			batchReqFile = batchReqFiles[index];
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
				lno = 0;
				while ((nextLine = reader.readNext()) != null) {
					lno++;
					readNum++;//读取记录数(总操作记录)
					nextLine.setRecNo(String.format("%s:%d", batchReqFile, lno));
					// 加入数据验证
					if (!checkBatchOne(nextLine)) {
						skipNum++;//跳过记录数(验证不合格数量)
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
					writer.writeNext(nextLine.getRspLineMsgs());
					
					/***
					 * 写明细日志
					 */
					addTxBatchDetail(nextLine);
					
				}
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

	/***
	 * 处理一条数据
	 * 
	 * @param batchLine
	 *            一条数据的对象
	 */
	public abstract void executeBatchOne(CsvBean batchLine) throws Exception;

	/***
	 * 验证一条数据
	 * 
	 * @param batchLine
	 *            一条数据的对象
	 * @return
	 */
	public abstract boolean checkBatchOne(CsvBean batchLine);

	/***
	 * 转换一条响应信息
	 * 
	 * @param batchLine
	 *            一条数据的对象
	 * @return
	 */
	public abstract boolean transRspBatchOne(CsvBean batchLine);

	/***
	 * 
	 * @param batchLine
	 *            一条数据的对象
	 * @return
	 */
	public abstract void responseWarnOne(CsvBean batchLine);
}

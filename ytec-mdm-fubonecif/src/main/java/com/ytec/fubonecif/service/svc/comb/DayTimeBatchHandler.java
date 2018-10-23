/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.svc
 * @文件名：DayTimeBatchHandler.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:06:55
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.FtpOperator;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.svc.comb.DayTimeBatch;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：DayTimeBatchHandler
 * @类描述：联机批量交易处理案例1
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:06:55   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:06:55
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DayTimeBatchHandler extends DayTimeBatch {
	private static Logger log = LoggerFactory
			.getLogger(DayTimeBatchHandler.class);
	private FtpOperator fp;
	private String localPath;
	private String remotePath;
	private String batchFile;
	private String bResFile;
	private String ip;
	private int port;
	private String user;
	private String pswd;

	@Override
	public void getControlInfo() {
		// TODO Auto-generated method stub
		this.asyn = true;
		localPath = BusinessCfg.getString("batchlocalPath");
		remotePath=data.getValueFromParameterMap("remotePath");
		if(StringUtil.isEmpty(remotePath)){
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"远程目录为空");
			return;
		}
		batchFile=data.getValueFromParameterMap("fileName");
		if(StringUtil.isEmpty(batchFile)){
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"批量文件名为空");
			return;
		}
		ip=  BusinessCfg.getString("ftpServerIp");
		port= BusinessCfg.getInt("ftpServerIp");;
		user=  BusinessCfg.getString("ftpServerUser");
		pswd=  BusinessCfg.getString("ftpServerPass");
		
		fp = new FtpOperator(ip, port, user, pswd,remotePath, localPath);
	}

	@Override
	public void getBatchFile() {
		// TODO Auto-generated method stub
		log.info("联机批量文件下载");
		/*** 获取数据文件 **/
		if (!fp.downloadFile(batchFile)) {
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
					"下载文件%s失败", batchFile);
			return;
		}
	}

	@Override
	@Transactional
	public void executeBatch() {
		// TODO Auto-generated method stub
		/*** 操作数据文件 **/
		try {
			log.info("联机批量操作");
			File file = new File(localPath + "/" + batchFile);
			if (!file.exists()) {
				log.error("文件{}/{}不存在", localPath, batchFile);
				data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"文件%s/%s不存在", localPath, batchFile);
				return;
			}
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String recText = null;
				int lno=0;
				while ((recText = reader.readLine()) != null) {
					// ****
					lno++;
					readNum++;
					//
					//***
					statisticalRecord(true);
					addTxBatchDetail(String.valueOf(lno),recText,"000000","成功");
				}
				reader.close();
			} catch (IOException e) {
				log.error("文件读取错误", e);
				data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"文件%s/%s读取错误", localPath, batchFile);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
		} catch (Exception e) {
			log.error("操作数据文件错误", e);
			data.setStatus(ErrorCode.ERR_BATCH_BIZLOGIC_ERROR);
		}
	}

	@Override
	public void putBatchFile() {
		// TODO Auto-generated method stub
		/*** 上传数据文件 **/
		log.info("联机批量文件上传");
		if (!fp.uploadFile(bResFile)) {
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"上传文件%s失败", bResFile);
			return;
		}
	}

	@Override
	public void synchroBatch() {
		// TODO Auto-generated method stub
		/*** 数据同步或通知其他系统取数据 **/
	}

}

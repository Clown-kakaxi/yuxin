/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.svc
 * @�ļ�����DayTimeBatchHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:06:55
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�DayTimeBatchHandler
 * @�������������������״�����1
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:06:55   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:06:55
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"Զ��Ŀ¼Ϊ��");
			return;
		}
		batchFile=data.getValueFromParameterMap("fileName");
		if(StringUtil.isEmpty(batchFile)){
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"�����ļ���Ϊ��");
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
		log.info("���������ļ�����");
		/*** ��ȡ�����ļ� **/
		if (!fp.downloadFile(batchFile)) {
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
					"�����ļ�%sʧ��", batchFile);
			return;
		}
	}

	@Override
	@Transactional
	public void executeBatch() {
		// TODO Auto-generated method stub
		/*** ���������ļ� **/
		try {
			log.info("������������");
			File file = new File(localPath + "/" + batchFile);
			if (!file.exists()) {
				log.error("�ļ�{}/{}������", localPath, batchFile);
				data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"�ļ�%s/%s������", localPath, batchFile);
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
					addTxBatchDetail(String.valueOf(lno),recText,"000000","�ɹ�");
				}
				reader.close();
			} catch (IOException e) {
				log.error("�ļ���ȡ����", e);
				data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"�ļ�%s/%s��ȡ����", localPath, batchFile);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
		} catch (Exception e) {
			log.error("���������ļ�����", e);
			data.setStatus(ErrorCode.ERR_BATCH_BIZLOGIC_ERROR);
		}
	}

	@Override
	public void putBatchFile() {
		// TODO Auto-generated method stub
		/*** �ϴ������ļ� **/
		log.info("���������ļ��ϴ�");
		if (!fp.uploadFile(bResFile)) {
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"�ϴ��ļ�%sʧ��", bResFile);
			return;
		}
	}

	@Override
	public void synchroBatch() {
		// TODO Auto-generated method stub
		/*** ����ͬ����֪ͨ����ϵͳȡ���� **/
	}

}

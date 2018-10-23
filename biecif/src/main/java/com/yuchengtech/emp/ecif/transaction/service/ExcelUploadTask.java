package com.yuchengtech.emp.ecif.transaction.service;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

@Component
@Scope("prototype")
public class ExcelUploadTask implements Runnable {
	private Logger log = LoggerFactory.getLogger(ExcelUploadTask.class);
	private File file = null;
	private String saveType = null;
	
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void run() {
		String flag = RandomUtils.uuid2();
		log.trace("[线程][开始]Excel上传数据入库", flag);
		if (file != null && file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				TxExcelReportBS bs = SpringContextHolder.getBean(TxExcelReportBS.class);
				bs.doUploadDatabase(workbook, saveType);
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Excel上传入库发生错误", e);
			} finally {
				file.delete();
			}
		}
		log.trace("[线程][结束]Excel上传数据入库", flag);
	}

}

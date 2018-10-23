package com.yuchengtech.emp.ecif.transaction.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

@Component
@Scope("prototype")
public class ZipUploadTask implements Runnable {
	private Logger log = LoggerFactory.getLogger(ZipUploadTask.class);
	private File file = null;
	private String saveType = null;
	private static final String CHINESE_CHARSET = "GBK";
	
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void run() {
		String flag = RandomUtils.uuid2();
		log.trace("[线程][开始]zip上传数据入库", flag);
		if (file != null && file.exists()) {
			try {
				ZipFile zipFile = new ZipFile(file, CHINESE_CHARSET);
				Enumeration<?> emu = zipFile.getEntries();
				ZipEntry entry;
				while (emu.hasMoreElements()) {
					entry = (ZipEntry) emu.nextElement();
					BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
			    	SAXReader saxReader = new SAXReader();
			    	saxReader.setEncoding("UTF-8");
			    	Document doc = saxReader.read(bis);

			    	TxMsgBS bs = SpringContextHolder.getBean(TxMsgBS.class);
					bs.doUploadDatabase(doc, saveType);
					bis.close();
					
					
					/*XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(zipFile.getInputStream(entry)));
					TxExcelReportBS bs = SpringContextHolder.getBean(TxExcelReportBS.class);
					bs.doUploadDatabase(workbook, saveType);*/
				}
				zipFile.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("zip文件上传入库发生错误", e);
			} finally {
				file.delete();
			}
		}
		log.trace("[线程][结束]zip上传数据入库", flag);
	}
}

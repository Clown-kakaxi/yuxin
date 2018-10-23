package com.yuchengtech.emp.ecif.transaction.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

@Component
@Scope("prototype")
public class XmlUploadTask implements Runnable {
	private Logger log = LoggerFactory.getLogger(XmlUploadTask.class);
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
		log.trace("[线程][开始]Xml上传数据入库", flag);
		if (file != null && file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
		    	SAXReader saxReader = new SAXReader();
		    	saxReader.setEncoding("UTF-8");
		    	Document doc = saxReader.read(fis);

		    	TxMsgBS bs = SpringContextHolder.getBean(TxMsgBS.class);
				bs.doUploadDatabase(doc, saveType);
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

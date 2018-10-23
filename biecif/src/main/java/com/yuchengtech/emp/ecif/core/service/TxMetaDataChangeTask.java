package com.yuchengtech.emp.ecif.core.service;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.yuchengtech.emp.ecif.transaction.service.TxDefBS;

public class TxMetaDataChangeTask extends TimerTask {
	
	 private static boolean isRunning = false;
	 private ServletContext context = null;
	 private TxMetadataCheckResultBS txMetadataCheckResultBS;

	 public TxMetaDataChangeTask(ServletContext context) {
		 this.context = context;
			
		 WebApplicationContext wac = (WebApplicationContext)context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		
		 txMetadataCheckResultBS = (TxMetadataCheckResultBS) wac.getBean("txMetadataCheckResultBS");	
	 }

	//下面的方法会按之前设定的每5秒执行一次，所以，此处不需要循环
	 public void run() {
		 if (!isRunning) {
			 isRunning = true;
			 context.log("开始执行元数据检查任务");
		 
			 txMetadataCheckResultBS.queryMetaDataChangeList();
			 
			 isRunning = false;
			 context.log("元数据检查任务执行结束");
		 } else {
			 context.log("上一次任务执行还未结束");
		 }
	 }
}


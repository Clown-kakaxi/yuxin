package com.yuchengtech.emp.ecif.alarm.service;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.yuchengtech.emp.ecif.transaction.service.TxDefBS;

public class TxAlarmTask extends TimerTask {
	
	 private static boolean isRunning = false;
	 private ServletContext context = null;
	 private TxAlarmInfoBS txAlarmInfoBS;

	 public TxAlarmTask(ServletContext context) {
		 this.context = context;
			
		 WebApplicationContext wac = (WebApplicationContext)context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		
		 txAlarmInfoBS = (TxAlarmInfoBS) wac.getBean("txAlarmInfoBS");	
	 }

	//下面的方法会按之前设定的每5秒执行一次，所以，此处不需要循环
	 public void run() {
		 if (!isRunning) {
			 isRunning = true;
			 context.log("开始执行报警任务");
		 
			 txAlarmInfoBS.queryTxAlarmInfoList();
			 
			 
			 isRunning = false;
			 context.log("报警任务执行结束");
		 } else {
			 context.log("上一次任务执行还未结束");
		 }
	 }
}


package com.yuchengtech.emp.ecif.alarm.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yuchengtech.emp.ecif.alarm.service.TxAlarmTask;

public class TxAlarmContextListener implements ServletContextListener {
	 private java.util.Timer timer = null;
	 private ServletContext context = null;
	 
	public void contextInitialized(ServletContextEvent event) {
		
		 this.context = event.getServletContext();
		 timer = new java.util.Timer(true);
		 event.getServletContext().log("定时器已启动");
		 //设定TxAlarmTask中任务每5秒执行一次，0表示马上执行，可以改为2000，则表示2秒以后开始执行
		 //以后都按后面指定的每5秒执行一次
		 timer.schedule(new TxAlarmTask(this.context), 3, 10 * 1000);
		 event.getServletContext().log("已经添加任务调度表");
	 }
	 
	public void contextDestroyed(ServletContextEvent event) {
		 timer.cancel();
		 this.context.log("定时器销毁");
		 this.context = null;
	 }
}

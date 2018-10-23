package com.yuchengtech.emp.ecif.core.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yuchengtech.emp.ecif.core.service.TxMetaDataChangeTask;

public class TxMetaDataListener implements ServletContextListener {
	 private java.util.Timer timer = null;
	 private ServletContext context = null;
	 
	//10秒钟后启动， 30秒执行一次 
	public void contextInitialized(ServletContextEvent event) {
		
		 this.context = event.getServletContext();
		 timer = new java.util.Timer(true);
		 event.getServletContext().log("元数据变化检查定时器已启动");
		 //设定TxAlarmTask中任务每30秒执行一次，0表示马上执行，可以改为2000，则表示2秒以后开始执行
		 //以后都按后面指定的每30秒执行一次
		 timer.schedule(new TxMetaDataChangeTask(this.context), 10*1000, 30 * 1000);
	 }
	 
	public void contextDestroyed(ServletContextEvent event) {
		 timer.cancel();
		 this.context.log("元数据变化定时器销毁");
		 this.context = null;
	 }
}

package com.yuchengtech.emp.ecif.base.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.yuchengtech.emp.utils.SpringContextHolder;
/**
 * 检查lisence 
 * @author 
 *
 */
public class LisenceCheckListener implements ServletContextListener {
	private static Logger log = Logger.getLogger(LisenceCheckListener.class);
	private EcifLisenceManager ecifLisenceManager ;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ecifLisenceManager = (EcifLisenceManager)SpringContextHolder.getBean("ecifLisenceManager");
		if(ecifLisenceManager.verified){
	    	log.error("Lisence 验证通过") ;
	    }else{
	    	log.error(ecifLisenceManager.LISENCE_ERROR);
	    	//强行终止web应用
	    	System.exit(0);
		}
	}
}

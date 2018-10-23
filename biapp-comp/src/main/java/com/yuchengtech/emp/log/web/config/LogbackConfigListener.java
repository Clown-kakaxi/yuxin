package com.yuchengtech.emp.log.web.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 功能描述：
 * @author liuch(liucheng2@yuchengtech.com)
 * @version 1.0 2013-1-11 上午10:59:51
 * @see
 * HISTORY
 * 2013-1-11 上午10:59:51 创建文件
 */
public class LogbackConfigListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        LogbackWebConfigurer.initLogging(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        LogbackWebConfigurer.shutdownLogging(event.getServletContext());
    }
}

/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.common.LogicSysInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.MenuInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.ParamInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.PasswdInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.ResOperInfoHolder;

/**
 * <pre>
 * Title: 系统初始化Listener
 * Description: 完成系统相关初始化工作,比如字典数据加载，全局变量设置等
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:1.01     修改人：songxf  修改日期: 2012-07-05    修改内容:增加逻辑系统信息初始化
 * </pre>
 */
public class AppInitListener implements ServletContextListener {

	private static Logger logger = LoggerFactory
			.getLogger(AppInitListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */

	public void contextInitialized(ServletContextEvent sce) {

		try {

			logger.info("系统初始化开始.......");

			/*
			 * @Revision 20130403105200-liuch 修改应用的上下文路径获取方式；
			 * 
			 * String appPath = sce.getServletContext().getRealPath("/");
			 * appPath = appPath.replace("\\", "/"); 系统绝对物理路径
			 * GlobalConstants.APP_REAL_PATH = appPath;
			 * 
			 * 系统应用上下文 GlobalConstants.APP_CONTEXT_PATH = appPath;
			 * if(appPath!=null && appPath.indexOf("/")>0) {
			 * GlobalConstants.APP_CONTEXT_PATH =
			 * appPath.substring(appPath.lastIndexOf("/")); }
			 * 
			 * @Revision 20130403105200-liuch
			 */

			/* 系统绝对物理路径 */
			GlobalConstants.APP_REAL_PATH = sce.getServletContext()
					.getRealPath("/");

			/* 系统应用上下文 */
			String scn = sce.getServletContext().getServletContextName();
			GlobalConstants.APP_CONTEXT_PATH =scn.replace(".war", "");

			// 初始化所有菜单URL信息
			MenuInfoHolder.refreshMenuUrlInfo();

			// 初始化操作权限信息
			ResOperInfoHolder.refreshResOperInfo();

			// 初始化逻辑系统信息
			LogicSysInfoHolder.refreshLogicSysInfo();

			// 初始化参数信息
			ParamInfoHolder.refreshParamInfo();
			
			// 密码安全策略
			PasswdInfoHolder.refreshPasswdInfo();

			logger.info("系统初始化结束.");

		} catch (Exception e) {

			logger.error("系统初始化失败.", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */

	public void contextDestroyed(ServletContextEvent sce) {

		MenuInfoHolder.clearMenuUrlInfo();
		ResOperInfoHolder.clearResOperInfo();

	}
}

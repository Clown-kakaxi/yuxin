/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.common;

import java.util.List;

import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title:逻辑系统信息持有者
 * Description: 将逻辑系统信息存入缓存，避免在获取逻辑系统列表时反复访问数据库，利用缓存框架集群支持功能，可以将逻辑系统信息在WEB集群中之间共享。
 * </pre>
 * 
 * @author songxf
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class LogicSysInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(ResOperInfoHolder.class);

	// 信息在缓存中的名称
	private static final String LOGIC_SYS_CACHE_NAME = "logic_sys_cache_name";
	// 信息在缓存中的key值
	private static final String LOGIC_SYS_CACHE_KEY = "logic_sys_cache_key";

	private static CacheManager cacheManager;
	private static Object lock = new Object();

	static {

		cacheManager = SpringContextHolder.getBean("shiroCacheManager");
	}

	/**
	 * 更新逻辑系统信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshLogicSysInfo() {

		synchronized (lock) {

			initLogicSysInfo();

		}
	}

	/**
	 * 清理逻辑系统信息
	 */
	public static void clearLogicSysInfo() {

		synchronized (lock) {
			cacheManager.getCache(LOGIC_SYS_CACHE_NAME).clear();
		}
	}

	/**
	 * 初始化逻辑系统信息并存放到缓存中
	 */
	public static void initLogicSysInfo() {

		logger.info("缓存逻辑系统配置信息开始........");

		LogicSysBS logicSysBS = SpringContextHolder.getBean("logicSysBS");

		List<BioneLogicSysInfo> logicSysInfoList = logicSysBS.findLogicSysInfo();

		if (logicSysInfoList != null) {

			// 将资源操作信息加入缓存
			cacheManager.getCache(LOGIC_SYS_CACHE_NAME).put(LOGIC_SYS_CACHE_KEY, logicSysInfoList);
		}

		logger.info("缓存逻辑系统信息结束.");

	}

	/**
	 * 从缓存中获取逻辑系统信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<BioneLogicSysInfo> getLogicSysInfo() {

		List<BioneLogicSysInfo> logicSysList = null;

		logicSysList = (List<BioneLogicSysInfo>) cacheManager.getCache(LOGIC_SYS_CACHE_NAME).get(LOGIC_SYS_CACHE_KEY);

		synchronized (lock) {
			// 缓存过期后，重新初始化
			if (logicSysList == null) {

				initLogicSysInfo();
				logicSysList = (List<BioneLogicSysInfo>) cacheManager.getCache(LOGIC_SYS_CACHE_NAME).get(
						LOGIC_SYS_CACHE_KEY);
			}
		}

		return logicSysList;

	}

}

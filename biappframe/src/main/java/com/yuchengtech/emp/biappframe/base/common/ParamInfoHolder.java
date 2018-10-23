/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.variable.entity.BioneParamInfo;
import com.yuchengtech.emp.biappframe.variable.entity.BioneParamTypeInfo;
import com.yuchengtech.emp.biappframe.variable.service.ParamBS;
import com.yuchengtech.emp.biappframe.variable.service.ParamTypeBS;
import com.yuchengtech.emp.utils.SpringContextHolder;
/**
 * 
 * <pre>
 * Title:系统参数信息持有者
 * Description: 将系统参数存入缓存，避免在获取逻辑系统列表时反复访问数据库，利用缓存框架集群支持功能，可以将逻辑系统信息在WEB集群中之间共享。
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
public class ParamInfoHolder {

	private static Logger logger = LoggerFactory
			.getLogger(ParamInfoHolder.class);

	// 信息在缓存中的名称
	private static final String PARAMETER_CACHE_NAME = "param_cache_name";
	// 信息在缓存中的key值
	private static CacheManager cacheManager;

	private static Object lock = new Object();
	static {

		cacheManager = SpringContextHolder.getBean("shiroCacheManager");
	}

	/**
	 * 更新参数信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshParamInfo() {

		synchronized (lock) {

			initParamInfo();

		}
	}

	/**
	 * 清理逻辑系统信息
	 */
	public static void clearParamInfo() {

		synchronized (lock) {
			cacheManager.getCache(PARAMETER_CACHE_NAME).clear();
		}
	}

	/**
	 * 初始化逻辑系统信息并存放到缓存中
	 */
	public static void initParamInfo() {

		logger.info("缓存参数信息开始........");
		LogicSysBS logicSysBS = SpringContextHolder.getBean("logicSysBS");
		ParamTypeBS paramTypeBS = SpringContextHolder.getBean("paramTypeBS");
		ParamBS paramBS = SpringContextHolder.getBean("paramBS");
		List<BioneLogicSysInfo> logicSysList = logicSysBS.findLogicSysInfo();
		String logicSysNo = "";
		String paramTypeNo = "";
		for (BioneLogicSysInfo logicSysInfo : logicSysList) {
			logicSysNo = logicSysInfo.getLogicSysNo();
			List<BioneParamTypeInfo> paramTypeList = paramTypeBS
					.findParamTypeList(logicSysNo);
			Map<String, Map<String, String>> paramTypeMap = new HashMap<String, Map<String, String>>();
			for (BioneParamTypeInfo paramTypeInfo : paramTypeList) {
				paramTypeNo = paramTypeInfo.getParamTypeNo();
				List<BioneParamInfo> paramList = paramBS.findParamBySysAndType(
						logicSysNo, paramTypeNo);
				Map<String, String> paramMap = new HashMap<String, String>();
				for (BioneParamInfo paramInfo : paramList) {
					paramMap.put(paramInfo.getParamValue(),
							paramInfo.getParamName());
				}
				paramTypeMap.put(paramTypeNo, paramMap);
			}
			cacheManager.getCache(PARAMETER_CACHE_NAME).put(logicSysNo,
					paramTypeMap);
		}
		logger.info("缓存逻辑系统信息结束.");

	}

	/**
	 * 从缓存中获取逻辑系统的参数类型信息
	 * 
	 * @param logicSysNo
	 *            逻辑系统编号
	 * @param paramTypeNo
	 *            参数类型标识
	 * @return 参数Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParamInfo(String logicSysNo,
			String paramTypeNo) {

		Map<String, Map<String, String>> paramTypeMap = null;
		Map<String, String> paramMap = null;

		synchronized (lock) {
			paramTypeMap = (Map<String, Map<String, String>>) cacheManager
					.getCache(PARAMETER_CACHE_NAME).get(logicSysNo);
			if (paramTypeMap != null) {
				paramMap = paramTypeMap.get(paramTypeNo);
			}

			// 缓存过期后，重新初始化
			if (paramTypeMap == null || paramMap == null) {

				initParamInfo();
				paramTypeMap = (Map<String, Map<String, String>>) cacheManager
						.getCache(PARAMETER_CACHE_NAME).get(logicSysNo);
				paramMap = paramTypeMap.get(paramTypeNo);
			}
		}

		return paramMap;
	}

	/**
	 * 从缓存中获取逻辑系统的参数名称
	 * 
	 * @param logicSysNo
	 *            逻辑系统编号
	 * @param paramTypeNo
	 *            参数类型标识
	 * @return paramName 参数名称
	 */
	public static String getParamName(String logicSysNo, String paramTypeNo,
			String paramValue) {
		String paramName = "";
		Map<String, String> paramMap = getParamInfo(logicSysNo, paramTypeNo);
		if (paramMap != null) {
			paramName = paramMap.get(paramValue);
			if (paramName != null) {
				return paramName;
			}
		}
		return paramValue;
	}
}

/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.variable.entity.BioneParamTypeInfo;
import com.yuchengtech.emp.biappframe.variable.service.ParamTypeBS;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;
/**
 * 
 * <pre>
 * Title:系统参数信息持有者
 * Description: 将系统参数存入缓存，避免在获取逻辑系统列表时反复访问数据库，利用缓存框架集群支持功能，可以将逻辑系统信息在WEB集群中之间共享。
 * </pre>
 * 
 * @author yangyh
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ParamTypeInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(ParamTypeInfoHolder.class);

	// 信息在缓存中的名称
	private static final String PARAMTYPE_CACHE_NAME = "paramtype_cache_name";
	// 信息在缓存中的key值

	private static Object lock = new Object();

	/**
	 * 更新参数信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshParamTypeInfo() {

		synchronized (lock) {

			initParamTypeInfo();

		}
	}

	/**
	 * 清理逻辑系统信息
	 */
	public static void clearParamTypeInfo() {

		synchronized (lock) {
			EhcacheUtils.removeCache(PARAMTYPE_CACHE_NAME);
		}
	}

	/**
	 * 初始化逻辑系统信息并存放到缓存中
	 */
	public static void initParamTypeInfo() {

		logger.info("缓存参数信息开始........");
		LogicSysBS logicSysBS = SpringContextHolder.getBean("logicSysBS");
		ParamTypeBS paramTypeBS = SpringContextHolder.getBean("paramTypeBS");
		List<BioneLogicSysInfo> logicSysList = logicSysBS.findLogicSysInfo();
		String logicSysNo = "";
		String paramTypeNo = "";
		for (BioneLogicSysInfo logicSysInfo : logicSysList) {
			logicSysNo = logicSysInfo.getLogicSysNo();
			List<BioneParamTypeInfo> paramTypeList = paramTypeBS.findParamTypeList(logicSysNo);
			Map<String, String> paramTypeMap = new HashMap<String, String>();
			for (BioneParamTypeInfo paramTypeInfo : paramTypeList) {
				paramTypeNo = paramTypeInfo.getParamTypeNo();
				try{
				String paramTypeName = paramTypeBS.findParamTypeInfoByNo(paramTypeNo).getParamTypeName();
				paramTypeMap.put(paramTypeNo, paramTypeName);
				}catch(Exception ex){
					
				}
			}
			EhcacheUtils.put(PARAMTYPE_CACHE_NAME,logicSysNo, paramTypeMap);
		}
		logger.info("缓存逻辑系统信息结束.");

	}

	/**
	 * 从缓存中获取逻辑系统的参数类型信息
	 * @param logicSysNo   逻辑系统编号
	 * @param paramTypeNo  参数类型标识
	 * @return 参数Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParamTypeInfo(String logicSysNo) {

		Map<String, String> paramTypeMap = null;
		String paramTypeName = null;
		
		synchronized (lock) {
			
			
			paramTypeMap = (Map<String, String>) EhcacheUtils.get(PARAMTYPE_CACHE_NAME,logicSysNo);
			
			// 缓存过期后，重新初始化
			if (paramTypeMap == null || paramTypeName == null) {

				initParamTypeInfo();
				paramTypeMap = (Map<String, String>) EhcacheUtils.get(PARAMTYPE_CACHE_NAME,logicSysNo);
			}
		}
		return paramTypeMap;

	}

	/**
	 * 从缓存中获取逻辑系统的参数名称
	 * @param logicSysNo   逻辑系统编号
	 * @param paramTypeNo  参数类型标识
	 * @return paramName 参数名称
	 */
	public static String getParamTypeName(String logicSysNo, String paramValue) {
		String paramTypeName = "";
		Map<String, String> paramTypeMap = getParamTypeInfo(logicSysNo);
		if (paramTypeMap != null) {
			paramTypeName = paramTypeMap.get(paramValue);
			if (paramTypeName != null) {
				return paramTypeName;
			}
		}
		return paramValue;
	}
}

package com.yuchengtech.emp.biappframe.base.common;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.variable.service.SysVarBS;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;
/**
 * 
 * <pre>
 * Title:系统变量信息持有者
 * Description: 将系统变量存入缓存，避免在获取系统变量值时反复访问数据库，利用缓存框架集群支持功能，可以将逻辑系统信息在WEB集群中之间共享。
 * </pre>
 * 
 * @author xugy
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：许广源		  修改日期:     修改内容:
 * </pre>
 */
public class SysVarInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(SysVarInfoHolder.class);
	
	// 信息在缓存中的名称
	private static final String SYSVAR_CACHE_NAME = "sysvar_cache_name";
	// 信息在缓存听的key
	
	private static Object lock = new Object();
	
	/**
	 * 更新系统变量信息，新增或者修改系统变量信息后需要调用此方法
	 */
	public static void refreshSysVarInfo() {
		synchronized(lock) {
			initSysVarInfo();
		}
	}
	
	/**
	 * 清理系统变量信息
	 */
	public static void clearSysVarInfo() {
		synchronized(lock) {
			EhcacheUtils.removeCache(SYSVAR_CACHE_NAME);
		}
	}
	
	/**
	 * 初始化系统变量信息并存放到缓存中
	 */
	@SuppressWarnings({ "unchecked" })
	public static void initSysVarInfo() {
		
		logger.info("缓存系统变量信息开始........");
		
		SysVarBS sysVarBS = SpringContextHolder.getBean("sysVarBS");
		
		Map<String, Map<String, String>> sysVarMaplist = sysVarBS.findSysVar();
		
		if(sysVarMaplist != null) {
			
			Iterator<?> iter = sysVarMaplist.entrySet().iterator();
			
			while(iter.hasNext()) {
				
				Map.Entry<String, Map<String, String>> entry = ((Map.Entry<String, Map<String, String>>) iter.next());
				
				EhcacheUtils.put(SYSVAR_CACHE_NAME,entry.getKey(), entry.getValue());
			}

		}
		
		logger.info("缓存系统变量信息结束........");
	}
	
	/**
	 * @param logicSysNo
	 * 			逻辑系统标识
	 * @return	
	 * 			所属逻辑系统的系统变量 Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getSysVarInfo(String logicSysNo) {
		
		Map<String, String> sysVarMap;
		
		synchronized(lock) {
						
			sysVarMap = (Map<String, String>) EhcacheUtils.get(SYSVAR_CACHE_NAME,logicSysNo);
			
			// 缓存过期后，重新初始化
			if(sysVarMap == null) {
				logger.info("缓存过期, 正在重新初始化........");
				initSysVarInfo();
				sysVarMap = (Map<String, String>) EhcacheUtils.get(SYSVAR_CACHE_NAME,logicSysNo);
			}
		}
		return sysVarMap;
	}
	
	public static String getSysVarValue(String logicSysNo, String varNo) {
		
		Map<String, String> sysVarMap = getSysVarInfo(logicSysNo);
		if(sysVarMap != null) {
			
			String varValue = sysVarMap.get(varNo);
			
			return varValue != null ? varValue : ""; 
		}
		return null;
	}
	
}


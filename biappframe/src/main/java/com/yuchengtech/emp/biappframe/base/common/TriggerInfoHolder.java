package com.yuchengtech.emp.biappframe.base.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.schedule.entity.BioneTriggerInfo;
import com.yuchengtech.emp.biappframe.schedule.service.TriggerBS;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title:系统触发器信息持有者
 * Description: 将系统触发器信息存入缓存，避免在获取逻辑系统列表时反复访问数据库，利用缓存框架集群支持功能，可以将逻辑系统信息在WEB集群中之间共享。
 * </pre>
 * 
 * @author yangyuhui
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
public class TriggerInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(TriggerInfoHolder.class);

	// 信息在缓存中的名称
	private static final String TRIGGER_CACHE_NAME = "trigger_cache_name";
	// 信息在缓存中的key值

	private static Object lock = new Object();


	/**
	 * 更新触发器信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshTriggerInfo() {

		synchronized (lock) {

			initTriggerInfo();

		}
	}

	/**
	 * 清理机构信息
	 */
	public static void clearOrgInfo() {

		synchronized (lock) {
			EhcacheUtils.removeCache(TRIGGER_CACHE_NAME);
		}
	}

	/**
	 * 初始化机构信息并存放到缓存中
	 */
	public static void initTriggerInfo() {

		logger.info("缓存触发器信息开始.........");
		
		TriggerBS triggerBS = SpringContextHolder.getBean("triggerBS");
		List<BioneTriggerInfo> triggerInfoList = triggerBS.findTriggerIdAndName();
		Map<String, String> triggerMap = new HashMap<String, String>();
		for(BioneTriggerInfo triggerInfo : triggerInfoList) {
			triggerMap.put(triggerInfo.getTriggerId(), triggerInfo.getTriggerName());
		}
		EhcacheUtils.put(TRIGGER_CACHE_NAME,"TriggerIDAndName", triggerMap);
		
		logger.info("缓存触发器信息结束.........");

	}

	/**
	 * 从缓存中获取触发器的ID与名称信息
	 * 
	 * 
	 * @return 触发器Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getTriggerInfo() {

		Map<String, String> triggerMap = null;

		synchronized (lock) {
			
			triggerMap = (Map<String, String>) EhcacheUtils.get(TRIGGER_CACHE_NAME,"TriggerIDAndName");
			
			// 缓存过期后，重新初始化
			if (triggerMap == null) {
				
				logger.info("缓存过期,正在重新初始化.........");
				
				initTriggerInfo();

				triggerMap = (Map<String, String>) EhcacheUtils.get(TRIGGER_CACHE_NAME,"TriggerIDAndName");
			}
		}

		return triggerMap;

	}

	/**
	 * 从缓存中获取触发器名称
	 * @return orgName 参数名称
	 */
	public static String getTriggerName(String triggerId) {
		String triggerName = "";
		Map<String, String> triggerMap = getTriggerInfo();
		if (triggerMap != null) {
			triggerName = triggerMap.get(triggerId);
			if (triggerName != null) {
				return triggerName;
			}
		}
		return "";
	}
}

/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.user.service.OrgTreeBS;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title:系统机构参数信息持有者
 * Description: 将系统机构参数存入缓存，避免在获取逻辑系统列表时反复访问数据库，利用缓存框架集群支持功能，可以将逻辑系统信息在WEB集群中之间共享。
 * </pre>
 * 
 * @author xuguangyuan
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
public class OrgInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(OrgInfoHolder.class);

	// 信息在缓存中的名称
	private static final String ORGINFO_CACHE_NAME = "orginfo_cache_name";
	// 信息在缓存中的key值

	private static Object lock = new Object();

	/**
	 * 更新机构信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshOrgInfo() {

		synchronized (lock) {

			initOrgInfo();
		}
	}

	/**
	 * 清理机构信息
	 */
	public static void clearOrgInfo() {

		synchronized (lock) {
			EhcacheUtils.removeCache(ORGINFO_CACHE_NAME);
		}
	}

	/**
	 * 初始化机构信息并存放到缓存中
	 */
	@SuppressWarnings("unchecked")
	public static void initOrgInfo() {

		logger.info("缓存机构信息开始.........");
		
		LogicSysBS logicSysBS = SpringContextHolder.getBean("logicSysBS");
		OrgTreeBS orgBS = SpringContextHolder.getBean("orgTreeBS");
		
		List<BioneLogicSysInfo> logicSysList = logicSysBS.findLogicSysInfo();
		
		if(!CollectionUtils.isEmpty(logicSysList)) {
			
			Set<String> logicSysNoSet = Sets.newHashSet();
			
			for(int i = 0; i < logicSysList.size(); i++) {
				logicSysNoSet.add(logicSysList.get(i).getLogicSysNo());
			}
			
			Map<String, Map<String, Object>> orgNoAndNameMap = orgBS.findOrgNoAndOrgName(logicSysNoSet);
			
			if(!MapUtils.isEmpty(orgNoAndNameMap)) {
				
				Iterator<?> iter = orgNoAndNameMap.entrySet().iterator();
				
				while(iter.hasNext()) {
					
					Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
					
					EhcacheUtils.put(ORGINFO_CACHE_NAME,entry.getKey(), entry.getValue());
				}
			}
		}

		logger.info("缓存机构信息结束.........");
	}

	/**
	 * 从缓存中获取机构的标识与名称信息
	 * 
	 * @param logicSysNo  逻辑系统标识
	 * 
	 * @return 机构Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getOrgInfo(String logicSysNo) {

		Map<String, String> orgMap;
		
		synchronized (lock) {
			
			
			orgMap = (Map<String, String>) EhcacheUtils.get(ORGINFO_CACHE_NAME,logicSysNo);
			
			// 缓存过期后，重新初始化
			if (MapUtils.isEmpty(orgMap)) {
				
				logger.info("缓存过期,正在重新初始化.........");
				
				initOrgInfo();

				orgMap = (Map<String, String>) EhcacheUtils.get(ORGINFO_CACHE_NAME,logicSysNo);
			}
		}

		return orgMap;
	}

	/**
	 * 从缓存中获取机构名称
	 * 
	 * @param logicSysNo  
	 * 			逻辑系统标识
	 * @param orgNo  
	 * 			机构标识
	 * 
	 * @return orgName 参数名称
	 */
	public static String getOrgName(String logicSysNo, String orgNo) {
		String orgName = "";
		Map<String, String> orgMap = getOrgInfo(logicSysNo);
		if (!MapUtils.isEmpty(orgMap)) {
			orgName = orgMap.get(orgNo);
			if (orgName != null) {
				return orgName;
			}
		}
		return "";
	}
}

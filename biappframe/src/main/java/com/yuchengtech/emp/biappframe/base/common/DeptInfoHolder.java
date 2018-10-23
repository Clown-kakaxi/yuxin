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
import com.yuchengtech.emp.biappframe.user.service.DeptTreeBS;
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
public class DeptInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(DeptInfoHolder.class);

	// 信息在缓存中的名称
	private static final String DEPTINFO_CACHE_NAME = "deptinfo_cache_name";
	// 信息在缓存中的key值

	private static Object lock = new Object();

	/**
	 * 更新部门信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshDeptInfo() {

		synchronized (lock) {
			
			initDeptInfo();
		}
	}

	/**
	 * 清理机构信息
	 */
	public static void clearDeptInfo() {

		synchronized (lock) {
			EhcacheUtils.removeCache(DEPTINFO_CACHE_NAME);
		}
	}

	/**
	 * 初始化机构信息并存放到缓存中
	 */
	@SuppressWarnings("unchecked")
	public static void initDeptInfo() {

		logger.info("缓存部门信息开始.........");
		
		LogicSysBS logicSysBS = SpringContextHolder.getBean("logicSysBS");
		DeptTreeBS deptBS = SpringContextHolder.getBean("deptTreeBS");
		
		List<BioneLogicSysInfo> logicSysList = logicSysBS.findLogicSysInfo();
		
		if(!CollectionUtils.isEmpty(logicSysList)) {
			
			Set<String> logicSysNoSet = Sets.newHashSet();
			
			for(int i = 0; i < logicSysList.size(); i++) {
				logicSysNoSet.add(logicSysList.get(i).getLogicSysNo());
			}
			
			Map<String, Map<String, Object>> deptNoAndNameMap = deptBS.findDeptNoAndDeptName(logicSysNoSet);
			
			if(!MapUtils.isEmpty(deptNoAndNameMap)) {
				
				Iterator<?> iter = deptNoAndNameMap.entrySet().iterator();
				
				while(iter.hasNext()) {
					
					Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
					
					EhcacheUtils.put(DEPTINFO_CACHE_NAME,entry.getKey(), entry.getValue());
					
				}
			}
		}
		
		logger.info("缓存部门信息结束.........");
	}

	/**
	 * 从缓存中获取机构的标识与名称信息
	 * 
	 * @param logicSysNo  逻辑系统标识
	 * 
	 * @return 部门Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getDeptInfo(String logicSysNo) {

		Map<String, String> deptMap;

		synchronized (lock) {
						
			deptMap = (Map<String, String>) EhcacheUtils.get(DEPTINFO_CACHE_NAME,logicSysNo);
			
			// 缓存过期后，重新初始化
			if (MapUtils.isEmpty(deptMap)) {
				
				logger.info("缓存过期,正在重新初始化.........");
				
				initDeptInfo();

				deptMap = (Map<String, String>) EhcacheUtils.get(DEPTINFO_CACHE_NAME,"logicSysNo");
			}
		}

		return deptMap;
	}

	/**
	 * 从缓存中获取机构名称
	 * 
	 * @param logicSysNo  
	 * 			逻辑系统标识
	 * @param orgDeptNo  
	 * 			机构部门标识
	 * 
	 * @return deptName 参数名称
	 */
	public static String getDeptName(String logicSysNo, String orgDeptNo) {
		String deptName = "";
		Map<String, String> deptMap = getDeptInfo(logicSysNo);
		if (!MapUtils.isEmpty(deptMap)) {
			deptName = deptMap.get(orgDeptNo);
			if (deptName != null) {
				return deptName;
			}
		}
		return "";
	}
}

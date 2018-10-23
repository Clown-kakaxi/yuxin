/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.common;

import java.util.List;

import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.authres.entity.BioneResOperInfo;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title:数据访问规则信息持有者
 * Description: 将数据访问规则存入缓存，避免在数据权限控制时频繁访问数据库，利用缓存框架集群支持功能，可以将菜单信息在WEB集群中之间共享。
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class DataRuleInfoHolder {

	private static Logger logger = LoggerFactory
			.getLogger(DataRuleInfoHolder.class);

	// 缓存中的名称
	private static final String DATA_RULE_CACHE_NAME = "data_rule_cache_name";
	// 缓存中的key值
	private static final String DATA_RULE_CACHE_KEY = "data_rule_cache_key";

	private static CacheManager cacheManager;
	
	private static Object lock = new Object();

	static {

		cacheManager = SpringContextHolder.getBean("shiroCacheManager");
	}

	/**
	 * 更数据访问规则，新增或者修改数据访问规则后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的规则）
	 */
	public static void refreshResOperInfo() {

		synchronized (lock) {

			initResOperInfo();

		}
	}

	/**
	 * 清理数据访问规则
	 */
	public static void clearResOperInfo() {

		synchronized (lock) {
			cacheManager.getCache(DATA_RULE_CACHE_NAME).clear();
		}
	}

	/**
	 * 初始化数据访问规则并存放到缓存中
	 */
	public static void initResOperInfo() {

		logger.info("缓存数据访问规则信息开始........");

		AuthBS authBS = SpringContextHolder.getBean("authBS");

		List<BioneResOperInfo> resOperInfoList = authBS
				.getEntityList(BioneResOperInfo.class);

		if (resOperInfoList != null) {

			List<String> resOperPermissionList = Lists.newArrayList();
			for (BioneResOperInfo resOperInfo : resOperInfoList) {

				// 存放的数据格式为 完整的类名称+":"+方法名称+":"+资源操作标识
				// 系统权限判断的方式为：
				// 1、根据当前用户请求执行URL，解析出 将要执行的完整的类名称和方法名称
				// 2、根据第一步的结果取得资源操作标识
				// 3、通过Shiro判断资源操作标识是否授权给当前登录用户
				resOperPermissionList.add(resOperInfo.getVisitUrl()
						+ GlobalConstants.PERMISSION_SEPARATOR
						+ resOperInfo.getMethodName()
						+ GlobalConstants.PERMISSION_SEPARATOR
						+ resOperInfo.getOperNo());
			}

			// 将资源操作信息加入缓存
			cacheManager.getCache(DATA_RULE_CACHE_NAME).put(DATA_RULE_CACHE_KEY,
					resOperPermissionList);

		}

		logger.info("缓存数据访问规则信息结束.");

	}

	/**
	 * 从缓存中获取数据访问规则
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getResOperInfo() {

		List<String> resOperPermissionList = null;

		synchronized (lock) {
			resOperPermissionList = (List<String>) cacheManager.getCache(
					DATA_RULE_CACHE_NAME).get(DATA_RULE_CACHE_KEY);

			// 缓存过期后，重新初始化
			if (resOperPermissionList == null) {

				initResOperInfo();
			}
		}

		return resOperPermissionList;

	}

}

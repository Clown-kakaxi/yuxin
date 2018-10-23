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
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.utils.SpringContextHolder;
/**
 * 
 * <pre>
 * Title:资源操作信息持有者
 * Description: 将资源操作信息存入缓存，避免在在权限认证时频繁访问数据库，利用缓存框架集群支持功能，可以将菜单信息在WEB集群中之间共享。
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
public class ResOperInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(ResOperInfoHolder.class);

	// 信息在缓存中的名称
	private static final String RES_OPER_CACHE_NAME = "res_oper_cache_name";
	// 信息在缓存中的key值
	private static final String RES_OPER_CACHE_KEY = "res_oper_cache_key";
	private static final String RES_OPER_NO_CACHE_KEY = "res_oper_no_cache_key";

	private static CacheManager cacheManager;
	private static Object lock = new Object();

	static {

		cacheManager = SpringContextHolder.getBean("shiroCacheManager");
	}

	/**
	 * 更新资源操作信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshResOperInfo() {

		synchronized (lock) {

			initResOperInfo();

		}
	}

	/**
	 * 清理资源操作信息
	 */
	public static void clearResOperInfo() {

		synchronized (lock) {
			cacheManager.getCache(RES_OPER_CACHE_NAME).clear();
		}
	}

	/**
	 * 初始化资源操作信息并存放到缓存中
	 */
	public static void initResOperInfo() {

		logger.info("缓存操作权限配置信息开始........");

		AuthBS authBS = SpringContextHolder.getBean("authBS");

		List<BioneResOperInfo> resOperInfoList = authBS.getEntityList(BioneResOperInfo.class);

		if (resOperInfoList != null) {
			LogicSysBS logicSysBS = SpringContextHolder.getBean("logicSysBS");
			List<BioneLogicSysInfo> logicSysList = logicSysBS.findLogicSysInfo();
			for (BioneLogicSysInfo logicSys : logicSysList) {
				String logicSysNo = logicSys.getLogicSysNo();
				// 资源操作许可字符串集合
				List<String> resOperPermissionList = Lists.newArrayList();
				// 资源操作标识集合
				List<String> resOperNoList = Lists.newArrayList();

				for (BioneResOperInfo resOperInfo : resOperInfoList) {

					// 存放的数据格式为 完整的类名称+":"+方法名称+":"+资源操作标识
					// 系统权限判断的方式为：
					// 1、根据当前用户请求执行URL，解析出 将要执行的完整的类名称和方法名称
					// 2、根据第一步的结果取得资源操作标识
					// 3、通过Shiro判断资源操作标识是否授权给当前登录用户
					resOperPermissionList.add(resOperInfo.getVisitUrl() + GlobalConstants.PERMISSION_SEPARATOR
							+ resOperInfo.getMethodName() + GlobalConstants.PERMISSION_SEPARATOR
							+ resOperInfo.getOperNo());

					resOperNoList.add(resOperInfo.getOperNo());
				}

				// 将资源操作信息加入缓存
				cacheManager.getCache(RES_OPER_CACHE_NAME).put(RES_OPER_CACHE_KEY + logicSysNo, resOperPermissionList);

				cacheManager.getCache(RES_OPER_CACHE_NAME).put(RES_OPER_NO_CACHE_KEY + logicSysNo, resOperNoList);
			}

		}

		logger.info("缓存操作权限配置信息结束.");

	}

	/**
	 * 从缓存中获取资源操作信息信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getResOperInfo(String logicSysNo) {

		List<String> resOperPermissionList = null;

		resOperPermissionList = (List<String>) cacheManager.getCache(RES_OPER_CACHE_NAME).get(
				RES_OPER_CACHE_KEY + logicSysNo);

		synchronized (lock) {
			// 缓存过期后，重新初始化
			if (resOperPermissionList == null) {

				initResOperInfo();
				resOperPermissionList = (List<String>) cacheManager.getCache(RES_OPER_CACHE_NAME).get(
						RES_OPER_CACHE_KEY + logicSysNo);
			}
		}

		return resOperPermissionList;

	}

	/**
	 * 从缓存中获取资源操作标识符
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getResOperNoInfo(String logicSysNo) {

		List<String> resOperNoList = null;

		resOperNoList = (List<String>) cacheManager.getCache(RES_OPER_CACHE_NAME).get(
				RES_OPER_NO_CACHE_KEY + logicSysNo);

		synchronized (lock) {
			// 缓存过期后，重新初始化
			if (resOperNoList == null) {

				initResOperInfo();
				resOperNoList = (List<String>) cacheManager.getCache(RES_OPER_CACHE_NAME).get(
						RES_OPER_NO_CACHE_KEY + logicSysNo);
			}
		}

		return resOperNoList;

	}

}

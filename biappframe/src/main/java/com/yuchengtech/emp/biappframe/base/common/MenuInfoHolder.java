package com.yuchengtech.emp.biappframe.base.common;

import java.util.List;

import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.authres.service.MenuBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title:菜单信息持有者
 * Description: 将菜单信息存入缓存，避免在在权限认证时频繁访问数据库，利用缓存框架集群支持功能，可以将菜单信息在WEB集群中之间共享。
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
public class MenuInfoHolder {

	private static Logger logger = LoggerFactory.getLogger(MenuInfoHolder.class);

	// 菜单URL信息在缓存中的名称
	private static final String MENU_URL_CACHE_NAME = "menu_url_cache_name";
	private static CacheManager cacheManager;
	private static Object lock = new Object();

	static {

		cacheManager = SpringContextHolder.getBean("shiroCacheManager");
	}

	/**
	 * 更新菜单URL信息，新增或者修改菜单后需要调用此方法
	 */
	public static void refreshMenuUrlInfo() {

		synchronized (lock) {

			initMenuUrlInfo();
		}

	}

	/**
	 * 清理菜单URL信息
	 */
	public static void clearMenuUrlInfo() {

		synchronized (lock) {

			cacheManager.getCache(MENU_URL_CACHE_NAME).clear();
		}
	}

	/**
	 * 初始化菜单URL信息并存放到缓存中
	 */
	private static void initMenuUrlInfo() {

		logger.info("缓存菜单URL信息开始........");

		MenuBS menuBS = SpringContextHolder.getBean("menuBS");
		LogicSysBS logicSysBS = SpringContextHolder.getBean("logicSysBS");
		List<BioneLogicSysInfo> logicSysList = logicSysBS.findLogicSysInfo();
		for (BioneLogicSysInfo logicSys : logicSysList) {
			String logicSysNo = logicSys.getLogicSysNo();

			List<Object[]> menuInfoList = menuBS.findAllValidMenu(logicSysNo, null);

			List<String> menuUrlList = null;

			if (menuInfoList != null) {

				menuUrlList = Lists.newArrayList();
				for (Object[] menuInfo : menuInfoList) {
					BioneFuncInfo func=(BioneFuncInfo) menuInfo[0];
					menuUrlList.add(func.getNavPath());
				}
			}

			if (menuUrlList != null) {
				// 将菜单URL加入缓存
				cacheManager.getCache(MENU_URL_CACHE_NAME).put(logicSysNo, menuUrlList);
			}

			logger.info("缓存菜单URL信息结束.");

		}
	}

	/**
	 * 从缓存中获取所有菜单URL信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getMenuUrlInfo(String logicSysNo) {

		List<String> menuUrlList = null;

		menuUrlList = (List<String>) cacheManager.getCache(MENU_URL_CACHE_NAME).get(logicSysNo);

		synchronized (lock) {

			// 缓存过期后重新初始化
			if (menuUrlList == null) {
				initMenuUrlInfo();
				menuUrlList = (List<String>) cacheManager.getCache(MENU_URL_CACHE_NAME).get(logicSysNo);
			}
		}

		return menuUrlList;

	}

}

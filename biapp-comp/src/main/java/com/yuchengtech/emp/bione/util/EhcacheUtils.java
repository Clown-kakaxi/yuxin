package com.yuchengtech.emp.bione.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.utils.SpringContextHolder;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * <pre>
 * Title: 系统缓存工具类
 * Description: 系统缓存的添加和获取
 * </pre>
 * 
 * @author songxf
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 * </pre>
 */
public class EhcacheUtils {

	private static Logger logger = LoggerFactory.getLogger(EhcacheUtils.class);

	private static CacheManager cacheManager;

	static {

		cacheManager = SpringContextHolder.getBean("defaultCacheManager");
	}

	/**
	 * 初始化cache
	 * @param cacheName	
	 * @return
	 */
	public synchronized static Ehcache initCache(String cacheName) {
		checkCacheManager();
		if (null == cacheManager.getCache(cacheName)) {
			cacheManager.addCache(cacheName);
		}

		Ehcache cache = cacheManager.getCache(cacheName);
		return cache;
	}

	/**
	 * 添加缓存
	 * @param key
	 *            关键字
	 * @param value
	 *            值
	 */
	public static void put(String cacheName, Object key, Object value) {
		Ehcache cache = initCache(cacheName);
		// 创建Element,然后放入Cache对象中
		Element element = new Element(key, value);
		cache.put(element);
	}

	/**
	 * 获取cache
	 * 
	 * @param key
	 *            关键字
	 * @return 

	 */
	public static Object get(String cacheName, Object key) {
		Ehcache cache = initCache(cacheName);
		Element element = cache.get(key);
		if (null == element) {
			return null;
		} else {
			return element.getObjectValue();
		}
	}

	/**
	 * 删除cache
	 * 
	 * @param key
	 *            关键字
	 */
	public static void remove(String cacheName, Object key) {
		Ehcache cache = initCache(cacheName);
		cache.remove(key);
	}

	/**
	 * 释放CacheManage
	 * 
	 */
	public static void shutdown(String cacheName) {
		checkCacheManager();
		cacheManager.shutdown();
	}

	/**
	 * 移除cache
	 * @param cacheName
	 */
	public static void removeCache(String cacheName) {
		checkCacheManager();
		Ehcache cache = cacheManager.getCache(cacheName);
		if (null != cache) {
			cacheManager.removeCache(cacheName);
		}
	}

	/**
	 * 移除所有cache
	 */
	public static void removeAllCache() {
		checkCacheManager();
		cacheManager.removalAll();
	}

	/**
	 * 移除所有Element
	 */
	public static void removeAllKey(String cacheName) {
		Ehcache cache = initCache(cacheName);
		cache.removeAll();
	}

	/**
	 * 获取所有的cache名称
	 * @return
	 */
	public static String[] getAllCaches() {
		checkCacheManager();
		return cacheManager.getCacheNames();
	}

	/**
	 * 获取Cache所有的Keys
	 * @return
	 */
	public static List<?> getKeys(String cacheName) {
		Ehcache cache = initCache(cacheName);
		return cache.getKeys();
	}

	/**
	 * 检测cacheManager是否存在
	 */
	private static void checkCacheManager() {
		if (null == cacheManager) {
			logger.error("CacheManager不存在");
			throw new IllegalArgumentException("CacheManager不存在");
		}

	}

}

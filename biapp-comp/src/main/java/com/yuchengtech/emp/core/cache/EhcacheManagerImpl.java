package com.yuchengtech.emp.core.cache;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuchengtech.emp.core.Constants;
import com.yuchengtech.emp.core.exception.SystemException;

/**
 * 
 * 功能描述: cache的实现类 Copyright: Copyright (c) 2011 Company: 北京宇信易诚科技有限公司
 * 
 * @author 陈路凝
 * @version 1.0 2012-8-14下午02:15:24
 * @see HISTORY 2012-8-14下午02:15:24 创建文件
 ************************************************* 
 */
@Service
public class EhcacheManagerImpl implements CacheManager {

	private static final Logger log = LoggerFactory.getLogger(EhcacheManagerImpl.class);

	@Resource(name = "defaultCacheManager")
	private net.sf.ehcache.CacheManager ehCacheManager;

	/**
	 * 
	 * 获取指定Cache中指定key的对象
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 * @throws SystemException
	 *             Date: 2012-8-14下午02:10:01
	 */
	public Object get(String cacheName, String key) throws SystemException {
		try {
			if (cacheName == null)
				return null;
			else {
				Element element = getCacheByName(cacheName).get(key);
				if (element != null)
					return element.getObjectValue();
			}
			return null;
		} catch (net.sf.ehcache.CacheException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 
	 * 把value对象，以key为关键字放到指定名称的cache中
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 * @throws SystemException
	 *             Date: 2012-8-14下午02:11:50
	 */
	public void put(String cacheName, String key, Object value)
			throws SystemException {
		try {
			Element element = new Element(key, value);
			this.getCacheByName(cacheName).put(element);
		} catch (IllegalArgumentException e) {
			throw new SystemException(e);
		} catch (IllegalStateException e) {
			throw new SystemException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 
	 * 清空指定cache中指定关键字的数据
	 * 
	 * @param cacheName
	 * @param key
	 * @throws SystemException
	 *             Date: 2012-8-14下午02:14:27
	 */
	public void remove(String cacheName, String key) throws SystemException {
		try {
			this.getCacheByName(cacheName).remove(key);
		} catch (IllegalStateException e) {
			throw new SystemException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 
	 * 清空指定cache中所有数据
	 * 
	 * @param cacheName
	 * @throws SystemException
	 *             Date: 2012-8-14下午02:14:32
	 */
	public void removeAll(String cacheName) throws SystemException {
		try {
			this.getCacheByName(cacheName).removeAll();
		} catch (IllegalStateException e) {
			throw new SystemException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new SystemException(e);
		}

	}

	/**
	 * 
	 * 获取指定名称的cache
	 * 
	 * @param name
	 * @return
	 * @throws SystemException
	 *             Date: 2012-8-14下午02:23:55
	 */
	private Cache getCacheByName(String name) throws SystemException {
		if (name != null) {
			try {
				if (ehCacheManager.getCache(name) == null) {
					ehCacheManager.addCache(name);
				}
				return ehCacheManager.getCache(name);
			} catch (Exception e) {
				log.error("TJFCache cann't add cacheNames ");
				throw new SystemException("TJFCache cann't add cacheNames ", e);
			}
		} else {// 获取缺省的cache
			try {
				if (ehCacheManager.getCache(Constants.TJF_DEFAULT_CACHE) == null) {
					ehCacheManager.addCache(Constants.TJF_DEFAULT_CACHE);
				}
				return ehCacheManager.getCache(Constants.TJF_DEFAULT_CACHE);
			} catch (Exception e) {
				log.warn("TJFCache cann't get cacheNames,possible for ehcahe.xml not define ");
				throw new SystemException(
						"TJFCache cann't get cacheNames,possible for ehcahe.xml not define  ",
						e);
			}
		}
	}

}

package com.yuchengtech.emp.core.cache;

import com.yuchengtech.emp.core.exception.SystemException;

/**
 * 
 * 功能描述: cache接口 
 * 
 * Copyright: Copyright (c) 2011 
 * Company: 北京宇信易诚科技有限公司
 * 
 * @author 陈路凝
 * @version 1.0 2011-5-18下午02:07:53
 * @see HISTORY 2011-5-18下午02:07:53 创建文件
 ************************************************/
public interface CacheManager {

	/**
	 * 
	 * 获取指定Cache中指定key的对象
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 * @throws SystemException
	 *             Date: 2011-5-18下午02:10:01
	 */
	public Object get(String cacheName, String key) throws SystemException;

	/**
	 * 
	 * 把value对象，以key为关键字放到指定名称的cache中
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 * @throws SystemException
	 *             Date: 2011-5-18下午02:11:50
	 */
	public void put(String cacheName, String key, Object value)
			throws SystemException;

	/**
	 * 
	 * 清空指定cache中所有数据
	 * 
	 * @param cacheName
	 * @throws SystemException
	 *             Date: 2011-5-18下午02:14:32
	 */
	public void removeAll(String cacheName) throws SystemException;

	/**
	 * 
	 * 清空指定cache中指定关键字的数据
	 * 
	 * @param cacheName
	 * @param key
	 * @throws SystemException
	 *             Date: 2011-5-18下午02:14:27
	 */
	public void remove(String cacheName, String key) throws SystemException;
}
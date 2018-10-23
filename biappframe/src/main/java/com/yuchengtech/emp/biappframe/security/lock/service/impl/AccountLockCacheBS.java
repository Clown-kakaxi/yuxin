package com.yuchengtech.emp.biappframe.security.lock.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.common.ParamInfoHolder;
import com.yuchengtech.emp.biappframe.security.lock.entity.BioneAccountLockInfo;
import com.yuchengtech.emp.biappframe.security.lock.service.IAccountLockService;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

/*
 * 缓存的实现方式
 */
@Service("accountLockCacheBS")
@Transactional(readOnly=true)
public class AccountLockCacheBS implements IAccountLockService {

	
	@SuppressWarnings("unchecked")
	public void lock(BioneAccountLockInfo lockinfo) {
		Map<String, BioneAccountLockInfo> lockedAccounts = (Map<String, BioneAccountLockInfo>) EhcacheUtils
				.get(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
						AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY);
		if (lockedAccounts == null) {
			lockedAccounts = new HashMap<String, BioneAccountLockInfo>();
		}
		lockedAccounts.put(lockinfo.getUserNo(), lockinfo);
//		AccountLockInfoHolder.refreshAccountLockInfo(lockedAccounts);
		EhcacheUtils.put(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
				AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY, lockedAccounts);
	}

	@SuppressWarnings("unchecked")
	public BioneAccountLockInfo get(String userId) {
		Map<String, BioneAccountLockInfo> lockedAccounts = (Map<String, BioneAccountLockInfo>) EhcacheUtils
				.get(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
						AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY);
		if (lockedAccounts != null && !lockedAccounts.isEmpty()) {
			return lockedAccounts.get(userId);
		}
		return null;
	}

	public boolean contains(String userId) {
		return (this.get(userId) != null);
	}

	
	@SuppressWarnings("unchecked")
	public void unlock(BioneAccountLockInfo lockinfo) {
		if (lockinfo == null) {
			throw new IllegalArgumentException("lockinfo should not be null! ");
		}
		Map<String, BioneAccountLockInfo> lockedAccounts = (Map<String, BioneAccountLockInfo>) EhcacheUtils
				.get(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
						AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY);
		if (lockedAccounts != null && !lockedAccounts.isEmpty() && lockedAccounts.containsKey(lockinfo.getUserNo())) {
			lockedAccounts.remove(lockinfo.getUserNo());
//			AccountLockInfoHolder.refreshAccountLockInfo(lockedAccounts);
			EhcacheUtils.put(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
					AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY, lockedAccounts);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void clear(BioneAccountLockInfo lockinfo) {
		if (lockinfo == null) {
			throw new IllegalArgumentException("lockinfo should not be null! ");
		}
		Map<String, BioneAccountLockInfo> lockedAccounts = (Map<String, BioneAccountLockInfo>) EhcacheUtils
				.get(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
						AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY);
		if (lockedAccounts != null && !lockedAccounts.isEmpty() && lockedAccounts.containsKey(lockinfo.getUserNo())) {
			lockedAccounts.remove(lockinfo.getUserNo());
//			AccountLockInfoHolder.refreshAccountLockInfo(lockedAccounts);
			EhcacheUtils.put(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
					AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY, lockedAccounts);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void clearAll() {
		Map<String, BioneAccountLockInfo> lockedAccounts = (Map<String, BioneAccountLockInfo>) EhcacheUtils
				.get(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
						AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY);
		lockedAccounts.clear();
//		AccountLockInfoHolder.refreshAccountLockInfo(lockedAccounts);
		EhcacheUtils.put(AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_NAME,
				AccountLockInfoHolder.ACCOUNT_LOCK_CACHE_KEY, lockedAccounts);
	}
	
	
	static class AccountLockInfoHolder {

		private static Logger logger = LoggerFactory
				.getLogger(ParamInfoHolder.class);

		// 信息在缓存中的名称
		private static final String ACCOUNT_LOCK_CACHE_NAME = "account_lock_cache_name";
		
		private static final String ACCOUNT_LOCK_CACHE_KEY = "account_lock_cache_key";

		// 信息在缓存中的key值
		private static CacheManager cacheManager;

		private static Object lock = new Object();

		static {
			cacheManager = SpringContextHolder.getBean("shiroCacheManager");
		}

		/**
		 * 更新参数信息，新增或者修改资源操作信息后需要调用此方法
		 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
		 */
		public static void refreshAccountLockInfo(Map<String, BioneAccountLockInfo> lockMap) {
			synchronized (lock) {
				initAccountLockInfo(lockMap);
			}
		}

		/**
		 * 清理逻辑系统信息
		 */
		public static void clearAccountLockInfo() {
			synchronized (lock) {
				cacheManager.getCache(ACCOUNT_LOCK_CACHE_NAME).clear();
			}
		}

		/**
		 * 初始化逻辑系统信息并存放到缓存中
		 */
		private static void initAccountLockInfo(Map<String, BioneAccountLockInfo> lockMap) {

			logger.info("缓存参数信息开始........");

			
			cacheManager.getCache(ACCOUNT_LOCK_CACHE_NAME).put(ACCOUNT_LOCK_CACHE_KEY,  lockMap);

			
			logger.info("缓存逻辑系统信息结束.");
		}
	}

}

package com.yuchengtech.emp.biappframe.base.common;

import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdSecurityInfo;
import com.yuchengtech.emp.biappframe.passwd.service.PwdSecurityBS;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title:密码安全参数信息持有者
 * Description: 将密码安全配置信息保存到Cache中；
 * </pre>
 * 
 * @author liuch(liucheng2@yuchengtech.com)
 * @version 1.0.0
 * 
 */
public class PasswdInfoHolder {
	
	
	private static Logger logger = LoggerFactory.getLogger(ParamInfoHolder.class);

	// 信息在缓存中的名称
	public static final String PWD_SECURITY_CACHE_NAME = "pwd_security_cache_name";
	// 信息在缓存中的key值
	public static final String PWD_SECURITY_KEY = "biappframe_pwd_sec";
	
	private static CacheManager cacheManager;

	private static Object lock = new Object();
	
	static {
		cacheManager = SpringContextHolder.getBean("shiroCacheManager");
//		cacheManager = SpringContextHolder.getBean("defaultCacheManager");
	}

	/**
	 * 更新参数信息，新增或者修改资源操作信息后需要调用此方法
	 * （目前没有提供前台配置功能，当手工修改数据库的信息后只有等待缓存过期，然后自动从数据库获取最新的的数据）
	 */
	public static void refreshPasswdInfo() {
		synchronized (lock) {
			initPasswdInfo();
		}
	}

	/**
	 * 清理逻辑系统信息
	 */
	public static void clearPasswdInfo() {
		synchronized (lock) {
			cacheManager.getCache(PWD_SECURITY_CACHE_NAME).clear();
		}
	}

	/**
	 * 初始化逻辑系统信息并存放到缓存中
	 */
	public static void initPasswdInfo() {

		logger.info("缓存参数信息开始........");
		
		PwdSecurityBS pwdSecurityBS = SpringContextHolder.getBean("pwdSecurityBS");
		
		BionePwdSecurityInfo pwdSec = pwdSecurityBS.getEntityById("1");
		
		cacheManager.getCache(PWD_SECURITY_CACHE_NAME).put(PWD_SECURITY_KEY, pwdSec);
		
		logger.info("缓存逻辑系统信息结束.");
	}

	
}

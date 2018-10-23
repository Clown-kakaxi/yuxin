package com.yuchengtech.emp.biappframe.security.lock.service;

import com.yuchengtech.emp.biappframe.security.lock.entity.BioneAccountLockInfo;


/**
 * 账户锁定服务
 * @author liuch
 */
public interface IAccountLockService {

	/**
	 * 锁定账户
	 * 
	 */
	public abstract void lock(BioneAccountLockInfo lockinfo) ;
	
	/**
	 * 解锁
	 * @param lockinfo
	 */
	public abstract void unlock(BioneAccountLockInfo lockinfo);
	
	/**
	 * 获取账户被锁定信息
	 * @param userId
	 * @return
	 */
	public abstract BioneAccountLockInfo get(String userNo);
	
	/**
	 * 是否包含
	 * @param userId
	 * @return
	 */
	public abstract boolean contains(String userNo) ;
	
	/**
	 * 清除，是不满足解锁条件时，强制解锁
	 * @param userId
	 * @return
	 */
	public abstract void clear(BioneAccountLockInfo lockinfo) ;
	
	/**
	 * 清除
	 * @param userId
	 * @return
	 */
	public abstract void clearAll() ;
	
	
}

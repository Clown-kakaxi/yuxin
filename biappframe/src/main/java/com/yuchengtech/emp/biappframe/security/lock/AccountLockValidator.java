package com.yuchengtech.emp.biappframe.security.lock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.yuchengtech.emp.biappframe.base.common.PasswdInfoHolder;
import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdSecurityInfo;
import com.yuchengtech.emp.biappframe.passwd.service.PwdSecurityBS;
import com.yuchengtech.emp.biappframe.security.lock.entity.BioneAccountLockInfo;
import com.yuchengtech.emp.biappframe.security.lock.service.IAccountLockService;
import com.yuchengtech.emp.bione.util.EhcacheUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 账户锁验证器
 * 
 * @author liuch
 */
public class AccountLockValidator {

	
	private IAccountLockService lockBS = SpringContextHolder.getBean("accountLockCacheBS");
//	private IAccountLockService lockBS = SpringContextHolder.getBean("accountLockDBBS");
	
	/** 
	 * 验证结果
	 * true  - 验证通过
	 * false - 验证不通过（不允许登录系统）
	 */
	private boolean validResult;
	
	/** 
	 * 账户锁定状态 
	 * true  - 锁定
	 * false - 未锁定
	 */
	private boolean lockedState ;
	
	/** 安全策略 */
	private static BionePwdSecurityInfo pwdRules ;
	
	/**
	 * 是否验证成功
	 * @return
	 */
	public boolean isValid() {
		return this.validResult ;
	}
	
	/**
	 * 是否已被锁定
	 * @return
	 */
	public boolean isLocked() {
		return this.lockedState ;
	}
	
	/** instance */
	private static AccountLockValidator lockValidator ;
	
	/**
	 * get instance
	 * @return
	 */
	public static AccountLockValidator getInstance() {
		if(lockValidator==null) {
			lockValidator = new AccountLockValidator();
		}
		return lockValidator;
	}
	
	/** @Contructor() */
	private AccountLockValidator() { }
	
	
	/**
	 * 验证
	 * @param userId
	 * @param isAuthenticated 用户名密码验证是否成功（由shiro完成）
	 */
	public void validate(String userNo, boolean isAuthenticated) {
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		if (pwdRules == null) {
			pwdRules = this.getPwdRules();
		}
		if (pwdRules == null) { // 未配置安全策略，则不进行验证；
			this.validResult = true;
			this.lockedState = false;
		}
		else if (pwdRules.getUsePwdSecurity().equals("0")) { // 不启用密码安全策略
			this.validResult = true;
			this.lockedState = false;
		}
		else {
			if(!isAuthenticated) { // 密码错误，（用户名错误也包含在内，最总都会成为垃圾信息）
				// 进行锁定操作
				// 1。判断是否达到错误次数
				BioneAccountLockInfo lockinfo = null;
				if(this.lockBS.contains(userNo)) {
					// 已有错误记录
					lockinfo = this.lockBS.get(userNo);
					// 判断记录是否已经超出最大值
					if(this.beyondErrorTimes(lockinfo)) {
						// 达到最大值，则判断是否达到被控制时间；
						if(this.beyondLockHour(lockinfo)) {
							// 超出锁定时间，应该解锁。但是由于此次又输出，则应更新记录，重新被锁；
							lockinfo.setErrorTimes(new BigDecimal(1));
							lockinfo.setCreateTime(now);
							lockinfo.setLastUpdateTime(now);
							this.lockBS.lock(lockinfo);
							//
							this.validResult = false; // 验证不通过
							this.lockedState = false; // 账户未锁定
						} 
						else {
							// 未超出，继续保持锁定
							this.validResult = false; // 验证不通过
							this.lockedState = true;  // 账户已锁定
						}
					}
					else {
						// 尚未达到错误次数，追加一次
						lockinfo.setErrorTimes(lockinfo.getErrorTimes().add(new BigDecimal(1)));
						lockinfo.setLastUpdateTime(now);
						this.lockBS.lock(lockinfo);
						//
						this.validResult = false; // 验证不通过
						if(this.beyondErrorTimes(lockinfo)) { // 次数增长后再次判断是否达到上限
							this.lockedState = true;
						} else {
							this.lockedState = false; // 账户已锁定
						}
					}
				}
				else {
					// 暂无错误记录，则新添加一次；
					lockinfo = new BioneAccountLockInfo();
					lockinfo.setLockId(java.util.UUID.randomUUID().toString().replace("-", ""));
					lockinfo.setUserNo(userNo);
					lockinfo.setErrorTimes(new BigDecimal(1));
					lockinfo.setCreateTime(now);
					lockinfo.setLastUpdateTime(now);
					// 执行锁定
					this.lockBS.lock(lockinfo);
					//
					this.validResult = false; // 验证不通过
					this.lockedState = false; // 账户未锁定
				}
			}
			else { // 用户名密码正确
				BioneAccountLockInfo lockinfo = null;
				if(this.lockBS.contains(userNo)) {
					lockinfo = this.lockBS.get(userNo);
					// 已存在，则判断是否应解锁
					if(this.beyondErrorTimes(lockinfo)) {
						// 已被锁定
						if(this.beyondLockHour(lockinfo)) {
							// 锁定时间已过，解锁；
							this.lockBS.unlock(lockinfo);
							this.validResult = true;
							this.lockedState = false;
						}
						else {
							// 锁定时间未到，保持锁定状态；
							this.validResult = false;
							this.lockedState = true; // 锁定状态
						}
					}
					else {
						// 仅仅是有错误记录，但未被锁定；
						// 此次登陆成功，之前的错误记录，一笔勾销；
						this.lockBS.clear(lockinfo);
						this.validResult = true;
						this.lockedState = false;
					}
				}
				else {
					// 无错误记录
					this.validResult = true;
					this.lockedState = false;
				}	
			}
		}
	}
	
	
	/** 是否达到错误的次数 */
	public boolean beyondErrorTimes(BioneAccountLockInfo lockinfo) {
		// 错误次数的判断，要清除隔天（自然天）的错误记录；
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (format.format(lockinfo.getLastUpdateTime().getTime()).compareTo(
				format.format(now.getTime())) < 0) {
			lockinfo.setErrorTimes(new BigDecimal(0)); // 清除错误次数
			return false;
		}
		return (lockinfo.getErrorTimes()
				.compareTo(pwdRules.getAllowErrorTimes()) >= 0);
	}
	
	/** 是否锁定时间已满 */
	public boolean beyondLockHour(BioneAccountLockInfo lockinfo) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Timestamp lastUpdateTime = lockinfo.getLastUpdateTime();
		long differHour = (now.getTime()/1000 - lastUpdateTime.getTime()/1000)/(60*60);  //相差小时
		long lockHour = pwdRules.getLockTime().longValue();
		return (differHour-lockHour)>=0;
	}
	
	/*
	 * 获取验证策略
	 * 
	 * @return
	 */
	private BionePwdSecurityInfo getPwdRules() {
		BionePwdSecurityInfo pwdSecurity = (BionePwdSecurityInfo) EhcacheUtils.get(PasswdInfoHolder.PWD_SECURITY_CACHE_NAME, PasswdInfoHolder.PWD_SECURITY_KEY);
		if(pwdSecurity==null) {
			PwdSecurityBS pwdSecurityBS = SpringContextHolder.getBean("pwdSecurityBS");
			pwdSecurity = pwdSecurityBS.getEntityById(BionePwdSecurityInfo.class, "1");
			EhcacheUtils.put(PasswdInfoHolder.PWD_SECURITY_CACHE_NAME, PasswdInfoHolder.PWD_SECURITY_KEY, pwdSecurity);
		}
		return pwdSecurity;
	}
	
	
}

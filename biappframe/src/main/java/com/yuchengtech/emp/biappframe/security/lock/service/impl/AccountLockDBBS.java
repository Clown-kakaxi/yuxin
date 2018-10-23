package com.yuchengtech.emp.biappframe.security.lock.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.lock.entity.BioneAccountLockInfo;
import com.yuchengtech.emp.biappframe.security.lock.service.IAccountLockService;

/**
 * 
 * @author liuch
 *
 */
@Service("accountLockDBBS")
@Transactional(readOnly=true)
public class AccountLockDBBS extends BaseBS<BioneAccountLockInfo> implements IAccountLockService {

	
	public void lock(BioneAccountLockInfo lockinfo) {
		// TODO Auto-generated method stub
		
	}

	public void unlock(BioneAccountLockInfo lockinfo) {
		// TODO Auto-generated method stub
		
	}

	public BioneAccountLockInfo get(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean contains(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear(BioneAccountLockInfo lockinfo) {
		// TODO Auto-generated method stub
		
	}

	public void clearAll() {
		// TODO Auto-generated method stub
		
	}

	
	
}

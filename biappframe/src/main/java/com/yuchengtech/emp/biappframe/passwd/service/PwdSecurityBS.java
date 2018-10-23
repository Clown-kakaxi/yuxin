package com.yuchengtech.emp.biappframe.passwd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdSecurityInfo;

@Service("pwdSecurityBS")
@Transactional(readOnly = true)
public class PwdSecurityBS extends BaseBS<BionePwdSecurityInfo> {
	
	
	
}

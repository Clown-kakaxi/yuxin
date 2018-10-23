package com.yuchengtech.emp.biappframe.passwd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdHisInfo;


@Service("pwdHisBS")
@Transactional(readOnly = true)
public class PwdHisBS extends BaseBS<BionePwdHisInfo> {
	
	
	
}
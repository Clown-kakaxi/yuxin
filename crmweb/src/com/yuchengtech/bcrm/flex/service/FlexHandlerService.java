package com.yuchengtech.bcrm.flex.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * @describtion: flex后台调用action，专门处理关于flex后台调用的处理
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014-1-24 下午5:48:24
 */
@Service
public class FlexHandlerService extends CommonService{
	public FlexHandlerService(){
		JPABaseDAO<AdminAuthAccount,Long> baseDAO = new JPABaseDAO<AdminAuthAccount,Long>(AdminAuthAccount.class);
		super.setBaseDAO(baseDAO);
	}

}

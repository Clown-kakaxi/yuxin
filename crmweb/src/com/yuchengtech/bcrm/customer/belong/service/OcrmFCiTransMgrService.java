package com.yuchengtech.bcrm.customer.belong.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransMgr;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiTransMgrService extends CommonService{
	public OcrmFCiTransMgrService(){
		JPABaseDAO<OcrmFCiTransMgr,Long> baseDao = new JPABaseDAO<OcrmFCiTransMgr,Long>(OcrmFCiTransMgr.class);
		super.setBaseDAO(baseDao);
	}
}

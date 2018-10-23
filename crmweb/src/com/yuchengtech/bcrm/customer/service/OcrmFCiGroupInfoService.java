package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiGroupInfoService extends CommonService{
	public OcrmFCiGroupInfoService(){
		JPABaseDAO<OcrmFCiGroupInfo,Long> baseDao = new JPABaseDAO<OcrmFCiGroupInfo,Long>(OcrmFCiGroupInfo.class);
		super.setBaseDAO(baseDao);
	}
}

package com.yuchengtech.bcrm.customer.belong.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiBelongTrusteeship;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiBelongTrusteeshipService extends CommonService{
	public OcrmFCiBelongTrusteeshipService(){
		JPABaseDAO<OcrmFCiBelongTrusteeship,Long> baseDao = new JPABaseDAO<OcrmFCiBelongTrusteeship,Long>(OcrmFCiBelongTrusteeship.class);
		super.setBaseDAO(baseDao);
	}
}

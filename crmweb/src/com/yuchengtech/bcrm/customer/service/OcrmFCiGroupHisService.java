package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupHis;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiGroupHisService extends CommonService{
	public OcrmFCiGroupHisService(){
		JPABaseDAO<OcrmFCiGroupHis,Long> baseDao = new JPABaseDAO<OcrmFCiGroupHis,Long>(OcrmFCiGroupHis.class);
		super.setBaseDAO(baseDao);
	}
}

package com.yuchengtech.bcrm.customer.belong.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransBusiness;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiTransBusinessService extends CommonService {
	public OcrmFCiTransBusinessService(){
		JPABaseDAO<OcrmFCiTransBusiness,Long> baseDao = new JPABaseDAO<OcrmFCiTransBusiness,Long>(OcrmFCiTransBusiness.class);
		super.setBaseDAO(baseDao);
	}

}

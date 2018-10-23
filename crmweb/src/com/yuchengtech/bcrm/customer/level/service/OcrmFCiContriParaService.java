package com.yuchengtech.bcrm.customer.level.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.OcrmFCiContriPara;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiContriParaService extends CommonService{
	public OcrmFCiContriParaService(){
		JPABaseDAO<OcrmFCiContriPara,Long> baseDao = new JPABaseDAO<OcrmFCiContriPara,Long>(OcrmFCiContriPara.class);
		super.setBaseDAO(baseDao);
	}
}

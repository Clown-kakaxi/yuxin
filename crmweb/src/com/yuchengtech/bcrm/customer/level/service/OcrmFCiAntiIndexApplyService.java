package com.yuchengtech.bcrm.customer.level.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiIndexApply;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiAntiIndexApplyService extends CommonService{
	public OcrmFCiAntiIndexApplyService(){
		JPABaseDAO<OcrmFCiAntiIndexApply,Long> baseDao = new JPABaseDAO<OcrmFCiAntiIndexApply,Long>(OcrmFCiAntiIndexApply.class);
		super.setBaseDAO(baseDao);
	}
}

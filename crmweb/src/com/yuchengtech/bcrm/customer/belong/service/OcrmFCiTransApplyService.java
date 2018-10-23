package com.yuchengtech.bcrm.customer.belong.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransApply;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiTransApplyService extends CommonService{
	public OcrmFCiTransApplyService(){
		JPABaseDAO<OcrmFCiTransApply,Long> baseDao = new JPABaseDAO<OcrmFCiTransApply,Long>(OcrmFCiTransApply.class);
		super.setBaseDAO(baseDao);
	}
}

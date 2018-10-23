package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmACiCustlossRule;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmACiCustlossRuleService extends CommonService{
	public OcrmACiCustlossRuleService(){
		JPABaseDAO<OcrmACiCustlossRule,Long> baseDao = new JPABaseDAO<OcrmACiCustlossRule,Long>(OcrmACiCustlossRule.class);
		super.setBaseDAO(baseDao);
	}
}

package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmACiCustlossRemind;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmACiCustlossRemindService extends CommonService{
	public OcrmACiCustlossRemindService(){
		JPABaseDAO<OcrmACiCustlossRemind,Long> baseDao = new JPABaseDAO<OcrmACiCustlossRemind,Long>(OcrmACiCustlossRemind.class);
		super.setBaseDAO(baseDao);
	}
}

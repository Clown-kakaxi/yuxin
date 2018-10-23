package com.yuchengtech.bcrm.customer.phoneMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.phoneMkt.model.OcrmFMmTelMain;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFMmTelMainService extends CommonService{
	public OcrmFMmTelMainService(){
		JPABaseDAO<OcrmFMmTelMain,Long> baseDao = new JPABaseDAO<OcrmFMmTelMain,Long>(OcrmFMmTelMain.class);
		super.setBaseDAO(baseDao);
	}
}

package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmACiCustlossFeedback;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmACiCustlossFeedbackService extends CommonService{
	public OcrmACiCustlossFeedbackService(){
		JPABaseDAO<OcrmACiCustlossFeedback,Long> baseDao = new JPABaseDAO<OcrmACiCustlossFeedback,Long>(OcrmACiCustlossFeedback.class);
		super.setBaseDAO(baseDao);
	}
}

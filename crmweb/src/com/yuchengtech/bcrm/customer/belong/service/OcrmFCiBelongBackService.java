package com.yuchengtech.bcrm.customer.belong.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiBelongBack;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiBelongBackService extends CommonService{
	public OcrmFCiBelongBackService(){
		JPABaseDAO<OcrmFCiBelongBack,Long> baseDao = new JPABaseDAO<OcrmFCiBelongBack,Long>(OcrmFCiBelongBack.class);
		super.setBaseDAO(baseDao);
	}
}

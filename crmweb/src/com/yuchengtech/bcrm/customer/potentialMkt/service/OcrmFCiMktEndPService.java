package com.yuchengtech.bcrm.customer.potentialMkt.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktEndP;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktEndPService extends CommonService{
	public OcrmFCiMktEndPService(){
		JPABaseDAO<OcrmFCiMktEndP,Long> baseDao = new JPABaseDAO<OcrmFCiMktEndP,Long>(OcrmFCiMktEndP.class);
		super.setBaseDAO(baseDao);
	}
}

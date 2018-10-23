package com.yuchengtech.bcrm.custview.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiDepandloan;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 
 * @author huwei
 * 存贷比信息
 *
 */
@Service
public class AcrmFCiDepandloanService extends CommonService{

	public AcrmFCiDepandloanService(){
		JPABaseDAO<AcrmFCiDepandloan,Long> baseDao = new JPABaseDAO<AcrmFCiDepandloan,Long>(AcrmFCiDepandloan.class);
		super.setBaseDAO(baseDao);
	}
	
}

package com.yuchengtech.bcrm.customer.level.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.OcrmFCiGradeLevel;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiGradeLevelService extends CommonService{
	public OcrmFCiGradeLevelService(){
		JPABaseDAO<OcrmFCiGradeLevel,Long> baseDao = new JPABaseDAO<OcrmFCiGradeLevel,Long>(OcrmFCiGradeLevel.class);
		super.setBaseDAO(baseDao);
	}
	public Object saveData(Object obj) {
			return baseDAO.save(obj);
		}
	 
}

package com.yuchengtech.bcrm.workplat.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFWpSchedule;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/***
 * 日程安排的service
 */
@Service
public class OcrmFWpScheduleService extends CommonService {
	
	public OcrmFWpScheduleService() {
		JPABaseDAO<OcrmFWpSchedule, Long> baseDAO = new JPABaseDAO<OcrmFWpSchedule, Long>(OcrmFWpSchedule.class);
		super.setBaseDAO(baseDAO);
	}

}

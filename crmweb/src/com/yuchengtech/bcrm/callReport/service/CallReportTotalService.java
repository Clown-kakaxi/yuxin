package com.yuchengtech.bcrm.callReport.service;


import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.callReport.model.OcrmFCiCallreportInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;



@Service
public class CallReportTotalService extends CommonService {
		
	public CallReportTotalService(){
		JPABaseDAO<OcrmFCiCallreportInfo,String> baseDao = new JPABaseDAO<OcrmFCiCallreportInfo,String>(OcrmFCiCallreportInfo.class);
		super.setBaseDAO(baseDao);
	}
}

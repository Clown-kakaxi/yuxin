package com.yuchengtech.bcrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.AcrmFCiLoanImpaInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class LoanImpaInformationService extends CommonService{
	
	   public LoanImpaInformationService(){
		   JPABaseDAO<AcrmFCiLoanImpaInfo, String>  baseDAO=new JPABaseDAO<AcrmFCiLoanImpaInfo, String>(AcrmFCiLoanImpaInfo.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}

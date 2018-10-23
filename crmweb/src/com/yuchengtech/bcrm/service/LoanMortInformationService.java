package com.yuchengtech.bcrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.AcrmFCiLoanMortInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class LoanMortInformationService extends CommonService{
	
	   public LoanMortInformationService(){
		   JPABaseDAO<AcrmFCiLoanMortInfo, String>  baseDAO=new JPABaseDAO<AcrmFCiLoanMortInfo, String>(AcrmFCiLoanMortInfo.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}

package com.yuchengtech.bcrm.custmanager.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custmanager.model.AcrmACiCustRecognise;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class AcrmACiCustRecogniseService extends CommonService {

	public AcrmACiCustRecogniseService(){
		JPABaseDAO<AcrmACiCustRecognise,Long> baseDAO = new JPABaseDAO<AcrmACiCustRecognise,Long>(AcrmACiCustRecognise.class);
			super.setBaseDAO(baseDAO);
	}
	
	public void batchDel(HttpServletRequest request){
		String custId = request.getParameter("custId");
		this.em.createNativeQuery("delete from ACRM_A_CI_CUST_RECOGNISE where CUST_ID = '"+custId+"'").executeUpdate();
	}
}

package com.yuchengtech.bcrm.product.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFCiCustFitProd;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiCustFitProdService extends CommonService{
	
	   public OcrmFCiCustFitProdService(){
		   JPABaseDAO<OcrmFCiCustFitProd, String>  baseDAO=new JPABaseDAO<OcrmFCiCustFitProd, String>(OcrmFCiCustFitProd.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}

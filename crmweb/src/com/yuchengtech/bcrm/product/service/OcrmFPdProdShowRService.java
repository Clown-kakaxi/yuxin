package com.yuchengtech.bcrm.product.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowR;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFPdProdShowRService extends CommonService{
	
	   public OcrmFPdProdShowRService(){
		   JPABaseDAO<OcrmFPdProdShowR, String>  baseDAO=new JPABaseDAO<OcrmFPdProdShowR, String>(OcrmFPdProdShowR.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}

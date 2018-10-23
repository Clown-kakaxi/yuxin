package com.yuchengtech.bcrm.product.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdColumn;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFPdProdColumnService extends CommonService{
	
	   public OcrmFPdProdColumnService(){
		   JPABaseDAO<OcrmFPdProdColumn, String>  baseDAO=new JPABaseDAO<OcrmFPdProdColumn, String>(OcrmFPdProdColumn.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}

package com.yuchengtech.bcrm.product.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowColumn;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFPdProdShowColumnService extends CommonService{
	
	   public OcrmFPdProdShowColumnService(){
		   JPABaseDAO<OcrmFPdProdShowColumn, String>  baseDAO=new JPABaseDAO<OcrmFPdProdShowColumn, String>(OcrmFPdProdShowColumn.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}

package com.yuchengtech.bcrm.product.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowTable;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFPdProdShowTableService extends CommonService{
	
	   public OcrmFPdProdShowTableService(){
		   JPABaseDAO<OcrmFPdProdShowTable, String>  baseDAO=new JPABaseDAO<OcrmFPdProdShowTable, String>(OcrmFPdProdShowTable.class);  
		   super.setBaseDAO(baseDAO);
	   }


	
}

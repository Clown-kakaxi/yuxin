package com.yuchengtech.bcrm.product.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdItemRel;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 产品对照关系service
 * @author ZSXIN
 *
 */
@Service
public class ProductContrastRelationService extends CommonService{
	
	   public ProductContrastRelationService(){
		   JPABaseDAO<OcrmFPdProdItemRel, String>  baseDAO=new JPABaseDAO<OcrmFPdProdItemRel, String>(OcrmFPdProdItemRel.class);  
		   super.setBaseDAO(baseDAO);
	   }
	   /**
	    * 删除的方法
	    * @param idStr
	    */
	   public void remove(String idStr){
		   String[] strarray = idStr.split(",");
	        for (int i = 0; i < strarray.length; i++) {
	            long id = Long.parseLong(strarray[i]);
	            em.remove(em.find(OcrmFPdProdItemRel.class, id));
	        }
	   }
	
}

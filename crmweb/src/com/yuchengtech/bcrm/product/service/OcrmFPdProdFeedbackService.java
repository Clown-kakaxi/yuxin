package com.yuchengtech.bcrm.product.service;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdFeedback;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFPdProdFeedbackService extends CommonService{
	private EntityManager em;
	public OcrmFPdProdFeedbackService(){
		JPABaseDAO<OcrmFPdProdFeedback,Long> baseDao = new JPABaseDAO<OcrmFPdProdFeedback,Long>(OcrmFPdProdFeedback.class);
		super.setBaseDAO(baseDao);
		
	}
  public void remove(String id){
		
	  OcrmFPdProdFeedback model = em.find(OcrmFPdProdFeedback.class, id);
		if(model!=null){
			em.remove(model);
		}
	}
}

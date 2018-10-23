/**
 * 
 */
package com.yuchengtech.bcrm.product.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.product.model.ProductFeature;

/**
 * @author yaoliang
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class ProductFeatureService {
	
	@PersistenceContext
	private EntityManager em;
	
	public void  saveProductFeature(ProductFeature productFeature){		
		
		if(productFeature.getFeatureId()==null){
			em.persist(productFeature);
		}else{
			em.merge(productFeature);
		}
	}
	
	public void deleteProductFeature(Long featrueId){
		
		ProductFeature productFeature = em.find(ProductFeature.class, featrueId);
		if(productFeature!=null){
			em.remove(productFeature);
		}
	}
	
	public ProductFeature getProductFeature(Long featrueId){
		return em.find(ProductFeature.class, featrueId);		
	}
	
	@SuppressWarnings("unchecked")
	public List getProductFeatureList(){
		
		StringBuffer sb = new StringBuffer("select productFeature from ProductFeature productFeature");
		Query query = em.createQuery(sb.toString());		
		return query.getResultList();
	}
}

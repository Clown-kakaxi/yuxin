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

import com.yuchengtech.bcrm.product.model.ProductItemRelation;

/**
 * @author yaoliang
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class ProductItemRelationService{
	
	@PersistenceContext
	private EntityManager em;
	
	public void  saveProductFeature(ProductItemRelation productItemRelation){		
		
		if(productItemRelation.getId()==null){
			em.persist(productItemRelation);
		}else{
			em.merge(productItemRelation);
		}
	}
	
	public void deleteProductItemRelatione(String id){
		
		ProductItemRelation productItemRelation = em.find(ProductItemRelation.class, id);
		if(productItemRelation!=null){
			em.remove(productItemRelation);
		}
	}
	
	public ProductItemRelation getProductItemRelation(String id){
		return em.find(ProductItemRelation.class, id);		
	}
	
	@SuppressWarnings("unchecked")
	public List getProductItemRelationList(){
		
		StringBuffer sb = new StringBuffer("select productItemRelation from ProductItemRelation productItemRelation");
		Query query = em.createQuery(sb.toString());		
		return query.getResultList();
	}
}

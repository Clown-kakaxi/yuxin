package com.yuchengtech.bob.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户摘要信息
 * @author Administrator
 */

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerDescriptionService {
	@SuppressWarnings("unused")
	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	    }
	
/*	@SuppressWarnings("unchecked")
	//无查询条件
	public List <CustomerDescription> query(int first,int max) { 
		 //StringBuffer querysql=new StringBuffer();
		 //querysql.append("select c from CustomerDescription c ");
		 Query q = em.createQuery("select c from CustomerDescription c "); 
	     q.setFirstResult(first);
		 q.setMaxResults(max);  
	     List<CustomerDescription> list= q.getResultList(); 
		 return list;
	} */
	
	
  

}
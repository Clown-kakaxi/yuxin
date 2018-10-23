package com.yuchengtech.bob.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 客户视图—>客户评级信息
 * @author Administrator
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerLevelService {
	
	@SuppressWarnings("unused")
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	}
/*	
	//无查询条件
	public List <CustomerLevel> query(int first,int max,String customerId) { 
		 StringBuffer querysql=new StringBuffer();
		 querysql.append("select c from CustomerLevel c where c.customerId=?1");
		 Query q = em.createQuery(querysql.toString());
		 q.setParameter(1,customerId);
		 q.setFirstResult(first);
		 q.setMaxResults(max); 
	     List<CustomerLevel> list= q.getResultList(); 
		 return list;
	}  */
}
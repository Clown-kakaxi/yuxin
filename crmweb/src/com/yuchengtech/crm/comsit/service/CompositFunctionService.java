package com.yuchengtech.crm.comsit.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.crm.comsit.model.OcrmCompositFunction;

@Service
@Transactional(value="postgreTransactionManager")
public class CompositFunctionService {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public void save(OcrmCompositFunction ocf){
		String deleteSQL = "delete from OcrmCompositFunction o where o.menuId="+ocf.getMenuId();
		Query q = em.createQuery(deleteSQL);
		q.executeUpdate();
		em.persist(ocf);
	}
	
}

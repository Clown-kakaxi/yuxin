package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SystemUnitAndUsers;

@Service
@Transactional(value="postgreTransactionManager")
public class SystemUnitAndUsersService {
	private EntityManager em;
	@PersistenceContext(unitName = "BOB_JPA_ORACLE")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	private EntityManager getEntityManager() {
		return em;
	}
	
	/**
     * 保存：包括新增和修改
     * @param wri
     */
	public void save(SystemUnitAndUsers wri){
	    if(wri.getUserid() == null){
	        em.persist(wri);
	    }else{
	        em.merge(wri);
	    }
	}
	
	/**
     * 移除记录
     * @param id
     */
	public void remove(Long id){
	    em.remove(em.find(SystemUnitAndUsers.class, id));
	}
	
	/**
     * 查看记录
     * @param id
     * @return
     */
	public SystemUnitAndUsers find(Long id){
	    return em.find(SystemUnitAndUsers.class, id);
	}
	
	

	/**
	 * 
	 * @param userid
	 * @return List
	 * @see 根据用户ID取得用户对应的机构ID
	 */
	@SuppressWarnings("unchecked")
	public List<SystemUnitAndUsers> getUnitsByUserid(String userid) {
		  Query query = getEntityManager().createQuery("select suu FROM SystemUnitAndUsers suu where suu.userid=?1");
		  query.setParameter(1, userid);
		return query.getResultList();
	}

	/**
	 * 
	 * @param userid
	 * @return SystemUnitAndUsers
	 * @see 根据用户ID取得用户对应的机构ID(重载方法)
	 */
	public SystemUnitAndUsers findUnitsByUserid(String userid){
		  Query query = getEntityManager().createQuery("select suu FROM SystemUnitAndUsers suu where suu.userid=?1");
		  query.setParameter(1, userid);
		return (SystemUnitAndUsers) query.getSingleResult();
		
	}
	
	
}

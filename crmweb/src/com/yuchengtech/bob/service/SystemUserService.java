package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SystemUsers;


@Service
@Transactional(value="postgreTransactionManager")
public class SystemUserService {
	private EntityManager em;

	@PersistenceContext(unitName="BOB_JPA_ORACLE")
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
    public void save(SystemUsers wri){
        if (wri.getId()==null){
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
        em.remove(em.find(SystemUsers.class, id));
    }
    
    /**
     * 查看记录
     * @param id
     * @return
     */
    public SystemUsers find(Long id){
        return em.find(SystemUsers.class, id);
    }
	
	@SuppressWarnings("unchecked")
	public List<SystemUsers> findAll() {
		  Query query = getEntityManager().createQuery("select ar FROM SystemUsers ar");		
		  return query.getResultList();
	}
	
	/**
	 * 根据输入的用户id(username) 取得数据库唯一ID
	 * @param userid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SystemUsers> findByuserid(String userid) {
		  Query query = getEntityManager().createQuery("select su FROM SystemUsers su where su.userid=?1");
		  query.setParameter(1, userid);
		return query.getResultList();
	}
	
	/**
	 * 根据输入的用户id(username) 取得数据库唯一ID
	 * @param userid
	 * @return
	 * @see 这个方法是findByuserid(String userid)的重载方法
	 */
	
	public SystemUsers findByusername(String userid) {
		  Query query = getEntityManager().createQuery("select su FROM SystemUsers su where su.userid=?1");
		  query.setParameter(1, userid);
		return (SystemUsers) query.getSingleResult();
	}
	
}

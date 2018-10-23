package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SystemUserGroup;

/**
 * 用户组定义Service
 * 
 * @author Administrator
 *
 */

@Service
@Transactional(value="postgreTransactionManager")
public class SystemUserGroupService {

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
	public void save(SystemUserGroup wri){
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
	    em.remove(em.find(SystemUserGroup.class, id));
	}
	
	/**
     * 查看记录
     * @param id
     * @return
     */
	public SystemUserGroup find(Long id){
	    return em.find(SystemUserGroup.class, id);
	}
	
	/**
     * 查询所有记录
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<SystemUserGroup>findAll(){
	    String wriFindAll = "Select wri from SystemUserGroup wri";
	    Query wriQuery = em.createQuery(wriFindAll);
	    return wriQuery.getResultList();
	}
	
	/**
	 * 根据用户组的ID取得用户组对象
	 * @param unitid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SystemUserGroup> findGroupByuserid(Long id) {
		
		  Query query = getEntityManager().createQuery("select sug FROM SystemUserGroup sug where sug.id=?1");
		  query.setParameter(1, id);
		return query.getResultList();
	}
	
	public SystemUserGroup findGroupBygroupid(String groupid) {
		  Query query = getEntityManager().createQuery("select sug FROM SystemUserGroup sug where sug.groupid=?1");
		  query.setParameter(1, groupid);
		return (SystemUserGroup) query.getResultList();
		
	}
}

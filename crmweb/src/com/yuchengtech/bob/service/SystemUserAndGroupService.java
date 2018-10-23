package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SystemUserAndGroup;
import com.yuchengtech.bob.model.SystemUserGroup;

/**
 * 用户和用户组关系Service
 * @author Administrator
 *
 */

@Service
@Transactional(value="postgreTransactionManager")
public class SystemUserAndGroupService {
    private EntityManager em;
    
    @PersistenceContext
    public void setEntityManager (EntityManager em){
        this.em = em;
    }
    
    /**
     * 保存：包括新增和修改
     * @param wi
     */
    public void save(SystemUserAndGroup sysug){
        if(sysug.getId() != null){
            em.persist(sysug);
        }else em.merge(sysug);
    }
    
    /**
     * 移除记录
     * @param id
     */
    public void remove(Long id){
        em.remove(em.find(SystemUserAndGroup.class, id));
    }
    
    /**
     * 查找数据
     * @param id
     * @return
     */
    public SystemUserAndGroup find(Long id){
        return em.find(SystemUserAndGroup.class, id);
    }
    
    /**
     * 查询所有数据
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SystemUserAndGroup>findAll(){
        String sysugFindAll = "select sysug from SystemUserAndGroup sysug";
        Query sysugQuery = em.createQuery(sysugFindAll);
        return sysugQuery.getResultList();
    }

	//根据用户ID取得用户组ID
	public List<SystemUserGroup> findGroupIDbyUserid(String userid) {
		
		
		return null;
	}
	
}

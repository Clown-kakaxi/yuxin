package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SystemRole;


@Service
@Transactional(value="postgreTransactionManager")
public class SystemRoleService {
    
    private EntityManager em;
    
    @PersistenceContext
    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    
    /**
     * 保存：包括新增和修改
     * @param wri
     */
    public void save(SystemRole wri){
        if(wri.getId() == null){
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
        em.remove(em.find(SystemRole.class, id));
    }
    
    /**
     * 查看记录
     * @param id
     * @return
     */
    public SystemRole find(Long id){
        return em.find(SystemRole.class, id);
    }
    
    /**
     * 查询所有记录
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SystemRole> findAll(){
        String wriFindAll = "select wri from SystemRole wri";
        Query wriQuery = em.createQuery(wriFindAll);
        return wriQuery.getResultList();
    }

}

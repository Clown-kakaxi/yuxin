package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SystemPermissions;

/**
 * 权限明细Service
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class SystemPermissionsService {
    
    private EntityManager em;
    
    @PersistenceContext
    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    
    /**
     * 保存：包括新增和修改
     * @param wri
     */
    public void save(SystemPermissions wri){
        if(wri.getId()==null){
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
        em.remove(em.find(SystemPermissions.class, id));
    }
    
    /**
     * 查看记录
     * @param id
     * @return
     */
    public SystemPermissions find(Long id){
        return em.find(SystemPermissions.class, id);
    }
    
    /**
     * 查询所有记录
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SystemPermissions> findAll(){
        String wriFindAll = "select wri from SystemPermissions wri";
        Query wriQuery = em.createQuery(wriFindAll);
        return wriQuery.getResultList();
    }

}

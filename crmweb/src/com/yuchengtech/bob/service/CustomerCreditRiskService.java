package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerCreditRisk;

/**
 * 债券发行信息服务
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerCreditRiskService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param ccr
     */
    public void save(CustomerCreditRisk ccr) {
        if (ccr.getID() == null) {
            em.persist(ccr);
        } else
            em.merge(ccr);
    }

    /**
     * 移除记录
     * 
     * @param id
     */
    public void remove(Long id) {
        em.remove(em.find(CustomerCreditRisk.class, id));
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerCreditRisk find(Long id) {
        return em.find(CustomerCreditRisk.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerCreditRisk> findAll() {
        String ccrFindAll = "select ccr from CustomerCreditRisk ccr";
        Query ccrQuery = em.createQuery(ccrFindAll);
        return ccrQuery.getResultList();
    }
}

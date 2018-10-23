package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerMiddleProfit;

/**
 * 中间业务测算
 * 
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerMiddleProfitService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param clp
     */
    public void save(CustomerMiddleProfit clp) {
        if (clp.getID() == null) {
            em.persist(clp);
        } else
            em.merge(clp);
    }

    /**
     * 移除记录
     * 
     * @param id
     */
    public void remove(String idStr) {
        String[] strarray = idStr.split(",");
        for (int i = 0; i < strarray.length; i++) {
            long id = Long.parseLong(strarray[i]);
            em.remove(em.find(CustomerMiddleProfit.class, id));
        }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerMiddleProfit find(Long id) {
        return em.find(CustomerMiddleProfit.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerMiddleProfit> findAll() {
        String clpFindAll = "select clp from CustomerMiddleProfit clp";
        Query clpQuery = em.createQuery(clpFindAll);
        return clpQuery.getResultList();
    }

}
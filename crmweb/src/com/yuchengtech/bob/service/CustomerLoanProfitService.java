package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerLoanProfit;

/**
 * 贷款测算
 * 
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerLoanProfitService {

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
    public void save(CustomerLoanProfit clp) {
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
            em.remove(em.find(CustomerLoanProfit.class, id));
        }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerLoanProfit find(Long id) {
        return em.find(CustomerLoanProfit.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerLoanProfit> findAll() {
        String clpFindAll = "select clp from CustomerLoanProfit clp";
        Query clpQuery = em.createQuery(clpFindAll);
        return clpQuery.getResultList();
    }

}
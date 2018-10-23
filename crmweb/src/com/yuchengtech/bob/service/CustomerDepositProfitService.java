package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerDepositProfit;

/**
 * 存款测算
 * 
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerDepositProfitService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param cdp
     */
    public void save(CustomerDepositProfit cdp) {
        if (cdp.getID() == null) {
            em.persist(cdp);
        } else
            em.merge(cdp);
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
            em.remove(em.find(CustomerDepositProfit.class, id));
        }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerDepositProfit find(Long id) {
        return em.find(CustomerDepositProfit.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerDepositProfit> findAll() {
        String cdpFindAll = "select cdp from CustomerDepositProfit cdp";
        Query cdpQuery = em.createQuery(cdpFindAll);
        return cdpQuery.getResultList();
    }

}
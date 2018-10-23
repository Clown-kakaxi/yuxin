package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerEconomicCapitalCalculation;

/**
 * 经济资本测算服务
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerEconomicCapitalCalculationService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param cecc
     */
    public void save(CustomerEconomicCapitalCalculation cecc) {
        if (cecc.getID() == null) {
            em.persist(cecc);
        } else
            em.merge(cecc);
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
            em.remove(em.find(CustomerEconomicCapitalCalculation.class, id));
            }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerEconomicCapitalCalculation find(Long id) {
        return em.find(CustomerEconomicCapitalCalculation.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerEconomicCapitalCalculation> findAll() {
        String ceccFindAll = "select cecc from CustomerEconomicCapitalCalculation cecc";
        Query ceccQuery = em.createQuery(ceccFindAll);
        return ceccQuery.getResultList();
    }
}

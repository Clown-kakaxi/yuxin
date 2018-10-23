package com.yuchengtech.bob.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerLoanPriceControl;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 贷款定价器工具
 * 
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerLoanPriceService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param clpc
     */
    public void save(CustomerLoanPriceControl clpc) {
    	
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
    	Date calculateTime = new Date();
    	String custId = auth.getUserId();
        String orgId = auth.getUnitId();
        
        clpc.setCUST_ID(custId);
        clpc.setORG_ID(orgId);
    	clpc.setCALCULATE_TIME(calculateTime);
    	
    	if (clpc.getID() == null) {
    		clpc.setCUST_ID(custId);
            clpc.setORG_ID(orgId);
        	clpc.setCALCULATE_TIME(calculateTime);
            em.persist(clpc);
        } else
            em.merge(clpc);
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
            em.remove(em.find(CustomerLoanPriceControl.class, id));
        }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerLoanPriceControl find(Long id) {
        return em.find(CustomerLoanPriceControl.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerLoanPriceControl> findAll() {
        String clpcFindAll = "select clpc from CustomerLoanPriceControl clpc";
        Query clpcQuery = em.createQuery(clpcFindAll);
        return clpcQuery.getResultList();
    }

}
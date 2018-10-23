package com.yuchengtech.bcrm.customer.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.OcrmFCiContributionParm;

/**
 * 
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class AnalogProfParamMaintenService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param ppm
     */
    public void save(OcrmFCiContributionParm ppm) {
        if (ppm.getParmId() == null) {
            em.persist(ppm);
        } else
            em.merge(ppm);
    }

    /**
     * 移除记录
     * 
     * @param id
     */
    public void remove(String idStr) {
        String[] strarray = idStr.split(",");
        for (int i = 0; i < strarray.length; i++) {
            long PARM_ID = Long.parseLong(strarray[i]);
            em.remove(em.find(OcrmFCiContributionParm.class, PARM_ID));
        }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public OcrmFCiContributionParm find(Long id) {
        return em.find(OcrmFCiContributionParm.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<OcrmFCiContributionParm> findAll() {
        String ppmFindAll = "select ppm from OcrmFCiContributionParm ppm";
        Query ppmQuery = em.createQuery(ppmFindAll);
        return ppmQuery.getResultList();
    }
}



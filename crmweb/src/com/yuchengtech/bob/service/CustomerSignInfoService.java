package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerSignInfo;

/**
 * 客户视图—>高管信息
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerSignInfoService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param csi
     */
    public void save(CustomerSignInfo csi) {
        if (csi.getID() == null) {
            em.persist(csi);
        } else
            em.merge(csi);
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
            em.remove(em.find(CustomerSignInfo.class, id));
        }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerSignInfo find(Long id) {
        return em.find(CustomerSignInfo.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerSignInfo> findAll() {
        String csiFindAll = "select csi from CustomerSignInfo csi";
        Query csiQuery = em.createQuery(csiFindAll);
        return csiQuery.getResultList();
    }
}
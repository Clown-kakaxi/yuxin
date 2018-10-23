package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerLeaderInfo;

/**
 * 客户视图—>高管信息
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerMangerService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 保存：包括新增和修改
     * 
     * @param cli
     */
    public void save(CustomerLeaderInfo cli) {
        if (cli.getID() == null) {
            em.persist(cli);
        } else
            em.merge(cli);
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
            em.remove(em.find(CustomerLeaderInfo.class, id));
        }
    }

    /**
     * 查看记录
     * 
     * @param id
     * @return
     */
    public CustomerLeaderInfo find(Long id) {
        return em.find(CustomerLeaderInfo.class, id);
    }

    /**
     * 查看所有记录
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustomerLeaderInfo> findAll() {
        String cliFindAll = "select cli from CustomerLeaderInfo cli";
        Query cliQuery = em.createQuery(cliFindAll);
        return cliQuery.getResultList();
    }
}
package com.yuchengtech.bcrm.custview.service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.custview.model.CustomerEvent;


/**
 * 客户视图->客户事件信息
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerEventService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<CustomerEvent> query(int first, int max, String customerId) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from CustomerEvent c where c.customerId=?1");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, customerId);
        q.setFirstResult(first);
        q.setMaxResults(max);
        List<CustomerEvent> list = q.getResultList();
        return list;
    }

    public void save(CustomerEvent customerEvent) {
        if (customerEvent.getEVENT_ID() == null) {
            // 新增
            em.persist(customerEvent);
        } else {
            // 更新
            em.merge(customerEvent);
        }
    }

    // 删除
    public void remove(String idStr) {
        String[] strarray = idStr.split(",");
        for (int i = 0; i < strarray.length; i++) {
            long id = Long.parseLong(strarray[i]);
            CustomerEvent customerEvent = em.find(CustomerEvent.class, id);
            ;
            if (customerEvent != null) {
                em.remove(customerEvent);
            }
        }
    }
}
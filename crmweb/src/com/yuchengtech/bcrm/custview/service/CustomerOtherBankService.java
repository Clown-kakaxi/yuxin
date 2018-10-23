package com.yuchengtech.bcrm.custview.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.custview.model.CustomerOtherBank;


/**
 * 客户视图->客户他行信息
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class CustomerOtherBankService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<CustomerOtherBank> query(int first, int max, String customerId) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from CustomerOtherBank c where c.CUST_ID=?1");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, customerId);
        q.setFirstResult(first);
        q.setMaxResults(max);
        List<CustomerOtherBank> list = q.getResultList();
        return list;
    }

    public void save(CustomerOtherBank customerOtherBank) {
        if (customerOtherBank.getMXTID() == null) {
            // 新增
            em.persist(customerOtherBank);
        } else {
            // 更新
            em.merge(customerOtherBank);
        }
    }

    // 删除
    public void remove(String idStr) {
        String[] strarray = idStr.split(",");
        for (int i = 0; i < strarray.length; i++) {
            long Mxtid = Long.parseLong(strarray[i]);
            CustomerOtherBank customerOtherBank = em.find(CustomerOtherBank.class,
                    Mxtid);
            ;
            if (customerOtherBank != null) {
                em.remove(customerOtherBank);
            }
        }
    }
}
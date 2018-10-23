package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CustomerSomeInfo;


@Service
@Transactional(value="postgreTransactionManager")
public class CustomerSomeInfoService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<CustomerSomeInfo> query(String customerId) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from CustomerSomeInfo c where c.CUST_ID=?1");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, customerId);
        q.setFirstResult(0);
        q.setMaxResults(10);
        List<CustomerSomeInfo> list = q.getResultList();
        return list;
    }

    public void save(CustomerSomeInfo customerSomeInfo) {
    	//CustomerSomeInfo customerSomeInfo2=new CustomerSomeInfo();
    	List<CustomerSomeInfo> list=query(customerSomeInfo.getCUST_ID());
    	
        if (list.isEmpty()) {
            // 新增
            em.persist(customerSomeInfo);
        } else {
        	customerSomeInfo.setID(Long.parseLong(list.get(0).getID().toString()));
            // 更新
            em.merge(customerSomeInfo);
        }
    }

    // 删除
    public void remove(String idStr) {
        String[] strarray = idStr.split(",");
        for (int i = 0; i < strarray.length; i++) {
            long id = Long.parseLong(strarray[i]);
            CustomerSomeInfo customerSomeInfo = em.find(CustomerSomeInfo.class,
                    id);
            ;
            if (customerSomeInfo != null) {
                em.remove(customerSomeInfo);
            }
        }
    }
}
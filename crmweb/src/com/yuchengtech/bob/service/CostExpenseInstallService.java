package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.CostExpenseInstall;

@Service
@Transactional(value="postgreTransactionManager")
public class CostExpenseInstallService {
	
    private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<CostExpenseInstall> query(String CUST_ID) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from CostExpenseInstall c where c.CUST_ID=?1");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, CUST_ID);
        q.setFirstResult(0);
        q.setMaxResults(10);
        List<CostExpenseInstall> list = q.getResultList();
        return list;
    }

    public void save(CostExpenseInstall costExpenseInstall) {
    	//CustomerSomeInfo customerSomeInfo2=new CustomerSomeInfo();
    	List<CostExpenseInstall> list=query(costExpenseInstall.getCUST_ID());
    	
        if (list.isEmpty()) {
            // 新增
            em.persist(costExpenseInstall);
        } else {
        	costExpenseInstall.setId(Long.parseLong(list.get(0).getId().toString()));
            // 更新
            em.merge(costExpenseInstall);
        }
    }

/*    // 删除
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
    }*/
}

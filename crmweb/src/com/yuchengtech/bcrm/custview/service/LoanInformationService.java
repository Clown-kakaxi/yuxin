package com.yuchengtech.bcrm.custview.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.custview.model.LoanInformation;


@Service
@Transactional(value="postgreTransactionManager")
public class LoanInformationService {
	
    private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<LoanInformation> query(String PAYACCOUNT) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from LoanInformation c where c.PAYACCOUNT=?1");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, PAYACCOUNT);
        q.setFirstResult(0);
        q.setMaxResults(10);
        List<LoanInformation> list = q.getResultList();
        return list;
    }

    public void save(LoanInformation loanInformation) {
    	//CustomerSomeInfo customerSomeInfo2=new CustomerSomeInfo();
    	List<LoanInformation> list=query(loanInformation.getPAYACCOUNT());
    	
        if (list.isEmpty()) {
            // 新增
            em.persist(loanInformation);
        } else {
        	loanInformation.setId(Long.parseLong(list.get(0).getId().toString()));
            // 更新
            em.merge(loanInformation);
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

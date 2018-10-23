package com.yuchengtech.bob.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.RollCustomer;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class RollCustomerService{
    
    private EntityManager em;
    
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    // 查询名单列表
    @SuppressWarnings("unchecked")
    public List<RollCustomer> findAll() {
        Query query = getEntityManager().createQuery("select mp FROM RollCustomer mp");
        return query.getResultList();
    }

    // 根据rollId是否为空进行新增或者修改营销计划
    public void save(RollCustomer rollCustomer) {
        
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        Date date = new Date(); 
        rollCustomer.setUpdateUser(currenUserId);
        rollCustomer.setUpdateDate(date);
        if (rollCustomer.getRollId() == null) {
            //新增
            rollCustomer.setCreateUser(currenUserId);
            rollCustomer.setCreateDate(date);
            em.persist(rollCustomer);
        } else {
            //修改
            em.merge(rollCustomer);
        }
    }

    // 删除
    public void remove(long id) {
            RollCustomer rollCustomer = find(id);
            if (rollCustomer != null) {
            em.remove(rollCustomer);
        }
    }
    
	//批量删除
	public void batchRemove(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			RollCustomer rollCustomer = find(id);
			if (rollCustomer != null) {
				em.remove(rollCustomer);
			}
		}
	}

    private EntityManager getEntityManager() {
        return em;
    }

    public RollCustomer find(long id) {
        return em.find(RollCustomer.class, id);
    }

}

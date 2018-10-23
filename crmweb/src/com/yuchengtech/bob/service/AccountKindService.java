package com.yuchengtech.bob.service;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.AccountKind;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class AccountKindService{
	
	private EntityManager em;	
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// 查询账户类型列表
	@SuppressWarnings("unchecked")
	public List<AccountKind> findAll() {
		Query query = getEntityManager().createQuery("select ak FROM AccountKind ak");
        return query.getResultList();
	}

	// 根据ID是否为空进行新增或者修改账户类型
	@SuppressWarnings("unchecked")
	public void save(AccountKind accountKind) throws Exception{
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long ID = accountKind.getKindId();
        String employeeId = auth.getUserId();
        String employeeName = auth.getUsername();
        String orgNum = auth.getUnitId();
        String useOrg = auth.getUnitName();
        String kindCode = accountKind.getKindCode();
		Date createDate = new Date(); 
			
		 StringBuffer querysql=new StringBuffer();
		 querysql.append("select ak FROM AccountKind ak where ak.kindCode = ?1");
		 Query query = em.createQuery(querysql.toString()); 
		 query.setParameter(1, kindCode);
		 query.setFirstResult(0);
		 query.setMaxResults(10);  
	     List<AccountKind> list= query.getResultList(); 
		
		accountKind.setEmployeeId(employeeId);
		accountKind.setEmployeeName(employeeName);
		accountKind.setOrgNum(orgNum);
		accountKind.setUseOrg(useOrg);		
		accountKind.setCreateDate(createDate);
		

		if (ID == null) {
			//新增
			if(list.isEmpty())
			em.persist(accountKind);
			else
			{
				Exception myException = new Exception("账户类型编码重复!");
				throw myException;
			}
		} else {
			//修改
			if(list.isEmpty())
			em.merge(accountKind);
			else
			{
				Exception myException = new Exception("账户类型编码重复!");
				throw myException;
			}
		}
	}

	// 删除账户类型
    public void remove(long id) {
    	 AccountKind accountKind = find(id);
        	if (accountKind != null) {
            em.remove(accountKind);
		}
    }
    
	//批量删除账户类型
	public void batchRemove(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			AccountKind accountKind = find(id);
			if (accountKind != null) {
				em.remove(accountKind);
			}
		}
	}
    
	private EntityManager getEntityManager() {
		return em;
	}

	public  AccountKind find(long id) {
		return em.find( AccountKind.class, id);
	}

}

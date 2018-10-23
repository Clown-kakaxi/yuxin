package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.ClientGroupDepositLoan;

/*
 * 2011-07-19
 * yaoliang
 * 集团客户存贷款信息
 * 
 * */
@Service
@Transactional(value="postgreTransactionManager")
public class GroupDepLnService {
	
	private EntityManager em;  
	@PersistenceContext
	public void setEntityManager(EntityManager em)
	{
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	public List<ClientGroupDepositLoan> depLnList(ClientGroupDepositLoan depLn) {
			Query memberQuery = em.createQuery("SELECT groupDepLn FROM ClientGroupInfo.ocrmFCiGroupMembers.ocrmFCiGroupInfo groupDepLn where groupDepLn.groupId=?1 " );
			memberQuery.setParameter(1, depLn.getCustId());
			return memberQuery.getResultList();
	}
}

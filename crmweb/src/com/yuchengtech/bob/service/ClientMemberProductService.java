package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.ClientMemberProduct;
/*
 * yaoliang
 * 2011-08-10
 * 集团客户产品信息业务逻辑
 * */
@Service
@Transactional(value="postgreTransactionManager")
public class ClientMemberProductService {

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	/*
	 * 查询集团客户产品信息列表
	 * */
	@SuppressWarnings("unchecked")
	public List<ClientMemberProduct> memberProductList(String groupId){
		StringBuffer buffer = new StringBuffer("select clientMemberProduct from ClientMemberProduct clientMemberProduct where (1=1)");
		Query query = em.createQuery(buffer.toString());
		return query.getResultList();		
	}
}

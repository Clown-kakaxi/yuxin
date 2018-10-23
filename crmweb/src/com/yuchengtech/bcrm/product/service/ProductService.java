package com.yuchengtech.bcrm.product.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.product.model.ProductInfo;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @author yaoliang 2011-08-22 产品service
 */
@Transactional
@Service
public class ProductService {
	
	private EntityManager em;
	private boolean sign = true;
	@PersistenceContext
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	/**
	 * 保存：包括新增和修改
	 * @param ws
	 */
	public boolean save(ProductInfo ws){
		////system.out.printlnln(ProductInfoName+"12312312******************************************");
		if(ws.getProductId()== null){
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currentUserid = auth.getUserId();
			Date date = new Date();
			ws.setProductCreator(currentUserid);
			ws.setCreateDate(date);
			em.persist(ws);
			sign = true;
		}else{
			em.merge(ws);
			sign = true;
		}
		return sign;
	}
	
	/**
	 * 移除记录
	 * @param id
	 */
	public void remove(String id){
		ProductInfo ws2 = em.find(ProductInfo.class, id);
		if(ws2!=null){
		}
	}
	
	/**
	 * 查看记录
	 * @param id
	 * @return
	 */
	public ProductInfo find(String id){
		return em.find(ProductInfo.class, id);
	}
	
	/**
	 * 查询所有记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductInfo> findAll(){
		String wsFindAll = "select ws from ProductInfo ws";
		Query wsQuery = em.createQuery(wsFindAll);
		return wsQuery.getResultList();
	}
}

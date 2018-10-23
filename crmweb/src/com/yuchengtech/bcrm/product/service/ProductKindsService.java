/**
 * 
 */
package com.yuchengtech.bcrm.product.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.product.model.ProductCategory;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @author yaoliang
 * 2011-08-24
 * 产品种类Service
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class ProductKindsService {
	
	
	EntityManager em;
	@PersistenceContext
	public void setEm(EntityManager em){
		
		this.em = em;
	}	

	@SuppressWarnings("unchecked")
	public List<ProductCategory> getProductKindsList(){
		
		StringBuffer sb = new StringBuffer ("select productKinds from ProductCategory  productKinds  ");
		
		Query query = em.createQuery(sb.toString());
		
		return query.getResultList();
	}
	
	public void saveProductKinds(ProductCategory productKinds){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(productKinds.getCatlCode() == null){
			List list = this.em.createQuery("select t from ProductCategory t where t.catlName = '"+productKinds.getCatlName()+"'").getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1,0,"","该产品类别已存在!");
			}
			String rootcode=productKinds.getCatlParent().toString();
			if("银行产品树".equals(rootcode)){
				rootcode="0";
				productKinds.setCatlParent(rootcode);
			}
			ProductCategory parentCate = getProductKinds(rootcode);
			if(parentCate == null){
				em.persist(productKinds);	
				auth.setPid(productKinds.getCatlCode().toString());
			}else{
				em.persist(parentCate);
				em.persist(productKinds);	
				auth.setPid(productKinds.getCatlCode().toString());
			}
		}else{
			List list = this.em.createQuery("select t from ProductCategory t where t.catlName = '"+productKinds.getCatlName()+"' and t.catlCode <> '"+productKinds.getCatlCode()+"'").getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1,0,"","该产品类别已存在!");
			}
			em.merge(productKinds);
		}
	}
	
	public ProductCategory getProductKinds(String caltCode){
		return em.find(ProductCategory.class, caltCode);
	}
	
	public void deleteProductKinds(String caltCode){		
		ProductCategory productKinds = em.find(ProductCategory.class, caltCode);
		if(productKinds != null){
			em.remove(productKinds);			
			StringBuffer sb = new StringBuffer(" select count(productCategory.catlCode) "					
					+" from ProductCategory as  productCategory where productCategory.catlParent ='"+productKinds.getCatlParent()+"'");
			Query query = em.createQuery(sb.toString());
			long count = (Long)query.getSingleResult();
			if(count == 0){
				ProductCategory parentCate = getProductKinds(productKinds.getCatlParent());
//				parentCate.setIsLeaf("Y");
				em.persist(parentCate);
			}
		}
	}
}

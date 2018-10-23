package com.yuchengtech.bcrm.sales.service;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.workplat.model.WorkingplatformInfo;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class MarketingNewsService{
	
	private EntityManager em;	
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// 查询账户类型列表
	@SuppressWarnings("unchecked")
	public List<WorkingplatformInfo> findAll() {
		Query query = getEntityManager().createQuery("select wi FROM WorkingplatformInfo wi");
        return query.getResultList();
	}

	// 根据ID是否为空进行新增或者修改账户类型
	public void save(WorkingplatformInfo workingplatformInfo) {
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();				
		Long ID = workingplatformInfo.getMessageId();
		String messageType = "3";
		String publishUser = auth.getUsername();
		String publishOrg = auth.getUnitId();
		Date publishDate = new Date();
		
		workingplatformInfo.setMessageType(messageType);
		workingplatformInfo.setPublishUser(publishUser);
		workingplatformInfo.setPublishOrg(publishOrg);
		workingplatformInfo.setPublishDate(publishDate);
		
		if (ID == null) {
			//新增
			workingplatformInfo.setMessageType(messageType);
			workingplatformInfo.setPublishUser(publishUser);
			workingplatformInfo.setPublishOrg(publishOrg);
			workingplatformInfo.setPublishDate(publishDate);
			em.persist(workingplatformInfo);
		} else {
			//修改
			em.merge(workingplatformInfo);
		}
	}

	// 删除账户类型
    public void remove(long id) {
    	WorkingplatformInfo workingplatformInfo = find(id);
        	if (workingplatformInfo != null) {
            em.remove(workingplatformInfo);
		}
    }
    
	//批量删除账户类型
	public void batchRemove(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			WorkingplatformInfo workingplatformInfo = find(id);
			if (workingplatformInfo != null) {
				em.remove(workingplatformInfo);
			}
		}
	}
    
	private EntityManager getEntityManager() {
		return em;
	}

	public  WorkingplatformInfo find(long id) {
		return em.find( WorkingplatformInfo.class, id);
	}

}

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
import com.yuchengtech.bob.model.SubjectStatistics;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class SubjectStatisticsService{
	
	private EntityManager em;	
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// 查询科目列表
	@SuppressWarnings("unchecked")
	public List<SubjectStatistics> findAll() {
		Query query = getEntityManager().createQuery("select ss FROM SubjectStatistics ss");
        return query.getResultList();
	}

	// 根据ID是否为空进行新增或者修改科目
	@SuppressWarnings("unchecked")
	public void save(SubjectStatistics subjectStatistics) throws Exception {
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();				
		Long ID = subjectStatistics.getSubjectId();
        String employeeName = auth.getUsername();
        String subjectKind = subjectStatistics.getSubjectKind();
		Date createDate = new Date(); 
		
		 StringBuffer querysql=new StringBuffer();
		 querysql.append("select ss FROM SubjectStatistics ss where ss.subjectKind = ?1");
		 Query query = em.createQuery(querysql.toString()); 
		 query.setParameter(1, subjectKind);
		 query.setFirstResult(0);
		 query.setMaxResults(10);  
	     List<AccountKind> list= query.getResultList(); 
		
		subjectStatistics.setEmployeeName(employeeName);
		subjectStatistics.setCreateDate(createDate);
		
		if (ID == null) {
			//新增
			if(list.isEmpty())
			em.persist(subjectStatistics);
			else
			{
				Exception myException = new Exception("科目号重复!");
				throw myException;
			}
		} else {
			//修改
			if(list.isEmpty())
			em.merge(subjectStatistics);
			else
			{
				Exception myException = new Exception("科目号重复!");
				throw myException;
			}
		}
	}

	// 删除科目
    public void remove(long id) {
    	SubjectStatistics subjectStatistics = find(id);
        	if (subjectStatistics != null) {
            em.remove(subjectStatistics);
		}
    }
    
	//批量删除科目
	public void batchRemove(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			SubjectStatistics subjectStatistics = find(id);
			if (subjectStatistics != null) {
				em.remove(subjectStatistics);
			}
		}
	}
    
	private EntityManager getEntityManager() {
		return em;
	}

	public SubjectStatistics find(long id) {
		return em.find(SubjectStatistics.class, id);
	}

}

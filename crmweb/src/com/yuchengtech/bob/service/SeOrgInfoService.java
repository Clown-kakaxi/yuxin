package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SeOrgInfo;

/**
 * 日程服务
 * @author WillJoe
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class SeOrgInfoService {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	/**
	 * 保存：包括新增和修改
	 * @param ws
	 */
	public void save(SeOrgInfo ws){
		if(ws.getAgencyCode()==null){
		    //ws.setCreateOrganizer(groupId);
			em.persist(ws);
		}else em.merge(ws);
	}
	
	/**
	 * 移除记录
	 * @param id
	 */
	public void remove(Long id){
		//system.out.printlnln(id);
		em.remove(em.find(SeOrgInfo.class, id));
	}
	
	/**
	 * 查看记录
	 * @param id
	 * @return
	 */
	public SeOrgInfo find(Long id){
		return em.find(SeOrgInfo.class, id);
	}
	
	/**
	 * 查询所有记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SeOrgInfo> findAll(){
		String wsFindAll = "select ws from SeOrgInfo ws";
		Query wsQuery = em.createQuery(wsFindAll);
//		List<SeOrgInfo> list=wsQuery.getResultList();
//		SimpleDateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd");
//		for(int i=0;i<list.size();i++){
//		    try {
////		        String string=list.get(i).getStartDate().toString();
//		        Date date=dFormat.parse(dFormat.format(list.get(i).getStartDate()));
//                list.get(i).setStartDate(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//		    
//		}
		return wsQuery.getResultList();
	}
	
//	/****** 获取创建人Id和创建机构Id *****/
//	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    
//    String agencyCode = auth.getUserId();
    
    //String agencyName= auth.get
}

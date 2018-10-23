package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.GroupMember;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 对于集团基本信息的维护service
 * @author sujm
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class GroupMemberRemoveService {

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * 保存：包括新增和修改
	 * 
	 * @param ws
	 */
	public void save(GroupMember ws) {
		if (ws.getId() == null) {		
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currenUserId = auth.getUserId();
			ws.setUpdateUserId(currenUserId);
//			Date date = new Date();
//			ws.setUpdateDate(date);
//			ws.setSubmitDate(date);
			ws.setAppStatus("1");
			em.persist(ws);GroupMember ws2 = em.find(GroupMember.class, ws.getId());
			em.merge(ws2);
		} else {
			/**
			 * 修改对象信息，并录入更新时间和更新人
			 * */
			GroupMember ws2 = em.find(GroupMember.class, ws.getId());
			Date date = new Date();
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			String temp_app_user_name = auth.getCname();
			String temp_app_user_id = auth.getUnitId();
			String tem_app_user_org_id = auth.getUnitId();
			
			if(ws2.getAppStatus().equals("0")||ws2.getAppStatus().equals("1")){
				int tempStatus=2;
				ws2.setHostOpinion("");
				ws2.setCreditOpinion("");
				ws2.setAppUserId(temp_app_user_id);
				ws2.setAppUserName(temp_app_user_name);
				ws2.setAppUserOrgId(tem_app_user_org_id);
				if(ws2.getAppStatus().equals("1")){
					ws2.setSubmitDate(date);
					ws2.setAppDate(null);
				}
				if(ws2.getAppStatus().equals("0")){
					ws2.setSubmitDate(date);
					ws2.setAppDate(null);
				}
				ws2.setAppStatus(String.valueOf(tempStatus));
				em.merge(ws2);
			}else {
			if(ws2.getAppStatus().equals("2")){
			int tempStatus=0;
			ws2.setAppStatus(String.valueOf(tempStatus));
			ws2.setAppDate(date);
			em.merge(ws2);
			}
		}
		}
		}

	/**
	 * 移除记录
	 * @param id
	 */
	public void remove(Long id){
		em.remove(em.find(GroupMember.class, id));
	}

	/**
	 * 查看记录
	 * 
	 * @param id
	 * @return
	 */
	public GroupMember find(Long id) {
		//system.out.printlnln(id);
		return em.find(GroupMember.class, id);
	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupMember> findAll() {
		String wsFindAll = "select ws from GroupMember ws";
		Query wsQuery = em.createQuery(wsFindAll);
		return wsQuery.getResultList();
	}

}

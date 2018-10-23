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
public class GroupMemberForAppService {

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
		//system.out.printlnln(ws.getId());
//system.out.printlnntln(em);
		if (ws.getId() == null) {
			em.persist(ws);
		} else {
			GroupMember ws2 = em.find(GroupMember.class, ws.getId());
			
			Date date = new Date();
			String hostOpinion = ws.getHostOpinion();
			String creditOpinion = ws.getCreditOpinion();
			
			int i = Integer.parseInt(ws.getAppStatus());
			int tempStatus = i+1;
			
			ws2.setAppStatus(String.valueOf(tempStatus));
			ws2.setCreditOpinion(creditOpinion);
			ws2.setHostOpinion(hostOpinion);
			ws2.setAppDate(date);
			em.merge(ws2);
		}
	}

	/**
	 * 保存：包括新增和修改
	 * 
	 * @param ws
	 */
	public void save1(GroupMember ws) {
	//system.out.printlnrintln(ws.getId()
	//system.out.println.println(em);
		if (ws.getId() == null) {
			em.persist(ws);
		} else {
			GroupMember ws2 = em.find(GroupMember.class, ws.getId());
			// ws.setProperty("groupStatus", "启用");
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currenUserId = auth.getUserId();
			Date date = new Date();
			ws2.setUpdateUserId(currenUserId);
//			ws2.setGroupStatus("1");
			ws2.setUpdateDate(date);
			em.merge(ws2);
		}
	}

	/**
	 * 移除记录
	 * 
	 * @param id
	 */
	public void changeSts(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			GroupMember groupMember = find(id);
			if (groupMember != null) {
				em.remove(groupMember);
			}
		}
	}

	/**
	 * 查看记录
	 * 
	 * @param id
	 * @return
	 */
	public GroupMember find(Long id){
	//system.out.printlnut.println(id);
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

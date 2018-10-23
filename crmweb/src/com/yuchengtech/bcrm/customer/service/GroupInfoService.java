package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.GroupInfo;
import com.yuchengtech.bcrm.customer.model.GroupMember;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 对于集团基本信息的维护service
 * @author sujm
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class GroupInfoService {

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
//	public  void save(GroupMember gm){
//		
//		
//	}
	
	/**
	 * 保存：包括新增和修改
	 * 
	 * @param ws
	 */
	public void save(GroupInfo ws) {
		if (ws.getId() == null) {
	
			
			GroupMember gm = new GroupMember();
			GroupMember gm1 = new GroupMember();
			GroupMember gm2 = new GroupMember();
			
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currenUserId = auth.getUserId();
			ws.setUpdateUserId(currenUserId);
			ws.setCreateUserId(currenUserId);
			ws.setCreateUserName(auth.getCname());
			ws.setCreateUserOrgId(auth.getUnitId());
			Date date = new Date();
			ws.setUpdateDate(date);
			ws.setCreateDate(date);
			em.persist(ws);
			GroupInfo ws2 = em.find(GroupInfo.class, ws.getId());
			Long sss = ws2.getId();
			String groupNo = "G00"+sss;
			ws2.setGroupNo(groupNo);
			
			gm.setGroupNo(groupNo);
			gm.setId(sss);
			gm.setGroupName(ws.getGroupName());
			gm.setCustId(groupNo);
			gm.setParentId("0");
			
			
			em.merge(ws2);
			
			String parentId = ws2.getId().toString();
			
			gm1.setCustId("JTDWDBCY");
			gm1.setParentId(parentId);
			gm1.setGroupNo(groupNo);
			gm1.setMemberType("2");
			
			gm2.setParentId(parentId);
			gm2.setGroupNo(groupNo);
			gm2.setMemberType("1");
			gm2.setAppStatus("3");
			gm2.setCustId(ws.getGroupRootCustId());
//			gm2.setGroupName(ws.getGroupName());
			
			em.merge(gm);
			em.merge(gm2);
			em.merge(gm1);
		} else {
			/**
			 * 修改对象信息，并录入更新时间和更新人
			 * */
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currenUserId = auth.getUserId();
			Date date = new Date();
			ws.setUpdateUserId(currenUserId);
			ws.setUpdateDate(date);
			em.merge(ws);
		}
	}

	/**
	 * 保存：包括新增和修改
	 * 
	 * @param ws
	 */
	public void save1(GroupInfo ws) {
		//system.out.printlnln(ws.getId());
//system.out.printlnntln(em);
		if (ws.getId() == null) {
			em.persist(ws);
		} else {
			GroupInfo ws2 = em.find(GroupInfo.class, ws.getId());
			// ws.setProperty("groupStatus", "启用");
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currenUserId = auth.getUserId();
			Date date = new Date();
			ws2.setUpdateUserId(currenUserId);
			ws2.setGroupStatus("1");
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
			// GroupInfo GroupInfo = find(id);
			GroupInfo ws2 = em.find(GroupInfo.class, id);
			if (ws2 != null) {
				AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String currenUserId = auth.getUserId();
				Date date = new Date();
				ws2.setGroupStatus("0");
				ws2.setUpdateDate(date);
				ws2.setUpdateUserId(currenUserId);
				em.merge(ws2);
			}
		}
	}

	/**
	 * 查看记录
	 * 
	 * @param id
	 * @return
	 */
	public GroupInfo find(Long id) {//system.out.printlnrintln(id);
		return em.find(GroupInfo.class, id);
	}

	//通过groupNo查找集团客户
	public GroupInfo findByGroupNo(String groupNo){
		StringBuilder stringBuilder	 = new StringBuilder("select groupInfo from GroupInfo groupInfo where groupInfo.groupNo = '"+groupNo+"'"); 
		Query query =em.createQuery(stringBuilder.toString());
	   return(GroupInfo)query.getSingleResult();
	}
	
	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupInfo> findAll() {
		String wsFindAll = "select ws from GroupInfo ws";
		Query wsQuery = em.createQuery(wsFindAll);
		return wsQuery.getResultList();
	}

}

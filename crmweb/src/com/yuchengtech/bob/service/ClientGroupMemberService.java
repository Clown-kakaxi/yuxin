package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.ClientGroupMember;
/*
 * 2011-07-22
 * yaoliang
 * 集团客户成员业务逻辑，主要包含了，新增、修改、删除、查询方法
 * */
@Service
@Transactional(value="postgreTransactionManager")
public class ClientGroupMemberService {
	
	private EntityManager em;
	@PersistenceContext
	public void setEntityManager(EntityManager em)
	{
		this.em = em;
	}
	
	public void saveGroupMember(ClientGroupMember amember) {
		if (amember.getMemberId() == null) {
			em.persist(amember);//新增一个成员
		} else {
			em.merge(amember);//修改成员
		}
	}
	
	//删除一个集团客户成员
	public   void deleteGroupMember(Long id) {
		ClientGroupMember amember = em.find(ClientGroupMember.class, id);
		if(amember != null){
			em.remove(amember);
		}
	}		
	//查找集团客户成员列表
	@SuppressWarnings("unchecked")
//	public List<ClientGroupMember> memberSearch(Map amember,int maxRows,int startRows) {
	public List<ClientGroupMember> memberList(String groupId) {	
		
		//system.out.printlnln("groupId : "+groupId);
		StringBuffer infoQueryStrBuffer = new StringBuffer("SELECT groupMember FROM ClientGroupMember groupMember where (1=1) and groupMember.ocrmFCiGroupInfo.groupId=?1 ");
			
		Query memberQuery = em.createQuery(infoQueryStrBuffer.toString());
		memberQuery.setParameter(1, groupId);
		return memberQuery.getResultList();//放回数据的List
	}
	//通过主键查找单个集团客户的成员
	public ClientGroupMember memberSearch(Long id) {
			Query memberQuery = em.createQuery("SELECT memberQuery FROM GroupMember memberQuery where memberQuery.id = ?1");
			memberQuery.setParameter(1,id);
			return (ClientGroupMember) memberQuery.getSingleResult();
		
	}
}

package com.yuchengtech.bcrm.custmanager.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.custmanager.model.MarketTeam;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 日程服务
 * @author WillJoe
 *
 */
@Service
@Transactional(value="postgreTransactionManager")
public class MarketTeamService {
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	/**
	 * 保存：包括新增和修改
	 * @param ws
	 */
	public void save(MarketTeam ws){
		////system.out.printlnln(marketTeamName+"12312312******************************************");
		if(ws.getMktTeamId()==null){
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currenUserId = auth.getUserId();
			Date date = new Date();
			ws.setCreateDate(date);
			ws.setCreateUser(currenUserId);
			StringBuffer id2= new StringBuffer(ws.getOrgId());
			
//			String org_id = id2.substring(0, 5).toString();
//			ws.setOrganizationId(org_id);
			ws.setCreateUserId(auth.getUserId());
			ws.setCreateUserName(auth.getCname());
			ws.setCreateUserOrgId(auth.getUnitId());
//			ws.setOrganizationId("0000000");
			em.persist(ws);
		}else em.merge(ws);
//system.out.printlnntln("更新");
	}
	
	/**
	 * 移除记录
	 * @param id
	 */
	public void remove(Long id){
//		em.remove(em.find(MarketTeam.class, id));
		MarketTeam ws2 = em.find(MarketTeam.class, id);
		if(ws2!=null){
			ws2.setTeamStatus("2");
			em.merge(ws2);
		}
	}
	
	/**
	 * 查看记录
	 * @param id
	 * @return
	 */
	public MarketTeam find(Long id){
		return em.find(MarketTeam.class, id);
	}
	
	/**
	 * 查询所有记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarketTeam> findAll(){
		String wsFindAll = "select ws from MarketTeam ws";
		Query wsQuery = em.createQuery(wsFindAll);
		return wsQuery.getResultList();
	}
}

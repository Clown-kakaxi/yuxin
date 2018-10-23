package com.yuchengtech.bcrm.custmanager.service;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.json.JSONArray;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.custmanager.model.TeamCustomerManager;
import com.yuchengtech.bcrm.customer.model.GroupMember;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * @author sujm
 * 
 */
@Service
@Transactional(value="postgreTransactionManager")
public class TeamCustomerManagerService {

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
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

	public void batchSave(JSONArray jarray,JSONArray jarray1) throws ParseException{
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		Date date = new Date();
		if(jarray.size()>0){
			for(int i=0;i<jarray.size();i++){
				TeamCustomerManager tcm = new TeamCustomerManager();
				tcm.setUserId(jarray.get(i).toString());
				tcm.setMarketTeamId(jarray1.get(i).toString());
				tcm.setJoinDate(date);
				
				em.persist(tcm);
	        	}
	        }
		
	} 
	
	   //删除
    public void remove(JSONArray jarray) {
   //system.out.printlnntln(em);
      if(jarray.size()>0)
	     {
	   for (int i=0;i<jarray.size();i++)
   {
		     Long id=Long.parseLong(jarray.get(i).toString());
		     TeamCustomerManager teamCustomerManager = em.find(TeamCustomerManager.class, id);
			 
	   if (teamCustomerManager != null) {
	       em.remove(teamCustomerManager);
	    }
       }    	
    }
}
	
}

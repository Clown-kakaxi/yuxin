package com.yuchengtech.bcrm.customer.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.json.JSONArray;

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
public class GroupMemberService {

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
			String remark = ws.getRemark();
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			String temp_app_user_name = auth.getCname();
			String temp_app_user_id = auth.getUnitId();
			String tem_app_user_org_id = auth.getUnitId();
			
			if(ws2.getAppStatus().equals("0")||ws2.getAppStatus().equals("1")){
				int tempStatus=2;
				ws2.setHostOpinion("");
				ws2.setCreditOpinion("");
				ws2.setRemark(remark);
				ws2.setAppUserId(temp_app_user_id);
				ws2.setAppUserName(temp_app_user_name);
				ws2.setAppUserOrgId(tem_app_user_org_id);
				if(ws2.getAppStatus().equals("1")){
					ws2.setSubmitDate(date);
					ws2.setAppDate(null);
				}
				if(ws2.getAppStatus().equals("2")){
				ws2.setAppDate(date);
				}
				ws2.setAppStatus(String.valueOf(tempStatus));
				em.merge(ws2);
			}else{
			int tempStatus=0;
			
			ws2.setAppStatus(String.valueOf(tempStatus));
			ws2.setRemark(remark);
			ws2.setAppDate(date);
			em.merge(ws2);
			}
		}
		}

	/**
	 * 保存：包括新增和修改
	 * 
	 * @param ws
	 */
	public void save1(GroupMember ws,String str) {
		if (ws.getId() == null) {
			em.persist(ws);
		} else {
			GroupMember ws2 = em.find(GroupMember.class, ws.getId());
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String currenUserId = auth.getUserId();
//			Date date = new Date();
//			ws2.setUpdateUserId(currenUserId);
			ws2.setParentId(str);
//			ws2.setUpdateDate(date);
			em.merge(ws2);
		}
	}

	/**
	 * 移除记录
	 * 
	 * @param id
	 */
//	public void changeSts(String idStr) {
//		String[] strarray = idStr.split(",");
//		for (int i = 0; i < strarray.length; i++) {
//			long id = Long.parseLong(strarray[i]);
//			GroupMember groupMember = find(id);
//			if (groupMember != null) {
//				em.remove(groupMember);
//			}
//		}
//	}
	public void removeSujm(Long id){
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

//
//	public void batchSave(JSONArray jarray, JSONArray jarray1,JSONArray jarray2, JSONArray jarray3, JSONArray jarray4,
//			JSONArray jarray5, JSONArray jarray6, JSONArray jarray7) throws ParseException{
//		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String currenUserId = auth.getUserId();
//		if(jarray.size()>0){
//			for(int i=0;i<jarray.size();i++){
//				GroupMember gm = new GroupMember();
//				gm.setCustId(jarray.get(i).toString());
//				gm.setGroupNo(jarray2.get(i).toString());
//				gm.setParentId((Long) jarray3.get(i));
//				gm.setRelationId(jarray4.get(i).toString());
//				gm.setMemberType(jarray5.get(i).toString());
//				gm.setStockRate(jarray6.get(i).toString());
//				gm.setRemark(jarray7.get(i).toString());
//				gm.setUpdateUserId(currenUserId);
//				gm.setAppStatus("1");
//				em.persist(gm);
//			}
//		}
//		
//	}
	
	public void batchSave(JSONArray jarray,JSONArray jarray2, JSONArray jarray3, JSONArray jarray4,
			JSONArray jarray5, JSONArray jarray6, JSONArray jarray7) throws ParseException{
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		if(jarray.size()>0){
			for(int i=0;i<jarray.size();i++){
				GroupMember gm = new GroupMember();
				gm.setCustId(jarray.get(i).toString());
				gm.setGroupNo(jarray2.get(i).toString());
				gm.setParentId(jarray3.get(i).toString());
				gm.setRelationId(jarray4.get(i).toString());
				gm.setMemberType(jarray5.get(i).toString());
				gm.setUpdateUserId(currenUserId);
				String ss = jarray6.get(i).toString();
				if(ss==null||ss.equals("")){
				  gm.setStockRate("0");
				}
				else{
				gm.setStockRate(jarray6.get(i).toString());
				}
				gm.setRemark(jarray7.get(i).toString());
				gm.setUpdateUserId(currenUserId);
				gm.setAppStatus("1");
				em.persist(gm);
	        	}
	        }
		
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

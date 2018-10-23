package com.yuchengtech.bcrm.custmanager.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupInfo;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupMember;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupMembership;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 
* @ClassName: GroupMemberRelationShipService 
* @Description: 集团组织架构图
* @author wangmk1 
* @date 2014-8-14 上午11:36:44 
*
 */
@Service
public class GroupMemberRelationShipService  extends CommonService{
	
	public GroupMemberRelationShipService(){
		JPABaseDAO<OcrmFCiGroupMembership,Long> baseDao = new JPABaseDAO<OcrmFCiGroupMembership,Long>(OcrmFCiGroupMembership.class);
		super.setBaseDAO(baseDao);
	}
	
	@SuppressWarnings("unchecked")
	public void updateGraph(String groupId,String infos){
		JSONObject info = JSONObject.fromObject(infos);
		JSONObject graph = info.getJSONObject("graph");
		String groupNo=graph.getString("groupNo");
		JSONArray es = info.getJSONArray("edges");
    	String searchEdeges = "SELECT t FROM OcrmFCiGroupMembership t WHERE t.groupNo = ?1";
		Query edgesQuery = em.createQuery(searchEdeges);
		edgesQuery.setParameter(1, groupNo);
		List<OcrmFCiGroupMembership> edges = edgesQuery.getResultList();
		Collection<OcrmFCiGroupMembership> ec = JSONArray.toCollection(es, OcrmFCiGroupMembership.class);
		List<OcrmFCiGroupMembership> newedges=(List<OcrmFCiGroupMembership>) ec;
		for(OcrmFCiGroupMembership e : edges){
			em.remove(e);
		}
		for(OcrmFCiGroupMembership o : newedges){
			em.persist(o);
		}
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> show(String groupId){
		List<OcrmFCiGroupInfo> groupInfoList=em.createQuery("SELECT t FROM OcrmFCiGroupInfo t WHERE t.id = '"+groupId+"'").getResultList();;
		OcrmFCiGroupInfo groupInfo= groupInfoList.get(0);
		String groupNo=groupInfo.getGroupNo();
		
		String searchEdeges = "SELECT t FROM OcrmFCiGroupMembership t WHERE t.groupNo = ?1";
		Query edgesQuery = em.createQuery(searchEdeges);
		edgesQuery.setParameter(1, groupNo);
		List<OcrmFCiGroupMembership> edgesList = edgesQuery.getResultList();
		List<OcrmFCiGroupMember> memberList=em.createQuery("SELECT t FROM OcrmFCiGroupMember t WHERE t.groupNo = '"+groupNo+"'").getResultList();;
		Map<String, Object> map= new HashMap<String,Object>();
		 JSONArray graph = JSONArray.fromObject(groupInfoList);   
		 JSONArray edges = JSONArray.fromObject(edgesList);
		 JSONArray vertexes = JSONArray.fromObject(memberList);
		map.put("graph", graph);
		map.put("edges", edges);
		map.put("vertexes", vertexes);
		return map;
	}
}

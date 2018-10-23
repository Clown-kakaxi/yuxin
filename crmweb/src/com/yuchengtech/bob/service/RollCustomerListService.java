package com.yuchengtech.bob.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.RollCustomerList;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class RollCustomerListService {
	private EntityManager em;
	@Autowired
	private CommonQueryService cqs ;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// 查询名单关联的客户
	@SuppressWarnings("unchecked")
	public List<RollCustomerList> findAll() {
        Query query = getEntityManager().createQuery("select rcl FROM RollCustList rcl");
        return query.getResultList();
	}

	// 新增名单关联的客户
	public String save(RollCustomerList rollCustList) {
		if (rollCustList.getId() == null) {
			em.persist(rollCustList);
			return "0";
		} else {
			return "1";
		}
	}

	// 删除名单关联的客户
	public void remove(long rollId) {
		RollCustomerList rollCustomerList = find(rollId);
		if (rollCustomerList != null) {
			em.remove(rollCustomerList);
		}
	}
	
	//批量新增名单关联的客户
	@SuppressWarnings("unchecked")
	public void batchAdd(String idStr,String rollId) throws Exception     {
		if (idStr != null && rollId != null) {
			
			Date currDate = new Date();
			
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        
			String currenUserId = auth.getUserId();
			
			StringBuilder sb = new StringBuilder(
						"select fi.cust_id,fi.cust_zh_name,fi.cust_zzdm from acrm_f_ci_cust_info fi where fi.cust_id in ("
								+ idStr + ")");
			
			cqs.setPrimaryKey("CUST_ID");
			
			Map<String, Object> map = (Map<String, Object>) cqs.excuteQuery(sb.toString(),0,999999999);

			List list = (List) map.get("data");
			
			Map<String, Object> tempMap;
			
			String tempCustId;
			
			String tempCustName;
			
			String tempCustZzdm;
			
			for (int i = 0; i < list.size(); i++) {
					tempMap = (Map<String, Object>) list.get(i);

					tempCustId = (String) tempMap.get("CUST_ID");
					tempCustName = (String) tempMap.get("CUST_ZH_NAME");
					tempCustZzdm = (String) tempMap.get("CUST_ZZDM");
					
					RollCustomerList rollCustList = new RollCustomerList();
					rollCustList.setRollId(rollId);
					rollCustList.setCreateDate(currDate);
					rollCustList.setCreateUser(currenUserId);
					rollCustList.setCustomerId(tempCustId);
					rollCustList.setCustomerName(tempCustName);
					rollCustList.setZzdm(tempCustZzdm);
					em.persist(rollCustList);
				}
		}
    }
	
	//批量删除名单关联的客户
	public void batchRemove(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			RollCustomerList rollCustomerList = find(id);
			if (rollCustomerList != null) {
				em.remove(rollCustomerList);
			}
		}
	}
	
	
	private EntityManager getEntityManager() {
		return em;
	}
	
	public RollCustomerList find(long id) {
		return em.find(RollCustomerList.class, id);
	}

}

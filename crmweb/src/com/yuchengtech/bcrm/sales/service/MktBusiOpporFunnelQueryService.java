package com.yuchengtech.bcrm.sales.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @描述：营销管理->商机管理->销售漏斗查询Service
 * @author wzy
 * @date:2013-03-25
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class MktBusiOpporFunnelQueryService {

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("rawtypes")
	public String getQueryResultJsonData(String sql) {
		List rList = null;
		Object[] objs = null;
		String resultJson = null;
		rList = em.createNativeQuery(sql).getResultList();
		if (rList != null && rList.size() > 0) {
			resultJson = "";
			for (int i = 0; i < rList.size(); i++) {
				objs = (Object[]) rList.get(i);
				resultJson += (objs[2] + ",");
				resultJson += (objs[6]);
				if (i < rList.size() - 1) {
					resultJson += (",");
				}
			}
		}
		return resultJson;
	}
}
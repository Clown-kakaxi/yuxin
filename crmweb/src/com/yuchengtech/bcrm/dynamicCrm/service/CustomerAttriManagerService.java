package com.yuchengtech.bcrm.dynamicCrm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriScore;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.crm.exception.BizException;

@Service
public class CustomerAttriManagerService extends CommonService {
	
	public CustomerAttriManagerService(){
		JPABaseDAO<CustomerAttriScore, Long> baseDAO = new JPABaseDAO<CustomerAttriScore, Long>(CustomerAttriScore.class);  
		super.setBaseDAO(baseDAO);
	}
	
	public void saveCustomerAttriScore(CustomerAttriScore cas){
		if(cas.getIndexId() == null){
//			String jql = "select t from CustomerAttriScore t where t.indexValue = '" + cas.getIndexValue() + "'";
//			List list = this.em.createQuery(jql).getResultList();
//			if(list != null && list.size() > 0){
//				throw new BizException(1, 0, "", "该指标值已配置过指标分值!");
//			}
			em.persist(cas);
		}else{
//			String jql = "select t from CustomerAttriScore t where t.indexValue = '" + cas.getIndexValue() + "' and t.indexId <>'" + cas.getIndexId() + "'";
//			List list = this.em.createQuery(jql).getResultList();
//			if(list != null && list.size() > 0){
//				throw new BizException(1, 0, "", "该指标值已配置过指标分值!");
//			}
			em.merge(cas);
		}
	}
	
	/**
	 * 删除指标信息
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public void delete(String id){
		baseDAO.removeById(id);
	}
}

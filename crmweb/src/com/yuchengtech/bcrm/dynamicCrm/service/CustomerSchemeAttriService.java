package com.yuchengtech.bcrm.dynamicCrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.dynamicCrm.model.CustomerSchemeAttri;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CustomerSchemeAttriService extends CommonService {
	
	public CustomerSchemeAttriService(){
		JPABaseDAO<CustomerSchemeAttri, Long> baseDAO = new JPABaseDAO<CustomerSchemeAttri, Long>(CustomerSchemeAttri.class);  
		super.setBaseDAO(baseDAO);
	}
	
	@SuppressWarnings("unchecked")
	public void save(CustomerSchemeAttri cai){
//		if(cai.getId() != null){
//			baseDAO.persist(cai);
//		}else{
			baseDAO.save(cai);
//		}
	}
	
	/**
	 * 删除客户属性
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public void delete(String id){
		baseDAO.removeById(id);
	}
	
	/**
	 * 删除客户方案属性指标节点
	 * @param sql
	 */
	public void deleteIndex(String sql){
		super.batchUpdateByName(sql, null);
//		baseDAO.remove(sql);
	}
}

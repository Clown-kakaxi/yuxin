package com.yuchengtech.bcrm.dynamicCrm.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriItem;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CustomerAttriItemService extends CommonService {
	
	public CustomerAttriItemService(){
		JPABaseDAO<CustomerAttriItem, Long> baseDAO = new JPABaseDAO<CustomerAttriItem, Long>(CustomerAttriItem.class);  
		super.setBaseDAO(baseDAO);
	}
	
	@SuppressWarnings("unchecked")
	public void save(CustomerAttriItem cai){
			baseDAO.save(cai);
	}
	
	/**
	 * 删除客户属性
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public void delete(String id){
		//删除指标值表对应的指标分值表
		String sql = "delete from CustomerAttriScore s where " +
				"s.attriId = (select m.attriId from CustomerAttriItem m where m.id = '"+id+"') " +
				"and s.indexValue = (select i.indexValue from CustomerAttriItem i where i.id = '"+id+"')";
		super.batchUpdateByName(sql, null);
		baseDAO.removeById(id);
	}
}

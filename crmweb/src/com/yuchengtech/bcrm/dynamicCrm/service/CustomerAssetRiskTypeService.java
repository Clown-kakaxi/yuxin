package com.yuchengtech.bcrm.dynamicCrm.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAssetRiskType;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class CustomerAssetRiskTypeService extends CommonService {

	private static Logger log = Logger.getLogger(CustomerAssetRiskTypeService.class);
	
	public CustomerAssetRiskTypeService(){
		JPABaseDAO<CustomerAssetRiskType, Long> baseDAO = new JPABaseDAO<CustomerAssetRiskType, Long>(CustomerAssetRiskType.class);  
		super.setBaseDAO(baseDAO);
	}

	public Object save(Object obj){
		return super.save(obj);
	}
	
	/**
	 * 删除风险类别配置及关联产品推荐信息
	 * @param id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void delete(String riskTypeId, String riskTypeName){
		String jql = "select t from CustomerAssetConf t where t.riskTypeId = '" + riskTypeId + "'";
		List list = this.em.createQuery(jql).getResultList();
		
		//删除风险类别的产品推荐信息信息
		if(list != null && list.size() > 0){
			log.info("删除" + riskTypeName + "的产品推荐信息。");
			for(Object obj : list){
				this.em.remove(obj);
			}
		}
		baseDAO.removeById(riskTypeId);
	}
}

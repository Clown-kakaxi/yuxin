package com.yuchengtech.bcrm.dynamicCrm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAssetConf;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.crm.exception.BizException;

/**
 * @modify denghj
 * @since 2016-3-1
 * 改为风险类别对应推荐产品
 */
@Service
public class CustomerAssetConfService extends CommonService {
	
	public CustomerAssetConfService(){
		JPABaseDAO<CustomerAssetConf, Long> baseDAO = new JPABaseDAO<CustomerAssetConf, Long>(CustomerAssetConf.class);  
		super.setBaseDAO(baseDAO);
	}

	/**
	 * 保存风险类别配置推荐产品
	 * @param caf
	 */
	@SuppressWarnings("rawtypes")
	public void saveCustomerAssetConf(CustomerAssetConf caf){
		if(caf.getId() == null){
			String jql = "select t from CustomerAssetConf t where t.riskTypeId = '" + caf.getRiskTypeId() + "'";
			List list = this.em.createQuery(jql).getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1, 0, "", "该风险类别推荐产品配置已存在!");
			}
			em.persist(caf);
		}else{
			String jql = "select t from CustomerAssetConf t where t.riskTypeId = '" + caf.getRiskTypeId() + "' and t.id <> '" + caf.getId() + "'";
			List list = this.em.createQuery(jql).getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1, 0, "", "该风险类别推荐产品配置已存在!");
			}
			em.merge(caf);
		}
	}
	
	/**
	 * 删除风险类别配置推荐产品
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public void destroy(String id){
		baseDAO.removeById(id);
	}
}

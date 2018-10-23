package com.yuchengtech.bcrm.dynamicCrm.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.dynamicCrm.model.CustomerScheme;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

@Service
public class CustomerSchemeService extends CommonService {
	
	public CustomerSchemeService(){
		JPABaseDAO<CustomerScheme, Long> baseDAO = new JPABaseDAO<CustomerScheme, Long>(CustomerScheme.class);  
		super.setBaseDAO(baseDAO);
	}
	
	@SuppressWarnings("rawtypes")
	public void save(CustomerScheme cs){
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//如果当前cs的状态为0-启用，则把其他方案都改为停用
//		if(cs.getSchemeState() != null && "0".equals(cs.getSchemeState())){
//			String sql = "update CustomerScheme c set c.schemeState='1'";
//			super.batchUpdateByName(sql, null);
//		}
		
		if(cs.getSchemeId() == null || "".equals(cs.getSchemeId())){
			String jql = "select t from CustomerScheme t where t.schemeName = '" + cs.getSchemeName() + "'";
			List list = this.em.createQuery(jql).getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1, 0, "", "该方案名称已存在!");
			}
			cs.setSchemeId(null);
			em.persist(cs);
		}else{
			String jql = "select t from CustomerScheme t where t.schemeName = '" + cs.getSchemeName() 
				+ "' and t.schemeId <> '" + cs.getSchemeId() + "'";
			List list = this.em.createQuery(jql).getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1, 0, "", "该方案名称已存在!");
			}
			em.merge(cs);
		}		
		auth.setPid(cs.getSchemeId());
	}
	
	/**
	 * 删除方案
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public void delete(String id){
		//删除方案信息表对应方案属性指标表信息
		super.batchUpdateByName("delete from CustomerSchemeAttri a where a.schemeId = '"+id+"'", null);
		baseDAO.removeById(id);
	}
}

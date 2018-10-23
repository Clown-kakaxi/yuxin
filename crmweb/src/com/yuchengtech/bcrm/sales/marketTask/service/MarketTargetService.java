package com.yuchengtech.bcrm.sales.marketTask.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTarget;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @describtion: 营销指标信息
 *
 * @author : helin
 * @date : 2014-07-03 10:21:57
 */
@Service
public class MarketTargetService extends CommonService {
    public MarketTargetService(){
        JPABaseDAO<OcrmFMmTarget, String> baseDao = new JPABaseDAO<OcrmFMmTarget, String>(OcrmFMmTarget.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * 新增或修改营销指标
     */
    public Object save(Object obj) {
        OcrmFMmTarget marketTarget = (OcrmFMmTarget)obj;
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(marketTarget.getUpdateUser() == null){
        	List<?> list = this.em.createQuery("select t from OcrmFMmTarget t where t.targetCode = '"+marketTarget.getTargetCode()+"'").getResultList();
        	if(list != null && list.size() > 0){
        		throw new BizException(1,0,"","指标编号已存在！");
        	}
        }
        marketTarget.setUpdateDate(new Date());
        marketTarget.setUpdateUser(auth.getUserId());
        return super.save(marketTarget);
    }
    
    /**
     * 批量删除营销指标
     */
    public void batchRemove(String ids) {
    	List<?> list = this.em.createQuery("select t from OcrmFMmTaskTarget t where t.targetCode in ("+ids+")").getResultList();
    	if(list != null && list.size() > 0){
    		throw new BizException(1,0,"","待删除指标已使用过,不能删除！");
    	}
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFMmTarget t WHERE t.targetCode IN("+ ids +")", values);
    }
}

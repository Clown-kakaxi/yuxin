package com.yuchengtech.bcrm.finService.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.finService.model.OcrmFFinTarget;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 需求产品
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月23日 上午9:59:18
 */
@Service
public class OcrmFFinTargetService extends CommonService {
	public OcrmFFinTargetService(){
		JPABaseDAO<OcrmFFinTarget, Long> baseDAO = new JPABaseDAO<OcrmFFinTarget, Long>(OcrmFFinTarget.class);
		super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 新增需求产品目标
	 */
	public Object save(Object obj){
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OcrmFFinTarget target = (OcrmFFinTarget) obj;
		if(target.getTargetId() == null){
			target.setCreateDate(new Date());
			target.setCreatorId(auth.getUserId());
			target.setTargetScale(BigDecimal.valueOf(0));
		}
		target.setAvailable("1");
		return super.save(target);
	}
	
	/**
	 * 删除需求目标，同时删除目标的产品组合配置
	 */
	public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFFinTarget t WHERE t.targetId IN("+ ids +")", values);
        super.batchUpdateByName("DELETE FROM OcrmFFinProdConf t WHERE t.targetId IN("+ ids +")", values);
    }
}

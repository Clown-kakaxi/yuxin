package com.yuchengtech.bcrm.sales.marketTask.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTaskTarget;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * @describtion: 营销任务指标完成信息
 *
 * @author : helin
 * @date : 2014-07-03 10:21:33
 */
@Service
public class MarketTaskTargetService extends CommonService {
    public MarketTaskTargetService(){
        JPABaseDAO<OcrmFMmTaskTarget, Long> baseDao = new JPABaseDAO<OcrmFMmTaskTarget, Long>(OcrmFMmTaskTarget.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * create or update marketTaskTarget
     */
    public Object save(Object obj) {
        OcrmFMmTaskTarget marketTaskTarget = (OcrmFMmTaskTarget)obj;
        return super.save(marketTaskTarget);
    }
    
    /**
     * bacth delete marketTaskTarget
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFMmTaskTarget t WHERE t.id IN("+ ids +")", values);
    }
}

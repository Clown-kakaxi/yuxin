package com.yuchengtech.bcrm.wealthManager.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinCustRisk;
import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinCustRiskQa;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.core.SysPublicParamManager;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 风险评估
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014-06-19 17:36:10
 */
@Service
public class CustRiskEvaService extends CommonService {
    public CustRiskEvaService(){
        JPABaseDAO<OcrmFFinCustRisk, Long> baseDao = new JPABaseDAO<OcrmFFinCustRisk, Long>(OcrmFFinCustRisk.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * create or update custRiskEva
     */
    public Object save(Object obj) {
        OcrmFFinCustRisk custRiskEva = (OcrmFFinCustRisk)obj;
        return super.save(custRiskEva);
    }
    
    /**
     * bacth delete custRiskEva
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFFinCustRisk t WHERE t.id IN("+ ids +")", values);
    }
    
    /**
     * 提交在线评估
     * @param questionObj
     */
    public void commitRiskEva(Map questionObj){
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if (questionObj == null || questionObj ==null) {
			return ;
		}
    	String RISK_VALID_YEAR = SysPublicParamManager.getInstance().findParamItemByName("RISK_VALID_YEAR").getPropValue();
    	Date limitDate = new Date();
    	limitDate.setYear(limitDate.getYear() + Integer.valueOf(RISK_VALID_YEAR));//此处要调用系统参数来设置，暂时未创建参数待修改
    	OcrmFFinCustRisk custRisk = new OcrmFFinCustRisk();
    	custRisk.setCustId((String) questionObj.get("CUST_ID"));
    	custRisk.setCustName((String) questionObj.get("CUST_NAME"));
    	custRisk.setHisFlag("0");
    	custRisk.setQStat("02");
    	custRisk.setEvaluateName(auth.getUserId());
    	custRisk.setEvaluateInst(auth.getUnitId());
    	custRisk.setEvaluateDate(new Date());
    	//custRisk.setEvaluateRelatTelephone("");
    	custRisk.setLimitDate(limitDate);
    	List<OcrmFFinCustRiskQa> rqList = new ArrayList<OcrmFFinCustRiskQa>();
    	StringBuffer sb = new StringBuffer("SELECT T.TITLE_ID,T.RESULT_ID,T.RESULT_SCORING FROM OCRM_F_SE_TITLE_RESULT T where 1=2 ");
    	for(Object key:questionObj.keySet()){
            if(questionObj.get(key) != null && !"".equals(questionObj.get(key)) 
            		&& !"CUST_ID".equals(key) && !"CUST_NAME".equals(key)){
            	sb.append(" OR ( T.TITLE_ID = '"+key+"' AND T.RESULT_ID = '"+questionObj.get(key)+"' )");
            }
        }
    	List resultList = this.em.createNativeQuery(sb.toString()).getResultList();
    	Long totalScore = 0l;
    	for(int i=0,size = resultList.size(); i< size;i++){
    		Object[] obj = (Object[]) resultList.get(i);
    		if(obj == null || obj.length < 3){
    			continue;
    		}
    		OcrmFFinCustRiskQa rq = new OcrmFFinCustRiskQa();
        	//rq.setCustQTId(custRisk.getCustQId());//必须待（主表保存后才能设置）
        	rq.setQaTitle(String.valueOf(obj[0]));
        	rq.setCustSelectContent(String.valueOf(obj[1]));
        	rq.setScoring(BigDecimal.valueOf(Long.valueOf(String.valueOf(obj[2]))));
        	//rq.setTitleRemark("");//题目备注设置为空
        	totalScore += rq.getScoring().longValue();
        	rqList.add(rq);
    	}
    	List list = this.em.createNativeQuery("select RISK_CHARACT from OCRM_F_FIN_RISK_PARAM t where t.lower_value <= "+totalScore+"  and t.upper_value >= "+totalScore).getResultList();
    	if(list != null && list.size()>0){
    		Object o = list.get(0);
    		custRisk.setCustRiskCharact(o.toString());
    	}
    	custRisk.setIndageteQaScoring(BigDecimal.valueOf(totalScore));
    	//将本客户以前评估的状态更改为历史状态
    	this.em.createQuery("update OcrmFFinCustRisk set hisFlag = '1' where custId = '"+custRisk.getCustId()+"'").executeUpdate();
    	super.save(custRisk);//新增评估
    	//JPABaseDAO<OcrmFFinCustRiskQa, Long> baseDao2 = new JPABaseDAO<OcrmFFinCustRiskQa, Long>(OcrmFFinCustRiskQa.class);
    	for(int i=0;i<rqList.size();i++){
    		rqList.get(i).setCustQId(custRisk.getCustQId());
    		this.em.persist(rqList.get(i));
    		//baseDao2.save(rqList.get(i));
    	}
    }
}

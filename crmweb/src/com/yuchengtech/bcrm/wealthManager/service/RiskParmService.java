package com.yuchengtech.bcrm.wealthManager.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinRiskParam;
import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinRiskParamAppr;
import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinRiskParamHis;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @describtion: 风险参数维护
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014-06-24 16:01:07
 */
@Service
public class RiskParmService extends CommonService {
    public RiskParmService(){
        JPABaseDAO<OcrmFFinRiskParam, Long> baseDao = new JPABaseDAO<OcrmFFinRiskParam, Long>(OcrmFFinRiskParam.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * 风险参数调整
     */
    public Object save(Object obj) {
        ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Map<String, Object>> listRecord = new ArrayList();
		try {
			/** 取列表中的数据并转换成list */
			listRecord = (List) JSONUtil.deserialize(request.getParameter("listRecordJson"));
		} catch (JSONException e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
		OcrmFFinRiskParamAppr appr = new OcrmFFinRiskParamAppr();
		appr.setApplyDate(new Date());
		appr.setApplyUser(auth.getUserId());
		appr.setApprStatus("01");//01 审批中  02 审批通过  03 审批拒绝
		Object o1 = super.save(appr);
		//将修改前的风险类型保存
		this.em.createNativeQuery("insert into ocrm_f_fin_risk_param_his(id,appr_id,risk_charact,lower_value,upper_value,is_before,REMARK) select id_sequence.nextval,'"+appr.getId()+"',t.risk_charact,t.lower_value,t.upper_value,'01',REMARK from ocrm_f_fin_risk_param t").executeUpdate();
        //将欲修改的记录保存
		if (listRecord != null && listRecord.size() >0) {
			for (Map<String, Object> tel : listRecord) {
				OcrmFFinRiskParamHis his = new OcrmFFinRiskParamHis();
				his.setApprId(String.valueOf(appr.getId()));
				his.setIsBefore("02");//01表示修改前，02表示修改后
				his.setRiskCharact((String) tel.get("RISK_CHARACT"));
				his.setLowerValue(BigDecimal.valueOf(Long.valueOf(String.valueOf(tel.get("LOWER_VALUE")))));
				his.setUpperValue(BigDecimal.valueOf(Long.valueOf(String.valueOf(tel.get("UPPER_VALUE")))));
				his.setRemark((String) tel.get("REMARK"));
				super.save(his);
			}
		}
		//提交审批流程
		String instanceid = "RISKPAR_"+appr.getId();
		String jobName ="风险参数调整_"+auth.getUsername()+"_"+new SimpleDateFormat("yyyy-MM-dd").format(appr.getApplyDate());//自定义流程名称
		try {
			initWorkflowByWfidAndInstanceid("22", jobName, null, instanceid);
			wfCompleteJob(instanceid, "22_a3", "22_a4", null, null);
			response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"22_a3\",\"nextNode\":\"22_a4\"}");
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
		return o1;
    }
    
    /**
     * bacth delete riskParm
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFFinRiskParam t WHERE t.id IN("+ ids +")", values);
    }
}

package com.yuchengtech.bcrm.wealthManager.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.wealthManager.model.OcrmFFinRiskParam;
import com.yuchengtech.bcrm.wealthManager.service.RiskParmService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @describtion: 风险参数维护
 * 
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014-06-24 16:01:07
 */
@Action("/riskParm")
public class RiskParmAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private RiskParmService riskParmService;
    
    @Autowired
    public void init(){
        model = new OcrmFFinRiskParam();
        setCommonService(riskParmService);
    }
    
    /**
     * 风险参数查询SQL
     */
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer("SELECT t.* FROM OCRM_F_FIN_RISK_PARAM t WHERE 1=1 ");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
                if("ID".equals(key)){
                    sb.append(" AND t.ID = '"+this.getJson().get(key)+"'");
                }
            	if("RISK_CHARACT".equals(key)){
                    sb.append(" AND t.RISK_CHARACT = '"+this.getJson().get(key)+"'");
                }
            }
        }
        SQL = sb.toString();
        datasource =ds;
        configCondition("t.LOWER_VALUE","=","LOWER_VALUE",DataType.String);
        configCondition("t.UPPER_VALUE","=","UPPER_VALUE",DataType.String);
    }
    
    /**
     * batch delete riskParm
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("ids");
        riskParmService.batchRemove(ids);
        return "success";
    }
    
    /**
   	 * 查询当前审批风险参数调整明细
   	 * @return
   	 */
   	public HttpHeaders queryRiskHisByApprId() {
   		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String apprId = request.getParameter("apprId");
   			StringBuilder sb = new StringBuilder("select t1.Id,T1.APPR_ID,T1.RISK_CHARACT,T1.LOWER_VALUE,T1.UPPER_VALUE,T1.IS_BEFORE,T1.REMARK from ocrm_f_fin_risk_param_his t1 where t1.appr_id = '"+apprId+"' ORDER BY T1.IS_BEFORE,T1.LOWER_VALUE");
   			QueryHelper queryHelper = new QueryHelper(sb.toString(), ds.getConnection());
   			queryHelper.addOracleLookup("RISK_CHARACT","CUST_RISK_CHARACT");//风险类型
   			if(this.json!=null){
           		this.json.clear();
   			}else {
           		this.json = new HashMap<String,Object>(); 
           	}
   			this.json.put("json",queryHelper.getJSON());
   		} catch (Exception e) {
   			e.printStackTrace();
   			throw new BizException(1,2,"1002",e.getMessage());
   		}
   		return new DefaultHttpHeaders("success").disableCaching();
   	}
}

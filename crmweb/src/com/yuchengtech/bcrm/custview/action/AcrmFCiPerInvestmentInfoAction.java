package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerInvestment;
import com.yuchengtech.bcrm.custview.service.AcrmFCiPerInvestmentInfoService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 对私客户视图(个人投资信息)
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiPerInvestmentInfo")
public class AcrmFCiPerInvestmentInfoAction extends CommonAction{
	@Autowired
	private AcrmFCiPerInvestmentInfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiPerInvestment();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
		StringBuilder sb = new StringBuilder();
		sb.append("select to_char(t.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TMM ,t.* from ACRM_F_CI_PER_INVESTMENT t  where 1=1 ");
		if(customerId != null){
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("INVEST_AIM".equals(key)){
					sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
				}
		  		if("INVEST_EXPECT".equals(key)){
		  			sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
		  		}
		  		if("INVEST_TYPE".equals(key)){
		  			sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
		  		}
			}
		}
		
		SQL = sb.toString();
	    setPrimaryKey(" t.INVESTMENT_ID desc");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			long idStr = Long.parseLong(request.getParameter("messageId"));
			String jql = "delete from AcrmFCiPerInvestment c where c.investmentId in ('"
					+ idStr + "')";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
}

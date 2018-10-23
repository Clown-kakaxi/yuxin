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
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerLikeinfo;
import com.yuchengtech.bcrm.custview.service.AcrmFCiPerLikeinfoService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 对私客户视图(个人客户喜好信息)
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiPerLikeinfo")
public class AcrmFCiPerLikeinfoAction extends CommonAction{
	@Autowired
	private AcrmFCiPerLikeinfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiPerLikeinfo();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
		StringBuilder sb = new StringBuilder();
		sb.append("select p.finance_business_prefer as LIKE_BUSI_TYPE," +
				" p.contact_time_prefer as LIKE_CONTACT_TIME," +
				" p.invest_cycle as INVEST_CYCLE, " +
				" per.religious_belief as CUST_RELIGION, " +
				" t.* from ACRM_F_CI_PER_LIKEINFO t " +
				" left join ACRM_F_CI_PER_PREFERENCE p on t.cust_id = p.cust_id " +
				" left join ACRM_F_CI_PERSON per on t.cust_id = per.cust_id " +
				" where 1=1 ");
		if(customerId != null){
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		
		SQL = sb.toString();
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			long idStr = Long.parseLong(request.getParameter("messageId"));
			String jql = "delete from AcrmFCiPerLikeinfo c where c.id in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
}

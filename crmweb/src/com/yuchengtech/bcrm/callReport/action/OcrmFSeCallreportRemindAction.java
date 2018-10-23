package com.yuchengtech.bcrm.callReport.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreportRemind;
import com.yuchengtech.bcrm.callReport.service.OcrmFSeCallreportRemindService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * callReport
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/ocrmFSeCallreportRemind")
public class OcrmFSeCallreportRemindAction extends CommonAction{
	@Autowired
	private OcrmFSeCallreportRemindService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new OcrmFSeCallreportRemind();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder();
		String callIds = request.getParameter("callIds");
		String callReport_custId = request.getParameter("callReport_custId");
		sb.append("select p.* " +
				" from OCRM_F_SE_CALLREPORT_REMIND P " +
//				" left join OCRM_F_SE_CALLREPORT_REMIND N ON N.cust_ID = p.cust_ID " +
				" where 1=1 and p.remind_date >=add_months(sysdate,-3)  ");
		if(callIds!=null && !callIds.isEmpty()){
			sb.append(" and P.CALL_ID = '"+callReport_custId+"'");
		}
		if(callReport_custId!=null && !callReport_custId.isEmpty()){
			sb.append(" and P.CUST_ID = '"+callReport_custId+"'");
		}
		
		SQL = sb.toString();
		setPrimaryKey(" p.id desc");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql = "delete from OcrmFSeCallreportRemind c where c.id in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
}

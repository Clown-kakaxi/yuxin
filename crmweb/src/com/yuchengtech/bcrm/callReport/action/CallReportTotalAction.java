package com.yuchengtech.bcrm.callReport.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFCiCallreportInfo;
import com.yuchengtech.bcrm.callReport.service.CallReportTotalService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/callReportTotal")
public class CallReportTotalAction extends CommonAction {
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	@Autowired
	private CallReportTotalService service;
	
	
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init() {
		model = new OcrmFCiCallreportInfo();
		setCommonService(service);
	}
	
	public void prepare(){
		StringBuffer sb = new StringBuffer();
		sb.append("select t.ID,t.CUST_ID,t.CUST_NAME,to_char(t.CALLREPORT_INFO) CALLREPORT_INFO,t.CREATE_USER,t.CREATE_USERNAME,t.MANAGER_OPINION,t.MANAGER_USER_NAME,t.MANAGER_USER");
		sb.append(" from OCRM_F_CI_CALLREPORT_INFO t   ");
//		sb.append(" from OCRM_F_CI_CALLREPORT_INFO t left join OCRM_F_CI_BELONG_CUSTMGR m on t.cust_id=m.cust_id and m.mgr_id in (SELECT A1.ACCOUNT_NAME FROM ADMIN_AUTH_ACCOUNT A1 WHERE A1.ACCOUNT_NAME = '"+auth.getUserId()+"' OR A1.BELONG_TEAM_HEAD = '"+auth.getUserId()+"') ");
		/*StringBuffer sb=new StringBuffer("select r.call_id,"+
				" r.cust_id,"+
				" r.cust_name,"+
				" r.cust_type,"+
				" r.visit_date,"+
				" r.link_phone,"+
				" r.visit_way,"+
				" r.begin_date,"+
				" r.end_date,"+
				" r.next_visit_date,"+
				" r.next_visit_way,"+
				" r.visit_content," +
				" r.MKT_BAK_NOTE,"+
				" r.manager_opinion,"+
				" r.manager_user_name,"+
				" r.manager_user,"+
				" b.fail_reason,"+
				" M.mgr_name as cust_mgr,"+
				" M.mgr_id,"+
				" M.org_name,"+
				" M.up_org_name"+
				" from OCRM_F_SE_CALLREPORT r"+
				" left join  (select *"+
				"              from (select call_id,"+
				"                           fail_reason,"+
				"                           SALES_STAGE,"+
				"                           rank() over(partition by call_id order by SALES_STAGE desc) mm"+
				"                      from OCRM_F_SE_CALLREPORT_BUSI)"+
                "      WHERE mm = 1) b on r.call_id = b.call_id"+
				" LEFT JOIN (select m2.cust_id,m2.mgr_name,m2.mgr_id,m2.institution as org_id,m2.institution_name as org_name,d.org_name as up_org_name from OCRM_F_CI_BELONG_CUSTMGR m2 "+
                           " left join ADMIN_AUTH_ORG c on m2.institution = c.org_id "+
                           " left join ADMIN_AUTH_ORG d on c.up_org_id = d.org_id "+
			               " union "+
			               " select m1.cus_id,a.user_name,a.account_name as mgr_id,a.org_id,b.org_name,e.org_name as up_org_name from ACRM_F_CI_POT_CUS_COM m1 "+
			               " left join ADMIN_AUTH_ACCOUNT a on m1.cust_mgr = a.account_name"+
			               " left join ADMIN_AUTH_ORG b on a.org_id = b.org_id"+
			               " left join ADMIN_AUTH_ORG e on b.up_org_id = e.org_id) M ON r.CUST_ID = m.CUST_ID"+
				" where 1 = 1");
		for(String key:this.getJson().keySet()){
			 if (null != this.getJson().get(key) && !"".equals(this.getJson().get(key))){
				 if("VISIT_LEFT_DT".equals(key)){
					 sb.append(" and r.visit_date >= to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')");
				 }
				 if("VISIT_RIGHT_DT".equals(key)){
					 sb.append(" and r.visit_date <= to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')");
				 }
			 }
		}*/
		SQL=sb.toString();
		datasource=ds;
		configCondition("t.CUST_ID", "like", "CUST_ID",DataType.String);
		configCondition("t.CUST_NAME", "like", "CUST_NAME",DataType.String);
//		configCondition("m.MGR_ID", "like", "MGR_ID",DataType.String);
//		configCondition("m.MGR_NAME", "like", "MGR_NAME",DataType.String);
		configCondition("t.CREATE_USER", "like", "CREATE_USER",DataType.String);
		configCondition("t.CUST_MANAGER_NAME", "like", "CUST_MANAGER_NAME",DataType.String);
	}
	
//	public void saveRemark()throws Exception{
//		 ActionContext ctx = ActionContext.getContext();
//		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		 String callId = request.getParameter("callId");
//		 String managerOpinion = request.getParameter("managerOpinion");
//		 StringBuilder sb = new StringBuilder(" update OcrmFSeCallreport r" +
//		 		" set r.managerOpinion = '"+managerOpinion+"'," +
//		 				"r.lastUpdateTm = to_date('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"','yyyy-MM-dd') " +
//		 						" where r.callId= '"+callId+"'");
//		 this.executeUpdate(sb.toString(), null);
//	}
	/**
	 * 主管录入意见
	 * @return
	 */
	public DefaultHttpHeaders save(){
		 	ActionContext ctx = ActionContext.getContext();
		 	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 	String id = request.getParameter("id");
			String jql = "update OcrmFCiCallreportInfo r set r.managerOpinion=:managerOpinion,r.managerUserName=:managerUserName,r.managerUser=:managerUser where r.id='"+id+"'";
	        Map<String,Object> values = new HashMap<String,Object>();
	        values.put("managerOpinion",((OcrmFCiCallreportInfo)model).getManagerOpinion());
	        values.put("managerUserName",auth.getUsername());
	        values.put("managerUser",auth.getUserId());
	        service.batchUpdateByName(jql, values);
		return new DefaultHttpHeaders("success");
	}
	
}

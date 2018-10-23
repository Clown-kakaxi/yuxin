package com.yuchengtech.bcrm.callReport.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreport;
import com.yuchengtech.bcrm.callReport.service.OcrmFSeCallreportService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;
/**
 * callReport
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/ocrmFSeCallreport")
public class OcrmFSeCallreportAction extends CommonAction{
	@Autowired
	private OcrmFSeCallreportService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new OcrmFSeCallreport();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder();
		String callReport = request.getParameter("callReport_custId");//查询客户近三次联系内容概览
		String visitDate = request.getParameter("visitDate");//查询客户近三次联系内容概览
		String showAll = request.getParameter("showAll");//查询客户近三次联系内容概览
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String callId = request.getParameter("callIds");
		sb.append("select t.LINK_PHONE, t.CALL_ID,t.CUST_CHANNEL," +
				" t.CUST_ID,t.CUST_NAME,t.CUST_TYPE,t.LAST_UPDATE_TM,t.LAST_UPDATE_USER,t.RECOMMEND_CUST_ID,t.VISIT_CONTENT, " +
				" t.VISIT_DATE,t.VISIT_WAY,t.BEGIN_DATE,t.END_DATE,t.NEXT_VISIT_DATE,t.NEXT_VISIT_WAY," +
				" (select USERNAME from SYS_USERS s where s.USERID = t.CREATE_USER and rownum<=1) as CREATE_USER, "+
				" p.CERT_TYPE as IDENT_TYPE,p.CERT_CODE as IDENT_NO,p.CUS_ID as POT_CUS_ID,t.mkt_bak_note "+
				" from OCRM_F_SE_CALLREPORT t " +
				" left join ACRM_F_CI_POT_CUS_COM p on t.CUST_ID = p.CUS_ID and p.CUST_TYPE = '2' " +
				" where 1=1 and t.create_user = '"+auth.getUserId()+"' ");
		if(callReport!=null && !callReport.isEmpty()){
			sb.setLength(0);
			sb.append("select t.LINK_PHONE, t.call_id,t.CUST_CHANNEL," +
					" t.CUST_ID,t.CUST_NAME,t.CUST_TYPE,t.LAST_UPDATE_TM,t.LAST_UPDATE_USER,t.RECOMMEND_CUST_ID,t.VISIT_CONTENT," +
					" t.VISIT_DATE,t.VISIT_WAY,t.begin_date,t.end_date,t.next_visit_date,t.next_visit_way," +
					" (select username from sys_users s where s.userid = t.create_user and rownum<=1) as create_user,t.mkt_bak_note "+
					" from OCRM_F_SE_CALLREPORT t " +
//					" left join ACRM_F_CI_CUSTOMER a on t.cust_id = a.cust_id " +
					"where 1=1 and t.create_user = '"+auth.getUserId()+"' ")
			  .append(" and t.cust_id = '"+callReport+"'");
			if(visitDate!=null && !visitDate.isEmpty()){
				sb.append(" and t.visit_Date <= to_date('"+visitDate+"','yyyy-MM-dd')");
			}
			if("DB2".equals(SystemConstance.DB_TYPE)){
//				sb.append(" fetch first 3 rows only ");
			} else if(showAll!=null && !showAll.isEmpty()){
				sb.append("");
			}else {
//				sb.append(" and rownum　<= 3 ");
			}
			
		}
		if(callId!=null && !callId.isEmpty()){//回写新增面板信息
			String rownumcondition = " and rownum = 1";
			if("DB2".equals(SystemConstance.DB_TYPE)){
				rownumcondition = " fetch first 1 rows only ";
			} else {
				rownumcondition = " and rownum = 1 ";
			}
			sb.setLength(0);
			sb.append("select t.LINK_PHONE, t.call_id,t.CUST_CHANNEL," +
					" t.CUST_ID,t.CUST_NAME,t.CUST_TYPE,t.LAST_UPDATE_TM,t.LAST_UPDATE_USER,t.RECOMMEND_CUST_ID,t.VISIT_CONTENT," +
					" t.VISIT_DATE,t.VISIT_WAY,t.begin_date,t.end_date,t.next_visit_date,t.next_visit_way, " +
					" (select username from sys_users s where s.userid = t.create_user and rownum<=1) as create_user,t.mkt_bak_note, "+
					" p.CERT_TYPE as IDENT_TYPE,p.CERT_CODE as IDENT_NO,p.CUS_ID as POT_CUS_ID "+
					" from OCRM_F_SE_CALLREPORT t " +
					" left join OCRM_F_SE_CALLREPORT_busi s on t.call_id = s.call_id " +
					" left join ACRM_F_CI_POT_CUS_COM p on t.CUST_ID = p.CUS_ID and p.CUST_TYPE = '2' "+
//					" left join ACRM_F_CI_CUSTOMER a on t.cust_id = a.cust_id " +
					"where 1=1 and t.create_user = '"+auth.getUserId()+"' ")
			  .append(" and t.call_id = '"+callId+"' " + rownumcondition);
		}
		for(String key:this.getJson().keySet()){
			 if (null != this.getJson().get(key) && !"".equals(this.getJson().get(key))){
				 if("CUST_ID".equals(key)){
					 sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
				 }
				 if("CUST_NAME".equals(key)){
					 sb.append(" and t.CUST_NAME like '%"+this.getJson().get(key)+"%'");
				 }
			 }
		}
		SQL = sb.toString();
		if(callReport!=null && !callReport.isEmpty()){
			setPrimaryKey(" t.visit_Date desc");
		}else{
			setPrimaryKey(" t.CALL_id desc");
		}
		addOracleLookup("FILE_TYPE", "FILE_TYPE");
		datasource = ds;
		
		configCondition("t.VISIT_DATE","=","VISIT_DATE",DataType.Date);
		configCondition("t.VISIT_WAY","=","VISIT_WAY",DataType.String);
		configCondition("t.CUST_TYPE","=","CUST_TYPE",DataType.String);
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql = "delete from OcrmFSeCallreport c where c.callId in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
	/**
	 * 获取客户提醒事项
	 */
	public void searchRemind(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId =  request.getParameter("cust_id");
		StringBuilder sb = new StringBuilder();
        sb.append("select t.* from OCRM_F_WP_REMIND t  where 1=1 ");
		SQL = sb.toString();
	}
}

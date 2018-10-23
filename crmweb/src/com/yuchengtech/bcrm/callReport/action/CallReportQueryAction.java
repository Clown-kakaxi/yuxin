package com.yuchengtech.bcrm.callReport.action;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFCiCallreportInfo;
import com.yuchengtech.bcrm.callReport.service.CallReportQueryService;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/callReportQueryAction")
public class CallReportQueryAction extends CommonAction{
	  @Autowired
	  @Qualifier("dsOracle")
	  private DataSource ds;
	  
	  @Autowired
	  private CallReportQueryService callReportQueryService;
	  
	  AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  
	  @Autowired
		public void init() {
			model = new OcrmFCiCallreportInfo();
			setCommonService(callReportQueryService);
		}
	  
	  public void prepare(){
		  ActionContext ctx = ActionContext.getContext();
	      request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	      String custId=request.getParameter("custId");
	      System.out.println("初始化查询"+custId);
		  StringBuffer sb=new StringBuffer();
		  Date now = new Date();
//			 sb.append("select c.id,to_char(c.CALLREPORT_INFO) CALLREPORT_INFO,c.CREATE_ORG,c.CREATE_TM,c.CREATE_USER,c.CUST_ID,to_char(c.REMIND_TM,'yyyy-MM-dd') as REMIND_TM,c.REMIND_TYPE,c.UPDATE_ORG,");
//			 sb.append("c.UPDATE_TM,c.UPDATE_USER from OCRM_F_CI_CALLREPORT_INFO c where to_char(c.CREATE_TM,'yyyyMMdd')!='"+DateUtils.formatDate(now, "yyyyMMdd")+"'");
		  	 sb.append("select c.id,to_char(c.CALLREPORT_INFO) CALLREPORT_INFO,c.CREATE_ORG,to_char(c.CREATE_TM,'yyyy-MM-dd hh24:mi:ss') as CREATE_TM,c.CREATE_USER,c.CREATE_USERNAME,c.CUST_ID,c.CUST_NAME,c.UPDATE_ORG,");
			 sb.append("c.UPDATE_TM,c.UPDATE_USER,c.UPDATE_USERNAME from OCRM_F_CI_CALLREPORT_INFO c where to_char(c.CREATE_TM,'yyyyMMdd')!='"+DateUtils.formatDate(now, "yyyyMMdd")+"'");
			 sb.append(" and c.CUST_ID='"+custId+"'");
			 sb.append(" order by c.CREATE_TM desc");
			 SQL=sb.toString();
			 datasource=ds;
	  }
	  /**
	   * 保存及修改callreport内容，保存提醒内容
	   * @throws Exception
	   */
	  public void saveInfo() throws Exception{
		  ActionContext ctx = ActionContext.getContext();
	      request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
	      Map parameterMap = new HashMap();
	      parameterMap.put("id", request.getParameter("id"));
	      parameterMap.put("custId", request.getParameter("custId"));
	      parameterMap.put("custName", request.getParameter("custName"));
	      parameterMap.put("remindDt", request.getParameter("remindDt"));
	      parameterMap.put("callreport", request.getParameter("callreport"));
	      parameterMap.put("remindType", request.getParameter("remindType"));
	      parameterMap.put("createTime", request.getParameter("createTime"));
	      parameterMap.put("createUsername", request.getParameter("createUsername"));
	      parameterMap.put("createUser", request.getParameter("createUser"));
	      parameterMap.put("createOrg", request.getParameter("createOrg"));
	      callReportQueryService.saveRemind((OcrmFCiCallreportInfo) model, parameterMap);
	  }
	  
	  public void serachToday() throws ParseException{
		  ActionContext ctx = ActionContext.getContext();
		  request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		  HttpServletResponse response = ServletActionContext.getResponse();
	      response.setContentType("text/html:charset=utf-8");
		  response.setCharacterEncoding("UTF-8");			
		  String custId=request.getParameter("custId");
		  System.out.println("今日查询"+custId);
		  StringBuffer sb=new StringBuffer();
		  Date today = new Date();
//		  sb.append("select c.id,c.CALLREPORT_INFO,c.CREATE_ORG,c.CREATE_TM,c.CREATE_USER,c.CUST_ID,to_char(c.REMIND_TM,'yyyy-MM-dd') as REMIND_TM,c.REMIND_TYPE,c.UPDATE_ORG,");
//		  sb.append("c.UPDATE_TM,c.UPDATE_USER from OCRM_F_CI_CALLREPORT_INFO c where to_char(c.CREATE_TM,'yyyyMMdd')='"+DateUtils.formatDate(today, "yyyyMMdd")+"'");
		  sb.append("select c.id,c.CALLREPORT_INFO,c.CREATE_ORG,c.CREATE_TM,c.CREATE_USER,c.CREATE_USERNAME,c.CUST_ID,c.CUST_NAME,c.UPDATE_ORG,");
		  sb.append("c.UPDATE_TM,c.UPDATE_USER,c.UPDATE_USERNAME from OCRM_F_CI_CALLREPORT_INFO c where to_char(c.CREATE_TM,'yyyyMMdd')='"+DateUtils.formatDate(today, "yyyyMMdd")+"'");
		  sb.append(" and c.CUST_ID='"+custId+"'");
		  QueryHelper query;
		  try {
			   query = new QueryHelper(sb.toString(), ds.getConnection());
			   List<HashMap<String, Object>>	tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			   Map<String,Object> resultMap =  new HashMap<String,Object>();
			   if(tempRowsList.size()>0){
				   resultMap.put("id", tempRowsList.get(0).get("ID"));
				   resultMap.put("createOrg", tempRowsList.get(0).get("CREATE_ORG"));
				   resultMap.put("createTime", tempRowsList.get(0).get("CREATE_TM"));
				   resultMap.put("createUser", tempRowsList.get(0).get("CREATE_USER"));
				   resultMap.put("createUsername", tempRowsList.get(0).get("CREATE_USERNAME"));
				   resultMap.put("callreport", tempRowsList.get(0).get("CALLREPORT_INFO"));
				   this.setJson(resultMap);
			   }
		  } catch (SQLException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		 
	  }
	  
	  public void serachHistory(){		  
		  ActionContext ctx = ActionContext.getContext();
		  request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		  HttpServletResponse response = ServletActionContext.getResponse();
	      response.setContentType("text/html:charset=utf-8");
		  response.setCharacterEncoding("UTF-8");
		  
		  StringBuffer sb=new StringBuffer();
		  String id = request.getParameter("id");
//		  sb.append("select c.id,c.CALLREPORT_INFO,c.CREATE_ORG,c.CREATE_TM,c.CREATE_USER,c.CUST_ID,to_char(c.REMIND_TM,'yyyy-MM-dd') as REMIND_TM,c.REMIND_TYPE,c.UPDATE_ORG,");
//		  sb.append("c.UPDATE_TM,c.UPDATE_USER from OCRM_F_CI_CALLREPORT_INFO c where c.id='"+id+"'");
		  sb.append("select c.id,c.CALLREPORT_INFO,c.CREATE_ORG,c.CREATE_TM,c.CREATE_USER,c.CREATE_USERNAME,c.CUST_ID,c.CUST_NAME,c.UPDATE_ORG,");
		  sb.append("c.UPDATE_TM,c.UPDATE_USER,c.UPDATE_USERNAME from OCRM_F_CI_CALLREPORT_INFO c where c.id='"+id+"'");
		  QueryHelper query;
		  try {
			   query = new QueryHelper(sb.toString(), ds.getConnection());
			   List<HashMap<String, Object>>	tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			   Map<String,Object> resultMap =  new HashMap<String,Object>();
			   resultMap.put("id", tempRowsList.get(0).get("ID"));
			   resultMap.put("createOrg", tempRowsList.get(0).get("CREATE_ORG"));
			   resultMap.put("createTime", tempRowsList.get(0).get("CREATE_TM"));
			   resultMap.put("createUser", tempRowsList.get(0).get("CREATE_USER"));
			   resultMap.put("createUserNAME", tempRowsList.get(0).get("CREATE_USERNAME"));
			   resultMap.put("callreport", tempRowsList.get(0).get("CALLREPORT_INFO"));
			   this.setJson(resultMap);
			
		  } catch (SQLException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	  }
}

package com.yuchengtech.bcrm.custview.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.custview.service.AcrmFCiContmethInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对私客户视图(联系信息)
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiContmethInfo")
public class AcrmFCiContmethInfoAction extends CommonAction{
	@Autowired
	private AcrmFCiContmethInfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiContmeth();
		setCommonService(service);
		needLog=true;
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
	    String check = request.getParameter("check");
	    String flag=request.getParameter("flag");
		StringBuilder sb = new StringBuilder();
		String custType=request.getParameter("custType");
		sb.append("SELECT T.CONTMETH_ID,T.CUST_ID,T.IS_PRIORI,T.CONTMETH_TYPE,TO_CHAR(T.LAST_UPDATE_TM, 'yyyy-MM-dd hh24:mi:ss') AS LAST_UPDATE_TMM,REPLACE(T.CONTMETH_INFO, '/', '-') CONTMETH_INFOO,T.CONTMETH_INFO,T.CONTMETH_SEQ,T.REMARK,T.STAT,T.LAST_UPDATE_SYS, " +
				" (case when a.user_name is not null then a.user_name else T.LAST_UPDATE_USER end ) as LAST_UPDATE_USER,T.TX_SEQ_NO,T.ETL_DATE " +
				" FROM ACRM_F_CI_CONTMETH t " +
				" left join admin_auth_account a on t.LAST_UPDATE_USER = a.account_name " +
				" where (t.CONTMETH_TYPE<>'501' AND T.CONTMETH_TYPE <> '503') ");
		if("callcenter".equals(flag)){
			sb = new StringBuilder();
			sb.append("SELECT T.CONTMETH_ID,T.CUST_ID,T.IS_PRIORI,T.CONTMETH_TYPE,TO_CHAR(T.LAST_UPDATE_TM, 'yyyy-MM-dd hh24:mi:ss') AS LAST_UPDATE_TMM,REPLACE(T.CONTMETH_INFO, '/', '-') CONTMETH_INFOO,T.CONTMETH_INFO,T.CONTMETH_SEQ,T.REMARK,T.STAT,T.LAST_UPDATE_SYS, " +
					" (case when a.user_name is not null then a.user_name else T.LAST_UPDATE_USER end ) as LAST_UPDATE_USER,T.TX_SEQ_NO,T.ETL_DATE " +
					" FROM ACRM_F_CI_CONTMETH t " +
					" left join admin_auth_account a on t.LAST_UPDATE_USER = a.account_name " +
					" where t.CONTMETH_TYPE in ('500','501','2031','2041','209','102') ");
		}
		if("1".equals(custType)){
			sb = new StringBuilder();
			sb.append("SELECT T.CONTMETH_ID,T.CUST_ID,T.IS_PRIORI,T.CONTMETH_TYPE,TO_CHAR(T.LAST_UPDATE_TM, 'yyyy-MM-dd hh24:mi:ss') AS LAST_UPDATE_TMM,REPLACE(T.CONTMETH_INFO, '/', '-') CONTMETH_INFOO,T.CONTMETH_INFO,T.CONTMETH_SEQ,T.REMARK,T.STAT,T.LAST_UPDATE_SYS, " +
					" (case when a.user_name is not null then a.user_name else T.LAST_UPDATE_USER end ) as LAST_UPDATE_USER,T.TX_SEQ_NO,T.ETL_DATE " +
					" FROM ACRM_F_CI_CONTMETH t " +
					" left join admin_auth_account a on t.LAST_UPDATE_USER = a.account_name " +
					" where ( T.CONTMETH_TYPE <> '503') ");
		}
		if(customerId != null){
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		if("1".equals(check)){
			sb.append(" and t.stat = '1' ");//表示未删除
		}
		if("0".equals(check)){
			sb.append(" and t.stat = '0' ");//表示已删除
		}
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("CONTMETH_TYPE".equals(key)){
					sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
				}
		  		if("IS_PRIORI".equals(key)){
		  			sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
		  		}
			}
		}
		SQL = sb.toString();
	    setPrimaryKey(" T.CONTMETH_TYPE,T.IS_PRIORI,T.CONTMETH_ID DESC ");
		datasource = ds;
		if("callcenter".equals(flag)){
			addOracleLookup("CONTMETH_TYPE", "XD000193_CALLCENTER");
		}else{
			addOracleLookup("CONTMETH_TYPE", "XD000193");
		}
		addOracleLookup("IS_PRIORI", "XD000332");
		
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr =request.getParameter("messageId");
//			request.getRemoteAddr();
			String jql = "update  AcrmFCiContmeth c set c.stat = '0' where c.contmethId in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
	// 刪除2方
	public void batchDestroy2(String messageId) {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String idStr =messageId;
//		request.getRemoteAddr();
		String jql = "update  AcrmFCiContmeth c set c.stat = '0' where c.contmethId in ("
				+ idStr + ")";
		Map<String, Object> values = new HashMap<String, Object>();
		service.batchUpdateByName(jql, values);
		addActionMessage("batch removed successfully");
//		return "success";
	}
	
	//设置首选项 1是 0 否
	public String setPreference(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr =request.getParameter("messageId");
		AcrmFCiContmeth thList = (AcrmFCiContmeth)service.find(idStr);
		service.setPreference(thList,idStr);
		addActionMessage("batch removed successfully");
		return "success";
	}
	
	/**
	 * 发起审批工作流(对私视图联系信息)==修改
	 */
	public void initFlowCI() throws Exception{
		 ActionContext ctx = ActionContext.getContext();
	   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   	 String custId = request.getParameter("custId");
	   	 String s1 = request.getParameter("perModel");
	   	 String contMethId = request.getParameter("contMethId");
	   	 JSONArray jarray = JSONArray.fromObject(s1);
	   	 Date date = new Date();
	   	 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	   	 String p = sdf.format(date);
	   	 service.bathsave(jarray,date,p);
	   	 
	   	 String instanceid = "MU"+"_"+custId+"_"+p+"_"+"X"+"_"+contMethId;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		 String jobName = "对私联系信息修改_"+custId;//自定义流程名称
		 
		 //验证是否已经提交审核
		 int i = service.judgeSec(jobName, "MU"+"_"+custId, "X");
		 if(i>0){
			 throw new BizException(1, 0, "1002", "正在审核中,请勿重复提交...");
		 }
			
		 //以下方法调用流程引擎..
		 service.initWorkflowByWfidAndInstanceid("81", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		 String nextNode = "81_a4";
		 List list = auth.getRolesInfo();
		 for(Object m:list){
				Map map = (Map)m;//map自m引自list，ROLE_CODE为键, R000为值
				if("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE")) ){//ARMRM个金法金
					nextNode = "81_a4";
					continue ;
				}else if("R300".equals(map.get("ROLE_CODE"))){//OP经办
					nextNode = "81_a5";
					continue ;
				}else if(
						"R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))|| 
						"R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){
					nextNode = "81_a7";
					continue ;
				}
		 }	
	     Map<String,Object> map1=new HashMap<String,Object>();
		 map1.put("instanceid", instanceid);
	     map1.put("currNode", "81_a3");
	     map1.put("nextNode",  nextNode);
	     this.setJson(map1);
	}
	/**
	 * 发起审批工作流2--对私视图联系信息==删除
	 */
	public void initFlowDI() throws Exception{
		 ActionContext ctx = ActionContext.getContext();
	   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   	 
	   	 String custId = request.getParameter("custId");
	   	 String messageId = request.getParameter("messageId");
	   	 
	   	 Date date = new Date();
	   	 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	   	 String p = sdf.format(date);
	   	 
	   	 
	   	 String instanceid = "CI_"+custId+"_"+p+"_"+"D";//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		 String jobName = "对私联系信息删除_"+custId;//自定义流程名称
		 
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 String nextNode = "81_a4";
		 List list = auth.getRolesInfo();
		 for(Object m:list){
			Map map = (Map)m;//map自m引自list，ROLE_CODE为键, R000为值
			paramMap.put("role", map.get("ROLE_CODE"));
			if("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE"))){//ARMRM个金法金
				nextNode = "81_a4";
				continue ;
			}else if("R300".equals(map.get("ROLE_CODE"))){//OP经办
				nextNode = "81_a5";
				continue ;
			}else if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))
					|| "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){
				nextNode = "81_a7";
				continue ;
			}
		 }	
		//以下方法调用流程引擎..
		 service.bathsave2(messageId,custId,date,p);
		 service.initWorkflowByWfidAndInstanceid("81", jobName, paramMap, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		 
		 Map<String,Object> map1=new HashMap<String,Object>();
		 map1.put("instanceid", instanceid);
	     map1.put("currNode", "81_a3");
	     map1.put("nextNode",  nextNode);
 	     this.setJson(map1);
	}
	/**
	 * 发起审批工作流(对私视图联系信息)==新增
	 */
	public void initFlowZI() throws Exception{
		 ActionContext ctx = ActionContext.getContext();
	   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   	 String custId = request.getParameter("custId");
	   	 String s1 = request.getParameter("perModel");
	   	 JSONArray jarray = JSONArray.fromObject(s1);
	   	 Date date = new Date();
	   	 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	   	 String p = sdf.format(date);
	   	 service.bathsave(jarray,date,p);
	   	 
	   	 String instanceid = "CI_"+custId+"_"+p+"_"+"Z";//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		 String jobName = "对私联系信息新增_"+custId;//自定义流程名称
		 
		//验证是否已经提交审核
		 int i = service.judgeSec(jobName, "CI_"+custId, "Z");
		 if(i>0){
			 throw new BizException(1, 0, "1002", "正在审核中,请勿重复提交...");
		 }
		 
		 //以下方法调用流程引擎..
		 service.initWorkflowByWfidAndInstanceid("81", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		 String nextNode = "81_a4";
		 List list = auth.getRolesInfo();
		 for(Object m:list){
				Map map = (Map)m;//map自m引自list，ROLE_CODE为键, R000为值
				if("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE")) || "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){//ARMRM个金法金
					nextNode = "81_a4";
					continue ;
				}else if("R300".equals(map.get("ROLE_CODE"))){//OP经办
					nextNode = "81_a5";
					continue ;
				}else{
					continue ;
				}
		 }	
	     Map<String,Object> map1=new HashMap<String,Object>();
		 map1.put("instanceid", instanceid);
	     map1.put("currNode", "81_a3");
	     map1.put("nextNode",  nextNode);
	     this.setJson(map1);
	}
}

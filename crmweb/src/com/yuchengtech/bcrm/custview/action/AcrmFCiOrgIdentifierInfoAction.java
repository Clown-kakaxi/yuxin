package com.yuchengtech.bcrm.custview.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.custview.service.AcrmFCiOrgIdentifierInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * 对公客户视图证件信息
 * @author agile
 *
 */
@Action("/acrmFCiOrgIdentifierInfo")
public class AcrmFCiOrgIdentifierInfoAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private AcrmFCiOrgIdentifierInfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiCustIdentifier();//AcrmFCiOrgIdentifier();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");

		StringBuilder sb = new StringBuilder();
		sb.append("select S.USERNAME AS LAST_UPDATE_USER_NAME ,to_char(t.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TMM ," +
				" to_char(t.IDENT_MODIFIED_TIME,'yyyy-MM-dd hh24:mi:ss') as IDENT_MODIFIED_TIMEE , " +
				" t.* from ACRM_F_CI_CUST_IDENTIFIER t " +
				" LEFT JOIN SYS_USERS S ON T.LAST_UPDATE_USER = S.USERID " +
				" where 1=1 ");
		if(customerId != null){
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		SQL = sb.toString();
		setPrimaryKey(" t.IDENT_ID desc");
		addOracleLookup("IDENT_TYPE", "COM_CRET_TYPE");
		addOracleLookup("IDENT_ORG", "PAR1300083");
		addOracleLookup("IDENT_VALID_FLAG", "XD000142");
//		addOracleLookup("IDENT_ORG", "IF_FLAG");
//		addOracleLookup("IDENT_ORG", "IF_FLAG");
//		addOracleLookup("IDENT_ORG", "IF_FLAG");
//		addOracleLookup("IDENT_ORG", "IF_FLAG");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("messageId");
		String jql = "delete from AcrmFCiCustIdentifier c where c.identId in ("+ idStr + ")";
		Map<String, Object> values = new HashMap<String, Object>();
		service.batchUpdateByName(jql, values);
		addActionMessage("batch removed successfully");
		return "success";
	}
	
	/**
	 * 对公客户证件信息新增时发起审批工作流
	 * @throws Exception 
	 * @throws IOException
	 */
	public String save() throws Exception{
		try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	        AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        
	        String custId = request.getParameter("custId");
			String s1 = request.getParameter("perModel");
		    JSONArray jarray = JSONArray.fromObject(s1);
		    Date date = new Date();
		    String flag = String.valueOf(new Date().getTime());// 修改标识更改为毫秒级
		    String instanceid = "CI_"+custId+"_"+flag+"_save";// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			String jobName = "对公客户证件信息新增_"+custId;// 自定义流程名称
			//验证是否已经提交审核
			 int i = service.judgeSec(jobName, "CI_"+custId, "save");
			 if(i>0){
				 throw new BizException(1, 0, "1002", "正在审核中,请勿重复提交...");
			 }
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String nextNode = "";
			List<?> list = auth.getRolesInfo();
			for (Object m : list) {
				Map<?, ?> map = (Map<?, ?>) m;
				paramMap.put("role", map.get("ROLE_CODE"));
				if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
					nextNode = "75_a5";
					continue;
				} else if ("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE"))) {// 个法金ARM,RM
					nextNode = "75_a4";
					continue;
				} else if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))
						|| "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){
					nextNode = "75_a7";
					continue;
				}
			}
			service.bathsave(jarray,date,flag);
			service.initWorkflowByWfidAndInstanceid("75", jobName, paramMap, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			
			response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"75_a3\",\"nextNode\":\""+nextNode+"\"}");
			response.getWriter().flush();
    	}catch(Exception e){
    		e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
    	}
        return "success";
	}
	
	/**
	 * 对公客户证件信息审批流程修改时的提交审批函数
	 * 发起工作流
	 * */
	public String initFlow() throws Exception{
		try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	        AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        
	        String custId=request.getParameter("custId");
			String s1 = request.getParameter("perModel");
		    JSONArray jarray = JSONArray.fromObject(s1);
		    Date date=new Date();
		    String flag = String.valueOf(new Date().getTime());// 修改标识更改为毫秒级
		    service.bathsave(jarray,date,flag);
		    
		    String instanceid = "ZJ"+"_"+custId+"_"+flag+"_modify";// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			String jobName = "对公客户证件信息修改_" + custId;// 自定义流程名称
			
			//验证是否已经提交审核
			 int i = service.judgeSec(jobName, "ZJ"+"_"+custId, "modify");
			 if(i>0){
				 throw new BizException(1, 0, "1002", "正在审核中,请勿重复提交...");
			 }
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String nextNode = "";
			List<?> list = auth.getRolesInfo();
			for (Object m : list) {
				Map<?, ?> map = (Map<?, ?>) m;
				paramMap.put("role", map.get("ROLE_CODE"));
				if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
					nextNode = "75_a5";
					continue;
				} else if ("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE"))) {// 个法金ARM,RM
					nextNode = "75_a4";
					continue;
				} else if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))
						|| "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){
					nextNode = "75_a7";
					continue;
					
				}
			}
			service.initWorkflowByWfidAndInstanceid("75", jobName, paramMap, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"75_a3\",\"nextNode\":\""+nextNode+"\"}");
			response.getWriter().flush();
    	}catch(Exception e){
    		e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
    	}
        return "success";
	}
	
	//对公客户证件信息删除流程审批
	public String delete() throws Exception{
		try {
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	        AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        
	        String custId=request.getParameter("custId");
	        String s1 = request.getParameter("perModel");
		    JSONArray jarray = JSONArray.fromObject(s1);
		    Date date=new Date();
		    String flag = String.valueOf(new Date().getTime());// 修改标识更改为毫秒级
		    service.bathsave(jarray,date,flag);
		    
		    String instanceid = "CI"+"_"+custId+"_"+flag+"_delete";// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			String jobName = "对公客户证件信息删除_" + custId;// 自定义流程名称
			service.initWorkflowByWfidAndInstanceid("75", jobName, null, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			String nextNode = "";
			List<?> list = auth.getRolesInfo();
			for (Object m : list) {
				Map<?, ?> map = (Map<?, ?>) m;
				if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
					nextNode = "75_a5";
					continue;
				} else if ("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE")) || "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))) {// 个法金ARM,RM
					nextNode = "75_a4";
					continue;
				} 
			}
			response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"75_a3\",\"nextNode\":\""+nextNode+"\"}");
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
        return "success";
	}
}



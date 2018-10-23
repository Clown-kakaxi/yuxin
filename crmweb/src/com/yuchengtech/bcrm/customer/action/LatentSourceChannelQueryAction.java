package com.yuchengtech.bcrm.customer.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.OcrmSysLookupItemtemp;
import com.yuchengtech.bcrm.customer.service.LatentSourceChannelService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.model.LookupMappingItem;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/latentSourceChannelQueryAction", results = { @Result(name = "success", type = "json")})
/**
 * 个金潜在客户来源渠道维护
 * 2016-01-28
 * @author mamusa
 *
 */
public class LatentSourceChannelQueryAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private LatentSourceChannelService latentSourceChannelService;
	
	@Autowired
	public void init() {
		model = new LookupMappingItem();
		setCommonService(latentSourceChannelService);
	}
	
	
    public void prepare() {
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);   
        StringBuilder sb = new StringBuilder("select F_ID,F_VALUE,F_CODE,F_COMMENT,F_LOOKUP_ID  from ocrm_sys_lookup_item where 1>0 and F_LOOKUP_ID='XD000353'");
        
        if(!"".equals(request.getParameter("Id"))&&request.getParameter("Id")!=null){
			sb.append(" and F_ID='"+request.getParameter("Id")+"' ");
		}
        setPrimaryKey(" F_CODE desc ");
        
        SQL=sb.toString();
        datasource = ds;
    }
    
    
    public DefaultHttpHeaders saveData()  throws Exception{
		try{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		/*String codedate = request.getParameter("code");
		String codeorg = request.getParameter("codeF");
		int largestNumber = Integer.parseInt(request.getParameter("largestNumber")==null?"":request.getParameter("largestNumber"));*/
		String largestNumberV = request.getParameter("largestNumberV");
		LookupMappingItem lmitemp=(LookupMappingItem)model;
		//lmitemp.setCode(""+codedate.replaceAll("-", "")+codeorg+largestNumber);
		lmitemp.setCode(largestNumberV);
		String temps=lmitemp.getValue();
		lmitemp.setValue(lmitemp.getCode()+"-"+temps);
		LookupMappingItem lmitow=(LookupMappingItem) latentSourceChannelService.save(lmitemp);
		OcrmSysLookupItemtemp looktemp=new OcrmSysLookupItemtemp();
		looktemp.setId(lmitow.getId());
		looktemp.setCode(lmitow.getCode());
		looktemp.setValue(lmitow.getValue());
		looktemp.setComment(lmitow.getComment());
		looktemp.setLookup(lmitow.getLookup());
		latentSourceChannelService.save(looktemp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new DefaultHttpHeaders("success");
	}
    
    public void saveDateCommit(){
    	try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
			LookupMappingItem lmi=(LookupMappingItem)model;
			//LookupMappingItem lmitow=(LookupMappingItem) latentSourceChannelService.save(lmi);
			 Date date = new Date();
			 //SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			// String p = sdf.format(date);
			 String instanceid = "SOURCE"+"_"+lmi.getCode()+"_"+lmi.getValue()+"_"+lmi.getComment();//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			 String jobName = "个金潜在客户来源渠道复核_"+lmi.getValue();//自定义流程名称
			 latentSourceChannelService.initWorkflowByWfidAndInstanceid("134", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			 String nextNode = "134_a4";
			 response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"134_a3\",\"nextNode\":\""+nextNode+"\"}");
			 response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	

	public String destroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		Long idstr=null;
			if(!"".equals(request.getParameter("id"))&&request.getParameter("id")!=null){
				idstr=Long.parseLong(request.getParameter("id"));
			}
			latentSourceChannelService.remove(idstr);
			String sql=" delete from ocrm_sys_lookup_itemtemp where f_id='"+idstr+"'";
			deleteTempSourceChannel(sql);
		return idstr.toString();
	}
	
    public void deleteTempSourceChannel(String sql){
    	Connection  connection=null;
   		Statement stmt=null;
    	try{
    		 connection = ds.getConnection();
		     stmt = connection.createStatement();
    	     stmt.executeUpdate(sql);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
	   		JdbcUtil.close(null, stmt, connection);
	   		
	   	}
    }
	/**
  	 * 来源审批待办界面查询
  	 * @return
  	 */
  	public HttpHeaders querySourceChannelById() {
   		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String fId = request.getParameter("fId");
   			StringBuilder sb = new StringBuilder();
   			sb.append(" select F_ID,F_VALUE,F_CODE,F_COMMENT,F_LOOKUP_ID  from ocrm_sys_lookup_itemtemp where F_LOOKUP_ID='XD000353' ");
   			sb.append(" and F_ID='"+fId+"'");
   			QueryHelper queryHelper = new QueryHelper(sb.toString(), ds.getConnection());
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
  	
  	
  	public void getlargestNumberByorgid() {
   		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String orgId = request.getParameter("orgId");
   			StringBuilder sb = new StringBuilder(); 
   			sb.append("  select case when  max(substr(F_CODE, 12)) is null then 0 else TO_NUMBER(max(substr(F_CODE, 12)))  end  larges  from ocrm_sys_lookup_item where substr(F_CODE, 9, 3) = '"+orgId+"'  and F_LOOKUP_ID='XD000353' ");
   		  Connection  connection=null;
    		Statement stmt=null;
    		ResultSet result=null;
    		String larges="";
     	try{
     		  connection = ds.getConnection();
  		 stmt = connection.createStatement();
     		 result = stmt.executeQuery(sb.toString());
     		while(result.next()){
     			larges=result.getString("larges");
     		}	
     	}catch(Exception e){
     		e.printStackTrace();
     	}finally{
     		JdbcUtil.close(result, stmt, connection);
     	}
     	
     	 Map<String,Object> maps = new HashMap<String, Object>();
 		maps.put("larges", larges);
 		this.setJson(maps);
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   	}
  	
}

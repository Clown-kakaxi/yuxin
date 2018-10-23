package com.yuchengtech.bcrm.custview.action;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.yuchengtech.bcrm.custview.model.AcrmFCiAccountInfo;
import com.yuchengtech.bcrm.custview.service.AcrmFCiAccountInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对私客户视图 开通账户信息
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiAccountInfo")
public class AcrmFCiAccountInfoAction extends CommonAction{
	@Autowired
	private AcrmFCiAccountInfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiAccountInfo();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
	    String old = request.getParameter("old");//复核流程数据查询
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ACRM_F_CI_ACCOUNT_INFO  o where 1=1 ");
		if(customerId != null){
			sb.append(" and o.cust_id = '"+customerId+"'");
		}
		
		if("1".equals(old)){
			sb.setLength(0);
			sb.append("select o.*,i.ACCOUNT_CONTENTS as ACCOUNT_CONTENTS_OLD,i.IS_DOMESTIC_CUST as IS_DOMESTIC_CUST_old from ACRM_F_CI_ACCOUNT_INFO_temp  o" +
					" left join ACRM_F_CI_ACCOUNT_INFO i on o.cust_id =i.cust_id  where 1=1 and o.cust_id = '"+customerId+"'");
		}
		SQL = sb.toString();
		datasource = ds;
	}	
	
	/**
	 * 发起审批工作流
	 */
	public void initFlow(){
		try {
			 ActionContext ctx = ActionContext.getContext();
		   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   	 String custId = request.getParameter("custId");
		   	 String jn = request.getParameter("jn");
			 String jw = request.getParameter("jw");
			 String accountContent="";
		     String[]  accountContents  = request.getParameterValues("accountContents");
			 if(accountContents != null ){
				for(String  a :  accountContents){
					accountContent += ","+a;
				}
			 }
			 AcrmFCiAccountInfo info = (AcrmFCiAccountInfo)service.find(custId);	
		     if(info!=null && "1".equals(info.getState())){//0暂存，1审核，2审核结束
		    	 throw new BizException(1, 0, "1002", "正在审核中,不能重复审核...");
		     }
		     //保存开通账户信息临时表
		     service.saveTemp(accountContent.substring(1),custId,jn,jw);
		     //设置复核状态
		     if(info!=null){
		    	 service.batchUpdateByName("update AcrmFCiAccountInfo o set o.state = '1' where o.custId = '"+info.getCustId()+"'", null);
		     }
		     
		   	 Date date = new Date();
		   	 SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		   	 String p = sdf.format(date);
		   	 String instanceid = "KTZH"+"_"+custId+"_"+p;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			 String jobName = "开通账户信息复核_"+custId;//自定义流程名称
			 //以下方法调用流程引擎..
			 service.initWorkflowByWfidAndInstanceid("86", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			 String nextNode = "86_a5";
		     Map<String,Object> map1=new HashMap<String,Object>();
			 map1.put("instanceid", instanceid);
		     map1.put("currNode", "86_a3");
		     map1.put("nextNode",  nextNode);
		     this.setJson(map1);
		} catch (Exception e) {
			throw new BizException(0, 1, "1002",e.getMessage());
		}
	}
}

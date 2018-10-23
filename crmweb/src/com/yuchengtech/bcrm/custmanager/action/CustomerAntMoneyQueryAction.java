package com.yuchengtech.bcrm.custmanager.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.service.CustomerAntMoneyService;
import com.yuchengtech.bcrm.sales.model.OcrmFMkMktActivity;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
 * @ClassName: CustomerAntMoneyQueryAction
 * @Description: 客户反洗钱查询Action
 *    客户查询去除数据权限的代码控制，改为由后台数据过滤器配置 by :sujm 20140821
 * @author luyy
 * @date 2014-7-17  
 *
 */
@SuppressWarnings("serial")
@Action("customerAntMoneyQuery")
public class CustomerAntMoneyQueryAction extends CommonAction {

    @Autowired
	private CustomerAntMoneyService customerAntMoneyService;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
    @Autowired
	public void init(){
	  	model = new OcrmFMkMktActivity(); 
		setCommonService(customerAntMoneyService);
	}
	
	public void prepare(){

		StringBuffer sb=new StringBuffer("SELECT DISTINCT C.CUST_ID,C.CUST_NAME,C.CORE_NO,C.IDENT_TYPE,C.IDENT_NO,C.CUST_TYPE,C.CUST_LEVEL,C.CURRENT_AUM,C.TOTAL_DEBT,C.RISK_LEVEL," +
			" linkman.linkman_name, linkman.mobile LINKMAN_TEL, O.org_biz_cust_type as BELONG_LINE_NO,C.CUST_STAT,M.INSTITUTION_NAME AS ORG_NAME,M.MGR_NAME,GD.CUST_GRADE_TYPE, " +
			" C.CREDIT_LEVEL,C.FAXTRADE_NOREC_NUM,T.BELONG_TEAM_HEAD_NAME,L.BL_NAME,GD.CUST_GRADE as FXQ_RISK_LEVEL,GD.LAST_UPDATE_USER,GD.CUST_GRADE_ID," +
			" fc.FLAG " +
			" FROM ACRM_F_CI_CUSTOMER C LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID " +
			" LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T ON M.MGR_ID = T.CUST_MANAGER_ID " +
			" left join acrm_f_ci_org o on o.CUST_ID = C.CUST_ID " +
			" LEFT JOIN ACRM_F_CI_BUSI_LINE L ON o.org_biz_cust_type = to_char(L.BL_NO) " +
			" left join acrm_a_fact_fxq_customer fc on fc.CUST_ID = C.CUST_ID " +//判断客户是否是新老客户
			" left join ( select exe.org_cust_id cust_id,exe.linkman_name,exe.mobile  from   ACRM_F_CI_ORG_EXECUTIVEINFO exe where exe.linkman_type='21' "+
			" union all " +
			" select plink.cust_id,plink.linkman_name,plink.mobile from  ACRM_F_CI_PER_LINKMAN plink where plink.linkman_type='21') linkman on linkman.cust_id=c.cust_id "+
			" LEFT JOIN (SELECT ee.CUST_ID, ee.CUST_GRADE_TYPE,ee.CUST_GRADE,ee.CUST_GRADE_ID," +
			" 	(case when ee.LAST_UPDATE_USER <> 'ETL' then tt.user_name else ee.LAST_UPDATE_USER end ) as LAST_UPDATE_USER FROM ACRM_F_CI_GRADE ee " +
			" 	left join admin_auth_account tt on tt.account_name = ee.LAST_UPDATE_USER " +
			"   WHERE CUST_GRADE_TYPE = '01') GD ON GD.CUST_ID = C.CUST_ID " +
			" left join ACRM_F_CI_PERSON n on c.cust_id = n.cust_id" +
			" WHERE 1=1 ");
		
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if("ACCT_NO".equals(key)){
					sb.append(" and c.cust_id in (select cust_id from ACRM_F_CI_LOAN_ACT where ACCOUNT like '"+this.json.get(key)+"%')");
				}
				if("ORG_NAME".equals(key)){ 
					sb.append(" and (m.INSTITUTION in (SELECT unitid FROM SYS_UNITS  WHERE UNITSEQ LIKE '%"+this.json.get(key)+"%'))");
				}
				if("BL_NAME".equals(key)&&!"归属业务条线".equals(this.json.get(key))){
					sb.append(" and  (o.org_biz_cust_type in (select distinct bl_ID from ACRM_F_CI_BUSI_LINE t START   WITH bl_ID='"+this.json.get(key)+"' CONNECT BY PRIOR BL_ID=PARENT_ID))");
				}
			}
		}
		//添加证件类型与证件号码查询,取证件表上的数据进行查询
		if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))
			&& null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"' AND I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
		}else if(null!=this.getJson().get("IDENT_TYPE")&&!"".equals(this.getJson().get("IDENT_TYPE"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"+this.json.get("IDENT_TYPE")+"')");
		}else if(null!=this.getJson().get("IDENT_NO")&&!"".equals(this.getJson().get("IDENT_NO"))){
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_NO like '%"+this.json.get("IDENT_NO")+"%')");
		}
			
		SQL=sb.toString();
		datasource=ds;
		
		setPrimaryKey("c.CUST_ID desc ");
		configCondition("c.CUST_ID","like","CUST_ID",DataType.String);
		configCondition("c.CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("c.CUST_TYPE","=","CUST_TYPE",DataType.String);
		configCondition("c.CUST_STAT","=","CUST_STAT",DataType.String);
		configCondition("c.LINKMAN_NAME","like","LINKMAN_NAME",DataType.String);
		configCondition("c.LINKMAN_TEL","like","LINKMAN_TEL",DataType.String);
		configCondition("c.CUST_LEVEL","=","CUST_LEVEL",DataType.String);
		configCondition("c.RISK_LEVEL","=","RISK_LEVEL",DataType.String);
		configCondition("c.CREDIT_LEVEL","=","CREDIT_LEVEL",DataType.String);
		configCondition("c.TOTAL_DEBT",">=","TOTAL_DEBT",DataType.Number);
		configCondition("m.MGR_NAME","=","MGR_NAME",DataType.String);
		configCondition("c.FAXTRADE_NOREC_NUM","=","FAXTRADE_NOREC_NUM",DataType.Number);
		configCondition("c.CURRENT_AUM",">=","CURRENT_AUM",DataType.Number);
		configCondition("t.BELONG_TEAM_HEAD","=","MGR_ID1",DataType.String);
		configCondition("t.BELONG_TEAM_HEAD_NAME","=","BELONG_TEAM_HEAD_NAME",DataType.String);
		configCondition("C.CORE_NO","=","CORE_NO",DataType.String);
		configCondition("GD.CUST_GRADE","=","FXQ_RISK_LEVEL",DataType.String);
	}
	
    /**
   	 * 发起工作流
   	 * */
   	@SuppressWarnings("rawtypes")
	public void applySave() throws Exception{
   	  	ActionContext ctx = ActionContext.getContext();
   		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
   		String requestId =  request.getParameter("instanceid");
   		String name =  request.getParameter("name");
   		
   		String jobName = "反洗钱风险等级审核_"+name;//自定义流程名称
   		int times = 0;
   		
   		//list办理中流程，list2已办结流程
   		List list = customerAntMoneyService.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORD where instanceid like 'ANTMONEY_"+requestId+"%'");
   		List list2 = customerAntMoneyService.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORDEND where instanceid like 'ANTMONEY_"+requestId+"%'");
   		if(list!= null && list.size() > 0){
   			//如果有办理中流程，不让再提交
   	    	response.getWriter().write("existTask");
   	    	response.getWriter().flush();
   		}else{
   			if(list2!= null && list2.size() > 0){
   				customerAntMoneyService.bathsave(request);
   				
   	   			times = list.size();
   	    		//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
   	    		String instanceid = "ANTI_"+requestId+"_"+times;
   	    	    //调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
   	    		customerAntMoneyService.initWorkflowByWfidAndInstanceid("125", jobName, null, instanceid);
   	    		response.getWriter().write(instanceid);
   	    		response.getWriter().flush();
   			}
   		}
   		
   	}
	
}

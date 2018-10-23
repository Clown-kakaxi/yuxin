package com.yuchengtech.bcrm.customer.customerMktTeam.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerMktTeam.model.OcrmFCmTeamCustManager;
import com.yuchengtech.bcrm.customer.customerMktTeam.service.CustomerMktTeamMembersService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description:客户经理团队管理成员信息查询数据源
 * @author xiebz
 * @data 2014-07-02
 */
@ParentPackage("json-default")
@Action("/customerMktTeamMembers")
public class CustomerMktTeamMembersAction extends CommonAction{
	private static final long serialVersionUID = -1307317536382455940L;

	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private CustomerMktTeamMembersService service;
    
    @Autowired
    public void init(){
        model = new OcrmFCmTeamCustManager();
        setCommonService(service);
    }
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mktTeamId = request.getParameter("mktTeamId");
		StringBuilder sb = new StringBuilder("SELECT AT.USER_NAME AS APPROVERS,T.*,nvl(R1.TASKNUM,0) AS TASKNUM "
				+ " FROM OCRM_F_CM_TEAM_CUST_MANAGER T " +
				" LEFT JOIN ADMIN_AUTH_ACCOUNT AT ON AT.ACCOUNT_NAME = T.APPROVER " +
				" left join OCRM_F_CM_MKT_TEAM tm on tm.mkt_team_id = t.mkt_team_id "
				+ " left join (select T3.OPER_OBJ_ID, t4.oper_obj_id as oper_obj_id_per, count(1) as TASKNUM from OCRM_F_MM_TASK t3 "
				+ " left join OCRM_F_MM_TASK t4 on t3.task_parent_id=t4.task_id "
				+ " where t3.task_stat <> '4' group by T3.OPER_OBJ_ID, t4.oper_obj_id) R1 on R1.OPER_OBJ_ID=T.CUST_MANAGER_ID    AND to_char(T.MKT_TEAM_ID) = R1.oper_obj_id_per " +
				" where 1=1 and (tm.team_leader_id = '"+auth.getUserId()+"' OR (tm.team_leader_id <> '"+auth.getUserId()+"' and t.cust_manager_state ='2')) " +
				" and T.MKT_TEAM_ID = ").append(mktTeamId);
		
		String teamLeaderId = request.getParameter("teamLeaderId");
		//0.待加入 ，1 待删除  ，2  生效 ，3失效
		if(teamLeaderId != null ){
			if(!teamLeaderId.equals(auth.getUserId())){
				sb.append(" and  t.CUST_MANAGER_STATE IN ('2','3') ");
			}
		}
		
		String operate = request.getParameter("operate");
    	String id=request.getParameter("id");
    	String flag=request.getParameter("flag");
    	if("temp".equals(operate)){
    		sb.setLength(0);
    		sb.append(" select T.CUST_MANAGER_NAME,g1.org_name as CUST_MANAGER_ORG,T.JOIN_DATE,T.TEAM_NAME,g2.org_name as BELONG_ORG,T.TEAM_TYPE from  OCRM_F_CM_TEAM_CUST_MANAGER t " +
    		 	" left  join ADMIN_AUTH_ORG g1 on T.CUST_MANAGER_ORG = g1.org_id " +
    		 	" left  join ADMIN_AUTH_ORG g2 on T.BELONG_ORG = g2.org_id " +
    		 	" where t.mkt_team_id = '"+id+"' ");
    		if("0".equals(flag)){//团队成员待加入查询
    			sb.append(" and t.cust_manager_state = '0' ");
    		}
    		if("1".equals(flag)){//团队成员待删除查询
    			sb.append(" and t.cust_manager_state = '1' ");
    		}
    	}
		
		setPrimaryKey("t.ID desc");
		
		addOracleLookup("CUST_MANAGER_STATE", "TEAM_CUSTMANAGER_STATUS");
		
	    SQL = sb.toString();
	    datasource = ds;
	}
	
	 /**
     * 删除 团队成员审批
     */
    public void batchDes()throws Exception{
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("idStr");
        String requestId =  request.getParameter("instanceid");//团队ID
		String name =  request.getParameter("name");//团队Name
		service.batchRemove(ids);
//		String instanceid = "CMTP_"+requestId+"_1_"+new SimpleDateFormat("MMddHHmm").format(new Date());//_0标示 是删除审批还是新增审批 0 删除，1新增
//		String jobName = "客户经理团队删除成员复核_"+name;//自定义流程名称
//		service.initWorkflowByWfidAndInstanceid("34", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
//		
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("instanceid", instanceid);
//	    map.put("currNode", "34_a3");
//	    map.put("nextNode",  "34_a4");
//	    this.setJson(map);
    }
    /**
	 * 客户经理团队成员复核流程 
	 * 发起工作流
	 * */
	public void initFlow() throws Exception{
	  	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String requestId =  request.getParameter("instanceid");//团队ID
		String name =  request.getParameter("name");//团队Name
		
		String instanceid = "CMTP_"+requestId+"_0_"+new SimpleDateFormat("MMddHHmm").format(new Date());//_0标示 是删除审批还是新增审批 0 删除，1新增
		String jobName = "客户经理团队成员复核_"+name;//自定义流程名称
		service.initWorkflowByWfidAndInstanceid("34", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceid", instanceid);
	    map.put("currNode", "34_a3");
	    map.put("nextNode",  "34_a4");
	    this.setJson(map);
	}
}

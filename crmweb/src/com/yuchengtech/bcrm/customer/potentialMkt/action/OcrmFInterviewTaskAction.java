package com.yuchengtech.bcrm.customer.potentialMkt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewTask;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFInterviewTaskService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 企商金客户营销流程 -  拜访信息页面  
 */

@SuppressWarnings("serial")
@Action("/ocrmFInterviewTask")
public class OcrmFInterviewTaskAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFInterviewTaskService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFInterviewTask();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlNew = "select c.id,"+
               "c.cust_id,"+
               "c.cust_name,"+
               "c.mgr_id,"+
               "c.mgr_name,"+
               "c.task_type,"+
               "to_date(to_char(c.visit_time,'yyyy/mm/dd'),'yyyy/mm/dd') as visit_time,"+
               "c.VISIT_START_TIME,"+
	           "c.VISIT_END_TIME,"+
               "c.create_time,"+
               "c.remark,"+
               "c.review_state,"+
               "c.visit_type,"+
               "c.task_number," +
               "c.new_record_id, " +
               "c.flag," +
    			"a.interviewee_name," +
    			" a.interviewee_post," +
    			" a.interviewee_phone," +
    			" a.join_person,   " +
    			" a.join_person_id,   " +
    			" to_date(to_char(a.call_time,'yyyy/mm/dd'),'yyyy/mm/dd') as call_time, " +
    			//" cast(a.call_time as DATE) call_time, " +
    			" a.res_followup, " +
    			" a.res_otherinfo, " +
    			" a.mark_result, " +
    			" a.mark_refusereason, " +
    			" a.mark_result as mark_result_old," +
    			" a.mark_refusereason as mark_refusereason_old," +
    			" a.call_spendtime, " +
    			" to_date(to_char(a.call_nexttime,'yyyy/mm/dd'),'yyyy/mm/dd') as call_nexttime, " +
    			//" cast(a.call_nexttime as DATE) call_nexttime, " +
    			" a.cus_domicile, " +
    			" a.cus_nature, " +
    			" a.cus_legalperson , " +
    			" a.cus_regtime, " +
    			" a.cus_cntpeople, " +
    			" a.cus_onmark,  " +
    			" a.cus_ownbusi, " +
    			" a.cus_busistatus, " +
    			" a.cus_operateperson," +
    			" a.cus_accountperson," +
    			" a.cus_majorproduct," +
    			" a.cus_majorrival," +
    			" a.res_custsource," +
    			" a.res_casebyperson," +
    			" a.res_casebyptel," +
    			" a.DCRB_MAJORSHOLDER, " +
    			" a.DCRB_FLOW," +
    			" a.DCRB_FIXEDASSETS," +
    			" a.DCRB_PROFIT," +
    			" a.DCRB_SYMBIOSIS," +
    			" a.DCRB_OTHERTRADE," +
    			" a.DCRB_MYSELFTRADE," +
    			" a.cus_onmarkplace," +
    			" a.cus_siteoperatetime, "+
    			" a.CUS_OPERATEAGE, "+
                " a.CUS_SITEOPERATEAGE, "+
    			" a.cus_operateaddr, "+
    			" a.If_Ownbankcust, "+
                " a.Res_Custsourcedate,"+
    			" b.cus_status," +        
    			" b.isbuschange," +       
    			" b.bus_explain," +       
    			" b.isrevchange," +       
    			" b.rev_explain ," +      
    			" b.isprochange ," +      
    			" b.pro_explain ," +      
    			" b.issupchange," +       
    			" b.sup_explain ," +      
    			" b.ispurchange ," +      
    			" b.pur_explain ," +      
    			" b.isequchange ," +      
    			" b.equ_explain ," +      
    			" b.isopcchange ," +      
    			" b.opc_explain," +       
    			" b.iscolchange," +       
    			" b.col_explain ," +      
    			" b.issymchange ," +      
    			" b.sym_explain ," +      
    			" b.mark_product," +
    			" p.pur_cust2call, " +
    			" p.pur_seek2coll, " +
    			" p.pur_warn2call, " +
    			" p.pur_defend2call, " +
    			" p.pur_mark2pro, " +
    			" p.pur_risk2call, "+
    			" ac.org_id, " +
    			" m.conclusion,  "+
    			" case when c.visit_time >= c.create_time then '正常预约' else '补录预约' end as ORDER_STATE"+
    			" from Ocrm_f_Interview_Record c " +
    			" left join ocrm_f_interview_new_record a on c.task_number = a.task_number  " +
    			" left join ocrm_f_interview_old_record b on c.task_number = b.task_number " +
    			" left join ocrm_f_interview_old_purpose p on c.task_number = p.task_number " +
    			" left join acrm_f_ci_pot_cus_com m on c.cust_id = m.cus_id "+
    			"left join admin_auth_account ac on ac.account_name=c.mgr_id " +
    			" where 1=1 and (c.task_type = '1' or (c.task_type = '0' and c.visit_type = '2')) ";
    	String sqlOld = "select c.id,"+
		        "c.cust_id,"+
		        "c.cust_name,"+
		        "c.mgr_id,"+
		        "c.mgr_name,"+
		        "c.task_type,"+
		        "to_date(to_char(c.visit_time,'yyyy/mm/dd'),'yyyy/mm/dd') as visit_time,"+
		        "c.VISIT_START_TIME,"+
	            "c.VISIT_END_TIME,"+
		       // "cast(c.visit_time as DATE) visit_time,"+
		        "c.create_time,"+
		        "c.remark,"+
		        "c.review_state,"+
		        "c.visit_type,"+
		        "c.task_number," +
		        "c.new_record_id," +
		        "c.flag," +
    			"b.interviewee_name ," +
				" b.interviewee_post," +
				" b.interviewee_phone," +
				" b.join_person,   " +
				" b.join_person_id,   " +
   			    " to_date(to_char(b.call_time,'yyyy/mm/dd'),'yyyy/mm/dd') as call_time, " +
				//" cast(b.call_time as DATE) call_time, " +
				" b.res_followup, " +
				" b.res_otherinfo, " +
				" b.mark_result ," +
				" b.mark_refusereason," +
				" b.mark_result as mark_result_old, " +
				" b.mark_refusereason as mark_refusereason_old, " +
				" b.call_spendtime, " +
				" to_date(to_char(b.call_nexttime,'yyyy/mm/dd'),'yyyy/mm/dd') as call_nexttime, " +
				//" cast(b.call_nexttime as DATE) call_nexttime," +
				" a.cus_domicile, " +
				" a.cus_nature, " +
				" a.cus_legalperson , " +
				" a.cus_regtime, " +
				" a.cus_cntpeople, " +
				" a.cus_onmark,  " +
				" a.cus_ownbusi, " +
				" a.cus_busistatus, " +
				" a.cus_operateperson," +
				" a.cus_accountperson," +
				" a.cus_majorproduct," +
				" a.cus_majorrival," +
				" a.res_custsource," +
				" a.res_casebyperson," +
				" a.res_casebyptel," +
				" a.DCRB_MAJORSHOLDER, " +
    			" a.DCRB_FLOW," +
    			" a.DCRB_FIXEDASSETS," +
    			" a.DCRB_PROFIT," +
    			" a.DCRB_SYMBIOSIS," +
    			" a.DCRB_OTHERTRADE," +
    			" a.DCRB_MYSELFTRADE," +
    			" a.cus_onmarkplace," +
    			" a.cus_siteoperatetime, "+
    			" a.CUS_OPERATEAGE, "+
                " a.CUS_SITEOPERATEAGE, "+
                " a.cus_operateaddr, "+
                " a.If_Ownbankcust, "+
                " a.Res_Custsourcedate,"+
				" b.cus_status," +        
				" b.isbuschange," +       
				" b.bus_explain," +       
				" b.isrevchange," +       
				" b.rev_explain ," +      
				" b.isprochange ," +      
				" b.pro_explain ," +      
				" b.issupchange," +       
				" b.sup_explain ," +      
				" b.ispurchange ," +      
				" b.pur_explain ," +      
				" b.isequchange ," +      
				" b.equ_explain ," +      
				" b.isopcchange ," +      
				" b.opc_explain," +       
				" b.iscolchange," +       
				" b.col_explain ," +      
				" b.issymchange ," +      
				" b.sym_explain ," +      
				" b.mark_product," +
				" p.pur_cust2call, " +
				" p.pur_seek2coll, " +
				" p.pur_warn2call, " +
				" p.pur_defend2call, " +
				" p.pur_mark2pro, " +
				" p.pur_risk2call, "
				+ "ac2.org_id, " +
				" m.conclusion,  "+
				" case when c.visit_time >= c.create_time then '正常预约' else '补录预约' end as ORDER_STATE"+
				" from Ocrm_f_Interview_Record c " +
				" left join ocrm_f_interview_new_record a on c.task_number = a.task_number  " +
				" left join ocrm_f_interview_old_record b on c.task_number = b.task_number " +
				" left join ocrm_f_interview_old_purpose p on c.task_number = p.task_number "+
				" left join acrm_f_ci_pot_cus_com m on c.cust_id = m.cus_id "+
				" left join admin_auth_account ac2 on ac2.account_name=c.mgr_id" +
				" where 1=1 and c.task_type = '0' and (c.visit_type <>'2' or c.visit_type  is null ) ";
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder();
    	sb.append("select * from (");
    	sb.append(sqlNew);
    	sb.append(" union ");
    	sb.append(sqlOld);
    	sb.append(") c where 1=1 ");
    	
    	if( id != null && !"".equals(id)){//流程查询使用
    		if("0".equals(request.getParameter("type"))){//旧户
    			sb.setLength(0);
    			sqlOld = sqlOld.toString().split("where")[0];
    			sb.append(sqlOld);
    			sb.append(" where 1=1 and  c.task_number = '" + id + "'");

    			addOracleLookup("VISIT_TYPE", "VISIT_TYPE_OLD");
    			addOracleLookup("MARK_RESULT", "VISIT_RESULT_QS_OLD");
    			addOracleLookup("MARK_REFUSEREASON", "MARK_REASON_OLD");
    			
    		}else if("1".equals(request.getParameter("type"))){
    			sb.setLength(0);
    			sqlNew = sqlNew.toString().split("where")[0];
    			sb.append(sqlNew);
    			sb.append(" where 1=1 and c.task_number = '" + id + "'");
    			
    			addOracleLookup("VISIT_TYPE", "VISIT_TYPE_NEW");
    			addOracleLookup("MARK_RESULT", "VISIT_RESULT_QS");
    			addOracleLookup("MARK_REFUSEREASON", "MARK_REFUSEREASON");
    		}else if("01".equals(request.getParameter("type"))){
    			sb.setLength(0);
    			sqlNew = sqlNew.toString().split("where")[0];
    			sb.append(sqlNew);
    			sb.append(" where 1=1 and  c.task_number = '" + id + "'");
    			
    			addOracleLookup("VISIT_TYPE", "VISIT_TYPE_OLD");
    			addOracleLookup("MARK_RESULT", "VISIT_RESULT_QS");
    			addOracleLookup("MARK_REFUSEREASON", "MARK_REFUSEREASON");
    		}
            addOracleLookup("CUS_STATUS", "CUS_STATUS");
    		addOracleLookup("ISBUSCHANGE", "IF_FLAG");
    		addOracleLookup("ISREVCHANGE", "IF_FLAG");
    		addOracleLookup("ISPROCHANGE", "IF_FLAG");
    		addOracleLookup("ISSUPCHANGE", "IF_FLAG");
    		addOracleLookup("ISPURCHANGE", "IF_FLAG");
    		addOracleLookup("ISEQUCHANGE", "IF_FLAG");
    		addOracleLookup("ISOPCCHANGE", "IF_FLAG");
    		addOracleLookup("ISSYMCHANGE", "ISCOLCHANGE");
    		addOracleLookup("ISCOLCHANGE", "ISCOLCHANGE");
    		
    		addOracleLookup("PUR_CUST2CALL", "IF_FLAG");
    		addOracleLookup("PUR_SEEK2COLL", "IF_FLAG");
    		addOracleLookup("PUR_WARN2CALL", "IF_FLAG");
    		addOracleLookup("PUR_DEFEND2CALL", "IF_FLAG");
    		addOracleLookup("PUR_MARK2PRO", "IF_FLAG");
    		addOracleLookup("PUR_RISK2CALL", "IF_FLAG");
    		
    		addOracleLookup("CUS_ONMARK", "IF_FLAG");
    		addOracleLookup("CUS_ONMARKPLACE", "CUS_ONMARKPLACE");
            addOracleLookup("CUS_NATURE", "CUS_NATURE");
            addOracleLookup("CUS_OWNBUSI", "CUS_OWNBUSI");
            addOracleLookup("CUS_BUSISTATUS", "CUS_BUSISTATUS");
            addOracleLookup("RES_CUSTSOURCE", "CUST_SOURCE");
            
    	}else{
        	for (String key : this.getJson().keySet()) {// 查询条件判断
			if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
				if (key.equals("VISIT_TIME")) {
					sb.append(" AND c.VISIT_TIME =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
				} else if(key.equals("MGR_NAME")||"MGR_ID".equals(key)){
					sb.append(" AND c."+key+" = '"+this.getJson().get(key)+"'");
				}else if(key.equals("TASK_TYPE")){
					sb.append(" AND c."+key+" = '"+this.getJson().get(key)+"'");
				}
			}
		}
    		setPrimaryKey("c.REVIEW_STATE asc ");
    	}
    	SQL=sb.toString();
    	datasource = ds;
    	
    	configCondition("REVIEW_STATE","=","REVIEW_STATE",DataType.String);
		configCondition("TASK_TYPE","=","TASK_TYPE",DataType.String);
		configCondition("VISIT_TYPE","=","VISIT_TYPE",DataType.String);
		configCondition("CUST_ID","=","CUST_ID",DataType.String);
		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("MGR_NAME","=","MGR_NAME",DataType.String);
		configCondition("INTERVIEWEE_NAME","like","INTERVIEWEE_NAME",DataType.String);
		configCondition("INTERVIEWEE_POST","=","INTERVIEWEE_POST",DataType.String);
		configCondition("INTERVIEWEE_PHONE","=","INTERVIEWEE_PHONE",DataType.String);
		configCondition("VISIT_TIME","=","VISIT_TIME",DataType.Date);
	}
	/**保存拜访任务信息时不需要审批，反馈时才需要审批
	 * 20141209 modify
    public DefaultHttpHeaders save() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    
		service.save(model);
		String type = ((OcrmFInterviewTask)model).getTaskType();
		String instanceid = "";
		if("0".equals(type)){
			instanceid = "QSJVISIT_"+((OcrmFInterviewTask)model).getId()+"_0"+"_"+new SimpleDateFormat("HHmmss").format(new Date());//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		}else if("1".equals(type)){
			instanceid = "QSJVISIT_"+((OcrmFInterviewTask)model).getId()+"_1"+"_"+new SimpleDateFormat("HHmmss").format(new Date());//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		}
		String jobName = "企商金拜访复核_"+((OcrmFInterviewTask)model).getCustName();//自定义流程名称
		service.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("instanceid", instanceid);
	    map1.put("currNode", "95_a3");
	    map1.put("nextNode",  "95_a4");
	    this.setJson(map1);
    	
    	return new DefaultHttpHeaders("success");
    }
    */
	
    /**
     * 删除
     */
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for(String id : ids){
    		//删除拜访信息
    		service.batchUpdateByName(" delete from OcrmFInterviewTask g where g.id='"+Long.parseLong(id)+"'", null);
    		//删除拜访明细信息
    		service.batchUpdateByName(" delete from OcrmFInterviewOldRecord g where g.taskNumber ='"+Long.parseLong(id)+"'", null);
    		service.batchUpdateByName(" delete from OcrmFInterviewNewRecord g where g.taskNumber ='"+Long.parseLong(id)+"'", null);
    		//日程表 自己在页面手工删除  日程表的vid 存拜访任务表的id值
    		//service.deleteSchedule(id);
    	}
    	
    }
    
    /**
     * 校验潜在客户拜访类型为新户首次拜访的时候是否已存在
     * '0'不存在
     * '1'存在
     * @return
     */
    public String checkNewRecord(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String CUST_ID = request.getParameter("CUST_ID");
    	String MGR_ID = request.getParameter("MGR_ID");
    	String VISIT_TYPE = request.getParameter("VISIT_TYPE");
    	String isFlag = "0";
    	String sb = " select * from ocrm_f_interview_record r " +
    				"where r.cust_id = '"+CUST_ID+"' " +
    				" and r.mgr_id = '"+MGR_ID+"' and r.visit_type = '"+VISIT_TYPE+"' ";
    	List list = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
    	if(list.size()>0){
    		isFlag = "1";
    	}
    	
    	Map<String,Object> map1=new HashMap<String,Object>();
	    map1.put("isFlag",  isFlag);
	    this.setJson(map1);
    	return "success";
    }
}
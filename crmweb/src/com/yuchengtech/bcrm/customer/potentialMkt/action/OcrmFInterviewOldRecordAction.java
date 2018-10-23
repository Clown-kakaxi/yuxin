package com.yuchengtech.bcrm.customer.potentialMkt.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewOldRecord;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewTask;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFInterviewOldRecordService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 企商金客户营销流程 -  拜访信息页面  
 */

@SuppressWarnings("serial")
@Action("/ocrmFInterviewOldRecord")
public class OcrmFInterviewOldRecordAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFInterviewOldRecordService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFInterviewOldRecord();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = "select c.* from ocrm_f_interview_Old_record c " +
    			" where 1=1 and c.task_number = '"+request.getParameter("ID")+"' ";
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	SQL=sb.toString();
    	datasource = ds;
	}
    /**
     * 旧户提交流程
     * @return
     * @throws Exception
     */
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public DefaultHttpHeaders save() throws Exception{
	    	ActionContext ctx = ActionContext.getContext();
	    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    
			service.save(model,true);
			String mgrId = "";
			String custName = "";
			Vector<OcrmFInterviewTask> list = (Vector<OcrmFInterviewTask>) service.findByJql("select c from OcrmFInterviewTask c where (c.id ='"+((OcrmFInterviewOldRecord)model).getTaskNumber().split(",")[0]+"' or c.taskNumber = '"+((OcrmFInterviewOldRecord)model).getTaskNumber().split(",")[0]+"')", null);
			for(OcrmFInterviewTask task:list){
				custName = task.getCustName();
				mgrId = task.getMgrId();
			}
			
			String instanceid = "";
			String jobName = "";
			
			instanceid = "QSJVISIT_"+((OcrmFInterviewOldRecord)model).getTaskNumber().split(",")[0]+"_0"+"_"+new SimpleDateFormat("HHmmss").format(new Date());//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			jobName = "企商金旧户拜访复核_"+custName;//自定义流程名称
			
			service.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			
			String nextNode = "";
			List roles = auth.getRolesInfo();
			for (Object m : roles) {
				Map map = (Map) m;
				if ("R309".equals(map.get("ROLE_CODE"))) {//法金Team head 
					nextNode = "95_a7";//支行行长
					continue;
				}else if ("R106".equals(map.get("ROLE_CODE"))) {// 总行法金Team head 
					nextNode = "95_a8";//总行法金业务条线
					continue;
				}else if ("R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))) {// 法金ARM,RM
					//RM没有直属上级Team Head，可由支行行长直接审批
					String teamHead = service.getTeamhead(mgrId);
					if(teamHead != null && !teamHead.isEmpty()){
						nextNode = "95_a4";//直属上级Team Head
					}else{
						nextNode = "95_a7";//支行行长
					}
					continue;
				}else if( "R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))){//总行法金ARM,RM 
					//总行RM没有直接上级Team Head,可由法金业务条线主管审批
					String teamHead = service.getTeamhead(mgrId);
					if(teamHead != null && !teamHead.isEmpty()){
						nextNode = "95_a4";//直属上级Team Head
					}else{
						nextNode = "95_a8";//
					}
					continue;
				}else{
					nextNode = "95_a4";
				}
			}
			Map<String,Object> map1=new HashMap<String,Object>();
			map1.put("instanceid", instanceid);
		    map1.put("currNode", "95_a3");
		    map1.put("nextNode",  nextNode);
		    this.setJson(map1);
	    	
	    	return new DefaultHttpHeaders("success");
	 }
	 
    /**
     * 删除
     */
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for(String id : ids){
    		service.batchUpdateByName(" delete from OcrmFInterviewOldRecord g where g.id='"+Long.parseLong(id)+"'", null);
    	}
    }
}
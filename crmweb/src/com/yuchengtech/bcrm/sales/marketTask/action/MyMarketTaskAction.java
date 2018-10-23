package com.yuchengtech.bcrm.sales.marketTask.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bcrm.sales.marketTask.service.MyMarketTaskService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 我的营销任务
 * 
 * @author : helin
 * @date : 2014-07-03 10:25:20
 */
@Action("/myMarketTask")
public class MyMarketTaskAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private MyMarketTaskService myMarketTaskService;
    
    @Autowired
    public void init(){
        model = new OcrmFMmTask();
        setCommonService(myMarketTaskService);
    }
    
    public void prepare(){
    	 AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         StringBuffer sb = new StringBuffer("SELECT t.*,t1.task_name as task_parent_name FROM OCRM_F_MM_TASK t left join OCRM_F_MM_TASK t1 on t.task_parent_id = t1.task_id ");
         sb.append(" where 1=1 ");
         //暂存状态的子任务不允许查询出来
         sb.append(" and (t.task_parent_id is null or (t.task_parent_id is not null and t.task_stat <> '1' )) ");
         sb.append(" and t.DIST_TASK_TYPE = '2' and t.oper_obj_id = '"+auth.getUserId()+"' ");
         for(String key:this.getJson().keySet()){
             if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
                 if("TASK_ID".equals(key)){
                     sb.append(" AND t.TASK_ID = '"+this.getJson().get(key)+"'");
                 }
             	if("TASK_NAME".equals(key)){
                     sb.append(" AND t.TASK_NAME LIKE '%"+this.getJson().get(key)+"%'");
                 }
             }
         }
         SQL = sb.toString();
         datasource =ds;
         setPrimaryKey("T.RECENTLY_UPDATE_DATE DESC,T.TASK_ID DESC");
         
         configCondition("t1.TASK_NAME", "like", "TASK_PARENT_NAME", DataType.String);
         configCondition("t.TASK_TYPE", "=", "TASK_TYPE", DataType.String);
         configCondition("t.TASK_STAT", "=", "TASK_STAT", DataType.String);
         configCondition("t.DIST_TASK_TYPE", "=", "DIST_TASK_TYPE", DataType.String);
         configCondition("t.TASK_BEGIN_DATE", "=", "TASK_BEGIN_DATE", DataType.Date);
         configCondition("t.TASK_END_DATE", "=", "TASK_END_DATE", DataType.Date);
    }
    
    /**
     * batch delete myMarketTask
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("ids");
        myMarketTaskService.batchRemove(ids);
        return "success";
    }
    
    /**
     * 查询指标周期类型
     */
    public String queryTargetCycle(){
		if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
    	this.json.put("json",myMarketTaskService.queryTargetCycle());
        return "success";
    }
    
    /**
     * 任务反馈
     * @return
     * @throws Exception 
     */
    public void feedBackadTask() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(ServletActionContext.HTTP_RESPONSE);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		OcrmFMmTask marketTask = (OcrmFMmTask) model;
		myMarketTaskService.feedBackadTask(marketTask);
		String instanceid ="MKT_"+marketTask.getTaskId()+"_"+auth.getUserId()+"_"+new SimpleDateFormat("HHmmss").format(new Date());// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "营销任务反馈审批流程";// 自定义流程名称
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String nextNode = "120_a4";
		 List<?> list = auth.getRolesInfo();
		 for(Object m:list){
			Map<?, ?> map = (Map<?, ?>)m;//map自m引自list，ROLE_CODE为键, R000为值
			paramMap.put("role", map.get("ROLE_CODE"));
			if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))
					|| "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){//ARMRM个金法金
				nextNode = "120_a4";
				continue ;
			}else if("R106".equals(map.get("ROLE_CODE")) || "R309".equals(map.get("ROLE_CODE"))){
				nextNode = "120_a5";
				continue ;
			}
		 }
		myMarketTaskService.initWorkflowByWfidAndInstanceid("120", jobName,
				paramMap, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		response.getWriter().write(
				"{\"instanceid\":\"" + instanceid
						+ "\",\"currNode\":\"120_a3\",\"nextNode\":\""
						+ nextNode + "\"}");
		response.getWriter().flush();
    }
    
}

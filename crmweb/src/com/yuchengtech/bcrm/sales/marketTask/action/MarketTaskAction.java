package com.yuchengtech.bcrm.sales.marketTask.action;

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
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bcrm.sales.marketTask.service.MarketTaskService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 营销任务信息
 * 
 * @author : helin
 * @date : 2014-07-03 10:19:20
 * 
 * 修改人：chixinl
 * 修改内容：sql
 */
@Action("/marketTask")
public class MarketTaskAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private MarketTaskService marketTaskService;
    
    @Autowired
    public void init(){
        model = new OcrmFMmTask();
        setCommonService(marketTaskService);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer("SELECT t.*,t1.task_name as task_parent_name FROM OCRM_F_MM_TASK t left join OCRM_F_MM_TASK t1 on t.task_parent_id = t1.task_id ");
        sb.append(" where 1=1 ");
        //暂存状态的子任务不允许查询出来
        sb.append(" and (t.task_parent_id is null or (t.task_parent_id is not null and t.task_stat <> '1' )) ");
        //查询创建人是登录人，或执行对象类型是机构-且是当前登录机构，或执行对象类型是团队-且是当前登录人负责的团队
        sb.append(" and (t.CREATE_USER = '"+auth.getUserId()+"' OR (t.DIST_TASK_TYPE = '1' and t.OPER_OBJ_ID = '"+auth.getUnitId()+"') ");
        sb.append(" OR (t.DIST_TASK_TYPE = '3' and t.OPER_OBJ_ID IN ( select to_char(MKT_TEAM_ID) from OCRM_F_CM_MKT_TEAM where  TEAM_LEADER_ID = '"+auth.getUserId()+"')) )");
        
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
        setPrimaryKey(" t.task_id asc");
        datasource =ds;
        //营销任务放大镜特有的查询条件及转换
        addOracleLookup("TASK_TYPE", "MTASK_TYPE");
        addOracleLookup("TASK_STAT", "MTASK_STAT");
        addOracleLookup("DIST_TASK_TYPE", "MTASK_OPER_TYPE");
        if(this.getJson().get("SEARCH_TYPE") != null && !"".equals(this.getJson().get("SEARCH_TYPE"))){
        	if("ALL".equals(this.getJson().get("SEARCH_TYPE"))){
        		SQL = "SELECT * FROM OCRM_F_MM_TASK T WHERE T.TASK_STAT <> '1' ";
        	}
        	if("PARENT".equals(this.getJson().get("SEARCH_TYPE"))){
        		SQL = "SELECT * FROM OCRM_F_MM_TASK T WHERE T.TASK_ID IN (SELECT TASK_PARENT_ID FROM ("+SQL+"))";
        	}
        	if("CURRENT".equals(this.getJson().get("SEARCH_TYPE"))){
        	}
        }
        
        configCondition("t1.TASK_NAME", "like", "TASK_PARENT_NAME", DataType.String);
        configCondition("t.TASK_TYPE", "=", "TASK_TYPE", DataType.String);
        configCondition("t.TASK_STAT", "=", "TASK_STAT", DataType.String);
        configCondition("t.DIST_TASK_TYPE", "=", "DIST_TASK_TYPE", DataType.String);
        configCondition("t.TASK_BEGIN_DATE", "=", "TASK_BEGIN_DATE", DataType.Date);
        configCondition("t.TASK_END_DATE", "=", "TASK_END_DATE", DataType.Date);
    }
    
    /**
     * batch delete 营销任务信息
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String ids = request.getParameter("idStr");
        marketTaskService.batchRemove(ids);
        return "success";
    }
    
    /**
     * 查询任务对象及其指标
     * @return
     */
    public String queryTaskTarget(){
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String taskId = request.getParameter("taskId");
        Map<?, ?> map = marketTaskService.queryTaskTarget(taskId);
        if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
    	this.json.put("json",map);
        return "success";
    }
    
    /**
     * 下达营销任务
     * @return
     */
    public String transTask(){
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String taskId = request.getParameter("taskId");
        marketTaskService.transTask(taskId);
        return "success";
    }
    /**
     * 关闭营销任务
     * @return
     */
    public String closeTask(){
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String taskId = request.getParameter("taskId");
        marketTaskService.closeTask(taskId);
        return "success";
    }
    
    /**
     * 调整营销任务
     * @return
     */
    public String adjustTask(){
    	OcrmFMmTask marketTask = (OcrmFMmTask)model;
    	marketTaskService.adjustTask(marketTask);
    	return "success";
    }
    
    /**
     * 分解营销任务
     * @return
     */
    public String resolveTask(){
    	OcrmFMmTask marketTask = (OcrmFMmTask)model;
    	marketTaskService.resolveTask(marketTask);
    	return "success";
    }
    
}

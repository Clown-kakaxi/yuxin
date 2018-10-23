package com.yuchengtech.bcrm.sales.marketTask.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;

/**
 * @description 营销任务树展示
 * @author sujm
 * @since 2014-08-16
 * */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/TaskTreeQuery", results = { @Result(name = "success", type = "json")})
public class TaskTreeQueryAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	/**
	 *营销任务树展示sql
	 */
	@SuppressWarnings("unchecked")
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        this.setJson(request.getParameter("condition"));
        StringBuffer lastStr = new StringBuffer("");
        String tempTaskId = "";
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("TASK_ID")){
                	tempTaskId=(String)this.getJson().get("TASK_ID");
                	String taskArr[] = tempTaskId.split(",");
                	lastStr.append(" and ( t1.taskseq like '%"+taskArr[0]+"%' ");
                	for (int i=1;i<taskArr.length;i++){
                		lastStr.append(" or t1.taskseq like '%"+taskArr[i]+"%'");
                	}
                	lastStr.append(" )");
                }
                else if(key.equals("TASK_TYPE")){
                	lastStr.append(" and t1.TASK_TYPE = '"+(String)this.getJson().get(key)+"'");
                }
                else if(key.equals("TASK_STAT")){
                	lastStr.append(" and t1.TASK_STAT = '"+(String)this.getJson().get(key)+"'");
                }
                else if(key.equals("DIST_TASK_TYPE")){
                	lastStr.append(" and t1.DIST_TASK_TYPE = '"+(String)this.getJson().get(key)+"'");                
                	}
            }
        }
       
		StringBuilder sb = new StringBuilder(" select t1.task_id as ID,t1.task_id,    					"+
											" t1.task_name,t1.TASK_STAT,t1.TASK_TYPE,t1.DIST_TASK_TYPE, "+
											" (case  when to_char(t1.task_id) in ('"+tempTaskId.replace(",", "','")+"')and to_char(t1.task_parent_id) not in('"+tempTaskId.replace(",", "','")+"') then 0 when t1.task_parent_id is not null then t1.task_parent_id else 0 end ) as task_parent_id, "+
											" T1.TASKSEQ "+
											"   from MKT_TASK_VIEW t1 " +
										    " LEFT JOIN ADMIN_AUTH_ACCOUNT T2 ON T1.OPER_OBJ_ID=T2.ACCOUNT_NAME " +
											" LEFT JOIN ADMIN_AUTH_ORG T3 ON T1.OPER_OBJ_ID=T3.ORG_ID "+
											 "  WHERE 1>0     ");
		
		sb.append(lastStr);
	
		SQL=sb.toString();
		datasource = ds;
		 configCondition("t1.TASK_BEGIN_DATE", "=", "TASK_BEGIN_DATE", DataType.Date);
		 configCondition("t1.TASK_END_DATE", "=", "TASK_END_DATE", DataType.Date);
	}
}



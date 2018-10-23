package com.yuchengtech.bcrm.customer.potentialMkt.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCallNewRecord;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewTask;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCallNewRecordService;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFInterviewTaskService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对公客户营销流程 -  电访信息  新客户 （查询，维护，删除） 2014-11-25
 * @author dongyi
 * @since 2014-11-27
 */

@SuppressWarnings("serial")
@Action("/ocrmFCallNewRecord")
public class OcrmFCallNewRecordAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private OcrmFCallNewRecordService service ;
    
    @Autowired
    private OcrmFInterviewTaskService taskService ;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCallNewRecord();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = " select c.*   from ocrm_f_call_new_record c " +
    			" where 1=1  ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    		addOracleLookup("TEL_CONTACTER", "TEL_CONTACTER");
    		addOracleLookup("CUST_REVENUE", "CUST_REVENUE");
    		addOracleLookup("CUS_OWNBUSI", "CUS_OWNBUSI");
    		addOracleLookup("CUST_SOURCE", "CUST_SOURCE");
    		addOracleLookup("CALL_RESULT", "CALL_RESULT");
    	}else{
    		sb.append(" and c.id = '"+id+"'");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
	}
    public DefaultHttpHeaders save() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	/**
    	 * 产生拜访信息(同意拜访)
    	 */
    	if(((OcrmFCallNewRecord)model).getId() == null && "1".equals(String.valueOf(((OcrmFCallNewRecord)model).getCallResult()))){
    		OcrmFInterviewTask task = new OcrmFInterviewTask();
    		task.setCustId(((OcrmFCallNewRecord)model).getCustId());
    		task.setCustName(((OcrmFCallNewRecord)model).getCustName());
    		task.setMgrId(((OcrmFCallNewRecord)model).getMgrId());
    		task.setMgrName(((OcrmFCallNewRecord)model).getMgrName());
    		task.setTaskType("1");
    		if(((OcrmFCallNewRecord)model).getVisitDate()!=null){
    			task.setVisitTime(((OcrmFCallNewRecord)model).getVisitDate());
    		}else{
    			throw new BizException(1, 0, "10000", "请选择拜访日期");
    		}
    		task.setCreateTime(new Date());
    		task.setReviewState("1");
    		task.setModelType("1");//（0是旧案，1是新案）
    		taskService.saveTask(task);
    	}
    	service.save(model);
    	return new DefaultHttpHeaders("success");
    }
    /***
	 * 删除电访信息
	 */
	public void delData(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id =  request.getParameter("id");
	   		service.delData(id);
	}
    
}
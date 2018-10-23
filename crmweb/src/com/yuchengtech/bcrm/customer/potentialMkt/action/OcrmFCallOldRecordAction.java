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
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCallOldRecord;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewTask;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCallOldRecordService;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFInterviewTaskService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对公客户营销流程 -  电访信息  旧客户 （查询，维护，删除） 2014-11-25
 * @author dongyi
 * @since 2014-11-27
 */

@SuppressWarnings("serial")
@Action("/mktCallOldFRecord")
public class OcrmFCallOldRecordAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private OcrmFCallOldRecordService service ;
    
    @Autowired
    private OcrmFInterviewTaskService taskService ;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCallOldRecord();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = " select c.*   from ocrm_f_call_old_record c " +
    			" where 1=1  ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    		addOracleLookup("CALL_PURPOSE", "CALL_PURPOSE");
    		addOracleLookup("CUST_BUSI_CONDITION", "CUST_BUSI_CONDITION");
    		addOracleLookup("MAIN_BUSI_CHANGE", "IF_FLAG");
    		addOracleLookup("REVENUE_CHANGE", "IF_FLAG");
    		addOracleLookup("PROFI_CHANGE", "IF_FLAG");
    		addOracleLookup("MAIN_SUPPLIER_CHANGE", "IF_FLAG");
    		addOracleLookup("MAIN_BUYER_CHANGE", "IF_FLAG");
    		addOracleLookup("EQUITY_STRUC_CHANGE", "IF_FLAG");
    		addOracleLookup("MANAGEMENT_CHANGE", "IF_FLAG");
    		addOracleLookup("COLLATERAL_CONDITION", "COLLATERAL_CONDITION");
    		addOracleLookup("COOPERATION_CHANGE", "COLLATERAL_CONDITION");
    		addOracleLookup("IF_PRECONTRACT", "IF_FLAG");
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
    	if(((OcrmFCallOldRecord)model).getId() == null && "1".equals(String.valueOf(((OcrmFCallOldRecord)model).getIfPrecontract()))){
    		OcrmFInterviewTask task = new OcrmFInterviewTask();
    		task.setCustId(((OcrmFCallOldRecord)model).getCustId());
    		task.setCustName(((OcrmFCallOldRecord)model).getCustName());
    		task.setMgrId(((OcrmFCallOldRecord)model).getMgrId());
    		task.setMgrName(((OcrmFCallOldRecord)model).getMgrName());
    		task.setTaskType("0");//旧户
    		if(((OcrmFCallOldRecord)model).getVisitDate()!=null){
    			task.setVisitTime(((OcrmFCallOldRecord)model).getVisitDate());
    		}else{
    			throw new BizException(1, 0, "10000", "请选择拜访日期");
    		}
    		task.setCreateTime(new Date());
    		task.setReviewState("1");
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
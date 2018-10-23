
package com.yuchengtech.bcrm.customer.belong.action;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiBelongBack;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiBelongBackService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户退回处理
 * @author luyy
 *@since 2014-07-10
 */

@SuppressWarnings("serial")
@Action("/custBack")
public class OcrmFCiBelongBackAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFCiBelongBackService service;
	
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFCiBelongBack();
		setCommonService(service);
	}
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
    	if(id !=null && !"".equals(id)){//工作流展示查询
    		StringBuffer sb = new StringBuffer(" select m.* from OCRM_F_CI_BELONG_BACK m  where id = '"+id+"'");
    		SQL = sb.toString();
        	datasource = ds;
    	}else{
    		StringBuffer sb = new StringBuffer("");
    		String type = request.getParameter("type");
    		if("1".equals(type)){
    			//wzy,20140925,modify:修改查询逻辑，如果某个客户执行了退回，而且流程未走完，此客户不能被查询出来
//    			sb.append(" select c.cust_id,c.cust_name,c.cust_type,c.cust_stat,m.mgr_id," +
//        				"m.mgr_name,m.institution,m.institution_name,m.main_type,m.id  " +
//        				"from ACRM_F_CI_CUSTOMER c left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id where m.id not in (select record_id from " +
//        				"OCRM_F_CI_BELONG_BACK where BACK_STAT='1') and m.mgr_id='"+auth.getUserId()+"'");
    			sb.append(" select c.cust_id,c.cust_name,c.cust_type,c.cust_stat,m.mgr_id," +
        				"m.mgr_name,m.institution,m.institution_name,m.main_type,m.id  " +
        				"from acrm_f_ci_customer c left join ocrm_f_ci_belong_custmgr m on c.cust_id = m.cust_id "+
        				" where c.cust_id not in (select t.cust_id from " +
        				" ocrm_f_ci_belong_back t where back_stat = '1' and t.mgr_id = m.mgr_id) "+
        				" and m.mgr_id='"+auth.getUserId()+"'");

            	//处理页面查询条件
        		  for(String key : this.getJson().keySet()){
               		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
           				if(null!=key&&(key.equals("CUST_ID")||"MGR_NAME".equals(key)||"INSTITUTION_NAME".equals(key)||"MAIN_TYPE".equals(key))){
           					sb.append("  and m."+key+" like '%"+this.getJson().get(key)+"%'  ");
           				}else if(null!=key&&("CUST_NAME".equals(key)||"CUST_TYPE".equals(key)||"CUST_STAT".equals(key))){
           					sb.append("  and c."+key+" like '%"+this.getJson().get(key)+"%'  ");
           				}
               		}
           		}
    		}else if("2".equals(type)){
    			sb.append(" select * from OCRM_F_CI_BELONG_BACK where MGR_ID='"+auth.getUserId()+"'");
    			addOracleLookup("CUST_TYPE", "XD000080");
    			addOracleLookup("MAIN_TYPE", "MAINTAIN_TYPE");
    			addOracleLookup("BACK_STAT", "BACK_STAT");
    			
    		}else if("3".equals(type)){
    			//wzy,20140925,add:查询某客户的退回记录
    	    	String cust_id = request.getParameter("cust_id");
    			sb.append(" select * from ocrm_f_ci_belong_back where cust_id = '"+cust_id+"'");
    			addOracleLookup("CUST_TYPE", "XD000080");
    			addOracleLookup("MAIN_TYPE", "MAINTAIN_TYPE");
    			addOracleLookup("BACK_STAT", "BACK_STAT");    			
    		}
    		
        	SQL = sb.toString();
        	datasource = ds;
    	}
    }
	
	/**
	 * 提交客户退回申请
	 * @throws Exception
	 */
	public void save() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	((OcrmFCiBelongBack)model).setBackDate(new Date());
    	((OcrmFCiBelongBack)model).setBackStat("1");
    	
    	service.save(model);
		//提交流程处理
		Long id = ((OcrmFCiBelongBack)model).getId();
		String name = ((OcrmFCiBelongBack)model).getCustName();
		String instanceid = "TH_"+id;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "客户退回_"+name;//自定义流程名称

		service.initWorkflowByWfidAndInstanceid("30", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
	    response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"30_a3\",\"nextNode\":\"30_a4\"}");
		response.getWriter().flush();
	}
	
}

package com.yuchengtech.bcrm.customer.potentialMkt.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCallC;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktVisitC;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktCallCService;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktVisitCService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 对公客户营销流程 -  电访信息  luyy  2014-07-25
 * modify - 修改查询sql dongyi 2014-11-25
 */

@SuppressWarnings("serial")
@Action("/mktCallC")
public class MktCallQueryAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private OcrmFCiMktCallCService service ;
    
    @Autowired
    private OcrmFCiMktVisitCService vservice;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuffer sb = new StringBuffer(
    			" select id,cust_id,cust_name,call_date,mgr_name,cust_type,review_state,mgr_id,VISIT_DATE,RECAL_DATE,org_id " +
    			" from( " +
    			" select " +
    			" t1.id as id,t1.cust_id,t1.cust_name,t1.call_date,t1.mgr_name,t1.mgr_id,t1.review_state,'1' as cust_type,t1.VISIT_DATE,t1.RECAL_DATE,ac.org_id " +
    			" from ocrm_f_call_new_record t1"
    			+ " left join admin_auth_account ac on ac.account_name=t1.mgr_id  " +
    			" union " +
    			" select " +
    			" t2.id as id,t2.cust_id,t2.cust_name,t2.call_date,t2.mgr_name,t2.mgr_id,t2.review_state,'2' as cust_type,t2.VISIT_DATE,t2.VISIT_DATE as RECAL_DATE,ac2.org_id  " +
    			" from ocrm_f_call_old_record t2"
    			+ " left join admin_auth_account ac2 on ac2.account_name=t2.mgr_id " +
    			" ) where 1=1   " );
    	
    		
    		SQL=sb.toString();
    		datasource = ds;
    		setPrimaryKey("call_date desc ");
    		configCondition("CUST_ID","=","CUST_ID",DataType.String);
    		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
    		configCondition("CALL_DATE","=","CALL_DATE",DataType.Date);
    		configCondition("MGR_ID","like","MGR_ID",DataType.String);
    		configCondition("CUST_TYPE","=","CUST_TYPE",DataType.String);
    		configCondition("REVIEW_STATE","=","REVIEW_STATE",DataType.String);
    		
    		
        	
        	
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	((OcrmFCiMktCallC)model).setPhoneDate(new Date());
    	((OcrmFCiMktCallC)model).setUserId(auth.getUserId());
    	((OcrmFCiMktCallC)model).setCheckStat("2");
    	((OcrmFCiMktCallC)model).setRecordDate(new Date());
    	((OcrmFCiMktCallC)model).setUpdateDate(new Date());
    	
    	service.save(model);
    	
    	Long callId = ((OcrmFCiMktCallC)model).getId();
    	String result = ((OcrmFCiMktCallC)model).getCallResult();
    	if("1".equals(result)){//如果同意拜访  生成拜访信息
    		OcrmFCiMktVisitC visit = new OcrmFCiMktVisitC();
    		visit.setCallId(callId.toString());
    		visit.setCustId(((OcrmFCiMktCallC)model).getCustId());
    		visit.setCustName(((OcrmFCiMktCallC)model).getCustName());
    		visit.setIndustType(((OcrmFCiMktCallC)model).getIndustType());
    		visit.setVisitDate(((OcrmFCiMktCallC)model).getVisitDate());
    		visit.setUserId(auth.getUserId());
    		visit.setCheckStat("1");
    		visit.setRecordDate(new Date());
    		visit.setUpdateDate(new Date());
    		
    		vservice.save(visit);
    	}
    	
    	//初始化流程
    	
    	String name = ((OcrmFCiMktCallC)model).getCustName();
		String instanceid = "CALL_"+callId+"_2";//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "电访复核_"+name;//自定义流程名称
		
		service.initWorkflowByWfidAndInstanceid("39", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		String nextNode = "39_a4";
		List list = auth.getRolesInfo();
		for(Object m:list){
			Map map = (Map)m;
			if("R202".equals(map.get("ROLE_CODE"))){//分行行长/区域中心主管
				nextNode = "39_a6";
				break ;
			}else if("R301".equals(map.get("ROLE_CODE"))){//支行行长
				nextNode = "39_a5";
				continue ;
			}else if("R304".equals(map.get("ROLE_CODE"))){//客户经理或助理
				nextNode = "39_a4";
				continue ;
			}else{
				continue ;
			}
		}	
	  Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("instanceid", instanceid);
	    map1.put("currNode", "39_a3");
	    map1.put("nextNode",  nextNode);
	    this.setJson(map1);
    	
    	return new DefaultHttpHeaders("success");
    }
    
    /**
     * 校验该客户信息是否进行转移
     * '0'未转移
     * '1'已转移
     * @return
     */
    public String checkTrans(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id =  request.getParameter("id");
    	String flg = "0";
    	String sb = " select b.*  from ocrm_f_ci_trans_business b where 1=1 and b.state <> '99' and b.type='1' and b.bus_data_id = '"+id+"' ";
    	List<Object> list = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
    	if(list.size() > 0){
    		flg = "1";
    	}
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("flg", flg);
    	this.setJson(map);
    	return "success";
    }
    
}
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
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCallP;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktVisitP;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktCallPService;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktVisitPService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 个金客户营销流程 -  电访信息页面  luyy  2014-07-22
 */

@SuppressWarnings("serial")
@Action("/mktCallP")
public class MktCallpAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private OcrmFCiMktCallPService service ;
    
    @Autowired
    private OcrmFCiMktVisitPService vservice;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktCallP();  
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
    	String sqlapp = " select c.*,a.user_name  from OCRM_F_CI_MKT_CALL_P c " +
    			"left join admin_auth_account a on c.user_id = a.account_name  where 1=1  ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    		addOracleLookup("JOB_TYPE", "PAR0400044");
    		addOracleLookup("CUST_SOURCE", "CUST_SOURCE");
    		addOracleLookup("IF_NEW", "IF_FLAG");
    		addOracleLookup("CALL_RESULT", "CALL_RESULT");
    	}else{
    		sb.append("and user_id='"+auth.getUserId()+"' ");
        	for (String key : this.getJson().keySet()) {// 查询条件判断
			if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
				if (key.equals("PHONE_DATE")) {
					sb.append(" AND c.PHONE_DATE =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
				} else if(key.equals("CUST_ID")||"USER_ID".equals(key)||"JOB_TYPE".equals(key)||"CALL_RESULT".equals(key)||"CHECK_STAT".equals(key)||"USER_ID".equals(key)){
					sb.append(" AND c."+key+" = '"+this.getJson().get(key)+"'");
				}else if(key.equals("CALL_INFO")){
					sb.append(" AND c."+key+" like '%"+this.getJson().get(key)+"%'");
				}
			}
		}
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	((OcrmFCiMktCallP)model).setPhoneDate(new Date());
    	((OcrmFCiMktCallP)model).setUserId(auth.getUserId());
    	((OcrmFCiMktCallP)model).setCheckStat("2");
    	((OcrmFCiMktCallP)model).setRecordDate(new Date());
    	((OcrmFCiMktCallP)model).setUpdateDate(new Date());
    	
    	service.save(model);
    	
    	Long callId = ((OcrmFCiMktCallP)model).getId();
    	String result = ((OcrmFCiMktCallP)model).getCallResult();
    	if("1".equals(result)){//如果同意拜访  生成拜访信息
    		OcrmFCiMktVisitP visit = new OcrmFCiMktVisitP();
    		visit.setCallId(callId.toString());
    		visit.setCustId(((OcrmFCiMktCallP)model).getCustId());
    		visit.setCustName(((OcrmFCiMktCallP)model).getCustName());
    		visit.setJobType(((OcrmFCiMktCallP)model).getJobType());
    		visit.setVisitDate(((OcrmFCiMktCallP)model).getVisitDate());
    		visit.setIfNew(((OcrmFCiMktCallP)model).getIfNew());
    		visit.setUserId(auth.getUserId());
    		visit.setCheckStat("1");
    		visit.setRecordDate(new Date());
    		visit.setUpdateDate(new Date());
    		
    		vservice.save(visit);
    	}
    	
    	//初始化流程
    	
    	String name = ((OcrmFCiMktCallP)model).getCustName();
		String instanceid = "CALL_"+callId+"_1";//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
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
    
    
}
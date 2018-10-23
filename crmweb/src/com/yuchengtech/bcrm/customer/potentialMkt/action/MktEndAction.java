package com.yuchengtech.bcrm.customer.potentialMkt.action;

import java.util.Date;
import java.util.HashMap;
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
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktEndP;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktEndPService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 个金客户营销流程 -  结案  luyy  2014-07-24
 */

@SuppressWarnings("serial")
@Action("/mktEndP")
public class MktEndAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiMktEndPService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktEndP();  
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
    	String sqlapp = " select c.*,a.user_name  from OCRM_F_CI_MKT_END_P c " +
    			"left join admin_auth_account a on c.user_id = a.account_name  where 1=1  ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    		addOracleLookup("IF_DEAL", "IF_FLAG");
    	}else{
    		sb.append("and user_id='"+auth.getUserId()+"' ");
        	for (String key : this.getJson().keySet()) {// 查询条件判断
			if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
				if(key.equals("CUST_ID")||"IF_DEAL".equals(key)||"CHECK_STAT".equals(key)||"PRODUCT_ID".equals(key)){
					sb.append(" AND c."+key+" = '"+this.getJson().get(key)+"'");
				}else if(key.equals("BUT_AMT")){
					sb.append(" AND c.BUY_AMT = "+this.getJson().get(key)+"");
				}else if(key.equals("AREA_NAME")){
					sb.append(" AND c.AREA_ID = "+this.getJson().get(key)+"");
				}else if(key.equals("DEPT_NAME")){
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
    	
    	Long id = ((OcrmFCiMktEndP)model).getId();
    	OcrmFCiMktEndP visit = (OcrmFCiMktEndP)service.find(id);
    	visit.setCheckStat("2");
    	visit.setIfDeal(((OcrmFCiMktEndP)model).getIfDeal());
    	visit.setBuyAmt(((OcrmFCiMktEndP)model).getBuyAmt());
    	visit.setIfHasProd(((OcrmFCiMktEndP)model).getIfHasProd());
    	visit.setDealDate(((OcrmFCiMktEndP)model).getDealDate());
    	visit.setAccountDate(((OcrmFCiMktEndP)model).getAccountDate());
    	visit.setRefuseReason(((OcrmFCiMktEndP)model).getRefuseReason());
    	visit.setUpdateDate(new Date());
    	
    	service.save(visit);
    	
		String instanceid = "END_"+id+"_15";//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "结案复核_"+visit.getCustName();//自定义流程名称
		service.initWorkflowByWfidAndInstanceid("42", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		
		 Map<String,Object> map1=new HashMap<String,Object>();
			map1.put("instanceid", instanceid);
		    map1.put("currNode", "42_a3");
		    map1.put("nextNode",  "42_a4");
		    this.setJson(map1);
    	
    	return new DefaultHttpHeaders("success");
    }
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for(String id : ids){
    		service.batchUpdateByName(" delete from OcrmFCiMktEndP g where g.id='"+Long.parseLong(id)+"'", new HashMap());
    	}
    }
}
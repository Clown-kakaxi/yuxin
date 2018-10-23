package com.yuchengtech.bcrm.customer.level.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.level.model.AcrmACiCardApply;
import com.yuchengtech.bcrm.customer.level.service.AcrmACiCardApplyService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 贵宾卡发卡申请处理类  luyy  2014-07-21
 * 20140828,helin,configCondition bug fixed
 */
@SuppressWarnings("serial")
@Action("/cardApply")
public class AcrmACiCardApplyAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private AcrmACiCardApplyService service ;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
    	model = new AcrmACiCardApply();  
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
    	String sqlapp = " select c.* from ACRM_A_CI_CARD_APPLY c  where 1=1  ";
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if(id != null &&!"".equals(id)){//流程查询
    		sb.append(" and c.id = '"+id+"' ");
    		
    		SQL=sb.toString();
    		
    		addOracleLookup("CUST_GRADE", "PRE_CUST_LEVEL");
    		addOracleLookup("CARD_LVL", "GOODS_CUST_LEVEL");
    		addOracleLookup("H_CARD_LEV", "GOODS_CUST_LEVEL");
    		addOracleLookup("CARD_LEV_APP", "GOODS_CUST_LEVEL");
    	}else{
    		sb.append(" and c.user_id = '"+auth.getUserId()+"' ");
        	
        	for (String key : this.getJson().keySet()) {// 查询条件判断
    			if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
    				if (key.equals("CARD_APP_VALIDATE")) {
    					sb.append(" AND c.CARD_APP_VALIDATE =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
    				} else if(key.equals("CARD_LVL")){
    					String levels[] = this.getJson().get(key).toString().split(",");
    					if(levels.length>0){
    						for(int i=0;i<levels.length;i++){
    							if(i == 0){
    								sb.append(" AND (c.CARD_LVL  like '%" + levels[0]+ "%'");
    							}else{
    								sb.append(" or c.CARD_LVL   like '%" + levels[i]+ "%'");
    							}
    						}
    						
    					}
    					sb.append(" )");
    				}
    			}
    		}
        	
        	SQL=sb.toString();
        	
       		setPrimaryKey("c.ID desc ");
       		configCondition("c.CUST_ID","like","CUST_ID",DataType.String);
    		configCondition("c.CUST_NAME","like","CUST_NAME",DataType.String);
    		configCondition("c.IDENT_TYPE","=","IDENT_TYPE",DataType.String);
    		configCondition("c.IDENT_NO","like","IDENT_NO",DataType.String);
    		configCondition("c.AMT_AVG_30DAYS","=","AMT_AVG_30DAYS",DataType.Number);
    		configCondition("c.CUST_GRADE","=","CUST_GRADE",DataType.String);
    		configCondition("c.CARD_NUM","=","CARD_NUM",DataType.Number);
    		configCondition("c.H_CARD_LEV","=","H_CARD_LEV",DataType.String);
    		configCondition("c.CARD_LEV_APP","=","CARD_LEV_APP",DataType.String);
    		configCondition("c.CARD_APP_STATUS","=","CARD_APP_STATUS",DataType.String);
    		configCondition("c.GET_WAY","=","GET_WAY",DataType.String);
    	}
    	datasource = ds;
	}
    
    
    public DefaultHttpHeaders saveData() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	((AcrmACiCardApply)model).setUserId(auth.getUserId());
    	((AcrmACiCardApply)model).setApplyDate(new Date());
    	((AcrmACiCardApply)model).setCardAppStatus("1");
    	service.save(model);
    	
    	Long id = ((AcrmACiCardApply)model).getId();
    	String name = ((AcrmACiCardApply)model).getCustName();
		String instanceid = "CARD_"+id;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "发卡申请_"+name;//自定义流程名称
		
		service.initWorkflowByWfidAndInstanceid("35", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceid", instanceid);
		map.put("currNode", "35_a3");
		map.put("nextNode",  "35_a4");
		this.setJson(map);
    	
    	return new DefaultHttpHeaders("success");
    }
    
    public void getInfo() throws IOException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	String info = "";
    	List<Object[]> list1 = service.getBaseDAO().findByNativeSQLWithIndexParam(" SELECT AMT_AVG_30DAYS,CUST_GRADE,CARD_LVL,CARD_NUM,H_CARD_LEV FROM ACRM_A_CI_VIP_CUST WHERE CUST_ID='"+custId+"'");
    	if (list1 != null && list1.size() > 0) {
			Object[] o = list1.get(0);
			info = o[0]+"#"+o[1]+"#"+o[2]+"#"+o[3]+"#"+o[4] ;
    	}
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
    	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(info);
		response.getWriter().flush();
    	
    }
}
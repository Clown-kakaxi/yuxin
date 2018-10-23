package com.yuchengtech.bcrm.customer.potentialMkt.action;


import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktEndP;
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktEndPService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 企商金客户营销流程 -  Pipeline  luyy  2014-07-26
 */

@SuppressWarnings("serial")
@Action("/mktPipelineC")
public class MktPipelineCAction  extends CommonAction{
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
    	String sqlapp = " select c.*  from OCRM_F_CI_MKT_PIPELINE_C c where 1=1  ";
    	
    	StringBuilder sb  = new StringBuilder(sqlapp);
        	for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if(key.equals("CUST_ID")||"CASE_TYPE".equals(key)||"COMP_TYPE".equals(key)){
						sb.append(" AND c."+key+" = '"+this.getJson().get(key)+"'");
					}else if(key.equals("APPLY_AMT")){
						sb.append(" AND c."+key+" = "+this.getJson().get(key)+"");
					}else if(key.equals("AREA_NAME")){
						sb.append(" AND c.AREA_ID = "+this.getJson().get(key)+"");
					}else if (key.equals("VISIT_DATE")) {
						sb.append(" AND c."+key+" =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
					}else if(key.equals("DEPT_NAME")){
						sb.append(" AND c."+key+" like '%"+this.getJson().get(key)+"%'");
					}
				}
        	}
    		setPrimaryKey("c.ID desc ");
        	SQL=sb.toString();
        	datasource = ds;
	}

    
    
    
}
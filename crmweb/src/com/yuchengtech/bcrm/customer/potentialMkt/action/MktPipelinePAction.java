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
 * 个金客户营销流程 -  Pipeline  luyy  2014-07-24
 */

@SuppressWarnings("serial")
@Action("/mktPipelineP")
public class MktPipelinePAction  extends CommonAction{
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
    	String sqlapp = " select c.*,a.user_name,t.catl_name as product_catl_name  from OCRM_F_CI_MKT_PIPELINE_P c " +
    			"left join admin_auth_account a on c.user_id = a.account_name left join OCRM_F_PD_PROD_CATL t on" +
    			" c.product_catl = t.catl_code   where 1=1  ";
    	
    	StringBuilder sb  = new StringBuilder(sqlapp);
        	for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if("ID_DEAL".equals(key)||"CHECK_STAT".equals(key)||"PRODUCT_ID".equals(key)
							||"RISK_LEVEL_PERSECT".equals(key)||"RISK_LEVEL".equals(key)){
						sb.append(" AND c."+key+" = '"+this.getJson().get(key)+"'");
					}else if(key.equals("SALE_AMT")||"BUY_AMT".equals(key)){
						sb.append(" AND c."+key+" = "+this.getJson().get(key)+"");
					}else if(key.equals("AREA_NAME")){
						sb.append(" AND c.AREA_ID = "+this.getJson().get(key)+"");
					}else if (key.equals("VISIT_DATE")||"DEAL_DATE".equals(key)||"ACCOUNT_DATE".equals(key)) {
						sb.append(" AND c."+key+" =  to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
					}else if(key.equals("DEPT_NAME")||"RM".equals(key)||
							"HARD_INFO".equals(key)||"REFUSE_REASON".equals(key)){
						sb.append(" AND c."+key+" like '%"+this.getJson().get(key)+"%'");
					}else if(key.equals("PRODUCT_CATL_NAME")){
						sb.append(" AND c.PRODUCT_CATL = '"+this.getJson().get(key)+"'");
					}
				}
        	}
    		setPrimaryKey("c.ID desc ");
        	SQL=sb.toString();
        	datasource = ds;
	}

    
    
    
}
package com.yuchengtech.bcrm.finService.action;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.finService.model.OcrmFFinCustDemand;
import com.yuchengtech.bcrm.finService.service.CustDemandService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;

@Action("/custDemand")
public class CustDemandAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustDemandService custDemandService;
	
	@Autowired
	public void init(){
		model = new OcrmFFinCustDemand();
		setCommonService(custDemandService);
	}
	
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("select t.* from OCRM_F_FIN_CUST_DEMAND t where t.cust_id = '"+custId+"'");
		
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
     * 生成报告文件
	 * @throws Exception 
     */
    public void createReport() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String demandId = request.getParameter("demandId");
        custDemandService.createReport(demandId);
    }
    
    public String queryConfigProdType(){
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String demandId = request.getParameter("demandId");
        String riskType = request.getParameter("riskType");
        String sql = "SELECT I.F_VALUE,t1.prod_rate as prod_rate1, NVL(SUM(T.CONF_SCALE), 0) prod_rate2  FROM OCRM_SYS_LOOKUP_ITEM I"
        		+ " left join OCRM_F_FIN_TEMPLATE t1 on t1.prod_type = i.f_code and t1.risk_type='"+riskType+"'"
        		+ " LEFT JOIN (SELECT P.PROD_TYPE_ID, ROUND(100*C.CONF_SCALE/c1.total,2) CONF_SCALE"
        		+ " FROM OCRM_F_FIN_PROD_CONF C  LEFT JOIN OCRM_F_PD_PROD_INFO P ON P.PRODUCT_ID = C.PROD_ID "
        		+ " left join (select DEMAND_ID,nvl(sum(CONF_SCALE),1) total from OCRM_F_FIN_PROD_CONF group by DEMAND_ID)  c1 on c1.DEMAND_ID = c.demand_id"
        		+ " WHERE C.DEMAND_ID = '"+demandId+"' ) T ON TO_CHAR(T.PROD_TYPE_ID) = I.F_CODE WHERE I.F_LOOKUP_ID = 'PROD_TYPE_ID' GROUP BY I.F_VALUE,t1.prod_rate";
        QueryHelper query;
		try {
			query = new QueryHelper(sql,ds.getConnection());
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
	    	this.json.put("json",query.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return "success";
    }
    
    public String queryConfigProd(){
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String demandId = request.getParameter("demandId");
        String sql = "select i.f_value PROD_TYPE_ID,p.prod_name,nvl(sum(c.conf_scale),0) PROD_SCALE,round(100*nvl(sum(c.conf_scale),0)/c2.total,2) PROD_RATE from OCRM_F_FIN_PROD_CONF c "
				+" left join ocrm_f_pd_prod_info p on p.product_id = c.prod_id "
				+" left join ocrm_sys_lookup_item i on i.f_code = p.prod_type_id and i.f_lookup_id = 'PROD_TYPE_ID' "
				+" left join (select c1.demand_id,nvl(sum(c1.conf_scale),1) as total from OCRM_F_FIN_PROD_CONF c1 where c1.demand_id = '"+demandId+"' group by c1.demand_id) c2 on c2.demand_id = c.demand_id "
				+" where c.demand_id = '"+demandId+"' group by i.f_value,p.prod_name,c2.total";
        QueryHelper query;
		try {
			query = new QueryHelper(sql,ds.getConnection());
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
	    	this.json.put("json",query.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return "success";
    }
    
    public String queryConfigChart(){
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String demandId = request.getParameter("demandId");
        String sql = "SELECT I.F_VALUE,NVL(SUM(T.CONF_SCALE),0) CONF_SCALE FROM OCRM_SYS_LOOKUP_ITEM I LEFT JOIN (SELECT P.PROD_TYPE_ID,C.CONF_SCALE FROM OCRM_F_FIN_PROD_CONF C LEFT JOIN OCRM_F_PD_PROD_INFO P ON P.PRODUCT_ID = C.PROD_ID WHERE C.DEMAND_ID = '"+demandId+"') T ON TO_CHAR(T.PROD_TYPE_ID) = I.F_CODE WHERE I.F_LOOKUP_ID = 'PROD_TYPE_ID' GROUP BY I.F_VALUE";
        QueryHelper query;
		try {
			query = new QueryHelper(sql,ds.getConnection());
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
	    	this.json.put("json",query.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return "success";
    }
}

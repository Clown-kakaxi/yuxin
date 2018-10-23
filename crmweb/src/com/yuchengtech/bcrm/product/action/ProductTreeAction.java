package com.yuchengtech.bcrm.product.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.ProdInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.constance.SystemConstance;
/***
 * 产品查询处理的 action
 * @author luyueyue  06-27
 *
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="product-list", results={@Result(name="success", type="json")})
public class ProductTreeAction  extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	@Autowired
	private ProdInfoService service;
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		boolean ifAll = true;//是否查询全部产品
		String CATL_CODE = "";
		String userType=request.getParameter("user_type");
    	StringBuffer sb  = new StringBuffer("select a.*,b.catl_name, " +
    			"  (case when (select count(*) from OCRM_F_PD_PROD_TC_SET tc where tc.product_id = a.product_id )>0 then 1 else 0 end ) as TARGET_CUST_NUM " );//查询是否设定过目标客户
		if("ORACLE".equals(SystemConstance.DB_TYPE)){		
			sb.append(" from ocrm_f_pd_prod_info a inner join ocrm_f_pd_prod_catl_view b on a.catl_code=b.catl_code where 1=1 ");
			if("1".equals(userType)){
				sb.append("  and  (a.type_fit_cust ='1' or a.type_fit_cust='1,2')");
			}
		}else if("DB2".equals(SystemConstance.DB_TYPE)){
			sb.append(" from ocrm_f_pd_prod_catl_view f,ocrm_f_pd_prod_info a inner join ocrm_f_pd_prod_catl_view b on a.catl_code=b.catl_code where 1=1 ");			
		}
    	for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if(null!=key&&key.equals("CATL_CODE")&&!"0".equals(this.json.get("CATL_CODE"))){
					ifAll = false;
					CATL_CODE = this.getJson().get(key).toString();
					if("ORACLE".equals(SystemConstance.DB_TYPE)){
						sb.append("  and b.catlseq like (select catlseq from ocrm_f_pd_prod_catl_view where catl_code="+this.getJson().get(key)+")||'%'");
					}else if("DB2".equals(SystemConstance.DB_TYPE)){
						sb.append(" and  f.catl_code="+this.getJson().get(key)+" and locate(f.catlseq,b.catlseq)>0");
					}
					
				}else if(null!=key&&key.equals("PRODUCT_ID")){
					sb.append("  and a.PRODUCT_ID like '%"+this.getJson().get(key)+"%'  ");
				}else if(null!=key&&key.equals("PROD_NAME")){
					sb.append("  and a.PROD_NAME like '%"+this.getJson().get(key)+"%'  ");
				}
				else if("PROD_START_DATE".equals(key)){
					sb.append(" and  a.PROD_START_DATE= to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')" );
				}else if("PROD_END_DATE".equals(key)){
					sb.append("  and  a.PROD_END_DATE= to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')");
                }else if("PROD_STATE".equals(key)){
                	sb.append(" and a.PROD_STATE = '"+ this.getJson().get(key) +"'");
                }
    		}
		}
    	
    	//根据查询的产品类别  查询相应的展示方案 以添加数据字典转换
    	if(ifAll){
    		addOracleLookup("RISK_LEVEL","PROD_RISK_LEVEL");
    	}else{
    		String sql = "select  column_name, COLUMN_TYPE,DIC_NAME from OCRM_F_PD_PROD_COLUMN t where to_char(t.column_id) in " +
			"(select COLUMN_ID from OCRM_F_PD_PROD_SHOW_COLUMN where plan_id = (select PROD_VIEW from OCRM_F_PD_PROD_CATL where CATL_CODE='"+CATL_CODE+"'))";
			List<Object[]> list = service.getBaseDAO().findByNativeSQLWithIndexParam(sql);
			if(list.size()>0){
				for (Object[] o : list) {
					if("2".equals(o[1].toString())){//数据字典
						this.addOracleLookup( o[0].toString(), o[2].toString());
					}
				}
			}
    	}
    	addOracleLookup("TARGET_CUST_NUM","IF_FLAG");
    	setPrimaryKey("TO_NUMBER(A.PRODUCT_ID)");
    	SQL=sb.toString();
    	datasource = ds;
        	
    }
	
	 //查询产品类别字典
    public String searchPlan()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select CATL_NAME as value,CATL_CODE as key from OCRM_F_PD_PROD_CATL ");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
    //查询默认的展示方案
    public void findViewId() throws IOException{
    	 ActionContext ctx = ActionContext.getContext();
    	 String result = "";
    	 List<Object[]> list =  service.getBaseDAO().findByNativeSQLWithIndexParam(" select count(1) as num,PROD_VIEW from OCRM_F_PD_PROD_CATL group by PROD_VIEW  " +
    	 		"having PROD_VIEW is not null order by num desc");
    	    if(list != null && list.size()>0){
    	    	result = "yes#"+list.get(0)[1].toString();
    	    }else{
    	    	result = "no#";
    	    }
    		HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
 		 	response.setCharacterEncoding("UTF-8");
 			response.getWriter().write(result);
 			response.getWriter().flush();
    }
}

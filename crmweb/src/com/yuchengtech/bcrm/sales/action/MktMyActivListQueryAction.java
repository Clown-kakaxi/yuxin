package com.yuchengtech.bcrm.sales.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 我的营销活动查询
 * hujun 
 * 2014-07-04
 */
@ParentPackage("json-default")
@Action(value="/MktMyActiListQueryAction", results={
    @Result(name="success", type="json"),
})
public class MktMyActivListQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	 	 
	public void prepare() {
		// TODO Auto-generated method stub
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	StringBuilder sb = new StringBuilder();
    	String cuts="(select count(s.cust_id) from OCRM_F_MK_ACTI_CUSTOMER s left join ocrm_f_mk_mkt_my_acti tt on tt.cust_id=s.cust_id where " +
    			"s.mkt_acti_id = t.mkt_acti_id and tt.executor_id='"+auth.getUserId()+"' and tt.mkt_acti_id=t.mkt_acti_id)";
    	sb.append(" select t.*," +cuts+" as CUST_NUM," +"(select count(p.product_id) from OCRM_F_MK_ACTI_PRODUCT p where p.mkt_acti_id=t.mkt_acti_id) as PRODUCT_NUM from ocrm_f_mk_mkt_activity t where 1>0 and "+cuts+">0 ");
    	
		 for(String key:this.getJson().keySet()){
			 if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
		         if(key.equals("MY_ACTI_NAME"))
		             sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
		         else if(key.equals("MKT_ACTI_TYPE"))
		             sb.append(" and t."+key+"= '"+this.getJson().get(key)+"'");
		         else if(key.equals("CREATE_DATE"))
		        	 sb.append(" and t."+key+"= to_date('" +this.getJson().get(key).toString().substring(0, 10)+"','yyyy-MM-dd')");
		         else if(key.equals("MKT_ACTI_STAT"))
		             sb.append(" and t."+key+"= '"+this.getJson().get(key)+"'");
		         else{
	             	sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
	             }
			 }
		 }
		 setPrimaryKey("t.CREATE_DATE desc ");
		 SQL=sb.toString();
		 datasource = ds;
	}
    
}

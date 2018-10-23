package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/queryattentioncustomer", results={
    @Result(name="success", type="json")
})
public class QueryAttentionCustomerAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
    public void prepare() {
		StringBuilder s = new StringBuilder("select t1.CUST_ID,t1.id,t1.create_date,cust_info.cust_zh_name," + 
	    		"cust_info.CERT_NUM,cust_info.cust_lev, " +
	    		"1000000 as ck,5000000 as dk " +
	    		"from ocrm_f_ci_attention_Cust_Info t1 inner join OCRM_F_CI_CUST_DESC cust_info " +
	    		"on t1.CUST_ID = cust_info.CUST_ID   where   ");
	    AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
             s.append(" t1.user_id='" + currenUserId+ "' ");
		   for(String key:this.getJson().keySet()){
               if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
//                if(key.equals("cust_name")||key.equals("cust_zzdm")||key.equals("hy_class"))
            	   if(key.equals("CUST_ZH_NAME")||key.equals("cust_id"))
                       s.append(" and cust_info."+key+" like"+" '%"+this.getJson().get(key)+"%'");
                else  if(key.equals("HY_CLASS")||key.equals("CUST_LEV")||key.equals("CUST_SCOPE")||key.equals("STS")||key.equals("CUST_BIG_LEV")||key.equals("HY_CLASS")||key.equals("CUST_SMALL_LEV")||key.equals("CUST_LEV"))
                {
                	s.append(" and cust_info."+key+"='"+this.getJson().get(key)+"'");
                }
            	 
           }}

			setPrimaryKey("t1.id");


//     	   addGreenplumLookup("CUST_SCOPE", "QYGM");
//     	   addGreenplumLookup("HY_CLASS", "HYFL");

     
      	   
      	   addOracleLookup("CUST_LEV", "CUST_LEVEL4");
//      	   addOracleLookup("CUST_BIG_LEV", "CUST_LEVEL1");
//      	   addOracleLookup("CUST_SMALL_LEV", "CUST_LEVEL2");
      	   SQL=s.toString();
	       datasource = ds;
      
    }

}

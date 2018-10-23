package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/queryCustomerBaseGrades", results={
    @Result(name="success", type="json")
})
public class QueryCustomerBaseGradesAction extends CommonAction{
 
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
    public void prepare() {

	    StringBuilder sb = new StringBuilder("select t1.* from acrm_f_ci_cust_info t1 where 1>0");
	    for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
             if(key.equals("CUST_ZH_NAME"))
                    sb.append(" and CUST_ZH_NAME like"+" '%"+this.getJson().get(key)+"%'");
             else if(key.equals("CUST_ZZDM"))
                 sb.append(" and CUST_ZZDM like"+" '%"+this.getJson().get(key)+"%'");
             else if(key.equals("BELONG_INSTN"))
                 sb.append(" and BELONG_INSTN like"+" '%"+this.getJson().get(key)+"%'");
        }}
			setPrimaryKey("t1.id");
			setBranchFileldName("t1.BELONG_INSTN");
	        SQL=sb.toString();
	        datasource = ds;

    }
}

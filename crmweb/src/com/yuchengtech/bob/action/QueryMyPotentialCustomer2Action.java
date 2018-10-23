package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/querymypotentialcustomer2", results={
    @Result(name="success", type="json")
})
public class QueryMyPotentialCustomer2Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Override
	public void prepare() {
		StringBuilder s = new StringBuilder("select * from OCRM_F_CI_POTENTIAL_CUST");
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				if(key.equals("CUST_NAME")&&key.equals("CUST_ORG_NO"))
					s.append(" and "+key+" like"+" '%"+this.getJson().get(key)+"%'");
				else if(key.equals("CUST_SCALE")&&!this.getJson().get(key).equals("0"))
					s.append(" and "+key+"="+" '"+this.getJson().get(key)+"'");
			}
		}
		SQL=s.toString();
		datasource = ds;
    }
}

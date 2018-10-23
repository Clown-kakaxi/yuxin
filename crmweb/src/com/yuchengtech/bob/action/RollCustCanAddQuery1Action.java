package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
				
@Action(value="/rollCustCanAddQuery1", results={
    @Result(name="success", type="json")
})
public class RollCustCanAddQuery1Action extends CommonAction{
    
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
    public void prepare() {
		StringBuilder s;
	    s = new StringBuilder("select cust_id,cust_zh_name,INSTITUTION_NAME,MGR_NAME from  ocrm_f_ci_cust_view t where 1=1 " );
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                    if(key.equals("cust_zh_name")||key.equals("cust_id"))
                      s.append(" and t."+key+" like"+" '%"+this.getJson().get(key)+"%'");
                    if (key.equals("msFlag")){
                        if(this.getJson().get(key).equals("1")){
                            s.append(" and t.CRE_MS_FLG like '%CRM_YN_002%' ");
                        }
                     }
               }}
        SQL = s.toString();
        setPrimaryKey("t.cust_id");
        datasource=ds;
    }



}

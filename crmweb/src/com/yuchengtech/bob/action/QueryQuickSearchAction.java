package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/queryquicksearch", results={
    @Result(name="success", type="json"),
})
public class QueryQuickSearchAction extends CommonAction{
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Override
    public void prepare() {
        StringBuilder sb = new StringBuilder("select cust_id,cust_zh_name,cust_en_name,cert_num as cust_zzdm from OCRM_F_CI_CUST_DESC where 1>0 ");
        String mod = (String) this.getJson().get("mod");
        String name = (String) this.getJson().get("name");
//        String custID = (String) this.getJson().get("custID");
        
        if("quick".equals(mod)){
        	if(name!=""&&name!=null){
        		 sb.append("and cust_zh_name like '%"+name+"%' or cert_num like '%"+name+"%'");
        	}
//        	if(custID!=""&&custID!=null){
////        		 sb.append("where cust_zzdm like '%"+custID+"%' or or cert_num like '%"+name+"%'");
//        		 sb.append("and cert_num like '%"+custID+"%'");
//        	}
           
           
        }
        else if("all".equals(mod)){
            /**
             * Blob field
             */
            sb.append("and cust_detail like '%"+name+"%'");
        }
        SQL=sb.toString();
        setPrimaryKey("cust_id");
        datasource=ds;
        //setBranchFileldName("BELONG_INSTN");
    }
}

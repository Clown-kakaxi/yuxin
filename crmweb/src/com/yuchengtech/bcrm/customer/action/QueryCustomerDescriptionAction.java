package com.yuchengtech.bcrm.customer.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/querycustomerdescription", results={
    @Result(name="success", type="json")
})
public class QueryCustomerDescriptionAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
    public void prepare() {
	    StringBuilder s = new StringBuilder("select t1.* from OCRM_F_CI_CUST_DESC t1 where 1>0");
	    for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("CUST_ZH_NAME"))
                    s.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                else if(key.equals("CERT_NUM"))
                	s.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                else if(key.equals("CUST_TYP"))
                	s.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
            }
        }
/*	    int currentPage =this.getStart()/this.getLimit()+1;
        PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
		QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi);*/
//        setBranchFileldName("t1.BELONG_INSTN");
        
        SQL=s.toString();
        setPrimaryKey("t1.CUST_ID");
          datasource = ds;
    }



}

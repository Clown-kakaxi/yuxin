package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/querycustomerquery2", results={
    @Result(name="success", type="json")
})
public class QueryCustomerQuery2Action extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
	@Override
    public void prepare() {
        	StringBuilder s = new StringBuilder
        	   ("select t1.cust_id,t1.cust_zh_name,t1.cust_zzdm,t1.HY_CLASS,t1.sts,t1.CUST_SCOPE,t1.CRM_SCOPE,t1.cust_lev from V_ACRM_F_CI_CUST_INFO t1 where 1>0 ");
        	   for(String key:this.getJson().keySet()){
                   if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                    if(key.equals("CUST_ZH_NAME")||key.equals("CUST_ZZDM")||key.equals("HY_CLASS")||key.equals("BELONG_INSTN")||key.equals("JJZZ_TYP")||key.equals("CREATE_ORG"))
                           s.append(" and t1."+key+" like"+" '%"+this.getJson().get(key)+"%'");
                    else if(key.equals("CRM_SCOPE")||key.equals("STS")||key.equals("CUST_LEV")||key.equals("HY_TYP_DESC")||key.equals("CUST_SCOPE"))
                        s.append(" and t1."+key+"="+" '"+this.getJson().get(key)+"'");
                    else if((key.equals("CUST_BASE_NAME")))
                        s.append("and  exists (select t4.cust_id from ocrm_f_ci_relate_cust_base t4 where t1.cust_id=t4.cust_id   and t4.CUST_BASE_ID='"+this.getJson().get(key)+"')");
                    else if((key.equals("ROLL_NAME")))
                        s.append("and exists (select t5.cust_id from ocrm_f_mm_rcust_list t5 where t1.cust_id=t5.cust_id  and t5.roll_id='"+this.getJson().get(key)+"')");
               }}
          	/*   int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi);*/
        	   setPrimaryKey("t1.cust_id");
        	   /*
        	   HashMap<String, String> GreenplumMapping = new HashMap<String, String>();*/
        	    
//        	  addGreenplumLookup("CUST_SCOPE", "QYGM");
//        	  addGreenplumLookup("CRM_SCOPE", "KHQYGM");
//        	  addGreenplumLookup("HY_CLASS", "HYFL");
        /*	   if (!GreenplumMapping.isEmpty()) {
                   for(Entry<String, String> item : GreenplumMapping.entrySet()) {
                       qh.addGreenplumLookup(item.getKey(), item.getValue());
                   }
               }*//*
               HashMap<String, String> oracleMapping = new HashMap<String, String>();*/
        	   
        	  addOracleLookup("CUST_LEV", "CUST_LEVEL4");
        	/*   if (!oracleMapping.isEmpty()) {
                   for(Entry<String, String> item : oracleMapping.entrySet()) {
                       qh.addOracleLookup(item.getKey(), item.getValue());
                   }
               }*/
        	  setBranchFileldName("t1.BELONG_INSTN");
        	  SQL=s.toString();
  	           datasource = ds;
    }
}
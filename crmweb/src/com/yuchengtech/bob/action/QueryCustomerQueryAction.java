package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/querycustomerquery", results={
    @Result(name="success", type="json")
})
public class QueryCustomerQueryAction extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
	@Override
    public void prepare() {
        	StringBuilder s = new StringBuilder
        	   ("select t1.*" +
        	   		" from V_ACRM_F_CI_CUST_INFO1 t1 " +
        	   		"where 1>0 ");
        	   for(String key:this.getJson().keySet()){
                   if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                    if(key.equals("CUST_ZH_NAME")||key.equals("CUST_ZZDM")||key.equals("BELONG_INSTN")||key.equals("JJZZ_TYP")||key.equals("CREATE_ORG")||key.equals("CUST_LEV")||key.equals("HY_TYP_DESC"))
                           s.append(" and t1."+key+" like"+" '%"+this.getJson().get(key)+"%'");
                    else if(key.equals("CRM_SCOPE")||key.equals("STS")||key.equals("CUST_BIG_LEV")||key.equals("CUST_SMALL_LEV")||key.equals("HY_CLASS")||key.equals("SPECIFY_FLG")||key.equals("EMPLOYEE_NUM")||key.equals("HY_TYP2")||key.equals("ASS_AMT")||key.equals("HY_TYP3")||key.equals("BUSINESS_JSR")||key.equals("GROUP_FLG")||key.equals("HN_FLG")||key.equals("CUST_SCOPE")||key.equals("MARKET_FLG"))
                        s.append(" and t1."+key+"="+" '"+this.getJson().get(key)+"'");
                    else if(key.equals("HY_CLASS2"))
                        s.append(" and t1.HY_CLASS="+" '"+this.getJson().get(key)+"'");
                    else if((key.equals("CUST_BASE_NAME")))
                        s.append("and  exists (select t4.cust_id from ocrm_f_ci_relate_cust_base t4 where t1.cust_id=t4.cust_id   and t4.CUST_BASE_ID='"+this.getJson().get(key)+"')");
                    else if((key.equals("ROLL_NAME")))
                        s.append("and exists (select t5.cust_id from ocrm_f_mm_rcust_list t5 where t1.cust_id=t5.cust_id  and t5.roll_id='"+this.getJson().get(key)+"')");
                    else if((key.equals("MATURE_DT"))){
                    	s.append(" and t1.MATURE_DT <="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                    }
                    else if((key.equals("ZZDM_VALDT"))){
                    	s.append(" and t1.ZZDM_VALDT <="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                    }
                    else if((key.equals("BGN_DT_BEFORE"))){
                    	s.append(" and t1.BGN_DT >="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                    }
                    else if((key.equals("BGN_DT_AFTER"))){
                    	s.append(" and t1.BGN_DT <="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                    }
               }}
        	   setPrimaryKey("t1.cust_id");
//        	   addGreenplumLookup("STS", "CIFKHZT");
//        	   addGreenplumLookup("CRM_SCOPE", "KHQYGM");
//        	   addGreenplumLookup("CUST_SCOPE", "QYGM");
//        	   addGreenplumLookup("GROUP_FLG", "YN");
//        	   addGreenplumLookup("REGION", "XZQY");
//        	   addGreenplumLookup("COUNTRY", "GJDQ");
//        	   addGreenplumLookup("HY_TYP3", "HYLX2");
//        	   addGreenplumLookup("HY_TYP2", "HYLX1");
//        	   addGreenplumLookup("MARKET_FLG", "YN");
//        	   addGreenplumLookup("SPECIFY_FLG", "YN");
//        	   addGreenplumLookup("HN_FLG", "YN");
//        	   addGreenplumLookup("ASS_AMT", "XSZE");
//        	   addGreenplumLookup("BUSINESS_JSR", "XSE");
//        	   addGreenplumLookup("EMPLOYEE_NUM", "CYRYS");
//        	   addGreenplumLookup("CPTL_CUR", "CCY");
//        	   addGreenplumLookup("HY_CLASS", "HYFL");
//        	   addGreenplumLookup("JJZZ_TYP", "JJZZLX");
        	   addOracleLookup("CUST_LEV", "CUST_LEVEL4");
        	   addOracleLookup("CUST_BIG_LEV", "CUST_LEVEL1");
        	   addOracleLookup("CUST_SMALL_LEV", "CUST_LEVEL2");
//        	   setBranchFileldName("t1.cust_id");
        	   SQL=s.toString();
   	           datasource = ds;
    }
}
package com.yuchengtech.bcrm.custview.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;
/*
 * 
 * 
 *    mahcao  客户搜索
 * 
 */
@ParentPackage("json-default")
@Action(value="/customerCredita", results={
    @Result(name="success", type="json"),
})
public class CustomerCreditaAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
 	public void prepare() {
 		
		// TODO Auto-generated method stub
 		String s=this.getJson().get("custId").toString();
// 		AcrmFCiPerCustInfo asd=new AcrmFCiPerCustInfo();
//      AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
//      Long managerId = auth.getXtStaffId();
      
    	StringBuilder sb=new StringBuilder("select c.* from ACRM_F_CI_CRE_CONTRACT c where 1>0 ");
        sb.append(" and c.CUSTOM_ID = '"+s+"'");
        setPrimaryKey("c.CUSTOM_ID");
        addOracleLookup("CURR_NO", "ACC1300012");
        addOracleLookup("AWARD_KIND", "CDE0100008");
        addOracleLookup("LINE_TYP", "CDE0100007");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

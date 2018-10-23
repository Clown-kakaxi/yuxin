package com.yuchengtech.bcrm.baobiao.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/custMgrVipCustNumZbSum", results={
    @Result(name="success", type="json"),
})
public class custMgrVipCustNumSumAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
 	public void prepare() {
		// TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder("select ci.* from ACRM_M_VIP_NUM_RATE_COUNT ci where CUST_MANAGER_ID IS NOT NULL" );
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("MONTH"))
                    sb.append(" and ci.MONTH = '"+this.getJson().get(key)+"'");
                
                else if(key.equals("instncode"))
                	sb.append(" and (ci.ORG_ID2 = '"+this.getJson().get(key)+"'or ci.ORG_ID4 = '"+this.getJson().get(key)+"')");
                else if(key.equals("CUST_MANAGER"))
                    sb.append(" and ci.CUST_MANAGER_ID = '"+this.getJson().get(key)+"'");
            }
        }
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
		String userId = auth.getUserId();        
		String role = "";
		for(GrantedAuthority ga : auth.getAuthorities() ){
		    role += ga.getAuthority()+"$";
		}
//		if(role.indexOf("2")>=0){
//			sb.append(" and  ci.create_user = '"+userId+"'");  
//		}
		
        setPrimaryKey("ci.ORG_ID4");
//        setBranchFileldName("u.unitid");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

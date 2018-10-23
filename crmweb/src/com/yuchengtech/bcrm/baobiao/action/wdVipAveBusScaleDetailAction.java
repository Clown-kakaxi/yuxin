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
@Action(value="/wdVipAveBusScaleDetail", results={
    @Result(name="success", type="json"),
})
public class wdVipAveBusScaleDetailAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
 	public void prepare() {
		// TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder("select ci.* from ACRM_M_VIP_BUSI_SCALE_DETAIL ci where CUST_MANAGER_ID IS NULL and ORG_ID4 IS not NULL" );
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("MONTH1"))
                    sb.append(" and ci.MONTH >= '"+this.getJson().get(key)+"'");
                else if(key.equals("MONTH2"))
                	sb.append(" and ci.MONTH <= '"+this.getJson().get(key)+"'");
                else if(key.equals("CUR_GRADE"))
                	sb.append(" and ci."+key+" = '"+this.getJson().get(key)+"'");
                else if(key.equals("instncode"))
                	sb.append(" and ci.ORG_ID2 = '"+this.getJson().get(key)+"'");
               
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
		addOracleLookup("CUR_GRADE", "P_VIP_CUST_LEV");
		addOracleLookup("PER_GRADE", "P_VIP_CUST_LEV");
        setPrimaryKey("ci.ORG_ID2");
//        setBranchFileldName("u.unitid");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

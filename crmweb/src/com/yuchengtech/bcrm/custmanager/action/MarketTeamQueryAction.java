package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@Action("/marketTeamQuery")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "marketTeamQuery"})
})
public class MarketTeamQueryAction extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	@Override
	public void prepare() {
	    	StringBuilder sb = new StringBuilder();	
         sb = new StringBuilder("select p.mkt_team_id as mkt_team_id,p.create_user_id as create_user_id,p.create_user_name as create_user_name,p.create_user_org_id as create_user_org_id,(select count(*) from ocrm_f_cm_team_cust_manager cm where cm.mkt_team_id = p.mkt_team_id) as team_no,(select count(*) from ocrm_f_cm_mkt_team_cust ct where ct.mkt_team_id = p.mkt_team_id) as cust_no,(select u.unitname from sys_units u where u.unitid=p.org_id) as org_name,p.create_user as create_user,p.mkt_team_name as mkt_team_name,p.team_leader_id as team_leader_id,p.org_id as org_id,p.team_leader as team_leader,p.create_date as create_date,p.lead_telephone as lead_telephone,p.team_status as team_status from ocrm_f_cm_mkt_team p where 1>0");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("MKT_TEAM_ID"))
                    sb.append(" and "+key+" = "+this.getJson().get(key));
                if(key.equals("MKT_TEAM_NAME"))
                    sb.append(" and "+key+" like '%"+this.getJson().get(key)+"%'");
                if(key.equals("TEAM_STATUS"))
                    sb.append(" and "+key+" like '%"+this.getJson().get(key)+"%'");
            }
        }

		SQL = sb.toString();
        setPrimaryKey("p.CREATE_DATE desc");
        
		datasource = ds;
//		setBranchFileldName("ORG_ID");
	}
}

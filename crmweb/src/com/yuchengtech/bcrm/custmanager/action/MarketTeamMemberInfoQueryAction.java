package com.yuchengtech.bcrm.custmanager.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

@Action("/marketTeamMemberInfoQuery")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "marketTeamMemberInfoQuery"})
})
public class MarketTeamMemberInfoQueryAction extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	private HttpServletRequest request;
//	private String marketTeamId;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
       
		String mktTI= request.getParameter("marketTeamId");
		// SQL为德阳银行 临时修改 以应付POC
        StringBuilder sb = new StringBuilder("select p.id as id,p.user_id as user_id,p.join_date as join_date,a.org_id,a.user_name as username,o.org_name as unitname,p.mkt_team_id as mkt_team_id,a.MOBILEPHONE as telephone from ocrm_f_cm_team_cust_manager p left join ADMIN_AUTH_ACCOUNT a on a.account_name = p.user_id left join admin_auth_org o on o.org_id = a.org_id where 1 > 0");
        if(mktTI!=null&&mktTI!=""){
       	 sb.append(" and p.MKT_TEAM_ID ="+mktTI);
       }
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
            	if(key.equals("MKT_TEAM_ID1")&& !(mktTI!=null||mktTI!=""))
                    sb.append(" and p.MKT_TEAM_ID ="+this.getJson().get(key));
            	if(key.equals("USER_NAME"))
                    sb.append(" and a.USERNAME like '%"+this.getJson().get(key)+"%'");
                if(key.equals("USER_ID"))
                    sb.append(" and p."+key+" like '%"+this.getJson().get(key)+"%'");
                if(key.equals("UNITNAME"))
                    sb.append(" and o.org_name" +" like '%"+this.getJson().get(key)+"%'");
            }
        }
        
//        String marketTeamId1 =(String) this.getJson().get("marketTeamId"); 
        
        
		SQL = sb.toString();
        setPrimaryKey("p.ID desc");
        
		datasource = ds;
		
	}
	

	public Map<String, Object> getJson() {
		return super.getJson();
	}
	
}

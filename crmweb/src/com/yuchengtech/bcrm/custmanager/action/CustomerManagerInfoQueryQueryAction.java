package com.yuchengtech.bcrm.custmanager.action;

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

@Action("/CustomerManagerqueryqueryInfo")
@Results({@Result(name = "success", type = "redirectAction", params = {
		"actionName", "CustomerManagerqueryqueryInfo" }) })
public class CustomerManagerInfoQueryQueryAction extends CommonAction {

	 @Autowired
     @Qualifier("dsOracle")
     private DataSource ds;
	 private HttpServletRequest request;
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String mktTeamId = request.getParameter("mktTeamId");
		if(mktTeamId==null||mktTeamId==""){
			System.out.println("无此结果！");
		}else{
		StringBuilder sb = new StringBuilder("select v.id as id, v.id as user_id, g.org_name as unitname,v.mobilephone as telephone,v.user_name as user_name,g.org_id  as unitid from admin_auth_account v left join admin_auth_org g on g.org_id = v.org_id v.id not in (select cm.user_id from ocrm_f_cm_team_cust_manager cm where cm.mkt_team_id = " +
			mktTeamId+"') ");
//		for (String key : this.getJson().keySet()) {
//			if (null != this.getJson().get(key)
//					&& !this.getJson().get(key).equals("")) {
//				if (key.equals("USER_ID1")) sb.append(" and O.USER_ID like '%"+this.getJson().get(key)+"%'");
//				else{
//					if (key.equals("USER_NAME1")) sb.append(" and V.USER_NAME like '%"+this.getJson().get(key)+"%'");
//					else{
//					if (key.equals("UNITNAME")) sb.append(" and V.USER_NAME like '%"+this.getJson().get(key)+"%'");
//						}
//					}
//				}
//		}
		SQL = sb.toString();
		setPrimaryKey("v.ID desc");

		addOracleLookup("ACCESSIONSTATE", "MANAGER_STATUS");
		addOracleLookup("MANAGER_TYPE", "MANAGER_TYPE");
		addOracleLookup("MANAGER_LEVEL", "MANAGER_LEVEL");
		addOracleLookup("LEVL", "MANAGER_GRADE");
		addOracleLookup("POLITICAL", "POL_LANDSCAPE");
		addOracleLookup("IS_HAVING_CARD", "IS_HAVING_CODE");
		addOracleLookup("EDUCATIONLEVEL", "EDUCATIONLEVEL");
		datasource = ds;
		setBranchFileldName("unitid");
		
		
	}
	}
}

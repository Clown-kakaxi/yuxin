package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@Action("/addTeamleaderQuery")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "addTeamleaderQuery" }) })
public class AddTeamLeaderQueryAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(
				"select t.* from (select t.id,t.userid,t.username,t.password ,t1.unitid ,t1.UNITNAME from sys_users t left join sys_units t1 on t.unitid = t1.id)t where 1>0");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("USERID"))
					sb.append(" and " + key + " like " + " '%"+ this.getJson().get(key) + "%'");
				if (key.equals("USERNAME"))sb.append(" and " + key + " like " + " '%"+ this.getJson().get(key) + "%'");
			}
		}

		SQL = sb.toString();
		setPrimaryKey("t.USERID desc");
		datasource = ds;
//		setBranchFileldName("t.unitid");
	}
}

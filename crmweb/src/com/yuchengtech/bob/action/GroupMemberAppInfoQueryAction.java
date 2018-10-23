package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@Action("/groupMemberAppInfoQuery")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "groupMemberAppInfoQuery" }) })
public class GroupMemberAppInfoQueryAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder(
				"select id as id,unitname as unitname,group_no as group_no,credit_opinion as credit_opinion, up_name as up_name,group_name as group_name,parent_cust_zh_name as parent_cust_zh_name,group_host_org_no_name as group_host_org_no_name,group_host_org_no as group_host_org_no,app_status as app_status,submit_date as submit_date,app_date as app_date,parent_id as parent_id,cust_zh_name as cust_zh_name from (select t1.id as id,u.unitname as unitname,t1.group_no as group_no,t1.credit_opinion as credit_opinion,vs.cust_zh_name as up_name,t.group_name as group_name,c.cust_zh_name as parent_cust_zh_name,(select u.unitname from sys_units u where u.unitid=t.group_host_org_no)as group_host_org_no_name,t.group_host_org_no as group_host_org_no,t1.app_status as app_status,t1.submit_date as submit_date,t1.update_date as update_date,t1.parent_id as parent_id,t1.app_date as app_date,t2.cust_zh_name as cust_zh_name from ocrm_f_ci_group_member t1 left join ocrm_f_ci_group_info t on t.group_no = t1.group_no left join v_acrm_f_ci_cust_info t2 on t2.cust_id = t.group_root_cust_id left join v_acrm_f_ci_cust_info c on c.cust_id = t1.cust_id left join ocrm_f_ci_group_member w on w.id = t1.parent_id left join v_acrm_f_ci_cust_info vs on vs.cust_id=w.cust_id left join sys_units u on u.unitid = t.group_host_org_no)  where (app_status='0'or app_status ='1'or app_status ='2'or app_status ='3')");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("GROUP_NO") || key.equals("APP_STATUS"))
					sb.append(" and " + key + "=" + " '"
							+ this.getJson().get(key) + "'");
				if (key.equals("PARENT_CUST_ZH_NAME"))
					sb.append(" and " + key + " like " + " '%"
							+ this.getJson().get(key) + "%'");
				if (key.equals("CUST_ZH_NAME"))
					sb.append(" and " + key + " like " + " '%"
							+ this.getJson().get(key) + "%'");
				if (key.equals("UP_NAME"))
					sb.append(" and " + key + "=" + " '"
							+ this.getJson().get(key) + "'");
				if (key.equals("GROUP_HOST_ORG_NO_NAME"))
					sb.append(" and UNITNAME like " + " '%"
							+ this.getJson().get(key) + "%'");
				if (key.equals("GROUP_NAME"))
					sb.append(" and " + key + " like " + " '%"
							+ this.getJson().get(key) + "%'");
			}
		}

		SQL = sb.toString();
		setPrimaryKey("ID desc");
		addOracleLookup("APP_STATUS", "APP_STATUS");
		datasource = ds;

	}
}

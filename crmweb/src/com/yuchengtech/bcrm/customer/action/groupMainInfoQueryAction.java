package com.yuchengtech.bcrm.customer.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@Action("/groupmaininfo")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "groupmaininfo" }) })
public class groupMainInfoQueryAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(
				"select t1.cust_id,t1.cust_zzdm,t1.cust_zh_name,t1.belong_instn ,t2.zc_addr from (select t1.cust_id,t1.cust_zzdm,t1.cust_zh_name,t1.belong_instn,nvl(t2.group_root_cust_id,'0') from acrm_f_ci_cust_info t1 left join ocrm_f_ci_group_info t2 on t1.cust_id=t2.group_root_cust_id  where nvl(t2.group_root_cust_id,'0')='0' union select t1.cust_id,t1.cust_zzdm,t1.cust_zh_name,t1.belong_instn,nvl(t2.group_root_cust_id,'0') from acrm_f_ci_latent_cust_info t1 left join ocrm_f_ci_group_info t2 on t1.cust_id=t2.group_root_cust_id where nvl(t2.group_root_cust_id,'0')='0') t1 left join acrm_f_ci_addr t2 on t1.cust_id =t2.cust_id  where 1>0");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("CUST_ZZDM"))
					sb.append(" and " + key + " like " + " '%"+ this.getJson().get(key) + "%'");
				if (key.equals("CUST_ZH_NAME"))sb.append(" and " + key + " like " + " '%"+ this.getJson().get(key) + "%'");
			}
		}

		SQL = sb.toString();
		setPrimaryKey("t1.CUST_ID desc");
		datasource = ds;
	}
}

package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/querymypotentialcustomer", results={
    @Result(name="success", type="json")
})
public class QueryMyPotentialCustomerAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		StringBuilder s = new StringBuilder(
			"select t1.*,t4.username " +
			"from ACRM_F_CI_LATENT_CUST_INFO t1 " +
//			"left join ocrm_f_ci_relation_units  t2 " +
//			"on t1.cust_id=t2.bid " +
//			"left join sys_units t3 " +
//			"on t2.unitcode=t3.unitid " +
			"left join sys_users t4 " +
			"on  t1.create_name=t4.userid " +
			"where 1>0");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("CUST_ZH_NAME"))
					s.append(" and t1." + key + " like" + " '%"
							+ this.getJson().get(key) + "%'");
				else if (key.equals("CUST_ZZDM") || key.equals("IS_POTENTIAL")
						|| key.equals("ASSIGN_STS") || key.equals("CUST_SCOPE")
						|| key.equals("CHANNEL") || key.equals("HY_CLASS"))
					s.append(" and t1." + key + "=" + " '"
							+ this.getJson().get(key) + "'");
				else if ((key.equals("CRM_DT_START"))) {
					s.append(" and t1.CRM_DT >=" + "to_date('"
							+ this.getJson().get(key) + "', 'YYYY-MM-DD') ");
				} else if ((key.equals("CRM_DT_END"))) {
					s.append(" and t1.CRM_DT <=" + "to_date('"
							+ this.getJson().get(key) + "', 'YYYY-MM-DD') ");
				} else if (key.equals("BELONG_ORG_NAME"))
					s.append(" and t3.UNITNAME like" + " '%"
							+ this.getJson().get(key) + "%'");
			}
		}
//		addGreenplumLookup("CRE_MS_FLG", "YN");
		addOracleLookup("ASSIGN_STS", "CUST_STATUS");
		addOracleLookup("IS_POTENTIAL", "CUST_DISTRIBUTE");
//		addGreenplumLookup("CUST_SCOPE", "QYGM");
//		addGreenplumLookup("HY_CLASS", "HYFL");
		setPrimaryKey("t1.id");
//		setBranchFileldName2("t1.cust_id");
		SQL = s.toString();
		datasource = ds;
    }

}
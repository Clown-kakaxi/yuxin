package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;


@ParentPackage("json-default")
@Action(value = "/accountKindQuery", results = { @Result(name = "success", type = "json"), })
public class AccountKindQueryAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
    public void prepare() {
		StringBuilder sb = new StringBuilder(
				"select KIND_ID,KIND_CODE,KIND_NAME,KIND_DESC,CREATE_DATE,EMPLOYEE_ID,EMPLOYEE_NAME,ORG_NUM,USE_ORG from OCRM_SYS_ACCOUNT_KIND where 1>0");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("KIND_CODE"))
					sb.append(" and " + key + " like '%"
							+ this.getJson().get(key) + "%'");
				else if (key.equals("KIND_NAME"))
					sb.append(" and " + key + " like '%"
							+ this.getJson().get(key) + "%'");
				else if (key.equals("KIND_DESC"))
					sb.append(" and " + key + " like '%"
							+ this.getJson().get(key) + "%'");
				else {
					sb.append(" and " + key + " = " + this.getJson().get(key));
				}
			}
		}
		setPrimaryKey("KIND_ID");
        
        SQL=sb.toString();
        datasource = ds;
	}
}

package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value = "/subjectStatisticsQuery", results = { @Result(name = "success", type = "json"), })
public class SubjectStatisticsQueryAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public void prepare() {

		StringBuilder sb = new StringBuilder(
				"select SUBJECT_ID,SUBJECT_KIND,SUBJECT_NAME,BUSSINESS_TYPE,CREATE_DATE,EMPLOYEE_NAME from OCRM_SYS_SUBJECT_STATISITCS where 1>0");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("SUBJECT_KIND"))
					sb.append(" and " + key + " like '%"
							+ this.getJson().get(key) + "%'");
				else if (key.equals("SUBJECT_NAME"))
					sb.append(" and " + key + " like '%"
							+ this.getJson().get(key) + "%'");
				else if (key.equals("BUSSINESS_TYPE"))
					sb.append(" and " + key + " like '%"
							+ this.getJson().get(key) + "%'");
				else {
					sb.append(" and " + key + " = " + this.getJson().get(key));
				}
			}
		}
		setPrimaryKey("SUBJECT_ID");
		addOracleLookup("BUSSINESS_TYPE", "BUSSINESS_TYPE");

		SQL = sb.toString();
		datasource = ds;
	}
}

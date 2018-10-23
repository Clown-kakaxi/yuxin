package com.yuchengtech.bcrm.custview.action;



import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;


@ParentPackage("json-default")
@Action(value = "/cusMgrHis-Action", results = { @Result(name = "success", type = "json")})
public class CusMgrHisAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String a = request.getParameter("custId");
		StringBuilder sb = new StringBuilder(
				"  select c.*, case when c.BEFORE_MAIN_TYPE is null then '新增' " +
				"when c.AFTER_MAIN_TYPE is null then '删除' else '修改' end STATE  " +
				" from OCRM_F_CI_BELONG_HIST c where 1>0 and  c.CUST_ID = '"+a+"' ");
	
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("CUST_ID"))
					{sb.append(" and " + key + " like '%"
							+ this.getJson().get(key) + "%'");}
	
			}
		}
		setPrimaryKey("c.ID");
		addOracleLookup("BEFORE_MAIN_TYPE", "MAINTAIN_TYPE");
        addOracleLookup("AFTER_MAIN_TYPE","MAINTAIN_TYPE");
        SQL=sb.toString();
        datasource = ds;
	}
}



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
@Action(value = "/eventinformation", results = { @Result(name = "success", type = "json") })
public class QueryEventInformationAction extends CommonAction {

    private HttpServletRequest request;

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public void prepare() {
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String customerId = request.getParameter("customerId");

        StringBuilder sb = new StringBuilder(
                "select t.* "
                        + "from acrm_f_ci_event t where t.CUST_ID= '" + customerId
                        + "'");
        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("EVENT_NAME"))
                    sb.append(" and t." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("EVENT_TYP"))
                    sb.append(" and t." + key + "= '" + this.getJson().get(key)
                            + "'");
            }
        }

        SQL = sb.toString();
        setPrimaryKey("t.EVENT_ID");
        addOracleLookup("EVENT_TYP", "EVENT_TYP");
        datasource = ds;
    }

}
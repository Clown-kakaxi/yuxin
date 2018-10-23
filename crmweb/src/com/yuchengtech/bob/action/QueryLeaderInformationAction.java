package com.yuchengtech.bob.action;

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
@Action(value = "/leaderinformation", results = { @Result(name = "success", type = "json") })
public class QueryLeaderInformationAction extends CommonAction {

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
                "select t.* from OCRM_F_CI_MANAGER t where t.CUST_ID= '"
                        + customerId + "'");
        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("GGNAME_ZH"))
                    sb.append(" and t." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("IMT_LEV"))
                    sb.append(" and t." + key + "= '" + this.getJson().get(key)
                            + "'");
                else if (key.equals("POSITION"))
                    sb.append(" and t." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("FZ_TYP"))
                    sb.append(" and t." + key + "= '" + this.getJson().get(key)
                            + "'");
                else if (key.equals("CRET_NO"))
                    sb.append(" and t." + key + " like '%"
                            + this.getJson().get(key) + "%'");
            }
        }

        SQL = sb.toString();
        setPrimaryKey("t.ID");
//        addGreenplumLookup("CRET_TYP", "CMRZJLX");
//        addGreenplumLookup("COUNTRY", "GJDQ");
        addOracleLookup("IMT_LEV", "IMT_LEV");
        datasource = ds;
    }

}

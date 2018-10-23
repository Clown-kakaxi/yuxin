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
@Action(value = "/queryfamilymemberinformation", results = { @Result(name = "success", type = "json") })
public class QueryFamilyMemberInformationAction extends CommonAction {

    private HttpServletRequest request;

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public void prepare() {
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String customerId = request.getParameter("customerId");
        String managerId = request.getParameter("managerId");

        StringBuilder sb = new StringBuilder("select t.* "
                + "from OCRM_F_CI_FAMILY_MEMBER t where t.CUST_ID= '"
                + customerId + "' and MANAGER_ID= '" + managerId + "'");
        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("MEMBERNAME"))
                    sb.append(" and t." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("FAMILYRELA"))
                    sb.append(" and t." + key + "= '" + this.getJson().get(key)
                            + "'");
            }
        }

        SQL = sb.toString();
        setPrimaryKey("t.MXTID");
//        addGreenplumLookup("MEMBERCRET_TYP", "CMRZJLX");
        addOracleLookup("FAMILYRELA", "FAMILY_RELA");
        datasource = ds;
    }

}
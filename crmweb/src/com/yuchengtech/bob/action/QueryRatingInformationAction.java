package com.yuchengtech.bob.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.service.CommonQueryService;

@ParentPackage("json-default")
@Action(value = "/queryratinginformation", results = { @Result(name = "success", type = "json") })
public class QueryRatingInformationAction extends BaseAction {

	@Autowired
    private CommonQueryService cqs;
	
	private HttpServletRequest request;

    public String index() {
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String customerId = request.getParameter("customerId");
        StringBuilder sb = new StringBuilder(
                "select ID,CUST_ID,PJ_DT,PJ_SYS,VAL_DT,CUST_NAME,PJ_CAUSE,CUST_LEV,"
                        + "CUST_LEV_DESC,CRM_DT from OCRM_F_CI_LEV where CUST_ID= '"
                        + customerId + "'");
        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("CUST_LEV"))
                    sb.append(" and " + key + "= '" + this.getJson().get(key)
                            + "'");
            }
        }

        this.setJson(cqs.excuteQuery(sb.toString(), this.getStart(),
                this.getLimit()));
        return "success";
    }

}

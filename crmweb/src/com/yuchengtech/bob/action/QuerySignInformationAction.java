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
@Action(value = "/querysigninformation", results = { @Result(name = "success", type = "json") })
public class QuerySignInformationAction extends BaseAction {

	@Autowired
    private CommonQueryService cqs; 
	
	private HttpServletRequest request;

    public String index() {
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String customerId = request.getParameter("customerId");
        StringBuilder sb = new StringBuilder(
                "select t.* from ocrm_f_ci_contract_info t where t.CUST_ID= '"
                        + customerId + "'");
        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("SIGN_NAME"))
                    sb.append(" and t." + key + " like '%" + this.getJson().get(key)
                            + "%'");
                else if (key.equals("SIGN_ORG"))
                    sb.append(" and t." + key + " like '%" + this.getJson().get(key)
                            + "%'");
            }
        }

        this.setJson(cqs.excuteQuery(sb.toString(), this.getStart(),
                this.getLimit()));
        return "success";
    }

}

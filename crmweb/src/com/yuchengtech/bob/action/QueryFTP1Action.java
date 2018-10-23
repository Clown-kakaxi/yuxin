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
@Action(value = "/queryftp1", results = { @Result(name = "success", type = "json") })
public class QueryFTP1Action extends CommonAction {

    private HttpServletRequest request;

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public void prepare() {
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);

        // 币种
        String ftpNo = request.getParameter("ftpNo");

        StringBuilder sb = new StringBuilder("select t.* "
                + "from OCRM_F_SM_SYS_FTP t where t.SYSTEM_FTP_NO = '" + ftpNo
                + "' ");

        SQL = sb.toString();
        setPrimaryKey("t.SYSTEM_FTP_DATE desc");
        datasource = ds;
    }

}

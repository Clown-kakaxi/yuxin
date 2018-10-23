package com.yuchengtech.bob.action;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;

@ParentPackage("json-default")
@Action(value = "/queryftp", results = { @Result(name = "success", type = "json") })
public class QueryFTPAction extends BaseAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public String index() throws SQLException {

        StringBuilder sb = new StringBuilder("select t.* "
                + "from OCRM_F_SM_SYS_FTP_INFO t where 1>0 ");

        PagingInfo pi = new PagingInfo(1000000000, 1);
        QueryHelper qh = new QueryHelper(sb.toString(), ds.getConnection(), pi);
        qh.setPrimaryKey("t.ID");
        setJson(qh.getJSON());
        return "success";
    }

}

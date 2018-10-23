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
@Action(value = "/querycustomermiddleprofit", results = { @Result(name = "success", type = "json") })
public class QueryMiddleProfitAction extends BaseAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public String index() throws SQLException {
        StringBuilder sb = new StringBuilder("select t.* "
                + "from OCRM_F_CI_MID_PROFIT t where 1>0 ");

        int currentPage = this.getStart() / this.getLimit() + 1;
        PagingInfo pi = new PagingInfo(this.getLimit(), currentPage);
        QueryHelper qh = new QueryHelper(sb.toString(), ds.getConnection(), pi);
        qh.setPrimaryKey("t.ID");
//        qh.addOracleLookup("COVER_STS", "CASH_FLOW_COVER");
//        qh.addOracleLookup("FTP", "FTP");
//        qh.addGreenplumLookup("MARGIN_LIMIT_TIME", "DKQX");
//        qh.addOracleLookup("GUARANTEE_MODE", "GUARANTEE_METHOD");
//        qh.addOracleLookup("NO_RE_LOAN_CUST_LV", "CUST_LEVEL3");
//        qh.addGreenplumLookup("CURRENCY", "CCY");
//        qh.addGreenplumLookup("IS_REAL_ESTATE", "YN");
        
        setJson(qh.getJSON());
        return "success";
    }

}

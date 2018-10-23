package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/querycustomerloanprofit", results = { @Result(name = "success", type = "json") })
public class QueryLoanProfitAction extends CommonAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public void prepare() {
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        StringBuilder sb = new StringBuilder("select t.* "
                + "from ocrm_f_ci_profit t where 1>0 and t.cust_id = '"+currenUserId+"' ");
        // 机构权限控制，from 表中机构字段名
//        setBranchFileldName("ORG_ID");

        SQL = sb.toString();
        setPrimaryKey("t.ID");
//        addGreenplumLookup("COVER_STS", "FGXS");
//        addGreenplumLookup("LOAN_LIMIT_TIME", "DKQX");
//        addGreenplumLookup("CURRENCY", "CCY");
        datasource = ds;
    }

}

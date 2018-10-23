package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value = "/querydepositlimit", results = { @Result(name = "success", type = "json") })
public class QueryDepositLimitAction extends CommonAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    public void prepare() {

        StringBuilder sb = new StringBuilder(
                "SELECT  as80std, as80itr FROM fdm.acrm_f_cor_aspf80 where as80std = '活期存款' "
                        + "or as80std = '协定存款'   or as80std = '一天通知存款' or as80std = '七天通知存款' "
                        + "or as80std = '定期三个月'  or as80std = '定期半年' or as80std = '定期一年' "
                        + "or as80std = '定期二年' or as80std = '定期三年' or as80std = '定期五年' ");

        SQL = sb.toString();
        setPrimaryKey("as80incls");
        datasource = ds;
    }

}

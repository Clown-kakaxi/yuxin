package com.yuchengtech.bob.action;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value = "/queryworkdeposit", results = { @Result(name = "success", type = "json"), })
public class QueryworkDepositReportFormAction extends CommonAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    /**
     * @describe Create sql for search and export.
     */
    @SuppressWarnings("unchecked")
	public void prepare() {
        StringBuilder sb = new StringBuilder("");
        String depTable = " mdm.acrm_m_wk_dep ";
        Map paramsMap = this.getJson();

        String checkedNodes = (String) paramsMap.get("checkedNodes");

        sb.append("Select t1.*,t2.unitname from "
                + depTable
                + " t1 left join fdm.acrm_f_sm_sys_units_sta t2 on t1.instn_cod=t2.unitid where 1>0 ");

        if (checkedNodes != null && !checkedNodes.equals("")
                && !checkedNodes.equals("undefined")
                && !checkedNodes.equals("null")) {
            String[] checkedOrg = checkedNodes.split(",");
            if (checkedOrg.length > 0) {
                sb.append(" and ");
                if (checkedOrg.length > 1) {
                    sb.append("(");
                }
                for (int i = 0; i < checkedOrg.length; i++) {
                    sb.append(" t1.INSTN_COD = " + checkedOrg[i] + " ");
                    // //system.out.printlnln(checkedOrg[i]+"^^^^");
                    if (i < checkedOrg.length - 1) {
                        sb.append(" or ");
                    }
                }
                if (checkedOrg.length > 1) {
                    sb.append(")");
                }
            }
        }

        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("STAT_DT"))
                    sb.append(" and t1." + key + "= to_date('"
                            + this.getJson().get(key) + "','yyyy-MM-dd')");
                else if (key.equals("CUR_COD"))
                    sb.append(" and t1.CUR_COD = '" + this.getJson().get(key)
                            + "'");
                else if (key.equals("contain_fin"))
                    sb.append(" and t1.contain_fin = '"
                            + this.getJson().get(key) + "'");
            }
        }

        SQL = sb.toString();
        setPrimaryKey("t1.id");
        addOracleLookup("cur_cod", "CUR_TYPE");
        datasource = ds;
    }

}

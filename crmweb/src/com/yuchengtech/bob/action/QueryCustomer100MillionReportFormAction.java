package com.yuchengtech.bob.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Action(value = "/query100millioncust", results = { @Result(name = "success", type = "json"), })
public class QueryCustomer100MillionReportFormAction extends CommonAction {

    private List<String> orgIds = new ArrayList<String>();

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

    /**
     * @describe Create sql for search and export.
     */
    @SuppressWarnings("unchecked")
	public void prepare() {
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String orgId = auth.getUnitId();
        /**inOrgs(orgId);
         * TODO 循环取出上级机构编号
         */
        String checkedOrg1 = "";
        StringBuilder sb = new StringBuilder("");
        String depTable = " mdm.acrm_m_ci_ydet ";
        Map paramsMap = this.getJson();
        String level = (String) paramsMap.get("instn_level");
        String checkedNodes = (String) paramsMap.get("checkedNodes");
        // //system.out.printlnln(checkedNodes+"*********************************************");

        sb.append("Select t1.instn_id,t1.stat_dt,t1.cur_cod,t1.cust_zzdm,t1.cust_name,t1.business_typ," +
        		"t1.id,t1.crm_dt,sum(t1.current_bal) current_bal,sum(mon_bal) mon_bal," +
        		"sum(t1.quarter_bal) quarter_bal,sum(year_bal) year_bal,t4.unitname ");

        if (level == null || level.equals("") || level.equals("undefined")
                || level.equals("null")) {
            level = "level_3";
        }
        if (checkedNodes != null && !checkedNodes.equals("")
                && !checkedNodes.equals("undefined")
                && !checkedNodes.equals("null")) {
            checkedOrg1 = buildCheckedNodes(checkedNodes);
            // checkedOrg =
            // " and acrm_f_sm_sys_units_sta.unitid in ("+(String)paramsMap.get("checkedNodes")+")";
        } else {
            level = "level_3";
        }
        /*
         * if(checkedNodes != null && !checkedNodes.equals("") &&
         * !checkedNodes.equals("undefined") && !checkedNodes.equals("null")) {
         * String[] checkedOrg = checkedNodes.split(",");
         * if(checkedOrg.length>0){ sb.append(" and "); if(checkedOrg.length>1){
         * sb.append("("); } for(int i=0;i<checkedOrg.length;i++){
         * sb.append(" t1.INSTN_ID = "+checkedOrg[i]+" "); //
         //system.out.printlnntln(checkedOrg[i]+"^^^^"); if(i<checkedOrg.length-1){
         * sb.append(" or "); } } if(checkedOrg.length>1){ sb.append(")"); } } }
         */
        sb.append(" from "
                + depTable
                + " t1 inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "
                + checkedOrg1
                + ") t2  on t1.instn_id = t2.unitid "
                + " inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t1.instn_id inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."
                + level + " = t4.unitid  ");

        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                if (key.equals("STAT_DT"))
                    sb.append(" and t1." + key + "= to_date('"
                            + this.getJson().get(key) + "','yyyy-MM-dd')");
                else if (key.equals("CUR_TYP"))
                    sb.append(" and t1.cur_cod = '" + this.getJson().get(key)
                            + "'");
                else if (key.equals("BUSI_TYP"))
                    sb.append(" and t1.business_typ = '"
                            + this.getJson().get(key) + "'");
            }
        }
        sb.append(" group by t1.stat_dt,t1.cust_zzdm,t1.cur_cod,t1.business_typ,t1.instn_id,t1.cust_name,t1.id,t1.crm_dt, " 
                + " t3."+level+",t4.unitname ");
        SQL = sb.toString();
        setPrimaryKey("t1.id");
        datasource = ds;
    }

    public String buildCheckedNodes(String checkedNodes) {

        String[] checkedOrg = checkedNodes.split(",");
        StringBuffer stringBuffer = new StringBuffer(" inner join ( ");
        for (int i = 0; i < checkedOrg.length; i++) {
            stringBuffer.append("select " + checkedOrg[i] + " unitid union ");
        }
        int lastUnion = stringBuffer.lastIndexOf("union");
        stringBuffer = stringBuffer.replace(lastUnion, stringBuffer.length(),
                " ");
        stringBuffer
                .append("  )  t7 on acrm_f_sm_sys_units_sta.unitid = t7.unitid ");
        return stringBuffer.toString();
    }

}

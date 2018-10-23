package com.yuchengtech.bob.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Action(value = "/queryaccountsum", results = { @Result(name = "success", type = "json"), })
public class QueryAccountSumReportFormAction extends CommonAction {

    private List<String> orgIds = new ArrayList<String>();
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;


    /**
     * @describe Create sql for search and export.
     */
    @SuppressWarnings("unchecked")
	public void prepare() {
        RollMemberQueryAction rm = new RollMemberQueryAction();
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        // String userId = auth.getUsername();
        String orgId = auth.getUnitId();
        /**inOrgs(orgId);
         * TODO 循环取出上级机构
         */
        StringBuilder sb = new StringBuilder("");
        String checkedOrg1 = "";
        String depTable = " mdm.acrm_m_ac_sum ";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map paramsMap = this.getJson();
        String stateDate = (String) paramsMap.get("CRM_DT");
        String level = (String) paramsMap.get("instn_level");
        String caculateType = (String) paramsMap.get("caculate_type");
        String checkedNodes = (String) paramsMap.get("checkedNodes");
        // //system.out.printlnln(checkedNodes+"*********************************************");

        /** 判断传入的统计时间,如果统计时间是近3天以前,去查询历史表 */
        GregorianCalendar calendar = new GregorianCalendar();

        GregorianCalendar calendarDay = new GregorianCalendar(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE) - 3);

        String dataDay = "" + calendarDay.get(Calendar.YEAR) + "-"
                + calendarDay.get(Calendar.MONTH) + "-"
                + calendarDay.get(Calendar.DATE);

        try {
            Date dateParam = dateFormat.parse(stateDate);
            Date dateToday = dateFormat.parse(dataDay);
            if (dateParam.before(dateToday)) {
                depTable = " mdm.acrm_m_ac_sum_h ";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sb.append("Select t1.acct_name,t1.bal_typ,t1.instn_id,t1.crm_dt,"
                + "sum(t1.bal) bal,sum(t1.mon_avg) mon_avg,sum(t1.quar_avg) quar_avg,sum(t1.year_avg) year_avg,"
                + "sum(t1.bal_cd) bal_cd,sum(t1.mon_cd) mon_cd,sum(t1.quar_cd) quar_cd,sum(t1.year_cd) year_cd,"
                + "sum(t1.bal_cy) bal_cy,sum(t1.mon_cy) mon_cy,sum(t1.quar_cy) quar_cy,sum(t1.year_cy) year_cy,"
                + "sum(t1.bal_cq) bal_cq,sum(t1.mon_cq) mon_cq,sum(t1.quar_cq) quar_cq,sum(t1.year_cq) year_cq,"
                + "sum(t1.bal_cm) bal_cm,sum(t1.mon_cm) mon_cm,sum(t1.quar_cm) quar_cm,sum(t1.year_cm) year_cm,"
                + "sum(t1.bal_cny) bal_cny,sum(t1.mon_cny) mon_cny,sum(t1.quar_cny) quar_cny,sum(t1.year_cny) year_cny,"
                + "sum(t1.bal_cd_cny) bal_cd_cny,sum(t1.mon_cd_cny) mon_cd_cny,sum(t1.quar_cd_cny) quar_cd_cny,sum(t1.year_cd_cny) year_cd_cny,"
                + "sum(t1.bal_cy_cny) bal_cy_cny,sum(t1.mon_cy_cny) mon_cy_cny,sum(t1.quar_cy_cny) quar_cy_cny,sum(t1.year_cy_cny) year_cy_cny,"
                + "sum(t1.bal_cq_cny) bal_cq_cny,sum(t1.mon_cq_cny) mon_cq_cny,sum(t1.quar_cq_cny) quar_cq_cny,sum(t1.year_cq_cny) year_cq_cny,"
                + "sum(t1.bal_cm_cny) bal_cm_cny,sum(t1.mon_cm_cny) mon_cm_cny,sum(t1.quar_cm_cny) quar_cm_cny,sum(t1.year_cm_cny) year_cm_cny,"
                + "sum(t1.bal_sum) bal_sum,sum(t1.mon_sum) mon_sum,sum(t1.quar_sum) quar_sum,sum(t1.year_sum) year_sum,"
                + "sum(t1.bal_cd_sum) bal_cd_sum,sum(t1.mon_cd_sum) mon_cd_sum,sum(t1.quar_cd_sum) quar_cd_sum,sum(t1.year_cd_sum) year_cd_sum,"
                + "sum(t1.bal_cy_sum) bal_cy_sum,sum(t1.mon_cy_sum) mon_cy_sum,sum(t1.quar_cy_sum) quar_cy_sum,sum(t1.year_cy_sum) year_cy_sum,"
                + "sum(t1.bal_cq_sum) bal_cq_sum,sum(t1.mon_cq_sum) mon_cq_sum,sum(t1.quar_cq_sum) quar_cq_sum,sum(t1.year_cq_sum) year_cq_sum,"
                + "sum(t1.bal_cm_sum) bal_cm_sum,sum(t1.mon_cm_sum) mon_cm_sum,sum(t1.quar_cm_sum) quar_cm_sum,sum(t1.year_cm_sum) year_cm_sum,"
                + "t1.id,t4.unitname ");

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

        sb.append(" from "
                + depTable
                + " t1 inner join (select acrm_f_sm_sys_units_sta.unitid  from fdm.acrm_f_sm_sys_units_sta "
                + checkedOrg1
                + ") t2  on t1.instn_id = t2.unitid "
                + " inner join fdm.acrm_f_sm_sys_units_sta t3 on t3.level_4 = t1.instn_id inner join fdm.acrm_f_sm_sys_units_sta t4 on t3."
                + level + " = t4.unitid  ");

        /** 判断币种 */
        String curString = (String) paramsMap.get("CUR_TYP");
        int curtyp = 0;
        if (curString.equals("01"))
            curtyp = 0;
        else if (curString.equals("02"))
            curtyp = 1;
        else if (curString.equals("03"))
            curtyp = 2;
        String[] typStrings = { "", "_CNY", "_SUM" };// 根据币种而改变查询条件

        int ctype = 0;
        if (caculateType.equals("01"))
            ctype = 0;
        else if (caculateType.equals("02"))
            ctype = 1;
        else if (caculateType.equals("03"))
            ctype = 2;
        else if (caculateType.equals("04"))
            ctype = 3;
        String[] caculateTypeStrings = { "_CY", "_CQ", "_CM", "_CD" };

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
                    sb.append(" t1.INSTN_ID = " + checkedOrg[i] + " ");
                    ///system.out.printlnntln(checkedOrg[i]+"^^^^");
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
                /*
                 * if (key.equals("INSTN_ID")){ sb.append(" and t1." + key +
                 * " = '" + this.getJson().get(key) + "'");
               //system.out.printlnrintln(this
                 * .getJson().get(key)+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"); }
                 * else
                 */if (key.equals("CUST_LIST")) {
                    List<String> list = rm.getCustName(this.getJson().get(key)
                            + "");
                    if (list.size() > 0) {
                        sb.append(" and ");
                        if (list.size() > 1)
                            sb.append("( ");
                        for (int i = 0; i < list.size(); i++) {
                            sb.append("t1.ACCT_NAME = '" + list.get(i) + "'");
                            if (i < list.size() - 1)
                                sb.append(" or ");
                        }
                        if (list.size() > 1) {
                            sb.append(" ) ");
                        }
                    }
                } else if (key.equals("CRM_DT"))
                    sb.append(" and t1." + key + "= to_date('"
                            + this.getJson().get(key) + "','yyyy-MM-dd')");
                else if (key.equals("BAL_TYP"))
                    sb.append(" and t1." + key + " = '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("ACCT_NAME"))
                    sb.append(" and t1." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("BAL1"))
                    sb.append(" and t1.BAL" + typStrings[curtyp] + " >= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("BAL2"))
                    sb.append(" and t1.BAL" + typStrings[curtyp] + " <= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("QUAR_AVG1"))
                    sb.append(" and t1.QUAR_AVG" + typStrings[curtyp] + " >= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("QUAR_AVG2"))
                    sb.append(" and t1.QUAR_AVG" + typStrings[curtyp] + " <= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("MON_AVG1"))
                    sb.append(" and t1.MON_AVG" + typStrings[curtyp] + " >= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("MON_AVG2"))
                    sb.append(" and t1.MON_AVG" + typStrings[curtyp] + " <= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("YEAR_AVG1"))
                    sb.append(" and t1.YEAR_AVG" + typStrings[curtyp] + " >= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("YEAR_AVG2"))
                    sb.append(" and t1.YEAR_AVG" + typStrings[curtyp] + " <= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("YEAR_AVG3"))
                    sb.append(" and t1.YEAR" + caculateTypeStrings[ctype]
                            + typStrings[curtyp] + " >= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("BAL3"))
                    sb.append(" and t1.BAL" + caculateTypeStrings[ctype]
                            + typStrings[curtyp] + " >= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("YEAR_C11"))
                    sb.append(" and t1.YEAR_C11" 
                            + typStrings[curtyp] + " >= '"
                            + this.getJson().get(key) + "'");
            }
        }

        sb.append(" group by t1.acct_name,t1.bal_typ,t1.instn_id,t1.crm_dt,t1.id, "
                + " t3." + level + ",t4.unitname ");
        SQL = sb.toString();
        setPrimaryKey("t1.id");
//        addGreenplumLookup("bal_typ", "DRYEFX");
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

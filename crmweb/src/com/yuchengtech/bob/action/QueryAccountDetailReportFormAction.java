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
@Action(value = "/queryaccountdetail", results = { @Result(name = "success", type = "json"), })
public class QueryAccountDetailReportFormAction extends CommonAction {

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
         * 
         * 循环取出上级机构
         * 
         */
        StringBuilder sb = new StringBuilder("");
        String depTable=" mdm.acrm_m_ac_det ";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map paramsMap = this.getJson();
        String stateDate= (String) paramsMap.get("STAT_DT");
        String listType= (String) paramsMap.get("LIST_TYP");

        String checkedNodes = (String)paramsMap.get("checkedNodes");
//        //system.out.printlnln(checkedNodes+"*********************************************");
        
        /**判断传入的统计时间,如果统计时间是近3天以前,去查询历史表*/
        GregorianCalendar calendar = new GregorianCalendar();
        
        GregorianCalendar calendarDay = new GregorianCalendar (calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE)-3);

        String dataDay = ""+calendarDay.get(Calendar.YEAR)+"-"+calendarDay.get(Calendar.MONTH)+"-"+calendarDay.get(Calendar.DATE);
        
        try{
            Date dateParam = dateFormat.parse(stateDate);
            Date dateToday = dateFormat.parse(dataDay);
            if(dateParam.before(dateToday)){
                depTable = " mdm.acrm_m_ac_det_h ";
            }           
        }catch(Exception ex){
            ex.printStackTrace();
        }

        
        sb.append("Select t1.*,t2.unitname from "+depTable+" t1 left join fdm.acrm_f_sm_sys_units_sta t2 on t1.instn_id=t2.unitid where 1>0 ");

        /** 判断币种 */
        String curString=(String) paramsMap.get("CUR_TYP");
        int curtyp = 0;
                    if (curString.equals("01"))
                        curtyp = 0;
                    else if (curString.equals("02"))
                        curtyp = 1;
                    else if (curString.equals("03"))
                        curtyp = 2;
        String[] typStrings = { "", "_CNY", "_SUM" };// 根据币种而改变查询条件

        if(checkedNodes != null && !checkedNodes.equals("") && !checkedNodes.equals("undefined") && !checkedNodes.equals("null"))
        {
            String[] checkedOrg = checkedNodes.split(",");
            if(checkedOrg.length>0){
                sb.append(" and ");
                if(checkedOrg.length>1){
                    sb.append("(");
                }
                for(int i=0;i<checkedOrg.length;i++){
                    sb.append(" t1.INSTN_ID = "+checkedOrg[i]+" ");
//                  //system.out.printlnntln(checkedOrg[i]+"^^^^");
                    if(i<checkedOrg.length-1){
                        sb.append(" or ");
                    }
                }
                if(checkedOrg.length>1){
                    sb.append(")");
                }
            }
        }
        
        /**判断名单类别*/
        int listty=0;
        if(listType.equals("1"))listty=0;
        else if(listType.equals("2"))listty=1;
        String[] listStrings = { "CUST", "ACCT" };// 根据名单类别而改变查询条件
        
        for (String key : this.getJson().keySet()) {
            if (null != this.getJson().get(key)
                    && !this.getJson().get(key).equals("")) {
                /*if (key.equals("INSTN_ID")){
                    sb.append(" and t1." + key + " = '"
                            + this.getJson().get(key) + "'");
                //system.out.printlnrintln(this.getJson().get(key)+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                    }
                else*/ if (key.equals("CUST_LIST")) {
                    List<String> list = rm.getCustName(this.getJson().get(key)
                            + "");
                    if (list.size() > 0) {
                        sb.append(" and ");
                        if (list.size() > 1)
                            sb.append("( ");
                        for (int i = 0; i < list.size(); i++) {
                            sb.append("t1."+listStrings[listty]+"_NAME = '" + list.get(i) + "'");
                            if (i < list.size() - 1)
                                sb.append(" or ");
                        }
                        if (list.size() > 1) {
                            sb.append(" ) ");
                        }
                    }
                } else if (key.equals("STAT_DT"))
                    sb.append(" and t1." + key + "= to_date('"
                            + this.getJson().get(key) + "','yyyy-MM-dd')");
                else if (key.equals("SUBJECT_COD1"))
                    sb.append(" and t1.SUBJECT_COD >= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("SUBJECT_COD2"))
                    sb.append(" and t1.SUBJECT_COD <= '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("ACCT_NO"))
                    sb.append(" and t1." + key + " = '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("ACCT_CLASS"))
                    sb.append(" and t1." + key + " = '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("CUST_NAME"))
                    sb.append(" and t1." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("CUST_ZZDM"))
                    sb.append(" and t1." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("BAL_FX"))
                    sb.append(" and t1." + key + " = '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("ACCT_NAME"))
                    sb.append(" and t1." + key + " like '%"
                            + this.getJson().get(key) + "%'");
                else if (key.equals("ACCT_STATUS"))
                    sb.append(" and t1." + key + " = '"
                            + this.getJson().get(key) + "'");
                else if (key.equals("ACCT_CHAR"))
                    sb.append(" and t1." + key + " = '"
                            + this.getJson().get(key) + "'");
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
                else if (key.equals("MATURE_DT1"))
                    sb.append(" and t1.MATURE_DT >= to_date('"
                            + this.getJson().get(key) + "','yyyy-MM-dd')");
                else if (key.equals("MATURE_DT2"))
                    sb.append(" and t1.MATURE_DT <= to_date('"
                            + this.getJson().get(key) + "','yyyy-MM-dd')");
            }
        }

        SQL = sb.toString();
        setPrimaryKey("t1.id");
//        addGreenplumLookup("intc_tpy", "JXFS");
//        addGreenplumLookup("acct_status", "ZHZT");
//        addGreenplumLookup("acct_char", "SFWJBH");
//        addGreenplumLookup("acct_class", "JXZL");
//        addGreenplumLookup("bal_fx", "DRYEFX");
        datasource = ds;
    }
    
}

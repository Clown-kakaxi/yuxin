package com.yuchengtech.bcrm.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/queryrestapplywait")
public class QueryRestApplyWaitAction extends CommonAction
{
  private static final long serialVersionUID = 1L;

  @Autowired
  @Qualifier("dsOracle")
  private DataSource dsOracle;

  public void prepare()
  {
    AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    StringBuilder sb = new StringBuilder("SELECT T.INSTANCEID,T.WFJOBNAME,T.WFNAME,T.WFSTARTTIME,T.PRENODEID,T.PRENODENAME,T.NODEID,T.NODENAME,T.SPSTATUS,T.WFSIGN,T.WFSTATUS,T.NODEPLANENDTIME,T.AUTHOR,A.USER_NAME AS AUTHOR_NAME");
    //add by liuming 20170703
	sb.append(",O.ORG_NAME,M1.T_MGR_ID,M1.T_MGR_NAME,M1.ORG_NAME AS T_ORG_NAME ");
    sb.append(",case ");
    sb.append(" when t.WFSIGN = 'comFirstExa'  then  ");//对公客户复核信息
    sb.append("     (select c.cust_name from acrm_f_ci_customer c where c.cust_id = substr(t.INSTANCEID, 4, 12)) ");
    sb.append(" when t.WFSIGN = 'comFsxFirReview'  then  ");//对公非授信修改
    sb.append("     (select c.cust_name from acrm_f_ci_customer c where c.cust_id = substr(t.INSTANCEID, 4, 12)) ");
    sb.append(" when t.WFSIGN = 'priCustBaseInfo_Check'  then  ");//对私基本信息修改
    sb.append("     (select c.cust_name from acrm_f_ci_customer c where c.cust_id = substr(t.INSTANCEID, 4, 12)) ");
    sb.append(" when t.WFSIGN = 'pubCustBaseInfo_Check'  then  ");//对公基本信息修改
    sb.append("     (select c.cust_name from acrm_f_ci_customer c where c.cust_id = substr(t.INSTANCEID, 4, 12)) ");
    sb.append(" when t.WFSIGN = 'perFsxFirReview'  then ");//对私非授信修改
    sb.append("     (select c.cust_name from acrm_f_ci_customer c where c.cust_id = substr(t.INSTANCEID, 4, 12)) ");
    sb.append(" when t.WFSIGN = 'qsjVisit'  then substr(t.WFJOBNAME, 11) ");//企商金新户、旧户拜访复核
    sb.append(" when t.WFSIGN = 'tmuOther'  then substr(t.WFJOBNAME, 16) ");//MU、投行及其他手续费审批
    sb.append(" when t.WFSIGN = 'newPay2'  then substr(t.WFJOBNAME, 11) ");//贷款新拨及还款审批
    sb.append(" when t.WFSIGN = 'desipotOut'  then substr(t.WFJOBNAME, 11) ");//存款汇入与汇出审批流程
    sb.append(" when t.WFSIGN = 'AntMoneyTask'  then substr(t.WFJOBNAME, 11) ");//反洗钱风险等级审核
    sb.append(" when t.WFSIGN = 'AntMoneyReview'  then substr(t.WFJOBNAME, 7) ");//反洗钱复评
    sb.append(" when t.WFSIGN = 'antMoneyAdjust'  then substr(t.WFJOBNAME, 11) ");//反洗钱风险等级调整
    sb.append("  when t.WFSIGN = 'openAccount' then substr(t.WFJOBNAME,4) ");//crm一键开户
    sb.append(" end CUST_NAME ");
    //add end
	sb.append(" FROM WF_WORKLIST T");
	sb.append(" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR");
    //add by liuming 20170703
	sb.append(" LEFT JOIN ADMIN_AUTH_ORG O  ON A.ORG_ID = O.ORG_ID ");
	sb.append(" LEFT JOIN (SELECT WM_CONCAT(C.CUST_NAME) AS CUST_NAME,T.INSTANCEID AS APPLYNO ");
	sb.append("              FROM WF_WORKLIST T  LEFT JOIN OCRM_F_CI_TRANS_CUST C  ");
	sb.append("               ON C.APPLY_NO = SUBSTR(T.INSTANCEID,INSTR(T.INSTANCEID, '_') + 1,LENGTH(T.INSTANCEID)) GROUP BY T.INSTANCEID) M ");
	sb.append("               ON T.INSTANCEID = M.APPLYNO ");
	sb.append(" LEFT JOIN (SELECT T.INSTANCEID AS A_APPLYNO,A.T_MGR_ID,A.T_MGR_NAME,O.ORG_NAME  ");
	sb.append("              FROM WF_WORKLIST T  LEFT JOIN OCRM_F_CI_TRANS_APPLY A ");
	sb.append("                ON TO_CHAR(A.APPLY_NO) = SUBSTR(T.INSTANCEID, INSTR(T.INSTANCEID, '_') + 1, LENGTH(T.INSTANCEID)) ");
	sb.append("                LEFT JOIN ADMIN_AUTH_ACCOUNT AC  ON AC.ACCOUNT_NAME = A.T_MGR_ID  ");
	sb.append("                LEFT JOIN ADMIN_AUTH_ORG O  ON AC.ORG_ID = O.ORG_ID) M1  ON T.INSTANCEID = M1.A_APPLYNO  ");
	//add end
	sb.append(" where T.CURRENTNODEUSERS LIKE '%"+auth.getUserId()+"%'");
    sb.append(" AND T.WFSTATUS <> '3' ");//状态,3-异常中止的将在办结任务中查询
    
    for (String key : getJson().keySet()) {
      if ((getJson().get(key) != null) && (!getJson().get(key).equals(""))) {
        if (key.equals("INSTANCEID"))
          sb.append(" and t." + key + " = '" + getJson().get(key) + "'");
        else if ((key.equals("WFJOBNAME")) || (key.equals("WFNAME")) || (key.equals("AUTHOR")))
          sb.append(" and t." + key + " like '%" + getJson().get(key) + "%'");
      }
    }
    setPrimaryKey("T.WFSTARTTIME DESC");

    this.SQL = sb.toString();
    this.datasource = this.dsOracle;
  }
}
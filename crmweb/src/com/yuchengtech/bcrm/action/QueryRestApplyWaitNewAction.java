package com.yuchengtech.bcrm.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 首页磁贴待办工作查询
 */
@Action("/queryrestapplywaitNew")
public class QueryRestApplyWaitNewAction extends CommonAction {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	public void prepare() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StringBuilder sb = new StringBuilder("SELECT T.INSTANCEID,T.WFJOBNAME,T.WFNAME,to_char(to_date(T.WFSTARTTIME,'yyyy-MM-dd HH24:mi:ss'),'yyyy-MM-dd') as WFSTARTTIME,T.PRENODEID,T.PRENODENAME,T.NODEID,T.NODENAME,T.SPSTATUS,T.WFSIGN,T.WFSTATUS,T.NODEPLANENDTIME,T.AUTHOR,A.USER_NAME AS AUTHOR_NAME");
		sb.append(",O.ORG_NAME,M.CUST_NAME,M1.T_MGR_ID,M1.T_MGR_NAME,M1.ORG_NAME AS T_ORG_NAME ");
		sb.append(" FROM WF_WORKLIST T");
		sb.append(" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR");
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
		sb.append(" where T.CURRENTNODEUSERS LIKE '%"+auth.getUserId()+"%'");
        sb.append(" AND T.WFSTATUS <> '3' ");//状态,3-异常中止的将在办结任务中查询
        
        setPrimaryKey("T.WFSTARTTIME DESC");
        
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

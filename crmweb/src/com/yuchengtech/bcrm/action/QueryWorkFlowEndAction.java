package com.yuchengtech.bcrm.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 
 *
 * @author : 
 * @update 20141013,增加查询控制-撤办处理
 */
@Action("/queryworkflowend")
public class QueryWorkFlowEndAction extends CommonAction {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;
    
	public void prepare() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StringBuilder sb = new StringBuilder("SELECT T.*,A.USER_NAME AS AUTHOR_NAME FROM (");
		sb.append(" SELECT T0.INSTANCEID,T0.WFJOBNAME,T0.WFNAME,T0.WFSTARTTIME,T0.WFENDTIME,T0.SPSTATUS,T0.WFSIGN,T0.AUTHOR");
		sb.append(" FROM WF_INSTANCE_END T0 ");
		sb.append(" WHERE (T0.ALLREADERSLIST LIKE '%"+auth.getUserId()+"%')");
		sb.append(" UNION");
		sb.append(" SELECT T1.INSTANCEID,T1.WFJOBNAME,T1.WFNAME,T1.WFSTARTTIME,'' AS WFENDTIME,'-1' SPSTATUS,T1.WFSIGN,T1.AUTHOR");
		sb.append(" FROM WF_WORKLIST T1 WHERE T1.WFSTATUS = '3' AND T1.ALLREADERSLIST LIKE '%"+auth.getUserId()+"%'");//WFSTATUS 3,表示流程异常中止（包括撤销办理的）
		sb.append(" ) T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where 1=1 ");
		
		for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
            	if(key.equals("WFJOBNAME")||key.equals("WFNAME")||key.equals("AUTHOR"))
            		sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
            }
        }
		setPrimaryKey("T.WFSTARTTIME DESC,T.WFENDTIME DESC");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

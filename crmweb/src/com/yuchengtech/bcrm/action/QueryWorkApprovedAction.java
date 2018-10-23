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
 * @update 20141013,增加查询控制-过滤撤办处理
 */
@Action("/queryWorkApproved")
public class QueryWorkApprovedAction extends CommonAction {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	public void prepare() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StringBuilder sb = new StringBuilder("SELECT T.INSTANCEID,T.WFJOBNAME,T.WFNAME,T.WFSTARTTIME,T.PRENODEID,T.PRENODENAME,T.NODEID,T.NODENAME,T.SPSTATUS,T.WFSIGN,T.WFSTATUS,T.AUTHOR,A.USER_NAME AS AUTHOR_NAME");
		sb.append(" FROM WF_WORKLIST T");
		sb.append(" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR");
		sb.append(" where t.ALLREADERSLIST LIKE '%"+auth.getUserId()+"%' AND T.CURRENTNODEUSERS NOT LIKE '%"+auth.getUserId()+"%'");
        sb.append(" AND T.WFSTATUS <> '3' ");//状态,3-异常中止的将在办结任务中查询
		
		for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
            	if(key.equals("WFJOBNAME")||key.equals("WFNAME")||key.equals("AUTHOR"))
            		sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
            }
        }
		this.setPrimaryKey("T.WFSTARTTIME DESC");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

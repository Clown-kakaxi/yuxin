package com.yuchengtech.bcrm.customer.action;

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
@Action(value="/graphlist", results={
    @Result(name="success", type="json"),
})
public class QueryUserGraphAction extends CommonAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	@Override
	public void prepare() {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        String userId = auth.getUserId();
        
        String searchSql = "SELECT g.ID ,g.GRAPH_NAME,g.GRAPH_DESCRIBE," +
        		"g.AUTHOR,(SELECT u.USERNAME FROM SYS_USERS u WHERE u.USERID=g.AUTHOR) AS AUTHOR_NAME,g.CREATE_DATE," +
        		"g.UPDATER,(SELECT  u.USERNAME FROM SYS_USERS u WHERE u.USERID=g.UPDATER) AS UPDATER_NAME,g.UPDATE_DATE," +
        		"g.CREATE_ORG,(SELECT o.UNITNAME FROM SYS_UNITS o WHERE o.UNITID = g.CREATE_ORG) AS CREATE_ORG_NAME " +
        		"FROM OCRM_F_CI_GRAPH g WHERE g.AUTHOR = '"+userId+"' ";
        
        for(String key : this.getJson().keySet()){
        	if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
        		if(null!=key&&key.equals("GRAPH_NAME"))
        			searchSql += " AND g.GRAPH_NAME like '%"+this.getJson().get(key)+"%' ";
        		if(null!=key&&key.equals("CREATE_DATE")){
        			String et = (String)this.getJson().get(key);
        			searchSql += " AND g.CREATE_DATE ='"+et.substring(0, 10)+"' ";
        		}
        	}
        }
        
        SQL=searchSql;
        datasource = ds;
	}
	
}

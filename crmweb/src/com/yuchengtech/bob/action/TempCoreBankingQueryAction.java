package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/tempCoreBankingQuery")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "tempCoreBankingQuery"})
})
public class TempCoreBankingQueryAction extends CommonAction{
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    int i=0;
	@Override
	public void prepare() {
		
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
    	String id = (String) this.getJson().get("id");
    	//system.out.printlnln(id);
        StringBuilder sb = new StringBuilder("select p.*,");
        sb.append("to_char(invoke_time, 'YYYY-MM-DD HH24:MI:SS') as invoked, ");
        sb.append("to_char(finish_time, 'YYYY-MM-DD HH24:MI:SS') as finished ");
        sb.append("from ocrm_temp_core_banking p where 1>0");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
            	if(key.equals("SUBJECT_ID")){
            		sb.append(" and " + key + " = '" + this.getJson().get(key) + "'");
            	}else{
            		if(key.equals("ACCOUNT")||key.equals("BRANCH_ID")){
            		sb.append(" and " + key + " = '" + this.getJson().get(key) + "'");
            		}else{
            			if(key.equals("CURRENCY_TYPE")){
            				sb.append(" and " + key + " = '" + this.getJson().get(key) + "'");
            			}else{
            				if(key.equals("CLIENT_NAME")){
            				sb.append(" and "+key+" like "+" '%"+this.getJson().get(key)+"%'");
            				}
            			}
            		}
            	}
            }
        }
        sb.append("and user_id = '"+currenUserId+"'");
        /**
         * 由于网点号由核心提供，但尚未得到，暂时取消该约束
         */
        //setBranchFileldName("p.BRANCH_ID"); 
    	setPrimaryKey("p.ID desc");
    	SQL = sb.toString();
        datasource = ds;
	}
}

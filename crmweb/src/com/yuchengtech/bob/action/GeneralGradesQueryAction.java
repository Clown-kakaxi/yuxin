package com.yuchengtech.bob.action;

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
@Action(value="/gradeQuery", results={
    @Result(name="success", type="json"),
})
public class GeneralGradesQueryAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
    public void prepare() {
    	
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String str = auth.getUserId();
		
        StringBuilder sb = new StringBuilder("select g.*,u.USERNAME from ocrm_f_ci_general_grades g  left join sys_users u on g.FILLER_NAME = u.USERID where 1>0 and g.FILLER_NAME = '" +str+"'" );
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("CARD_USE"))
                    sb.append(" and "+key+" like '%"+this.getJson().get(key)+"%'");
                else if(key.equals("APPLY_DATES"))
                    sb.append(" and APPLY_DATE >= to_date('" +this.getJson().get(key)+ "','yyyy-MM-dd')");
                else if(key.equals("APPLY_DATEE"))
                    sb.append(" and APPLY_DATE <= to_date('" +this.getJson().get(key)+ "','yyyy-MM-dd')");
                else{
                	sb.append(" and "+key+" = "+this.getJson().get(key));
                }
            }
        }
        setPrimaryKey(" g.APPLY_DATE desc,g.ID");
        addOracleLookup("CARD_USE", "CARD_USE");
        
        SQL=sb.toString();
        datasource = ds;
    }
}

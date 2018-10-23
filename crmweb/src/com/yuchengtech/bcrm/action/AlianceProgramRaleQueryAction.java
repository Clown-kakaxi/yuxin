package com.yuchengtech.bcrm.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/alianceProgramRaleQueryAction", results={
    @Result(name="success", type="json"),
})
public class AlianceProgramRaleQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
 	public void prepare() {
// 		ActionContext ctx = ActionContext.getContext();
 		//HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuilder sb = new StringBuilder("select t.*,o.org_name,AO.ORG_NAME CREATE_ORG_NAME,AA.USER_NAME CREATE_USER_NAME from OCRM_F_CI_ALIANCERVICE_RELA t left join admin_auth_org o on o.org_id = t.RANGE_APPLY  " 
        		+ " LEFT JOIN ADMIN_AUTH_ORG AO ON T.Create_Org=AO.ORG_ID"
        		+ " LEFT JOIN ADMIN_AUTH_ACCOUNT AA ON T.Create_User=AA.ACCOUNT_NAME"
        		+ " where 1>0");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("ALIANCE_PROGRAM_NAME"))
                    sb.append(" and t.ALIANCE_PROGRAM_NAME ='"+this.getJson().get(key)+"'");
                else if(key.equals("ADD_SERVICE_IDENTIFY")){
                	sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
                }else if("ORG_NAME".equals(key)){
                	sb.append(" and o.ORG_NAME = '"+this.getJson().get(key)+"'");
                }else if(key.equals("CREATE_USER")){
                	sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
                }else if(key.equals("CREATE_ORG")){
                	sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
                }else if(key.equals("CREATE_DATE")){
                	sb.append(" and t."+key+" = to_date('" + this.getJson().get(key).toString().substring(0, 10)
							+ "','yyyy-MM-dd')");
                }
            }
        }
        setPrimaryKey("t.ID,t.CREATE_DATE desc");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

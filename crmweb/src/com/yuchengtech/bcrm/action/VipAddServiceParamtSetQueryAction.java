package com.yuchengtech.bcrm.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/vipAddServiceParamtSetQueryAction", results={
    @Result(name="success", type="json"),
})
public class VipAddServiceParamtSetQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
 	public void prepare() {
// 		ActionContext ctx = ActionContext.getContext();
 		//HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuilder sb = new StringBuilder("select t.*,o.org_name as create_org_name,a.user_name as create_user_name from OCRM_F_CI_VIPADDPARAM_SET t " +
        		"left join admin_auth_org o on t.create_org=o.org_id left join admin_auth_account a on t.create_user=a.account_name " +
        		//"LEFT JOIN SYS_USERS S ON S.USERID=t.CREATE_USER " +
        		//"LEFT JOIN SYS_UNITS S1 ON S1.UNITID=t.CREATE_ORG " +
        		"where 1>0");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("ADD_SERVICE_NAME"))
                    sb.append(" and t.ADD_SERVICE_NAME ='"+this.getJson().get(key)+"'");
                else if(key.equals("VIP_CARD_LEVEL")){
                	sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
                }else if(key.equals("CREATE_USER")){
                	sb.append(" and t.CREATE_USER = '"+this.getJson().get(key)+"'");
                }else if(key.equals("CREATE_ORG_NAME")){
                	sb.append(" and o.org_name = '"+this.getJson().get(key)+"'");
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

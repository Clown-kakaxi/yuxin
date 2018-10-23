package com.yuchengtech.bcrm.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 联盟商管理信息查询ACTION
 * @author hujun
 * 2014-06-23
 */
@ParentPackage("json-default")
@Action(value="/alianceProgramQueryAction", results={
    @Result(name="success", type="json"),
})
public class AlianceProgramQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;   
 	public void prepare() {
 		ActionContext ctx = ActionContext.getContext();
 		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuilder sb = new StringBuilder("select t.*,o.org_name from OCRM_F_CI_ALIANCE_PROGRAM t left join ADMIN_AUTH_ORG o on t.service_range = o.org_id where 1>0");
        String id=request.getParameter("id");
        if(id!=null){
        	sb.append(" and t.id='"+id+"'");
        }
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("ALIANCE_PROGRAM_NAME"))
                    sb.append(" and t.ALIANCE_PROGRAM_NAME ='"+this.getJson().get(key)+"'");
                else if(key.equals("ALIANCE_PROGRAM_ID")){
                	sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
                }else if(key.equals("ALIANCE_PROG_LEVEL")){
                	sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
                }else if(key.equals("ORG_NAME")){
                	sb.append(" and t.SERVICE_RANGE = '"+this.getJson().get(key)+"'");
                }else if(key.equals("START_DATE")){
                	sb.append(" and t."+key+" = to_date('" + this.getJson().get(key).toString().substring(0, 10)
							+ "','yyyy-MM-dd')");
                }else if(key.equals("END_DATE")){
                	sb.append(" and t."+key+" = to_date('" + this.getJson().get(key).toString().substring(0, 10)
							+ "','yyyy-MM-dd')");
                }
            }
        }
        addOracleLookup("ALIANCE_PROG_LEVEL", "ALIANCE_PROG_LEVEL");
        setPrimaryKey("t.ID desc");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

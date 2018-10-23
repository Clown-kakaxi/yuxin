package com.yuchengtech.bob.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/rollQuery", results={
    @Result(name="success", type="json"),
})
public class RollQueryAction extends CommonAction{
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;	

	public void prepare() {
		// TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder("select r.*,u1.username as create_user_name, u2.username as update_user_name from OCRM_F_MM_ROLL_CUST r " +
        		"left join (select t1.userid userid, t1.username username, t2.unitid unitid from sys_users t1 inner join sys_units t2 on t1.unitid=t2.id) u1 on r.create_user = u1.userid " +
        		"left join sys_users u2 on r.update_user = u2.userid where 1>0");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("ROLL_TYPE"))
                    sb.append(" and r."+key+"="+this.getJson().get(key));
                else if(key.equals("createDateS"))
                    sb.append(" and r.CREATE_DATE >= to_date('" +this.getJson().get(key)+ "','yyyy-MM-dd')");
                else if(key.equals("createDateE"))
                    sb.append(" and r.CREATE_DATE <= to_date('" +this.getJson().get(key)+ "','yyyy-MM-dd')");
                else{
                	sb.append(" and r."+key+" like '%"+this.getJson().get(key)+"%'");
                }
            }
        }
        setPrimaryKey(" r.ROLL_TYPE, r.update_date desc");
        setBranchFileldName("u1.unitid");
        addOracleLookup("ROLL_TYPE", "ROLL_TYPE");
        addOracleLookup("ROLL_KIND", "ROLL_KIND");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}

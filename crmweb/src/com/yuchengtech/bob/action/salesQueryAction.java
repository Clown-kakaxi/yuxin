package com.yuchengtech.bob.action;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;

@ParentPackage("json-default")
@Action(value="/salesquery", results={
    @Result(name="success", type="json")
})
/**
 * 对greenplum数据库中客户经理销售信息查询
 * Author: 苏建明
 * */
public class salesQueryAction extends BaseAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	public String index() {
        try {
        	 StringBuilder s = new StringBuilder("select * from mdm.acrm_m_ci_prd");
        	   for(String key:this.getJson().keySet()){
                   if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                    if(key.equals("CUST_ID"))
                           s.append(" and "+key+"like"+" '%"+this.getJson().get(key)+"%'");
                    if(key.equals("STAT_DT"))
                        s.append(" and "+key+"like"+" '%"+this.getJson().get(key)+"%'");
                    if(key.equals("CUST_NAME"))
                        s.append(" and "+key+"like"+" '%"+this.getJson().get(key)+"%'");
                    if(key.equals("HY_TYP"))
                        s.append(" and "+key+"like"+" '%"+this.getJson().get(key)+"%'");
               }}
        	   int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi);
        	   setJson(qh.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return "success";
    }
}
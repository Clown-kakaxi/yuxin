package com.yuchengtech.bob.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bob.service.CommonQueryService;

@Action("/orginfo1")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "orginfo1"})
})
public class SeOrgInfoQueryAction extends BaseAction{
    
	@Autowired
	private CommonQueryService cqs ;
    
    public String index() {
        StringBuilder sb = new StringBuilder("select p.* from se_org_info p  where 1>0");
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("AGECODE"))
                    sb.append(" and "+key+" like '%"+this.getJson().get(key)+"%'");
                else{
                	sb.append(" and "+key+" = "+this.getJson().get(key));
                }
            }
        }
        sb.append(" order by p.AGECODE desc");
        cqs.setPrimaryKey("p.ID");
        this.setJson((Map<String, Object>) cqs.excuteQuery(sb.toString(),this.getStart(),this.getLimit()));       
        return "success";
    }
}

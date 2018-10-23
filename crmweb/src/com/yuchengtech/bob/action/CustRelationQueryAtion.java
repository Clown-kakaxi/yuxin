package com.yuchengtech.bob.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bob.service.CommonQueryService;

@ParentPackage("json-default")
@Action(value="/queryRalationChart", results={
    @Result(name="success", type="json"),
})

public class CustRelationQueryAtion extends BaseAction{
    
    @Autowired
    private CommonQueryService cqs;
    
    
    public String index() {
        StringBuilder sb = new StringBuilder("select t1.* from OCRM_F_CI_RELATION t1 where t1.DEST_CUST_ID=A201108290113988'");

        cqs.setPrimaryKey("t1.id");
        this.setJson((Map<String, Object>) cqs.excuteQuery(sb.toString(),0,100000));       
        return "success";
    }
}

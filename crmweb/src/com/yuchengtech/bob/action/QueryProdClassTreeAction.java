/**
 * 
 */
package com.yuchengtech.bob.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.yuchengtech.bob.service.SystemUnitRecursiveGPService;

/**
 * @author km
 * 
 */
@ParentPackage("json-default")
@Action(value = "/queryprodleveltree", results = { @Result(name = "success", type = "json") })
public class QueryProdClassTreeAction // extends CommonAction
        implements ModelDriven<Object>, Validateable {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    private HttpServletRequest request;
    private SystemUnitRecursiveGPService systemUnitRecursiveGPService = new SystemUnitRecursiveGPService();
    @SuppressWarnings("rawtypes")
    private List childUnitList;

  
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void index() {


        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);

        String level = request.getParameter("level");
        StringBuffer stringBuffer = new StringBuffer(
                "select * from (SELECT distinct t.CATL_NAME FROM ocrm_f_pd_prod_catl t where t.CATL_LEVEL='"+level+"' ) t ");
        Map systemUnitMap = systemUnitRecursiveGPService
                .systemUnitList(stringBuffer.toString());
        List unitList = (List) systemUnitMap.get("data");

        for (int i = 0; i < unitList.size(); i++) {
            Map tempMap = (Map) unitList.get(i);
            tempMap.put("id", (tempMap.get("prod_level"+level+""))+"");
            tempMap.put("text", (tempMap.get("prod_level"+level+""))+"");
            tempMap.put("checked", false);
            tempMap.put("leaf", "true");
        }
        childUnitList = unitList;
        // SQL=stringBuffer.toString();
        // datasource = ds;
    }

    public void validate() {
        // TODO Auto-generated method stub

    }

    public Object getModel() {
        // TODO Auto-generated method stub
        return childUnitList;
    }

}

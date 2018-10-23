package com.yuchengtech.bcrm.payOrRepay.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.action.BaseQueryAction;
import com.yuchengtech.bob.action.LookupAction.KeyValuePair;
import com.yuchengtech.bob.core.QueryHelper;

@ParentPackage("json-default")
@Action(value = "/specialSearchAction", results = { @Result(name = "success", type = "json") })
public class SpecialSearchAction extends BaseQueryAction{
	public class KeyValuePair {
	        
	        private String key;
	        private String value;
	        
	        public KeyValuePair(String key, String value) {
	            this.key = key;
	            this.value = value;
	        }
	
	        public String getKey() {
	            return key;
	        }
	               
	        public String getValue() {
	            return value;
	        }
	                
	    }
	
    private List<KeyValuePair> JSON;
    
    public SpecialSearchAction() {
        JSON = new ArrayList<KeyValuePair>();
    }
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;//数据源
    public void prepare(){}
    
    @SuppressWarnings("unchecked")
	public List<KeyValuePair> searchSpecial1() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String custType = request.getParameter("custType"); // 获取客户类别
		
		StringBuilder sb = new StringBuilder("");
		sb.append("  SELECT F.F_VALUE AS VALUE, F.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM F WHERE F.F_LOOKUP_ID='XD000304'");
		if(null == custType ||custType.equals("")){
			sb.append("  ");
		}	
		else if (custType.equals("2")){//个人
			sb.append(" AND F.F_CODE IN('J','K')");
		}
		else{//企业
			sb.append(" AND F.F_CODE NOT IN('J','K')");
		}
		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			 ConcurrentHashMap<String, String> map = null;
			 for(int i=0;i<((List) json.get("data")).size();i++){
				HashMap m = (HashMap)((ArrayList) json.get("data")).get(i);
                JSON.add(new KeyValuePair(m.get("KEY").toString(),  m.get("VALUE").toString()));
			 }
		} catch (Exception e) {
		}
		return JSON;
	       
}
    @SuppressWarnings("unchecked")
	public void searchSpecial() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String custType = request.getParameter("custType"); // 获取客户类别
		
		StringBuilder sb = new StringBuilder("");
		sb.append("  SELECT F.F_VALUE AS VALUE, F.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM F WHERE F.F_LOOKUP_ID='XD000304'");
		if(null == custType ||custType.equals("")){
			sb.append("  ");
		}	
		else if (custType.equals("2")){//个人
			sb.append("  AND F.F_CODE IN ('99','J','K')");
		}
		else{//企业
			sb.append(" AND F.F_CODE NOT IN('J','K')");
		}
		
		sb.append("  ORDER BY REPLACE(F.F_CODE,99,0)"); // 设置查询排序条件
		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			((List) json.get("data")).add(map);

		} catch (Exception e) {
	  }
   }

}

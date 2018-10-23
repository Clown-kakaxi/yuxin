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
@Action(value = "/searchAddressAction", results = { @Result(name = "success", type = "json") })
public class SearchAddressAction extends BaseQueryAction{
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
    
    public SearchAddressAction() {
        JSON = new ArrayList<KeyValuePair>();
    }
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;//数据源
    public void prepare(){}
    /**
     * 查询省
     */
    @SuppressWarnings("unchecked")
	public void searchS() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String custType = request.getParameter("custType"); // 获取客户类别
		
		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT  OI.F_VALUE AS VALUE, OI.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM OI WHERE OI.F_LOOKUP_ID ='XD000001' AND OI.F_CODE LIKE '%0000'");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
			((List) json.get("data")).add(map);

		} catch (Exception e) {
	  }
   }
//    @SuppressWarnings("unchecked")
//   	public void searchC() {
//       	ActionContext ctx = ActionContext.getContext();
//   		request = (HttpServletRequest) ctx
//   				.get(ServletActionContext.HTTP_REQUEST);
//   		String custType = request.getParameter("custType"); // 获取客户类别
//   		
//   		StringBuilder sb = new StringBuilder("");
//   		sb.append("SELECT  OI.F_VALUE AS VALUE, OI.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM OI WHERE OI.F_LOOKUP_ID ='XD000001' AND OI.F_CODE LIKE '%0000'");
//
//   		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
//   		try {
//   			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
//   			json = qh.getJSON();
//   			HashMap<String, Object> map = new HashMap<String, Object>();
//   			((List) json.get("data")).add(map);
//
//   		} catch (Exception e) {
//   	  }
//      }
//    @SuppressWarnings("unchecked")
//	public void ifNew() {
//    	ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest) ctx
//				.get(ServletActionContext.HTTP_REQUEST);
//		String custType = request.getParameter("custType"); // 获取客户类别
//		
//		StringBuilder sb = new StringBuilder("");
//		sb.append("SELECT  OI.F_VALUE AS VALUE, CASE OI.F_CODE WHEN '0' THEN '2' ELSE '1' END AS KEY FROM OCRM_SYS_LOOKUP_ITEM OI WHERE OI.F_LOOKUP_ID ='IF_FLAG'");
//
//		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
//		try {
//			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
//			json = qh.getJSON();
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("KEY", "9");
//			map.put("VALUE", "未知");
//			((List) json.get("data")).add(map);
//		} catch (Exception e) {
//	  }
//   }
}

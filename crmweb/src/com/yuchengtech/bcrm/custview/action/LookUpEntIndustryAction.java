package com.yuchengtech.bcrm.custview.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.yuchengtech.bob.core.LookupManager;

@ParentPackage("json-default")
@Action(value="/lookupEntIndustry", results={
  @Result(name="success", type="json"),
})
public class LookUpEntIndustryAction {
  

  private List<HashMap<String, Object>> JSON =new ArrayList<HashMap<String, Object>>();
  
  private String name;
  

  public String index() {
      return "success";
  }

  public List<HashMap<String, Object>> getJSON() {
	  ConcurrentHashMap<String, String> map = LookupManager.getInstance().getOracleValues(name);
	  if (map != null) {
    	  String parent="";
    	  for(Entry<String, String> item : map.entrySet()) {
    		  HashMap<String, Object> map2 = new HashMap<String, Object>();
    		  if(item.getKey().length()==5){
    			  parent = item.getKey().substring(0,4);
    		  } else if(item.getKey().length() == 4){
    			  parent = item.getKey().substring(0,3);
    		  }else if(item.getKey().length() == 3){
    			  parent = item.getKey().substring(0,1);
    		  }else{
    			  parent = "0";
    		  }
    		  map2.put("ID", item.getKey());
    		  map2.put("VALUE", item.getValue());
    		  map2.put("PARENT",parent);
    		  JSON.add(map2);
    	  }
      }
      return JSON;
  }

  public void setName(String name) {
      this.name = name;
  }

}

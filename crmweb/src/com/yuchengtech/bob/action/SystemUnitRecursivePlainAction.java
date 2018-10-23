/**
 * 
 */
package com.yuchengtech.bob.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.service.SystemUnitRecursiveService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @author yanzl
 *
 */
@ParentPackage("json-default")

@Action(value="/system-unit-recursive-plain", results={
    @Result(name="success", type="json")
})
public class SystemUnitRecursivePlainAction extends CommonAction implements
ModelDriven<Object>, Validateable{
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	private SystemUnitRecursiveService  systemUnitRecursiveService = new SystemUnitRecursiveService();
	@SuppressWarnings({ "unchecked" })
	private List childUnitList ;	
	@SuppressWarnings({ "unchecked" })
    public void prepare() {
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		String unit_id = auth.getUnituid();
		StringBuffer stringBuffer = new StringBuffer("select  t.*   from acrm_f_sm_sys_units_sta t connect by prior t.id = t.superunitid	" 
				+" start with t.id ='"+unit_id+"'");
		Map systemUnitMap = systemUnitRecursiveService.systemUnitList(stringBuffer.toString());
		List unitList = (List)systemUnitMap.get("data");
		Map rootMap=null;
		List childrenString = new ArrayList();
		try{
			for(int i=0;i<unitList.size();i++){			
				Map tempMap = (Map)unitList.get(i);
				tempMap.put("id", (tempMap.get("UNITID")+""));
				tempMap.put("text", tempMap.get("UNITNAME"));
				tempMap.put("expanded", true);
				if((tempMap.get("ID")+"").equals(unit_id)){
					rootMap = tempMap;
				}
			}
			for(int j=0;j<unitList.size();j++){
				Map temp =(Map) unitList.get(j);
				if(rootMap.get("ID").equals(temp.get("SUPERUNITID")) ){
					children(temp,unitList);
					childrenString.add(temp);					
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(childrenString.size() == 0){
			rootMap.put("leaf", "true");			
		}
		rootMap.put("children", childrenString);
		
		//system.out.printlnln(rootMap);
		
		childUnitList = childrenString;
		
        SQL=stringBuffer.toString();
        datasource = ds;
	}
	
	//递归得到children元素
	@SuppressWarnings({ "unchecked" })
	public void children(Map parentMap,List unitList){

		List childList = new ArrayList();
		for(int i=0;i<unitList.size();i++){			
			Map tempMap = (Map)unitList.get(i);			
			if((tempMap.get("SUPERUNITID")+"").equals(parentMap.get("ID")+""))
			{
				childList.add(tempMap);
				children(tempMap,unitList);
			}
		}
		parentMap.put("children", childList);
		if(childList.size()==0){			
			parentMap.put("leaf", "true");
		}
	}		
	
	
	public void validate() {
		// TODO Auto-generated method stub
		
	}
	public Object getModel() {
		// TODO Auto-generated method stub
		return childUnitList;
	}

}

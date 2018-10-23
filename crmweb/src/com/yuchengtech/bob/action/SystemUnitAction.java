package com.yuchengtech.bob.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.yuchengtech.bcrm.common.service.OrgSearchService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 机构树
 * @author lixb
 * @since 2012-09-21
 *
 */
@ParentPackage("json-default")

@Action(value="/system-unit-recursive1", results={
    @Result(name="success", type="json")
})
@SuppressWarnings("unchecked")
public class SystemUnitAction extends BaseAction implements
ModelDriven<Object>, Validateable{
	
	@Autowired
	private OrgSearchService oss;
	private List childUnitList ;
	public String index() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);	
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		String unit_id = auth.getUnitId();
		Map systemUnitMap =oss.searchSubOrgTree(unit_id);
		List unitList = (List)systemUnitMap.get("data");
		Map rootMap=null;
		List childrenString = new ArrayList();
		String lable = request.getParameter("lable");
		try{
			for(int i=0;i<unitList.size();i++){			
				Map tempMap = (Map)unitList.get(i);
//				tempMap.put("id", (tempMap.get("UNITID")+""+tempMap.get("LEVELUNIT")));
				tempMap.put("id", (tempMap.get("UNITID")));
				tempMap.put("text", tempMap.get("UNITNAME"));
				tempMap.put("level", tempMap.get("LEVELUNIT"));
				tempMap.put("expanded", true);
				tempMap.put("checked", false);  //子节点复选框
				if((tempMap.get("UNITID")+"").equals(unit_id)){
					rootMap = tempMap;
				}
			}
			for(int j=0;j<unitList.size();j++){
				Map temp =(Map) unitList.get(j);
				if(rootMap.get("UNITID").equals(temp.get("SUPERUNITID")) ){
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
		return "success";
	}
	
	//递归得到children元素
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
		
		return childUnitList;
	}

}

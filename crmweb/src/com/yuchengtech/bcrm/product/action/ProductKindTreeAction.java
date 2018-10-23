/**
 * 
 */
package com.yuchengtech.bcrm.product.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.yuchengtech.bcrm.product.service.ProductKindTreeService;
import com.yuchengtech.bob.action.BaseAction;

/**
 * @author yaoliang
 *
 */
@Action("/product-kind-tree")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "product-kind-tree" }) })
		
public class ProductKindTreeAction extends BaseAction implements
ModelDriven<Object>, Validateable{
	
	private List kindChild = new ArrayList(); 
	@Autowired
	ProductKindTreeService productKindTreeService;
	
	public String index(){
		
		StringBuffer sb = new StringBuffer("select OCRM_F_PD_PROD_CATL.* from OCRM_F_PD_PROD_CATL where (1=1) ");
		
		Map params = this.getJson();
		
		Iterator iterator = params.keySet().iterator();
		
		while(iterator.hasNext()){
			
			String key = (String)iterator.next();
			String keyValue = (String)params.get(key);			
			if(key.equals("CATL_PARENT")){
				if(keyValue==null || keyValue.equals("") || keyValue.equals("null")){
					sb.append("  and CATL_PARENT is null ");
				}else{
					sb.append("  and " + key + " = " + keyValue  + " ");
				}
			}
			else{
				
				if(keyValue!=null && !keyValue.equals("") && !keyValue.equals("null")){				
					sb.append("  and " + key + " = '" + keyValue  + "' ");
				}
			}
			
		}		
		Map map = productKindTreeService.productListKinds(sb.toString());
		transToTreeNode((List)map.get("data"));
		return "success";
	}
	
	public void transToTreeNode(List list){
		kindChild = list;
		for(int i=0;i<kindChild.size();i++){			
			Map map = (Map)list.get(i);
			map.put("id", map.get("CATL_CODE"));
			map.put("text", map.get("CATL_NAME"));
			map.put("cls", map.get("CATL_LEVEL"));
//			String leaf = (String)map.get("IS_LEAF");
//			if(leaf == null || leaf.equals("Y") || leaf.equals("") || leaf.equals("null")){
//				map.put("leaf", Boolean.parseBoolean("true"));
//			}else
//			{
//				map.put("leaf", Boolean.parseBoolean("false"));
//			}
		}
		
	}
	
	
	public List getKindChild() {
		return kindChild;
	}

	public void setKindChild(List kindChild) {
		this.kindChild = kindChild;
	}

	public void validate() {
		// TODO Auto-generated method stub
		
	}

	public Object getModel() {
		return kindChild;
		// TODO Auto-generated method stub
	//	return null;
	}
}

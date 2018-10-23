/**
 * 
 */
package com.yuchengtech.bcrm.product.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bcrm.product.service.ProductKindsSearchService;
import com.yuchengtech.bob.action.BaseAction;
import com.yuchengtech.bob.service.CommonQueryService;

/**
 * @author yaoliang
 *2011-08-30
 */
@ParentPackage("json-default")

@Action(value="/product-kinds-search", results={
    @Result(name="success", type="json")
})
public class ProductKindsSearchAction extends BaseAction {
	
	private Map<String,Object> kindsSearchMap; 
	
	@Autowired
	private CommonQueryService commonQueryService ;
	@Autowired
	private ProductKindsSearchService productKindsSearchService;
	
	public String index() throws Exception{
		
		HttpServletRequest req = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer("select OCRM_F_PD_PROD_CATL.* from OCRM_F_PD_PROD_CATL where (1=1) ");
		
		Map map = this.getJson();
		
		Iterator iterator = map.keySet().iterator();
		
		while(iterator.hasNext()){
			
			String key = (String)iterator.next();
			
			String keyValue = (String)map.get(key);
			
			if(keyValue !=null && !keyValue.equals("")){
				
				sb.append(" and "+key+" = "+keyValue);
			}
		}		
			
		commonQueryService.setPrimaryKey("CATL_CODE");
		
//		kindsSearchMap=commonQueryService.excuteQuery(sb.toString(),this.getStart(),this.getLimit());	
		
		kindsSearchMap=productKindsSearchService.productKindsList(sb);	
		
		return "success";
	}
	
	public Map getKindsSearchMap() {
	
		return kindsSearchMap;
	
	}
	public void setKindsSearchMap(Map kindsSearchMap) {
	
		this.kindsSearchMap = kindsSearchMap;
	
	}
	
}

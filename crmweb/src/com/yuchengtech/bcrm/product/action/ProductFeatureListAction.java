/**
 * 
 */
package com.yuchengtech.bcrm.product.action;

import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bob.action.BaseAction;
import com.yuchengtech.bob.service.CommonQueryService;
/**
 * @author yaoliang
 *
 */
@ParentPackage("json-default")
@Action(value="/product-feature-list",results={
	@Result(name = "success",type="json")
})
public class ProductFeatureListAction extends BaseAction{
	
	private Map productFeatureMap;
	@Autowired
	private CommonQueryService commonQueryService ;
	
	public String index(){
		
		StringBuffer sb = new StringBuffer("select OCRM_F_PD_PROD_FEATURE.* from OCRM_F_PD_PROD_FEATURE where  (1=1)  ");
		
		Map paramMap = this.getJson();
		
		Iterator iterator = paramMap.keySet().iterator();
		
		while(iterator.hasNext()){
			String key =(String) iterator.next();
			String keyValue = (String)paramMap.get(key);
			if(keyValue!=null && !keyValue.equals("")){
				
				if("FEATURE_NAME".equals(key)){
					sb.append("  and  FEATURE_NAME like '%"+keyValue+"%'  ");
				}else{					
					sb.append( " and  FEATURE_CATL_ID = "+keyValue);
				}				
			}
		}		
		commonQueryService.setPrimaryKey("FEATURE_ID");
		commonQueryService.addOracleLookup("FEATURE_CATL_ID", "FEATURE_CATL_ID");
		commonQueryService.addOracleLookup("FEATURE_STATUS", "IS_CREA_CHANCE");
		productFeatureMap = commonQueryService.excuteQuery(sb.toString(), getStart(), getLimit());
		
		return "success";
	}
	
	public Map getProductFeatureMap() {
		return productFeatureMap;
	}
	public void setProductFeatureMap(Map productFeatureMap) {
		this.productFeatureMap = productFeatureMap;
	}

}

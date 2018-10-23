/**
 * 
 */
package com.yuchengtech.bcrm.product.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.yuchengtech.bcrm.product.service.ProductFeatureListService;
import com.yuchengtech.bob.action.BaseAction;

/**
 * @author yaoliang
 *
 */
@ParentPackage("json-default")

@Action(value="/product-feature-search", results={
    @Result(name="success", type="json")
})
public class ProductFeatureSearchAction extends BaseAction{

	private Map featureSearchMap = new HashMap();
	@Resource(name = "productFeatureListService")
	private ProductFeatureListService productFeatureListService;
	
	public String index (){		
		
		StringBuffer sb = new StringBuffer("select OCRM_F_PD_PROD_FEATURE.* from OCRM_F_PD_PROD_FEATURE where (1 = 1) ");
		Iterator iterator = this.getJson().keySet().iterator();		
		while(iterator.hasNext()){
			
			String key = (String)iterator.next();
			
			String keyValue =this.getJson().get(key) + "";
			
			if( keyValue != null && !keyValue.equals("") && !keyValue.equals("null")){				
				if( "FEATURE_CATL_ID" .equals(key))
				sb.append("  and OCRM_F_PD_PROD_FEATURE.FEATURE_CATL_ID = "+keyValue);
			}
		}		
		
		
		featureSearchMap.put("data",productFeatureListService.featureSearchList(sb.toString()));		

		return "success";
	}

	public Map getFeatureSearchMap() {
		return featureSearchMap;
	}

	public void setFeatureSearchMap(Map featureSearchMap) {
		this.featureSearchMap = featureSearchMap;
	}

	public ProductFeatureListService getProductFeatureListService() {
		return productFeatureListService;
	}

	public void setProductFeatureListService(
			ProductFeatureListService productFeatureListService) {
		this.productFeatureListService = productFeatureListService;
	}
	
}

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

@Action(value="/product-item-relation-list", results={
    @Result(name="success", type="json")
})
public class ProductItemRelationListAction  extends BaseAction {
	
	private Map relationMap; 
	@Autowired
	private CommonQueryService commonQueryService;
	public String index(){
		StringBuffer sb = new StringBuffer("select OCRM_F_PD_PROD_ITEM_REL.*,OCRM_F_PD_PROD_CATL.catl_name, OCRM_F_PD_PROD_INFO.prod_name from OCRM_F_PD_PROD_ITEM_REL,OCRM_F_PD_PROD_INFO,OCRM_F_PD_PROD_CATL  where "
				+" OCRM_F_PD_PROD_ITEM_REL.product_id = OCRM_F_PD_PROD_INFO.product_id and  OCRM_F_PD_PROD_INFO.catl_code = OCRM_F_PD_PROD_CATL.catl_code  ");
		Map paramMap = getJson();
		Iterator iterator = paramMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String)iterator.next();
			String keyValue = (String)paramMap.get(key);
			if(keyValue != null && !keyValue .equals("") && !keyValue.equals("null")){				
				sb.append("  and OCRM_F_PD_PROD_ITEM_REL."+key+" ='"+keyValue+"'");
			}
		}
		commonQueryService.setPrimaryKey("OCRM_F_PD_PROD_ITEM_REL.ID");
		commonQueryService.addOracleLookup("TJKJ", "TJKJ");
		commonQueryService.addOracleLookup("REL_TYPE", "REL_TYPE");
		relationMap = commonQueryService.excuteQuery(sb.toString(), getStart(), getLimit());
		
		return "success";
	}
	public Map getRelationMap() {
		return relationMap;
	}
	public void setRelationMap(Map relationMap) {
		this.relationMap = relationMap;
	}	
	
}
